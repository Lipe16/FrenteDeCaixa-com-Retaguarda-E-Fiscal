package br.com.videoaulasneri.adelcio.nfeweb;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.certUtil;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ObjetoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.WebServiceUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import br.inf.portalfiscal.nfe.schema.consCad.TConsCad;
import br.inf.portalfiscal.nfe.schema.retConsCad.TRetConsCad;
import br.inf.portalfiscal.www.nfe.wsdl.CadConsultaCadastro2.CadConsultaCadastro2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.CadConsultaCadastro2.CadConsultaCadastro2Stub.CadConsultaCadastro2Result;
import javax.swing.JOptionPane;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
//import br.com.videoaulasneri.adelcio.

/**
 * Classe responsavel por Consultar a Situaçao do XML na SEFAZ.
 * 
 * @author Samuel Oliveira - samuk.exe@hotmail.com - www.samuelweb.com.br
 * 
 */

public class ConsultaCadastro {

	private static CadConsultaCadastro2Result result;
	private static ConfiguracoesIniciaisNfe configuracoesNfe;
	private static CertificadoUtil certUtil;
        private static boolean display = false;
        private Estados estado;

	/**
	 * Classe Reponsavel Por Consultar o status da NFE na SEFAZ
	 * 
	 * @param Chave
	 * @return Resposta da Sefaz
	 * @throws NfeException
	 */
	public static TRetConsCad consultaCadastro(TConsCad consCad, boolean valida, String estado) throws NfeException {
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - entro no metodo . . .");		
//
//		certUtil = new CertificadoUtil();
		if (certUtil == null) {
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - certUtil.iniciaConfiguracoes() . . ."); 
                    certUtil = new CertificadoUtil();
			certUtil.iniciaConfiguracoes();
                }
		configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();

		try {

//			certUtil.iniciaConfiguracoes();

			String xml = XmlUtil.objectToXml(consCad);

			if (valida) {
				String erros = Validar.validaXml(xml, Validar.CONSULTA_CADASTRO); 
				if (!ObjetoUtil.isEmpty(erros)) {
					throw new NfeException("Erro Na Validacao do Xml: " + erros);
				}
			}
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - passou valida . . .");		

			System.out.println("Xml Consulta: " + xml);
			OMElement ome = AXIOMUtil.stringToOM(xml);

			CadConsultaCadastro2Stub.NfeDadosMsg dadosMsg = new CadConsultaCadastro2Stub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - passou 1 . . .");		

			CadConsultaCadastro2Stub.NfeCabecMsg nfeCabecMsg = new CadConsultaCadastro2Stub.NfeCabecMsg();
//			nfeCabecMsg.setCUF(String.valueOf(configuracoesNfe.getEstado().getCodigoIbge()));
			nfeCabecMsg.setCUF(String.valueOf(Estados.valueOf(estado).getCodigoIbge()));
			nfeCabecMsg.setVersaoDados("2.00");
if (display) 
    JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - passou 2 . . . estado: " + String.valueOf(Estados.valueOf(estado).getCodigoIbge()));		

			CadConsultaCadastro2Stub.NfeCabecMsgE nfeCabecMsgE = new CadConsultaCadastro2Stub.NfeCabecMsgE();
			nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
                        String url = WebServiceUtil.getUrlConsulta(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.CONSULTA_CADASTRO, estado);
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - passou 3 . . . url: " + url);		

			CadConsultaCadastro2Stub stub = new CadConsultaCadastro2Stub(url);
			result = stub.cadConsultaCadastro2(dadosMsg, nfeCabecMsgE);
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro.consultaCadastro() - passou 4 . . .");		
			
			return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsCad.class);

		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
		}

	}

}
