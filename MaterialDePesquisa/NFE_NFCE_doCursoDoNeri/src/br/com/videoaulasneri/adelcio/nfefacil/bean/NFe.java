/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.nfefacil.bean;

public class NFe {

	private final String xmlns="http://www.portalfiscal.inf.br/nfe"; //\"\nxmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"\nxmlns:xsi=\"www.w3.org/2001/XMLSchema-instance\"";
        private InfNFe infNFe;
        private InfNFeSupl infNFeSupl;

	public NFe(InfNFe infNFe, InfNFeSupl infNFeSupl) {
		this.infNFe = infNFe;
		this.infNFeSupl = infNFeSupl;
	}
	

}
