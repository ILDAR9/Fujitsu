package com.ildar.web.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 02.08.12
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class ShowImage extends Component {

    BufferedImage img;
    static int height;
    static int width;

    public ShowImage(BufferedImage img) {      //install image into JFrame
        this.img = img;
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100, 100);
        } else {
            return new Dimension(img.getHeight(), img.getTileWidth());
        }
    }


    public static void writeSize(String info, int width, int height) {
        System.out.printf("%s width = %s; height = %s.\n", info, width, height);
    }

    private static void checkSize(JFrame frame, BufferedImage img) {
        writeSize("Original size:", img.getWidth(), img.getHeight());
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();
        int temp;
        float k = img.getWidth() / (float) img.getHeight();   // width / height
        width = img.getWidth();
        height = img.getHeight();
        if (img.getWidth() > (temp = (int) bounds.getWidth() - 200)) {  //if original width is bigger than screen width
            width = temp;
            height = (int) ((float) width / k);
        }
        if (img.getHeight() > (temp = (int) bounds.getHeight() - 200)) { //so for height
            height = temp;
            width = (int) ((float) height * k);
        }
        System.out.println("final: " + width + " " + height);

    }

    public static void showImg(BufferedImage img) {
        if (img == null) {
            System.err.println("The path isn't correct!!!");
            return;
        }
        JFrame frame = new JFrame("Finded ^_^");     //frame's name
        checkSize(frame, img);

        img = ImageWork.scale(img, width, height, img.isAlphaPremultiplied());   //trying to adjust image size
        ShowImage.writeSize("Scaled size:", width, height);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //switch on closing function

        frame.add(new ShowImage(img));
        frame.pack();     //packing heap  of components
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);   //shows us that it gets
    }

    public static void showImg(File file) {
        BufferedImage img = ImageWork.loadImage(file);
        showImg(img);
    }

   /* public static void showImg() {
        showImg(ImageWork.readPathToImg());
    }     */
}
