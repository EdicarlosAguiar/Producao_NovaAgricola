/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.JRadioButton;

/**
 *
 * @author edica
 */
public class JD_PARCELAMENTO_PDV extends javax.swing.JDialog {

    private float valorTaxa;
    private String parcelamento, valorVenda, plano;

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public JRadioButton getjRParcela11() {
        return jRParcela11;
    }

    public void setjRParcela11(JRadioButton jRParcela11) {
        this.jRParcela11 = jRParcela11;
    }

    public String getParcelamento() {
        return parcelamento;
    }

    public void setParcelamento(String parcelamento) {
        this.parcelamento = parcelamento;
    }

    public float getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(float valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public JD_PARCELAMENTO_PDV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        jRParcela1.setVisible(false);
        jRParcela2.setVisible(false);
        jRParcela3.setVisible(false);
        jRParcela4.setVisible(false);
        jRParcela5.setVisible(false);
        jRParcela6.setVisible(false);
        jRParcela7.setVisible(false);
        jRParcela8.setVisible(false);
        jRParcela9.setVisible(false);
        jRParcela10.setVisible(false);
        jRParcela11.setVisible(false);
        jRParcela12.setVisible(false);
        jRParcela13.setVisible(false);
        jRParcela14.setVisible(false);
        jRParcela15.setVisible(false);
        jRParcela16.setVisible(false);

    }

    public void pegaValor(PDV pdv) {
        txtValor.setText(pdv.getValorParaCalculoParcela());
        txtCodVenda.setText(String.valueOf(pdv.getCodVenda()));
    }

