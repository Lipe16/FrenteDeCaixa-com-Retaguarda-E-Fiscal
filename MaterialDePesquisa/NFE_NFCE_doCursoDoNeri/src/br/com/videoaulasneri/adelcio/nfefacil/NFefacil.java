
/*

Descrição: Classe Principal do Sistema. Contém os Painéis de Acessos a Todas as Opcoes do Sistema

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.nfefacil;

import br.com.videoaulasneri.adelcio.utilitarios.NFeStatusServicoFactoryDinamicoA3;
import br.com.videoaulasneri.adelcio.relatorios.Relfatper;
import java.io.File;
import java.text.ParseException;
import br.com.videoaulasneri.adelcio.fatura.XML_Compra;
import br.com.videoaulasneri.adelcio.fatura.XML_Devolucao;
import br.com.videoaulasneri.adelcio.fatura.Baixa;
import java.awt.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;
import br.com.videoaulasneri.adelcio.admin.Login;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.CancelarNFe;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.InutilizarNFe;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.MontarEnviarNFe;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.StatusServico;
import java.security.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.apache.commons.mail.EmailException;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.CartaCorrecao;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.ConsultarNFe;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.utilitarios.Conexao_fatura;
import br.com.videoaulasneri.adelcio.admin.ImportaCidades;
import br.com.videoaulasneri.adelcio.admin.ImportaIbpt;
import br.com.videoaulasneri.adelcio.cron.CronImportNFCe;
import br.com.videoaulasneri.adelcio.cron.CronImportNFe;
import br.com.videoaulasneri.adelcio.fatura.ImportaCsvVenda;
import br.com.videoaulasneri.adelcio.utilitarios.XMLReader;
import br.com.videoaulasneri.adelcio.fatura.bean.Email;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.NFeConsultaStatusServicoAll;
import br.com.videoaulasneri.adelcio.nfeweb.util.CertificadoUtil;
import br.com.videoaulasneri.adelcio.nfeweb.util.NFeBuildAllCacerts;
import br.com.videoaulasneri.adelcio.relatorios.RelArecper;
import br.com.videoaulasneri.adelcio.relatorios.RelRecper;
import br.com.videoaulasneri.adelcio.utilitarios.ConfiguraEmail;
import br.com.videoaulasneri.adelcio.utilitarios.EnviaEmail;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.util.List;
import java.util.Vector;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import br.com.videoaulasneri.adelcio.utilitarios.DadosCertificadoA3;
import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfce;
import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfce_new;
import br.com.videoaulasneri.adelcio.utilitarios.GerarCacertsNfe;
import br.com.videoaulasneri.adelcio.utilitarios.SendAttachmentInEmail;
import br.com.videoaulasneri.adelcio.utilitarios.SendEmail;
import br.com.videoaulasneri.adelcio.utilitarios.XMLReaderCarta;
import br.com.videoaulasneri.adelcio.utilitarios.XMLReaderFatura;
import com.lowagie.text.Paragraph;
import java.io.OutputStream;
import java.net.URL;

public class NFefacil extends JFrame implements MouseListener, ActionListener, ItemListener {

   public configNfeFacil obj_conf = new configNfeFacil();
    public config_fatura obj_conf_fat = new config_fatura();
    String versaoNFe = "Versão NFe: 3.10";
    String versao = "Versão: 1.2.7 (03/03/2018)";
    private static String tipoAmbiente = "";
    static String tipoExecucao = "externa";
    static int stat_empresa = 0;
    static String stat_panel = "0";
    static String stat_caixa = "";
    static String import_automat = "";
    static String stat_usuario = "";
    private static String stc_data;
    private static String stc_camLerAss;
    private static String stc_camAssinat;
    private static String stc_camImpdanfe;
    private static String stc_fileCert;
    private static String stc_senhaCert;
    private static String stc_fileKey;
    private static String stc_drive;
    private static String stc_anomesdia;
    private static String stc_tipoEmis;
    private static boolean stc_exibirDsp;
    public static JPanel panel_envia;
    public static TextArea ta_pc_consulta;
    public static TextArea ta_en_envia;
    public static KeyStore ksFixo = null;
    public static String txt_token = null;
    public static Provider pr_token = null;
    public static CertificadoUtil certUtil;
    private static String caminho_conexao          = ""; 
    private static String caminho_nfeprot          = ""; 
    private static String caminho_assinatura       = ""; 
    private static String caminho_impdanfe         = ""; 
    private static String caminho_grava_assinar    = ""; 
    private static String file_keystore            = ""; 
    private static String file_truststore          = ""; 
    private static String senha_keystore           = ""; 
    private static String senha_truststore         = ""; //  "123456";
    private static String pasta_grava_assinada     = "";
    private static String pasta_grava_retlote      = "";
    private static String pasta_grava_retrecep     = "";
    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static boolean status_fatura = false;
    private static String codcid = "0";
    private static JLabel lb_ambiente;
    private static String motivo = "Off Line";

    String dataDemo = "";  //  "20201231";
    int difData = 0;
    JTabbedPane painel_pai;
    JPanel panel_login, panel_consulta, panel_fatura;  //panel_configura, 
    JPanel panel_ambiente;
    // componentes do painel panel_login
    JLabel lb_cor, lb_titulo, lb_email, lb_cidade, lb_versao, lb_versaoNFe, lb_usuario, lb_senha, lb_empresa, 
            lb_saudacao, lb_status, lb_icon_nfce, lb_icon_nfe, lb_ambFat, lb_statFat;
    JTextField tf_usuario;
    JPasswordField tf_senha;
    JButton bt_login, bt_sair, bt_login_status, bt_envia_email, bt_conf_email;  //  , bt_trocacor
    JComboBox cb_empresa;
//    JRadioButton rb_configura; //rb_consulta, rb_transmite, rb_fatura;  //, rb_impDanfe;
    ButtonGroup bg_login;
    JRadioButton rb_emis_normal, rb_emis_dpec, rb_emis_offLine;  //, rb_emis_evento;
    ButtonGroup bg_ambiente, bg_tpemis;
    ImageIcon ic_nfce, ic_nfe;

    //componentes do painel panel_consulta
    JFileChooser fc_escolha;
    JLabel lb_pc_escolhenfe;
    JLabel lb_pc_titulo, lb_pc_resultado, lb_pc_ambiente;
    JLabel lb_pc_data_ini, lb_pc_data_fim;
    JFormattedTextField tf_pc_data_ini, tf_pc_data_fim;
    MaskFormatter dataFormatter;

    JButton bt_pc_consulta, bt_pc_sair;
    JRadioButton rb_pc_consulta_periodo, rb_pc_consulta_sitnfe, rb_pc_consulta_lote, rb_pc_consulta_stat;
    JRadioButton rb_pc_autoriz, rb_pc_rejeit, rb_pc_todas;
    ButtonGroup bg_pc_consulta, bg_pc_periodo;

    // componentes do painel panel_envia
    JLabel lb_en_titulo, lb_en_modelo, lb_en_serie, lb_en_nfinut_ini, lb_en_nfinut_fim;
    JTextField tf_en_modelo, tf_en_serie, tf_en_nfinut_ini, tf_en_nfinut_fim;
    JButton bt_en_envia, bt_en_sair, bt_en_impdanfe, bt_en_impcarta;
    JRadioButton rb_en_lote, rb_en_cancela, rb_en_inutiliza, rb_en_corrige;
    ButtonGroup bg_en_transmite;

//  itens de menu do painel faturamento
    JMenuItem fatCliente    = new JMenuItem("Clientes");
    JMenuItem fatUnidade    = new JMenuItem("Unidade Medida");
    JMenuItem fatTipoDoc    = new JMenuItem("Tipo de Docto");
    JMenuItem fatProduto    = new JMenuItem("Produtos");
    JMenuItem fatCfop       = new JMenuItem("Cod.Fiscal Operaçao");
    JMenuItem fatFormaPgto  = new JMenuItem("Formas de Pagto");
    JMenuItem fatAliqUf     = new JMenuItem("Aliquotas ICMS por UF");
    JMenuItem fatCidade     = new JMenuItem("Cidades");
    JMenuItem fatBanco      = new JMenuItem("Bancos");

    JMenuItem fatDigiNf     = new JMenuItem("Digitação de Nota Fiscal");
    JMenuItem fatCancNf     = new JMenuItem("Cancelamento de Nota Fiscal");
    JMenuItem fatDevoNf     = new JMenuItem("Devolução Total da NFe");
    JMenuItem fatLerXml     = new JMenuItem("Exibir Dados do XML da NFe");
    JMenuItem fatBaixa      = new JMenuItem("Baixa de Ctas a Receber");
    JMenuItem fatXmlCompra  = new JMenuItem("Importa NFe de Compra");
//    JMenuItem fatCsvVenda   = new JMenuItem("Importa CSV de Venda");
//    JMenuItem fatImpVenda   = new JMenuItem("Importar/Emitir NFCe");
    JMenuItem fatMonitoraNFeNFCe   = new JMenuItem("Inicia Monitoramento");
    JMenuItem fatStopMonitorNFeNFCe   = new JMenuItem("Finaliza Monitoramento");

    JMenuItem fatRelFatura  = new JMenuItem("Movimento no Período");
    JMenuItem fatRelRecebido= new JMenuItem("Recebimento no Período");
    JMenuItem fatRelAreceber= new JMenuItem("A Receber no Período");
//        JMenuItem exitAction = new JMenuItem("Exit");
    JMenuItem admMenu1      = new JMenuItem("Empresa");
    JMenuItem admMenu2      = new JMenuItem("Parametros da Ass.Digital");
    JMenuItem admMenu3      = new JMenuItem("Usuários");
    JMenuItem admMenu4      = new JMenuItem("Impressoras(PDV)");
    JMenuItem admMenu5      = new JMenuItem("Posição Caixa");
    JMenuItem admMenu6      = new JMenuItem("Numero da Ultima NFe/NFCe");
    JMenuItem admMenu7      = new JMenuItem("Importa Cidades");
    JMenuItem admMenu8      = new JMenuItem("Configurar email");
    JMenuItem admMenu9      = new JMenuItem("Enviar email");
    JMenuItem admMenu10     = new JMenuItem("Importa Tabela IBPT");
    JMenuItem admMenu11     = new JMenuItem("Exibe Dados Certif.A3(Cartão)");
    JMenuItem admMenu12     = new JMenuItem("Verif. StatServico c/Cartão");
    JMenuItem admMenu13     = new JMenuItem("Gera Arquivo NFeCacerts");
//    JMenuItem admMenu11     = new JMenuItem("Importar/Emitir NFCe");

    boolean reimprime = false;
    String senha_config  = "";

    ResultSet rs_nfe, rs_nfe_sys, rs_emp, rs_cid;
    Statement st_nfe, st_nfe_sys, st_fat, st_emp, st_cid;
    Date hoje;
    int empresa                     = 0, qt_panel = 0, qtdNfe = 0, idx = 0;
    String arqs_assinar[]           = new String[500];
    String url, usuario,senha;
    String url_sys, usuario_sys,senha_sys;
    String wsenha, wnome_usuario;
    String chNfe;
    String tpEmis                   = "1"; //  1 = Normal / 3 = SCAN / 4 = DPEC
    String anomesdia                = "";
    String datahoje                 = "";
    String emp4dig                  = "";
    public static boolean status_servico = false;
    String senha_token              = "";
    String nome_empresa             = "";
    // inicio variaveis do arquivo de configuracao
    String driver                   = ""; //  "sun.jdbc.odbc.JdbcOdbcDriver";
    String banco                    = ""; //  "jdbc:odbc:portonfe";
    String driver_sys               = ""; //  "sun.jdbc.odbc.JdbcOdbcDriver";
    String caminho_consulta         = ""; 
    String caminho_ler_assinar      = ""; 
    String caminho_txt              = ""; 
    String caminho_grava_dpec       = ""; 
    String caminho_lote_dpec        = ""; 
    String caminho_grava_retdpec    = ""; 
    String caminho_grava_assinada   = ""; 
    String caminho_grava_retconsu   = ""; 
    String caminho_grava_retcanc    = ""; 
    String caminho_grava_retcorr    = ""; 
    String caminho_grava_retinut    = ""; 
    String caminho_grava_retlote    = ""; 
    String caminho_grava_retrecep   = ""; 
    String caminho_grava_inut       = ""; 
    String caminho_grava_canc       = ""; 
    String caminho_grava_corr       = ""; 
    String caminho_grava_impDanfe   = ""; 
    String caminho_ibpt            = "ibpt"; 
    public static String cUF        = ""; 
    String ano                      = ""; 
    String cnpj                     = ""; 

    String ultimo_lote              = "";
    String drive                    = "";
    String resultado                = "";
    String tipo_envio               = "";
    String tipo_consulta            = "";
    String tipo_periodo             = "";
    String pasta_grava_consulta     = "";
    String pasta_grava_retinut      = "";
    String pasta_grava_retcanc      = "";
    String pasta_grava_retcorr      = "";
    boolean ja_consultou            = false;
    String fsep = System.getProperty("file.separator");
    String arqemail     = "";
    String hostName     = "";
    String nomeRemet    = "";
    String emailRemet   = "";
    String senhaEmail   = "";
    String portaEmail   = "";
    boolean ssl         = false;

    private final String TPEMIS_OFFLINE  = "0";
    private final String TPEMIS_NORMAL   = "1";
    private final String TPEMIS_DPEC     = "4";
    private final String TPEMIS_EVENTO   = "5";
    private String pasta_raiz = "";
    private String correcao = "";

    Connection consis;
    static Connection confat;
    private boolean exibir_display = true;
    Conexao_fatura con_fatura;
    String usuario_fatura = "postgres";
    String senha_fatura = "nerizon";
    String local_fatura = "localhost"; //"192.168.0.0";
    String bd_fatura = "fatura";
    String driver_fatura = "org.postgresql.Driver";
    String drive_fatura = "C";
    String jdbc_fatura = "jdbc:postgresql://";
    private String endereco_empresa = "";
    private String wmodeloNFe = "";
    String status = "", caixa = "";
    public static String fuso_horario = "-02:00";
    public String informarCnpjCartao = "N";
    public static String cupomNaTela = "N";
    public static String contatoCupom = "Porto Informatica Ltda - portoinfo@sercomtel.com.br";
    public static String uriCupom = "http://www.sped.fazenda.pr.gov.br/modules/conteudo/conteudo.php?conteudo=100";
    private static int interval = 15;  //  intervalo (em segundos) da captura autom.do arq.XML
    String wnivel_usuario = "";
    String codempresa = "0";
    public static String arqXML = "";
    private static boolean suspender = false, autorizando = false;
    private static CronImportNFe ciNFe;
    private static CronImportNFCe ciNFCe;
    private boolean monitorComModelo = false;

    public NFefacil(){
        setCiNFCe(new CronImportNFCe());
        inicializa();
   }
    public NFefacil(String tipoAmb, String tipoExec, int itemEmpresa, String usuario, String caixa, String senha){
        tipoAmbiente = tipoAmb;
        tipoExecucao = tipoExec;
        inicializa();
        tf_usuario.setText(usuario);
        tf_senha.setText(senha);
        cb_empresa.setSelectedIndex(itemEmpresa);
        lb_empresa.setVisible(false);
        lb_usuario.setVisible(false);
        lb_senha  .setVisible(false);
        cb_empresa.setVisible(false);
        tf_usuario.setVisible(false);
        tf_senha  .setVisible(false);
        bt_login  .setVisible(false);
        if (verificaSenha()){
            exibe_opcoes_menu();
            configurarPastas();
        }
    }
    public void inicializa(){
         setTitle("Sistema de Emissão de NFe/NFCe");
//         int largTela = 1000;
//         int altTela = 700;
//         setSize(largTela,altTela);
//         //setLocation(0,0);
//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension tamTela = kit.getScreenSize();
//        //Pegando a largura total da tela
//        int larg = tamTela.width;
//        //Pegando a altura total da tela
//        int alt = tamTela.height;
//        /*Se você quiser que o seu JFrame ocupe 70% da tela por exemplo 
//        multiplique a largura e altura totais por 0,7*/
//        int minhaLargura= (int) (larg*0.7);
//        int minhaAltura = (int) (alt*0.7);
//        int deslocX = (int) (larg/2 - largTela/2); 
//        int deslocY = (int) (alt/2 - altTela/2); 
//        //Mandando o JFrame utilizar suas dimensões
//        setLocation(deslocX,deslocY);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamTela = kit.getScreenSize();
        int larg = tamTela.width;
        int alt = tamTela.height;
        
        int minhaLargura= (int) (larg);
        int minhaAltura = (int) (alt);
        setSize(minhaLargura,minhaAltura);
        setLocation(0,0);

        setResizable(true);
         setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // EXIT_ON_CLOSE);
         getContentPane().setBackground(Color.gray);
         ImageIcon icone = new ImageIcon("/imagens/nfce.png"); //".//Imagens//Logo_Pil_1.jpg"
         this.setIconImage(icone.getImage());

        //  inicializacao de paineis
         painel_pai         = new JTabbedPane();
         panel_login        = new JPanel();
         panel_consulta     = new JPanel();
         panel_envia        = new JPanel();
//         panel_configura    = new JPanel();
         panel_fatura       = new JPanel();
         panel_ambiente     = new JPanel();

         //inicializacao de componentes no painel panel_login
         lb_status          = new JLabel("Pressione \"Efetuar Login\" e aguarde a verificação do Status do Serviço");
         lb_cor             = new JLabel("");
         lb_titulo          = new JLabel("Sistema de Emissão de NFe e NFCe");
         lb_email           = new JLabel("Prof.Neri - email: videoaulas@gmail.com - Fone: (54)3329-5400 - Carazinho-RS");  
         lb_cidade          = new JLabel("Adelcio Porto - email: portoinfo@sercomtel.com.br - Fone: (43)99994-6037 - Londrina-PR");
         lb_versaoNFe       = new JLabel(versaoNFe);
         lb_versao          = new JLabel(versao);
         lb_empresa         = new JLabel("   Empresa");
         setLb_ambiente(new JLabel("** HOMOLOGAÇÃO **"));
         lb_ambFat          = new JLabel("** HOMOLOGAÇÃO **");
         lb_statFat         = new JLabel("* Off Line *");
         lb_usuario         = new JLabel("     Usuário");
         lb_senha           = new JLabel("       Senha");
         lb_saudacao        = new JLabel("");
         ic_nfce             = new javax.swing.ImageIcon(getClass().getResource("/imagens/nfce.png"));
         ic_nfe             = new javax.swing.ImageIcon(getClass().getResource("/imagens/logo_nacional_do_projeto.gif"));  //new ImageIcon("//imagens//logo_nacional_do_projeto.gif");
         lb_icon_nfce        = new JLabel();  //(ic_nfce);
         lb_icon_nfe        = new JLabel();  //(ic_nfe);
         cb_empresa         = new JComboBox();
         tf_usuario         = new JTextField();
         tf_senha           = new JPasswordField();
         bt_login           = new JButton("Efetuar Login");
         bt_sair            = new JButton("Sair");
         bt_login_status    = new JButton("Status Serviço");
         bt_conf_email     = new JButton("Configura Email");
         bt_envia_email     = new JButton("Envia Email xml");
         bg_login           = new ButtonGroup();
         bg_ambiente        = new ButtonGroup();
         bg_tpemis          = new ButtonGroup();
         rb_emis_normal     = new JRadioButton("1 - Normal");
         rb_emis_dpec       = new JRadioButton("4 - DPEC");
         rb_emis_offLine    = new JRadioButton("0 - Off Line");

         lb_titulo          .setForeground(Color.yellow);
         lb_email           .setForeground(Color.yellow);
         lb_cidade          .setForeground(Color.yellow);
         lb_versaoNFe       .setForeground(Color.yellow);
         lb_versao          .setForeground(Color.black);
         lb_versaoNFe       .setFont(new Font("Arial",Font.BOLD,14));
        
         //inicializacao de componentes no painel panel_envia
         lb_en_titulo       = new JLabel("Transmite Arquivos para a Receita Estadual");
         lb_en_modelo       = new JLabel("Modelo: ");
         lb_en_serie        = new JLabel("Serie: ");
         lb_en_nfinut_ini   = new JLabel("Nf Inicial: ");
         lb_en_nfinut_fim   = new JLabel("Nf Final..: ");
         tf_en_modelo       = new JTextField();
         tf_en_serie        = new JTextField();
         tf_en_nfinut_ini   = new JTextField();
         tf_en_nfinut_fim   = new JTextField();
         bt_en_impcarta     = new JButton("Imprime Carta");
         bt_en_envia        = new JButton("Processar");
         bt_en_sair         = new JButton("Sair");
         bg_en_transmite    = new ButtonGroup();
         bt_en_impdanfe     = new JButton("Danfe");
         rb_en_lote         = new JRadioButton("Autorizar");
         rb_en_cancela      = new JRadioButton("Cancelamento");
         rb_en_corrige      = new JRadioButton("Carta de Correcao");
         rb_en_inutiliza    = new JRadioButton("Inutilização");
         ta_en_envia        = new TextArea();

         //inicializacao de componentes no painel panel_consulta
         lb_pc_escolhenfe       = new JLabel("Escolha a NFe para Consulta");
         fc_escolha             = new JFileChooser(caminho_consulta);
         ta_pc_consulta         = new TextArea();
         lb_pc_titulo           = new JLabel("Consultar Situações Diversas");
         lb_pc_resultado        = new JLabel("Resultado da Consulta");
         lb_pc_ambiente         = new JLabel("** HOMOLOGAÇÃO **");
         lb_pc_data_ini         = new JLabel("Data Inicial");
         lb_pc_data_fim         = new JLabel("Data Final");

         try {
            dataFormatter = new MaskFormatter("##/##/####");
        } catch (ParseException ex) {
            Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
        }
         dataFormatter          .setValidCharacters("0123456789");
         tf_pc_data_ini         = new JFormattedTextField(dataFormatter);
         tf_pc_data_fim         = new JFormattedTextField(dataFormatter);

         tf_pc_data_ini         .setColumns(10);
         tf_pc_data_fim         .setColumns(10);

         bt_pc_consulta         = new JButton("Consultar");
         bt_pc_sair             = new JButton("Sair");
         ta_pc_consulta         .setEditable(false);
         bg_pc_consulta         = new ButtonGroup();
         rb_pc_consulta_periodo = new JRadioButton("Situação das NFes no Período");
         rb_pc_consulta_sitnfe  = new JRadioButton("Situacao NFe");
         rb_pc_consulta_lote    = new JRadioButton("Lote");
         rb_pc_consulta_stat    = new JRadioButton("Status do Serviço");
         bg_pc_periodo          = new ButtonGroup();
         rb_pc_autoriz          = new JRadioButton("Autorizadas");
         rb_pc_rejeit           = new JRadioButton("Não Autorizadas");
         rb_pc_todas            = new JRadioButton("Todas");

         //define layout para os paineis
         getContentPane()   .setLayout(null);
         panel_login        .setLayout(null);
         panel_consulta     .setLayout(null);
         panel_envia        .setLayout(null);
