/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidortcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author correia
 */
public class DataBase extends Thread{
    private static final int MAX=100;
    
    private static WisardServer wisardServer = new WisardServer();
    
    private String request;
    private String answer;
    private byte[] data;
    private int byteRead;
    
    private Socket socket;
    private BufferedOutputStream buffOutputStream;
    private BufferedInputStream buffInputStream;


    public DataBase(Socket socket) {
        this.socket = socket;
    }

    public void run(){
        data = new byte[MAX];
        
        //create a stream for input and output
        try{
            buffOutputStream = new BufferedOutputStream(socket.getOutputStream());
            buffInputStream = new BufferedInputStream(socket.getInputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
        
        try{
            byteRead = buffInputStream.read(data, 0, data.length);
        }catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
        
        // request requested
        request = new String(data, 0, data.length);
        request = request.trim();
        
        String msg = wisardServer.operationRequest(request);
        
        data = msg.getBytes();
        
        try{
            buffOutputStream.write(data, 0, data.length);
            buffOutputStream.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
    }//run()

}
