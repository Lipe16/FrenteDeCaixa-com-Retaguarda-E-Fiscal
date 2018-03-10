/*

Descrição: Transmissão / Autorização da Carta de Correção

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.Nfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import static br.com.videoaulasneri.adelcio.utilitarios.Biblioteca.normalizeXML;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

public class CartaCorrecao {
    String caminhoCertificado;  // = "C:\\nfe\\dados\\empr0001\\certificado.pfx";
    String senhaCertificado;  // = "xyxyxyxy";
    String ambiente;
    Estados estado;
    boolean display = true;

    public CartaCorrecao(String caminhoCertificado, String senhaCertificado, String ambiente, Estados estado) {
        this.caminhoCertificado = caminhoCertificado;
        this.senhaCertificado   = senhaCertificado;
        this.ambiente           = ambiente;
        this.estado             = estado;
        
    }
    public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
            final String PastaSchemas = "C:\\nfe\\Schemas";
                Certificado certificado = null;
                if (caminhoCertificado.indexOf("token.cfg") == -1) {  //  o certificado é do tipoA1
                    certificado = CertificadoUtil.certificadoPfx(caminhoCertificado, senhaCertificado);
                } else {  //  o certificado é do tipoA# (Cartão)
                    certificado = CertificadoUtil.certificadoA3(caminhoCertificado, senhaCertificado);
                }

            return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
                            certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);

    }

        
    public String processar(
            String ufEmissor, 
            String cnpjEmissor,
            String chave,
            String correcao,
            String pastaCorrecao,
            String pastaRetcorr,
            String fuso_horario
        ) {
        
            String retorno = "erro";
            try{
                //Inicia As Configurações
                ConfiguracoesIniciaisNfe config = iniciaConfigurações();

                //Substitua os X pelo Chave
                //String chave = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
                String id = "ID110110"+chave+"01";

                br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento envEvento = new br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento();
                envEvento.setVersao("1.00");
                envEvento.setIdLote("1");

                br.inf.portalfiscal.nfe.schema.envcce.TEvento evento = new br.inf.portalfiscal.nfe.schema.envcce.TEvento();
                evento.setVersao("1.00");

                br.inf.portalfiscal.nfe.schema.envcce.TEvento.InfEvento infEvento = new br.inf.portalfiscal.nfe.schema.envcce.TEvento.InfEvento();
                infEvento.setId(id);
                infEvento.setCOrgao(ufEmissor);
                infEvento.setTpAmb(config.getAmbiente());
                //Substitua os X pelo CNPJ
                infEvento.setCNPJ(cnpjEmissor);
                infEvento.setChNFe(chave);

                Date data = new Date();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String dataHoraGMT = sdf.format(data) + fuso_horario;

                infEvento.setDhEvento(dataHoraGMT);  //("2016-09-22T16:00:03-03:00");
                infEvento.setTpEvento("110110");
                infEvento.setNSeqEvento("1");
                infEvento.setVerEvento("1.00");

                br.inf.portalfiscal.nfe.schema.envcce.TEvento.InfEvento.DetEvento detEvento = new br.inf.portalfiscal.nfe.schema.envcce.TEvento.InfEvento.DetEvento();
                detEvento.setVersao("1.00");
                detEvento.setDescEvento("Carta de Correcao");

                //Informe a Correção
                detEvento.setXCorrecao(correcao);
                detEvento.setXCondUso("A Carta de Correcao e disciplinada pelo paragrafo 1o-A do art. 7o do Convenio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularizacao de erro ocorrido na emissao de documento fiscal, desde que o erro nao esteja relacionado com: I - as variaveis que determinam o valor do imposto tais como: base de calculo, aliquota, diferenca de preco, quantidade, valor da operacao ou da prestacao; II - a correcao de dados cadastrais que implique mudanca do remetente ou do destinatario; III - a data de emissao ou de saida.");
                infEvento.setDetEvento(detEvento);
                evento.setInfEvento(infEvento);
                envEvento.getEvento().add(evento);
                System.out.println("xml da carta de correcao: "+XmlUtil.objectToXml(envEvento));
                String arqCorrecao = pastaCorrecao + "//" + chave + ".xml";
                gravarXML(XmlUtil.objectToXml(envEvento), arqCorrecao);
                
                //Informe false para não fazer a validação do XML - Ganho De tempo.
                br.inf.portalfiscal.nfe.schema.envcce.TRetEnvEvento retornoCorr =  Nfe.cce(envEvento,true,  ConstantesUtil.NFE);
                System.out.println("Status:" + retornoCorr.getRetEvento().get(0).getInfEvento().getCStat());
                System.out.println("Motivo:" + retornoCorr.getRetEvento().get(0).getInfEvento().getXMotivo());
                System.out.println("Data:" + retornoCorr.getRetEvento().get(0).getInfEvento().getDhRegEvento());
            retorno = retornoCorr.getRetEvento().get(0).getInfEvento().getCStat()+"-"+retornoCorr.getRetEvento().get(0).getInfEvento().getXMotivo();

JOptionPane.showMessageDialog(null, "Status:" + retornoCorr.getRetEvento().get(0).getInfEvento().getCStat() 
        + "\nMotivo:" + retornoCorr.getRetEvento().get(0).getInfEvento().getXMotivo() 
        + "\nData:" + retornoCorr.getRetEvento().get(0).getInfEvento().getDhRegEvento()
    );


                //Criação do ProcEventoNFe
                br.inf.portalfiscal.nfe.schema.envcce.TProcEvento procEvento = new br.inf.portalfiscal.nfe.schema.envcce.TProcEvento();
                procEvento.setEvento(evento);
                procEvento.setRetEvento(retornoCorr.getRetEvento().get(0));
                procEvento.setVersao("1.00");

                String xmlProcEventoNfe = XmlUtil.objectToXml(procEvento);
                String arqRetcorr = pastaRetcorr + "//" + chave + ".xml";
                System.out.println(xmlProcEventoNfe);
                gravarXML(xmlProcEventoNfe, arqRetcorr);

            } catch (NfeException | JAXBException e) {
            System.out.println("Erro:" + e.getMessage());
        }
        return retorno;
    }
        void gravarXML(String texto, String nomeArq) {
            String txtForm = formataTexto(texto);
            try {
             byte [] textobyte = txtForm.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(nomeArq);
                arquivo_gerado.write(textobyte);
                arquivo_gerado.close();
        
                //JOptionPane.showMessageDialog(null, "Caminho do Arquivo Assinado: "+arqAssinado);
            } catch (Exception io) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo de erro: "+io);
            }
        }
        String formataTexto(String txt){
            String retorno = "";
             txt = txt.replaceAll("&#xd;", "");
             //System.out.println("\n\nTexto fim: "+txt);
             normalizeXML(txt);
            while (txt.indexOf(" <") != -1){
                    txt = txt.replaceAll(" <", "<");
            }
            while (txt.indexOf("\n") != -1){
                    txt = txt.replaceAll("\n", "");
            }
            while (txt.indexOf("\r") != -1){
                    txt = txt.replaceAll("\r", "");
            }
            retorno = txt;
            return retorno;
        }
}