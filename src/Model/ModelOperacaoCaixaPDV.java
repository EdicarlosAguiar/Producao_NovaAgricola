/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Edicarlos
 */
public class ModelOperacaoCaixaPDV {

    private static int codigo;
    private static String data;
    private static String operador;
    private static double saldo_inical;
    private static double entrada;
    private static double saida;
    private static String status;
    private static String operacao;
    private static double saldo_final;
    private static String aprovador;
    private static String conta;

    public static String getConta() {
        return conta;
    }

    public static void setConta(String conta) {
        ModelOperacaoCaixaPDV.conta = conta;
    }
    

    public static String getAprovador() {
        return aprovador;
    }

    public static void setAprovador(String aprovador) {
        ModelOperacaoCaixaPDV.aprovador = aprovador;
    }
    

    public static double getSaldo_final() {
        return saldo_final;
    }

    public static void setSaldo_final(double saldo_final) {
        ModelOperacaoCaixaPDV.saldo_final = saldo_final;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public double getSaldo_inical() {
        return saldo_inical;
    }

    public void setSaldo_inical(double saldo_inical) {
        this.saldo_inical = saldo_inical;
    }

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

}
