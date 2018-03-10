/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import br.com.videoaulasneri.adelcio.nfefacil.bean.Dest;
import br.com.videoaulasneri.adelcio.nfefacil.bean.EnderDest;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Prod;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMS00;
import br.com.videoaulasneri.adelcio.nfefacil.bean.PISAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.COFINSAliq;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Emit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.EnderEmit;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Dup;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Ide;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Protocolo;
import br.com.videoaulasneri.adelcio.nfefacil.bean.ICMSTot;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Transp;
import br.com.videoaulasneri.adelcio.nfefacil.bean.Vol;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class XMLReader extends JFrame implements ActionListener{
    private HashMap<String, String> nomeExtenso = new HashMap<String, String>();
    private HashMap<Integer, String> nomePanel;
    private HashMap<Integer, String> nomeTag;
    private HashMap<Integer, Integer> qtdeTag;
    private HashMap<Integer, Integer> nivTag;
    Element elOld;
    String fileName = "", nomeArqXml = "";
    JLabel lb_titulo;
    JButton bt_sair, bt_confirma, bt_nova, bt_gerar;
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
    Emit emit;
    EnderEmit enderemit;
    Dest dest;
    EnderDest enderdest;
    Prod det;
    ICMS00 icms;
    PISAliq pis;
    COFINSAliq cofins;
    Dup financ;  //  Registro
    Transp transp;
    Vol vol;
    ICMSTot total;
    Protocolo prot;  //  Registro
    String empresa = "";
    private String Id;
    boolean achouId = false;

    
    public XMLReader(String empresa){
        this.empresa = empresa;
//JOptionPane.showMessageDialog(null, "XMLReader - passou 1 . . .");
        inicializaVariaveis();
//JOptionPane.showMessageDialog(null, "XMLReader - passou 2 . . .");
        initComponents();
        //JOptionPane.showMessageDialog(null, "XMLReader - passou 3 . . .");
       preencheNomeExtenso();
        //JOptionPane.showMessageDialog(null, "XMLReader - passou 4 . . .");
       fileName = escolheArq();
       if (fileName == null || fileName.equals("")) {
           //System.exit(0);
        //JOptionPane.showMessageDialog(null, "XMLReader - o nome estava vazio . . .");
           dispose();
       }  else {
           getContentPane()      .add(bt_nova);
           getContentPane()      .add(bt_gerar);
           getContentPane()      .add(bt_sair);
            if (Integer.parseInt(empresa) != 168) {  //  somente para a Difripar
                bt_gerar.setVisible(false);
            }
           search();
       }
        
    }
     public void initComponents(){
        lb_titulo   = new JLabel("videoaulaneri e Adelcio");

        //  inicializacao de paineis
         painel_pai         = new JTabbedPane();
         painel_pai         .setBounds(10,30,970,620);
         painel_pai         .setBackground(new Color(0,255,255));

         //bt_confirma = new JButton("Confirma");
        bt_nova     = new JButton("Nova Consulta");
        bt_gerar    = new JButton("Gerar Arq.p/Importar");
        bt_sair     = new JButton("Sair");

        setTitle("Visualização da NFe");
        setSize(1000, 730);
        //setLocation(750,1000);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane()        .setLayout(null);

        lb_titulo                .setBounds(130, 5,  700, 20);
        //bt_confirma              .setBounds(400, 600, 100, 30);
        bt_nova                  .setBounds(300, 660, 120, 30);
        bt_gerar                 .setBounds(450, 660, 180, 30);
        bt_sair                  .setBounds(650, 660, 100, 30);

        lb_titulo                .setFont(new Font("Arial",Font.BOLD,16));

        //bt_confirma              .addActionListener(this);
        bt_nova                  .addActionListener(this);
        bt_gerar                 .addActionListener(this);
        bt_sair                  .addActionListener(this);

        getContentPane()   .add(painel_pai);
        getContentPane()      .add(lb_titulo);
        //getContentPane()      .add(bt_confirma);
        //System.out.println("Executou initComponents . . . ");
        show();
     }
    public void inicializaVariaveis(){
        fsep = System.getProperty("file.separator");
        nivelTag = 1;
        indTag = 2;
        indElem=0;
        indDet = 0;
        indTabPanel = 0;
        indTabTabPane = 0;
        linha = -10;
        coluna = 10;
        nivelMax = 0;
        direita = true;
//        caminho_escolha += fsep+"Bkp";  //drive+fsep+"NfeProt"+fsep+"Bkp";
        String emp4dig = ""+empresa;
        int tamtxt = emp4dig.length();
        for (int i=0;i<(4-tamtxt);i++){
            emp4dig = "0"+emp4dig;
        }
        caminho_escolha = drive+fsep+"dados"+fsep+"empr"+emp4dig;  //  +fsep+"NfeProt"+fsep+"Bkp";
        //JOptionPane.showMessageDialog(null, "Caminho escolha: "+caminho_escolha);

    }

    public String escolheArq(){
        String arquivo = null;
        File[] arquivos = escolhe_arquivo(true, caminho_escolha, JFileChooser.FILES_ONLY, "xml");
        if (arquivos != null ){
            for (int i=0;i<arquivos.length;i++){
                arquivo = arquivos[i].getAbsolutePath();
                nomeArqXml = arquivos[i].getName();
                //String nome_arq= arquivos[i].getName();
                //JOptionPane.showMessageDialog(null,"Nome Puro do Arquivo escolhido: " + nomeArqXml);
               //System.out.println("Arquivo escolhido: " + arquivo);
            }
        }
        return arquivo;
    }
    public void search(){
        nomePanel = new HashMap<Integer, String>();
        nomeTag = new HashMap<Integer, String>();
        qtdeTag = new HashMap<Integer, Integer>();
        nivTag = new HashMap<Integer, Integer>();
        File file = new File(fileName);
        SAXBuilder builder = new SAXBuilder();
        Document document;

        try{
            //System.out.println("Aquivo: "+fileName);
            document = builder.build(file);
            Element el = document.getRootElement();
            elOld = el;

           //System.out.println("Elemento Raiz: "+document.getRootElement().getName());
            if (!document.getRootElement().getName().equals("enviNFe") && !document.getRootElement().getName().equals("NFe") && !document.getRootElement().getName().equals("nfeProc")){
                JOptionPane.showMessageDialog(null, "Este Documento não contém informações sobre a NFe!");
                dispose();
            }
            painel_pai             .addTab(document.getRootElement().getName(), createJPanel(document.getRootElement().getName()));
            if (document.getRootElement().getName().equals("NFe")) nivelMax = 3;
            else nivelMax = 4;
            nomeTag.put(1, document.getRootElement().getName());
            nivTag.put(1, 0);
            trataElemento(el);
            //for (int i=1; i<indTag; i++){
            //    System.out.println("Tag: "+nomeTag.get(i)+" - Nivel: "+nivTag.get(i)+" - Tamanho: "+qtdeTag.get(i));
            //}
        } catch(JDOMException e){
                e.printStackTrace();
        } catch(IOException e){
                e.printStackTrace();
        }

    }
    private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
   //System.out.println("Elemento lido: "+el.getName()+" - tipo: "+el.getContent());
   //System.out.println("Tamanho da lista: "+tamanho);
        if (tamanho == 0){  //Elemento
            //qtdeTag.put(indTag-1, 0);
            String label = nomeExtenso.get(el.getQualifiedName().toString().trim());
            if ( label == null) label = el.getQualifiedName().toString().trim();
            //System.out.println(desloca1+(indElem+1)+"-E."+label+" = "+el.getText());
            //JOptionPane.showMessageDialog(null,"Nome da Tag Pai: "+el.getQualifiedName());
/*
            if (!achouId && el.getQualifiedName().equalsIgnoreCase("infnfe")) {
                setId(el.getAttributeValue("Id"));
                JOptionPane.showMessageDialog(null,"Achou Id: "+getId());
                achouId = true;
                createJLabel("lb_Id", "Id");
                createJTextField("tf_Id", getId());
            }
*/
            if (el.getQualifiedName().toString().trim().equalsIgnoreCase(("infCpl"))){
                linha = 50;
                createJLabel("lb_"+el.getQualifiedName().toString().trim(), label);
                createJTextArea("tf_"+el.getQualifiedName().toString().trim(), el.getText().toString());
            }  else {
                if (el.getQualifiedName().toString().trim().equals("cUF")) {
                }
                createJLabel("lb_"+el.getQualifiedName().toString().trim(), label);
                createJTextField("tf_"+el.getQualifiedName().toString().trim(), el.getText().toString());
            }
// inicio instancia campos da classe det
           //System.out.println("Campo do Det("+numDet+"): "+el.getQualifiedName().toString().trim()+" - Valor: "+el.getText().toString());
            if (!labelAtual.equals(labelAnt)){
               System.out.println("\nlabelAtual: "+labelAtual);
                indexCpo = 0;
           }
            indexCpo++;
           trataCampoLido(el.getQualifiedName().toString().trim(), el.getText().toString());
           System.out.println("    private String  "+el.getQualifiedName().toString().trim()+";");
            if (!labelAtual.equals(labelAnt)){
               labelAnt = labelAtual;
            }
            //campos.put(""+index+el.getQualifiedName().toString().trim(),el.getText().toString());
// inicio instancia campos da classe det
            indElem++;
        }else {
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                if (!el1.getQualifiedName().equals("Signature")){
                    if (el1.getQualifiedName().equals("protNFe")) nivelTag = 2;
                    if (el1.getContent().size() > 0 && el1.getText().trim().equals("")) {   // Tag     getText().trim().equals("")){ //Tag
                        indElem=0;
                        qtdeTag.put(indTag-1, tamanho);
                        //System.out.println("Encontrou Tag: "+el1.getQualifiedName()+" - Tamanho de getContent(): "+el1.getContent().size());
                        //System.out.println(desloca0+"<"+el1.getQualifiedName()+">"+" - isAncestor de ["+elOld.getName()+"]: "+el.isAncestor(elOld));
                        if (nivelTag < nivelMax) {
                            String labelPanel = el1.getQualifiedName();
                            String nome_do_Panel = labelPanel+indTag;
                            String nome_do_TabPane = "TP_"+labelPanel+indTag;
                            String label = nomeExtenso.get(labelPanel);
                            if ( label == null) label = labelPanel;
                            if (label.equalsIgnoreCase("det")) {
                                indDet++;
                                label += ""+indDet;
                                lendoDet = true;
                                numDet++;
                            } else {
                                lendoDet = false;
                            }
                            labelAtual = label;
                            if (!el1.getQualifiedName().equalsIgnoreCase("infnfe") && !el1.getQualifiedName().equalsIgnoreCase("protnfe")){
                                painel_pai             .addTab(label, createJPanel(nome_do_Panel));
                                nomePanel.put(indTag, nome_do_Panel);
                                direita = true;
                            }
                             if (!achouId && el1.getQualifiedName().equalsIgnoreCase("infnfe")) {
                                 //List listaAtrib = el1.getAttributes();
                                setId(el1.getAttributeValue("Id"));
                                //JOptionPane.showMessageDialog(null,"Achou Id: "+getId());
                                achouId = true;
                                createJLabel("lb_Id", "Id");
                                createJTextField("tf_Id", getId());
                             }
                           //  inicializacao de paineis
                            //System.out.println("Vai criar o tabbedPane: "+nome_do_TabPane+" com painel na posicao: "+indTabPanel);
                            //createJTabPanel(nome_do_TabPane, tabJPanel[indTabPanel]);
                        }
                        elOld = el1;
                        List atributos = el1.getAttributes();
                        //if (atributos.size() > 0){
                        //    for (int ia=0; ia<atributos.size(); ia++){ //Atributo
                        //        System.out.println(desloca1+(ia+1)+"-Atrib. "+atributos.get(ia).toString().substring(12,atributos.get(ia).toString().length()-1));
                        //    }
                        //}
                        nomeTag.put(indTag, el1.getName().toString().trim());
                        nivTag.put(indTag, nivelTag);
                        nivelTag++;
                        indTag++;
                    }
                    trataElemento(el1);
                }
            }
            nivelTag--;
        }
    }
