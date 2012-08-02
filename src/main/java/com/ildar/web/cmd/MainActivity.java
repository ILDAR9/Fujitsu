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
public class MainActivity {
    private static String path;

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
                    ImageWork.startSearch(new File(path));
                    break;
                case 2:
                    HashBase.clean();
                    break;
                case 3:
                    HashBase.correctBase();
                    break;
                case 4:
                    ShowImage.showImg(new File(path));
                    break;
                default:
                    System.out.println("====> I didn't get your idea.");
            }
            System.out.println("------------------------------------------------------------");
        }
    }

    private static int askAction() {
        String request = new Scanner(System.in).nextLine();
        Pattern pattern = Pattern.compile("\\s*[Ee]xit\\s*");
        Matcher matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return -1;
        }
        pattern = Pattern.compile("\\s*[Aa]dd\\s*[Ii]mages\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return 0;
        }
        pattern = Pattern.compile("\\s*[Ss]earch\\s*['\"]*\\s*([^'^\"]+)\\s*['\"]*\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            path = matcher.group(1);
            return 1;
        }
        pattern = Pattern.compile("\\s*[Rr]eset\\s*[Dd]atabase\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return 2;
        }
        pattern = Pattern.compile("\\s*[Aa]djust\\s*[Dd]atabase\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return 3;
        }
        pattern = Pattern.compile("\\s*[Ss]how\\s*['\"]*\\s*([^'^\"]+)\\s*['\"]*\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            path = matcher.group(1);
            return 4;
        }
        return 404;

    }

    public static void main(String[] args) {
        start();
    }
}
