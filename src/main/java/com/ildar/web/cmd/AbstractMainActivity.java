package com.ildar.web.cmd;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 03.08.12
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMainActivity {
    protected static StringBuilder path;

    protected static int askAction() {
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
            path = new StringBuilder(matcher.group(1));
            return 1;
        }
        pattern = Pattern.compile("\\s*[Rr]eset\\s*[Dd]atabase\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return 2;
        }
        pattern = Pattern.compile("\\s*[Ss]how\\s*['\"]*\\s*([^'^\"]+)\\s*['\"]*\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            path = new StringBuilder(matcher.group(1));
            return 3;
        }
        pattern = Pattern.compile("\\s*[Aa]djust\\s*[Dd]atabase\\s*");
        matcher = pattern.matcher(request);
        if (matcher.matches()) {
            return 4;
        }

        return 404;

    }
}
