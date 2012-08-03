package com.ildar.web.cmd;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ildar.web.main.*;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 01.08.12
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends AbstractMainActivity{


   public static void start() {
        System.out.println("\t\t\tWellcome to the IldarGlasses v1.1! ^_^");
        while (true) {
            System.out.println("add images || search '...' || reset database || show '...' || adjust database || exit");
            System.out.print(">");
            switch (askAction()) {
                case -1:
                    System.out.println("GoodBye!!! ^_^");
                    return;
                case 0:
                    HashBase.addImagesToBase();
                    break;
                case 1:
                    ImageWork.startSearch(new File(path.toString()));
                    break;
                case 2:
                    HashBase.clean();
                    break;
                case 3:
                    ShowImage.showImg(new File(path.toString()));

                    break;
                case 4:
                    HashBase.correctBase();
                    break;
                default:
                    System.out.println("====> I didn't get your idea.");
            }
            System.out.println("------------------------------------------------------------");
        }
    }


    public static void main(String[] args) {
        start();
    }
}
