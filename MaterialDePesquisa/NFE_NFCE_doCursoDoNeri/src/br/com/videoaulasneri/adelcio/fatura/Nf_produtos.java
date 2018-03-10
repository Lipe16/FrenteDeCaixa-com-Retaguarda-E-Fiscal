/*

Descrição: Digitação dos Produtos para Emissão da NFe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Relatorios.Impressao;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
////classe auxiliar para centralizar as colunas dp JTable
public class Nf_produtos extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement, stat_forma, stat_prazo, stat_prod, stat_max;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Cadastro: nf_produtos");

    //Labels dos campos da tabela;
    JLabel label_pedido              = new JLabel("Pedido: ");
    JLabel label_item                = new JLabel("Item: ");
    JLabel label_cod_produto         = new JLabel("Cod.Produto: ");
    JLabel label_cod_cfop            = new JLabel("Cod.CFOP: ");
    JLabel label_quantidade          = new JLabel("Quantidade: ");
    JLabel label_peso                = new JLabel("Peso: ");
    JLabel label_vlr_unitario        = new JLabel("Vlr Unitario: ");
    JLabel label_vlr_produto         = new JLabel("Vlr Produto: ");
    JLabel label_vlr_desconto        = new JLabel("Vlr Desconto: ");
    JLabel label_vlr_total           = new JLabel("Vlr Total: ");
    JLabel label_icms_bc             = new JLabel("Icms BC: ");
    JLabel label_icms_perc           = new JLabel("Icms %: ");
    JLabel label_icms_pred           = new JLabel("Icms % Red: ");
    JLabel label_icms_vlr            = new JLabel("Icms Vlr: ");
    JLabel label_icms_cst            = new JLabel("Icms CST: ");
    JLabel label_ipi_bc              = new JLabel("Ipi BC: ");
    JLabel label_ipi_perc           = new JLabel("Ipi %: ");
    JLabel label_ipi_vlr             = new JLabel("Ipi Vlr: ");
    JLabel label_ipi_cst             = new JLabel("Ipi CST: ");
    JLabel label_pis_bc              = new JLabel("Pis BC: ");
    JLabel label_pis_perc           = new JLabel("Pis %: ");
    JLabel label_pis_vlr             = new JLabel("Pis Vlr: ");
    JLabel label_pis_cst             = new JLabel("Pis CST: ");
    JLabel label_cofins_bc           = new JLabel("Cofins BC: ");
    JLabel label_cofins_perc           = new JLabel("Cofins %: ");
    JLabel label_cofins_vlr          = new JLabel("Cofins Vlr: ");
    JLabel label_cofins_cst          = new JLabel("Cofins CST: ");

    JLabel label_vlr_produtos         = new JLabel("Vlr Produtos NF: ");
    JLabel label_vlr_descontos        = new JLabel("Vlr Descontos NF: ");
    JLabel label_vlr_totais           = new JLabel("Vlr Total NF: ");
    JLabel jlFoto                    = new JLabel();
    
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
    JButton botao_salvar               = new JButton("Salvar");
    //JTextFields dos campos das tabelas
    JTextField tf_pedido             = new JTextField();
    JTextField tf_item               = new JTextField();
    JTextField tf_cod_produto        = new JTextField();
    JTextField tf_cod_cfop           = new JTextField();
    JTextField tf_quantidade         = new JTextField();
    JTextField tf_peso               = new JTextField();
    JTextField tf_vlr_unitario       = new JTextField();
    JTextField tf_vlr_produto        = new JTextField();
    JTextField tf_vlr_desconto       = new JTextField();
    JTextField tf_vlr_total          = new JTextField();
    JTextField tf_icms_bc            = new JTextField();
    JTextField tf_icms_perc          = new JTextField();
    JTextField tf_icms_pred          = new JTextField();
    JTextField tf_icms_vlr           = new JTextField();
    JTextField tf_icms_cst           = new JTextField();
    JTextField tf_ipi_bc             = new JTextField();
    JTextField tf_ipi_perc           = new JTextField();
    JTextField tf_ipi_vlr            = new JTextField();
    JTextField tf_ipi_cst            = new JTextField();
    JTextField tf_pis_bc             = new JTextField();
    JTextField tf_pis_perc           = new JTextField();
    JTextField tf_pis_vlr            = new JTextField();
    JTextField tf_pis_cst            = new JTextField();
    JTextField tf_cofins_bc          = new JTextField();
    JTextField tf_cofins_perc        = new JTextField();
    JTextField tf_cofins_vlr         = new JTextField();
    JTextField tf_cofins_cst         = new JTextField();

    JTextField tf_vlr_produtos        = new JTextField();
    JTextField tf_vlr_descontos       = new JTextField();
    JTextField tf_vlr_totais          = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox<String> cb_pesquisa            = new JComboBox<>();
    JComboBox<String> cb_pesq_lab            = new JComboBox<>();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[24];
    String [] lab_tabela             = new String[24];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[24];
    String [] tab_cb_cpo_exibe       = new String[24];
    String [] tab_cb_cpo_assoc       = new String[24];
    JTextField [] tab_cb_tf          = new JTextField[24];
    Statement [] tab_cb_st           = new Statement[24];
    ResultSet [] tab_cb_rs           = new ResultSet[24];
    JComboBox [] tab_cb_cb           = new JComboBox[24];
    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 01;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_item;
    PreparedStatement ps_aux;
    int empresa = 0;
    String pedido = "";

    ResultSet rs_cod_produto, rs_nf, rs_dup;
    Statement st_cod_produto, st_nf, st_dup;
    JComboBox<String> cb_cod_produto      = new JComboBox<>();
    JLabel label_psqcod_produto           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_produto          = new JTextField();
    ButtonGroup bg_psqcod_produto         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_produto   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_produto   = new JRadioButton("Meio");
    ResultSet rs_cod_cfop;
    Statement st_cod_cfop;
    JComboBox<String> cb_cod_cfop           = new JComboBox<>();
    JLabel label_psqcod_cfop           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_cfop          = new JTextField();
    ButtonGroup bg_psqcod_cfop         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_cfop   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_cfop   = new JRadioButton("Meio");
    JPanel jPFoto                      = new javax.swing.JPanel();

    private boolean alterou = false;
    double vlr_produto_old  = 0.00,
            vlr_desconto_old = 0.00,
            vlr_total_old   = 0.00,
            icms_bc_old     = 0.00,
            icms_vlr_old    = 0.00,
            ipi_bc_old      = 0.00,
            ipi_vlr_old     = 0.00,
            pis_bc_old      = 0.00,
            pis_vlr_old     = 0.00,
            cofins_bc_old   = 0.00,
            cofins_vlr_old  = 0.00;
    double qtde_volume_old     = 0;
    double peso_volume_old  = 0;
    double preco = 0;
    int numeronfe = 0;
    private double preco_custo;
    private String ufCDest, ufEmit;
    String fsep = System.getProperty("file.separator");

    public Nf_produtos(int empresa, Connection confat, String pedido, String numeronfe, String ufEmit, String ufCDest)
    {
        setTitle("Formulario de Manutencao de Produtos da NF");
        setSize(1010, 680);
        setLocation(135,5);
        setResizable(true);
        this.empresa = empresa;
        this.connection = confat;
        this.pedido = pedido;
        this.numeronfe = Integer.parseInt(numeronfe);
        this.ufCDest = ufCDest;
        setUfEmit(ufEmit);
System.out.println("nf_produtos - UFEmit: "+ufEmit);        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
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
        label_pedido            .setForeground(Color.black);
        label_item              .setForeground(Color.black);
        label_cod_produto       .setForeground(Color.black);
        label_cod_cfop          .setForeground(Color.black);
        label_quantidade        .setForeground(Color.black);
        label_peso              .setForeground(Color.black);
        label_vlr_unitario      .setForeground(Color.black);
        label_vlr_produto       .setForeground(Color.black);
        label_vlr_desconto      .setForeground(Color.black);
        label_vlr_total         .setForeground(Color.black);
        label_icms_bc           .setForeground(Color.black);
        label_icms_perc         .setForeground(Color.black);
        label_icms_pred         .setForeground(Color.black);
        label_icms_vlr          .setForeground(Color.black);
        label_icms_cst          .setForeground(Color.black);
        label_ipi_bc            .setForeground(Color.black);
        label_ipi_perc         .setForeground(Color.black);
        label_ipi_vlr           .setForeground(Color.black);
        label_ipi_cst           .setForeground(Color.black);
        label_pis_bc            .setForeground(Color.black);
        label_pis_perc         .setForeground(Color.black);
        label_pis_vlr           .setForeground(Color.black);
        label_pis_cst           .setForeground(Color.black);
        label_cofins_bc         .setForeground(Color.black);
        label_cofins_perc         .setForeground(Color.black);
        label_cofins_vlr        .setForeground(Color.black);
        label_cofins_cst        .setForeground(Color.black);

        label_vlr_produtos       .setForeground(Color.black);
        label_vlr_descontos      .setForeground(Color.black);
        label_vlr_totais         .setForeground(Color.black);
        jlFoto                  .setForeground(Color.black);

    //alinha os labels a direita
        label_pedido            .setHorizontalAlignment(JLabel.RIGHT);
        label_item              .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_produto       .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_cfop          .setHorizontalAlignment(JLabel.RIGHT);
        label_quantidade        .setHorizontalAlignment(JLabel.RIGHT);
        label_peso              .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_unitario      .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_produto       .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_desconto      .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_total         .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_bc           .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_perc         .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_pred         .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_vlr          .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_cst          .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_bc            .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_perc         .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_vlr           .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_cst           .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_bc            .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_perc         .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_vlr           .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_cst           .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_bc         .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_perc         .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_vlr        .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_cst        .setHorizontalAlignment(JLabel.RIGHT);

        label_vlr_produtos       .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_descontos      .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_totais         .setHorizontalAlignment(JLabel.RIGHT);
        jlFoto                   .setHorizontalAlignment(JLabel.RIGHT);
        jPFoto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPFoto.setBackground(new java.awt.Color(102, 102, 102));

    //Gera cor frente dos texfields setForegroundColor()
        tf_pedido               .setForeground(Color.black);
        tf_item                 .setForeground(Color.black);
        tf_cod_produto          .setForeground(Color.black);
        tf_cod_cfop             .setForeground(Color.black);
        tf_quantidade           .setForeground(Color.black);
        tf_peso                 .setForeground(Color.black);
        tf_vlr_unitario         .setForeground(Color.black);
        tf_vlr_produto          .setForeground(Color.black);
        tf_vlr_desconto         .setForeground(Color.black);
        tf_vlr_total            .setForeground(Color.black);
        tf_icms_bc              .setForeground(Color.black);
        tf_icms_perc            .setForeground(Color.black);
        tf_icms_pred            .setForeground(Color.black);
        tf_icms_vlr             .setForeground(Color.black);
        tf_icms_cst             .setForeground(Color.black);
        tf_ipi_bc               .setForeground(Color.black);
        tf_ipi_perc             .setForeground(Color.black);
        tf_ipi_vlr              .setForeground(Color.black);
        tf_ipi_cst              .setForeground(Color.black);
        tf_pis_bc               .setForeground(Color.black);
        tf_pis_perc             .setForeground(Color.black);
        tf_pis_vlr              .setForeground(Color.black);
        tf_pis_cst              .setForeground(Color.black);
        tf_cofins_bc            .setForeground(Color.black);
        tf_cofins_perc          .setForeground(Color.black);
        tf_cofins_vlr           .setForeground(Color.black);
        tf_cofins_cst           .setForeground(Color.black);

        tf_vlr_produtos          .setForeground(Color.black);
        tf_vlr_descontos         .setForeground(Color.black);
        tf_vlr_totais            .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_pedido               .setBackground(new Color(255, 255, 158));
        tf_item                 .setBackground(Color.white);
        tf_cod_produto          .setBackground(new Color(255, 255, 158));
        tf_cod_cfop             .setBackground(new Color(255, 255, 158));
        tf_quantidade           .setBackground(new Color(255, 255, 158));
        tf_peso                 .setBackground(Color.white);
        tf_vlr_unitario         .setBackground(Color.white);
        tf_vlr_produto          .setBackground(Color.white);
        tf_vlr_desconto         .setBackground(Color.white);
        tf_vlr_total            .setBackground(Color.white);
        tf_icms_bc              .setBackground(Color.white);
        tf_icms_perc            .setBackground(Color.white);
        tf_icms_pred            .setBackground(Color.white);
        tf_icms_vlr             .setBackground(Color.white);
        tf_icms_cst             .setBackground(Color.white);
        tf_ipi_bc               .setBackground(Color.white);
        tf_ipi_perc             .setBackground(Color.white);
        tf_ipi_vlr              .setBackground(Color.white);
        tf_ipi_cst              .setBackground(Color.white);
        tf_pis_bc               .setBackground(Color.white);
        tf_pis_perc             .setBackground(Color.white);
        tf_pis_vlr              .setBackground(Color.white);
        tf_pis_cst              .setBackground(Color.white);
        tf_cofins_bc            .setBackground(Color.white);
        tf_cofins_perc          .setBackground(Color.white);
        tf_cofins_vlr           .setBackground(Color.white);
        tf_cofins_cst           .setBackground(Color.white);

        tf_vlr_produtos          .setBackground(Color.white);
        tf_vlr_descontos         .setBackground(Color.white);
        tf_vlr_totais            .setBackground(Color.white);

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
        botao_salvar              .setBackground(Color.red);

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
        botao_salvar               .setBounds(730,125,100,30);

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
        botao_salvar             .setToolTipText("Salva o Registro Atual");

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
        botao_salvar             .addActionListener(this);
        cb_pesq_lab              .addActionListener(this);
        //tf_pesquisa              .addActionListener(this);
        botao_pesquisa           .addActionListener(this);
        cb_pesquisa              .addActionListener(this);
        jTable1                  .addMouseListener(this);
        jTable1                  .addKeyListener(this);
        tf_psqcod_produto        .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_produto        .addKeyListener(this);
        tf_cod_produto           .addKeyListener(this);
        tf_cod_produto           .addFocusListener(this);
        tf_psqcod_cfop           .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_cfop           .addKeyListener(this);
        tf_cod_cfop              .addKeyListener(this);
        tf_cod_cfop              .addFocusListener(this);
        cb_cod_produto           .addActionListener(this);
        cb_cod_produto           .addFocusListener(this);
        cb_cod_produto           .addMouseListener(this);
        cb_cod_cfop              .addActionListener(this);
        cb_cod_cfop              .addFocusListener(this);
        cb_cod_cfop              .addMouseListener(this);
        tf_vlr_unitario          .addKeyListener(this);
        tf_vlr_unitario          .addMouseListener(this);
        tf_vlr_unitario          .addFocusListener(this);
        tf_vlr_desconto          .addKeyListener(this);
        tf_quantidade            .addKeyListener(this);
        tf_quantidade            .addFocusListener(this);
        tf_quantidade            .addMouseListener(this);

       
        //Posicionando os componentes labels e textfields da tabela
        label_pedido             .setBounds(100,175,100,20);
        tf_pedido                .setBounds(200,175,88,20);
        label_item               .setBounds(313,175,48,20);
        tf_item                  .setBounds(361,175,88,20);

        label_cod_produto        .setBounds(100,200,100,20);
        tf_cod_produto           .setBounds(200,200,88,20);
        cb_cod_produto           .setBounds(298,200, 400,20);
        label_psqcod_produto     .setBounds(708,200, 100,20);
        tf_psqcod_produto        .setBounds(773,200, 100,20);
        rb_inic_psqcod_produto   .setBounds(868,200, 60,20);
        rb_meio_psqcod_produto   .setBounds(928,200, 55,20);

        label_cod_cfop           .setBounds(100,225,100,20);
        tf_cod_cfop              .setBounds(200,225,88,20);
        cb_cod_cfop              .setBounds(298,225, 400,20);
        label_psqcod_cfop        .setBounds(708,225, 100,20);
        tf_psqcod_cfop           .setBounds(773,225, 100,20);
        rb_inic_psqcod_cfop      .setBounds(868,225, 60,20);
        rb_meio_psqcod_cfop      .setBounds(928,225, 55,20);

        label_quantidade         .setBounds(100,250,100,20);
        tf_quantidade            .setBounds(200,250,88,20);
        label_peso               .setBounds(300,250,100,20);
        tf_peso                  .setBounds(400,250,92,20);
        label_vlr_unitario       .setBounds(500,250,100,20);
        tf_vlr_unitario          .setBounds(600,250,92,20);

        label_vlr_produto        .setBounds(100,275,100,20);
        tf_vlr_produto           .setBounds(200,275,92,20);
        label_vlr_desconto       .setBounds(300,275,100,20);
        tf_vlr_desconto          .setBounds(400,275,92,20);
        label_vlr_total          .setBounds(500,275,100,20);
        tf_vlr_total             .setBounds(600,275,92,20);

        label_icms_bc            .setBounds(100,300,100,20);
        tf_icms_bc               .setBounds(200,300,92,20);
        label_icms_perc          .setBounds(300,300,100,20);
        tf_icms_perc             .setBounds(400,300,92,20);
        label_icms_vlr           .setBounds(500,300,100,20);
        tf_icms_vlr              .setBounds(600,300,92,20);
        label_icms_cst           .setBounds(700,300,100,20);
        tf_icms_cst              .setBounds(800,300,39,20);
        label_icms_pred          .setBounds(840,300,83,20);
        tf_icms_pred             .setBounds(930,300,42,20);
        jPFoto                  .setBounds(850,330,100,100);
        jlFoto                   .setBounds(850,330,100,100);


        label_ipi_bc             .setBounds(100,325,100,20);
        tf_ipi_bc                .setBounds(200,325,92,20);
        label_ipi_perc          .setBounds(300,325,100,20);
        tf_ipi_perc             .setBounds(400,325,92,20);
        label_ipi_vlr            .setBounds(500,325,100,20);
        tf_ipi_vlr               .setBounds(600,325,92,20);
        label_ipi_cst            .setBounds(700,325,100,20);
        tf_ipi_cst               .setBounds(800,325,39,20);

        label_pis_bc             .setBounds(100,350,100,20);
        tf_pis_bc                .setBounds(200,350,92,20);
        label_pis_perc          .setBounds(300,350,100,20);
        tf_pis_perc             .setBounds(400,350,92,20);
        label_pis_vlr            .setBounds(500,350,100,20);
        tf_pis_vlr               .setBounds(600,350,92,20);
        label_pis_cst            .setBounds(700,350,100,20);
        tf_pis_cst               .setBounds(800,350,39,20);

        label_cofins_bc          .setBounds(100,375,100,20);
        tf_cofins_bc             .setBounds(200,375,92,20);
        label_cofins_perc          .setBounds(300,375,100,20);
        tf_cofins_perc             .setBounds(400,375,92,20);
        label_cofins_vlr         .setBounds(500,375,100,20);
        tf_cofins_vlr            .setBounds(600,375,92,20);
        label_cofins_cst         .setBounds(700,375,100,20);
        tf_cofins_cst            .setBounds(800,375,39,20);

        label_vlr_produtos        .setBounds(100,425,100,20);
        tf_vlr_produtos           .setBounds(200,425,92,20);
        label_vlr_descontos       .setBounds(300,425,100,20);
        tf_vlr_descontos          .setBounds(400,425,92,20);
        label_vlr_totais          .setBounds(500,425,100,20);
        tf_vlr_totais             .setBounds(600,425,92,20);

        posUltLabel           = 425;
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
                "Pedido",
                "Item",
                "Cod_produto",
                "Cod_cfop",
                "Quantidade",
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_nf_produtos());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_pedido);
        getContentPane()      .add(tf_pedido);
        getContentPane()      .add(label_item);
        getContentPane()      .add(tf_item);
        getContentPane()      .add(label_cod_produto);
        getContentPane()      .add(tf_cod_produto);
        getContentPane()      .add(cb_cod_produto);
        getContentPane()      .add(label_psqcod_produto);
        getContentPane()      .add(tf_psqcod_produto);
        getContentPane()      .add(rb_inic_psqcod_produto);
        getContentPane()      .add(rb_meio_psqcod_produto);
        bg_psqcod_produto          .add(rb_inic_psqcod_produto);
        bg_psqcod_produto          .add(rb_meio_psqcod_produto);
        getContentPane()      .add(label_cod_cfop);
        getContentPane()      .add(tf_cod_cfop);
        getContentPane()      .add(cb_cod_cfop);
        getContentPane()      .add(label_psqcod_cfop);
        getContentPane()      .add(tf_psqcod_cfop);
        getContentPane()      .add(rb_inic_psqcod_cfop);
        getContentPane()      .add(rb_meio_psqcod_cfop);
        bg_psqcod_cfop          .add(rb_inic_psqcod_cfop);
        bg_psqcod_cfop          .add(rb_meio_psqcod_cfop);
        getContentPane()      .add(label_quantidade);
        getContentPane()      .add(tf_quantidade);
        getContentPane()      .add(label_peso);
        getContentPane()      .add(tf_peso);
        getContentPane()      .add(label_vlr_unitario);
        getContentPane()      .add(tf_vlr_unitario);
        getContentPane()      .add(label_vlr_produto);
        getContentPane()      .add(tf_vlr_produto);
        getContentPane()      .add(label_vlr_desconto);
        getContentPane()      .add(tf_vlr_desconto);
        getContentPane()      .add(label_vlr_total);
        getContentPane()      .add(tf_vlr_total);
        getContentPane()      .add(label_icms_bc);
        getContentPane()      .add(tf_icms_bc);
        getContentPane()      .add(label_icms_perc);
        getContentPane()      .add(tf_icms_perc);
        getContentPane()      .add(label_icms_pred);
        getContentPane()      .add(tf_icms_pred);
        getContentPane()      .add(label_icms_vlr);
        getContentPane()      .add(tf_icms_vlr);
        getContentPane()      .add(label_icms_cst);
        getContentPane()      .add(tf_icms_cst);
        getContentPane()      .add(label_ipi_bc);
        getContentPane()      .add(tf_ipi_bc);
        getContentPane()      .add(label_ipi_vlr);
        getContentPane()      .add(label_ipi_perc);
        getContentPane()      .add(tf_ipi_perc);
        getContentPane()      .add(tf_ipi_vlr);
        getContentPane()      .add(label_ipi_cst);
        getContentPane()      .add(tf_ipi_cst);
        getContentPane()      .add(label_pis_bc);
        getContentPane()      .add(tf_pis_bc);
        getContentPane()      .add(label_pis_perc);
        getContentPane()      .add(tf_pis_perc);
        getContentPane()      .add(label_pis_vlr);
        getContentPane()      .add(tf_pis_vlr);
        getContentPane()      .add(label_pis_cst);
        getContentPane()      .add(tf_pis_cst);
        getContentPane()      .add(label_cofins_bc);
        getContentPane()      .add(tf_cofins_bc);
        getContentPane()      .add(label_cofins_perc);
        getContentPane()      .add(tf_cofins_perc);
        getContentPane()      .add(label_cofins_vlr);
        getContentPane()      .add(tf_cofins_vlr);
        getContentPane()      .add(label_cofins_cst);
        getContentPane()      .add(tf_cofins_cst);
        getContentPane()      .add(jScrollPane);

        getContentPane()      .add(label_vlr_produtos);
        getContentPane()      .add(tf_vlr_produtos);
        getContentPane()      .add(label_vlr_descontos);
        getContentPane()      .add(tf_vlr_descontos);
        getContentPane()      .add(label_vlr_totais);
        getContentPane()      .add(tf_vlr_totais);

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
        getContentPane()      .add(botao_salvar);
        getContentPane()      .add(jPFoto);
        getContentPane()      .add(jlFoto);

        cpo_tabela[0]         = "pedido";
        cpo_tabela[1]         = "item";
        cpo_tabela[2]         = "cod_produto";
        cpo_tabela[3]         = "cod_cfop";
        cpo_tabela[4]         = "quantidade";
        cpo_tabela[5]         = "peso";
        cpo_tabela[6]         = "vlr_unitario";
        cpo_tabela[7]         = "vlr_produto";
        cpo_tabela[8]         = "vlr_desconto";
        cpo_tabela[9]         = "vlr_total";
        cpo_tabela[10]         = "icms_bc";
        cpo_tabela[11]         = "icms_perc";
        cpo_tabela[12]         = "icms_pred";
        cpo_tabela[13]         = "icms_vlr";
        cpo_tabela[14]         = "icms_cst";
        cpo_tabela[15]         = "ipi_bc";
        cpo_tabela[16]         = "ipi_vlr";
        cpo_tabela[17]         = "ipi_cst";
        cpo_tabela[18]         = "pis_bc";
        cpo_tabela[19]         = "pis_vlr";
        cpo_tabela[20]         = "pis_cst";
        cpo_tabela[21]         = "cofins_bc";
        cpo_tabela[22]         = "cofins_vlr";
        cpo_tabela[23]         = "cofins_cst";
        lab_tabela[0]         = "Pedido";
        lab_tabela[1]         = "Item";
        lab_tabela[2]         = "Cod_produto";
        lab_tabela[3]         = "Cod_cfop";
        lab_tabela[4]         = "Quantidade";
        lab_tabela[5]         = "Peso";
        lab_tabela[6]         = "Vlr_unitario";
        lab_tabela[7]         = "Vlr_produto";
        lab_tabela[8]         = "Vlr_desconto";
        lab_tabela[9]         = "Vlr_total";
        lab_tabela[10]         = "Icms_bc";
        lab_tabela[11]         = "Icms_perc";
        lab_tabela[12]         = "Icms_pred";
        lab_tabela[13]         = "Icms_vlr";
        lab_tabela[14]         = "Icms_cst";
        lab_tabela[15]         = "Ipi_bc";
        lab_tabela[16]         = "Ipi_vlr";
        lab_tabela[17]         = "Ipi_cst";
        lab_tabela[18]         = "Pis_bc";
        lab_tabela[19]         = "Pis_vlr";
        lab_tabela[20]         = "Pis_cst";
        lab_tabela[21]         = "Cofins_bc";
        lab_tabela[22]         = "Cofins_vlr";
        lab_tabela[23]         = "Cofins_cst";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[2]          = tf_cod_produto;
        tab_cb_st[2]          = st_cod_produto;
        tab_cb_rs[2]          = rs_cod_produto;
        tab_cb_cb[2]          = cb_cod_produto;
        tab_cb_tf[3]          = tf_cod_cfop;
        tab_cb_st[3]          = st_cod_cfop;
        tab_cb_rs[3]          = rs_cod_cfop;
        tab_cb_cb[3]          = cb_cod_cfop;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[2]     = "produto";
        tab_cb_cpo_assoc[2]   = "codigo";
        tab_cb_cpo_exibe[2]   = "descricao";
        tab_cb_tab_box[3]     = "cfop";
        tab_cb_cpo_assoc[3]   = "codigo";
        tab_cb_cpo_exibe[3]   = "descricao";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        try
        {
              //conexao conn = new conexao();
              //connection = conn.conecta("", "");
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stat_max  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_cod_produto  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_cod_cfop  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_nf  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_dup  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stat_forma  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stat_prazo  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stat_prod  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_cod_produto, rs_cod_produto, cb_cod_produto, 2);
              preenche_cb_auxiliar(st_cod_cfop, rs_cod_cfop, cb_cod_cfop, 3);

              //mostra_conteudo_nos_campos();
              preencher_jtable("tudo");
              //cb_pesquisa.addActionListener(this);
              stat_aux = true;
              clicado  = true;
              novo_registro();
            tf_vlr_produtos        .setText("0.00");
            tf_vlr_descontos       .setText("0.00");
            tf_vlr_totais          .setText("0.00");
            atualizarTotaisNf();
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
                 String texto = "";
//                 if (cb_pesq_lab.getSelectedIndex() != 0) {
//                    texto =  cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
//                 } else {
                    texto =  cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText();
//                 }

                  sql_query = "select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                          + texto
                                 +" order by item";
             } else {
                  sql_query = "select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                          ;
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("item"));
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
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(33);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from nf_produtos");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar nf_produtos: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                  String texto = "";
//                 if (cb_pesq_lab.getSelectedIndex() != 0) {
//                    texto =  cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
//                 } else {
                    texto =  cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText();
//                 }
               sqlquery = "select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+"and "
                           + texto
                           +" order by item";
            } else {
                sqlquery = "select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                        ;
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("pedido"),
                resultset.getString("item"),
                resultset.getString("cod_produto"),
                resultset.getString("cod_cfop"),
                resultset.getString("quantidade"),
                resultset.getString("peso"),
                resultset.getString("vlr_unitario"),
                                            }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: nf_produtos: "+qtreg);
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
System.out.println("nf_produtos - mostra_conteudo_nos_campos . . .");
           clicado = false;
           try
           {
               String data;
               tf_pedido             .setText(resultset.getString("pedido"));
               tf_item               .setText(resultset.getString("item"));
               tf_cod_produto        .setText(resultset.getString("cod_produto"));
               tf_cod_cfop           .setText(resultset.getString("cod_cfop"));
System.out.println("nf_produtos - Passou 1 . . .");
               tf_quantidade         .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("quantidade")) ,2 ,0));
System.out.println("nf_produtos - Passou 2 . . .");

               tf_peso               .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("peso")) ,3 ,0));
               tf_vlr_unitario       .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_unitario")) ,2 ,0));
               tf_vlr_produto        .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_produto")) ,2 ,0));
               tf_vlr_desconto       .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_desconto")) ,2 ,0));
               tf_vlr_total          .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_total")) ,2 ,0));
               tf_icms_bc            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_bc")) ,2 ,0));
               tf_icms_perc          .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_perc")) ,2 ,0));
               tf_icms_pred          .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_pred")) ,2 ,0));
               tf_icms_vlr           .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_vlr")) ,2 ,0));
               tf_icms_cst           .setText(resultset.getString("icms_cst"));
               tf_ipi_bc             .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("ipi_bc")) ,2 ,0));
               tf_ipi_perc           .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("ipi_perc")) ,2 ,0));
               tf_ipi_vlr            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("ipi_vlr")) ,2 ,0));
               tf_ipi_cst            .setText(resultset.getString("ipi_cst"));
               tf_pis_bc             .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("pis_bc")) ,2 ,0));
               tf_pis_perc           .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("pis_perc")) ,2 ,0));
               tf_pis_vlr            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("pis_vlr")) ,2 ,0));
               tf_pis_cst            .setText(resultset.getString("pis_cst"));
               tf_cofins_bc          .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("cofins_bc")) ,2 ,0));
               tf_cofins_perc        .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("cofins_perc")) ,2 ,0));
               tf_cofins_vlr         .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("cofins_vlr")) ,2 ,0));
               tf_cofins_cst         .setText(resultset.getString("cofins_cst"));
System.out.println("nf_produtos - Passou 3 . . .");

               posiciona_combo_e_jtable();
              tf_pedido.setEnabled(false);
              tf_peso.setEnabled(false);
              tf_vlr_produto.setEnabled(false);
              tf_vlr_total.setEnabled(false);
              tf_icms_bc.setEnabled(false);
              tf_icms_perc.setEnabled(false);
              tf_icms_vlr.setEnabled(false);
              tf_icms_pred.setEnabled(false);
              tf_icms_cst.setEnabled(false);
              tf_ipi_bc.setEnabled(false);
              tf_ipi_perc.setEnabled(false);
              tf_ipi_vlr.setEnabled(false);
              tf_ipi_cst.setEnabled(false);
              tf_pis_bc.setEnabled(false);
              tf_pis_perc.setEnabled(false);
              tf_pis_vlr.setEnabled(false);
              tf_pis_cst.setEnabled(false);
              tf_cofins_bc.setEnabled(false);
              tf_cofins_perc.setEnabled(false);
              tf_cofins_vlr.setEnabled(false);
              tf_cofins_cst.setEnabled(false);
               navega =0;

              tf_vlr_produtos.setEnabled(false);
              tf_vlr_descontos.setEnabled(false);
              tf_vlr_totais.setEnabled(false);
               
System.out.println("nf_produtos - Passou 4 . . .");
            vlr_produto_old  = Double.parseDouble(tf_vlr_produto.getText());
            vlr_desconto_old = Double.parseDouble(tf_vlr_desconto.getText());
            vlr_total_old    = Double.parseDouble(tf_vlr_total.getText());
            icms_bc_old      = Double.parseDouble(tf_icms_bc.getText());
            icms_vlr_old     = Double.parseDouble(tf_icms_vlr.getText());
            ipi_bc_old       = Double.parseDouble(tf_ipi_bc.getText());
            ipi_vlr_old      = Double.parseDouble(tf_ipi_vlr.getText());
            pis_bc_old       = Double.parseDouble(tf_pis_bc.getText());
            pis_vlr_old      = Double.parseDouble(tf_pis_vlr.getText());
            cofins_bc_old    = Double.parseDouble(tf_cofins_bc.getText());
            cofins_vlr_old   = Double.parseDouble(tf_cofins_vlr.getText());
            qtde_volume_old  = Double.parseDouble(tf_quantidade.getText());
            peso_volume_old  = Double.parseDouble(tf_peso.getText());
            String imagem = "";
System.out.println("nf_produtos - Passou 5 . . .");
            try
            {
                String sql_prod = "select *  from produto where codigo = "+Integer.parseInt(tf_cod_produto.getText());
                rs_cod_produto = st_cod_produto.executeQuery(sql_prod);
                float peso_unit = 0;
                while (rs_cod_produto.next()) {
                    imagem = rs_cod_produto.getString("images");
                }
            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null,"Erro ao tentar ler a tabela Produto");
            }
System.out.println("nf_produtos - Passou 6 . . .");
                
            System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem);
            File f = new File(System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem);
            Image image;
            try {
                image = ImageIO.read(f);
              jlFoto.setIcon(new ImageIcon(image.getScaledInstance(jlFoto.getWidth(),jlFoto.getHeight(), Image.SCALE_DEFAULT)));  //("/imagens/produtos/semfoto.jpg"));
            } catch (IOException ex) {
                System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem+"Erro ao carregar foto: "+ex);
            }
System.out.println("nf_produtos - Passou 7 . . .");
           }
           catch(Exception erro_sql)
           {
               if (navega == 1) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
               else if (navega == 2) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel avancar pois voce ja esta no ultimo registro da tabela");
               else {
                   JOptionPane.showMessageDialog(null,"Erro não catalogado! "+erro_sql);
               }
//               novo_registro();
           }
           clicado = true;
//Desabilita campo chave para alteracao/exclusao 
           botao_gravar.setEnabled(false);
           botao_alterar.setEnabled(true);
           botao_excluir.setEnabled(true);
           tf_pedido.setEnabled(false);
           tf_cod_produto.requestFocus();
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
            else if (acao.getSource() == botao_salvar) {
                if (numeronfe > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    if (check_textField())
                        gravar();
                }
            }
            else if (acao.getSource() == botao_excluir)
                if (numeronfe > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    excluir();
                }
            else if (acao.getSource() == botao_alterar){
                if (numeronfe > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    if (check_textField())
                        alterar();
                }
            }
            else if (acao.getSource() == botao_novo)
                if (numeronfe > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    novo_registro();
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
                if (alterou) {

                }
                this.dispose();
           }
            else if (acao.getSource() == cb_cod_produto){
//JOptionPane.showMessageDialog(null, "Campo: tf_cod_produto . . .");
                String filtro = "";
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_produto, rs_cod_produto, cb_cod_produto, tf_cod_produto, 2, filtro);
            }
            else if (acao.getSource() == cb_cod_cfop){
                //JOptionPane.showMessageDialog(null, "Campo: tf_cod_cfop . . .");
                String filtro = "";
                if (this.ufCDest.equals(getUfEmit())) {
                    filtro = "cfop < 6000";
                } else {
                    filtro = "cfop >= 6000";
                }
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_cfop, rs_cod_cfop, cb_cod_cfop, tf_cod_cfop, 3, filtro);
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
            //.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
            JOptionPane.showMessageDialog(null,"Nao foi possivel avançar pois voce ja esta no último registro da tabela");
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
            //JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
            JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
        }
    }

    //acao ultimo registro
    public void vai_ultimo_registro()
    {
        try
        {
             navega=2;
             resultset.last();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            //JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
            JOptionPane.showMessageDialog(null,"Nao foi possivel avançar pois voce ja esta no último registro da tabela");
        }
    }

    //acao proximo registro
    public void vai_primeiro_registro()
    {
        try
        {
             navega=1;
             resultset.first();
             mostra_conteudo_nos_campos();
             //preencher_jtable();
        }
        catch(SQLException erro_sql)
        {
            //JOptionPane.showMessageDialog(null,"Nao conseguiu mostrar os dados "+erro_sql);
            JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
        }
    }
    public void novo_registro()
    {
         int ult_cod=0;
        try
        {
            String sql_prod = "select max(item) as maxcod from nf_produtos where empresa = " + empresa + " and pedido = " + pedido;
System.out.println("Sql_prod: " + sql_prod);
            rs_cod_produto = stat_max.executeQuery(sql_prod);
            while (rs_cod_produto.next()) {
                ult_cod = rs_cod_produto.getInt("maxcod");
            }
            ult_cod++;
        }
        catch(SQLException erro)
        {
             JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
            ult_cod = 1;
        }
        tf_pedido             .setText(pedido);
        tf_item               .setText(""+ult_cod);
        tf_cod_produto        .setText("");
        tf_cod_cfop           .setText("");
        tf_quantidade         .setText("1.00");
        tf_peso               .setText("0.00");
        tf_vlr_unitario       .setText("0.00");
        tf_vlr_produto        .setText("0.00");
        tf_vlr_desconto       .setText("0.00");
        tf_vlr_total          .setText("0.00");
        tf_icms_bc            .setText("0.00");
        tf_icms_perc          .setText("0.00");
        tf_icms_pred          .setText("0.00");
        tf_icms_vlr           .setText("0.00");
        tf_icms_cst           .setText("00");
        tf_ipi_bc             .setText("0.00");
        tf_ipi_perc           .setText("0.00");
        tf_ipi_vlr            .setText("0.00");
        tf_ipi_cst            .setText("00");
        tf_pis_bc             .setText("0.00");
        tf_pis_perc           .setText("0.00");
        tf_pis_vlr            .setText("0.00");
        tf_pis_cst            .setText("00");
        tf_cofins_bc          .setText("0.00");
        tf_cofins_perc        .setText("0.00");
        tf_cofins_vlr         .setText("0.00");
        tf_cofins_cst         .setText("00");

        
        posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
        tf_pedido.setEnabled(false);
        tf_item.setEnabled(false);
        tf_peso.setEnabled(false);
        tf_vlr_produto.setEnabled(false);
        tf_vlr_total.setEnabled(false);
        tf_icms_bc.setEnabled(false);
        tf_icms_perc.setEnabled(false);
        tf_icms_vlr.setEnabled(false);
        tf_icms_pred.setEnabled(false);
        tf_icms_cst.setEnabled(false);
        tf_ipi_bc.setEnabled(false);
        tf_ipi_perc.setEnabled(false);
        tf_ipi_vlr.setEnabled(false);
        tf_ipi_cst.setEnabled(false);
        tf_pis_bc.setEnabled(false);
        tf_pis_perc.setEnabled(false);
        tf_pis_vlr.setEnabled(false);
        tf_pis_cst.setEnabled(false);
        tf_cofins_bc.setEnabled(false);
        tf_cofins_perc.setEnabled(false);
        tf_cofins_vlr.setEnabled(false);
        tf_cofins_cst.setEnabled(false);
    }
    
    private void zerarVlrOld() {
        vlr_produto_old  = 0.00;
        vlr_desconto_old = 0.00;
        vlr_total_old   = 0.00;
        icms_bc_old     = 0.00;
        icms_vlr_old    = 0.00;
        ipi_bc_old      = 0.00;
        ipi_vlr_old     = 0.00;
        pis_bc_old      = 0.00;
        pis_vlr_old     = 0.00;
        cofins_bc_old   = 0.00;
        cofins_vlr_old  = 0.00;
        qtde_volume_old = 0.00;
        peso_volume_old = 0.00;
        
    }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_pedido.getText() == null ) tf_pedido.setText("");
        if ( tf_item.getText() == null ) tf_item.setText("");
        if ( tf_cod_produto.getText() == null ) tf_cod_produto.setText("");
        if (tf_cod_produto.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        if ( tf_cod_cfop.getText() == null ) tf_cod_cfop.setText("");
        if (tf_cod_cfop.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Um Codigo válido precisa ser informado!");
           retorno = false;
        }
        if ( tf_quantidade.getText() == null ) tf_quantidade.setText("0.00");
        if ( tf_peso.getText() == null ) tf_peso.setText("0.00");
        if ( tf_vlr_unitario.getText() == null ) tf_vlr_unitario.setText("0.00");
        if ( tf_vlr_produto.getText() == null ) tf_vlr_produto.setText("0.00");
        if ( tf_vlr_desconto.getText() == null ) tf_vlr_desconto.setText("0.00");
        if ( tf_vlr_total.getText() == null ) tf_vlr_total.setText("0.00");
        if ( tf_icms_bc.getText() == null ) tf_icms_bc.setText("0.00");
        if ( tf_icms_perc.getText() == null ) tf_icms_perc.setText("0");
        if ( tf_icms_pred.getText() == null ) tf_icms_pred.setText("0");
        if ( tf_icms_vlr.getText() == null ) tf_icms_vlr.setText("0.00");
        if ( tf_icms_cst.getText() == null ) tf_icms_cst.setText("");
        if ( tf_ipi_bc.getText() == null ) tf_ipi_bc.setText("0.00");
        if ( tf_ipi_perc.getText() == null ) tf_ipi_perc.setText("0");
        if ( tf_ipi_vlr.getText() == null ) tf_ipi_vlr.setText("0.00");
        if ( tf_ipi_cst.getText() == null ) tf_ipi_cst.setText("");
        if ( tf_pis_bc.getText() == null ) tf_pis_bc.setText("0.00");
        if ( tf_pis_perc.getText() == null ) tf_pis_perc.setText("0");
        if ( tf_pis_vlr.getText() == null ) tf_pis_vlr.setText("0.00");
        if ( tf_pis_cst.getText() == null ) tf_pis_cst.setText("");
        if ( tf_cofins_bc.getText() == null ) tf_cofins_bc.setText("0.00");
        if ( tf_cofins_perc.getText() == null ) tf_cofins_perc.setText("0");
        if ( tf_cofins_vlr.getText() == null ) tf_cofins_vlr.setText("0.00");
        if ( tf_cofins_cst.getText() == null ) tf_cofins_cst.setText("");
        if (Double.parseDouble(tf_vlr_unitario.getText()) < (preco * 0.8)) {
           JOptionPane.showMessageDialog(null, "CUIDADO! Preco informado Menor que 20% do Valor cadastrado ["+preco+"]");
        }
        if (Double.parseDouble(tf_vlr_unitario.getText()) > (preco * 1.2)) {
           JOptionPane.showMessageDialog(null, "CUIDADO! Preco informado Maior que 20% do Valor cadastrado ["+preco+"]");
        }
        if (
                Integer.parseInt(tf_icms_perc.getText()) > 0 && tf_icms_cst.getText().equals("102") ||
                Integer.parseInt(tf_icms_perc.getText()) == 0 && tf_icms_cst.getText().equals("101") 
               ) {
           JOptionPane.showMessageDialog(null, "O codigo CST do ICMS = 101 exige Aliquota MAIOR que 0!");
           retorno = false;
        }
        return retorno;
    }
    public boolean ValidaNumero(JTextField Numero, String nomeLabel) {
        System.out.println("Metodo ValidaNumero(): "+Numero.getText());
        Double valor;
        if(Numero.getText() == null || Numero.getText().equals("")) {
            Numero.setText("0");
            return true;
        } else if (Numero.getText().length() != 0){
            try {
                valor = Double.parseDouble(Numero.getText());
                return true;
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Esse Campo ["+nomeLabel+"] so aceita numeros" ,"Informacao",JOptionPane.INFORMATION_MESSAGE);
                Numero.grabFocus();
                return false;
            }
        } else return true;
    }
    public String formataNumDec(String texto, int tamDec) {
        if (texto.equals("") || texto == null) texto = "0";
        String texto_formatado = texto;
        int posicaoPonto = texto.indexOf(".");
        int atualDec = 0;
        if (posicaoPonto == -1){ //o valor e um inteiro, acrescente as casas decimais
            texto_formatado = texto_formatado+".";
        }else {
            atualDec = texto.length()-(posicaoPonto+1);  //qtde de casas decimais antes da formatacao
        }
        if (atualDec>tamDec)
            texto_formatado = texto_formatado.substring(0,texto_formatado.indexOf(".")+tamDec+1);
        else {
            for (int i=atualDec; i<tamDec; i++)
                texto_formatado = texto_formatado+"0";
        }
        return texto_formatado;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                String sql_prod = "select *  from produto where codigo = "+Integer.parseInt(tf_cod_produto.getText());
                rs_cod_produto = stat_prod.executeQuery(sql_prod);
                float peso_unit = 0;
                while (rs_cod_produto.next()) {
                    peso_unit = rs_cod_produto.getFloat("peso");
                }
                tf_peso.setText(""+Biblioteca.arredondar((Double.parseDouble(tf_quantidade.getText()) * peso_unit) ,3,0));
                //captura_dados_produto();
//JOptionPane.showMessageDialog(null, "gravar() - icms_perc = " + tf_icms_perc.getText()+" - icms_pred = " + tf_icms_pred.getText());
                tf_vlr_unitario.setText(tf_vlr_unitario.getText().replaceAll(",", "."));
                double vlr_prod = (Double.parseDouble(tf_vlr_unitario.getText())) * Double.parseDouble(tf_quantidade.getText());
                double vlr_desc = 0.0;  //Double.parseDouble(tf_vlr_unitario.getText());
                tf_vlr_produto.setText(""+vlr_prod);  //  (tf_vlr_produto.getText().replaceAll(",", "."));
                tf_vlr_desconto.setText(tf_vlr_desconto.getText().replaceAll(",", "."));
                tf_vlr_total.setText(""+(vlr_prod - vlr_desc));  //  (tf_vlr_total.getText().replaceAll(",", "."));
                tf_quantidade.setText(tf_quantidade.getText().replaceAll(",", "."));
                tf_peso.setText(tf_peso.getText().replaceAll(",", "."));
                tf_icms_bc.setText(tf_icms_bc.getText().replaceAll(",", "."));
                tf_icms_perc.setText(tf_icms_perc.getText().replaceAll(",", "."));
                tf_icms_vlr.setText(tf_icms_vlr.getText().replaceAll(",", "."));
                tf_icms_pred.setText(tf_icms_pred.getText().replaceAll(",", "."));
                tf_ipi_bc.setText(tf_ipi_bc.getText().replaceAll(",", "."));
                tf_ipi_perc.setText(tf_ipi_perc.getText().replaceAll(",", "."));
                tf_ipi_vlr.setText(tf_ipi_vlr.getText().replaceAll(",", "."));
                tf_pis_bc.setText(tf_pis_bc.getText().replaceAll(",", "."));
                tf_pis_perc.setText(tf_pis_perc.getText().replaceAll(",", "."));
                tf_pis_vlr.setText(tf_pis_vlr.getText().replaceAll(",", "."));
                tf_cofins_bc.setText(tf_cofins_bc.getText().replaceAll(",", "."));
                tf_cofins_perc.setText(tf_cofins_perc.getText().replaceAll(",", "."));
                tf_cofins_vlr.setText(tf_cofins_vlr.getText().replaceAll(",", "."));
                 String sql_insert = "insert into nf_produtos ( "+
                         "empresa, " +
                        "pedido, "+
                        "item, "+
                        "cod_produto, "+
                        "cod_cfop, "+
                        "quantidade, "+
                        "peso, "+
                        "preco_custo, "+
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
                         empresa + " , " +
                        tf_pedido.getText() + ", "+
                        tf_item.getText() + ", "+
                        tf_cod_produto.getText() + ", "+
                        tf_cod_cfop.getText() + ", "+
                        tf_quantidade.getText() + ", "+
                        ""+tf_peso.getText() + ", "+
                        ""+getPreco_custo() + ", "+
                        ""+tf_vlr_unitario.getText() + ", "+
                        ""+tf_vlr_produto.getText() + ", "+
                        ""+tf_vlr_desconto.getText() + ", "+
                        ""+tf_vlr_total.getText() + ", "+
                        ""+tf_icms_bc.getText() + ", "+
                        ""+tf_icms_perc.getText() + ", "+
                        ""+tf_icms_pred.getText() + ", "+
                        ""+tf_icms_vlr.getText() + ", "+
                        "'"+tf_icms_cst.getText() + "', "+
                        ""+tf_ipi_bc.getText() + ", "+
                        ""+tf_ipi_perc.getText() + ", "+
                        ""+tf_ipi_vlr.getText() + ", "+
                        "'"+tf_ipi_cst.getText() + "', "+
                        ""+tf_pis_bc.getText() + ", "+
                        ""+tf_pis_perc.getText() + ", "+
                        ""+tf_pis_vlr.getText() + ", "+
                        "'"+tf_pis_cst.getText() + "', "+
                        ""+tf_cofins_bc.getText() + ", "+
                        ""+tf_cofins_perc.getText() + ", "+
                        ""+tf_cofins_vlr.getText() + ", "+
                        "'"+tf_cofins_cst.getText() + "' " +
                        ")";
//JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                         );
                 resultset.last();
                 //mostra_conteudo_nos_campos();
                 posiciona_combo_e_jtable();
                 clicado = true;
                 alterou = true;
                 atualizar_nf();
                 novo_registro();
                 zerarVlrOld();
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o registro!\nErro: "+erro);
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
                 //captura_dados_produto();
                tf_vlr_unitario.setText(tf_vlr_unitario.getText().replaceAll(",", "."));
                tf_vlr_produto.setText(tf_vlr_produto.getText().replaceAll(",", "."));
                tf_vlr_desconto.setText(tf_vlr_desconto.getText().replaceAll(",", "."));
                tf_vlr_total.setText(tf_vlr_total.getText().replaceAll(",", "."));
                tf_quantidade.setText(tf_quantidade.getText().replaceAll(",", "."));
                tf_peso.setText(tf_peso.getText().replaceAll(",", "."));
                tf_icms_bc.setText(tf_icms_bc.getText().replaceAll(",", "."));
                tf_icms_perc.setText(tf_icms_perc.getText().replaceAll(",", "."));
                tf_icms_vlr.setText(tf_icms_vlr.getText().replaceAll(",", "."));
                tf_icms_pred.setText(tf_icms_pred.getText().replaceAll(",", "."));
                tf_ipi_bc.setText(tf_ipi_bc.getText().replaceAll(",", "."));
                tf_ipi_perc.setText(tf_ipi_perc.getText().replaceAll(",", "."));
                tf_ipi_vlr.setText(tf_ipi_vlr.getText().replaceAll(",", "."));
                tf_pis_bc.setText(tf_pis_bc.getText().replaceAll(",", "."));
                tf_pis_perc.setText(tf_pis_perc.getText().replaceAll(",", "."));
                tf_pis_vlr.setText(tf_pis_vlr.getText().replaceAll(",", "."));
                tf_cofins_bc.setText(tf_cofins_bc.getText().replaceAll(",", "."));
                tf_cofins_perc.setText(tf_cofins_perc.getText().replaceAll(",", "."));
                tf_cofins_vlr.setText(tf_cofins_vlr.getText().replaceAll(",", "."));
                 String sql_alterar = "update nf_produtos set " 
                         //+"item = "+ tf_item.getText()+", "
                         +"cod_produto = "+ tf_cod_produto.getText()+", "
                        +"cod_cfop = "+ tf_cod_cfop.getText()+", "
                        +"quantidade = "+ tf_quantidade.getText()+", "
                        +"peso = "+ tf_peso.getText()+", "
                        +"vlr_unitario = "+ tf_vlr_unitario.getText()+", "
                        +"vlr_produto = "+ tf_vlr_produto.getText()+", "
                        +"vlr_desconto = "+ tf_vlr_desconto.getText()+", "
                        +"vlr_total = "+ tf_vlr_total.getText()+", "
                        +"icms_bc = "+ tf_icms_bc.getText()+", "
                        +"icms_perc = "+ tf_icms_perc.getText()+", "
                        +"icms_pred = "+ tf_icms_pred.getText()+", "
                        +"icms_vlr = "+ tf_icms_vlr.getText()+", "
                        +"icms_cst = '"+ tf_icms_cst.getText()+"', "
                        +"ipi_bc = "+ tf_ipi_bc.getText()+", "
                        +"ipi_perc = "+ tf_ipi_perc.getText()+", "
                        +"ipi_vlr = "+ tf_ipi_vlr.getText()+", "
                        +"ipi_cst = '"+ tf_ipi_cst.getText()+"', "
                        +"pis_bc = "+ tf_pis_bc.getText()+", "
                        +"pis_perc = "+ tf_pis_perc.getText()+", "
                        +"pis_vlr = "+ tf_pis_vlr.getText()+", "
                        +"pis_cst = '"+ tf_pis_cst.getText()+"', "
                        +"cofins_bc = "+ tf_cofins_bc.getText()+", "
                        +"cofins_perc = "+ tf_cofins_perc.getText()+", "
                        +"cofins_vlr = "+ tf_cofins_vlr.getText()+", "
                        +"cofins_cst = '"+ tf_cofins_cst.getText()+"'"
                        +" where pedido = " + tf_pedido.getText()
                        +" and item = " + tf_item.getText();
                  System.out.println("sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                          );
                  resultset.last();
                  //mostra_conteudo_nos_campos();
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  posiciona_combo_e_jtable();
                  tf_pesquisa.setText("");
                  clicado = true;
                  alterou = true;
                  atualizar_nf();

                  novo_registro();
                  zerarVlrOld();
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar regravar registro = "+erro);
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
           String nome = "Excluir Produto ref.Item: "+tf_item.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM nf_produtos where empresa = "+empresa+" and  pedido = " + pedido + " and cod_produto = "+tf_cod_produto.getText();  //tf_pedido.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                          );
                  resultset.last();
                  //mostra_conteudo_nos_campos();
                  clicado = true;
                  alterou = true;
                  novo_registro();
                  atualizar_nf();
                  zerarVlrOld();
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

     private void calcular_vlr_produto() {
         tf_vlr_produto.setText(""+Biblioteca.arredondar((Double.parseDouble(tf_quantidade.getText())*Double.parseDouble(tf_vlr_unitario.getText())), 2, 0));
         tf_vlr_total.setText(""+Biblioteca.arredondar((Double.parseDouble(tf_vlr_produto.getText())-Double.parseDouble(tf_vlr_desconto.getText())), 2, 0));
//JOptionPane.showMessageDialog(null, "Calculou o valor do produto! = "+tf_vlr_produto.getText());
     }
     private void capturar_dados_produto() {
        try {
            if (tf_cod_produto.getText() != null && !tf_cod_produto.getText().equals("")) {
                String sql_prod = "select * from produto where codigo = "+tf_cod_produto.getText();
//JOptionPane.showMessageDialog(null, "Sql_prod: "+sql_prod);
                ResultSet rs_prod = st_cod_produto.executeQuery(sql_prod);
                while(rs_prod.next()) {
                     tf_peso.setText(""+Biblioteca.arredondar((rs_prod.getDouble("peso")*Double.parseDouble(tf_quantidade.getText())),3,0));
                     setPreco_custo(rs_prod.getDouble("preco_compra"));
                     preco = rs_prod.getDouble("preco");
                     if (Double.parseDouble(tf_vlr_unitario.getText()) == 0) {
                        tf_vlr_unitario.setText(""+preco);
                     }
                     tf_icms_cst.setText(rs_prod.getString("icms_cst"));
                     tf_icms_perc.setText(rs_prod.getString("icms_perc"));
                     tf_icms_pred.setText(rs_prod.getString("icms_pred"));
                     tf_ipi_cst.setText(rs_prod.getString("ipi_cst"));
                     tf_ipi_perc.setText(rs_prod.getString("ipi_perc"));
                     tf_pis_cst.setText(rs_prod.getString("pis_cst"));
                     tf_pis_perc.setText(rs_prod.getString("pis_perc"));
                     tf_cofins_cst.setText(rs_prod.getString("cofins_cst"));
                     tf_cofins_perc.setText(rs_prod.getString("cofins_perc"));
                     String imagem = "", caminhoImagem = "";
                     imagem = rs_prod.getString("images");
                     if (imagem.equals("")) {
                         jlFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.JPG")));
                     } else {
                         caminhoImagem = System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem;
                        System.out.println("Caminho: "+caminhoImagem);
                        File f = new File(caminhoImagem);
                        Image image;
                        try {
                            image = ImageIO.read(f);
                          jlFoto.setIcon(new ImageIcon(image.getScaledInstance(jlFoto.getWidth(),jlFoto.getHeight(), Image.SCALE_DEFAULT)));  //("/imagens/produtos/semfoto.jpg"));
                        } catch (IOException ex) {
                            System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem+"Erro ao carregar foto: "+ex);
                        }
                     }
                     double wvalor_prod = Double.parseDouble(tf_vlr_unitario.getText()) * Double.parseDouble(tf_quantidade.getText());
                     double wvalor_total = wvalor_prod - Double.parseDouble(tf_vlr_desconto.getText());
                     tf_vlr_produto.setText(""+wvalor_prod);
                     tf_vlr_total.setText(""+wvalor_total);
                     double wicms_perc = Double.parseDouble(tf_icms_perc.getText());
                     double wicms_pred = Double.parseDouble(tf_icms_pred.getText());
                     float wreducao = 0;
                     if (wicms_pred == 0) {
                         wreducao = 1;
                    } else {
                         wreducao = (float) (wicms_pred / wicms_perc);
                    }
                     double wicms_bc = Biblioteca.arredondar((Double.parseDouble(tf_vlr_total.getText()) * wreducao), 2, 0);
                     tf_icms_bc.setText(""+wicms_bc);
                     tf_icms_vlr.setText(""+ Biblioteca.arredondar((wicms_bc * (wicms_perc / 100)), 2, 0));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar capturar os dados da tabela Produto: "+ex);
        }

     }
     private void atualizarTotaisNf() {
         tf_vlr_totais.setText(Nf.tf_valor_total.getText());
         tf_vlr_descontos.setText(Nf.tf_valor_descontos.getText());
         tf_vlr_produtos.setText(Nf.tf_valor_produtos.getText());
         
     }
    private void atualizar_nf() {
        String sqlquery = "select * from nf where empresa = "+empresa+" and  pedido = "+pedido;
        try {
            rs_nf = st_nf.executeQuery(sqlquery);
            double vlr_total        = 0.00,
                    vlr_produtos    = 0.00,
                    vlr_descontos   = 0.00,
                    icms_bc         = 0.00,
                    icms_vlr        = 0.00,
                    ipi_bc          = 0.00,
                    ipi_vlr         = 0.00,
                    pis_bc          = 0.00,
                    pis_vlr         = 0.00,
                    cofins_bc       = 0.00,
                    cofins_vlr      = 0.00;
            
            double peso_volume      = 0;
            int qtde_volume         = 0;
            int cod_forma_pgto      = 0;
            Date dataemis           = null;
            rs_nf.first();
                try {
                    dataemis        = rs_nf.getDate("data_digitacao");
                    cod_forma_pgto  = Integer.parseInt(rs_nf.getString("cod_forma_pgto"));
                    vlr_produtos    = rs_nf.getDouble("valor_produtos");
                    vlr_descontos   = rs_nf.getDouble("valor_descontos");
                    vlr_total       = rs_nf.getDouble("valor_total");
                    icms_bc         = rs_nf.getDouble("icms_bc");
                    icms_vlr        = rs_nf.getDouble("icms_vlr");
                    ipi_bc          = rs_nf.getDouble("ipi_bc");
                    ipi_vlr         = rs_nf.getDouble("ipi_vlr");
                    pis_bc          = rs_nf.getDouble("pis_bc");
                    pis_vlr         = rs_nf.getDouble("pis_vlr");
                    cofins_bc       = rs_nf.getDouble("cofins_bc");
                    cofins_vlr      = rs_nf.getDouble("cofins_vlr");
                    qtde_volume     = rs_nf.getInt("qtde_volume");
                    peso_volume     = rs_nf.getDouble("peso_volume");

                    vlr_produtos    -= vlr_produto_old;
                    vlr_descontos   -= vlr_desconto_old;
                    vlr_total       -= vlr_total_old;
                    icms_bc         -= icms_bc_old;
                    icms_vlr        -= icms_vlr_old;
                    ipi_bc          -= ipi_bc_old;
                    ipi_vlr         -= ipi_vlr_old;
                    pis_bc          -= pis_bc_old;
                    pis_vlr         -= pis_vlr_old;
                    cofins_bc       -= cofins_bc_old;
                    cofins_vlr      -= cofins_vlr_old;
                    qtde_volume     -= qtde_volume_old;
                    peso_volume     -= peso_volume_old;

                    vlr_produtos    += Double.parseDouble(tf_vlr_produto.getText());
                    vlr_descontos   += Double.parseDouble(tf_vlr_desconto.getText());
                    vlr_total       += Double.parseDouble(tf_vlr_total.getText());
                    icms_bc         += Double.parseDouble(tf_icms_bc.getText());
                    icms_vlr        += Double.parseDouble(tf_icms_vlr.getText());
                    ipi_bc          += Double.parseDouble(tf_ipi_bc.getText());
                    ipi_vlr         += Double.parseDouble(tf_ipi_vlr.getText());
                    pis_bc          += Double.parseDouble(tf_pis_bc.getText());
                    pis_vlr         += Double.parseDouble(tf_pis_vlr.getText());
                    cofins_bc       += Double.parseDouble(tf_cofins_bc.getText());
                    cofins_vlr      += Double.parseDouble(tf_cofins_vlr.getText());
                    qtde_volume     += Double.parseDouble(tf_quantidade.getText());
                    peso_volume     += Double.parseDouble(tf_peso.getText());
                    sqlquery = "update nf set "
                            +" valor_produtos = "+vlr_produtos+", "
                            +" valor_descontos = "+vlr_descontos+", "
                            +" valor_total = "+vlr_total+", "
                            +" qtde_volume = "+qtde_volume+", "
                            +" peso_volume = "+peso_volume+", "
                            +" icms_bc = "+icms_bc+", "
                            +" icms_vlr = "+icms_vlr+", "
                            +" ipi_bc = "+ipi_bc+", "
                            +" ipi_vlr = "+ipi_vlr+", "
                            +" pis_bc = "+pis_bc+", "
                            +" pis_vlr = "+pis_vlr+", "
                            +" cofins_bc = "+cofins_bc+", "
                            +" cofins_vlr = "+cofins_vlr
                            + " where pedido = "+pedido;
                  //JOptionPane.showMessageDialog(null, "Comando para atualizar nf: "+sqlquery+" - vlr_tot_old: "+vlr_total_old+" - vlr_total: "+vlr_total);
                  st_nf.executeUpdate(sqlquery);
                  tf_vlr_totais.setText(""+vlr_total);
                  tf_vlr_descontos.setText(""+vlr_descontos);
                  tf_vlr_produtos.setText(""+vlr_produtos);
                  Nf.tf_valor_total.setText(""+vlr_total);
                  Nf.tf_valor_descontos.setText(""+vlr_descontos);
                  Nf.tf_valor_produtos.setText(""+vlr_produtos);
                  Nf.tf_qtde_volume.setText(""+qtde_volume);
                  Nf.tf_peso_volume.setText(""+peso_volume);
                  calcular_prazos(cod_forma_pgto, dataemis, vlr_total);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar Capturar os campos da Tabela NF. Erro: "+ex);
                }
            //}
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar Ler a Tabela NF. Erro: "+ex);
        }
    }
     // procedimento para exclusao de registro
    public void imprimir()
    {
        JOptionPane.showMessageDialog(null, "Impressao da Tabela - Falta Implementar este Botao");
        //Impressao impr = new Impressao(empresa, "rel_nf_produtos.jasper");
        //impr.imprimeRelJasper();
    }
    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            st_aux  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cb_aux.removeAllItems();
            String sql_aux = "select * from "+tab_cb_tab_box[indice];
            if (tab_cb_tab_box[indice].equals("produto")) {
                sql_aux += " order by codigo";
            } else if (tab_cb_tab_box[indice].equals("cfop")) {
                //sql_aux += " where cfop > 4999";
                String filtro = "";
                System.out.println("nf_produtos - ufemit: "+getUfEmit()+" - ufdest: "+this.ufCDest);
                if (this.ufCDest.equals(getUfEmit())) {
                    filtro = " where operacao = 'E'";
                } else {
                    filtro = " where operacao = 'I'";
                }
                sql_aux += filtro+" order by cfop";
            }
            if (tab_cb_tab_box[indice].equals("cfop")) {
System.out.println("metodo: preenche_cb_auxiliar - Sql_aux: "+sql_aux);
            }
            rs_aux = st_aux.executeQuery(sql_aux);
            String conteudo = "";
            int idxVda51 = 0, idxVda61 = 0, idx = 0;
            while(rs_aux.next()) {
                conteudo = rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]).trim();
                if (tab_cb_tab_box[indice].equals("cfop")) {  //  cfop
                    conteudo += " - "+rs_aux.getString("cfop")+" - "+rs_aux.getString("seqcfop");
                    if (rs_aux.getString("cfop").equals("5102")) {
                       idxVda51 = idx;
                    } else if (rs_aux.getString("cfop").equals("6102")) {
                       idxVda61 = idx; 
                    }
                } 
                cb_aux.addItem(conteudo);
                 idx++;
            }
            rs_aux.first();
            if (tab_cb_tab_box[indice].equals("cfop")) {  //  cfop
                if (this.ufCDest.equals(getUfEmit())) {  // venda estadual
                    cb_aux.setSelectedIndex(idxVda51);
                } else {                                //  venda interestadual
                    cb_aux.setSelectedIndex(idxVda61);
                }
            } else {
                cb_aux.setSelectedIndex(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ComboBox "+cpo_tabela[indice]+": "+ex);
        }
    }
    public void trata_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, JTextField tf_aux, int indice, String filtro) {
//System.out.println("Tratamento para o ComboBox: cb_"+cpo_tabela[indice]);
        String sql = "";
//System.out.println("metodo: trata_cb_auxiliar() - Valor do filtro: "+filtro);
        try {
            if (cb_aux.getSelectedItem() != null){
                int j =-1;
                sql = "select * from "+tab_cb_tab_box[indice];
                if (filtro.equals("")) {
                    if (indice == 2) {  //  produto
                        sql += " order by codigo";
                    } else {  //  cfop
                        sql += " order by cfop";
                    }
                } else {
                    if (indice == 2) {  //  produto
                        sql += " where "+filtro+" order by codigo";
                    } else {  //  cfop
                        sql += " where "+filtro+" order by cfop";
                    }
                }
//JOptionPane.showMessageDialog(null, "Comando sql para getMetadata(): "+sql);
                st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs_aux = st_aux.executeQuery(sql);
                for (int i=1; i<=rs_aux.getMetaData().getColumnCount(); i++){
                    if (rs_aux.getMetaData().getColumnName(i).equals(tab_cb_cpo_exibe[indice])){
                        j = i;
                        break;
                    }
                }
		 try {
                    //String sql_aux;
                    if (filtro.equals("")) {
                        sql = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?"; //'"+cb_aux.getSelectedItem()+"'";
                     } else {
                        sql = "select * from "+tab_cb_tab_box[indice]+" where "+filtro+" and "+tab_cb_cpo_assoc[indice]+" = ?"; //'"+cb_aux.getSelectedItem()+"'";
                     }
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Sql: "+sql);
                    if (ps_aux != null)
                        ps_aux.clearParameters();
                    ps_aux = connection.prepareStatement(sql);
                    String chave1 = "", chave2 = "";
                    String chave = cb_aux.getSelectedItem().toString();
                /*
                    if (chave.indexOf("[") != -1) {
                        chave1 = chave.substring(chave.indexOf(" - ")+3, chave.indexOf("[")-3);
                    } else {
                        chave1 = chave.substring(chave.indexOf(" - ")+3, chave.length());
                    }
                 *
                 */
                    chave1 = chave.substring(0, chave.indexOf(" - "));
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Valor de chave1: ["+chave1+"]");
                    chave2 = chave.substring(0, chave.indexOf(" - "));
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Valor de chave2: ["+chave2+"]");
                    if (j > 0){
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Indice da coluna: ["+rs_aux.getMetaData().getColumnType(j)+"] - Nome da Coluna: "+rs_aux.getMetaData().getColumnName(j));
                        //System.out.println("Tipo do campo ["+rs_aux.getMetaData().getColumnName(j)+"] = "+rs_aux.getMetaData().getColumnType(j));
                        //if (rs_aux.getMetaData().getColumnType(j) == 4 ) {
                            ps_aux.setInt(1, Integer.parseInt(chave1));
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Passou If . . . ");
                        //}  else {
                        //    ps_aux.setString(1, chave1);
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Passou Else . . . ");
                        //}
                    }
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Sql_aux: "+sql);
                    rs_aux = ps_aux.executeQuery();
