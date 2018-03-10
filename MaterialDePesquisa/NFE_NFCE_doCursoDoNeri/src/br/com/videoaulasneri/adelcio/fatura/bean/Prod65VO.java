/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura.bean;

public class Prod65VO {
  private int pedido;
  private int item;
  private int cod_produto;
  private int cod_cfop;
  private float quantidade;
  private float peso;
  private double vlr_unitario;
  private double vlr_produto;
  private double vlr_desconto;
  private double vlr_total;
  private boolean cancelado;

  public Prod65VO() {

  }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCod_produto() {
        return cod_produto;
    }

    public void setCod_produto(int cod_produto) {
        this.cod_produto = cod_produto;
    }

    public int getCod_cfop() {
        return cod_cfop;
    }

    public void setCod_cfop(int cod_cfop) {
        this.cod_cfop = cod_cfop;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public double getVlr_unitario() {
        return vlr_unitario;
    }

    public void setVlr_unitario(double vlr_unitario) {
        this.vlr_unitario = vlr_unitario;
    }

    public double getVlr_produto() {
        return vlr_produto;
    }

    public void setVlr_produto(double vlr_produto) {
        this.vlr_produto = vlr_produto;
    }

    public double getVlr_desconto() {
        return vlr_desconto;
    }

    public void setVlr_desconto(double vlr_desconto) {
        this.vlr_desconto = vlr_desconto;
    }

    public double getVlr_total() {
        return vlr_total;
    }

    public void setVlr_total(double vlr_total) {
        this.vlr_total = vlr_total;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

}
