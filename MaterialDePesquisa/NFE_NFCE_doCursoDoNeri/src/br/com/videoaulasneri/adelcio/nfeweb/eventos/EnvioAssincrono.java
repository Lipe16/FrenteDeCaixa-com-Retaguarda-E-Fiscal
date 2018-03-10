/*

Descrição: Transmissão / Autorização do Envio Assincrono de NFe / NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.ConsultaRecibo;
import br.com.videoaulasneri.adelcio.nfeweb.Nfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import br.inf.portalfiscal.nfe.schema.consrecinfe.TConsReciNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TEnviNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TRetEnviNFe;
import br.inf.portalfiscal.nfe.schema.retconsrecinfe.TRetConsReciNFe;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

public class EnvioAssincrono {
    String ufEmissor = "41";
    String caminhoCertificado;  // = "C:\\nfe\\dados\\empr0001\\certificado.pfx";
    String senhaCertificado;  // = "xyxyxyxy";
    String ambiente;
    Estados estado;
    boolean display = true;

    public EnvioAssincrono(String caminhoCertificado, String senhaCertificado, String ambiente, Estados estado) {
        this.caminhoCertificado = caminhoCertificado;
        this.senhaCertificado   = senhaCertificado;
        this.ambiente           = ambiente;
        this.estado             = estado;
    }
    public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
            final String PastaSchemas = "C:\\nfe\\Schemas";
            Certificado certificado = CertificadoUtil.certificadoPfx(caminhoCertificado, senhaCertificado);

            return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
                            certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);

    }

    public String processar(TEnviNFe enviNFe, String tipo) throws NfeException  {
        String retorno = "";
        TRetEnviNFe retornoEnv = Nfe.enviarNfe(enviNFe, ConstantesUtil.NFE);
        if(!retornoEnv.getCStat().equals("103")){
            throw new NfeException("Status:" + retornoEnv.getCStat() + " - Motivo:" + retornoEnv.getXMotivo());
        }

        String recibo = retornoEnv.getInfRec().getNRec();

        TConsReciNFe consReciNFe = new TConsReciNFe();
        consReciNFe.setVersao("3.10");  //(config.getVersaoNfe());
        consReciNFe.setTpAmb(ambiente);  //(config.getAmbiente());
        consReciNFe.setNRec(recibo);

        TRetConsReciNFe retornoNfe = new TRetConsReciNFe();
        while(true){
            retornoNfe = ConsultaRecibo.reciboNfe(consReciNFe, true, tipo); 
            if(retornoNfe.getCStat().equals("105")){
                try {
                    System.out.println("Lote Em Processamento, vai tentar novamente apos 2 Segundo.");
                    Thread.sleep(2000);
                    continue;
                } catch (InterruptedException ex) {
                    Logger.getLogger(EnvioAssincrono.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                break;
            }
        }

        if(!retornoNfe.getCStat().equals("104")){
            throw new NfeException("Status:" + retornoNfe.getCStat() + " - " + retornoNfe.getXMotivo());
        }

        System.out.println("Status: " + retornoNfe.getProtNFe().get(0).getInfProt().getCStat() + " - " + retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo());
        System.out.println("Data: " + retornoNfe.getProtNFe().get(0).getInfProt().getDhRecbto());
        System.out.println("Protocolo: " + retornoNfe.getProtNFe().get(0).getInfProt().getNProt());

        try {
            System.out.println("XML Final: " + XmlUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)));
        } catch (JAXBException ex) {
            Logger.getLogger(EnvioAssincrono.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}