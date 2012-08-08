package com.ildar.web.http.test;

import com.ildar.web.main.HashBase;
import com.ildar.web.main.ImageWork;
import com.ildar.web.main.ShowImage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 08.08.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class Server extends Thread {
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
                new Server(i, server.accept());
                i++;
            }
        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }

    public Server(int num, Socket socket) {
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

            byte buf[] = new byte[1024];
            int readedBytes = is.read(buf);

            String data = new String(buf, 0, readedBytes);
            String clientData = "client" + num + ": " + data;

            System.out.println(clientData);

            // add data about client's socket:
            data = "The connection established\nYou are " + clientData;

            // output client's data:
            os.write(12342);

            buf= new byte[1024];
             readedBytes = is.read(buf);

            data = new String(buf, 0, readedBytes);
            System.out.println(data);


            os.write("Request accepted".getBytes());

        } catch (Exception e) {
            System.out.println("init error: " + e);
        }
    }
}
