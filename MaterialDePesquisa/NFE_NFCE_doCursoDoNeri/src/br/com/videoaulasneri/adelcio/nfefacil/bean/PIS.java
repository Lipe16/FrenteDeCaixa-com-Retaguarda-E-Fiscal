/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class PIS {
    private PISAliq PISAliq;
    private PISNT PISNT;
    private PISOutr PISOutr;

    public PIS(PISAliq PISAliq){
        this.PISAliq = PISAliq;
    }
    public PIS(PISNT PISNT){
        this.PISNT = PISNT;
    }
    public PIS(PISOutr PISOutr){
        this.PISOutr = PISOutr;
    }
    public PIS(){
        
    }

}
