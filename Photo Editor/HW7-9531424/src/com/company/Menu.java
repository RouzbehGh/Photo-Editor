package com.company;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Roozbeh Ghasemi on 5/15/2017.
 */
public class Menu extends JFrame {
    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
    JLabel blueLabel;
    JLabel redLabel;
    JLabel greenLabel;
    JPanel colorPanel;
    JPanel sliders;
    JPanel labels;
    static SimplePaint sp = new SimplePaint();
    private JMenu file, edit;
    private JMenuItem openItem, closeItem, newItem, saveItem, Simplepaint;
    private JMenuItem filterItem, rotateItem, changecolor;
    private static JLabel jLabel;
    private JList filtersList;
    static BufferedImage img;
    private static BufferedImage tmpImg;
    private static BufferedImage tmpImg2;
    public static BufferedImage tmpImg4;

    private JButton button;
    private JPanel filterPanel, rotatePanel;
    private double angle;
    public int x, y;

    public static void setImg(BufferedImage mimg) {
        img = mimg;
    }

    public static void setTmpImg2(BufferedImage mtmpImg2) {
        tmpImg2 = mtmpImg2;
    }

    public static void setTmpImg(BufferedImage mtmpImg) {
        tmpImg = mtmpImg;
    }

    public BufferedImage getImg() {
        return img;
    }

    public Menu() {
        createLabel();
        createMenuBar();
        setTitle("Retica Photo Editor");
        setSize(550, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createLabel() {
        jLabel = new JLabel();
        jLabel.setBounds(10, 10, 670, 250);


        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jLabel.setVerticalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());

        add(jLabel);
    }

    private void createfiltersArray() {
        filterPanel = new JPanel();
        String[] names = {"Icons\\original.jpg", "Icons\\Twirl.png", "Icons\\Invert.png", "Icons\\Crystallize.png", "Icons\\Blur.png", "Icons\\ColorHalftone.png", "Icons\\Grayscale.png", "Icons\\Mirror.png", "Icons\\Stamp.png", "Icons\\Weave.png", "Icons\\solarize.png", "Icons\\GaussianFilter.png"};
        ImageIcon[] filterIcons = new ImageIcon[12];
        setLayout(new FlowLayout());


        for (int i = 0; i < 12; i++) {
            try {
                File f = new File(names[i]);
                BufferedImage image = ImageIO.read(f);
                filterIcons[i] = new ImageIcon(image);

            } catch (IOException e) {
                System.out.println(e);
            }
        }

        JList filtersList = new JList(filterIcons);
        filtersList.setVisibleRowCount(5);
        filtersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        filterPanel.add(new JScrollPane(filtersList));
        filtersList.addListSelectionListener(
                new ListSelectionListener() {
                    // handle list selection events
                    public void valueChanged(ListSelectionEvent event) {
                        int n = filtersList.getSelectedIndex();
                        Image.applyFilters(n, img);
                    }
                });
        button = addButton();
        filterPanel.add(button);
        add(filterPanel);
        setVisible(true);
    }

    public JButton addButton() {
        button = new JButton("Select");
        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        img = tmpImg2;
                    }
                });
        return button;
    }

    public void changecolor() {
        redSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
        blueSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
        greenSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
        redLabel = new JLabel("Red = 0");
        blueLabel = new JLabel("Blue = 0");
        greenLabel = new JLabel("Green = 0");
        event e = new event();
        redSlider.addChangeListener(e);
        blueSlider.addChangeListener(e);
        greenSlider.addChangeListener(e);
        colorPanel = new JPanel();
        ImageIcon image = new ImageIcon(img);
        JLabel label = new JLabel(image);
        colorPanel.setBackground(java.awt.Color.CYAN);
        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(1, 3, 3, 3));
        sliders = new JPanel();
        labels = new JPanel();
        pane.add(sliders);
        pane.add(labels);
        pane.add(colorPanel);
        sliders.setLayout(new GridLayout(3, 1, 2, 3));
        sliders.add(redSlider);
        sliders.add(greenSlider);
        sliders.add(blueSlider);
        labels.setLayout(new GridLayout(3, 1, 2, 3));
        labels.add(redLabel);
        labels.add(greenLabel);
        labels.add(blueLabel);
    }

    public class event implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int r = redSlider.getValue();
            int g = greenSlider.getValue();
            int b = blueSlider.getValue();
            redLabel.setText("Red =     " + r);
            greenLabel.setText("Green = " + g);
            blueLabel.setText("Blue =   " + b);
            colorPanel.setBackground(new java.awt.Color(r, g, b));
        }

    }


    public void doRotation() {
        Rotate rot = new Rotate();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        slider.setMinorTickSpacing(15);
        slider.setMajorTickSpacing(90);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        rotatePanel = new JPanel();
        rotatePanel.add(slider);
        button = addButton();
        rotatePanel.add(button);
        add(rotatePanel);


        class SliderListener implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    angle = (double) source.getValue();
                    System.out.println(angle);
                    tmpImg2 = rot.rotate(img, angle);
                    resizeImage(tmpImg2);
                }
            }
        }
        slider.addChangeListener(new SliderListener());
        setVisible(true);
    }


    private void createMenuBar() {

        //create menu bar
        JMenuBar menubar = new JMenuBar();

        //create menu objects
        file = new JMenu("File");
        edit = new JMenu("Edit");

        //create menu items
        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        closeItem = new JMenuItem("Close");
        saveItem = new JMenuItem("Save");
        Simplepaint = new JMenuItem("Simple Paint");
        filterItem = new JMenuItem("Filter");
        rotateItem = new JMenuItem("Rotate");
        changecolor = new JMenuItem("Color");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");


        //handler

        handler handler = new handler();
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitMenuItem.addActionListener(handler);
        Simplepaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.SimplePaint();
            }
        });
        newItem.addActionListener(handler);
        openItem.addActionListener(handler);
        closeItem.addActionListener(handler);
        saveItem.addActionListener(handler);

        filterItem.addActionListener(handler);
        changecolor.addActionListener(handler);
        rotateItem.addActionListener(handler);

        //add items to menu objects
        file.add(newItem);
        file.add(openItem);
        file.add(Simplepaint);
        file.add(closeItem);
        file.add(saveItem);
        file.add(exitMenuItem);
        edit.add(filterItem);
        edit.add(rotateItem);
        edit.add(changecolor);

        //add menu objects to bar
        menubar.add(file);
        menubar.add(edit);

        //set bar
        setJMenuBar((menubar));

    }

    public void removeC() {
        if (isAncestorOf(filterItem)) {
            System.out.println("filter true!");
            remove(filterPanel);
        }
        if (isAncestorOf(rotatePanel)) {
            System.out.println("Rotate true!");
            remove(rotatePanel);
        }

    }

    private class handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == filterItem) {

                removeC();
                createfiltersArray();

            }

            if (event.getSource() == rotateItem) {
                removeC();
                doRotation();

            }
            if (event.getSource() == changecolor) {
                removeC();
                changecolor();
            }

            if (event.getSource() == openItem) {
                Image.openImage(img, tmpImg);

            }
            if (event.getSource() == newItem) {


                Image imgclass = new Image();

                imgclass.newImage();
                img = imgclass.getImg();
                resizeImage(img);


            }
            if (event.getSource() == closeItem) {
                System.exit(0);
            }
            if (event.getSource() == saveItem) {
                try {
                    Image.saveImage(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void resizeImage(BufferedImage image) {
        ImageIcon img1 = new ImageIcon(image);
        jLabel.setIcon(img1);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Menu ex = new Menu();
            ex.setVisible(true);
        });

    }
}
