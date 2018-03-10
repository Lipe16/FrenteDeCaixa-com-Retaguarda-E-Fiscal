/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.videoaulasneri.adelcio.nfefacil;

import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfce_new;
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
public class Processar_arq_xml_NFCe_cron {
    private boolean display = false;
        public void executar(int interval) {
            try {
                String diretorio = System.getProperty("user.dir")+System.getProperty("file.separator")+"xml"+System.getProperty("file.separator")+"nfce";
                String arqOrigem = null;
//                while (true) {
                    gravar_nfce_lido();
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
                            NFefacil.getCiNFCe().finalizar(false);
                            tratar_geraXML_NFCe();
                            fxml.delete();
//                JOptionPane.showMessageDialog(null, "Apagou o arquivo: " + fxml.getAbsolutePath());
                            //NFefacil.setSuspender(false);
                            //NFefacil.setAutorizando(false);
                            NFefacil.getCiNFCe().executar(interval, false);
                        } else {
                            System.out.println("Apagando arquivo desconhecido: " + fxml.getAbsolutePath());
                            fxml.delete();
                        }
                    }
//                }
            } catch(Exception e) {
                
            }
        }
        private void gravar_nfce_lido() {
            String sql = "";
            Date dataAtual = new Date();
            java.sql.Timestamp sq = new java.sql.Timestamp(dataAtual.getTime());
             sql = "update nfce_lido set data = '" + sq + "' where id = 1";
            try {
                Statement stat_nfce_lido = NFefacil.confat.createStatement();
                int qtins = stat_nfce_lido.executeUpdate(sql);
                sql = "select * from nfce_lido where id = 1";
                ResultSet rs_lido = stat_nfce_lido.executeQuery(sql);
                while (rs_lido.next()) {
                    sq = rs_lido.getTimestamp("data");
                    String dataSq = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(sq);
                    System.out.println("data nfce_lido: " + dataSq);
                }
            } catch (SQLException ex) {
                    System.out.println("erro ao tratar tabela nfce_lido: " + ex);
            }
        }
    private void tratar_geraXML_NFCe() {
        System.out.println("tratar_geraXML_NFCe() - Vai gerar XML para o arquivo: " + NFefacil.arqXML);
        GeraXML_nfce_new gxml = new GeraXML_nfce_new(
                NFefacil.getTipoAmbiente(),
                NFefacil.getStc_camLerAss(),
                NFefacil.confat,
                NFefacil.stat_empresa, 
                NFefacil.getStc_drive(),
                NFefacil.getStc_anomesdia(),
                display,  //  display
                NFefacil.cUF,
                NFefacil.fuso_horario,
                NFefacil.contatoCupom,
                NFefacil.uriCupom,
                NFefacil.arqXML,
                NFefacil.cupomNaTela
                );
        String tipoEmis = "1";
        while (true) {
            if (NFefacil.status_servico) {
                tipoEmis = "1";
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Serviço Indisponível! Emissão em Contingencia!");
//                UIManager.put("OptionPane.okButtonText", "Ok");
//                UIManager.put("OptionPane.cancelButtonText", "Desiste");
//                 if (JOptionPane.showConfirmDialog(null, "Serviço Indisponível! Tenta Novamente? ","Verificação do Serviço", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
//                    NFefacil.status_servico = NFefacil.check_status_servico("");
//                 } else {
                    tipoEmis = "9";
//                    break;
//                 }
            }
        }
        String nomeArquivo = gxml.trataTexto("Gerando texto", tipoEmis);
//        File ffim = new File(NFefacil.arqXML);
//        ffim.delete();
    }
   
}
