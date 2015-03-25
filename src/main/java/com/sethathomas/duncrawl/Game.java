/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import org.newdawn.slick.opengl.TextureLoader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import static java.lang.System.exit;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.newdawn.slick.opengl.Texture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author seth
 */
public class Game {

  private static enum State {

    INTRO, GAME, MENU, MAIN_MENU;
  }
  private static State state;

  Bitmap level;
  int[] playerpos = new int[2];

  // Textures
  Texture brick = loadTexture("brick");
  Texture floor = loadTexture("floor3");
  Texture ceiling = loadTexture("ceiling");
  Texture stairs = loadTexture("stairs");

  // Monsters
  Texture tbearSprite = loadTexture("monsters/tamanbear");
  Texture mantisSprite = loadTexture("monsters/mantis");
  Texture nessieSprite = loadTexture("monsters/nessie");
  Texture batsSprite = loadTexture("monsters/bats");

  // Camera
  Camera cam;

  // Priority Queue
  PriorityQueue<Monster> pq;
  ComparatorImpl ci = new ComparatorImpl();

  public Game() {
    level = new Bitmap("level1.png");
    cam = new Camera((float) 65, (float) Display.getWidth() / Display.getHeight(), 0.3f, 1000);
    state = State.GAME;
  }

  public void run() {

    // Display level in console and collect player data
    System.out.println("Height: " + level.getHeight() + ", Width: " + level.getWidth());
    for (int i = 0; i < level.getWidth(); i++) {
      for (int j = 0; j < level.getHeight(); j++) {
	if (level.getPixel(i, j) == -1) {
	  System.out.print("f");
	} else if (level.getPixel(i, j) == -16777216) {
	  System.out.print("w");
	} else if (level.getPixel(i, j) == -65536) {
	  System.out.print("p");
	  playerpos[0] = i;
	  playerpos[1] = j;
	} else {
	  System.out.print(" ");
	  //System.out.print("e(" + level.getPixel(i, j) + ")");
	}
      }
      System.out.println();
    }
    System.out.println(playerpos[0]);
    System.out.println(playerpos[1]);

    // Set camera
    cam.setX(-playerpos[0] * 2);
    cam.setZ(playerpos[1] * 2);
    cam.rotateY(180);

    // initialize priority queue
    pq = new PriorityQueue<>(100, ci);

    // Display Loop
    Cube cube = new Cube();
    Monster monster = new Monster(playerpos[0] * 2, -playerpos[1] * 2, tbearSprite);
    Monster monster2 = new Monster((playerpos[0] - 2) * 2, -(playerpos[1] - 1) * 2, mantisSprite);
    Menu menu = new Menu();

    while (!Display.isCloseRequested()) {
      keyboardLoop();

      switch (state) {
	case INTRO:
	  glColor3f(1.0f, 0.0f, 0.0f);
	  glRectf(0, 0, 800, 600);
	  break;
	case GAME:
	  glDisable(GL_BLEND);
	  glColor3f(5f, 5f, 5f); // clear INTRO color (fix this)
	  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	  glLoadIdentity();
	  cam.useView();

	  // Generate level from map
	  for (int i = 0; i < level.getWidth(); i++) {
	    for (int j = 0; j < level.getHeight(); j++) {
	      if (level.getPixel(i, j) == -1) {
		cube.drawCube(i * 2, -j * 2, floor, "floor");
		cube.drawCube(i * 2, -j * 2, ceiling, "ceiling");
	      } else if (level.getPixel(i, j) == -16777216) {
		cube.drawCube(i * 2, -j * 2, brick, "block");
	      } else if (level.getPixel(i, j) == -65536) {
		cube.drawCube(i * 2, -j * 2, floor, "floor");
		cube.drawCube(i * 2, -j * 2, ceiling, "ceiling");
	      } else if (level.getPixel(i, j) == -10066330) {
		cube.drawCube(i * 2, -j * 2, stairs, "floor");
		cube.drawCube(i * 2, -j * 2, ceiling, "ceiling");
	      }
	    }
	  }

	  ci.cam = cam;

	  pq.add(monster);
	  pq.add(monster2);

	  while (!pq.isEmpty()) {
	    Monster m = pq.remove();
	    glPushMatrix();
	    m.faceCam(cam);
	    m.drawMonster();
	    glPopMatrix();
	  }

	  break;
	case MENU:
	  glClear(GL_DEPTH_BUFFER_BIT);
	  menu.drawMenu();

	  //cube.drawCube(playerpos[0] * 2, -playerpos[1] * 2, brick, "block");
	  break;
      }

      Display.update();
      Display.sync(60);
    }
  }

