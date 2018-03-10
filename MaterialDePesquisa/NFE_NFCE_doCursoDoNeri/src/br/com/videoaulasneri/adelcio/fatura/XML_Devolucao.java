/*

Descrição: Classe Responsável pela Geração da NFe de Devolução

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_assinatura;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_impdanfe;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_nfeprot;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getFile_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getSenha_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getTipoAmbiente;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.MontarEnviarNFe;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.Component;
import java.sql.Statement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XML_Devolucao extends JFrame {
    private String y_localsgbd = "", y_driver = "", y_url = "", y_usuarioBD = "", y_senhaBD = "", y_drive = "", y_nomebd;
    String nomeArqXML = "", arqSaida, caminho_escolha = "", caminho_grava = "";
    Component componente;
    String fsep = System.getProperty("file.separator");
    Connection con_fat;
    Statement stateDevolu;
    private String nNF, cNF, cDV, NumNFeFat;
    private Date data_emissao;
    String anomesdia = "";
    int empresa = 0;
    private String cUF, CNPJ_emit, mod, serie, Id;
    boolean stat_nfe_fat = false;
    private int cliente = 0, pedido = 0;
    boolean temRegNumNFe = false;
    String tpAmb = "";
    Date data = new Date();
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String strData = sdf.format(data);
    String fuso_horario = "-03:00";

    public void trataXML(
            int empresa, 
            String caminho_escolha, 
            String anomesdia, 
            Connection con_fat, 
            String tpAmb,
            String fuso_horario
            ){
        //String fsep = System.getProperty("file.separator");
        //File file = new File(System.getProperty("user.dir")+fsep+"conexao.xml");
        this.empresa = empresa;
        this.componente = getContentPane();  //componente;
        this.anomesdia = anomesdia;
        this.caminho_escolha = caminho_escolha+fsep+this.anomesdia;
        this.caminho_grava = caminho_escolha.substring(0, caminho_escolha.length()-7);
//JOptionPane.showMessageDialog(null, "XML_Devolucao. Caminho para Gravacao: "+caminho_grava);
        this.con_fat       = con_fat;
        this.tpAmb = tpAmb;
        this.fuso_horario  = fuso_horario;
         
        nomeArqXML = escolheArq();
        try {
            stateDevolu = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "XML_Devolucao. Erro ao criar statement: "+ex);
        }
//JOptionPane.showMessageDialog(null, "XML_Devolucao. Nome do arquivo para leitura: "+nomeArqXML);
        if (nomeArqXML != null) {
//JOptionPane.showMessageDialog(null, "Passou 1 . . .");
            File file = new File(nomeArqXML);
            SAXBuilder builder = new SAXBuilder();
            Document document;
//JOptionPane.showMessageDialog(null, "Passou 2 . . .");

            try{
//JOptionPane.showMessageDialog(null, "Passou 3 . . .");
                document = builder.build(file);
                Element el = document.getRootElement();
//JOptionPane.showMessageDialog(null, "Passou 4 . . .");

               System.out.println("Elemento Raiz: "+document.getRootElement().getName());
    /*
                if (!document.getRootElement().getName().equalsIgnoreCase("Conexao")){
                    JOptionPane.showMessageDialog(null, "Este Documento nao contem informacoes sobre a Conexao!");
                    System.exit(0);
                }
     *
     */
                carrega_parametros();
                carrega_empresa();
                setData_emissao(new Date());  //  (rs_mvfatura.getDate("data_emissao"));
                setnNF(pegar_prox_numNF());
                setcNF(getnNF().substring(1,9));
                criaId();
                arqSaida = caminho_grava+fsep+"E"+getnNF().substring(2,9)+".xml";
                trataElemento(el);
                if (stat_nfe_fat) {
                    gravaSaida(document);
                    atualizar_numeronf();
                    atualizar_nf_fatura();
                    tratar_assinaturaNfe(arqSaida);
                } else {
                    JOptionPane.showMessageDialog(null, "Devolucao Abortada! O XML não será gerado! ");
                }
                //System.exit(0);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "XML_Devolucao. \nErro: "+e);
            }
        }

    }
    public boolean tratar_assinaturaNfe(String arquivoNfe){
        boolean ret_assinar = false;
        //ta_en_envia.append("Assinando a NFe: "+arquivoNfe+"\n");
        //if (getPasta_grava_assinada().equals("")){
        //    setPasta_grava_assinada(escolhe_pasta(caminho_grava_assinada, panel_envia));
        //}
        String chNfe = Biblioteca.extrair_TAG(arquivoNfe, "infNFe", "A", "Id", 1);
        chNfe = chNfe.substring(3, chNfe.length());
        String arq_assinado = getCaminho_assinatura()+"\\"+chNfe+"_sign.xml";
        String arq_prot     = getCaminho_nfeprot()+"\\"+anomesdia+"\\"+chNfe+"_prot.xml"; 
        String arq_danfe           = getCaminho_impdanfe()+"\\"+chNfe+"_danfe.xml";
//System.out.println("arq assinado: "+arq_assinado+"\narq prot: "+arq_prot+"\narq danfe: "+arq_danfe);        
        try {
/*            
JOptionPane.showMessageDialog(null,"MontarEnviarNFe:"
                +"\narquivoNfe: "+arquivoNfe
                + "\narq_assinado: "+arq_assinado 
                + "\narq_danfe: "+arq_danfe 
                + "\narq_prot: "+arq_prot
                + "\ngetFile_keystore(): "+getFile_keystore() 
                + "\ngetSenha_keystore(): "+getSenha_keystore() 
                + "\ngetTipoAmbiente(): "+getTipoAmbiente()
                + "\ncUF: "+cUF
                + "\nNFe: "+"NFe"
                + "\nBiblioteca.pegaEstado(cUF): "+Biblioteca.pegaEstado(cUF)
         + "");
*/            
            MontarEnviarNFe envia = new MontarEnviarNFe(
                arquivoNfe, 
                arq_assinado, 
                arq_danfe, 
                arq_prot,
                getFile_keystore(), 
                getSenha_keystore(), 
                getTipoAmbiente(),
                cUF,
                "NFe",
                Biblioteca.pegaEstado(cUF),
                "",  //  idToken(somente para o mod.65 - NFCe)
                "",   //  csc(somente para o mod.65 - NFCe)  
                null  //  connection  (somente para o mod.65 - NFCe)    
            ); 
            ret_assinar = envia.processa();
            if (ret_assinar) {
                //grava_nfe(Integer.parseInt(chNfe.substring(25,34)));
//System.out.println("Vai chamar o metodo apaga_arquivos_temporarios . . . cam_assinar: "+getCaminho_grava_assinar());            
               //enviaEmailXml(true, arq_prot);
            }
//JOptionPane.showMessageDialog(null,"Passou 3 . . .");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar assinar/gravar nfe: "+ ex);
            //ta_en_envia.append("Erro ao tentar assinar/gravar nfe: "+ ex);
        }
        return ret_assinar;
    }
    
    private void carrega_empresa() {
        String CNPJ = "", xNome = "", xFant = "", xLgr = "", nro = "", xCpl = "", xBairro = "", cMun = "",
                xMun = "", CEP = "", cPais = "", xPais = "", fone = "", IE = "", tpnf = "", dataSaida = "";
       try {
            String sql_emit = "select e.*, ibge.* from empresa e join ibge ibge on e.codcidade = ibge.codigo";
//JOptionPane.showMessageDialog(null, "carrega_empresa() - Comando sql_emit: "+sql_emit);
            ResultSet rs_emit = stateDevolu.executeQuery(sql_emit);
            while(rs_emit.next()){
                CNPJ    = trataTiraPonto(rs_emit.getString("cnpj"));  //("e.cnpj_cpf"));
                nro     = rs_emit.getString("numero");  //("e.numero");
                cMun    = rs_emit.getString("codcidade").substring(0,7);  //("ibge.codcidade");
                //setUF_empresa(rs_emit.getString("uf"));  //("ibge.uf"));
                CEP     = trataTiraPonto(rs_emit.getString("cep"));  //("e.cep");
                cPais   = rs_emit.getString("codigo_pais_nfe");  //("e.codigo_pais_nfe");
                xPais   = "BRASIL";
                fone    = trataTiraPonto(rs_emit.getString("telefone"));  //("e.fone"));
                IE      = trataTiraPonto(rs_emit.getString("inscest"));  //("e.insc_est"));
                tpnf    = rs_emit.getString("tipo_nf");  //("e.tipo_nf");
//JOptionPane.showMessageDialog(null,"trata_empresa.codcidade: "+cMun);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler a tabela empresa. Erro: "+e);
        }

        setCNPJ_emit(CNPJ);//
        //JOptionPane.showMessageDialog(null,"CNPJ Emit: "+getCNPJ_emit());
        //setUF_emit(getUF_empresa());
        //dataSaida = anomesdia.substring(6,8)+"/"+anomesdia.substring(4,6)+"/"+anomesdia.substring(0,4);
        //System.out.println("Anomesdia: ["+anomesdia+"] - Data da Saida: "+dataSaida);

    }
    public void carrega_parametros(){
        //nfemenu.ta_gx_gera.append("\nEntrou no metodo: carregaParamentros() . . .");   //suprimir
        String sql = "";
        try {
            //sql = "select * from paramnfe where empresa = "+ empresa;
//JOptionPane.showMessageDialog(null, "carrega_parametros() - Comando sql: "+sql);
            //ResultSet resultset = stateDevolu.executeQuery(sql);
            //while(resultset.next()){
                setcUF("41");  //(resultset.getString("cuf"));
                setMod("55");  //(resultset.getString("mod"));
                setSerie("001");  //(formataStrEsq(resultset.getString("serie").substring(0,1),3));
            //}

        } catch (Exception ex) {
            //Logger.getLogger(geraXML.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Erro ao tentar carregar dados do parametro! \nErro: "+ex+"\n");
        }

    }

    private String escolheArq(){
        String arquivo = null;
        File[] arquivos = escolhe_arquivo(true, caminho_escolha, JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            for (int i=0;i<arquivos.length;i++){
                arquivo = arquivos[i].getAbsolutePath();
                nomeArqXML = arquivos[i].getName();
                //String nome_arq= arquivos[i].getName();
                //JOptionPane.showMessageDialog(null,"Nome Puro do Arquivo escolhido: " + nomeArqXml);
               //System.out.println("Arquivo escolhido: " + arquivo);
            }
        }
        return arquivo;
    }
    private File[] escolhe_arquivo(boolean multiplo, String caminho, int tipo_arq, String ext_arq){
        System.out.println("Caminho para Leitura: "+caminho);
//            System.out.println("filtro do filechoose: "+"Arquivos "+ext_arq+", "+ext_arq.toLowerCase()+", "+ext_arq.toUpperCase());
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(componente);
            JFileChooser fc_escolha   = new JFileChooser(caminho);
            fc_escolha.setDialogTitle("Escolha o Arquivo para Leitura");
            fc_escolha.setApproveButtonText("Confirma");
            fc_escolha.setFileSelectionMode(tipo_arq);
            //fc_escolha.setDialogType(JFileChooser.CUSTOM_DIALOG);
            if (ext_arq != null) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos "+ext_arq, ext_arq.toLowerCase(), ext_arq.toUpperCase());
                fc_escolha.setFileFilter(filter);
            }
            fc_escolha.setBounds(10, 30, 300,350);
            fc_escolha.setAutoscrolls(true);
            fc_escolha.setMultiSelectionEnabled(multiplo);
            int returnVal = fc_escolha.showOpenDialog(componente);
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(componente);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File[] f_result = fc_escolha.getSelectedFiles();
                //System.out.println("Saiu de Filechooser com OPEN");
                return f_result;
            } else {
                //System.out.println("Saiu de Filechooser com CANCEL");
                return null;
            }
    }
    private void gravaSaida(Document doc) {
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlOutput.output(doc, new FileWriter(arqSaida));
            //JOptionPane.showMessageDialog(null, "Arquivo de Saida: "+arqSaida+" gravado com sucesso!");

        } catch (IOException ex) {
            //Logger.getLogger(XML_Devolucao.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo de saida. \nErro: "+ex);
        }

    }
    private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
        System.out.println("Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
        System.out.println("Tamanho da lista: "+tamanho);
        if (tamanho > 0){  //Elemento
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                String campo = el1.getQualifiedName().toString().trim();
                System.out.println("Elemento:"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());
                if (campo.equalsIgnoreCase(("tpNF"))){
                    el1.setText("0");
                    //System.out.println("Alterou o campo tpNF para: "+el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("natOp"))){
                    el1.setText("DEVOLUCAO DE FATURAMENTO");
                } else
                if (campo.equalsIgnoreCase(("nNF"))){
                    setNumNFeFat(el1.getText());
                    verificaStat_nfe_fat();
//JOptionPane.showMessageDialog(null,"XML_Devolucao -Num.NFe de Faturamento: "+getNumNFeFat());
                    el1.setText(""+(Integer.parseInt(getnNF().substring(0,9))));
                } else
                if (campo.equalsIgnoreCase(("cNF"))){
                    el1.setText(getcNF());  //(getnNF().substring(1,9));
                } else
                if (campo.equalsIgnoreCase(("cDV"))){
                    el1.setText(getcDV());
                } else
                if (campo.equalsIgnoreCase(("dhEmi"))){
                    el1.setText(strData+fuso_horario);    //(getData_emissao().toString());
                } else
                if (campo.equalsIgnoreCase(("dhSaiEnt"))){
                    el1.setText(strData+fuso_horario);  //(getData_emissao().toString());
                } else
                if (campo.equalsIgnoreCase(("finNFe"))){
                    el1.setText("4");
                } else
                if (campo.equalsIgnoreCase(("CFOP"))){
                    if (el1.getText().equals("5102")) {
                        el1.setText("1202");
                    } else
                    if (el1.getText().equals("5403")) {
                        el1.setText("1411");
                    } else
                    if (el1.getText().equals("5405")) {
                        el1.setText("1411");
                    } else
                    if (el1.getText().equals("6102")) {
                        el1.setText("2202");
                    } else
                    if (el1.getText().equals("6403")) {
                        el1.setText("2411");
                    } else
                    if (el1.getText().equals("6405")) {
                        el1.setText("2411");
                    }
                } else
                if (campo.equalsIgnoreCase(("infCpl"))){
                    el1.setText("Devolucao Ref. NFe "+this.getnNF()+" - "+el1.getText());
                } else
                if (
                        campo.equalsIgnoreCase("NFe") ||
                        campo.equalsIgnoreCase("infNFe") ||
                        campo.equalsIgnoreCase("ide") ||
                        campo.equalsIgnoreCase("det") || 
                        campo.equalsIgnoreCase("prod") ||
                        campo.equalsIgnoreCase("cobr") ||
                        campo.equalsIgnoreCase("infAdic")
                        ){
                    if (campo.equalsIgnoreCase("infNFe")){
                        System.out.println("Alterou o atributo id do campo ide . . .");
                        el1.getAttribute("Id").setValue(getId());
                    } else
                    if (campo.equalsIgnoreCase("cobr")){
                        System.out.println("Excluiu o campo dup . . .");
                        el1.removeContent();
                    }
                    trataElemento(el1);
                }
            }
        } else {
        }
        //JOptionPane.showMessageDialog(null,"MÃ©todo XMLReader().trataElemento()\nLocalSGBD: "+getY_localsgbd()+"\nDriver: "+getY_driver()+"\nURL: "+getY_url()+"\nUsuarioBD: "+getY_usuarioBD()+"\nSenhaBD: "+getY_senhaBD()+"\nDrive: "+getY_drive());
    }