//         panel_configura    .setLayout(null);
         panel_fatura       .setLayout(null);
         panel_ambiente     .setLayout(null);
         panel_ambiente     .setBounds(20,20,150,80);

         //define tipo e tamanho de fonte para os labels do painel panel_login
         lb_status          .setFont(new Font("Arial",Font.BOLD,16));
         lb_cor             .setFont(new Font("Arial",Font.BOLD,10));
         lb_titulo          .setFont(new Font("Arial",Font.BOLD,22));
         lb_empresa         .setFont(new Font("Arial",Font.BOLD,16));
         getLb_ambiente()        .setFont(new Font("Arial",Font.BOLD,24));
         lb_ambFat          .setFont(new Font("Arial",Font.BOLD,18));
         lb_statFat         .setFont(new Font("Arial",Font.BOLD,18));
         lb_usuario         .setFont(new Font("Arial",Font.BOLD,16));
         lb_senha           .setFont(new Font("Arial",Font.BOLD,16));
         lb_saudacao        .setFont(new Font("Arial",Font.BOLD,20));
         lb_status          .setForeground(Color.blue);
         lb_status          .setHorizontalAlignment(JLabel.CENTER);

         //define tipo e tamanho de fonte para os labels do painel panel_consulta
         lb_pc_escolhenfe    .setFont(new Font("Arial",Font.BOLD,14));
         lb_pc_titulo        .setFont(new Font("Arial",Font.BOLD,18));
         lb_pc_resultado     .setFont(new Font("Arial",Font.BOLD,14));
         lb_pc_ambiente      .setFont(new Font("Arial",Font.BOLD,24));
         tf_pc_data_ini      .setFont(new Font("Arial",Font.BOLD,14));
         tf_pc_data_fim      .setFont(new Font("Arial",Font.BOLD,14));
         //define tipo e tamanho de fonte para os labels do painel panel_envia
         lb_en_titulo        .setFont(new Font("Arial",Font.BOLD,16));
         lb_en_modelo        .setFont(new Font("Arial",Font.BOLD,14));
         lb_en_serie         .setFont(new Font("Arial",Font.BOLD,14));
         lb_en_nfinut_ini    .setFont(new Font("Arial",Font.BOLD,14));
         lb_en_nfinut_fim    .setFont(new Font("Arial",Font.BOLD,14));
         tf_en_modelo        .setFont(new Font("Arial",Font.BOLD,14));
         tf_en_serie         .setFont(new Font("Arial",Font.BOLD,14));
         tf_en_nfinut_ini    .setFont(new Font("Arial",Font.BOLD,14));
         tf_en_nfinut_fim    .setFont(new Font("Arial",Font.BOLD,14));
         rb_emis_normal      .setFont(new Font("Arial",Font.BOLD,14));
         rb_emis_offLine     .setFont(new Font("Arial",Font.BOLD,14));
         rb_emis_dpec        .setFont(new Font("Arial",Font.BOLD,14));


//         painel_pai         .setBounds(10,60,970,590);
         painel_pai         .setBounds(0, 50, larg, alt-50);

         //posiionamento dos objetos no getContentPane
         lb_titulo          .setBounds(330,  5, 600, 30);
         lb_email           .setBounds(300, 25, 500, 20);
         lb_cidade          .setBounds(300, 38, 500, 20);

         //posiionamento dos objetos no painel panel_login
         lb_status          .setBounds(250, 290, 550, 30);
         lb_empresa         .setBounds(370, 60, 100, 20);
         cb_empresa         .setBounds(480, 60, 200, 20);
         getLb_ambiente()        .setBounds(400, 15, 420, 30);
         lb_usuario         .setBounds(370, 90, 100, 20);
         tf_usuario         .setBounds(480, 90, 100, 20);
         lb_senha           .setBounds(370, 120, 100, 20);
         tf_senha           .setBounds(480, 120, 100, 20);
         bt_login           .setBounds(450, 190, 150, 30);
         rb_emis_normal     .setBounds(350, 150, 130, 30);
         rb_emis_dpec       .setBounds(480, 150, 130, 30);
         rb_emis_offLine    .setBounds(610, 150, 130, 30);
         lb_saudacao        .setBounds(350, 330, 500, 30);
         lb_icon_nfe        .setBounds(50,  80, 125, 120);
         lb_icon_nfce       .setBounds(750, 80, 125, 120);
         bt_sair            .setBounds(450, 520, 100, 30);
         bt_login_status    .setBounds(30, 420, 150, 30);
         lb_cor             .setBounds(25, 548, 180, 15);
         lb_versao          .setBounds(larg-200, 50, 200, 20);
         lb_versaoNFe       .setBounds(415, 50, 150, 20);

         //posiionamento dos objetos no painel panel_consulta
         lb_pc_escolhenfe       .setBounds(10, 10, 250, 25);
         lb_pc_titulo           .setBounds(10, 02, 650, 25);
         rb_pc_consulta_periodo .setBounds(30, 25, 200, 20);
         rb_pc_consulta_sitnfe  .setBounds(250, 25, 150, 20);
         rb_pc_consulta_lote    .setBounds(420, 25, 150, 20);
         rb_pc_consulta_stat    .setBounds(590, 25, 150, 20);
         lb_pc_data_ini         .setBounds( 10, 50, 60, 20);
         tf_pc_data_ini         .setBounds( 80, 50, 100, 20);
         lb_pc_data_fim         .setBounds(190, 50, 60, 20);
         tf_pc_data_fim         .setBounds(260, 50, 100, 20);
         rb_pc_autoriz          .setBounds(380, 50, 120, 20);
         rb_pc_rejeit           .setBounds(510, 50, 120, 20);
         rb_pc_todas            .setBounds(640, 50, 60, 20);
         lb_pc_resultado        .setBounds(10, 70, 400, 25);
         lb_pc_ambiente         .setBounds(680, 02, 420, 25);
         ta_pc_consulta         .setBounds(010, 95, 840, 315);  //.setBounds(10, 40, 740, 380);
         bt_pc_consulta         .setBounds(400, 520, 100, 30);
         bt_pc_sair             .setBounds(520, 520, 100, 30);

         //adiciona posicionamento no panel_envia
         lb_en_titulo           .setBounds( 10,  2, 800, 25);
         rb_en_lote             .setBounds(150, 50, 150, 20);
         rb_en_cancela          .setBounds(350, 50, 150, 20);
         rb_en_corrige          .setBounds(550, 50, 150, 20);
         rb_en_inutiliza        .setBounds(750, 50, 150, 20);
         lb_en_modelo           .setBounds(100, 80, 70, 20);
         tf_en_modelo           .setBounds(170, 80, 30, 20);
         lb_en_serie            .setBounds(220, 80, 60, 20);
         tf_en_serie            .setBounds(280, 80, 40, 20);
         lb_en_nfinut_ini       .setBounds(350, 80, 80, 20);
         tf_en_nfinut_ini       .setBounds(430, 80, 50, 20);
         lb_en_nfinut_fim       .setBounds(530, 80, 80, 20);
         tf_en_nfinut_fim       .setBounds(610, 80, 50, 20);
         ta_en_envia            .setBounds(010, 110, 840, 300);
         bt_en_impcarta         .setBounds(250, 520, 170, 30);
         bt_en_envia            .setBounds(550, 520, 100, 30);
         bt_en_sair             .setBounds(720, 520, 100, 30);
         bt_en_impdanfe         .setBounds(50, 520, 150, 30);


         //adiciona o objeto do painel panel login ao actionLinstener
         bt_login           .addActionListener(this);
         bt_login_status    .addActionListener(this);
         bt_sair            .addActionListener(this);
         cb_empresa         .addItemListener(this);
         rb_emis_normal     .addActionListener(this);
         rb_emis_dpec       .addActionListener(this);
         rb_emis_offLine    .addActionListener(this);

         //adiciona o objeto do painel panel envia ao actionLinstener
         rb_en_lote         .addActionListener(this);
         rb_en_cancela      .addActionListener(this);
         rb_en_corrige      .addActionListener(this);
         rb_en_inutiliza    .addActionListener(this);
         bt_en_impcarta     .addActionListener(this);
         bt_en_envia        .addActionListener(this);
         bt_en_sair         .addActionListener(this);
         bt_en_impdanfe     .addActionListener(this);


         //adiciona o objeto do painel panel consulta ao actionLinstener
         rb_pc_consulta_periodo .addActionListener(this);
         rb_pc_consulta_sitnfe  .addActionListener(this);
         rb_pc_consulta_lote    .addActionListener(this);
         rb_pc_consulta_stat    .addActionListener(this);
         rb_pc_autoriz          .addActionListener(this);
         rb_pc_rejeit           .addActionListener(this);
         rb_pc_todas            .addActionListener(this);
         bt_pc_consulta         .addActionListener(this);
         bt_pc_sair             .addActionListener(this);

         //adiciona o objeto do painel panel fatura ao actionLinstener
         //bt_fat_param           .addActionListener(this);
         fatAliqUf.addActionListener(this);
         fatBaixa.addActionListener(this);
         fatBanco.addActionListener(this);
         fatCancNf.addActionListener(this);
         fatCfop.addActionListener(this);
         fatCidade.addActionListener(this);
         fatCliente.addActionListener(this);
//         fatImpVenda.addActionListener(this);
         fatMonitoraNFeNFCe.addActionListener(this);
         fatStopMonitorNFeNFCe.addActionListener(this);
         fatDevoNf.addActionListener(this);
         fatDigiNf.addActionListener(this);
         fatFormaPgto.addActionListener(this);
         fatLerXml.addActionListener(this);
         fatProduto.addActionListener(this);
         fatRelAreceber.addActionListener(this);
         fatRelFatura.addActionListener(this);
         fatRelRecebido.addActionListener(this);
         fatTipoDoc.addActionListener(this);
         fatUnidade.addActionListener(this);
         fatXmlCompra.addActionListener(this);

         admMenu1.addActionListener(this);
         admMenu2.addActionListener(this);
         admMenu3.addActionListener(this);
         admMenu4.addActionListener(this);
         admMenu5.addActionListener(this);
         admMenu6.addActionListener(this);
         admMenu7.addActionListener(this);
         admMenu8.addActionListener(this);
         admMenu9.addActionListener(this);
         admMenu10.addActionListener(this);
         admMenu11.addActionListener(this);
         admMenu12.addActionListener(this);
         admMenu13.addActionListener(this);
         panel_consulta         .addMouseListener(this);
         painel_pai             .addMouseListener(this);
         panel_fatura           .addMouseListener(this);
         tf_usuario             .addMouseListener(this);
         tf_senha               .addMouseListener(this);
         painel_pai             .setBackground(new Color(0,255,255));
         panel_ambiente         .setBackground(new Color(133, 200, 200));
         lb_saudacao            .setForeground(Color.blue);
         getLb_ambiente()            .setForeground(Color.red);
         lb_ambFat              .setForeground(Color.red);
         lb_statFat             .setForeground(Color.red);
         lb_pc_ambiente         .setForeground(Color.red);

         if (stat_panel.equals("1")) {
            rb_emis_normal         .setSelected(true);
            tpEmis = TPEMIS_NORMAL;
            System.out.println("Tipo de Emissao = Normal ");
         } else  if (stat_panel.equals("0")) {
            rb_emis_offLine        .setSelected(true);
            tpEmis = TPEMIS_OFFLINE;
            System.out.println("Tipo de Emissao = Off Line ");
         }
         //adiciona os objetos no painel pai
         painel_pai             .addTab("Tela Principal",panel_login);
         qt_panel               = 1;

         getContentPane()   .add(lb_versaoNFe);
         getContentPane()   .add(lb_versao);
         getContentPane()   .add(lb_titulo);
         getContentPane()   .add(lb_email);
         getContentPane()   .add(lb_cidade);
         getContentPane()   .add(painel_pai);

         //adiciona os objetos no painel login
         lb_icon_nfce.setIcon(ic_nfce);
         lb_icon_nfe.setIcon(ic_nfe);
         panel_login        .add(rb_emis_normal);
         //panel_login        .add(rb_emis_dpec);
         panel_login        .add(rb_emis_offLine);
         //panel_login        .add(rb_emis_evento);
         panel_login        .add(panel_ambiente);
         panel_login        .add(lb_cor);
         panel_login        .add(lb_status);
         panel_login        .add(lb_icon_nfce);
         panel_login        .add(lb_icon_nfe);
         panel_login        .add(lb_empresa);
         panel_login        .add(cb_empresa);
         panel_login        .add(tf_usuario);
         panel_login        .add(getLb_ambiente());
         panel_login        .add(lb_usuario);
         panel_login        .add(lb_senha);
         panel_login        .add(tf_senha);
         panel_login        .add(bt_login);
         panel_login        .add(bt_sair);
         panel_login        .add(lb_saudacao);
         bg_tpemis          .add(rb_emis_normal);
         bg_tpemis          .add(rb_emis_dpec);
         bg_tpemis          .add(rb_emis_offLine);
         bg_pc_consulta     .add(rb_pc_consulta_periodo);
         bg_pc_consulta     .add(rb_pc_consulta_sitnfe);
         bg_pc_consulta     .add(rb_pc_consulta_lote);
         bg_pc_consulta     .add(rb_pc_consulta_stat);
         bg_pc_periodo      .add(rb_pc_autoriz);
         bg_pc_periodo      .add(rb_pc_rejeit);
         bg_pc_periodo      .add(rb_pc_todas);
         panel_consulta     .add(lb_pc_titulo);
         panel_consulta     .add(lb_pc_resultado);
         panel_consulta     .add(lb_pc_ambiente);
         panel_consulta     .add(ta_pc_consulta);
         panel_consulta     .add(rb_pc_consulta_periodo);
         panel_consulta     .add(rb_pc_consulta_sitnfe);
         panel_consulta     .add(rb_pc_consulta_lote);
         panel_consulta     .add(rb_pc_consulta_stat);
         panel_consulta     .add(bt_pc_consulta);
//         panel_consulta     .add(bt_pc_sair);

         //adiciona componentes no panel_envia
         panel_envia        .add(lb_en_titulo);
         bg_en_transmite    .add(rb_en_lote);
         bg_en_transmite    .add(rb_en_cancela);
         bg_en_transmite    .add(rb_en_corrige);
         bg_en_transmite    .add(rb_en_inutiliza);
         panel_envia        .add(rb_en_lote);
         panel_envia        .add(rb_en_cancela);
         panel_envia        .add(rb_en_corrige);
         panel_envia        .add(rb_en_inutiliza);
         panel_envia        .add(ta_en_envia);
         panel_envia        .add(bt_en_impcarta);
         panel_envia        .add(bt_en_envia);
         panel_envia        .add(bt_en_impdanfe);

        habilita_campos_tela_config(false);
         tf_usuario         .setText(stat_usuario);
         tf_senha           .setText(""); //"123456");
         drive = System.getProperty("user.dir"); 
         if (drive.length() > 15) drive = "c:"+fsep+"nfe";  //  pasta raiz
         System.out.println("Pasta de dados do sistema: "+drive);
         le_configNfeFacil();
         System.out.println("Passou 1 . . . ");
         hoje               = new Date();
         String formato     = "yyyyMMdd";
         SimpleDateFormat   formatter = new SimpleDateFormat(formato);
         anomesdia          = formatter.format(hoje);
         ano                = anomesdia.substring(2,4);

         formato            = "dd/MM/yyyy";
         formatter          = new SimpleDateFormat(formato);
         datahoje           = formatter.format(hoje);
         tf_pc_data_ini     .setText(datahoje);
         tf_pc_data_fim     .setText(datahoje);

         conectaFatura();
         System.out.println("Passou 2 . . . ");
         preencherCBempresa();
        if (!tipoAmbiente.equals("1") && !tipoAmbiente.equals("2")) {
            JOptionPane.showMessageDialog(null, "Tipo de Ambiente informado [ "+getTipoAmbiente()+" ] não é válido!\n Informar: \n1 (para Produção) ou \n2 (para Homologação)");
            setTipoAmbiente("2"); 
        }
         System.out.println("Passou 3 . . . ");
         veriTipoAmbiente();

         System.out.println("Passou 4 . . . ");
         lookandfeel();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                sair();
            }
        });

    }
    private void sair() {
        int escolha = JOptionPane.showConfirmDialog(null, "Confirme a saida", "Tem certeza que deseja sair?", JOptionPane.OK_CANCEL_OPTION);
        if (escolha == JOptionPane.OK_OPTION) {
            if (tipoExecucao.equals("externa")) 
                System.exit(0);
            else 
                this.dispose();
        }
        
    }
    private void veriTipoAmbiente(){
         if (getTipoAmbiente().equals("1")) {
             getLb_ambiente().setText("** PRODUÇÃO **");
             getLb_ambiente().setForeground(Color.blue);
             lb_ambFat.setText("** PRODUÇÃO **");
             lb_ambFat.setForeground(Color.blue);
             lb_pc_ambiente.setText("** PRODUÇÃO **");
             lb_pc_ambiente.setForeground(Color.blue);
            setTitle("Sistema de NFe - Prof. Neri e Adelcio - "+versaoNFe+" - Produção");
         } else {
             getLb_ambiente().setText("** HOMOLOGAÇÃO **");
             getLb_ambiente().setForeground(Color.red);
             lb_ambFat.setText("** HOMOLOGAÇÃO **");
             lb_ambFat.setForeground(Color.red);
             lb_pc_ambiente.setText("** HOMOLOGAÇÃO **");
             lb_pc_ambiente.setForeground(Color.red);
            setTitle("Sistema de NFe - Prof. Neri e Adelcio - "+versaoNFe+" - Homologação");
         }

    }

