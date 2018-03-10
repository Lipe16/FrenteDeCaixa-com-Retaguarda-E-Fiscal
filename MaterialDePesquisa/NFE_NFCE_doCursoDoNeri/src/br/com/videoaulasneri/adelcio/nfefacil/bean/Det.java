/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;


import br.com.videoaulasneri.adelcio.nfefacil.bean.Imposto;
public class Det {
    private String nItem;
    private Prod prod;
    private Imposto imposto;

    public Det(String nItem, Prod prod, Imposto imposto){
        this.nItem      = nItem;
        this.prod       = prod;
        this.imposto    = imposto;
    }

   // public det(){
   //
   // }
}