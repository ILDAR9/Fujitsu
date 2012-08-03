package com.ildar.web.http.client;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 03.08.12
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ildar.web.cmd.AbstractMainActivity;

class SampleClient extends AbstractMainActivity {
    static OutputStream out;

    public static void start() {
        System.out.println("\t\t\tWellcome to the IldarGlasses v1.1! ^_^");
        while (true) {
            System.out.println("add images || search '...' || reset database || show '...' || adjust database || exit");
            System.out.print(">");
            try {
                int ans = askAction();
                if (ans < 0) {
                    System.out.println("GoodBye!!! ^_^");
                    out.write(-1);
                    return;
                } else if (ans >= 0 && ans <= 4) {
                    if (ans /2==0){
                    out.write(ans);
                    } else {
                        out.write(path.insert(0,ans).toString().getBytes());
                    }
                } else {
                    System.out.println("====> I didn't get your idea.");
                }
            } catch (IOException e) {
                System.out.println("Can't to send answer to the server.");
            }
            System.out.println("------------------------------------------------------------");
        }
    }

    public static void main(String args[]) {
        String host = "localhost";
        int port = 3138;
        try {
            Socket socket = new Socket(host, port);
            String temp;
            temp = socket.getInetAddress().getHostAddress()
                    + ":" + socket.getLocalPort();

            out = socket.getOutputStream();
            out.write(temp.getBytes());

            // read response
            byte buf[] = new byte[64 * 1024];
            int readedBytes = socket.getInputStream().read(buf);
            String data = new String(buf, 0, readedBytes);
            System.out.println(data);
            start();
        } // вывод исключений
        catch (UnknownHostException e) {
            System.out.println("The hostname is invalid or server isn't active.");
        } catch (IOException e) {
            System.out.println("The hostname is invalid.");
        }
    }
}
