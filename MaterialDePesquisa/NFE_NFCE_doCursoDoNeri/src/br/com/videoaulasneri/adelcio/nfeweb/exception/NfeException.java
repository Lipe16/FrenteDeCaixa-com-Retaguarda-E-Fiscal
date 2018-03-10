/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.exception;

import javax.swing.JOptionPane;

/**
 * Excecao a ser lancada na ocorrencia de falhas provenientes da Nota Fiscal Eletronica.
 * 
 */
public class NfeException extends Exception {

	private static final long serialVersionUID = -5054900660251852366L;
	
	String message;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param e
	 */
	public NfeException(Throwable e) {
		super(e);
	}

	
	/**
	 * Construtor da classe.
	 * 
	 * @param code
	 */
	public NfeException(String message) {
		this((Throwable) null);
		this.message = message;
                JOptionPane.showMessageDialog(null, "Mensagem de Erro: " + message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}