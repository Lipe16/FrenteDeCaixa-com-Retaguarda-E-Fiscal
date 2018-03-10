/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;


public class Ibge {

	private Integer codigo;
	
	private Integer codcidade;
	private String cidade;
	private String uf;
	private String distrito;
        private int ufNum;
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getCodcidade() {
		return codcidade;
	}
	public void setCodcidade(Integer codcidade) {
		this.codcidade = codcidade;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = trataString(cidade);
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = trataString(distrito);
	}
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

    public int getUfNum() {
        return ufNum;
    }

    public void setUfNum(int ufNum) {
        this.ufNum = ufNum;
    }

}
