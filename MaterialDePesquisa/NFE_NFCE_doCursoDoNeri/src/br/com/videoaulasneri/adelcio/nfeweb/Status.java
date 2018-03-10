/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

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
import br.inf.portalfiscal.nfe.schema.consstatserv.TConsStatServ;
import br.inf.portalfiscal.nfe.schema.retconsstatserv.TRetConsStatServ;
import br.inf.portalfiscal.www.nfe.wsdl.NfeStatusServico2.NfeStatusServico2Stub;
import javax.swing.JOptionPane;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.certUtil;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import br.inf.portalfiscal.www.nfe.wsdl.NfeStatusServico.NfeStatusServicoStub;


/**
 * Classe responsavel por fazer a Verificacao do Status Do Webservice
 * 
 */
public class Status {
    static boolean display = false;

	static NfeStatusServico2Stub.NfeStatusServicoNF2Result result;
	static NfeStatusServicoStub.NfeStatusServicoNFResult resultBA;
	private static ConfiguracoesIniciaisNfe configuracoesNfe;

	public static TRetConsStatServ statusServico(TConsStatServ consStatServ, boolean valida , String tipo) throws NfeException {
		
		configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();
		boolean BA = configuracoesNfe.getEstado().getCodigoIbge().equals("29");
		//CertificadoUtil certificadoUtil = new CertificadoUtil();
		//certificadoUtil.iniciaConfiguracoes();
                if (certUtil == null) {  //
                    certUtil = new CertificadoUtil();
                    certUtil.iniciaConfiguracoes();
                }
		boolean nfce = tipo.equals(ConstantesUtil.NFCE);

		try {

			String xml = XmlUtil.objectToXml(consStatServ);
if (display) JOptionPane.showMessageDialog(null, "Status - xml do status: ["+xml+"]"); 
	
			if(valida){
				String erros = Validar.validaXml(xml, Validar.STATUS);
				if(!ObjetoUtil.isEmpty(erros)){
					throw new NfeException("Erro Na Validacao do Xml: "+erros);
				}
			}
			

			OMElement ome = AXIOMUtil.stringToOM(xml);
			if(BA && !nfce){
				NfeStatusServicoStub.NfeDadosMsg dadosMsgBA = new NfeStatusServicoStub.NfeDadosMsg();
				dadosMsgBA.setExtraElement(ome);
				
				NfeStatusServicoStub.NfeCabecMsg nfeCabecMsgBA = new NfeStatusServicoStub.NfeCabecMsg();
				nfeCabecMsgBA.setCUF(configuracoesNfe.getEstado().getCodigoIbge());
				nfeCabecMsgBA.setVersaoDados(configuracoesNfe.getVersaoNfe());
				
				NfeStatusServicoStub.NfeCabecMsgE nfeCabecMsgEBA = new NfeStatusServicoStub.NfeCabecMsgE();
				nfeCabecMsgEBA.setNfeCabecMsg(nfeCabecMsgBA);
				
				NfeStatusServicoStub stubBA = new NfeStatusServicoStub(nfce ? WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.STATUS_SERVICO) : WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.STATUS_SERVICO));
				resultBA = stubBA.nfeStatusServicoNF(dadosMsgBA, nfeCabecMsgEBA);
				
				return XmlUtil.xmlToObject(resultBA.getExtraElement().toString(), TRetConsStatServ.class);
			}else{
                            NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();
                            dadosMsg.setExtraElement(ome);

                            NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();
                            /**
                             * Codigo do Estado.
                             */
                            nfeCabecMsg.setCUF(configuracoesNfe.getEstado().getCodigoIbge());

                            /**
                             * Versao do XML
                             */
                            nfeCabecMsg.setVersaoDados(configuracoesNfe.getVersaoNfe());
                            NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();
                            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

                            xml = XmlUtil.objectToXml(consStatServ);
if (display) JOptionPane.showMessageDialog(null, "Status - valor da variavel nfce: "+nfce);
                            String target = "";
                            if (nfce) {
                                target = WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.STATUS_SERVICO);
//teste com UF=PE               target = "https://nfce-homologacao.svrs.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx";
                            } else {
                                target = WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.STATUS_SERVICO);
                            }
if (display) JOptionPane.showMessageDialog(null, "Status - webservice: ["+target+"]"); 
                            NfeStatusServico2Stub stub = new NfeStatusServico2Stub(nfce ? WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.STATUS_SERVICO) : WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.STATUS_SERVICO));
                            //NfeStatusServico2Stub stub = new NfeStatusServico2Stub(nfce ? target : WebServiceUtil.getUrl(ConstantesUtil.NFE, ConstantesUtil.SERVICOS.STATUS_SERVICO));
                            result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);

                            return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsStatServ.class);
                        }			
		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
                }
//                    return null;
//                } catch (XMLStreamException e) {
//                    return null;
//                } catch (JAXBException e) {
//                    return null;
////                }
		
	}
	
}