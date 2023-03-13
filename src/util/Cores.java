/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;

/**
 *
 * @author Edicarlos
 */
public class Cores {

    //Padrão de Cores Tabelas
    //private Color corLinhaImparTabela = new Color(223, 223, 248); COr Original
    private Color corLinhaImparTabela = new Color(250,250,250);
    private Color corLinhaParTabela = new Color(217,211,211);//[217,211,211]
    private Color corPreenchimentoCabecalho = new Color(255,255,255);//94,110,110)//[94,110,110]new Color(102, 204, 255);//[86,86,86]
    private Color corFonteCabecalho  = new Color(51,51,51);
    private Color corFonteDadosTabela = new Color(51,51,51);
    private Color corPreechimentoCabecalhoPDV = new Color(0,102,102);
    private Color corLinhaSelecionada = new Color(0,102,153);
    private Color corFonteLinhaSelecionada = new Color(255,255,255);
    private Color CorFundoLinhaDeletada = new Color(239,237,237);//[239,237,237]
    private Color CorFonteLinhaDeletada = new Color(240,240,240);//[240,240,240]
    private int sizeFonteHenderTable = 14;
    
    
    //Padrão de Cores Titulos e Fontes
    private Color corPreenchimentoTituloBase = Color.WHITE;//new Color(153, 255, 255);
    private Color corPreenchimentoTituloFormInputs = new Color(0, 102, 102);
    private Color corFonteTituloBase = Color.BLACK;
    private Color corFonteMenuBase = Color.BLACK;
    private Color corTotal = (new Color(0,102,102));
    private Color corDesconto = Color.red;
    private Color subTotal = (new Color(0,102,102));

    //Padrão de Cores Paineis
    private Color corPreenchimentoMenuBase = new Color(102, 204, 255);
    private Color corPainelCorpoForm = Color.WHITE;
    
    //Outros padroes
    private Color corCampoDesabilitado = new Color(240,240,240);
    private Color corFocuInput = new Color(255,255,153);
     private Color corFocuSair = new Color(255,255,255);

    public int getSizeFonteHenderTable() {
        return sizeFonteHenderTable;
    }

    public void setSizeFonteHenderTable(int sizeFonteHenderTable) {
        this.sizeFonteHenderTable = sizeFonteHenderTable;
    }
     

    public Color getCorFocuSair() {
        return corFocuSair;
    }

    public void setCorFocuSair(Color corFocuSair) {
        this.corFocuSair = corFocuSair;
    }

    public Color getCorFocuInput() {
        return corFocuInput;
    }

    public void setCorFocuInput(Color corFocuInput) {
        this.corFocuInput = corFocuInput;
    }

    
    
    public Color getCorFonteLinhaSelecionada() {
        return corFonteLinhaSelecionada;
    }

    public void setCorFonteLinhaSelecionada(Color corFonteLinhaSelecionada) {
        this.corFonteLinhaSelecionada = corFonteLinhaSelecionada;
    }

    
    
    public Color getCorLinhaSelecionada() {
        return corLinhaSelecionada;
    }

    public void setCorLinhaSelecionada(Color corLinhaSelecionada) {
        this.corLinhaSelecionada = corLinhaSelecionada;
    }

    
    
    public Color getCorPreechimentoCabecalhoPDV() {
        return corPreechimentoCabecalhoPDV;
    }

    public void setCorPreechimentoCabecalhoPDV(Color corPreechimentoCabecalhoPDV) {
        this.corPreechimentoCabecalhoPDV = corPreechimentoCabecalhoPDV;
    }

    
    public Color getCorCampoDesabilitado() {
        return corCampoDesabilitado;
    }

    public void setCorCampoDesabilitado(Color corCampoDesabilitado) {
        this.corCampoDesabilitado = corCampoDesabilitado;
    }


    public Color getCorPreenchimentoTituloFormInputs() {
        return corPreenchimentoTituloFormInputs;
    }

    public void setCorPreenchimentoTituloFormInputs(Color corPreenchimentoFormInputs) {
        this.corPreenchimentoTituloFormInputs = corPreenchimentoFormInputs;
    }

    public Color getCorFonteDadosTabela() {
        return corFonteDadosTabela;
    }

