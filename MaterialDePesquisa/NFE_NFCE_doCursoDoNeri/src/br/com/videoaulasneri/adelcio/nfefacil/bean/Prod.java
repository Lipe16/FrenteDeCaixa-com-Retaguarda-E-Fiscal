/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class Prod {
    private String cProd;
    private String cEAN;
    private String xProd;
    private String NCM;
    private String CFOP;
    private String uCom;
    private String qCom;
    private String vUnCom;
    private String CEST;
    private String vProd;
    private String cEANTrib;
    private String uTrib;
    private String qTrib;
    private String vUnTrib;
    private String vDesc;
    private String indTot;
    private DI DI;
    private String xPed;
    
    public Prod() {
        
    }

    public Prod(String cProd, String cEAN, String xProd, String NCM,
                String CFOP, String uCom, String qCom, String vUnCom,
                String CEST, String vProd, String cEANTrib, String uTrib,
                String qTrib, String vUnTrib, String vDesc, String indTot,
                DI DI, String xPed
       ){
        this.cProd  = cProd;
        this.cEAN   = cEAN;
        this.xProd  = xProd;
        this.NCM    = NCM;
        this.CFOP   = CFOP;
        this.uCom   = uCom;
        this.qCom   = qCom;
        this.vUnCom = vUnCom;
        this.CEST  = CEST;
        this.vProd  = vProd;
        this.cEANTrib = cEANTrib;
        this.uTrib  = uTrib;
        this.qTrib  = qTrib;
        this.vUnTrib = vUnTrib;
        this.vDesc = vDesc;
        this.indTot  = indTot;
        this.DI = DI;
        this.xPed = xPed;
    }

    public String getcProd() {
        return cProd;
    }

    public void setcProd(String cProd) {
        this.cProd = cProd;
    }

    public String getcEAN() {
        return cEAN;
    }

    public void setcEAN(String cEAN) {
        this.cEAN = cEAN;
    }

    public String getxProd() {
        return xProd;
    }

    public void setxProd(String xProd) {
        this.xProd = xProd;
    }

    public String getNCM() {
        return NCM;
    }

    public void setNCM(String NCM) {
        this.NCM = NCM;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getuCom() {
        return uCom;
    }

    public void setuCom(String uCom) {
        this.uCom = uCom;
    }

    public String getqCom() {
        return qCom;
    }

    public void setqCom(String qCom) {
        this.qCom = qCom;
    }

    public String getvUnCom() {
        return vUnCom;
    }

    public void setvUnCom(String vUnCom) {
        this.vUnCom = vUnCom;
    }

    public String getCEST() {
        return CEST;
    }

    public void setCEST(String CEST) {
        this.CEST = CEST;
    }

    public String getvProd() {
        return vProd;
    }

    public void setvProd(String vProd) {
        this.vProd = vProd;
    }

    public String getcEANTrib() {
        return cEANTrib;
    }

    public void setcEANTrib(String cEANTrib) {
        this.cEANTrib = cEANTrib;
    }

    public String getuTrib() {
        return uTrib;
    }

    public void setuTrib(String uTrib) {
        this.uTrib = uTrib;
    }

    public String getqTrib() {
        return qTrib;
    }

    public void setqTrib(String qTrib) {
        this.qTrib = qTrib;
    }

    public String getvUnTrib() {
        return vUnTrib;
    }

    public void setvUnTrib(String vUnTrib) {
        this.vUnTrib = vUnTrib;
    }

    public String getvDesc() {
        return vDesc;
    }

    public void setvDesc(String vDesc) {
        this.vDesc = vDesc;
    }

    public String getIndTot() {
        return indTot;
    }

    public void setIndTot(String indTot) {
        this.indTot = indTot;
    }

    public DI getDI() {
        return DI;
    }

    public void setDI(DI DI) {
        this.DI = DI;
    }

    public String getxPed() {
        return xPed;
    }

    public void setxPed(String xPed) {
        this.xPed = xPed;
    }

}
