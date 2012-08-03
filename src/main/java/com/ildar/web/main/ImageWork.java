package com.ildar.web.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 01.08.12
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
public class ImageWork {

    static int[] hashCode = new int[3];
    static BufferedImage img;
    static boolean isException;

    public static File readPathToImg() {
        Scanner scanner = new Scanner(System.in);
        return new File(scanner.nextLine());
    }

    public static BufferedImage loadImage(File file) {

        BufferedImage img = null;
//        synchronized (img) {
        if (file.exists()) {
            try {
                img = ImageIO.read(file);
                System.out.printf("Image '%s' is loaded.\n", file.getCanonicalPath());
            } catch (IOException e) {
                System.err.printf("Can't load the image '%s'\n", file.getAbsolutePath());
            }
        } else {
            System.err.println("The path isn't correct");
        }

        return img;
    }

    private static BufferedImage deleteRGB(BufferedImage img) {   //taking several common parts of R, G and B
        int temp;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                temp = img.getRGB(j, i);
                int r = (int) ((temp & 0xff) * 0.3);
                int g = temp & 0xff00;
                g >>>= 8;
                g = (int) (0.59 * g);
                int b = temp & 0xff0000;
                b >>>= 16;
                b = (int) (0.11 * b);
                temp = 0;
                temp = r + g + b;
                img.setRGB(j, i, temp);
            }
        }
        return img;
    }

    public static BufferedImage scale(BufferedImage image, int width, int height, boolean alpha) {
        System.out.println("Scalling...");
        BufferedImage scaledImg = new BufferedImage(width, height,
                (alpha) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImg.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        return scaledImg;
    }

    private static int averageSum(File file) {
        img = loadImage(file);
        if (img == null) {
            return -1;
        }
        img = scale(img, 8, 8, img.isAlphaPremultiplied());                 //уменьшаем до 8*8
        img = deleteRGB(img);                                   //получаем однопотоковую картинку

        int sum = 0;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                sum += img.getRGB(j, i);
            }
        }
        return sum / (img.getHeight() * img.getWidth());
    }

    private static int toBite(BufferedImage img, int averageSum, int start, int end) {
        int temp = 0;
        for (int i = start; i < end; i++) {
            for (int j = 0; j < img.getTileWidth(); j++) {
                if (img.getRGB(j, i) < averageSum) {
                    temp += 1;           //черный
                }
                temp <<= 1;             //смещаем
            }
        }
        return temp;
    }

    public static int checkingHemingDistance(int firstPart0, int secondPart0, int lastPart0, int max) {
        int sum = 0;
        sum += xorCounter(hashCode[0], firstPart0);
        if (sum > max) {
            return -1;
        }
        sum += xorCounter(hashCode[1], secondPart0);
        if (sum > max) {
            return -1;
        }
        sum += xorCounter(hashCode[2], lastPart0);
        if (sum > max) {
            return -1;
        }
        return sum;
    }

    public static int fullHemingDistance(int firstPart0, int secondPart0, int lastPart0) {
        int sum = 0;
        sum += xorCounter(hashCode[0], firstPart0);
        sum += xorCounter(hashCode[1], secondPart0);
        sum += xorCounter(hashCode[2], lastPart0);
        return sum;
    }    //ДЛя интереса

    private static int xorCounter(int temp, int temp0) {
        // System.out.println(temp + " " + temp0);
        temp = temp ^ temp0; //xor - им поэлементно
        temp0 = 0;           //кол-во единичек
        do {
            temp0 += temp % 2;
        } while ((temp /= 2) != 0);
        return temp0;
    }

    private static BufferedImage square(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        if (width >= height) {
            return img.getSubimage((width - height) / 2, 0, height, height);
        } else {
            return img.getSubimage(0, (height - width) / 2, width, width);
        }
    }

    private static void getHardHash(File file) {
        int averageSum = averageSum(file);    //добавить метод square для увеличения узнаваемости (отсутствует)
        hashCode[0] = toBite(img, averageSum, 0, 3);
        hashCode[1] = toBite(img, averageSum, 3, 6);
        hashCode[2] = toBite(img, averageSum, 6, 8);
    }        // вывод хэша для случая  с квадратом в центре

    private static void getSimpleHash(int averageSum) {
        hashCode[0] = toBite(img, averageSum, 0, 3);
        hashCode[1] = toBite(img, averageSum, 3, 6);
        hashCode[2] = toBite(img, averageSum, 6, 8);
    }

    public static void compareImagesTest(File file) {
        List<String> ans;
        int max = 4;
        int averageSum;
        do {
            max += 2;                                         //начнем с max = 6
            averageSum = averageSum(file);
            for (int i = 0; i < 4; i++) {
                hashCode[0] = toBite(img, averageSum, 0, 3);
                hashCode[1] = toBite(img, averageSum, 3, 6);
                hashCode[2] = toBite(img, averageSum, 6, 8);

            }
            getSimpleHash(averageSum(file));
            ans = HashBase.startSearch(max); // Возвращется обратно в этот класс для проверки дистанции Хеминга
        } while (ans.isEmpty());
        System.out.println("max = " + max);
        Iterator<String> iter = ans.iterator();
        while (iter.hasNext()) {
            String temp = iter.next();
            System.out.println(temp);
            ShowImage.showImg(new File(temp));
        }
        class Box {
            int[][] hashCode;
            int index;

            void setHashCode(int[] hashCode) {
                this.hashCode[index++] = hashCode;
            }

            int[] getHashCode(int i) {
                return hashCode[i];
            }
        }
    }

    public static void startSearch(File file) {
        int averageSum = averageSum(file);
        if (averageSum == -1) {
            return;
        }
        getSimpleHash(averageSum);
        List<String> ans;                         //набор ответов
        int max = 4;                          //начнем с max = 4
        /*for (int i = 0; i < 4; i++){
       ans = com.ildar.web.main.HashBase.startSearch(max); // Возвращется обратно в этот класс для проверки дистанции Хеминга
       if (ans.size() != 1){
           return;
       }
   }     */
        do {
            max += 2;
            ans = HashBase.startSearch(max); // Возвращется обратно в этот класс для проверки дистанции Хеминга
        } while (!isException && max < 14 && ans.isEmpty());
        HashBase.end();
        if (isException) {
            System.err.println("There is a database's Exception");
            return;
        }
        if (ans.isEmpty()) {
            System.out.println("--------------------There is no same image )-;");
            return;
        }
        System.out.println("max = " + max);
        Iterator<String> iter = ans.iterator();
        String imagePath;
        while (iter.hasNext()) {
            imagePath = iter.next();
            System.out.println(imagePath);
            ShowImage.showImg(new File(imagePath));
        }
        /*   class Box {
           int[][] hashCode;
           int index;

           void setHashCode(int[] hashCode) {
               this.hashCode[index++] = hashCode;
           }

           int[] getHashCode(int i) {
               return hashCode[i];
           }
       } */
    }

    /*  public static void addImagesToBase() {
       File directory = new File("");
       File[] imageFile = directory.listFiles();
       com.ildar.web.main.HashBase.lastImgID();
       for (int i = 0; i < imageFile.length; i++) {
           imagesToSQL(imageFile[i]);
       }
       com.ildar.web.main.HashBase.end();
       com.ildar.web.main.HashBase.saveImgID();
   } */

    static void imagesToSQL(File file) {
        getSimpleHash(averageSum(file));
        HashBase.addValues(hashCode, file, null);
    }

}
