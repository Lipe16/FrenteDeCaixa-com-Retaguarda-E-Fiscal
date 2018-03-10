/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class COFINSAliq {
    private String CST;
    private String vBC;
    private String pCOFINS;
    private String vCOFINS;
    
    public COFINSAliq() {
        
    }

    public COFINSAliq(String CST, String vBC, String pCOFINS, String vCOFINS){
        this.CST = CST;
        this.vBC = vBC;
        this.pCOFINS = pCOFINS;
        this.vCOFINS = vCOFINS;
    }

    /**
     * @return the CST
     */
    public String getCST() {
        return CST;
    }

    /**
     * @param CST the CST to set
     */
    public void setCST(String CST) {
        this.CST = CST;
    }

    /**
     * @return the vBC
     */
    public String getvBC() {
        return vBC;
    }

    /**
     * @param vBC the vBC to set
     */
    public void setvBC(String vBC) {
        this.vBC = vBC;
    }

    /**
     * @return the pCOFINS
     */
    public String getpCOFINS() {
        return pCOFINS;
    }

    /**
     * @param pCOFINS the pCOFINS to set
     */
    public void setpCOFINS(String pCOFINS) {
        this.pCOFINS = pCOFINS;
    }

    /**
     * @return the vCOFINS
     */
    public String getvCOFINS() {
        return vCOFINS;
    }

    /**
     * @param vCOFINS the vCOFINS to set
     */
    public void setvCOFINS(String vCOFINS) {
        this.vCOFINS = vCOFINS;
    }
}
