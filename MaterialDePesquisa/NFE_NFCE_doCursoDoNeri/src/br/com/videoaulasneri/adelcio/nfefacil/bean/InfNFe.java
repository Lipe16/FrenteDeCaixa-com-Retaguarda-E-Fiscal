/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil.bean;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class InfNFe {

	private String Id;
	private String versao;
	private transient String pk_nitem;
	private Ide ide;
	private Emit emit;
        private Dest dest;
        private AutXML autXML;
        private List<Det> listDet;
        private Total total;
        private Transp transp;
        private List<Dup> cobr;
        private List<Pag> listPag;
        private InfAdic infAdic;

	public InfNFe(String Id, String versao, Ide ide, Emit emit, Dest dest, AutXML autXML, List<Det> listDet, Total total,Transp transp,
                      List<Dup> cobr, List<Pag> listPag, InfAdic infAdic) {
		this.versao     = versao;
		this.Id         = Id;
		this.ide        = ide;
                this.emit       = emit;
                this.dest       = dest;
                this.autXML       = autXML;
                this.listDet    = listDet;
                this.total      = total;
                this.transp     = transp;
                this.cobr       = cobr;
                this.listPag    = listPag;
                this.infAdic    = infAdic;
	}
}
