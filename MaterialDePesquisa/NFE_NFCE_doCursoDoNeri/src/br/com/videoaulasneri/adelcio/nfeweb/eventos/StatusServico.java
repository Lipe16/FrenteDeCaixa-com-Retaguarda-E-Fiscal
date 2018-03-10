/*

Descrição: Classe para Consulta do Status do Serviço da Receita Estadual 

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
import br.inf.portalfiscal.nfe.schema.consstatserv.TConsStatServ;
import br.inf.portalfiscal.nfe.schema.retconsstatserv.TRetConsStatServ;
import java.util.List;
import javax.swing.JOptionPane;

public class StatusServico {
    String caminhoCertificado;
    String senhaCertificado;
    String ambiente;
    Estados estado;
    boolean display = false;

    public StatusServico(String caminhoCertificado, String senhaCertificado, String ambiente, Estados estado) {
        this.caminhoCertificado = caminhoCertificado;
        this.senhaCertificado   = senhaCertificado;
        this.ambiente           = ambiente;
        this.estado             = estado;
    //  a linha abaixo habilita o debug na saida do netbeans    
    //  System.setProperty("javax.net.debug", "all");
    }
    public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
            final String PastaSchemas = "C:\\nfe\\Schemas";
            Certificado certificado = null;
if (display) JOptionPane.showMessageDialog(null, "StatusServico.iniciaConfigurações() - entrou no metodo c/caminho certif.: " + caminhoCertificado + " - senhacertif.: " + senhaCertificado );
            if (caminhoCertificado.indexOf("token.cfg") == -1) {  //  o certificado é do tipoA1
if (display) JOptionPane.showMessageDialog(null, "StatusServico.iniciaConfigurações() - tipo A1. . ." );
                certificado = CertificadoUtil.certificadoPfx(caminhoCertificado, senhaCertificado);
            } else {  //  o certificado é do tipoA# (Cartão)
if (display) JOptionPane.showMessageDialog(null, "StatusServico.iniciaConfigurações() - tipo A3. . ." );
                certificado = CertificadoUtil.certificadoA3(caminhoCertificado, senhaCertificado);
            }
if (display) JOptionPane.showMessageDialog(null, "StatusServico.iniciaConfigurações() - Passou tipo . . ." );
/*
            System.out.println("Nome do certificado: "+certificado.getNome()+"\n"
                                + "Senha do Certificado: "+certificado.getSenha()+"\n"
                                + "Vencimento: "+certificado.getVencimento());
*/
if (display) JOptionPane.showMessageDialog(null, "StatusServico.iniciaConfigurações() - " + "Nome do certificado: "+certificado.getNome()+"\n"
                                + "Senha do Certificado: "+certificado.getSenha()+"\n"
                                + "Vencimento: "+certificado.getVencimento());
            return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
                            certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);
    }
    public String processar(String tipoNFe) {
        String retorno = "erro";
        try {
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 1 . . ." );
                //Inicia As Configurações
                ConfiguracoesIniciaisNfe config = iniciaConfigurações();
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 1.1 . . ." );

                TConsStatServ consStatServ = new TConsStatServ();
                consStatServ.setTpAmb(config.getAmbiente());
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 1.2 . . ." );
                consStatServ.setCUF(config.getEstado().getCodigoIbge());
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 1.3 . . ." );
                consStatServ.setVersao(config.getVersaoNfe());
                consStatServ.setXServ("STATUS");
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 2 . . ." );

                //Informe false para não fazer a validação do XML - Ganho De tempo.
                TRetConsStatServ retornoRet = Nfe.statusServico(consStatServ,false, tipoNFe);
if (display) JOptionPane.showMessageDialog(null, "StatusServico - Passou 3 . . ." );
                System.out.println("Status:" + retornoRet.getCStat());
                System.out.println("Motivo:" + retornoRet.getXMotivo());
                System.out.println("Data:" + retornoRet.getDhRecbto());
            retorno = retornoRet.getCStat()+"-"+retornoRet.getXMotivo();

if (display) JOptionPane.showMessageDialog(null, "Status:" + retornoRet.getCStat() + "\nMotivo:" + retornoRet.getXMotivo() + "\nData:" + retornoRet.getDhRecbto());

            } catch (NfeException e) {
                System.out.println("Erro:" + e.getMessage());
            }
        return retorno;
    }
}