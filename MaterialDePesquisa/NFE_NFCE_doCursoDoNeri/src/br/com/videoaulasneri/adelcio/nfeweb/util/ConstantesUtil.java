/*

Descrição: Classe que contém as constantes necessárias para a assinatura e autorizacao da NFe / NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.util;

public interface ConstantesUtil {
	
	public static final String NFE = "NFe";
	public static final String NFCE = "NFCe";
	
	interface SERVICOS {
		
		public static final String STATUS_SERVICO = "NfeStatusServico_3.10";
		public static final String ENVIO = "NfeAutorizacao_3.10";
		public static final String CONSULTA_RECIBO = "NFeRetAutorizacao_3.10";
		public static final String URL_QRCODE = "URL-QRCode";
		public static final String URL_CONSULTANFCE = "URL-ConsultaNFCe";
		public static final String CONSULTA_DEST = "ConsultaDest_1.01";
		public static final String EVENTO = "RecepcaoEvento_1.00";
		public static final String INUTILIZACAO = "NfeInutilizacao_3.10";
		public static final String CONSULTA_XML = "NfeConsultaProtocolo_3.10";
		public static final String DISTRIBUICAO_DFE = "NFeDistribuicaoDFe_1.00";
		public static final String DOWNLOAD = "NfeDownloadNF_1.00";
		public static final String MANIFESTACAO = "MANIFESTACAO";
		public static final String CSC = "AdministrarCSCNFCe_1.00";
		public static final String CONSULTA_CADASTRO = "NfeConsultaCadastro_2.00";
		
	}
	
	interface AMBIENTE {
		public static final String HOMOLOGACAO = "2";
		public static final String PRODUCAO = "1";
	}
	
	interface VERSAO{
		public static final String V3_10 = "3.10";
		public static final String V1_00 = "1_00";
	}

}
