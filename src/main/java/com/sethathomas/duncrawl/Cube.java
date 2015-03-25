/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import org.newdawn.slick.opengl.Texture;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author seth
 */
public class Cube {

  public Cube() {
    
  }
  
  public void drawCube(float x, float z, Texture tex, String type) {
    // Bind texture
    tex.bind();

    // Draw Cube
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //unblur
    glBegin(GL_QUADS);

    // wall
    if (type.equals("block")) {
      // FrontFace
      //glColor3f(1f, 0f, 0f);
      glTexCoord2f(0, 1);
      glVertex3f(x - 1, -1, z + 1);
      glTexCoord2f(0, 0);
      glVertex3f(x - 1, +1, z + 1);
      glTexCoord2f(1, 0);
      glVertex3f(x + 1, +1, z + 1);
      glTexCoord2f(1, 1);
      glVertex3f(x + 1, -1, z + 1);

      // BackFace
      //glColor3f(0f, 1f, 0f);
      glTexCoord2f(1, 1);
      glVertex3f(x - 1, -1, z - 1);
      glTexCoord2f(1, 0);
      glVertex3f(x - 1, +1, z - 1);
      glTexCoord2f(0, 0);
      glVertex3f(x + 1, +1, z - 1);
      glTexCoord2f(0, 1);
      glVertex3f(x + 1, -1, z - 1);

      // LeftFace
      //glColor3f(0f, 0f, 1f);
      glTexCoord2f(0, 1);
      glVertex3f(x - 1, -1, z - 1);
      glTexCoord2f(1, 1);
      glVertex3f(x - 1, -1, z + 1);
      glTexCoord2f(1, 0);
      glVertex3f(x - 1, +1, z + 1);
      glTexCoord2f(0, 0);
      glVertex3f(x - 1, +1, z - 1);

      // RightFace
      //glColor3f(1f, 1f, 0f);
      glTexCoord2f(1, 1);
      glVertex3f(x + 1, -1, z - 1);
      glTexCoord2f(0, 1);
      glVertex3f(x + 1, -1, z + 1);
      glTexCoord2f(0, 0);
      glVertex3f(x + 1, +1, z + 1);
      glTexCoord2f(1, 0);
      glVertex3f(x + 1, +1, z - 1);
    } //floor
    else if (type.equals("floor")) {
      // BottomFace
      //glColor3f(0f, 1f, 1f);
      glTexCoord2f(0, 0);
      glVertex3f(x - 1, -1, z - 1);
      glTexCoord2f(1, 0);
      glVertex3f(x + 1, -1, z - 1);
      glTexCoord2f(1, 1);
      glVertex3f(x + 1, -1, z + 1);
      glTexCoord2f(0, 1);
      glVertex3f(x - 1, -1, z + 1);
    } else if (type.equals("ceiling")) {
      // TopFace
      //glColor3f(1f, 0f, 1f);
      glTexCoord2f(0, 0);
      glVertex3f(x - 1, +1, z - 1);
      glTexCoord2f(1, 0);
      glVertex3f(x + 1, +1, z - 1);
      glTexCoord2f(1, 1);
      glVertex3f(x + 1, +1, z + 1);
      glTexCoord2f(0, 1);
      glVertex3f(x - 1, +1, z + 1);
    } else {
      System.out.println("not valid block");
    }
    glEnd();
  }

  public void moveCube(float x, float z) {
    glTranslatef(x, 0, z);
  }
}
