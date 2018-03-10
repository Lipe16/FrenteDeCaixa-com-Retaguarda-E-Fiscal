/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura.bean;

import java.util.Date;

public class PedImporta {
  private int pedido;
  private int cod_cliente;
  private String ide_ambiente;
  private String ide_tipoNfe;
  private String ide_modeloNfe;
  private String ide_codigoPedido;
  private Date ide_dataEmissao;

  private String emit_cnpj;
  private String dest_tipoPessoa;
  private String dest_doc;
  
  private double total_vlrProdutos;
  private double total_vlrDescontos;
  private double total_vlrTotal;
  
  private String transp_cnpj;
  private int transp_codTransp;
  private int transp_qtdeVol;
  private double transp_pesoVol;
  
  private String cobr_codTipoDoc;
  private int cobr_codFormaPgto;
  private Date cobr_dataVctoInicial;
  private double cobr_vlr1aParc;
  
  private String infAdic_descricao;

  public PedImporta() {
      
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
     * @return the ide_ambiente
     */
    public String getIde_ambiente() {
        return ide_ambiente;
    }

    /**
     * @param ide_ambiente the ide_ambiente to set
     */
    public void setIde_ambiente(String ide_ambiente) {
        this.ide_ambiente = ide_ambiente;
    }

    /**
     * @return the ide_tipoNfe
     */
    public String getIde_tipoNfe() {
        return ide_tipoNfe;
    }

    /**
     * @param ide_tipoNfe the ide_tipoNfe to set
     */
    public void setIde_tipoNfe(String ide_tipoNfe) {
        this.ide_tipoNfe = ide_tipoNfe;
    }

    /**
     * @return the ide_modeloNfe
     */
    public String getIde_modeloNfe() {
        return ide_modeloNfe;
    }

    /**
     * @param ide_modeloNfe the ide_modeloNfe to set
     */
    public void setIde_modeloNfe(String ide_modeloNfe) {
        this.ide_modeloNfe = ide_modeloNfe;
    }

    /**
     * @return the ide_codigoPedido
     */
    public String getIde_codigoPedido() {
        return ide_codigoPedido;
    }

    /**
     * @param ide_codigoPedido the ide_codigoPedido to set
     */
    public void setIde_codigoPedido(String ide_codigoPedido) {
        this.ide_codigoPedido = ide_codigoPedido;
    }

    /**
     * @return the emit_cnpj
     */
    public String getEmit_cnpj() {
        return emit_cnpj;
    }

    /**
     * @param emit_cnpj the emit_cnpj to set
     */
    public void setEmit_cnpj(String emit_cnpj) {
        this.emit_cnpj = emit_cnpj;
    }

    /**
     * @return the dest_tipoPessoa
     */
    public String getDest_tipoPessoa() {
        return dest_tipoPessoa;
    }

    /**
     * @param dest_tipoPessoa the dest_tipoPessoa to set
     */
    public void setDest_tipoPessoa(String dest_tipoPessoa) {
        this.dest_tipoPessoa = dest_tipoPessoa;
    }

    /**
     * @return the dest_doc
     */
    public String getDest_doc() {
        return dest_doc;
    }

    /**
     * @param dest_doc the dest_doc to set
     */
    public void setDest_doc(String dest_doc) {
        this.dest_doc = dest_doc;
    }

    /**
     * @return the transp_cnpj
     */
    public String getTransp_cnpj() {
        return transp_cnpj;
    }

    /**
     * @param transp_cnpj the transp_cnpj to set
     */
    public void setTransp_cnpj(String transp_cnpj) {
        this.transp_cnpj = transp_cnpj;
    }

    /**
     * @return the infAdic_descricao
     */
    public String getInfAdic_descricao() {
        return infAdic_descricao;
    }

    /**
     * @param infAdic_descricao the infAdic_descricao to set
     */
    public void setInfAdic_descricao(String infAdic_descricao) {
        this.infAdic_descricao = infAdic_descricao;
    }

    /**
     * @return the total_vlrProdutos
     */
    public double getTotal_vlrProdutos() {
        return total_vlrProdutos;
    }

    /**
     * @param total_vlrProdutos the total_vlrProdutos to set
     */
    public void setTotal_vlrProdutos(double total_vlrProdutos) {
        this.total_vlrProdutos = total_vlrProdutos;
    }

    /**
     * @return the total_vlrDescontos
     */
    public double getTotal_vlrDescontos() {
        return total_vlrDescontos;
    }

    /**
     * @param total_vlrDescontos the total_vlrDescontos to set
     */
    public void setTotal_vlrDescontos(double total_vlrDescontos) {
        this.total_vlrDescontos = total_vlrDescontos;
    }

    /**
     * @return the total_vlrTotal
     */
    public double getTotal_vlrTotal() {
        return total_vlrTotal;
    }

    /**
     * @param total_vlrTotal the total_vlrTotal to set
     */
    public void setTotal_vlrTotal(double total_vlrTotal) {
        this.total_vlrTotal = total_vlrTotal;
    }

    /**
     * @return the cobr_vlr1aParc
     */
    public double getCobr_vlr1aParc() {
        return cobr_vlr1aParc;
    }

    /**
     * @param cobr_vlr1aParc the cobr_vlr1aParc to set
     */
    public void setCobr_vlr1aParc(double cobr_vlr1aParc) {
        this.cobr_vlr1aParc = cobr_vlr1aParc;
    }

    /**
     * @return the ide_dataEmissao
     */
    public Date getIde_dataEmissao() {
        return ide_dataEmissao;
    }

    /**
     * @param ide_dataEmissao the ide_dataEmissao to set
     */
    public void setIde_dataEmissao(Date ide_dataEmissao) {
        this.ide_dataEmissao = ide_dataEmissao;
    }

    /**
     * @return the cobr_dataVctoInicial
     */
    public Date getCobr_dataVctoInicial() {
        return cobr_dataVctoInicial;
    }

    /**
     * @param cobr_dataVctoInicial the cobr_dataVctoInicial to set
     */
    public void setCobr_dataVctoInicial(Date cobr_dataVctoInicial) {
        this.cobr_dataVctoInicial = cobr_dataVctoInicial;
    }

    /**
     * @return the cobr_codTipoDoc
     */
    public String getCobr_codTipoDoc() {
        return cobr_codTipoDoc;
    }

    /**
     * @param cobr_codTipoDoc the cobr_codTipoDoc to set
     */
    public void setCobr_codTipoDoc(String cobr_codTipoDoc) {
        this.cobr_codTipoDoc = cobr_codTipoDoc;
    }

    /**
     * @return the cobr_codFormaPgto
     */
    public int getCobr_codFormaPgto() {
        return cobr_codFormaPgto;
    }

    /**
     * @param cobr_codFormaPgto the cobr_codFormaPgto to set
     */
    public void setCobr_codFormaPgto(int cobr_codFormaPgto) {
        this.cobr_codFormaPgto = cobr_codFormaPgto;
    }

    /**
     * @return the cod_cliente
     */
    public int getCod_cliente() {
        return cod_cliente;
    }

    /**
     * @param cod_cliente the cod_cliente to set
     */
    public void setCod_cliente(int cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    /**
     * @return the transp_codTransp
     */
    public int getTransp_codTransp() {
        return transp_codTransp;
    }

    /**
     * @param transp_codTransp the transp_codTransp to set
     */
    public void setTransp_codTransp(int transp_codTransp) {
        this.transp_codTransp = transp_codTransp;
    }

    /**
     * @return the transp_qtdeVol
     */
    public int getTransp_qtdeVol() {
        return transp_qtdeVol;
    }

    /**
     * @param transp_qtdeVol the transp_qtdeVol to set
     */
    public void setTransp_qtdeVol(int transp_qtdeVol) {
        this.transp_qtdeVol = transp_qtdeVol;
    }

    /**
     * @return the transp_pesoVol
     */
    public double getTransp_pesoVol() {
        return transp_pesoVol;
    }

    /**
     * @param transp_pesoVol the transp_pesoVol to set
     */
    public void setTransp_pesoVol(double transp_pesoVol) {
        this.transp_pesoVol = transp_pesoVol;
    }

}
