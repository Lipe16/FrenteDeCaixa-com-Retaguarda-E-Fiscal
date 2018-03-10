/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura.bean;

public class ProdImporta {
  private int pedido;
  private int codigoProduto;
  private int prod_item;
  private String prod_ean;
  private String prod_ncm;
  private int prod_cfop;
  private int prod_seqCfop;
  private float prod_qtde;
  private float prod_peso;
  private double prod_vlrUnitario;
  private double prod_vlrProduto;
  private double prod_vlrDesconto;
  private double prod_vlrTotal;
  private String prod_descricao;
  private String prod_unidade;
  private String prod_codProduto;

  public ProdImporta() {

  }

    /**
     * @return the pedido
     */
    public int getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the prod_item
     */
    public int getProd_item() {
        return prod_item;
    }

    /**
     * @param prod_item the prod_item to set
     */
    public void setProd_item(int prod_item) {
        this.prod_item = prod_item;
    }

    /**
     * @return the prod_ean
     */
    public String getProd_ean() {
        return prod_ean;
    }

    /**
     * @param prod_ean the prod_ean to set
     */
    public void setProd_ean(String prod_ean) {
        this.prod_ean = prod_ean;
    }

    /**
     * @return the prod_ncm
     */
    public String getProd_ncm() {
        return prod_ncm;
    }

    /**
     * @param prod_ncm the prod_ncm to set
     */
    public void setProd_ncm(String prod_ncm) {
        this.prod_ncm = prod_ncm;
    }

    /**
     * @return the prod_cfop
     */
    public int getProd_cfop() {
        return prod_cfop;
    }

    /**
     * @param prod_cfop the prod_cfop to set
     */
    public void setProd_cfop(int prod_cfop) {
        this.prod_cfop = prod_cfop;
    }

    /**
     * @return the prod_seqCfop
     */
    public int getProd_seqCfop() {
        return prod_seqCfop;
    }

    /**
     * @param prod_seqCfop the prod_seqCfop to set
     */
    public void setProd_seqCfop(int prod_seqCfop) {
        this.prod_seqCfop = prod_seqCfop;
    }

    /**
     * @return the prod_qtde
     */
    public float getProd_qtde() {
        return prod_qtde;
    }

    /**
     * @param prod_qtde the prod_qtde to set
     */
    public void setProd_qtde(float prod_qtde) {
        this.prod_qtde = prod_qtde;
    }

    /**
     * @return the prod_vlrUnitario
     */
    public double getProd_vlrUnitario() {
        return prod_vlrUnitario;
    }

    /**
     * @param prod_vlrUnitario the prod_vlrUnitario to set
     */
    public void setProd_vlrUnitario(double prod_vlrUnitario) {
        this.prod_vlrUnitario = prod_vlrUnitario;
    }

    /**
     * @return the prod_vlrProduto
     */
    public double getProd_vlrProduto() {
        return prod_vlrProduto;
    }

    /**
     * @param prod_vlrProduto the prod_vlrProduto to set
     */
    public void setProd_vlrProduto(double prod_vlrProduto) {
        this.prod_vlrProduto = prod_vlrProduto;
    }

    /**
     * @return the prod_vlrDesconto
     */
    public double getProd_vlrDesconto() {
        return prod_vlrDesconto;
    }

    /**
     * @param prod_vlrDesconto the prod_vlrDesconto to set
     */
    public void setProd_vlrDesconto(double prod_vlrDesconto) {
        this.prod_vlrDesconto = prod_vlrDesconto;
    }

    /**
     * @return the prod_vlrTotal
     */
    public double getProd_vlrTotal() {
        return prod_vlrTotal;
    }

    /**
     * @param prod_vlrTotal the prod_vlrTotal to set
     */
    public void setProd_vlrTotal(double prod_vlrTotal) {
        this.prod_vlrTotal = prod_vlrTotal;
    }

    /**
     * @return the prod_descricao
     */
    public String getProd_descricao() {
        return prod_descricao;
    }

    /**
     * @param prod_descricao the prod_descricao to set
     */
    public void setProd_descricao(String prod_descricao) {
        this.prod_descricao = prod_descricao;
    }

    /**
     * @return the prod_unidade
     */
    public String getProd_unidade() {
        return prod_unidade;
    }

    /**
     * @param prod_unidade the prod_unidade to set
     */
    public void setProd_unidade(String prod_unidade) {
        this.prod_unidade = prod_unidade;
    }

    /**
     * @return the prod_codProduto
     */
    public String getProd_codProduto() {
        return prod_codProduto;
    }

    /**
     * @param prod_codProduto the prod_codProduto to set
     */
    public void setProd_codProduto(String prod_codProduto) {
        this.prod_codProduto = prod_codProduto;
    }

    /**
     * @return the codigoProduto
     */
    public int getCodigoProduto() {
        return codigoProduto;
    }

    /**
     * @param codigoProduto the codigoProduto to set
     */
    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    /**
     * @return the prod_peso
     */
    public float getProd_peso() {
        return prod_peso;
    }

    /**
     * @param prod_peso the prod_peso to set
     */
    public void setProd_peso(float prod_peso) {
        this.prod_peso = prod_peso;
    }

}
