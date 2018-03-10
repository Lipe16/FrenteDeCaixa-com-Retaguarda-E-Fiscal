/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class Dest {
    private String CNPJ;
    private String CPF;
    private String xNome;
    private EnderDest enderDest;
    private String indIEDest;
    private String IE;
    private String email;
    
    public Dest() {
        
    }

    public Dest(String CNPJ, String CPF, String xNome, EnderDest enderDest, String indIEDest, String IE, String email){
        this.CNPJ       = CNPJ;
        this.CPF        = CPF;
        this.xNome      = xNome;
        this.enderDest  = enderDest;
        this.indIEDest  = indIEDest;
        this.IE         = IE;
        this.email      = email;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getxNome() {
        return xNome;
    }

    public void setxNome(String xNome) {
        this.xNome = xNome;
    }

    public EnderDest getEnderDest() {
        return enderDest;
    }

    public void setEnderDest(EnderDest enderDest) {
        this.enderDest = enderDest;
    }

    public String getIndIEDest() {
        return indIEDest;
    }

    public void setIndIEDest(String indIEDest) {
        this.indIEDest = indIEDest;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
