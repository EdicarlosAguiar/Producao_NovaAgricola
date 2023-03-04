/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Edicarlos
 */
public class utilitario {
    
   private String tituloPrincipal = "AguiTech | DulceControl Comercio e Logistica v_1.2";

   private int cont_acesso;
   private int limite_acesso;
   private static String dataBase;
   private static String ultimoFechamento;
   private static int codCaixinha;

    public int getCodCaixinha() {
        return codCaixinha;
    }

    public void setCodCaixinha(int codCaixinha) {
        this.codCaixinha = codCaixinha;
    }
   

    public String getUltimoFechamento() {
        return ultimoFechamento;
    }

    public void setUltimoFechamento(String ultimoFechamento) {
        this.ultimoFechamento = ultimoFechamento;
    }
   

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
   

    public int getLimite_acesso() {
        return limite_acesso;
    }

    public void setLimite_acesso(int limite_acesso) {
        this.limite_acesso = limite_acesso;
    }

    public int getCont_acesso() {
        return cont_acesso;
    }

    public void setCont_acesso(int cont_acesso) {
        this.cont_acesso = cont_acesso;
    }


   
   
    public String getTituloPrincipal() {
        return tituloPrincipal;
    }


   
    private String mensageErro;
    private String dadosEmpresaRodape = "Direitos de uso reservados a: NOVA AGRICOLA - DIST. IRRIGADO TABULEIRO DE RUSSAS";

    public String getMensageErro() {
        return mensageErro;
    }

    public String getDadosEmpresaRodape() {
        return dadosEmpresaRodape;
    }

    public void setDadosEmpresaRodape(String dadosEmpresaRodape) {
        this.dadosEmpresaRodape = dadosEmpresaRodape;
    }

    public void setMensageErro(String mensageErro) {
        this.mensageErro = mensageErro;
    }
    
    public void inserirIcon(JFrame frame){
        
        try {
            
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/Imagens2/IconBrowser.png"));
        } catch (Exception e) {
        }
        
    }
    
   
    
}