    public void preenchePArcelas() {

        jRParcela1.setVisible(true);
        jRParcela2.setVisible(true);
        jRParcela3.setVisible(true);
        jRParcela4.setVisible(true);
        jRParcela5.setVisible(true);
        jRParcela6.setVisible(true);
        jRParcela7.setVisible(true);
        jRParcela8.setVisible(true);
        jRParcela9.setVisible(true);
        jRParcela10.setVisible(true);
        jRParcela11.setVisible(true);
        jRParcela12.setVisible(true);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");

        float parcela01 = valor / 1;
        float parcela02 = valor / 2;
        float parcela03 = valor / 3;
        float parcela04 = valor / 4;
        float parcela05 = valor / 5;
        float parcela06 = valor / 6;
        float parcela07 = valor / 7;
        float parcela08 = valor / 8;
        float parcela09 = valor / 9;
        float parcela10 = valor / 10;
        float parcela11 = valor / 11;
        float parcela12 = valor / 12;
        float parcela13 = valor / 13;
        float parcela14 = valor / 14;
        float parcela15 = valor / 15;
        float parcela16 = valor / 16;

        //Formatando tudo
        String valoTaxa01 = df.format(parcela01);
        String valoTaxa02 = df.format(parcela02);
        String valoTaxa03 = df.format(parcela03);
        String valoTaxa04 = df.format(parcela04);
        String valoTaxa05 = df.format(parcela05);
        String valoTaxa06 = df.format(parcela06);
        String valoTaxa07 = df.format(parcela07);
        String valoTaxa08 = df.format(parcela08);
        String valoTaxa09 = df.format(parcela09);
        String valoTaxa10 = df.format(parcela10);
        String valoTaxa11 = df.format(parcela11);
        String valoTaxa12 = df.format(parcela12);
        String valoTaxa13 = df.format(parcela13);
        String valoTaxa14 = df.format(parcela14);
        String valoTaxa15 = df.format(parcela15);
        String valoTaxa16 = df.format(parcela16);

        jRParcela1.setText("01x de " + valoTaxa01 + " taxa " + "2,95%");
        jRParcela2.setText("02x de " + valoTaxa02 + " taxa " + "4,63%");
        jRParcela3.setText("03x de " + valoTaxa03 + " taxa " + "5,45%");
        jRParcela4.setText("04x de " + valoTaxa04 + " taxa " + "6,26%");
        jRParcela5.setText("05x de " + valoTaxa05 + " taxa " + "7,08%");
        jRParcela6.setText("06x de " + valoTaxa06 + " taxa " + "7,90%");
        jRParcela7.setText("07x de " + valoTaxa07 + " taxa " + "8,85%");
        jRParcela8.setText("08x de " + valoTaxa08 + " taxa " + "9,66%");
        jRParcela9.setText("09x de " + valoTaxa09 + " taxa " + "10,48%");
        jRParcela10.setText("10x de " + valoTaxa10 + " taxa " + "11,29%");
        jRParcela11.setText("11x de " + valoTaxa11 + " taxa " + "12,11%");
        jRParcela12.setText("12x de " + valoTaxa12 + " taxa " + "12,92%");
        jRParcela13.setText("13x de " + valoTaxa13 + " taxa " + "14,52%");
        jRParcela14.setText("14x de " + valoTaxa14 + " taxa " + "15,32%");
        jRParcela15.setText("15x de " + valoTaxa15 + " taxa " + "16,13%");
        jRParcela16.setText("16x de " + valoTaxa16 + " taxa " + "16,94%");

        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);
        lblPercTaxa.setText("0,00%");
        lblValorTaxa.setText("0,00");
    }

    public void preencheParcelasPlano02() {
        jRParcela1.setVisible(true);
        jRParcela2.setVisible(true);
        jRParcela3.setVisible(true);
        jRParcela4.setVisible(true);
        jRParcela5.setVisible(true);
        jRParcela6.setVisible(true);
        jRParcela7.setVisible(true);
        jRParcela8.setVisible(true);
        jRParcela9.setVisible(true);
        jRParcela10.setVisible(true);
        jRParcela11.setVisible(true);
        jRParcela12.setVisible(true);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");

        float parcela01 = valor / 1;
        float parcela02 = valor / 2;
        float parcela03 = valor / 3;
        float parcela04 = valor / 4;
        float parcela05 = valor / 5;
        float parcela06 = valor / 6;
        float parcela07 = valor / 7;
        float parcela08 = valor / 8;
        float parcela09 = valor / 9;
        float parcela10 = valor / 10;
        float parcela11 = valor / 11;
        float parcela12 = valor / 12;
        float parcela13 = valor / 13;
        float parcela14 = valor / 14;
        float parcela15 = valor / 15;
        float parcela16 = valor / 16;

        //Formatando tudo
        String valoTaxa01 = df.format(parcela01);
        String valoTaxa02 = df.format(parcela02);
        String valoTaxa03 = df.format(parcela03);
        String valoTaxa04 = df.format(parcela04);
        String valoTaxa05 = df.format(parcela05);
        String valoTaxa06 = df.format(parcela06);
        String valoTaxa07 = df.format(parcela07);
        String valoTaxa08 = df.format(parcela08);
        String valoTaxa09 = df.format(parcela09);
        String valoTaxa10 = df.format(parcela10);
        String valoTaxa11 = df.format(parcela11);
        String valoTaxa12 = df.format(parcela12);
        String valoTaxa13 = df.format(parcela13);
        String valoTaxa14 = df.format(parcela14);
        String valoTaxa15 = df.format(parcela15);
        String valoTaxa16 = df.format(parcela16);

        jRParcela1.setText("01x de " + valoTaxa01 + " taxa " + "3,69%");
        jRParcela2.setText("02x de " + valoTaxa02 + " taxa " + "5,99%");
        jRParcela3.setText("03x de " + valoTaxa03 + " taxa " + "6,29%");
        jRParcela4.setText("04x de " + valoTaxa04 + " taxa " + "7,15%");
        jRParcela5.setText("05x de " + valoTaxa05 + " taxa " + "7,99%");
        jRParcela6.setText("06x de " + valoTaxa06 + " taxa " + "8,79%");
        jRParcela7.setText("07x de " + valoTaxa07 + " taxa " + "9,59%");
        jRParcela8.setText("08x de " + valoTaxa08 + " taxa " + "10,39%");
        jRParcela9.setText("09x de " + valoTaxa09 + " taxa " + "11,19%");
        jRParcela10.setText("10x de " + valoTaxa10 + " taxa " + "11,99%");
        jRParcela11.setText("11x de " + valoTaxa11 + " taxa " + "12,79%");
        jRParcela12.setText("12x de " + valoTaxa12 + " taxa " + "13,49%");
        jRParcela13.setVisible(false);
        jRParcela14.setVisible(false);
        jRParcela15.setVisible(false);
        jRParcela16.setVisible(false);

        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);
        lblPercTaxa.setText("0,00%");
        lblValorTaxa.setText("0,00");

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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodVenda = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        lblValorTaxa = new javax.swing.JLabel();
        lblPercTaxa = new javax.swing.JLabel();
        painelParcelas = new javax.swing.JPanel();
        jRParcela1 = new javax.swing.JRadioButton();
        jRParcela2 = new javax.swing.JRadioButton();
        jRParcela3 = new javax.swing.JRadioButton();
        jRParcela4 = new javax.swing.JRadioButton();
        jRParcela5 = new javax.swing.JRadioButton();
        jRParcela6 = new javax.swing.JRadioButton();
        jRParcela7 = new javax.swing.JRadioButton();
        jRParcela8 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jRParcela9 = new javax.swing.JRadioButton();
        jRParcela10 = new javax.swing.JRadioButton();
        jRParcela11 = new javax.swing.JRadioButton();
        jRParcela12 = new javax.swing.JRadioButton();
        jRParcela13 = new javax.swing.JRadioButton();
        jRParcela14 = new javax.swing.JRadioButton();
        jRParcela15 = new javax.swing.JRadioButton();
        jRParcela16 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jrPlano01 = new javax.swing.JRadioButton();
        jrPlano02 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Venda:");

        jPanel2.setBackground(new java.awt.Color(94, 110, 110));

        lblTitulo.setBackground(new java.awt.Color(94, 110, 110));
        lblTitulo.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Parcelamento de Venda no Cartão");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("x");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("x");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(221, 221, 221)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(281, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Valor da Venda:");

        txtCodVenda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtCodVenda.setForeground(new java.awt.Color(51, 51, 51));
        txtCodVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodVenda.setEnabled(false);

        txtValor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtValor.setForeground(new java.awt.Color(51, 51, 51));
        txtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValor.setText("1.000,00");
        txtValor.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Valor da Taxa:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Taxa:");

        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(51, 51, 51));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Ok");
        btnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnConfirmar.setOpaque(true);
        btnConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnConfirmarFocusGained(evt);
            }
        });
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnConfirmarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseReleased(evt);
            }
        });
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        lblValorTaxa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblValorTaxa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTaxa.setText("0,00");
        lblValorTaxa.setOpaque(true);

        lblPercTaxa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblPercTaxa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPercTaxa.setText("0,00");
        lblPercTaxa.setOpaque(true);

        painelParcelas.setBackground(new java.awt.Color(255, 255, 255));
        painelParcelas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        painelParcelas.setMaximumSize(new java.awt.Dimension(252, 208));
        painelParcelas.setMinimumSize(new java.awt.Dimension(252, 208));
        painelParcelas.setPreferredSize(new java.awt.Dimension(252, 208));

        jRParcela1.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela1.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela1.setText("Parca01");
        jRParcela1.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela1.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela1.setOpaque(true);
        jRParcela1.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela1MouseClicked(evt);
            }
        });
        jRParcela1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela1ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela1);

        jRParcela2.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela2.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela2.setText("Parca02");
        jRParcela2.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela2.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela2.setOpaque(true);
        jRParcela2.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela2MouseClicked(evt);
            }
        });
        jRParcela2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela2ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela2);

        jRParcela3.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela3.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela3.setText("Parca03");
        jRParcela3.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela3.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela3.setOpaque(true);
        jRParcela3.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela3MouseClicked(evt);
            }
        });
        jRParcela3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela3ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela3);

        jRParcela4.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela4.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela4.setText("Parca04");
        jRParcela4.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela4.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela4.setOpaque(true);
        jRParcela4.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela4MouseClicked(evt);
            }
        });
        jRParcela4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela4ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela4);

        jRParcela5.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela5.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela5.setText("Parca05");
        jRParcela5.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela5.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela5.setOpaque(true);
        jRParcela5.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela5MouseClicked(evt);
            }
        });
        jRParcela5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela5ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela5);

        jRParcela6.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela6.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela6.setText("Parca06");
        jRParcela6.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela6.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela6.setOpaque(true);
        jRParcela6.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela6MouseClicked(evt);
            }
        });
        jRParcela6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela6ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela6);

        jRParcela7.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela7.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela7.setText("Parca07");
        jRParcela7.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela7.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela7.setOpaque(true);
        jRParcela7.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela7MouseClicked(evt);
            }
        });
        jRParcela7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela7ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela7);

        jRParcela8.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela8.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela8.setText("Parca08");
        jRParcela8.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela8.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela8.setOpaque(true);
        jRParcela8.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela8MouseClicked(evt);
            }
        });
        jRParcela8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela8ActionPerformed(evt);
            }
        });
        painelParcelas.add(jRParcela8);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(252, 208));
        jPanel3.setMinimumSize(new java.awt.Dimension(252, 208));
        jPanel3.setPreferredSize(new java.awt.Dimension(252, 208));

        jRParcela9.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela9.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela9.setText("Parca09");
        jRParcela9.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela9.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela9.setOpaque(true);
        jRParcela9.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela9MouseClicked(evt);
            }
        });
        jRParcela9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela9ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela9);

        jRParcela10.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela10.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela10.setText("Parca10");
        jRParcela10.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela10.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela10.setOpaque(true);
        jRParcela10.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela10MouseClicked(evt);
            }
        });
        jRParcela10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela10ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela10);

        jRParcela11.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela11.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela11.setText("Parca11");
        jRParcela11.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela11.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela11.setOpaque(true);
        jRParcela11.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela11MouseClicked(evt);
            }
        });
        jRParcela11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela11ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela11);

        jRParcela12.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela12.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela12.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela12.setText("Parca12");
        jRParcela12.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela12.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela12.setOpaque(true);
        jRParcela12.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela12MouseClicked(evt);
            }
        });
        jRParcela12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela12ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela12);

        jRParcela13.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela13.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela13.setText("Parca13");
        jRParcela13.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela13.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela13.setOpaque(true);
        jRParcela13.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela13MouseClicked(evt);
            }
        });
        jRParcela13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela13ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela13);

        jRParcela14.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela14.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela14.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela14.setText("Parca14");
        jRParcela14.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela14.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela14.setOpaque(true);
        jRParcela14.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela14MouseClicked(evt);
            }
        });
        jRParcela14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela14ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela14);

        jRParcela15.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela15.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela15.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela15.setText("Parca15");
        jRParcela15.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela15.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela15.setOpaque(true);
        jRParcela15.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela15MouseClicked(evt);
            }
        });
        jRParcela15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela15ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela15);

        jRParcela16.setBackground(new java.awt.Color(255, 255, 255));
        jRParcela16.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jRParcela16.setForeground(new java.awt.Color(0, 51, 153));
        jRParcela16.setText("Parca16");
        jRParcela16.setMaximumSize(new java.awt.Dimension(210, 23));
        jRParcela16.setMinimumSize(new java.awt.Dimension(210, 23));
        jRParcela16.setOpaque(true);
        jRParcela16.setPreferredSize(new java.awt.Dimension(210, 23));
        jRParcela16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRParcela16MouseClicked(evt);
            }
        });
        jRParcela16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRParcela16ActionPerformed(evt);
            }
        });
        jPanel3.add(jRParcela16);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Selecione o plano"));

        jrPlano01.setText("Mercado pago");
        jrPlano01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrPlano01MouseClicked(evt);
            }
        });

        jrPlano02.setText("Giga Ton");
        jrPlano02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrPlano02MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jrPlano01)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrPlano02)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrPlano01)
                    .addComponent(jrPlano02))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodVenda)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValor)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblValorTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPercTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(painelParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPercTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblValorTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRParcela1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela1ActionPerformed

    private void jRParcela2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela2ActionPerformed

    private void jRParcela3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela3ActionPerformed

    private void jRParcela5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela5ActionPerformed

    private void jRParcela4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela4ActionPerformed

    private void jRParcela7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela7ActionPerformed

    private void jRParcela6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela6ActionPerformed

    private void jRParcela8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela8ActionPerformed

    private void jRParcela9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela9ActionPerformed

    private void jRParcela10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela10ActionPerformed

    private void jRParcela11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela11ActionPerformed

    private void jRParcela12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela12ActionPerformed

    private void jRParcela13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela13ActionPerformed

    private void jRParcela14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela14ActionPerformed

    private void jRParcela15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela15ActionPerformed

    private void jRParcela16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRParcela16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRParcela16ActionPerformed

    private void btnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusGained
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarFocusGained

    private void btnConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseEntered
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMouseEntered

    private void btnConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseExited
        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarMouseExited

    private void btnConfirmarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMousePressed

        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMousePressed

    private void btnConfirmarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseReleased
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMouseReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        this.dispose();

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void jRParcela1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela1MouseClicked

        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0295);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("2,95%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela1.getText());

        } else {
            float taxa = (float) (valor * 0.0369);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("3,69%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela1.getText());
        }
    }//GEN-LAST:event_jRParcela1MouseClicked

    private void jRParcela2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela2MouseClicked
        jRParcela1.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {

            float taxa = (float) (valor * 0.0463);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("4,63%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela2.getText());

        } else {
            float taxa = (float) (valor * 0.0599);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("5,99%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela2.getText());

        }


    }//GEN-LAST:event_jRParcela2MouseClicked

    private void jRParcela3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela3MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0545);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("5,45%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela3.getText());
        } else {

            float taxa = (float) (valor * 0.0629);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("6,29%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela3.getText());

        }

    }//GEN-LAST:event_jRParcela3MouseClicked

    private void jRParcela4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela4MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0624);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("6,26%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela4.getText());
        } else {
            float taxa = (float) (valor * 0.0715);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("7,15%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela4.getText());

        }

    }//GEN-LAST:event_jRParcela4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jRParcela5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela5MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0708);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("7,08%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela5.getText());
        } else {
            float taxa = (float) (valor * 0.0799);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("7,99%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela5.getText());

        }

    }//GEN-LAST:event_jRParcela5MouseClicked

    private void jRParcela6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela6MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0790);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("7,90%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela6.getText());
        } else {
            float taxa = (float) (valor * 0.0879);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("8,79%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela6.getText());

        }
    }//GEN-LAST:event_jRParcela6MouseClicked

    private void jRParcela7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela7MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0885);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("8,85%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela7.getText());
        } else {
            float taxa = (float) (valor * 0.0959);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("9,59%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela7.getText());

        }

    }//GEN-LAST:event_jRParcela7MouseClicked

    private void jRParcela8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela8MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.0966);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("9,66%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela8.getText());
        } else {
            float taxa = (float) (valor * 0.1039);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("10,39%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela8.getText());
        }
    }//GEN-LAST:event_jRParcela8MouseClicked

    private void jRParcela9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela9MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.1048);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("10,48%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela9.getText());
        } else {
            float taxa = (float) (valor * 0.1119);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("11,19%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela9.getText());
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jRParcela9MouseClicked

    private void jRParcela10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela10MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.1129);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("11,29%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela10.getText());
        } else {
            float taxa = (float) (valor * 0.1199);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("11,99%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela10.getText());
        }
    }//GEN-LAST:event_jRParcela10MouseClicked

    private void jRParcela11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela11MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));

        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.1211);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("12,11%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela11.getText());
        } else {
            float taxa = (float) (valor * 0.1279);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("12,79%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela11.getText());
        }
    }//GEN-LAST:event_jRParcela11MouseClicked

    private void jRParcela12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela12MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        if (jrPlano01.isSelected()) {
            float taxa = (float) (valor * 0.1292);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("12,92%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela12.getText());
        } else {
            float taxa = (float) (valor * 0.1349);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String valoTaxa = df.format(taxa);
            lblValorTaxa.setText("R$ " + valoTaxa);
            lblPercTaxa.setText("13,49%");
            this.setValorTaxa(taxa);
            this.setParcelamento(jRParcela12.getText());
        }
    }//GEN-LAST:event_jRParcela12MouseClicked

    private void jRParcela13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela13MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        float taxa = (float) (valor * 0.1452);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valoTaxa = df.format(taxa);

        lblValorTaxa.setText("R$ " + valoTaxa);
        lblPercTaxa.setText("14,52%");

        this.setValorTaxa(taxa);
        this.setParcelamento(jRParcela13.getText());
