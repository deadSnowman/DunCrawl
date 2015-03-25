/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author seth
 */
public class Monster {

  private final float x;
  private final float z;
  private final Texture tex;

  public Monster(float x, float z, Texture tex) {
    this.x = x;
    this.z = z;
    this.tex = tex;
  }

  public float getX() {
    return x;
  }

  public float getZ() {
    return z;
  }

  public void drawMonster() {

    glPushMatrix();

    glMatrixMode(GL_MODELVIEW);

    // enable trasparency
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glColor4d(1, 1, 1, 1); // r, g, b, alpha

    // Bind texture
    this.tex.bind();

    // Draw Plane
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //unblur
    glBegin(GL_QUADS);
    glTexCoord2f(0, 1);
    glVertex3f(x - 1, -1, z);
    glTexCoord2f(0, 0);
    glVertex3f(x - 1, +1, z);
    glTexCoord2f(1, 0);
    glVertex3f(x + 1, +1, z);
    glTexCoord2f(1, 1);
    glVertex3f(x + 1, -1, z);
    glEnd();

    glDisable(GL_BLEND);
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();

  }

  public void moveMonster(float x, float z) {
    glTranslatef(x, 0, z);
  }

  public void rotate(float x) {
    glTranslatef(this.x, 0, this.z);
    glRotatef(x, 0, 1, 0);
    glTranslatef(-this.x, 0, -this.z); // translate back to 0, 0
  }

  public void faceCam(Camera cam) {
    Vector3f directToCam = new Vector3f(this.x - -cam.getX(), 0, this.z - -cam.getZ());

    if (directToCam.getX() < 0) {
      rotate(-(float) Math.toDegrees(Math.atan(directToCam.getZ() / directToCam.getX())) + 90);
    } else {
      rotate(-(float) Math.toDegrees(Math.atan(directToCam.getZ() / directToCam.getX())) + 270);
    }
  }
}
