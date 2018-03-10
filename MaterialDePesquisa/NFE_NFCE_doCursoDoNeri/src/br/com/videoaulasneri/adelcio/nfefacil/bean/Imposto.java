/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class Imposto {
    private String vTotTrib;
    private ICMS ICMS;
    private PIS PIS;
    private COFINS COFINS;

    public Imposto( String vTotTrib, ICMS ICMS, PIS PIS, COFINS COFINS){
        this.vTotTrib = vTotTrib;
        this.ICMS   = ICMS;
        this.PIS    = PIS;
        this.COFINS = COFINS;
    }
    public Imposto(){}
}
