/*

Descrição: POJO para a importacao das Aliquotas de Impostos Federais, Estaduais e Municipais por UF (IBPT)
site para baixar as tabelas por UF: https://deolhonoimposto.ibpt.org.br/Site/PassoPasso

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.bean;

import java.util.Date;


public class Ibpt {

	private String uf;
	private String ncm;
	private String tipo;
	private String descricao;
	private double aliqNacionalFederal;
	private double aliqImportadosFederal;
	private double aliqEstadual;
	private double aliqMunicipal;
        private Date vigenciaInicio;
        private Date vigenciaFim;
	private String chave;
	private String versao;
	private String fonte;
        
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

    /**
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * @return the ncm
     */
    public String getNcm() {
        return ncm;
    }

    /**
     * @param ncm the ncm to set
     */
    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the aliqNacionalFederal
     */
    public double getAliqNacionalFederal() {
        return aliqNacionalFederal;
    }

    /**
     * @param aliqNacionalFederal the aliqNacionalFederal to set
     */
    public void setAliqNacionalFederal(double aliqNacionalFederal) {
        this.aliqNacionalFederal = aliqNacionalFederal;
    }

    /**
     * @return the aliqImportadosFederal
     */
    public double getAliqImportadosFederal() {
        return aliqImportadosFederal;
    }

    /**
     * @param aliqImportadosFederal the aliqImportadosFederal to set
     */
    public void setAliqImportadosFederal(double aliqImportadosFederal) {
        this.aliqImportadosFederal = aliqImportadosFederal;
    }

    /**
     * @return the aliqEstadual
     */
    public double getAliqEstadual() {
        return aliqEstadual;
    }

    /**
     * @param aliqEstadual the aliqEstadual to set
     */
    public void setAliqEstadual(double aliqEstadual) {
        this.aliqEstadual = aliqEstadual;
    }

    /**
     * @return the aliqMunicipal
     */
    public double getAliqMunicipal() {
        return aliqMunicipal;
    }

    /**
     * @param aliqMunicipal the aliqMunicipal to set
     */
    public void setAliqMunicipal(double aliqMunicipal) {
        this.aliqMunicipal = aliqMunicipal;
    }

    /**
     * @return the vigenciaInicio
     */
    public Date getVigenciaInicio() {
        return vigenciaInicio;
    }

    /**
     * @param vigenciaInicio the vigenciaInicio to set
     */
    public void setVigenciaInicio(Date vigenciaInicio) {
        this.vigenciaInicio = vigenciaInicio;
    }

    /**
     * @return the vigenciaFim
     */
    public Date getVigenciaFim() {
        return vigenciaFim;
    }

    /**
     * @param vigenciaFim the vigenciaFim to set
     */
    public void setVigenciaFim(Date vigenciaFim) {
        this.vigenciaFim = vigenciaFim;
    }

    /**
     * @return the chave
     */
    public String getChave() {
        return chave;
    }

    /**
     * @param chave the chave to set
     */
    public void setChave(String chave) {
        this.chave = chave;
    }

    /**
     * @return the versao
     */
    public String getVersao() {
        return versao;
    }

    /**
     * @param versao the versao to set
     */
    public void setVersao(String versao) {
        this.versao = versao;
    }

    /**
     * @return the fonte
     */
    public String getFonte() {
        return fonte;
    }

    /**
     * @param fonte the fonte to set
     */
    public void setFonte(String fonte) {
        this.fonte = fonte;
    }


}
