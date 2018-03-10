/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.util;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.ksFixo;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.pr_token;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.httpclient.protocol.Protocol;

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import java.security.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Classe Responsavel Por Carregar os Certificados Do Repositorio do Windows
 * 
 * 
 */
public class CertificadoUtil {

	private static ConfiguracoesIniciaisNfe configuracoesNfe;
	private static final int SSL_PORT = 443;
        private static boolean display = false;
        private static String senhaJKS = "changeit";

	// Construtor
	public CertificadoUtil() throws NfeException {
                configuracoesNfe = ConfiguracoesIniciaisNfe.getInstance();
	}
	
	public static Certificado certificadoPfx(String caminhoCertificado, String senha) {  //throws NfeException{
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil.certificadPfx() - entrou no metodo . . . ");          
		
		Certificado certificado = new Certificado();
		try{
			certificado.setArquivo(caminhoCertificado);
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil.certificadPfx() - instanciou certificado . . . ");          
			certificado.setSenha(senha);
			
			KeyStore ks = getKeyStore(certificado);
			ks.load(null, null);
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 1 . . .");            
	
			Enumeration<String> aliasEnum = ks.aliases();
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 1.1 . . .");            
			String aliasKey = (String) aliasEnum.nextElement();
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 2 . . .");            
			certificado.setNome(aliasKey);
			certificado.setTipo(Certificado.getARQUIVO());
			certificado.setVencimento(DataValidade(certificado).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			certificado.setDiasRestantes(diasRestantes(certificado));
			certificado.setValido(valido(certificado));
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 3 . . .");            
		}catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e){
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil.certificadPfx(1) - Erro ao carregar informacoes do certificado:"+e.getMessage());          
//			throw new NfeException("Erro ao carregar informaÃ§Ãµes do certificado:"+e.getMessage());
		}catch (Exception e){
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil.certificadPfx(2) - Erro ao carregar informacoes do certificado:"+e.getMessage());          
		}
			
		return certificado;
			
	}

	public static Certificado certificadoA3(String caminhoCertificado, String senha) throws NfeException {
		
		Certificado certificado = new Certificado();
		try{
                    certificado.setSenha(senha);
                   
//   inicio
            String configName = caminhoCertificado;
            String senhaDoCertificado = senha;  //  "neligigu";
            String arquivoCacertsGeradoTodosOsEstados = "c:\\nfe\\NFeCacerts";  //"c:\\nfe\\dados\\empr0001\\certificado.jks";  //"c:\\nfe\\certnovo.jks";   //
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil.certificadA3() - Arq.Config.: "+configName);  //+"  - senha: "+senhaDoCertificado);            
/*
            Provider p = new sun.security.pkcs11.SunPKCS11(configName);
    		Security.addProvider(p);
    		char[] pin = senhaDoCertificado.toCharArray();
    		KeyStore ks = KeyStore.getInstance("pkcs11", p);
    		ks.load(null, pin);
*/
            if (ksFixo == null) {
                if (pr_token == null) {
                    pr_token = new sun.security.pkcs11.SunPKCS11(configName);
                    Security.addProvider(pr_token);
                }
    		char[] pin = senhaDoCertificado.toCharArray();
    		ksFixo = KeyStore.getInstance("pkcs11", pr_token);
    		ksFixo.load(null, pin);
            }

if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 1 . . .");            
            String aliasKey = "";
            Enumeration<String> aliasesEnum = ksFixo.aliases();
    		while (aliasesEnum.hasMoreElements()) {
    			aliasKey = (String) aliasesEnum.nextElement();
    			if (ksFixo.isKeyEntry(aliasKey)) break;
    		}
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 2 . . .");            
    		X509Certificate certificate = (X509Certificate) ksFixo.getCertificate(aliasKey);
    		PrivateKey privateKey = (PrivateKey) ksFixo.getKey(aliasKey, senhaJKS.toCharArray());
    		br.com.videoaulasneri.adelcio.utilitarios.SocketFactoryDinamico socketFactoryDinamico = new br.com.videoaulasneri.adelcio.utilitarios.SocketFactoryDinamico(certificate, privateKey);
    		socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 3 . . .");            

		if (ksFixo.containsAlias(aliasKey)) {
                    info("Certificado de: "+aliasKey);
                }
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 4 . . .");            
                
            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);  
            Protocol.registerProtocol("https", protocol);    		

			certificado.setNome(aliasKey);
			certificado.setTipo(Certificado.getCARTAO());
			certificado.setVencimento(certificate.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			certificado.setDiasRestantes(diasRestantesA3(certificate));
			certificado.setValido(validoA3(certificate));
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - Passou 5 . . ."); 
                        info("Tipo Certtificado: "+certificado.getTipo());
                        info("Vencimento:......: "+certificado.getVencimento());
                        info("Dias Restantes...: "+certificado.getDiasRestantes());
if (display) JOptionPane.showMessageDialog(null, "CertificadoUtil - finalizou . . ."); 
//  fim                        
                }catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException  | UnrecoverableKeyException e){
			throw new NfeException("Erro ao carregar informaÃ§Ãµes do certificado A3:"+e.getMessage());
		}
			
