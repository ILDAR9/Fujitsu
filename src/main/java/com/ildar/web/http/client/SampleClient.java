package com.ildar.web.http.client;

/**
 * Created with IntelliJ IDEA.
 * User: NurgalievI2
 * Date: 03.08.12
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */

import com.ildar.web.cmd.AbstractMainActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

class SampleClient extends AbstractMainActivity {
    InputStream is;
    OutputStream out;
    static final String host = "localhost";
    static final int port = 3138;

    public SampleClient(Socket socket) throws IOException {
        String temp;
        temp = socket.getInetAddress().getHostAddress()
                + ":" + socket.getLocalPort();
        out = socket.getOutputStream();
        is = socket.getInputStream();
    }

    public void clientStart() throws IOException {
        byte buf[] = new byte[1024];
        int readedBytes = is.read(buf);
        String data = new String(buf, 0, readedBytes);
        System.out.println(data);
    }

    public void startTask() {
        System.out.println("\t\t\tWellcome to the IldarGlasses v1.1! ^_^");
        String request;
        try {
            while (true) {

                System.out.println("add images || search '...' || reset database || show '...' || adjust database || exit");
                System.out.print(">");

                int action = askAction();
                if (action < 0) {
                    System.out.println("GoodBye!!! ^_^");
                    out.write(-1);
                    break;
                } else if (action <= 4) {
                    if (action % 2 == 0) {
                        request = Integer.toString(action);
                        out.write(action);
                    } else {
                        request = path.insert(0, Integer.toString(action) + " ").toString();
                        out.write(request.getBytes());
                    }
                    System.out.println("Sended request: " + request);
                } else {
                    System.out.println("====> I didn't get your idea.");
                }
                System.out.println("------------------------------------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Can't to send answer to the server.");
        } catch (Exception e) {
            try {
                out.write(-1);
            } catch (IOException e1) {
                System.out.println("Close Socket command incorrect");
            }
            e.printStackTrace();
            return;
        }
    }


    public static void main(String args[]) {
        try {

            Socket socket = new Socket(host, port);
            SampleClient client = new SampleClient(socket);
            client.clientStart();
            client.startTask();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("The hostname is invalid or server isn't active.");
        } catch (IOException e) {
            System.out.println("The hostname is invalid.");
        }
    }
}
