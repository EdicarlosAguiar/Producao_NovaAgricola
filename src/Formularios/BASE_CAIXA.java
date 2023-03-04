/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.SelecaoInvalida;
import FormNotificacao.exclusaoFinalizada;
import static Formularios.BASE_REQ1.requisicao;
import FormulariosConsultas.JD_SD_CAIXINHA;
import Model.ModelCompra;
import Model.ModelUsuario;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.factories.ElementFactory;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.State.NEW;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Footer;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class BASE_CAIXA extends javax.swing.JFrame {

    utilitario util = new utilitario();

    static String caixaSelecionado, statusMOv, nomeCaixa;
    int codigoCaixa;
    String dataAbertura, dataFechamento;
    double saldoFinal, saldoInicial;

    public static String getCaixaSelecionado() {
        return caixaSelecionado;
    }

    public static void setCaixaSelecionado(String caixaSelecionado) {
        BASE_CAIXA.caixaSelecionado = caixaSelecionado;
    }

    ModelUsuario user = new ModelUsuario();

    public BASE_CAIXA() {
        initComponents();
        this.setTitle(util.getTituloPrincipal());
        utilitario util = new utilitario();
        carregaTabela();
        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();
        util.inserirIcon(this);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
    }

    public void imprimirTermoFechamento() {
        buscaStatusdoMov();
        saldoFinal = 0;
        saldoInicial = 0;
        Document documentoPDF = new Document();
        OutputStream outPutStream = null;
        documentoPDF.setMargins(5, 5, 10, 10);//Esquerda,Direita,Top,Rodape

        //Fonte Padrao
        com.lowagie.text.Font fontPadrao = new com.lowagie.text.Font();
        fontPadrao.setSize(10);
        fontPadrao.setStyle(0);
        fontPadrao.setFamily("Arial");
        fontPadrao.setColor(Color.BLACK);

        //Fonte cabecalho tabela
        com.lowagie.text.Font fontTituloTabela = new com.lowagie.text.Font();
        fontTituloTabela.setSize(10);
        fontTituloTabela.setStyle(1);
        fontTituloTabela.setFamily("Arial");
        fontTituloTabela.setColor(Color.BLACK);

        //Fonte tirulos tabelas (Entradas e Saidas)
        com.lowagie.text.Font titulosTabelas = new com.lowagie.text.Font();
        titulosTabelas.setSize(12);
        titulosTabelas.setStyle(1);
        titulosTabelas.setFamily("Arial");
        titulosTabelas.setColor(Color.BLACK);

        // Fone
        com.lowagie.text.Font tituLoDocumento = new com.lowagie.text.Font();
        tituLoDocumento.setSize(12);
        tituLoDocumento.setStyle(1);
        tituLoDocumento.setColor(Color.black);

        try {
            //Local onde o arquivo será salvo
            PdfWriter.getInstance(documentoPDF, new FileOutputStream("C:\\print\\FechamentoCaixa.pdf"));
            Image logo = Image.getInstance("C:\\print\\logoNovaAgricola.png");

            logo.scaleToFit(96, 96);
            documentoPDF.open();
            documentoPDF.setPageSize(PageSize.A4);
            documentoPDF.addTitle("AguiTech - Fechamento de Caixa");

            //Cabeçalho do Formulario
            Paragraph tituloTabelaEntradas = new Paragraph(new Paragraph("     Entradas(+)", titulosTabelas));

            //Tabela Entradas________________________________________________________________
            PdfPTable entradas = new PdfPTable(5);

            entradas.setWidthPercentage(96.0f);
            entradas.setHorizontalAlignment(1);
            //Fonte cabecalho tabela
            com.lowagie.text.Font fontDadosTabelaProdutos = new com.lowagie.text.Font();
            fontDadosTabelaProdutos.setSize(6);
            fontDadosTabelaProdutos.setStyle(0);
            fontDadosTabelaProdutos.setColor(Color.BLACK);

            //Fonte para os titulos das colunas das tabelas
            com.lowagie.text.Font fontTitulosColunaTbProdutos = new com.lowagie.text.Font();
            fontTitulosColunaTbProdutos.setSize(9);
            fontTitulosColunaTbProdutos.setStyle(1);
            fontTitulosColunaTbProdutos.setColor(Color.BLACK);

            PdfPCell coluna1 = new PdfPCell(new Paragraph("Data", fontTituloTabela));
            PdfPCell coluna2 = new PdfPCell(new Paragraph("Natureza", fontTituloTabela));
            PdfPCell coluna3 = new PdfPCell(new Paragraph("Historico/Justificativa", fontTituloTabela));
            PdfPCell coluna4 = new PdfPCell(new Paragraph("Valor", fontTituloTabela));
            PdfPCell coluna5 = new PdfPCell(new Paragraph("Status", fontTituloTabela));

            //Formatação dos rotulos da tabela de produtos
            coluna1.setPadding(5);
            coluna2.setPadding(5);
            coluna3.setPadding(5);
            coluna4.setPadding(5);
            coluna5.setPadding(5);

            coluna1.setHorizontalAlignment(1);//Data
            coluna2.setHorizontalAlignment(1);//Natureza
            coluna3.setHorizontalAlignment(1);//Observação
            coluna4.setHorizontalAlignment(1); //Valor
            coluna5.setHorizontalAlignment(1);//Status
            //Fim da formatação dos rotulos
            coluna4.setHorizontalAlignment(1);
            coluna5.setHorizontalAlignment(1);
            // tabela.addCell(cabecalho);
            entradas.addCell(coluna1);
            entradas.addCell(coluna2);
            entradas.addCell(coluna3);
            entradas.addCell(coluna4);
            entradas.addCell(coluna5);
            //Fim da tabelas entradas_____________________________________________________________

            //Preenhcer a tabela de entradas
            try {

                Conexao conn = new Conexao();
                PreparedStatement pst;
                ResultSet rs;
                int linha = 1;
                pst = conn.getConexao().prepareStatement("select * from conta_finan "
                        + "where tipo_mov = 'ENTRADA' and codigo =?");
                pst.setInt(1, codigoCaixa);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String tipomov = rs.getString("natureza");
                    //Valores
                    double inicial = rs.getDouble("saldo_inicial");
                    double saque = rs.getDouble("saques");
                    double pagamento_pendente = rs.getDouble("pagamento_pendente");
                    double soma = inicial + saque + pagamento_pendente;
                    saldoFinal += soma;
                    DecimalFormat dfSdIni = new DecimalFormat("#,##0.00");
                    String valor = dfSdIni.format(soma);

                    String data = rs.getString("data");
                    String natureza = rs.getString("natureza");
                    String obs = rs.getString("observacao");
                    String aprovador = rs.getString("usuario_apr");
                    String data_apr = rs.getString("data_aprovacao");

                    String status = rs.getString("status_mov");
                    PdfPCell dataEntrada = new PdfPCell(new Paragraph(data, fontDadosTabelaProdutos));
                    PdfPCell naturezaEntrada = new PdfPCell(new Paragraph(natureza, fontDadosTabelaProdutos));
                    PdfPCell obsEntrada = new PdfPCell(new Paragraph(obs, fontDadosTabelaProdutos));
                    PdfPCell valorEntrada = new PdfPCell(new Paragraph("R$ " + valor, fontDadosTabelaProdutos));
                    PdfPCell statusEmtrada = new PdfPCell(new Paragraph(status + "\n" + aprovador + " | " + data_apr, fontDadosTabelaProdutos));

                    valorEntrada.setHorizontalAlignment(2);

                    dataEntrada.setPadding(5);
                    naturezaEntrada.setPadding(5);
                    valorEntrada.setPadding(5);
                    obsEntrada.setPadding(5);
                    statusEmtrada.setPadding(5);

                    entradas.addCell(dataEntrada);
                    entradas.addCell(naturezaEntrada);
                    entradas.addCell(obsEntrada);
                    entradas.addCell(valorEntrada);
                    entradas.addCell(statusEmtrada);
                    if (tipomov.equals("ABERTURA DE CAIXA")) {
                        saldoInicial = soma;
                    } else {

                    }
                }

                pst.close();
                rs.close();

                Paragraph tituloMovSaida = new Paragraph(new Paragraph("     Saidas(-)", titulosTabelas));

                //Tabela saidas
                PdfPTable saidas = new PdfPTable(5);
                saidas.setWidthPercentage(96.0f);
                saidas.setHorizontalAlignment(1);
                //Fonte cabecalho tabela

                //Formatação dos rotulos da tabela de produtos
                coluna1.setPadding(5);
                coluna2.setPadding(5);
                coluna3.setPadding(5);
                coluna4.setPadding(5);
                coluna5.setPadding(5);

                coluna1.setHorizontalAlignment(1);//Data
                coluna2.setHorizontalAlignment(1);//Natureza
                coluna3.setHorizontalAlignment(1);//Observação
                coluna4.setHorizontalAlignment(1); //Valor
                coluna5.setHorizontalAlignment(1);//Status
                //Fim da formatação dos rotulos
                // tabela.addCell(cabecalho);
                saidas.addCell(coluna1);
                saidas.addCell(coluna2);
                saidas.addCell(coluna3);
                saidas.addCell(coluna4);
                saidas.addCell(coluna5);
                //Fim da tabelas saidas

                try {
                    pst = conn.getConexao().prepareStatement("select * from conta_finan "
                            + "where tipo_mov = 'SAIDA' and codigo =?");
                    pst.setInt(1, codigoCaixa);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        //Valores
                        double inicial = rs.getDouble("saldo_inicial");
                        double saque = rs.getDouble("saques");
                        double pagamento_pendente = rs.getDouble("pagamento_pendente");
                        double soma = inicial + saque + pagamento_pendente;
                        DecimalFormat dfSdIni = new DecimalFormat("#,##0.00");
                        String valor = dfSdIni.format(soma);

                        saldoFinal -= soma;
                        String data = rs.getString("data");
                        String natureza = rs.getString("natureza");
                        String obs = rs.getString("observacao");
                        String aprovador = rs.getString("usuario_apr");
                        String data_apr = rs.getString("data_aprovacao");

                        String status = rs.getString("status_mov");
                        PdfPCell dataEntrada = new PdfPCell(new Paragraph(data, fontDadosTabelaProdutos));
                        PdfPCell naturezaEntrada = new PdfPCell(new Paragraph(natureza, fontDadosTabelaProdutos));
                        PdfPCell obsEntrada = new PdfPCell(new Paragraph(obs, fontDadosTabelaProdutos));
                        PdfPCell valorEntrada = new PdfPCell(new Paragraph("R$ " + valor, fontDadosTabelaProdutos));
                        PdfPCell statusEmtrada = new PdfPCell(new Paragraph(status + "\n" + aprovador + " | " + data_apr, fontDadosTabelaProdutos));
                        valorEntrada.setHorizontalAlignment(2);

                        dataEntrada.setPadding(5);
                        naturezaEntrada.setPadding(5);
                        valorEntrada.setPadding(5);
                        obsEntrada.setPadding(5);
                        statusEmtrada.setPadding(5);

                        saidas.addCell(dataEntrada);
                        saidas.addCell(naturezaEntrada);
                        saidas.addCell(obsEntrada);
                        saidas.addCell(valorEntrada);
                        saidas.addCell(statusEmtrada);

                    }

                    pst.close();
                    rs.close();

                    //Assinaturas
                    PdfPTable assinatura = new PdfPTable(2);
                    assinatura.setWidthPercentage(96.0f);
                    assinatura.setHorizontalAlignment(1);

                    PdfPCell respAlmox = new PdfPCell(new Paragraph("Resp. Caixinha:" + "\n" + "\n"
                            + "____________________________", fontPadrao));

                    PdfPCell solicitante = new PdfPCell(new Paragraph("Aprovador:" + "\n" + "\n"
                            + "____________________________", fontPadrao));

                    respAlmox.setBorderColor(Color.white);
                    solicitante.setBorderColor(Color.white);
                    respAlmox.setHorizontalAlignment(0);
                    solicitante.setHorizontalAlignment(2); //0 = esquerda, 1 = centro, 2 direita;
                    assinatura.addCell(respAlmox);
                    assinatura.addCell(solicitante);

                    PdfPTable cabeca = new PdfPTable(6);
                    PdfPTable divide = new PdfPTable(1);

                    PdfPCell divideTabs = new PdfPCell(new Paragraph("  ", fontDadosTabelaProdutos));
                    divideTabs.setBorderWidthBottom(1);
                    divideTabs.setBorderWidthLeft(0);
                    divideTabs.setBorderWidthRight(0);
                    divideTabs.setBorderWidthTop(0);
                    divide.addCell(divideTabs);

                    divide.setWidthPercentage(96.0f);
                    divide.setHorizontalAlignment(1);
                    cabeca.setWidthPercentage(96.0f);
                    cabeca.setHorizontalAlignment(1);

                    PdfPCell empresa = new PdfPCell(logo);
                    Paragraph titulo = new Paragraph("TERMO FECHAMENTO DE CAIXA");

                    //Formatar valores
                    DecimalFormat dfSdIni = new DecimalFormat("#,##0.00");
                    String saldoInicio = dfSdIni.format(saldoInicial);
                    String saldoFim = dfSdIni.format(saldoFinal);

                    PdfPCell Rotulo_codCaixa_SaldoIni = new PdfPCell(new Paragraph("Nº Caixa: " + "\n" + "\n" + "Saldo Inicial: ", fontTituloTabela));
                    PdfPCell Dados_codCaixa_SaldoIni = new PdfPCell(new Paragraph(codigoCaixa + "\n" + "\n" + String.valueOf("R$ " + saldoInicio), fontPadrao));
                    PdfPCell Rotulo_NomeCaixa_SaldoFim = new PdfPCell(new Paragraph("Nome: " + "\n" + "\n" + "Sd. Final: ", fontTituloTabela));
                    PdfPCell Dados_NomeCaixa_SaldoFim = new PdfPCell(new Paragraph(codigoCaixa + " - " + nomeCaixa + "\n" + "\n" + String.valueOf("R$ " + saldoFim), fontPadrao));
                    PdfPCell Rotulo_DataIni_DataFim = new PdfPCell(new Paragraph("Abertura: " + "\n" + "\n" + "Fechamento: ", fontTituloTabela));
                    PdfPCell Dados_DataIni_DataFim = new PdfPCell(new Paragraph(dataAbertura + "\n" + "\n" + dataFechamento, fontPadrao));

                    Rotulo_codCaixa_SaldoIni.setPadding(5);
                    Dados_codCaixa_SaldoIni.setPadding(5);
                    Rotulo_NomeCaixa_SaldoFim.setPadding(5);
                    Dados_NomeCaixa_SaldoFim.setPadding(5);
                    Rotulo_DataIni_DataFim.setPadding(5);
                    Dados_DataIni_DataFim.setPadding(5);

                    Rotulo_NomeCaixa_SaldoFim.setHorizontalAlignment(0);
                    Rotulo_DataIni_DataFim.setHorizontalAlignment(0);

                    logo.setAlignment(1);
                    titulo.setAlignment(1);
                    Paragraph brc = new Paragraph(" ");

                    //Define a largura das colunas da tabela de titulos
                    float[] configTabCab = new float[]{210f, 200f, 230f, 250f, 200f, 400f};
                    cabeca.setWidths(configTabCab);
                    Rotulo_codCaixa_SaldoIni.setBorder(0);
                    Dados_codCaixa_SaldoIni.setBorder(0);
                    Rotulo_NomeCaixa_SaldoFim.setBorder(0);
                    Dados_NomeCaixa_SaldoFim.setBorder(0);
                    Rotulo_DataIni_DataFim.setBorder(0);
                    Dados_DataIni_DataFim.setBorder(0);

                    //Largura tabela entradas
                    float[] tabelaDados = new float[]{100f, 230f, 500f, 120f, 220f};
                    entradas.setWidths(tabelaDados);
                    saidas.setWidths(tabelaDados);

                    cabeca.addCell(Rotulo_DataIni_DataFim);
                    cabeca.addCell(Dados_DataIni_DataFim);
                    cabeca.addCell(Rotulo_codCaixa_SaldoIni);
                    cabeca.addCell(Dados_codCaixa_SaldoIni);
                    cabeca.addCell(Rotulo_NomeCaixa_SaldoFim);
                    cabeca.addCell(Dados_NomeCaixa_SaldoFim);

                    //Inserindo os dados no documento
                    documentoPDF.add(logo);
                    documentoPDF.add(titulo);
                    documentoPDF.add(brc);
                    documentoPDF.add(brc);
                    documentoPDF.add(cabeca);
                    documentoPDF.add(divide);
                    documentoPDF.add(brc);
                    documentoPDF.add(brc);
                    documentoPDF.add(tituloTabelaEntradas);
                    documentoPDF.add(brc);
                    documentoPDF.add(entradas);
                    documentoPDF.add(brc);
                    documentoPDF.add(brc);
                    documentoPDF.add(tituloMovSaida);
                    documentoPDF.add(brc);
                    documentoPDF.add(saidas);

                    ArrayList totalItensEntrada = entradas.getRows();
                    ArrayList totalItensSaida = saidas.getRows();
                    int totalItens = totalItensEntrada.size() + totalItensSaida.size();

                    while (totalItens < 21) {
                        documentoPDF.add(brc);
                        totalItens++;
                    }

                    documentoPDF.add(assinatura);

                    //Abrir o PDF no padrão do arquivo.
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File("C:\\print\\FechamentoCaixa.pdf"));

                } catch (FileNotFoundException ex) {
                    JOptionPane.showConfirmDialog(this, "Documento não entradao " + ex);
                } catch (DocumentException ex) {
                    JOptionPane.showConfirmDialog(this, "Err " + ex);
                } catch (IOException ex) {
                    JOptionPane.showConfirmDialog(this, "Erro " + ex);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar dados" + e);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados" + e);
            }

        } catch (DocumentException ex) {
            Logger.getLogger(BASE_CAIXA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BASE_CAIXA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BASE_CAIXA.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            documentoPDF.close();
        }

    }

    public void mostraComponetes() {

        PainelTabelaTitulos.setVisible(true);

    }

    public void configInicias() {

        Cores cor = new Cores();
        tabela.setSelectionBackground(cor.getCorLinhaSelecionada());

        //  tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        btnAlterar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
        btnVisualizar.setBackground(Color.WHITE);
        btnVoltar.setBackground(Color.WHITE);

    }

    public void AlinharColunas() {
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        tabela.getColumnModel().getColumn(1).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(2).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(3).setCellRenderer(direita);

        int linha = 0;
        int indice = Integer.parseInt(tabela.getValueAt(linha, 19).toString());
        while (linha < 10) {
            if (linha == 0) {
                JOptionPane.showMessageDialog(rootPane, indice);

            } else {
                direita.setBackground(Color.green);
            }
            linha = linha + 1;
        }

    }

    public void CorLinhaTabela() {

        Cores cor = new Cores();

        tabela
                .setDefaultRenderer(Object.class,
                        new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int colum) {
                        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                        // 
                        Color c = cor.getCorLinhaImparTabela();
                        Color fonte;

                        int indice = Integer.parseInt(table.getValueAt(row, 0).toString());

                        //String tipoMov = table.getValueAt(row, 1).toString();
                        // if (tipoMov.equals("ENTRADA")) {
                        //  fonte = Color.BLUE;
                        // } else {
                        //     fonte = Color.RED;
                        //  }
                        if (indice % 2 != 0) {
                            c = c;

                            label.setBackground(c);
                            //   label.setForeground(fonte);

                            tabela.setShowGrid(true);
                            //  tabela.setRowMargin(10);

                            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                            DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                            direita.setHorizontalAlignment(SwingConstants.RIGHT);
                            esquerda.setHorizontalAlignment(SwingConstants.LEFT);

                      //      DefaultTableCellRenderer colunaValor = new DefaultTableCellRenderer();
                          //  colunaValor.setHorizontalAlignment(SwingConstants.RIGHT);

                            tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                            tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda);

                            direita.setBackground(c);
                            esquerda.setBackground(c);
                        //    colunaValor.setBackground(c);
                        //    colunaValor.setForeground(fonte);

                        } else {
                            c = cor.getCorLinhaParTabela();
                            label.setBackground(c);

                            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                            DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                            direita.setHorizontalAlignment(SwingConstants.RIGHT);
                            esquerda.setHorizontalAlignment(SwingConstants.LEFT);

                            DefaultTableCellRenderer colunaValor = new DefaultTableCellRenderer();
                            colunaValor.setHorizontalAlignment(SwingConstants.RIGHT);

                            tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                            tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);
                            tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda);

                            esquerda.setBackground(c);
                            direita.setBackground(c);
                         //   colunaValor.setBackground(c);
                           // colunaValor.setForeground(fonte);
                        }

                        // 
                        return label;
                    }

                }
                );

    }

    public void carregaTabela() {
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from conta_finan order by id desc");
            rs = pst.executeQuery();

            while (rs.next()) {

                //Valores
                double inicial = rs.getDouble("saldo_inicial");
                double saque = rs.getDouble("saques");
                double pagamento_pendente = rs.getDouble("pagamento_pendente");
                double soma = inicial + saque + pagamento_pendente;

                DecimalFormat df = new DecimalFormat("#,##0.00");
                String valor = df.format(soma);

                {
                    modelo.addRow(new Object[]{
                        tabela.getRowCount() + 1,
                        rs.getString("TIPO_MOV"),
                        rs.getString("data"),
                        rs.getInt("codigo"),
                        rs.getString("nome_caixa"),
                        rs.getString("natureza"),
                        rs.getString("observacao"),
                        "R$ " + valor + "  ",
                        rs.getString("status_mov"),
                        rs.getString("status_caixa"),
                        rs.getString("USUARIO_EMI"),
                        rs.getString("USUARIO_APR"),
                        rs.getInt("id"),}
                    );

                }
                linha = linha + 1;
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados" + e);
        }
        CorLinhaTabela();

    }

    public void pesquisa() {
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;

            if (cbTipoPesquisa.getSelectedItem().equals("Data")) {
                pst = conn.getConexao().prepareStatement("select * from conta_finan where data=? order by id desc");
                pst.setString(1, txtPesquisa.getText());
            } else {
                pst = conn.getConexao().prepareStatement("select * from conta_finan where codigo=? order by id desc");
                pst.setString(1, txtPesquisa.getText());
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                //Valores
                double inicial = rs.getDouble("saldo_inicial");
                double saque = rs.getDouble("saques");
                double pagamento_pendente = rs.getDouble("pagamento_pendente");
                double soma = inicial + saque + pagamento_pendente;

                DecimalFormat df = new DecimalFormat("#,##0.00");
                String valor = df.format(soma);

                {
                    modelo.addRow(new Object[]{
                        tabela.getRowCount() + 1,
                        rs.getString("TIPO_MOV"),
                        rs.getString("data"),
                        rs.getInt("codigo"),
                        rs.getString("nome_caixa"),
                        rs.getString("natureza"),
                        rs.getString("observacao"),
                        "R$ " + valor + "  ",
                        rs.getString("status_mov"),
                        rs.getString("status_caixa"),
                        rs.getString("USUARIO_EMI"),
                        rs.getString("USUARIO_APR"),
                        rs.getInt("id"),}
                    );

                }
                linha = linha + 1;
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        }
        CorLinhaTabela();

    }

    public void buscaStatusdoMov() {

        Conexao conn = new Conexao();
        Cores cor = new Cores();
        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select status_mov,nome_caixa,data,"
                    + "data_fechamento,natureza from conta_finan where codigo =?");
            pst.setInt(1, codigoCaixa);
            rs = pst.executeQuery();

            while (rs.next()) {
                String tipoMov = rs.getString("natureza");

                if (tipoMov.equals("ABERTURA DE CAIXA")) {
                    dataAbertura = rs.getString("data");
                } else {

                }

                statusMOv = rs.getString("status_mov");
                nomeCaixa = rs.getString("nome_caixa");
                dataFechamento = rs.getString("data_fechamento");

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        painelCorpo = new javax.swing.JPanel();
        painelPrincipal = new javax.swing.JPanel();
        PainelTabelaTitulos = new javax.swing.JPanel();
        cbTipoPesquisa = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblDatahora = new javax.swing.JLabel();
        lblDadosEmpresa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnEntrar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Categoria de Produtos");

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        PainelTabelaTitulos.setBackground(new java.awt.Color(255, 255, 255));

        cbTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data", "Caixa" }));
        cbTipoPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbTipoPesquisaMouseClicked(evt);
            }
        });
        cbTipoPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoPesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbTipoPesquisaKeyReleased(evt);
            }
        });

        txtPesquisa.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tipo Mov", "Data", "Cod Caixa", "Nome Caixa", "Natureza Op", "Observação/Historico", "Valor", "Status da Movimentação", "Status Caixa", "Emitido Por", "Aprovador", "Cod Mov"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(3);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(0).setMaxWidth(3);
            tabela.getColumnModel().getColumn(4).setMinWidth(200);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(4).setMaxWidth(200);
            tabela.getColumnModel().getColumn(5).setMinWidth(200);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(5).setMaxWidth(200);
            tabela.getColumnModel().getColumn(6).setMinWidth(300);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(300);
            tabela.getColumnModel().getColumn(6).setMaxWidth(300);
            tabela.getColumnModel().getColumn(12).setMinWidth(50);
            tabela.getColumnModel().getColumn(12).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(12).setMaxWidth(50);
        }

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(1018, 28));

        lblDatahora.setForeground(new java.awt.Color(38, 85, 100));
        lblDatahora.setText("Data e Hora:");

        lblDadosEmpresa.setForeground(new java.awt.Color(38, 85, 100));
        lblDadosEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDadosEmpresa.setText(" Edicarlos Aguiar de Sousa - ME - Todos os direitos reservados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDatahora, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatahora)
                    .addComponent(lblDadosEmpresa))
                .addContainerGap())
        );

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));
        btnEntrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Add.png"))); // NOI18N
        btnEntrar.setText("Incluir");
        btnEntrar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar.setOpaque(true);
        btnEntrar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrarFocusGained(evt);
            }
        });
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrarMouseReleased(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEntrar);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Altar2.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setAlignmentY(0.8F);
        btnAlterar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setEnabled(false);
        btnAlterar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnAlterar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setOpaque(true);
        btnAlterar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnAlterar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAlterarFocusGained(evt);
            }
        });
        btnAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlterarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlterarMouseExited(evt);
            }
        });
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAlterar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Delete.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setAlignmentY(0.8F);
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnExcluir.setMaximumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setMinimumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setOpaque(true);
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel2.add(btnExcluir);

        btnVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/print.png"))); // NOI18N
        btnVisualizar.setText("Termo Fec");
        btnVisualizar.setAlignmentY(0.8F);
        btnVisualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVisualizar.setContentAreaFilled(false);
        btnVisualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisualizar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVisualizar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnVisualizar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnVisualizar.setOpaque(true);
        btnVisualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseExited(evt);
            }
        });
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnVisualizar);

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Home.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setAlignmentY(0.0F);
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVoltar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnVoltar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnVoltar.setOpaque(true);
        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVoltarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVoltarMouseExited(evt);
            }
        });
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        jPanel2.add(btnVoltar);

        lblTitulo.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Financeiro - Caixinha Interno");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout PainelTabelaTitulosLayout = new javax.swing.GroupLayout(PainelTabelaTitulos);
        PainelTabelaTitulos.setLayout(PainelTabelaTitulosLayout);
        PainelTabelaTitulosLayout.setHorizontalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        PainelTabelaTitulosLayout.setVerticalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisa)
                            .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        ACAO_CAIXA acao = new ACAO_CAIXA(this, true);

        acao.setVisible(true);
        carregaTabela();
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnEntrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseReleased
        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));

    }//GEN-LAST:event_btnEntrarMouseReleased

    private void btnEntrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMousePressed
        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMousePressed

    private void btnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseExited
        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));

    }//GEN-LAST:event_btnEntrarMouseExited

    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMouseEntered

    private void btnEntrarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrarFocusGained
        carregaTabela();
    }//GEN-LAST:event_btnEntrarFocusGained

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnVisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseEntered
        btnVisualizar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVisualizarMouseEntered

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnVisualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseExited
        btnVisualizar.setBackground(Color.white);
    }//GEN-LAST:event_btnVisualizarMouseExited

    private void btnVoltarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseExited
        btnVoltar.setBackground(Color.white);
    }//GEN-LAST:event_btnVoltarMouseExited

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        ACAO_CAIXA ac = new ACAO_CAIXA(this, true);

        int linha = tabela.getSelectedRow();
        setCaixaSelecionado((String) tabela.getValueAt(linha, 12).toString());

        int codCaixa = Integer.parseInt(tabela.getValueAt(linha, 3).toString());
        codigoCaixa = codCaixa;
        ac.setCodCaixa(codCaixa);

        ac.setCodTitulo(Integer.parseInt(getCaixaSelecionado()));
        ac.setValor(tabela.getValueAt(linha, 7).toString());
        ac.setNatureza(tabela.getValueAt(linha, 5).toString());
        ac.setObservacao(tabela.getValueAt(linha, 6).toString());
        ac.setNomeCaixa(tabela.getValueAt(linha, 4).toString());
        statusMOv = tabela.getValueAt(linha, 8).toString();
        nomeCaixa = tabela.getValueAt(linha, 4).toString();

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        //buscaStatusdoMov();
        if (statusMOv.equals("APROVADO")) {
            JOptionPane.showMessageDialog(this, "Açao não permitida!" + "\n"
                    + "Não é permitido a exclusão de uma movimentação já aprovada!", "AGUITECH", 2);
        } else {

            if (caixaSelecionado == null) {
                SelecaoInvalida erro = new SelecaoInvalida(this, true);
                erro.setVisible(true);
            } else {
                try {
                    int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da movimentação: "
                            + caixaSelecionado + "?",
                            "AGUITECH", JOptionPane.YES_NO_OPTION);
                    if (resp1 == 0) {
                        Conexao conn = new Conexao();
                        PreparedStatement pst;
                        ResultSet rs;

                        pst = conn.getConexao().prepareStatement("delete from conta_finan where id=?");
                        pst.setInt(1, Integer.parseInt(caixaSelecionado));
                        pst.executeUpdate();
                        exclusaoFinalizada confirmacao = new exclusaoFinalizada(this, true);
                        confirmacao.setVisible(true);
                        pst.close();

                    } else {

                    }

                } catch (Exception e) {
                }

            }

        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        carregaTabela();        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        if (caixaSelecionado == null) {
            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);
        } else {
            imprimirTermoFechamento();
        }
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtPesquisa.getText().equals("")) {
            carregaTabela();
        } else {
            pesquisa();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAlterarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseExited
        btnAlterar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnAlterarMouseExited

    private void btnAlterarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseEntered
        btnAlterar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnAlterarMouseEntered

    private void btnAlterarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAlterarFocusGained
        carregaTabela();
    }//GEN-LAST:event_btnAlterarFocusGained

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased
        JD_SD_CAIXINHA sd = new JD_SD_CAIXINHA(this, true);
        utilitario util = new utilitario();

        int tecla = evt.getKeyCode();

        if (tecla == 115) {

            int linha = tabela.getSelectedRow();
            int codCaixa = Integer.parseInt(tabela.getValueAt(linha, 3).toString());
            //  JOptionPane.showMessageDialog(this, codCaixa);
            util.setCodCaixinha(codCaixa);
            sd.pegaCodCaixa(util);
            sd.buscaDados();
            sd.setVisible(true);

        } else {

        }
    }//GEN-LAST:event_tabelaKeyReleased

    private void cbTipoPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbTipoPesquisaMouseClicked
        tabela.setRowSelectionInterval(0, 0);
        tabela.clearSelection();// 
    }//GEN-LAST:event_cbTipoPesquisaMouseClicked

    private void cbTipoPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoPesquisaKeyPressed

        tabela.setRowSelectionInterval(0, 0);
        tabela.clearSelection();// TODO add your handling code here:
    }//GEN-LAST:event_cbTipoPesquisaKeyPressed

    private void cbTipoPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoPesquisaKeyReleased

        tabela.setRowSelectionInterval(0, 0);
        tabela.clearSelection();//  
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoPesquisaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BASE_CAIXA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASE_CAIXA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASE_CAIXA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASE_CAIXA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BASE_CAIXA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PainelTabelaTitulos;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox<String> cbTipoPesquisa;
    public javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tabela;
    public javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
