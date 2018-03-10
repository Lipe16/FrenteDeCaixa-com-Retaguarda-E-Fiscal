/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.ksFixo;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.pr_token;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import java.security.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 * Classe Responsavel Por Assinar O Xml.
 * 
 * 
 */
public class Assinar {

	public static final String INFINUT = "infInut";

	public static final String EVENTO = "evento";

	public static final String NFE = "NFe";
	
	private static ConfiguracoesIniciaisNfe configuracoesNfe;

	private static NodeList elements;

	private static PrivateKey privateKey;

	private static KeyInfo keyInfo;

	Assinar assinarXMLsCertfificadoA1;

	
	/**
	 * @param stringXml
	 * @param certificado
	 * @param tipo ('Nfe' para nfe normal , 'infInut' para inutilizacao, 'evento' para eventos)
	 * @return String do Xml Assinado
	 * @throws NfeException
	 */
	public static String assinaNfe(String stringXml ,String tipo) throws NfeException{
		
                configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();

		stringXml = XmlUtil.removeAcentos(stringXml);
		stringXml = assinaDocNFe(stringXml , tipo);

		return stringXml;
	}

	/**
	 * Assinatura do XML de Envio de Lote da NF-e utilizando Certificado
	 * Digital.
	 * 
	 * @param Conteudo do Xml
	 * @param Nome do Certificado Digital
	 * @return String do XMl Assinado
	 * @throws Exception
	 */
	private static String assinaDocNFe(String xml, String tipo) throws NfeException{

		Document document = null;
		
		try {
			document = documentFactory(xml);
			XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
			ArrayList<Transform> transformList = signatureFactory(signatureFactory);
			loadCertificates(signatureFactory);
      //System.out.println("Assinar.assinaDocNFe() - Passou 1 . . .");
	
			for (int i = 0; i < document.getDocumentElement().getElementsByTagName(tipo).getLength(); i++) {
				assinarNFe(tipo, signatureFactory, transformList, privateKey, keyInfo, document, i);
			}
      //System.out.println("Assinar.assinaDocNFe() - Passou 2 . . .");
		
		} catch (SAXException | IOException | ParserConfigurationException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | KeyStoreException | UnrecoverableEntryException | NoSuchProviderException | CertificateException | MarshalException | XMLSignatureException e) {
			throw new NfeException("Erro ao Assinar Nfe. Erro: "+e.getMessage());
		}

		return outputXML(document);
	
	}

	private static void assinarNFe(String tipo, XMLSignatureFactory fac, ArrayList<Transform> transformList, PrivateKey privateKey, KeyInfo ki, Document document, int indexNFe) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, MarshalException, XMLSignatureException{

		if (tipo.equals(EVENTO)) {
			elements = document.getElementsByTagName("infEvento");
		} else if(tipo.equals(INFINUT)){
			elements = document.getElementsByTagName("infInut");
		}else{
			elements = document.getElementsByTagName("infNFe");
		}
     //System.out.println("Assinar.assinarNFe() - Passou 1 . . .");
		
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);
		String id = el.getAttribute("Id");
     //System.out.println("Assinar.assinarNFe() - Passou 2 . . . id: "+id);

		el.setIdAttribute("Id", true);

		Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);

		SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null), fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
				Collections.singletonList(ref));
     //System.out.println("Assinar.assinarNFe() - Passou 3 . . .");

		XMLSignature signature = fac.newXMLSignature(si, ki);

		DOMSignContext dsc = null ;

		if (tipo.equals(INFINUT)) {
     //System.out.println("Assinar.assinarNFe() - Passou 3.1 . . . O tipo é INFINUT . . .");
			dsc = new DOMSignContext(privateKey, document.getFirstChild());
		}else{
			dsc = new DOMSignContext(privateKey, document.getDocumentElement().getElementsByTagName(tipo).item(indexNFe));
		}
     //System.out.println("Assinar.assinarNFe() - Passou 4 . . .getFirstChild(): "+document.getFirstChild());
		
		dsc.setBaseURI("ok");
