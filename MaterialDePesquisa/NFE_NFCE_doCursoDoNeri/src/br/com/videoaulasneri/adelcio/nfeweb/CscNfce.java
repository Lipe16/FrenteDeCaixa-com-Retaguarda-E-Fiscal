/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

import java.rmi.RemoteException;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.WebServiceUtil;
import br.inf.portalfiscal.www.nfe.wsdl.CscNFCe.CscNFCeStub;


/**
 * Classe responsavel por trazer o Codigo Csc Do Cliente
 * 
 */
public class CscNfce {

	static CscNFCeStub.CscNFCeResult result;
	private static ConfiguracoesIniciaisNfe configuracoesNfe;
	private static CertificadoUtil certUtil;

	public static String consultaCSC() throws NfeException {
		
		certUtil = new CertificadoUtil();
		configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();

		try {

			/**
			 * Informacoes do Certificado Digital.
			 */
			certUtil.iniciaConfiguracoes();

			/**
			 * Xml de Consulta.
			 */
			StringBuilder xml = new StringBuilder();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			   .append("<consStatServ versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
			   .append("<tpAmb>" + configuracoesNfe.getAmbiente() + "</tpAmb>")
               .append("<cUF>" + configuracoesNfe.getEstado().getCodigoIbge() +"</cUF>")
               .append("<xServ>STATUS</xServ>")
               .append("</consStatServ>");

			OMElement ome = AXIOMUtil.stringToOM(xml.toString());
			CscNFCeStub.NfeDadosMsg dadosMsg = new CscNFCeStub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);

			CscNFCeStub.NfeCabecMsg nfeCabecMsg = new CscNFCeStub.NfeCabecMsg();
			/**
			 * Codigo do Estado.
			 */
			nfeCabecMsg.setCUF(String.valueOf(configuracoesNfe.getEstado().getCodigoIbge()));

			/**
			 * Versao do XML
			 */
			
			nfeCabecMsg.setVersaoDados("1.00");
			CscNFCeStub.NfeCabecMsgE nfeCabecMsgE = new CscNFCeStub.NfeCabecMsgE();
			nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

			CscNFCeStub stub = new CscNFCeStub( WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.CSC));
			result = stub.admCscNFCe(dadosMsg, nfeCabecMsgE);
		
		} catch (RemoteException | XMLStreamException e) {
			throw new NfeException(e.getMessage());
		}
		
		return result.getExtraElement().toString();
	}

}