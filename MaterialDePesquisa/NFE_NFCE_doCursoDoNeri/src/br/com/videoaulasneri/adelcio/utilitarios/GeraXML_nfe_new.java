/*

Descrição: Esta classe le a tabela de pedidos digitados e gera o XML para a NFe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_assinatura;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_grava_assinar;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_impdanfe;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_nfeprot;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getFile_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getPasta_grava_assinada;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getSenha_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getTipoAmbiente;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.panel_envia;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.setPasta_grava_assinada;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.ta_en_envia;
import com.thoughtworks.xstream.XStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINS;
import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINSNT;
import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINSAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS00;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS10;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS20;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS40;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN101;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN102;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS51;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS60;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSTot;
import br.com.videoaulasneri.adelcio.nfefacil.bean.NFe;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PIS;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISNT;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Dest;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Det;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Dup;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Emit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.EnderDest;
import br.com.videoaulasneri.adelcio.nfefacil.bean.EnderEmit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Ide;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Imposto;
import br.com.videoaulasneri.adelcio.nfefacil.bean.InfAdic;
import br.com.videoaulasneri.adelcio.nfefacil.bean.InfNFe;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Prod;
import br.com.videoaulasneri.adelcio.nfefacil.bean.DI;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN500;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Total;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Transp;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Transporta;
import br.com.videoaulasneri.adelcio.nfefacil.bean.VeicTransp;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Vol;
import br.com.videoaulasneri.adelcio.nfefacil.bean.NFref;
import br.com.videoaulasneri.adelcio.nfefacil.bean.InfNFeSupl;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Pag;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.MontarEnviarNFe;
import br.com.videoaulasneri.adelcio.nfefacil.bean.AutXML;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JFileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeraXML_nfe_new {
    ResultSet resultset, result_det;
    Statement statement, stateUpItem, stateUpFat, stateFatura, stateItem;
    Connection con_fat;
    String driver, url, usuario, senha;
    String caminho_ler_assinar;
    String dataGera = "";
    String tpAmb, drive, txt, anomesdia, dataSaida;
    private String Id,indpag, CPF_dest, CNPJ_dest, xNome_dest, IdDest, IndIEDest, IE_dest, email_dest, nNF, natOp, cDV, cUF, mod, serie, CNPJ_emit, cNF, UF_emit;
    private String cmunfg, tpimp, tpemis, finnfe, procemis, verproc, indFinal;
    //Vector vetor_det   = new Vector();  //implementar
    //Emitente emitente;
    int nItem;

    int empresa;
    Ide ide;
    Emit emit;
    EnderEmit enderEmit;
    Dest dest;
    EnderDest enderDest;
    AutXML autXML;
    Det det;
    //private List<Object> listDet;
    List<Det> ldet = new ArrayList<Det>();
    Prod prod;
    DI DI;
    Imposto imposto;
    PIS pis;
    PISNT pisNT;
    PISAliq pisAliq;
    ICMS icms;
    ICMS00 icms00;
    ICMS10 icms10;
    ICMS20 icms20;
    ICMS40 icms40;
    ICMSSN101 icmssn101;
    ICMSSN102 icmssn102;
    ICMSSN500 icmssn500;
    ICMS51 icms51;
    ICMS60 icms60;
    COFINS cofins;
    COFINSNT cofinsNT;
    COFINSAliq cofinsAliq;
    Total total;
    ICMSTot ICMSTot;
    Transp transp;
    Transporta transporta;
    VeicTransp veictransp;
    Vol vol;
    NFref nfref;
    Dup dup;
    List<Dup> cobr = new ArrayList<Dup>();
    Pag pag;
    List<Pag> lpag = new ArrayList<Pag>();
    InfAdic infAdic;
    InfNFeSupl infNFeSupl;
    String vProdTrib, vTotTrib;

    Connection consis;
    boolean nao_pegou_natOp;
    private int cliente, pedido, sequencia, item, banco, mvfatura, vendedor;
    private Date data_emissao, data_movimento;
    private String UF_clie, UF_empresa;
    private String modBC, tipo_cobranca;
    //private double perc_pis, perc_cofins;
    private BigDecimal total_vbc = BigDecimal.ZERO;
    private BigDecimal total_vicms = BigDecimal.ZERO;
    private BigDecimal total_vbcst = BigDecimal.ZERO;
    private BigDecimal total_vST = BigDecimal.ZERO;
    private BigDecimal total_vProd = BigDecimal.ZERO;
    private BigDecimal total_vFrete = BigDecimal.ZERO;
    private BigDecimal total_vSeg = BigDecimal.ZERO;
    private BigDecimal total_vDesc = BigDecimal.ZERO;
    private BigDecimal total_vII = BigDecimal.ZERO;
    private BigDecimal total_vIPI = BigDecimal.ZERO;
    private BigDecimal total_vPIS = BigDecimal.ZERO;
    private BigDecimal total_vCOFINS = BigDecimal.ZERO;
    private BigDecimal total_vOutro = BigDecimal.ZERO;
    private BigDecimal total_vNF = BigDecimal.ZERO;
    private String desc_CFOP;
    private boolean consu_final = false, diferido = false;
    private String sit_trib;
    private boolean display = false;  //  variavel para definir se deve exibir o display de andamento do processo
    private String dhCont;
    private String xJust;
    private String carroIni, carroFim;
    private String UfEmit, UfDest;
    private String modelonfe = "55";
    private String wchave = null;
    String fuso_horario = "-03:00";
    boolean temRegNumNFe = false;
    String fsep = System.getProperty("file.separator");
    String arq_danfe = "";
    int codigoEmp = 0;
    private int forma_pgto = 0;
    private String arqXML = "C://nfe//arquivo.XML";

    //Biblioteca.FormataString fs = new Biblioteca.FormataString();
    public GeraXML_nfe_new(
            String tpAmb,
            String caminho_ler_assinar,
            Connection con_fat,
            int empresa,
            String drive,
            String anomesdia,
            boolean display,
//            String pedido,
            String UfEmit,
//            String UfDest,
            String fuso_horario,
            String arqXML
        ){
        try {
         this.tpAmb                 = tpAmb;
         this.caminho_ler_assinar   = caminho_ler_assinar;
         this.con_fat               = con_fat;
         this.empresa               = empresa;
         this.drive                 = drive;
         this.anomesdia             = anomesdia;
         this.display               = display;
//         this.pedido                = Integer.parseInt(pedido);
         this.UfEmit                = UfEmit;
         this.UfDest                = UfDest;
         this.fuso_horario          = fuso_horario;
         this.arqXML                = arqXML;
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"geraXML_fat() - Erro: "+ex);

        }
        this.display = false;  //  escolheExibirDisplay();
     }
    public String trataTexto(String txtParam){
//JOptionPane.showMessageDialog(null,"geraXML_fat().trataTexto() Valor de display: "+display);
        if (display)
            JOptionPane.showMessageDialog(null,"geraXML_fat().trataTexto() Entrou no metodo . . .");
        try {
        if (display)
            JOptionPane.showMessageDialog(null,"geraXML_fat().trataTexto() c/anomesdia: "+anomesdia+" e texto: "+txtParam);
            setData_movimento(new java.sql.Date(new SimpleDateFormat("yyyyMMdd").parse(anomesdia).getTime()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"geraXML_fat().trataTexto() Erro: "+ex);
        }
        this.txt = txtParam;
        ldet = null; //.clear();
        cobr = null; //.clear();
        ldet = new ArrayList<Det>();
        cobr = new ArrayList<Dup>();
        lpag = new ArrayList<Pag>();
        infNFeSupl = null;
        nItem = 0;

        String retorno = "";
        if (!trata_fatura()) {
            retorno = "erro";
        }

        if (display) JOptionPane.showMessageDialog(null,"Vai finalizar GeraXML_fat.trataTexto() . . .");
        //boolean continua = false;
        //if (!continua) {
        //    return("Finalizacao programada durante testes . . .");
        //}
        return retorno;
    }
    private boolean escolheExibirDisplay() {
       boolean retorno = false;
       UIManager.put("OptionPane.yesButtonText", "Exibir Mensagens");
       UIManager.put("OptionPane.noButtonText", "Não Exibir");
       UIManager.put("OptionPane.cancelButtonText", "Não Gerar XML");
       int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Exibir Mensagens Passo-a-passo", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
       if ( escolha == JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null,"Escolheu exibir Mensagens Passo-a-passo");
            retorno = true;
       } else if ( escolha == JOptionPane.NO_OPTION){
            JOptionPane.showMessageDialog(null, "As Mensagens passo-a-passo não serão exibidas!");
            retorno = false;
       } else {
            JOptionPane.showMessageDialog(null, "Vode Cancelou e o arq. XML não será gerado!");
           System.exit(0);
       }

       UIManager.put("OptionPane.yesButtonText", "Sim");
       UIManager.put("OptionPane.noButtonText", "Não");

        return retorno;
    }
    private boolean trata_fatura(){
        boolean retorno = true;
        try {
            statement = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stateItem = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stateFatura = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stateUpItem = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stateUpFat = con_fat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs_emp = stateFatura.executeQuery("select * from empresa where codempresa = "+ empresa);
            while (rs_emp.next()) {
                codigoEmp = rs_emp.getInt("codigo");
            }
            //carrega_parametros();
            if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - entrou . . .");
        File f = new File(arqXML);  
        if (f.exists()) {
//            carrega_parametros();
            setData_emissao(new Date());  //  (rs_mvfatura.getDate("data_emissao"));
            setnNF(pegar_prox_numNF());
            setPedido(pegarProxPedido());
System.out.println("Numero da NFe confirmado: "+Integer.parseInt(getnNF()));                    
            if (Integer.parseInt(getnNF()) == 0) {
                JOptionPane.showMessageDialog(null,"O Número da NFe não foi informado e o Arquivo XML não será gerado!");
                return false;
            }
            setIndpag("1");       //1^
            String wserie = "";
            String sql = "select * from pardigital where empresa = "+ codigoEmp;
            if (display) JOptionPane.showMessageDialog(null,"Comando sql_pardigital: "+sql);
            resultset = statement.executeQuery(sql);
            while(resultset.next()){
                setcUF(UfEmit);
                setMod(modelonfe);  //(resultset.getString("mod"));
                setSerie(formataStrEsq(resultset.getString("serie55").trim(),3));
                wserie = resultset.getString("serie55");
                setTpimp("1");  //(resultset.getString("tpimp"));
                setTpemis("1");
                setFinnfe("1");  //(resultset.getString("finnfe"));
                setProcemis("0");  //(resultset.getString("procemis"));
                setVerproc(resultset.getString("verproc"));
            }
//  inicio rs_mvfatura aqui
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Processando o pedido: "+getPedido());
//  comentado temporariamente. Falta tratar.
    setCliente(0);  //(rs_mvfatura.getInt("cod_cliente"));
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Pegou cod_cliente: "+getCliente());
                    setData_emissao(new Date());  
//  comentado temporariamente. Falta tratar.
                   setBanco(0);  //(rs_mvfatura.getInt("cod_banco"));
//  comentado temporariamente. Falta tratar.
                    setTipo_cobranca("");  //(rs_mvfatura.getString("cod_tipo_doc"));
                    setnNF(pegar_prox_numNF());
//  comentado temporariamente. Falta tratar.
                    setForma_pgto(0);  //(rs_mvfatura.getInt("cod_forma_pgto"));
System.out.println("Numero da NFe confirmado: "+Integer.parseInt(getnNF()));                    
                    if (Integer.parseInt(getnNF()) == 0) {
                        JOptionPane.showMessageDialog(null,"O Número da NFe não foi informado e o Arquivo XML não será gerado!");
                        return false;
                    }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Pegou numero nfe: "+getnNF());
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Vai executar metodo trata_emit() . . .");
                    trata_emit();
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Vai executar metodo trata_dest() . . .");
                    trata_dest();

                    total_vbc       = BigDecimal.ZERO;
                    total_vicms     = BigDecimal.ZERO;
                    total_vbcst     = BigDecimal.ZERO;
                    total_vST       = BigDecimal.ZERO;
                    total_vProd     = BigDecimal.ZERO;
                    total_vFrete    = BigDecimal.ZERO;
                    total_vSeg      = BigDecimal.ZERO;
                    total_vDesc     = BigDecimal.ZERO;
                    total_vII       = BigDecimal.ZERO;
                    total_vIPI      = BigDecimal.ZERO;
                    total_vPIS      = BigDecimal.ZERO;
                    total_vCOFINS   = BigDecimal.ZERO;
                    total_vOutro    = BigDecimal.ZERO;
                    total_vNF       = BigDecimal.ZERO;

     //  utilizar array para guardar apenas uma msg de cada sit.trib. diferente
                    setSit_trib("");  //("Falta montar texto da sit.tributaria");
                    List<Integer> listTrib = new ArrayList<Integer>(); ;
                    List<String> listAliq = new ArrayList<String>(); ;;
                    nao_pegou_natOp = true;
                    BigDecimal vlrProd = BigDecimal.ZERO;
                    BigDecimal vlrDesc = BigDecimal.ZERO;
                    try {
                        String sql_item = "select * from nf_produtos"
                                +" where "
                                +" empresa = "+empresa
                                +" and pedido = "+getPedido()+" order by pedido, item";
                        System.out.println("Comando sql_item: "+sql_item);
            if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Comando sql_item: "+sql_item);
                        ResultSet rs_item = stateItem.executeQuery(sql_item);
                        while(rs_item.next()){
                            nItem++;
                            if (display) JOptionPane.showMessageDialog(null,"\nInicio de Tratamento de trata_detProd() . . .");  //suprimir
                            float peso_medio = 0;
                            String tipo_preco = "UN";
                            String wdescprod = "", sigla = "";
                            String cProd = "", wcodInterno = "";
                            String cEAN = "";
                            String ncm = "";
                            //int wunidade = 0;
                            String wunidade = "";
                            boolean incidePisCofins = false, diferido = false;
                            int wtabtrib = 0, wtabtrib_f = 0;  //, trb_pis = 0, trb_cofins = 0;
                            float wpericm = 0, wpericm_f = 0, wpericm_d = 0;
                            double icms_pRedBC = 0, wpis_perc = 0, wcofins_perc = 0;
                            //int peso_faixa = rs_item.getInt("peso_faixa");
                            float peso_fat = rs_item.getFloat("peso");
                            float qtde_fat = rs_item.getFloat("quantidade");
                            float wqtdfat = peso_fat;
                            BigDecimal vl_qCom = new BigDecimal(rs_item.getFloat("quantidade")).setScale(4, RoundingMode.HALF_EVEN);
                            vlrProd = rs_item.getBigDecimal("vlr_produto").setScale(2, RoundingMode.HALF_EVEN);
                            String vDesc = null;
                            vlrDesc = rs_item.getBigDecimal("vlr_desconto").setScale(2, RoundingMode.HALF_EVEN);
                            if (vlrDesc.compareTo(BigDecimal.ZERO) != 0) {
                                vDesc = "" + vlrDesc;
                            }
                            String qCom = "" + vl_qCom;
                            String vUnTrib = "" + rs_item.getBigDecimal("vlr_unitario").setScale(4);
                            vProdTrib = null;
                            int witem = rs_item.getInt("item");
                            int wcfop = rs_item.getInt("cod_cfop");
                            double wicms_pred = 0;
                            String wicms_cst = "", wpis_CST = "", wcofins_CST = "";

                            String sql_produto = "select * from produto where codigo = "+rs_item.getInt("cod_produto");
                if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_mvfatura() - Comando sql_produto: "+sql_produto);
                            ResultSet rs_produto = statement.executeQuery(sql_produto);
                            while(rs_produto.next()){
                                wdescprod = Biblioteca.trataString(rs_produto.getString("nome_reduzido"));
                                if (wdescprod.length() > 44) wdescprod = wdescprod.substring(0,60);
                                peso_medio      = rs_produto.getFloat("peso");
                                cProd           = rs_produto.getString("codigo");
                                cEAN            = rs_produto.getString("ean");
                                ncm             = rs_produto.getString("ncm");
                                wunidade        = rs_produto.getString("unidade");
                                wtabtrib        = rs_produto.getInt("icms_cst");
                                wicms_cst       = rs_produto.getString("icms_cst");
                                wpericm         = rs_produto.getFloat("icms_perc");
                                wicms_pred      = rs_produto.getDouble("icms_pred");
                                wpericm_d       = wpericm;  //rs_produto.getFloat("pericm_d");
                                wpis_CST = rs_produto.getString("pis_cst");
                                wpis_perc = rs_produto.getDouble("pis_perc");
                                wcofins_CST = rs_produto.getString("cofins_cst");
                                wcofins_perc = rs_produto.getDouble("cofins_perc");
                            }
                if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - passou 1 . . . ");
                            if (cEAN != null) {
                                wcodInterno = cEAN;
                            } else {
                                wcodInterno = cProd;
                            }
                             boolean temTrib = false;
                            if (listTrib.size() > 0) {
                                for (Integer trib : listTrib) {
                                    if (trib == wtabtrib) {
                                        temTrib = true;
                                        break;
                                    }
                                }
                            }
                if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_mvfatura() - passou 2 . . . ");
                            if (!temTrib) {
                                listTrib.add(wtabtrib);
                                listAliq.add(formataStrDec("pericm", ""+wpericm, 2));
                            }
                            if (cProd.length() > 8) cProd =  cProd.substring(0,8);
                if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - passou 2.0.1 . . . ");

                            String CFOP = "0000";
                            String uCom = wunidade;  //"XX";

                            String sql_cfop = "select * from cfop where codigo = "+wcfop;
                            //System.out.println("Comando sql_produto: "+sql_produto);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - Comando sql_cfop: "+sql_cfop);
                            setDesc_CFOP("");
                            ResultSet rs_cfop = statement.executeQuery(sql_cfop);
                            while(rs_cfop.next()){
                                CFOP = rs_cfop.getString("cfop");
                                setDesc_CFOP(rs_cfop.getString("descricao"));
                            }
                            setModBC("3");  //(rs_item.getString("icms_modbc"));
                            int resto = 44 - wdescprod.length();
                            for (int i=0; i<resto; i++) {
                                wdescprod += " ";
                            }
                            String wqtdfatx = formataStrDec("wqtdfat", ""+wqtdfat,3);
                            int tamqfat = wqtdfatx.length();
                            String spac = "";
                            for (int i=0;i<(7-tamqfat);i++) {
                                spac += " ";
                            }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 2.0.2 . . . ");
                            String str_qCom = formataStrDec("qCom", qCom,3);
                            int tamqcom = str_qCom.length();
                            spac = "";
                            for (int i=0;i<(8-tamqcom);i++) {
                                spac += " ";
                            }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 2.1 . . . vlrProd: ["+vlrProd+"] - vl_qCom: ["+vl_qCom+"]");  //+"] - result[0]: ["+result_un[0]+"] - result[1]: ["+result_un[1]+"]");
                            float wvUnCom = 0;
                            try {
                                wvUnCom = vlrProd.floatValue() / vl_qCom.floatValue();  //).setScale(4, RoundingMode.DOWN);
                            } catch(Exception e1) {
                                JOptionPane.showMessageDialog(null,"Erro ao tentar calcular o vUnCom. Erro: "+e1);
                            }
                            String vUnCom = formataStrDec("vUnCom", "" + wvUnCom,4);  //.setScale(4, RoundingMode.HALF_UP);  //  rs_item.getString("preco_unitario")+"00";

    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 2.2 . . . ");
                            String vProd = ""+vlrProd;
                            long cEAN_num = 0;
                            if (cEAN != null && cEAN.trim().length() == 13) {
                                cEAN_num = Long.parseLong(cEAN.trim().substring(0,13));
                            }
                            String cEAN_str = ""+cEAN_num;
                            if (cEAN_str.length() < 13) {
                                cEAN = "";
                            } else {
                                cEAN = formata_ean(cEAN);
                            }
                            if (cEAN == null) {
                                cEAN = "";
                             }
                            String cEANTrib = cEAN;  //rs_item.getString("p.ean14");
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 3 . . . ");
                           String uTrib = tipo_preco;
                            String qTrib;
                            if (tipo_preco.equals("KG")) {
                                qTrib = ""+new BigDecimal(peso_fat).setScale(4, RoundingMode.HALF_EVEN);
                            } else {
                                qTrib = ""+new BigDecimal(qtde_fat).setScale(4, RoundingMode.HALF_EVEN);
                            }
                if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 4 . . . ");
                            String str_qTrib = formataStrDec("qTrib", qTrib, 3);
                            int tamqtrib = str_qTrib.length();
                            spac = "";
                            for (int i=0;i<(12-tamqtrib);i++) {
                                spac += " ";
                            }
                            String xProd = wdescprod;
                            if (ncm == null || ncm.length() < 8) ncm = "16010000";
                            uCom    = Biblioteca.tiraEspacoFinal(uCom);
                            uTrib   = Biblioteca.tiraEspacoFinal(uTrib);
                            xProd   = Biblioteca.tiraEspacoFinal(xProd);
                            if (nao_pegou_natOp) {
                                setNatOp(Biblioteca.tiraEspacoFinal(getDesc_CFOP()));
                                nao_pegou_natOp = false;
                            }
                            String CEST = null;  
    //trata_detIcms();
                    if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_detIcms()");  //suprimir
                            imposto = null;
                            icms00 = null;
                            icms10 = null;
                            icms20 = null;
                            icms40 = null;
                            icmssn101 = null;
                            icmssn102 = null;
                            icmssn500 = null;
                            icms51 = null;
                            icms60 = null;
                            icms = null;
                            pis = null;
                            cofins = null;

                            String CST, sequ_cst, origem_cst;
                            BigDecimal big_valor = BigDecimal.ZERO;
    // Variaveis para ICMS
                            int icms_tabtrib = 0;
                            int icms_modBC = Integer.parseInt(getModBC());
                            BigDecimal icms_vBC = BigDecimal.ZERO;
                            double icms_pICMS = 0;
                            BigDecimal icms_vICMS = BigDecimal.ZERO;
                            int cod_tabtrib = 1313;

                            CST = "00";
                            origem_cst = "0";
                            sequ_cst = "0";
                            cod_tabtrib = 1313;
                            String modBC = "", vBC = "", pICMS = "", vICMS = "", pRedBC = "";
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_detIcms() - CST: "+wicms_cst+" - consu_final: "+consu_final+" - diferido: "+diferido);
                            if (consu_final && !diferido) {
                                CST = "102";  //40";
                                icms_pICMS = 0;
                            } else {
                                String sql_trib;
                                ResultSet rs_trib;
                                if (getUF_clie().equals(getUF_empresa()) || getUF_clie().equals("EX")) {
                                    //sql_trib = "select * from tabtrib where codigo = "+wtabtrib;
                                    icms_pICMS  = wpericm;
                               } else {
                                    icms_pICMS  = wpericm_f;
                                    
                                    String sql_uficms = "select * from aliquf where codigo = '"+getUF_clie()+"'";
                                    ResultSet rs_uficms;
                                    rs_uficms = statement.executeQuery(sql_uficms);
                                    while(rs_uficms.next()){
                                        icms_pICMS  = rs_uficms.getDouble("aliqicms");
                                    }
                                    
                               }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 5 . . . ");
                                if (consu_final && !diferido && icms_pICMS == 0) {
                                    icms_pICMS = wpericm_d;
                                } else {
                                    origem_cst = "0";
                                    CST = wicms_cst;
                                    icms_pRedBC = wicms_pred;
                                }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 6 . . . ");
                                float reducao = 1;
                                if ( icms_pICMS > 0 && icms_pRedBC > 0) {
                                    reducao = (float)(icms_pRedBC / icms_pICMS);
                                }
                                modBC    = getModBC();
                                icms_vBC = vlrProd.multiply(new BigDecimal(reducao));
                                //big_valor = new BigDecimal(db_vBC).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 7 . . . ");
                                icms_vICMS = (icms_vBC.multiply(new BigDecimal(icms_pICMS / 100))).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                vBC      = String.valueOf(icms_vBC.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                pICMS    = String.valueOf(new BigDecimal(icms_pICMS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                vICMS    = String.valueOf(icms_vICMS);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 8 . . . ");
                                pRedBC   = String.valueOf(new BigDecimal(icms_pRedBC).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            }  //  fim if (consufinal)
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 8.1 . . . CST: "+CST);

                            if (CST.equals("00")){

                                icms00 = new ICMS00(origem_cst, CST, modBC, vBC, pICMS, vICMS);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 9 . . . ");
                            } else if (CST.equals("101")){
                                icms_vBC = BigDecimal.ZERO;
                                icmssn101 = new ICMSSN101(origem_cst, "101", pICMS, vICMS);
                            } else if (CST.equals("102")){
                                icmssn102 = new ICMSSN102(origem_cst, "102");
                                icms_vBC = BigDecimal.ZERO;
                            } else if (CST.equals("300")){
                                icmssn102 = new ICMSSN102(origem_cst, "300");
                                icms_vBC = BigDecimal.ZERO;
                            } else if (CST.equals("20")){

                                icms20 = new ICMS20(origem_cst, CST, modBC, pRedBC, vBC, pICMS, vICMS);
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 10 . . . ");

                            } else if (CST.equals("40")){

                                icms40 = new ICMS40(origem_cst, CST);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 11 . . . entrou CST 40");

                            } else if (CST.equals("50") && sequ_cst.equals("1")){  //  antigo 51

                                icms00 = new ICMS00(origem_cst, CST, modBC, vBC, pICMS, vICMS);
                            } else if (CST.equals("51")){  //  antigo 51

                                icms51 = new ICMS51(origem_cst, CST, modBC, pRedBC, vBC, pICMS, vICMS);
                            } else if (CST.equals("52") && sequ_cst.equals("1")){  //  antigo 51

                                icms20 = new ICMS20(origem_cst, CST, modBC, pRedBC, vBC, pICMS, vICMS);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 12 . . . ");
                            } else if (CST.equals("60")){

                                icms60 = new ICMS60(origem_cst, CST);
                                icms_vBC = BigDecimal.ZERO;
                                icms_vICMS = BigDecimal.ZERO;
                                CFOP = "5405";
                            } else if (CST.equals("99") || CST.equals("500")){
                                icmssn500 = new ICMSSN500(origem_cst, "500");
                                icms_vBC = BigDecimal.ZERO;
                                icms_vICMS = BigDecimal.ZERO;
                                CFOP = "5405";
                            }

                            prod    = new Prod( cProd, cEAN, xProd, ncm, CFOP, uCom, qCom, vUnCom, CEST, vProd, cEANTrib, uTrib, qTrib, vUnTrib, vDesc, "1", DI, null);
                            icms = new ICMS(icms00, icms10, icms20, icms40, icmssn101, icmssn102, icmssn500, icms51, icms60);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 13 . . . ");
    //  acumula totais de ICMS
                total_vbc = total_vbc.add(icms_vBC);
                total_vicms = total_vicms.add(icms_vICMS);
                total_vProd = total_vProd.add(vlrProd);
                total_vDesc = total_vDesc.add(vlrDesc);
                total_vNF = total_vNF.add(vlrProd);
                total_vNF = total_vNF.subtract(vlrDesc);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 14 . . . ");

                            double percpis = 0, perccofins = 0;
                            String pis_CST = "00";
                            pis_CST = wpis_CST;
                            if (pis_CST.equals("") || pis_CST.equals("00")) pis_CST = "08";
                            percpis = wpis_perc;
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - PIS passou 15 . . . ");
                           BigDecimal pis_vBC = BigDecimal.ZERO;
                            double pis_pPIS = 0;
                            BigDecimal pis_vPIS = BigDecimal.ZERO;
                            if (pis_CST.equals("01")) {
                                pis_vBC = vlrProd;
                                pis_pPIS = percpis;
                                pis_vPIS = pis_vBC.multiply(new BigDecimal(pis_pPIS / 100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        //JOptionPane.showMessageDialog(null,"\n         metodo trata_detPis() pis_vBC = "+pis_vBC);  //suprimir
                                //big_valor       = new BigDecimal(db_vBC).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                String vBC_pis  = String.valueOf(pis_vBC);
                                String pPIS     = String.valueOf(new BigDecimal(pis_pPIS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                String vPIS     = String.valueOf(pis_vPIS);

                                pisAliq = new PISAliq(pis_CST, vBC_pis, pPIS, vPIS);
                                pis = new PIS(pisAliq);
                                total_vPIS = total_vPIS.add(pis_vPIS);
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - pis_CST = 01 . . . ");
                            } else if (pis_CST.equals("08")){
                                pisNT = new PISNT(pis_CST);
                                pis = new PIS(pisNT);
                            } else if (pis_CST.equals("07")){
                                pisNT = new PISNT(pis_CST);
                                pis = new PIS(pisNT);
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - pis_CST = 07 . . . ");
                            }  else if (pis_CST.equals("06")) {
                                pisNT = new PISNT("06");
                                pis = new PIS(pisNT);
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - pis_CST = 06 . . . ");
                            }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 16 . . . ");
    // Variaveis para COFINS - verificar CST correto aqui
                            String cofins_CST = "00";
                            cofins_CST = wcofins_CST;
                            if (cofins_CST.equals("") || cofins_CST.equals("00")) cofins_CST = "08";
                            perccofins = wcofins_perc;
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - COFINS passou 17 . . . ");
                            BigDecimal cofins_vBC = BigDecimal.ZERO;
                            double cofins_pCOFINS = 0;
                            BigDecimal cofins_vCOFINS = BigDecimal.ZERO;
                            if (cofins_CST.equals("01")) {
                                cofins_vBC = vlrProd;
                                cofins_pCOFINS = perccofins;
                                cofins_vCOFINS = cofins_vBC.multiply(new BigDecimal(cofins_pCOFINS / 100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                String CST_cof = cofins_CST;
                                String vBC_cof = String.valueOf(cofins_vBC);
                                String pCOFINS = String.valueOf(new BigDecimal(cofins_pCOFINS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                String vCOFINS = String.valueOf(cofins_vCOFINS);

                                cofinsAliq = new COFINSAliq(CST_cof, vBC_cof, pCOFINS, vCOFINS);
                                cofins = new COFINS(cofinsAliq);
                                total_vCOFINS = total_vCOFINS.add(cofins_vCOFINS);
                            } else if (cofins_CST.equals("08")) {
                                cofinsNT = new COFINSNT(cofins_CST);
                                cofins = new COFINS(cofinsNT);
                            } else if (cofins_CST.equals("07")) {
                                cofinsNT = new COFINSNT(cofins_CST);
                                cofins = new COFINS(cofinsNT);
                            } else if (cofins_CST.equals("06")) {
                                cofinsNT = new COFINSNT(cofins_CST);
                                cofins = new COFINS(cofinsNT);
                            }
    //tr
                            try {
                                imposto   = new Imposto( vProdTrib, icms, pis, cofins);
                                det       = new Det( ""+nItem, prod,imposto);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18 . . . ");
                                ldet.add(det);
                                //JOptionPane.showMessageDialog(null,"Passou adicao de det na listDet. Tamanho: "+ldet.size());
                            } catch(Exception et){
                                JOptionPane.showMessageDialog(null,"Erro na atribuicao de det: "+et);
                            }
    //  atualiza os campos da tabela mvfatura_item
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.1 . . . ");
                    String sql_update_item = "update nf_produtos set"
                            +" icms_cst         = "+CST + ", "
                            +" icms_pred        = "+icms_pRedBC + ", "
                            +" icms_bc          = "+icms_vBC + ", "
                            +" icms_perc        = "+icms_pICMS + ", "
                            +" icms_vlr         = "+icms_vICMS + ", "
                            +" pis_cst          = "+pis_CST + ", "
                            +" pis_bc           = "+pis_vBC + ", "
                            +" pis_perc         = "+pis_pPIS + ", "
                            +" pis_vlr          = "+pis_vPIS + ", "
                            +" cofins_cst       = "+cofins_CST + ", "
                            +" cofins_bc        = "+cofins_vBC + ", "
                            +" cofins_perc      = "+cofins_pCOFINS + ", "
                            +" cod_interno      = '"+wcodInterno + "', "
                            +" cofins_vlr       = "+cofins_vCOFINS
                            +" where"
                            +" pedido = "+getPedido()
                            +" and item = "+witem
                            ;
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.2 . . . ");
                            try {
    //if (display) JOptionPane.showMessageDialog(null,"Comando sql_update_item: "+sql_update_item);
                                int result = stateUpItem.executeUpdate(sql_update_item);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.3 . . . ");
                            } catch(Exception e) {
                                JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar a tabela mvfatura_item. Erro: "+e);
                            }
                        }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.4 . . . ");
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela: nf_produtos. Erro: "+e);
                    }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 19 . . . ");

                    if (getNatOp() == null)  setNatOp("5102");
    //trata_total();  atribuir totais acumulados dos itens aqui
                    if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_total()");  //suprimir
                    String vBC      = "" + total_vbc.setScale(2, RoundingMode.HALF_EVEN);
                    String vICMS    = "" + total_vicms.setScale(2, RoundingMode.HALF_EVEN);
                    String vBCST    = "" + total_vbcst.setScale(2, RoundingMode.HALF_EVEN);
                    String vST      = "" + total_vST.setScale(2, RoundingMode.HALF_EVEN);
                    String vProd    = "" + total_vProd.setScale(2, RoundingMode.HALF_EVEN);
                    String vFrete   = "" + total_vFrete.setScale(2, RoundingMode.HALF_EVEN);
                    String vSeg     = "" + total_vSeg.setScale(2, RoundingMode.HALF_EVEN);
                    String vDesc    = "" + total_vDesc.setScale(2, RoundingMode.HALF_EVEN);
                    String vII      = "" + total_vII.setScale(2, RoundingMode.HALF_EVEN);
                    String vIPI     = "" + total_vIPI.setScale(2, RoundingMode.HALF_EVEN);
                    String vPIS     = "" + total_vPIS.setScale(2, RoundingMode.HALF_EVEN);
                    String vCOFINS  = "" + total_vCOFINS.setScale(2, RoundingMode.HALF_EVEN);
                    String vOutro   = "" + total_vOutro.setScale(2, RoundingMode.HALF_EVEN);
                    String vNF      = "" + total_vNF.setScale(2, RoundingMode.HALF_EVEN);
    if (display) {
    JOptionPane.showMessageDialog(null,"trata_total - 01: "+vBC);
    JOptionPane.showMessageDialog(null,"trata_total - 02: "+vICMS);
    JOptionPane.showMessageDialog(null,"trata_total - 03: "+vProd);
    JOptionPane.showMessageDialog(null,"trata_total - 04: "+vPIS);
    JOptionPane.showMessageDialog(null,"trata_total - 05: "+vCOFINS);
    JOptionPane.showMessageDialog(null,"trata_total - 06: "+vNF);
                    }
                    try {
                        ICMSTot = new ICMSTot( vBC, vICMS, "0.00", vBCST, vST, vProd, vFrete, vSeg, vDesc, vII, vIPI, vPIS, vCOFINS, vOutro, vNF, vTotTrib);
                        total = new Total(ICMSTot);
                    } catch(Exception e) {
                        JOptionPane.showMessageDialog(null,"Erro ao instanciar ICMSTot. Erro: "+e);
                    }
    //trata_vol();
    if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_vol()");  //suprimir
                    String CPF_transp = null, CNPJ_transp = "", xNome_transp = "", IE_transp = "", xEnder_transp = "", xMun_transp = "", UF_transp = "";
//comentado temporariamente. falta tratar
                    String modFrete     = "0";  //rs_mvfatura.getString("modFrete");
                    String qVol         = "";//  rs_mvfatura.getString("qtde_volume");
                    String pesoL        = "";//  String.valueOf(rs_mvfatura.getBigDecimal("peso_volume").setScale(3, RoundingMode.HALF_EVEN));
                    String pesoB        = "";//  String.valueOf(rs_mvfatura.getBigDecimal("peso_volume").setScale(3, RoundingMode.HALF_EVEN));
                    String placa        = "";//  rs_mvfatura.getString("placa_veiculo");
                    String UFVeic       = "";//  rs_mvfatura.getString("uf_placa");
                    String codtransp    = "";//  rs_mvfatura.getString("cod_transportador");
    if (display) JOptionPane.showMessageDialog(null, "Placa do veiculo: "+placa+" - UF: "+UFVeic);
                    if (codtransp != null && !codtransp.equals("") && Integer.parseInt(codtransp) != 0) { // os dados do transportador já vem informado no arq. TXT
            String sql_transp = "select c.codigo as cli_codigo,"
                    +" c.pessoa as cli_pessoa,"
                    +" c.inscest as cli_inscest,"
                    +" c.cnpj as cli_cnpj,"
                    +" c.endereco as cli_endereco,"
                    +" c.razaosocial as cli_razao,"
                    +" c.fantasia as cli_fantasia,"
                    +" c.numero as cli_numero,"
                    +" c.bairro as cli_bairro,"
                    +" c.cep as cli_cep,"
                    +" c.codcidade as cli_codcidade,"
                    +" cid.cidade as cid_cidade,"
                    +" c.email as cli_email,"
                    +" c.telefone as cli_telefone,"
                    +" c.consufinal as cli_consufinal,"
                    +" c.diferido as cli_diferido,"
                    +" cid.codcidade as cid_codcidade, "
                    +" cid.cidade as cid_cidade, "
                    +" cid.uf as cid_uf "
                    +" from cliente c"
                    +" join ibge as cid on c.codcidade = cid.codigo"
                                +" where c.codigo = "+codtransp;
                        System.out.println("Comando sql_transp: "+sql_transp);
                        if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - Comando sql_transp: "+sql_transp);
                        ResultSet rs_transp = statement.executeQuery(sql_transp);
                        while(rs_transp.next()){
                            CNPJ_transp = Biblioteca.trataTiraPonto(rs_transp.getString("cli_cnpj"));
                            xNome_transp = Biblioteca.tiraEspacoFinal(Biblioteca.trataString(rs_transp.getString("cli_razao")));       //
                            IE_transp = Biblioteca.trataTiraPonto(rs_transp.getString("cli_inscest"));       //
                            xEnder_transp = Biblioteca.trataString(rs_transp.getString("cli_endereco")+" "
                                    +rs_transp.getString("cli_numero")+" "
                                    +rs_transp.getString("cli_bairro"));       //
                            xMun_transp = Biblioteca.trataString(rs_transp.getString("cid_cidade"));       //
                            UF_transp = rs_transp.getString("cid_uf");
                            UFVeic = UF_transp;
                    if (display) JOptionPane.showMessageDialog(null, "Encontrou transportadora: "+xNome_transp);
                        }
                    } else {
                    if (display) JOptionPane.showMessageDialog(null, "Não encontrou transportadora . . .");
    //                    if (codtransp.equals("8888888")) {       // as Tags para transportador nao serao geradas
                            CNPJ_transp     = null;
                            xNome_transp    = null;
                            IE_transp       = null;
                            xEnder_transp   = null;
                            xMun_transp     = null;
                            UF_transp       = null;
    //                    } else {                          // os dados do transportador sao carregados a partir da tabela TRANSPOR
                            sql = "";
                            int wcodtr = 0;
                            if (codtransp == null || codtransp.equals("")) wcodtr = 1;
                            else wcodtr = Integer.parseInt(codtransp);
                            if (wcodtr != 0) {
                                try {
             sql = "select c.codigo as cli_codigo,"
                    +" c.pessoa as cli_pessoa,"
                    +" c.inscest as cli_inscest,"
                    +" c.cnpj as cli_cnpj,"
                    +" c.endereco as cli_endereco,"
                    +" c.razaosocial as cli_razao,"
                    +" c.fantasia as cli_fantasia,"
                    +" c.numero as cli_numero,"
                    +" c.bairro as cli_bairro,"
                    +" c.cep as cli_cep,"
                    +" c.codcidade as cli_codcidade,"
                    +" cid.cidade as cid_cidade,"
                    +" c.email as cli_email,"
                    +" c.telefone as cli_telefone,"
                    +" c.consufinal as cli_consufinal,"
                    +" c.diferido as cli_diferido,"
                    +" cid.codcidade as cid_codcidade, "
                    +" cid.cidade as cid_cidade, "
                    +" cid.uf as cid_uf "
                    +" from cliente c"
                    +" join ibge as cid on c.codcidade = cid.codigo"
                                +" where c.codigo = "+wcodtr;
                        if (display) JOptionPane.showMessageDialog(null, "Comando Sql para a tabela Transpor: "+sql);
                                    System.out.println("Comando sql: "+sql);
                                    resultset = statement.executeQuery(sql);
                                    boolean tem_trp = false;
                                    while(resultset.next()){
                        if (display) JOptionPane.showMessageDialog(null, "Achou a transportadora . . . : "+sql);
                                        CNPJ_transp     = Biblioteca.trataTiraPonto(resultset.getString("cli_cnpj"));
                                        xNome_transp    = Biblioteca.trataString(resultset.getString("cli_razao"));
                                        IE_transp       = Biblioteca.trataTiraPonto(resultset.getString("cli_inscest"));
                                        xEnder_transp   = Biblioteca.trataString(resultset.getString("cli_endereco")+" "
                                                +resultset.getString("cli_numero")+" "
                                                +resultset.getString("cli_bairro"));
                                        xMun_transp     = Biblioteca.trataString(resultset.getString("cid_cidade"));
                                        UF_transp       = resultset.getString("cid_uf");
                                        UFVeic          = UF_transp;
                                        tem_trp         = true;
                                    }
                                    if (!tem_trp || placa == null || placa.equals("")) {
                                        UFVeic          = null;
                                        placa           = null;
                                    }
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null,"Erro ao tentar carregar dados da Tabela transpor! \nErro: "+ex+"\n");
                                    txt = "";
                                    UFVeic          = null;
                                    placa           = null;
                                }
                        }
    //                    }

                    }
                    if (CNPJ_transp != null) {
                        if (CNPJ_transp.equals("")) CNPJ_transp = null;
                        if (xNome_transp.equals("")) xNome_transp = null;
                        else xNome_transp = xNome_transp;
                        if (IE_transp.equals("")) IE_transp = null;
                        if (xEnder_transp.equals("")) xEnder_transp = null;
                        if (xMun_transp.equals("")) xMun_transp = null;
                        if (UF_transp.equals("")) UF_transp = null;
                        if (UFVeic.equals("")) UFVeic = getUF_emit();
                        if (CNPJ_transp != null && CNPJ_transp.length() == 11){
                            CPF_transp = CNPJ_transp;
                            CNPJ_transp = null;
                        }else{
                            CPF_transp = null;
                        }
                    } else {
    //                if (codtransp.equals("8888888")) {
                        veictransp = null;
                        transporta = null;
    //                }
                    }
                    if (Integer.parseInt(codtransp) == 0) {
                       veictransp = null;
                       transporta = null;
                    } else {
                        transporta  = new Transporta( CNPJ_transp, CPF_transp, xNome_transp, IE_transp, xEnder_transp, xMun_transp, UF_transp);
                    }
    //                else {
                    //JOptionPane.showMessageDialog(null,"\nPassou 1 . . .");  //suprimir
                    //JOptionPane.showMessageDialog(null,"\nPassou 2 . . .");  //suprimir
                        if (placa == null || placa.trim().length() == 0) //  LLombardi e Styroflex
                            veictransp = null;  //  new veicTransp(placa, UFVeic);
                        else
                            veictransp = new VeicTransp(placa, UFVeic);
                    //JOptionPane.showMessageDialog(null,"\nPassou 3 . . .");  //suprimir
//                    transporta  = new transporta( CNPJ_transp, CPF_transp, xNome_transp, IE_transp, xEnder_transp, xMun_transp, UF_transp);
    //                }
                    vol         = new Vol(qVol, pesoL, pesoB);
                    transp      = new Transp(modFrete, transporta, veictransp, vol);
                    //JOptionPane.showMessageDialog(null,"\nPassou 4 . . .");  //suprimir
    //trata_cobr();
                    if (display)
                        JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_cobr()");  //suprimir
                    boolean fim = false;
                    String nDup, dVenc, vDup;
                    String nome_banco = null;
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 1 . . .");  //suprimir
                    if (getBanco() != 0 && getTipo_cobranca().equals("BL")) {
                        String sql_banco = "select * from banco where codigo = "+getBanco();
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 2 . . .");  //suprimir
    //JOptionPane.showMessageDialog(null,"Comando sql_banco: "+sql_banco);
                        if (display)
                            JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - Comando sql_banco: "+sql_banco);
                        ResultSet rs_transp = statement.executeQuery(sql_banco);
                        while(rs_transp.next()){
                            nome_banco = rs_transp.getString("nome_reduzido");
                        }
                    } else {
                        //JOptionPane.showMessageDialog(null,"O banco estava Zerado!!");
                        if (getTipo_cobranca().equals("AV")) {
                            if (getNatOp().indexOf("BONIFICA") != -1) {
                                nome_banco = "BONIFICAÇÃO";
                            } else {
                                nome_banco = "A VISTA";
                            }
                        } else if (getTipo_cobranca().equals("CH")){
                            nome_banco = "CHEQUE";
                        } else if (getTipo_cobranca().equals("CC")){
                            nome_banco = "Cred.Conta";
                        } else {
                            nome_banco = ".";
                        }
                    }
                    if (nome_banco == null && getTipo_cobranca().equals("BL")) {
                        nome_banco = "BRADESCO";
                    }
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 3 . . .");  //suprimir
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - total_vNF: "+total_vNF);  //suprimir
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - nome_banco: "+nome_banco);  //suprimir
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - dVenc: "+rs_mvfatura.getString("data_vencimento"));  //suprimir
                    String sql_prazo = "select * from nf_prazo"
                            +" where "
                            +" empresa = "+empresa
                            +" and pedido = "+getPedido()
                            +" order by pedido, parcela";
                    System.out.println("Comando sql_item: "+sql_prazo);
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Comando sql_prazo: "+sql_prazo);
                    ResultSet rs_prazo = statement.executeQuery(sql_prazo);
                    String str_dVenc = "";
                    while (rs_prazo.next()) {
                        str_dVenc = rs_prazo.getString("datavcto");
                        break;
                    }
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 1 . . . ");
                    dVenc = str_dVenc;
                    vDup = "" + total_vNF.setScale(2);  //  "" + rs_mvfatura.getBigDecimal("total_vNF").setScale(2);
                    //if (nome_banco != null) {
                        nDup = Biblioteca.tiraEspacoFinal(nome_banco);
                    //}
                    dup = new Dup(nDup, dVenc, vDup);
                    cobr.add(dup);
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 2 . . . ");
    //trata_infAdic();
    //            JOptionPane.showMessageDialog(null,"Trata banco passou 2 . . .");
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 4 . . .");  //suprimir
    /*
                    String nome_vend = "", cod_vend = "";
                    String sql_vend = "select * from vendedor where codigo = "+getVendedor();
                    if (display)
                        JOptionPane.showMessageDialog(null,"geraXML_siscom - Comando sql_vend: "+sql_vend);
                    ResultSet rs_vend = con_fat.executeSQL(sql_vend);
                    while(rs_vend.next()){
                        nome_vend = rs_vend.getString("nome");
                        cod_vend = rs_vend.getString("codvendedor");
                    }
     *
     */
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 5 . . .");  //suprimir
                    String tipo_cobr = "Tipo Desconh.";
                    if (getTipo_cobranca().equals("AV")) {
                        if (getNatOp().indexOf("BONIFICA") != -1) {
                            tipo_cobr = "BONIFICAÇÃO";
                        } else {
                            tipo_cobr = "A VISTA";
                        }
                    } else if (getTipo_cobranca().equals("BL")) {
                        tipo_cobr = "A PRAZO";
                    } else if (getTipo_cobranca().equals("CH")) {
                        tipo_cobr = "CHEQUE";
                    } else if (getTipo_cobranca().equals("CC")) {
                        tipo_cobr = "CRED.CTA";
                    } else {  //  DESCONHECIDO
                        tipo_cobr = "A VISTA(*)";
                    }
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 3 . . . ");
//comentado temporariamente. falta tratar
                    String infCpl =  "";  //Biblioteca.trataString(rs_mvfatura.getString("dados_adicionais"));
                    if (infCpl == null || infCpl.equals("")) {
                        infCpl = ".";
                    }
    //JOptionPane.showMessageDialog(null,"\n        metodo trata_cobr() - passou 6 . . .");  //suprimir
                    infCpl = Biblioteca.tiraEspacoInicial(infCpl);
                    infCpl = Biblioteca.tiraEspacoFinal(infCpl);
    //                boolean ja_trib = false;
    /*
                    int idx_trib = 0;
                    for (Integer trib : listTrib) {
                        String sql_trib = "select * from tabtrib where codigo = "+trib;
                        if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom - Comando sql_trib: "+sql_trib);
                        ResultSet rs_trib = con_siscom.executeSQL(sql_trib);
                        while(rs_trib.next()){
    //                        if (!ja_trib) {
    //                            ja_trib = true;
    //                            setSit_trib("");
    //                        }
                             setSit_trib(getSit_trib() + " ST: "+ rs_trib.getString("cst")+" ALIQUOTA DE "+listAliq.get(idx_trib)+" "+rs_trib.getString("mensagem")+".");
                        }
                        idx_trib++;
                    }
                    infCpl += "Vend: "+cod_vend+" "+nome_vend+" Ped: "+pedido+" "+tipo_cobr+" "+getSit_trib();
     *
     */
         if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 4 . . . ");
                   if (infCpl != null && infCpl.length() > 1) {
                        infAdic = new InfAdic(infCpl);
                    } else {
                        infAdic = null;
                    }
    //            JOptionPane.showMessageDialog(null,"Trata banco passou 3 . . .");
                    geraArqXML(""+Integer.parseInt(getnNF()));
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 5 . . . ");
    //            JOptionPane.showMessageDialog(null,"Vai atualizar tabela: numeronfe . . .");
                    atualizar_numeronf();
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 6 . . . ");
    //            JOptionPane.showMessageDialog(null,"Vai atualizar tabela: mvfatura . . .");
                    atualizar_mvfatura();
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 7 . . . ");
//  fim do rs_mvfatura aqui
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Finalizou metodo . . . ");
            } else {
                JOptionPane.showMessageDialog(null,"Não foi encontrado nenhum arquivo: "+arqXML+" para Gerar XML! ");
           }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"trata_total - Erro ao tentar ler a tabela: fatura. Erro: "+e);
        }
       return retorno;
    }
    private int pegarProxPedido() {
        int retorno = 0;
        try {
            String sql_query = "select * from nf where empresa = "+empresa+" order by pedido";
            resultset = statement.executeQuery(sql_query);
            resultset.last();
            String codigo = resultset.getString("pedido");
            retorno = Integer.parseInt(codigo);
        } catch (Exception e) {

        }
        
        retorno++;
        return retorno;
    }
    private String pegar_prox_numNF() {
        int numnf = 0;
        temRegNumNFe = false;
       try {
            String sql_numeronfe = "select max(numeronfe) as ultima_nfe from numeronfe where modelonfe = '"+modelonfe+"' and ambiente = '"+tpAmb+"' and empresa = "+empresa+" and serienfe = '"+getSerie()+"'";
            ResultSet rs_numeronfe = statement.executeQuery(sql_numeronfe);
            while(rs_numeronfe.next()){
                numnf = rs_numeronfe.getInt("ultima_nfe");
                temRegNumNFe = true;
            }
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela: numeronfe. Erro: "+e);
        }
        numnf++;
        if (tpAmb.equals("2")) {  //  somente para homologacao
            numnf =confirmaNumeroNFe(""+(numnf));
        }
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
                            + "'"+modelonfe+"', "
                            + "'"+tpAmb+"' "
                        + ")";
             System.out.println("Comando sql_insert_numeronfe: "+sql_numeronfe);
            } else {
                sql_numeronfe = "update numeronfe set "
                            + "numeronfe = "+Integer.parseInt(getnNF())
                        + " where "
                            + "empresa = " + empresa+" and "
                            + "serienfe = " + "'"+getSerie()+"' and "
                            + "modelonfe = " + "'"+modelonfe+"' and "
                            + "ambiente = "+ "'"+tpAmb+"' "
                        ;
             //System.out.println("Comando sql_update_numeronfe: "+sql_numeronfe);
            }
            int result = statement.executeUpdate(sql_numeronfe);
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar inserir na tabela: numeronfe. Erro: "+e);
        }
    }
    private void atualizar_mvfatura() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date data_emis = new Date();
        try {
            data_emis = new java.sql.Date(format.parse(anomesdia).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(GeraXML_nfe_new.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql_update = "update nf set"
                +" chave_NFe = '" + chaveFormat(getId().substring(3,47)) + "', "
                +" data_emissao = '" + data_emis + "', "
                //+" data_saida = '" + new Date() + "', "
                +" icms_bc = " + total_vbc + ", "
                +" icms_vlr = " + total_vicms + ", "
                //+" total_vbcst = " + total_vbcst + ", "
                //+" total_vst = " + total_vST + ", "
                +" valor_produtos = " + total_vProd + ", "
                //+" total_vfrete = " + total_vFrete + ", "
                //+" total_vseg = " + total_vSeg + ", "
                +" valor_descontos = " + total_vDesc + ", "
                //+" total_vii = " + total_vII + ", "
                +" ipi_vlr = " + total_vIPI + ", "
                +" pis_vlr = " + total_vPIS + ", "
                +" cofins_vlr = " + total_vCOFINS + ", "
                //+" total_voutro = " + total_vOutro + ", "
                //+" total_vnf = " + total_vNF + ", "
                +" valor_total = " + total_vNF + ", "
                +" numero_nfe = " + getnNF() + ", "
                +" serie_nfe = '" + getSerie() + "', "
                +" tpemis = '" + getTpemis() +"', "
                +" tpamb = '" + tpAmb +"' "
                +" where pedido = " + getPedido()
                +" and modelonfe = '"+modelonfe+"'";
                ;
        try {
//            System.out.println("Comando sql_update: "+sql_update);
            if (display) JOptionPane.showMessageDialog(null,"Comando sql_update: "+sql_update);
            int result = stateUpFat.executeUpdate(sql_update);
            if (display) JOptionPane.showMessageDialog(null,"Tabela fatura atualizado com numeronf: "+getnNF());
            String sql = "select * from forma_pgto where codigo = " + getForma_pgto();
            ResultSet rs_formaPgto = statement.executeQuery(sql);
            int dias_inicial = 99;
            while (rs_formaPgto.next()) {
                dias_inicial = rs_formaPgto.getInt("dias_inicial");
            }
            if (dias_inicial == 0) {
                sql_update = "update nf_prazo set"
                        +" vlr_pago = vlr_parcela, "
                        +" datapgto = datavcto "
                        +" where "
                        +" empresa = "+empresa
                        +" and pedido = "+getPedido()
                        +" and parcela = 1"
                        ;
                result = stateUpFat.executeUpdate(sql_update);
                if (display) JOptionPane.showMessageDialog(null,"Tabela nf_prazo atualizado com sucesso . . . ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar a tabela nf_prazo. Erro: "+e);
        }
     }
    public String geraArqXML(String numeronf){
        //nfemenu.ta_gx_gera.append("\nEntrou no metodo: geraArqXML()");  //suprimir
        String xml = "";
        try {
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Entrou no try . . .");

            //instancia o XStream
            XStream xstream = new XStream();
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou instancia XStream . . .");
//                        TNFe nfe = XmlUtil.xmlToObject(xml, TNFe.class);
//                        nfe.getInfNFe().setVersao(xml);


            criaId();
            //instancia um objeto infNFe
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou criaId() . . .");

            InfNFe infNFe = new InfNFe(
                    getId(),  //"NFe35080599999090910270550010000000015180051273",// Id
                    "3.10",  //  "1.10", //versao
                    ide,
                    emit, //
                    dest, //
                    autXML,  //  somente para a Bahia
                    ldet,
                    total, //
                    transp, //
                    cobr, //
                    lpag,
                    infAdic //
            );
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou 0 . . .");

            //seta os campo versao e id de infNFe como um atributos do XML
            xstream.useAttributeFor(InfNFe.class, "versao");
            xstream.useAttributeFor(InfNFe.class, "Id");
            xstream.useAttributeFor(Det.class, "nItem");
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou 1 . . .");

            //instancia um objeto NFe
            NFe nfe = new NFe(infNFe, infNFeSupl);
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou 2 . . .");

            //alias para a NFe
            xstream.alias("NFe", NFe.class);
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou 3 . . .");

            //seta o campo xmlns de NFe como um atributo do XML
            xstream.useAttributeFor(NFe.class, "xmlns");
            //xstream.useAttributeFor(NFe.class, "xmlns:ds");

            xml = xstream.toXML(nfe);

            String nnf6pos = numeronf;
            for (int i=numeronf.length(); i<6; i++) nnf6pos = "0"+nnf6pos;
            String nome_arquivo = caminho_ler_assinar+"\\"+wchave+"-nfe.xml";  //  +"//E"+nnf6pos+".xml";
            if (gravaArqXML(nome_arquivo, xml)){
                //atualizaTabelaNF(numeronf);
                //JOptionPane.showMessageDialog(null, "A rotina de atualizacao da tabela NF está suspensa temporariamente para testes");
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar Gerar o arquivo xml! \nErro: "+e.getMessage());
            xml = "Erro ao tentar Gerar o arquivo xml! \nErro: "+e;
        }
//JOptionPane.showMessageDialog(null, "Finalizou o metodo geraArqXML() . . .");

        //System.out.println(xml);
        return(xml);
    }
    private String chaveFormat(String texto) {
        String retorno = "";
        if (texto.length() == 44) {
            retorno = texto.substring(0,4)+" "
                    +texto.substring(4,8)+" "
                    +texto.substring(8, 12)+" "
                    +texto.substring(12, 16)+" "
                    +texto.substring(16, 20)+" "
                    +texto.substring(20, 24)+" "
                    +texto.substring(24, 28)+" "
                    +texto.substring(28, 32)+" "
                    +texto.substring(32, 36)+" "
                    +texto.substring(36, 40)+" "
                    +texto.substring(40,44);
        }
        return retorno;
    }
    public boolean gravaArqXML(String nome_arquivo, String texto_retornado){
        try
        {
       //nfemenu.ta_gx_gera.append("\nEntrou no metodo gravaArqXML()");   //suprimir
             //texto_retornado = texto_retornado.replaceAll("<NFe>", txt_inicio);
             //System.out.println("Texto ini: "+texto_retornado);
             int posicao = texto_retornado.indexOf("<listDet>");
             if (posicao >=0)
                texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+10,texto_retornado.length());
             posicao = texto_retornado.lastIndexOf("</listDet>");
             if (posicao >=0)
                texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+11,texto_retornado.length());
             texto_retornado = texto_retornado.replaceAll("&#xd;", "");
             texto_retornado = texto_retornado.replaceAll("nfefacil.bean.", "");
             texto_retornado = texto_retornado.replaceAll("br.com.videoaulasneri.adelcio.Det","det");
             texto_retornado = texto_retornado.replaceAll("br.com.videoaulasneri.adelcio.Dup","dup");
             Biblioteca.normalizeXML(texto_retornado);
            while (texto_retornado.indexOf(" <") != -1){
                    texto_retornado = texto_retornado.replaceAll(" <", "<");
            }
            while (texto_retornado.indexOf("\n") != -1){
                    texto_retornado = texto_retornado.replaceAll("\n", "");
            }
            while (texto_retornado.indexOf("\r") != -1){
                    texto_retornado = texto_retornado.replaceAll("\r", "");
            }
             byte [] texto = texto_retornado.getBytes();
             FileOutputStream arquivo_gerado = new FileOutputStream(nome_arquivo);
             arquivo_gerado.write(texto);
             arquivo_gerado.close();
if (display) JOptionPane.showMessageDialog(null,"\n\nCriado o arquivo: " + nome_arquivo);
            //+ "\n\nPara Transmitir, Autorizar e Imprimir a Danfe deste documento, saia desta Tela e entre no painel de Transmissões.");
            if (escolheTransmitirNFe()) {
                if (tratar_assinaturaNfe(nome_arquivo)) {
                    apaga_arquivos_temporarios(nome_arquivo); 
                }
                imprimeDanfeJava(arq_danfe);
            }
            return true;
        }
        catch(IOException erro_arquivo)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível gravar esse arquivo: "+erro_arquivo);
            return false;
        }
    }
    private boolean escolheTransmitirNFe() {
       boolean retorno = false;
       UIManager.put("OptionPane.yesButtonText", "Transmitir Agora");
       UIManager.put("OptionPane.noButtonText", "Transmitir Depois");
       int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Transmitir/Autorizar a NFe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       if ( escolha == JOptionPane.YES_OPTION){
            //JOptionPane.showMessageDialog(null,"Escolheu Transmitir e Autorizar a NFe agora");
            retorno = true;
       } else {
            JOptionPane.showMessageDialog(null, "Escolheu Transmitir e Autorizar a NFe depois"
            + "\n\nPara Transmitir, Autorizar e Imprimir a Danfe deste documento, saia desta Tela e entre no painel de Transmissões."            );
            retorno = false;
       }

       UIManager.put("OptionPane.yesButtonText", "Sim");
       UIManager.put("OptionPane.noButtonText", "Não");

        return retorno;
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
        arq_danfe           = getCaminho_impdanfe()+"\\"+chNfe+"_danfe.xml";
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
    public void apaga_arquivos_temporarios(String arquivoOrigem) {  //(String caminhoOrigem, String caminhoDestino){
        try {
//System.out.println("Metodo apaga_arquivos_temporarios() - arquivo encontrado: "+arquivoOrigem);
            String nomeArq = arquivoOrigem.substring(arquivoOrigem.indexOf("ASSINAR")+8, arquivoOrigem.length());
            File arqudel    = new File(arquivoOrigem);
            char chartxt[]  = new char[(int)arqudel.length()];
            String arq_saida_xml = getCaminho_grava_assinar()+"\\"+anomesdia+"\\"+nomeArq;
//System.out.println("Vai mover o Arquivo de saida para: "+arq_saida_xml);
            FileReader entrada;
            try {
                entrada = new FileReader(arquivoOrigem);
                entrada.read(chartxt);  //(res);
                entrada.close();

                String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();

            } catch (Exception ex) {
                System.out.println("Erro ao processar arq.xml(apaga_arquivos_temporarios): "+ex);
            }
            //arqudel.deleteOnExit();
            try {
                arqudel.delete();
//System.out.println("Apagou o arquivo: "+arqudel);
                String sAss = nomeArq.substring(0, nomeArq.indexOf("-nfe"))+"_sign.xml";
                String arq_xml_ass         = getCaminho_assinatura() + "\\" + sAss;
//System.out.println("Lendo o arquivo assinado: "+arq_xml_ass);
                File arqudelAss    = new File(arq_xml_ass);
                char chartxtAss[]  = new char[(int)arqudelAss.length()];
                String arq_saida_ass = getCaminho_assinatura()+"\\"+"BKPNFE"+"\\"+anomesdia+"\\"+sAss;
//System.out.println("Vai mover o Arquivo de saida para: "+arq_saida_ass);
                FileReader entradaAss;
                entradaAss = new FileReader(arq_xml_ass);
                entradaAss.read(chartxtAss);  //(res);
                entradaAss.close();

                String texto_string = new String(chartxtAss);  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_ass);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();
                arqudelAss.delete();
//System.out.println("Apagou o arquivo: "+arqudelAss);

             } catch (Exception ex) {
                System.out.println("Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
            }
        } catch (Exception ex) {
            System.out.println("Erro no metodo apaga_arquivos_temporarios \nErro: "+ex);
        }
    }
    private boolean trata_emit(){
        if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_emit()");  //suprimir
        String CNPJ = "", xNome = "", xFant = "", xLgr = "", nro = "", xCpl = "", xBairro = "", 
                xMun = "", CEP = "", cPais = "", xPais = "", fone = "", IE = "", tpnf = "", dataSaida = "", crt = "3";
        try {
            String sql_emit = "select e.*, ibge.codcidade as codcid, ibge.cidade as cidade, ibge.uf as ufcid from empresa e join ibge on e.codcidade = ibge.codigo where e.codempresa = "+empresa;
if (display) JOptionPane.showMessageDialog(null,"Comando sql_emit: "+sql_emit);
            ResultSet rs_emit = statement.executeQuery(sql_emit);
            while(rs_emit.next()){
                crt     = rs_emit.getString("crt");  
                codigoEmp = rs_emit.getInt("codigo");
                CNPJ    = Biblioteca.trataTiraPonto(rs_emit.getString("cnpj"));  //("e.cnpj_cpf"));
                xNome   = Biblioteca.trataString(rs_emit.getString("razao"));  //("e.razao");
                xFant   = Biblioteca.trataString(rs_emit.getString("fantasia"));  //("e.fantasia");
                xLgr    = Biblioteca.trataString(rs_emit.getString("endereco"));  //("e.endereco");
                nro     = rs_emit.getString("numero");  //("e.numero");
                xCpl    = Biblioteca.trataString(rs_emit.getString("complemento"));  //("e.complemento");
                xBairro = Biblioteca.trataString(rs_emit.getString("bairro"));  //("e.bairro");
                setCmunfg(rs_emit.getString("codcid").substring(0,7));  //("ibge.codcidade");
                xMun    = Biblioteca.trataString(rs_emit.getString("cidade")); //("ibge.cidade");
                setUF_empresa(rs_emit.getString("ufcid"));  //("ibge.uf"));
                CEP     = Biblioteca.trataTiraPonto(rs_emit.getString("cep"));  //("e.cep");
                cPais   = rs_emit.getString("codigo_pais_nfe");  //("e.codigo_pais_nfe");
                xPais   = "BRASIL";
                fone    = Biblioteca.trataTiraPonto(rs_emit.getString("telefone"));  //("e.fone"));
                IE      = Biblioteca.trataTiraPonto(rs_emit.getString("inscest"));  //("e.insc_est"));
                tpnf    = "1";  //rs_emit.getString("tipo_nf");  //("e.tipo_nf");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler a tabela empresa. Erro: "+e);
        }
        CEP = formataStrZeroIni(CEP, 8);
        setCNPJ_emit(CNPJ);//
        setUF_emit(getUF_empresa());
        dataSaida = anomesdia.substring(6,8)+"/"+anomesdia.substring(4,6)+"/"+anomesdia.substring(0,4);
        System.out.println("Anomesdia: ["+anomesdia+"] - Data da Saida: "+dataSaida);

        enderEmit   = new EnderEmit(xLgr, nro, xBairro, getCmunfg(), xMun, getUF_empresa(), CEP, cPais, xPais, fone);

        emit        = new Emit( CNPJ, xNome, xFant, enderEmit, IE, crt);

        return true;
    }
    public void trata_ide(){
        if (display) JOptionPane.showMessageDialog(null, "Entrou trata_ide() . . .");
        try {
         Date data = new Date();
         final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
         String strData = sdf.format(data);
         ide    = new Ide(getcUF(), //cUF
                  getcNF(), // cNF
                  getNatOp(), //natOp
                  getIndpag(), //indPag,
                  getMod(), //mod,
                  ""+Integer.parseInt(getSerie()), //serie,
                  ""+Integer.parseInt(getnNF()), //nNF,
                  //anomesdia.substring(0,4)+"-"+anomesdia.substring(4,6)+"-"+anomesdia.substring(6,8)+"T08:00:00"+fuso_horario, //dhEmi,
                  strData+fuso_horario, //dhEmi,
                  strData+fuso_horario, //dhSaiEnt,
                  "1",  //  tpNf 
                  getIdDest(),  //idDest
                  getCmunfg(), //cMunFG,
                  getTpimp(), //tpImp,
                  getTpemis(), //tpEmis,
                  getcDV(), //cDV,
                  tpAmb,  //resultset.getString("tpamb"), //tpAmb,
                  getFinnfe(), //finNFe,
                  getIndFinal(),  //indFinal
                  "2",  //indPres
                  "0",  //getProcemis(), //procEmi,
                  getVerproc(), //verProc
                  getDhCont(),  //  data e horario da contingencia DPEC
                  getxJust(),  //  Justificativa Contingencia DPEC
                  nfref
                );
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar cria IDE. Erro: "+e);
        }
        if (display) JOptionPane.showMessageDialog(null, "Saiu Trata_ide() . . .");
    }
    private boolean trata_dest(){
        if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_dest()");  //suprimir
        String pessoa = "", CNPJ = "", xNome_dest = "", IE = "", xLgr = "", nro = "", xCpl = "",
                xBairro = "", cMun = "", xMun = "", CEP = "", fone = "", email = "";
        consu_final = false;
        diferido = false;
        try {
//            String sql_emit = "select e.*, ibge.codcidade as codcid, ibge.cidade as cidade, ibge.uf as ufcid from empresa e join ibge on e.codcidade = ibge.codigo where e.codempresa = "+empresa;

            String sql_dest = "select c.codigo as cli_codigo,"
                    +" c.pessoa as cli_pessoa,"
                    +" c.inscest as cli_inscest,"
                    +" c.cnpj as cli_cnpj,"
                    +" c.endereco as cli_endereco,"
                    +" c.razaosocial as cli_razao,"
                    +" c.fantasia as cli_fantasia,"
                    +" c.numero as cli_numero,"
                    +" c.bairro as cli_bairro,"
                    +" c.cep as cli_cep,"
                    +" c.codcidade as cli_codcidade,"
                    +" cid.cidade as cid_cidade,"
                    +" c.email as cli_email,"
                    +" c.telefone as cli_telefone,"
                    +" c.consufinal as cli_consufinal,"
                    +" c.diferido as cli_diferido,"
                    +" cid.codcidade as cid_codcidade, "
                    +" cid.uf as cid_uf "
                    +" from cliente c"
                    +" join ibge as cid on c.codcidade = cid.codigo"
                    +" where c.codigo = "+getCliente();
if (display) JOptionPane.showMessageDialog(null,"Comando sql_dest: "+sql_dest);
            ResultSet rs_dest = statement.executeQuery(sql_dest);
            int codigo_cliente = 0;
            while(rs_dest.next()){
                codigo_cliente = rs_dest.getInt("cli_codigo");
                pessoa      = rs_dest.getString("cli_pessoa");
                CNPJ        = Biblioteca.trataTiraPonto(rs_dest.getString("cli_cnpj"));
                xNome_dest  = Biblioteca.trataString(rs_dest.getString("cli_codigo"))+" - "+Biblioteca.trataString(rs_dest.getString("cli_razao"));
                IE          = Biblioteca.trataTiraPonto(rs_dest.getString("cli_inscest"));
                xLgr        = Biblioteca.trataString(rs_dest.getString("cli_endereco"));//+", "+rs_dest.getString("cli_numero");
                nro         = rs_dest.getString("cli_numero");
                xBairro     = Biblioteca.trataString(rs_dest.getString("cli_bairro"));
                cMun        = rs_dest.getString("cid_codcidade").substring(0,7);
                xMun        = Biblioteca.trataString(rs_dest.getString("cid_cidade"));
                setUF_clie(rs_dest.getString("cid_uf"));
                CEP         = Biblioteca.trataTiraPonto(rs_dest.getString("cli_cep"));
                fone        = Biblioteca.trataTiraPonto(rs_dest.getString("cli_telefone"));
                email       = rs_dest.getString("cli_email");  
                consu_final = rs_dest.getString("cli_consufinal").equals("S") ? true : false;
                diferido    = rs_dest.getString("cli_diferido").equals("S") ? true : false;
            }
            if (xNome_dest.length() > 60) {
                xNome_dest = xNome_dest.substring(0,60);
            }
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler a tabela Cliente. Erro: "+e);
        }
        String cPais = "1058";
        String xPais = "BRASIL";       //
        if (nro.equals("")) nro = "S/N";
        if (xCpl.equals("")) xCpl = null;
        if (CEP.equals("")) CEP = null;
        setEmail_dest(email);       //
        if (tpAmb.equals("2")) {  //  Homologacao
            xNome_dest = "NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL";
        }
        setxNome_dest(xNome_dest);
        setCNPJ_dest(CNPJ);       //10931741000140^
        if (IE == null || IE.equals("")) {
            setIE_dest(null);      
        } else {
            setIE_dest(IE);      
        }
        if (pessoa.equals("F")){  //  pessoa fisica
            setCPF_dest(getCNPJ_dest());
            setCNPJ_dest(null);
            setIE_dest(null);  //("ISENTO");
            if (UfEmit.equals(UfDest)) {
                setIndIEDest("2");
            } else {
                setIndIEDest("9");
            }
            if (
                    UfEmit.equals("35") || //  35=SP  
                    UfEmit.equals("15") || //  15=PA  
                    UfEmit.equals("24")    //  24=RN  
                ) {
                setIndIEDest("9");
           }
            setIndFinal("1");
        } else {  //  pessoa juridica
            setCPF_dest(null);
            //setIE_dest(null);       //90486565-68^
            if (getIE_dest() == null || getIE_dest().equals("")) {
                setIndIEDest("9");
                setIndFinal("1");
                setIE_dest(null);
            } else {
                setIdDest("1");
                setIndIEDest("1");
                setIndFinal("0");
            }
       }
        if (UfEmit.equals(UfDest)) {
            setIdDest("1");
        } else {
            setIdDest("2");
        }
//        NFefacil.ta_gx_gera.append("\nIniciou geração do arq.XML para a NF ["+getnNF()+"]");
        CEP = formataStrZeroIni(CEP,8);

        if (fone.equals("")) fone = "0";
        else if (fone.length()>10) fone = fone.substring(fone.length()-10,fone.length());
        if (fone.length() < 7) fone = null;

        enderDest       = new EnderDest(xLgr, nro, xCpl, xBairro, cMun, xMun, getUF_clie(), CEP, cPais, xPais, fone);
        dest       = new Dest(getCNPJ_dest(), getCPF_dest(), getxNome_dest(), enderDest, getIndIEDest(), getIE_dest(), getEmail_dest());
//System.out.println("GeraXML_NFe - Uf_emit: " + UF_emit);
        if (UF_emit.equals("BA")) {
            String cnpjContador = "13937073000156";
            String cpfContador = null;
            autXML = new AutXML(cnpjContador, cpfContador);
        }
//System.out.println("GeraXML_NFe - autXML: " + autXML);
        vTotTrib = null;
        return true;
    }
    private String formataStrZeroIni(String cpo, int tamanho) {
        String retorno = cpo;
        int len = cpo.length();
        for (int i=len; i<tamanho; i++) {
            retorno = "0"+retorno;
        }
        return retorno;
    }
    private void trata_infAdic(){

    }
    private String formataInfCpx(String txt){
        int ini = txt.length();
        for (int i=ini;i<40;i++){
            txt = txt+" ";
        }
        return txt;
    }
    public static void main(String args[]){
        //geraXML gxml = new geraXML("c://nfe//dados//empr0002//assinar");
        //driver       = "sun.jdbc.odbc.JdbcOdbcDriver";
        //url          = "jdbc:odbc:lombardi";
        //usuario      = "";
        //senha        = "";
        //gxml.conectaCliente("sun.jdbc.odbc.JdbcOdbcDriver", "jdbc:odbc:lombardi", "", "");
        //gxml.carregaNfe("25/06/2010", "todas", null);
    }

   public String getIndpag() {
        return indpag;
    }

    public void setIndpag(String indpag) {
        this.indpag = indpag;
    }

    public String getCNPJ_dest() {
        return CNPJ_dest;
    }

    public void setCNPJ_dest(String CNPJ_dest) {
        this.CNPJ_dest = CNPJ_dest;
    }

    public String getxNome_dest() {
        return xNome_dest;
    }

    public void setxNome_dest(String xNome_dest) {
        xNome_dest = Biblioteca.tiraEspacoFinal(xNome_dest);
        this.xNome_dest = xNome_dest;

    }

    public String getIE_dest() {
        return IE_dest;
    }

    public void setIE_dest(String IE_dest) {
        this.IE_dest = IE_dest;
    }

    public String getnNF() {
        return nNF;
    }

    public void setnNF(String nNF) {
        this.nNF = nNF;
    }

    public String getNatOp() {
        return natOp;
    }

    public void setNatOp(String natOp) {
        natOp = Biblioteca.tiraEspacoFinal(natOp);
        this.natOp = natOp;
    }

    public String getcDV() {
        return cDV;
    }

    public void setcDV(String cDV) {
        this.cDV = cDV;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    public void criaId(){
        if (display) JOptionPane.showMessageDialog(null, "Entrou criaId() . . .");
        wchave = null;
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
        if (display) {
            JOptionPane.showMessageDialog(null,
                    "chave: "+wchave
                    +"\ntamanho da chave: "+wchave.length()
                    +"\ncUF: "+cUF
                    +"\nanomes: "+anomesdia.substring(2,6)
                    +"\ncnpj: "+getCNPJ_emit()
                    +"\nMod: "+getMod()
                    +"\nSerie: "+getSerie()
                    +"\nnNF: "+getnNF()
                    +"\nForma Emissao: "+"0"
                    +"\ncNF: "+getcNF()
                    );
        }
        if (display) JOptionPane.showMessageDialog(null,"Vai chamar o metodo calcdv() . . .");
        setcDV(calcdv(wchave));
        if (display) JOptionPane.showMessageDialog(null,"Voltou do metodo calcdv() . . .");
        wchave = wchave+getcDV();
        if (display) JOptionPane.showMessageDialog(null,"Chave completa: "+wchave+"\nTamanho: "+wchave.length());
        setId("NFe"+wchave);
        if (display) JOptionPane.showMessageDialog(null,"Vai chamar o metodo trata_ide() . . .");
        if (getTpemis().equals("4") ) {   //  Contingencia DPEC
            Calendar cal = new GregorianCalendar();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
            setDhCont(sdf.format(cal.getTime()));
            setxJust("Site da Receita Estadual Inativo Temporariamente");
        } else {
            setDhCont(null);
            setxJust(null);
        }
        trata_ide();
        if (display) JOptionPane.showMessageDialog(null,"Voltou do metodo trata_ide() . . .");
    }
    public String calcdv(String wnumero){

        int winic=43-wnumero.length();
if (display) JOptionPane.showMessageDialog(null,"Metodo calcdv() - Numero: "+wnumero+" - Tamanho: "+wnumero.length()+" - Inicio: "+winic);
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
if (display) JOptionPane.showMessageDialog(null,"Metodo calcdv() - wdigito: "+wdigito);
        return ""+wdigito;
    }

    public String getcUF() {
        return cUF;
    }

    public void setcUF(String cUF) {
        this.cUF = cUF;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCNPJ_emit() {
        return CNPJ_emit;
    }

    public void setCNPJ_emit(String CNPJ_emit) {
        this.CNPJ_emit = CNPJ_emit;
    }

    public String getcNF() {
        return cNF;
    }

    public void setcNF(String cNF) {
        this.cNF = cNF;
    }

    public String getCmunfg() {
        return cmunfg;
    }

    public void setCmunfg(String cmunfg) {
        this.cmunfg = cmunfg;
    }

    public String getTpimp() {
        return tpimp;
    }

    public void setTpimp(String tpimp) {
        this.tpimp = tpimp;
    }

    public String getTpemis() {
        return tpemis;
    }

    public void setTpemis(String tpemis) {
        this.tpemis = tpemis;
    }

    public String getFinnfe() {
        return finnfe;
    }

    public void setFinnfe(String finnfe) {
        this.finnfe = finnfe;
    }

    public String getProcemis() {
        return procemis;
    }

    public void setProcemis(String procemis) {
        this.procemis = procemis;
    }

    public String getVerproc() {
        return verproc;
    }

    public void setVerproc(String verproc) {
        verproc = verproc.replaceAll("V:1.00 R01", "V:2.00 R01");
        this.verproc = verproc;
    }
    public String getCPF_dest() {
        return CPF_dest;
    }

    public void setCPF_dest(String CPF_dest) {
        this.CPF_dest = CPF_dest;
    }

    public String getUF_emit() {
        return UF_emit;
    }

    public void setUF_emit(String UF_emit) {
        this.UF_emit = UF_emit;
    }

    public String getEmail_dest() {
        return email_dest;
    }

    public void setEmail_dest(String email_dest) {
        if (email_dest.length()<8) email_dest = null;
        this.email_dest = email_dest;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public Date getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(Date data_emissao) {
        this.data_emissao = data_emissao;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public Date getData_movimento() {
        return data_movimento;
    }

    public void setData_movimento(Date data_movimento) {
        this.data_movimento = data_movimento;
    }

    public String getUF_clie() {
        return UF_clie;
    }

    public void setUF_clie(String UF_clie) {
        this.UF_clie = UF_clie;
    }

    public String getUF_empresa() {
        return UF_empresa;
    }

    public void setUF_empresa(String UF_empresa) {
        this.UF_empresa = UF_empresa;
    }

    public String getModBC() {
        return modBC;
    }

    public void setModBC(String modBC) {
        this.modBC = modBC;
    }
    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public int getMvfatura() {
        return mvfatura;
    }

    public void setMvfatura(int mvfatura) {
        this.mvfatura = mvfatura;
    }

    public String getDesc_CFOP() {
        return desc_CFOP;
    }

    public void setDesc_CFOP(String desc_CFOP) {
        this.desc_CFOP = desc_CFOP;
    }

    public String getTipo_cobranca() {
        return tipo_cobranca;
    }

    public void setTipo_cobranca(String tipo_cobranca) {
        this.tipo_cobranca = tipo_cobranca;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public String getSit_trib() {
        return sit_trib;
    }

    public void setSit_trib(String sit_trib) {
        this.sit_trib = sit_trib;
    }

    private String formata_ean(String ean) {
        String ean_fmt = ean;
        int tam = ean.length();
        for (int i=tam; i<14; i++) {
            ean_fmt = "0"+ean_fmt;
        }
        return ean_fmt;
    }

    private String formataStrDec(String nome, String campo, int dec) {
        String retorno = campo, situ = "";
        int pos_pto = campo.indexOf(".");
        if (pos_pto == -1) {
            retorno += ".000";
            situ = "Estava sem ponto";
        } else {
            int qtd_zeros = (campo.length()-1) - pos_pto;
            if (qtd_zeros < dec) {
                for (int i=qtd_zeros;i<dec;i++) {
                    retorno += "0";
                }
                situ = "Estava com menos de "+dec;
            } else if (qtd_zeros > dec) {
                retorno = retorno.substring(0, pos_pto+dec+1);
                situ = "Estava com mais de "+dec;
            } else {
                situ = "Estava exatamente com "+dec;
            }
        }
        return retorno;
    }

    public String getDhCont() {
        return dhCont;
    }

    public void setDhCont(String dhCont) {
        this.dhCont = dhCont;
    }

    public String getxJust() {
        return xJust;
    }

    public void setxJust(String xJust) {
        this.xJust = xJust;
    }

    private String formataStrEsq(String campo, int tamanho) {
if (display) JOptionPane.showMessageDialog(null, "Metodo formataStrEsq() - campo: "+campo+" - tamanho: "+campo.length());
        String retorno = campo;
        if (campo.length() < tamanho) {
            for (int i=campo.length(); i<tamanho; i++) {
                retorno = "0"+retorno;
if (display) JOptionPane.showMessageDialog(null, "Metodo formataStrEsq() - retorno: "+retorno);
            }
        }
        return retorno;
    }

    public String getIndIEDest() {
        return IndIEDest;
    }

    public void setIndIEDest(String IndIEDest) {
        this.IndIEDest = IndIEDest;
    }

    public String getIdDest() {
        return IdDest;
    }

    public void setIdDest(String IdDest) {
        this.IdDest = IdDest;
    }

    public String getIndFinal() {
        return indFinal;
    }

    public void setIndFinal(String indFinal) {
        this.indFinal = indFinal;
    }
    public void imprimeDanfeJava(String arquivoOrigem){
if (display) JOptionPane.showMessageDialog(null,"GerarXML_nfe.imprimeDanfeJava() - arquivoOrigem: "+arquivoOrigem);
       boolean imprimir = true;
       char tipoImp = 'I';
       tipoImp = Biblioteca.tipoImpressao();
       String arq_xml = arquivoOrigem;
                String emp4dig = ""+empresa;
                int tamtxt = emp4dig.length();
                for (int i=0;i<(4-tamtxt);i++){
                    emp4dig = "0"+emp4dig;
                }
       UIManager.put("OptionPane.yesButtonText", "Imprimir Agora");
       UIManager.put("OptionPane.noButtonText", "Imprimir Depois");
       int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Impressão da Danfe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       if (escolha == JOptionPane.NO_OPTION) {
           JOptionPane.showMessageDialog(null,"Voce desistiu de Imprimir a Danfe!"
                   + "\nVocê poderá Imprimir a Danfe deste Documento Abrindo o Painel de Transmissões e clicando no Botão: DANFE");
        } else {
           UIManager.put("OptionPane.yesButtonText", "Sim");
           UIManager.put("OptionPane.noButtonText", "Não");
            try {
                File arqConfig = new File(drive+"/dados/impDanfe.cfg");
                String texto_string = "<pasta_impdanfe>"+drive+"\\DADOS\\EMPR"+emp4dig+"\\IMPDANFE"+"</pasta_impdanfe>";  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(arqConfig);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo: "+drive+"\\impDanfe.cnf\nErro: "+ex);
            }

            String nProt = "", dhRecbto = "", duplicata = "", duplicata1 = "";
            File arqDanfe    = new File(arq_xml);
            nProt = "";
            dhRecbto = "";
            duplicata = "";
            duplicata1 = "";
            String dEmi = anomesdia;
            char chartxte[]  = new char[(int)arqDanfe.length()];
            String dEmix = Biblioteca.extrair_TAG(arq_xml, "ide", "T", "dhEmi", 1);
            dEmi = dEmix.substring(0,4)+dEmix.substring(5,7)+dEmix.substring(8,10);
            String nDup = "", dVenc = "", vDup = "";
            int parcela = 0, idx = 0;
            while (true) {  
                idx++;
                nDup = Biblioteca.extrair_TAG(arq_xml, "cobr", "T", "nDup", idx);
                vDup = Biblioteca.extrair_TAG(arq_xml, "cobr", "T", "vDup", idx);;
                if (vDup != null) {
                    vDup.replaceAll(".", ",");
                    vDup = "Vlr: "+vDup;
                } else {
                    break;  //  finaliza o while(true)
                }
                if (nDup.indexOf("BONIFI") != -1) {
                    dVenc = "";
                    vDup = "";
                } else if ((nDup.indexOf("CARTEIRA") != -1 && nDup.indexOf("CH") == -1) || nDup.indexOf("A VISTA") != -1 ){
                    dVenc = "        ";
                } else {
                    dVenc = Biblioteca.extrair_TAG(arq_xml, "cobr", "T", "dVenc", 1);
                    if (dVenc!= null && dVenc.length() == 10){
                        dVenc = "Vcto: "+dVenc.substring(8,10)+"/"+dVenc.substring(5,7)+"/"+dVenc.substring(0,4);
                    }
                }
                parcela++;
                if (parcela <= 3) {
                    duplicata += nDup+"  "+dVenc+"  "+vDup+"     ";
                } else {
                    duplicata1 += nDup+"  "+dVenc+"  "+vDup+"     ";
                }
            }
            try {
                String chNfe = Biblioteca.extrair_TAG(arq_xml, "infNFe", "A", "Id", 1);
                chNfe = chNfe.substring(3, chNfe.length());
                HashMap<String, String> parameterMap = new HashMap<String, String>( );
                //le o arq. da pasta NfeProt para capturar os dados do protocolo de autorizacao
                String arq_prot_xml = getCaminho_nfeprot()+"\\"+dEmi+"\\"+chNfe+"_prot.xml";  //+s.substring(0,7)+"P.xml";
                    nProt = Biblioteca.extrair_TAG(arq_prot_xml, "infProt", "T", "nProt", 1);
                    dhRecbto = Biblioteca.extrair_TAG(arq_prot_xml, "infProt", "T", "dhRecbto", 1);
                //atribui o arq.xml para a impressao da Danfe
                JRXmlDataSource xmlDataSource = new JRXmlDataSource(new File(arq_xml), "/enviNFe/NFe/infNFe/det"); 

                //o Nome do parâmetro e o valor é passado ao HashMap
                String pastaImg = drive + fsep + "DADOS" + fsep + "EMPR" + emp4dig + fsep;  //drive+fsep+"DADOS"+fsep+"EMPR"+emp4dig+fsep+"relatorio"+fsep;
                parameterMap.put("pastaImg", pastaImg);
                //atribui os valores dos parametros de protocolo
                parameterMap.put("nProt", nProt);
                parameterMap.put("dhRecbto", dhRecbto);
                parameterMap.put("duplicata", duplicata);
                parameterMap.put("duplicata1", duplicata1);
                String arquivo = System.getProperty("user.dir")+fsep+"Relatorios"+fsep+"Danfe.jasper";

                 //chama fillReport
                JasperPrint jp = JasperFillManager.fillReport(arquivo,
                                        parameterMap, xmlDataSource);

                 //exibe o relatório com viewReport
                if (tipoImp == 'V'){ // visualizar na tela antes de imprimir
                    //JOptionPane.showMessageDialog(null, "Vai imprimir na Tela . . .!");
                    JasperViewer.viewReport(jp, false);
                }  else if (tipoImp == 'I') {  //imprime direto na impressora
                    int totVias = 1;
                    for (int via = 0;via<totVias;via++)
                        JasperPrintManager.printReport(jp, false);
                }  else
                    JOptionPane.showMessageDialog(null, "Voce Desistiu da Impressão!");
            } catch (JRException ejr) {
                JOptionPane.showMessageDialog(null, "Erro(1) ao tentar imprimir o relatorio.jasper. Erro: "+ ejr);
                ejr.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro(2) ao tentar imprimir o relatorio.jasper. Erro: "+ e);
            }
            apaga_arquivo_impdanfe(arquivoOrigem);
       }
    }
    public void apaga_arquivo_impdanfe(String arq_xml){
        String nomeArq = arq_xml.substring(arq_xml.indexOf("IMPDANFE")+9);
//JOptionPane.showMessageDialog(null, "GeraXML_nfe.apaga_arquivo_impdanfe - Nome do arquivo danfe origem: "+nomeArq);
        
        File arqudel    = new File(arq_xml);
        char chartxt[]  = new char[(int)arqudel.length()];
        String arq_saida_xml = getCaminho_impdanfe()+"\\BKP\\"+anomesdia+"\\"+nomeArq;
//JOptionPane.showMessageDialog(null, "GeraXML_nfe.apaga_arquivo_impdanfe - Nome do arquivo danfe destino: "+nomeArq);
        FileReader entrada;
        try {
            entrada = new FileReader(arq_xml);
            entrada.read(chartxt);  
            entrada.close();

            String texto_string = new String(chartxt);  
            byte[] texto_byte = texto_string.getBytes();
            FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
            arquivo_gerado.write(texto_byte);
            arquivo_gerado.close();

        } catch (Exception ex) {
            System.out.println("GeraXML_nfe.apaga_arquivo_impdanfe - Erro ao processar arq.xml(apaga_arquivo_impdanfe): "+ex);
        }
        try {
            arqudel.delete();
            System.out.println("GeraXML_nfe.apaga_arquivo_impdanfe - Apagou o arquivo de origem danfe: "+arqudel);
        } catch (Exception ex) {
            System.out.println("GeraXML_nfe.apaga_arquivo_impdanfe - Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
        }
    }

    public int getForma_pgto() {
        return forma_pgto;
    }

    public void setForma_pgto(int forma_pgto) {
        this.forma_pgto = forma_pgto;
    }
    
}
