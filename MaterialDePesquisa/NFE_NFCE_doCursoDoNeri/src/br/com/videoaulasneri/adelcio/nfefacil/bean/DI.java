/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class DI {
    private String nDI;
    private String dDI;
    private String xLocDesemb;
    private String UFDesemb;
    private String dDesemb;
    private String cExportador;
    
    public DI(String nDI, String dDI, String xLocDesemb, String UFDesemb, String dDesemb, String cExportador){
        this.nDI = nDI;
        this.dDI = dDI;
        this.xLocDesemb = xLocDesemb;
        this.UFDesemb = UFDesemb;
        this.dDesemb = dDesemb;
        this.cExportador = cExportador;
    }

}
