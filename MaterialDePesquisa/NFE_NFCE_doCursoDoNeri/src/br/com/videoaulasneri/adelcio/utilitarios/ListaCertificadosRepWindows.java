package br.com.videoaulasneri.adelcio.utilitarios;  
  
import java.io.IOException;  
import java.security.KeyStore;  
import java.security.KeyStoreException;  
import java.security.NoSuchAlgorithmException;  
import java.security.NoSuchProviderException;  
import java.security.cert.CertificateException;  
import java.util.ArrayList;  
import java.util.Enumeration;  
import java.util.List;  
  
public class ListaCertificadosRepWindows {  
  
//    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException {  
    public void processar() {
        try {
        KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");  
          
        ks.load(null, null);  
          
        Enumeration<String> al = ks.aliases();  
          
        List<String> certificados = new ArrayList<String>();  
        while(al.hasMoreElements())  
        {  
            String alias = al.nextElement();  
            certificados.add(alias);  
        }  
          
        for (String cert : certificados)  
        {  
            System.out.println(cert);  
        }  
        } catch(Exception e) {
            System.out.println("Erro: " + e);
        } 
    }  
} 