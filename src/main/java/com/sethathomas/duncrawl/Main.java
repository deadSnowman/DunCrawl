/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sethathomas.duncrawl;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author seth
 */
public class Main {

    private static Game game;

    public static void main(String[] args) {
        initDisplay();
        initGame();
        cleanUp();
    }

    private static void initGame() {
        game = new Game();
        game.run();
    }

    public static void cleanUp() {
        Display.destroy();
    }

    private static void initDisplay() {
        try {
            //Display.setDisplayMode(new DisplayMode(800, 600));
            //Display.setDisplayMode(new DisplayMode(1366, 768));

            DisplayMode[] modes = Display.getAvailableDisplayModes();

            for (DisplayMode current : modes) {
                System.out.println(current.getWidth() + "x" + current.getHeight() + "x"
                        + current.getBitsPerPixel() + " " + current.getFrequency() + "Hz");
            }

            Display.setFullscreen(true); // not working
            Display.create();

            Display.setVSyncEnabled(true);
        } catch (LWJGLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
