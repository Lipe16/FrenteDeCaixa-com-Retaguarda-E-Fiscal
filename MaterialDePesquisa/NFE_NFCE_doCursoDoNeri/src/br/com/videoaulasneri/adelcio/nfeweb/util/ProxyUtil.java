/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.util;

import org.apache.axis2.transport.http.HttpTransportProperties;

public class ProxyUtil {
	 HttpTransportProperties.ProxyProperties proxyProperties;

	public ProxyUtil(String ip, int porta, String usuario,String senha) {
		   
		proxyProperties = new HttpTransportProperties.ProxyProperties();      
        
       proxyProperties.setProxyName(ip);    
         
       proxyProperties.setProxyPort(porta);    
        
       proxyProperties.setUserName(usuario);    
        
       proxyProperties.setPassWord(senha);    
	}
	 
	public String getProxyHostName(){
		return proxyProperties.getProxyHostName();   
	}
	
	public String getProxyPort(){
		return String.valueOf(proxyProperties.getProxyPort());   
	}
	
	public String getProxyUserName(){
		return proxyProperties.getUserName();   
	}
	
	public String getProxyPassWord(){
		return proxyProperties.getPassWord();   
	}
}

