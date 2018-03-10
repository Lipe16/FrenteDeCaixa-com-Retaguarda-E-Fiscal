/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "consStatServ")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsStatServ {

    /**
     * Versao do leiaute.
     */
    @XmlAttribute
    private String versao;

   /**
     * namespace
     */
    @XmlAttribute
    private String xmlns;

    /**
     * Identificacao do Ambiente:
     * 1 - Producao / 2 - Homologacao
     */
    @XmlElement(name = "tpAmb")
    private Integer tpAmb = 2;

    /**
     * Codigo da UF consultada.
     */
    @XmlElement(name = "cUF")
    private Integer cUF;

    /**
     * Servicos solicitado 'STATUS'.
     */
    @XmlElement(name = "xServ")
    private String xServ;

    public ConsStatServ() {
        versao = "2.00";
        xmlns = "http://www.portalfiscal.inf.br/nfe";
        xServ = "STATUS";
    }


    public Integer getCUF() {
        return cUF;
    }

    public void setCUF(Integer cUF) {
        this.cUF = cUF;
    }

    public Integer getTpAmb() {
        return tpAmb;
    }

    public void setTpAmb(Integer tpAmb) {
        this.tpAmb = tpAmb;
    }

    public String getXServ() {
        return xServ;
    }

    public void setXServ(String xServ) {
        this.xServ = xServ;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

}