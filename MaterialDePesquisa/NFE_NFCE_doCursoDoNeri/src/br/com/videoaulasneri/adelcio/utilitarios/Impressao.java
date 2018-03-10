/*

Descrição: Clase que Recebe os Parametros e Gera a Impressão das Tabelas (Unidades, Cfop, Forma_pgto, etc)

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

//import Utilitarios.conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

public class Impressao {
    String nomeTab, sqlComando, nomeRelJasper, pastaSistema, fsep = System.getProperty("file.separator");
    String empresa;
    Connection conTab;
    String razaoEmp, enderEmp;
    boolean display = false;

    public Impressao(String empresa, String nomeRelJasper, Connection conTab, String razaoEmpresa, String enderEmpresa){
        this.empresa       = empresa;
        this.pastaSistema  = pastaSistema;
        this.nomeRelJasper = nomeRelJasper;
        this.conTab         = conTab;
        this.razaoEmp = razaoEmpresa;
        this.enderEmp = enderEmpresa;
    }
    public Impressao(String empresa, String nomeTab, String nomeRelJasper, Connection conTab){
        this.nomeTab       = nomeTab;
        this.empresa       = empresa;
        this.pastaSistema  = pastaSistema;
        this.nomeRelJasper = nomeRelJasper;
        this.conTab         = conTab;
    }
    public Impressao(String empresa, String nomeTab, String sqlComando, String nomeRelJasper, Connection conTab){
        this.nomeTab       = nomeTab;
        this.empresa       = empresa;
        this.sqlComando    = sqlComando;
        this.pastaSistema  = pastaSistema;
        this.nomeRelJasper = nomeRelJasper;
        this.conTab         = conTab;
    }

    public void imprimeRelJasper(){
        String arquivo = "";
        try{
        //String report_dir = pastaSistema+fsep+"Relatorios"+fsep;
        String report_dir = System.getProperty("user.dir")+fsep+"Relatorios"+fsep;
if (display) JOptionPane.showMessageDialog(null,"report_dir: "+report_dir);
        arquivo = report_dir+nomeRelJasper;
if (display) JOptionPane.showMessageDialog(null, "Arquivo Jasper: "+arquivo);
if (display) JOptionPane.showMessageDialog(null, "Report_Dir: "+report_dir+" - Arquivo Jasper: "+arquivo);  //+" - empresa: "+empresa+" - nomeTab: "+nomeTab);
        char tipoImp = tipoImpressao();
            HashMap parameterMap = new HashMap();
//            if (empresa != 0) parameterMap.put("empresa", empresa);
//            if (nomeTab != null) parameterMap.put("nomeTab", nomeTab);
//            if (this.dataInicio() != null) parameterMap.put("data_inicio", this.dataInicio(););
//            parameterMap.put("linha", nomeTab);
//            parameterMap.put("SUBREPORT_DIR", report_dir);
//            conexao conn = new conexao();
//            Connection conTab = conn.conecta("", "", nomeBanco);
            parameterMap.put("empresa", razaoEmp);
            parameterMap.put("endereco", enderEmp);
            parameterMap.put("pedido", 140);
            if (conTab != null) {
                if (sqlComando != null){
                    //ResultSet rs = Conexao.getConn().createStatement().executeQuery(sqlComando);
                    ResultSet rs = null;
                    try {
                        rs = conTab.createStatement().executeQuery(sqlComando);
                        //while (rs.next()){
                        //    System.out.println("Func: "+rs.getString("codfunc")+"Datamvto: "+rs.getString("datamvto"));
                        //}
if (display) JOptionPane.showMessageDialog(null,"Impressao: Passou 1 . . .");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao tentar executar a Consulta ao Banco de Dados!\nErro: "+ex);
                    }
     
                    if ( rs != null){
                        try {
                            String jp = JasperFillManager.fillReportToFile(arquivo, parameterMap, new JRResultSetDataSource(rs));
                            //JasperPrint jp = JasperFillManager.fillReport(arquivo, parameterMap, new JRResultSetDataSource(rs));
if (display) JOptionPane.showMessageDialog(null,"Impressao: Passou 2 . . .");
                            if (tipoImp == 'V') // visualizar na tela antes de imprimir
                                JasperViewer.viewReport(jp, false);
                            else if (tipoImp == 'I')  //imprime direto na impressora
                                JasperPrintManager.printPage(jp, 0, false);
                            else
                                JOptionPane.showMessageDialog(null, "Voce Desistiu da Impressão!");
                        } catch(Exception ejr){
                            ejr.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erro ao tentar imprimir o mvto ponto: "+ejr);
                        }
                    }
                } else {
if (display) JOptionPane.showMessageDialog(null, "Vai imprimir informando a conexao . . .");
                    JasperPrint jp = JasperFillManager.fillReport(arquivo, parameterMap, conTab);
if (display) JOptionPane.showMessageDialog(null, "Executou JasperPrint . . . ");
               if (tipoImp == 'V') // visualizar na tela antes de imprimir
                        JasperViewer.viewReport(jp, false);
                    else if (tipoImp == 'I')  //imprime direto na impressora
                        JasperPrintManager.printPage(jp, 0, false);
                    else
                        JOptionPane.showMessageDialog(null, "Voce Desistiu da Impressão!");
                }
if (display) JOptionPane.showMessageDialog(null, "Executou ViewReport . . . ");
//                conTab.desconecta();
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
        UIManager.put("OptionPane.yesButtonText", "Visualizar na Tela");
        UIManager.put("OptionPane.noButtonText", "Imprimir em Papel");
        int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Impressão do Relatório", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if ( escolha == JOptionPane.YES_OPTION)
            tipoImp = 'V';
        else if ( escolha == JOptionPane.NO_OPTION)
            tipoImp = 'I';
        else
            tipoImp = ' ';
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        return tipoImp;
    }
}
