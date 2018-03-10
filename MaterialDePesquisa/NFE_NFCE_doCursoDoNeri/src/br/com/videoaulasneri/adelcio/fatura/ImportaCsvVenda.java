/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Importar.ImportaCadastros;
import br.com.videoaulasneri.adelcio.fatura.bean.PedImporta;
import br.com.videoaulasneri.adelcio.fatura.bean.ProdImporta;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportaCsvVenda
{
	private Scanner input;
        Connection connection;
        Statement statement;
        ResultSet rs_arq;
        String driver, url, usuario, senha, uf;
        private int empresa, qtregi;
        String tipoReg = "";
        final String  IDE = "ide".toUpperCase();
        final String  EMIT = "emit".toUpperCase();
        final String  DEST = "dest".toUpperCase();
        final String  PROD = "prod".toUpperCase();
        final String  TOTAL = "total".toUpperCase();
        final String  TRANSP = "transp".toUpperCase();
        final String  COBR = "cobr".toUpperCase();
        final String  INFADIC = "infAdic".toUpperCase();
        private boolean continua = true;
        private String tipoAmbiente = "", emp_cnpj = "", codigoPedido = "";
        private int codCliente = 0;
        Date dataHoje = new Date(), dataOntem = dataHoje;
        PedImporta pedImp;
        ProdImporta prodImp;
        List<ProdImporta> listProd;
        
        public ImportaCsvVenda(int empresa, Connection confat, String tipoAmbiente) {
            this.empresa = empresa;
            this.connection = confat;
            this.tipoAmbiente = tipoAmbiente;
            try {
                statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch (Exception e) {
                
            }
        }

	public void importaArquivo()
	{
            boolean executou = false;
            String pathCSV = "c:\\nfe";
            String arquivoCSV = null;
            File arquivo = escolhe_arquivo(pathCSV, JFileChooser.FILES_ONLY, "csv");
            if (arquivo != null) {
                arquivoCSV = arquivo.getAbsolutePath();
                int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Importar o arquivo\n"+arquivo, "Importa Venda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if ( escolha == JOptionPane.YES_OPTION){
                    if (arquivoCSV != null) {

                        Calendar calHoje = Calendar.getInstance();
                        calHoje.setTime( new Date() );
                        calHoje.set(Calendar.HOUR_OF_DAY, 0);
                        calHoje.set(Calendar.MINUTE, 0);
                        calHoje.set(Calendar.SECOND, 0);
                        calHoje.set(Calendar.MILLISECOND, 0);
                        dataHoje = calHoje.getTime();

                        Calendar calOntem = Calendar.getInstance();
                        calOntem.setTime( dataOntem );
                        calOntem.add(Calendar.DAY_OF_MONTH, -2);
                        calOntem.set(Calendar.HOUR_OF_DAY, 0);
                        calOntem.set(Calendar.MINUTE, 0);
                        calOntem.set(Calendar.SECOND, 0);
                        calOntem.set(Calendar.MILLISECOND, 0);
                        dataOntem = calOntem.getTime();
                        System.out.println("Data de Hoje: "+ dataHoje+" - Data de Ontem: " + dataOntem);

                        processa(arquivoCSV);
                        fechaArquivo();
                        executou = true;
                    }
                }
            }
            if (!executou) {
                JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado e a Importação não será realizada!");
            }
	}
        public void processa(String arqcsv)
        {
		System.out.println("Nome do arquivo texto: " + arqcsv );
		
		try
		{
                    input = new Scanner(new File(arqcsv));
                    leRegistros();
		}
		catch (FileNotFoundException e)
		{
                    JOptionPane.showMessageDialog(null, "Não encontrou o arquivo: " + arqcsv + " e a Importação de Cidades não será efetuada!");
		}
            
        }
	public void leRegistros()
	{
            int qtReg = 0;
            try
            {
                String frase = input.nextLine();
		while ( input.hasNextLine())
		{
//                    frase = input.nextLine();
                    String delimita = ";";
                    qtReg++;
                    StringTokenizer token = new StringTokenizer( frase , delimita);
                    int cpo = 0;
                    while ( token.hasMoreTokens() )
                    {
                        if (!continua) break;
                        cpo++;
                        String wconteudo = token.nextToken();
                        switch (cpo%20)
                        {
                                case 1: 
                                    identificarTipo(wconteudo);
                                    break;
                                default: 
                                    tratarDados(cpo, wconteudo);
                                    break;
                    	}
                    }
                    if (!continua) break;
                    frase = input.nextLine();
                }
                if (continua && listProd.size() > 0) {
                    double vlrTotal = 0, vlrDesc = 0, vlrProd = 0, vlrUnitQtde = 0, pesoVol = 0;
                    String msgErro = "";
                    int qtdeVol = 0;
                    for(ProdImporta prodImp : listProd) {
                        vlrProd += prodImp.getProd_vlrProduto();
                        vlrDesc += prodImp.getProd_vlrDesconto();
                        vlrTotal += prodImp.getProd_vlrTotal();
                        qtdeVol += (int)prodImp.getProd_qtde();
                        pesoVol += (prodImp.getProd_peso() * prodImp.getProd_qtde());
                        vlrUnitQtde = prodImp.getProd_vlrUnitario() * prodImp.getProd_qtde();
                        System.out.println("Item: " + prodImp.getProd_item() + " - Cod.Produto: " + prodImp.getProd_codProduto() + "  - Vlr.Total: " + prodImp.getProd_vlrTotal());
                        if (!verificaProduto(prodImp.getProd_codProduto(), prodImp.getProd_item())) {
                            msgErro += "\nO Produto [" + prodImp.getProd_codProduto() + "] informado no Item [" + prodImp.getProd_item() + "] não está cadastrado!";
                        }
                        if (vlrUnitQtde != prodImp.getProd_vlrProduto()) {
                            msgErro += "No Item [" + prodImp.getProd_item()+ "] Vlr Unitario x Qtde [" + vlrUnitQtde + "] está diferente do Vlr do Produto informado [" + prodImp.getProd_vlrProduto() + "]!";
                        }
                        if (prodImp.getProd_vlrTotal() != (prodImp.getProd_vlrProduto() - prodImp.getProd_vlrDesconto())) {
                            msgErro += "\nNo Item [" + prodImp.getProd_item()+ "] Vlr Total [" + prodImp.getProd_vlrTotal() + "] está diferente do (Vlr do Produto - Vlr desconto) informado [" + (prodImp.getProd_vlrProduto() - prodImp.getProd_vlrDesconto())+ "]!";
                        }
                    }
                    if (vlrProd != pedImp.getTotal_vlrProdutos()) {
                        msgErro += "\nA soma do Vlr de Produtos [" + vlrProd+ "] está diferente do Vlr Total de Produtos do Pedido informado [" + pedImp.getTotal_vlrProdutos() + "]!";
                    }
                    if (vlrDesc != pedImp.getTotal_vlrDescontos()) {
                        msgErro += "\nA soma do Vlr de Desconto dos Produtos [" + vlrDesc+ "] está diferente do Vlr Total de Descontos do Pedido informado [" + pedImp.getTotal_vlrDescontos()+ "]!";
                    }
                    if (vlrTotal != pedImp.getTotal_vlrTotal()) {
                        msgErro += "\nA soma do Vlr Total dos Produtos [" + vlrTotal+ "] está diferente do Vlr Total do Pedido informado [" + pedImp.getTotal_vlrTotal() + "]!";
                    }
                    if (pedImp.getTotal_vlrTotal() != (pedImp.getTotal_vlrProdutos() - pedImp.getTotal_vlrDescontos())) {
                        msgErro += "\nO Valor Total Vlr Total [" + pedImp.getTotal_vlrTotal() + "] está diferente dos (Vlr dos Produtos - Vlr dos Descontos) do Pedido informado [" + (pedImp.getTotal_vlrProdutos() - pedImp.getTotal_vlrDescontos()) + "]!";
                    }
                    if (msgErro.length() > 0) {
                        continua = false;
                        JOptionPane.showMessageDialog(null, " Ocorreram os Seguintes erros na Verificação do Pedido [" + codigoPedido + "]:\n" +msgErro + "\n\n\tE o Pedido não será gravado!");
                    } else {
                        pedImp.setTransp_qtdeVol(qtdeVol);
                        pedImp.setTransp_pesoVol(pesoVol);
                        gravaPedido();
                    }
               }
                JOptionPane.showMessageDialog(null, "Fim da importação do Pedido!");
	    }
            catch( NoSuchElementException e)
            {
            	System.err.println("Erro de tipo de dado incompativel!");
		System.exit(1);
            }
            catch (IllegalStateException e)
            {
            	System.err.println("Erro ao ler o arquivo!");
		System.exit(1);
            }
        }    
        private void identificarTipo(String texto) {
            System.out.println("\nidentificarTipo: " + texto);
            tipoReg = texto.toUpperCase();
        }
        
        private void  tratarDados(int cpo, String valor) {
System.out.print("campo["+cpo+"] - ["+valor+"] / ");
            if (tipoReg.equals(IDE)) {
                switch (cpo) {
                    case 2:
                        pedImp = new PedImporta();
                        listProd = new ArrayList();
                        pedImp.setIde_ambiente(verifyString(valor, cpo, 1));
                        if (!continua) break;
                        if (!pedImp.getIde_ambiente().equals(this.tipoAmbiente)) {
                            JOptionPane.showMessageDialog(null, "Tipo de ambiente do Pedido [" + pedImp.getIde_ambiente() + "] difere do Tipo de Ambiente Atual [" + this.tipoAmbiente + "]");
                            continua = false;
                            break;
                        }
                        break;
                    case 3:
                        pedImp.setIde_tipoNfe(verifyString(valor, cpo, 1));
                        if (!continua) break;
                        if (!pedImp.getIde_tipoNfe().equals("0") && !pedImp.getIde_tipoNfe().equals("1")) {
                            JOptionPane.showMessageDialog(null, "Tipo de NFe do Pedido [" + valor + "] difere dos Tipos esperados [0 ou 1]");
                            continua = false;
                            break;
                        }
                        break;
                    case 4:
                        pedImp.setIde_modeloNfe(verifyString(valor, cpo, 2));
                        if (!continua) break;
                        if (!pedImp.getIde_modeloNfe().equals("55")) {
                            JOptionPane.showMessageDialog(null, "Modelo de NFe do Pedido [" + valor + "] difere do Modelo esperado [55]");
                            continua = false;
                            break;
                        }
                        break;
                    case 5:  //  codigo do pedido
                        codigoPedido = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        break;
                    case 6:
                        Date date = convertStringToDate(valor, cpo);
                        if (!continua) break;
                        if (date.compareTo(dataOntem) < 0) {
                            continua = false;
                            JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + cpo + "] do Registro [" + tipoReg +"] para Data. A Data informada [" + valor + "] é menor que a data de ontem [" + convertDateToString(dataOntem) + "]");
                        }
                        pedImp.setIde_dataEmissao(date);
                        break;
                    default:
                        break;
                }
            } else
            if (tipoReg.equals(EMIT)) {
                switch (cpo) {
                    case 2:
                        pedImp.setEmit_cnpj(verifyString(valor, cpo, 14));
                        if (!continua) break;
                        if (!pedImp.getEmit_cnpj().equals(verificaCnpjEmitente())) {
                            JOptionPane.showMessageDialog(null, "CNPJ do Emitente do Pedido [" + valor + "] difere do CNPJ do Emitente Atual [" + this.emp_cnpj + "]");
                            continua = false;
                            break;
                        }
                        break;
                    default:
                        break;
                }
            } else
            if (tipoReg.equals(DEST)) {
                switch (cpo) {
                    case 2:
                        pedImp.setDest_tipoPessoa(verifyString(valor, cpo, 1));
                        if (!continua) break;
                        if (
                                !pedImp.getDest_tipoPessoa().equals("F") && 
                                !pedImp.getDest_tipoPessoa().equals("J") && 
                                !pedImp.getDest_tipoPessoa().equals(" ")
                                ) {
                            JOptionPane.showMessageDialog(null, "Tipo de Pessoa do Pedido [" + valor + "] difere dos Tipos esperados [F ou J]");
                            continua = false;
                            break;
                        }
                        break;
                    case 3:
                        if (pedImp.getDest_tipoPessoa().equals("F")) {
                            pedImp.setDest_doc(verifyString(valor, cpo, 11));
                        } else if (pedImp.getDest_tipoPessoa().equals("J")) {
                            pedImp.setDest_doc(verifyString(valor, cpo, 14));
                        }
                        if (!continua) break;
                        if (!existeCliente("N")) {
                            JOptionPane.showMessageDialog(null, "O cliente do documento [" + pedImp.getDest_doc()+ "] informado não está cadastrado!");
                            continua = false;
                        } else {
                            pedImp.setCod_cliente(codCliente);
                        }
                        break;
                    default:
                        break;
                }

            } else
            if (tipoReg.equals(PROD)) {
                int vlrInt = 0;
                double vlrDouble = 0.0;
                float vlrFloat = 0f;
                String vlrString;
                switch (cpo) {
                    case 2:
                        prodImp = new ProdImporta();
                        vlrInt = convertToInteger(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_item(vlrInt);
                        break;
                    case 3:
                        vlrString = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_ean(vlrString);
                        break;
                    case 4:
                        vlrString = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_ncm(vlrString);
                        break;
                    case 5:
                        vlrInt = convertToInteger(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_cfop(vlrInt);
                        break;
                    case 6:
                        vlrInt = convertToInteger(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_seqCfop(vlrInt);
                        break;
                    case 7:
                        vlrFloat = convertToFloat(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_qtde(vlrFloat);
                        break;
                    case 8:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_vlrUnitario(vlrDouble);
                        break;
                    case 9:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_vlrProduto(vlrDouble);
                        break;
                    case 10:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_vlrDesconto(vlrDouble);
                        break;
                    case 11:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_vlrTotal(vlrDouble);
                        break;
                    case 12:
                        vlrString = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_descricao(vlrString);
                        break;
                    case 13:
                        vlrString = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        prodImp.setProd_unidade(vlrString);
                        break;
                    case 14:
                        vlrString = verifyNotNull(valor, cpo);
                        if (!continua) break;
                        verificaProduto(valor, prodImp.getProd_item());
                        if (!continua) break;
                        prodImp.setProd_codProduto(vlrString);
                        listProd.add(prodImp);
                        break;
                    default:
                        break;
                }
 
            } else
            if (tipoReg.equals(TOTAL)) {
                double vlrDouble = 0.0;
                switch (cpo) {
                    case 2:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        pedImp.setTotal_vlrProdutos(vlrDouble);
                        break;
                    case 3:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        pedImp.setTotal_vlrDescontos(vlrDouble);
                        break;
                    case 4:
                        vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        pedImp.setTotal_vlrTotal(vlrDouble);
                        break;
                    default:
                        break;
                }

            } else
            if (tipoReg.equals(TRANSP)) {
                switch (cpo) {
                    case 2:
                        if (valor != null && valor.length() > 1) {
                            pedImp.setTransp_cnpj(verifyString(valor, cpo, 14));
                            if (!existeCliente("S")) {
                               JOptionPane.showMessageDialog(null, "O transportador do documento [" + pedImp.getTransp_cnpj()+ "] informado não está cadastrado!");
                               continua = false;
                           } else {
                                pedImp.setTransp_codTransp(codCliente);
                            }
                       } 
                        break;
                    default:
                        break;
                }

            } else
            if (tipoReg.equals(COBR)) {
                switch (cpo) {
                    case 2:
                        verifyString(valor, cpo, 2);
                        if (!continua) break;
                        verificaTipoDoc(valor);
                        if (!continua) break;
                        pedImp.setCobr_codTipoDoc(valor);
                        break;
                    case 3:
                        int vlrInt = convertToInteger(valor, cpo);
                        if (!continua) break;
                        verificaFormaPgto(valor);
                        if (!continua) break;
                        pedImp.setCobr_codFormaPgto(vlrInt);
                        break;
                    case 4:
                        Date date = convertStringToDate(valor, cpo);
                        if (!continua) break;
                        if (date.compareTo(dataHoje) < 0) {
                            continua = false;
                            JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + cpo + "] do Registro [" + tipoReg +"] para Data. A Data informada [" + valor + "] é menor que a data de hoje [" + convertDateToString(dataHoje) + "]");
                        }
                        pedImp.setCobr_dataVctoInicial(date);
                        break;
                    case 5:
                        double vlrDouble = convertToDouble(valor, cpo);
                        if (!continua) break;
                        pedImp.setCobr_vlr1aParc(vlrDouble);
                        break;
                    default:
                        break;
                }

            } else 
            if (tipoReg.equals(INFADIC)) {
                switch (cpo) {
                    case 2:
                        if (valor != null && valor.length() > 0) {
                            pedImp.setInfAdic_descricao(valor);
                        } 
                        break;
                    default:
                        break;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Identificador de Registro Inválido: " + tipoReg);
            }
        }
        private String verificaCnpjEmitente() {
            String retorno = null;
            try {
                ResultSet rsEmp = statement.executeQuery("select cnpj from empresa where codigo = " + empresa);
                while (rsEmp.next()) {
                    emp_cnpj = rsEmp.getString("cnpj");
                    retorno = emp_cnpj;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler o Cad.de Empresa. Erro: " + ex);
                continua = false;
            }
            return retorno;
        }
        private boolean existeCliente(String ehTransp) {
            boolean retorno = false;
            String sql = "select * from cliente where cnpj = '" + pedImp.getDest_doc() + "' and ehtransp = '" + ehTransp + "'";
            try {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    String cnpj = rs.getString("cnpj");
                    codCliente = rs.getInt("codigo");
                    retorno = true;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler o Cad.de Cliente. \nComando: " + sql + "\nErro: " + ex);
                continua = false;
            }
            return retorno;
        }
        private boolean verificaProduto(String valor, int item) {
            boolean retorno = false;
            try {
                ResultSet rsTipo = statement.executeQuery("select * from produto where codigo_fornec = '" + valor.toUpperCase() + "'");
                while (rsTipo.next()) {
                    prodImp.setProd_peso(rsTipo.getFloat("peso"));
                    prodImp.setCodigoProduto(rsTipo.getInt("codigo"));
                    retorno = true;
                }
            } catch (SQLException ex) {
                continua = false;
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler o Cad.de Produtos. Erro: " + ex);
            }
            return retorno;
        }
        private void verificaTipoDoc(String valor) {
            String codigo = "";
            try {
                ResultSet rsTipo = statement.executeQuery("select * from tipo_doc where codigo = '" + valor.toUpperCase() + "'");
                while (rsTipo.next()) {
                    codigo = rsTipo.getString("codigo");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler o Cad.de Tipos de Documento. Erro: " + ex);
            }
            if (codigo.equals("")) {
                JOptionPane.showMessageDialog(null, "O Tipo de Documento [" + valor + "] informado não está cadastrado!");
                continua = false;
            }
        }
        private void verificaFormaPgto(String valor) {
            int codigo = 0;
            try {
                ResultSet rsTipo = statement.executeQuery("select * from forma_pgto where codigo = " + Integer.parseInt(valor));
                while (rsTipo.next()) {
                    codigo = rsTipo.getInt("codigo");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler o Cad.de Formas de Pgto. Erro: " + ex);
            }
            if (codigo == 0) {
                JOptionPane.showMessageDialog(null, "A Forma de Pgto [" + valor + "] informado não está cadastrado!");
                continua = false;
            }
        }
        private String verifyString(String valor, int numCpo, int tamanho) {
            String retorno = null;
            int tamVerif = 0;
            if (valor == null) {
                continua = false;
            } else if (valor.length() != tamanho) {
                tamVerif = valor.length();
                continua = false;
            }
            if (!continua) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar verificar tamanho campo num.[" + numCpo + "] do Registro [" + tipoReg +"]. Está com [" + tamVerif + "] e era esperado [" + tamanho + "].");
            } else {
                retorno = valor;
            }
            return retorno;
        }
        private String verifyNotNull(String valor, int numCpo) {
            String retorno = null;
            int tamVerif = 0;
            if (valor == null) {
                continua = false;
            }
            if (!continua) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar verificar tamanho campo num.[" + numCpo + "] do Registro [" + tipoReg +"]. O valor está nulo");
            } else {
                retorno = valor;
            }
            return retorno;
        }
        private int convertToInteger(String valor, int numCpo) {
            int retorno = 0;
            try {
                retorno = Integer.parseInt(valor);
            } catch (Exception e) {
                continua = false;
                JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + numCpo + "] do Registro [" + tipoReg +"] para Numero");
            }
            return retorno;
        }
        private double convertToDouble(String valor, int numCpo) {
            double retorno = 0;
            try {
                retorno = Double.parseDouble(valor);
            } catch (Exception e) {
                continua = false;
                JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + numCpo + "] do Registro [" + tipoReg +"] para Valor");
            }
            return retorno;
        }
        private float convertToFloat(String valor, int numCpo) {
            float retorno = 0;
            try {
                retorno = Float.parseFloat(valor);
            } catch (Exception e) {
                continua = false;
                JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + numCpo + "] do Registro [" + tipoReg +"] para Ponto Flutuante");
            }
            return retorno;
        }
        private Date convertStringToDate(String data, int numCpo) {
            Date retorno = null;
                if (data == null || data.equals(""))
                   return null;
            Date date = null;
            try {
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                date = (java.util.Date)formatter.parse(data);
                System.out.println("\nA data informada no campo [" + tipoReg + "] do reg [" + numCpo + "] é: "+date);
                retorno = date;
            } catch (ParseException e) {            
                continua = false;
                JOptionPane.showMessageDialog(null, "Erro ao tentar converter campo num.[" + numCpo + "] do Registro [" + tipoReg +"] para Data. [" + data + "] Informe a data no formato: aaaammdd");
            }
            return retorno;
        }
        private String convertDateToString(Date date) {
            String retorno = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            retorno = formatter.format(date);
            System.out.println(retorno);
            return retorno;
        }
 	private void gravaPedido() {
            if (continua) {
                if (continua) {
                    if (!existePedido()) {
                        if (gravaTabelaNf_prod() && gravaTabelaNf_prazo() && gravaTabelaNf()) { 
                            JOptionPane.showMessageDialog(null, "Pedido [" + pedImp.getPedido() + "] gravado com sucesso");
                        }
                    }
                }
            }
        }	
        private boolean existePedido() {
            boolean retorno = false;
            Date dataPedido = null;
            String sql = "select * from nf where pedido_cliente = '" + codigoPedido +"'";
            try {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    dataPedido = rs.getDate("data_digitacao");
                    retorno = true;
                }
                if (retorno) {
                    JOptionPane.showMessageDialog(null,"Já existe um Pedido registrado com este codigo [" + codigoPedido + "] na Data: " + dataPedido);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ImportaCsvVenda - Erro ao tentar ler a tabela: nf. Erro: " + ex);
            }
            
           return retorno;
        }
        private boolean gravaTabelaNf_prod() {
           pedImp.setPedido(pegaNumPedido());
           boolean retorno = false;
           String sql_insert = "";
           for (ProdImporta prodImp : listProd) {
               try {
                    sql_insert = "insert into nf_produtos ( "+
                        "empresa, " +
                        "pedido, "+
                        "item, "+
                        "cod_produto, "+
                        "cod_cfop, "+
                        "quantidade, "+
                        "peso, "+
                        "preco_custo, "+
                        "vlr_unitario, "+
                        "vlr_produto, "+
                        "vlr_desconto, "+
                        "vlr_total, "+
                        "icms_bc, "+
                        "icms_perc, "+
                        "icms_pred, "+
                        "icms_vlr, "+
                        "icms_cst, "+
                        "ipi_bc, "+
                        "ipi_perc, "+
                        "ipi_vlr, "+
                        "ipi_cst, "+
                        "pis_bc, "+
                        "pis_perc, "+
                        "pis_vlr, "+
                        "pis_cst, "+
                        "cofins_bc, "+
                        "cofins_perc, "+
                        "cofins_vlr, "+
                        "cofins_cst "+
                        ")"+
                        " values ("+
                         empresa + " , " +
                        pedImp.getPedido() + ", "+
                        prodImp.getProd_item() + ", "+
                        prodImp.getCodigoProduto() + ", "+
                        prodImp.getProd_cfop() + ", "+
                        prodImp.getProd_qtde() + ", "+
                        prodImp.getProd_peso() + ", "+
                        ""+0 + ", "+  //  preco de custo
                        ""+prodImp.getProd_vlrUnitario() + ", "+
                        ""+prodImp.getProd_vlrProduto() + ", "+
                        ""+prodImp.getProd_vlrDesconto() + ", "+
                        ""+prodImp.getProd_vlrTotal() + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        "'"+0 + "', "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        "'"+0 + "', "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        "'"+0 + "', "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        ""+0 + ", "+
                        "'"+0 + "' " +
                        ")";
//JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 retorno = true;
//                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
            }
            catch(SQLException erro)
            {
                retorno = false;
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o produto [" + prodImp.getProd_descricao() + "] do pedido!\nComando: " + sql_insert + "\nErro: "+erro);
                 //JOptionPane.showMessageDialog(null, "Registro já existe na Base de Dados!");
            }
           }
           return retorno;
        }
        private boolean gravaTabelaNf_prazo() {
           boolean retorno = false;
           String sql_insert = "";
                String sql_dup = "select * from Forma_Pgto where codigo = " + pedImp.getCobr_codFormaPgto();
                int nparc = 0, primdia = 0, intervalo = 0;
                try {
                    ResultSet rs_dup = statement.executeQuery(sql_dup);
                          while (rs_dup.next()){
                            nparc = rs_dup.getInt("qtde_parcelas");
                            primdia = rs_dup.getInt("dias_inicial");
                            intervalo = rs_dup.getInt("dias_intervalo");
                          }
                } catch (SQLException ex) {
                      JOptionPane.showMessageDialog(null, "Erro ao tentar ler atabela de formas de pgto! Erro: "+ex);
                }
        
                  int seqdup = 1;
                  double totvlparc = 0.0;
                  double vlparc = 0.00;  //  vlTotNF / nparc;
                  double vp1 = 0.00;
//calcula a data do vencto de cada parcela
                  java.util.Date dataparc = null;
                  String datastr = null;
                  for (int i=0; i<nparc; i++){
                      try {
                          dataparc = Biblioteca.adicionarDias(pedImp.getCobr_dataVctoInicial(), (primdia + (intervalo*i)));
                          //java.sql.Date sqlDate = new java.sql.Date(dataparc.getTime());
                          datastr = DateFormat.getDateInstance().format(dataparc);
                          if (nparc == (i+1))
                              vlparc = Biblioteca.arredondar((pedImp.getTotal_vlrTotal() - totvlparc), 2, 0);
                          else
                              vlparc = Biblioteca.arredondar((pedImp.getTotal_vlrTotal() - totvlparc) / (nparc - i), 2, 1);
                              sql_insert = "insert into nf_prazo (empresa, pedido, parcela, datavcto, vlr_parcela, vlr_pago, datavcto_orig ) values ("
                                  + empresa +", "
                                  + pedImp.getPedido()+", "
                                  +(i+1)+", "
                                  +"'"+dataparc+"', "
                                  +vlparc+", "
                                  +0.00+", "
                                  +"'"+dataparc+"' "
                                  + ")";
                            statement.executeUpdate(sql_insert);
                          seqdup++;
//                          resultparc = resultparc + (i+1)+"a. duplicata com vcto em: "+datastr+" Vlr: "+formataNumDec(""+vlparc,2)+"\n";
                          totvlparc = totvlparc + vlparc;
                          retorno = true;
                          //JOptionPane.showMessageDialog(null, "Gravacao da "+(i+1)+"a. duplicata realizada com sucesso para a data: "+datastr);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao tentar gravar as duplicatas desta NF: "+ex);
                        ex.printStackTrace();
                        retorno = false;
                    }
                  }
           return retorno;
        }
        private boolean gravaTabelaNf() {
           boolean retorno = false;
           String sql_insert = "";
           try {
             sql_insert = "insert into nf ( "
                +"empresa, "
                 +"pedido, "
                 +"cod_cliente, "
                 +"cod_forma_pgto, "
                 +"cod_tipo_doc, "
                 +"cod_banco, "
                 +"data_digitacao, "
                 +"valor_produtos, "
                 +"valor_descontos, "
                 +"valor_total, "
                 +"cod_transportador, "
                 +"dados_adicionais, "
                 +"qtde_volume, "
                 +"peso_volume, "
                 +"placa_veiculo, "
                 +"uf_placa, "
                 +"pedido_cliente, "
                 +"numero_nfe, "
                 +"serie_nfe, "
                 +"modelonfe, ";
            sql_insert += "chave_nfe, "+
                "icms_bc, "+"icms_vlr, "+"ipi_bc, "+"ipi_vlr, "+"pis_bc, "+"pis_vlr, "
                +"cofins_bc, "+"cofins_vlr, num_NFe_fat, fin_NFe "
                +") values ("
                +empresa+", "
                +pedImp.getPedido() + ", "
                +pedImp.getCod_cliente() + ", "
                +pedImp.getCobr_codFormaPgto() + ", "
                +"'"+pedImp.getCobr_codTipoDoc() + "', "
                +0 + ", "  //  codigo do banco
                +"'"+(new Date()) + "', "
                +pedImp.getTotal_vlrProdutos() + ", "
                +pedImp.getTotal_vlrDescontos() + ", "
                +pedImp.getTotal_vlrTotal() + ", "
                +pedImp.getTransp_codTransp() + ", "
                +"'"+pedImp.getInfAdic_descricao() + "', "
                +pedImp.getTransp_qtdeVol() + ", "
                +pedImp.getTransp_pesoVol() + ", "
                +"'"+  "', "  //  placa do veiculo
                +"'"+  "', "  //  uf da placa
                +"'"+codigoPedido + "', "
                +0 + ", "  //  num.NFe
                +"'"+"55" + "', "
                +"'"+pedImp.getIde_modeloNfe() + "', "
                +"'"+ "', "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0 + ", "
                +0+ ", "
                +"'"+"1"+"' "  //  finNFe
                + ")";
                 statement.executeUpdate(sql_insert);
//                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 retorno = true;
            }
            catch(SQLException erro)
            {
                retorno = false;
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o registro na tabela nf!\nComando: " + sql_insert + "\n Erro: "+erro);
//    JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
            }
           return retorno;
        }
        private int pegaNumPedido() {
            int retorno=0;
            try {
                String sql_query = "select max(pedido) as ultCod from nf where empresa = "+empresa;
                ResultSet resultset = statement.executeQuery(sql_query);
                while (resultset.next()) {
                    retorno = resultset.getInt("ultCod");
                }
            } catch(SQLException erro) {

            }
            retorno++;
            return retorno;
        }
        public void insereRegistro()
        {
	//  Inclusao do registro no banco de dados
//        try
//        {
//                    int codigo = 0;
//                    rs_ibge = statement.executeQuery("select * from cfop where cfop = "+reg.getCfop()+"  and seqcfop = "+reg.getSeqcfop());
//                    if (!rs_ibge.first()){
//                        rs_ibge = statement.executeQuery("select max(codigo) as ultCod from cfop");
//                        while (rs_ibge.next()) {
//                            codigo = rs_ibge.getInt("ultCod");
//                        }
//                        codigo++;
//                        String sqlinsert ="insert into cfop ("+
//                            "codigo, "+
//                            "cfop, "+
//                            "descricao, "+
//                            "observacao, "+
//                            "operacao, "+
//                            "faturamento,"+
//                            "financeiro, "+
//                            "seqcfop "+
//                            ") "+
//                            "values ("+
//                             codigo+", "+
//                             reg.getCfop()+", "+
//                             "'"+reg.getDescricao()+"', "+
//                             "'', "+
//                             "'"+reg.getOperacao()+"', "+
//                             true + ", "+
//                             false + ", "+
//                             reg.getSeqcfop() +
//                             ")";
//
//                        System.out.println("Sql: "+sqlinsert);
//                        statement.executeUpdate(sqlinsert);
//                        qtregi = qtregi + 1;
////        		  JOptionPane.showMessageDialog(null,"Gravacao realizado com sucesso!");
//                    }
//                }
//                catch (Exception erro)
//                {
//                    JOptionPane.showMessageDialog(null,"Erro a tentar Gravar o registro com codigo: "+reg.getCodigo()+" - Cfop: "+reg.getCfop()+" - Erro: "+erro);
//                }
//             //}
		
        }
        public void fechaArquivo()
	{
		if (input != null)
			input.close();
	}
	
    public File escolhe_arquivo(String caminho, int tipo_arq, String ext_arq){
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
            }
            JFileChooser fc_escolha   = new JFileChooser(caminho);
            fc_escolha.setDialogTitle("Escolha o Arquivo");
            fc_escolha.setApproveButtonText("Confirma");
            fc_escolha.setFileSelectionMode(tipo_arq);
            if (ext_arq != null) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos "+ext_arq, ext_arq.toLowerCase(), ext_arq.toUpperCase());
                fc_escolha.setFileFilter(filter);
            }
            fc_escolha.setBounds(10, 30, 300,350);
            fc_escolha.setAutoscrolls(true);
            fc_escolha.setMultiSelectionEnabled(false);
            int returnVal;
            returnVal = fc_escolha.showOpenDialog(null);
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
            }
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File f_result = fc_escolha.getSelectedFile();
                return f_result;
            } else {
                return null;
            }
    }
	public static void main( String args[] )
	{
		ImportaCsvVenda iCsvVenda = new ImportaCsvVenda(0, null, null);
		iCsvVenda.importaArquivo();
	}
}