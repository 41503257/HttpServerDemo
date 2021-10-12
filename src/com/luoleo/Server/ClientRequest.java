package com.luoleo.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
/* 每个线程对http请求的处理以及响应内容
* 输入流使用BufferReader缓存请求信息，可进行判断处理
* 输出流通过PrintStream来发送响应。
* Content-Length的长度需要和响应体长度一样
* 设置大于响应体长度时，会一直等待且浏览器不加载，小于则只会接收所设定长度内容
* */
public class ClientRequest implements Runnable{
    private Socket socket;
    private PrintStream out;
    private BufferedReader input;
    public ClientRequest(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            SocketAddress address= socket.getRemoteSocketAddress();
            System.out.println("Handling client at"+address);
            this.input=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String request=this.input.readLine();
            System.out.println(request);
            this.out=new PrintStream(this.socket.getOutputStream());
            this.out.println("HTTP/1.1 200 OK");
            this.out.println("Content-Type: text/html;charset=UTF-8");
            this.out.println("Content-Length:38");
            this.out.println("Server:luoserver/1.1");
            this.out.println("Date:"+new Date());
            this.out.println();
            this.out.print("<h1>hello!</h1>");
            this.out.print("<h3>HTTP服务器!</h3>");
            this.out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.close();
        }
    }
    public void close(){
        try{
            if(this.out!=null){
                this.out.close();
                this.out=null;
            }
            if(this.input!=null){
                this.input.close();
                this.input=null;
            }
            if(this.socket!=null){
                this.socket.close();
                this.socket=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
