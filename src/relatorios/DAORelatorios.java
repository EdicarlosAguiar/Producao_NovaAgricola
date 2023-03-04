/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package relatorios;

import conexoes.ConexaoMySql;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Administrador
 */
public class DAORelatorios extends ConexaoMySql {

    public boolean gerarRelatorioCliente() {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     estado.uf AS estado_uf,"
                    + "     estado.nome AS estado_nome,"
                    + "     cidade.nome AS cidade_nome,"
                    + "     clientes.NOME AS clientes_NOME,"
                    + "     clientes.ENDERECO AS clientes_ENDERECO,"
                    + "     clientes.BAIRRO AS clientes_BAIRRO,"
                    + "     clientes.CEP AS clientes_CEP,"
                    + "     clientes.TELEFONE AS clientes_TELEFONE,"
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, " 
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO "
                    + " FROM "
                    + "     estado estado INNER JOIN cidade cidade ON estado.codigo = cidade.fk_cod_estado "
                    + "     INNER JOIN clientes clientes ON cidade.codigo = clientes.COD_CIDADE "
                    + "     AND clientes.COD_ESTADO = estado.codigo");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioClientes.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public boolean gerarRelatorioClienteIndividual(int pCodigoCliente) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     estado.uf AS estado_uf,"
                    + "     estado.nome AS estado_nome,"
                    + "     cidade.nome AS cidade_nome,"
                    + "     clientes.NOME AS clientes_NOME,"
                    + "     clientes.ENDERECO AS clientes_ENDERECO,"
                    + "     clientes.BAIRRO AS clientes_BAIRRO,"
                    + "     clientes.CEP AS clientes_CEP,"
                    + "     clientes.TELEFONE AS clientes_TELEFONE,"
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, " 
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO "
                    + " FROM "
                    + "     estado estado INNER JOIN cidade cidade ON estado.codigo = cidade.fk_cod_estado "
                    + "     INNER JOIN clientes clientes ON cidade.codigo = clientes.COD_CIDADE "
                    + "     AND clientes.COD_ESTADO = estado.codigo WHERE clientes.CODIGO = '"+pCodigoCliente+"'");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioClientes.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioProdutos() {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.FORNECEDORES_CODIGO AS produtos_FORNECEDORES_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     produtos.ESTOQUE AS produtos_ESTOQUE, "
                    + "     produtos.CODIGO_BARRAS AS produtos_CODIGO_BARRAS, "
                    + "     produtos.UNIDADE_MEDIDA AS produtos_UNIDADE_MEDIDA, "
                    + "     fornecedores.CODIGO AS fornecedores_CODIGO, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     fornecedores.ENDERECO AS fornecedores_ENDERECO, "
                    + "     fornecedores.BAIRRO AS fornecedores_BAIRRO, "
                    + "     fornecedores.COD_CIDADE AS fornecedores_COD_CIDADE, "
                    + "     fornecedores.COD_ESTADO AS fornecedores_COD_ESTADO, "
                    + "     fornecedores.CEP AS fornecedores_CEP, "
                    + "     fornecedores.TELEFONE AS fornecedores_TELEFONE, "
                    + "     unidade_medida.codigo AS unidade_medida_codigo, "
                    + "     unidade_medida.abreviacao AS unidade_medida_abreviacao, "
                    + "     unidade_medida.descricao AS unidade_medida_descricao "
                    + "FROM "
                    + "     fornecedores fornecedores INNER JOIN produtos produtos ON fornecedores.CODIGO = produtos.FORNECEDORES_CODIGO "
                    + "     INNER JOIN unidade_medida unidade_medida ON produtos.UNIDADE_MEDIDA = unidade_medida.codigo");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioProdutos.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioFornecedores() {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     fornecedores.CODIGO AS fornecedores_CODIGO, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     fornecedores.ENDERECO AS fornecedores_ENDERECO, "
                    + "     fornecedores.BAIRRO AS fornecedores_BAIRRO, "
                    + "     fornecedores.CEP AS fornecedores_CEP, "
                    + "     fornecedores.TELEFONE AS fornecedores_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     estado.nome AS estado_nome "
                    + " FROM "
                    + "     estado estado INNER JOIN cidade cidade ON estado.codigo = cidade.fk_cod_estado "
                    + "     INNER JOIN fornecedores fornecedores ON cidade.codigo = fornecedores.COD_CIDADE "
                    + "     AND fornecedores.COD_ESTADO = estado.codigo");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioFornecedores.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioVenda(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     vendas.CODIGO AS vendas_CODIGO, "
                    + "     vendas.CLIENTES_CODIGO AS vendas_CLIENTES_CODIGO, "
                    + "     vendas.DATA_VENDA AS vendas_DATA_VENDA, "
                    + "     vendas.VALOR_TOTAL AS vendas_VALOR_TOTAL, "
                    + "     vendas.DESCONTO AS vendas_DESCONTO, "
                    + "     vendas_produto.QUANTIDADE AS vendas_produto_QUANTIDADE, "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     unidade_medida.abreviacao AS unidade_medida_abreviacao, "
                    + "     vendas.OBSERVACAO AS vendas_OBSERVACAO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, "
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO, "
                    + "     forma_pagamento.descricao AS forma_pagamento_descricao, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     vendas_produto.`VALOR_UNITARIO` AS vendas_produto_VALOR_UNITARIO "
                    + " FROM "
                    + "     vendas vendas INNER JOIN vendas_produto vendas_produto ON vendas.CODIGO = vendas_produto.CODIGO_VENDA "
                    + "     INNER JOIN produtos produtos ON vendas_produto.CODIGO_PRODUTO = produtos.CODIGO "
                    + "     INNER JOIN unidade_medida unidade_medida ON produtos.UNIDADE_MEDIDA = unidade_medida.codigo "
                    + "     INNER JOIN fornecedores fornecedores ON produtos.FORNECEDORES_CODIGO = fornecedores.CODIGO "
                    + "     INNER JOIN clientes clientes ON vendas.CLIENTES_CODIGO = clientes.CODIGO "
                    + "     INNER JOIN forma_pagamento forma_pagamento ON vendas.TIPO_PAGAMENTO = forma_pagamento.codigo "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado where VENDAS.CODIGO = '" + pCodigo + "'");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioVendas.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public boolean gerarRelatorioOrcamento(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     vendas.CODIGO AS vendas_CODIGO, "
                    + "     vendas.CLIENTES_CODIGO AS vendas_CLIENTES_CODIGO, "
                    + "     vendas.DATA_VENDA AS vendas_DATA_VENDA, "
                    + "     vendas.VALOR_TOTAL AS vendas_VALOR_TOTAL, "
                    + "     vendas.DESCONTO AS vendas_DESCONTO, "
                    + "     vendas_produto.QUANTIDADE AS vendas_produto_QUANTIDADE, "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     unidade_medida.abreviacao AS unidade_medida_abreviacao, "
                    + "     vendas.OBSERVACAO AS vendas_OBSERVACAO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, "
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO, "
                    + "     forma_pagamento.descricao AS forma_pagamento_descricao, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     vendas_produto.`VALOR_UNITARIO` AS vendas_produto_VALOR_UNITARIO  "
                    + " FROM "
                    + "     vendas vendas INNER JOIN vendas_produto vendas_produto ON vendas.CODIGO = vendas_produto.CODIGO_VENDA "
                    + "     INNER JOIN produtos produtos ON vendas_produto.CODIGO_PRODUTO = produtos.CODIGO "
                    + "     INNER JOIN unidade_medida unidade_medida ON produtos.UNIDADE_MEDIDA = unidade_medida.codigo "
                    + "     INNER JOIN fornecedores fornecedores ON produtos.FORNECEDORES_CODIGO = fornecedores.CODIGO "
                    + "     INNER JOIN clientes clientes ON vendas.CLIENTES_CODIGO = clientes.CODIGO "
                    + "     INNER JOIN forma_pagamento forma_pagamento ON vendas.TIPO_PAGAMENTO = forma_pagamento.codigo "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado where VENDAS.CODIGO = '" + pCodigo + "'");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioOrcamento.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioPDV(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     VENDAS.CODIGO AS VENDASCODIGO, "
                    + "     VENDAS.CLIENTES_CODIGO AS VENDASCLIENTES_CODIGO, "
                    + "     VENDAS.DATA_VENDA AS VENDASDATA_VENDA, "
                    + "     VENDAS.VALOR_TOTAL AS VENDASVALOR_TOTAL, "
                    + "     VENDAS.DESCONTO AS VENDASDESCONTO, "
                    + "     VENDAS_PRODUTO.CODIGO AS VENDAS_PRODUTOCODIGO, "
                    + "     VENDAS_PRODUTO.CODIGO_PRODUTO AS VENDAS_PRODUTOCODIGO_PRODUTO, "
                    + "     VENDAS_PRODUTO.CODIGO_VENDA AS VENDAS_PRODUTOCODIGO_VENDA, "
                    + "     VENDAS_PRODUTO.QUANTIDADE AS VENDAS_PRODUTOQUANTIDADE, "
                    + "     PRODUTOS.CODIGO AS PRODUTOSCODIGO, "
                    + "     PRODUTOS.NOME AS PRODUTOSNOME, "
                    + "     PRODUTOS.VALOR AS PRODUTOSVALOR, "
                    + "     CLIENTES.CODIGO AS CLIENTESCODIGO, "
                    + "     empresa.RAZAO_SOCIAL AS empresa_RAZAO_SOCIAL, "
                    + "     empresa.NOME_FANTASIA AS empresa_NOME_FANTASIA, "
                    + "     empresa.ENDERECO AS empresa_ENDERECO, "
                    + "     empresa.BAIRRO AS empresa_BAIRRO, "
                    + "     empresa.COD_CIDADE AS empresa_COD_CIDADE, "
                    + "     empresa.COD_ESTADO AS empresa_COD_ESTADO, "
                    + "     empresa.CEP AS empresa_CEP, "
                    + "     empresa.TELEFONE AS empresa_TELEFONE, "
                    + "     empresa.CNPJ AS empresa_CNPJ, "
                    + "     estado.uf AS estado_uf, "
                    + "     cidade.nome AS cidade_nome "
                    + "FROM "
                    + "     VENDAS VENDAS INNER JOIN VENDAS_PRODUTO VENDAS_PRODUTO ON VENDAS.CODIGO = VENDAS_PRODUTO.CODIGO_VENDA "
                    + "     INNER JOIN CLIENTES CLIENTES ON VENDAS.CLIENTES_CODIGO = CLIENTES.CODIGO "
                    + "     INNER JOIN PRODUTOS PRODUTOS ON VENDAS_PRODUTO.CODIGO_PRODUTO = PRODUTOS.CODIGO, "
                    + "     estado estado INNER JOIN cidade cidade ON estado.codigo = cidade.fk_cod_estado "
                    + "     INNER JOIN empresa empresa ON cidade.codigo = empresa.COD_CIDADE "
                    + "     AND empresa.COD_ESTADO = estado.codigo where VENDAS.CODIGO = '" + pCodigo + "'");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioPDV.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:\\BLVendasPdvMySQL\\rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContaReceber(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     estado.uf AS estado_uf, "
                    + "     cidade.nome AS cidade_nome,"
                    + "     clientes.CODIGO AS clientes_CODIGO,"
                    + "     clientes.NOME AS clientes_NOME,"
                    + "     clientes.ENDERECO AS clientes_ENDERECO,"
                    + "     clientes.BAIRRO AS clientes_BAIRRO,"
                    + "     clientes.CEP AS clientes_CEP,"
                    + "     clientes.TELEFONE AS clientes_TELEFONE,"
                    + "     contas_receber.PK_CODIGO AS contas_receber_PK_CODIGO,"
                    + "     contas_receber.DESCRICAO AS contas_receber_DESCRICAO,"
                    + "     contas_receber.DATA AS contas_receber_DATA,"
                    + "     contas_receber.VENCIMENTO AS contas_receber_VENCIMENTO,"
                    + "     contas_receber.FK_TIPO_PAGAMENTO AS contas_receber_FK_TIPO_PAGAMENTO,"
                    + "     contas_receber.OBSERVACAO AS contas_receber_OBSERVACAO,"
                    + "     contas_receber.SITUACAO AS contas_receber_SITUACAO,"
                    + "     contas_receber.VALOR AS contas_receber_VALOR,"
                    + "     contas_receber.PAGAMENTO AS contas_receber_PAGAMENTO "
                    + " FROM "
                    + "     clientes clientes INNER JOIN contas_receber contas_receber ON clientes.CODIGO = contas_receber.FK_CODIGO_PESSOA "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado WHERE pk_codigo = '" + pCodigo + "';");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioContaReceber.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContaPagar(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     contas_pagar.PK_CODIGO AS contas_pagar_PK_CODIGO, "
                    + "     contas_pagar.FK_CODIGO_PESSOA AS contas_pagar_FK_CODIGO_PESSOA, "
                    + "     contas_pagar.DESCRICAO AS contas_pagar_DESCRICAO, "
                    + "     contas_pagar.DATA AS contas_pagar_DATA, "
                    + "     contas_pagar.VENCIMENTO AS contas_pagar_VENCIMENTO, "
                    + "     contas_pagar.OBSERVACAO AS contas_pagar_OBSERVACAO, "
                    + "     contas_pagar.SITUACAO AS contas_pagar_SITUACAO, "
                    + "     contas_pagar.VALOR AS contas_pagar_VALOR, "
                    + "     contas_pagar.PAGAMENTO AS contas_pagar_PAGAMENTO, "
                    + "     fornecedores.CODIGO AS fornecedores_CODIGO, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     fornecedores.ENDERECO AS fornecedores_ENDERECO, "
                    + "     fornecedores.BAIRRO AS fornecedores_BAIRRO, "
                    + "     fornecedores.CEP AS fornecedores_CEP, "
                    + "     fornecedores.TELEFONE AS fornecedores_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     estado.nome AS estado_nome "
                    + " FROM "
                    + "     estado estado INNER JOIN cidade cidade ON estado.codigo = cidade.fk_cod_estado "
                    + "     INNER JOIN fornecedores fornecedores ON cidade.codigo = fornecedores.COD_CIDADE "
                    + "     AND fornecedores.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN contas_pagar contas_pagar ON fornecedores.CODIGO = contas_pagar.FK_CODIGO_PESSOA WHERE contas_pagar.PK_CODIGO = " + pCodigo + ";");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioContaPagar.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioVendasCliente(Date pDataInicial, Date dataFinal, int pCodigoCliente) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     vendas.CODIGO AS vendas_CODIGO, "
                    + "     vendas.CLIENTES_CODIGO AS vendas_CLIENTES_CODIGO, "
                    + "     vendas.DATA_VENDA AS vendas_DATA_VENDA, "
                    + "     vendas.VALOR_TOTAL AS vendas_VALOR_TOTAL, "
                    + "     vendas.DESCONTO AS vendas_DESCONTO, "
                    + "     vendas_produto.QUANTIDADE AS vendas_produto_QUANTIDADE, "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     unidade_medida.abreviacao AS unidade_medida_abreviacao, "
                    + "     vendas.OBSERVACAO AS vendas_OBSERVACAO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, "
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO, "
                    + "     forma_pagamento.descricao AS forma_pagamento_descricao, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     vendas_produto.`VALOR_UNITARIO` AS vendas_produto_VALOR_UNITARIO  "
                    + " FROM "
                    + "     vendas vendas INNER JOIN vendas_produto vendas_produto ON vendas.CODIGO = vendas_produto.CODIGO_VENDA "
                    + "     INNER JOIN produtos produtos ON vendas_produto.CODIGO_PRODUTO = produtos.CODIGO "
                    + "     INNER JOIN unidade_medida unidade_medida ON produtos.UNIDADE_MEDIDA = unidade_medida.codigo "
                    + "     INNER JOIN fornecedores fornecedores ON produtos.FORNECEDORES_CODIGO = fornecedores.CODIGO "
                    + "     INNER JOIN clientes clientes ON vendas.CLIENTES_CODIGO = clientes.CODIGO "
                    + "     INNER JOIN forma_pagamento forma_pagamento ON vendas.TIPO_PAGAMENTO = forma_pagamento.codigo "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado "
                    + "WHERE "
                    + "(data_venda BETWEEN  '" + pDataInicial + "' AND '" + dataFinal + "' )"
                    + "AND (vendas.tipo = 1) "
                    + "AND (clientes.codigo = '"+pCodigoCliente+"')");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioVendasPorCliente.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioVendaTodosCliente(Date pDataInicial, Date dataFinal) {
        try {
            this.conectar();
           this.executarSQL("SELECT "
                    + "     vendas.CODIGO AS vendas_CODIGO, "
                    + "     vendas.CLIENTES_CODIGO AS vendas_CLIENTES_CODIGO, "
                    + "     vendas.DATA_VENDA AS vendas_DATA_VENDA, "
                    + "     vendas.VALOR_TOTAL AS vendas_VALOR_TOTAL, "
                    + "     vendas.DESCONTO AS vendas_DESCONTO, "
                    + "     vendas_produto.QUANTIDADE AS vendas_produto_QUANTIDADE, "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf, "
                    + "     unidade_medida.abreviacao AS unidade_medida_abreviacao, "
                    + "     vendas.OBSERVACAO AS vendas_OBSERVACAO, "
                    + "     clientes.CPF_CNPJ AS clientes_CPF_CNPJ, "
                    + "     clientes.OBSERVACAO AS clientes_OBSERVACAO, "
                    + "     forma_pagamento.descricao AS forma_pagamento_descricao, "
                    + "     fornecedores.NOME AS fornecedores_NOME, "
                    + "     vendas_produto.`VALOR_UNITARIO` AS vendas_produto_VALOR_UNITARIO  "
                    + " FROM "
                    + "     vendas vendas INNER JOIN vendas_produto vendas_produto ON vendas.CODIGO = vendas_produto.CODIGO_VENDA "
                    + "     INNER JOIN produtos produtos ON vendas_produto.CODIGO_PRODUTO = produtos.CODIGO "
                    + "     INNER JOIN unidade_medida unidade_medida ON produtos.UNIDADE_MEDIDA = unidade_medida.codigo "
                    + "     INNER JOIN fornecedores fornecedores ON produtos.FORNECEDORES_CODIGO = fornecedores.CODIGO "
                    + "     INNER JOIN clientes clientes ON vendas.CLIENTES_CODIGO = clientes.CODIGO "
                    + "     INNER JOIN forma_pagamento forma_pagamento ON vendas.TIPO_PAGAMENTO = forma_pagamento.codigo "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado "
                    + "WHERE "
                    + "(data_venda BETWEEN  '" + pDataInicial + "' AND '" + dataFinal + "' )"
                    + "AND (vendas.tipo = 1)");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioVendasPeriodo.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Gerar relatório de venda de uma conta a receber
     *
     * @param pCodigo
     * @return
     */
    public boolean gerarRelatorioContaVenda(int pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     vendas.CODIGO AS vendas_CODIGO, "
                    + "     vendas.CLIENTES_CODIGO AS vendas_CLIENTES_CODIGO, "
                    + "     vendas.DATA_VENDA AS vendas_DATA_VENDA, "
                    + "     vendas.VALOR_TOTAL AS vendas_VALOR_TOTAL, "
                    + "     vendas.DESCONTO AS vendas_DESCONTO, "
                    + "     vendas.TIPO_PAGAMENTO AS vendas_TIPO_PAGAMENTO, "
                    + "     vendas_produto.CODIGO AS vendas_produto_CODIGO, "
                    + "     vendas_produto.CODIGO_PRODUTO AS vendas_produto_CODIGO_PRODUTO, "
                    + "     vendas_produto.CODIGO_VENDA AS vendas_produto_CODIGO_VENDA, "
                    + "     vendas_produto.QUANTIDADE AS vendas_produto_QUANTIDADE, "
                    + "     produtos.CODIGO AS produtos_CODIGO, "
                    + "     produtos.FORNECEDORES_CODIGO AS produtos_FORNECEDORES_CODIGO, "
                    + "     produtos.NOME AS produtos_NOME, "
                    + "     produtos.VALOR AS produtos_VALOR, "
                    + "     produtos.ESTOQUE AS produtos_ESTOQUE, "
                    + "     produtos.CODIGO_BARRAS AS produtos_CODIGO_BARRAS, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE, "
                    + "     cidade.nome AS cidade_nome, "
                    + "     estado.uf AS estado_uf "
                    + " FROM "
                    + "     clientes clientes INNER JOIN vendas vendas ON clientes.CODIGO = vendas.CLIENTES_CODIGO "
                    + "     INNER JOIN vendas_produto vendas_produto ON vendas.CODIGO = vendas_produto.CODIGO_VENDA "
                    + "     INNER JOIN produtos produtos ON vendas_produto.CODIGO_PRODUTO = produtos.CODIGO "
                    + "     INNER JOIN cidade cidade ON clientes.COD_CIDADE = cidade.codigo "
                    + "     INNER JOIN estado estado ON clientes.COD_ESTADO = estado.codigo "
                    + "     AND estado.codigo = cidade.fk_cod_estado "
                    + " WHERE vendas.CODIGO = '" + pCodigo + "';");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioContaVendasReceber.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContasReceberCliente(int pCodigoCliente, int pStatus, Date pDataInicial, Date pDataFinal) {
        String pCaminho = "";
            if (pStatus == 0) {
                pCaminho = "ArquivosJasper/relatorioReceber.jasper";
            } else {
                pCaminho = "ArquivosJasper/relatorioRecebidas.jasper";
            }
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     contas_receber.PK_CODIGO AS contas_receber_PK_CODIGO, "
                    + "     contas_receber.DESCRICAO AS contas_receber_DESCRICAO, "
                    + "     contas_receber.DATA AS contas_receber_DATA, "
                    + "     contas_receber.VENCIMENTO AS contas_receber_VENCIMENTO, "
                    + "     contas_receber.OBSERVACAO AS contas_receber_OBSERVACAO, "
                    + "     contas_receber.SITUACAO AS contas_receber_SITUACAO, "
                    + "     contas_receber.VALOR AS contas_receber_VALOR, "
                    + "     contas_receber.PAGAMENTO AS contas_receber_PAGAMENTO, "
                    + "     clientes.CODIGO AS clientes_CODIGO, "
                    + "     clientes.NOME AS clientes_NOME, "
                    + "     clientes.ENDERECO AS clientes_ENDERECO, "
                    + "     clientes.BAIRRO AS clientes_BAIRRO, "
                    + "     clientes.CEP AS clientes_CEP, "
                    + "     clientes.TELEFONE AS clientes_TELEFONE "
                    + " FROM "
                    + "     contas_receber contas_receber INNER JOIN clientes clientes ON contas_receber.FK_CODIGO_PESSOA = clientes.CODIGO  "
                    + "WHERE (clientes.CODIGO = '" + pCodigoCliente + "') AND (pagamento BETWEEN  '" + pDataInicial + "' AND '" + pDataFinal + "'"
                    + " ) AND (SITUACAO = '" + pStatus + "');");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream(pCaminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContasPagarFornecedor(int pCodigoFornecedor, int pStatus, Date pDataInicial, Date pDataFinal) {
        String pCaminho = "";
            if (pStatus == 0) {
                pCaminho = "ArquivosJasper/relatorioPagar.jasper";
            } else {
                pCaminho = "ArquivosJasper/relatorioPagas.jasper";
            }
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     fornecedores.CODIGO  AS fornecedores_CODIGO, "
                    + "     fornecedores.NOME  AS fornecedores_NOME, "
                    + "     fornecedores.ENDERECO  AS fornecedores_ENDERECO, "
                    + "     fornecedores.BAIRRO  AS fornecedores_BAIRRO, "
                    + "     fornecedores.CEP  AS fornecedores_CEP, "
                    + "     fornecedores.TELEFONE  AS fornecedores_TELEFONE, "
                    + "     contas_pagar.PK_CODIGO  AS contas_pagar_PK_CODIGO, "
                    + "     contas_pagar.FK_CODIGO_PESSOA  AS contas_pagar_FK_CODIGO_PESSOA, "
                    + "     contas_pagar.DESCRICAO  AS contas_pagar_DESCRICAO, "
                    + "     contas_pagar.DATA  AS contas_pagar_DATA, "
                    + "     contas_pagar.VENCIMENTO  AS contas_pagar_VENCIMENTO, "
                    + "     contas_pagar.FK_TIPO_PAGAMENTO  AS contas_pagar_FK_TIPO_PAGAMENTO, "
                    + "     contas_pagar.OBSERVACAO  AS contas_pagar_OBSERVACAO, "
                    + "     contas_pagar.SITUACAO  AS contas_pagar_SITUACAO, "
                    + "     contas_pagar.VALOR  AS contas_pagar_VALOR, "
                    + "     contas_pagar.PAGAMENTO  AS contas_pagar_PAGAMENTO, "
                    + "     cidade.nome  AS cidade_nome, "
                    + "     estado.uf  AS estado_uf "
                    + " FROM "
                    + "      contas_pagar  contas_pagar INNER JOIN  fornecedores  fornecedores ON contas_pagar. FK_CODIGO_PESSOA  = fornecedores. CODIGO  "
                    + "     INNER JOIN  estado  estado ON fornecedores. COD_ESTADO  = estado. codigo  "
                    + "     INNER JOIN  cidade  cidade ON fornecedores. COD_CIDADE  = cidade. codigo  "
                    + "     AND estado. codigo  = cidade. fk_cod_estado WHERE (fornecedores.CODIGO = '" + pCodigoFornecedor + "') AND (pagamento BETWEEN  '" + pDataInicial + "' AND '" + pDataFinal + "'"
                    + " ) AND (SITUACAO = '" + pStatus + "');");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream(pCaminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContasReceberTODAS(int pStatus, Date pDataInicial, Date pDataFinal) {
        String pCaminho = "";
            if (pStatus == 0) {
                pCaminho = "ArquivosJasper/relatorioReceber.jasper";
            } else {
                pCaminho = "ArquivosJasper/relatorioRecebidas.jasper";
            }
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     contas_receber.PK_CODIGO AS contas_receber_PK_CODIGO,"
                    + "     contas_receber.DESCRICAO AS contas_receber_DESCRICAO,"
                    + "     contas_receber.DATA AS contas_receber_DATA,"
                    + "     contas_receber.VENCIMENTO AS contas_receber_VENCIMENTO,"
                    + "     contas_receber.OBSERVACAO AS contas_receber_OBSERVACAO,"
                    + "     contas_receber.SITUACAO AS contas_receber_SITUACAO,"
                    + "     contas_receber.VALOR AS contas_receber_VALOR,"
                    + "     contas_receber.PAGAMENTO AS contas_receber_PAGAMENTO,"
                    + "     clientes.CODIGO AS clientes_CODIGO,"
                    + "     clientes.NOME AS clientes_NOME,"
                    + "     clientes.ENDERECO AS clientes_ENDERECO,"
                    + "     clientes.BAIRRO AS clientes_BAIRRO,"
                    + "     clientes.CEP AS clientes_CEP,"
                    + "     clientes.TELEFONE AS clientes_TELEFONE "
                    + " FROM"
                    + "     contas_receber contas_receber INNER JOIN clientes clientes ON contas_receber.FK_CODIGO_PESSOA = clientes.CODIGO "
                    + " WHERE pagamento BETWEEN  '" + pDataInicial + "' AND '" + pDataFinal + "'"
                    + " AND SITUACAO = '" + pStatus + "';");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream(pCaminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioContasPagarTODAS(int pStatus, Date pDataInicial, Date pDataFinal) {
        String pCaminho = "";
            if (pStatus == 0) {
                pCaminho = "ArquivosJasper/relatorioPagar.jasper";
            } else {
                pCaminho = "ArquivosJasper/relatorioPagas.jasper";
            }
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     fornecedores. CODIGO  AS fornecedores_CODIGO, "
                    + "     fornecedores. NOME  AS fornecedores_NOME, "
                    + "     fornecedores. ENDERECO  AS fornecedores_ENDERECO, "
                    + "     fornecedores. BAIRRO  AS fornecedores_BAIRRO, "
                    + "     fornecedores. CEP  AS fornecedores_CEP, "
                    + "     fornecedores. TELEFONE  AS fornecedores_TELEFONE, "
                    + "     contas_pagar. PK_CODIGO  AS contas_pagar_PK_CODIGO, "
                    + "     contas_pagar. FK_CODIGO_PESSOA  AS contas_pagar_FK_CODIGO_PESSOA, "
                    + "     contas_pagar. DESCRICAO  AS contas_pagar_DESCRICAO, "
                    + "     contas_pagar. DATA  AS contas_pagar_DATA, "
                    + "     contas_pagar. VENCIMENTO  AS contas_pagar_VENCIMENTO, "
                    + "     contas_pagar. FK_TIPO_PAGAMENTO  AS contas_pagar_FK_TIPO_PAGAMENTO, "
                    + "     contas_pagar. OBSERVACAO  AS contas_pagar_OBSERVACAO, "
                    + "     contas_pagar. SITUACAO  AS contas_pagar_SITUACAO, "
                    + "     contas_pagar. VALOR  AS contas_pagar_VALOR, "
                    + "     contas_pagar. PAGAMENTO  AS contas_pagar_PAGAMENTO, "
                    + "     cidade. nome  AS cidade_nome, "
                    + "     estado. uf  AS estado_uf "
                    + " FROM "
                    + "      contas_pagar  contas_pagar INNER JOIN  fornecedores  fornecedores ON contas_pagar. FK_CODIGO_PESSOA  = fornecedores. CODIGO  "
                    + "     INNER JOIN  estado  estado ON fornecedores. COD_ESTADO  = estado. codigo  "
                    + "     INNER JOIN  cidade  cidade ON fornecedores. COD_CIDADE  = cidade. codigo  "
                    + "     AND estado. codigo  = cidade. fk_cod_estado  WHERE pagamento BETWEEN  '" + pDataInicial + "' AND '" + pDataFinal + "'"
                    + " AND SITUACAO = '" + pStatus + "';");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream(pCaminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
