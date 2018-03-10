/*

Descrição: Manutenção da Tabela de Empresa (Emitente da NFe)

Autor: Videoaulasneri - email: videoaulasneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
public class Empresa extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Cadastro: empresa");

    //Labels dos campos da tabela;
    JLabel label_codigo              = new JLabel("Codigo: ");
    JLabel label_bairro              = new JLabel("Bairro: ");
    JLabel label_cep                 = new JLabel("Cep: ");
    JLabel label_cnpj                = new JLabel("Cnpj: ");
    JLabel label_codempresa          = new JLabel("Cod.Empresa: ");
    JLabel label_codigo_pais_nfe     = new JLabel("Cod.Pais(nfe): ");
    JLabel label_complemento         = new JLabel("Complemento: ");
    JLabel label_contato             = new JLabel("Contato: ");
    JLabel label_endereco            = new JLabel("Endereco: ");
    JLabel label_fantasia            = new JLabel("Fantasia: ");
    JLabel label_telefone            = new JLabel("Telefone: ");
    JLabel label_inscest             = new JLabel("Insc.Est: ");
    JLabel label_numero              = new JLabel("Numero: ");
    JLabel label_razao               = new JLabel("Razao: ");
    //JLabel label_tipo_nf             = new JLabel("Tipo NF: ");
    JLabel label_codcidade           = new JLabel("Cod.Cidade: ");
    JLabel label_crt                 = new JLabel("Crt: ");
    JLabel label_margem_lucro        = new JLabel("Margem_lucro: ");

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
    JTextField tf_bairro             = new JTextField();
    JTextField tf_cep                = new JTextField();
    JTextField tf_cnpj               = new JTextField();
    JTextField tf_codempresa         = new JTextField();
    JTextField tf_codigo_pais_nfe    = new JTextField();
    JTextField tf_complemento        = new JTextField();
    JTextField tf_contato            = new JTextField();
    JTextField tf_endereco           = new JTextField();
    JTextField tf_fantasia           = new JTextField();
    JTextField tf_telefone           = new JTextField();
    JTextField tf_inscest            = new JTextField();
    JTextField tf_numero             = new JTextField();
    JTextField tf_razao              = new JTextField();
    //JTextField tf_tipo_nf            = new JTextField();
    JTextField tf_codcidade          = new JTextField();
    JTextField tf_crt                = new JTextField();
    JTextField tf_margem_lucro       = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox cb_pesquisa            = new JComboBox();
    JComboBox cb_pesq_lab            = new JComboBox();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[18];
    String [] lab_tabela             = new String[18];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[18];
    String [] tab_cb_cpo_exibe       = new String[18];
    String [] tab_cb_cpo_assoc       = new String[18];
    JTextField [] tab_cb_tf          = new JTextField[18];
    Statement [] tab_cb_st           = new Statement[18];
    ResultSet [] tab_cb_rs           = new ResultSet[18];
    JComboBox [] tab_cb_cb           = new JComboBox[18];
    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 9;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_fantasia;
    PreparedStatement ps_aux;
    //int empresa = 0;

    ResultSet rs_codcidade;
    Statement st_codcidade;
    JComboBox cb_codcidade              = new JComboBox();
    JLabel label_psqcodcidade           = new JLabel("Pesquisar: ");
    JTextField tf_psqcodcidade          = new JTextField();
    ButtonGroup bg_psqcodcidade         = new ButtonGroup();
    JRadioButton rb_inic_psqcodcidade   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcodcidade   = new JRadioButton("Meio");
    JButton bt_copyDescr                = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar_registro.gif")));


    public Empresa(Connection connection)
    {
        setTitle("Formulario de Manutencao de empresa");
        setSize(1010, 680);
        setLocation(135,85);
        setResizable(true);
        //this.empresa = empresa;
        this.connection = connection;
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
        label_bairro            .setForeground(Color.black);
        label_cep               .setForeground(Color.black);
        label_cnpj              .setForeground(Color.black);
        label_codempresa        .setForeground(Color.black);
        label_codigo_pais_nfe   .setForeground(Color.black);
        label_complemento       .setForeground(Color.black);
        label_contato           .setForeground(Color.black);
        label_endereco          .setForeground(Color.black);
        label_fantasia          .setForeground(Color.black);
        label_telefone          .setForeground(Color.black);
        label_inscest           .setForeground(Color.black);
        label_numero            .setForeground(Color.black);
        label_razao             .setForeground(Color.black);
        //label_tipo_nf           .setForeground(Color.black);
        label_codcidade         .setForeground(Color.black);
        label_crt               .setForeground(Color.black);
        label_margem_lucro      .setForeground(Color.black);


    //alinha os labels a direita
        label_codigo            .setHorizontalAlignment(JLabel.RIGHT);
        label_bairro            .setHorizontalAlignment(JLabel.RIGHT);
        label_cep               .setHorizontalAlignment(JLabel.RIGHT);
        label_cnpj              .setHorizontalAlignment(JLabel.RIGHT);
        label_codempresa        .setHorizontalAlignment(JLabel.RIGHT);
        label_codigo_pais_nfe   .setHorizontalAlignment(JLabel.RIGHT);
        label_complemento       .setHorizontalAlignment(JLabel.RIGHT);
        label_contato           .setHorizontalAlignment(JLabel.RIGHT);
        label_endereco          .setHorizontalAlignment(JLabel.RIGHT);
        label_fantasia          .setHorizontalAlignment(JLabel.RIGHT);
        label_telefone          .setHorizontalAlignment(JLabel.RIGHT);
        label_inscest           .setHorizontalAlignment(JLabel.RIGHT);
        label_numero            .setHorizontalAlignment(JLabel.RIGHT);
        label_razao             .setHorizontalAlignment(JLabel.RIGHT);
        //label_tipo_nf           .setHorizontalAlignment(JLabel.RIGHT);
        label_codcidade         .setHorizontalAlignment(JLabel.RIGHT);
        label_crt               .setHorizontalAlignment(JLabel.RIGHT);
        label_margem_lucro      .setHorizontalAlignment(JLabel.RIGHT);


    //Gera cor frente dos texfields setForegroundColor()
        tf_codigo               .setForeground(Color.black);
        tf_bairro               .setForeground(Color.black);
        tf_cep                  .setForeground(Color.black);
        tf_cnpj                 .setForeground(Color.black);
        tf_codempresa           .setForeground(Color.black);
        tf_codigo_pais_nfe      .setForeground(Color.black);
        tf_complemento          .setForeground(Color.black);
        tf_contato              .setForeground(Color.black);
        tf_endereco             .setForeground(Color.black);
        tf_fantasia             .setForeground(Color.black);
        tf_telefone             .setForeground(Color.black);
        tf_inscest              .setForeground(Color.black);
        tf_numero               .setForeground(Color.black);
        tf_razao                .setForeground(Color.black);
        //tf_tipo_nf              .setForeground(Color.black);
        tf_codcidade            .setForeground(Color.black);
        tf_crt                  .setForeground(Color.black);
        tf_margem_lucro         .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_codigo               .setBackground(Color.white);
        tf_bairro               .setBackground(Color.white);
        tf_cep                  .setBackground(Color.white);
        tf_cnpj                 .setBackground(Color.white);
        tf_codempresa           .setBackground(Color.white);
        tf_codigo_pais_nfe      .setBackground(Color.white);
        tf_complemento          .setBackground(Color.white);
        tf_contato              .setBackground(Color.white);
        tf_endereco             .setBackground(Color.white);
        tf_fantasia             .setBackground(Color.white);
        tf_telefone             .setBackground(Color.white);
        tf_inscest              .setBackground(Color.white);
        tf_numero               .setBackground(Color.white);
        tf_razao                .setBackground(Color.white);
        //tf_tipo_nf              .setBackground(Color.white);
        tf_codcidade            .setBackground(Color.white);
        tf_crt                  .setBackground(Color.white);
        tf_margem_lucro         .setBackground(Color.white);

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

        tf_crt                   .setToolTipText("1 - Simples Nacional, " +
                                                "2 - Simples Nacional - excesso de sublimite da receita bruta, " +
                                                "3 - Regime Normal" +
                                                "");
        
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
        tf_psqcodcidade          .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcodcidade          .addKeyListener(this);
        tf_codcidade             .addKeyListener(this);
        tf_codcidade             .addFocusListener(this);
        cb_codcidade             .addActionListener(this);
        bt_copyDescr             .addActionListener(this);
        
        //Posicionando os componentes labels e textfields da tabela
        label_codigo             .setBounds(100,165,90,20);
        tf_codigo                .setBounds(190,165,88,20);
        
        label_codempresa         .setBounds(400,165,90,20);
        tf_codempresa            .setBounds(490,165,30,20);

        label_cnpj               .setBounds(100,190,90,20);
        tf_cnpj                  .setBounds(190,190,150,20);

        label_inscest            .setBounds(400,190,90,20);
        tf_inscest               .setBounds(490,190,150,20);

        label_razao              .setBounds(100,215,90,20);
        tf_razao                 .setBounds(190,215,400,20);

        label_fantasia           .setBounds(100,240,90,20);
        tf_fantasia              .setBounds(190,240,400,20);
        bt_copyDescr             .setBounds(595,240,25,25);
        label_endereco           .setBounds(100,265,90,20);
        tf_endereco              .setBounds(190,265,350,20);

        label_numero             .setBounds(550,265,90,20);
        tf_numero                .setBounds(640,265,100,20);

        label_complemento        .setBounds(750,265,90,20);
        tf_complemento           .setBounds(840,265,150,20);

        label_bairro             .setBounds(100,290,90,20);
        tf_bairro                .setBounds(190,290,200,20);
        label_cep                .setBounds(400,290,90,20);
        tf_cep                   .setBounds(490,290,100,20);

        label_codcidade          .setBounds(100,315,90,20);
        tf_codcidade             .setBounds(190,315,88,20);
        cb_codcidade             .setBounds(288,315, 300,20);
        label_psqcodcidade       .setBounds(598,315, 70,20);
        tf_psqcodcidade          .setBounds(670,315, 100,20);
        rb_inic_psqcodcidade     .setBounds(770,315, 60,20);
        rb_meio_psqcodcidade     .setBounds(830,315, 55,20);

        label_codigo_pais_nfe    .setBounds(100,340,90,20);
        tf_codigo_pais_nfe       .setBounds(190,340,150,20);

        label_contato            .setBounds(360,340,90,20);
        tf_contato               .setBounds(450,340,200,20);
        label_telefone           .setBounds(680,340,90,20);
        tf_telefone              .setBounds(770,340,150,20);

        //label_tipo_nf            .setBounds(100,365,90,20);
        //tf_tipo_nf               .setBounds(190,365,20,20);
        label_crt                .setBounds(100,365,90,20);
        tf_crt                   .setBounds(190,365,23,20);
        label_margem_lucro       .setBounds(220,365,90,20);
        tf_margem_lucro          .setBounds(330,365,192,20);
//Posicao do ultimo cpo da Tabela no Formulario. Utiliz.para Iniciar a exibicao do JTable
        posUltLabel           = 365;
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
                "Codempresa",
                "Razao",
                "Cnpj",
                "Fantasia",
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_empresa());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_codigo);
        getContentPane()      .add(tf_codigo);
        getContentPane()      .add(label_bairro);
        getContentPane()      .add(tf_bairro);
        getContentPane()      .add(label_cep);
        getContentPane()      .add(tf_cep);
        getContentPane()      .add(label_cnpj);
        getContentPane()      .add(tf_cnpj);
        getContentPane()      .add(label_codempresa);
        getContentPane()      .add(tf_codempresa);
        getContentPane()      .add(label_codigo_pais_nfe);
        getContentPane()      .add(tf_codigo_pais_nfe);
        getContentPane()      .add(label_complemento);
        getContentPane()      .add(tf_complemento);
        getContentPane()      .add(label_contato);
        getContentPane()      .add(tf_contato);
        getContentPane()      .add(label_endereco);
        getContentPane()      .add(tf_endereco);
        getContentPane()      .add(label_fantasia);
        getContentPane()      .add(tf_fantasia);
        getContentPane()      .add(bt_copyDescr);
        getContentPane()      .add(label_telefone);
        getContentPane()      .add(tf_telefone);
        getContentPane()      .add(label_inscest);
        getContentPane()      .add(tf_inscest);
        getContentPane()      .add(label_numero);
        getContentPane()      .add(tf_numero);
        getContentPane()      .add(label_razao);
        getContentPane()      .add(tf_razao);
        //getContentPane()      .add(label_tipo_nf);
        //getContentPane()      .add(tf_tipo_nf);
        getContentPane()      .add(label_codcidade);
        getContentPane()      .add(tf_codcidade);
        getContentPane()      .add(cb_codcidade);
        getContentPane()      .add(label_psqcodcidade);
        getContentPane()      .add(tf_psqcodcidade);
        getContentPane()      .add(rb_inic_psqcodcidade);
        getContentPane()      .add(rb_meio_psqcodcidade);
        bg_psqcodcidade          .add(rb_inic_psqcodcidade);
        bg_psqcodcidade          .add(rb_meio_psqcodcidade);
        getContentPane()      .add(label_crt);
        getContentPane()      .add(tf_crt);
        getContentPane()      .add(label_margem_lucro);
        getContentPane()      .add(tf_margem_lucro);
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
        cpo_tabela[1]         = "bairro";
        cpo_tabela[2]         = "cep";
        cpo_tabela[3]         = "cnpj";
        cpo_tabela[4]         = "codempresa";
        cpo_tabela[5]         = "codigo_pais_nfe";
        cpo_tabela[6]         = "complemento";
        cpo_tabela[7]         = "contato";
        cpo_tabela[8]         = "endereco";
        cpo_tabela[9]         = "fantasia";
        cpo_tabela[10]         = "telefone";
        cpo_tabela[11]         = "inscest";
        cpo_tabela[12]         = "numero";
        cpo_tabela[13]         = "razao";
        cpo_tabela[14]         = "tipo_nf";
        cpo_tabela[15]         = "codcidade";
        cpo_tabela[16]         = "crt";
        cpo_tabela[17]         = "margem_lucro";
        lab_tabela[0]         = "Codigo";
        lab_tabela[1]         = "Bairro";
        lab_tabela[2]         = "Cep";
        lab_tabela[3]         = "Cnpj";
        lab_tabela[4]         = "Codempresa";
        lab_tabela[5]         = "Codigo_pais_nfe";
        lab_tabela[6]         = "Complemento";
        lab_tabela[7]         = "Contato";
        lab_tabela[8]         = "Endereco";
        lab_tabela[9]         = "Fantasia";
        lab_tabela[10]         = "Telefone";
        lab_tabela[11]         = "Inscest";
        lab_tabela[12]         = "Numero";
        lab_tabela[13]         = "Razao";
        lab_tabela[14]         = "Tipo_nf";
        lab_tabela[15]         = "Codcidade";
        lab_tabela[16]         = "Crt";
        lab_tabela[17]         = "Margem_lucro";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[15]          = tf_codcidade;
        tab_cb_st[15]          = st_codcidade;
        tab_cb_rs[15]          = rs_codcidade;
        tab_cb_cb[15]          = cb_codcidade;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[15]     = "ibge";
        tab_cb_cpo_assoc[15]   = "codigo";
        tab_cb_cpo_exibe[15]   = "cidade";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        try
        {
              //conexao conn = new conexao();
              //connection = conn.conecta("", "");
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_codcidade  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, 15);

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
                 if (
                     cb_pesq_lab.getSelectedIndex() != 0 && 
                     cb_pesq_lab.getSelectedIndex() != 4 &&
                     cb_pesq_lab.getSelectedIndex() != 15 &&
                     cb_pesq_lab.getSelectedIndex() != 17
                    ) {
                  sql_query = "select * from empresa where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
                 } else {
                  sql_query = "select * from empresa where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+"";
                 }
             } else {
                  sql_query = "select * from empresa";
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("bairro"));
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
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(200);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from empresa");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar empresa: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 if (
                     cb_pesq_lab.getSelectedIndex() != 0 && 
                     cb_pesq_lab.getSelectedIndex() != 4 &&
                     cb_pesq_lab.getSelectedIndex() != 15 &&
                     cb_pesq_lab.getSelectedIndex() != 17
                    ) {
                sqlquery = "select * from empresa where "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
                 } else {
                sqlquery = "select * from empresa where "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+"";
                 }
            } else {
                sqlquery = "select * from empresa";            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("codigo"),
                resultset.getString("codempresa"),
                resultset.getString("razao"),
                resultset.getString("cnpj"),
                resultset.getString("fantasia"),
                                            }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: empresa: "+qtreg);
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
               tf_bairro             .setText(resultset.getString("bairro"));
               tf_cep                .setText(resultset.getString("cep"));
               tf_cnpj               .setText(resultset.getString("cnpj"));
               tf_codempresa         .setText(resultset.getString("codempresa"));
               tf_codigo_pais_nfe    .setText(resultset.getString("codigo_pais_nfe"));
               tf_complemento        .setText(resultset.getString("complemento"));
               tf_contato            .setText(resultset.getString("contato"));
               tf_endereco           .setText(resultset.getString("endereco"));
               tf_fantasia           .setText(resultset.getString("fantasia"));
               tf_telefone           .setText(resultset.getString("telefone"));
               tf_inscest            .setText(resultset.getString("inscest"));
               tf_numero             .setText(resultset.getString("numero"));
               tf_razao              .setText(resultset.getString("razao"));
               //tf_tipo_nf            .setText(resultset.getString("tipo_nf"));
               tf_codcidade          .setText(resultset.getString("codcidade"));
               tf_crt                .setText(resultset.getString("crt"));
               tf_margem_lucro       .setText(resultset.getString("margem_lucro"));

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
            else if (acao.getSource() == bt_copyDescr) {
                String wdesc = tf_razao.getText();
                if (wdesc.length() > 50) {
                    wdesc = wdesc.substring(0, 50);
                }
                tf_fantasia.setText(wdesc);
            }
            else if (acao.getSource() == cb_pesquisa){
                if (clicado){
                   //JOptionPane.showMessageDialog(null,"Entrou no if (acao.getSource() == cb_pesquisa)");
                   pesquisaviacombobox();
                }
            }
            else if (acao.getSource() == botao_imprimir)
                imprimir();
            else if (acao.getSource() == botao_sair) {
/*
                if (connection != null) {
                    try {
                         connection.close();
                    } catch (SQLException ex) {
                         JOptionPane.showMessageDialog(null,"Erro ao tentar Fechar a Conexao com o Banco de Dados!");
                    }
                }
 * 
 */
                JOptionPane.showMessageDialog(null, "Se alguma informação foi alterada ou houve Inclusão de Empresa, \nsaia do Sistema e Entre novamente para que as alterações sejam consideradas!");
                this.dispose();
           }
            else if (acao.getSource() == cb_codcidade){
               if ( stat_aux)
                   trata_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, tf_codcidade, 15);
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
        tf_bairro             .setText("");
        tf_cep                .setText("");
        tf_cnpj               .setText("");
        tf_codempresa         .setText("");
        tf_codigo_pais_nfe    .setText("1058"); //  brasil = 1058
        tf_complemento        .setText("");
        tf_contato            .setText("");
        tf_endereco           .setText("");
        tf_fantasia           .setText("");
        tf_telefone           .setText("");
        tf_inscest            .setText("");
        tf_numero             .setText("");
        tf_razao              .setText("");
        //tf_tipo_nf            .setText("");
        tf_codcidade          .setText("");
        tf_crt                .setText("1");
        tf_margem_lucro       .setText("0");
      posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
          tf_codigo.setEditable(false);
           botao_gravar.setEnabled(true);
           botao_alterar.setEnabled(false);
           botao_excluir.setEnabled(false);
