/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class ICMS {
    private ICMS00 ICMS00;
    private ICMS10 ICMS10;
    private ICMS20 ICMS20;
    private ICMS40 ICMS40;
    private ICMSSN101 ICMSSN101;
    private ICMSSN102 ICMSSN102;
    private ICMSSN500 ICMSSN500;
    private ICMS51 ICMS51;
    private ICMS60 ICMS60;

    public ICMS(ICMS00 ICMS00, ICMS10 ICMS10, ICMS20 ICMS20, ICMS40 ICMS40, ICMSSN101 ICMSSN101, ICMSSN102 ICMSSN102, ICMSSN500 ICMSSN500, ICMS51 ICMS51, ICMS60 ICMS60){
        this.ICMS00 = ICMS00;
        this.ICMS10 = ICMS10;
        this.ICMS20 = ICMS20;
        this.ICMS40 = ICMS40;
        this.ICMSSN101 = ICMSSN101;
        this.ICMSSN102 = ICMSSN102;
        this.ICMSSN500 = ICMSSN500;
        this.ICMS51 = ICMS51;
        this.ICMS60 = ICMS60;
    }
    public ICMS(){
        
    }

}
