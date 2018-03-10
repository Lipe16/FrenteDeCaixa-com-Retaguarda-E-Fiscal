/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.certUtil;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ObjetoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.WebServiceUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import static br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfce.normalizeXML;
import br.inf.portalfiscal.nfe.schema.inutnfe.TInutNFe;
import br.inf.portalfiscal.nfe.schema.retinutnfe.TRetInutNFe;
import br.inf.portalfiscal.www.nfe.wsdl.NfeInutilizacao2.NfeInutilizacao2Stub;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

/**
 * Classe Responsavel por inutilizar uma Faixa de numeracao da Nfe.
 * 
 * 
 */
public class Inutilizar {

	public static NfeInutilizacao2Stub.NfeInutilizacaoNF2Result result;
	private static ConfiguracoesIniciaisNfe configuracoesNfe;
	//private static CertificadoUtil certUtil;
        private static boolean display = false;
	
	public static TRetInutNFe inutiliza(TInutNFe inutNFe, boolean valida, String tipo) throws NfeException {
                /**
                 * Informacoes do Certificado Digital.
                 */

		if (certUtil == null) {
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - certUtil.iniciaConfiguracoes() . . ."); 
                    certUtil = new CertificadoUtil();
			certUtil.iniciaConfiguracoes();
                }
		configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();
		boolean nfce = tipo.equals(ConstantesUtil.NFCE);
		
		try {

			String xml = XmlUtil.objectToXml(inutNFe);
System.out.println("Inutilizar - xml para assinar: ["+xml+"]"); 
			xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
			
			/**
			 * Assina o Xml
			 */
			xml = Assinar.assinaNfe(xml, Assinar.INFINUT);
			
			if(valida){
				String erros = Validar.validaXml(xml, Validar.INUTILIZACAO);
				
				if(!ObjetoUtil.isEmpty(erros)){
					throw new NfeException("Erro Na Validacao do Xml: "+erros);
				}
			}
			
			System.out.println("Xml Inutilizar: "+xml);
                        gravarXML(xml);

			OMElement ome = AXIOMUtil.stringToOM(xml);
			NfeInutilizacao2Stub.NfeDadosMsg dadosMsg = new NfeInutilizacao2Stub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 1 . . ."); 

			NfeInutilizacao2Stub.NfeCabecMsg nfeCabecMsg = new NfeInutilizacao2Stub.NfeCabecMsg();
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 2 . . ."); 
			
			/**
			 * Codigo do Estado.
			 */
			nfeCabecMsg.setCUF(String.valueOf(configuracoesNfe.getEstado().getCodigoIbge()));
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 3 . . ."); 

			/**
			 * Versao do XML
			 */
			nfeCabecMsg.setVersaoDados(configuracoesNfe.getVersaoNfe());
			NfeInutilizacao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeInutilizacao2Stub.NfeCabecMsgE();
			nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 4 . . ."); 
                        String url = nfce ? WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.INUTILIZACAO) : WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.INUTILIZACAO);
                        //url = url.substring(0, url.indexOf("?"));
                        System.out.println("Inutilizar - URL: "+url);
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 5 . . . url: "+url); 
			NfeInutilizacao2Stub stub = new NfeInutilizacao2Stub(url);
			result = stub.nfeInutilizacaoNF2(dadosMsg, nfeCabecMsgE);
if (display) JOptionPane.showMessageDialog(null, "Inutilizar - Passou 6 . . ."); 

			return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetInutNFe.class);
		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
		}
		
	}
        static void gravarXML(String texto) {
            String txtForm = formataTexto(texto);
            try {
             byte [] textobyte = txtForm.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream("c:\\nfe\\arqAssinado.xml");
                arquivo_gerado.write(textobyte);
                arquivo_gerado.close();
        
                //JOptionPane.showMessageDialog(null, "Caminho do Arquivo Assinado: "+arqAssinado);
            } catch (Exception io) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo assinado: "+io);
            }
        }
        static String formataTexto(String txt){
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
//if (display) JOptionPane.showMessageDialog(null,"\n\nTexto fim: "+txt);
            return retorno;
        }

}