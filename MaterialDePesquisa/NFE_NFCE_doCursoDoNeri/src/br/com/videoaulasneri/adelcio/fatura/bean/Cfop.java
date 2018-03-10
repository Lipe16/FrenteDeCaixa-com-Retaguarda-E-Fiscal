/*

Descrição: POJO para a importacao dos codigos fiscais de operacao

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;


public class Cfop {

	private Integer codigo;
	
	private Integer cfop;
	private String descricao;
	private String observacao;
	private String operacao;
        private int seqcfop;
        private boolean faturamento;
        private boolean finenceiro;
/*
 codigo integer NOT NULL,
  cfop integer,
  descricao character varying(120),
  observacao character varying(120),
  faturamento boolean NOT NULL,
  financeiro boolean,
  seqcfop integer,
  operacao character(1),
        
        */
        private String trataString( String wcampo )
        {
		char cpo[] = new char[wcampo.length()];
		int j = 0;
		for ( int i=0; i<wcampo.length(); i++ )
		{
			if ( wcampo.charAt( i ) == '_' )
                        {
                            cpo[j] = ' ';
                            j++;
                        }
                        else if ( wcampo.charAt( i ) == '"' );
                        else if ( wcampo.charAt( i ) == '\'' );
			else
                        {
                            cpo[j] = wcampo.charAt( i );
                            j++;
                        }
		}
		String resultado = "";
		for (int i=0;i<j;i++)
		{
			resultado = resultado + cpo[i];
               }
                return resultado;
        }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCfop() {
        return cfop;
    }

    public void setCfop(Integer cfop) {
        this.cfop = cfop;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public int getSeqcfop() {
        return seqcfop;
    }

    public void setSeqcfop(int seqcfop) {
        this.seqcfop = seqcfop;
    }

    public boolean isFaturamento() {
        return faturamento;
    }

    public void setFaturamento(boolean faturamento) {
        this.faturamento = faturamento;
    }

    public boolean isFinenceiro() {
        return finenceiro;
    }

    public void setFinenceiro(boolean finenceiro) {
        this.finenceiro = finenceiro;
    }


}