// TODO add your handling code here:
    }//GEN-LAST:event_jRParcela13MouseClicked

    private void jRParcela14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela14MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela15.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        float taxa = (float) (valor * 0.1532);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valoTaxa = df.format(taxa);

        lblValorTaxa.setText("R$ " + valoTaxa);
        lblPercTaxa.setText("15,32%");

        this.setValorTaxa(taxa);
        this.setParcelamento(jRParcela14.getText());
// TODO add your handling code here:
    }//GEN-LAST:event_jRParcela14MouseClicked

    private void jRParcela15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela15MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela16.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        float taxa = (float) (valor * 0.1613);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valoTaxa = df.format(taxa);

        lblValorTaxa.setText("R$ " + valoTaxa);
        lblPercTaxa.setText("16,13%");
        this.setValorTaxa(taxa);
        this.setParcelamento(jRParcela15.getText());

// TODO add your handling code here:
    }//GEN-LAST:event_jRParcela15MouseClicked

    private void jRParcela16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRParcela16MouseClicked
        jRParcela1.setSelected(false);
        jRParcela2.setSelected(false);
        jRParcela3.setSelected(false);
        jRParcela4.setSelected(false);
        jRParcela5.setSelected(false);
        jRParcela6.setSelected(false);
        jRParcela7.setSelected(false);
        jRParcela8.setSelected(false);
        jRParcela9.setSelected(false);
        jRParcela10.setSelected(false);
        jRParcela11.setSelected(false);
        jRParcela12.setSelected(false);
        jRParcela13.setSelected(false);
        jRParcela14.setSelected(false);
        jRParcela15.setSelected(false);

        float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
        float taxa = (float) (valor * 0.1694);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valoTaxa = df.format(taxa);

        lblValorTaxa.setText("R$ " + valoTaxa);
        lblPercTaxa.setText("16,94%");

        this.setValorTaxa(taxa);
        this.setParcelamento(jRParcela16.getText());
