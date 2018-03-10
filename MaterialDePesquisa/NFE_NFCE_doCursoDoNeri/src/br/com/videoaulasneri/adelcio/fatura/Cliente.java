/*
  Programa Desenvolvido por Porto Informatica Ltda
  portoinfo@sercomtel.com.br
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Relatorios.Impressao;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getFile_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getSenha_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getTipoAmbiente;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.ConsultarCadastro;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import br.inf.portalfiscal.nfe.schema.retConsCad.TRetConsCad;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
public class Cliente extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement, statcli;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Cadastro: cliente");

    //Labels dos campos da tabela;
    JLabel label_codigo              = new JLabel("Codigo: ");
    JLabel label_razaosocial         = new JLabel("Razão Social: ");
    JLabel label_fantasia            = new JLabel("Fantasia: ");
    JLabel label_pessoa              = new JLabel("Pessoa: ");
    JLabel label_cnpj                = new JLabel("CNPJ/CPF: ");
    JLabel label_inscest             = new JLabel("Inscest: ");
    JLabel label_endereco            = new JLabel("Endereco: ");
    JLabel label_numero              = new JLabel("Numero: ");
    JLabel label_bairro              = new JLabel("Bairro: ");
    JLabel label_cep                 = new JLabel("Cep: ");
    JLabel label_codcidade           = new JLabel("Cidade: ");
    JLabel label_telefone            = new JLabel("Telefone: ");
    JLabel label_contato             = new JLabel("Contato: ");
    JLabel label_ramalcontato        = new JLabel("Ramal: ");
    JLabel label_email               = new JLabel("Email: ");
    JLabel label_consufinal          = new JLabel("Cons.Final(S/N): ");
    JLabel label_diferido            = new JLabel("Diferido(S/N): ");
    JLabel label_ehtransp            = new JLabel("Transportadora(S/N): ");

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
    JButton botao_conscad            = new JButton("Consulta Cliente na Sefaz");
    //JTextFields dos campos das tabelas
    JTextField tf_codigo             = new JTextField();
    JTextField tf_razaosocial        = new JTextField();
    JTextField tf_fantasia           = new JTextField();
    JTextField tf_pessoa             = new JTextField();
    JTextField tf_cnpj               = new JTextField();
    JTextField tf_inscest            = new JTextField();
    JTextField tf_endereco           = new JTextField();
    JTextField tf_numero             = new JTextField();
    JTextField tf_bairro             = new JTextField();
    JTextField tf_cep                = new JTextField();
    JTextField tf_codcidade          = new JTextField();
    JFormattedTextField tf_telefone;  //           = new JTextField();
    JTextField tf_contato            = new JTextField();
    JTextField tf_ramalcontato       = new JTextField();
    JTextField tf_email              = new JTextField();
    JTextField tf_consufinal         = new JTextField();
    JTextField tf_diferido           = new JTextField();
    JTextField tf_ehtransp           = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox<String> cb_pesquisa    = new JComboBox<>();
    JComboBox<String> cb_pesq_lab    = new JComboBox<>();
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
    int indice_pesquisa              = 01;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_razaosocial;
    PreparedStatement ps_aux;
    int empresa = 0;
    private String UF_Cliente;
    private String cli_uf;

    ResultSet rs_pessoa;
    Statement st_pessoa;
    JComboBox<String> cb_pessoa             = new JComboBox<>();
    JLabel label_psqpessoa           = new JLabel("Pesquisar: ");
    JTextField tf_psqpessoa          = new JTextField();
    ButtonGroup bg_psqpessoa         = new ButtonGroup();
    JRadioButton rb_inic_psqpessoa   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqpessoa   = new JRadioButton("Meio");

    ResultSet rs_codcidade;
    Statement st_codcidade;
    JComboBox<String> cb_codcidade          = new JComboBox<>();
    JLabel label_psqcodcidade           = new JLabel("Pesquisar: ");
    JTextField tf_psqcodcidade          = new JTextField();
    ButtonGroup bg_psqcodcidade         = new ButtonGroup();
    JRadioButton rb_inic_psqcodcidade   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcodcidade   = new JRadioButton("Meio");
    MaskFormatter foneFormat, cpfFormat, cnpjFormat;
    String razaoEmp, enderEmp;
    JButton bt_copyDescr     = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar_registro.gif")));
    String UFEmit = "";

    public Cliente(int empresa, Connection confat, String razaoEmpresa, String enderEmpresa, String UFEmit)
    {
        setTitle("Manutencao de Cliente");
        setSize(1010, 680);
        setLocation(10,10);
        setResizable(true);
        this.empresa = empresa;
        this.connection = confat;
        this.razaoEmp = razaoEmpresa;
        this.enderEmp = enderEmpresa;
        this.UFEmit = UFEmit;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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

        //mascara para telefone

        try{
            foneFormat  = new MaskFormatter("(##)####-####");
            tf_telefone = new JFormattedTextField(foneFormat);
            cnpjFormat  = new MaskFormatter("##.###.###/####-##");
            cpfFormat   = new MaskFormatter("###.###.###-##");
            //tf_Cnpj     = new JFormattedTextField(cnpjFormat);
            tf_cnpj     = new JTextField();

        } catch(Exception e) {
            e.printStackTrace();
        }
    //Gera cor frente dos lanbels setForegroundColor()
        label_codigo            .setForeground(Color.black);
        label_razaosocial       .setForeground(Color.black);
        label_fantasia          .setForeground(Color.black);
        label_pessoa            .setForeground(Color.black);
        label_cnpj              .setForeground(Color.black);
        label_inscest           .setForeground(Color.black);
        label_endereco          .setForeground(Color.black);
        label_numero            .setForeground(Color.black);
        label_bairro            .setForeground(Color.black);
        label_cep               .setForeground(Color.black);
        label_codcidade         .setForeground(Color.black);
        label_telefone          .setForeground(Color.black);
        label_contato           .setForeground(Color.black);
        label_ramalcontato      .setForeground(Color.black);
        label_email             .setForeground(Color.black);
        label_consufinal        .setForeground(Color.black);
        label_diferido          .setForeground(Color.black);
        label_ehtransp          .setForeground(Color.black);


    //alinha os labels a direita
        label_codigo            .setHorizontalAlignment(JLabel.RIGHT);
        label_razaosocial       .setHorizontalAlignment(JLabel.RIGHT);
        label_fantasia          .setHorizontalAlignment(JLabel.RIGHT);
        label_pessoa            .setHorizontalAlignment(JLabel.RIGHT);
        label_cnpj              .setHorizontalAlignment(JLabel.RIGHT);
        label_inscest           .setHorizontalAlignment(JLabel.RIGHT);
        label_endereco          .setHorizontalAlignment(JLabel.RIGHT);
        label_numero            .setHorizontalAlignment(JLabel.RIGHT);
        label_bairro            .setHorizontalAlignment(JLabel.RIGHT);
        label_cep               .setHorizontalAlignment(JLabel.RIGHT);
        label_codcidade         .setHorizontalAlignment(JLabel.RIGHT);
        label_telefone          .setHorizontalAlignment(JLabel.RIGHT);
        label_contato           .setHorizontalAlignment(JLabel.RIGHT);
        label_ramalcontato      .setHorizontalAlignment(JLabel.RIGHT);
        label_email             .setHorizontalAlignment(JLabel.RIGHT);
        label_consufinal        .setHorizontalAlignment(JLabel.RIGHT);
        label_diferido          .setHorizontalAlignment(JLabel.RIGHT);
        label_ehtransp          .setHorizontalAlignment(JLabel.RIGHT);


    //Gera cor frente dos texfields setForegroundColor()
        tf_codigo               .setForeground(Color.black);
        tf_razaosocial          .setForeground(Color.black);
        tf_fantasia             .setForeground(Color.black);
        tf_pessoa               .setForeground(Color.black);
        tf_cnpj                 .setForeground(Color.black);
        tf_inscest              .setForeground(Color.black);
        tf_endereco             .setForeground(Color.black);
        tf_numero               .setForeground(Color.black);
        tf_bairro               .setForeground(Color.black);
        tf_cep                  .setForeground(Color.black);
        tf_codcidade            .setForeground(Color.black);
        tf_telefone             .setForeground(Color.black);
        tf_contato              .setForeground(Color.black);
        tf_ramalcontato         .setForeground(Color.black);
        tf_email                .setForeground(Color.black);
        tf_consufinal           .setForeground(Color.black);
        tf_diferido             .setForeground(Color.black);
        tf_ehtransp             .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_codigo               .setBackground(Color.white);
        tf_razaosocial          .setBackground(Color.white);
        tf_fantasia             .setBackground(Color.white);
        tf_pessoa               .setBackground(Color.white);
        tf_cnpj                 .setBackground(Color.white);
        tf_inscest              .setBackground(Color.white);
        tf_endereco             .setBackground(Color.white);
        tf_numero               .setBackground(Color.white);
        tf_bairro               .setBackground(Color.white);
        tf_cep                  .setBackground(Color.white);
        tf_codcidade            .setBackground(Color.white);
        tf_telefone             .setBackground(Color.white);
        tf_contato              .setBackground(Color.white);
        tf_ramalcontato         .setBackground(Color.white);
        tf_email                .setBackground(Color.white);
        tf_consufinal           .setBackground(Color.white);
        tf_diferido             .setBackground(Color.white);
        tf_ehtransp             .setBackground(Color.white);

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
        botao_conscad            .setToolTipText("Consulta Situação do Destinatário na Receita Estadual");

        tf_email                 .setToolTipText("Se informar email, o sistema envia o XML para o cliente automaticamente");

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
        botao_conscad            .addActionListener(this);
        cb_pesq_lab              .addActionListener(this);
        //tf_pesquisa              .addActionListener(this);
        botao_pesquisa           .addActionListener(this);
        cb_pesquisa              .addActionListener(this);
        jTable1                  .addMouseListener(this);
        jTable1                  .addKeyListener(this);
        tf_psqpessoa                       .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqpessoa                       .addKeyListener(this);
        tf_pessoa                       .addKeyListener(this);
        tf_pessoa                       .addFocusListener(this);
        tf_psqcodcidade                    .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcodcidade                    .addKeyListener(this);
        tf_codcidade                    .addKeyListener(this);
        tf_codcidade                    .addFocusListener(this);
        cb_pessoa            .addActionListener(this);
        cb_codcidade         .addActionListener(this);
        bt_copyDescr             .addActionListener(this);

        
        //Posicionando os componentes labels e textfields da tabela
        label_codigo             .setBounds(100,175,100,20);
        tf_codigo                .setBounds(200,175,88,20);
        botao_conscad            .setBounds(650,190,200,30);
        label_razaosocial        .setBounds(100,200,97,20);
        tf_razaosocial           .setBounds(200,200,320,20);
        label_fantasia           .setBounds(100,225,100,20);
        tf_fantasia              .setBounds(200,225,320,20);
        bt_copyDescr             .setBounds(530,225,25,25);
        label_pessoa             .setBounds(575,225,62,20);
        tf_pessoa                .setBounds(637,225,23,20);
        cb_pessoa                .setBounds(670,225, 200,20);
        label_psqpessoa          .setBounds(880,225, 100,20);
        tf_psqpessoa             .setBounds(945,225, 100,20);
        rb_inic_psqpessoa        .setBounds(1040,225, 60,20);
        rb_meio_psqpessoa        .setBounds(1080,225, 55,20);
        label_cnpj               .setBounds(100,250,100,20);
        tf_cnpj                  .setBounds(200,250,144,20);
        label_inscest            .setBounds(369,250,69,20);
        tf_inscest               .setBounds(438,250,160,20);
        label_endereco           .setBounds(100,275,100,20);
        tf_endereco              .setBounds(200,275,360,20);
        label_numero             .setBounds(585,275,62,20);
        tf_numero                .setBounds(647,275,79,20);
        label_bairro             .setBounds(100,300,100,20);
        tf_bairro                .setBounds(200,300,200,20);
        label_cep                .setBounds(425,300,41,20);
        tf_cep                   .setBounds(466,300,88,20);
        label_codcidade          .setBounds(100,325,100,20);
        tf_codcidade             .setBounds(200,325,88,20);
        cb_codcidade             .setBounds(290,325, 200,20);
        label_psqcodcidade       .setBounds(520,325, 100,20);
        tf_psqcodcidade          .setBounds(600,325, 100,20);
        rb_inic_psqcodcidade     .setBounds(700,325, 60,20);
        rb_meio_psqcodcidade     .setBounds(760,325, 55,20);
        label_telefone           .setBounds(100,350,100,20);
        tf_telefone              .setBounds(200,350,160,20);
        label_contato            .setBounds(385,350,69,20);
        tf_contato               .setBounds(454,350,160,20);
        label_ramalcontato       .setBounds(610,350,100,20);
        tf_ramalcontato          .setBounds(710,350,120,20);
        label_email              .setBounds(100,375,100,20);
        tf_email                 .setBounds(200,375,400,20);
        label_consufinal         .setBounds(620,375,100,20);
        tf_consufinal            .setBounds(720,375,20,20);
        label_diferido           .setBounds(750,375,100,20);
        tf_diferido              .setBounds(850,375,20,20);
        label_ehtransp           .setBounds(100,400,100,20);
        tf_ehtransp              .setBounds(200,400,20,20);

        posUltLabel           = 375;
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
                "Razão Social",
                "Pessoa",
                "CNPJ/CPF",
                "Inscest",
                "Consufinal",
                "Diferido"
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_cliente());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_codigo);
        getContentPane()      .add(tf_codigo);
        getContentPane()      .add(label_razaosocial);
        getContentPane()      .add(tf_razaosocial);
        getContentPane()      .add(label_fantasia);
        getContentPane()      .add(tf_fantasia);
        getContentPane()      .add(bt_copyDescr);
        getContentPane()      .add(label_pessoa);
        getContentPane()      .add(tf_pessoa);
        getContentPane()      .add(cb_pessoa);
        //getContentPane()      .add(label_psqpessoa);
        //getContentPane()      .add(tf_psqpessoa);
        //getContentPane()      .add(rb_inic_psqpessoa);
        //getContentPane()      .add(rb_meio_psqpessoa);
        //bg_psqpessoa          .add(rb_inic_psqpessoa);
        //bg_psqpessoa          .add(rb_meio_psqpessoa);
        getContentPane()      .add(label_cnpj);
        getContentPane()      .add(tf_cnpj);
        getContentPane()      .add(label_inscest);
        getContentPane()      .add(tf_inscest);
        getContentPane()      .add(botao_conscad);
        getContentPane()      .add(label_endereco);
        getContentPane()      .add(tf_endereco);
        getContentPane()      .add(label_numero);
        getContentPane()      .add(tf_numero);
        getContentPane()      .add(label_bairro);
        getContentPane()      .add(tf_bairro);
        getContentPane()      .add(label_cep);
        getContentPane()      .add(tf_cep);
        getContentPane()      .add(label_codcidade);
        getContentPane()      .add(tf_codcidade);
        getContentPane()      .add(cb_codcidade);
        getContentPane()      .add(label_psqcodcidade);
        getContentPane()      .add(tf_psqcodcidade);
        getContentPane()      .add(rb_inic_psqcodcidade);
        getContentPane()      .add(rb_meio_psqcodcidade);
        bg_psqcodcidade          .add(rb_inic_psqcodcidade);
        bg_psqcodcidade          .add(rb_meio_psqcodcidade);
        getContentPane()      .add(label_telefone);
        getContentPane()      .add(tf_telefone);
        getContentPane()      .add(label_contato);
        getContentPane()      .add(tf_contato);
        getContentPane()      .add(label_ramalcontato);
        getContentPane()      .add(tf_ramalcontato);
        getContentPane()      .add(label_email);
        getContentPane()      .add(tf_email);
        getContentPane()      .add(label_consufinal);
        getContentPane()      .add(tf_consufinal);
        getContentPane()      .add(label_diferido);
        getContentPane()      .add(tf_diferido);
        getContentPane()      .add(label_ehtransp);
        getContentPane()      .add(tf_ehtransp);
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
        cpo_tabela[1]         = "razaosocial";
        cpo_tabela[2]         = "fantasia";
        cpo_tabela[3]         = "pessoa";
        cpo_tabela[4]         = "cnpj";
        cpo_tabela[5]         = "inscest";
        cpo_tabela[6]         = "endereco";
        cpo_tabela[7]         = "numero";
        cpo_tabela[8]         = "bairro";
        cpo_tabela[9]         = "cep";
        cpo_tabela[10]         = "codcidade";
        cpo_tabela[11]         = "telefone";
        cpo_tabela[12]         = "contato";
        cpo_tabela[13]         = "ramalcontato";
        cpo_tabela[14]         = "email";
        cpo_tabela[15]         = "consufinal";
        cpo_tabela[16]         = "diferido";
        cpo_tabela[17]         = "ehtransp";
        lab_tabela[0]         = "Codigo";
        lab_tabela[1]         = "Razão Social";
        lab_tabela[2]         = "Fantasia";
        lab_tabela[3]         = "Pessoa";
        lab_tabela[4]         = "Cnpj";
        lab_tabela[5]         = "Inscest";
        lab_tabela[6]         = "Endereco";
        lab_tabela[7]         = "Numero";
        lab_tabela[8]         = "Bairro";
        lab_tabela[9]         = "Cep";
        lab_tabela[10]         = "Cidade";
        lab_tabela[11]         = "Telefone";
        lab_tabela[12]         = "Contato";
        lab_tabela[13]         = "Ramal Contato";
        lab_tabela[14]         = "Email";
        lab_tabela[15]         = "Consumidor Final";
        lab_tabela[16]         = "Diferido";
        lab_tabela[17]         = "Transportadora";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[3]          = tf_pessoa;
        tab_cb_st[3]          = st_pessoa;
        tab_cb_rs[3]          = rs_pessoa;
        tab_cb_cb[3]          = cb_pessoa;
        tab_cb_tf[10]          = tf_codcidade;
        tab_cb_st[10]          = st_codcidade;
        tab_cb_rs[10]          = rs_codcidade;
        tab_cb_cb[10]          = cb_codcidade;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[3]     = "pessoa";
        tab_cb_cpo_assoc[3]   = "codigo";
        tab_cb_cpo_exibe[3]   = "descricao";
        tab_cb_tab_box[10]     = "ibge";
        tab_cb_cpo_assoc[10]   = "codigo";
        tab_cb_cpo_exibe[10]   = "cidade";
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
              statcli    = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_pessoa  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_codcidade  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_pessoa, rs_pessoa, cb_pessoa, 3);
              preenche_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, 10);

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
                 boolean isString = false;
                 if (cb_pesq_lab.getSelectedIndex() != 0) isString = true;
                 if (isString) {
                    sql_query = "select * from cliente where upper(" +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+") like '%"+tf_pesquisa.getText().toUpperCase()+"%'";
                 } else {
                    sql_query = "select * from cliente where " +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText();
                 }
             } else {
                  sql_query = "select * from cliente";
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("razaosocial"));
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
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(3);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(20);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(20);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from cliente");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar cliente: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                boolean isString = false;
                 if (cb_pesq_lab.getSelectedIndex() != 0) isString = true;
                 if (isString) {
                    sqlquery = "select * from cliente where upper(" +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+") like '%"+tf_pesquisa.getText().toUpperCase()+"%'";
                 } else {
                    sqlquery = "select * from cliente where " +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText();
                 }
//                sqlquery = "select * from cliente where "
//                           //+" codigo > 0 and "
//                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%' order by "+cpo_tabela[0];
            } else {
                sqlquery = "select * from cliente "
                        //+ "where codigo > 0 "
                        + "order by "+cpo_tabela[0];
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("codigo"),
                resultset.getString("razaosocial"),
                resultset.getString("pessoa"),
                resultset.getString("cnpj"),
                resultset.getString("inscest"),
                resultset.getString("consufinal"),
                resultset.getString("diferido"),
                  }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: cliente: "+qtreg);
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
               tf_razaosocial        .setText(resultset.getString("razaosocial"));
               tf_fantasia           .setText(resultset.getString("fantasia"));
               tf_pessoa             .setText(resultset.getString("pessoa"));
               tf_cnpj               .setText(resultset.getString("cnpj"));
               tf_inscest            .setText(resultset.getString("inscest"));
               tf_endereco           .setText(resultset.getString("endereco"));
               tf_numero             .setText(resultset.getString("numero"));
               tf_bairro             .setText(resultset.getString("bairro"));
               tf_cep                .setText(resultset.getString("cep"));
               tf_codcidade          .setText(resultset.getString("codcidade"));
               tf_telefone           .setText(resultset.getString("telefone") == null ? "" : resultset.getString("telefone"));
               tf_contato            .setText(resultset.getString("contato") == null ? "" : resultset.getString("contato"));
               tf_ramalcontato       .setText(resultset.getString("ramalcontato"));
               tf_email              .setText(resultset.getString("email"));
               tf_consufinal         .setText(resultset.getString("consufinal"));
               tf_diferido           .setText(resultset.getString("diferido"));
               tf_ehtransp           .setText(resultset.getString("ehtransp"));

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
            else if (acao.getSource() == bt_copyDescr) {
                String wdesc = tf_razaosocial.getText();
                if (wdesc.length() > 60) {
                    wdesc = wdesc.substring(0, 60);
                }
                tf_fantasia.setText(wdesc);
            }
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
                this.dispose();
           }
            else if (acao.getSource() == cb_pessoa){
               if ( stat_aux)
                   trata_cb_auxiliar(st_pessoa, rs_pessoa, cb_pessoa, tf_pessoa, 3);
            }
            else if (acao.getSource() == cb_codcidade){
               if ( stat_aux)
                   trata_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, tf_codcidade, 10);
            }
            else if (acao.getSource() == botao_conscad) {
                if (!tf_cnpj.getText().equals("") && !tf_pessoa.getText().equals("")) {
                    String codigoUF = Biblioteca.pegaEstado(UFEmit).name();
                    if (!tf_codcidade.getText().equals("")) {
                        pegaUfDest();
                        codigoUF = this.getCli_uf();
                    }
                    while (true){
                        codigoUF = JOptionPane.showInputDialog("Informe/Confirme a UF para Consultar Cliente com Cnpj/Cpf ["+tf_cnpj.getText()+"]", codigoUF);
                        break;
                    }
//JOptionPane.showMessageDialog(null,"Clientes - codigoUF: " + codigoUF + " - UFEmit: " + UFEmit);                    
                    if (codigoUF != null) {
                        codigoUF = codigoUF.toUpperCase();
                        ConsultarCadastro conscad = new ConsultarCadastro(
                                getFile_keystore(), 
                                getSenha_keystore(), 
                                getTipoAmbiente(), 
                                Biblioteca.pegaEstado(UFEmit)
                            );
                        String resultado = null;
                        TRetConsCad retorno;
                        if (tf_pessoa.getText().equals("J")) {
                            retorno = conscad.consultar(codigoUF, tf_cnpj.getText(), null);
                        } else {
                            retorno = conscad.consultar(codigoUF, null, tf_cnpj.getText());
                        }
        		if(retorno.getInfCons().getCStat().equals("111")){
                            if (tf_pessoa.getText().equals("J")) {
                                resultado =
                                    "Razão Social: "+retorno.getInfCons().getInfCad().get(0).getXNome() +
                                    "\nFantasia: "+retorno.getInfCons().getInfCad().get(0).getXFant() +
                                    "\nCnpj:"+retorno.getInfCons().getInfCad().get(0).getCNPJ() +
                                    " - Insc.Estadual:"+retorno.getInfCons().getInfCad().get(0).getIE();
                            } else {
                                resultado = 
                                    "Nome: "+retorno.getInfCons().getInfCad().get(0).getXNome() +
                                    "\nCPF:"+retorno.getInfCons().getInfCad().get(0).getCPF();
                            }
                            String situacao = retorno.getInfCons().getInfCad().get(0).getCSit();
                            String descSituacao = situacao.equals("0") ? "Nao Habilitado" : "Habilitado";
//                            String dataBaixa = retorno.getInfCons().getInfCad().get(0).getDBaixa().toString();
//                            String descDataBaixa = dataBaixa == null ? "" : dataBaixa;
                            resultado +=  
                                    "\nSituacao:"+situacao + " - "+descSituacao +
                                    "\nInicio Atividade:"+retorno.getInfCons().getInfCad().get(0).getDIniAtiv() +
                                    "\nData Baixa:"+retorno.getInfCons().getInfCad().get(0).getDBaixa() +
                                    "\nEndereco:"+retorno.getInfCons().getInfCad().get(0).getEnder().getXLgr() +
                                    " , "+retorno.getInfCons().getInfCad().get(0).getEnder().getNro() +
                                    " - Bairro: "+retorno.getInfCons().getInfCad().get(0).getEnder().getXBairro() +
                                     " \nCidade:"+retorno.getInfCons().getInfCad().get(0).getEnder().getXMun() +
                                   " - "+retorno.getInfCons().getInfCad().get(0).getUF();
                        } else if (retorno.getInfCons().getCStat().equals("999")) {
                            resultado = "999 - Cliente não encontrado ou Erro não catalogado!";
                        } else {
                            resultado = retorno.getInfCons().getCStat()+" - " +retorno.getInfCons().getXMotivo();
                        }
                        JOptionPane.showMessageDialog(null, resultado);
         		if(retorno.getInfCons().getCStat().equals("111")){
                            if (escolheCapturaDados()) {
                                 tf_razaosocial.setText(retorno.getInfCons().getInfCad().get(0).getXNome());
                                 tf_fantasia.setText(retorno.getInfCons().getInfCad().get(0).getXFant());
                                 tf_inscest.setText(retorno.getInfCons().getInfCad().get(0).getIE());
                                 tf_endereco.setText(retorno.getInfCons().getInfCad().get(0).getEnder().getXLgr());
                                 tf_numero.setText(retorno.getInfCons().getInfCad().get(0).getEnder().getNro());
                                 tf_bairro.setText(retorno.getInfCons().getInfCad().get(0).getEnder().getXBairro());
                                 tf_cep.setText(retorno.getInfCons().getInfCad().get(0).getEnder().getCEP());
                                 tf_codcidade.setText(pegaCidade(retorno.getInfCons().getInfCad().get(0).getEnder().getCMun()));
                                 mostra_tf_ref_cb_aux(tab_cb_st[10], tab_cb_rs[10], tab_cb_cb[10], tab_cb_tf[10], 10);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhuma UF foi informada e a Consulta NÃO será feita!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Informe o Cpf/Cnpj e o Tipo de Pessoa para consultar!");
                }

            }
      }
    private boolean escolheCapturaDados() {
       boolean retorno = false;
       UIManager.put("OptionPane.yesButtonText", "Atualizar Cadastro");
       UIManager.put("OptionPane.noButtonText", "Não Atualizar");
       int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Atualizar Dados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       if ( escolha == JOptionPane.YES_OPTION){
            retorno = true;
       } else {
            JOptionPane.showMessageDialog(null, "Você desistiu de Atualizar o Cadastro do Cliente com os Dados da Sefaz");
            retorno = false;
       }

       UIManager.put("OptionPane.yesButtonText", "Sim");
       UIManager.put("OptionPane.noButtonText", "Não");

        return retorno;
    }
    private String pegaCidade(String codcidade) {
          String retorno = "";
            String sql = "select codigo from ibge where codcidade = "+codcidade.trim();
            try {
                resultset = statement.executeQuery(sql);
                resultset.last();
                retorno = resultset.getString("codigo");
             } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar capturar o codigo da cidade. Erro: "+e);
                JOptionPane.showMessageDialog(null, "Comando: "+sql);
            }
          //}
          return retorno;
    }
      private String pegaUfDest() {
          String retorno = "PR";
          //if (wcodcliente > 0) {
            String sql = "select codcidade, uf from ibge where codigo = "+tf_codcidade.getText();
            try {
                resultset = statement.executeQuery(sql);
                resultset.last();
                this.setCli_uf(resultset.getString("uf"));
                retorno = resultset.getString("codcidade").substring(0, 2);
             } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar capturar a UF do Cliente. Erro: "+e);
                JOptionPane.showMessageDialog(null, "Comando: "+sql);
            }
          //}
          return retorno;
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
         String sql = "";
        try
        {
            sql = "select max(codigo) as ultcod from cliente";
            ResultSet rscli = statcli.executeQuery(sql);
            while (rscli.next()) {
//JOptionPane.showMessageDialog(null, "Entrou no while . . . ");
                ult_cod = rscli.getInt("ultcod");
            }
            ult_cod++;
        }
        catch(SQLException erro)
        {
             JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro+"\nsql: "+sql);
            ult_cod = 1;
        }
        tf_codigo             .setText(""+ult_cod);
        tf_razaosocial        .setText("");
        tf_fantasia           .setText("");
        tf_pessoa             .setText("");
        tf_cnpj               .setText("");
        tf_inscest            .setText("");
        tf_endereco           .setText("");
        tf_numero             .setText("");
        tf_bairro             .setText("");
        tf_cep                .setText("");
        tf_codcidade          .setText("");
        tf_telefone           .setText("");
        tf_contato            .setText("");
        tf_ramalcontato       .setText("");
        tf_email              .setText("");
        tf_consufinal         .setText("N");
        tf_diferido           .setText("N");
        tf_ehtransp           .setText("N");
      posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
           tf_codigo.setEditable(false);
           botao_gravar.setEnabled(true);
           botao_alterar.setEnabled(false);
           botao_excluir.setEnabled(false);
//Limpa filtros da inclusao anterior nos comboBox auxiliares 
          if (!tf_psqpessoa.getText().equals("")){
             tf_psqpessoa.setText("");
             preenche_cb_auxiliar(st_pessoa, rs_pessoa, cb_pessoa, 3);
          }
          if (!tf_psqcodcidade.getText().equals("")){
             tf_psqcodcidade.setText("");
             preenche_cb_auxiliar(st_codcidade, rs_codcidade, cb_codcidade, 10);
          }
     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_codigo.getText() == null ) tf_codigo.setText("");
        if ( tf_razaosocial.getText() == null ) tf_razaosocial.setText("");
        if ( tf_fantasia.getText() == null ) tf_fantasia.setText("");
        if ( tf_pessoa.getText() == null ) tf_pessoa.setText("");
        if (tf_pessoa.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        if ( tf_cnpj.getText() == null ) tf_cnpj.setText("");
        if ( tf_inscest.getText() == null ) tf_inscest.setText("");
        if ( tf_endereco.getText() == null ) tf_endereco.setText("");
        if ( tf_numero.getText() == null ) tf_numero.setText("");
        if ( tf_bairro.getText() == null ) tf_bairro.setText("");
        if ( tf_cep.getText() == null ) tf_cep.setText("");
        if ( tf_codcidade.getText() == null ) tf_codcidade.setText("");
        tf_razaosocial.setText(tf_razaosocial.getText().toUpperCase());
        tf_fantasia.setText(tf_fantasia.getText().toUpperCase());
        tf_endereco.setText(tf_endereco.getText().toUpperCase());
        tf_bairro.setText(tf_bairro.getText().toUpperCase());
        if (tf_codcidade.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        if ( tf_telefone.getText() == null ) tf_telefone.setText("");
        if ( tf_contato.getText() == null ) tf_contato.setText("");
        if ( tf_ramalcontato.getText() == null ) tf_ramalcontato.setText("");
        if ( tf_email.getText() == null ) tf_email.setText("");
        if ( tf_consufinal.getText() == null || tf_consufinal.getText().equals("")) tf_consufinal.setText("N");
        if ( tf_diferido.getText() == null || tf_diferido.getText().equals("")) tf_diferido.setText("N");
        if ( tf_ehtransp.getText() == null || tf_ehtransp.getText().equals("")) tf_ehtransp.setText("N");
        tf_consufinal.setText(tf_consufinal.getText().trim().toUpperCase());
        if (!tf_consufinal.getText().equals("N") && !tf_consufinal.getText().equals("S")) {
           JOptionPane.showMessageDialog(null, "O valor para Consumidor Final deve ser S ou N !");
           retorno = false;
        }
        tf_diferido.setText(tf_diferido.getText().trim().toUpperCase());
        if (!tf_diferido.getText().equals("N") && !tf_diferido.getText().equals("S")) {
           JOptionPane.showMessageDialog(null, "O valor para Diferido deve ser S ou N !");
           retorno = false;
        }
        tf_ehtransp.setText(tf_ehtransp.getText().trim().toUpperCase());
        if (!tf_ehtransp.getText().equals("N") && !tf_ehtransp.getText().equals("S")) {
           JOptionPane.showMessageDialog(null, "O valor para Transportadora deve ser S ou N !");
           retorno = false;
        }
        if (tf_cep.getText().length() != 8) {
           JOptionPane.showMessageDialog(null, "O CEP deve conter exatamente 8 caracteres!");
           retorno = false;
        }
        if (tf_razaosocial.getText().length() > 60) {
           JOptionPane.showMessageDialog(null, "A Razao Social nâo pode ter mais de 60 caracteres!");
           retorno = false;
        }
        if (tf_fantasia.getText().length() > 60) {
           JOptionPane.showMessageDialog(null, "A Fantasia nâo pode ter mais de 60 caracteres!");
           retorno = false;
        }
        //System.out.println("Consufinal: ["+tf_consufinal.getText()+"] - Diferido: ["+tf_diferido.getText()+"] . . .");
        return retorno;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                 String sql_check = "select * from cliente where cnpj = '" + tf_cnpj.getText().toString().trim() + "'";
                 resultset = statement.executeQuery(sql_check);
                 boolean achou = false;
                 String razao = "";
                 String codcli = "";
                 while(resultset.next()) {
                     achou = true;
                     codcli = resultset.getString("codigo");
                     razao = resultset.getString("razaosocial");
                 }
                 if (achou) {
                     JOptionPane.showMessageDialog(null, "Já existe o cliente: "+codcli+" - "+razao+" com este CNPJ no sistema!");
                 } else {
                    String sql_insert = "insert into cliente ( "+
                           "codigo, "+"razaosocial, "+"fantasia, "+"pessoa, "+"cnpj, "+"inscest, "+"endereco, "+"numero, "+"bairro, "+
                           "cep, "+"codcidade, "+"telefone, "+"contato, "+"ramalcontato, "+"email, "+"consufinal, "+"diferido, "+"ehtransp "+")"+
                           " values (";
                    sql_insert += tf_codigo.getText() + ", ";
                    sql_insert += "'"+tf_razaosocial.getText() + "', ";

                                            sql_insert += "'"+tf_fantasia.getText() + "', ";
                    sql_insert += "'"+tf_pessoa.getText() + "', ";
                    sql_insert += "'"+tf_cnpj.getText() + "', ";
                    sql_insert += "'"+tf_inscest.getText() + "', ";

                                            sql_insert += "'"+tf_endereco.getText() + "', ";
                    sql_insert += "'"+tf_numero.getText() + "', ";
                    sql_insert += "'"+tf_bairro.getText() + "', ";

                                            sql_insert += tf_cep.getText() + ", ";
                    sql_insert += tf_codcidade.getText() + ", ";
                    sql_insert += "'"+tf_telefone.getText() + "', ";
                    sql_insert += "'"+tf_contato.getText() + "', ";

                    sql_insert += "'"+tf_ramalcontato.getText() + "', ";
                    sql_insert += "'"+tf_email.getText() + "', ";
                    sql_insert += "'"+tf_consufinal.getText() + "', ";
                    sql_insert += "'"+tf_diferido.getText() + "', ";
                    sql_insert += "'"+tf_ehtransp.getText() + "' ";
   sql_insert += ")";
               //System.out.println("Comando sql_insert = " + sql_insert);
               //JOptionPane.showMessageDialog(null,"Comando sql_insert = " + sql_insert);
                    statement.executeUpdate(sql_insert);
                    JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                    clicado = false;
                    preencher_cb_pesquisa("tudo");
                    preencher_jtable("tudo");
                    resultset  = statement.executeQuery("select * from cliente");
                    resultset.last();
                    //mostra_conteudo_nos_campos();
                    posiciona_combo_e_jtable();
                    clicado = true;
                    novo_registro();
                    }
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
                 String sql_alterar = "update cliente set " ;
                 sql_alterar += "razaosocial = '"+ tf_razaosocial.getText()+"', ";
                 sql_alterar += "fantasia = '"+ tf_fantasia.getText()+"', ";
                 sql_alterar += "pessoa = '"+ tf_pessoa.getText()+"', ";
                 sql_alterar += "cnpj = '"+ tf_cnpj.getText()+"', ";
                 sql_alterar += "inscest = '"+ tf_inscest.getText()+"', ";
                 sql_alterar += "endereco = '"+ tf_endereco.getText()+"', ";
                 sql_alterar += "numero = '"+ tf_numero.getText()+"', ";
                 sql_alterar += "bairro = '"+ tf_bairro.getText()+"', ";
                 sql_alterar += "cep = "+ tf_cep.getText()+", ";
                 sql_alterar += "codcidade = "+ tf_codcidade.getText()+", ";
                 sql_alterar += "telefone = '"+ tf_telefone.getText()+"', ";
                 sql_alterar += "contato = '"+ tf_contato.getText()+"', ";
                 sql_alterar += "ramalcontato = '"+ tf_ramalcontato.getText()+"', ";
                 sql_alterar += "email = '"+ tf_email.getText()+"', " ;
                 sql_alterar += "consufinal = '"+ tf_consufinal.getText()+"', " ;
                 sql_alterar += "diferido = '"+ tf_diferido.getText()+"', " ;
                 sql_alterar += "ehtransp = '"+ tf_ehtransp.getText()+"' " ;
                 sql_alterar += " where codigo = " + tf_codigo.getText() ;
                  //System.out.println("sql_altera = " + sql_alterar);
                  //JOptionPane.showMessageDialog(null, "sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from cliente");
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
           String nome = "Deletar cliente : "+tf_razaosocial.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM cliente Where codigo = " + tf_codigo.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from cliente");
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
        //Impressao impr = new Impressao(empresa, "rel_cliente.jasper");
        //impr.imprimeRelJasper();
    }
    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            cb_aux.removeAllItems();
            String sql_aux = "select * from "+tab_cb_tab_box[indice];
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next()) {
//                 cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                if (indice == 10) {  //  ibge
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
                    if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_assoc[indice])){
                        j = i;
                        break;
                    }
                }
		 try {
                    String sql_aux;
                    String chave1 = "", chave2 = "";
                    String chave = cb_aux.getSelectedItem().toString();
                    chave1 = chave.substring(chave.indexOf(" - ")+3, chave.length());
                    chave2 = chave.substring(0, chave.indexOf(" - "));
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?"; //'"+cb_aux.getSelectedItem()+"'";
                    System.out.println("Sql_aux: "+sql_aux);
                    if (ps_aux != null)
                        ps_aux.clearParameters();
                    ps_aux = connection.prepareStatement(sql_aux);
                    if (j >0){
                        System.out.println("Tipo do campo ["+rs_aux.getMetaData().getColumnName(j)+"] = "+rs_aux.getMetaData().getColumnType(j));
                        if (rs_aux.getMetaData().getColumnType(j) == 4 )
                            ps_aux.setInt(1, Integer.parseInt(chave2));
                        else
                            ps_aux.setString(1, chave2);
                    }
//                    System.out.println("Sql_aux: "+sql_aux+ " - chave1: [" + chave1 + "] - chave2: [" + chave2 + "]");
                    rs_aux = ps_aux.executeQuery();
		}finally {}
                while(rs_aux.next()) {
                     tf_aux.setText(rs_aux.getString(tab_cb_cpo_assoc[indice]));
 //                    System.out.println(" chave selecionada: " + rs_aux.getString(tab_cb_cpo_assoc[indice]));
                }
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
                 if (indice == 10) {
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]) + "(" + rs_aux.getString("uf") + ")");
                 } else {
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                 }
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
           resultset  = statement.executeQuery("select * from cliente");  // where codigo = " + tf_pesquisa.getText().toUpperCase());
           resultset.first();
           boolean achou = false;
           int reg = 0;
           String cpo_tab;
           while(true)
           {
               cpo_tab = resultset.getString(cpo_tabela[cb_pesq_lab.getSelectedIndex()]);
System.out.println("Pesquisando: "+cpo_tab.toUpperCase()+" - e comparando com: "+tf_pesquisa.getText().toUpperCase());
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
System.out.println("Cliente - vai chamar mostra_cont...");
                mostra_conteudo_nos_campos();
System.out.println("Cliente - voltou de mostra_cont...");
                clicado = false;
                preencher_cb_pesquisa("filtrar");
                cb_pesquisa.setSelectedItem(tf_razaosocial.getText());
                preencher_jtable("filtrar");
                clicado = true;
           } else {
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
        String sql = "select * from cliente where Descricao = "+cb_pesquisa.getSelectedItem();
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
                    String sql_query = "select * from cliente where "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%' order by "+cpo_tabela[0];
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from cliente order by "+cpo_tabela[0]);
                }
                //resultset = statement.executeQuery("select * from cliente order by "+cpo_tabela[0]);
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
       } else if(e.getSource() == tf_pessoa) {
           System.out.println("Teclou enter no campo pessoa . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3);
               tf_pessoa.requestFocus();
           }
       } else if(e.getSource() == tf_codcidade) {
           System.out.println("Teclou enter no campo codcidade . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[10], tab_cb_cb[10], tab_cb_tf[10], 10);
               tf_codcidade.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqpessoa){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_pessoa, rs_pessoa, cb_pessoa, tf_psqpessoa, rb_inic_psqpessoa, rb_meio_psqpessoa, 3);
        }
        else 
        if (e.getSource() == tf_psqcodcidade){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_codcidade, rs_codcidade, cb_codcidade, tf_psqcodcidade, rb_inic_psqcodcidade, rb_meio_psqcodcidade, 10);
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
                   sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '"+tf_psqAux.getText().toUpperCase()+"%'";
                else
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" like '%"+tf_psqAux.getText().toUpperCase()+"%'";
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next()) {
                if (indice == 10) {
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
       if (e.getSource() == tf_pessoa){
           System.out.println("Perdeu o foco do campo pessoa . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3))
        tf_pessoa.requestFocus();
       }
       if (e.getSource() == tf_codcidade){
           System.out.println("Perdeu o foco do campo codcidade . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[10], tab_cb_cb[10], tab_cb_tf[10], 10))
        tf_codcidade.requestFocus();
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
                    if (indice == 10) {
                        cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]) + "(" + rs_aux.getString("uf") + ")");
                    } else {
                        cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                    }
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
        //JFrame form = new Cliente(0, null);
        //    form.setVisible(true);
    }

    public String getUF_Cliente() {
        return UF_Cliente;
    }

    public void setUF_Cliente(String UF_Cliente) {
        this.UF_Cliente = UF_Cliente;
    }

    public String getCli_uf() {
        return cli_uf;
    }

    public void setCli_uf(String cli_uf) {
        this.cli_uf = cli_uf;
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_cliente extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_cliente()
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
