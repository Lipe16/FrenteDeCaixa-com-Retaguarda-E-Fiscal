/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

import java.rmi.RemoteException;
import java.util.Iterator;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.util.AXIOMUtil;

import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ObjetoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.WebServiceUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import br.inf.portalfiscal.nfe.schema.envinfe.TEnviNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TRetEnviNFe;
import br.inf.portalfiscal.www.nfe.wsdl.NfeAutorizacao.NfeAutorizacaoStub;
import br.inf.portalfiscal.www.nfe.wsdl.NfeAutorizacao.NfeAutorizacaoStub.NfeAutorizacaoLoteResult;
import javax.swing.JOptionPane;

/**
 * Classe Responsavel por Enviar o XML.
 * 
 */
public class Enviar {

	private static NfeAutorizacaoLoteResult result;
	private static ConfiguracoesIniciaisNfe configuracoesNfe;
	private static CertificadoUtil certUtil;
        static boolean display = false;

	/**
	 * Metodo para Montar a NFE..
	 * 
	 * @param TEnviNFe
	 * @return String
	 * @throws NfeException
	 */
	public TEnviNFe montaNfe(TEnviNFe enviNFe, boolean valida) throws NfeException {

		certUtil = new CertificadoUtil();

		try {

			/**
			 * Informacoes do Certificado Digital.
			 */
			certUtil.iniciaConfiguracoes();

			/**
			 * Cria o xml
			 */
			String xml = XmlUtil.objectToXml(enviNFe);

			/**
			 * Assina o Xml
			 */
			xml = Assinar.assinaNfe(xml, Assinar.NFE);

			/**
			 * Valida o Xml caso sejÃ¡ selecionado True
			 */
			if(valida){
				String erros = Validar.validaXml(xml, Validar.ENVIO);
				if (!ObjetoUtil.isEmpty(erros)) {
                                        JOptionPane.showMessageDialog(null, "Houve erros durante a validacao!\n\nAbra o arquivo xml (da pasta //nfe//dados//emprxxxx//assinar) em um \neditor de textos(wordpad),"
                                                + "conserte o(s) erro(s) e tente novamente!\n\nErro(s) encontrado(s):\n"+erros);                                    
					//throw new NfeException("Erro Na Validacao do Xml: " + erros);
				} else {
                                    System.out.println("XML validado com sucesso!");                                    
                                }
			}

			System.out.println("Xml Assinado: " + xml);

			return XmlUtil.xmlToObject(xml, TEnviNFe.class);

		} catch (JAXBException e) {
			throw new NfeException(e.getMessage());
		}

	}

	/**
	 * Metodo para Enviar a NFE.
	 * 
	 * @param nfe
	 * @return Nfe
	 * @throws NfeException
	 */
	public static TRetEnviNFe enviaNfe(TEnviNFe enviNFe, String tipo) throws NfeException {

		certUtil = new CertificadoUtil();
		boolean nfce = tipo.equals(ConstantesUtil.NFCE);
		configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();
		String qrCode = "";
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 1 . . ." ); 

		try {
			if (nfce) {
				qrCode = enviNFe.getNFe().get(0).getInfNFeSupl().getQrCode();
				enviNFe.getNFe().get(0).getInfNFeSupl().setQrCode("");
			}
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 2 . . ." ); 

			String xml = XmlUtil.objectToXml(enviNFe);
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 3 . . ." + xml); 
			if (nfce) {
				enviNFe.getNFe().get(0).getInfNFeSupl().setQrCode(qrCode);
			}
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 4 . . ." ); 
			
			OMElement ome = AXIOMUtil.stringToOM(xml);
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 5 . . ." ); 

			Iterator<?> children = ome.getChildrenWithLocalName("NFe");
			while (children.hasNext()) {
				OMElement omElementNFe = (OMElement) children.next();
				if ((omElementNFe != null) && ("NFe".equals(omElementNFe.getLocalName()))) {
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 6 . . ." ); 
					omElementNFe.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe", null);
					if (nfce) {

						OMFactory f = OMAbstractFactory.getOMFactory();
						OMText omt = f.createOMText(qrCode, OMElement.CDATA_SECTION_NODE);

						Iterator<?> itInfSupl = omElementNFe.getChildrenWithLocalName("infNFeSupl");
						while (itInfSupl.hasNext()) {
							Object elementInfSupl = itInfSupl.next();
							if (elementInfSupl instanceof OMElement) {
								OMElement omElementInfSupl = (OMElement) elementInfSupl;
								Iterator<?> itqrCode = omElementInfSupl.getChildrenWithLocalName("qrCode");
								while (itqrCode.hasNext()) {
									Object elementQrCode = itqrCode.next();
									if (elementQrCode instanceof OMElement) {
										OMElement omElementQrCode = (OMElement) elementQrCode;
										omElementQrCode.addChild(omt);
									}
								}
							}
						}
					}
				}
			}
			
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 7 . . ." ); 
			//Adicionado CDATA apos OM
			if (nfce) {
				enviNFe.getNFe().get(0).getInfNFeSupl().setQrCode("<![CDATA["+qrCode +"]]>");
			}
			
if (display) JOptionPane.showMessageDialog(null, "Enviar - Xml para Envio: " + ome.toString()); 
			
			NfeAutorizacaoStub.NfeDadosMsg dadosMsg = new NfeAutorizacaoStub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);
			NfeAutorizacaoStub.NfeCabecMsg nfeCabecMsg = new NfeAutorizacaoStub.NfeCabecMsg();
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 8 . . ." ); 

			/**
			 * Codigo do Estado.
			 */
			nfeCabecMsg.setCUF(String.valueOf(configuracoesNfe.getEstado().getCodigoIbge()));
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 9 . . ." ); 
			/**
			 * Versao do XML
			 */
			nfeCabecMsg.setVersaoDados(configuracoesNfe.getVersaoNfe());
if (display) JOptionPane.showMessageDialog(null, "Enviar - Passou 10 . . ." ); 

			NfeAutorizacaoStub.NfeCabecMsgE nfeCabecMsgE = new NfeAutorizacaoStub.NfeCabecMsgE();
			nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);
if (display) JOptionPane.showMessageDialog(null, "Enviar.enviaNfe() - Url da NFe: "+WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.ENVIO));
			NfeAutorizacaoStub stub = new NfeAutorizacaoStub(nfce ? WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.ENVIO) : WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.ENVIO));
			result = stub.nfeAutorizacaoLote(dadosMsg, nfeCabecMsgE);

			return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetEnviNFe.class);

		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
		}

	}

}
