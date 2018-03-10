/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb;

import java.time.LocalDate;


public class Certificado {
	
	private static String WINDOWS = "windows";
	private static String ARQUIVO = "arquivo";
	private static String CARTAO = "cartao";

    public static String getWINDOWS() {
        return WINDOWS;
    }

    public static void setWINDOWS(String aWINDOWS) {
        WINDOWS = aWINDOWS;
    }

    public static String getARQUIVO() {
        return ARQUIVO;
    }

    public static void setARQUIVO(String aARQUIVO) {
        ARQUIVO = aARQUIVO;
    }

    public static String getCARTAO() {
        return CARTAO;
    }

    public static void setCARTAO(String aCARTAO) {
        CARTAO = aCARTAO;
    }
	
	private String nome;
	
	private LocalDate vencimento;
	
	private Long diasRestantes;
	
	private String arquivo;
	
	private String senha;
	
	private String tipo;
	
	private boolean valido;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Long getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Long diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }


}
