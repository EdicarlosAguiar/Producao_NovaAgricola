/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Administrador
 */
public class MyTime extends Observable implements Runnable {   
      
    private String horas;   
      
    public MyTime(Observer obs) {   
        this.addObserver(obs);  
    }   
    public void run() {   
        while(true){  
            this.horas = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());  
            this.notifyObservers();  
        }          
    }    
  
    public String getHoras() {  
        return horas;  
    }    
}  
