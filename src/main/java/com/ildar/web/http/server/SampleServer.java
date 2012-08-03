package com.ildar.web.http.server;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 03.08.12
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */

import com.ildar.web.main.HashBase;
import com.ildar.web.main.ImageWork;
import com.ildar.web.main.ShowImage;

import java.io.*;
import java.net.*;

class SampleServer extends Thread {
    Socket socket;
    int num;

    public static void main(String args[]) {
        try {
            int i = 0; // connection's counter

            String host = "localhost";
            int port = 3138;
            ServerSocket server = new ServerSocket(port, 0,
                    InetAddress.getByName(host));

            System.out.println("server is started");

            // listing port
            while (true) {
                // waiting new client connection
                new SampleServer(i, server.accept());
                i++;
            }
        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }

    public SampleServer(int num, Socket socket) {
        // copy data
        this.num = num;
        this.socket = socket;

        // run net Therad
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            byte buf[] = new byte[64 * 1024];
            int readedBytes = is.read(buf);

            String data = new String(buf, 0, readedBytes);
            System.out.println(data);

            // add data about client's socket:
            data = "The connection established\nYou are client" + num + ": " + data;

            // output client's data:
            os.write(data.getBytes());

           /* switch (askAction()) {
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
            }               */
            socket.close();
        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }
}