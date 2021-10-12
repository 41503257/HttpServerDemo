package com.luoleo.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) {

        try{
            for(int i=0;i<20;i++) {
              Thread t=new Thread(new ClientThread());
              t.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class ClientThread implements Runnable{

    @Override
    public void run() {
        String msg="Client Data";
        Socket socket = null;
        try {
            Thread.sleep(1000);
            socket = new Socket("127.0.0.1", 80);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                printWriter.println(Thread.currentThread()+msg);
                printWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
