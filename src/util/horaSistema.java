/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;




public class horaSistema implements ActionListener {
    
    private String dataBase;

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    String hora = dtf.format(LocalDateTime.now());

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Calendar now = Calendar.getInstance();
        this.dataBase = String.format("%1$th:%1$th:%1$th", now);
        
    
    }

}
