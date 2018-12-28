package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Roozbeh Ghasemi on 5/18/2017.
 */
public class Rotate {

    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = (int)Math.floor(width*cos+height*sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((newWidth - width) / 2, (newHeight - height) / 2);
        g.rotate(Math.toRadians(angle), width / 2, height / 2);
        g.drawRenderedImage(image, null);
        g.dispose();

        return result;

    }
}