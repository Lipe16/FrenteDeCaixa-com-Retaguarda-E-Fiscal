/*

Descrição: Esta classe le a tabela de pedidos digitados e gera o XML para a NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import com.thoughtworks.xstream.XStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Object;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINSOutr;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS00;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS10;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS20;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS40;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN101;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN102;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSSN500;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS51;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS60;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSTot;
import br.com.videoaulasneri.adelcio.nfefacil.bean.NFe;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PIS;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISNT;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISOutr;
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
import br.com.videoaulasneri.adelcio.nfefacil.bean.NFe;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Total;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Transp;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Transporta;
import br.com.videoaulasneri.adelcio.nfefacil.bean.VeicTransp;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Vol;
import br.com.videoaulasneri.adelcio.nfefacil.NFefacil;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Card;
import br.com.videoaulasneri.adelcio.nfefacil.bean.NFref;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Cobr;
import br.com.videoaulasneri.adelcio.nfefacil.bean.InfNFeSupl;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Pag;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Card;
import br.com.videoaulasneri.adelcio.nfefacil.bean.AutXML;

public class GeraXML_nfce {
    ResultSet resultset, result_det;
    Statement statement, stateUpItem, stateUpFat, stateFatura, stateItem;
    Connection con_fat;
    String driver, url, usuario, senha;
    String data_mvto;
    String caminho_ler_assinar;
    String dataGera = "";
    String tpAmb, drive, txt, anomesdia, dataSaida;
    private String Id,indpag, CPF_dest, CNPJ_dest, CPF_CNPJ_dest, xNome_dest, IdDest, IndIEDest, IE_dest, email_dest, nNF, natOp, cDV, cUF, mod, serie, CNPJ_emit, cNF, UF_emit;
    private String tpnf, cmunfg, tpimp, tpemis, finnfe, procemis, verproc, indFinal;
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
    List<Det> ldet = new ArrayList<Det>();
    Prod prod;
    DI DI;
    Imposto imposto;
    PIS pis;
    PISNT pisNT;
    PISAliq pisAliq;
    PISOutr pisOutr;
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
    COFINSOutr cofinsOutr;
    Total total;
    ICMSTot ICMSTot;
    Transp transp;
    Transporta transporta;
    Vol vol;
    NFref nfref;
    Dup dup;
    List<Dup> cobr = new ArrayList<Dup>();
    Pag pag;
    Card card;
    List<Pag> lpag = new ArrayList<Pag>();
    InfAdic infAdic;
    InfNFeSupl infNFeSupl;
    String vProdTrib;
    BigDecimal vProdIbpt;

    Connection consis;
    boolean nao_pegou_natOp;
    private int pedido, sequencia, item, mvfatura;
    private Date data_emissao, data_movimento;
    private String UF_clie, UF_empresa, cpf_cnpj;
    private String modBC;
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
    private BigDecimal total_vTotTrib = BigDecimal.ZERO;
    private BigDecimal total_vTotIbpt = BigDecimal.ZERO;
    private String desc_CFOP;
    private boolean consu_final = false;
    private String sit_trib, wcarro;
    private String dhCont;
    private String xJust;
    private String carroIni, carroFim;
    private String UfEmit, UfDest;
    private String modelonfe = "65";
    String wchave = null;
    private int qtdVol = 0;
    private String strDhEmi = "";
    private String idToken, csc, hashQRCode;
    String fuso_horario = "";  //"-03:00";
    String textoGerado = "";
    String nome_arquivo = "";
    boolean temRegNumNFe = false;
    private String crt = "1";
    private boolean display = true;  //  variavel para definir se deve exibir o display de andamento do processo
    private int codigoEmp;
    private String eAutorizadaTEF;
    private String eCnpjOperadora;
    private String ufStr = "PR";
    int[] tabUfNum = {11, 12, 13, 14, 15, 16, 17,
                    21, 22, 23, 24, 25, 26, 27, 28, 29,
                    31, 32, 33, 35,
                    41, 42, 43,
                    50, 51, 52, 53
    };
    String[] tabUf = {"RO", "AC", "AM", "RR", "PA", "AP", "TO", 
                    "MA", "PI", "CE", "RN", "PB", "PE", "AL", "SE", "BA", 
                    "MG", "ES", "RJ", "SP", 
                    "PR", "SC", "RS", 
                    "MS", "MT", "GO", "DF"

    };
    
    public GeraXML_nfce(
            String tpAmb,
            String data_mvto,
            String caminho_ler_assinar,
            Connection con_fat,
            int empresa,
            String drive,
            String anomesdia,
            //String tpEmis,
            boolean display,
            String pedido,
            String UfEmit,
            String UfDest,
            String fuso_horario,
            String eAutorizacaoTEF,
            String eCnpjOperadora,
            String serie65
        ){
        try {
/*
                JOptionPane.showMessageDialog(null,
                        "Parametros recebidos = "
                        +" - tipoAmb = "+tpAmb
                        +" - data Mvto = "+data_mvto
                        +" - Stc_camLerAss = "+caminho_ler_assinar
                        //+" - connection = "+this.connection
                        +" - Empresa = "+empresa
                        +" - stc_drive = "+drive
                        +" - Stc_anomesdia = "+anomesdia
                        +" - Stc_tipoEmis = "+tpEmis
                        +" - Stc_exibirDsp = "+display
                        +" - Pedido = "+pedido
                        +" - UFEmit/Dest = "+UfEmit
                        );
 * 
 */
         this.tpAmb                 = tpAmb;
         this.data_mvto             = data_mvto.substring(6,10)+"-"+data_mvto.substring(3,5)+"-"+data_mvto.substring(0,2);
         this.caminho_ler_assinar   = caminho_ler_assinar;
         this.con_fat               = con_fat;
         this.empresa               = empresa;
         this.drive                 = drive;
         this.anomesdia             = anomesdia;
         //this.setTpemis(tpEmis);
         this.display               = display;
         this.pedido                = Integer.parseInt(pedido);
         this.UfEmit = UfEmit;
         this.UfDest = UfDest;
         this.fuso_horario = fuso_horario;
         this.eAutorizadaTEF = eAutorizacaoTEF;
         this.eCnpjOperadora = eCnpjOperadora;
         setSerie(serie65);
         //JOptionPane.showMessageDialog(null,"geraXML_nfce() instanciou a classe . . .");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"geraXML_nfce() - Erro: "+ex);

        }
        this.display = false;  ///  escolheExibirDisplay();
     }
    public String trataTexto(String txtParam, String tipoEmis){
//JOptionPane.showMessageDialog(null,"geraXML_nfce().trataTexto()  com fuso_horario: "+this.fuso_horario);
        int idx = 30;
        for (int i=0; i< 27; i++) {
            if (Integer.parseInt(UfEmit) == tabUfNum[i]) {
                idx = i;
                i = 27;
            }
        }
        if (idx != 30) {
            ufStr = tabUf[idx];
        }
        textoGerado = "";
         this.setTpemis(tipoEmis);
        if (display)
            JOptionPane.showMessageDialog(null,"geraXML_nfce().trataTexto() Entrou no metodo . . .");
        try {
        if (display)
            JOptionPane.showMessageDialog(null,"geraXML_nfce().trataTexto() c/anomesdia: "+anomesdia+" e texto: "+txtParam);
            setData_movimento(new java.sql.Date(new SimpleDateFormat("yyyyMMdd").parse(anomesdia).getTime()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"geraXML_nfce().trataTexto() Erro: "+ex);
        }
        this.txt = txtParam;
        //String retorno = "";
        //ler_paramfat();
/*
        Conexao_fatura conex = new Conexao_fatura();
        if (display) JOptionPane.showMessageDialog(null,"Vai conectar b.dados fatura . . .");
        //consis = con_siscom.conecta("postgres", "nerizon", "org.postgresql.Driver", "jdbc:postgresql://localhost/siscom_difal");
        con_fat = conex.conecta("postgres", "nerizon", "org.postgresql.Driver", "jdbc:postgresql://localhost/fatura");
*/
        setIdDest("1");
        setIndFinal("1");

        ldet = null; //.clear();
        cobr = null; //.clear();
        lpag = null; //.clear();
        ldet = new ArrayList<Det>();
        lpag = new ArrayList<Pag>();
        infNFeSupl = null;
        nItem = 0;
        
        if (!trata_fatura()) {
           nome_arquivo = "erro"; 
        }
//        return textoGerado;
        return nome_arquivo;
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
            //JOptionPane.showMessageDialog(null, "As Mensagens passo-a-passo não serão exibidas!");
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
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - entrou . . .");
            ResultSet rs_emp = stateFatura.executeQuery("select * from empresa where codempresa = "+ empresa);
            while (rs_emp.next()) {
                codigoEmp = rs_emp.getInt("codigo");
            }
           wcarro = "";
            setIndpag("0");  
/*            
            ResultSet rs_param = stateFatura.executeQuery("select * from pardigital where empresa = "+ codigoEmp);
            String wserie = "";
            while (rs_param.next()) {
                wserie = rs_param.getString("serie65");
            }
*/            
            String sql_fatura = "select fatu.* from pedidos65 fatu"
                    +" where fatu.pedido = "+pedido+" "
                    +" and empresa = "+empresa
                    +" and fatu.numero_nfe = 0 ";
                    //+" and fatu.serie_nfe = ''"//+wserie
                    //;
            System.out.println("Comando sql_fatura: "+sql_fatura);
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Comando sql_fatura: "+sql_fatura);
            boolean achou = false;
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Vai executar sql(1) . . .");
            ResultSet rs_mvfatura = stateFatura.executeQuery(sql_fatura);
            int qtPedidos = 0;
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Executou sql(1) . . .");
            if (rs_mvfatura != null) {
                while(rs_mvfatura.next()){
                    qtPedidos++;
                    //setPedido(rs_mvfatura.getInt("pedido"));
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Processando o pedido: "+getPedido());
                    cpf_cnpj = rs_mvfatura.getString("documento");
                    String nomeDoc = rs_mvfatura.getString("nome_documento");
                    if (nomeDoc != null) {
                        if (tpAmb.equals("1")) {  //Producao
                            nomeDoc = nomeDoc.trim();
                            setxNome_dest(nomeDoc);
                        } else {  //  Homologacao
                            setxNome_dest("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
                        }
                    }
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Pegou doc.cliente: "+nomeDoc);
                    setQtdVol(rs_mvfatura.getInt("qtde_volume"));
                    if (cpf_cnpj != null) {
                        cpf_cnpj = cpf_cnpj.trim();
                    }
                    if (cpf_cnpj == null) {
                        setCNPJ_dest(null);
                        setCPF_dest(null);
                        setCPF_CNPJ_dest(null);
                    } else if (cpf_cnpj.length() == 14) {
                        setCNPJ_dest(cpf_cnpj);
                        setCPF_dest(null);
                        setCPF_CNPJ_dest(cpf_cnpj);
                    } else {
                        setCNPJ_dest(null);
                        setCPF_dest(cpf_cnpj);
                        setCPF_CNPJ_dest(cpf_cnpj);
                    }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Pegou cpf_cnpj dest: "+cpf_cnpj);
                    //setVendedor(rs_mvfatura.getInt("cod_vendedor"));
                    carrega_parametros();
                    setData_emissao(new Date());  //  (rs_mvfatura.getDate("data_emissao"));
                    setnNF(pegar_prox_numNF());
System.out.println("Numero da NFCe confirmado: "+Integer.parseInt(getnNF()));                    
                    if (Integer.parseInt(getnNF()) == 0) {
                        JOptionPane.showMessageDialog(null,"O Número da NFCe não foi informado e o Arquivo XML não será gerado!");
                        return false;
                    }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Pegou numero nfe: "+getnNF());
    /*
     *     capturar os campos da tabela mvfatura aqui
     */
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
                    total_vTotTrib  = BigDecimal.ZERO;
                    total_vTotIbpt  = BigDecimal.ZERO;

     //  utilizar array para guardar apenas uma msg de cada sit.trib. diferente
                    setSit_trib("");  //("Falta montar texto da sit.tributaria");
                    List<Integer> listTrib = new ArrayList<Integer>(); ;
                    List<String> listAliq = new ArrayList<String>(); ;;
                    nao_pegou_natOp = true;
                    BigDecimal vlrProd = BigDecimal.ZERO;
                    BigDecimal vlrDesc = BigDecimal.ZERO;
                    BigDecimal vlrTotal = BigDecimal.ZERO;
                    try {
                        String sql_item = "select * from produtos65"
                                +" where "
                                +" pedido = "+getPedido()
                                +" and empresa = "+empresa
                                +" order by pedido, item";
                        System.out.println("Comando sql_item: "+sql_item);
            if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Comando sql_item: "+sql_item);
                        ResultSet rs_item = stateItem.executeQuery(sql_item);
                        while(rs_item.next()){
                            boolean cancelado = rs_item.getBoolean("cancelado");
                            if (!cancelado) {
                            nItem++;
                            if (display) JOptionPane.showMessageDialog(null,"\nInicio de Tratamento de trata_detProd() . . .");  //suprimir
                            float peso_medio = 0;
                            String tipo_preco = "UN";
                            //String tipo_peso = "KG";
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
                            vProdTrib = null;
                            vProdIbpt = BigDecimal.ZERO;
                            double wtrib_st_perc = 0;
                            //int peso_faixa = rs_item.getInt("peso_faixa");
                            float peso_fat = rs_item.getFloat("peso");
                            float qtde_fat = rs_item.getFloat("quantidade");
                            float wqtdfat = peso_fat;
                            BigDecimal vl_qCom = new BigDecimal(rs_item.getFloat("quantidade")).setScale(4, RoundingMode.HALF_EVEN);
                            vlrProd = rs_item.getBigDecimal("vlr_produto").setScale(2, RoundingMode.HALF_EVEN);
                            vlrTotal = rs_item.getBigDecimal("vlr_total").setScale(2, RoundingMode.HALF_EVEN);
                            String vDesc = null;
                            vlrDesc = rs_item.getBigDecimal("vlr_desconto").setScale(2, RoundingMode.HALF_EVEN);
                            if (vlrDesc.compareTo(BigDecimal.ZERO) != 0) {
                                vDesc = "" + vlrDesc;
                            }
                            String qCom = "" + vl_qCom;
                            String vUnTrib = "" + rs_item.getBigDecimal("vlr_unitario").setScale(4);
                            int witem = rs_item.getInt("item");
                            int wcfop = rs_item.getInt("cod_cfop");
                            double wicms_pred = 0;
                            String wicms_cst = "", wpis_CST = "", wcofins_CST = "", wTipoNcm = "";
                            int wcod_produto = rs_item.getInt("cod_produto");
                            String CEST = null;  //  cod.especificador de subst.tributaria incluido em 08/12/2015

                            String sql_produto = "select * from produto where codigo = "+wcod_produto;
                            //System.out.println("Comando sql_produto: "+sql_produto);
                if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_mvfatura() - Comando sql_produto: "+sql_produto);
                            ResultSet rs_produto = statement.executeQuery(sql_produto);
                            while(rs_produto.next()){
                                wdescprod = Biblioteca.trataString(rs_produto.getString("nome_reduzido"));
                                //if (wdescprod.length() > 35) wdescprod = wdescprod.substring(0,35);
                                if (wdescprod.length() > 44) wdescprod = wdescprod.substring(0,44);
                                //tipo_preco      = rs_produto.getString("tipo_preco");
                                //tipo_peso       = rs_produto.getString("tipo_peso");
                                peso_medio      = rs_produto.getFloat("peso");
                                cProd           = rs_produto.getString("codigo");
                                //sigla           = Biblioteca.trataString(rs_produto.getString("sigla"));
                                cEAN            = rs_produto.getString("ean");
                                ncm             = rs_produto.getString("ncm");
                                wTipoNcm         = rs_produto.getString("tipo_ncm");
                                //incidePisCofins = rs_produto.getBoolean("pis_cofins");
                                //diferido        = rs_produto.getBoolean("diferido");
                                wunidade        = rs_produto.getString("unidade");
                                wtabtrib        = rs_produto.getInt("icms_cst");
                                wicms_cst       = rs_produto.getString("icms_cst");
                                //wtabtrib_f      = rs_produto.getInt("trbicm_fora_s");
                                wpericm         = rs_produto.getFloat("icms_perc");
                                wicms_pred      = rs_produto.getDouble("icms_pred");
                                //wpericm_f       = rs_produto.getFloat("pericm_fora");
                                wpericm_d       = wpericm;  //rs_produto.getFloat("pericm_d");
                                //trb_pis         = rs_produto.getInt("pis_perc");
                                //trb_cofins      = rs_produto.getInt("cofins_perc");
                                wpis_CST = rs_produto.getString("pis_cst");
                                wpis_perc = rs_produto.getDouble("pis_perc");
                                wcofins_CST = rs_produto.getString("cofins_cst");
                                wcofins_perc = rs_produto.getDouble("cofins_perc");

                                wtrib_st_perc = rs_produto.getDouble("trib_st_perc");
                                CEST = rs_produto.getString("cest").trim();
                            }
                            if (cEAN != null && cEAN.length() > 3) {
                                wcodInterno = cEAN;
                            } else {
                                wcodInterno = cProd;
                            }
                            if (CEST != null && CEST.length() < 7) {
                                CEST = null;
                            }
                if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - passou 1 . . . ");
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

                            String CFOP = ""+wcfop;
                            String uCom = wunidade;  //"XX";

                            String sql_cfop = "select * from cfop where cfop = "+wcfop;
                            //System.out.println("Comando sql_produto: "+sql_produto);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - Comando sql_cfop: "+sql_cfop);
                            setDesc_CFOP("");
                            ResultSet rs_cfop = statement.executeQuery(sql_cfop);
                            while(rs_cfop.next()){
                                CFOP = rs_cfop.getString("cfop");
                                setDesc_CFOP(rs_cfop.getString("descricao"));
                            }
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - Desc_cfop: "+getDesc_CFOP());
    //JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - passou 1 . . . ");
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
                           // wdescprod += spac + wqtdfatx;
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

    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 2.2 . . . wtrib_st_perc: "+wtrib_st_perc);
                            String vProd = ""+vlrProd;
                            if (wtrib_st_perc > 0) {
                                BigDecimal wvlrTrib = (rs_item.getBigDecimal("vlr_total").multiply(new BigDecimal(wtrib_st_perc/100))).setScale(2, RoundingMode.HALF_EVEN);
                                vProdTrib = ""+wvlrTrib;
                                total_vTotTrib  = total_vTotTrib.add(wvlrTrib);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_fatura() - passou 2.2.1 . . . vProdTrib: "+vProdTrib);
                            }

                            String sql_ibpt = "select * from ibpt where uf = '" + ufStr + "' and ncm = '" + ncm + "' and tipo = '" + wTipoNcm + "'";
                            //System.out.println("Comando sql_produto: "+sql_produto);
if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - Comando sql_ibpt: "+sql_ibpt);
                            double aliqFederal = 0.0, aliqEstadual = 0.0, aliqMunicipal = 0.0;
                            Date vigenciaInicio = null;
                            Date vigenciaFim = null;
                            ResultSet rs_ibpt = statement.executeQuery(sql_ibpt);
                            while(rs_ibpt.next()){
                                aliqFederal     = rs_ibpt.getDouble("aliqNacionalFederal");
                                aliqEstadual    = rs_ibpt.getDouble("aliqEstadual");
                                aliqMunicipal   = rs_ibpt.getDouble("aliqMunicipal");
                                vigenciaInicio  = rs_ibpt.getDate("vigenciaInicio");
                                vigenciaFim     = rs_ibpt.getDate("vigenciaFim");
                            }
                            if (vigenciaInicio == null || vigenciaFim == null) {
                                JOptionPane.showMessageDialog(null,"Não encontrado Aliquotas no cad.IBPT para o produto: " + wcod_produto + "\n Verifique no Cad.Produtos se o NCM deste produto está correto. " + "\nSe estiver correto, favor Baixar as Tabelas do site: https://deolhonoimposto.ibpt.org.br/Site/PassoPasso \npara a pasta: c:\\nfe\\ibpt e Importar na Opção: Importar Tabela IBPT do Menu Administração");
                            } else if (getData_emissao().before(vigenciaInicio) || getData_emissao().after(vigenciaFim)) {
                                JOptionPane.showMessageDialog(null,"Data de Vigencia fora do prazo para o produto: " + wcod_produto);
                            } else {
                                vProdIbpt = vlrTotal.multiply(new BigDecimal(aliqFederal / 100))
                                        .add(vlrTotal.multiply(new BigDecimal(aliqEstadual / 100)))
                                        .add(vlrTotal.multiply(new BigDecimal(aliqMunicipal / 100))).setScale(2, RoundingMode.HALF_EVEN);
if (display) JOptionPane.showMessageDialog(null,"geraXML_siscom.trata_mvfatura() - vProdIbpt: "+vProdIbpt 
                + " - vlrTotal: " + vlrTotal 
                + "  - aliqFed: " + aliqFederal 
                + " - aliqEst: " + aliqEstadual 
                + "  - aliqMun: " + aliqMunicipal);
                                total_vTotIbpt = total_vTotIbpt.add(vProdIbpt);
                            }
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
                            if (cEAN.length() > 0 && cEAN.substring(0,1).equals("2")) {
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
                            if (nItem == 1 && tpAmb.equals("2")) { //  Ambiente de Homologacao
                                xProd = "NOTA FISCAL EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL";
                            }
                            if (ncm == null || ncm.length() < 8) ncm = "16010000";
                            uCom    = tiraEspacoFinal(uCom);
                            uTrib   = tiraEspacoFinal(uCom);  //tiraEspacoFinal(uTrib);
                            xProd   = tiraEspacoFinal(xProd);
                            if (nao_pegou_natOp) {
                                setNatOp(tiraEspacoFinal(getDesc_CFOP()));
                                nao_pegou_natOp = false;
                            }
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

    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_detIcms() - consu_final: "+consu_final+" - diferido: "+diferido);
                            if (consu_final && !diferido) {
                                CST = "40";
                                icms_pICMS = 0;
                            } else {
                                String sql_trib;
                                ResultSet rs_trib;
                                icms_pICMS  = wpericm;
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 5 . . . ");
                                if (consu_final && !diferido && icms_pICMS == 0) {
                                    icms_pICMS = wpericm_d;
                                } else {
                                    icms_pRedBC = wicms_pred;
                                }
                                origem_cst = "0";
                                CST = wicms_cst;
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 6 . . . ");
                                float reducao = 1;
                                if ( icms_pICMS > 0 && icms_pRedBC > 0) {
                                    reducao = (float)(icms_pRedBC / icms_pICMS);
                                }
                                modBC    = getModBC();
                                icms_vBC = vlrTotal.multiply(new BigDecimal(reducao));
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 7 . . . ");
                                icms_vICMS = (icms_vBC.multiply(new BigDecimal(icms_pICMS / 100))).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                vBC      = String.valueOf(icms_vBC.setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                pICMS    = String.valueOf(new BigDecimal(icms_pICMS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                vICMS    = String.valueOf(icms_vICMS);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 8 . . . ");
                                pRedBC   = String.valueOf(new BigDecimal(icms_pRedBC).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            }  //  fim if (consufinal)

                            //JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_detIcms() - passou 2 . . .");  //suprimir
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
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 11 . . . ");

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
                total_vNF = total_vNF.add(vlrTotal);
    if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 14 . . . ");

                            double percpis = 0, perccofins = 0;
    //trata_detPis();
                    //JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_detPis() com vlrProd = "+vlrProd);  //suprimir
    // Variaveis para PIS - verificar CST correto aqui
                            String pis_CST = "00";
                            pis_CST = wpis_CST;
                            if (pis_CST.equals("") || pis_CST.equals("00")) pis_CST = "08";
                            percpis = wpis_perc;
     if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - PIS passou 15 . . . ");
                           BigDecimal pis_vBC = BigDecimal.ZERO;
                            double pis_pPIS = 0;
                            BigDecimal pis_vPIS = BigDecimal.ZERO;
                            String vBC_pis  = "0.00";
                            String pPIS     = "0.0000";
                            String vPIS     = "0.00";
                            if (pis_CST.equals("01")) {
                                pis_vBC = vlrTotal;
                                pis_pPIS = percpis;
                                pis_vPIS = pis_vBC.multiply(new BigDecimal(pis_pPIS / 100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                vBC_pis  = String.valueOf(pis_vBC);
                                pPIS     = String.valueOf(new BigDecimal(pis_pPIS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                vPIS     = String.valueOf(pis_vPIS);

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
                            }  else if (pis_CST.equals("49")) {
                                 pisOutr = new PISOutr(pis_CST, vBC_pis, pPIS, vPIS);
                                pis = new PIS(pisOutr);
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
                            String CST_cof = "00";
                            String vBC_cof = "0.00";
                            String pCOFINS = "0.0000";
                            String vCOFINS = "0.00";
                            if (cofins_CST.equals("01")) {
                                cofins_vBC = vlrTotal;
                                cofins_pCOFINS = perccofins;
                                cofins_vCOFINS = cofins_vBC.multiply(new BigDecimal(cofins_pCOFINS / 100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                CST_cof = cofins_CST;
                                vBC_cof = String.valueOf(cofins_vBC);
                                pCOFINS = String.valueOf(new BigDecimal(cofins_pCOFINS).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                                vCOFINS = String.valueOf(cofins_vCOFINS);

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
                            } else if (cofins_CST.equals("49")) {
                                CST_cof = cofins_CST;
                                cofinsOutr = new COFINSOutr(CST_cof, vBC_cof, pCOFINS, vCOFINS);
                                cofins = new COFINS(cofinsOutr);
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
//  substituir comando update por comando insert into . . .
                            boolean sucesso = false;
                            String sql_query = "select * from nf_produtos where "
                                    +" empresa = "+empresa
                                    +" and pedido = "+getPedido()
                                    +" and item = "+witem
                                    ;
                            resultset = statement.executeQuery(sql_query);
                            while(resultset.next()){
                                sucesso = true;
                            }
                            if (sucesso) {
                                String sql_update_item1 = "update nf_produtos set"
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
                                        +" cofins_vlr       = "+cofins_vCOFINS
                                        +" where"
                                        +" empresa = "+empresa
                                        +" and pedido = "+getPedido()
                                        +" and item = "+witem
                                        ;
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.2 . . . ");
                                try {
        //if (display) JOptionPane.showMessageDialog(null,"Comando sql_update_item: "+sql_update_item);
                                    int result = stateUpItem.executeUpdate(sql_update_item1);
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.3 . . . ");
                                } catch(Exception e) {
                                    JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar a tabela mvfatura_item. Erro: "+e);
                                }
                             } else {
                                String sql_update_item = "insert into nf_produtos ("
                                                +" empresa,"
                                                +" pedido,"
                                                + " item,"
                                                + " cod_produto,"
                                                + " cod_cfop,"
                                                + " quantidade,"
                                                + " peso,"
                                                + " vlr_unitario,"
                                                + " vlr_produto,"
                                                + " vlr_desconto,"
                                                + " vlr_total,"
                                                + " icms_bc,"
                                                + " icms_perc,"
                                                + " icms_pred,"
                                                + " icms_vlr,"
                                                + " icms_cst,"
                                                + " pis_bc,"
                                                + " pis_perc,"
                                                + " pis_vlr,"
                                                + " pis_cst,"
                                                + " cofins_bc,"
                                                + " cofins_perc,"
                                                + " cofins_vlr,"
                                                + " cofins_cst,"
                                                + " vtottrib,"
                                                + " vtotibpt,"
                                                + " cod_interno,"
                                                + " preco_custo"
                                                + ") values ("
                                                +empresa+", "
                                                + getPedido()+", "
                                                +nItem+", "
                                                +wcod_produto+", "
                                                +wcfop+", "
                                                +qtde_fat+", "
                                                +peso_fat+", "
                                                +vUnTrib+", "
                                                +vlrProd+", "
                                                +vlrDesc+", "
                                                +vlrTotal+", "
                                                +icms_vBC + ", "
                                                +icms_pICMS + ", "
                                                +icms_pRedBC + ", "
                                                +icms_vICMS + ", "
                                                +"'"+CST + "', "
                                                +pis_vBC + ", "
                                                +pis_pPIS + ", "
                                                +pis_vPIS + ", "
                                                +"'"+pis_CST + "', "
                                                +cofins_vBC + ", "
                                                +cofins_pCOFINS + ", "
                                                +cofins_vCOFINS+", "
                                                +"'"+cofins_CST + "', "
                                                +vProdTrib + ", "
                                                +vProdIbpt + ", "
                                                +"'"+wcodInterno + "', "
                                                +0+" "
                                                +")";

if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.2 . . . ");
                                try {
if (display) JOptionPane.showMessageDialog(null,"Comando insert nf_produtos: "+sql_update_item);
                                    int result = stateUpItem.executeUpdate(sql_update_item);
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - passou 18.3 . . . ");
                                } catch(Exception e) {
                                JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar a tabela mvfatura_item. Erro: "+e);
                                }
                             }
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
                    String vTotTrib = null;
                    if (total_vTotTrib.compareTo(BigDecimal.ZERO) != 0) {
                        vTotTrib = "" + total_vTotTrib.setScale(2, RoundingMode.HALF_EVEN);
                    }
    if (display) {
                    JOptionPane.showMessageDialog(null,"trata_total - 01(vBC): "+vBC);
                    JOptionPane.showMessageDialog(null,"trata_total - 02(vICMS): "+vICMS);
                    JOptionPane.showMessageDialog(null,"trata_total - 03(vProd): "+vProd);
                    JOptionPane.showMessageDialog(null,"trata_total - 04(vPIS): "+vPIS);
                    JOptionPane.showMessageDialog(null,"trata_total - 05(vCOFINS): "+vCOFINS);
                    JOptionPane.showMessageDialog(null,"trata_total - 06(vNF): "+vNF);
                    JOptionPane.showMessageDialog(null,"trata_total - 07(vTotTrib): "+vTotTrib);
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
                    String modFrete     = "9";
                    transp      = new Transp(modFrete, null, null, null);
//  inicio pag
                   try {
                        String sql_pgto = "select * from tipopgto65"
                                +" where "
                                +" pedido = "+getPedido()
                                +" and empresa = "+empresa
                                +" order by id_tipo_pgto desc";
                        System.out.println("Comando sql_item: "+sql_pgto);
            if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Comando sql_pgto: "+sql_pgto);
                        ResultSet rs_pgto = stateItem.executeQuery(sql_pgto);
                        double totPgto = total_vNF.doubleValue();  //  vlrTotal.doubleValue();
                        while(rs_pgto.next()){
                            int idtipo  = rs_pgto.getInt("id_tipo_pgto");
                            double vpag = rs_pgto.getFloat("valor");
                            if (idtipo > 1) {
                                if (vpag > total_vNF.doubleValue()) {
                                    vpag = total_vNF.doubleValue();
                                }
                                totPgto = totPgto - vpag;
                            } else { //  idtipo == 1 = dinheiro
                                vpag = totPgto;
                            }
                            String sql_cadtipo = "select * from tipo_pgto where id = "+idtipo;
                if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_mvfatura() - Comando sql_cadtipo: "+sql_cadtipo);
                            ResultSet rs_cadtipo = statement.executeQuery(sql_cadtipo);
                            String codtipo = "00";
                            while(rs_cadtipo.next()){
                                codtipo = Biblioteca.trataString(rs_cadtipo.getString("codigo"));
                            }
                            String tPag, vPag;
                            tPag = codtipo.trim();
                            vPag = ""+String.format("%.2f", vpag);
                            vPag = vPag.replaceAll(",", ".");
if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_mvfatura() - Tipo de Pgto: ["+tPag+"] . . .");
                            if (tPag.equals("03")) {
                                 String tpIntegra = "1";
                                String CNPJ = eCnpjOperadora;
                                String tBand = "99";
                                String cAut = eAutorizadaTEF;
                                card = new Card(tpIntegra, CNPJ, tBand, cAut);
                           } else if ( tPag.equals("04") ) {
                                String tpIntegra = "1";
                                String CNPJ = "99999999999999";
                                String tBand = "99";
                                String cAut = "999";
                                card = new Card(tpIntegra, CNPJ, tBand, cAut);
                            }
                            pag = new Pag(tPag, vPag, card);
                            lpag.add(pag);
                       }
                    } catch(Exception e) {
                       JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - Erro ao tentar criar a tag pag: "+e);
                    }
//  fim pag
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 3 . . . ");

                    String infCpl = Biblioteca.trataString(rs_mvfatura.getString("dados_adicionais"));
                    infCpl = infCpl.trim();
         if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 4 . . . ");
                   if (infCpl != null && infCpl.length() > 0) {
                        infAdic = new InfAdic(infCpl);
                    } else {
                        infAdic = null;
                    }
                    geraArqXML(""+Integer.parseInt(getnNF()));
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 5 . . . ");
                    atualizar_numeronf();
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 6 . . . ");
                    atualizar_mvfatura();
        if (display) JOptionPane.showMessageDialog(null,"geraXML_fat.trata_fatura() - leu nf_prazo 7 . . . ");
                 }
            } else {
                JOptionPane.showMessageDialog(null,"Não foi encontrado nenhum Pedido para Gerar XML! ");
           }
            if (qtPedidos == 0) {
                JOptionPane.showMessageDialog(null,"Não foi encontrado nenhum Pedido para Gerar XML! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"trata_total - Erro ao tentar ler a tabela: fatura. Erro: "+e);
        }
       return retorno;
    }
    private String pegar_prox_numNF() {
        int numnf = 0;
        temRegNumNFe = false;
       try {
            String sql_numeronfe = "select max(numeronfe) as ultima_nfe from numeronfe where modelonfe = '"+modelonfe+"' and ambiente = '"+tpAmb+"' and empresa = "+empresa+" and serienfe = '"+getSerie()+"'";
System.out.println("GeraXML_NFe.pegar_prox_numNF() - comando sql: "+sql_numeronfe);           
            ResultSet rs_numeronfe = statement.executeQuery(sql_numeronfe);
            while(rs_numeronfe.next()){
                numnf = rs_numeronfe.getInt("ultima_nfe");
                temRegNumNFe = true;
System.out.println("GeraXML_NFe.pegar_prox_numNF() - encontrou num.nfe: "+numnf);           
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
            strNumnf = JOptionPane.showInputDialog("Confirme o Número desta NFCe", strNumnf);
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
             System.out.println("Comando sql_update_numeronfe: "+sql_numeronfe);
            }
            int result = statement.executeUpdate(sql_numeronfe);
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar inserir na tabela: numeronfe. Erro: "+e);
        }
    }
    private void atualizar_mvfatura() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date data_emis = new Date();
    String sql_update = "";
        try {
            data_emis = new java.sql.Date(format.parse(anomesdia).getTime());
        //String sql_query = "select ";
            boolean sucesso = false;
            String sql_query = "select * from nf where "
                    +" pedido = "+getPedido()
                    +" and modelonfe = '"+modelonfe+"'"
                    +" and empresa = "+empresa
                    ;
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()){
                sucesso = true;
            }
if (display) JOptionPane.showMessageDialog(null,"Resultado da busca do pedido "+getPedido()+" na tabela nf: "+sucesso);
            int result = 0;
            if (sucesso) {
                 sql_update = "update nf set"
                    +" chave_NFe = '" + chaveFormat(getId().substring(3, getId().length())) + "', "
                    +" data_emissao = '" + data_emis + "', "
                    +" icms_bc = " + total_vbc + ", "
                    +" icms_vlr = " + total_vicms + ", "
                    +" valor_produtos = " + total_vProd + ", "
                    +" valor_descontos = " + total_vDesc + ", "
                    +" ipi_vlr = " + total_vIPI + ", "
                    +" pis_vlr = " + total_vPIS + ", "
                    +" cofins_vlr = " + total_vCOFINS + ", "
                    +" valor_total = " + total_vNF + ", "
                    +" numero_nfe = " + getnNF() + ", "
                    +" serie_nfe = '" + getSerie() + "', "
                    +" tpemis = '" + getTpemis() +"', "
                    +" tpamb = '" + tpAmb +"' "
                    +" where pedido = " + getPedido()
                    +" and modelonfe = '"+modelonfe+"'";
                    ;
if (display) JOptionPane.showMessageDialog(null,"Comando sql_update(nf): "+sql_update);
                result = stateUpFat.executeUpdate(sql_update);
                if (display) JOptionPane.showMessageDialog(null,"Tabela fatura atualizado com numeronf: "+getnNF());

            } else  {
                 sql_update = "insert into nf "
                        +" ("
                        +" empresa,"
                        +" pedido,"
                        + " cod_cliente,"
                        + " cod_forma_pgto,"
                        + " cod_tipo_doc,"
                        + " cod_banco,"
                        + " data_digitacao,"
                        + " valor_produtos,"
                        + " valor_descontos,"
                        + " valor_total,"
                        + " cod_transportador,"
                        + " dados_adicionais,"
                        + " qtde_volume,"
                        + " peso_volume,"
                        + " placa_veiculo,"
                        + " uf_placa,"
                        + " pedido_cliente,"
                        + " numero_nfe,"
                        + " serie_nfe,"
                        + " data_emissao,"
                        + " chave_nfe,"
                        + " icms_bc,"
                        + " icms_vlr,"
                        + " pis_bc,"
                        + " pis_vlr,"
                        + " cofins_bc,"
                        + " cofins_vlr,"
                        + " vtottrib,"
                        + " vtotibpt,"
                        + " fin_nfe,"
                        + " modelonfe,"
                        + " doc_cliente,"
                        + " tpemis,"
                        + " tpamb"
                       + ")"
                       +" values ("
                       +empresa+", "
                       +getPedido()+", "
                       +"0, "
                       +"1, "
                       +"'AV', "
                       +"1, "
                        +"'" + data_emis + "', "  //  data_digitacao
                        +total_vProd + ", "
                        +total_vDesc + ", "
                        +total_vNF + ", "
                        +"0, "
                        +"'', "  //  dados_adicionais
                        +getQtdVol()+", "
                        +0+", "
                        +"'', "  //  placa veiculo
                        +"'', "  //  uf_placa
                        +"'', "  //  pedido_cliente
                        +getnNF()+", "
                        +"'"+getSerie()+"', "
                        +"'" + data_emis + "', "  //  data_emis
                        +"'"+chaveFormat(getId().substring(3, getId().length()))+"', "
                        +total_vbc+", "
                        +total_vicms+", "
                        +total_vbc+", "
                        +total_vPIS+", "
                        +total_vbc+", "
                        +total_vCOFINS+", "
                        +total_vTotTrib+", "
                        +total_vTotIbpt+", "
                        +"'"+getFinnfe()+"', "
                        +"'"+modelonfe+"', "
                        +"'"+getCPF_CNPJ_dest()+"', "
                        +"'"+getTpemis()+"', "
                        +"'"+tpAmb+"' "
                        +")"
                        ;
        //            System.out.println("Comando sql_update: "+sql_update);
        if (display) JOptionPane.showMessageDialog(null,"Comando insere nf: "+sql_update);
                    result = stateUpFat.executeUpdate(sql_update);
        //if (display)
//JOptionPane.showMessageDialog(null,"Tabela fatura atualizado com numeronf: "+getnNF());
                }
//                 sql_update = "update pedidos65 set"
//                    +" numero_nfe = " + getnNF() + ", "
//                    +" serienfe = '" + getSerie() + "' "
//                    +" where pedido = "+pedido+" "
//                    +" and empresa = "+empresa;
//if (display) JOptionPane.showMessageDialog(null,"Comando sql_update(pedidos65): "+sql_update);
//                result = stateUpFat.executeUpdate(sql_update);
//                if (display) JOptionPane.showMessageDialog(null,"Tabela pedidos65 atualizado com numeronf: "+getnNF());
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar a tabela nf. Erro: "+e+" - sql: "+sql_update);
            }
     }
    public String geraArqXML(String numeronf){
        String xml = "";
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Entrou no try . . .");

            //instancia o XStream
            XStream xstream = new XStream();
if (display) JOptionPane.showMessageDialog(null, "metodo geraArqXml() - Passou instancia XStream . . .");

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
            nome_arquivo = caminho_ler_assinar+"\\"+wchave+"-NFe.xml";  //+"//E"+nnf6pos+".xml";
//if (display) JOptionPane.showMessageDialog(null,"metodo geraArqXml() - Conteudo da NFe: "+xml);
            if (gravaArqXML(nome_arquivo, xml)){
                System.out.println("A rotina de atualizacao da tabela NF está suspensa temporariamente para testes");
            }
//JOptionPane.showMessageDialog(null, "Finalizou o metodo geraArqXML() . . .");

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
    public void carrega_parametros(){
        String sql = "";
        try {
            sql = "select * from pardigital where empresa = "+ codigoEmp;
            if (display) JOptionPane.showMessageDialog(null,"Comando sql_pardigital: "+sql);
            resultset = statement.executeQuery(sql);
            while(resultset.next()){
                setMod(modelonfe);  //(resultset.getString("mod"));
//                setSerie(resultset.getString("serie65"));  //
                setTpnf("1");  //(resultset.getString("tpnf"));
                setTpimp("4");  //(resultset.getString("tpimp"));
                setTpemis("1");
                setFinnfe("1");  //(resultset.getString("finnfe"));
                setProcemis("0");  //(resultset.getString("procemis"));
                setIdToken(resultset.getString("idToken"));
                setCsc(resultset.getString("csc"));
                setVerproc(resultset.getString("verproc"));
                setCmunfg(NFefacil.getCodcid());
                setcUF(UfEmit);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar carregar dados do pardigital! \nErro: "+ex+"\n");
            txt = "";
        }

    }
    public boolean gravaArqXML(String nome_arquivo, String texto_retornado){
        try
        {
             int posicao = texto_retornado.indexOf("<listDet>");
             if (posicao >=0) {
                texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+10,texto_retornado.length());
                posicao = texto_retornado.lastIndexOf("</listDet>");
                if (posicao >=0) {
                    texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+11,texto_retornado.length());
                }
             }

             posicao = texto_retornado.indexOf("<listPag>");
             if (posicao >=0) {
                texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+10,texto_retornado.length());
                 posicao = texto_retornado.lastIndexOf("</listPag>");
                 if (posicao >=0) {
                    texto_retornado = texto_retornado.substring(0, posicao)+texto_retornado.substring(posicao+11,texto_retornado.length());
                 }
             }

             texto_retornado = texto_retornado.replaceAll("&#xd;", "");
             texto_retornado = texto_retornado.replaceAll("nfefacil.bean.", "");
             texto_retornado = texto_retornado.replaceAll("br.com.videoaulasneri.adelcio.Det","det");
             texto_retornado = texto_retornado.replaceAll("br.com.videoaulasneri.adelcio.Dup","dup");
             texto_retornado = texto_retornado.replaceAll("br.com.videoaulasneri.adelcio.Pag","pag");
             //System.out.println("\n\nTexto fim: "+texto_retornado);
             normalizeXML(texto_retornado);
            while (texto_retornado.indexOf(" <") != -1){
                    texto_retornado = texto_retornado.replaceAll(" <", "<");
            }
            while (texto_retornado.indexOf("\n") != -1){
                    texto_retornado = texto_retornado.replaceAll("\n", "");
            }
            while (texto_retornado.indexOf("\r") != -1){
                    texto_retornado = texto_retornado.replaceAll("\r", "");
            }
//if (display) JOptionPane.showMessageDialog(null,"\n\nTexto fim: "+texto_retornado);
             textoGerado = texto_retornado;
//  grava o arquivo XML na pasta: ASSINAR
             byte [] texto = texto_retornado.getBytes();
             FileOutputStream arquivo_gerado = new FileOutputStream(nome_arquivo);
             arquivo_gerado.write(texto);
             arquivo_gerado.close();

if (display) JOptionPane.showMessageDialog(null,"\n\nCriado o arquivo: "+nome_arquivo);
             return true;
        }
        catch(Exception erro_arquivo)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível gravar esse arquivo: "+erro_arquivo);
            return false;
        }
    }
    private boolean trata_emit(){
        if (display) JOptionPane.showMessageDialog(null,"\nEntrou no metodo trata_emit()");  //suprimir
        String CNPJ = "", xNome = "", xFant = "", xLgr = "", nro = "", xCpl = "", xBairro = "", cMun = "",
                xMun = "", CEP = "", cPais = "", xPais = "", fone = "", IE = "", tpnf = "", dataSaida = "";
        try {
            String sql_emit = "select e.*, ibge.codcidade as codcid, ibge.cidade as cidade, ibge.uf as ufcid from empresa e join ibge ibge on e.codcidade = ibge.codigo where e.codempresa = "+empresa;
if (display) JOptionPane.showMessageDialog(null,"Comando sql_emit: "+sql_emit);
            ResultSet rs_emit = statement.executeQuery(sql_emit);
            while(rs_emit.next()){
                CNPJ    = Biblioteca.trataTiraPonto(rs_emit.getString("cnpj"));  //("e.cnpj_cpf"));
                xNome   = Biblioteca.trataString(rs_emit.getString("razao"));  //("e.razao");
                xFant   = Biblioteca.trataString(rs_emit.getString("fantasia"));  //("e.fantasia");
                xLgr    = Biblioteca.trataString(rs_emit.getString("endereco"));  //("e.endereco");
                nro     = rs_emit.getString("numero");  //("e.numero");
                xCpl    = Biblioteca.trataString(rs_emit.getString("complemento"));  //("e.complemento");
                xBairro = Biblioteca.trataString(rs_emit.getString("bairro"));  //("e.bairro");
                cMun    = rs_emit.getString("codcid").substring(0,7);  //("ibge.codcidade");
                xMun    = Biblioteca.trataString(rs_emit.getString("cidade")); //("ibge.cidade");
                setUF_empresa(rs_emit.getString("ufcid"));  //("ibge.uf"));
                CEP     = Biblioteca.trataTiraPonto(rs_emit.getString("cep"));  //("e.cep");
                cPais   = rs_emit.getString("codigo_pais_nfe");  //("e.codigo_pais_nfe");
                xPais   = "BRASIL";
                fone    = Biblioteca.trataTiraPonto(rs_emit.getString("telefone"));  //("e.fone"));
                IE      = Biblioteca.trataTiraPonto(rs_emit.getString("inscest"));  //("e.insc_est"));
                tpnf    = "1";  //rs_emit.getString("tipo_nf");  //("e.tipo_nf");
                crt     = rs_emit.getString("crt");  //("e.tipo_nf");
//JOptionPane.showMessageDialog(null,"trata_empresa.codcidade: "+cMun);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler a tabela empresa. Erro: "+e);
        }
        CEP = formataStrZeroIni(CEP, 8);
        setCNPJ_emit(CNPJ);//
        setUF_emit(getUF_empresa());
        dataSaida = anomesdia.substring(6,8)+"/"+anomesdia.substring(4,6)+"/"+anomesdia.substring(0,4);
        System.out.println("Anomesdia: ["+anomesdia+"] - Data da Saida: "+dataSaida);

        enderEmit   = new EnderEmit(xLgr, nro, xBairro, cMun, xMun, getUF_empresa(), CEP, cPais, xPais, fone);

        emit        = new Emit( CNPJ, xNome, xFant, enderEmit, IE, crt);

        //carrega_parametros();
        setTpnf(tpnf);
        System.out.println("Valor de tpnf: "+getTpnf());
        //JOptionPane.showMessageDialog(null,"Valor de tpnf: "+getTpnf());
        return true;
    }
    public void trata_ide(){
        if (display) JOptionPane.showMessageDialog(null, "Entrou trata_ide() . . .");
        //nfemenu.ta_gx_gera.append("\nEntrou no metodo trata_trata_ide()");  //suprimir
        try {
if (display) {
        JOptionPane.showMessageDialog(null, "01: "+getcUF());
        JOptionPane.showMessageDialog(null, "02: "+getcNF());
        JOptionPane.showMessageDialog(null, "03: "+getNatOp());
        JOptionPane.showMessageDialog(null, "04: "+getIndpag());
        JOptionPane.showMessageDialog(null, "05: "+getMod());
        JOptionPane.showMessageDialog(null, "06: "+getSerie());
        JOptionPane.showMessageDialog(null, "07: "+getnNF());
        JOptionPane.showMessageDialog(null, "08: "+anomesdia.substring(0,4)+"-"+anomesdia.substring(4,6)+"-"+anomesdia.substring(6,8));
        JOptionPane.showMessageDialog(null, "09: "+anomesdia.substring(0,4)+"-"+anomesdia.substring(4,6)+"-"+anomesdia.substring(6,8));  //  +dataSaida.substring(6,10)+"-"+dataSaida.substring(3,5)+"-"+dataSaida.substring(0,2));
        JOptionPane.showMessageDialog(null, "10: "+getTpnf());
        JOptionPane.showMessageDialog(null, "11: "+getCmunfg());
        JOptionPane.showMessageDialog(null, "12: "+getTpimp());
        JOptionPane.showMessageDialog(null, "13: "+getTpemis());
        JOptionPane.showMessageDialog(null, "14: "+getcDV());
        JOptionPane.showMessageDialog(null, "15: "+tpAmb);
        JOptionPane.showMessageDialog(null, "16: "+getFinnfe());
        JOptionPane.showMessageDialog(null, "17: "+getProcemis());
        JOptionPane.showMessageDialog(null, "18: "+getVerproc());
}
        Date data = new Date();
         final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
         strDhEmi = sdf.format(data) + fuso_horario;
//JOptionPane.showMessageDialog(null, "GeraXML_NFCe - strDhEmi: "+strDhEmi);         
         ide    = new Ide(getcUF(), //cUF
                  getcNF(), // cNF
                  getNatOp(), //natOp
                  getIndpag(), //indPag,
                  getMod(), //mod,
                  ""+Integer.parseInt(getSerie().trim()), //serie,
                  ""+Integer.parseInt(getnNF()), //nNF,
                  strDhEmi, //  dhEmi //anomesdia.substring(0,4)+"-"+anomesdia.substring(4,6)+"-"+anomesdia.substring(6,8)+"T08:00:00-03:00", //dhEmi,
                  null, //dhSaiEnt,
                  getTpnf(), //tpNF,
                  getIdDest(),  //idDest
                  getCmunfg(), //cMunFG,
                  getTpimp(), //tpImp,
                  getTpemis(), //tpEmis,
                  getcDV(), //cDV,
                  tpAmb,  //resultset.getString("tpamb"), //tpAmb,
                  getFinnfe(), //finNFe,
                  getIndFinal(),  //indFinal
                  "1",  //indPres
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
        String pessoa = "", CNPJ = "", IE = "", xLgr = "", nro = "", xCpl = "",
                xBairro = "", cMun = "", xMun = "", CEP = "", fone = "", email = "";
        if (
                (getCNPJ_dest() == null || getCNPJ_dest().equals(""))
                &&
                (getCPF_dest() == null || getCPF_dest().equals(""))
                ) {
            dest = null;
        } else {
            //xNome_dest = nomeDest;
            setIndIEDest("9");
            dest       = new Dest(getCNPJ_dest(), getCPF_dest(), xNome_dest, null, getIndIEDest(), null, null);
        }
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
        xNome_dest = tiraEspacoFinal(xNome_dest);
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
        natOp = tiraEspacoFinal(natOp);
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
                        +formataStrEsq(getSerie().trim(),3)  //  getSerie()
                        +getnNF()
                        +"1"
                        +getcNF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atribuir conteudo para wchave. \nwchave: "
                +"\ncUF: "+getcUF()
                +"\nanomes: "+anomesdia.substring(2,6)
                +"\ncnpj: "+getCNPJ_emit()
                +"\nMod: "+getMod()
                +"\nSerie: "+formataStrEsq(getSerie().trim(),3)  //  getSerie()
                +"\nnNF: "+getnNF()
                +"\nForma Emissao: "+"1"
                +"\ncNF: "+getcNF()
                     +"\nErro: "+e);
        }
        if (display) {
            JOptionPane.showMessageDialog(null,
                    "chave: "+wchave
                    +"\ntamanho da chave: "+wchave.length()
                    +"\ncUF: "+cUF
                    +"\nanomes: "+anomesdia.substring(2,6)
                    +"\ncnpj: "+getCNPJ_emit()
                    +"\nMod: "+getMod()
                    +"\nSerie: "+formataStrEsq(getSerie().trim(),3)  //  getSerie()
                    +"\nnNF: "+getnNF()
                    +"\nForma Emissao: "+"0"
                    +"\ncNF: "+getcNF()
                    );
        }
        if (display) JOptionPane.showMessageDialog(null,"Vai chamar o metodo calcdv() com chave: "+wchave);
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
        setNatOp("VENDA");
        trata_ide();
        if (display) JOptionPane.showMessageDialog(null,"Voltou do metodo trata_ide() . . .");
    }
    public String calcdv(String wnumero){
        int wdigito = 0;
        try {
            int winic=43-wnumero.length();
    if (display) JOptionPane.showMessageDialog(null,"Metodo calcdv() - Numero: "+wnumero+" - Tamanho: "+wnumero.length()+" - Inicio: "+winic);
            //System.out.println("Numero: "+wnumero+" - Tamanho: "+wnumero.length()+" - Inicio: "+winic);
            int[] tpeso = {4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2};
            int wsoma=0;
            int indx=0;
            for (int i=winic;i<43;i++){
    //if (display && wnumero.length() == 43) JOptionPane.showMessageDialog(null,"indx: "+indx+" - wnumero.sub: "+wnumero.substring(indx,indx+1)+" - tpeso["+i+"]: "+tpeso[i]);
                wsoma=wsoma+(Integer.parseInt(wnumero.substring(indx,indx+1))*tpeso[i]);
                indx++;
            }
            int wresto=wsoma%11;
            wdigito=11-wresto;
            //JOptionPane.showMessageDialog(null,"wresto: "+wresto+" - wdigito: "+wdigito);
            if (wdigito>9) wdigito=0;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro no Metodo calcdv() - Erro: "+e);
        }
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

    public String getTpnf() {
        return tpnf;
    }

    public void setTpnf(String tpnf) {
        this.tpnf = tpnf;
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

    /**
     * @return the procemis
     */
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
    public static String normalizeXML(String xml) {
        if ((xml != null) && (!"".equals(xml))) {
            xml = xml.replaceAll("\\r\\n", "");
            xml = xml.replaceAll(" standalone=\"no\"", "");
            while (xml.indexOf(" <") != -1){
                    xml = xml.replaceAll(" <", "<");
            }
        }
            return xml;
    }
    private String tiraEspacoFinal(String texto){
        System.out.println("texto original: ["+texto+"]");
        int tamtxt = texto.length();
        try {
            for (int i=tamtxt;(texto.substring(texto.length()-1, texto.length()).indexOf(" ") != -1);i--){
                texto = texto.substring(0,texto.length()-1);
                System.out.println("texto modificada: ["+texto+"]");
            }
        } catch (Exception e) {
            
        }
        return texto;
    }
    private String tiraEspacoInicial(String texto){
        //JOptionPane.showMessageDialog(null,"texto original: ["+texto+"]");
        int tamtxt = texto.length(), inic = 0;
        System.out.println("texto original: ["+texto+"] - Tamanho: "+tamtxt);
        try {
            for (int i=0;(texto.substring(i, i+1).indexOf(" ") != -1);i++){
                inic = i;
            }
            texto = texto.substring(inic,texto.length()-inic);
        } catch(Exception e){

        }
        return texto;
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
//if (display) JOptionPane.showMessageDialog(null, "Campo Formatado: "+nome+" - Antes: "+campo+" - Depois: "+retorno+" - Situacao: "+situ);
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
        String retorno = campo;
        if (campo.length() < tamanho) {
            for (int i=campo.length(); i<tamanho; i++) {
                retorno = "0"+retorno;
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

    public String getCPF_CNPJ_dest() {
        return CPF_CNPJ_dest;
    }

    public void setCPF_CNPJ_dest(String CPF_CNPJ_dest) {
        this.CPF_CNPJ_dest = CPF_CNPJ_dest;
    }

    public int getQtdVol() {
        return qtdVol;
    }

    public void setQtdVol(int qtdVol) {
        this.qtdVol = qtdVol;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getHashQRCode() {
        return hashQRCode;
    }

    public void setHashQRCode(String hashQRCode) {
        this.hashQRCode = hashQRCode;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }
}
