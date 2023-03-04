/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Edicarlos
 */
public class ModelContaPagar {
    
    private int titulo;
    private int codCompra;
    private int codForn;
    private String fornecedor;
    private float valorTitulo;
    private String valor;
    private String vencimento;
    private String status;
    private String parcela;
    private String data_base;
    private String vencimento_base;
    private String natureza,titulo_origem,tipo_titulo;

    public String getTitulo_origem() {
        return titulo_origem;
    }

    public void setTitulo_origem(String titulo_origem) {
        this.titulo_origem = titulo_origem;
    }

    public String getTipo_titulo() {
        return tipo_titulo;
    }

    public void setTipo_titulo(String tipo_titulo) {
        this.tipo_titulo = tipo_titulo;
    }
   

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }
    

    public float getValorTitulo() {
        return valorTitulo;
    }

    public void setValorTitulo(float valorTitulo) {
        this.valorTitulo = valorTitulo;
    }

    public String getData_base() {
        return data_base;
    }

    public void setData_base(String data_base) {
        this.data_base = data_base;
    }

    public String getVencimento_base() {
        return vencimento_base;
    }

    public void setVencimento_base(String vencimento_base) {
        this.vencimento_base = vencimento_base;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public int getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(int codCompra) {
        this.codCompra = codCompra;
    }

    public int getCodForn() {
        return codForn;
    }

    public void setCodForn(int codForn) {
        this.codForn = codForn;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