//    private String pegar_prox_numNF() {
//        int numnf = 0;
//       try {
////JOptionPane.showMessageDialog(null,"geraXML_fat.pegar_prox_numNF - entrou . . .");
//            String sql_numeronfe = "select max(numeronfe) as ultima_nfe from numeronfe";
////JOptionPane.showMessageDialog(null,"geraXML_fat.pegar_prox_numNF - Comando sql_numeronfe: "+sql_numeronfe);
//            ResultSet rs_numeronfe = stateDevolu.executeQuery(sql_numeronfe);
//            while(rs_numeronfe.next()){
//                numnf = rs_numeronfe.getInt("ultima_nfe");
//            }
//         } catch(Exception e) {
//            JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela: numeronfe. Erro: "+e);
//        }
//        numnf++;
//        String txt_numnf = "";
//        int len = (""+numnf).length();
//        for (int i=(9-len);i>0;i--) {
//           txt_numnf+= "0";
//        }
//       return txt_numnf+numnf;
//    }
    private String pegar_prox_numNF() {
        int numnf = 0;
        temRegNumNFe = false;
       try {
            String sql_numeronfe = "select max(numeronfe) as ultima_nfe from numeronfe where modelonfe = '"+getMod()+"' and ambiente = '"+tpAmb+"' and empresa = "+empresa+" and serienfe = '"+getSerie()+"'";
            ResultSet rs_numeronfe = stateDevolu.executeQuery(sql_numeronfe);
            while(rs_numeronfe.next()){
                numnf = rs_numeronfe.getInt("ultima_nfe");
                temRegNumNFe = true;
            }
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela: numeronfe. Erro: "+e);
        }
        numnf++;
        numnf =confirmaNumeroNFe(""+(numnf));
        String txt_numnf = "";
        int len = (""+numnf).length();
        for (int i=(9-len);i>0;i--) {
           txt_numnf+= "0";
        }
       return txt_numnf+numnf;
    }
    public int confirmaNumeroNFe(String strNumnf){
        while (true){
            strNumnf = JOptionPane.showInputDialog("Confirme o Número desta NFe", strNumnf);
            //System.out.println("Data do Movimento["+dataProc+"]");
            break;
        }
        if (strNumnf == null || strNumnf.equals("")) {
            strNumnf = "0";
        }
        return Integer.parseInt(strNumnf);
    }
    
    private void atualizar_numeronf() {
        try {
            String sql_numeronfe = "";
            if (!temRegNumNFe) {
                sql_numeronfe = "insert into numeronfe ("
                            + "empresa, "
                            + "numeronfe, "
                            + "serienfe, "
                            + "modelonfe, "
                            + "ambiente "
                        + ") values ("
                            + empresa+", "
                            + Integer.parseInt(getnNF())+", "
                            + "'"+getSerie()+"', "
                            + "'"+getMod()+"', "
                            + "'"+tpAmb+"' "
                        + ")";
             System.out.println("Comando sql_insert_numeronfe: "+sql_numeronfe);
            } else {
                sql_numeronfe = "update numeronfe set "
                            + "numeronfe = "+Integer.parseInt(getnNF())
                        + " where "
                            + "empresa = " + empresa+" and "
                            + "serienfe = " + "'"+getSerie()+"' and "
                            + "modelonfe = " + "'"+getMod()+"' and "
                            + "ambiente = "+ "'"+tpAmb+"' "
                        ;
             //System.out.println("Comando sql_update_numeronfe: "+sql_numeronfe);
            }
            int result = stateDevolu.executeUpdate(sql_numeronfe);
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar inserir na tabela: numeronfe. Erro: "+e);
        }
    }
    private void verificaStat_nfe_fat() {
       try {
            Date data_canc = null, data_devo = null;
            stat_nfe_fat = true;
            String sql_fat = "select * from nf where numero_nfe = "+Integer.parseInt(getNumNFeFat());
            ResultSet rs_nf = stateDevolu.executeQuery(sql_fat);
            while(rs_nf.next()){
                data_canc = rs_nf.getDate("data_cancelamento");
                data_devo = rs_nf.getDate("data_devolucao");
                setCliente(rs_nf.getInt("cod_cliente"));
                setPedido(rs_nf.getInt("pedido"));
            }
            if (getPedido() == 0) {
                stat_nfe_fat = false;
                JOptionPane.showMessageDialog(null,"O Pedido não foi encontrado e a NFe não pode ser Devolvida!");
            }
            if (data_canc != null) {
                stat_nfe_fat = false;
                JOptionPane.showMessageDialog(null,"Esta NFe já foi Cancelada em: "+data_canc+" e não pode ser Devolvida!");
            }
            if (data_devo != null) {
                stat_nfe_fat = false;
                JOptionPane.showMessageDialog(null,"Esta NFe já foi Devolvida em: "+data_devo+" e não pode ser Devolvida!");
            }
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela: nf. Erro: "+e);
        }

    }
