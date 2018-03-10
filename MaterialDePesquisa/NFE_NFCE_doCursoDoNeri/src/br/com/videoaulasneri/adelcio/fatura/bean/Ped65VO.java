/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura.bean;

import java.sql.Date;

public class Ped65VO {
  private int pedido;
  private int codlogin;
  private int codcaixa;
  private int codmaquina;
  private String documento;
  private String nome_documento;
  private int cod_forma_pgto;
  private Date data_digitacao;
  private int qtde_itens;
  private double valor_produtos;
  private double valor_descontos;
  private double valor_total;
  private String dados_adicionais;
  //qtde_volume integer DEFAULT 0,
  //peso_volume double precision,
  private int numero_nfe;
  private String serie_nfe;
  private Date data_emissao;
  private String chave_nfe;
  private boolean cancelado;
  private String qrcode;
  private String tpemis;
  private String tpamb;
  private String docAutTEF;
  private String cnpjTEF;

  public Ped65VO() {
      
  }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getCodlogin() {
        return codlogin;
    }

    public void setCodlogin(int codlogin) {
        this.codlogin = codlogin;
    }

    public int getCodcaixa() {
        return codcaixa;
    }

    public void setCodcaixa(int codcaixa) {
        this.codcaixa = codcaixa;
    }

    public int getCodmaquina() {
        return codmaquina;
    }

    public void setCodmaquina(int codmaquina) {
        this.codmaquina = codmaquina;
    }
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getCod_forma_pgto() {
        return cod_forma_pgto;
    }

    public void setCod_forma_pgto(int cod_forma_pgto) {
        this.cod_forma_pgto = cod_forma_pgto;
    }

    public Date getData_digitacao() {
        return data_digitacao;
    }

    public void setData_digitacao(Date data_digitacao) {
        this.data_digitacao = data_digitacao;
    }

    public int getQtde_itens() {
        return qtde_itens;
    }

    public void setQtde_itens(int qtde_itens) {
        this.qtde_itens = qtde_itens;
    }

    public double getValor_produtos() {
        return valor_produtos;
    }

    public void setValor_produtos(double valor_produtos) {
        this.valor_produtos = valor_produtos;
    }

    public double getValor_descontos() {
        return valor_descontos;
    }

    public void setValor_descontos(double valor_descontos) {
        this.valor_descontos = valor_descontos;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public String getDados_adicionais() {
        return dados_adicionais;
    }

    public void setDados_adicionais(String dados_adicionais) {
        this.dados_adicionais = dados_adicionais;
    }

    public String getSerie_nfe() {
        return serie_nfe;
    }

    public void setSerie_nfe(String serie_nfe) {
        this.serie_nfe = serie_nfe;
    }

    public Date getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(Date data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getChave_nfe() {
        return chave_nfe;
    }

    public void setChave_nfe(String chave_nfe) {
        this.chave_nfe = chave_nfe;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    /**
     * @return the nome_documento
     */
    public String getNome_documento() {
        return nome_documento;
    }

    /**
     * @param nome_documento the nome_documento to set
     */
    public void setNome_documento(String nome_documento) {
        this.nome_documento = nome_documento;
    }

    /**
     * @return the qrcode
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * @param qrcode the qrcode to set
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * @return the tpemis
     */
    public String getTpemis() {
        return tpemis;
    }

    /**
     * @param tpemis the tpemis to set
     */
    public void setTpemis(String tpemis) {
        this.tpemis = tpemis;
    }

    /**
     * @return the tpamb
     */
    public String getTpamb() {
        return tpamb;
    }

    /**
     * @param tpamb the tpamb to set
     */
    public void setTpamb(String tpamb) {
        this.tpamb = tpamb;
    }

    /**
     * @return the numero_nfe
     */
    public int getNumero_nfe() {
        return numero_nfe;
    }

    /**
     * @param numero_nfe the numero_nfe to set
     */
    public void setNumero_nfe(int numero_nfe) {
        this.numero_nfe = numero_nfe;
    }

    /**
     * @return the docAutTEF
     */
    public String getDocAutTEF() {
        return docAutTEF;
    }

    /**
     * @param docAutTEF the docAutTEF to set
     */
    public void setDocAutTEF(String docAutTEF) {
        this.docAutTEF = docAutTEF;
    }

    /**
     * @return the cnpjTEF
     */
    public String getCnpjTEF() {
        return cnpjTEF;
    }

    /**
     * @param cnpjTEF the cnpjTEF to set
     */
    public void setCnpjTEF(String cnpjTEF) {
        this.cnpjTEF = cnpjTEF;
    }

}
