/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class COFINS {
    private COFINSAliq COFINSAliq;
    private COFINSNT COFINSNT;
    private COFINSOutr COFINSOutr;

    public COFINS(COFINSAliq COFINSAliq){
        this.COFINSAliq = COFINSAliq;
    }
    public COFINS(COFINSNT COFINSNT){
        this.COFINSNT = COFINSNT;
    }
    public COFINS(COFINSOutr COFINSOutr){
        this.COFINSOutr = COFINSOutr;
    }
    public COFINS(){
        
    }

}