//Limpa filtros da inclusao anterior nos comboBox auxiliares 
          if (!tf_psqcodcidade.getText().equals("")){
             tf_psqcodcidade.setText("");
             preenche_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, 15);
          }
     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_codigo.getText() == null ) tf_codigo.setText("");
        if ( tf_bairro.getText() == null ) tf_bairro.setText("");
        if ( tf_cep.getText() == null ) tf_cep.setText("");
        if ( tf_cnpj.getText() == null ) tf_cnpj.setText("");
        if ( tf_codempresa.getText() == null ) tf_codempresa.setText("");
        if ( tf_codigo_pais_nfe.getText() == null ) tf_codigo_pais_nfe.setText("");
        if ( tf_complemento.getText() == null ) tf_complemento.setText("");
        if ( tf_contato.getText() == null ) tf_contato.setText("");
        if ( tf_endereco.getText() == null ) tf_endereco.setText("");
        if ( tf_fantasia.getText() == null ) tf_fantasia.setText("");
        if ( tf_telefone.getText() == null ) tf_telefone.setText("");
        if ( tf_inscest.getText() == null ) tf_inscest.setText("");
        if ( tf_numero.getText() == null ) tf_numero.setText("");
        if ( tf_razao.getText() == null ) tf_razao.setText("");
        //if ( tf_tipo_nf.getText() == null ) tf_tipo_nf.setText("");
        if ( tf_codcidade.getText() == null ) tf_codcidade.setText("");
        if (tf_codcidade.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        tf_razao.setText(tf_razao.getText().toUpperCase());
        tf_fantasia.setText(tf_fantasia.getText().toUpperCase());
        tf_endereco.setText(tf_endereco.getText().toUpperCase());
        tf_bairro.setText(tf_bairro.getText().toUpperCase());
        tf_contato.setText(tf_contato.getText().toUpperCase());
       if ( tf_crt.getText() == null ) tf_crt.setText("");
        if ( tf_margem_lucro.getText() == null ) tf_margem_lucro.setText("0");
        return retorno;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                 String sql_insert = "insert into empresa ( "+
                        "codigo, "+"bairro, "+"cep, "+"cnpj, "+"codempresa, "+"codigo_pais_nfe, "+"complemento, "+"contato, "+
                        "endereco, "+"fantasia, "+"telefone, "+"inscest, "+"numero, "+"razao, "+"codcidade, "+"crt, "+
                        "margem_lucro "+")"+
                        " values ("+tf_codigo.getText() + ", "+"'"+tf_bairro.getText() + "', "+"'"+tf_cep.getText() + "', "+
                        "'"+tf_cnpj.getText() + "', "+Integer.parseInt(tf_codempresa.getText()) + ", "+"'"+tf_codigo_pais_nfe.getText() + "', "+
                        "'"+tf_complemento.getText() + "', "+"'"+tf_contato.getText() + "', "+"'"+tf_endereco.getText() + "', "+
                        "'"+tf_fantasia.getText() + "', "+"'"+tf_telefone.getText() + "', "+"'"+tf_inscest.getText() + "', "+
                        "'"+tf_numero.getText() + "', "+"'"+tf_razao.getText() + "', "+tf_codcidade.getText() + ", "+
                        "'"+tf_crt.getText() + "', "+tf_margem_lucro.getText() + ")";
                 //System.out.println("Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao da empresa realizada com sucesso!");
//JOptionPane.showMessageDialog(null, "Incluiu na Tabela empresa");
                 Statement st_cria = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 sql_insert = "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES ("+Integer.parseInt(tf_codempresa.getText())+"  , 'admin', 'admin', 'Administrador', '55', 'A', 'N');";
                 st_cria.executeUpdate(sql_insert);
                 
                 ResultSet rs_par = statement.executeQuery("select max(codigo) as ultCod from pardigital");
                 int codigo = 0;
                 while(rs_par.next()) {
                     codigo = rs_par.getInt("ultCod");
                 }
                 
                 codigo++;
JOptionPane.showMessageDialog(null, "Incluiu na Tabela de Usuarios");

sql_insert = "INSERT INTO pardigital ("
                            + "codigo, "
                            + "empresa, "
                            + "senha_truststore, "
                            + "senha_token, "
                            + "senha_keystore, "
                            + "serie55, "
                            + "serie65,  "
                            + "verproc, "
                            + "idtoken, "
                            + "csc"
                         + ") VALUES ("
                            + codigo + ", "
                            + Integer.parseInt(tf_codigo.getText())+", "
                            + "'123456',  "
                            + "'', "
                            + "'', "
                            + "'001', "
                            + "'001', "
                            + "'NFe-Porto V:3.10',"
                            + "'000000',"
                            + "'xxxxxxxxxxxxxxx'"
                         + ");";
                st_cria.executeUpdate(sql_insert);
JOptionPane.showMessageDialog(null, "Incluiu na Tabela pardigital");

                sql_insert =  "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES ("+Integer.parseInt(tf_codigo.getText())+", '001', '55', '1', 0); "
                            + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES ("+Integer.parseInt(tf_codigo.getText())+", '001', '55', '2', 0); "
                            + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES ("+Integer.parseInt(tf_codigo.getText())+", '001', '65', '1', 0); "
                            + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES ("+Integer.parseInt(tf_codigo.getText())+", '001', '65', '2', 0); ";
                st_cria.executeUpdate(sql_insert);
JOptionPane.showMessageDialog(null, "Incluiu na Tabela numeronfe");

sql_insert = "INSERT INTO numerolote (empresa, numeroproximolote) VALUES ("+Integer.parseInt(tf_codempresa.getText())+", 1);";
                st_cria.executeUpdate(sql_insert);
JOptionPane.showMessageDialog(null, "Incluiu na Tabela numerolote");

clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from empresa");
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
                 String sql_alterar = "update empresa set " 
                         +"bairro = '"+ tf_bairro.getText()+"', "
                         +"cep = '"+ tf_cep.getText()+"', "
                         +"cnpj = '"+ tf_cnpj.getText()+"', "
                         +"codempresa = "+ Integer.parseInt(tf_codempresa.getText())+", "
                         +"codigo_pais_nfe = '"+ tf_codigo_pais_nfe.getText()+"', "
                         +"complemento = '"+ tf_complemento.getText()+"', "
                         +"contato = '"+ tf_contato.getText()+"', "
                         +"endereco = '"+ tf_endereco.getText()+"', "
                         +"fantasia = '"+ tf_fantasia.getText()+"', "
                         +"telefone = '"+ tf_telefone.getText()+"', "
                         +"inscest = '"+ tf_inscest.getText()+"', "
                         +"numero = '"+ tf_numero.getText()+"', "
                         +"razao = '"+ tf_razao.getText()+"', "
                         +"codcidade = "+ tf_codcidade.getText()+", "
                         +"crt = '"+ tf_crt.getText()+"', "
                         +"margem_lucro = "+ tf_margem_lucro.getText() 
                         +" where codigo = " + tf_codigo.getText() ;
                  System.out.println("sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from empresa");
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
           String nome = "Deletar empresa : "+tf_bairro.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM empresa Where codigo = " + tf_codigo.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from empresa");
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
        //Impressao impr = new Impressao(empresa, "rel_empresa.jasper");
        //impr.imprimeRelJasper();
    }
    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            cb_aux.removeAllItems();
            String sql_aux = "select * from "+tab_cb_tab_box[indice];
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next()) {
                if (indice == 15) {  //  ibge
                    cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]) + "(" + rs_aux.getString("uf") + ")");
                } else {
                    cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                }
            }
            rs_aux.first();
            cb_aux.setSelectedIndex(-1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ComboBox "+cpo_tabela[indice]+": "+ex);
        }
    }
    public void trata_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_aux, int indice) {
        System.out.println("Tratamento para o ComboBox: cb_"+cpo_tabela[indice]);
        try {
            if (cb_aux.getSelectedItem() != null){
                int j =-1;
                String sql = "select * from "+tab_cb_tab_box[indice];
                System.out.println("Comando sql para getMetadata(): "+sql);
                st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs_aux = st_aux.executeQuery(sql);
                for (int i=1; i<=rs_aux.getMetaData().getColumnCount(); i++){
                    if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_assoc[indice])){
                        j = i;
                        break;
                    }
                }
		 try {
                    String sql_aux;
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?"; //'"+cb_aux.getSelectedItem()+"'";
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
                            ps_aux.setInt(1, Integer.parseInt(chave2));
                        else
                            ps_aux.setString(1, chave2);
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
//JOptionPane.showMessageDialog(null,"Sql_aux do metodo mostra_tf_ref_cb_aux() para getMetaData: "+sql_aux);
            st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//JOptionPane.showMessageDialog(null,"empresa - Passou 0 . . .");
            rs_aux = st_aux.executeQuery(sql_aux);
//JOptionPane.showMessageDialog(null,"empresa - Passou 0.1 . . .");
            //rs_aux = st_aux.executeQuery("select * from "+tab_cb_tab_box[indice]);
            int j =-1;
            for (int i=1; i<=rs_aux.getMetaData().getColumnCount(); i++){
                if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_assoc[indice])){
                   j = i;
                   break; 
               }
            } 