private JLabel createJLabel(String name, String text) {
    JLabel label = new JLabel(text);
    label.setName(name);
    //System.out.println("Vai inserir o label criado com o nome: "+name+" na posicao: "+(indTabPanel)+" do Painel: "+tabJPanel[indTabPanel].getName()+" na linha: "+linha);
    if (direita) {
        coluna = 10;
    }
    else coluna = 420;
    linha = linha+20;  // temporario
    coluna = 20;  // temporario
    if (name.equals("lb_Id")) {
        label.setBounds(200, 350,  50, 30);
        label.setFont(new Font("Arial",Font.BOLD,18));
    } else {
        label.setBounds(coluna, linha,  250, 20);
        label.setFont(new Font("CourierNew", Font.BOLD, 10));
    }
    label.setHorizontalAlignment(JLabel.RIGHT);
    tabJPanel[indTabPanel].add(label);
    return label;
}
private JTextField createJTextField(String name, String text) {
    JTextField jtf =new JTextField();
    jtf.setName(name);
    jtf.setText(text);
    //System.out.println("Vai inserir o textfield criado com o nome: "+name+" na posicao: "+(indTabPanel)+" do Painel: "+tabJPanel[indTabPanel].getName()+" na linha: "+linha);
    if (name.equals("tf_Id")) {
        jtf.setBounds(260, 350,  490, 30);
        jtf.setFont(new Font("Arial",Font.BOLD,18));
        jtf.setForeground(new Color(255, 0, 0));
    } else {
        jtf.setBounds(coluna+260, linha,  650, 20);
        jtf.setForeground(new Color(0, 0, 255));
    }
    jtf.setBackground(new Color(255, 255, 210));
    if (direita){
        direita = false;
    } else direita = true;
    tabJPanel[indTabPanel].add(jtf);
    return jtf;
}
private JTextArea createJTextArea(String name, String text) {
    JTextArea jtf =new JTextArea();
    jtf.setName(name);
    jtf.setText(text);
    //System.out.println("Vai inserir o textarea criado com o nome: "+name+" na posicao: "+(indTabPanel)+" do Painel: "+tabJPanel[indTabPanel].getName()+" na linha: "+linha);
    jtf.setBounds(coluna+260, linha,  650, 350);
    jtf.setLineWrap(true);
    jtf.setBackground(new Color(255, 255, 210));
    jtf.setForeground(new Color(0, 0, 255));
    //jtf.setBorder(null);
    //jtf.setColumns(300);
    //jtf.setRows(5);
    if (direita){
        direita = false;
    } else direita = true;
    tabJPanel[indTabPanel].add(jtf);
    return jtf;
}
private JPanel createJPanel(String name) {
    indTabPanel++;
    JPanel panel = new JPanel();
    panel.setName(name);
    panel.setLayout(null);
    panel.setSize(900, 350);
    panel.setBackground(new Color(224, 223, 227));

    if (name.equalsIgnoreCase("nfe") || name.equalsIgnoreCase("nfeproc")){
        JLabel lbtitnfe = new JLabel("VISUALIZADOR DE NFe");
        lbtitnfe.setFont(new Font("Arial", Font.BOLD, 40));
        lbtitnfe.setForeground(Color.blue);
        //lbtitnfe.setHorizontalAlignment(JLabel.CENTER);
        //lbtitnfe.setVerticalAlignment(JLabel.CENTER);
        lbtitnfe.setBounds(280,180,650,50);
        panel.add(lbtitnfe);
    }
    tabJPanel[indTabPanel] = panel;
    //System.out.println("Panel criado com o nome: "+name+" na posicao: "+indTabPanel+" com o nome: "+tabJPanel[indTabPanel].getName());
    linha = -10;
    return panel;
}
private JTabbedPane createJTabPanel(String name, JPanel nomePanel) {
    JTabbedPane tabPane = new JTabbedPane();
    tabPane.setName(name);
    tabTabPane[indTabTabPane] = tabPane;
    indTabTabPane++;
     tabPane         .setBounds(50,50,900,350);
     tabPane         .setBackground(new Color(100,100,100));
     //nomePanel.add(tabPane);
     painel_pai.add(tabPane);
    return tabPane;
}


    private void preencheNomeExtenso(){
      //  labels para panel
        nomeExtenso.put("cobr", "Financeiro");
        nomeExtenso.put("transp", "Transportadora");
        nomeExtenso.put("ide", "Identificação");
        nomeExtenso.put("emit", "Emitente");
        nomeExtenso.put("dest", "Destinatário");
        nomeExtenso.put("total", "Totais");
        nomeExtenso.put("infProt", "Protocolo");
     //    campos com valores
        nomeExtenso.put("Id", "Chave da NFe");
        nomeExtenso.put("cUF", "Cod.UF");
        nomeExtenso.put("cNF", "Cod.Nota Fiscal");
        nomeExtenso.put("natOp", "Natureza de Operacao");
        nomeExtenso.put("indPag", "Indice Pagto");
        nomeExtenso.put("mod", "Modelo da NFe");
        nomeExtenso.put("serie", "Serie da NFe");
        nomeExtenso.put("nNF", "Numero da NFe");
        nomeExtenso.put("dEmi", "Data de Emissao");
        nomeExtenso.put("dSaiEnt", "Data da Saida/Entrada");
        nomeExtenso.put("tpNF", "Tipo de Nota Fiscal");
        nomeExtenso.put("cMunFG", "Codigo do Município");
        nomeExtenso.put("tpImp", "Tipo de Impressão");
        nomeExtenso.put("tpEmis", "Tipo de Emissão");
        nomeExtenso.put("cDV", "Código Verificador");
        nomeExtenso.put("tpAmb", "Ambiente(1-Produção/2-Homologação)");
        nomeExtenso.put("finNFe", "Finalidade da NFe");
        nomeExtenso.put("procEmi", "Tipo de Emissão");
        nomeExtenso.put("verProc", "Versão do Sistema Emissor");
        nomeExtenso.put("xNome", "Nome");
        nomeExtenso.put("xFant", "Fantasia");
        nomeExtenso.put("IE", "Insc.Estadual");
        nomeExtenso.put("xLgr", "Logradouro");
        nomeExtenso.put("nro", "Numero");
        nomeExtenso.put("xBairro", "Bairro");
        nomeExtenso.put("cMun", "Cod.Municipio");
        nomeExtenso.put("xMun", "Nome Municipio");
        nomeExtenso.put("cPais", "Cod.Pais");
        nomeExtenso.put("fone", "Telefone");
        nomeExtenso.put("xPais", "Nome Pais");
        nomeExtenso.put("cProd", "Cod.Produto");
        nomeExtenso.put("xProd", "Descr.Produto");
        nomeExtenso.put("CFOP", "Cod.Fiscal de Operacao");
        nomeExtenso.put("cEAN", "EAN");
        nomeExtenso.put("uCom", "Unid.Comercial");
        nomeExtenso.put("qCom", "Qtde Comercial");
        nomeExtenso.put("vUnCom", "Vlr Unit.Comercial");
        nomeExtenso.put("vProd", "Valor do Produto");
        nomeExtenso.put("cEANTrib", "Cod.EAN Tributável");
        nomeExtenso.put("uTrib", "Unidade Tributável");
        nomeExtenso.put("qTrib", "Qtde Tributável");
        nomeExtenso.put("vUnTrib", "Vlr Unit.Tributável");
        nomeExtenso.put("orig", "Origem");
        nomeExtenso.put("CST", "Cod.Sit.Tributária");
        nomeExtenso.put("modBC", "Tipo de Base de Cálculo");
        nomeExtenso.put("pRedBC", "% Redução Base Cálculo");
        nomeExtenso.put("pICMS", "% Redução ICMS");
        nomeExtenso.put("pPIS", "% Redução PIS");
        nomeExtenso.put("pCOFINS", "% Redução COFINS");
        nomeExtenso.put("infCpl", "Informações Adicionais");
        nomeExtenso.put("infAdic", "Informações Adicionais");
        nomeExtenso.put("modFrete", "Tipo de Frete");
        nomeExtenso.put("xEnder", "Endereço");
        nomeExtenso.put("qVol", "Qtde de Volumes");
        nomeExtenso.put("pesoL", "Peso Líquido");
        nomeExtenso.put("pesoB", "Peso Bruto");
        nomeExtenso.put("vBC", "Base de Calculo");
        nomeExtenso.put("vICMS", "Valor ICMS");
        nomeExtenso.put("vBCST", "Base Calc.Subst.Tributária");
        nomeExtenso.put("vST", "Valor Sit.Tributária");
        nomeExtenso.put("vFrete", "Valor do Frete");
        nomeExtenso.put("vSeg", "Valor do Seguro");
        nomeExtenso.put("vDesc", "Valor do Desconto");
        nomeExtenso.put("vIPI", "Valor do IPI");
        nomeExtenso.put("vPIS", "Valor do PIS");
        nomeExtenso.put("vCOFINS", "Valor do COFINS");
        nomeExtenso.put("vOutro", "Valor de Outros");
        nomeExtenso.put("vNF", "Valor da Nota Fiscal");
        nomeExtenso.put("verAplic", "Versão da Aplicação");
        nomeExtenso.put("chNFe", "Chave da NFe");
        nomeExtenso.put("dhRecbto", "Data e Hora do Recebimento");
        nomeExtenso.put("nProt", "Número do Protocolo");
        nomeExtenso.put("digVal", "Texto de Validação");
        nomeExtenso.put("cStat", "Status da NFe");
        nomeExtenso.put("xMotivo", "Situação da NFe");
        //nomeExtenso.put("", "");
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
            XMLReader xread = new XMLReader("0005");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_sair){
            dispose();  //System.exit(0);
        } else if (e.getSource() == bt_gerar){
            System.out.println("Clicou em gerar . . .");
            geraRegsFinais();
            gravaArqTxt();
        } else if (e.getSource() == bt_nova){
            painel_pai.removeAll();
            inicializaVariaveis();
            bt_gerar.setEnabled(true);
            fileName = escolheArq();
            achouId = false;
            search();
        }
    }

    private void trataCampoLido(String campo, String valor) {
        if (indexCpo == 1) {
            if (labelAnt.indexOf("det") != -1){
                gravaDet();
            }
        }
        if (labelAtual.indexOf("det") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" det com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                det = new Prod();
                icms = new ICMS00();
                pis = new PISAliq();
                cofins = new COFINSAliq();
            }
            switch (indexCpo) {
                case 1:
                    det.setcProd(valor);
                        break;
                case 2:
                    det.setcEAN(valor);
                        break;
                case 3:
                    det.setxProd(valor);
                        break;
                case 4:
                    det.setNCM(valor);
                        break;
                case 5:
                    det.setCFOP(valor);
                        break;
                case 6:
                    det.setuCom(valor);
                        break;
                case 7:
                    det.setqCom(valor);
                        break;
                case 8:
                    det.setvUnCom(valor);
                        break;
                case 9:
                    det.setvProd(valor);
                        break;
                case 10:
                    det.setcEANTrib(valor);
                        break;
                case 11:
                    det.setuTrib(valor);
                        break;
                case 12:
                    det.setqTrib(valor);
                        break;
                case 13:
                    det.setvUnTrib(valor);
                        break;
                case 14:
                    det.setvDesc(valor);
                        break;
                case 15:
                    det.setIndTot(valor);
                        break;
                case 16:
                    icms.setOrig(valor);
                        break;
                case 17:
                    icms.setCST(valor);
                        break;
                case 18:
                    icms.setModBC(valor);
                        break;
                case 19:
                    icms.setvBC(valor);
                        break;
                case 20:
                    icms.setpICMS(valor);
                        break;
                case 21:
                    icms.setvICMS(valor);
                        break;
                case 22:
                    //det.setqSelo(valor);
                        break;
                case 23:
                    //det.setcEnq(valor);
                        break;
                case 24:
                    //det.setpIPI(valor);
                        break;
                case 25:
                    //det.setvIPI(valor);
                        break;
                case 26:
                    pis.setpPIS(valor);
                        break;
                case 27:
                    pis.setvPIS(valor);
                        break;
                case 28:
                    cofins.setpCOFINS(valor);
                        break;
                case 29:
                    cofins.setvCOFINS(valor);
                        break;
                default:
                        break;
	  }
      } else if (labelAtual.indexOf("Identificação") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                ide = new Ide();
            }
            switch (indexCpo) {
                case 1:
                    ide.setcUF(valor);
                         break;
                case 2:
                    ide.setcNF(valor);
                         break;
                case 3:
                    ide.setNatOp(valor);
                         break;
                case 4:
                    ide.setIndPag(valor);
                         break;
                case 5:
                    ide.setMod(valor);
                         break;
                case 6:
                    ide.setSerie(valor);
                         break;
                case 7:
                    ide.setnNF(valor);
                         break;
                case 8:
                    ide.setDhEmi(valor);
                         break;
                case 9:
                    ide.setDhSaiEnt(valor);
                         break;
                case 10:
                    //ide.sethSaiEnt(valor);
                         break;
                case 11:
                    ide.setTpNF(valor);
                         break;
                case 12:
                    ide.setcMunFG(valor);
                         break;
                case 13:
                    ide.setTpImp(valor);
                         break;
                case 14:
                    ide.setTpEmis(valor);
                         break;
                case 15:
                    ide.setcDV(valor);
                         break;
                case 16:
                    ide.setTpAmb(valor);
                         break;
                case 17:
                    ide.setFinNFe(valor);
                         break;
                case 18:
                    ide.setProcEmi(valor);
                         break;
                case 19:
                    ide.setVerProc(valor);
                         break;
                default:
                        break;
           }
      } else if (labelAtual.indexOf("Emitente") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                emit = new Emit();
                enderemit = new EnderEmit();
            }
            switch (indexCpo) {
                case 1:
                    emit.setCNPJ(valor);
                         break;
                case 2:
                    emit.setxNome(valor);
                         break;
                case 3:
                    enderemit.setxLgr(valor);
                         break;
                case 4:
                    enderemit.setNro(valor);
                         break;
                case 5:
                    enderemit.setxBairro(valor);
                         break;
                case 6:
                    enderemit.setcMun(valor);
                         break;
               case 7:
                    enderemit.setxMun(valor);
                         break;
                case 8:
                    enderemit.setUF(valor);
                         break;
                case 9:
                    enderemit.setCEP(valor);
                         break;
                case 10:
                    enderemit.setcPais(valor);
                         break;
                case 11:
                    enderemit.setxPais(valor);
                         break;
                case 12:
                    enderemit.setFone(valor);
                         break;
                 case 13:
                    emit.setIE(valor);
                         break;
                case 14:
                    emit.setCRT(valor);
                         break;
                default:
                        break;
           }
      } else if (labelAtual.indexOf("Destinatário") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                dest = new Dest();
                enderdest = new EnderDest();
            }
            switch (indexCpo) {
                case 1:
                    dest.setCNPJ(valor);
                         break;
                case 2:
                    dest.setxNome(valor);
                         break;
                case 3:
                    enderdest.setxLgr(valor);
                         break;
                case 4:
                    enderdest.setNro(valor);
                         break;
                case 5:
                    enderdest.setxCpl(valor);
                         break;
                case 6:
                    enderdest.setxBairro(valor);
                         break;
                case 7:
                    enderdest.setcMun(valor);
                         break;
               case 8:
                    enderdest.setxMun(valor);
                         break;
                case 9:
                    enderdest.setUF(valor);
                         break;
                case 10:
                    enderdest.setCEP(valor);
                         break;
                case 11:
                    enderdest.setcPais(valor);
                         break;
                case 12:
                    enderdest.setxPais(valor);
                         break;
                case 13:
                    enderdest.setFone(valor);
                         break;
                 case 14:
                    dest.setIE(valor);
                         break;
                default:
                        break;
           }
      } else if (labelAtual.indexOf("Transportadora") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                transp = new Transp();
                vol = new Vol();
            }
            switch (indexCpo) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 13:
                case 12:
                    vol.setPesoL(valor);
                         break;
                default:
                        break;
           }
      } else if (labelAtual.indexOf("Financeiro") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                financ = new Dup();
            }
            switch (indexCpo) {
                case 1:
                    financ.setnDup(valor);
                         break;
                case 2:
                    financ.setdVenc(valor);
                         break;
                case 3:
                    financ.setvDup(valor);
                         break;
                default:
                        break;
           }
      } else if (labelAtual.indexOf("Totais") != -1){
            System.out.println("Tratando a Tag "+labelAtual+" com o campo: "+campo+" e valor: "+valor);
            if (indexCpo == 1) {
                total = new ICMSTot();
            }
            switch (indexCpo) {
                case 1:
                     total.setvBC(valor);
                         break;
               case 2:
                     total.setvICMS(valor);
                         break;
                case 3:
                case 4:
                case 5:
                     total.setvProd(valor);
                         break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    total.setvNF(valor);
                         break;
                default:
                        break;
           }
        }
    }

    private void gravaDet() {
            System.out.println("Gravando o registro 04 . . .");
            reg04+= "04000000000EN "+det.getcEAN()
                  +preencheCaracter(20, " ")
                  +"EA "
                  +"00000"
                  +formatZeros(det.getqCom(),15,2) //  qtde
                  +preencheCaracter(3, " ")
                  +preencheCaracter(16, "0")
                  +formatZeros(det.getvProd(),15,2)  //  vlrProd
                  +preencheCaracter(15, "0")
                  +formatZeros(det.getvUnCom(),15,2)  //  vlr unit
                  +preencheCaracter(40, " ")
                  +formatZeros(det.getqTrib(),15,3)  //  peso pos 175 a 189
                  +preencheCaracter(45, " ")
                  +preencheCaracter(55, "0")
                  +formatZeros(icms.getpICMS(),5,2)  // perc.Icms pos 329 a 333
                  +formatZeros(icms.getvICMS(),15,2)  // vlr icms pos 334 a 348
                  +preencheCaracter(26, " ")
                  +formatZeros("0.00",15,2)    //  vlr reduzido
                  + (char)13 + (char)10;
            System.out.println("Reg04: ["+reg04+"]");
            //reg04+= reg04;

    }

    private void geraRegsFinais() {
            System.out.println("Gravando o registro 01 . . .");
            String reg01 = "01"
                  +preencheCaracter(6, " ")
                  +ide.getnNF().substring(0,6) //
                  +ide.getSerie().substring(0,1)+"  " //
                  +preencheCaracter(2, " ")
                  +ide.getDhEmi().substring(0,4)+ide.getDhEmi().substring(5,7)+ide.getDhEmi().substring(8,10)
                  +ide.getDhSaiEnt().substring(11,13)+ide.getDhSaiEnt().substring(14,16)
                  +ide.getDhSaiEnt().substring(0,4)+ide.getDhSaiEnt().substring(5,7)+ide.getDhSaiEnt().substring(8,10)
                  +preencheCaracter(4, "0")
                  +ide.getDhSaiEnt().substring(0,4)+ide.getDhSaiEnt().substring(5,7)+ide.getDhSaiEnt().substring(8,10)
                  +preencheCaracter(4, "0")
                  +det.getCFOP()
                  +preencheCaracter(150, " ")
                  +dest.getCNPJ()  //  cnpjemit pos inic. 252
                  +preencheCaracter(64, " ")
                  +emit.getCNPJ()  //  cnpjemit pos inic. 252
                  + (char)13 + (char)10;
            System.out.println("Reg01: ["+reg01+"]");

            System.out.println("Gravando o registro 02 . . .");
            String reg02 = "02"
                  +preencheCaracter(15, " ")
                  +financ.getdVenc().substring(0,4)+financ.getdVenc().substring(5,7)+financ.getdVenc().substring(8,10)
                  +preencheCaracter(11, " ")
                  +formatZeros(financ.getvDup(),15,2)  //
                  + (char)13 + (char)10;
            System.out.println("Reg09: ["+reg02+"]");

            System.out.println("Gravando o registro 03 . . .");
            String reg03 = "03"
                  +preencheCaracter(121, "0")
                  + (char)13 + (char)10;
            System.out.println("Reg03: ["+reg03+"]");

            System.out.println("Gravando o registro 09 . . .");
            String reg09 = "09"
                  +preencheCaracter(34, "0")
                  +formatZeros(vol.getPesoL(),15,3) //
                  +preencheCaracter(120, "0")
                  +formatZeros(total.getvBC(),15,2)  //
                  +formatZeros(total.getvICMS(),15,2)  //
                  +preencheCaracter(90, "0")
                  +formatZeros(total.getvProd(),15,2)  //
                  + (char)13 + (char)10;
            System.out.println("Reg09: ["+reg09+"]");
            regArq+= reg01 + reg02 + reg03 + reg04 + reg09;

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
    private void gravaArqTxt(){
         try {
            Date data = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMM");
            String datahoje = sdf.format(data);
            int numseq = 0;
            boolean arqJaExiste = true;
            String nome_do_arquivo = "", nome_do_arqLidos = "", nomeArq = "";
            String pastaLidos       = drive+fsep+"empr0168"+fsep+"estoque"+fsep+"mercador"+fsep+"lidos"+fsep;
            String pastaMercador    = drive+fsep+"empr0168"+fsep+"estoque"+fsep+"mercador"+fsep;
//  Verifica se o arquivo TXT já existe na pasta de destino
            while(arqJaExiste){
                String numseqStr = ""+numseq;
                if (numseqStr.length() == 1) numseqStr = "0"+numseqStr;
                nomeArq = "NF"+datahoje+numseqStr;
                nome_do_arqLidos = pastaLidos+nomeArq+".TXT";
                try{
                    FileReader fr = new FileReader(nome_do_arqLidos);
                    fr.close();
                    numseq++;
                } catch (FileNotFoundException efnf) {
                    nome_do_arquivo = pastaMercador+nomeArq+".TXT";
                    try{
                        FileReader fr = new FileReader(nome_do_arquivo);
                        fr.close();
                        numseq++;
                    } catch (FileNotFoundException efnf1) {
                        arqJaExiste = false;
                    }
                }

             }
//   Grava o arquivo TXT na pasta de destino
            nome_do_arquivo = pastaMercador+nomeArq+".TXT";
            FileWriter out = new FileWriter(nome_do_arquivo);
            out.write(regArq);
            out.close();
            bt_gerar.setEnabled(false);

            String arq_xml         = fileName;
   //  move o arq XML para a pasta Lidos 
            File arquivo    = new File(arq_xml);
            char chartxt[]  = new char[(int)arquivo.length()];
            FileReader entrada;
            try {
                entrada = new FileReader(arq_xml);
                entrada.read(chartxt);  //(res);
                entrada.close();

                String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                String arq_saida_xml = caminho_escolha+fsep+"lidos"+fsep+nomeArqXml;
                System.out.println("Arquivo xml lido: "+arq_saida_xml);
                FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Erro ao processar arq.xml: arq_xml\n"+ex);
                //Logger.getLogger(recepcaonfe.class.getName()).log(Level.SEVERE, null, ex);
            }
   //  apaga o arq XML da pasta XML
            try {
            File arqudel    = new File(arq_xml);
            if (arqudel.isFile()){
                arqudel.delete();  //  deleteOnExit();
                //JOptionPane.showMessageDialog(null, "O arquivo: "+arq_xml+" foi apagado com sucesso!");
             }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar apagar o arquivo: "+arq_xml+"\n"+e);
            }

            JOptionPane.showMessageDialog(null,"Foi Gerado   o arquivo: "+nome_do_arquivo);
        }
        catch(IOException exc)
        {
            JOptionPane.showMessageDialog(null,"Erro ao gravar no arquivo !\nErro: "+exc);
        }

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
}
