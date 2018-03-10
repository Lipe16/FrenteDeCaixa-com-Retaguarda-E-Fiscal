/*

Descrição: Classe Responsável pela Impressão da Nota Fiscal do Consumidor Eletronica - NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

//import Utilitarios.conexao;
import br.com.videoaulasneri.adelcio.nfefacil.NFefacil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import br.com.videoaulasneri.adelcio.utilitarios.XMLReaderUmaTag;
import java.io.File;
import java.io.FileOutputStream;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperManager;

public class ImpressaoNFCe {
    String nomeTab, sqlComando, nomeRelJasper, pastaSistema, fsep = System.getProperty("file.separator");
    int empresa;
    Connection conTab;
    int pedido = 0;
    String qrCode;
    String tipovia;
    double vlr_venda = 0;
    String arq_prot = "";
    String caixa = "cx";
    boolean gerarPDF = false;
    NFefacil nfem2;
    String contatoCupom = "Porto Informatica Ltda - portoinfo@sercomtel.com.br";
    String uriCupom = "http://www.sped.fazenda.pr.gov.br/modules/conteudo/conteudo.php?conteudo=100";
    String cupomNaTela = "N";
    boolean display = false;

    private XMLReaderUmaTag lerxml;

    public ImpressaoNFCe(
            int empresa,
            String nomeRelJasper,
            Connection conTab,
            int pedido,
            String qrCode,
            String tipovia,
            String caixa,
            String arq_prot,
            String contatoCupom,
            String uriCupom,
            boolean gerarPDF,
            String cupomNaTela
            ){
        this.empresa        = empresa;
        this.pastaSistema   = pastaSistema;
        this.nomeRelJasper  = nomeRelJasper;
        this.conTab         = conTab;
        this.pedido         = pedido;
        this.qrCode         = qrCode;
        this.tipovia        = tipovia;
        this.caixa          = caixa;
        this.arq_prot       = arq_prot;
        this.contatoCupom   = contatoCupom;
        this.uriCupom       = uriCupom;
        this.gerarPDF       = gerarPDF;
        this.cupomNaTela    = cupomNaTela;
    }

    public void imprimeRelJasper(){
        String arquivo = "";
        try{
            //String report_dir = pastaSistema+fsep+"Relatorios"+fsep;
            String report_dir = System.getProperty("user.dir")+fsep+"Relatorios"+fsep;
    //JOptionPane.showMessageDialog(null,"report_dir: "+report_dir);
            arquivo = report_dir+nomeRelJasper;
    //JOptionPane.showMessageDialog(null, "Arquivo Jasper: "+arquivo);
if (display) JOptionPane.showMessageDialog(null, "Report_Dir: "+report_dir+" - Arquivo Jasper: "+arquivo+" - pedido: "+pedido);
            char tipoImp = tipoImpressao();
            if (tipoImp != ' ') {
                String emp_razao = "EMPRESA TESTE LTDA",
                        emp_endereco = "Rua do Sotware, 100",
                        emp_cnpj = "01.234.567/0001-89",
                        emp_ie = "ISENTO",
                        emp_cidade = "CURITIBA",
                        emp_uf = "PR",
                        emp_bairro = "Bacacheri",
                        autorizacao = ""
                        ;
                int codcidade = 0;
                String sql_query = "select * from empresa where codempresa = "+this.empresa;
//JOptionPane.showMessageDialog(null, "ImpressaoNFCe.pegarPedido() - Sql_query para a tabela empresa: "+sql_query);
                Statement st_tipo = conTab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_tipo = st_tipo.executeQuery(sql_query);
                while (rs_tipo.next()) {
                    emp_razao = rs_tipo.getString("razao");
                    emp_cnpj = rs_tipo.getString("cnpj");
                    emp_ie = rs_tipo.getString("inscest");
                    emp_bairro = rs_tipo.getString("bairro");
                    emp_endereco = rs_tipo.getString("endereco");
                    String emp_numero = rs_tipo.getString("numero");
                    if (emp_numero != null) {
                        emp_endereco += ", "+emp_numero;
                    }
                    codcidade = rs_tipo.getInt("codcidade");
                }
                sql_query = "select * from ibge where codigo = "+codcidade;
//JOptionPane.showMessageDialog(null, "ImpressaoNFCe.pegarPedido() - Sql_query para a tabela ibge: "+sql_query);
                rs_tipo = st_tipo.executeQuery(sql_query);
                while (rs_tipo.next()) {
                    emp_cidade = rs_tipo.getString("cidade");
                    emp_uf = rs_tipo.getString("uf");
                }
                        //tipoemissao = "VIA CONSUMIDOR";
                double vlr_dinheiro = 0.00;
                double vlr_troco = 0.00;
                double vlr_cartao = 0.00;
                double vlr_outros = 0.00;
                int numnfe = 0;

                sql_query = "select * from pedidos65 where pedido = "+pedido+" and empresa = "+this.empresa;
//JOptionPane.showMessageDialog(null, "ImpressaoNFCe.pegarPedido() - Sql_query para a tabela pedidos65: "+sql_query);
                rs_tipo = st_tipo.executeQuery(sql_query);
                while (rs_tipo.next()) {
                    vlr_venda = rs_tipo.getDouble("valor_total");
                    numnfe = rs_tipo.getInt("numero_nfe");
                }
                sql_query = "select * from tipopgto65 where pedido = "+pedido+" and empresa = "+this.empresa;
//JOptionPane.showMessageDialog(null, "ImpressaoNFCe.pegarPedido() - Sql_query para a tabela tipopgto65: "+sql_query);
                rs_tipo = st_tipo.executeQuery(sql_query);
                while (rs_tipo.next()) {
                    int id_tipo = rs_tipo.getInt("id_tipo_pgto");
                    if (id_tipo == 1) {  //  dinheiro
                        vlr_dinheiro = rs_tipo.getDouble("valor");
                    } else if (id_tipo == 3 || id_tipo == 4) {  //  cartao debito/credito
                        vlr_cartao = rs_tipo.getDouble("valor");
                    } else if (id_tipo == 90) {  //  cartao debito/credito
                        vlr_troco = rs_tipo.getDouble("valor");
                        vlr_dinheiro += vlr_troco;
                    } else {
                        vlr_outros = rs_tipo.getDouble("valor");
                    }
                }
                if (vlr_troco == 0) {
                    vlr_troco = (vlr_dinheiro - vlr_cartao - vlr_outros) - vlr_venda;
                }
//JOptionPane.showMessageDialog(null, "Arquivo de Protocolo: "+arq_prot);

                lerxml = new XMLReaderUmaTag();
                autorizacao = lerxml.trataXML(arq_prot, "infProt", "A", "Id", 1);
                if (autorizacao == null) {
                    lerxml = new XMLReaderUmaTag();
                    autorizacao = lerxml.trataXML(arq_prot, "infProt", "T", "nProt", 1);
                }
if (display) JOptionPane.showMessageDialog(null, "Autorizacao encontrada; "+autorizacao);
                if (autorizacao != null && autorizacao.length() > 0) {
                   //autorizacao = autorizacao.substring(2, autorizacao.length());
                    lerxml = new XMLReaderUmaTag();
                    String dataAut = lerxml.trataXML(arq_prot, "infProt", "T", "dhRecbto", 1);
                    if (dataAut != null) {
                        autorizacao += " "+dataAut;
if (display) JOptionPane.showMessageDialog(null, "Data da Autorizacao encontrada; "+dataAut);
                    }
                } else {
                    autorizacao = "";
                }
                String site = "https://www.sefaz.rs.gov.br/NFE/NFE-COM.aspx";
                if (emp_uf.toUpperCase().equals("PR")) {
                    site = "http://www/sped.fazenda.pr.gov.br/modules/conteudo/conteudo.php?conteudo=100";
                }

                HashMap parameterMap = new HashMap();

                parameterMap.put("empresa", empresa);
                parameterMap.put("pedido", pedido);
                parameterMap.put("qrCode", this.qrCode);
                parameterMap.put("emp_razao", emp_razao);
                parameterMap.put("emp_endereco", emp_endereco);
                parameterMap.put("emp_cnpj", emp_cnpj);
                parameterMap.put("emp_ie", emp_ie);
                parameterMap.put("emp_bairro", emp_bairro);
                parameterMap.put("emp_cidade", emp_cidade);
                parameterMap.put("emp_uf", emp_uf);
                parameterMap.put("autorizacao", autorizacao);
                parameterMap.put("caixa", caixa);
                //parameterMap.put("tipoemissao", tipoemissao);
                parameterMap.put("tipovia", tipovia);
                parameterMap.put("vlr_dinheiro", vlr_dinheiro);
                parameterMap.put("vlr_troco", vlr_troco);
                parameterMap.put("vlr_cartao", vlr_cartao);
                parameterMap.put("vlr_outros", vlr_outros);
                parameterMap.put("site", site);
                parameterMap.put("contatoCupom", contatoCupom);
                parameterMap.put("uriCupom", uriCupom);
if (display) JOptionPane.showMessageDialog(null,
        "map - "
        +"\n pedido: "+parameterMap.get("pedido")
        +"\n emp_razao: "+parameterMap.get("emp_razao")
        +"\n emp_endereco: "+parameterMap.get("emp_endereco")
        +"\n emp_cnpj: "+parameterMap.get("emp_cnpj")
        +"\n emp_ie: "+parameterMap.get("emp_ie")
        +"\n emp_bairro: "+parameterMap.get("emp_bairro")
        +"\n emp_cidade: "+parameterMap.get("emp_cidade")
        +"\n emp_uf: "+parameterMap.get("emp_uf")
        +"\n autorizacao: "+parameterMap.get("autorizacao")
        +"\n caixa: "+parameterMap.get("caixa")
        +"\n tipovia: "+parameterMap.get("tipovia")
        +"\n vlr_dinheiro: "+parameterMap.get("vlr_dinheiro")
        +"\n vlr_troco: "+parameterMap.get("vlr_troco")
        +"\n vlr_cartao: "+parameterMap.get("vlr_cartao")
        +"\n vlr_outros: "+parameterMap.get("vlr_outros")
        +"\n qrCode: "+parameterMap.get("qrCode")
        );
                if (conTab != null) {
if (display) JOptionPane.showMessageDialog(null, "Vai imprimir informando a conexao . . .");
                   JasperPrint jp = JasperFillManager. fillReport(arquivo, parameterMap, conTab);
if (display) JOptionPane.showMessageDialog(null, "Executou JasperPrint . . . ");
                   if (tipoImp == 'V') {
                       if (gerarPDF) {
                            String arqPdf = System.getProperty("user.dir")+fsep+"pdf"+fsep+"nfce"+fsep+"nfce"  + numnfe + ".pdf";
                            File pdf = new File(arqPdf);
                            pdf.createNewFile();
                            FileOutputStream arquivoGerado = new FileOutputStream(pdf);

                            JasperExportManager.exportReportToPdfStream(jp, arquivoGerado);
                            arquivoGerado.flush();
                            arquivoGerado.close();
                       } else {
                            JasperViewer.viewReport(jp, false);
                       }
// visualizar na tela antes de imprimir
                   } else if (tipoImp == 'I') { //imprime direto na impressora
                        JasperPrintManager.printPage(jp, 0, false);
                   } else {
                        JOptionPane.showMessageDialog(null, "Voce Desistiu da Impressão!");
                   }
if (display) JOptionPane.showMessageDialog(null, "Executou ViewReport . . . ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Voce Desistiu da Impressão!");
            }
        } catch (JRException ejr) {
            JOptionPane.showMessageDialog(null, "Erro(1) ao tentar imprimir o relatorio: "+arquivo+"\n. Erro: "+ ejr);
            ejr.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro(2) ao tentar imprimir o relatorio: "+arquivo+"\n. Erro: "+ e);
        }
    }
    private char tipoImpressao(){
        char tipoImp = 'V';
//        UIManager.put("OptionPane.yesButtonText", "Visualizar na Tela");
//        UIManager.put("OptionPane.noButtonText", "Imprimir em Papel");
//        UIManager.put("OptionPane.cancelButtonText", "Desistir");
//        int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Impressão do Relatório", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if ( escolha == JOptionPane.YES_OPTION)
//            tipoImp = 'V';
//        else if ( escolha == JOptionPane.NO_OPTION)
//            tipoImp = 'I';
//        else
//            tipoImp = ' ';
//        UIManager.put("OptionPane.yesButtonText", "Sim");
//        UIManager.put("OptionPane.noButtonText", "Não");
//        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        if (nfem2.getTipoAmbiente().equals("1") && cupomNaTela.equals("N")) {  //  producao
            tipoImp = 'I';
        }
        return tipoImp;
    }
}