  public static Texture loadTexture(String key) {
    try {
      return TextureLoader.getTexture("png", new FileInputStream(new File("res/" + key + ".png")));
    } catch (IOException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public void keyboardLoop() {
    boolean forward = Keyboard.isKeyDown(Keyboard.KEY_I);
    boolean backward = Keyboard.isKeyDown(Keyboard.KEY_K);
    boolean left = Keyboard.isKeyDown(Keyboard.KEY_J);
    boolean right = Keyboard.isKeyDown(Keyboard.KEY_L);

    // Debug movement
    if (state == State.GAME) {
      if (forward) {
	cam.move(.3f, 1);
      }
      if (backward) {
	cam.move(-.3f, 1);
      }
      if (left) {
	cam.move(.3f, 0);
      }
      if (right) {
	cam.move(-.3f, 0);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
	cam.rotateY(-4f);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
	cam.rotateY(4f);
      }

      // Real movement
      if (Keyboard.next()) {
	if (Keyboard.getEventKeyState()) {
	  if (Keyboard.getEventKey() == Keyboard.KEY_W) {
	    cam.moveGrid(2f, 1);
	    System.out.println("W was pressed - moved to X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  if (Keyboard.getEventKey() == Keyboard.KEY_S) {
	    cam.moveGrid(-2f, 1);
	    System.out.println("S was pressed - moved to X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  if (Keyboard.getEventKey() == Keyboard.KEY_A) {
	    cam.moveGrid(2f, 0);
	    System.out.println("A was pressed - moved to X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  if (Keyboard.getEventKey() == Keyboard.KEY_D) {
	    cam.moveGrid(-2f, 0);
	    System.out.println("D was pressed - moved to X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
	    cam.rotateY(-90f);
	    System.out.println("Q was pressed - stayed at X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  if (Keyboard.getEventKey() == Keyboard.KEY_E) {
	    cam.rotateY(90f);
	    System.out.println("E was pressed - stayed at X: " + cam.getX() + ", Z: " + cam.getZ());
	  }
	  // Switch from GAME state to MENU state
	  if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
	    state = State.MENU;
	    System.out.println("MENU");
	  }
	  // Close game
	  if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
	    Display.destroy();
	    exit(0);
	  }
	}
      }
    } // end GAME state keyboard reading

    // Switch from MENU state to GAME state
    if (state == State.MENU) {
      if (Keyboard.next()) {
	if (Keyboard.getEventKeyState()) {
	  if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
	    state = State.GAME;
	    System.out.println("GAME");
	  }
	}
      }
    } // end MENU state keyboard reading

  }

  static class ComparatorImpl implements Comparator<Monster> {

    Camera cam;

    @Override
    public int compare(Monster m1, Monster m2) {

      Vector3f m1dist = new Vector3f(m1.getX() - -cam.getX(), 0, m1.getZ() - -cam.getZ());
      Vector3f m2dist = new Vector3f(m2.getX() - -cam.getX(), 0, m2.getZ() - -cam.getZ());
      float result = m2dist.length() - m1dist.length();

      if (result > 0) {
	return 1;
      } else if (result < 0) {
	return -1;
      } else {
	return 0;
      }
    }
  }
}
