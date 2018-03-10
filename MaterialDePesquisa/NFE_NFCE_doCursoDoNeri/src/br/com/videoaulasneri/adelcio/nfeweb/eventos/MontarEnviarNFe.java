/*

Descrição: Transmissão / Autorização da NFe / NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfeweb.eventos;

import javax.xml.bind.JAXBException;
import br.com.videoaulasneri.adelcio.nfeweb.Certificado;
import br.com.videoaulasneri.adelcio.nfeweb.ConfiguracoesIniciaisNfe;
import br.com.videoaulasneri.adelcio.nfeweb.ConsultaRecibo;
import br.com.videoaulasneri.adelcio.nfeweb.Nfe;
import br.com.videoaulasneri.adelcio.nfeweb.exception.NfeException;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import br.com.videoaulasneri.adelcio.nfeweb.util.NFCeUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.WebServiceUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.XmlUtil;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import static br.com.videoaulasneri.adelcio.utilitarios.Biblioteca.normalizeXML;
import br.inf.portalfiscal.nfe.schema.consrecinfe.TConsReciNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TEnviNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe.InfNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe.InfNFe.Dest;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe.InfNFe.Ide;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe.InfNFe.Total;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe.InfNFeSupl;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import br.inf.portalfiscal.nfe.schema.envinfe.TRetEnviNFe;
import br.inf.portalfiscal.nfe.schema.retconsrecinfe.TRetConsReciNFe;
import java.util.Base64;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MontarEnviarNFe {
    String arqSemAssinar;  
    String arqAssinado;  
    String arqDanfe;  
    String arqProt;  
    String caminhoCertificado;  // = "C:\\nfe\\dados\\empr0001\\certificado.pfx";
    String senhaCertificado;  // = "a-senha-aqui";
    String ambiente;
    String xml = "";
    String ufEmissor;
    Estados uf;
    String tipoNFe;
    Estados estado;
    String idToken;
    String csc;
    private ResultSet resultset;
    private Statement statement;
    private Connection connection;
    boolean display = false;
	
	public MontarEnviarNFe(
                    String arqSemAssinar, 
                    String arqAssinado,
                    String arqDanfe,
                    String arqProt,
                    String caminhoCertificado, 
                    String senhaCertificado, 
                    String ambiente,
                    String ufEmissor,
                    String tipoNFe,
                    Estados estado,
                    String idToken,
                    String csc,
                    Connection connection
                ) {
                this.arqSemAssinar          = arqSemAssinar; 
                this.arqAssinado            = arqAssinado;
                this.arqDanfe               = arqDanfe;
                this.arqProt                = arqProt;
                this.caminhoCertificado     = caminhoCertificado;
                this.senhaCertificado       = senhaCertificado;
                this.ambiente               = ambiente;
                this.ufEmissor              = ufEmissor;
                this.tipoNFe                = tipoNFe;
                this.estado                 = estado;
                this.idToken                = idToken;
                this.csc                    = csc;
                this.connection             = connection;
        }
        
        public boolean processa() {
                //pegaEstado();
                try {
                  FileReader arq = new FileReader(arqSemAssinar);
                  BufferedReader lerArq = new BufferedReader(arq);

                  String linha = lerArq.readLine(); // lê a primeira linha
                  while (linha != null) {
                    //System.out.printf("%s\n", linha);
                    xml += linha;

                    linha = lerArq.readLine(); // lê da segunda até a última linha
                  }

                  arq.close();
                  System.out.println( "Xml Sem Assinar:" +xml);
                } catch (IOException e) {
                    System.err.printf("Erro na abertura do arquivo: "+arqSemAssinar+"%s.\n",
                      e.getMessage());
                }
                return assinar_e_gravar(xml);
            
        }
	
	public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
                final String PastaSchemas = "C:\\nfe\\Schemas";
		//Certificado certificado = CertificadoUtil.certificadoPfx(caminhoCertificadoPfx, senhaCertificado);
                Certificado certificado = null;
                if (caminhoCertificado.indexOf("token.cfg") == -1) {  //  o certificado é do tipoA1
                    certificado = CertificadoUtil.certificadoPfx(caminhoCertificado, senhaCertificado);
                } else {  //  o certificado é do tipoA# (Cartão)
                    certificado = CertificadoUtil.certificadoA3(caminhoCertificado, senhaCertificado);
                }
		
                System.out.println("Nome do certificado: "+certificado.getNome()+"\n"
                                + "Senha do Certificado: "+certificado.getSenha()+"\n"
                                + "Vencimento: "+certificado.getVencimento());
		return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(estado, ambiente,
				certificado, PastaSchemas, ConstantesUtil.VERSAO.V3_10);
		
	}
	public boolean assinar_e_gravar(String xml){
            boolean retorno = false;
            try {
                    // Inicia As Configurações
                    ConfiguracoesIniciaisNfe config =  iniciaConfigurações();

                    TEnviNFe enviNFe = new TEnviNFe();

                        // Monta EnviNfe
                        TNFe nfe = XmlUtil.xmlToObject(xml, TNFe.class);

                        enviNFe.setVersao("3.10");
                        enviNFe.setIdLote("1");
//                        enviNFe.setIndSinc("1"); // 0 Assíncrono e 1 Síncrono. 
                        if (ufEmissor.equals("BA") || ufEmissor.equals("29")) {
                            enviNFe.setIndSinc("0");
                        } else {
                            enviNFe.setIndSinc("1");
                        }
if (display) JOptionPane.showMessageDialog(null, "MontatEnviarNFe.assinar() - Uf Emissor: " + ufEmissor + " - tipo de Transmis: " + enviNFe.getIndSinc() );
                        enviNFe.getNFe().add(nfe);

                        // Monta e Assina o XML
                        enviNFe = Nfe.montaNfe(enviNFe , true);
if (display) JOptionPane.showMessageDialog(null, "assinar - Xml Assinado com sucesso!" );
                    InfNFe infNFe   = enviNFe.getNFe().get(0).getInfNFe();
                    Dest dest       = enviNFe.getNFe().get(0).getInfNFe().getDest();
                    Ide ide         = enviNFe.getNFe().get(0).getInfNFe().getIde();
                    Total total     = enviNFe.getNFe().get(0).getInfNFe().getTotal();
                    String cDestCnpjCpf = "";
                    int numeroNFe   = Integer.parseInt(ide.getNNF());
                    String serie    = ide.getSerie();
                    if (tipoNFe.equals(ConstantesUtil.NFCE)) {
                        try {
                            //QRCODE
                            for (int i=serie.length(); i<3; i++) {
                                serie = "0"+serie;
                            }
                            if (dest != null && (dest.getCNPJ() != null || dest.getCPF() != null)) {
                                cDestCnpjCpf = dest.getCNPJ() == null ? dest.getCPF() : dest.getCNPJ();
                            }
System.out.println("Qrcode(1): "+infNFe.getId().substring(3));
System.out.println("Qrcode(2): "+"100");
System.out.println("Qrcode(3): "+ambiente);
System.out.println("Qrcode(4): "+cDestCnpjCpf);
System.out.println("Qrcode(5): "+ide.getDhEmi());
System.out.println("Qrcode(6): "+total.getICMSTot().getVNF());
System.out.println("Qrcode(7): "+total.getICMSTot().getVICMS());
System.out.println("Qrcode(8): "+Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()));
System.out.println("Qrcode(9): "+idToken);
System.out.println("Qrcode(10): "+csc);
System.out.println("Qrcode(11): "+WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.URL_QRCODE));

                            String qrCode = NFCeUtil.getCodeQRCode(
                                    infNFe.getId().substring(3), 
                                    "100", 
                                    ambiente, 
                                    cDestCnpjCpf, 
                                    ide.getDhEmi(), 
                                    total.getICMSTot().getVNF(), 
                                    total.getICMSTot().getVICMS(),
                                    Base64.getEncoder().encodeToString(enviNFe.getNFe().get(0).getSignature().getSignedInfo().getReference().getDigestValue()), 
                                    idToken, 
                                    csc, 
                                    WebServiceUtil.getUrl(ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.URL_QRCODE)
                            );
//JOptionPane.showMessageDialog(null, "MontarEnviarNFe - qrcode: " + qrCode);                            
                            gravaQrcodeNaNf(qrCode, numeroNFe, serie);
                            InfNFeSupl infNFeSupl = new InfNFeSupl();
                            
                            infNFeSupl.setQrCode(qrCode);
                            enviNFe.getNFe().get(0).setInfNFeSupl(infNFeSupl);
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(null, "MontarEnviarNFe - Erro na montagem do QRCode: "+e);
                        }
                    }

                    //System.out.println( "Xml Assinado:" + XmlUtil.objectToXml(enviNFe));
                    gravarXML(XmlUtil.objectToXml(enviNFe), arqAssinado);
                    gravarXML(XmlUtil.objectToXml(enviNFe), arqDanfe);
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - vai chamar Nfe.enviarNfe com tipoNFe: "+tipoNFe);
                    // Envia a Nfe para a Sefaz
                    TRetEnviNFe retornoEnv = Nfe.enviarNfe(enviNFe, tipoNFe);
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com retorno: "+retornoEnv);
                    String status = "", motivo = "", data = "", protocolo = "";
                    if ( retornoEnv.getProtNFe() != null) {
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com protocolo . . . ");
                        status = retornoEnv.getProtNFe().getInfProt().getCStat();
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com status: "+status);
                        motivo = retornoEnv.getProtNFe().getInfProt().getXMotivo();
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com motivo: "+motivo);
                        data = retornoEnv.getProtNFe().getInfProt().getDhRecbto();
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com data: "+data);
                        protocolo = retornoEnv.getProtNFe().getInfProt().getNProt();
                        System.out.println(XmlUtil.criaNfeProc(enviNFe, retornoEnv.getProtNFe()));
                        
                        gravarXML(XmlUtil.criaNfeProc(enviNFe, retornoEnv.getProtNFe()), arqProt);
                        retorno = true;
                    } else {
//  inicio da rotina para metodo assincrono 27/05/2017
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com status: "+retornoEnv.getCStat());
                        if(retornoEnv.getCStat().equals("103") || retornoEnv.getCStat().equals("776")){
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - SEM PROTOCOLO - retornou com status: "+retornoEnv.getCStat()+" - infRec(): "+retornoEnv.getInfRec());

                            String recibo = retornoEnv.getInfRec().getNRec();
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - SEM PROTOCOLO - passou 1 . . . ");

                            TConsReciNFe consReciNFe = new TConsReciNFe();
                            consReciNFe.setVersao("3.10");  //(config.getVersaoNfe());
                            consReciNFe.setTpAmb(ambiente);  //(config.getAmbiente());
                            consReciNFe.setNRec(recibo);
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - SEM PROTOCOLO - passou 2 . . . ");

                            TRetConsReciNFe retornoNfe = new TRetConsReciNFe();
                            while(true){
                                retornoNfe = ConsultaRecibo.reciboNfe(consReciNFe, false, tipoNFe); 
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - while(true) retornou com status: "+retornoNfe.getCStat());
                                if(retornoNfe.getCStat().equals("105")){
                                    try {
                                        System.out.println("Lote Em Processamento, vai tentar novamente apos 2 Segundo.");
                                        Thread.sleep(3000);
                                        continue;
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(EnvioAssincrono.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }else{
                                    System.out.println("MontarEnviarNFe - break - status: "+retornoNfe.getCStat());
                                    System.out.println("MontarEnviarNFe - break - motivo: "+retornoNfe.getXMotivo());
                                    break;
                                }
                            }

if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - saiu do while(true) com status: "+retornoNfe.getCStat());
                            if(retornoNfe.getCStat().equals("104")){
                                System.out.println("Status: " + retornoNfe.getProtNFe().get(0).getInfProt().getCStat() + " - " + retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo());
                                System.out.println("Data: " + retornoNfe.getProtNFe().get(0).getInfProt().getDhRecbto());
                                System.out.println("Protocolo: " + retornoNfe.getProtNFe().get(0).getInfProt().getNProt());
                        status = retornoNfe.getProtNFe().get(0).getInfProt().getCStat();
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com status: "+status);
                        motivo = retornoNfe.getProtNFe().get(0).getInfProt().getXMotivo();
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com motivo: "+motivo);
                        data = retornoNfe.getProtNFe().get(0).getInfProt().getDhRecbto();
if (display) 
JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com data: "+data);
                        protocolo = retornoNfe.getProtNFe().get(0).getInfProt().getNProt();

//                        JOptionPane.showMessageDialog(null, "Status:" + status 
//                                + "\nMotivo:" + motivo
//                                + "\nData:" + data
//                                + "\nProtocolo:" + protocolo
//                            );
 
if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - Vai chamar metodo para gravar NFeProt . . . ");
                        gravarXML(XmlUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)), arqProt);

                                try {
                                    System.out.println("XML Final: " + XmlUtil.criaNfeProc(enviNFe, retornoNfe.getProtNFe().get(0)));
                                } catch (JAXBException ex) {
                                    Logger.getLogger(EnvioAssincrono.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                retorno = true;
                            } else {
                                retorno = false;
                            }
                        } 
                        if (!retorno) {
//  fim da rotina para metodo assincrono 27/05/2017                        
                            if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe SEM protocolo . . . ");
                            status = retornoEnv.getCStat();
    if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com status: "+status);
                            motivo = retornoEnv.getXMotivo();
    if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com motivo: "+motivo);
                            data = retornoEnv.getDhRecbto();
    if (display) JOptionPane.showMessageDialog(null, "MontarEnviarNFe - voltou de Nfe.enviarNfe com data: "+data);
                            retorno = false;
// copia temporaria. apagar a linha abaixo apos o teste de CDATA para qrCode                        
                        //gravarXML(XmlUtil.objectToXml(enviNFe), arqProt);
                        }
                    }
                    System.out.println("Status:" + status);
                    System.out.println("Motivo:" + motivo);
                    System.out.println("Data:" + data);
                    System.out.println("Protocolo:" + protocolo);
/*
                    if (!ambiente.equals("1") || (ambiente.equals("1") && status.indexOf("100") == -1)) {
                        JOptionPane.showMessageDialog(null, "Status:" + status 
                                + "\nMotivo:" + motivo
                                + "\nData:" + data
                                + "\nProtocolo:" + protocolo
                            );
                    }
*/
                    if (!status.equals("100")) {
                        JOptionPane.showMessageDialog(null, "Esta NFCe NÃO foi Autorizada. Status: " + status + " - Motivo: " + motivo);
                    }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MontarEnviarNFe.class.getName()).log(Level.SEVERE, null, ex);
                }
                    gravaMotivoNaNf(status, motivo, numeroNFe, serie);

            } catch (NfeException | JAXBException e) {
                    JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
                    retorno = false;
            }
            return retorno;
        }
        void gravarXML(String texto, String nomeArq) {
if (display) JOptionPane.showMessageDialog(null, "Entrou no metodo: MontarEnviarNFe.gravarXML() . . . Gravando arq. na pasta NFeProt . . .");           
            String txtForm = Biblioteca.formataTexto(texto);
            try {
             byte [] textobyte = txtForm.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(nomeArq);
                arquivo_gerado.write(textobyte);
                arquivo_gerado.close();
if (display) JOptionPane.showMessageDialog(null, "Metodo: MontarEnviarNFe.gravarXML() - gravou arq.: "+nomeArq);           
        
                //JOptionPane.showMessageDialog(null, "Caminho do Arquivo Assinado: "+arqAssinado);
            } catch (Exception io) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo: "+io);
            }
        }

        public static void main(String[] args) {
	}

        private void gravaQrcodeNaNf(String qrCode, int numeroNFCe, String serie) {
            String sql_update = "update nf set"
                    +" qrcode = '" + qrCode + "' "
                    +" where  " 
                    +" numero_nfe = " + numeroNFCe + " "
                    +" and serie_nfe = '" + serie + "' "
                    +" and modelonfe = '65'";
                    ;
//JOptionPane.showMessageDialog(null,"Comando numero_nfe: "+numeroNFCe + " = serie_nfe: " + serie);
                System.out.println("MontarEnviarNFe.gravaQrcodeNaNf() - Comando sql_update: "+sql_update);
            try {
                Statement stateUpFat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                int result = stateUpFat.executeUpdate(sql_update);
            } catch (Exception ex) {
    JOptionPane.showMessageDialog(null,"MontarEnviarNFe.gravaQrcodeNaNf() - Erro ao tentar gravar o qrcode na tabela nf. Erro: "+ ex);
            }
            
        }
        private void gravaMotivoNaNf(String status, String motivo, int numeroNFe, String serie) {
            String modeloNfe = "55";
            if (tipoNFe.equals(ConstantesUtil.NFCE)) {
                modeloNfe = "65";
            }
            String sql_update = "update nf set"
                    +" status_nfe = '" + status + "', "
                    +" motivo_nfe = '" + motivo + "' "
                    +" where  " 
                    +" numero_nfe = " + numeroNFe + " "
                    +" and serie_nfe = '" + serie + "' "
                    +" and modelonfe = '" + modeloNfe + "'";
                    ;
//JOptionPane.showMessageDialog(null,"Comando numero_nfe: "+numeroNFCe + " = serie_nfe: " + serie);
                System.out.println("MontarEnviarNFe.gravaMotivoNaNf() - Comando sql_update: "+sql_update);
            try {
                Statement stateUpFat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                int result = stateUpFat.executeUpdate(sql_update);
            } catch (Exception ex) {
    //JOptionPane.showMessageDialog(null,"MontarEnviarNFe.gravaMotivoNaNf() - Erro ao tentar gravar o motivo na tabela nf. Erro: "+ ex + "\n comando: " + sql_update);
            }
            
        }
}