//        private void atualizar_numeronf() {
//        try {
//            String sql_numeronfe = "insert into numeronfe ("
//                            + "chave_nfe, "
//                            + "numeronfe, "
//                            + "cliente, "
//                            + "pedido, "
//                            + "data_emissao"
//                        + ") values ("
//                            + "'"+getId()+"', "
//                            + "'"+getnNF()+"', "
//                            + "'"+getCliente()+"', "
//                            + "'"+getPedido()+"', "
//                            + "'"+getData_emissao()+"' "
//                        + ")";
//            JOptionPane.showMessageDialog(null,"Comando sql_insert_numeronfe: "+sql_numeronfe);
//            int result = stateDevolu.executeUpdate(sql_numeronfe);
//            JOptionPane.showMessageDialog(null,"Numero da NFe atualizado na tabela: numeronfe!");
//         } catch(Exception e) {
//            JOptionPane.showMessageDialog(null,"Erro ao tentar inserir na tabela: numeronfe. Erro: "+e);
//        }
//    }
    private void atualizar_nf_fatura() {
        try {
            String sql_nf = "update nf set "
                            + "data_devolucao = '"+getData_emissao()+"' "
                            + "where numero_nfe = "+getNumNFeFat();
//JOptionPane.showMessageDialog(null,"Comando sql_nf: "+sql_nf);
            int result = stateDevolu.executeUpdate(sql_nf);
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar na tabela: nf. Erro: "+e);
        }
    }
    public void criaId(){
        String wchave = null;
        try {
            setcNF(getnNF().substring(2,9)+calcdv(getnNF().substring(2,9)));
            wchave = getcUF()
                        +anomesdia.substring(2,6)
                        +getCNPJ_emit()
                        +getMod()
                        +getSerie()
                        +getnNF()
                        +"1"
                        +getcNF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atribuir conteudo para wchave. Erro: "+e);
        }
//        if (display) {
//            JOptionPane.showMessageDialog(null,
//                    "chave: "+wchave
//                    +"\ntamanho da chave: "+wchave.length()
//                    +"\ncUF: "+cUF
//                    +"\nanomes: "+anomesdia.substring(2,6)
//                    +"\ncnpj: "+getCNPJ_emit()
//                    +"\nMod: "+getMod()
//                    +"\nSerie: "+getSerie()
//                    +"\nnNF: "+getnNF()
//                    +"\nForma Emissao: "+"0"
//                    +"\ncNF: "+getcNF()
//                    );
//        }
//        if (display) JOptionPane.showMessageDialog(null,"Vai chamar o metodo calcdv() . . .");
        setcDV(calcdv(wchave));
//        if (display) JOptionPane.showMessageDialog(null,"Voltou do metodo calcdv() . . .");
        wchave = wchave+getcDV();
//        if (display) JOptionPane.showMessageDialog(null,"Chave completa: "+wchave+"\nTamanho: "+wchave.length());
        setId("NFe"+wchave);
        setId("NFe"+wchave);
        //if (display) JOptionPane.showMessageDialog(null,"Vai chamar o metodo trata_ide() . . .");
        //if (display) JOptionPane.showMessageDialog(null,"Voltou do metodo trata_ide() . . .");
    }
    public String calcdv(String wnumero){

        int winic=43-wnumero.length();
//if (display) JOptionPane.showMessageDialog(null,"Metodo calcdv() - Numero: "+wnumero+" - Tamanho: "+wnumero.length()+" - Inicio: "+winic);
        int[] tpeso = {4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2};
        int wsoma=0;
        int indx=0;
        for (int i=winic;i<43;i++){
            wsoma=wsoma+(Integer.parseInt(wnumero.substring(indx,indx+1))*tpeso[i]);
            indx++;
        }
        int wresto=wsoma%11;
        int wdigito=11-wresto;
        if (wdigito>9) wdigito=0;
//if (display) JOptionPane.showMessageDialog(null,"Metodo calcdv() - wdigito: "+wdigito);
        return ""+wdigito;
    }


    public static void main(String[] args){
        XML_Devolucao xr = new XML_Devolucao();
        xr.trataXML(5,"c:","", null, null, null);
    }

    /**
     * @return the nNF
     */
    public String getnNF() {
        return nNF;
    }

    /**
     * @param nNF the nNF to set
     */
    public void setnNF(String nNF) {
        this.nNF = nNF;
    }

    /**
     * @return the data_emissao
     */
    public Date getData_emissao() {
        return data_emissao;
    }

    /**
     * @param data_emissao the data_emissao to set
     */
    public void setData_emissao(Date data_emissao) {
        this.data_emissao = data_emissao;
    }

    /**
     * @return the cNF
     */
    public String getcNF() {
        return cNF;
    }

    /**
     * @param cNF the cNF to set
     */
    public void setcNF(String cNF) {
        this.cNF = cNF;
    }

    /**
     * @return the cDV
     */
    public String getcDV() {
        return cDV;
    }

    /**
     * @param cDV the cDV to set
     */
    public void setcDV(String cDV) {
        this.cDV = cDV;
    }

    /**
     * @return the cUF
     */
    public String getcUF() {
        return cUF;
    }

    /**
     * @param cUF the cUF to set
     */
    public void setcUF(String cUF) {
        this.cUF = cUF;
    }
    public String trataTiraPonto( String wcampo )
    {
            char cpo[] = new char[wcampo.length()];
            int j = 0;
            for ( int i=0; i<wcampo.length(); i++ )
            {
                    if ( wcampo.charAt( i ) == '.' ) {  }
                    else if ( wcampo.charAt( i ) == ' ' ) {  }
                    else if ( wcampo.charAt( i ) == '-' ) {  }
                    else if ( wcampo.charAt( i ) == '(' ) {  }
                    else if ( wcampo.charAt( i ) == ')' ) {  }
                    else if ( wcampo.charAt( i ) == '/' ) {  }
                    else if ( wcampo.charAt( i ) == ',' ) {  }
                    else if ( wcampo.charAt( i ) == '*' ) {  }
                    else { cpo[j] = wcampo.charAt( i ); j++; }
            }
            String resultado = "";
            for (int i=0;i<j;i++)
            {
                    resultado = resultado + cpo[i];
           }
            return resultado;
    }

    /**
     * @return the CNPJ_emit
     */
    public String getCNPJ_emit() {
        return CNPJ_emit;
    }

    /**
     * @param CNPJ_emit the CNPJ_emit to set
     */
    public void setCNPJ_emit(String CNPJ_emit) {
        this.CNPJ_emit = CNPJ_emit;
    }
    private String formataStrEsq(String campo, int tamanho) {
//JOptionPane.showMessageDialog(null, "Metodo formataStrEsq() - campo: "+campo+" - tamanho: "+campo.length());
        String retorno = campo;
        if (campo.length() < tamanho) {
            for (int i=campo.length(); i<tamanho; i++) {
                retorno = "0"+retorno;
//JOptionPane.showMessageDialog(null, "Metodo formataStrEsq() - retorno: "+retorno);
            }
        }
        return retorno;
    }

    /**
     * @return the mod
     */
    public String getMod() {
        return mod;
    }

    /**
     * @param mod the mod to set
     */
    public void setMod(String mod) {
        this.mod = mod;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the NumNFeFat
     */
    public String getNumNFeFat() {
        return NumNFeFat;
    }

    /**
     * @param NumNFeFat the NumNFeFat to set
     */
    public void setNumNFeFat(String NumNFeFat) {
        this.NumNFeFat = NumNFeFat;
    }

    /**
     * @return the cliente
     */
    public int getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the pedido
     */
    public int getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

}
