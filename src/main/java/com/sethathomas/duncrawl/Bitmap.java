/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author seth
 */
public class Bitmap {
  
  private int width;
  private int height;
  private int[] pixels;
  
  public Bitmap(String fileName) {
    try {
      BufferedImage image = ImageIO.read(new File("./res/bitmaps/" + fileName));
      
      width = image.getWidth();
      height = image.getHeight();
      
      pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
      /*for( int i = 0; i < width; i++ )
        for( int j = 0; j < height; j++ )
          pixels[i][j] = image.getRGB(i, j);*/
    } catch (IOException ex) {
      Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    this.flipX(); //reorient level
  }
  public Bitmap(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
  }
  
  public Bitmap flipY() {
    int[] tmp = new int[pixels.length];
    
    for(int i = 0; i < width; i++)
      for(int j = 0; j < height; j++){
        tmp[i + j * width] = pixels[(width - i - 1) + j * width];
      }
    
    pixels = tmp;
    return this;
  }
  
  public Bitmap flipX() {
    int[] tmp = new int[pixels.length];
    
    for(int i = 0; i < width; i++)
      for(int j = 0; j < height; j++){
        tmp[i + j * width] = pixels[i + (height - j - 1) * width];
      }
    
    pixels = tmp;
    return this;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixels() {
    return pixels;
  }
  
  public int getPixel(int x, int y) {
    return pixels[x + y * width];
  }
  
  public void setPixel(int x, int y, int value) {
    pixels[x + y * width] = value;
  }

  public int getWidth() {
    return width;
  }
  
  
}
