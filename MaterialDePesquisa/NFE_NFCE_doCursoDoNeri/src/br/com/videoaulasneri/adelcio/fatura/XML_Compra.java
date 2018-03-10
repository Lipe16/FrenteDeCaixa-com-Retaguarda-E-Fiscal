/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura;

import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINSAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS51;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSTot;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Ide;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Emit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.EnderEmit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.InfAdic;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Prod;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class XML_Compra extends JFrame implements ActionListener{
    private HashMap<String, String> nomeExtenso = new HashMap<String, String>();
    private HashMap<Integer, String> nomePanel;
    private HashMap<Integer, String> nomeTag;
    private HashMap<Integer, Integer> qtdeTag;
    private HashMap<Integer, Integer> nivTag;
    Element elOld;
    String fileName = "", nomeArqXml = "";
    JLabel lb_titulo;
    JButton bt_sair, bt_confirma, bt_nova;  //, bt_gerar;
    JTabbedPane painel_pai;
    JPanel[] tabJPanel = new JPanel[100];
    JTabbedPane[] tabTabPane = new JTabbedPane[100];
    //String desloca = "", desloca0="", desloca1="  ";
    String fsep = System.getProperty("file.separator");
    //boolean elem=false, atrib=false, tag=true;
    int nivelTag = 1, indTag = 2, indElem=0, indDet = 0, indTabPanel = 0, indTabTabPane = 0;
    int linha = -10, coluna = 10, nivelMax = 0;
    boolean direita = true;
    //JPanel panel_login, panel_consulta, panel_configura, panel_geratxt;
    private boolean lendoDet = false;
    String drive = System.getProperty("user.dir");
    String caminho_escolha;
    private int numDet = 0, indexCpo = 0;
    String labelAtual = "Nada Ainda", labelAnt = "Nada Ainda", regArq = "", reg04 = "";
    Ide ide;
    int empresa;
    ICMSTot icmstot;;
    Emit emit;
    EnderEmit enderEmit;
    Prod prod;
    ICMS51 icms;
    PISAliq pis;
    COFINSAliq cofins;
    InfAdic infadic;

    boolean nfeCompleta = false;
    boolean
            ehTag_ide = false, ehTag_emit = false, ehTag_dest = false, ehTag_enderEmit = false, ehTag_enderDest = false,
            ehTag_prod = false, ehTag_det = false,
            ehTag_ICMS = false,
            ehTag_ICMS00 = false,
            ehTag_ICMS10 = false,
            ehTag_ICMS20 = false,
            ehTag_ICMS51 = false,
            ehTag_IPI = false, ehTag_IPINT = false,
            ehTag_PIS = false, ehTag_PISNT = false,
            ehTag_COFINS = false, ehTag_COFINSNT = false,
            ehTag_total = false, ehTag_transp = false,
            ehTag_infAdic = false, ehTag_infProt = false
            ;
    boolean existeEmit = false;
    private String Id,indpag, CPF_dest, CNPJ_dest, xNome_dest, IndIEDest, IE_dest, email_dest, nNF, natOp, cDV, cUF, mod, serie, CNPJ_emit, cNF, UF_emit;
    private String tpnf, cmunfg, tpimp, tpemis, finnfe, procemis, verproc, dhEmi;
    JLabel lb_CNPJ_emit, lb_xNome_emit, lb_xFant_emit, lb_Id, lb_nNF, lb_serie, lb_dhEmi;
    JTextField tf_Id        = new JTextField();
    JTextField tf_nNF       = new JTextField();
    JTextField tf_serie     = new JTextField();
    JTextField tf_dhEmi     = new JTextField();
    JTextField tf_CNPJ_emit = new JTextField();
    JTextField tf_xNome_emit = new JTextField();
    JTextField tf_xFant_emit = new JTextField();

    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    private String pessoaEmit;
    private int codigoEmit;
    private String CNPJDest;
    private double margem = 0.0;
    private boolean continua = true;
    private int pedido;
    private String nItem;
    private int codProduto;
    private float pesoProduto;
    private String nomeArquivo = "";
    boolean display = false;
    
    public XML_Compra(int empresa, Connection connection){
        this.empresa = empresa;
//JOptionPane.showMessageDialog(null, "XMLReader - passou 1 . . .");
        inicializaVariaveis();
//JOptionPane.showMessageDialog(null, "XMLReader - passou 2 . . .");
        initComponents();
        try {
            statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch(Exception erro_class)
        {
               JOptionPane.showMessageDialog(null,"Driver nao localizado: "+erro_class);
        }
        setMargem(pegaMargem());

    }
     public void initComponents(){
        lb_titulo   = new JLabel("Desenvolvido por: Videoaulas Neri e Adelcio" );  //www.portoinformatica.net.br - email: portoinfo@sercomtel.com.br");
        lb_Id       = new JLabel("Id da NFe:");
        lb_nNF      = new JLabel("Número da NFe:");
        lb_serie    = new JLabel("Série da NFe:");
        lb_dhEmi    = new JLabel("Data de Emissão:");
        lb_CNPJ_emit= new JLabel("CNPJ do Emitente:");
        lb_xNome_emit= new JLabel("Nome do Emitente:");
        lb_xFant_emit= new JLabel("Fantasia do Emitente:");
        //  inicializacao de paineis
         painel_pai         = new JTabbedPane();
         painel_pai         .setBounds(10,30,970,580);
         painel_pai         .setBackground(new Color(0,255,255));

         //bt_confirma = new JButton("Confirma");
        bt_nova     = new JButton("Escolher Arquivo");
        //bt_gerar    = new JButton("Importar");
        bt_sair     = new JButton("Sair");

        setTitle("Importação da NFe de Compra");
        setSize(1000, 700);
        //setLocation(750,1000);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane()        .setLayout(null);

        lb_titulo                .setBounds(130, 5,  700, 20);

        tf_Id                  .setForeground(Color.black);
        tf_nNF                 .setForeground(Color.black);
        tf_serie               .setForeground(Color.black);
        tf_dhEmi               .setForeground(Color.black);
        tf_CNPJ_emit           .setForeground(Color.black);
        tf_xNome_emit          .setForeground(Color.black);
        tf_xFant_emit          .setForeground(Color.black);

        tf_Id                  .setBackground(Color.white);
        tf_nNF                 .setBackground(Color.white);
        tf_serie               .setBackground(Color.white);
        tf_dhEmi               .setBackground(Color.white);
        tf_CNPJ_emit           .setBackground(Color.white);
        tf_xNome_emit          .setBackground(Color.white);
        tf_xFant_emit          .setBackground(Color.white);

        lb_Id                  .setBounds(100,70,150,20);
        tf_Id                  .setBounds(250,70,500,20);

        lb_nNF                 .setBounds(100,100,150,20);
        tf_nNF                 .setBounds(250,100,500,20);

        lb_serie               .setBounds(100,130,150,20);
        tf_serie               .setBounds(250,130,500,20);

        lb_dhEmi               .setBounds(100,160,150,20);
        tf_dhEmi               .setBounds(250,160,500,20);

        lb_CNPJ_emit           .setBounds(100,190,150,20);
        tf_CNPJ_emit           .setBounds(250,190,500,20);

        lb_xNome_emit          .setBounds(100,220,150,20);
        tf_xNome_emit          .setBounds(250,220,500,20);

        lb_xFant_emit          .setBounds(100,250,150,20);
        tf_xFant_emit          .setBounds(250,250,500,20);

        //bt_confirma              .setBounds(400, 600, 100, 30);
        bt_nova                  .setBounds(350, 600, 150, 30);
        //bt_gerar                 .setBounds(470, 600, 180, 30);
        bt_sair                  .setBounds(600, 600, 100, 30);

        lb_titulo                .setFont(new Font("Arial",Font.BOLD,16));

        lb_nNF                   .setFont(new Font("Arial",Font.BOLD,12));
        lb_Id                    .setFont(new Font("Arial",Font.BOLD,12));
        lb_serie                 .setFont(new Font("Arial",Font.BOLD,12));
        lb_dhEmi                 .setFont(new Font("Arial",Font.BOLD,12));
        lb_CNPJ_emit             .setFont(new Font("Arial",Font.BOLD,12));
        lb_xNome_emit            .setFont(new Font("Arial",Font.BOLD,12));
        lb_xFant_emit            .setFont(new Font("Arial",Font.BOLD,12));

        //bt_confirma              .addActionListener(this);
        bt_nova                  .addActionListener(this);
        //bt_gerar                 .addActionListener(this);
        bt_sair                  .addActionListener(this);

        getContentPane()      .add(painel_pai);
        getContentPane()      .add(lb_titulo);

        getContentPane()      .add(lb_Id);
        getContentPane()      .add(tf_Id);
        getContentPane()      .add(lb_nNF);
        getContentPane()      .add(tf_nNF);
        getContentPane()      .add(lb_serie);
        getContentPane()      .add(tf_serie);
        getContentPane()      .add(lb_dhEmi);
        getContentPane()      .add(tf_dhEmi);
        getContentPane()      .add(lb_CNPJ_emit);
        getContentPane()      .add(tf_CNPJ_emit);
        getContentPane()      .add(lb_xNome_emit);
        getContentPane()      .add(tf_xNome_emit);
        getContentPane()      .add(lb_xFant_emit);
        getContentPane()      .add(tf_xFant_emit);

        getContentPane()      .add(bt_nova);
        //getContentPane()      .add(bt_gerar);
        getContentPane()      .add(bt_sair);
        //getContentPane()      .add(bt_confirma);
        //System.out.println("Executou initComponents . . . ");
        //bt_gerar.setVisible(false);
        show();
     }
    public void inicializaVariaveis(){
//        caminho_escolha += fsep+"Bkp";  //drive+fsep+"NfeProt"+fsep+"Bkp";
        String emp4dig = ""+empresa;
        int tamtxt = emp4dig.length();
        for (int i=0;i<(4-tamtxt);i++){
            emp4dig = "0"+emp4dig;
        }
        caminho_escolha = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"XML_Compras";  //  +fsep+"NfeProt"+fsep+"Bkp";
        //JOptionPane.showMessageDialog(null, "Caminho escolha: "+caminho_escolha);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_sair){
            dispose();  //System.exit(0);
        //} else if (e.getSource() == bt_gerar){
            //System.out.println("Clicou em gerar . . .");
        //    importar();
        } else if (e.getSource() == bt_nova){
            //painel_pai.removeAll();
            inicializaVariaveis();
            inicializaTags();
//            bt_gerar.setEnabled(true);
            fileName = escolheArq();
            if (fileName != null) {
                continua = true;
                trataXML();
                //transfereArquivo();
                //if (nfeCompleta) {
                //    bt_gerar.setVisible(true);
                //}
            } else {
                //bt_gerar.setVisible(false);
            }
        }
    }

    public String escolheArq(){
        String arquivo = null;
        File[] arquivos = escolhe_arquivo(true, caminho_escolha, JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            for (int i=0;i<arquivos.length;i++){
                arquivo = arquivos[i].getAbsolutePath();
                nomeArqXml = arquivo;  //arquivos[i].getName();
                nomeArquivo = arquivos[i].getName();
                //String nome_arq= arquivos[i].getName();
//JOptionPane.showMessageDialog(null,"Nome Puro do Arquivo escolhido: " + nomeArquivo);
//JOptionPane.showMessageDialog(null,"Caminho completo: " + arquivo); continua = false;
            }
        }
        return arquivo;
    }
    void trataXML(){
        File file = new File(nomeArqXml);  //+"conexaoNFe.xml");
        SAXBuilder builder = new SAXBuilder();
        Document document;

        try{
            //System.out.println("Aquivo: "+fileName);
            document = builder.build(file);
            Element el = document.getRootElement();

           //System.out.println("Elemento Raiz: "+document.getRootElement().getName());
            if (!document.getRootElement().getName().equalsIgnoreCase("nfeProc")){
                JOptionPane.showMessageDialog(null, "Este Documento nao contem informacoes de NFe!");
                System.exit(0);
            }
            trataElemento(el);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo xml. \nErro: "+e);
        }

    }
    private void trataElemento(Element el){
        if (continua) {
            List list = el.getChildren();
            int tamanho = list.size();
            //System.out.println("Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
            //System.out.println("Tamanho da lista: "+tamanho);
            if (tamanho > 0){  //Elemento
                Iterator it = list.iterator();
                for (int i=0;i<tamanho;i++){
                    Element el1 = (Element)it.next();
                    String campo = el1.getQualifiedName().toString().trim();
    //JOptionPane.showMessageDialog(null, "Elemento:"+el1.getQualifiedName().toString().trim()+" - Valor: "+el1.getText().toString());

                    if (el1.getText().toString().equalsIgnoreCase((""))){
                        if (!campo.equalsIgnoreCase(("Signature"))) {
                            if (campo.equalsIgnoreCase(("NFe"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: NFe . . .");
                            } else
                            if (campo.equalsIgnoreCase(("infNFe"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: infNFe . . .");
                                setId(el1.getAttributeValue("Id"));
                                tf_Id.setText(getId());
                            } else
                            if (campo.equalsIgnoreCase(("ide"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: ide . . .");
                               ehTag_ide = true;
                               ide = new Ide();
                            } else
                            if (campo.equalsIgnoreCase(("emit"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: emit . . .");
                                ehTag_emit = true;
                                emit = new Emit();
                            } else
                            if (campo.equalsIgnoreCase(("enderEmit"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: enderEmit . . .");
                                ehTag_enderEmit = true;
                                enderEmit = new EnderEmit();
                            } else
                            if (campo.equalsIgnoreCase(("dest"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: dest . . .");
                                ehTag_emit = false;
                                ehTag_dest = true;
                            } else
                            if (campo.equalsIgnoreCase(("enderDest"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: enderDest . . .");
                                ehTag_enderEmit = false;
                                ehTag_enderDest = true;
                            } else
                            if (campo.equalsIgnoreCase(("det"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: det . . .");
                                ehTag_det = true;
                                setnItem(el1.getAttributeValue("nItem"));
                            } else
                            if (campo.equalsIgnoreCase(("prod"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: prod . . .");
                                ehTag_prod = true;
                                prod = new Prod();
                                icms = new ICMS51();
                                //ipi = new IPI();
                                pis = new PISAliq();
                                cofins = new COFINSAliq();
                            } else
                            if (campo.equalsIgnoreCase(("ICMS"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: ICMS . . .");
                                ehTag_ICMS = true;
/*
                            } else
                            if (campo.equalsIgnoreCase(("ICMS51"))){
    JOptionPane.showMessageDialog(null, "Encontrou a TAG: ICMS51 . . .");
                                ehTag_ICMS51 = true;
 *
 */
                            } else
                            if (campo.equalsIgnoreCase(("IPI"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: IPI . . .");
                               ehTag_ICMS = false;
                               ehTag_IPI = true;
                            } else
                            if (campo.equalsIgnoreCase(("PIS"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: PIS . . .");
                               ehTag_ICMS = false;
                               ehTag_IPI = false;
                               ehTag_PIS = true;
                            } else
                            if (campo.equalsIgnoreCase(("COFINS"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: COFINS . . .");
                               ehTag_ICMS = false;
                               ehTag_PIS = false;
                               ehTag_COFINS = true;
                            } else
                            if (campo.equalsIgnoreCase(("PISNT"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: PISNT . . .");
                               ehTag_ICMS = false;
                               ehTag_IPI = false;
                               ehTag_PISNT = true;
                               pis = new PISAliq();
                            } else
                            if (campo.equalsIgnoreCase(("COFINSNT"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: COFINSNT . . .");
                               ehTag_ICMS = false;
                               ehTag_PIS = false;
                               ehTag_PISNT = false;
                               ehTag_COFINSNT = true;
                               cofins = new COFINSAliq();
                            } else
                            if (campo.equalsIgnoreCase(("total"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: total . . .");
                                ehTag_total = true;
                                icmstot = new ICMSTot();
                            } else
                            if (campo.equalsIgnoreCase(("transp"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: transp . . .");
                                ehTag_transp = true;
                                ehTag_dest = false;
                            } else
                            if (campo.equalsIgnoreCase(("infAdic"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: infAdic . . .");
                                ehTag_infAdic = true;
                                infadic = new InfAdic();
                            } else
                            if (campo.equalsIgnoreCase(("protNFe"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: protNFe . . .");
                            } else
                            if (campo.equalsIgnoreCase(("infProt"))){
    if (display) JOptionPane.showMessageDialog(null, "Encontrou a TAG: infProt . . .");
                                ehTag_infProt = true;
                            }
                            trataElemento(el1);
                        }
                    } else {
    //  tratamento Tags ide
                        if (campo.equalsIgnoreCase(("nNF")) && ehTag_ide) {
                            ide.setnNF(el1.getText().toString());
                            tf_nNF.setText(ide.getnNF());
                        } else
                        if (campo.equalsIgnoreCase(("serie")) && ehTag_ide) {
                            ide.setSerie(el1.getText().toString());
                            tf_serie.setText(ide.getSerie());
                        } else
                        if (campo.equalsIgnoreCase(("dhEmi")) && ehTag_ide) {
                            ide.setDhEmi(el1.getText().toString());
                            tf_dhEmi.setText(ide.getDhEmi());
                        } else
                        if (campo.equalsIgnoreCase(("tpNF")) && ehTag_ide) {
                            ide.setTpNF(el1.getText().toString());
                            tf_dhEmi.setText(ide.getDhEmi());
                        } else
                        if (campo.equalsIgnoreCase(("finNFe")) && ehTag_ide) {
                            ide.setFinNFe(el1.getText().toString());
                            tf_dhEmi.setText(ide.getDhEmi());
                            ehTag_ide = false;
                        } else
    //  tratamento Tags emit
                        if (campo.equalsIgnoreCase(("CNPJ")) && ehTag_emit) {
                            emit.setCNPJ(el1.getText().toString());
                            tf_CNPJ_emit.setText(emit.getCNPJ());
                        } else
                        if (campo.equalsIgnoreCase(("xNome")) && ehTag_emit) {
                            emit.setxNome(el1.getText().toString());
                            tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("xFant")) && ehTag_emit) {
                            emit.setxFant(el1.getText().toString());
                            tf_xFant_emit.setText(emit.getxFant());
                        } else
                        if (campo.equalsIgnoreCase(("xLgr")) && ehTag_emit) {
                            enderEmit.setxLgr(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("nro")) && ehTag_emit) {
                            enderEmit.setNro(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("xBairro")) && ehTag_emit) {
                            enderEmit.setxBairro(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("cMun")) && ehTag_emit) {
                            enderEmit.setcMun(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("xMun")) && ehTag_emit) {
                            enderEmit.setxMun(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("UF")) && ehTag_emit) {
                            enderEmit.setUF(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("CEP")) && ehTag_emit) {
                            enderEmit.setCEP(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("fone")) && ehTag_emit) {
                            enderEmit.setFone(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("IE")) && ehTag_emit) {
                            emit.setIE(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
    //  tratamento Tags dest
                        if (campo.equalsIgnoreCase(("CNPJ")) && ehTag_dest) {
                            setCNPJDest(el1.getText().toString());
                            if (verificaDestino()) {
                                existeEmit = verificaExisteEmit();
                                if (!existeEmit) {
                                    gravarEmit();
                                }
                                continua = verificaNf();
                            } else {
                                JOptionPane.showMessageDialog(null, "Esta NFe de Compra não foi emitida para a sua empresa!");
                            }
                        } else
    //  tratamento Tags det
                        if (campo.equalsIgnoreCase(("cProd")) && ehTag_prod) {
                            prod.setcProd(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("cEAN")) && ehTag_prod) {
                            prod.setcEAN(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("xProd")) && ehTag_prod) {
                            prod.setxProd(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("NCM")) && ehTag_prod) {
                            prod.setNCM(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("CFOP")) && ehTag_prod) {
                            prod.setCFOP(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("uCom")) && ehTag_prod) {
                            prod.setuCom(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("qCom")) && ehTag_prod) {
                            prod.setqCom(el1.getText().toString());
                         } else
                        if (campo.equalsIgnoreCase(("vUnCom")) && ehTag_prod) {
                            prod.setvUnCom(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("vProd")) && ehTag_prod) {
                            prod.setvProd(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("cEANTrib")) && ehTag_prod) {
                            prod.setcEANTrib(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("uTrib")) && ehTag_prod) {
                            prod.setuTrib(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("qTrib")) && ehTag_prod) {
                            prod.setqTrib(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("vUnTrib")) && ehTag_prod) {
                            prod.setvUnTrib(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("indTot")) && ehTag_prod) {
                            prod.setIndTot(el1.getText().toString());
                       } else
    //  tratamento Tags icms
                        if (campo.equalsIgnoreCase(("CST")) && ehTag_ICMS) {
                            icms.setCST(el1.getText().toString());
                            icms.setvBC("0.00");
                            icms.setpICMS("0.00");
                            icms.setpRedBC("0.00");
                            icms.setvICMS("0.00");
                        } else
                        if (campo.equalsIgnoreCase(("vBC")) && ehTag_ICMS) {
                            icms.setvBC(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("pPIS")) && ehTag_ICMS) {
                            icms.setpICMS(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("pRedBC")) && ehTag_ICMS) {
                            icms.setpRedBC(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("vPIS")) && ehTag_ICMS) {
                            icms.setvICMS(el1.getText().toString());
    //  tratamento Tags ipi
                        } else
    //  tratamento Tags pis
                        if (campo.equalsIgnoreCase(("CST")) && ehTag_PIS) {
                            pis.setCST(el1.getText().toString());
                            pis.setvBC("0.00");
                            pis.setpPIS("0.00");
                            pis.setvPIS("0.00");
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vBC")) && ehTag_PIS) {
                            pis.setvBC(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("pPIS")) && ehTag_PIS) {
                            pis.setpPIS(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("vPIS")) && ehTag_PIS) {
                            pis.setvPIS(el1.getText().toString());
                        } else
    //  tratamento Tags cofins
                        if (campo.equalsIgnoreCase(("CST")) && ehTag_COFINS) {
                            cofins.setCST(el1.getText().toString());
                            cofins.setvBC("0.00");
                            cofins.setpCOFINS("0.00");
                            cofins.setvCOFINS("0.00");
                            //tf_xNome_emit.setText(emit.getxNome());
                            if (ehTag_COFINSNT) {
                                gravarDet();
                            }
                        } else
                        if (campo.equalsIgnoreCase(("vBC")) && ehTag_COFINS) {
                            cofins.setvBC(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("pCOFINS")) && ehTag_COFINS) {
                            cofins.setpCOFINS(el1.getText().toString());
                        } else
                        if (campo.equalsIgnoreCase(("vCOFINS")) && ehTag_COFINS) {
                            cofins.setvCOFINS(el1.getText().toString());
                            gravarDet();
    //  tratamento Tags icmstot
                        } else
                        if (campo.equalsIgnoreCase(("vBC"))  && ehTag_total) {
                            icmstot.setvBC(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vICMS"))  && ehTag_total) {
                            icmstot.setvICMS(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vProd"))  && ehTag_total) {
                            icmstot.setvProd(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vDesc")) && ehTag_total) {
                            icmstot.setvDesc(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vPIS"))  && ehTag_total) {
                            icmstot.setvPIS(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vCOFINS"))  && ehTag_total) {
                            icmstot.setvCOFINS(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("vNF"))  && ehTag_total) {
                            icmstot.setvNF(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                        } else
                        if (campo.equalsIgnoreCase(("infCpl")) ) {
                            infadic.setInfCpl(el1.getText().toString());
                            //tf_xNome_emit.setText(emit.getxNome());
                            gravarNf();
                            JOptionPane.showMessageDialog(null, "NF de Entrada registrada com Sucesso . . .");
                        } else
    //  tratamento Tags infProt
                        if (campo.equalsIgnoreCase(("cStat")) && ehTag_infProt) {
                            if (el1.getText().toString().equals("100")) {
    JOptionPane.showMessageDialog(null, "NFe Autorizada . . .");
                                nfeCompleta = true;
                                transfereArquivo();
                            }
                        } else
                        if (campo.equalsIgnoreCase(("xMotivo")) && ehTag_infProt && !nfeCompleta) {
    JOptionPane.showMessageDialog(null, "Esta NFe NAO foi Autorizada pelo Motivo: "+el1.getText().toString());
                        }

                    }
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Não encontrou o arq.de Config.da Porta: portaDispositivo.xml");
            }
            //System.out.println("Método XMLReader().trataElemento()\nPorta: "+getPorta()+"\nDriver: ");
        } else {

        }
    }

    public File[] escolhe_arquivo(boolean multiplo, String caminho, int tipo_arq, String ext_arq){
//            System.out.println("filtro do filechoose: "+"Arquivos "+ext_arq+", "+ext_arq.toLowerCase()+", "+ext_arq.toUpperCase());
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
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
            int returnVal = fc_escolha.showOpenDialog(getContentPane());
            try {
                UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            } catch (Exception ex) {
                //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File[] f_result = fc_escolha.getSelectedFiles();
                //System.out.println("Saiu de Filechooser com OPEN");
                return f_result;
            } else {
                //System.out.println("Saiu de Filechooser com CANCEL");
                return null;
            }
    }
    public static void main(String args[]){
            XML_Compra xread = new XML_Compra(5, null);
    }

    private void importar() {
        //JOptionPane.showMessageDialog(null, "Entrou metodo importar() . . .");
        //JOptionPane.showMessageDialog(null, "Saiu metodo importar() . . .");
    }
    private String preencheCaracter(int tamCpo, String carac) {
        String espaco = "";
        try {
            for(int i=0;i<tamCpo;i++) {
                espaco+= carac;
            }
        } catch (Exception e) {
            System.out.println("metodo preencheCaracter() - Erro : "+e);
        }
        return espaco;
    }
    private String formatZeros(String valor, int tamTot, int tamDec){
        String vlrFormatado = "";
        try {
            int posiPto = valor.indexOf(".");
            int tamcpo = valor.substring(0,posiPto+tamDec).length();
            for (int i=0; i<(tamTot-tamcpo); i++){
                vlrFormatado+= "0";
            }
            vlrFormatado+= valor.substring(0,(valor.indexOf(".")))+valor.substring((posiPto)+1,(posiPto+tamDec+1));
        } catch (Exception e) {
            System.out.println("metodo formatZeros() - Erro : "+e);
        }
        return vlrFormatado;
    }

    private void inicializaTags() {
            ehTag_ide = false;
            ehTag_emit = false; ehTag_dest = false; ehTag_enderEmit = false; ehTag_enderDest = false;
            ehTag_prod = false; ehTag_det = false;
            ehTag_ICMS = false;
            ehTag_ICMS00 = false;
            ehTag_ICMS10 = false;
            ehTag_ICMS20 = false;
            ehTag_ICMS51 = false;
            ehTag_IPI = false; ehTag_IPINT = false;
            ehTag_PIS = false; ehTag_PISNT = false;
            ehTag_COFINS = false; ehTag_COFINSNT = false;
            ehTag_total = false; ehTag_transp = false;
            ehTag_infAdic = false; ehTag_infProt = false;
            tf_Id.setText("");
            tf_nNF.setText("");
            tf_serie.setText("");
            tf_dhEmi.setText("");
            tf_CNPJ_emit.setText("");

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
     * @return the cUF
     */
    private boolean verificaExisteEmit() {
        boolean retorno = false;
        String sql_query = "select * from cliente where cnpj = '"+emit.getCNPJ()+"'";
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                retorno = true;
                if (display) JOptionPane.showMessageDialog(null, "Encontrou o cliente! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela de clientes! "+e);
        }
        return retorno;
    }
    private void gravarEmit() {
        String sql_insert = "";
        try
        {
         sql_insert = "insert into cliente ( "
                 +"Codigo, "
                 +"RazaoSocial, "
                 +"Fantasia, "
                 +"Pessoa, "
                 +"Cnpj, "
                 +"InscEst, "
                 +"Endereco, "
                 +"Numero, "
                 +"Bairro, "
                 +"Cep, "
                 +"Cidade, "
                 +"Codmunic, "
                 +"UF, "
                 +"Telefone, "
                 +"Contato, "
                 +"RamalContato, "
                 +"Email "
                 +")"
                 +" values ("
                 +getCodigoEmit() + ", "
                 +"'"+emit.getxNome() + "', "
                 +"'"+emit.getxFant() + "', "+
                "'"+getPessoaEmit() + "', "
                +"'"+emit.getCNPJ() + "', "
                +"'"+emit.getIE() + "', "
                +"'"+enderEmit.getxLgr() + "', "+
                "'"+enderEmit.getNro() + "', "
                +"'"+enderEmit.getxBairro() + "', "
                +enderEmit.getCEP() + ", "
                +"'"+enderEmit.getxMun() + "', "+
                "'"+enderEmit.getcMun() + "', "+
                "'"+enderEmit.getUF() + "', "
                +"'"+enderEmit.getFone() + "', "
                +"'"+ "', "
                +"'"+ "', "+
                "'"+ "' "
                + ")";
    System.out.println("Comando sql_insert = " + sql_insert);
         statement.executeUpdate(sql_insert);
         JOptionPane.showMessageDialog(null, "Gravacao do fornecedor realizada com sucesso!");
         //gravarMargem();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar gravar a tabela de clientes! "+e);
            JOptionPane.showMessageDialog(null, "Comando de Insercao: "+sql_insert);
        }
    }

    /**
     * @return the pessoaEmit
     */
    public String getPessoaEmit() {
        if (emit.getCNPJ().length() == 14) {
            pessoaEmit = "J";
        } else {
            pessoaEmit = "F";
        }
        return pessoaEmit;
    }

    /**
     * @param pessoaEmit the pessoaEmit to set
     */
    public void setPessoaEmit(String pessoaEmit) {
        this.pessoaEmit = pessoaEmit;
    }

    /**
     * @return the codigoEmit
     */
    public int getCodigoEmit() {
        String sql_query = "select max(codigo) as ultimoCod from cliente";
        codigoEmit = 0;
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                codigoEmit = resultset.getInt("ultimoCod");
            }
            codigoEmit++;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar capturar o ultimo codigo da tabela de clientes! "+e);
        }
        return codigoEmit;
    }

    /**
     * @param codigoEmit the codigoEmit to set
     */
    public void setCodigoEmit(int codigoEmit) {
        this.codigoEmit = codigoEmit;
    }

    /**
     * @return the CNPJDest
     */
    public String getCNPJDest() {
        return CNPJDest;
    }

    /**
     * @param CNPJDest the CNPJDest to set
     */
    public void setCNPJDest(String CNPJDest) {
        this.CNPJDest = CNPJDest;
    }

    private boolean verificaDestino() {
        boolean retorno = false;
        String sql_query = "select * from empresa where cnpj = '"+getCNPJDest()+"'";
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                retorno = true;
                if (display) JOptionPane.showMessageDialog(null, "O CNPJ do destinatario desta NFe pertence a sua empresa! ");
            }
            if (!retorno) {
                JOptionPane.showMessageDialog(null, "O CNPJ do destinatario desta NFe NAO pertence a sua empresa! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela de empresa! "+e);
        }

        return retorno;
    }

    private boolean verificaNf() {
        boolean retorno = true;
        String sql_query = "select * from nf_entrada where empresa = "+empresa+" and cod_fornecedor = "+getCodigoEmit()+" and numero_nfe = "+ide.getnNF()+"and serie_nfe = '"+ide.getSerie()+"'";
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                retorno = false;
                JOptionPane.showMessageDialog(null, "Ja existe esta NF de Entrada no sistema! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela de nf_entrada! "+e);
            retorno = false;
        }
        return retorno;
    }
    private boolean verificaMargem() {
        boolean retorno = false;
        String sql_query = "select * from margem where cnpj = '"+emit.getCNPJ()+"'";
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                margem = resultset.getDouble("margem");
                retorno = true;
                if (display) JOptionPane.showMessageDialog(null, "Ja existe a margem para este Fornecedor! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela de margem! "+e);
        }

        return retorno;
    }

    private void gravarMargem() {
        if (!verificaMargem()) {
        String sql_insert = "";
        try
        {
            if (confirmaMargem()) {
             sql_insert = "insert into margem ( "
                     +"Cnpj, "
                     +"margem "
                     +")"
                     +" values ("
                    +"'"+emit.getCNPJ() + "', "
                    +getMargem() + " "
                    + ")";
        System.out.println("Comando sql_insert = " + sql_insert);
             statement.executeUpdate(sql_insert);
             if (display) JOptionPane.showMessageDialog(null, "Gravacao da margem realizada com sucesso!");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar gravar a tabela de margem por fornecedor! "+e);
            JOptionPane.showMessageDialog(null, "Comando de Insercao: "+sql_insert);
        }

        }
    }

    private boolean confirmaMargem() {
        boolean retorno = true;
        try {
            UIManager.put("OptionPane.yesButtonText", "Confirmar");
            UIManager.put("OptionPane.noButtonText", "Desiste");
            UIManager.put("OptionPane.cancelButtonText", "Cancelar");
               //int escolha = JOptionPane.showConfirmDialog(null, "Percentual de Margem de Lucro ", "Informe o Percentual de Margem de Lucro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            setMargem(Double.parseDouble(JOptionPane.showInputDialog("Informe o Percentual de Margem de Lucro", getMargem())));
        } catch(Exception e) {
            setMargem(15.0);
            retorno = false;
            JOptionPane.showMessageDialog(null, "Voce desistiu de informar a Margem e a mesma não será gravada! ");
        }
        return retorno;
    }

    private double pegaMargem() {
        double retorno = 18.0;
        String sql_query = "select * from empresa where codempresa = "+empresa;
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                margem = resultset.getDouble("margem_lucro");
                retorno = margem;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela empresa para ler margem de lucro! "+e);
        }

        return retorno;

    }

    /**
     * @return the margem
     */
    public double getMargem() {
        return margem;
    }

    /**
     * @param margem the margem to set
     */
    public void setMargem(double margem) {
        this.margem = margem;
    }

    private boolean gravarNf() {
        boolean retorno = false;
        gravarMargem();
        String sql_insert = "";
        Date dataHoje = new Date();
        try {
            sql_insert = "insert into nf_entrada ( "
                         +"empresa, "
                         +"pedido, "
                         +"cod_fornecedor, "
                         +"cod_forma_pgto, "
                         +"cod_tipo_doc, "
                         +"cod_banco, "
                         +"data_digitacao, "
                         +"valor_produtos, "
                         +"valor_descontos, "
                         +"valor_total, "
                         +"cod_transportador, "
                         +"dados_adicionais, "
                         +"qtde_volume, "
                         +"peso_volume, "
                         +"placa_veiculo, "
                         +"uf_placa, "
                         +"pedido_fornecedor, "
                         +"numero_nfe, "
                         +"serie_nfe, ";  //"data_emissao, "+
                 sql_insert += "chave_nfe, "+
                        "icms_bc, "+"icms_vlr, "+"ipi_bc, "+"ipi_vlr, "+"pis_bc, "+"pis_vlr, "
                        +"cofins_bc, "+"cofins_vlr, num_NFe_fat, fin_NFe "
                        +") values ("
                        +empresa+", "
                        +0 + ", "  //  +" and numero_nfe = "++"and serie_nfe = '"+
                        +getCodigoEmit() + ", "
                        + 0 + ", "  //  forma_pgto
                        +"'"+"AV" + "', "  //  tipo_doc
                        +0 + ", "  // banco
                        +"'"+dataHoje + "', "
                        +icmstot.getvProd() + ", "  //  valor_produtos
                        +icmstot.getvDesc() + ", "  //  valor_descontos
                        +icmstot.getvNF() + ", "  //  valor_total
                        +0 + ", "
                        +"'"+infadic.getInfCpl() + "', "  //  dados_adicionais
                        +0 + ", "  //  qt_volumes
                        +0 + ", "  //  peso_volume
                        +"'"+"" + "', "  //  Placa
                        +"'"+"" + "', "  //  UF_placa
                        +"'"+"" + "', "  //  pedido_fornecedor
                        +ide.getnNF() + ", "
                        +"'"+ide.getSerie() + "', ";
                  sql_insert += "'"+getId().substring(3,47) + "', "  //  chave_nfe.getText()
                        +icmstot.getvBC() + ", "  //  icms_bc
                        +icmstot.getvICMS() + ", "  //  icms_vlr
                        +icmstot.getvBC() + ", "  //  ipi_bc
                        +icmstot.getvIPI() + ", "  //  ipi_vlr
                        +icmstot.getvBC() + ", "  //  pis_bc
                        +icmstot.getvPIS() + ", "  //  pis_vlr
                        +icmstot.getvBC() + ", "  //  cofins_bc
                        +icmstot.getvCOFINS() + ", "  //  cofins_vlr
                        +0+ ", "  //  num_nfe_fat
                        +"'"+ide.getFinNFe()+"' "
                        + ")";
                 statement.executeUpdate(sql_insert);
                 if (display) JOptionPane.showMessageDialog(null, "Gravacao da Tabela nf_entrada realizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar gravar a tabela de NF! "+e);
            JOptionPane.showMessageDialog(null, "Comando de Insercao: "+sql_insert);
            continua = false;
        }
        return retorno;
    }

    private boolean gravarDet() {
        boolean retorno = true;
        if (!verificaProduto()) {
            gravarProduto();
        }
        String sql_insert = "";
        try {
                 sql_insert = "insert into nf_prod_entra ( "+
                         "empresa, "+
                        "cod_fornecedor, "+
                        "numero_nfe, "+
                        "serie_nfe, "+
                        "pedido, "+
                        "item, "+
                        "cod_produto, "+
                        "cod_cfop, "+
                        "quantidade, "+
                        "peso, "+
                        "vlr_unitario, "+
                        "vlr_produto, "+
                        "vlr_desconto, "+
                        "vlr_total, "+
                        "icms_bc, "+
                        "icms_perc, "+
                        "icms_pred, "+
                        "icms_vlr, "+
                        "icms_cst, "+
                        "ipi_bc, "+
                        "ipi_perc, "+
                        "ipi_vlr, "+
                        "ipi_cst, "+
                        "pis_bc, "+
                        "pis_perc, "+
                        "pis_vlr, "+
                        "pis_cst, "+
                        "cofins_bc, "+
                        "cofins_perc, "+
                        "cofins_vlr, "+
                        "cofins_cst "+
                        ")"+
                        " values ("+
                        empresa+", "+
                        getCodigoEmit() + ", "+
                        ide.getnNF() + ", "+
                        "'"+ide.getSerie() + "', "+
                        0 + ", "+
                        Integer.parseInt(getnItem()) + ", "+
                        getCodProduto() + ", "+
                        prod.getCFOP() + ", "+
                        prod.getqCom() + ", "+
                        ""+(getPesoProduto()*Float.parseFloat(prod.getqCom())) + ", "+
                        ""+prod.getvUnCom() + ", "+
                        ""+prod.getvProd() + ", "+
                        ""+prod.getvDesc() + ", "+
                        ""+prod.getvProd() + ", "+
                        ""+icms.getvBC() + ", "+
                        ""+icms.getpICMS() + ", "+
                        ""+icms.getpRedBC() + ", "+
                        ""+icms.getvICMS() + ", "+
                        "'"+icms.getCST() + "', "+
                        ""+0.00 + ", "+  //  ipi BC
                        ""+0.00 + ", "+  //  ipi perc
                        ""+0.00 + ", "+  //  ipi vlr
                        "'"+"00" + "', "+  //  ipi cst
                        ""+pis.getvBC() + ", "+
                        ""+pis.getpPIS() + ", "+
                        ""+pis.getvPIS() + ", "+
                        "'"+pis.getCST() + "', "+
                        ""+cofins.getvBC() + ", "+
                        ""+cofins.getpCOFINS() + ", "+
                        ""+cofins.getvCOFINS() + ", "+
                        "'"+cofins.getCST() + "' " +
                        ")";
//if (display) JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 if (display) JOptionPane.showMessageDialog(null, "Gravacao do Produto na tabela: nf_prod_entra realizada com sucesso para o item: "+getnItem());
            }
            catch(Exception erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o Produtoo na Tabela: nf_prod_entra!\nErro: "+erro);
                JOptionPane.showMessageDialog(null, "Comando de Insercao: "+sql_insert);
                 //JOptionPane.showMessageDialog(null, "Registro já existe na Base de Dados!");
                 continua = false;
            }
            //x
        ehTag_det = false;
        ehTag_prod = false;
        ehTag_ICMS = false;
        ehTag_IPI = false;
        ehTag_PIS = false;
        ehTag_PISNT = false;
        ehTag_COFINS = false;
        ehTag_COFINSNT = false;
        return retorno;
    }

    private boolean verificaProduto() {
        boolean retorno = false;
        String sql_query = "select * from produto where codigo_fornec = '"+prod.getcProd()+"' and cnpj_fornecedor = '"+emit.getCNPJ()+"'";
//System.out.println("Comando sql_Query: "+sql_query);
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                setCodProduto(resultset.getInt("codigo"));
                retorno = true;
                if (display) JOptionPane.showMessageDialog(null, "Este Produto ja existe no cadastro. OK! ");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela de produto! "+e);
            continua = false;
        }
        return retorno;
    }

    private boolean gravarProduto() {
        boolean retorno = true;
        String sql_insert = "";
        try {
            sql_insert = "insert into produto ( "+
                        "codigo, "+"codigo_fornec, "+"cnpj_fornecedor, "+"fornecedor, "+"descricao, "+"unidade, "+
                        "nome_reduzido, "+"seg_name, "+"marca, "+"garantia, "+"ean, "+
                        "ncm, "+"cest, "+"origem, "+"preco_compra, "+"preco, "+"peso, "+"icms_cst, "+
                        "icms_perc, "+"icms_pred, "+"ipi_cst, "+"ipi_perc, "+"pis_cst, "+"pis_perc, "+"cofins_cst, "+
                        "cofins_perc, "+"images "+")"+
                        " values ("
                        +pegaProxCodProduto() + ", "
                        +"'"+prod.getcProd() + "', "
                        +"'"+emit.getCNPJ() + "', "
                        +"'"+emit.getxNome().substring(0,15) + "', "
                        +"'"+prod.getxProd() + "', "
                        +"'"+prod.getuCom().substring(0,2) + "', "
                        +"'"+prod.getxProd() + "', "
                        +"'"+ "', "  //  categoria
                        +"'"+ "', "  //  marca
                        +""+0.0 + ", "  //  garantia
                        +"'"+prod.getcEAN() + "', "
                        +"'"+prod.getNCM() + "', "
                        +"'"+ "', "  //  cest
                        +"'"+"I" + "', "  //  origem
                        +""+prod.getvUnCom() + ", "
                        +""+calcularPrecoVenda() + ", "
                        +""+1.0 + ", "  //  peso
                        +"'"+"02" + "', "  //  icms cst
                        +""+0.0 + ", "  //  icms perc
                        +""+0.0 + ", "  //  icms perc red
                        +"'"+"00" + "', "  //  ipi cst
                        +""+0.0 + ", "  //  ipi perc
                        +"'"+"07" + "', "  //  pis cst
                        +""+0.0 + ", "  //  pis perc
                        +"'"+"07" + "', "  //  cofins cst
                        +""+0.0 + ", "  //  cofins perc
                        +"'"+ "' "  //  images
                        + ")";
            //System.out.println("Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 if (display) JOptionPane.showMessageDialog(null, "Gravacao da tabela: produto realizada com sucesso!");

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar gravar na tabela de produto! "+e);
            JOptionPane.showMessageDialog(null, "Comando de Insercao: "+sql_insert);
            continua = false;
        }
        return retorno;
    }

    private double calcularPrecoVenda() {
        double retorno = 0.0;
        double margemDec = margem / 100;
        retorno = arredondar((Double.parseDouble(prod.getvUnCom()) * (1 + margemDec) ),2,0);
        return retorno;
    }

    /**
     * @return the nItem
     */
    public String getnItem() {
        return nItem;
    }

    /**
     * @param nItem the nItem to set
     */
    public void setnItem(String nItem) {
        this.nItem = nItem;
    }

    /**
     * @return the codProduto
     */
    public int pegaProxCodProduto() {
        String sql_query = "select max(codigo) as ultimoCod from produto";
        codProduto = 0;
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                codProduto = resultset.getInt("ultimoCod");
            }
            codProduto++;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar capturar o ultimo codigo da tabela de produtos! "+e);
        }
        return codProduto;
    }

    /**
     * @return the pesoProduto
     */
    public float getPesoProduto() {
        return pesoProduto;
    }

    /**
     * @param pesoProduto the pesoProduto to set
     */
    public void setPesoProduto(float pesoProduto) {
        this.pesoProduto = pesoProduto;
    }

    /**
     * @return the codProduto
     */
    public int getCodProduto() {
        return codProduto;
    }

    /**
     * @param codProduto the codProduto to set
     */
    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }
private double arredondar(double valor, int casas, int ceilOrFloor) {
    double arredondado = valor;
    arredondado *= (Math.pow(10, casas));
    if (ceilOrFloor == 0) {
        arredondado = Math.ceil(arredondado);
    } else {
        arredondado = Math.floor(arredondado);
    }
    arredondado /= (Math.pow(10, casas));
    return arredondado;
}

private void transfereArquivo() {

    File arqEntrada    = new File(nomeArqXml);
    char chartxt[]  = new char[(int)arqEntrada.length()];
    String arq_saida_xml = caminho_escolha+"//lidos//"+nomeArquivo;
    //JOptionPane.showMessageDialog(null,"Arquivo de saida: "+arq_saida_xml);
    FileReader entrada;
    try {
        entrada = new FileReader(nomeArqXml);
        entrada.read(chartxt);  //(res);
        entrada.close();

        String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
        byte[] texto_byte = texto_string.getBytes();
        FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
        arquivo_gerado.write(texto_byte);
        arquivo_gerado.close();
        try {
            arqEntrada.delete();
            //System.out.println("Apagou o arquivo: "+arqEntrada);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar apagar o arquivo: "+arqEntrada+"\nErro: "+ex);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,"Erro ao tentar transferir o arquivo para a pasta Lidos: "+ex);
    }
}

/*
    public int getPedido() {
        String sql_query = "select max(pedido) as ultimoCod from nf";
        pedido = 0;
        try {
            resultset = statement.executeQuery(sql_query);
            while(resultset.next()) {
                pedido = resultset.getInt("ultimoCod");
            }
            pedido++;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar capturar o ultimo codigo da tabela de nf! "+e);
        }
        return pedido;
    }
    public void setPedido(int pedido) {
        this.pedido = pedido;
    }
*/
}
