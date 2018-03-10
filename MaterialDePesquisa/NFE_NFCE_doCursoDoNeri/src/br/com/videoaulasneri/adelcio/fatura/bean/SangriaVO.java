/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;

import java.sql.Date;

public class SangriaVO {
    private Integer id;
    private Date dataSangria;
    private Double valor;
    private Integer codcaixa;
    private Integer codlogin;
    private Integer codgerente;

    public SangriaVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSangria() {
        return dataSangria;
    }

    public void setDataSangria(Date dataSangria) {
        this.dataSangria = dataSangria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getCodcaixa() {
        return codcaixa;
    }

    public void setCodcaixa(Integer codcaixa) {
        this.codcaixa = codcaixa;
    }

    public Integer getCodlogin() {
        return codlogin;
    }

    public void setCodlogin(Integer codlogin) {
        this.codlogin = codlogin;
    }

    public Integer getCodgerente() {
        return codgerente;
    }

    public void setCodgerente(Integer codgerente) {
        this.codgerente = codgerente;
    }


}