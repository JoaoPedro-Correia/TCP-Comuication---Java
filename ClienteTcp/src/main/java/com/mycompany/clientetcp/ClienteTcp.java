/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clientetcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author correia
 */
public class ClienteTcp {
     private String nomeDNS;
    private int serverPort;
    private byte[] meuIP;

    public ClienteTcp() {
        try {
            InetAddress endereco = InetAddress.getLocalHost();
            nomeDNS = endereco.getHostName();
            meuIP = endereco.getAddress();
        } catch (UnknownHostException e) {
            System.out.println("Host Desconhecido: " + e.getMessage());
        }
        serverPort = 6789;
    }

    public ClienteTcp(String nomeDNSservidor) {
        nomeDNS = nomeDNSservidor;
        meuIP = null;
        serverPort = 6789;
    }
    
    public String getNomeDNS() {
        return nomeDNS;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getMeuIP() {
        String s = new String(meuIP);
        return s;
    }
    
    public String enviarMensagem(String mensagem) throws UnknownHostException, IOException{
        byte[] dados = mensagem.getBytes();
        int bytesLidos;
        
        InetAddress enderecoServidor = null;
        Socket socket = null;
        BufferedOutputStream buffOutputStream;
        BufferedInputStream buffInputStream;
        
        // Obtem o endereço do servidor
        enderecoServidor = InetAddress.getByName(nomeDNS);
        
        // Cria socket para conexao
        socket = new Socket(enderecoServidor, this.serverPort);
        
        // Criar stream de entrada e saida
        buffOutputStream = new BufferedOutputStream(socket.getOutputStream());
        buffInputStream = new BufferedInputStream(socket.getInputStream());
        
        //envia solicitacao de srvico
        buffOutputStream.write(dados, 0, dados.length);
        buffOutputStream.flush();
        
        dados = new byte[1024];
        
        bytesLidos = buffInputStream.read(dados, 0, dados.length);
        
        socket.close();
        
        System.out.println("Resposta recebida: " + new String(dados, 0, bytesLidos));
        return new String(dados, 0, bytesLidos);
    }
}
