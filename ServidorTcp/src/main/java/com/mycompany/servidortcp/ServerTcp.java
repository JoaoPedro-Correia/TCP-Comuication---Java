/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidortcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

  /**
 * @author correia
 */
public class ServerTcp {
    private static final int PORTA = 6789;
    private static Thread dataBase = null;
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        // cria ponto de transporte
        try{
            serverSocket = new ServerSocket(PORTA);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return;
        }
                
        while(true){
            // cria conexão
            try{
                socket = serverSocket.accept();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
                return;
            }
            dataBase = new DataBase(socket);

            //Crio a thread para o cliente
            dataBase.run();
        }
    }
}