		return certificado;
			
	}

	private static void info(String log) {
		System.out.println("INFO: " + log);
	}
	/**
	 * Retorna a Lista de Certificados do Repositorio do Windows
	 * 
	 */ 
	public static List<Certificado> listaCertificadosWindows() throws NfeException {

		// Estou setando a variavel para 20 dispositivos no maximo
		List<Certificado> listaCert = new ArrayList<>(20);

		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");

			ks.load(null, null);

			Enumeration<String> aliasEnum = ks.aliases();
                        int idx = 0;

			while (aliasEnum.hasMoreElements()) {
				String aliasKey = (String) aliasEnum.nextElement();

				if (ObjetoUtil.differentNull(aliasKey)) {
					Certificado cert = new Certificado();
					cert.setNome(aliasKey);
					cert.setTipo(Certificado.getWINDOWS());
					cert.setSenha("");
					Date dataValidade = DataValidade(cert);
					if(dataValidade == null){
						cert.setNome("(INVALIDO)"+aliasKey);
						cert.setVencimento(LocalDate.of(2000, 1, 1));
						cert.setDiasRestantes(0L);
						cert.setValido(false);
					}else{
						cert.setVencimento(dataValidade.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						cert.setDiasRestantes(diasRestantes(cert)); 
						cert.setValido(valido(cert));
                                                cert.setNome(aliasKey);
					}
System.out.println("Certificado "+idx+" - Nome: "+cert.getNome()+" - Vencimento: "+cert.getVencimento());					
					listaCert.add(cert);
				}
                                idx++;
			}

		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException
				| IOException ex) {
			throw new NfeException("Erro ao Carregar Certificados:" + ex.getMessage());
		}

		return listaCert;

	}

	// Procedimento que retorna a Data De Validade Do Certificado Digital

	private static Date DataValidade(Certificado certificado) throws NfeException {

		Date data = new Date();
		
		try {
			X509Certificate cert = null;
			KeyStore.PrivateKeyEntry pkEntry = null;
			KeyStore ks = null;
			if(certificado.getTipo().equals(Certificado.getWINDOWS())){
				ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			}else if(certificado.getTipo().equals(Certificado.getARQUIVO())){
				ks = getKeyStore(certificado);
			}
			
			ks.load(null, null);
			if (ks.isKeyEntry(certificado.getNome())) {
				pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(certificado.getNome(), new KeyStore.PasswordProtection(certificado.getSenha().toCharArray()));
			}else{
				return null;
			}
	
			cert = (X509Certificate) pkEntry.getCertificate();
	
			if (cert == null) {
				return null;
			}
			data = cert.getNotAfter();
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException | NoSuchProviderException | CertificateException | IOException e) {
			throw new NfeException("Erro ao Pegar Data Certificado:"+e.getMessage());
		}
		
		return data;

	}

	// Retorna Os dias Restantes do Certificado Digital
	private static Long diasRestantes(Certificado certificado) throws NfeException {

		Date data = DataValidade(certificado);
		if (data == null) {
			return null;
		}
		long differenceMilliSeconds = data.getTime() - new Date().getTime();
		return differenceMilliSeconds / 1000 / 60 / 60 / 24;
	}

	// retorna True se o Certificado for validao
	public static boolean valido(Certificado certificado) throws NfeException {

		if (DataValidade(certificado) != null && DataValidade(certificado).after(new Date())) {
			return true;
		} else {
			return false;
		}

	}

	// Retorna Os dias Restantes do Certificado Digital
	private static Long diasRestantesA3(X509Certificate certificate) throws NfeException {

		Date data = certificate.getNotAfter();  //DataValidade(certificado);
		if (data == null) {
			return null;
		}
		long differenceMilliSeconds = data.getTime() - new Date().getTime();
		return differenceMilliSeconds / 1000 / 60 / 60 / 24;
	}

	// retorna True se o Certificado for validao
	public static boolean validoA3(X509Certificate certificate) throws NfeException {

		if (certificate.getNotAfter() != null && certificate.getNotAfter().after(new Date())) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("restriction")
	public void iniciaConfiguracoes() throws NfeException {

		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		System.clearProperty("javax.net.ssl.keyStore");
		System.clearProperty("javax.net.ssl.keyStorePassword");
		System.clearProperty("javax.net.ssl.trustStore");
		
		if(configuracoesNfe.getProxy()!=null){
			System.setProperty("http.proxyHost", configuracoesNfe.getProxy().getProxyHostName());
			System.setProperty("http.proxyPort", configuracoesNfe.getProxy().getProxyPort());
			System.setProperty("http.proxyUser", configuracoesNfe.getProxy().getProxyUserName()); 
			System.setProperty("http.proxyPassword", configuracoesNfe.getProxy().getProxyPassWord()); 
		}

		System.setProperty("jdk.tls.client.protocols", "TLSv1"); // Servidor do	Sefaz RS

		if(configuracoesNfe.getCertificado().getTipo().equals(Certificado.getWINDOWS())){
			System.setProperty("javax.net.ssl.keyStoreProvider", "SunMSCAPI");
			System.setProperty("javax.net.ssl.keyStoreType", "Windows-MY");
			System.setProperty("javax.net.ssl.keyStoreAlias", configuracoesNfe.getCertificado().getNome());
System.out.println("CertificadoUtil - O certificado é do Windows . . . \n     Nome............: "+configuracoesNfe.getCertificado().getNome()
                    +"\n     Com validade até: "+configuracoesNfe.getCertificado().getVencimento());                    
		}else if(configuracoesNfe.getCertificado().getTipo().equals(Certificado.getARQUIVO())){
			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");  
			System.setProperty("javax.net.ssl.keyStore", configuracoesNfe.getCertificado().getArquivo());  
System.out.println("CertificadoUtil - O certificado é do arquivo pfx . . . \n     Nome............: "+configuracoesNfe.getCertificado().getNome()
                    +"\n     Com validade até: "+configuracoesNfe.getCertificado().getVencimento());                    
		}else if(configuracoesNfe.getCertificado().getTipo().equals(Certificado.getCARTAO())){
		}
//System.out.println("CertificadoUtil.iniciaConfiguracoes() - Senha do Certificado: "+configuracoesNfe.getCertificado().getSenha());
		System.setProperty("javax.net.ssl.keyStorePassword", configuracoesNfe.getCertificado().getSenha());

		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		
		//Extrair Cacert do Jar
		String cacert = "";
        try {
            InputStream input = getClass().getResourceAsStream("/NFeCacerts");
            File file = File.createTempFile("tempfile", ".tmp");
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            cacert = file.getAbsolutePath();
            file.deleteOnExit();
        } catch (IOException ex) {
            throw new NfeException(ex.getMessage());
        }
	   
		System.setProperty("javax.net.ssl.trustStore", cacert);
		
		if(configuracoesNfe.isProtocol()){
			try {
				System.out.println("Modo Protocol Ativado.");
				ativaProtocolo(configuracoesNfe.getCertificado());
			} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
				 throw new NfeException(e.getMessage());
			}
		}
		
	}

	public static KeyStore getKeyStore(Certificado certificado) throws NfeException{
		try {
                    File file = new File(certificado.getArquivo());
                    if(!file.exists()){
                                    throw new NfeException("Certificado Digital nÃ£o Encontrado");
                    }
	        
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    keyStore.load(new ByteArrayInputStream(getBytesFromInputStream(new FileInputStream(file))), certificado.getSenha().toCharArray());
                    return keyStore;
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			throw new NfeException("Erro Ao Ler Certificado: "+e.getMessage());
		}
		
	}
	
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException
	{
	    try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
	    {
	        byte[] buffer = new byte[0xFFFF];

	        for (int len; (len = is.read(buffer)) != -1;)
	            os.write(buffer, 0, len);

	        os.flush();

	        return os.toByteArray();
	    }
	}
	
	public void ativaProtocolo(Certificado certificado) throws NfeException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException{
		
		KeyStore ks = getKeyStore(certificado);
		X509Certificate certificate = (X509Certificate) ks.getCertificate(certificado.getNome());  
                PrivateKey privateKey = (PrivateKey) ks.getKey(certificado.getNome(), certificado.getSenha().toCharArray());  
		SocketFactoryDinamico socketFactory = new SocketFactoryDinamico(certificate, privateKey );
		socketFactory.setFileCacerts(getClass().getResourceAsStream("/NFeCacerts"));
		Protocol protocol = new Protocol("https", socketFactory, 443);
		Protocol.registerProtocol("https", protocol);
	}

}