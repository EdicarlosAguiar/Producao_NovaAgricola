/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author Edicarlos
 */
public class FORMULARIO_NA_MAO {

    public static void components() {
        JMenuBar menu = new javax.swing.JMenuBar();
        JFrame painel = new javax.swing.JFrame();
        painel.setSize(1500, 1500);
        menu.setSize(20, 200);
        menu.setVisible(true);
        painel.add(menu);
        painel.setVisible(true);

    }

    public static void main(String args[]) {
       components(); //Inicia o formulario base
    }

}