//     System.out.println("Assinar.assinarNFe() - Passou 5 - dsc: "+dsc.toString());
//     System.out.println("Assinar.assinarNFe() - Passou 5.1 - dsc.BaseURI: "+dsc.getBaseURI().toString());
//     System.out.println("Assinar.assinarNFe() - Passou 5.2 - dsc.Parent.LocalName: "+dsc.getParent().getLocalName().toString());
//     System.out.println("Assinar.assinarNFe() - Passou 5.3 - dsc.Parent.TextContent: "+dsc.getParent().getTextContent().toString());

		signature.sign(dsc);
     //System.out.println("Assinar.assinarNFe() - Passou 6 . . .");
	}

	private static ArrayList<Transform> signatureFactory(XMLSignatureFactory signatureFactory) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

		ArrayList<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);
		return transformList;
	}

	private static Document documentFactory(String xml) throws SAXException, IOException, ParserConfigurationException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
		return document;
	}

	private static void loadCertificates(XMLSignatureFactory signatureFactory) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, NoSuchProviderException, CertificateException, IOException, NfeException {

		Certificado certificado = configuracoesNfe.getCertificado();
                System.out.println("Assinar.loadCertificates() - "
                                +"\nNome do certificado: "+certificado.getNome()+"\n"
                                + "Senha do Certificado: "+certificado.getSenha()+"\n"
                                + "Vencimento: "+certificado.getVencimento());
		KeyStore.PrivateKeyEntry pkEntry = null;
		KeyStore ks = null;
       //System.out.println("Assinar.loadCertificates() - Passou 1 . . .");
		if(certificado.getTipo().equals(Certificado.getWINDOWS())){
                    ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
                    ks.load(null, null);
		}else if(certificado.getTipo().equals(Certificado.getARQUIVO())){
                    ks = CertificadoUtil.getKeyStore(certificado);
                    ks.load(null, null);
		}else if(certificado.getTipo().equals(Certificado.getCARTAO())){
        //System.out.println("Assinar.loadCertificates() - Passou 2 . . .");
                   if (ksFixo == null) {
                        if (pr_token == null) {
                            String configName = "c:/nfe/token.cfg";
                            pr_token = new sun.security.pkcs11.SunPKCS11(configName);
                        }
                        ksFixo = KeyStore.getInstance("pkcs11", pr_token);
        //System.out.println("Assinar.loadCertificates() - O Keystore era nulo!");
                    } else {
        //System.out.println( "Assinar.loadCertificates() - O Keystore NAO era nulo!");
                    }
                    char[] pin = certificado.getSenha().toCharArray();
                    ksFixo.load(null, pin);
                    ks = ksFixo;
		}
        //System.out.println("Assinar.loadCertificates() - Passou 3 . . . getProvider: "+ks.getProvider().getName());

		pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(certificado.getNome(), new KeyStore.PasswordProtection(certificado.getSenha().toCharArray()));
                privateKey = pkEntry.getPrivateKey();
        //System.out.println("Assinar.loadCertificates() - Passou 4 . . .");
		
		X509Certificate cert = (X509Certificate) pkEntry.getCertificate();
		
		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();
        //System.out.println("Assinar.loadCertificates() - Passou 5 . . .");

		x509Content.add(cert);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
        //System.out.println("Assinar.loadCertificates() - Passou 6 . . .");
	}

	private static String outputXML(Document doc) throws NfeException {
		
		String xml = "";

		try{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		xml = os.toString();
		xml = xml.replaceAll("\\r\\n", "");
		xml = xml.replaceAll(" standalone=\"no\"", "");
		}catch(TransformerException e){
			throw new NfeException("Erro ao Transformar Documento:"+e.getMessage());
		}
		
		return xml;
	}

}