    public void setCorFonteDadosTabela(Color corFonteDadosTabela) {
        this.corFonteDadosTabela = corFonteDadosTabela;
    }

    public Color getCorTotal() {
        return corTotal;
    }

    public void setCorTotal(Color corTotal) {
        this.corTotal = corTotal;
    }

    public Color getCorDesconto() {
        return corDesconto;
    }

    public void setCorDesconto(Color corDesconto) {
        this.corDesconto = corDesconto;
    }

    public Color getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Color subTotal) {
        this.subTotal = subTotal;
    }

    
    public Color getCorPainelCorpoForm() {
        return corPainelCorpoForm;
    }

    public void setCorPainelCorpoForm(Color corPainelCorpoForm) {
        this.corPainelCorpoForm = corPainelCorpoForm;
    }
    
    //Padrao cores Tabeals de Inputs, Aterações e Exclusao
    private Color preenchimentoRodapeFormulario = new Color(240, 240, 240);
    private Color fontePradraoCamposEntrada = Color.BLACK;

    public Color getFontePradraoCamposEntrada() {
        return fontePradraoCamposEntrada;
    }

    public void setFontePradraoCamposEntrada(Color fontePradraoCamposEntrada) {
        this.fontePradraoCamposEntrada = fontePradraoCamposEntrada;
    }
    

    public Color getPreenchimentoRodapeFormulario() {
        return preenchimentoRodapeFormulario;
    }

    public void setPreenchimentoRodapeFormulario(Color preenchimentoRodapeFormulario) {
        this.preenchimentoRodapeFormulario = preenchimentoRodapeFormulario;
    }
    
    public Color getCorPreenchimentoCabecalho() {
        return corPreenchimentoCabecalho;
    }

    public void setCorPreenchimentoCabecalho(Color corPreenchimentoCabecalho) {
        this.corPreenchimentoCabecalho = corPreenchimentoCabecalho;
    }

    public Color getCorFonteCabecalho() {
        return corFonteCabecalho;
    }

    public void setCorFonteCabecalho(Color corFonteCabecalho) {
        this.corFonteCabecalho = corFonteCabecalho;
    }

    public Color getCorPreenchimentoTituloBase() {
        return corPreenchimentoTituloBase;
    }

    public void setCorPreenchimentoTituloBase(Color corPreenchimentoTituloBase) {
        this.corPreenchimentoTituloBase = corPreenchimentoTituloBase;
    }

    public Color getCorPreenchimentoMenuBase() {
        return corPreenchimentoMenuBase;
    }

    public void setCorPreenchimentoMenuBase(Color corPreenchimentoMenuBase) {
        this.corPreenchimentoMenuBase = corPreenchimentoMenuBase;
    }

    public Color getCorFonteTituloBase() {
        return corFonteTituloBase;
    }

    public void setCorFonteTituloBase(Color corFonteTituloBase) {
        this.corFonteTituloBase = corFonteTituloBase;
    }

    public Color getCorFonteMenuBase() {
        return corFonteMenuBase;
    }

    public void setCorFonteMenuBase(Color corFonteMenuBase) {
        this.corFonteMenuBase = corFonteMenuBase;
    }

    public Color getCorLinhaImparTabela() {
        return corLinhaImparTabela;
    }

    public void setCorLinhaImparTabela(Color corLinhaImparTabela) {
        this.corLinhaImparTabela = corLinhaImparTabela;
    }

    public Color getCorLinhaParTabela() {
        return corLinhaParTabela;
    }

    public void setCorLinhaParTabela(Color corLinhaParTabela) {
        this.corLinhaParTabela = corLinhaParTabela;
    }

    public Color getCorFundoLinhaDeletada() {
        return CorFundoLinhaDeletada;
    }

    public void setCorFundoLinhaDeletada(Color CorFundoLinhaDeletada) {
        this.CorFundoLinhaDeletada = CorFundoLinhaDeletada;
    }

    public Color getCorFonteLinhaDeletada() {
        return CorFonteLinhaDeletada;
    }

    public void setCorFonteLinhaDeletada(Color CorFonteLinhaDeletada) {
        this.CorFonteLinhaDeletada = CorFonteLinhaDeletada;
    }
    

}
