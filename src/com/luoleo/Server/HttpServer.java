package com.luoleo.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
* 利用Java socket实现http服务器
* 对每个客户端请求创建线程执行任务
* */
public class HttpServer {
    public static void main(String[] args)  {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(80,10);
            while(true){
                Socket socket=serverSocket.accept();
                Thread thread=new Thread(new ClientRequest(socket));
                thread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
