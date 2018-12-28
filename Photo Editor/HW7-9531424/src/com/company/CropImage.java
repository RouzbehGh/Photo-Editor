package com.company;

/**
 * Created by Roozbeh Ghasemi on 6/2/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

public class CropImage extends JFrame {

    Image image;
    Insets insets;

    public CropImage(BufferedImage img) {
        super("Crop Image");
        ImageIcon icon = new ImageIcon(img);
        image = icon.getImage();
        image = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(150, 120, 190, 150)));
    }

    public CropImage() {

    }

    public void paint(Graphics g) {
        super.paint(g);
        if (insets == null) {
            insets = getInsets();
        }
        g.drawImage(image, insets.left, insets.top, this);
    }

    public void starter(BufferedImage img) {
        JFrame frame = new CropImage(img);
        frame.setSize(250, 250);
        frame.show();
    }
}