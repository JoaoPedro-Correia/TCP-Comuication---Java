/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidortcp;

import java.util.ArrayList;

/**
 *
 * @author correia
 */

public class Wisard {
    //BLEACHING
    private static final Integer B = 4;
    
    private static Ram[][] neural = new Ram[5][5];    
    private ArrayList<String> discriminator;
    //input
    private ArrayList<String> s;
    //quantity for input group
    private Integer sn;
    
    /*Sq+Sw -> RAM [0,Sq,Sw,Sq+Sw]*/
    //s -> valores possiveis de entrada -> sintomas
    public Wisard(ArrayList discriminador, ArrayList s) {
        this.discriminator = discriminador;
        this.s = s;
        
        this.sn = s.size()/2;
        
        //discriminator amount
        int nD = this.discriminator.size();
        
        for(int i=0; i<nD; i++){
            for(int j=0; j<sn; j++){
                neural[i][j] = new Ram(2);
            }
        }
    }//wisard
    
    public String training(boolean[] input, int iDiscriminator){
        //search the bit for input group
        String msg = "";
        Integer[] bitRam = searchBitRam(input);
        
        for(int i=0; i<this.sn; i++){
            neural[iDiscriminator][i].addCelRam(bitRam[i]);
            msg += neural[iDiscriminator][i]+"\n";
        }
        return msg;
    }//training
    
    public String evaluate(boolean[] input, int iDisc){
        String msg = "";
        Integer[] bitRam = searchBitRam(input);
        
        ArrayList<Integer> sumBleac = new ArrayList<>(this.sn);
        //inicialize
        for (int i = 0; i < this.sn; i++){
            sumBleac.add(0);
        }
        
        for(int i=0; i<this.discriminator.size(); i++){
            for(int j=0; j<this.sn; j++){
                int c = neural[i][j].getCelRam(bitRam[j]);
                //how much of the output is more than b
                if(c >= this.B){
                    sumBleac.set(i, sumBleac.get(i)+1);
                }
            }
        }
        
        int bigDisc = searchBiggerDiscriminator(sumBleac);
        
        /*ANSWERS*/
        //DRAW
        if(bigDisc == -1){
            msg += "ERRO - DRAW";
        }else{
            //CORRCT
            if(bigDisc == iDisc){
                msg += "ANSWER ACCEPT";
            //WRONG
            }else{
                msg += "ANSWER WRONG";
            }
        }
        return msg;
    }//evaluate
    
    public String applicaiton(boolean[] input){
        String msg = "";
        Integer[] bitRam = searchBitRam(input);
        
        ArrayList<Integer> sumBleac = new ArrayList<>(this.sn);
        //inicialize
        for (int i = 0; i < this.sn; i++){
            sumBleac.add(0);
        }
        
        for(int i=0; i<this.discriminator.size(); i++){
            for(int j=0; j<this.sn; j++){
                int c = neural[i][j].getCelRam(bitRam[j]);
                //how much of the output is more than b
                if(c >= this.B){sumBleac.set(i, sumBleac.get(i)+1);}
            }
        }
                
        int bigDisc = searchDiscriminator(sumBleac);
        
        msg += "DOENÇA\n";
        msg += this.discriminator.get(bigDisc);
        
        return msg;
    }//application
    
    public int moveBit(boolean n1, boolean n2){
        int x1 = n1 == true ? 1 : 0;
        int x2 = n2 == true ? 1 : 0;
        
        return (x1 << 1) | x2;
    }//moveBit
    
    /*
    s9+s4
    s0+s5
    s2+s8
    s6+s1
    s3+s7
    */
    public Integer[] searchBitRam(boolean[] input){
        Integer[] bitRam = new Integer[input.length/2];
        bitRam[0] = moveBit(input[9], input[4]);
        bitRam[1] = moveBit(input[0], input[5]);
        bitRam[2] = moveBit(input[2], input[8]);
        bitRam[3] = moveBit(input[6], input[1]);
        bitRam[4] = moveBit(input[3], input[7]);
        
        return bitRam;
    }//searchBitRam
    
    private Integer searchBiggerDiscriminator(ArrayList<Integer> list){
        int count = 0;
        int ibig = 0;
        int bigN = 0;
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > bigN){
                bigN = list.get(i);
                ibig = i;
                count = 1;
            }else if(list.get(i) == bigN){
                count++;
            }
        }
        
        //just one
        if(count==1){
            return ibig;
        //erro - draw
        }else{
            return -1;
        }
    }//searchbiggerdiscriminator

    private Integer searchDiscriminator(ArrayList<Integer> list){
        int ibig = 0;
        int bigN = 0;
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > bigN){
                bigN = list.get(i);
                ibig = i;
            }
        }
        return ibig;
    }

}