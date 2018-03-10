/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.videoaulasneri.adelcio.nfefacil;

//import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfe_new;
import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfe_new;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Porto
 */
public class Processar_arq_xml_NFe_cron {
    private boolean display = false;
        public void executar(int interval) {
            try {
                String diretorio = System.getProperty("user.dir")+System.getProperty("file.separator")+"xml"+System.getProperty("file.separator")+"nfe";
                String arqOrigem = null;
//                while (true) {
                    gravar_nfe_lido();
                    File file = new File(diretorio);
                    File afile[] = file.listFiles();
                    int i = 0;
                    if (afile.length > 0) {
                        File fxml;
                        for (int j = afile.length; i < j; i++) {
                            fxml = afile[i];
                            System.out.println(fxml.getAbsolutePath());
                        }
                        fxml = afile[0];
                        if (fxml.getAbsolutePath().indexOf(".xml") != -1) {
                            //NFefacil.threadMessage("Encontrou o arquivo [" + fxml.getAbsolutePath() + "] na pasta XML!");
                            NFefacil.arqXML = fxml.getAbsolutePath();
                            NFefacil.getCiNFe().finalizar(false);
                            tratar_geraXML_NFe();
                            fxml.delete();
//                JOptionPane.showMessageDialog(null, "Apagou o arquivo: " + fxml.getAbsolutePath());
                            //NFefacil.setSuspender(false);
                            //NFefacil.setAutorizando(false);
                            NFefacil.getCiNFe().executar(interval, false);
                        } else {
                            System.out.println("Apagando arquivo desconhecido: " + fxml.getAbsolutePath());
                            fxml.delete();
                        }
                    }
//                }
            } catch(Exception e) {
                
            }
        }
        private void gravar_nfe_lido() {
            String sql = "";
            Date dataAtual = new Date();
            java.sql.Timestamp sq = new java.sql.Timestamp(dataAtual.getTime());
             sql = "update nfe_lido set data = '" + sq + "' where id = 1";
            try {
                Statement stat_nfe_lido = NFefacil.confat.createStatement();
                int qtins = stat_nfe_lido.executeUpdate(sql);
                sql = "select * from nfe_lido where id = 1";
                ResultSet rs_lido = stat_nfe_lido.executeQuery(sql);
                while (rs_lido.next()) {
                    sq = rs_lido.getTimestamp("data");
                    String dataSq = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(sq);
                    System.out.println("data nfe_lido: " + dataSq);
                }
            } catch (SQLException ex) {
                    System.out.println("erro ao tratar tabela nfe_lido: " + ex);
            }
        }
    private void tratar_geraXML_NFe() {
        JOptionPane.showMessageDialog(null,"tratar_geraXML_NFe() - Vai gerar XML para o arquivo: " + NFefacil.arqXML);
//        GeraXML_nfe_new gxml = new GeraXML_nfe_new( 
//                NFefacil.getTipoAmbiente(),
//                NFefacil.getStc_camLerAss(),
//                NFefacil.confat,
//                NFefacil.stat_empresa, 
//                NFefacil.getStc_drive(),
//                NFefacil.getStc_anomesdia(),
//                display,  //  display
//                NFefacil.cUF,
//                NFefacil.fuso_horario,
//                NFefacil.arqXML
//                );
//        String tipoEmis = "1";
//        while (true) {
//            if (NFefacil.status_servico) {
//                tipoEmis = "1";
//                break;
//            } else {
//                JOptionPane.showMessageDialog(null, "Serviço Indisponível! Tente Novamente mais tarde!");
//                break;
//            }
//        }
//        String nomeArquivo = gxml.trataTexto("Gerando texto", tipoEmis);
    }
   
}
