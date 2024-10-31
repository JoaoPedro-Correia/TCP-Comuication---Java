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
public class WisardServer{
    private ArrayList<String> symptoms;//{"s0","s1","s2","s3","s4","s5","s6","s7","s8","s9"};
    private ArrayList<String> illness; //{"d0", "d1", "d2", "d3", "d4"};
    private boolean[] symptomsB;
    private Wisard wisard;

    private static final int MAX_ILLNESS=5;
    private static final int MAX_SYMPTOMS=10;
    
    public WisardServer(){
        //initialize symptom
        this.symptoms = new ArrayList<>(MAX_SYMPTOMS);
        for (int i = 0; i < MAX_SYMPTOMS; i++){
            this.symptoms.add("s" + (i+1));
        }
        
        //initialize illness
        this.illness = new ArrayList<>(MAX_ILLNESS);
        for (int i = 0; i < MAX_ILLNESS; i++){ 
            this.illness.add("d" + (i+1));
        }
        
        wisard = new Wisard(illness, symptoms);
    }//constructor
        
    public String operationRequest(String msg){
        //
        System.out.println("Server request: "+msg);
        String[] m = msg.split(";");
                
        switch(m[0]){
            //training
            case "1":
                m[2]=removeFirstAndLast(m[2]);
                this.symptomsB = selectSymptoms(m[2]);
                return this.wisard.training(symptomsB, this.illness.indexOf(m[3]));
                
            //test
            case "2":
                m[2]=removeFirstAndLast(m[2]);
                this.symptomsB = selectSymptoms(m[2]);
                return this.wisard.evaluate(symptomsB, this.illness.indexOf(m[3]));
                
            //production
            case "3":
                m[2]=removeFirstAndLast(m[2]);
                this.symptomsB = selectSymptoms(m[2]);
                return this.wisard.applicaiton(symptomsB);
                
            //request illness
            case "4":
                return String.join(",", symptoms);                
            //request symptoms
            case "5":
                return String.join(",", illness);
        }
        return null;
    }//operationRequest
    
    //this method wait a String parameter with the symptons with ',' separeted -> 's5,s2,s3,s8'
    private boolean[] selectSymptoms(String symp){
        boolean[] symptomsBoolean = new boolean[this.symptoms.size()];
        
        String[] s = symp.split(",");
        
        //flip
        for(String m : s){
            int i = this.symptoms.indexOf(m);
            symptomsBoolean[i] = true;
        }
        
        return symptomsBoolean;
    }//selectSymptoms

    public static String removeFirstAndLast(String str) {
        if (str.length() < 2) {
            return ""; // return empty string if that is very short
        }
        str=str.replace(" ", "");
        return str.substring(1, str.length() - 1);
    }//removeFirstAndLast
}
