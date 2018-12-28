package com.company;


import com.company.filters.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Roozbeh Ghasemi on 5/18/2017.
 */
public class Image extends Menu {
    private static BufferedImage  colorImage;
    private static int red, blue, green, alpha;
    private static BufferedImage tmpImg;
    public Menu mn = new Menu();
    public static CropImage cropImage = new CropImage();
    private static BufferedImage imgs;

    public Image(BufferedImage img) {
        this.imgs = img;
    }

    public Image() {
    }

    public static void openImage(BufferedImage img, BufferedImage tmpImg) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                img = ImageIO.read(selectedFile);
                Menu.setImg(img);
                Menu.setTmpImg(img);
            } catch (IOException e) {
                System.out.println(e);
            }
            Menu.resizeImage(img);
            cropImage.starter(img);
        }
    }

    public static void saveImage(BufferedImage img) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File f = new File(fileChooser.getSelectedFile() + "");
                ImageIO.write(img, "png", f);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void applyFilters(int n, BufferedImage img) {
        tmpImg = img;

        if (n == 0) {
            img = tmpImg;
        }
        if (n == 1) {
            TwirlFilter filter = new TwirlFilter();
            filter.setAngle(5);
            filter.setRadius(500);
            img = filter.filter(img, null);

        }
        if (n == 2) {
            InvertFilter filter = new InvertFilter();
            img = filter.filter(img, null);
        }
        if (n == 3) {
            CrystallizeFilter filter = new CrystallizeFilter();
            img = filter.filter(img, null);
        }
        if (n == 4) {
            BlurFilter filter = new BlurFilter();
            img = filter.filter(img, null);
        }
        if (n == 5) {
            ColorHalftoneFilter filter = new ColorHalftoneFilter();
            img = filter.filter(img, null);
        }
        if (n == 6) {
            GrayscaleFilter filter = new GrayscaleFilter();
            img = filter.filter(img, null);
        }
        if (n == 7) {
            MirrorFilter filter = new MirrorFilter();
            img = filter.filter(img, null);
        }
        if (n == 8) {
            StampFilter filter = new StampFilter();
            img = filter.filter(img, null);

        }
        if (n == 9) {
            WeaveFilter filter = new WeaveFilter();
            img = filter.filter(img, null);
        }
        if (n == 10) {
            SolarizeFilter filter = new SolarizeFilter();
            img = filter.filter(img, null);
        }
        if (n == 11) {
            GaussianFilter filter = new GaussianFilter();
            img = filter.filter(img,null);
        }
        Menu.resizeImage(img);
        Menu.setTmpImg2(img);
        Menu.setImg(tmpImg);


    }
    public BufferedImage getImg() {

        return imgs;
    }
    public static void getRGB(int i, int j) {
        int pixel = colorImage.getRGB(i, j);
        alpha = (pixel >> 24) & 0xff;
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = pixel & 0xff;
    }

    public static BufferedImage filterColor(BufferedImage mimg, int amount, int color) {
        colorImage = new BufferedImage(mimg.getWidth(), mimg.getHeight(), mimg.getType());
        Graphics g = colorImage.getGraphics();
        g.drawImage(mimg, 0, 0, null);
        g.dispose();
        if (amount == 128) {
            return mimg;
        }
        for (int i = 0; i < colorImage.getWidth(); i++) {
            for (int j = 0; j < colorImage.getHeight(); j++) {
                getRGB(i, j);
                switch (color) {
                    case 0:
                        red = amount;
                        break;
                    case 1:
                        green = amount;
                        break;
                    case 2:
                        blue = amount;
                        break;
                }
                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                colorImage.setRGB(i, j, newPixel);
            }
        }
        return colorImage;
    }

    public void newImage() {

        imgs = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < imgs.getWidth(); i++) {
            for (int j = 0; j < imgs.getHeight(); j++) {
                int alpha, red, green, blue;
                alpha = red = green = blue = 250;
                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                imgs.setRGB(i, j, newPixel);

            }
        }
    }
}