//JOptionPane.showMessageDialog(null, "metodo: trata_cb_auxiliar() - Passou Sql_aux . . . ");
		}finally {}
                while(rs_aux.next()) {
                     tf_aux.setText(rs_aux.getString(tab_cb_cpo_assoc[indice]));
//JOptionPane.showMessageDialog(null, "Valor do campo capturado: "+tf_aux.getText()+" - cpo da tabela: "+tab_cb_cpo_assoc[indice]);
                    if (indice == 2) {
                       capturar_dados_produto();
                       tf_cod_cfop.requestFocus();
                    }
                }
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o textField: "+ex);
            JOptionPane.showMessageDialog(null, "Comando: "+sql);
        }
    }
    public void mostra_tf_ref_cb_aux(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_aux, int indice) {
        System.out.println("preenchimento do textfield para o ComboBox: cb_"+cpo_tabela[indice]);
        String sql_aux;
        try {
            sql_aux = "select * from "+tab_cb_tab_box[indice];
//JOptionPane.showMessageDialog(null, "metodo: mostra_tf_ref_cb_aux() - Sql_aux para getMetaData: "+sql_aux);
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
//JOptionPane.showMessageDialog(null, "metodo: mostra_tf_ref_cb_aux() - ps_aux: "+ps_aux.toString());
                 rs_aux = ps_aux.executeQuery();
             }finally {
                 ps_aux.clearParameters();
             }
             cb_aux.setSelectedIndex(-1);
             while(rs_aux.next()){
                 String assoc = rs_aux.getString(tab_cb_cpo_assoc[indice]);
                 String exibe = rs_aux.getString(tab_cb_cpo_exibe[indice]).trim();
                 String desc = "";
                 if (tab_cb_tab_box[indice].equals("cfop")) {
                     desc = " - "+rs_aux.getString("cfop")+" - "+rs_aux.getString("seqcfop");
                 }
//JOptionPane.showMessageDialog(null, "assoc = "+assoc+" - exibe = "+exibe+desc);
                 cb_aux.setSelectedItem(assoc+" - "+exibe+desc);
                 break;
            }
//JOptionPane.showMessageDialog(null, "cb_aux.getSelectedIndex() = "+cb_aux.getSelectedIndex());
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
           resultset  = statement.executeQuery("select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" order by item"
                   );
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
                cb_pesquisa.setSelectedItem(tf_item.getText());
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
            //tf_pedido.setText(resultset.getString("pedido"));//Essas duas linhas sao necessarias
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
        String sql = "select * from nf_produtos where Descricao = "+cb_pesquisa.getSelectedItem();
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
       } else if(e.getSource() == cb_cod_produto) {
           //JOptionPane.showMessageDialog(null, "metodo mouseClicked() - Clicou o mouse no campo cb_cod_produto . . .");
        }
    }
    
    public void mousePressed(MouseEvent e) {
       if(e.getSource() == cb_cod_produto) {
           //JOptionPane.showMessageDialog(null, "metodo mousePressed() - Clicou o mouse no campo cb_cod_produto . . .");
        }
       }
    
    public void mouseReleased(MouseEvent e) {
       if(e.getSource() == cb_cod_produto) {
           //JOptionPane.showMessageDialog(null, "metodo mouseReleaed() - Clicou o mouse no campo cb_cod_produto . . .");
        }
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == cb_cod_produto) {
           //JOptionPane.showMessageDialog(null, "metodo mouseExited() - Clicou o mouse no campo cb_cod_produto . . .");
        }
   }
    public void trataEvento_mouse_key_jTable1(){
            System.out.println("Numero da linha selecionada(mouseClicked/keyPressed): "+jTable1.getSelectedRow());
            try {
                if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                    String sql_query = "select * from nf_produtos where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                +" order by item";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from nf_produtos where empresa = "+empresa+" "
                          +"and pedido = "+pedido+" order by item"
                            );
                }
                //resultset = statement.executeQuery("select * from nf_produtos");
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
       } else if(e.getSource() == tf_cod_produto) {
           System.out.println("Teclou enter no campo cod_produto . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2);
               capturar_dados_produto();
               tf_cod_cfop.requestFocus();
           }
       } else if(e.getSource() == tf_vlr_unitario) {
           System.out.println("Teclou enter no campo vlr_unitario . . .");
           capturar_dados_produto();
           if (e.getKeyCode() == 10){
               calcular_vlr_produto();
               //capturar_dados_produto();
                tf_cod_produto.requestFocus();
           }
       } else if(e.getSource() == tf_vlr_desconto) {
           System.out.println("Teclou enter no campo vlr_desconto . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2);
               capturar_dados_produto();
               botao_gravar.requestFocus();
           }
       } else if(e.getSource() == tf_quantidade) {
//           if (e.getKeyCode() < 48 || e.getKeyCode() > 57){
//JOptionPane.showMessageDialog(null, "Pressionou a tecla: "+e.getKeyCode());
//            }
           System.out.println("Teclou enter no campo quantidade . . .");
           if (e.getKeyCode() == 10){
               //calcular_vlr_produto();
               capturar_dados_produto();
               tf_vlr_unitario.requestFocus();
           }
       } else if(e.getSource() == tf_cod_cfop) {
           System.out.println("Teclou enter no campo cod_cfop . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3);
               tf_quantidade.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqcod_produto){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_produto, rs_cod_produto, cb_cod_produto, tf_psqcod_produto, rb_inic_psqcod_produto, rb_meio_psqcod_produto, 2);
//    JOptionPane.showMessageDialog(null, "keyReleased() - Voltou do metodo preenche_cb_auxPesq() . . . ");
        }
        else 
        if (e.getSource() == tf_psqcod_cfop){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_cfop, rs_cod_cfop, cb_cod_cfop, tf_psqcod_cfop, rb_inic_psqcod_cfop, rb_meio_psqcod_cfop, 3);
        }
    }

    public void preenche_cb_auxPesq(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, JTextField tf_psqAux, JRadioButton rb_inic, JRadioButton rb_meio, int indice) {
//        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            st_aux = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stat_aux = false;
            String sql_aux = "";
            cb_aux.removeAllItems();
            String filtro = "";
            if (tab_cb_tab_box[indice].equals("cfop")) {
                if (this.ufCDest.equals(getUfEmit())) {
                    filtro = "  operacao = 'E' and ";
                } else {
                    filtro = " where operacao = 'I' and ";
                }
            }
            if (tf_psqAux.getText().equals(""))
                sql_aux = "select * from "+tab_cb_tab_box[indice];
            else {
                if (rb_inic.isSelected()) {
                   sql_aux = "select * from "+tab_cb_tab_box[indice]+" where " + filtro + tab_cb_cpo_exibe[indice]+" like '"+tf_psqAux.getText().toUpperCase()+"%'";
                } else {
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where " + filtro +tab_cb_cpo_exibe[indice]+" like '%"+tf_psqAux.getText().toUpperCase()+"%'";
                }
                if (tab_cb_tab_box[indice].equals("cfop") && Biblioteca.verSeEhNumero(tf_psqAux.getText())) {  //  cfop
                    sql_aux += " or cfop = "+Integer.parseInt(tf_psqAux.getText())+"";
                }
            }
//System.out.println("preenche_cb_auxPesq() - Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
//System.out.println("preenche_cb_auxPesq() - Passou 1 . . . "+tab_cb_cpo_assoc[indice]+" - "+tab_cb_cpo_exibe[indice]);

            while(rs_aux.next()) {
//System.out.println("preenche_cb_auxPesq() - Passou 1.1 . . . ");
                if (tab_cb_tab_box[indice].equals("cfop")) {  //  cfop
//System.out.println("preenche_cb_auxPesq() - Passou 1.1.1 . . . ");
                   cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]).trim()+" - "+rs_aux.getString("cfop")+" - "+rs_aux.getString("seqcfop"));
                } else {
//System.out.println("preenche_cb_auxPesq() - Passou 1.1.2 . . . ");
//JOptionPane.showMessageDialog(null, "preenche_cb_auxPesq() - antes. . . ");
                    cb_aux.addItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]).trim());
