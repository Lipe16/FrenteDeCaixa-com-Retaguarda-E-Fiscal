/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.videoaulasneri.adelcio.utilitarios;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.swing.JOptionPane;

public class DadosCertificadoA3 {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
        }
        public void processar() {
		
            try {
                    String configName = "c:/nfe/token.cfg";
                    String senhaDoCertificado = informarSenha();  
                    if (senhaDoCertificado !=null && !senhaDoCertificado.equals("")) {
			
			Provider provider = new sun.security.pkcs11.SunPKCS11(configName);
			Security.addProvider(provider);
			
			String alias = null;
			KeyStore ks = KeyStore.getInstance("pkcs11", provider);
			ks.load(null, senhaDoCertificado.toCharArray());
			
			Provider pp = ks.getProvider();
			info("--------------------------------------------------------");
			info("Provider   : " + pp.getName());
			info("Prov.Vers. : " + pp.getVersion());
			info("KS Type    : " + ks.getType());
			info("KS DefType : " + ks.getDefaultType());
	
			Enumeration <String> al = ks.aliases();
			while (al.hasMoreElements()) {
				alias = al.nextElement();
				info("--------------------------------------------------------");
				if (ks.containsAlias(alias)) {
					info("Alias exists : '" + alias + "'");
					
					X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
					info("Certificate  : '" + cert.toString() + "'");
					info("Version      : '" + cert.getVersion() + "'");
					info("SerialNumber : '" + cert.getSerialNumber() + "'");
					info("SigAlgName   : '" + cert.getSigAlgName() + "'");
					info("NotBefore    : '" + cert.getNotBefore().toString() + "'");
					info("NotAfter     : '" + cert.getNotAfter().toString() + "'");
					info("TBS          : '" + cert.getTBSCertificate().toString() + "'");

					JOptionPane.showMessageDialog(null, 
                                                "Certificado para: '" + alias + "'"
                                                +"\n..Validade de: '" + cert.getNotBefore().toString() + "'"
                                                +"\n..........Até: '" + cert.getNotAfter().toString() + "'"
                                                +"\n..........TBS: '" + cert.getTBSCertificate().toString() + "'"
                                        );
                                        
                                        info("\n   * * * FIM DA LISTAGEM DAS INFORMACOES DO CARTAO * * *\n.\n.\n.\n");
				} else {
					info("Alias doesn't exists : '" + alias + "'");
				}
			}
                    } else {
                        //JOptionPane.showMessageDialog(null, "Senha do Certificado do Cartão/Token informada inválida! ");
                    }
		} catch (Exception e) {
			error(e.toString());
                        JOptionPane.showMessageDialog(null, "Erro ao acessar o cartao A3: "+e);
		}
	}

	/**
	 * Log ERROR.
	 * 
	 * @param error
	 */
	private static void error(String error) {
		System.out.println("| ERROR: " + error);
	}

	/**
	 * Log INFO.
	 * 
	 * @param info
	 */
	private static void info(String info) {
		System.out.println("| INFO: " + info);
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
        
}
