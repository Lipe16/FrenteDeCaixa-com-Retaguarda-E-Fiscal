/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.videoaulasneri.adelcio.fatura.bean;

import java.util.Date;

/**
 *
 * @author Porto
 */
public class LeituraX {
    private String tipo;
    private Date data;
    private double valor;
    private int numeroNFCe;
    private String serieNFCe;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getNumeroNFCe() {
        return numeroNFCe;
    }

    public void setNumeroNFCe(int numeroNFCe) {
        this.numeroNFCe = numeroNFCe;
    }

    public String getSerieNFCe() {
        return serieNFCe;
    }

    public void setSerieNFCe(String serieNFCe) {
        this.serieNFCe = serieNFCe;
    }
    
}