//JOptionPane.showMessageDialog(null, "preenche_cb_auxPesq() - depois. . . ");
                }
            }
//System.out.println("preenche_cb_auxPesq() - Passou 2 . . . ");
            rs_aux.first();
            stat_aux = true;
//System.out.println("preenche_cb_auxPesq() - Passou 3 . . . ");
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
       if (e.getSource() == tf_cod_produto){
           //System.out.println("Perdeu o foco do campo cod_produto . . .");
           if(posiciona_combo_geral(tab_cb_st[2], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2))
            tf_cod_cfop.requestFocus();
       } else
       if (e.getSource() == cb_cod_produto){
           //System.out.println("Perdeu o foco do campo cb_cod_produto . . .");
           //JOptionPane.showMessageDialog(null, "cb_cod_produto.getSelectedIndex(): "+cb_cod_produto.getSelectedIndex());
           //if (cb_cod_produto.getSelectedItem() != null) {
           //   capturar_dados_produto();
           //}
           tf_cod_cfop.requestFocus();
       } else
       if (e.getSource() == tf_cod_cfop){
           //JOptionPane.showMessageDialog(null,"Perdeu o foco do campo cod_cfop . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3))
            tf_quantidade.requestFocus();
       } else
       if (e.getSource() == tf_quantidade){
           //System.out.println("Perdeu o foco do campo quantidade . . .");
           capturar_dados_produto();
           double vlrprod = Double.parseDouble(tf_quantidade.getText()) * Double.parseDouble(tf_vlr_unitario.getText());
           tf_vlr_produto .setText(""+vlrprod);
           double vlrtot = Double.parseDouble(tf_vlr_produto.getText()) - Double.parseDouble(tf_vlr_desconto.getText());
           tf_vlr_total .setText(""+vlrtot);
           tf_vlr_unitario .requestFocus();
       } else
       if (e.getSource() == tf_vlr_unitario){
           System.out.println("Perdeu o foco do campo vlr_unitario . . .");
           capturar_dados_produto();
           double vlrprod = Double.parseDouble(tf_quantidade.getText()) * Double.parseDouble(tf_vlr_unitario.getText());
           tf_vlr_produto .setText(""+vlrprod);
           double vlrtot = Double.parseDouble(tf_vlr_produto.getText()) - Double.parseDouble(tf_vlr_desconto.getText());
           tf_vlr_total .setText(""+vlrtot);
           tf_vlr_desconto .requestFocus();
       } else
       if (e.getSource() == tf_vlr_desconto){
           //System.out.println("Perdeu o foco do campo vlr_desconto . . .");
           double vlrtot = Double.parseDouble(tf_vlr_produto.getText()) - Double.parseDouble(tf_vlr_desconto.getText());
           tf_vlr_total .setText(""+vlrtot);
       }
    }
