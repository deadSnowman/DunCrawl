/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import java.awt.Font;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author seth
 */
public class Menu {

  //TrueTypeFont font;
  static TrueTypeFont trueTypeFont;
  static Font font;

  public Menu() {
    font = new Font("Serif", Font.PLAIN, 32);
    trueTypeFont = new TrueTypeFont(font, true);
  }

  public void drawMenu() {
    // Draw 2d
    glDisable(GL_DEPTH_TEST);
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, 1366, 768, 0, -1, 10);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();

    // enable trasparency
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glColor4d(1, 1, 1, 1); // r, g, b, alpha
    
    //Color.white.bind();
    glColor3f(0.5f, 0.5f, 1.0f);
    //glRectf(30, 30, Display.getWidth() - 30, Display.getHeight() - 30);


    trueTypeFont.drawString(20.0f, 20.0f, "Slick displaying True Type Fonts", Color.white);
    
    glDisable(GL_BLEND);
    
    
    // Draw 3d again
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glEnable(GL_DEPTH_TEST);
  }
}