// TODO add your handling code here:
    }//GEN-LAST:event_jRParcela16MouseClicked

    private void jrPlano02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrPlano02MouseClicked
        jrPlano01.setSelected(false);
        preencheParcelasPlano02();
        this.setPlano(jrPlano02.getText());

    }//GEN-LAST:event_jrPlano02MouseClicked

    private void jrPlano01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrPlano01MouseClicked
        jrPlano02.setSelected(false);
        preenchePArcelas();
        this.setPlano(jrPlano01.getText());// TODO add your handling code here:
    }//GEN-LAST:event_jrPlano01MouseClicked

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
            java.util.logging.Logger.getLogger(JD_PARCELAMENTO_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_PARCELAMENTO_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_PARCELAMENTO_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_PARCELAMENTO_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_PARCELAMENTO_PDV dialog = new JD_PARCELAMENTO_PDV(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRParcela1;
    private javax.swing.JRadioButton jRParcela10;
    private javax.swing.JRadioButton jRParcela11;
    private javax.swing.JRadioButton jRParcela12;
    private javax.swing.JRadioButton jRParcela13;
    private javax.swing.JRadioButton jRParcela14;
    private javax.swing.JRadioButton jRParcela15;
    private javax.swing.JRadioButton jRParcela16;
    private javax.swing.JRadioButton jRParcela2;
    private javax.swing.JRadioButton jRParcela3;
    private javax.swing.JRadioButton jRParcela4;
    private javax.swing.JRadioButton jRParcela5;
    private javax.swing.JRadioButton jRParcela6;
    private javax.swing.JRadioButton jRParcela7;
    private javax.swing.JRadioButton jRParcela8;
    private javax.swing.JRadioButton jRParcela9;
    private javax.swing.JRadioButton jrPlano01;
    private javax.swing.JRadioButton jrPlano02;
    private javax.swing.JLabel lblPercTaxa;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblValorTaxa;
    private javax.swing.JPanel painelParcelas;
    private javax.swing.JTextField txtCodVenda;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
