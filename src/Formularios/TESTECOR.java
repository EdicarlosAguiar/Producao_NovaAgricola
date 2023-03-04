/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.fontbox.pfb.PfbParser;

/**
 *
 * @author Desktop
 */
public class TESTECOR extends javax.swing.JFrame {

    /**
     * Creates new form TESTECOR
     */
    public TESTECOR() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        lblPreco = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Gerar etiqueta codigo barras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setText("Codigo Interno:");

        txtCodigo.setText("310300011");

        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNome.setText("PRODUTO TESTE PARA VENDA");
        lblNome.setOpaque(true);

        lblPreco.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblPreco.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPreco.setText("R$ 35,75");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPreco)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(154, 154, 154))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Document documentoPDF = new Document();
            OutputStream outPutStream = null;
            documentoPDF.setMargins(15, 50, 15, 50);
            Font fontPadrao = new Font();
            fontPadrao.setSize(10);
            fontPadrao.setStyle(1);
            fontPadrao.setFamily("ArialBlack");
            fontPadrao.setColor(Color.BLACK);

            Font fintePreco = new Font();
            fintePreco.setSize(15);
            fintePreco.setStyle(1);
            fintePreco.setFamily("ArialBlack");
            fintePreco.setColor(Color.WHITE);

            PdfWriter writer;
            try {

                writer = PdfWriter.getInstance(documentoPDF, new FileOutputStream("C:\\Users\\edica\\Documents\\NetBeansProjects\\NOVAAGRICOLA_02\\src\\DodBarra.pdf"));

                documentoPDF.open();
                documentoPDF.setPageSize(PageSize.A2);
                PdfPTable tabela = new PdfPTable(2);

                Paragraph linhaEmBranco = new Paragraph(" ");

              
                
                PdfPCell embrancoSegundaColunePrimeiraLinha = new PdfPCell(new Paragraph(" ", fontPadrao));
                PdfPCell embrancoSegundaColuneSegundaLinha = new PdfPCell(new Paragraph(" ", fontPadrao));
                PdfPCell embrancoTecerira = new PdfPCell(new Paragraph(" ", fontPadrao));
                PdfPCell embranco3 = new PdfPCell(new Paragraph(" ", fontPadrao));
                PdfPCell produto = new PdfPCell(new Paragraph(lblNome.getText(), fontPadrao));
                //  cell = new PdfPCell(new Phrase("Name"));
                produto.setColspan(2);
           

                PdfPCell preco = new PdfPCell(new Paragraph(lblPreco.getText(), fintePreco));
   preco.setColspan(1);
                embrancoSegundaColuneSegundaLinha.setBorderWidthTop(0);

                embrancoSegundaColunePrimeiraLinha.setBorderWidthLeft(0);
                embrancoSegundaColunePrimeiraLinha.setBorderWidthBottom(0);
                embrancoTecerira.setBorderWidth(0);
                preco.setBackgroundColor(Color.BLACK);
                preco.setHorizontalAlignment(0);//produto
                produto.setPaddingBottom(15);
                produto.setBorderWidthBottom(0);
                preco.setBorderWidthTop(0);
            
                preco.setPadding(11);
                preco.setHorizontalAlignment(1);
                produto.setHorizontalAlignment(1);
                produto.setBorderWidthRight(0);
      

                tabela.addCell(produto);
                tabela.addCell(preco);
                 tabela.addCell(linhaEmBranco);
            
              
                PdfContentByte cb = writer.getDirectContent();
                BarcodeEAN codeEAN = new BarcodeEAN();

                //   Barcode128 codeEAN = new Barcode128();
                String codigo = txtCodigo.getText() + 9999;
                codeEAN.setCodeType(BarcodeEAN.EAN13);
                codeEAN.setCode(codigo);
                Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
                imageEAN.scaleToFit(80, 130);
                imageEAN.setBorderColor(Color.BLACK);

                float posicaoCodY = 350;
                float posicaoCodX = -10;
                Phrase imagemCodigo = new Phrase(new Chunk(imageEAN, posicaoCodY, posicaoCodX));

                float[] columnWidths = new float[]{90, 30};
                tabela.setWidths(columnWidths);

                documentoPDF.add(tabela);
                documentoPDF.add(new Phrase(new Chunk(imageEAN, posicaoCodY, posicaoCodX)));

            } catch (FileNotFoundException ex) {

            } catch (DocumentException ex) {
                Logger.getLogger(BASE_REQ1.class.getName()).log(Level.SEVERE, null, ex);
            }

            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File("C:\\Users\\edica\\Documents\\NetBeansProjects\\NOVAAGRICOLA_02\\src\\DodBarra.pdf"));
            documentoPDF.close();
        } catch (IOException ex) {
            Logger.getLogger(TESTECOR.class.getName()).log(Level.SEVERE, null, ex);

        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TESTECOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TESTECOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TESTECOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TESTECOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TESTECOR().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