//System.out.println(compararDatas("01/07/06","05/07/06").toString());
    public void checkValidadeDemo(){
        if (dataDemo.length() > 0) {
            String demoData = dataDemo.substring(6,8)+"/"+dataDemo.substring(4,6)+"/"+dataDemo.substring(0,4);
            Long difDatal = Biblioteca.compararDatas(datahoje, demoData); 
            difData = (int) (difDatal*1);
            System.out.println("Diferença entre a Data de Hoje ["+datahoje+"] e a Data Demo ["+demoData+"]...: "+difData);
            if (difData<10){
                if (difData<=0){
                    JOptionPane.showMessageDialog(null, "O Prazo de Validade expirou há [ "+Math.abs(difData)+" ] dia(s)!\n\n     O Painel de Tranmissões foi Bloqueado!\n\n                 Entre em contato com: \n\n            PORTO INFORMÁTICA LTDA\n     e-mail: portoinfo@sercomtel.com.br\n     Informe a Versão: "+versao+"\n");
                }else {
                    JOptionPane.showMessageDialog(null, "Falta(m) [ "+Math.abs(difData)+" ] dia(s) para expirar O Prazo de Validade!\n\n                 Entre em contato com:\n\n            PORTO INFORMÁTICA LTDA\n     e-mail: portoinfo@sercomtel.com.br\n     Informe a Versão: "+versao+"\n");
                }
            } else if (difData<20){
               JOptionPane.showMessageDialog(null, "     Esta Versão tem Validade até: "+demoData+"\n     Para adiquirir a Versão sem Prazo de Validade, \n         Entre em contato com:\n\n            PORTO INFORMÁTICA LTDA\n     e-mail: portoinfo@sercomtel.com.br\n     Informe a Versão: "+versao+"\n");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bt_login) {
            lb_status          .setText("Pressione \\\"Efetuar Login\\\" e aguarde a verificação do Status do Serviço");
            repaint();
            checkValidadeDemo();
           lb_status            .setText("");
           lb_status            .setFont(new Font("Arial",Font.BOLD,24));
           if (verificaSenha()){
                emp4dig = ""+empresa;
                int tamtxt = emp4dig.length();
                for (int i=0;i<(4-tamtxt);i++){
                    emp4dig = "0"+emp4dig;
                }
                ksFixo = null;
                pr_token = null;
                certUtil = null;
               if (wmodeloNFe.equals("55")) {
                   exibe_opcoes_menu();
                   configurarPastas();
                   if (tpEmis.equals(TPEMIS_NORMAL)) { //("1")) {
                        status_servico = check_status_servico("login");
                   } else if (tpEmis.equals(TPEMIS_OFFLINE)) { //("0")) {
                       lb_status.setForeground(Color.red);
                        lb_status.setText("Sistema Off Line");
                   } else if (tpEmis.equals(TPEMIS_EVENTO)) { //("5")) {
                        status_servico = check_status_servico("login");
                   } else {  //  tpEmis = "4"
                       lb_status.setForeground(Color.blue);
                        lb_status.setText("Emissão em Regime de Contingência - DPEC");
                   }
                    arqemail = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"configemailxml.cfg";
                    setStc_drive(drive);
                    setStc_camLerAss(caminho_ler_assinar);
                    setStc_camAssinat(getCaminho_assinatura());
                    setStc_camImpdanfe(getCaminho_grava_impDanfe());
                    setStc_fileCert(getFile_keystore());
                    setStc_senhaCert(getSenha_keystore());
                    setStc_fileKey(getFile_keystore());
                    setStc_drive(drive);
                    setStc_anomesdia(anomesdia);
                    setStc_tipoEmis(tpEmis);
                    setStc_exibirDsp(exibir_display);
//  adiciona Painel fatura
                    addMenuFatura();
                    painel_pai         .addTab("Faturamento",panel_fatura);
//  adiciona painel consulta            
                    painel_pai         .addTab("Consultas",panel_consulta);
//  adiciona painel transmite            
                    painel_pai         .addTab("Transmissões",panel_envia);
                    painel_pai.setSelectedComponent(panel_login);
                    rb_pc_consulta_sitnfe.setSelected(true);
                    panel_consulta     .remove(lb_pc_data_ini);
                    panel_consulta     .remove(tf_pc_data_ini);
                    panel_consulta     .remove(lb_pc_data_fim);
                    panel_consulta     .remove(tf_pc_data_fim);
                    panel_consulta     .remove(rb_pc_autoriz);
                    panel_consulta     .remove(rb_pc_rejeit);
                    panel_consulta     .remove(rb_pc_todas);
                    panel_consulta     .repaint();
                    tipo_consulta      = "sitnfe";
                    tipo_envio = "lote";
                    lb_ambFat          .setBounds(0, 25, 220, 25);
                    lb_statFat         .setBounds(780, 25, 220, 25);
                    panel_envia       .add(lb_ambFat);
                    panel_envia       .add(lb_statFat);
                    painel_pai.setSelectedComponent(panel_fatura);
//                    if (import_automat.toLowerCase().equals("a")) {
//                        processar_escutarPasta();
//                    } else 
                    if (import_automat.toLowerCase().equals("55")) {
                        if (status_servico) {
                            monitorComModelo = true;
                            getCiNFe().executar(getInterval(), true);
                            JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfe para Gerar a NFe(modelo 55) </html>");
                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 24pt; color: red;'>Serviço Indisponível! Para monitorar, o sistema precisa acessar a SEFAZ!</html>");
                        }
                    } else if (import_automat.toLowerCase().equals("65")) {
                        if (status_servico) {
                            monitorComModelo = true;
                            getCiNFCe().executar(getInterval(), true);
                            JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfce para Gerar a NFCe(modelo 65) </html>");
                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 24pt; color: red;'>Serviço Indisponível! Para monitorar, o sistema precisa acessar a SEFAZ!</html>");
                        }
                    } else {
                        monitorComModelo = false;
                    }
               } else if (wmodeloNFe.equals("65")) { // NFC-e - Nota Fiscal Consumidor Eletronica
                    setMotivo("*** OFF LINE ***");
                   configurarPastas();
                    setStc_camLerAss(caminho_ler_assinar);
                    setStc_camAssinat(getCaminho_assinatura());
                    setStc_camImpdanfe(getCaminho_grava_impDanfe());
                    setStc_fileCert(getFile_keystore());
                    setStc_senhaCert(getSenha_keystore());
                    setStc_fileKey(getFile_keystore());
                    setStc_drive(drive);
                    setStc_anomesdia(anomesdia);
                    setStc_tipoEmis(tpEmis);
                   setStc_exibirDsp(exibir_display);
//JOptionPane.showMessageDialog(null, "Fuso Horario: " + fuso_horario);
                   new br.com.videoaulasneri.adelcio.fatura.NFCe(confat, empresa, tf_usuario.getText(), stat_caixa, fuso_horario, cUF, nome_empresa, endereco_empresa, contatoCupom, uriCupom, informarCnpjCartao, cupomNaTela).show();
               } else {
                   JOptionPane.showMessageDialog(null, "Este Usuario tem um modelo de NFe desconhecido [ "+wmodeloNFe+"] . . .Informe o responsável pelo sistema!");
                   System.exit(0);
               }
           } else {
            //JOptionPane.showMessageDialog(null, "NÃo Passou verificaSenha() . . .");
           }
        }
        else if (e.getSource() == bt_login_status){
            status_servico = check_status_servico("login");
        }
        else if (e.getSource() == bt_sair)
        {
            sair();
        }
        else if (e.getSource() == bt_pc_consulta)
        {
            ta_pc_consulta.setText("");
                if (tipo_consulta.equals("sitper")){
                    System.out.println("tf_data_ini: "+tf_pc_data_ini.getText()+" - tf_data_fim: "+tf_pc_data_fim.getText());
                    String dataini = Biblioteca.checkDataInformada(tf_pc_data_ini.getText(), anomesdia);
                    if (!dataini.equals("erro")){
                        String datafim = Biblioteca.checkDataInformada(tf_pc_data_fim.getText(), anomesdia);
                        if (!datafim.equals("erro")){
                            System.out.println("Data ini: "+dataini+" - data fim: "+datafim);
                            if (tf_pc_data_ini.getText().equals("") || tf_pc_data_fim.getText().equals("")){
                                JOptionPane.showMessageDialog(null, "As datas incial e final precisam ser informadas!");
                            } else if (Integer.parseInt(dataini) > Integer.parseInt(datafim)){
                                JOptionPane.showMessageDialog(null, "A Data Inicial não pode ser maior que a Data Final!");

                            } else if ( !rb_pc_autoriz.isSelected() && !rb_pc_rejeit.isSelected() && !rb_pc_todas.isSelected() ){
                                JOptionPane.showMessageDialog(null, "Escolha uma das Opções: \n     Só Autorizadas,\n     Só Rejeitadas ou\n     Todas");
                            } else {
                                ta_pc_consulta.setText("");
                                panel_consulta.repaint();
                                //processa_sitperiodonfe(dataini, datafim, tipo_periodo, ta_pc_consulta);
                            }
                        }
                    }
                } else {
                    if (Biblioteca.check_certificados(getFile_keystore(), getFile_truststore()) && status_servico ){
                        if (tipo_consulta.equals("lote")){
                            JOptionPane.showMessageDialog(null, "Em desenvolvimento. Aguarde!!!");
                        //processa_retrecepcaonfe();
                        } else if (tipo_consulta.equals("sitnfe")){
                            if (check_status_servico("envio")) {
                                processa_consultaNfe();
                            } else {
                                JOptionPane.showMessageDialog(null, "Serviço fora de Operação. Tente mais tarde!");
                            }
                        } if (tipo_consulta.equals("statserv")){
                            //processa_consultaStatus();
                            check_status_servico("consulta");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "(1)Serviço fora de Operação ou Arquivo(s) de Certificado(s) não encontrado(s)!\n Favor informar administrador!");
                }
            }
        }
        else if (e.getSource() == bt_pc_sair)
        {
            bg_login.clearSelection();
            painel_pai.setSelectedComponent(panel_login);
        }
        else if (e.getSource() == fatUnidade)  //bt_fat_unidade)
        {
            new br.com.videoaulasneri.adelcio.fatura.Unidade(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();


        }
//         else if (e.getSource() == fatImpVenda)  
//        {
//             processar_escutarPasta();
//        }
         else if (e.getSource() == fatMonitoraNFeNFCe)  
        {
            if (status_servico) {
                processar_monitorNFeNFCe();
            } else {
                JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 24pt; color: red;'>Serviço Indisponível! Para monitorar, o sistema precisa acessar a SEFAZ!</html>");
            }
        }
         else if (e.getSource() == fatStopMonitorNFeNFCe)  
        {
             processar_stopMonitorNFeNFCe();
        }
             else if (e.getSource() == fatCliente)  //bt_fat_clie)
        {
           new br.com.videoaulasneri.adelcio.fatura.Cliente(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa, cUF).show();
        }
        else if (e.getSource() == fatProduto)  //bt_fat_prod)
        {
           new br.com.videoaulasneri.adelcio.fatura.Produto(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
        }
        else if (e.getSource() == fatAliqUf)  //bt_fat_aliquf) 
        {
           new br.com.videoaulasneri.adelcio.fatura.AliqUF(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
        }
        else if (e.getSource() == fatCidade)  //bt_fat_cidade)
        {
           new br.com.videoaulasneri.adelcio.fatura.Cidades(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
        }
        else if (e.getSource() == fatBanco)  //bt_fat_banco)
        {
           new br.com.videoaulasneri.adelcio.fatura.Banco(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
        }
        else if (e.getSource() == fatXmlCompra)  //bt_fat_xmlCompra)
        {
            br.com.videoaulasneri.adelcio.fatura.XML_Compra lerxml = new XML_Compra(empresa, confat);
        }
        else if (e.getSource() == fatCfop)  //bt_fat_cfop)
        {
            new br.com.videoaulasneri.adelcio.fatura.Cfop(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
       }
        else if (e.getSource() == fatFormaPgto)  //bt_fat_forma_pgto)
        {
            new br.com.videoaulasneri.adelcio.fatura.Forma_pgto(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
       }
        else if (e.getSource() == fatTipoDoc)  //bt_fat_tipo_doc)
        {
           new br.com.videoaulasneri.adelcio.fatura.Tipo_doc(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa).show();
        }
        else if (e.getSource() == fatDigiNf)  //bt_fat_diginf)
        {
            new br.com.videoaulasneri.adelcio.fatura.Nf(empresa, confat, fuso_horario, cUF, contatoCupom).show();
        }
         else if (e.getSource() == fatCancNf)  //bt_fat_cancnf)
        {
            processa_cancelaNfe();
        }
        else if (e.getSource() == fatDevoNf)  //bt_fat_devonf)
        {
            XML_Devolucao devo = new XML_Devolucao();
            devo.trataXML(empresa, getCaminho_grava_assinar(), anomesdia,confat, getTipoAmbiente(), fuso_horario);
        }
        else if (e.getSource() == fatLerXml)  //bt_fat_lerxml)
        {
            XMLReader lerxml = new XMLReader(""+empresa);
        }
        else if (e.getSource() == fatBaixa)  //bt_fat_baixa)
        {
            new Baixa(empresa, confat).show();
        }
        else if (e.getSource() == fatRelFatura)  //bt_fat_rel_fatura)
        {
            Relfatper relfatper = new Relfatper(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa, tipoAmbiente);
            relfatper.show();
        }
        else if (e.getSource() == fatRelRecebido)  //bt_fat_rel_recebido)
        {
            RelRecper relRecper = new RelRecper(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa, tipoAmbiente);
            relRecper.show();
        }
        else if (e.getSource() == fatRelAreceber)  //bt_fat_rel_areceber)
        {
            RelArecper relArecper = new RelArecper(empresa, confat, emp4dig+" - "+nome_empresa, endereco_empresa, tipoAmbiente);
            relArecper.show(); 
        }
        else if (e.getSource() == bt_en_impdanfe)
        {
            imprimeDanfeJava();
        }
        else if (e.getSource() == rb_emis_normal){
            tpEmis = TPEMIS_NORMAL;
            System.out.println("Tipo de Emissao = Normal ");
        }
        else if (e.getSource() == rb_emis_offLine){
            tpEmis = TPEMIS_OFFLINE;
            System.out.println("Tipo de Emissao = Off Line ");
        }
       else if (e.getSource() == rb_emis_dpec){
            tpEmis = TPEMIS_DPEC;
        }
        else if (e.getSource() == rb_en_lote)
        {
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_envia);
            panel_envia.repaint();
            tipo_envio = "lote";
        }
        else if (e.getSource() == rb_en_cancela)
        {
            panel_envia.remove(lb_en_modelo);
            panel_envia.remove(tf_en_modelo);
            panel_envia.remove(lb_en_serie);
            panel_envia.remove(tf_en_serie);
            panel_envia.remove(lb_en_nfinut_ini);
            panel_envia.remove(tf_en_nfinut_ini);
            panel_envia.remove(lb_en_nfinut_fim);
            panel_envia.remove(tf_en_nfinut_fim);
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_envia);
            panel_envia.repaint();
            tipo_envio = "cancela";
        }
        else if (e.getSource() == rb_en_corrige)
        {
            panel_envia.remove(lb_en_modelo);
            panel_envia.remove(tf_en_modelo);
            panel_envia.remove(lb_en_serie);
            panel_envia.remove(tf_en_serie);
            panel_envia.remove(lb_en_nfinut_ini);
            panel_envia.remove(tf_en_nfinut_ini);
            panel_envia.remove(lb_en_nfinut_fim);
            panel_envia.remove(tf_en_nfinut_fim);
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_envia);
            panel_envia.repaint();
            tipo_envio = "corrige";
        }
        else if (e.getSource() == rb_en_inutiliza)
        {
            if (carregaDadosEmitente()) {
                panel_envia .add(lb_en_modelo);
                panel_envia .add(tf_en_modelo);
                panel_envia .add(lb_en_serie);
                panel_envia .add(tf_en_serie);
                panel_envia .add(lb_en_nfinut_ini);
                panel_envia .add(tf_en_nfinut_ini);
                panel_envia .add(lb_en_nfinut_fim);
                panel_envia .add(tf_en_nfinut_fim);
                tf_en_modelo.setText("55");
                tf_en_serie.setText("1");
                try {
                    UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
                } catch (Exception ex) {
                    Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.updateComponentTreeUI(panel_envia);
                panel_envia.repaint();
                tipo_envio = "inutiliza";
            }
        }
        else if (e.getSource() == rb_pc_consulta_lote)
        {
             panel_consulta     .remove(lb_pc_data_ini);
             panel_consulta     .remove(tf_pc_data_ini);
             panel_consulta     .remove(lb_pc_data_fim);
             panel_consulta     .remove(tf_pc_data_fim);
             panel_consulta     .remove(rb_pc_autoriz);
             panel_consulta     .remove(rb_pc_rejeit);
             panel_consulta     .remove(rb_pc_todas);
             panel_consulta     .repaint();
             tipo_consulta      = "lote";
        }
        else if (e.getSource() == rb_pc_consulta_periodo)
        {
             panel_consulta     .add(lb_pc_data_ini);
             panel_consulta     .add(tf_pc_data_ini);
             panel_consulta     .add(lb_pc_data_fim);
             panel_consulta     .add(tf_pc_data_fim);
             panel_consulta     .add(rb_pc_autoriz);
             panel_consulta     .add(rb_pc_rejeit);
             panel_consulta     .add(rb_pc_todas);
             //tf_pc_data_ini     .requestFocus();
             ta_pc_consulta     .setText("");
             panel_consulta     .repaint();
             tipo_consulta      = "sitper";
        }
        else if (e.getSource() == rb_pc_consulta_sitnfe)
        {
             panel_consulta     .remove(lb_pc_data_ini);
             panel_consulta     .remove(tf_pc_data_ini);
             panel_consulta     .remove(lb_pc_data_fim);
             panel_consulta     .remove(tf_pc_data_fim);
             panel_consulta     .remove(rb_pc_autoriz);
             panel_consulta     .remove(rb_pc_rejeit);
             panel_consulta     .remove(rb_pc_todas);
             panel_consulta     .repaint();
             tipo_consulta      = "sitnfe";
        }
        else if (e.getSource() == rb_pc_consulta_stat)
        {
             panel_consulta     .remove(lb_pc_data_ini);
             panel_consulta     .remove(tf_pc_data_ini);
             panel_consulta     .remove(lb_pc_data_fim);
             panel_consulta     .remove(tf_pc_data_fim);
             panel_consulta     .remove(rb_pc_autoriz);
             panel_consulta     .remove(rb_pc_rejeit);
             panel_consulta     .remove(rb_pc_todas);
             panel_consulta     .repaint();
             tipo_consulta      = "statserv";
        }
        else if (e.getSource() == rb_pc_autoriz)
        {
            tipo_periodo = "autoriz";
        }
        else if (e.getSource() == rb_pc_rejeit)
        {
            tipo_periodo = "rejeit";
        }
        else if (e.getSource() == rb_pc_todas)
        {
            tipo_periodo = "todas";
        }
         else if (e.getSource() == bt_en_impcarta)
        {
             ta_en_envia.setText("");
             imprimeCartaCorrecao();
        }
         else if (e.getSource() == bt_en_envia)
        {
             ta_en_envia.setText("");
             if (difData <=0 && dataDemo.length() > 0) {
                 checkValidadeDemo();
             } else {
                 if (Biblioteca.check_certificados(getFile_keystore(), getFile_truststore())){
                    if (tipo_envio.equals("inutiliza")){
                        if (!tf_en_nfinut_ini.getText().equals("")){
                            if (Integer.parseInt(tf_en_nfinut_ini.getText())<=Integer.parseInt(tf_en_nfinut_fim.getText())){
                                if (tf_en_modelo.getText() != null && (tf_en_modelo.getText().equals("55") || tf_en_modelo.getText().equals("65"))) {
                                    if (tf_en_serie.getText() != null && !tf_en_serie.getText().equals("")) {
                                    processa_inutilizaNfe(tf_en_modelo.getText(), tf_en_serie.getText(), tf_en_nfinut_ini.getText(), tf_en_nfinut_fim.getText());
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Alguma Serie tem que ser informada (Ex: 1, 2, etc) !!");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Modelo tem que ser 55(NFe) ou 65 (NFCe) !!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "NF Inicial NÃO pode ser maior que NF Final !!");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "NF Inicial e Final precisam ser informadas!");
                        }
                    }
                    else if (tipo_envio.equals("cancela")){
                        if (carregaDadosEmitente()) {
                            processa_cancelaNfe();
                        }
                    }
                    else if (tipo_envio.equals("corrige")){
                        processa_corrigeNfe();
                    }
                    else if (tipo_envio.equals("lote")){
                        if ( status.equals("107") || check_status_servico("envio") ){
                            processa_enviaLoteNfe();
                        } else {
                            JOptionPane.showMessageDialog(null, "Serviço fora de Operação. Tente mais tarde!");
                        }
                    }
                 } else {
                    JOptionPane.showMessageDialog(null, "(2)Serviço fora de Operação ou Arquivo(s) de Certificado(s) não encontrado(s)!\n Favor informar administrador!");
                 }
             }
        }
         else if (e.getSource() == bt_en_sair)
        {
            bg_login.clearSelection();
            painel_pai.setSelectedComponent(panel_login);
        }
        else if (e.getSource() == admMenu1)  //bt_cfg_empresa)
        {
            new br.com.videoaulasneri.adelcio.admin.Empresa(confat).show();
        }
        else if (e.getSource() == admMenu2)  //bt_cfg_pardigital)
        {
           new br.com.videoaulasneri.adelcio.admin.Pardigital(empresa, confat).show();
        }
        else if (e.getSource() == admMenu3)  //bt_cfg_login)
        {
            Login log = new Login(confat, empresa);
            log.show();
        }
        else if (e.getSource() == admMenu4)  //bt_cfg_maquina)
        {
           new br.com.videoaulasneri.adelcio.admin.Maquina(empresa, confat).show();
        }
        else if (e.getSource() == admMenu5)  //bt_cfg_caixa)
        {
           new br.com.videoaulasneri.adelcio.admin.Caixa(empresa, confat).show();
        }
        else if (e.getSource() == admMenu6)  //bt_cfg_numeronfe)
        {
           new br.com.videoaulasneri.adelcio.admin.Numeronfe(empresa, confat).show();
        }
        
        else if (e.getSource() == admMenu7)  //bt_cfg_importaIbge)
        {
            String codigoUF = "";
            while (true){
                codigoUF = JOptionPane.showInputDialog("Informe a UF para Importar(Ex: AL,PR, RS, ...(XX-Todos)", codigoUF);
                break;
            }
            if (codigoUF == null || codigoUF.equals("")) {
             JOptionPane.showMessageDialog(null,"Você desistiu de Importar Cidades! ");
            } else {
                ImportaCidades leibge = new ImportaCidades(confat);
                leibge.ImportaArquivo(codigoUF.toUpperCase());
            }
        }
        
        else if (e.getSource() == admMenu8)  
        {
           new ConfiguraEmail(arqemail);
        }
        
        else if (e.getSource() == admMenu9)  
        {
            enviaEmailXml(false, "");
        }
        
        else if (e.getSource() == admMenu10)  
        {
            File[] arquivos = escolhe_arquivo(true, caminho_ibpt, panel_envia,JFileChooser.FILES_AND_DIRECTORIES, "csv");
            if (arquivos != null ){
                reimprime = true;
                String arqIbpt = null;
                for (File arquivo : arquivos){
                    arqIbpt = arquivo.getAbsolutePath();  
                }
                if (arqIbpt != null) {
                    ImportaIbpt leibpt = new ImportaIbpt(confat);
                    JOptionPane.showMessageDialog(null,"cUF: " + cUF);
                    leibpt.ImportaArquivo(arqIbpt, cUF);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Você desistiu de Importar a Tabela IBPT! ");
            }
        }
         else if (e.getSource() == admMenu11)  
        {
            JOptionPane.showMessageDialog(null, "Vai consultar  as informacoes do cartao . . .");
            DadosCertificadoA3 dadosCertA3 = new DadosCertificadoA3();
            dadosCertA3.processar();
            JOptionPane.showMessageDialog(null, "Voltou de consultar as informacoes do cartao . . .");
        }
         else if (e.getSource() == admMenu12)  
        {
            JOptionPane.showMessageDialog(null, "Vai consultar status de servico com cartao . . .");
            NFeStatusServicoFactoryDinamicoA3 statA3 = new NFeStatusServicoFactoryDinamicoA3();
            JOptionPane.showMessageDialog(null, "Voltou de consultar status de servico com cartao . . .");
        }
         else if (e.getSource() == admMenu13)  
        {
            JOptionPane.showMessageDialog(null, "Vai gerar o arquivo NFecacerts . . .");
            GerarCacertsNfe buildCacerts = new GerarCacertsNfe();
            if (buildCacerts.processar()) {
                JOptionPane.showMessageDialog(null, "Gerou o arquivo NFecacerts . . .");
            }
        }
        
   }
    public void exibe_opcoes_menu(){
             painel_pai.setFocusable(false);
             lb_saudacao        .setText("Bem-vindo ao sistema, "+wnome_usuario);
             qt_panel           = 1;  //5;
    }
    void configurarPastas() {
             lookandfeel();
             le_configNfeFacil();
             inicializa_caminhos();
             verifica_existe_pasta_hoje();
             painel_pai.setFocusable(true);

    }
   public void habilita_campos_tela_config(boolean status){
   }
    @Override
    public void itemStateChanged(ItemEvent e) {
       if (e.getSource() == cb_empresa){
           if (qt_panel>1){

           }
            for (int i=1;i<qt_panel;i++){
                painel_pai.remove(1);
            }
            qt_panel = 1;
        }
    }
   private void preencherCBempresa() {
       try {
              st_nfe = confat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              String sql = "select par.empresa, emp.codigo as emp_codigo, emp.codempresa as emp_codempresa, emp.razao as emp_razao, emp.fantasia as emp_fantasia, "
                      + "cid.codcidade as cid_codcidade, emp.codcidade as emp_codcidade, emp.codigo, cid.codigo  "
                    + "from pardigital as par "
                    + "inner join empresa as emp on emp.codigo = par.empresa "
                    + "inner join ibge as cid on emp.codcidade = cid.codigo "
                    + "order by emp.codempresa ";
              //System.out.println("Comando sql pardigital: "+sql);
              rs_nfe = st_nfe.executeQuery(sql);
              cb_empresa.removeAllItems();
              while(rs_nfe.next()) {
                  
                  cb_empresa.addItem(
                          ""
                          +rs_nfe.getString("emp_codigo")+" "
                          +rs_nfe.getString("emp_razao")
                  );

            }
              if (stat_empresa > 0){
                  if (cb_empresa.getItemCount() == 0) {
                        JOptionPane.showMessageDialog(null,"Nao encontrou nenhum registro na tabela: pardigital!");
                  } else if (stat_empresa <= cb_empresa.getItemCount()){
                        cb_empresa.setSelectedIndex(stat_empresa);
                  } else {
                        cb_empresa.setSelectedIndex(0);
                  }
              }
            for (int i=1;i<qt_panel;i++){
                painel_pai.remove(i);
            }
            qt_panel = 1;
       }
        catch(Exception erro_sql)
        {
             JOptionPane.showMessageDialog(null,"Não conseguiu preencher o campo de empresas: "+erro_sql);
        }

   }
    public void lookandfeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            SwingUtilities.updateComponentTreeUI(panel_login);
            SwingUtilities.updateComponentTreeUI(panel_consulta);
            SwingUtilities.updateComponentTreeUI(panel_envia);
//            SwingUtilities.updateComponentTreeUI(panel_configura);
            SwingUtilities.updateComponentTreeUI(panel_fatura);
        }
        catch(Exception erro_laf)
        {
            JOptionPane.showMessageDialog(null,"Não conseguiu setar o look and feel Liquid, mesmo assim\n"+
                    "o software irá funcionar normalmente..  erro = "+erro_laf);
        }
    }
    public boolean verificaSenha() {
        boolean retorno = true;
        String sql = "";
        codempresa = cb_empresa.getSelectedItem().toString().substring(0, cb_empresa.getSelectedItem().toString().indexOf(" "));
System.out.println("Empresa selecionada: "+cb_empresa.getSelectedItem().toString()); 
        retorno = carregaDadosEmitente();
System.out.println("voltou do metodo carregaDadosEmitente() com: "+retorno);        
        if (retorno) {
            String tpemisStr = "";
            if (tpEmis.equals(TPEMIS_NORMAL)) {
                tpemisStr = "NORMAL";
            } else if (tpEmis.equals(TPEMIS_EVENTO)) {
                tpemisStr = "EVENTO";
            } else if (tpEmis.equals(TPEMIS_OFFLINE)) {
                tpemisStr = "OFF LINE";
            } else {
                tpemisStr = "DPEC";
            }
            lb_en_titulo.setText(lb_en_titulo.getText()+" ("+nome_empresa.trim()+") - "+tpemisStr);

            try {
                sql = "select * from login where empresa = "+empresa+" and usuario = '" + tf_usuario.getText()+"'";
                rs_nfe = st_nfe.executeQuery(sql);
                wsenha = "";
                boolean achouusu = false;
                while (rs_nfe.next()) {
                    wsenha = rs_nfe.getString("senha");
                    wnome_usuario = rs_nfe.getString("nome");
                    wmodeloNFe = rs_nfe.getString("modelonfe");
                    wnivel_usuario = rs_nfe.getString("nivel");
                    achouusu = true;
                }
                if (!achouusu){
                   JOptionPane.showMessageDialog(null, "Usuario [ "+tf_usuario.getText()+" ] nao cadastrado para a empresa: "+empresa);
                   retorno = false;
                } else if (!wsenha.equals(tf_senha.getText())){
                   JOptionPane.showMessageDialog(null, "Senha digitada não é Válida!");  //  nao confere com a tabela ["+wsenha+"]");
                   retorno = false;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao ler a tabela Login: "+ex);
                retorno = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao manipular a tabela Login: "+ex);
                retorno = false;
            }
        }
System.out.println("Fim do metodo: verificaSenha() . . . ");        
        return retorno;

    }
    
    private boolean carregaDadosEmitente() {
        boolean retorno = false;
        String cep = "";
        try {
            st_nfe = confat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select "
                    + "par.empresa, "
                    + "par.senha_token as par_senha_token, "
                    + "par.senha_keystore as par_senha_keystore, "
                    + "par.senha_truststore as par_senha_truststore, "
                    + "emp.codempresa as emp_codempresa, "
                    + "emp.razao as emp_razao, "
                    + "emp.fantasia as emp_fantasia, "
                    + "emp.endereco as emp_endereco, "
                    + "emp.numero as emp_numero, "
                    + "emp.bairro as emp_bairro, "
                    + "emp.cep as emp_cep, "
                    + "cid.codcidade as cid_codcidade, "
                    + "cid.cidade as cid_cidade, "
                    + "cid.uf as cid_uf, "
                    //+ "emp.codcidade as emp_codcidade, "
                    + "emp.codigo, "
                    + "cid.codigo,  "
                    + "emp.cnpj as emp_cnpj "
                    + "from pardigital as par "
                    + "inner join empresa as emp on emp.codigo = par.empresa "
                    + "inner join ibge as cid on emp.codcidade = cid.codigo "
                    + "where empresa = "+codempresa
                    + " order by emp.codempresa ";
System.out.println("sql de consulta a empresa: "+sql);            
            rs_nfe = st_nfe.executeQuery(sql);
            while (rs_nfe.next()) {
                empresa                     = Integer.parseInt(rs_nfe.getString("emp_codempresa").trim());
                stat_empresa = empresa;
                setSenha_keystore(rs_nfe.getString("par_senha_keystore"));
                setSenha_truststore(rs_nfe.getString("par_senha_truststore"));
                cUF                         = rs_nfe.getString("cid_codcidade").substring(0,2);
                cnpj                        = rs_nfe.getString("emp_cnpj");
                senha_token                 = rs_nfe.getString("par_senha_token");
                nome_empresa                = rs_nfe.getString("emp_razao");
                cep                         = rs_nfe.getString("emp_cep");
                if (senha_token != null && senha_token.trim().length() > 0) {
                    setFile_keystore(pasta_raiz + "token.cfg");
                    setSenha_keystore(senha_token);
                }

                endereco_empresa = rs_nfe.getString("emp_endereco").trim();
                String numero = rs_nfe.getString("emp_numero").trim();
                String bairro = rs_nfe.getString("emp_bairro").trim();
                endereco_empresa += ", "+numero+" - "+bairro+ " - "+rs_nfe.getString("cid_cidade").trim()+" - "+rs_nfe.getString("cid_uf");
                setCodcid(rs_nfe.getString("cid_codcidade"));
                
                lb_pc_titulo.setText(lb_pc_titulo.getText()+" ("+nome_empresa.trim()+")");
                retorno = true;
            }
            String msgAlerta = "";
            if (
                    (getSenha_keystore() == null || getSenha_keystore().length() < 3) &&
                    (senha_token == null || senha_token.length() < 3)
                ) {
                msgAlerta += "A senha do certificado (do certificado.pfx, Cartão ou Token) ainda não foi Informada. \n\n";
            }
            if (nome_empresa == null || nome_empresa.indexOf("EMPRESA PADRAO") != -1) {
                msgAlerta += "O Nome da Empresa Emitente ainda não foi informado!\n";
            }
            if (cnpj == null || cnpj.length() != 14) {
                msgAlerta += "O CNPJ do Emitente ainda não foi informado ou não está correto! Informe o CNPJ sem formatação(Ex: 02345678000154)\n";
            }
            if (cep == null || cep.length() != 8) {
                msgAlerta += "O CEP do Emitente ainda não foi informado ou não está correto! Informe o CEP com 9 caracteress(Ex: 86010037)\n";
            }
            if (msgAlerta.length() > 0) {
                msgAlerta = "Atenção! Faltam algumas informações importantes para o sistema funcionar:\n" + msgAlerta;
                msgAlerta += "\n\nInforme o Administrador do Sistema!";
                msgAlerta += "\n\n Verifique também se a numeração da última NFe/NFCe está correta!";
                JOptionPane.showMessageDialog(null, msgAlerta);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler a tabela empresa, ibge ou pardigital: "+ex);
            retorno = false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao manipular as tabelas empresa, ibge ou pardigital: "+ex);
            retorno = false;
        }
        return retorno;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (painel_pai.getSelectedIndex() == 1){
            if (ja_consultou){}
        }
        
        if (e.getSource() == tf_usuario || e.getSource() == tf_senha) {
            System.out.println("mouseClicked - Clicou no campo: tf_usuario ou tf_senha . . .");
            repaint();
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("pressionou o mouse no componente: "+e.getSource());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased no componente: "+e.getSource());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("mouseEntered no componente: "+e.getSource());
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("mouseExited no componente: "+e.getSource());
    }
    public void processa_cancelaNfe(){
        File[] arquivos = escolhe_arquivo(true, getCaminho_nfeprot()+"\\"+anomesdia, panel_envia,JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            ta_en_envia.setText("");
            chNfe       =  "";
            UIManager.put("OptionPane.yesButtonText", "Cancelar esta NFe");
            UIManager.put("OptionPane.noButtonText", "Desistir");
            for (int i=0;i<arquivos.length;i++){
                String arquivo = arquivos[i].getAbsolutePath();
                int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Cancelar a NFe\n"+arquivo, "Cancelamento de NFe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if ( escolha == JOptionPane.YES_OPTION){
                    tratar_cancelaNfe(arquivo);  //+"//"+f_result[i].getName());
                } else {
                    JOptionPane.showMessageDialog(null,"A NFe "+arquivo+" não foi cancelada!");
                }
            }
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.noButtonText", "Não");
            ta_en_envia.append("\n\n . . .FIM DO CANCELAMENTO DA(S) NFeS ESCOLHIDAS . . .\n");
         } else {
            ta_en_envia.append("\n\n . . . PROCESSO DE CANCELAMENTO FOI ABORTADO . . .\n");
            JOptionPane.showMessageDialog(null,"PROCESSO DE CANCELAMENTO FOI ABORTADO", "Cancelamento de NFe", JOptionPane.WARNING_MESSAGE);
         }

    }
    public void processa_corrigeNfe(){
        File[] arquivos = escolhe_arquivo(true, getCaminho_nfeprot()+"\\"+anomesdia, panel_envia,JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            ta_en_envia.setText("");
            chNfe       =  "";
            UIManager.put("OptionPane.yesButtonText", "Corrigr esta NFe");
            UIManager.put("OptionPane.noButtonText", "Desistir");
            for (int i=0;i<arquivos.length;i++){
                String arquivo = arquivos[i].getAbsolutePath();
                int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Corrigir a NFe\n"+arquivo, "Carta de Correcao de NFe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if ( escolha == JOptionPane.YES_OPTION){
                    tratar_corrigeNfe(arquivo);
                } else {
                    JOptionPane.showMessageDialog(null,"A NFe "+arquivo+" não foi corrigida!");
                }
            }
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.noButtonText", "Não");
            ta_en_envia.append("\n\n . . .FIM DA CORRECAO DA(S) NFeS ESCOLHIDAS . . .\n");
         } else {
            ta_en_envia.append("\n\n . . . PROCESSO DE CORRECAO FOI ABORTADO . . .\n");
            JOptionPane.showMessageDialog(null,"PROCESSO DE CORRECAO FOI ABORTADO");
         }

    }
    public void processa_consultaNfe(){
         ta_pc_consulta     .setText("");
         panel_consulta     .repaint();
        File[] arquivos = escolhe_arquivo(true, caminho_nfeprot+"\\"+anomesdia, panel_consulta,JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            ta_pc_consulta.setText("");  //removeAll();
            panel_consulta.repaint();
            panel_consulta.repaint();
            chNfe       =  "";
            for (int i=0;i<arquivos.length;i++){
                String pasta = arquivos[i].getAbsolutePath();
                System.out.println("A pasta do arquivo escolhido: " + pasta);  //getSelectedFile().getName());
                String dataXML = pasta.substring(pasta.length()-25,pasta.length()-17);
                tratar_consultaNfe(pasta, dataXML);
            }
            ta_pc_consulta.append("\n\n . . .FIM DA CONSULTA DA(S) NFeS ESCOLHIDAS . . .\n");
            ja_consultou = true;
         } else {
            ta_pc_consulta.append("\n\n . . . PROCESSO DE CONSULTAS FOI ABORTADO . . .\n");
            JOptionPane.showMessageDialog(null,"PROCESSO DE CONSULTAS FOI ABORTADO");
         }
         panel_consulta.repaint();

    }
    public void tratar_consultaNfe(String arq_consulta, String dataXML) {
        chNfe = Biblioteca.extrair_TAG(arq_consulta, "infNFe", "A", "Id", 1);
        chNfe = chNfe.substring(3, chNfe.length());
        String numero = Biblioteca.extrair_TAG(arq_consulta, "ide", "T", "nNF", 1);
        String modeloNFe = Biblioteca.extrair_TAG(arq_consulta, "ide", "T", "mod", 1);
        ta_pc_consulta.append("Aguarde . . .Consultando Nfe: "+chNfe+"\n\n");
        ConsultarNFe consultar = new ConsultarNFe(
                getFile_keystore(), 
                getSenha_keystore(), 
                getTipoAmbiente(), 
                numero,
                modeloNFe,
                Biblioteca.pegaEstado(cUF)
            );
       resultado = consultar.processar(chNfe);                
        if (!resultado.equals("erro")){
            exibeTextonoTextArea(resultado, ta_pc_consulta, "consultaNfe");
            if (pasta_grava_consulta.equals("")){
                pasta_grava_consulta = escolhe_pasta(caminho_grava_retconsu, panel_consulta);
            }
            if (!pasta_grava_consulta.equals("")){
                String nome_arquivo = pasta_grava_consulta+ "\\"+"retconsu_"+chNfe.substring(25,34)+".xml";
                gravar_retorno(nome_arquivo, resultado, ta_pc_consulta);
            }
        }else {
            JOptionPane.showMessageDialog(null,"Ocorreu algum erro durante a Transmissão!");
            ta_pc_consulta.setText("");
        }

    }
    public void imprimeCartaCorrecao(){
        XMLReaderCarta xmlcarta = new XMLReaderCarta();
        File fileName = escolheArqCarta();
        if (fileName != null && !fileName.equals("")) {
            String caminho_corrige = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"corrige"+fsep+"Bkp";
            br.com.videoaulasneri.adelcio.nfefacil.bean.CartaCorr carta = xmlcarta.trataXML(fileName.getAbsolutePath(), caminho_corrige);
             
            if (carta != null) {
                try {
                    // Gravando no arquivo
                    File arquivo;
                    String arqTXTCarta = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"txt"+fsep+"bkp"+fsep+anomesdia+fsep+fileName.getName().replaceAll(".xml",".pdf");
                    com.lowagie.text.Document document = null;
                    OutputStream outPutStream = null;
                    try {
                        document = new com.lowagie.text.Document(com.lowagie.text.PageSize.A4,30,20,20,30);
                        outPutStream = new FileOutputStream(arqTXTCarta);
                        try {
                            com.lowagie.text.pdf.PdfWriter.getInstance(document, outPutStream);
                            com.lowagie.text.Font font = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_ROMAN, 36, com.lowagie.text.Font.BOLD, new Color(0, 0, 255));
                            document.open();
                            Paragraph cabecalho;
                            cabecalho = new com.lowagie.text.Paragraph("Carta de Correção", font);
                            cabecalho.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                            document.add(cabecalho);

                            Paragraph paragrafo;

                            paragrafo = new Paragraph(" ");
                            document.add(paragrafo);

                            paragrafo = new Paragraph(" ");
                            document.add(paragrafo);

                            paragrafo = new Paragraph(" ");
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Chave da NFe: "+carta.getChNFe());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Evento: "+carta.getxEvento());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Orgão: "+carta.getcOrgao());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Status: "+carta.getcStat());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Motivo: "+carta.getxMotivo());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Data do Evento: "+carta.getDhRegEvento());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Protocolo: "+carta.getnProt());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Correção: "+carta.getxCorrecao());
                            document.add(paragrafo);

                            paragrafo = new Paragraph("Cond.de Uso: "+carta.getxCondUso());
                            document.add(paragrafo);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao tentar gerar a Impressao da Carta de Correcao");
                        }
                    }
                    finally {
                        if (document != null) {
                            document.close();
                        }
                        if (outPutStream != null) {
                            outPutStream.close();
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Arquivo gerado:   "+arqTXTCarta);
                }
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar Gerar o Arq. TXT da Carta de Correcao. Erro: "+ee);
                }
            }

        }
    }
    private File escolheArqCarta(){
        String caminho_escolha = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"retcorri"+fsep+"Bkp"+fsep+anomesdia;
        String arquivo = null, nomeArqXml = "";
        File[] arquivos = escolhe_arquivo(true, caminho_escolha, JFileChooser.FILES_ONLY, "xml");
        File arqXMLCarta = null;
        if (arquivos != null ){
            for (int i=0;i<arquivos.length;i++){
                arquivo = arquivos[i].getAbsolutePath();
                arqXMLCarta = arquivos[i];
                nomeArqXml = arquivos[i].getName();
            }
        }
        return arqXMLCarta;
    }
    public File[] escolhe_arquivo(boolean multiplo, String caminho, int tipo_arq, String ext_arq){
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
            JFileChooser fc_escolha   = new JFileChooser(caminho);
            fc_escolha.setDialogTitle("Escolha o Arquivo");
            fc_escolha.setApproveButtonText("Confirma");
            fc_escolha.setFileSelectionMode(tipo_arq);
            if (ext_arq != null) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos "+ext_arq, ext_arq.toLowerCase(), ext_arq.toUpperCase());
                fc_escolha.setFileFilter(filter);
            }
            fc_escolha.setBounds(10, 30, 300,350);
            fc_escolha.setAutoscrolls(true);
            fc_escolha.setMultiSelectionEnabled(multiplo);
            int returnVal = fc_escolha.showOpenDialog(getContentPane());
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File[] f_result = fc_escolha.getSelectedFiles();
                return f_result;
            } else {
                return null;
            }
    }
    public void imprimeDanfeJava(){
       boolean imprimir = true;
       reimprime = false;
       char tipoImp = 'I';
//       if (arquivoOrigem == null) {
       UIManager.put("OptionPane.yesButtonText", "Impressão");
       UIManager.put("OptionPane.noButtonText", "Re-impressão");
       UIManager.put("OptionPane.cancelButtonText", "Desiste");
       int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Impressão da Danfe", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
       if (escolha == JOptionPane.CANCEL_OPTION) {
           JOptionPane.showMessageDialog(null,"Voce desistiu de Imprimir a Danfe!");
        } else {
           if (escolha == JOptionPane.NO_OPTION) {
                tipoImp = Biblioteca.tipoImpressao();
                File[] arquivos = escolhe_arquivo(true, getCaminho_grava_impDanfe()+"\\bkp\\"+anomesdia, panel_envia,JFileChooser.FILES_ONLY, "xml");
                if (arquivos != null ){
                    reimprime = true;
                    for (int i=0;i<arquivos.length;i++){
                        String arquivo = arquivos[i].getAbsolutePath();
                        String nome_arq= arquivos[i].getName();
                        String modeloNFe = Biblioteca.extrair_TAG(arquivo, "ide", "T", "mod", 1);
                        if (modeloNFe != null && modeloNFe.equals("55")) {
                            //JOptionPane.showMessageDialog(null,"Arquivo escolhido: " + arquivo);  //getSelectedFile().getName());
                            System.out.println("Arquivo escolhido: " + arquivo);  //getSelectedFile().getName());
                            File arqDanfe    = new File(arquivo);
                            if (arqDanfe.isFile()){
                                char chartxt[]  = new char[(int)arqDanfe.length()];
                                String arq_saida = getCaminho_grava_impDanfe()+"\\"+nome_arq;
                                System.out.println("Gravando o arquivo para impressao: "+arq_saida);
                                FileReader entrada;
                                try {
                                    entrada = new FileReader(arquivo);
                                    entrada.read(chartxt);  //(res);
                                    entrada.close();

                                    String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                                    byte[] texto_byte = texto_string.getBytes();
                                    FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida);
                                    arquivo_gerado.write(texto_byte);
                                    arquivo_gerado.close();

                                } catch (Exception ex) {
                                    System.out.println("Erro ao tentar gravar o arq.para Impressão: "+ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Este arquivo: " + nome_arq + " não é uma NFe modelo 55 e a DANFE nãp pode ser impressa!");
                            imprimir = false;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum arquivo foi escolhido para re-impressão da Danfe!");
                    imprimir = false;
                }
           }
           UIManager.put("OptionPane.yesButtonText", "Sim");
           UIManager.put("OptionPane.noButtonText", "Não");
           
           if (imprimir){
               try {
                File arqConfig = new File(drive+"/dados/impDanfe.cfg");
                String texto_string = "<pasta_impdanfe>"+drive+"\\DADOS\\EMPR"+emp4dig+"\\IMPDANFE"+"</pasta_impdanfe>";  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(arqConfig);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();
               } catch(Exception ex){
                   JOptionPane.showMessageDialog(null, "Erro ao tentar gravar o arquivo: "+drive+"/impDanfe.cnf\nErro: "+ex);
               }
//inicio da nova rotina
                File lista_de_arquivos = new File(getCaminho_grava_impDanfe());
                String arquivos[]   = lista_de_arquivos.list();
//                String nProt = "", dhRecbto = "", duplicata = "", duplicata1 = "";
                Arrays.sort(arquivos);
                for (String s : arquivos) {
                    String arq_xml = getCaminho_grava_impDanfe() + fsep + s;  //"//" + s;
                    impressaoDaDanfe(arq_xml, tipoImp);
                }
                apaga_arquivos_impdanfe("Java");
                if (!reimprime){
                   apaga_arquivos_assinada();
               }
           }
       }
    }
    private void impressaoDaDanfe(String arq_xml, char tipoImp) {
        try {
            File arqDanfe    = new File(arq_xml);
            if (arqDanfe.isFile()){
                String nProt = "", dhRecbto = "", duplicata = "", duplicata1 = "";
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
                    chNfe = Biblioteca.extrair_TAG(arq_xml, "infNFe", "A", "Id", 1);
                    chNfe = chNfe.substring(3, chNfe.length());
                    HashMap<String, String> parameterMap = new HashMap<String, String>( );
                    //le o arq. da pasta NfeProt para capturar os dados do protocolo de autorizacao
                    String arq_prot_xml = getCaminho_nfeprot()+"\\"+dEmi+"\\"+chNfe+"_prot.xml";  //+s.substring(0,7)+"P.xml";
                        nProt = Biblioteca.extrair_TAG(arq_prot_xml, "infProt", "T", "nProt", 1);
                        dhRecbto = Biblioteca.extrair_TAG(arq_prot_xml, "infProt", "T", "dhRecbto", 1);
                    //atribui o arq.xml para a impressao da Danfe
                    JRXmlDataSource xmlDataSource = new JRXmlDataSource(new File(arq_xml), "/enviNFe/NFe/infNFe/det"); //path+"produtos.xml"), "/produtos/produto");

                    //o Nome do parâmetro e o valor é passado ao HashMap
                    String pastaImg = drive + fsep + "DADOS" + fsep + "EMPR" + emp4dig + fsep;  //drive+fsep+"DADOS"+fsep+"EMPR"+emp4dig+fsep+"relatorio"+fsep;
                    parameterMap.put("pastaImg", pastaImg);
                    //atribui os valores dos parametros de protocolo
                    parameterMap.put("nProt", nProt);
                    parameterMap.put("dhRecbto", dhRecbto);
                    parameterMap.put("duplicata", duplicata);
                    parameterMap.put("duplicata1", duplicata1);
                    parameterMap.put("contatoCupom", contatoCupom);
                    if (reimprime) {
                        parameterMap.put("ObsReimp", "REEMISSÃO");
                    }
                    String arquivo = System.getProperty("user.dir")+fsep+"Relatorios"+fsep+"Danfe.jasper";

                    ta_en_envia.append("\nImprimindo Danfe ref. NFe: "+arq_xml);
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

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Impressão da Danfe - Erro: "+ e);
        }
    }
    public void enviaEmailXml(boolean automatico, String arq_xml){
       boolean enviar = true;
       String txt_inicio = "", txt_nfe = "";
       if (!automatico) { //  foi pressionado o botao para enviar email avulso
            File[] arquivos = escolhe_arquivo(true, getCaminho_nfeprot()+"\\"+anomesdia, panel_envia,JFileChooser.FILES_ONLY, "xml");
            if (arquivos != null ){
                for (int i=0;i<arquivos.length;i++){
                    arq_xml = arquivos[i].getAbsolutePath();
                    trataXmlParaEmail(arq_xml, automatico);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum arquivo foi escolhido para enviar por email!");
                enviar = false;
            }
       } else {  //  acabou de autorizar a NFe e vai enviar email do XML para o cliente
            trataXmlParaEmail(arq_xml, automatico);
       }
    }
    
    private void trataXmlParaEmail(String arq_xml, boolean automatico) {
            try {
                String cnpj_cliente = Biblioteca.extrair_TAG(arq_xml, "dest", "T", "CPF", 1);
                if (cnpj_cliente == null || cnpj_cliente.equals("")) {
                    cnpj_cliente = Biblioteca.extrair_TAG(arq_xml, "dest", "T", "CNPJ", 1);
                }
                String email_cliente = Biblioteca.extrair_TAG(arq_xml, "dest", "T", "email", 1);
                String numNfe       = Biblioteca.extrair_TAG(arq_xml, "ide", "T", "nNF", 1);
//JOptionPane.showMessageDialog(null, "Cnpj do cliente: "+cnpj_cliente+" - email cliente: "+email_cliente+" - num.NFe: "+numNfe);
                
                int codcliente = 0;
                //if (email_cliente != null && !email_cliente.equals("")) {
                    String retorno = pegaEmailCliente(cnpj_cliente, codcliente, arq_xml, numNfe, email_cliente, automatico);
                //}
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro no tratam.arquivo para email: "+e);
            }
        
    }

    private String pegaEmailCliente(String cnpj, int codcliente, String arq_xml, String numNfe, String email_cliente, boolean automatico){
//System.out.println("Entrou metodo nfefacil.pegaEmailCliente() . . .");        
        String cnpj_cpf;
        if (cnpj.length() == 14) { //  cnpj
            cnpj_cpf = cnpj.substring(0,2)+"."+cnpj.substring(2,5)+"."+cnpj.substring(5,8)+"/"+cnpj.substring(8,12)+"-"+cnpj.substring(12,14);
        } else {  //  cpf
            cnpj_cpf = cnpj.substring(0,3)+"."+cnpj.substring(3,6)+"."+cnpj.substring(6,9)+"-"+cnpj.substring(9,11);
        }
        if (email_cliente == null) email_cliente = "";
        if (!automatico){
            //JOptionPane.showMessageDialog(null, "Nao foi encontrado endereco de email para este cliente!!");
            email_cliente = confirmaEmail(email_cliente);
        }
        if (email_cliente.length() >= 8){
            boolean sucesso = enviar_email(empresa, arq_xml, "Nfe"+numNfe+".xml", email_cliente, false, 0);
            if ( sucesso && !automatico) {
                //JOptionPane.showMessageDialog(null, "Email enviado com sucesso!");
            }
        }
        return email_cliente;
    }
    public String confirmaEmail(String email_cliente){
        while (true){
            email_cliente = JOptionPane.showInputDialog("Confirma email do cliente", email_cliente);
            //System.out.println("Data do Movimento["+dataProc+"]");
            break;
        }
        if (email_cliente == null) {
            email_cliente = "";
        }
        return email_cliente;
    }
    public String digitaCorrecao(){
        while (true){
            setCorrecao(JOptionPane.showInputDialog("Informe a Correcao para a NFe", getCorrecao()));
            break;
        }
        if (getCorrecao() == null) {
            setCorrecao("");
        }
        return getCorrecao(); 
    }
   private String formataCpfCnpj(String cnpj_cpf) {
       String result = "";
       if (cnpj_cpf.length() == 11) { //  cpf
           result = cnpj_cpf.substring(0,3)+"."+cnpj_cpf.substring(3,6)+"."+cnpj_cpf.substring(6,9)+"-"+cnpj_cpf.substring(9,11);
       } else if (cnpj_cpf.length() == 14) { //  cnpj
           result = cnpj_cpf.substring(0,2)+"."+cnpj_cpf.substring(2,5)+"."+cnpj_cpf.substring(5,8)+"/"+cnpj_cpf.substring(8,12)+"-"+cnpj_cpf.substring(12,14);
       }
       return result;
   }
    public boolean check_status_servico(String origem){
        String msg_stat = "";
        boolean retorno = false;
        int qtCheck = 0;
        while(true) {
            if (Biblioteca.check_certificados(getFile_keystore(), getFile_truststore())) {
                StatusServico statServ = new StatusServico(
                    getFile_keystore(), 
                    getSenha_keystore(), 
                    getTipoAmbiente(), 
                    Biblioteca.pegaEstado(cUF)
                );
                if (wmodeloNFe.equals("65")) {
                    resultado = statServ.processar(ConstantesUtil.NFCE);
                } else {
                    resultado = statServ.processar(ConstantesUtil.NFE);
                }
                if (resultado.indexOf("-")!= -1){
                    status = resultado.substring(0,resultado.indexOf("-"));
                    setMotivo(resultado.substring(resultado.indexOf("-")+1,resultado.length()));
                }
                if ( status.equals("107") ){
                    lb_status.setForeground(new java.awt.Color(51, 204,0));   //Color.green);
                    lb_statFat.setForeground(new java.awt.Color(51, 204,0));
                    
                    msg_stat = getMotivo() + "(" +qtCheck + ")";
                    lb_statFat.setText(getMotivo());
                    retorno = true;
                }else{
                    if (status.equals("999")) {  //  999-Erro não catalogado
                        try {
                            Thread.sleep(2000);   // aguarda 2 s para tentar novamente
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lb_status.setText("A g ua r d e . . .");
                        qtCheck++;
                        System.out.println("999 - Erro não Catalogado! - QtCheck: " + qtCheck);
                        if (qtCheck == 10) {  //  atingiu o numero maximo de tentativas
                            msg_stat = status+" - "+getMotivo();
                            lb_statFat.setText(getMotivo());
                            retorno = false;
                            break;
                        }  
                    } else {
                        lb_status.setForeground(Color.red);
                        lb_status.setForeground(Color.red);
                        if (status.equals("")){
                            msg_stat = "Não conseguiu verificar Status do Serviço . . .";
                            lb_statFat.setText("Sem Serviço");
                            retorno = false;
                        }else{
                            msg_stat = status+" - "+getMotivo();
                            lb_statFat.setText(getMotivo());
                            //if (motivo.indexOf("em opera") == -1)
                            //    JOptionPane.showMessageDialog(null, "Status do Servico: "+status+" - Motivo: "+motivo);
                        }
                        break;
                    }
                }

            }else {
                lb_status.setForeground(Color.red);
                msg_stat = "Não conseguiu verificar Status do Serviço . . .";
                break;
            }
            if (!retorno && !origem.equals("login")) {
                JOptionPane.showMessageDialog(null,"Serviço fora de Operação!");
            }
            if (origem.equals("consulta")) {
                exibeTextonoTextArea(resultado, ta_pc_consulta, "consultaStat");
                ta_pc_consulta.append("\n\n . . .FIM DA CONSULTA DO STATUS DE SERVICO . . .\n");
            }
            int tamtxt = msg_stat.length();
            int i = 0;
            for ( i=0; i<(30-tamtxt);i++) {msg_stat = " "+msg_stat;}
            lb_status.setText(msg_stat.trim());
            if (retorno) break;
        }
        
        return retorno;
    }
    public void tratar_cancelaNfe(String arq_cancela) {
        ta_en_envia.setText("");  //removeAll();
        ta_en_envia.append("Aguarde . . .Transmitindo Cancelamento da Nfe: "+chNfe+"\n\n");
        chNfe = Biblioteca.extrair_TAG(arq_cancela, "infNFe", "A", "Id", 1);
        boolean continua = true;
        if (chNfe != null && chNfe.length() >40){
            chNfe = chNfe.substring(3,chNfe.length());
            String modNFe = Biblioteca.extrair_TAG(arq_cancela, "ide", "T", "mod", 1);
            String tipoNFe = "";
            if (modNFe.equals("55")) {
               tipoNFe = ConstantesUtil.NFE; 
            } else {
               tipoNFe = ConstantesUtil.NFCE; 
            }
            String nProt = Biblioteca.extrair_TAG(arq_cancela, "infProt", "T", "nProt", 1); 
            int numLote = pegaNumLote();
            if (nProt != null){

            CancelarNFe canc = new CancelarNFe(
                    getFile_keystore(), 
                    getSenha_keystore(), 
                    getTipoAmbiente(), 
                    Biblioteca.pegaEstado(cUF)
                );
           resultado = canc.processar(
                    cUF, 
                    cnpj,
                    chNfe, 
                    ""+numLote,
                    nProt,
                    "NFe cancelada pelo emissor",
                    tipoNFe,
                    fuso_horario
                );                
                if (!resultado.equals("erro")){
                    exibeTextonoTextArea(resultado, ta_en_envia, "cancelaNfe");
                    if (pasta_grava_retcanc.equals("")){
                        pasta_grava_retcanc = escolhe_pasta(caminho_grava_retcanc, panel_envia);
                    }
                    if (!pasta_grava_retcanc.equals("")){
                        String nome_arquivo = pasta_grava_retcanc+"\\"+chNfe+".txt";
                        gravar_retorno(nome_arquivo, resultado, ta_en_envia);
                        ta_en_envia.append("\n\n Retorno do Cancelamento: "+resultado.substring(resultado.indexOf("-")+1, resultado.length())+"\n");
                        ta_en_envia.append("\n\n . . .FIM DA TRANSMISSAO DE CANCELAMENTO DA NFe ESCOLHIDA . . .\n");
                        if (atualizaNFeCancelada(chNfe)) {
                            JOptionPane.showMessageDialog(null,"Atualizou os dados de Cancelamento no Banco de Dados com Sucesso!", "Cancelamento de NFe", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,"Falhou ao tentar atualizar os dados de Cancelamento no Banco de Dados!", "Cancelamento de NFe", JOptionPane.ERROR_MESSAGE);
                        }
                        //pasta_grava_retcanc = "";
                    } else {
                        ta_en_envia.append("\n\n . . . PROCESSO DE TRANSMISSAO DE CANCELAMENTO DE NFes FOI ABORTADO . . .\n");
                        JOptionPane.showMessageDialog(null,"PROCESSO DE TRANSMISSAO DE CANCELAMENTO DE NFes FOI ABORTADO", "Cancelamento de NFe", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Ocorreu algum erro durante a Transmissão!");
                    ta_en_envia.setText("");
                }
            } else {
                continua = false;
            }
        } else {
            continua = false;
        }
        if (!continua) {
            JOptionPane.showMessageDialog(null,"O Processo de Cancelamento da NFe foi cancelado pelo Uusuario!");
        }
        //ta_en_envia.setText("");

    }
    private boolean atualizaNFeCancelada(String chNfe) {
        boolean retorno = true;
        try {
            Date data_canc = new Date();
            String sql_nf = "update nf set "
                            + "data_cancelamento = '"+data_canc+"' "
                            + "where numero_nfe = "+Integer.parseInt(chNfe.substring(28,37));
            st_fat = confat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int result = st_fat.executeUpdate(sql_nf);
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar atualizar na tabela: nf. Erro: "+e);
        }

        return retorno;
    }
    public void tratar_corrigeNfe(String arq_corrige) {
        ta_en_envia.setText("");  //removeAll();
        ta_en_envia.append("Aguarde . . .Transmitindo Correcao da Nfe: "+chNfe+"\n\n");
        chNfe = Biblioteca.extrair_TAG(arq_corrige, "infNFe", "A", "Id", 1);
        chNfe = chNfe.substring(3, chNfe.length());
        boolean continua = true;
        if (chNfe != null){
                setCorrecao(digitaCorrecao());
                if (getCorrecao().equals("")) {
                    continua = false;
                } else {
                CartaCorrecao carta = new CartaCorrecao(
                        getFile_keystore(), 
                        getSenha_keystore(), 
                        getTipoAmbiente(), 
                        Biblioteca.pegaEstado(cUF)
                    );
               resultado = carta.processar(
                        cUF, 
                        cnpj,
                        chNfe, 
                        getCorrecao(),
                        caminho_grava_corr+"\\"+anomesdia,
                        caminho_grava_retcorr+"\\"+anomesdia,
                        fuso_horario
                    );                
                    if (!resultado.equals("erro")){
                        exibeTextonoTextArea(resultado, ta_en_envia, "corrigeNfe");
                        if (pasta_grava_retcorr.equals("")){
                            pasta_grava_retcorr = escolhe_pasta(caminho_grava_retcorr, panel_envia);
                        }
                        if (!pasta_grava_retcorr.equals("")){
                            ta_en_envia.append("\n\n Retorno da Correcao: "+resultado+"\n");  //.substring(resultado.indexOf("-")+3, resultado.length())+"\n");
                            ta_en_envia.append("\n\n . . .FIM DA TRANSMISSAO DE CORRECAO DA(S) NFeS ESCOLHIDAS . . .\n");
                        } else {
                            ta_en_envia.append("\n\n . . . PROCESSO DE TRANSMISSAO DE CORRECAO DE NFes FOI ABORTADO . . .\n");
                            JOptionPane.showMessageDialog(null,"PROCESSO DE TRANSMISSAO DE CORRECAO DE NFes FOI ABORTADO");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Ocorreu algum erro durante a Transmissão!");
                        ta_en_envia.setText("");
                    }
                }
        } else {
            continua = false;
        }
        if (!continua) {
            JOptionPane.showMessageDialog(null,"O Processo de Correcao da NFe foi cancelado pelo Uusuario!");
        }

    }
    public void processa_inutilizaNfe(String modelo, String serie, String numnfini, String numnffim) {
        //JOptionPane.showMessageDialog(null, "Chave NFe: "+chNfe);
        ta_pc_consulta.setText("");  //removeAll();
        ta_pc_consulta.append("Aguarde . . .Transmitindo Inutilizacao das Nfes: "+numnfini+" a "+numnffim+"\n\n");
        //Adiciona 0 antes da serie
        String serie3 = serie;
        for (int i=serie.length(); i<3; i++) {
            serie3 = "0"+serie3;
        }

        serie.replaceAll("0", "");

        String nfini9 = numnfini;
        String nffim9 = numnffim;
        for (int i=(9-numnfini.length());i>0;i--){
            nfini9 = "0"+nfini9;
        }
         for (int i=(9-numnffim.length());i>0;i--){
            nffim9   = "0"+nffim9;
        }
         
         numnfini.replaceAll("0", "");
         numnffim.replaceAll("0", "");
        String Id = "ID" + cUF + ano + cnpj + modelo + serie3 + nfini9+ nffim9;
            InutilizarNFe inut = new InutilizarNFe(
                    getFile_keystore(), 
                    getSenha_keystore(), 
                    getTipoAmbiente(), 
                    Biblioteca.pegaEstado(cUF)
                );
            resultado = inut.processar(
                cUF, 
                cnpj,
                ano,
                Id,
                modelo,
                serie,
                numnfini,
                numnffim,
                "NFs nao utilizadas pelo emissor"
            );
            if (!resultado.equals("erro")){
                exibeTextonoTextArea(resultado, ta_en_envia,  "inutilizaNfe");
                if (pasta_grava_retinut.equals("")){
                    pasta_grava_retinut = escolhe_pasta(caminho_grava_retinut, panel_envia);
                }
                if (!pasta_grava_retinut.equals("")){
                     String nome_arquivo = pasta_grava_retinut+"\\"+tf_en_nfinut_ini.getText()+"-"+tf_en_nfinut_fim.getText()+"_inut.txt";
                    gravar_retorno(nome_arquivo, resultado, ta_en_envia);
                    ta_en_envia.append("\n\n . . .FIM DA TRANSMISSAO DE INUTILIZACAO DA(S) NFeS ESCOLHIDAS . . .\n");
                } else {
                    ta_en_envia.append("\n\n . . . PROCESSO DE TRANSMISSAO DE INUTILIZACAO DE NFes FOI ABORTADO . . .\n");
                    JOptionPane.showMessageDialog(null,"PROCESSO DE TRANSMISSAO DE INUTILIZACAO DE NFes FOI ABORTADO");
                }
                tf_en_nfinut_ini.setText("");
                tf_en_nfinut_fim.setText("");
                //ta_en_envia.setText("");
            }else{
                JOptionPane.showMessageDialog(null,"Ocorreu algum erro durante a Transmissão!");
                ta_en_envia.setText("");
            }
    }
    public void gravar_retorno(String nome_arquivo, String texto_retornado, TextArea text_area){
        try
        {
             byte [] texto = texto_retornado.getBytes();
             FileOutputStream arquivo_gerado = new FileOutputStream(nome_arquivo);
             arquivo_gerado.write(texto);
             arquivo_gerado.close();
        }
        catch(IOException erro_arquivo)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível gravar esse arquivo: "+erro_arquivo);
        }

    }
    public File[] escolhe_arquivo(boolean multiplo, String caminho, JPanel painel, int tipo_arq, String ext_arq){
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_login);
            fc_escolha   = new JFileChooser(caminho);
            fc_escolha.setDialogTitle("Escolha o Arquivo");
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
            int returnVal = fc_escolha.showOpenDialog(painel);
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_login);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File[] f_result = fc_escolha.getSelectedFiles();
                return f_result;
            } else {
                return null;
            }
    }
    public String escolhe_pasta(String caminho, JPanel painel){
            String retorno = "";
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_login);
            fc_escolha   = new JFileChooser(caminho);
            fc_escolha.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc_escolha.setBounds(10, 30, 300,350);
            fc_escolha.setAutoscrolls(true);
            fc_escolha.setDialogTitle("Escolha a Pasta para Gravar o Arquivo");
            fc_escolha.setApproveButtonText("Confirma");
            int returnVal = fc_escolha.showOpenDialog(painel);
            //  ativa lookandfeel Liquid apos exibir FileChooser
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(panel_login);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc_escolha.getSelectedFile().getPath();
                System.out.println("Pasta escolhida: " + path);
                retorno = path;
             } else {
                retorno = "";
             }
            return retorno;
    }
    public void exibeTextonoTextArea(String texto, TextArea textarea, String tipo_retorno){
        try {
            boolean extraiu = false;
            String texto_res = "", texto_linha = "", tracos = "", txt_stat = "", txt_xmot = "", txt_nfe = "", txt_dhrec = " ", txt_recep = "", txt_final = "";
            while (texto.indexOf("><")>0){
                if (!txt_xmot.equals("")) {
                    txt_final = txt_final+"\n"+txt_nfe+" - "+txt_stat+" - "+txt_xmot+" - "+txt_dhrec+" - "+txt_recep;
                    txt_stat = "";
                    txt_xmot = "";
                    txt_nfe = "";
                    txt_dhrec = " ";
                    txt_recep = "";
                }
                texto_linha = texto.substring(0, texto.indexOf("><")+1);
                if (texto_linha.indexOf("cStat")!= -1 ) {
                    txt_stat    = texto_linha.substring(7, texto_linha.indexOf("/cStat")-1);
                }//  texto_linha.length()-8);  //texto_res = texto_res+texto_linha;
                else if (texto_linha.indexOf("xMotivo")!= -1 )  {
                    txt_xmot    = texto_linha.substring(9, texto_linha.indexOf("/xMotivo")-1).toUpperCase();
                    if (txt_stat.indexOf("135") != -1) {  //  cancelamento homologado por evento
                       txt_xmot += " * * *   CORREÇÃO / CANCELAMENTO HOMOLOGADO COM SUCESSO!!!    * * *";
                    }
                } //  texto_linha.length()-10).toUpperCase();
                else if (texto_linha.indexOf("chNFe")!= -1 )    txt_nfe     = texto_linha.substring(32,41);  //texto_res = texto_res+"> > > "+texto_linha+" < < < < <"+"\n";
                else if (texto_linha.indexOf("dhRecbto")!= -1 ) txt_dhrec   = texto_linha.substring(10, texto_linha.indexOf("/dhRecbto")-1);//  texto_linha.length()-11);  //texto_res = texto_res+"> > > "+texto_linha+" < < < < <"+"\n";
                texto = texto.substring(texto.indexOf("><")+1, texto.length());
                extraiu = true;
            }
            if (texto.length()>0) texto_res = texto; 
            if (!extraiu) txt_final = "\n\t\t"+texto+"\n\n";
            textarea.append(txt_final);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro no metodo [exibeTextonoTextArea()] : "+e);
        }
    }
    public boolean processa_enviaLoteNfe(){
        //panel_envia.removeAll();
        boolean retorno = true;
        File arquivos[] = null;
        String pasta_ler_assinar = caminho_ler_assinar;  //  escolhe_pasta(caminho_ler_assinar, panel_envia);
        UIManager.put("OptionPane.yesButtonText", "Escolher a NFe");
        UIManager.put("OptionPane.noButtonText", "Desistir");
        int escolha = JOptionPane.showConfirmDialog(null, "Escolha a Opção Desejada", "Captura de Arquivos para Transmissão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        if ( escolha == JOptionPane.YES_OPTION){
             arquivos = escolhe_arquivo(true, pasta_ler_assinar, panel_envia,JFileChooser.FILES_ONLY, "xml");
        } else {
            retorno = false;
        }
        if (arquivos != null ){
            ta_en_envia.setText("");
            //panel_envia.repaint();
            chNfe       =  "";
            qtdNfe      = 0;
            idx         = 0;
            String caminho_escolhido_assinar = "";  
            for (int i=0;i<arquivos.length;i++){
                    caminho_escolhido_assinar =  arquivos[i].getParent();
                    String arquivo = arquivos[i].getAbsolutePath();
                    boolean ja_leu_arq = false;
                    for (int ia=0;ia<idx;ia++){
                        if (arqs_assinar[ia] != null){
                            if (arqs_assinar[ia].equals(arquivos[i].getName())){
                                ja_leu_arq = true;
                                break;
                            }
                        }
                    }
                    if (!ja_leu_arq ){
                        if ( tpEmis.equals(TPEMIS_NORMAL) || tpEmis.equals(TPEMIS_EVENTO)) {
                            if (tratar_assinaturaNfe(arquivo)){
                                arqs_assinar[idx] = arquivos[i].getName();
                                idx++;
                                qtdNfe++;
                            }
                        } else {  //  tpEmis.equals(TPEMIS_DPEC)
                            JOptionPane.showMessageDialog(null, "Para transmitir o arquivo é necessário primeiro clicar em [Normal] no painel inicial.");
                        }
                    }
            }
            //  apagando os arquivos da pasta assinar
            apaga_arquivos_temporarios();  //(caminho, getCaminho_grava_assinar());
            ta_en_envia.append("\n.\n.\n . . .FIM DO ENVIO DO LOTE DAS NFeS ESCOLHIDAS . . .\n.\n.\n");
            //pasta_grava_retlote = "";
            //processa_sitperiodonfe(anomesdia, anomesdia, "rejeit", ta_en_envia);
         } else {
            if (!retorno) {
                ta_en_envia.append("\n.\n   Você Desistiu de Escolher a NFe para Transmitir.\n.\n");
                JOptionPane.showMessageDialog(null," Você Desistiu de Escolher a NFe para Transmitir.\nPROCESSO DE ENVIO DE LOTE FOI ABORTADO");
            } else {
                ta_en_envia.append("\n.\n   Não encontrou nenhuma NFe para Transmitir em: "+pasta_ler_assinar+"\n.\n");
                JOptionPane.showMessageDialog(null,"Não encontrou nenhuma NFe para Transmitir em: \n"+pasta_ler_assinar+"\nPROCESSO DE ENVIO DE LOTE FOI ABORTADO");
            }
            ta_en_envia.append("\n.\n.\n . . . PROCESSO DE ENVIO DE LOTE FOI ABORTADO . . .\n.\n.\n");
            //pasta_grava_retlote = "";
         }
        return retorno;

    }
    public void vaiTratarEnviaDPEC(String caminho_escolhido_dpec){
        int numLote = pegaNumLote(), idxr = 0;
        String[] nrecibo = new String[30];
        nrecibo = tratar_enviaLoteDPEC(numLote);
        if (nrecibo[0] != null && !nrecibo[0].equals("erro")){
            if (ultimo_lote.equals("")) ultimo_lote = ""+numLote;
            for (int numeroLote = numLote;numeroLote<=Integer.parseInt(ultimo_lote);numeroLote++){
            try {
                ta_en_envia.append("\n . . . Consultando o Lote: "+nrecibo[idxr]+"\n");
                Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
                }
                idxr++;
            }
            String caminho = caminho_escolhido_dpec.substring(0, getCaminho_grava_assinar().indexOf("BKPNFE")-1);
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum problema durante Geracao do DPEC para a RFB! Tente transmitir novamente mais tarde!");
        }
        apaga_arquivos_assinada();

    }
    public boolean tratar_assinaturaNfe(String arquivoNfe){
        boolean ret_assinar = false;
        ta_en_envia.append("Assinando a ["+(idx+1)+".a] NFe: "+arquivoNfe+"\n");
        if (getPasta_grava_assinada().equals("")){
            setPasta_grava_assinada(escolhe_pasta(caminho_grava_assinada, panel_envia));
        }
        chNfe = Biblioteca.extrair_TAG(arquivoNfe, "infNFe", "A", "Id", 1);
        chNfe = chNfe.substring(3, chNfe.length());
        String arq_assinado = getCaminho_assinatura()+"\\"+chNfe+"_sign.xml";
        String arq_prot     = getCaminho_nfeprot()+"\\"+anomesdia+"\\"+chNfe+"_prot.xml"; 
        String arq_danfe    = getCaminho_grava_impDanfe()+"\\"+chNfe+"_danfe.xml";
        try {
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
               enviaEmailXml(true, arq_prot);
                char tipoImp = 'I';
                if (tipoAmbiente.equals("2")) {
                   tipoImp = Biblioteca.tipoImpressao();
                }
                impressaoDaDanfe(arq_danfe, tipoImp);
                apaga_arquivos_assinada();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar assinar/gravar nfe: "+ ex);
            ta_en_envia.append("Erro ao tentar assinar/gravar nfe: "+ ex);
        }
        return ret_assinar;
    }
    public boolean tratar_copiardpec(String arquivoNfe){
        boolean ret_copiar = false;
        ta_en_envia.append("Copiando para DPEC a ["+(idx+1)+".a] NFe: "+arquivoNfe+"\n");
        chNfe = Biblioteca.extrair_TAG(arquivoNfe, "infNFe", "A", "Id", 1);
        chNfe = chNfe.substring(3, chNfe.length());
        try {
             copia_arquivo_para_dpec(arquivoNfe);
             ret_copiar = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar copiar o arquivo(): "+ ex);
            ta_en_envia.append("Erro ao tentar copiar o arq: "+arquivoNfe+"\nErro: "+ ex);
        }
        return ret_copiar;
    }
     public int pegaNumLote(){
        int numLote = 0;
        try {
            //st_nfe = con_nfe.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st_nfe = confat.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select * from numerolote where empresa = "+empresa;
            rs_nfe = st_nfe.executeQuery(sql);
            while (rs_nfe.next()) {
                numLote = Integer.parseInt(rs_nfe.getString("numeroproximolote"));
            }

            if (numLote == 0){
               JOptionPane.showMessageDialog(null, "Nao encontrou registro de num.lote para esta empresa! ");
            } else {
                String str_nlote = ""+numLote;
                numLote = Integer.parseInt(str_nlote);  //.substring(4, str_nlote.length()));
                atualiza_numLote(numLote);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler a tabela numerolote: "+ex);
            numLote = 0;
        }
        ta_en_envia.append("\nPegou o numero ["+numLote+"] do Lote para Enviar . . . \n");
        return numLote;
    }
    public String[] tratar_enviaLoteDPEC(int numLote){
        ta_en_envia.append("\nEnviando o Lote: "+numLote+"\n");
        String caminho_leitura_dpec = caminho_grava_dpec.substring(0,caminho_grava_dpec.indexOf("ASSINAR"));
        String[] nrecibo = new String[30];
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (resultado.indexOf("erro") == -1){
            ultimo_lote = "";
            if (resultado.indexOf("</ultimoLote>")>0){
                ultimo_lote = resultado.substring(resultado.indexOf("<ultimoLote>")+12,resultado.indexOf("</ultimoLote>"));
            }
            if (!ultimo_lote.equals("")){
                if (Integer.parseInt(ultimo_lote)>numLote){
                    ta_en_envia.append("Houve mais de uma transmissao e o Num.Lote ["+ultimo_lote+"] será atualizado!");
                    atualiza_numLote(Integer.parseInt(ultimo_lote));
                }
            }
            resultado = resultado.substring(0,resultado.indexOf("<ultimoLote>"));
            exibeTextonoTextArea(resultado, ta_en_envia, "lote");
            if (getPasta_grava_retlote().equals("")){
                setPasta_grava_retlote(escolhe_pasta(caminho_grava_retlote, panel_envia));
            }
            String resultx = resultado, result_lote = "", result_lote_resto = resultado, este_recibo = "";
            int idxr = 0, pos_fim = 0;
            while (resultx.indexOf("<nRegDPEC>") != -1){
                pos_fim = (result_lote_resto.indexOf("</retDPEC>")+10);
                este_recibo = resultx.substring(resultx.indexOf("<nRegDPEC>")+10,resultx.indexOf("</nRegDPEC>"));
                nrecibo[idxr]       = este_recibo;
                result_lote         = result_lote_resto.substring(0, pos_fim);
                if (pos_fim < result_lote_resto.length()){
                    result_lote_resto   = result_lote_resto.substring(pos_fim,result_lote_resto.length());
                    resultx             = resultx.substring(pos_fim,resultx.length());
                }else{
                    result_lote_resto   = "";
                    resultx             = "";
                }
                if (!pasta_grava_retlote.equals("")){
                    String nome_arquivo = getPasta_grava_retlote()+ "\\"+"ret_lote_"+(numLote+idxr)+"_recibo_"+este_recibo+".xml";
                    gravar_retorno(nome_arquivo, result_lote, ta_en_envia);
                }
                else {
               // painel_pai.setSelectedComponent(panel_login);
                }
                idxr++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro durante a transmissao!");
            ta_en_envia.setText("");
            nrecibo[0] = resultado;
        }
        //apaga_arquivos_assinada();
        return nrecibo;
    }
    public void apaga_arquivos_temporarios() {  //(String caminhoOrigem, String caminhoDestino){
            String caminhoOrigem = getCaminho_grava_assinar().substring(0, getCaminho_grava_assinar().indexOf("BKPNFE")-1);
        try {
            String arq_xml;
            File lista_de_arquivos = new File(caminhoOrigem);
            String arquivos[]   = lista_de_arquivos.list();
            Arrays.sort(arquivos);
            for (String s : arquivos) {
                arq_xml         = caminhoOrigem + "\\" + s;
                File arqudel    = new File(arq_xml);
                if (arqudel.isFile()){
                    boolean ja_transm_arq = false;
                    for (int i=0;i<idx;i++){
                        if (arqs_assinar[i] != null){
                            if (arqs_assinar[i].equals(s)){
                                ja_transm_arq = true;
                                break;
                            }
                        }
                    }
                    if (ja_transm_arq){
                        char chartxt[]  = new char[(int)arqudel.length()];
                        String arq_saida_xml = getCaminho_grava_assinar()+"\\"+anomesdia+"\\"+s;
                        FileReader entrada;
                        try {
                            entrada = new FileReader(arq_xml);
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
                            String sAss = s.substring(0, s.indexOf("-nfe"))+"_sign.xml";
                            String arq_xml_ass         = getCaminho_assinatura() + "\\" + sAss;
                            File arqudelAss    = new File(arq_xml_ass);
                            char chartxtAss[]  = new char[(int)arqudelAss.length()];
                            String arq_saida_ass = getCaminho_assinatura()+"\\"+"BKPNFE"+"\\"+anomesdia+"\\"+sAss;
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

                         } catch (Exception ex) {
                            System.out.println("Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
                        }
                    }
                 }
            }
        } catch (Exception ex) {
            System.out.println("Erro no metodo apaga_arquivos_temporarios \nErro: "+ex);
        }
    }
    public void apaga_arquivos_impdanfe(String ling){
        if (!ling.equals("Javax"))
            JOptionPane.showMessageDialog(null, "Favor pressionar [OK] para voltar a Tela Principal!\n");
        String arq_xml;
        File lista_de_arquivos = new File(getCaminho_grava_impDanfe());
        String arquivos[]   = lista_de_arquivos.list();
        Arrays.sort(arquivos);
        for (String s : arquivos) {
            arq_xml         = getCaminho_grava_impDanfe() + "\\" + s;
            File arqudel    = new File(arq_xml);
            if (arqudel.isFile()){
                if (!reimprime) {
                    char chartxt[]  = new char[(int)arqudel.length()];
                    String arq_saida_xml;
                    arq_saida_xml = getCaminho_grava_impDanfe()+"\\BKP\\"+anomesdia+"\\"+s;
                    FileReader entrada;
                    try {
                        entrada = new FileReader(arq_xml);
                        entrada.read(chartxt);  //(res);
                        entrada.close();

                        String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                        byte[] texto_byte = texto_string.getBytes();
                        FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
                        arquivo_gerado.write(texto_byte);
                        arquivo_gerado.close();

                    } catch (Exception ex) {
                        System.out.println("Erro ao processar arq.xml(apaga_arquivos_impdanfe): "+ex);
                    }
                }
                try {
                    arqudel.delete();
                    System.out.println("Apagou o arquivo: "+arqudel);
                } catch (Exception ex) {
                    System.out.println("Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
                }
             }
        }
    }
    public void copia_arquivo_para_dpec(String arq_origem){
        String arq_xml;
            arq_xml         = arq_origem;
            String arqsai = arq_origem.substring(arq_origem.indexOf(".xml")-44, arq_origem.length());
            File arqudel    = new File(arq_xml);
            if (arqudel.isFile()){
                char chartxt[]  = new char[(int)arqudel.length()];
                String arq_saida_xml = caminho_grava_dpec.substring(0,caminho_grava_dpec.indexOf("ASSINAR"))+arqsai;
                FileReader entrada;
                try {
                    entrada = new FileReader(arq_xml);
                    entrada.read(chartxt);  //(res);
                    entrada.close();

                    String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                    byte[] texto_byte = texto_string.getBytes();
                    FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
                    arquivo_gerado.write(texto_byte);
                    arquivo_gerado.close();

                } catch (Exception ex) {
                    System.out.println("Erro ao processar arq.xml(copia_arquivo_para_dpec): "+ex);
                }
                try {
                    arqudel.delete();
                    System.out.println("Apagou o arquivo: "+arqudel);
                } catch (Exception ex) {
                    System.out.println("Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
                }
        }
    }
    public boolean le_configEmail()
    {
          try {
           Email obj_confEmail = new Email();
            FileInputStream arquivo         = new FileInputStream (arqemail);
           ObjectInputStream  obj_dados    = new ObjectInputStream(arquivo);
            Email obj_leitura         = (Email)obj_dados.readObject();
            hostName                        = obj_leitura.getHostName();
            nomeRemet                       = obj_leitura.getNomeRemet();
            emailRemet                      = obj_leitura.getEmailRemet();
            senhaEmail                      = obj_leitura.getSenhaEmail();
            portaEmail                      = obj_leitura.getPortaEmail();
            ssl                             = obj_leitura.isSsl();
            return true;
         } catch (Exception erro_ler) {
             JOptionPane.showMessageDialog(null, "Erro ao ler o arq.de Configuração ["+arqemail+"].\n"+erro_ler);
             return false;
         }
    }
    private boolean enviar_email(int empre, String anexo, String nomeArq, String email_cliente, boolean gravarLog, int codcliente) {
        boolean retorno = false;
        String msg_envio = "";
        le_configEmail();
        if (emailRemet != null && !emailRemet.equals("")) {
            String nomeDest, emailDest, subject, textoEmail;
            nomeDest = "";  //  "Porto Informatica";
            subject = "Arquivo xml: "+nomeArq;  //+" - "+nome_empresa;
            textoEmail = "Segue arquivo XML. Favor confirmar recebimento.";
            String status_envio = "N";
            try {
                String nomeEmail = emailRemet.substring(0, emailRemet.indexOf("@"));
                //JOptionPane.showMessageDialog(null, "Vai enviar email pela porta: "+portaEmail);
                EnviaEmail envia = new EnviaEmail(hostName, nomeRemet, emailRemet, nomeDest, email_cliente, subject, textoEmail, anexo, nomeEmail, senhaEmail, nomeArq, Integer.parseInt(portaEmail), ssl);
                msg_envio = "";  //"Email enviado com sucesso!";
                 status_envio = "N";
                retorno = true;
            } catch (Exception ex) {  //(MalformedURLException ex) {
                status_envio = "E";
                msg_envio = "Erro ao tentar enviar email para o XML [ "+nomeArq+" ] : "+ex;
                JOptionPane.showMessageDialog(null, msg_envio);
                ex.printStackTrace();
                retorno = false;
            }

            gravarLog = false;
            if (gravarLog) {
                try {
                Statement stat_logmax    = consis.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                  String sql_logmax = "select max(codlogemail) as codmax from logemail";
                ResultSet rs_logemail = stat_logmax.executeQuery(sql_logmax);
                 int codlogemail = 0;
                  while(rs_logemail.next()) {
                        codlogemail = rs_logemail.getInt("codmax");
                }
                 codlogemail++;
                String sql_log = "insert into logemail (codlogemail, codcliente, email, arquivo_xml, data_envio, operador, status, msg) values ("
                        +codlogemail
                        +", "
                        +codcliente
                        +", '"
                        +email_cliente
                        +"', '"
                        +nomeArq
                        +"', '"
                        +new Date()
                        +"', '"
                        +tf_usuario.getText()
                        +"', '"
                        +status_envio
                        +"', '"
                        +msg_envio
                        +"'"
                        +")";
                  Statement stat_logemail    = consis.createStatement();
                  int qtins = stat_logemail.executeUpdate(sql_log);
                  if (qtins > 0) {
                  }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar incluir logemail para o XML [ "+nomeArq+" ] : "+ex);
                ex.printStackTrace();
                }
            }
        } else {
           JOptionPane.showMessageDialog(null, "Para enviar email, é preciso configural o email antes!"); 
        }
        System.out.println("Resultado do Envio de email: "+msg_envio);
        return retorno;
    }
    public static String getStc_camAssinat() {
        return stc_camAssinat;
    }

    public static void setStc_camAssinat(String aStc_camAssinat) {
        stc_camAssinat = aStc_camAssinat;
    }

    public static String getStc_fileCert() {
        return stc_fileCert;
    }

    public static void setStc_fileCert(String aStc_fileCert) {
        stc_fileCert = aStc_fileCert;
    }

    public static String getStc_senhaCert() {
        return stc_senhaCert;
    }

    /**
     * @return the ciNFe
     */
    public static CronImportNFe getCiNFe() {
        return ciNFe;
    }

    /**
     * @param aCiNFe the ciNFe to set
     */
    public static void setCiNFe(CronImportNFe aCiNFe) {
        ciNFe = aCiNFe;
    }

    public static void setStc_senhaCert(String aStc_senhaCert) {
        stc_senhaCert = aStc_senhaCert;
    }

    public static String getStc_fileKey() {
        return stc_fileKey;
    }

    public static void setStc_fileKey(String aStc_fileKey) {
        stc_fileKey = aStc_fileKey;
    }

    public static String getCaminho_nfeprot() {
        return caminho_nfeprot;
    }

    public static void setCaminho_nfeprot(String aCaminho_nfeprot) {
        caminho_nfeprot = aCaminho_nfeprot;
    }

    public static String getPasta_grava_assinada() {
        return pasta_grava_assinada;
    }

    public static void setPasta_grava_assinada(String aPasta_grava_assinada) {
        pasta_grava_assinada = aPasta_grava_assinada;
    }

    public static String getCaminho_assinatura() {
        return caminho_assinatura;
    }

    public static void setCaminho_assinatura(String aCaminho_assinatura) {
        caminho_assinatura = aCaminho_assinatura;
    }

    public static String getFile_keystore() {
        return file_keystore;
    }

    public static void setFile_keystore(String aFile_keystore) {
        file_keystore = aFile_keystore;
    }

    public static String getFile_truststore() {
        return file_truststore;
    }

    public static void setFile_truststore(String aFile_truststore) {
        file_truststore = aFile_truststore;
    }

    public static String getSenha_keystore() {
        return senha_keystore;
    }

    public static void setSenha_keystore(String aSenha_keystore) {
        senha_keystore = aSenha_keystore;
    }

    public static String getSenha_truststore() {
        return senha_truststore;
    }

    public static void setSenha_truststore(String aSenha_truststore) {
        senha_truststore = aSenha_truststore;
    }

    public static String getPasta_grava_retlote() {
        return pasta_grava_retlote;
    }

    public static void setPasta_grava_retlote(String aPasta_grava_retlote) {
        pasta_grava_retlote = aPasta_grava_retlote;
    }

    public static String getCaminho_grava_assinar() {
        return caminho_grava_assinar;
    }

    public static void setCaminho_grava_assinar(String aCaminho_grava_assinar) {
        caminho_grava_assinar = aCaminho_grava_assinar;
    }

    public static String getPasta_grava_retrecep() {
        return pasta_grava_retrecep;
    }

    public static void setPasta_grava_retrecep(String aPasta_grava_retrecep) {
        pasta_grava_retrecep = aPasta_grava_retrecep;
    }

    public static String getStc_camImpdanfe() {
        return stc_camImpdanfe;
    }

    public static void setStc_camImpdanfe(String aStc_camImpdanfe) {
        stc_camImpdanfe = aStc_camImpdanfe;
    }
    public static String getStc_data() {
        return stc_data;
    }

    public static void setStc_data(String aStc_data) {
        stc_data = aStc_data;
    }

    public static String getStc_camLerAss() {
        return stc_camLerAss;
    }

    public static void setStc_camLerAss(String aStc_camLerAss) {
        stc_camLerAss = aStc_camLerAss;
    }

    public static String getStc_drive() {
        return stc_drive;
    }

    public static void setStc_drive(String aStc_drive) {
        stc_drive = aStc_drive;
    }

    public static String getStc_anomesdia() {
        return stc_anomesdia;
    }

    public static void setStc_anomesdia(String aStc_anomesdia) {
        stc_anomesdia = aStc_anomesdia;
    }

    public static String getStc_tipoEmis() {
        return stc_tipoEmis;
    }

    public static void setStc_tipoEmis(String aStc_tipoEmis) {
        stc_tipoEmis = aStc_tipoEmis;
    }

    public static boolean isStc_exibirDsp() {
        return stc_exibirDsp;
    }

    public static void setStc_exibirDsp(boolean aStc_exibirDsp) {
        stc_exibirDsp = aStc_exibirDsp;
    }

    public static String getTipoAmbiente() {
        return tipoAmbiente;
    }

    public static void setTipoAmbiente(String aTipoAmbiente) {
        tipoAmbiente = aTipoAmbiente;
    }


    /**
     * @return the ciNFCe
     */
    public static CronImportNFCe getCiNFCe() {
        return ciNFCe;
    }

    /**
     * @param aCiNFCe the ciNFCe to set
     */
    public static void setCiNFCe(CronImportNFCe aCiNFCe) {
        ciNFCe = aCiNFCe;
    }

    /**
     * @return the caminho_conexao
     */
    public static String getCaminho_conexao() {
        return caminho_conexao;
    }

    /**
     * @param aCaminho_conexao the caminho_conexao to set
     */
    public static void setCaminho_conexao(String aCaminho_conexao) {
        caminho_conexao = aCaminho_conexao;
    }

    public static String getCaminho_impdanfe() {
        return caminho_impdanfe;
    }

    public static void setCaminho_impdanfe(String aCaminho_impdanfe) {
        caminho_impdanfe = aCaminho_impdanfe;
    }

     public void verifica_existe_pasta_hoje(){
        check_pasta(pasta_raiz);
        check_pasta(caminho_grava_assinada+"\\"+anomesdia);
        check_pasta(getCaminho_nfeprot()+"\\"+anomesdia);
        check_pasta(getCaminho_grava_assinar()+"\\"+anomesdia);
        caminho_grava_dpec = caminho_ler_assinar.replace("Assinar".toUpperCase(), "DPEC");
        check_pasta(caminho_grava_dpec+"\\"+"ASSINAR");
        caminho_grava_retdpec = getCaminho_grava_assinar().replace("Assinar".toUpperCase(), "RetDPEC");
        check_pasta(caminho_grava_retdpec+"\\"+anomesdia);
        check_pasta(caminho_txt+"\\BKP"+"\\"+anomesdia);
        caminho_lote_dpec = getCaminho_grava_assinar().replace("Assinar".toUpperCase(), "LoteDPEC");
        check_pasta(caminho_lote_dpec+"\\"+anomesdia);
        check_pasta(caminho_consulta+"\\"+anomesdia);
        check_pasta(caminho_grava_retconsu+"\\"+anomesdia);
        check_pasta(caminho_grava_retcanc+"\\"+anomesdia);
        check_pasta(caminho_grava_retcorr+"\\"+anomesdia);
        check_pasta(caminho_grava_retinut+"\\"+anomesdia);
        check_pasta(caminho_grava_retlote+"\\"+anomesdia);
        check_pasta(caminho_grava_retrecep+"\\"+anomesdia);
        check_pasta(caminho_grava_inut+"\\"+anomesdia);
        check_pasta(caminho_grava_canc+"\\"+anomesdia);
        check_pasta(caminho_grava_corr+"\\"+anomesdia);
        check_pasta(getCaminho_grava_impDanfe()+"\\BKP"+"\\"+anomesdia);
        caminho_ibpt = drive+"\\"+caminho_ibpt;
        System.out.println("Caminho ibpt: " + caminho_ibpt);
        check_pasta(caminho_ibpt);
    }
    public void check_pasta(String ver_pasta){
        File pasta = new File(ver_pasta);
        if (!pasta.exists()) {
            try {pasta.mkdirs();}
            catch(Exception e) {JOptionPane.showMessageDialog(null, "Erro ao tentar criar a pasta: "+ver_pasta+"\nErro: "+e);}
        }
    }
    public void atualiza_numLote( int numLote){
        String sql = "update numerolote set numeroproximolote = "+(numLote+1)+" where empresa = "+empresa;
        try {
            int result = st_nfe.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o Num.do Lote. Erro: "+ex);
        }
    }
    public void le_configNfeFacil()
    {
         try
         {
            File arqu_conf = new File(drive+fsep+"configNfeFacil.txt");  //  File(drive+"//configuracoes.txt");
            if (!arqu_conf.exists()){
                inicializa_obj_conf();
                grava_configuracao();
            }
            FileInputStream arquivo = null;
            arquivo = new FileInputStream (drive+fsep+"configNfeFacil.txt");  //  FileInputStream (drive+"//configuracoes.txt");
            ObjectInputStream  obj_dados  = new ObjectInputStream(arquivo);
            configNfeFacil obj_leitura= (configNfeFacil)obj_dados.readObject();
            driver                      = obj_leitura.driver;
            banco                       = obj_leitura.banco;
            setCaminho_conexao(obj_leitura.caminho_conexao);
            setCaminho_nfeprot(obj_leitura.caminho_nfeprot);
            setCaminho_assinatura(obj_leitura.caminho_assinatura);
            caminho_consulta            = obj_leitura.caminho_consulta;
            caminho_ler_assinar         = obj_leitura.caminho_ler_assinar;
            caminho_txt                 = obj_leitura.caminho_txt;
            setCaminho_grava_assinar(obj_leitura.caminho_grava_assinar);
            caminho_grava_assinada      = obj_leitura.caminho_grava_assinada;
            caminho_grava_retconsu      = obj_leitura.caminho_grava_retconsu;
            caminho_grava_retcanc       = obj_leitura.caminho_grava_retcanc;
            caminho_grava_retcorr       = obj_leitura.caminho_grava_retcorr;
            caminho_grava_retinut       = obj_leitura.caminho_grava_retinut;
            caminho_grava_retlote       = obj_leitura.caminho_grava_retlote;
            caminho_grava_retrecep      = obj_leitura.caminho_grava_retrecep;
            caminho_grava_inut          = obj_leitura.caminho_grava_inut;
            caminho_grava_canc          = obj_leitura.caminho_grava_canc;
            caminho_grava_corr          = obj_leitura.caminho_grava_corr;
            setCaminho_grava_impDanfe(obj_leitura.caminho_grava_impDanfe);
            setCaminho_impdanfe(obj_leitura.caminho_grava_impDanfe);
            setFile_keystore(obj_leitura.file_keystore);
            setFile_truststore(obj_leitura.file_truststore);
            senha_config                = obj_leitura.senha_config;
           }
          catch(Exception erro)
          {
             JOptionPane.showMessageDialog(null,"Erro ao tentar ler de configurações." +erro+"\nApague o arquivo: configNfeFacil.txt na pasta: nfe e tente novamente! ");
             System.exit(0);
          }
    }
    public void grava_configuracao(){
          try
          {
             FileOutputStream arquivo = null;  //new FileOutputStream ("c://configNfeFacil.txt");  //  FileOutputStream (drive+"//configuracoes.txt");
             arquivo = new FileOutputStream (drive+fsep+"configNfeFacil.txt");  //  FileInputStream (drive+"//configuracoes.txt");
             ObjectOutputStream  obj_dados  = new ObjectOutputStream(arquivo);
             obj_dados.writeObject(obj_conf);
             obj_dados.flush();
             JOptionPane.showMessageDialog(null,"Parabéns, arquivo de configurações gravado com sucesso");
           }
          catch(Exception erro)
          {
             JOptionPane.showMessageDialog(null,"Erro ao tentar gravar configurações: "+erro);
          }
    }
    private void conectaFatura() {
        con_fatura = new Conexao_fatura();
        XMLReaderFatura xr = new XMLReaderFatura();

        xr.trataXML(getCaminho_conexao()+fsep+"conexaoNFefacil.xml");
        local_fatura        = xr.getY_localsgbd();
        drive_fatura        = xr.getY_drive();
        usuario_fatura      = xr.getY_usuarioBD();
        senha_fatura        = xr.getY_senhaBD();
        driver_fatura       = xr.getY_driver();
        bd_fatura           = xr.getY_nomebd();
        fuso_horario        = xr.getY_fusoHorario();
        contatoCupom        = xr.getY_contatoCupom();
        uriCupom            = xr.getY_uriCupom();
        informarCnpjCartao  = xr.getY_informarCnpjCartao();
        cupomNaTela         = xr.getY_cupomNaTela();
        setInterval(Integer.parseInt(xr.getY_interval()));
//JOptionPane.showMessageDialog(null, "NFefacil interv.Captura; " + xr.getY_interval());        
        confat = con_fatura.conecta(usuario_fatura, senha_fatura, driver_fatura, drive_fatura+"//"+local_fatura+"/"+bd_fatura);
    }
    public void executa_comando_externo(String comando){
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec(comando);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao executar o comando externo: "+ex);
        }

    }
    public void apaga_arquivos_assinada(){
        String arq_xml, lista_del = "";
        File lista_de_arquivos = new File(getCaminho_assinatura());
        String arquivos[] = lista_de_arquivos.list();
        Arrays.sort(arquivos);
        for (String s : arquivos) {
            arq_xml         = getCaminho_assinatura() + "\\" + s;
            try {
            File arqudel    = new File(arq_xml);
            if (arqudel.isFile()){
                arqudel.delete();  //  deleteOnExit();
                lista_del = lista_del + arq_xml + "\n";
             }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar apagar o arquivo: "+arq_xml+"\n"+e);
            }
        }
    }
public static String GetUTCdatetimeAsString()
    {
        Calendar cal = new GregorianCalendar();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        final String utcTime = sdf.format(cal.getTime());
JOptionPane.showMessageDialog(null, "Data do Evento: "+utcTime);

        return utcTime;
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
    void trataXML(){
        String fsep = System.getProperty("file.separator");
        File file = new File(getCaminho_conexao()+fsep+"conexaoNFefacil.xml");  //+"conexaoNFe.xml");
        SAXBuilder builder = new SAXBuilder();
        Document document;

        try{
            document = builder.build(file);
            Element el = document.getRootElement();

            if (!document.getRootElement().getName().equalsIgnoreCase("Conexao")){
                JOptionPane.showMessageDialog(null, "Este Documento nao contem informacoes sobre a Conexao!");
                System.exit(0);
            }
            trataElemento(el);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo conexaoNFefacil.xml. \nErro: "+e);
        }

    }
    private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
        if (tamanho > 0){  //Elemento
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                String campo = el1.getQualifiedName().toString().trim();
                if (campo.equalsIgnoreCase(("driver"))){
                    driver = el1.getText().toString();
                } else
                if (campo.equalsIgnoreCase(("url"))){
                    url = el1.getText().toString();
                } else
                if (campo.equalsIgnoreCase(("drive"))){
                    url = el1.getText().toString();
                } else
                if (campo.equalsIgnoreCase(("nomebd"))){
                    url += "//localhost/"+el1.getText().toString();
                } else
                if (campo.equalsIgnoreCase(("usuarioBD"))){
                    usuario = el1.getText().toString();
                } else
                if (campo.equalsIgnoreCase(("senhaBD"))){
                    senha = el1.getText().toString();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não encontrou o arq.de Config.da Porta: portaDispositivo.xml");
        }
    }


    public void inicializa_caminhos(){
            boolean alterou_cert = false;
            pasta_raiz           = drive+"\\DADOS\\EMPR"+emp4dig+"\\";
            setCaminho_conexao(getCaminho_conexao());
            setCaminho_nfeprot(pasta_raiz + getCaminho_nfeprot() + "\\BKP");
            setCaminho_assinatura(pasta_raiz + getCaminho_assinatura());
            caminho_consulta            = pasta_raiz+caminho_consulta+"\\BKPNFE";
            caminho_ler_assinar         = pasta_raiz+caminho_ler_assinar;
            caminho_txt                 = pasta_raiz+caminho_txt;
            setCaminho_grava_assinar(pasta_raiz + getCaminho_grava_assinar() + "\\BKPNFE");
            caminho_grava_assinada      = pasta_raiz+caminho_grava_assinada+"\\BKPNFE";
            caminho_grava_retconsu      = pasta_raiz+caminho_grava_retconsu+"\\BKP";
            caminho_grava_retcanc       = pasta_raiz+caminho_grava_retcanc+"\\BKP";
            caminho_grava_retcorr       = pasta_raiz+caminho_grava_retcorr+"\\BKP";
            caminho_grava_retinut       = pasta_raiz+caminho_grava_retinut+"\\BKP";
            caminho_grava_retlote       = pasta_raiz+caminho_grava_retlote+"\\BKP";
            caminho_grava_retrecep      = pasta_raiz+caminho_grava_retrecep+"\\BKP";
            caminho_grava_inut          = pasta_raiz+caminho_grava_inut+"\\BKP";
            caminho_grava_canc          = pasta_raiz+caminho_grava_canc+"\\BKP";
            caminho_grava_corr          = pasta_raiz+caminho_grava_corr+"\\BKP";
            setCaminho_grava_impDanfe(pasta_raiz + getCaminho_grava_impDanfe());
            setCaminho_impdanfe(getCaminho_grava_impDanfe());
            setFile_keystore(pasta_raiz + getFile_keystore());
            setFile_truststore(drive + getFile_truststore());
            pasta_grava_consulta        = caminho_grava_retconsu  +"\\"+anomesdia;
            setPasta_grava_assinada(caminho_grava_assinada + "\\" + anomesdia);
            pasta_grava_retinut         = caminho_grava_retinut   +"\\"+anomesdia;
            pasta_grava_retcanc         = caminho_grava_retcanc   +"\\"+anomesdia;
            pasta_grava_retcorr         = caminho_grava_retcorr   +"\\"+anomesdia;
            setPasta_grava_retlote(caminho_grava_retlote + "\\" + anomesdia);
            setPasta_grava_retrecep(caminho_grava_retrecep + "\\" + anomesdia);
            if (senha_token != null && senha_token.trim().length() > 0) {
                setFile_keystore(pasta_raiz + "token.cfg");
                setSenha_keystore(senha_token);
            }
            if ( alterou_cert ) {
                status_servico = check_status_servico("");
            }
    }
    public void inicializa_obj_conf(){
        obj_conf.driver                   = "org.postgresql.Driver";  //  "sun.jdbc.odbc.JdbcOdbcDriver";
        obj_conf.banco                    = "jdbc:postgresql://localhost/portonfe";  //  "jdbc:odbc:portonfe";
        obj_conf.caminho_conexao          = System.getProperty("user.dir");  //"C:";
        obj_conf.caminho_nfeprot          = "NFEPROT";
        obj_conf.caminho_assinatura       = "ASSINADA";
        obj_conf.caminho_consulta         = "ASSINADA";
        obj_conf.caminho_ler_assinar      = "ASSINAR";
        obj_conf.caminho_txt              = "TXT";
        obj_conf.caminho_grava_assinar    = "ASSINAR";
        obj_conf.caminho_grava_assinada   = "ASSINADA";
        obj_conf.caminho_grava_retconsu   = "RETCONSU";
        obj_conf.caminho_grava_retcanc    = "RETCANCE";
        obj_conf.caminho_grava_retcorr    = "RETCORRI";
        obj_conf.caminho_grava_retinut    = "RETINUTI";
        obj_conf.caminho_grava_retlote    = "RETLOTE";
        obj_conf.caminho_grava_retrecep   = "RETRECEP";
        obj_conf.caminho_grava_inut       = "INUTILIZ";
        obj_conf.caminho_grava_canc       = "CANCELA";
        obj_conf.caminho_grava_corr       = "CORRIGE";
        obj_conf.caminho_grava_impDanfe   = "IMPDANFE";
        obj_conf.file_keystore            = "certificado.pfx";
        obj_conf.file_truststore          = "NFeCacerts";  //"certificado.jks";
        obj_conf.senha_config             = "master";
    }
    public String getCorrecao() {
        return correcao;
    }

    public void setCorrecao(String correcao) {
        this.correcao = correcao;
    }

    public static boolean isStatus_fatura() {
        return status_fatura;
    }

    public static void setStatus_fatura(boolean aStatus_fatura) {
        status_fatura = aStatus_fatura;
    }

    public String getCaminho_grava_impDanfe() {
        return caminho_grava_impDanfe;
    }

    public void setCaminho_grava_impDanfe(String caminho_grava_impDanfe) {
        this.caminho_grava_impDanfe = caminho_grava_impDanfe;
    }
    public static String getCodcid() {
        return codcid;
    }

    public static void setCodcid(String aCodcid) {
        codcid = aCodcid;
    }
    private void addMenuFatura() {
        JMenuBar menuBar = new JMenuBar();
        
        // Define e adiciona dois menus drop down na barra de menus
        JMenu cadMenu = new JMenu("  Cadastros  ");
        JMenu movMenu = new JMenu("  Movimentações  ");
        JMenu relMenu = new JMenu("  Relatórios  ");
        JMenu ambMenu = new JMenu("    " + lb_ambFat.getText() + "    ");
        JMenu statMenu = new JMenu("    " + lb_statFat.getText() + "    ");
        cadMenu.setFont(new Font("Arial",Font.PLAIN,18));
        movMenu.setFont(new Font("Arial",Font.PLAIN,18));
        relMenu.setFont(new Font("Arial",Font.PLAIN,18));
        ambMenu.setFont(new Font("Arial",Font.PLAIN,18));
        statMenu.setFont(new Font("Arial",Font.PLAIN,18));
        statMenu.setToolTipText("Status do Serviço");
        cadMenu.setBorderPainted(true);
        if (lb_ambFat.getText().indexOf("HOMOL") != -1) {
            ambMenu.setForeground(Color.red);
        } else {
            ambMenu.setForeground(Color.blue);
        }
        if (lb_statFat.getText().toUpperCase().indexOf("EM OPERA") != -1) {
            statMenu.setForeground(Color.green);
        } else {
            statMenu.setForeground(Color.red);
        }
        menuBar.add(ambMenu);
        menuBar.add(cadMenu);
        menuBar.add(movMenu);
        menuBar.add(relMenu);
        if (wnivel_usuario.toUpperCase().equals("A")) {
            JMenu admMenu = new JMenu("  Administração  ");
            admMenu.setFont(new Font("Arial",Font.PLAIN,18));
            menuBar.add(admMenu);
            if (senha_token != null && senha_token.trim().length() > 0) {
                admMenu.add(admMenu11);
                admMenu.add(admMenu12);
                admMenu.add(admMenu13);
            }
                
            admMenu.add(admMenu1);
            admMenu.add(admMenu2);
            admMenu.add(admMenu3);
            admMenu.add(admMenu4);
            admMenu.add(admMenu5);
            admMenu.add(admMenu6);
            admMenu.add(admMenu7);
            admMenu.add(admMenu8);
            admMenu.add(admMenu9);
            admMenu.add(admMenu10);

            habilita_campos_tela_config(true);
        }
        menuBar.add(statMenu);
        
        // Adiciona um item simples para o menu
        cadMenu.add(fatCliente);
        cadMenu.add(fatUnidade);
        cadMenu.add(fatTipoDoc);
        cadMenu.add(fatProduto);
        cadMenu.add(fatCfop);
        cadMenu.add(fatFormaPgto);
        cadMenu.add(fatAliqUf);
        cadMenu.add(fatCidade);
        cadMenu.add(fatBanco);

        movMenu.add(fatDigiNf);
        movMenu.add(fatCancNf);
        movMenu.add(fatDevoNf);
        movMenu.add(fatLerXml);
        movMenu.add(fatBaixa);
        movMenu.addSeparator();
        movMenu.add(fatXmlCompra);
//        movMenu.add(fatImpVenda);
        movMenu.add(fatMonitoraNFeNFCe);
        movMenu.add(fatStopMonitorNFeNFCe);

        relMenu.add(fatRelFatura);
        relMenu.add(fatRelRecebido);
        relMenu.add(fatRelAreceber);
//        cadMenu.add(exitAction);

        JPanel panel_fatChild = new JPanel();
//        System.out.println("ambFat: " + lb_ambFat.getText());
        panel_fatChild.add(lb_ambFat);
        panel_fatChild.add(lb_statFat);
        setJPanelMenuBar(panel_fatura, panel_fatChild, menuBar);
//        painel_pai.setSelectedComponent(panel_fatura);
    }
    private void setJPanelMenuBar(JPanel parent, JPanel child, JMenuBar menuBar) {
        parent.removeAll();
        parent.setLayout(new BorderLayout());
        JRootPane root = new JRootPane();
        parent.add(root, BorderLayout.CENTER);
        root.setJMenuBar(menuBar); 
        System.out.println("Tamanho do JRootPane: " + root.getSize());
        root.getContentPane().add(child);
        parent.putClientProperty("root", root);  //if you need later
      }
//    private void tratar_geraXML_NFCe() {
//        System.out.println("tratar_geraXML_NFCe() - Vai gerar XML para o arquivo: " + arqXML);
//        GeraXML_nfce_new gxml = new GeraXML_nfce_new(
//                getTipoAmbiente(),
//                getStc_camLerAss(),
//                confat,
//                this.empresa, 
//                getStc_drive(),
//                getStc_anomesdia(),
//                false,  //  display
//                cUF,
//                fuso_horario,
//                contatoCupom,
//                uriCupom,
//                //informarCnpjCartao,
//                arqXML
//                );
//        String tipoEmis = "1";
//        while (true) {
//            if (status_servico) {
//                tipoEmis = "1";
//                break;
//            } else {
//                UIManager.put("OptionPane.okButtonText", "Ok");
//                UIManager.put("OptionPane.cancelButtonText", "Desiste");
//                 if (JOptionPane.showConfirmDialog(null, "Serviço Indisponível! Tenta Novamente? ","Verificação do Serviço", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
//                    status_servico = check_status_servico("");
//                 } else {
//                    tipoEmis = "9";
//                    break;
//                 }
//            }
//        }
//        String nomeArquivo = gxml.trataTexto("Gerando texto", tipoEmis);
//        File ffim = new File(arqXML);
//        ffim.delete();
//    }
    public static void main(String args[]) {
        if (args.length < 5) {  //  se nao for passado parametros na linha de comando, assume os valores abaixo
            setTipoAmbiente("2");  //  2 = homologacao / 1 = producao
            stat_empresa = 0;
            stat_panel = "0";  //  pre selecionar um painel
            stat_usuario = "admin";
            stat_caixa = "";
            import_automat = "N";
        } else {
            setTipoAmbiente(args[0]);
            stat_empresa    = Integer.parseInt(args[1]);
            stat_panel      = args[2];  //  pre selecionar um painel
            stat_usuario    = args[3];
            stat_caixa      = args[4];
            if (args.length == 5) {
                import_automat = "N";
            } else {
                import_automat  = args[5];
            }
        }  
        if (getTipoAmbiente() == null || getTipoAmbiente().equals("")) setTipoAmbiente("1");
        if (stat_panel == null || stat_panel.equals("")) stat_panel = "0";
        if (stat_caixa == null || stat_caixa.equals("")) stat_caixa = "caixa01";
        if (stat_usuario == null || stat_usuario.equals("")) stat_usuario = "";
        JFrame form = new NFefacil();
        form.setVisible(true);

    }
    private void processar_monitorNFeNFCe() {
        try {
            System.out.println("Vai executar classe: CronImportNFCe . . .");
            if (import_automat.equals("55")) {
                JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfe para Gerar a NCe(modelo 55) </html>");
                getCiNFe().executar(getInterval(), true);
            } else if (import_automat.equals("65")) {
                JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfce para Gerar a NFCe(modelo 65) </html>");
                getCiNFCe().executar(getInterval(), true);
            } else {
                UIManager.put("OptionPane.yesButtonText", "Monitoar NFe(mod.55)");
                UIManager.put("OptionPane.noButtonText", "Monitorar NFCe(mod.65)");
                UIManager.put("OptionPane.cancelButtonText", "Desistir");
                int escolha = JOptionPane.showConfirmDialog(null, "Escolha o modelo para Monitorar", "Monitoramento", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if ( escolha == JOptionPane.YES_OPTION){
                    import_automat = "55";
                    JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfe para Gerar a NCe(modelo 55) </html>");
                    getCiNFe().executar(getInterval(), true);
                } else if ( escolha == JOptionPane.NO_OPTION) {
                    import_automat = "65";
                    JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: " + System.getProperty("user.dir") + System.getProperty("file.separator") + " xml" + System.getProperty("file.separator") + "nfce para Gerar a NFCe(modelo 65) </html>");
                    getCiNFCe().executar(getInterval(), true);
                } else {
                    JOptionPane.showMessageDialog(null, "Você desistiu e o monitoramento não será realizado!");
                }
                UIManager.put("OptionPane.yesButtonText", "Sim");
                UIManager.put("OptionPane.noButtonText", "Não");
            }
            System.out.println("Executou classe: CronImportNFCe . . .");
        } catch (Exception ie) {
            JOptionPane.showMessageDialog(null, "Erro de Interrupção no método: testCron() . . . " + ie);
        }
    }
    private void processar_stopMonitorNFeNFCe() {
        try {
            System.out.println("Vai executar classe: CronImportNFCe . . .");
            if (import_automat.equals("55")) {
                getCiNFe().finalizar(true);
            } else {
                getCiNFCe().finalizar(true);
            }
            if (!monitorComModelo) {
                import_automat = "99";
            }
            System.out.println("Executou classe: CronImportNFCe . . .");
        } catch (Exception ie) {
            JOptionPane.showMessageDialog(null, "Erro de Interruption no método: testCron() . . . " + ie);
        }
    }
//    private void processar_escutarPasta() {
//        try {
//            JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai entrar em estado de espera de arquivo XML na pasta; \\nfe\\xml\\nfce para Gerar a NFCe(modelo 65)"
//                    + "\n<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Para finalizar esta opção, copie um arquivo com nome: exit para a pasta \\nfe\\xml\\nfce");
//            setExtendedState(JFrame.ICONIFIED);
//            while(true) {
//                escutarPasta();
//                System.out.println("Voltou do método: escutarPasta() . . . " );
//                if (isSuspender()) {
//                    break;
//                }
//            }
//            System.out.println("Finalizou while(true) para chamar o método: escutarPasta() . . . " );
//        } catch (InterruptedException ie) {
//            JOptionPane.showMessageDialog(null, "Erro de Interruption no método: escutarPasta() . . . " + ie);
//        }
//        setExtendedState(JFrame.NORMAL);  //(MAXIMIZED_BOTH); 
//        JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Finalizou a espera do arquivo XML para Gerar a NFCe . . .");
//        if (import_automat.toLowerCase().equals("a")) {
//            System.exit(0);
//        }
//    }
//    public void escutarPasta() throws InterruptedException {
//        long patience = 1000 * 60 * 60 * 24;
//        threadMessage("Starting ImportaArquivoXML thread");
//        long startTime = System.currentTimeMillis();
//        while(true) {
//            Thread t = new Thread(new ImportaArquivoXML());
//            t.start();
//
//            threadMessage("Aguardando aparecer arquivo XML na pasta XML do sistema . . .");
//            while (t.isAlive()) {
//                threadMessage("Ainda aguardando . . .");
//                t.join(1000);
//                if (((System.currentTimeMillis() - startTime) > patience)
//                      && t.isAlive()) {
//                    threadMessage("Cansado de esperar!");
//                    t.interrupt();
//                    t.join();
//                }
//                if (isSuspender()) {
//                    t.join();
//                }
//            }
//            threadMessage("Suspender = " + NFefacil.isSuspender());
//            if (NFefacil.isSuspender()) {
//                break;
//            }
//
//        }
//        threadMessage("Finalizou a espera! Vai gerar a NFe . . .");
////  chamar a rotina de geração de NFe aqui      
//        if (isAutorizando()) {
//            executarGerarXML_NFCe_new();
//        }
//    }
//    public static void threadMessage(String message) {
//        String threadName =
//            Thread.currentThread().getName();
//        System.out.format("%s: %s%n",
//                          threadName,
//                          message);
//    }
//
//    /**
//     * @return the suspender
//     */
//    public static boolean isSuspender() {
//        return suspender;
//    }
//
//    /**
//     * @param aSuspender the suspender to set
//     */
//    public static void setSuspender(boolean aSuspender) {
//        suspender = aSuspender;
//    }

    /**
     * @return the autorizando
     */
    public static boolean isAutorizando() {
        return autorizando;
    }

//    /**
//     * @param aAutorizando the autorizando to set
//     */
//    public static void setAutorizando(boolean aAutorizando) {
//        autorizando = aAutorizando;
//    }
//    public void executarGerarXML_NFCe_new() {
//        tratar_geraXML_NFCe();
//        setSuspender(false);
//        setAutorizando(false);
//    }
//
    /**
     * @return the interval
     */
    public static int getInterval() {
        return interval;
    }

    /**
     * @param aInterval the interval to set
     */
    public static void setInterval(int aInterval) {
        interval = aInterval;
    }

    /**
     * @return the lb_ambiente
     */
    public static JLabel getLb_ambiente() {
        return lb_ambiente;
    }

    /**
     * @param aLb_ambiente the lb_ambiente to set
     */
    public static void setLb_ambiente(JLabel aLb_ambiente) {
        lb_ambiente = aLb_ambiente;
    }

    /**
     * @return the motivo
     */
    public static String getMotivo() {
        return motivo;
    }

    /**
     * @param aMotivo the motivo to set
     */
    public static void setMotivo(String aMotivo) {
        motivo = aMotivo;
    }
}
//     class ImportaArquivoXML
//        implements Runnable {
//        public void run() {
//            try {
//                String diretorio = System.getProperty("user.dir")+System.getProperty("file.separator")+"xml"+System.getProperty("file.separator")+"nfce";
//                String arqOrigem = null;
//                String arqFim = "exit";
//                while (true) {
//                    gravar_nfce_lido();
//                    File file = new File(diretorio);
//                    File afile[] = file.listFiles();
//                    int i = 0;
//                    if (afile.length > 0) {
//                        if (afile.length == 1 && afile[0].getName().equals(arqFim)) {
//                            NFefacil.threadMessage("Encontrou o arquivo [" + arqFim + "] na pasta XML!");
//                            NFefacil.setSuspender(true);
//                            File ffim = afile[0];
//                            ffim.delete();
//                            break;
//                        } 
//                        File fxml;
//                        for (int j = afile.length; i < j; i++) {
//                            fxml = afile[i];
//                            System.out.println(fxml.getAbsolutePath());
//                        }
//                        if ( afile[0].getName().equals(arqFim)) {
//                            fxml = afile[1];
//                        } else {
//                            fxml = afile[0];
//                        }
//                        if (fxml.getAbsolutePath().indexOf(".xml") != -1) {
//                            NFefacil.threadMessage("Encontrou o arquivo [" + fxml.getAbsolutePath() + "] na pasta XML!");
//                            NFefacil.arqXML = fxml.getAbsolutePath();
//                            NFefacil.setSuspender(true);
//                            NFefacil.setAutorizando(true);
//                            Thread.sleep(1000); 
//                            NFefacil.threadMessage("Liberando a Thread . . .");
//                            Thread.currentThread().interrupt();
//                            Thread.currentThread().join();
//                        } else {
//                            if (!fxml.getName().equals(arqFim)) {
//                                System.out.println("Apagando arquivo desconhecido: " + fxml.getAbsolutePath());
//                                fxml.delete();
//                            }
//                            Thread.sleep(4000);
//                        }
//                    } else {
//                       Thread.sleep(4000); 
//                    }
//                }
//            } catch(Exception e) {
//                
//            }
//        }
//        private void gravar_nfce_lido() {
//            String sql = "";
//            Date dataAtual = new Date();
//            java.sql.Timestamp sq = new java.sql.Timestamp(dataAtual.getTime());
//             sql = "update nfce_lido set data = '" + sq + "' where id = 1";
//            try {
//                Statement stat_nfce_lido = NFefacil.confat.createStatement();
//                int qtins = stat_nfce_lido.executeUpdate(sql);
//                sql = "select * from nfce_lido where id = 1";
//                ResultSet rs_lido = stat_nfce_lido.executeQuery(sql);
//                while (rs_lido.next()) {
//                    sq = rs_lido.getTimestamp("data");
//                    String dataSq = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(sq);
//                    System.out.println("data nfce_lido: " + dataSq);
//                }
//            } catch (SQLException ex) {
//                    System.out.println("erro ao tratar tabela nfce_lido: " + ex);
//            }
//        }
//    }

    class configNfeFacil implements Serializable
    {
        String url, usuario, senha ;
        String driver                   = "";  //"sun.jdbc.odbc.JdbcOdbcDriver";
        String banco                    = "";  //"jdbc:odbc:portonfe";
        String caminho_conexao          = "";  
        String caminho_nfeprot          = "";  //"NFEPROT";
        String caminho_assinatura       = "";  //"ASSINADA";
        String caminho_consulta         = "";  //"ASSINADA";
        String caminho_ler_assinar      = "";  //"ASSINAR";
        String caminho_txt              = "";  //"TXT";
        String caminho_grava_assinar    = "";  //"ASSINAR";
        String caminho_grava_assinada   = "";  //"ASSINADA";
        String caminho_grava_retconsu   = "";  //"RETCONSU";
        String caminho_grava_retcanc    = "";  //"RETCANCE";
        String caminho_grava_retcorr    = "";  //"RETCORRI";
        String caminho_grava_retinut    = "";  //"RETINUTI";
        String caminho_grava_retlote    = "";  //"RETLOTE";
        String caminho_grava_retrecep   = "";  //"RETRECEP";
        String caminho_grava_inut       = "";  //"INUTILIZ";
        String caminho_grava_canc       = "";  //"CANCELA";
        String caminho_grava_corr       = "";  //"CORRIGE";
        String caminho_grava_impDanfe   = "";  //"IMPDANFE";
        String file_keystore            = "";  //"certificado.pfx";
        String file_truststore          = "";  //"NFeCacerts"  //"cartificado.jks";
        String senha_config             = "";
    }
    class config_fatura implements Serializable
    {
        String usuario = "";
        String senha = "";
        String local = "";
        String driver = "";
        String jdbc = "";
        String bd = "";
    }