//JOptionPane.showMessageDialog(null,"empresa - Passou 0.2 . . .");
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
//JOptionPane.showMessageDialog(null,"Sql_aux: "+sql_aux);
                 rs_aux = ps_aux.executeQuery();
             }finally {
                 ps_aux.clearParameters();
             }
//JOptionPane.showMessageDialog(null,"empresa - Passou 1 . . .");
             cb_aux.setSelectedIndex(0);  //(-1);
             while(rs_aux.next()){
                 if (indice == 15) {
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]) +"("+rs_aux.getString("uf") + ")");
                 } else {
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                 }
                 break;
            }
//JOptionPane.showMessageDialog(null,"empresa - Passou 2 . . .");
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
           resultset  = statement.executeQuery("select * from empresa");
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
                cb_pesquisa.setSelectedItem(tf_bairro.getText());
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
        String sql = "select * from empresa where Descricao = "+cb_pesquisa.getSelectedItem();
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
                    String sql_query = "select * from empresa where "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from empresa");
                }
                //resultset = statement.executeQuery("select * from empresa");
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
       } else if(e.getSource() == tf_codcidade) {
           System.out.println("Teclou enter no campo codcidade . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[15], tab_cb_cb[15], tab_cb_tf[15], 15);
               tf_crt.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqcodcidade){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_codcidade, rs_codcidade, cb_codcidade, tf_psqcodcidade, rb_inic_psqcodcidade, rb_meio_psqcodcidade, 15);
        }
    }

    public void preenche_cb_auxPesq(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_psqAux, JRadioButton rb_inic, JRadioButton rb_meio, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            String sql_aux = "";
            cb_aux.removeAllItems();
            if (tf_psqAux.getText().equals(""))
                sql_aux = "select * from "+tab_cb_tab_box[indice];
            else
                if (rb_inic.isSelected())
                   sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '"+tf_psqAux.getText().toUpperCase()+"%'";
                else
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '%"+tf_psqAux.getText().toUpperCase()+"%'";
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next()) {
                if (indice == 15) {
                    cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]) + "(" + rs_aux.getString("uf") + ")");
                } else {
                    cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                }
            }
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
       if (e.getSource() == tf_codcidade){
           System.out.println("Perdeu o foco do campo codcidade . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[15], tab_cb_cb[15], tab_cb_tf[15], 15))
        tf_crt.requestFocus();
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
        JFrame form = new Empresa(null);
            form.setVisible(true);
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_empresa extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_empresa()
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
