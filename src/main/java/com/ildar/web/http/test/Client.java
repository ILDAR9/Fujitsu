package com.ildar.web.http.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 08.08.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    public static void main(String args[]) {
        String host = "localhost";
       int port = 3138;
        OutputStream out;

        try {
            Socket socket = new Socket(host, port);
            String temp;
            temp = socket.getInetAddress().getHostAddress()
                    + ":" + socket.getLocalPort();
            out = socket.getOutputStream();
            out.write(temp.getBytes());

            // read response
            byte buf[] = new byte[3 * 1024];
            int readedBytes = socket.getInputStream().read(buf);
            String data = new String(buf, 0, readedBytes);
            System.out.println(data);
            temp = "Test";
            out.write(temp.getBytes());

             buf = new byte[3 * 1024];
             readedBytes = socket.getInputStream().read(buf);
             data = new String(buf, 0, readedBytes);
            System.out.println(data);
        } // вывод исключений
        catch (UnknownHostException e) {
            System.out.println("The hostname is invalid or server isn't active.");
        } catch (IOException e) {
            System.out.println("The hostname is invalid.");
        }

    }
}
