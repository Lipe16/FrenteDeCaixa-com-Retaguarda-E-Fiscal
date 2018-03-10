/*

Descrição: Transmissão / Autorização da Inutilização de Faixa de Numeração de NFe / NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.Nfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import br.inf.portalfiscal.nfe.schema.inutnfe.TInutNFe;
import br.inf.portalfiscal.nfe.schema.inutnfe.TInutNFe.InfInut;
import br.inf.portalfiscal.nfe.schema.retinutnfe.TRetInutNFe;
import javax.swing.JOptionPane;

public class InutilizarNFe {
    String caminhoCertificado;
    String senhaCertificado;
    String ambiente;
    Estados estado;
    boolean display = false;
    
    public  InutilizarNFe(String caminhoCertificado, String senhaCertificado, String ambiente, Estados estado) {
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
/*
            System.out.println("InutilizarNFe - "
                                +"\nNome do certificado: "+certificado.getNome()+"\n"
                                + "Senha do Certificado: "+certificado.getSenha()+"\n"
                                + "Vencimento: "+certificado.getVencimento());
*/
        return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
                            certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);

    }
    public String processar(
            String ufEmissor, 
            String cnpjEmissor,
            String ano,
            String Id,
            String modelo,
            String serie,
            String nfIni,
            String nfFim,
            String motivo
        ) {
        String retorno = "Erro";
        try{
            //Inicia As Configurações
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();

            TInutNFe inutNFe = new TInutNFe();
            inutNFe.setVersao("3.10");

            InfInut infInut = new InfInut();
            infInut.setId(Id);
            //infInut.setTpAmb(config.getAmbiente());
            infInut.setTpAmb(ambiente);
            infInut.setXServ("INUTILIZAR");
            infInut.setCUF(ufEmissor);
            infInut.setAno(ano);

            //Toque X Pelo CNPJ
            infInut.setCNPJ(cnpjEmissor);
            infInut.setMod(modelo);
            infInut.setSerie(serie);

            //Troque x Pelo Numero da Nota
            infInut.setNNFIni(nfIni);
            infInut.setNNFFin(nfFim);

            infInut.setXJust(motivo);
            inutNFe.setInfInut(infInut);
            String tipoNFe;
            if (modelo.equals("55")) {
                tipoNFe = ConstantesUtil.NFE;
            } else {
                tipoNFe = ConstantesUtil.NFCE;
            }
if (display) JOptionPane.showMessageDialog(null, "Vai chamar metodo:Nfe.inutilizacao . . . ");
            //Informe false para não fazer a validação do XML - Ganho De tempo.
            TRetInutNFe retornoRet = Nfe.inutilizacao(inutNFe,false, tipoNFe);
            br.inf.portalfiscal.nfe.schema.retinutnfe.TRetInutNFe.InfInut infRetorno = retornoRet.getInfInut();
            System.out.println("Status:" + infRetorno.getCStat());
            System.out.println("Motivo:" + infRetorno.getXMotivo());
            System.out.println("Data:" + infRetorno.getDhRecbto());
            retorno = infRetorno.getCStat()+" - "+infRetorno.getXMotivo() 
                    + " - Data:" + infRetorno.getDhRecbto()
                    + " - Modelo: "+modelo
                    + " - Serie: "+serie
                    + " - Num.NF Inicial: "+nfIni
                    + " - Num.NF Final: "+nfFim
                    ;

if (display) JOptionPane.showMessageDialog(null, "Status:" + infRetorno.getCStat() 
        + "\nMotivo:" + infRetorno.getXMotivo() 
        + "\nData:" + infRetorno.getDhRecbto()
    );

        } catch (NfeException e) {
            System.out.println("Erro:" + e.getMessage());
        }
        return retorno;
    }
}