/*
    private void pegaPeso() {
       String sql_prod = "select * from produto where codigo = "+tf_cod_produto.getText();
         JOptionPane.showMessageDialog(null, "Sql_aux: "+sql_prod);
        ResultSet rs_prod;
        try {
            rs_prod = stat_prod.executeQuery(sql_prod);
            while(rs_prod.next()) {
                tf_peso.setText(""+rs_prod.getFloat("peso"));
                tf_vlr_unitario.setText(""+rs_prod.getDouble("preco"));
           }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar ler a tabela produto. Erro: "+ex);
        }

    }
 *
 */
    private boolean posiciona_combo_geral(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_Aux, int indice){
       boolean retorno = false;
       try{
             if (!tf_Aux.getText().equals("")){
                tf_Aux.setText(tf_Aux.getText().toUpperCase());
                //rs = st.executeQuery("select "+cpo_combo+" from "+tabela+" where "+cpo_pesq+" = '"+jtf.getText()+"'");
                String sql_pesq = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = ?";
                PreparedStatement ps = connection.prepareStatement(sql_pesq);
                ps.setInt(1, Integer.parseInt(tf_Aux.getText()));
//System.out.println("posiciona_combo_geral - sql: "+sql_pesq+" [ "+tf_Aux.getText()+" ]");
                rs_aux = ps.executeQuery();
                String desc = "";
                while (rs_aux.next()){
       //JOptionPane.showMessageDialog(null, "posiciona_combo_geral - cpo_assoc: "+tab_cb_cpo_assoc[indice]+" - valor: "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                   if (tab_cb_tab_box[indice].equals("cfop")) {
                    desc = " - "+rs_aux.getString("cfop")+" - "+rs_aux.getString("seqcfop");
                   }
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]).trim()+desc);
                    retorno = true;
                }
                if (!retorno) {
                    cb_aux.setSelectedIndex(-1);
                    //JOptionPane.showMessageDialog(null, "Nao conseguiu encontrar Registro para o Codigo informado!");
                    tf_Aux.setText("");
                }
             }
        } catch(Exception erro){
            System.out.println("Erro ao tentar posicionar registros. Erro: "+erro);
            retorno = false;
            erro.printStackTrace();
        }
        return retorno;
    }
    private void calcular_prazos(int cod_forma_pgto, Date dataemis, Double vlrtotal) {
        try {
               String  sql_delete = "DELETE FROM nf_prazo where empresa = "+empresa+" and pedido = " + pedido;
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = stat_prazo.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  //JOptionPane.showMessageDialog(null, "Exclusao dos prazos realizada com sucesso!");
               }
               else {
                  //JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o(s) registro(s) de Prazo para Exclusao!");
               }
        } catch(Exception e) {
              JOptionPane.showMessageDialog(null, "Erro ao tentar excluir os Prazos! Erro: "+e);
        }
                  String sql_dup = "select * from Forma_Pgto where codigo = " + cod_forma_pgto;
                  int nparc = 0, primdia = 0, intervalo = 0;
        try {
            rs_dup = st_dup.executeQuery(sql_dup);
                  while (rs_dup.next()){
                    nparc = rs_dup.getInt("qtde_parcelas");
                    primdia = rs_dup.getInt("dias_inicial");
                    intervalo = rs_dup.getInt("dias_intervalo");
                  }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao tentar ler atabela de formas de pgto! Erro: "+ex);
        }
      String resultparc = "Qtde de Parcelas: "+nparc+
              "\n Prazo para o 1. Vcto: "+primdia+
              " dias\nQtde de Dias entre parcelas: "+intervalo+
              " Valor Total: "+Biblioteca.arredondar(vlrtotal, 2, 0)+
              " Valor da Parcela: "+Biblioteca.arredondar((vlrtotal/nparc), 2, 0)+
              "\n\n Data de Emissão: "+DateFormat.getDateInstance().format(dataemis)+
              "\n\n";
