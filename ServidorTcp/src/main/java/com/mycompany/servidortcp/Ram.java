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
public class Ram {
    private Integer size;
    private Integer[] ram = null;

    public Ram(int bin) {
        this.size = (int)Math.pow(2, bin);
        this.ram = new Integer[this.size];
        for (int i = 0; i < ram.length; i++) {
            ram[i] = 0;
        }
    }

    public void addCelRam(int cel){
        ram[cel]++;
    }
    
    public Integer getCelRam(int cel){
        return this.ram[cel];
    }
    
    public Integer setCelRam(int cel, Integer v){
        return this.ram[cel] = v;
    }

    @Override
    public String toString() {
        String s = "[";
        for(Integer i : this.ram){
            s = s + "," + i;
        }
        s+="]";
        
        return s;
    }
    
    
}
