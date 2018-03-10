/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;

public class VendaDetalheVO {
    private Integer id;
    private Integer cfop;
    private Integer idProduto;
    private Integer idVendaCabecalho;
    private Integer item;
    private Double quantidade;
    private Double valorUnitario;
    private Double valorTotal;
    private Double totalItem;
    private Double baseICMS;
    private Double taxaICMS;
    private Double ICMS;
    private Double taxaDesconto;
    private Double desconto;
    private Double taxaISSQN;
    private Double ISSQN;
    private Double taxaPIS;
    private Double PIS;
    private Double taxaCOFINS;
    private Double COFINS;
    private Double taxaAcrescimo;
    private Double acrescimo;
    private String totalizadorParcial;
    private String CST;
    private String cancelado;
    private String movimentaEstoque;
    private String GTIN;
    private String unidadeProduto;
    private String descricaoPDV;
    private String ECFICMS;
    //apenas para quando for recuperar uma venda perdida
    private String identificacaoCliente;
    private double acrescimoRateio;
    private double descontoRateio;
    private String hashTripa;
    private Integer ccf;
    private Integer coo;
    private String serieEcf;
    private String ecfIcmsSt;
    private Integer hashIncremento;

    public VendaDetalheVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCfop() {
        return cfop;
    }

    public void setCfop(Integer cfop) {
        this.cfop = cfop;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdVendaCabecalho() {
        return idVendaCabecalho;
    }

    public void setIdVendaCabecalho(Integer idVendaCabecalho) {
        this.idVendaCabecalho = idVendaCabecalho;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public Double getBaseICMS() {
        return baseICMS;
    }

    public void setBaseICMS(Double baseICMS) {
        this.baseICMS = baseICMS;
    }

    public Double getTaxaICMS() {
        return taxaICMS;
    }

    public void setTaxaICMS(Double taxaICMS) {
        this.taxaICMS = taxaICMS;
    }

    public Double getICMS() {
        return ICMS;
    }

    public void setICMS(Double ICMS) {
        this.ICMS = ICMS;
    }

    public Double getTaxaDesconto() {
        return taxaDesconto;
    }

    public void setTaxaDesconto(Double taxaDesconto) {
        this.taxaDesconto = taxaDesconto;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getTaxaISSQN() {
        return taxaISSQN;
    }

    public void setTaxaISSQN(Double taxaISSQN) {
        this.taxaISSQN = taxaISSQN;
    }

    public Double getISSQN() {
        return ISSQN;
    }

    public void setISSQN(Double ISSQN) {
        this.ISSQN = ISSQN;
    }

    public Double getTaxaPIS() {
        return taxaPIS;
    }

    public void setTaxaPIS(Double taxaPIS) {
        this.taxaPIS = taxaPIS;
    }

    public Double getPIS() {
        return PIS;
    }

    public void setPIS(Double PIS) {
        this.PIS = PIS;
    }

    public Double getTaxaCOFINS() {
        return taxaCOFINS;
    }

    public void setTaxaCOFINS(Double taxaCOFINS) {
        this.taxaCOFINS = taxaCOFINS;
    }

    public Double getCOFINS() {
        return COFINS;
    }

    public void setCOFINS(Double COFINS) {
        this.COFINS = COFINS;
    }

    public Double getTaxaAcrescimo() {
        return taxaAcrescimo;
    }

    public void setTaxaAcrescimo(Double taxaAcrescimo) {
        this.taxaAcrescimo = taxaAcrescimo;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public String getTotalizadorParcial() {
        return totalizadorParcial;
    }

    public void setTotalizadorParcial(String totalizadorParcial) {
        this.totalizadorParcial = totalizadorParcial;
    }

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getMovimentaEstoque() {
        return movimentaEstoque;
    }

    public void setMovimentaEstoque(String movimentaEstoque) {
        this.movimentaEstoque = movimentaEstoque;
    }

    public String getGTIN() {
        return GTIN;
    }

    public void setGTIN(String GTIN) {
        this.GTIN = GTIN;
    }

    public String getUnidadeProduto() {
        return unidadeProduto;
    }

    public void setUnidadeProduto(String unidadeProduto) {
        this.unidadeProduto = unidadeProduto;
    }

    public String getDescricaoPDV() {
        return descricaoPDV;
    }

    public void setDescricaoPDV(String descricaoPDV) {
        this.descricaoPDV = descricaoPDV;
    }

    public String getECFICMS() {
        return ECFICMS;
    }

    public void setECFICMS(String ECFICMS) {
        this.ECFICMS = ECFICMS;
    }

    public String getIdentificacaoCliente() {
        return identificacaoCliente;
    }

    public void setIdentificacaoCliente(String identificacaoCliente) {
        this.identificacaoCliente = identificacaoCliente;
    }

    public double getAcrescimoRateio() {
        return acrescimoRateio;
    }

    public void setAcrescimoRateio(double acrescimoRateio) {
        this.acrescimoRateio = acrescimoRateio;
    }

    public double getDescontoRateio() {
        return descontoRateio;
    }

    public void setDescontoRateio(double descontoRateio) {
        this.descontoRateio = descontoRateio;
    }

    public String getHashTripa() {
        return hashTripa;
    }

    public void setHashTripa(String hashTripa) {
        this.hashTripa = hashTripa;
    }

    public Integer getCcf() {
        return ccf;
    }

    public void setCcf(Integer ccf) {
        this.ccf = ccf;
    }

    public Integer getCoo() {
        return coo;
    }

    public void setCoo(Integer coo) {
        this.coo = coo;
    }

    public String getSerieEcf() {
        return serieEcf;
    }

    public void setSerieEcf(String serieEcf) {
        this.serieEcf = serieEcf;
    }

    public String getEcfIcmsSt() {
        return ecfIcmsSt;
    }

    public void setEcfIcmsSt(String ecfIcmsSt) {
        this.ecfIcmsSt = ecfIcmsSt;
    }

    public Integer getHashIncremento() {
        return hashIncremento;
    }

    public void setHashIncremento(Integer hashIncremento) {
        this.hashIncremento = hashIncremento;
    }


}