/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class Emit {
    private String CNPJ;
    private String xNome;
    private String xFant;
    private EnderEmit enderEmit;
    private String IE;
    private String CRT;

    public Emit(String CNPJ, String xNome, String xFant, EnderEmit enderEmit, String IE, String CRT){
        this.CNPJ       = CNPJ;
        this.xNome      = xNome;
        this.xFant      = xFant;
        this.enderEmit  = enderEmit;
        this.IE         = IE;
        this.CRT        = CRT;
    }
    public Emit() {
        
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getxNome() {
        return xNome;
    }

    public void setxNome(String xNome) {
        this.xNome = xNome;
    }

    public String getxFant() {
        return xFant;
    }

    public void setxFant(String xFant) {
        this.xFant = xFant;
    }

    public EnderEmit getEnderEmit() {
        return enderEmit;
    }

    public void setEnderEmit(EnderEmit enderEmit) {
        this.enderEmit = enderEmit;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getCRT() {
        return CRT;
    }

    public void setCRT(String CRT) {
        this.CRT = CRT;
    }

}
