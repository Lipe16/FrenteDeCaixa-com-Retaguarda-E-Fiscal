/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.util;

/**
 *
 */
/**
 * Enum que contÃ©m todos os estados brasileiros.
 *
 */
public enum Estados {

	RO("11","Rondonia"),
	AC("12","Acre"),
	AM("13","Amazonas"),
	RR("14","Roraima"),
	PA("15","Para"),
	AP("16","Amapa"),
	TO("17","Tocantins"),
	MA("21","Maranhao"),
	PI("22","Piaui"),
	CE("23","Ceara"),
	RN("24","Rio Grande do Norte"),
	PB("25","Paraiba"),
	PE("26","Pernambuco"),
	AL("27","Alagoas"),
	SE("28","Sergipe"),
	BA("29","Bahia"),
	MG("31","Minas Gerais"),
	ES("32","Espirito Santo"),
	RJ("33","Rio de Janeiro"),
	SP("35","Sao Paulo"),
	PR("41","Parana"),
	SC("42","Santa Catarina"),
	RS("43","Rio Grande do Sul"),
	MS("50","Mato Grosso do Sul"),
	MT("51","Mato Grosso"),
	GO("52","Goias"),
	DF("53","Distrito Federal");

	private final String codigoIbge;
	private final String nome;

	private Estados(String codigoIbge,String nome) {
		this.codigoIbge = codigoIbge;
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getCodigoIbge(){
		return codigoIbge;
	}
}
