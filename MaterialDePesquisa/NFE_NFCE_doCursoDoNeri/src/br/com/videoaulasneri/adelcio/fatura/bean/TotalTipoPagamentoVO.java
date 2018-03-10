/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;

public class TotalTipoPagamentoVO {
    private Integer id;
    private TipoPagamentoVO tipoPagamentoVO = new TipoPagamentoVO();
    private Double valor;


    public TotalTipoPagamentoVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

     public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoPagamentoVO getTipoPagamentoVO() {
        return tipoPagamentoVO;
    }

    public void setTipoPagamentoVO(TipoPagamentoVO tipoPagamentoVO) {
        this.tipoPagamentoVO = tipoPagamentoVO;
    }


 
}