//JOptionPane.showMessageDialog(null,resultparc);  //"nparc: "+nparc+" - primdia: "+primdia+" - intervalo: "+intervalo);

                  int seqdup = 1;
                  double totvlparc = 0.0;
                  double vlparc = 0.00;  //  vlTotNF / nparc;
                  double vp1 = 0.00;
//calcula a data do vencto de cada parcela
                  java.util.Date dataparc = null;
                  String datastr = null;
                  for (int i=0; i<nparc; i++){
                      try {
                          dataparc = Biblioteca.adicionarDias(dataemis, (primdia + (intervalo*i)));
                          //java.sql.Date sqlDate = new java.sql.Date(dataparc.getTime());
                          datastr = DateFormat.getDateInstance().format(dataparc);
                          if (nparc == (i+1))
                              vlparc = Biblioteca.arredondar((vlrtotal - totvlparc), 2, 0);
                          else
                              vlparc = Biblioteca.arredondar((vlrtotal - totvlparc) / (nparc - i), 2, 1);
                          String sql_insert = "insert into nf_prazo (empresa, pedido, parcela, datavcto, vlr_parcela, vlr_pago, datavcto_orig ) values ("
                                  + empresa+", "
                                  + pedido+", "
                                  +(i+1)+", "
                                  +"'"+dataparc+"', "
                                  +vlparc+", "
                                  +0.00+", "
                                  +"'"+dataparc+"' "
                                  + ")";
                            stat_prazo.executeUpdate(sql_insert);
                          seqdup++;
                         //statement.executeUpdate(sql_insert);
                          resultparc = resultparc + (i+1)+"a. parcela com vcto em: "+datastr+" Vlr: "+formataNumDec(""+vlparc,2)+"\n";
                          totvlparc = totvlparc + vlparc;
                          //JOptionPane.showMessageDialog(null, "Gravacao da "+(i+1)+"a. parcela realizada com sucesso para a data: "+datastr);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao tentar gravar as parcelas desta NF: "+ex);
                        ex.printStackTrace();
                    }
                  }
                  //JOptionPane.showMessageDialog(null, resultparc);
    }
    public static void main(String args[])
        {
        JFrame form = new Nf_produtos(3, null, null, null, "PR", "PR");
            form.setVisible(true);
    }

    /**
     * @return the preco_custo
     */
    public double getPreco_custo() {
        return preco_custo;
    }

    /**
     * @param preco_custo the preco_custo to set
     */
    public void setPreco_custo(double preco_custo) {
        this.preco_custo = preco_custo;
    }

    /**
     * @return the ufEmit
     */
    public String getUfEmit() {
        return ufEmit;
    }

    /**
     * @param ufEmit the ufEmit to set
     */
    public void setUfEmit(String ufEmit) {
        this.ufEmit = ufEmit;
    }

}
class CellRenderer_nf_produtos extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_nf_produtos()
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
