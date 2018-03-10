/*

Descrição: Manutenção da Tabela de Parametros Digitais da NFe e NFCe

Autor: Videoaulasneri - email: videoaulasneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
//import Relatorios.Impressao;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
public class Pardigital extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Parametro Digital");

    //Labels dos campos da tabela;
    JLabel label_codigo              = new JLabel("Codigo: ");
    JLabel label_empresa             = new JLabel("Empresa: ");
    JLabel label_senha_truststore    = new JLabel("Senha_truststore: ");
    JLabel label_senha_token         = new JLabel("Senha_token: ");
    JLabel label_senha_keystore      = new JLabel("Senha_keystore: ");
    JLabel label_serie55             = new JLabel("Serie55: ");
    JLabel label_serie65             = new JLabel("Serie65: ");
    JLabel label_verproc             = new JLabel("Verproc: ");
    JLabel label_idtoken             = new JLabel("Idtoken: ");
    JLabel label_csc                 = new JLabel("Csc Homologação: ");
    JLabel label_csc_prod            = new JLabel("Csc Produção: ");

    //Gerando os Bot??es
    JButton botao_primeiro_registro  = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/primeiro_registro.gif")));
    JButton botao_registro_anterior  = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/registro_anterior.gif")));
    JButton botao_proximo_registro   = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/proximo_registro.gif")));
    JButton botao_ultimo_registro    = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/ultimo_registro.gif")));
    JButton botao_novo               = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo_registro.gif")));
    JButton botao_gravar             = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/gravar_registro.gif")));
    JButton botao_alterar            = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar_registro.gif")));
    JButton botao_excluir            = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/delete.gif")));
    JButton botao_imprimir           = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.gif")));
    JButton botao_sair               = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/sair.gif")));
    JButton botao_pesquisa           = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/localizar.gif")));
    //JTextFields dos campos das tabelas
    JTextField tf_codigo             = new JTextField();
    JTextField tf_empresa            = new JTextField();
    JPasswordField tf_senha_truststore   = new JPasswordField();
    JPasswordField tf_senha_token        = new JPasswordField();
    JPasswordField tf_senha_keystore     = new JPasswordField();
    JTextField tf_serie55            = new JTextField();
    JTextField tf_serie65            = new JTextField();
    JTextField tf_verproc            = new JTextField();
    JTextField tf_idtoken            = new JTextField();
    JTextField tf_csc                = new JTextField();
    JTextField tf_csc_prod           = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox<String> cb_pesquisa    = new JComboBox<>();
    JComboBox<String> cb_pesq_lab    = new JComboBox<>();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[10];
    String [] lab_tabela             = new String[10];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[10];
    String [] tab_cb_cpo_exibe       = new String[10];
    String [] tab_cb_cpo_assoc       = new String[10];
    JTextField [] tab_cb_tf          = new JTextField[10];
    Statement [] tab_cb_st           = new Statement[10];
    ResultSet [] tab_cb_rs           = new ResultSet[10];
    JComboBox [] tab_cb_cb           = new JComboBox[10];
    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 01;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_empresa;
    PreparedStatement ps_aux;
    int empresa = 0;

    ResultSet rs_empresa;
    Statement st_empresa;
    JComboBox<String> cb_empresa            = new JComboBox<>();
    JLabel label_psqempresa           = new JLabel("Pesquisar: ");
    JTextField tf_psqempresa          = new JTextField();
    ButtonGroup bg_psqempresa         = new ButtonGroup();
    JRadioButton rb_inic_psqempresa   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqempresa   = new JRadioButton("Meio");



    public Pardigital(int empresa, Connection conexao)
    {
        setTitle("Formulario de Manutencao de pardigital");
        setSize(1010, 680);
        setLocation(135,85);
        setResizable(true);
        this.empresa = empresa;
        this.connection = conexao;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "N?o");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane()        .setLayout(null);
        panel_pesquisa          .setLayout(null);
        panel_pesquisa          .setBounds(140,30,780,50);
        lb_pesquisa             .setBounds(10,20,100,20);
        cb_pesq_lab             .setBounds(80,20,150,20);
        tf_pesquisa             .setBounds(240,20,200,20);
        botao_pesquisa          .setBounds(450,15,50,30);
        cb_pesquisa             .setBounds(520,20,200,20);
        lb_titulo               .setBounds(10,10,500,30);
        lb_titulo               .setFont(new Font("Arial",Font.BOLD,16));
        lb_titulo               .setForeground(Color.black);

    //Gera cor frente dos lanbels setForegroundColor()
        label_codigo            .setForeground(Color.black);
        label_empresa           .setForeground(Color.black);
        label_senha_truststore  .setForeground(Color.black);
        label_senha_token       .setForeground(Color.black);
        label_senha_keystore    .setForeground(Color.black);
        label_serie55           .setForeground(Color.black);
        label_serie65           .setForeground(Color.black);
        label_verproc           .setForeground(Color.black);
        label_idtoken           .setForeground(Color.black);
        label_csc               .setForeground(Color.black);
        label_csc_prod          .setForeground(Color.black);


    //alinha os labels a direita
        label_codigo            .setHorizontalAlignment(JLabel.RIGHT);
        label_empresa           .setHorizontalAlignment(JLabel.RIGHT);
        label_senha_truststore  .setHorizontalAlignment(JLabel.RIGHT);
        label_senha_token       .setHorizontalAlignment(JLabel.RIGHT);
        label_senha_keystore    .setHorizontalAlignment(JLabel.RIGHT);
        label_serie55           .setHorizontalAlignment(JLabel.RIGHT);
        label_serie65           .setHorizontalAlignment(JLabel.RIGHT);
        label_verproc           .setHorizontalAlignment(JLabel.RIGHT);
        label_idtoken           .setHorizontalAlignment(JLabel.RIGHT);
        label_csc               .setHorizontalAlignment(JLabel.RIGHT);
        label_csc_prod          .setHorizontalAlignment(JLabel.RIGHT);


    //Gera cor frente dos texfields setForegroundColor()
        tf_codigo               .setForeground(Color.black);
        tf_empresa              .setForeground(Color.black);
        tf_senha_truststore     .setForeground(Color.black);
        tf_senha_token          .setForeground(Color.black);
        tf_senha_keystore       .setForeground(Color.black);
        tf_serie55              .setForeground(Color.black);
        tf_serie65              .setForeground(Color.black);
        tf_verproc              .setForeground(Color.black);
        tf_idtoken              .setForeground(Color.black);
        tf_csc                  .setForeground(Color.black);
        tf_csc_prod             .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_codigo               .setBackground(Color.white);
        tf_empresa              .setBackground(Color.white);
        tf_senha_truststore     .setBackground(Color.white);
        tf_senha_token          .setBackground(Color.white);
        tf_senha_keystore       .setBackground(Color.white);
        tf_serie55              .setBackground(Color.white);
        tf_serie65              .setBackground(Color.white);
        tf_verproc              .setBackground(Color.white);
        tf_idtoken              .setBackground(Color.white);
        tf_csc                  .setBackground(Color.white);
        tf_csc_prod             .setBackground(Color.white);

        botao_primeiro_registro .setBackground(Color.red);
        botao_registro_anterior .setBackground(Color.red);
        botao_proximo_registro  .setBackground(Color.red);
        botao_ultimo_registro   .setBackground(Color.red);
        botao_novo              .setBackground(Color.red);
        botao_gravar            .setBackground(Color.red);
        botao_alterar           .setBackground(Color.red);
        botao_excluir           .setBackground(Color.red);
        botao_imprimir          .setBackground(Color.red);
        botao_sair              .setBackground(Color.red);
        panel_pesquisa          .setBackground(Color.red);

        //Posicionando os botoes com setBounds()
        botao_primeiro_registro  .setBounds(200,125,40,30);
        botao_registro_anterior  .setBounds(250,125,40,30);
        botao_proximo_registro   .setBounds(300,125,40,30);
        botao_ultimo_registro    .setBounds(350,125,40,30);
        botao_novo               .setBounds(400,125,40,30);
        botao_gravar             .setBounds(450,125,40,30);
        botao_alterar            .setBounds(500,125,40,30);
        botao_excluir            .setBounds(550,125,40,30);
        botao_imprimir           .setBounds(600,125,40,30);
        botao_sair               .setBounds(670,125,40,30);

        //Mensagens dos Botoes
        botao_primeiro_registro  .setToolTipText("Volta para o primeiro Registro");
        botao_registro_anterior  .setToolTipText("Volta para o Registro Anterior");
        botao_proximo_registro   .setToolTipText("Avanca para o proximo Registro");
        botao_ultimo_registro    .setToolTipText("Avanca para o ultimo Registro");
        botao_novo               .setToolTipText("Insere um novo registro");
        botao_gravar             .setToolTipText("Grava os dados cadastrados");
        botao_alterar            .setToolTipText("Regrava os dados cadastrados");
        botao_excluir            .setToolTipText("Excluir o Registro corrente");
        botao_imprimir           .setToolTipText("Imprimir a Tabela");
        botao_sair               .setToolTipText("Voltar ao Menu Principal");
        botao_pesquisa           .setToolTipText("Pesquisa/Filtra o Texto digitado para o Campo a Pesquisar");

        tf_senha_truststore     .setToolTipText("Senha do arquivo JKS(arq. certificado.jks)");
        tf_senha_token          .setToolTipText("Senha do Certificado Digital Tipo A3(Cartão)");
        tf_senha_keystore       .setToolTipText("Senha do Certificado Digital Tipo A1(arq. certificado.pfx)");
        tf_serie55              .setToolTipText("Série do Modelo 55 (NFe)");
        tf_serie65              .setToolTipText("Série do Modelo 65 (NFCe)");
        tf_verproc              .setToolTipText("Versão do Schema da NFe");
        tf_idtoken              .setToolTipText("Código de Token (mod.65) da Empresa Emitente.Ver com o Contador da Empresa");
        tf_csc                  .setToolTipText("Código de CSC (mod.65) de Homologação da Empresa Emitente.Ver com o Contador da Empresa");
        tf_csc_prod             .setToolTipText("Código de CSC (mod.65) de Produção da Empresa Emitente.Ver com o Contador da Empresa");


        //adActionListener(this)
        botao_primeiro_registro  .addActionListener(this);
        botao_registro_anterior  .addActionListener(this);
        botao_proximo_registro   .addActionListener(this);
        botao_ultimo_registro    .addActionListener(this);
        botao_novo               .addActionListener(this);
        botao_gravar             .addActionListener(this);
        botao_alterar            .addActionListener(this);
        botao_excluir            .addActionListener(this);
        botao_imprimir           .addActionListener(this);
        botao_sair               .addActionListener(this);
        cb_pesq_lab              .addActionListener(this);
        //tf_pesquisa              .addActionListener(this);
        botao_pesquisa           .addActionListener(this);
        cb_pesquisa              .addActionListener(this);
        jTable1                  .addMouseListener(this);
        jTable1                  .addKeyListener(this);
        tf_psqempresa            .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqempresa            .addKeyListener(this);
        tf_empresa               .addKeyListener(this);
        tf_empresa               .addFocusListener(this);
        cb_empresa               .addActionListener(this);

        
        //Posicionando os componentes labels e textfields da tabela
        label_codigo             .setBounds(100,165,150,20);
        tf_codigo                .setBounds(250,165,88,20);
        label_empresa            .setBounds(100,190,150,20);
        tf_empresa               .setBounds(250,190,88,20);
        cb_empresa               .setBounds(348,190, 200,20);
        label_psqempresa         .setBounds(558,190, 100,20);
        tf_psqempresa            .setBounds(623,190, 100,20);
        rb_inic_psqempresa       .setBounds(718,190, 60,20);
        rb_meio_psqempresa       .setBounds(778,190, 55,20);
        label_senha_truststore   .setBounds(100,215,150,20);
        tf_senha_truststore      .setBounds(250,215,160,20);
        label_senha_token        .setBounds(100,240,150,20);
        tf_senha_token           .setBounds(250,240,160,20);
        label_senha_keystore     .setBounds(100,265,150,20);
        tf_senha_keystore        .setBounds(250,265,160,20);
        label_serie55            .setBounds(100,290,150,20);
        tf_serie55               .setBounds(250,290,39,20);
        label_serie65            .setBounds(100,315,150,20);
        tf_serie65               .setBounds(250,315,39,20);
        label_verproc            .setBounds(100,340,150,20);
        tf_verproc               .setBounds(250,340,240,20);
        label_idtoken            .setBounds(100,365,150,20);
        tf_idtoken               .setBounds(250,365,63,20);
        label_csc                .setBounds(100,390,150,20);
        tf_csc                   .setBounds(250,390,320,20);
        label_csc_prod           .setBounds(100,415,150,20);
        tf_csc_prod              .setBounds(250,415,320,20);
//Posicao do ultimo cpo da Tabela no Formulario. Utiliz.para Iniciar a exibicao do JTable
        posUltLabel           = 390;
        panel_pesquisa        .add(lb_pesquisa);
        panel_pesquisa        .add(cb_pesq_lab);
        panel_pesquisa        .add(tf_pesquisa);
        panel_pesquisa        .add(botao_pesquisa);
        panel_pesquisa        .add(cb_pesquisa);
        getContentPane()      .add(panel_pesquisa);
        getContentPane()      .add(lb_titulo);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
           },
           new String [] {
                "Codigo",
                "Empresa",
                "Serie55",
                "Serie65",
                "Verproc",
                "Idtoken",
                "Csc"
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_pardigital());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_codigo);
        getContentPane()      .add(tf_codigo);
        getContentPane()      .add(label_empresa);
        getContentPane()      .add(tf_empresa);
        getContentPane()      .add(cb_empresa);
        getContentPane()      .add(label_psqempresa);
        getContentPane()      .add(tf_psqempresa);
        getContentPane()      .add(rb_inic_psqempresa);
        getContentPane()      .add(rb_meio_psqempresa);
        bg_psqempresa          .add(rb_inic_psqempresa);
        bg_psqempresa          .add(rb_meio_psqempresa);
        getContentPane()      .add(label_senha_truststore);
        getContentPane()      .add(tf_senha_truststore);
        getContentPane()      .add(label_senha_token);
        getContentPane()      .add(tf_senha_token);
        getContentPane()      .add(label_senha_keystore);
        getContentPane()      .add(tf_senha_keystore);
        getContentPane()      .add(label_serie55);
        getContentPane()      .add(tf_serie55);
        getContentPane()      .add(label_serie65);
        getContentPane()      .add(tf_serie65);
        getContentPane()      .add(label_verproc);
        getContentPane()      .add(tf_verproc);
        getContentPane()      .add(label_idtoken);
        getContentPane()      .add(tf_idtoken);
        getContentPane()      .add(label_csc);
        getContentPane()      .add(tf_csc);
        getContentPane()      .add(label_csc_prod);
        getContentPane()      .add(tf_csc_prod);
        getContentPane()      .add(jScrollPane);

        //adicionando os botoes no getContentPane()
        getContentPane()      .add(botao_primeiro_registro);
        getContentPane()      .add(botao_registro_anterior);
        getContentPane()      .add(botao_proximo_registro);
        getContentPane()      .add(botao_ultimo_registro);
        getContentPane()      .add(botao_novo);
        getContentPane()      .add(botao_gravar);
        getContentPane()      .add(botao_alterar);
        getContentPane()      .add(botao_excluir);
        getContentPane()      .add(botao_imprimir);
        getContentPane()      .add(botao_sair);

        cpo_tabela[0]         = "codigo";
        cpo_tabela[1]         = "empresa";
        cpo_tabela[2]         = "senha_truststore";
        cpo_tabela[3]         = "senha_token";
        cpo_tabela[4]         = "senha_keystore";
        cpo_tabela[5]         = "serie55";
        cpo_tabela[6]         = "serie65";
        cpo_tabela[7]         = "verproc";
        cpo_tabela[8]         = "idtoken";
        cpo_tabela[9]         = "csc";
        lab_tabela[0]         = "Codigo";
        lab_tabela[1]         = "Empresa";
        lab_tabela[2]         = "Senha_truststore";
        lab_tabela[3]         = "Senha_token";
        lab_tabela[4]         = "Senha_keystore";
        lab_tabela[5]         = "Serie55";
        lab_tabela[6]         = "Serie65";
        lab_tabela[7]         = "Verproc";
        lab_tabela[8]         = "Idtoken";
        lab_tabela[9]         = "Csc";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[1]          = tf_empresa;
        tab_cb_st[1]          = st_empresa;
        tab_cb_rs[1]          = rs_empresa;
        tab_cb_cb[1]          = cb_empresa;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[1]     = "empresa";
        tab_cb_cpo_assoc[1]   = "codigo";
        tab_cb_cpo_exibe[1]   = "razao";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        driver                = "org.postgresql.Driver";
        url                   = "jdbc:postgresql://localhost/nfefacil";
        usuario               = "postgres";
        senha                 = "nerizon";
        try
        {
              //Class.forName(driver);
              //connection = DriverManager.getConnection(url,usuario,senha);
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_empresa  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_empresa, rs_empresa, cb_empresa, 1);

              //mostra_conteudo_nos_campos();
              preencher_jtable("tudo");
              //cb_pesquisa.addActionListener(this);
              stat_aux = true;
              clicado  = true;
              novo_registro();
        }
        catch(Exception erro_class)
        {
               JOptionPane.showMessageDialog(null,"Driver nao localizado: "+erro_class);
        }
        //catch(SQLException erro_sql)
        //{
        //     JOptionPane.showMessageDialog(null,"Nao conseguiu conectar ao banco "+erro_sql);
        //}
        //catch(ClassNotFoundException erro_class)
        //{
        //       JOptionPane.showMessageDialog(null,"Driver nao localizado: "+erro_class);
        //}
    }
    public void preencher_cb_pesq_lab(){
         try {
             cb_pesq_lab.removeAllItems();
             for (int i=0 ;i<lab_tabela.length; i++){
                  cb_pesq_lab.addItem(lab_tabela[i]);
             }
         cb_pesq_lab.setSelectedIndex(indice_pesquisa);
         } catch(Exception e){
         }
    }
    public void preencher_cb_pesquisa(String acaoFiltro){
         this.acaoFiltro = acaoFiltro;
         try {
             cb_pesquisa.removeAllItems();
             String sql_query = "";
             qtRegTab = 0;
             if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 if (cb_pesq_lab.getSelectedIndex() >= 2) {
                    sql_query = "select * from pardigital where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
                 } else {
                    sql_query = "select * from pardigital where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText();
                 }
             } else {
                  sql_query = "select * from pardigital";
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("empresa"));
             }
             resultset.last();
         } catch (SQLException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(null, "Erro ao preencher o ComboBox Pesquisa: "+ex);
         }
     }
    public void selecionaJtable(int reg){
        jTable1.setRowSelectionInterval(reg, reg);
    }
    public void preencher_jtable(String acaoFiltro)
    {
        if ((qtRegTab*18) >= (640-(posUltLabel+30)))
            jScrollPane           .setBounds(5, posUltLabel+30,990, 640-(posUltLabel+30));
        else if ((qtRegTab*18) > 150)
            jScrollPane           .setBounds(5, 640-(qtRegTab*18),990, (qtRegTab*18));
        else
            jScrollPane           .setBounds(5, 490, 990, 150);
        repaint();
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(120);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from pardigital");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar pardigital: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 if (cb_pesq_lab.getSelectedIndex() >= 2) {
                    sqlquery = "select * from pardigital where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%' order by "+cpo_tabela[0];
                 } else {
                    sqlquery = "select * from pardigital where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText() + " order by "+cpo_tabela[0];
                 }
            } else {
                sqlquery = "select * from pardigital order by "+cpo_tabela[0];
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("codigo"),
                resultset.getString("empresa"),
                resultset.getString("serie55"),
                resultset.getString("serie65"),
                resultset.getString("verproc"),
                resultset.getString("idtoken"),
                resultset.getString("csc"),
                  }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: pardigital: "+qtreg);
            resultset.last();
        }
        catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"Erro ao listar no JTable "+erro);
        }
    }
   public void lookandfeel()
   {
        try
        {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception erro)
        {
            JOptionPane.showMessageDialog(null, "N??o conseguiu setar o novo LookAndFeel!!!");
        }
    }
    public void mostra_conteudo_nos_campos()
    {
           clicado = false;
           try
           {
               String data;
               tf_codigo             .setText(resultset.getString("codigo"));
               tf_empresa            .setText(resultset.getString("empresa"));
               tf_senha_truststore   .setText(resultset.getString("senha_truststore"));
               tf_senha_token        .setText(resultset.getString("senha_token"));
               tf_senha_keystore     .setText(resultset.getString("senha_keystore"));
               tf_serie55            .setText(resultset.getString("serie55"));
               tf_serie65            .setText(resultset.getString("serie65"));
               tf_verproc            .setText(resultset.getString("verproc"));
               tf_idtoken            .setText(resultset.getString("idtoken"));
               tf_csc                .setText(resultset.getString("csc"));
               tf_csc_prod           .setText(resultset.getString("csc_prod"));

               posiciona_combo_e_jtable();
               navega =0;
           }
           catch(Exception erro_sql)
           {
               if (navega == 1) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
               else if (navega == 2) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel avancar pois voce ja esta no ultimo registro da tabela");
               else {
                   JOptionPane.showMessageDialog(null,"Nenhum Registro foi Encontrado!");
                   novo_registro();
               }
           }
           clicado = true;
//Desabilita campo chave para alteracao/exclusao 
           tf_codigo.setEditable(false);
           botao_gravar.setEnabled(false);
           botao_alterar.setEnabled(true);
           botao_excluir.setEnabled(true);
     }
    public void posiciona_combo_e_jtable()
    {
        try {
            clicado = false;
            cb_pesquisa.setSelectedItem(tf_aux_pesq.getText());
            clicado = true;
            int reg = cb_pesquisa.getSelectedIndex();
            if (reg != -1) {
                 //System.out.println("selecionaJtable() reg: "+reg);
                 //selecionaJtable(reg);
            }
            ehnovo = false;
            for (int i=0; i<tab_cb_tab_box.length; i++){
                if ( tab_cb_tab_box[i] != null ) {
                    if (!tab_cb_tf[i].getText().equals("")){
                        mostra_tf_ref_cb_aux(tab_cb_st[i], tab_cb_rs[i], tab_cb_cb[i], tab_cb_tf[i], i);
                    } else{
                        tab_cb_cb[i].setSelectedIndex(-1);
                    }
                }
            }
        } catch(Exception erro){
             erro.printStackTrace();
             JOptionPane.showMessageDialog(null,"Nao foi possivel mostrar os dados - metodo posiciona_combo_e_jtable()! \nErro: "+erro);
        } 
    }

    //Acoes
      public void actionPerformed(ActionEvent acao)
      {
            if (acao.getSource() == botao_primeiro_registro)
                vai_primeiro_registro();
            else if (acao.getSource() == botao_proximo_registro)
                vai_proximo_registro();
            else if (acao.getSource() == botao_registro_anterior)
                vai_registro_anterior();
            else if (acao.getSource() == botao_ultimo_registro)
                vai_ultimo_registro();
            else if (acao.getSource() == botao_gravar){
                if (check_textField())
                    gravar();
            }
            else if (acao.getSource() == botao_excluir)
                excluir();
            else if (acao.getSource() == botao_alterar){
                if (check_textField())
                    alterar();
            }
            else if (acao.getSource() == botao_novo)
                novo_registro();
            else if (acao.getSource() == botao_pesquisa)
                pesquisadigitacao();
            else if (acao.getSource() == cb_pesquisa){
                if (clicado){
                   //JOptionPane.showMessageDialog(null,"Entrou no if (acao.getSource() == cb_pesquisa)");
                   pesquisaviacombobox();
                }
            }
            else if (acao.getSource() == botao_imprimir)
                imprimir();
            else if (acao.getSource() == botao_sair) {
                JOptionPane.showMessageDialog(null, "Se alguma informação foi alterada, \nsaia do Sistema e Entre novamente para que as alterações sejam consideradas!");
                this.dispose();
           }
            else if (acao.getSource() == cb_empresa){
               if ( stat_aux)
                   trata_cb_auxiliar(st_empresa, rs_empresa, cb_empresa, tf_empresa, 1);
            }
      }
    //acao proximo registro
    public void vai_proximo_registro()
    {
        try
        {
             navega=2;
             resultset.next();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
        }
    }

    //acao registro anterior
    public void vai_registro_anterior()
    {
        try
        {
             navega=1;
             resultset.previous();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
        }
    }

    //acao ultimo registro
    public void vai_ultimo_registro()
    {
        try
        {
             resultset.last();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
        }
    }

    //acao proximo registro
    public void vai_primeiro_registro()
    {
        try
        {
             resultset.first();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
        }
    }
    public void novo_registro()
    {
         int ult_cod=0;
        try
        {
            resultset.last();
            String codigo = resultset.getString("codigo");
            ult_cod = Integer.parseInt(codigo) + 1;
        }
        catch(SQLException erro)
        {
             //JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
            ult_cod = 1;
        }
        tf_codigo             .setText(""+ult_cod);
        tf_empresa            .setText("");
        tf_senha_truststore   .setText("");
        tf_senha_token        .setText("");
        tf_senha_keystore     .setText("");
        tf_serie55            .setText("");
        tf_serie65            .setText("");
        tf_verproc            .setText("");
        tf_idtoken            .setText("");
        tf_csc                .setText("");
        tf_csc_prod           .setText("");
      posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
           tf_codigo.setEditable(false);
           botao_gravar.setEnabled(true);
           botao_alterar.setEnabled(false);
           botao_excluir.setEnabled(false);
//Limpa filtros da inclusao anterior nos comboBox auxiliares 
          if (!tf_psqempresa.getText().equals("")){
             tf_psqempresa.setText("");
             preenche_cb_auxiliar(st_empresa, rs_empresa, cb_empresa, 1);
          }
     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_codigo.getText() == null ) tf_codigo.setText("");
        if ( tf_empresa.getText() == null ) tf_empresa.setText("");
        if (tf_empresa.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        if ( tf_senha_truststore.getText() == null ) tf_senha_truststore.setText("");
        if ( tf_senha_token.getText() == null ) tf_senha_token.setText("");
        if ( tf_senha_keystore.getText() == null ) tf_senha_keystore.setText("");
        if ( tf_serie55.getText() == null ) tf_serie55.setText("");
        if ( tf_serie65.getText() == null ) tf_serie65.setText("");
        if ( tf_verproc.getText() == null ) tf_verproc.setText("");
        if ( tf_idtoken.getText() == null ) tf_idtoken.setText("");
        if ( tf_csc.getText() == null ) tf_csc.setText("");
        if ( tf_csc_prod.getText() == null ) tf_csc_prod.setText("");
        return retorno;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                 String sql_insert = "insert into pardigital ( "+
                        "codigo, "+"empresa, "+"senha_truststore, "+"senha_token, "+"senha_keystore, "+"serie55, "+"serie65, "+
                        "verproc, "+"idtoken, "+"csc "+")"+
                        " values (";
                 sql_insert += tf_codigo.getText() + ", ";
                 sql_insert += tf_empresa.getText() + ", ";
                 sql_insert += "'"+tf_senha_truststore.getText() + "', ";

                                         sql_insert += "'"+tf_senha_token.getText() + "', ";
                 sql_insert += "'"+tf_senha_keystore.getText() + "', ";
                 sql_insert += "'"+tf_serie55.getText() + "', ";

                                         sql_insert += "'"+tf_serie65.getText() + "', ";
                 sql_insert += "'"+tf_verproc.getText() + "', ";
                 sql_insert += "'"+tf_idtoken.getText() + "', ";

                 sql_insert += "'"+tf_csc.getText() + "', ";
                 sql_insert += "'"+tf_csc_prod.getText() + "' ";
sql_insert += ")";
            //System.out.println("Comando sql_insert = " + sql_insert);
            //JOptionPane.showMessageDialog(null,"Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from pardigital");
                 resultset.last();
                 //mostra_conteudo_nos_campos();
                 posiciona_combo_e_jtable();
                 clicado = true;
                 novo_registro();
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o registro! Erro: "+erro);
                 //JOptionPane.showMessageDialog(null, "Registro já existe na Base de Dados!");
            }
         }else
         {
             JOptionPane.showMessageDialog(null,"Registro existente nao pode ser criado!\nEscolha o Botao para Regravar o Registro");
         }
     }

    //metodo para regravar no banco registro
    public void alterar()
    {
        if (!ehnovo)
        {
             try
            {
                 String sql_alterar = "update pardigital set " ;
                 sql_alterar += "empresa = "+ tf_empresa.getText()+", ";
                 sql_alterar += "senha_truststore = '"+ tf_senha_truststore.getText()+"', ";
                 sql_alterar += "senha_token = '"+ tf_senha_token.getText()+"', ";
                 sql_alterar += "senha_keystore = '"+ tf_senha_keystore.getText()+"', ";
                 sql_alterar += "serie55 = '"+ tf_serie55.getText()+"', ";
                 sql_alterar += "serie65 = '"+ tf_serie65.getText()+"', ";
                 sql_alterar += "verproc = '"+ tf_verproc.getText()+"', ";
                 sql_alterar += "idtoken = '"+ tf_idtoken.getText()+"', ";
                 sql_alterar += "csc = '"+ tf_csc.getText()+"', " ;
                 sql_alterar += "csc_prod = '"+ tf_csc_prod.getText()+"'" ;
                 sql_alterar += " where codigo = " + tf_codigo.getText() ;
                  //System.out.println("sql_altera = " + sql_alterar);
                  //JOptionPane.showMessageDialog(null, "sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from pardigital");
                  resultset.last();
                  //mostra_conteudo_nos_campos();
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  posiciona_combo_e_jtable();
                  tf_pesquisa.setText("");
                  clicado = true;
                  novo_registro();
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar regravar registro! Erro: "+erro);
            }
        }else
        {
            JOptionPane.showMessageDialog(null,"Registro novo nao pode ser regravado!\nEscolha o Botao para Gravar Registro");
        }
     }

         // procedimento para exclusao de registro
     public void excluir()
     {
       try
       {
           String nome = "Deletar pardigital : "+tf_empresa.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM pardigital Where codigo = " + tf_codigo.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from pardigital");
                  resultset.last();
                  //mostra_conteudo_nos_campos();
                  clicado = true;
                  novo_registro();
               }
               else {
                  JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o registro para Exclusao!");
               }
           }
           else
             return;
       }
       catch(SQLException erro)
       {
             JOptionPane.showMessageDialog(null, "Erro ao tentar excluir registro = "+erro);
             erro.printStackTrace();
       }
   }

     // procedimento para exclusao de registro
    public void imprimir()
    {
        JOptionPane.showMessageDialog(null, "Impressao da Tabela - Falta Implementar este Botao");
        //Impressao impr = new Impressao(empresa, "rel_pardigital.jasper");
        //impr.imprimeRelJasper();
    }
    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            cb_aux.removeAllItems();
            String sql_aux = "select * from "+tab_cb_tab_box[indice];
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next())
                 cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
            rs_aux.first();
            cb_aux.setSelectedIndex(-1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ComboBox "+cpo_tabela[indice]+": "+ex);
        }
    }
    public void trata_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, JTextField tf_aux, int indice) {
        System.out.println("Tratamento para o ComboBox: cb_"+cpo_tabela[indice]);
        try {
            if (cb_aux.getSelectedItem() != null){
                int j =-1;
                String sql = "select * from "+tab_cb_tab_box[indice];
                System.out.println("Comando sql para getMetadata(): "+sql);
                st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs_aux = st_aux.executeQuery(sql);
                for (int i=1; i<=rs_aux.getMetaData().getColumnCount(); i++){
                    if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_exibe[indice])){
                        j = i;
                        break;
                    }
                }
		 try {
                    String sql_aux;
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" = ?"; //'"+cb_aux.getSelectedItem()+"'";
                    System.out.println("Sql_aux: "+sql_aux);
                    if (ps_aux != null)
                        ps_aux.clearParameters();
                    ps_aux = connection.prepareStatement(sql_aux);
                    String chave1 = "", chave2 = "";
                    String chave = cb_aux.getSelectedItem().toString();
                    chave1 = chave.substring(chave.indexOf(" - ")+3, chave.length());
                    chave2 = chave.substring(0, chave.indexOf(" - "));
                    if (j >0){
                        System.out.println("Tipo do campo ["+rs_aux.getMetaData().getColumnName(j)+"] = "+rs_aux.getMetaData().getColumnType(j));
                        if (rs_aux.getMetaData().getColumnType(j) == 4 )
                            ps_aux.setInt(1, Integer.parseInt(chave1));
                        else
                            ps_aux.setString(1, chave1);
                    }
                    System.out.println("Sql_aux: "+sql_aux);
                    rs_aux = ps_aux.executeQuery();
		}finally {}
                while(rs_aux.next())
                     tf_aux.setText(rs_aux.getString(tab_cb_cpo_assoc[indice]));
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o textField  "+tf_aux+": "+ex);
        }
    }
    public void mostra_tf_ref_cb_aux(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_aux, int indice) {
        System.out.println("preenchimento do textfield para o ComboBox: cb_"+cpo_tabela[indice]);
        String sql_aux;
        try {
            sql_aux = "select * from "+tab_cb_tab_box[indice];
            System.out.println("Sql_aux do metodo mostra_tf_ref_cb_aux() para getMetaData: "+sql_aux);
            st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs_aux = st_aux.executeQuery(sql_aux);
            //rs_aux = st_aux.executeQuery("select * from "+tab_cb_tab_box[indice]);
            int j =-1;
            for (int i=1; i<=rs_aux.getMetaData().getColumnCount(); i++){
                if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_assoc[indice])){
                   j = i;
                   break; 
               }
            } 
             try {
                 sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?";
                 ps_aux = connection.prepareStatement(sql_aux);
                 if (j >0){
                     //System.out.println("Tipo do campo ["+rs_aux.getMetaData().getColumnName(j)+"] = "+rs_aux.getMetaData().getColumnType(j));
                     if (rs_aux.getMetaData().getColumnType(j) == 4 )
                         ps_aux.setInt(1, Integer.parseInt(tf_aux.getText()));
                     else
                         ps_aux.setString(1, tf_aux.getText());
                 } else {
                     ps_aux.setString(1, tf_aux.getText());
                 }
                 System.out.println("Sql_aux: "+sql_aux);
                 rs_aux = ps_aux.executeQuery();
             }finally {
                 ps_aux.clearParameters();
             }
             cb_aux.setSelectedIndex(-1);
             while(rs_aux.next()){
                 cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                 break;
            }
            if (cb_aux.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Registro nao encontrado!");
                tf_aux.requestFocus();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao selecionar o combobox aux: "+cb_aux+"\nErro: "+ex);
        }
    }
    public void pesquisadigitacao()
    {
       try
       {
           resultset  = statement.executeQuery("select * from pardigital");
           resultset.first();
           boolean achou = false;
           int reg = 0;
           String cpo_tab;
           while(true)
           {
               cpo_tab = resultset.getString(cpo_tabela[cb_pesq_lab.getSelectedIndex()]);
               //System.out.println("Pesquisando: "+cpo_tab.toUpperCase()+" - e comparando com: "+tf_pesquisa.getText().toUpperCase());
               if (cpo_tab.toUpperCase().indexOf(tf_pesquisa.getText().toUpperCase()) != -1)
               {
                   achou = true;
                   reg = resultset.getRow();
                   break;
               }
               resultset.next();
            }
           if (achou){
                resultset.first();
                for (int i=1; i<reg; i++){
                    resultset.next();
                }
                mostra_conteudo_nos_campos();
                clicado = false;
                preencher_cb_pesquisa("filtrar");
                cb_pesquisa.setSelectedItem(tf_empresa.getText());
                preencher_jtable("filtrar");
                clicado = true;
           }
               else {
                 JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o Texto no Campo informado!");
              }
        }
        catch(Exception erro)
        {
             JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o Texto no Campo informado!");
        }
    }
    public void pesquisaviacombobox()
    {
     if ( cb_pesquisa.getSelectedItem() != null ){
       try
       {
    
           resultset.first();
           boolean achou = false;
           int reg = 0;
           while(!achou)
           {
               String pesquisado = resultset.getString(cpo_tabela[indice_pesquisa]);
               System.out.println("Pesquisando o valor ["+pesquisado+"] no reg: "+reg+" e comparando com ["+cb_pesquisa.getSelectedItem()+"]");
               //if (pesquisado.equals(cb_pesquisa.getSelectedItem()))
               if (pesquisado !=null && pesquisado.equals(cb_pesquisa.getSelectedItem())) 
               {
                   achou = true;
                   System.out.println("Acho o reg pesquisado . . .");
                   if (resultset.getRow()>1) {
                       resultset.previous();
                       resultset.next();
                   } else {
                       resultset.first();
                   }
               }
               else{
                    reg++;
                    resultset.next();
               }
            }
            // nas linhas abaixo, mostra_conteudo_tabela();
            //tf_codigo.setText(resultset.getString("codigo"));//Essas duas linhas sao necessarias
            if (achou){
                mostra_conteudo_nos_campos();
                //preencher_jtable();
                clicado = false;
                cb_pesquisa.setSelectedItem(tf_aux_pesq.getText());
                //System.out.println("selecionaJtable() reg: "+reg);
                selecionaJtable(reg);
            }
            clicado = true;
/*
        String sql = "select * from pardigital where Descricao = "+cb_pesquisa.getSelectedItem();
        resultset = statement.executeQuery(sql);
        System.out.println("Comando sql(pesquisadigitacao): "+sql);
        while (resultset.next()) {
             mostra_conteudo_nos_campos();
        }
*/
        }
        catch(Exception erro)
        {
           System.out.println("Nao conseguiu localizar - metodo pesquisaviacombobox(), erro = "+erro);
           JOptionPane.showMessageDialog(null, "Impossivel localizar registro com conteudo VAZIO !");
        }
     }
    }
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == jTable1) {
           System.out.println("Numero da linha selecionada(mouseClicked): "+jTable1.getSelectedRow());
           trataEvento_mouse_key_jTable1();
        }
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    public void trataEvento_mouse_key_jTable1(){
            System.out.println("Numero da linha selecionada(mouseClicked/keyPressed): "+jTable1.getSelectedRow());
            try {
                if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                    String sql_query = "select * from pardigital where "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%' order by "+cpo_tabela[0];
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from pardigital order by "+cpo_tabela[0]);
                }
                //resultset = statement.executeQuery("select * from pardigital order by "+cpo_tabela[0]);
                 int qtreg = 0;
                 while(resultset.next()){
                     if (jTable1.getSelectedRow() == qtreg) {
                        //JOptionPane.showMessageDialog(null, "Achou registro: "+qtreg);
                         mostra_conteudo_nos_campos();
                         break;
                     }
                     qtreg++;
                 }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao Procurar registro: "+ ex);
            }
    }
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == jTable1){
          System.out.println("acao do keylistener: keyTyped");
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() == jTable1){
            //System.out.println("acao do keylistener: keyPressed");
            System.out.println("Código da Tecla: "+ e.getKeyCode());
            if (e.getKeyCode() == 40 || e.getKeyCode() == 38 ) { // 40 = seta pra baixo / 38 = seta pra cima
                int regkey = jTable1.getSelectedRow();
                if (e.getKeyCode() == 40 && jTable1.getSelectedRow() < (jTable1.getRowCount()-1))
                    regkey = jTable1.getSelectedRow()+1;
                else  if (e.getKeyCode() == 38 && jTable1.getSelectedRow() > 0)
                    regkey = jTable1.getSelectedRow()-1;
                jTable1.setRowSelectionInterval(regkey, regkey);

                System.out.println("Numero da linha selecionada(keyPressed): "+jTable1.getSelectedRow());
                //JOptionPane.showMessageDialog(null,"Numero da linha selecionada(keyPressed): "+jTable1.getSelectedRow());
                try{
                    trataEvento_mouse_key_jTable1();
                    if (e.getKeyCode() == 40 && jTable1.getSelectedRow() < (jTable1.getRowCount()-1))
                        regkey = jTable1.getSelectedRow()-1;
                    else  if (e.getKeyCode() == 38 && jTable1.getSelectedRow() > 0)
                        regkey = jTable1.getSelectedRow()+1;
                    if (regkey>=0 && regkey<jTable1.getRowCount()){
                        //JOptionPane.showMessageDialog(null, "Valor de regkey: "+regkey);
                        jTable1.setRowSelectionInterval(regkey, regkey);
                    }//else
                     //   JOptionPane.showMessageDialog(null, "Impossivel localizar registro com conteudo VAZIO !");
                }catch(Exception erro){
                    JOptionPane.showMessageDialog(null, "Impossivel localizar registro com conteudo VAZIO !\nErro: "+erro);
                }
            }
       } else if(e.getSource() == tf_empresa) {
           System.out.println("Teclou enter no campo empresa . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[1], tab_cb_cb[1], tab_cb_tf[1], 1);
               tf_empresa.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqempresa){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_empresa, rs_empresa, cb_empresa, tf_psqempresa, rb_inic_psqempresa, rb_meio_psqempresa, 1);
        }
    }

    public void preenche_cb_auxPesq(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, JTextField tf_psqAux, JRadioButton rb_inic, JRadioButton rb_meio, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            String sql_aux = "";
            cb_aux.removeAllItems();
            if (tf_psqAux.getText().equals(""))
                sql_aux = "select * from "+tab_cb_tab_box[indice];
            else
                if (rb_inic.isSelected())
                   sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '"+tf_psqAux.getText()+"%'";
                else
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '%"+tf_psqAux.getText()+"%'";
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next())
                 cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
            rs_aux.first();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ComboBox "+cpo_tabela[indice]+": "+ex);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("Ganhou o foco . . .");
    }
 
    @Override
    public void focusLost(FocusEvent e) {
       if (e.getSource() == tf_empresa){
           System.out.println("Perdeu o foco do campo empresa . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[1], tab_cb_cb[1], tab_cb_tf[1], 1))
        tf_empresa.requestFocus();
       }
    }
    private boolean posiciona_combo_geral(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_Aux, int indice){
       boolean retorno = false;
       try{
             if (!tf_Aux.getText().equals("")){
                tf_Aux.setText(tf_Aux.getText().toUpperCase());
                //rs = st.executeQuery("select "+cpo_combo+" from "+tabela+" where "+cpo_pesq+" = '"+jtf.getText()+"'");
                String sql_pesq = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?";
                PreparedStatement ps = connection.prepareStatement(sql_pesq);
                ps.setString(1, tf_Aux.getText());
                rs_aux = ps.executeQuery();
                while (rs_aux.next()){
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                    retorno = true;
                }
                if (!retorno) {
                    cb_aux.setSelectedIndex(-1);
                    //JOptionPane.showMessageDialog(null, "Nao conseguiu encontrar Registro para o Codigo informado!");
                    tf_Aux.setText("");
                }
             }
        } catch(Exception erro){
            System.out.println("Erro ao tentar posicionar combo de clientes. Erro: "+erro);
            retorno = false;
            erro.printStackTrace();
        }
        return retorno;
    }
    public static void main(String args[])
        {
        JFrame form = new Pardigital(0, null);
            form.setVisible(true);
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_pardigital extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_pardigital()
    {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setHorizontalAlignment(CENTER);

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
