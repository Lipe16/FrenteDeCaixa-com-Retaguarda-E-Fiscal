/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class ICMS60 {
    private String orig;
    private String CST;
    private String vBCSTRet;
    private String vICMSSTRet;
    
    public ICMS60(String orig, String CST){
        this.orig = orig;
        this.CST  = CST;
    }

    public ICMS60(String orig, String CST, String vBCSTRet, String vICMSSTRet){
        this.orig = orig;
        this.CST  = CST;
        this.vBCSTRet = vBCSTRet;
        this.vICMSSTRet = vICMSSTRet;
    }

}
