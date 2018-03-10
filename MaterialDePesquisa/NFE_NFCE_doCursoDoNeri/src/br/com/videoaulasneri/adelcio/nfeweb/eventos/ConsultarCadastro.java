package br.com.videoaulasneri.adelcio.nfeweb.eventos;

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.Nfe;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.certUtil;

import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import br.inf.portalfiscal.nfe.schema.consCad.TConsCad;
import br.inf.portalfiscal.nfe.schema.consCad.TConsCad.InfCons;
import br.inf.portalfiscal.nfe.schema.retConsCad.TRetConsCad;
import br.inf.portalfiscal.nfe.schema.consCad.TUfCons;
import br.inf.portalfiscal.www.nfe.wsdl.CadConsultaCadastro2.CadConsultaCadastro2Stub;
import javax.swing.JOptionPane;
public class ConsultarCadastro {
    String caminhoCertificado;
    String senhaCertificado;
    String ambiente;
    Estados estado;
    boolean display = false;
    
    public  ConsultarCadastro(String caminhoCertificado, String senhaCertificado, String ambiente, Estados estado) {
        this.caminhoCertificado = caminhoCertificado;
        this.senhaCertificado   = senhaCertificado;
        this.ambiente           = ambiente;
        this.estado             = estado;
    }
    public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
            final String PastaSchemas = "C:\\nfe\\Schemas";
                Certificado certificado = null;
                if (caminhoCertificado.indexOf("token.cfg") == -1) {  //  o certificado é do tipoA1
                    certificado = CertificadoUtil.certificadoPfx(caminhoCertificado, senhaCertificado);
                } else {  //  o certificado é do tipoA# (Cartão)
                    certificado = CertificadoUtil.certificadoA3(caminhoCertificado, senhaCertificado);
                }
        return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
                            certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);

    }
    public TRetConsCad consultar(String Uf, String cnpj, String cpf) {
        String resultado = "";
        TRetConsCad retorno;
        try{
		if (certUtil == null) {
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - certUtil.iniciaConfiguracoes() . . ."); 
                    certUtil = new CertificadoUtil();
			certUtil.iniciaConfiguracoes();
                }
            //Inicia As Configurações
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 1 . . . estado: " + estado); 

            TConsCad consCad = new TConsCad();
		consCad.setVersao("2.00");

if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 2 . . ."); 
		InfCons infCons = new InfCons();
                if (cnpj != null) {
                    infCons.setCNPJ(cnpj);  //  caso seja cnpj
                } else {
                    infCons.setCPF(cpf); //Caso Seja CPF
                }
		infCons.setXServ("CONS-CAD");
//		infCons.setUF(TUfCons.valueOf(config.getEstado().toString()));configuracoesNfe.getEstado().getCodigoIbge()
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 3.2 . . . UF: "+TUfCons.valueOf(Estados.valueOf(Uf).toString()));  //TUfCons.valueOf(config.getEstado().toString())); 
		infCons.setUF(TUfCons.valueOf(Estados.valueOf(Uf).toString()));

		consCad.setInfCons(infCons);

		retorno = Nfe.consultaCadastro(consCad, false, Uf);
if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 4 . . ."); 
		
//		if(retorno.getInfCons().getCStat().equals("111")){
////			System.out.println("Razão Social: "+retorno.getInfCons().getInfCad().get(0).getXNome());
////			System.out.println("Cnpj:"+retorno.getInfCons().getInfCad().get(0).getCNPJ());
////			System.out.println("Ie:"+retorno.getInfCons().getInfCad().get(0).getIE());
//if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 5.1 . . ."); 
//                        if (cnpj != null) {
//                            resultado =
//                                "Razão Social: "+retorno.getInfCons().getInfCad().get(0).getXNome() +
//                                "\nFantasia: "+retorno.getInfCons().getInfCad().get(0).getXFant() +
//                                "\nCnpj:"+retorno.getInfCons().getInfCad().get(0).getCNPJ() +
//                                " - Insc.Estadual:"+retorno.getInfCons().getInfCad().get(0).getIE();
//                        } else {
//                            resultado = 
//                                "Nome: "+retorno.getInfCons().getInfCad().get(0).getXNome() +
//                                "\nCPF:"+retorno.getInfCons().getInfCad().get(0).getCPF();
//                        }
//                        String situacao = retorno.getInfCons().getInfCad().get(0).getCSit();
//                        String descSituacao = situacao.equals("0") ? "Nao Habilitado" : "Habilitado";
////                        String dataBaixa = retorno.getInfCons().getInfCad().get(0).getDBaixa().toString();
////                        String descDataBaixa = dataBaixa == null ? "" : dataBaixa;
//                        resultado +=  
//                                "\nSituacao:"+situacao + " - "+descSituacao +
//                                "\nInicio Atividade:"+retorno.getInfCons().getInfCad().get(0).getDIniAtiv() +
//                                "\nData Baixa:"+retorno.getInfCons().getInfCad().get(0).getDBaixa() +
//                                "\nEndereco:"+retorno.getInfCons().getInfCad().get(0).getEnder().getXLgr() +
//                                " , "+retorno.getInfCons().getInfCad().get(0).getEnder().getNro() +
//                                " - Bairro: "+retorno.getInfCons().getInfCad().get(0).getEnder().getXBairro() +
//                                 " \nCidade:"+retorno.getInfCons().getInfCad().get(0).getEnder().getXMun() +
//                               " - "+retorno.getInfCons().getInfCad().get(0).getUF();
//
//if (display) JOptionPane.showMessageDialog(null, resultado);
//		}else{
//if (display) JOptionPane.showMessageDialog(null, "ConsultaCadastro - passou 5.2 . . ."); 
//                        resultado = retorno.getInfCons().getCStat()+" - " +retorno.getInfCons().getXMotivo();
//if (display) JOptionPane.showMessageDialog(null, resultado);
//		}


	} catch (NfeException e) {
            retorno = null;
            System.out.println(e.getMessage());
            resultado = "Erro: " + e.getMessage();
            JOptionPane.showMessageDialog(null, resultado);
	}
        return retorno;
    }
}