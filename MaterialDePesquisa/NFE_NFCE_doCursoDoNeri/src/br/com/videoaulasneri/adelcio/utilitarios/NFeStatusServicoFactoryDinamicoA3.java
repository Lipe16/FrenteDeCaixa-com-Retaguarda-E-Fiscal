/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.utilitarios;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.ksFixo;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.pr_token;
import br.inf.portalfiscal.www.nfe.wsdl.NfeStatusServico2.NfeStatusServico2Stub;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;

//import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub;
import java.util.Enumeration;
import javax.swing.JOptionPane;

public class NFeStatusServicoFactoryDinamicoA3 {
	private static final int SSL_PORT = 443;
        boolean display = false;
public NFeStatusServicoFactoryDinamicoA3() {

    System.setProperty("javax.net.debug", "all");

//JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Iniciou . . .");            
    try {
        String codigoDoEstado = informarUF();  //41 = PR, 43 = RS
        if (codigoDoEstado !=null && !codigoDoEstado.equals("")) {
            String textoParaURL = pegaUrl(codigoDoEstado);
            if (textoParaURL != null && !textoParaURL.equals("")) {
                URL url = new URL(textoParaURL); 
if (display) JOptionPane.showMessageDialog(null, "URL do Estado ["+codigoDoEstado+"] = ["+textoParaURL+"]");            
                String senhaDoCertificado = informarSenha();  
                if (senhaDoCertificado !=null && !senhaDoCertificado.equals("")) {

                    /**
                     * Informações do Certificado Digital A3.
                     */
                    String configName = "c:/nfe/token.cfg";
                    String arquivoCacertsGeradoTodosOsEstados = "c:/nfe/NFeCacerts";  //"c:\\nfe\\dados\\empr0001\\certificado.jks";  //"c:\\nfe\\certnovo.jks";   //
if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Arq.Config.: "+configName);  //+"  - senha: "+senhaDoCertificado);            
                    if (ksFixo == null) {
                        if (pr_token == null) {
                            pr_token = new sun.security.pkcs11.SunPKCS11(configName);
if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou Provider . . .");            
                            Security.addProvider(pr_token);
                        }
                        char[] pin = senhaDoCertificado.toCharArray();
                        ksFixo = KeyStore.getInstance("pkcs11", pr_token);
                        ksFixo.load(null, pin);
                    }

                        /**
                         * Resolve o problema do 403.7 Forbidden para Certificados A3 e A1 
                         * e elimina o uso das cofigurações:
                         * - System.setProperty("javax.net.ssl.keyStore", "NONE");
                         * - System.setProperty("javax.net.ssl.keyStoreType", "PKCS11");
                         * - System.setProperty("javax.net.ssl.keyStoreProvider", "SunPKCS11-SmartCard");
                         * - System.setProperty("javax.net.ssl.trustStoreType", "JKS");
                         * - System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoTodosOsEstados);
                         */
if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou Keystore . . .");            
                    String alias = "";
                    Enumeration<String> aliasesEnum = ksFixo.aliases();
                        while (aliasesEnum.hasMoreElements()) {
                                alias = (String) aliasesEnum.nextElement();
                                if (ksFixo.isKeyEntry(alias)) break;
                        }
                        X509Certificate certificate = (X509Certificate) ksFixo.getCertificate(alias);
                        PrivateKey privateKey = (PrivateKey) ksFixo.getKey(alias, "changeit".toCharArray());
                        SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
                        socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);

                        if (ksFixo.containsAlias(alias)) {
                            info("Certificado de: "+alias);
                        }

                    Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);  
                    Protocol.registerProtocol("https", protocol);    		

if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou Protocol . . .");            
                        /**
                     * Xml de Consulta.
                     */
                    StringBuilder xml = new StringBuilder();
                    xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                        .append("<consStatServ versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                        .append("<tpAmb>2</tpAmb>")
                        .append("<cUF>")
                        .append(codigoDoEstado)
                        .append("</cUF>")
                        .append("<xServ>STATUS</xServ>")
                        .append("</consStatServ>");

                    OMElement ome = AXIOMUtil.stringToOM(xml.toString());
        /*
        <consStatServ xmlns="http://www.portalfiscal.inf.br/nfe" versao="3.10"><tpAmb>2</tpAmb><cUF>41</cUF><xServ>STATUS</xServ></consStatServ>
        */            

                    NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();
                    dadosMsg.setExtraElement(ome);

                    NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();
                    /**
                     * Código do Estado.
                     */
                    nfeCabecMsg.setCUF(codigoDoEstado);

                    /**
                     * Versao do XML
                     */
                    nfeCabecMsg.setVersaoDados("3.10");
                    NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();
                    nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou nfeCabecMsg . . .");            
        System.out.println("xml da consulta de status com cartao: "+AXIOMUtil.stringToOM(xml.toString()));
        System.out.println("url da consulta de status com cartao: "+url.toString());

                    NfeStatusServico2Stub stub = new NfeStatusServico2Stub(url.toString());
if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou stub(1) . . .");            
                    NfeStatusServico2Stub.NfeStatusServicoNF2Result result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);

if (display) JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Passou stub(2) . . .");            
                    info(result.getExtraElement().toString());
                    JOptionPane.showMessageDialog(null, "Resultado da Consulta: "+result.getExtraElement().toString()); 
                } else {
                    JOptionPane.showMessageDialog(null, "Senha do Certificado do Cartão/Token informada inválida! ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "URL inválida para o Código do Estado Informado: "+codigoDoEstado);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Código do Estado informado inválido!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "NFeStatusServicoFactoryDinamicoA3 - Erro: "+e); 
        error(e.toString());
    }
	}

	/**
	 * Log Info.
	 * @param log
	 */
	private static void info(String log) {
		System.out.println("INFO: " + log);
	}

	/**
	 * Log Error.
	 * @param log
	 */
	private static void error(String log) {
		System.out.println("ERROR: " + log);
	}
	public static void main(String[] args) {
        
        }

        private String informarUF() {
            String retorno = "";
            String cUF = "";
            try {
                while (true){
                    cUF = JOptionPane.showInputDialog("Informe o Codigoo da UF para Verificar o Status do Serviço(Ex: PR, RS, GO, AM, Etc)", cUF);
                    if (cUF != null) {
                        cUF = cUF.toUpperCase();
                        try {
                            if (cUF != null && cUF.length() == 2) {
                                switch( cUF )
                               {
                                   case "RO":
                                           retorno = "11";  //Estados.RO;
                                           break;
                                   case "AC":
                                           retorno = "12";  //Estados.AC;
                                           break;
                                   case "AM":
                                           retorno = "13";  //Estados.AM;
                                           break;
                                   case "RR":
                                           retorno = "14";  //Estados.RR;
                                           break;
                                   case "PA":
                                           retorno = "15";  //Estados.PA;
                                           break;
                                   case "AP":
                                           retorno = "16";  //Estados.AP;
                                           break;
                                   case "TO":
                                           retorno = "17";  //Estados.TO;
                                           break;
                                   case "MA":
                                           retorno = "21";  //Estados.MA;
                                           break;
                                   case "PI":
                                           retorno = "22";  //Estados.PI;
                                           break;
                                   case "CE":
                                           retorno = "23";  //Estados.CE;
                                           break;
                                   case "RN":
                                           retorno = "24";  //Estados.RN;
                                           break;
                                   case "PB":
                                           retorno = "25";  //Estados.PB;
                                           break;
                                   case "PE":
                                           retorno = "26";  //Estados.PE;
                                           break;
                                   case "AL":
                                           retorno = "27";  //Estados.AL;
                                           break;
                                   case "SE":
                                           retorno = "28";  //Estados.SE;
                                           break;
                                   case "BA":
                                           retorno = "29";  //Estados.BA;
                                           break;
                                   case "MG":
                                           retorno = "31";  //Estados.MG;
                                           break;
                                   case "ES":
                                           retorno = "32";  //Estados.ES;
                                           break;
                                   case "RJ":
                                           retorno = "33";  //Estados.RJ;
                                           break;
                                   case "SP":
                                           retorno = "35";  //Estados.SP;
                                           break;
                                   case "PR":
                                           retorno = "41";  //Estados.PR;
                                           break;
                                   case "SC":
                                           retorno = "42";  //Estados.SC;
                                           break;
                                   case "RS":
                                           retorno = "43";  //Estados.RS;
                                           break;
                                   case "MS":
                                           retorno = "50";  //Estados.MS;
                                           break;
                                   case "MT":
                                           retorno = "51";  //Estados.MT;
                                           break;
                                   case "GO":
                                           retorno = "52";  //Estados.GO;
                                           break;
                                   case "DF":
                                           retorno = "53";  //Estados.DF;
                                           break;
                                   default:
                                           retorno = null;
                               }
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Código inválido! Informe a Sigla de Um Estado Brasileiro . . .");
                            }
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(null, "Código inválido! Informe a Sigla de Um Estado Brasileiro . . .");
                        }
                     } else {
                        break;
                    }
               }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar informar o Codigo Numerico do Estado. Erro: "+e);
            }
            return retorno;
        }
        
        private String informarSenha() {
            String retorno = "";
            try {
                while (true){
                    retorno = JOptionPane.showInputDialog("Informe a Senha do Certificado do Cartão/Token:", retorno);
                    break;
                }
            } catch(Exception e) {
                
            }
            return retorno;
        }
        
        private String pegaUrl(String cUF) {
            String retorno = "";
        switch( cUF )
       {
           case "11":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "12":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "13":
                   retorno = "https://homnfe.sefaz.am.gov.br/services2/services/NfeStatusServico2";
                   break;
           case "14":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "15":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "16":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "17":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "21":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "22":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "23":
                   retorno = "https://nfeh.sefaz.ce.gov.br/nfe2/services/NfeStatusServico2?wsdl";
                   break;
           case "24":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "25":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "26":
                   retorno = "https://nfce-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
//                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "27":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "28":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "29":
                   retorno = "https://hnfe.sefaz.ba.gov.br/webservices/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "31":
                   retorno = "https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeStatusServico2";
                   break;
           case "32":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "33":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "35":
                   retorno = "https://homologacao.nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx";
                   break;
           case "41":
                   retorno = "https://homologacao.nfe.fazenda.pr.gov.br/nfe/NFeStatusServico3?wsdl";
                   break;
           case "42":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           case "43":  //  
                   //retorno = "https://hom.svc.fazenda.gov.br/NfeStatusServico2/NfeStatusServico2.asmx";
                   retorno = "https://hom.sefazvirtual.fazenda.gov.br/NfeStatusServico2/NfeStatusServico2.asmx";
                   break;
           case "50":
                   retorno = "https://homologacao.nfe.ms.gov.br/homologacao/services2/NfeStatusServico2";
                   break;
           case "51":
                   retorno = "https://homologacao.sefaz.mt.gov.br/nfews/v2/services/NfeStatusServico2?wsdl";
                   break;
           case "52":
                   retorno = "https://homolog.sefaz.go.gov.br/nfe/services/v2/NfeStatusServico2?wsdl";
                   break;
           case "53":
                   retorno = "https://nfe-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                   break;
           default:
                   retorno = null;
       }
            return retorno;
        }
}