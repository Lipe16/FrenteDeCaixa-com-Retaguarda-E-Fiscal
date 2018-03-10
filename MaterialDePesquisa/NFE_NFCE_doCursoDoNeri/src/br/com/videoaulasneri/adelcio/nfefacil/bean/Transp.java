/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class Transp {
    private String modFrete;
    private Transporta transporta;
    private VeicTransp veicTransp;
    private Vol vol;

    public Transp(String modFrete, Transporta transporta, VeicTransp veicTransp, Vol vol){
        this.modFrete       = modFrete;
        this.transporta     = transporta;
        this.veicTransp     = veicTransp;
        this.vol            = vol;
    }
    public Transp(){
        
    }

}
