/*

Descrição: Manutenção da Tabela de Produtos

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Relatorios.Impressao;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import br.com.videoaulasneri.adelcio.utilitarios.Impressao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
public class Produto extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Cadastro: produto");

    //Labels dos campos da tabela;
    JLabel label_codigo              = new JLabel("Codigo: ");
    JLabel label_codigo_fornec       = new JLabel("Cod.Externo.: ");
    JLabel label_fornecedor          = new JLabel("Fornecedor: ");
    JLabel label_descricao           = new JLabel("Descricao: ");
    JLabel label_nome_reduzido       = new JLabel("Nome Reduzido: ");
    JLabel label_seg_name            = new JLabel("Seg.Nome: ");
    JLabel label_marca               = new JLabel("Marca: ");
    JLabel label_garantia            = new JLabel("Garantia: ");
    JLabel label_unidade             = new JLabel("Unidade: ");
    JLabel label_ean                 = new JLabel("Ean: ");
    JLabel label_ncm                 = new JLabel("Ncm: ");
    JLabel label_tipo_ncm            = new JLabel("Tipo: ");
    JLabel label_cest                = new JLabel("Cod.Espec.Sit.Tribut.: ");
    JLabel label_origem              = new JLabel("Origem(0/1): ");
    JLabel label_preco               = new JLabel("Preco Venda: ");
    JLabel label_preco_compra        = new JLabel("Preco Compra: ");
    JLabel label_peso                = new JLabel("Peso(9.999): ");
    JLabel label_icms_cst            = new JLabel("CST ICMS(xxx): ");
    JLabel label_icms_perc           = new JLabel("% ICMS: ");
    JLabel label_icms_pred           = new JLabel("% ICMS(Red): ");
    JLabel label_ipi_cst             = new JLabel("CST IPI(xx): ");
    JLabel label_ipi_perc            = new JLabel("% IPI: ");
    JLabel label_pis_cst             = new JLabel("CST PIS(xx): ");
    JLabel label_pis_perc            = new JLabel("% PIS: ");
    JLabel label_cofins_cst          = new JLabel("CST COFINS(xx): ");
    JLabel label_cofins_perc         = new JLabel("% COFINS: ");
    JLabel label_images              = new JLabel("Imagem: ");
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
    JButton botao_imagem             = new JButton("Ver Imagem");  //(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.jpg")));
    JButton botao_buscaImg           = new JButton("...");  //(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.jpg")));
    //JTextFields dos campos das tabelas
    JTextField tf_codigo             = new JTextField();
    JTextField tf_codigo_fornec      = new JTextField();
    JTextField tf_fornecedor         = new JTextField();
    JTextField tf_descricao          = new JTextField();
    JTextField tf_nome_reduzido      = new JTextField();
    JTextField tf_seg_name           = new JTextField();
    JTextField tf_marca              = new JTextField();
    JTextField tf_garantia           = new JTextField();
    JTextField tf_unidade            = new JTextField();
    JTextField tf_ean                = new JTextField();
    JTextField tf_ncm                = new JTextField();
    JTextField tf_tipo_ncm           = new JTextField();
    JTextField tf_cest               = new JTextField();
    JTextField tf_origem             = new JTextField();
    JTextField tf_preco_compra       = new JTextField();
    JTextField tf_preco              = new JTextField();
    JTextField tf_peso               = new JTextField();
    JTextField tf_icms_cst           = new JTextField();
    JTextField tf_icms_perc          = new JTextField();
    JTextField tf_icms_pred          = new JTextField();
    JTextField tf_ipi_cst            = new JTextField();
    JTextField tf_ipi_perc           = new JTextField();
    JTextField tf_pis_cst            = new JTextField();
    JTextField tf_pis_perc           = new JTextField();
    JTextField tf_cofins_cst         = new JTextField();
    JTextField tf_cofins_perc        = new JTextField();
    JTextField tf_images             = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox<String> cb_pesquisa    = new JComboBox<>();
    JComboBox<String> cb_pesq_lab    = new JComboBox<>();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[24];  //[18];
    String [] lab_tabela             = new String[24];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[24]; //[18];
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
    JTextField tf_aux_pesq           = tf_descricao;
    PreparedStatement ps_aux;
    int empresa = 0;

    ResultSet rs_unidade;
    Statement st_unidade;
    JComboBox<String> cb_unidade      = new JComboBox<>();
    JLabel label_psqunidade           = new JLabel("Pesquisar: ");
    JTextField tf_psqunidade          = new JTextField();
    ButtonGroup bg_psqunidade         = new ButtonGroup();
    JRadioButton rb_inic_psqunidade   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqunidade   = new JRadioButton("Meio");

    ButtonGroup bg_psqproduto         = new ButtonGroup();
    JRadioButton rb_inic_psqproduto   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqproduto   = new JRadioButton("Meio");
    JRadioButton rb_mixOr_psqproduto  = new JRadioButton("Mix(ou)");
    JRadioButton rb_mixAnd_psqproduto = new JRadioButton("Mix(e)");
    JLabel lb_psqfiltro               = new JLabel("Filtro Ativo: ");
    JTextField tf_psqfiltro           = new JTextField();
    JButton bt_psqfiltro              = new JButton("Limpar Filtro");
    JButton bt_copyDescr              = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar_registro.gif")));

    int altura = 0, largura = 0;
    String txtSql = "";
    String[] txtPesqs = new String[10];
    String razaoEmp, enderEmp;
    JPanel jPFoto = new javax.swing.JPanel();
    String fsep = System.getProperty("file.separator");

    public Produto(int empresa, Connection confat, String razaoEmpresa, String enderEmpresa)
    {
        setTitle("Formulario de Manutencao de Produto");
        setSize(1010, 760);
        setLocation(65,5);
        setResizable(true);
        altura = 760;  //getContentPane().HEIGHT;
        largura = 1010;  //getContentPane().WIDTH;
        this.empresa = empresa;
        this.connection = confat;
        this.razaoEmp = razaoEmpresa;
        this.enderEmp = enderEmpresa;
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
        rb_inic_psqproduto      .setBounds(340,70,65,20);
        rb_meio_psqproduto      .setBounds(410,70,55,20);
        rb_mixOr_psqproduto     .setBounds(470,70,75,20);
        rb_mixAnd_psqproduto    .setBounds(550,70,75,20);
        lb_psqfiltro            .setBounds(80,90,100,20);
        tf_psqfiltro            .setBounds(180,90,600,20);
        bt_psqfiltro            .setBounds(790,85,130,30);

        lb_titulo               .setBounds(10,10,500,30);
        lb_titulo               .setFont(new Font("Arial",Font.BOLD,16));
        lb_titulo               .setForeground(Color.black);

    //Gera cor frente dos lanbels setForegroundColor()
        label_codigo            .setForeground(Color.black);
        label_codigo_fornec     .setForeground(Color.black);
        label_fornecedor        .setForeground(Color.black);
        label_descricao         .setForeground(Color.black);
        label_nome_reduzido     .setForeground(Color.black);
        label_seg_name          .setForeground(Color.black);
        label_marca             .setForeground(Color.black);
        label_garantia          .setForeground(Color.black);
        label_unidade           .setForeground(Color.black);
        label_ean               .setForeground(Color.black);
        label_ncm               .setForeground(Color.black);
        label_tipo_ncm          .setForeground(Color.black);
        label_cest              .setForeground(Color.black);
        label_origem            .setForeground(Color.black);
        label_preco_compra      .setForeground(Color.black);
        label_preco             .setForeground(Color.black);
        label_peso              .setForeground(Color.black);
        label_icms_cst          .setForeground(Color.black);
        label_icms_perc         .setForeground(Color.black);
        label_icms_pred         .setForeground(Color.black);
        label_ipi_cst           .setForeground(Color.black);
        label_ipi_perc          .setForeground(Color.black);
        label_pis_cst           .setForeground(Color.black);
        label_pis_perc          .setForeground(Color.black);
        label_cofins_cst        .setForeground(Color.black);
        label_cofins_perc       .setForeground(Color.black);
        label_images            .setForeground(Color.black);
        lb_psqfiltro            .setForeground(Color.black);
        jlFoto                  .setForeground(Color.black);


    //alinha os labels a direita
        label_codigo            .setHorizontalAlignment(JLabel.RIGHT);
        label_codigo_fornec     .setHorizontalAlignment(JLabel.RIGHT);
        label_fornecedor        .setHorizontalAlignment(JLabel.RIGHT);
        label_descricao         .setHorizontalAlignment(JLabel.RIGHT);
        label_nome_reduzido     .setHorizontalAlignment(JLabel.RIGHT);
        label_seg_name          .setHorizontalAlignment(JLabel.RIGHT);
        label_marca             .setHorizontalAlignment(JLabel.RIGHT);
        label_garantia          .setHorizontalAlignment(JLabel.RIGHT);
        label_unidade           .setHorizontalAlignment(JLabel.RIGHT);
        label_ean               .setHorizontalAlignment(JLabel.RIGHT);
        label_ncm               .setHorizontalAlignment(JLabel.RIGHT);
        label_tipo_ncm          .setHorizontalAlignment(JLabel.RIGHT);
        label_cest              .setHorizontalAlignment(JLabel.RIGHT);
        label_origem            .setHorizontalAlignment(JLabel.RIGHT);
        label_preco_compra      .setHorizontalAlignment(JLabel.RIGHT);
        label_preco             .setHorizontalAlignment(JLabel.RIGHT);
        label_peso              .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_cst          .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_perc         .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_pred         .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_cst           .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_perc          .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_cst           .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_perc          .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_cst        .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_perc       .setHorizontalAlignment(JLabel.RIGHT);
        label_images            .setHorizontalAlignment(JLabel.RIGHT);
        jlFoto                  .setHorizontalAlignment(JLabel.RIGHT);
        lb_psqfiltro            .setHorizontalAlignment(JLabel.RIGHT);
        jPFoto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPFoto.setBackground(new java.awt.Color(102, 102, 102));

    //Gera cor frente dos texfields setForegroundColor()
        tf_codigo               .setForeground(Color.black);
        tf_codigo_fornec        .setForeground(Color.black);
        tf_fornecedor           .setForeground(Color.black);
        tf_descricao            .setForeground(Color.black);
        tf_nome_reduzido        .setForeground(Color.black);
        tf_seg_name             .setForeground(Color.black);
        tf_marca                .setForeground(Color.black);
        tf_garantia             .setForeground(Color.black);
        tf_unidade              .setForeground(Color.black);
        tf_ean                  .setForeground(Color.black);
        tf_ncm                  .setForeground(Color.black);
        tf_tipo_ncm             .setForeground(Color.black);
        tf_cest                 .setForeground(Color.black);
        tf_origem               .setForeground(Color.black);
        tf_preco_compra         .setForeground(Color.black);
        tf_preco                .setForeground(Color.black);
        tf_peso                 .setForeground(Color.black);
        tf_icms_cst             .setForeground(Color.black);
        tf_icms_perc            .setForeground(Color.black);
        tf_icms_pred            .setForeground(Color.black);
        tf_ipi_cst              .setForeground(Color.black);
        tf_ipi_perc             .setForeground(Color.black);
        tf_pis_cst              .setForeground(Color.black);
        tf_pis_perc             .setForeground(Color.black);
        tf_cofins_cst           .setForeground(Color.black);
        tf_cofins_perc          .setForeground(Color.black);
        tf_images               .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_codigo               .setBackground(Color.white);
        tf_codigo_fornec        .setBackground(Color.white);
        tf_fornecedor           .setBackground(Color.white);
        tf_descricao            .setBackground(Color.white);
        tf_nome_reduzido        .setBackground(Color.white);
        tf_seg_name             .setBackground(Color.white);
        tf_marca                .setBackground(Color.white);
        tf_garantia             .setBackground(Color.white);
        tf_unidade              .setBackground(Color.white);
        tf_ean                  .setBackground(Color.white);
        tf_ncm                  .setBackground(Color.white);
        tf_tipo_ncm             .setBackground(Color.white);
        tf_cest                 .setBackground(Color.white);
        tf_origem               .setBackground(Color.white);
        tf_preco_compra         .setBackground(Color.white);
        tf_preco                .setBackground(Color.white);
        tf_peso                 .setBackground(Color.white);
        tf_icms_cst             .setBackground(Color.white);
        tf_icms_perc            .setBackground(Color.white);
        tf_icms_pred            .setBackground(Color.white);
        tf_ipi_cst              .setBackground(Color.white);
        tf_ipi_perc             .setBackground(Color.white);
        tf_pis_cst              .setBackground(Color.white);
        tf_pis_perc             .setBackground(Color.white);
        tf_cofins_cst           .setBackground(Color.white);
        tf_cofins_perc          .setBackground(Color.white);
        tf_images               .setBackground(Color.white);

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
        botao_imagem            .setBackground(Color.red);
        botao_buscaImg          .setBackground(Color.red);
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
        botao_sair               .setToolTipText("Exibe Imagem do Produto");
        botao_pesquisa           .setToolTipText("Pesquisa/Filtra o Texto digitado para o Campo a Pesquisar");
        rb_mixOr_psqproduto      .setToolTipText("Informe partes a pesquisar(separadas por ; ) que se encontre pelo menos uma delas em qualquer posição do texto");
        rb_mixAnd_psqproduto     .setToolTipText("Informe partes a pesquisar(separadas por ; ) que se encontre todas elas em qualquer posição do texto");
        rb_meio_psqproduto       .setToolTipText("Informe parte a pesquisar que se encontre em qualquer posição do texto");
        rb_inic_psqproduto       .setToolTipText("Informe parte a pesquisar que se encontre no inicio do texto");
        tf_psqfiltro             .setToolTipText("Contreúdo do Filtro no Momento");

        tf_origem                .setToolTipText("0 = Nacional, 1 = Importado");
        tf_icms_cst              .setToolTipText("102 = para ICMSSN102, 20 = c/red.base calc., outros CST, ver manual do contribuinte em: https://www.nfe.fazenda.gov.br/portal/listaHistorico.aspx?tipoConteudo=jX820xdYiQU=");
        tf_pis_cst               .setToolTipText("08 = para PISNT (Não tributado)");
        tf_cofins_cst            .setToolTipText("08 = para COFINSNT (Não tributado)");
        
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
        botao_imagem             .addActionListener(this);
        botao_buscaImg           .addActionListener(this);
        cb_pesq_lab              .addActionListener(this);
        //tf_pesquisa              .addActionListener(this);
        botao_pesquisa           .addActionListener(this);
        cb_pesquisa              .addActionListener(this);
        jTable1                  .addMouseListener(this);
        jTable1                  .addKeyListener(this);
        tf_psqunidade            .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqunidade            .addKeyListener(this);
        tf_unidade               .addKeyListener(this);
        tf_unidade               .addFocusListener(this);
        cb_unidade               .addActionListener(this);
        bt_psqfiltro             .addActionListener(this);
        bt_copyDescr             .addActionListener(this);

        
        //Posicionando os componentes labels e textfields da tabela
        label_codigo             .setBounds(100,165,90,20);
        tf_codigo                .setBounds(190,165,88,20);
        label_codigo_fornec      .setBounds(300,165,90,20);
        tf_codigo_fornec         .setBounds(390,165,88,20);
        label_fornecedor         .setBounds(500,165,90,20);
        tf_fornecedor            .setBounds(590,165,120,20);

        label_descricao          .setBounds(100,190,90,20);
        tf_descricao             .setBounds(190,190,640,20);

        label_unidade            .setBounds(100,215,90,20);
        tf_unidade               .setBounds(190,215,31,20);
        cb_unidade               .setBounds(231,215, 200,20);
        label_psqunidade         .setBounds(441,215, 100,20);
        tf_psqunidade            .setBounds(506,215, 100,20);
        rb_inic_psqunidade       .setBounds(601,215, 60,20);
        rb_meio_psqunidade       .setBounds(661,215, 55,20);

        label_nome_reduzido      .setBounds(70,240,120,20);
        tf_nome_reduzido         .setBounds(190,240,340,20);
        bt_copyDescr             .setBounds(550,240,25,25);
        label_seg_name           .setBounds(570,240,90,20);
        tf_seg_name              .setBounds(665,240,340,20);

        label_marca              .setBounds(100,265,90,20);
        tf_marca                 .setBounds(190,265,340,20);
        label_garantia           .setBounds(570,265,90,20);
        tf_garantia              .setBounds(665,265,60,20);

        label_ean                .setBounds(100,290,90,20);
        tf_ean                   .setBounds(190,290,112,20);
        label_ncm                .setBounds(270,290,90,20);
        tf_ncm                   .setBounds(365,290,75,20);
        label_tipo_ncm           .setBounds(440,290,70,20);
        tf_tipo_ncm              .setBounds(510,290,30,20);
        label_cest               .setBounds(550,290,170,20);
        tf_cest                  .setBounds(720,290,90,20);
        tf_ncm                   .setToolTipText("Código NCM conforme Tabela IBPT de Tributos para NFCe");
        tf_tipo_ncm              .setToolTipText("Tipo NCM conforme Tabela IBPT de Tributos para NFCe");

        label_origem             .setBounds(100,315,90,20);
        tf_origem                .setBounds(192,315,23,20);

        label_preco_compra       .setBounds(270,315,90,20);
        tf_preco_compra          .setBounds(365,315,65,20);

        label_preco              .setBounds(450,315,90,20);
        tf_preco                 .setBounds(545,315,65,20);

        label_peso               .setBounds(620,315,90,20);
        tf_peso                  .setBounds(715,315,90,20);

        label_icms_cst           .setBounds(70,340,120,20);
        tf_icms_cst              .setBounds(192,340,45,20);
        label_icms_perc          .setBounds(270,340,90,20);
        tf_icms_perc             .setBounds(365,340,45,20);
        label_icms_pred          .setBounds(450,340,120,20);
        tf_icms_pred             .setBounds(580,340,45,20);

        label_ipi_cst            .setBounds(650,340,120,20);
        tf_ipi_cst               .setBounds(780,340,35,20);
        label_ipi_perc           .setBounds(810,340,90,20);
        tf_ipi_perc              .setBounds(900,340,45,20);

        label_pis_cst            .setBounds(70,365,120,20);
        tf_pis_cst               .setBounds(192,365,35,20);
        label_pis_perc           .setBounds(270,365,90,20);
        tf_pis_perc              .setBounds(365,365,45,20);

        label_cofins_cst         .setBounds(450,365,120,20);
        tf_cofins_cst            .setBounds(580,365,35,20);
        label_cofins_perc        .setBounds(650,365,120,20);
        tf_cofins_perc           .setBounds(780,365,45,20);
        label_images             .setBounds(70,390,120,20);
        tf_images                .setBounds(192,390,500,20);
        botao_imagem             .setBounds(760,390,110,30);
        botao_buscaImg           .setBounds(710,390,40,30);
        jPFoto                   .setBounds(850,120,100,100);
        jlFoto                   .setBounds(850,120,100,100);
//Posicao do ultimo cpo da Tabela no Formulario. Utiliz.para Iniciar a exibicao do JTable
        posUltLabel           = 415;
        panel_pesquisa        .add(lb_pesquisa);
        panel_pesquisa        .add(cb_pesq_lab);
        panel_pesquisa        .add(tf_pesquisa);
        panel_pesquisa        .add(botao_pesquisa);
        panel_pesquisa        .add(cb_pesquisa);
        getContentPane()      .add(panel_pesquisa);
        getContentPane()      .add(lb_titulo);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
           },
           new String [] {
                "Codigo",
                "Descricao",
                "Unidade",
                "EAN",
                "NCM",
                "Tipo",
                "Preço Vda"
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_produto());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_codigo);
        getContentPane()      .add(tf_codigo);
        getContentPane()      .add(label_codigo_fornec);
        getContentPane()      .add(tf_codigo_fornec);
        getContentPane()      .add(label_fornecedor);
        getContentPane()      .add(tf_fornecedor);
        getContentPane()      .add(label_descricao);
        getContentPane()      .add(tf_descricao);
        getContentPane()      .add(label_nome_reduzido);
        getContentPane()      .add(tf_nome_reduzido);
        getContentPane()      .add(label_seg_name);
        getContentPane()      .add(tf_seg_name);
        getContentPane()      .add(label_marca);
        getContentPane()      .add(tf_marca);
        getContentPane()      .add(label_garantia);
        getContentPane()      .add(tf_garantia);
        getContentPane()      .add(label_unidade);
        getContentPane()      .add(tf_unidade);
        getContentPane()      .add(cb_unidade);
        getContentPane()      .add(label_psqunidade);
        getContentPane()      .add(tf_psqunidade);
        getContentPane()      .add(rb_inic_psqunidade);
        getContentPane()      .add(rb_meio_psqunidade);
        bg_psqunidade          .add(rb_inic_psqunidade);
        bg_psqunidade          .add(rb_meio_psqunidade);
        getContentPane()      .add(rb_inic_psqproduto);
        getContentPane()      .add(rb_meio_psqproduto);
        getContentPane()      .add(rb_mixOr_psqproduto);
        getContentPane()      .add(rb_mixAnd_psqproduto);
        getContentPane()      .add(lb_psqfiltro);
        getContentPane()      .add(tf_psqfiltro);
        getContentPane()      .add(bt_psqfiltro);
        getContentPane()      .add(bt_copyDescr);
        getContentPane()      .add(jPFoto);
        getContentPane()      .add(jlFoto);
        bg_psqproduto          .add(rb_inic_psqproduto);
        bg_psqproduto          .add(rb_meio_psqproduto);
        bg_psqproduto          .add(rb_mixOr_psqproduto);
        bg_psqproduto          .add(rb_mixAnd_psqproduto);
        getContentPane()      .add(label_ean);
        getContentPane()      .add(tf_ean);
        getContentPane()      .add(label_ncm);
        getContentPane()      .add(tf_ncm);
        getContentPane()      .add(label_tipo_ncm);
        getContentPane()      .add(tf_tipo_ncm);
        getContentPane()      .add(label_cest);
        getContentPane()      .add(tf_cest);
        getContentPane()      .add(label_origem);
        getContentPane()      .add(tf_origem);
        getContentPane()      .add(label_preco_compra);
        getContentPane()      .add(tf_preco_compra);
        getContentPane()      .add(label_preco);
        getContentPane()      .add(tf_preco);
        getContentPane()      .add(label_peso);
        getContentPane()      .add(tf_peso);
        getContentPane()      .add(label_icms_cst);
        getContentPane()      .add(tf_icms_cst);
        getContentPane()      .add(label_icms_perc);
        getContentPane()      .add(tf_icms_perc);
        getContentPane()      .add(label_icms_pred);
        getContentPane()      .add(tf_icms_pred);
        getContentPane()      .add(label_ipi_cst);
        getContentPane()      .add(tf_ipi_cst);
        getContentPane()      .add(label_ipi_perc);
        getContentPane()      .add(tf_ipi_perc);
        getContentPane()      .add(label_pis_cst);
        getContentPane()      .add(tf_pis_cst);
        getContentPane()      .add(label_pis_perc);
        getContentPane()      .add(tf_pis_perc);
        getContentPane()      .add(label_cofins_cst);
        getContentPane()      .add(tf_cofins_cst);
        getContentPane()      .add(label_cofins_perc);
        getContentPane()      .add(tf_cofins_perc);
        getContentPane()      .add(label_images);
        getContentPane()      .add(tf_images);
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
        getContentPane()      .add(botao_imagem);
        getContentPane()      .add(botao_buscaImg);

        cpo_tabela[0]         = "codigo";
        cpo_tabela[1]         = "descricao";
        cpo_tabela[2]         = "unidade";
        cpo_tabela[3]         = "nome_reduzido";
        cpo_tabela[4]         = "seg_name";
        cpo_tabela[5]         = "fornecedor";
        cpo_tabela[6]         = "marca";
        cpo_tabela[7]         = "garantia";
        cpo_tabela[8]         = "ean";
        cpo_tabela[9]         = "ncm";
        cpo_tabela[10]         = "tipo_ncm";
        cpo_tabela[11]         = "cest";
        cpo_tabela[12]         = "origem";
        cpo_tabela[13]         = "preco";
        cpo_tabela[14]         = "peso";
        cpo_tabela[15]         = "icms_cst";
        cpo_tabela[16]         = "icms_perc";
        cpo_tabela[17]         = "icms_pred";
        cpo_tabela[18]         = "ipi_cst";
        cpo_tabela[19]         = "ipi_perc";
        cpo_tabela[20]         = "pis_cst";
        cpo_tabela[21]         = "pis_perc";
        cpo_tabela[22]         = "cofins_cst";
        cpo_tabela[23]         = "cofins_perc";
        lab_tabela[0]         = "Codigo";
        lab_tabela[1]         = "Descricao";
        lab_tabela[2]         = "Unidade";
        lab_tabela[3]         = "Nome Reduzido";
        lab_tabela[4]         = "Categoria";
        lab_tabela[5]         = "Fornecedor";
        lab_tabela[6]         = "Marca";
        lab_tabela[7]         = "Garantia";
        lab_tabela[8]         = "Ean";
        lab_tabela[9]         = "Ncm";
        lab_tabela[10]         = "Tipo";
        lab_tabela[11]         = "Cod.Espec.Sit.Tribut.";
        lab_tabela[12]         = "Origem(0/1)";
        lab_tabela[13]         = "Preco";
        lab_tabela[14]         = "Peso(9.999)";
        lab_tabela[15]         = "CST ICMS(xx)";
        lab_tabela[16]         = "% ICMS";
        lab_tabela[17]         = "% ICMS(Red)";
        lab_tabela[18]         = "CST IPI(xx)";
        lab_tabela[19]         = "% IPI";
        lab_tabela[20]         = "CST PIS(xx)";
        lab_tabela[21]         = "% PIS";
        lab_tabela[22]         = "CST COFINS(xx)";
        lab_tabela[23]         = "% COFINS";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[2]          = tf_unidade;
        tab_cb_st[2]          = st_unidade;
        tab_cb_rs[2]          = rs_unidade;
        tab_cb_cb[2]          = cb_unidade;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[2]     = "unidade";
        tab_cb_cpo_assoc[2]   = "codigo";
        tab_cb_cpo_exibe[2]   = "descricao";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();
        for (int idx=0;idx<10;idx++) {
             txtPesqs[idx] = "";
        }

        try
        {
              //conexao conn = new conexao();
              //connection = conn.conecta("", "");
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_unidade  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_unidade, rs_unidade, cb_unidade, 2);

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
/*
 * 
                  sql_query = "select * from produto where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+texto+"%'"
                                 +" order by codigo";
 */
                 String cpoSql = tf_pesquisa.getText(), like = "";
                 String texto = "";
                 if (txtSql.equals("")) {
                     txtSql = " where ";
                 } else {
                     txtSql += " and ";
                 }
                 if ( (cb_pesq_lab.getSelectedIndex() <= 9 && cb_pesq_lab.getSelectedIndex() != 0) ) {
                     cpoSql = cpoSql.toUpperCase();
                 }
                 if (rb_meio_psqproduto.isSelected()) {
                     like = "%";
                     texto = cpoSql;
                     tf_psqfiltro.setText(tf_psqfiltro.getText()+" - "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" [meio] = "+cpoSql);
                 } else if (rb_mixOr_psqproduto.isSelected() || rb_mixAnd_psqproduto.isSelected()) {
                     cpoSql = cpoSql.replaceAll(",", ";");
                     cpoSql = cpoSql.replaceAll("\\+", ";");
                     for (int idx=0;idx<10;idx++) {
                         if (txtPesqs[idx].equals("")) {
                             txtPesqs[idx] = cpoSql;
                         }
                     }
                     String[] parts = cpoSql.split(";");
                     String cmdMix = "";
                     String andOr = "and";
                     if (rb_mixOr_psqproduto.isSelected()) {
                         andOr = "or";
                        tf_psqfiltro.setText(tf_psqfiltro.getText()+" - "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" [mix(ou)] = "+cpoSql);
                     } else {
                        tf_psqfiltro.setText(tf_psqfiltro.getText()+" - "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" [mix(e)] = "+cpoSql);
                     }
                      //  String part1 = parts[0]; // 004
                      //  String part2 = parts[1]; // 034556
                     like = "%";
//JOptionPane.showMessageDialog(null, "cpoSql: ["+cpoSql+"] - Total de parts: "+parts.length);
                     for (int i=0;i<parts.length;i++) {
//JOptionPane.showMessageDialog(null, "parts["+i+"]: "+parts[i]);
                        if (i>0) {
                            cmdMix += "%' "+andOr+" "+"upper("+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+")"+" like '%";
                        }
                        cmdMix += parts[i];
                     }
                     texto = cmdMix;
                 } else if (rb_inic_psqproduto.isSelected()){
                     like = "";
                     texto = cpoSql;
                     tf_psqfiltro.setText(tf_psqfiltro.getText()+" - "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" [inicio] = "+cpoSql);
                 }
                 if ( (cb_pesq_lab.getSelectedIndex() <= 9 && cb_pesq_lab.getSelectedIndex() != 0) ) {
                    txtSql += "(upper("+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+")"+" like '"+like+texto+"%')";
                 } else {
                    txtSql += cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+texto;
                 }
                  //sql_query = "select * from produto where "
                  //               +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+texto+"%'"
                  //               +" order by codigo";
             } else {
                  //sql_query = "select * from produto order by codigo";
             }
             sql_query = "select * from produto "+txtSql+" order by codigo";
//JOptionPane.showMessageDialog(null, "preencher_cb_pesquisa() - Indice do Campo: "+cb_pesq_lab.getSelectedIndex()+" - Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("descricao"));
             }
//JOptionPane.showMessageDialog(null, "preencher_cb_pesquisa() - Tamanho do resultset: "+qtRegTab+" - Comando sql_Query(filtro): "+sql_query);
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
        if ((qtRegTab*18) >= ((altura-20)-(posUltLabel+30)))
            jScrollPane           .setBounds(5, posUltLabel+30,990, (altura-20)-(posUltLabel+30));
        else if ((qtRegTab*18) > 150)
            jScrollPane           .setBounds(5, (altura-20)-(qtRegTab*18),990, (qtRegTab*18));
        else
            jScrollPane           .setBounds(5, 490, 990, 150);
        repaint();
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(240);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(6);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(30);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from produto");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar produto: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
/*
                 String cpoSql = tf_pesquisa.getText(), like = "";
                 if (cb_pesq_lab.getSelectedIndex() == 3 || cb_pesq_lab.getSelectedIndex() == 5) {
                     cpoSql = cpoSql.toUpperCase();
                 }
                 if (rb_meio_psqproduto.isSelected()) {
                     like = "%";
                 }
                sqlquery = "select * from produto where "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+cpoSql+"%' "
                           +" order by codigo";
 *
 */
                  String cpoSql = tf_pesquisa.getText(), like = "";
                 String texto = "";
                 if (cb_pesq_lab.getSelectedIndex() == 3 || cb_pesq_lab.getSelectedIndex() == 5) {
                     cpoSql = cpoSql.toUpperCase();
                 }
                 if (rb_meio_psqproduto.isSelected()) {
                     like = "%";
                     texto = cpoSql;
                 } else if (rb_mixOr_psqproduto.isSelected() || rb_mixAnd_psqproduto.isSelected()) {
                     cpoSql = cpoSql.replaceAll(",", ";");
                     cpoSql = cpoSql.replaceAll("\\+", ";");
                     String[] parts = cpoSql.split(";");
                     String cmdMix = "";
                     String andOr = "and";
                     if (rb_mixOr_psqproduto.isSelected()) {
                         andOr = "or";
                     }
                      //  String part1 = parts[0]; // 004
                      //  String part2 = parts[1]; // 034556
                     like = "%";
//JOptionPane.showMessageDialog(null, "cpoSql: ["+cpoSql+"] - Total de parts: "+parts.length);
                     for (int i=0;i<parts.length;i++) {
//JOptionPane.showMessageDialog(null, "parts["+i+"]: "+parts[i]);
                        if (i>0) {
                            cmdMix += "%' "+andOr+" "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%";
                        }
                        cmdMix += parts[i];
                     }
                     texto = cmdMix;
                 }
                 // sqlquery = "select * from produto where "
                 //                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+texto+"%'"
                 //                +" order by codigo";
           } else {
               // sqlquery = "select * from produto order by codigo";
           }
            sqlquery = "select * from produto "+txtSql+" order by codigo";
//JOptionPane.showMessageDialog(null, "preencher_jTable() - Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("codigo"),
                resultset.getString("descricao"),
                resultset.getString("unidade"),
                resultset.getString("ean"),
                resultset.getString("ncm"),
                resultset.getString("tipo_ncm"),
                String.format("%.2f", resultset.getDouble("preco")),
                                            }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: produto: "+qtreg);
//JOptionPane.showMessageDialog(null, "preencher_jTable() - Tamanho do resultset: "+qtRegTab+" - Comando sql_Query(filtro): "+sqlquery);
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
               tf_codigo_fornec      .setText(resultset.getString("codigo_fornec"));
               tf_fornecedor         .setText(resultset.getString("fornecedor"));
               tf_descricao          .setText(resultset.getString("descricao").trim());
               tf_nome_reduzido      .setText(resultset.getString("nome_reduzido"));
               tf_seg_name           .setText(resultset.getString("seg_name"));
               tf_marca              .setText(resultset.getString("marca"));
               tf_garantia           .setText(resultset.getString("garantia"));
               tf_unidade            .setText(resultset.getString("unidade"));
               tf_ean                .setText(resultset.getString("ean"));
               tf_ncm                .setText(resultset.getString("ncm"));
               tf_tipo_ncm           .setText(resultset.getString("tipo_ncm"));
               tf_cest               .setText(resultset.getString("cest"));
               tf_origem             .setText(resultset.getString("origem"));
               tf_preco_compra       .setText(resultset.getString("preco_compra"));
               tf_preco              .setText(resultset.getString("preco"));
               tf_peso               .setText(resultset.getString("peso"));
               tf_icms_cst           .setText(resultset.getString("icms_cst"));
               tf_icms_perc          .setText(resultset.getString("icms_perc"));
               tf_icms_pred          .setText(resultset.getString("icms_pred"));
               tf_ipi_cst            .setText(resultset.getString("ipi_cst"));
               tf_ipi_perc           .setText(resultset.getString("ipi_perc"));
               tf_pis_cst            .setText(resultset.getString("pis_cst"));
               tf_pis_perc           .setText(resultset.getString("pis_perc"));
               tf_cofins_cst         .setText(resultset.getString("cofins_cst"));
               tf_cofins_perc        .setText(resultset.getString("cofins_perc"));
               tf_images             .setText(resultset.getString("images"));
                //jlFoto.setIcon(new ImageIcon(System.getProperty("user.dir")+fsep+"imagens"+fsep+tf_images.getText()));
                System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+tf_images.getText());
                if (tf_images.getText().equals("")) {
                    jlFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.JPG")));
                } else {
                    File f = new File(System.getProperty("user.dir")+fsep+"imagens"+fsep+tf_images.getText());
                    Image image;
                    try {
                        image = ImageIO.read(f);
                      jlFoto.setIcon(new ImageIcon(image.getScaledInstance(jlFoto.getWidth(),jlFoto.getHeight(), Image.SCALE_DEFAULT)));  //("/imagens/produtos/semfoto.jpg"));
                    } catch (IOException ex) {
                        System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+tf_images.getText()+"Erro ao carregar foto: "+ex);
                    }
                }
//JOptionPane.showMessageDialog(null,"Valor de Descricao: "+tf_descricao.getText());
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
           botao_imagem.setEnabled(true);
           botao_buscaImg.setEnabled(true);
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
            else if (acao.getSource() == botao_excluir) {
                if (checkNfProd()) {
                    excluir();
                }
            }
            else if (acao.getSource() == botao_alterar){
                if (check_textField())
                    alterar();
            }
            else if (acao.getSource() == botao_novo)
                novo_registro();
            else if (acao.getSource() == bt_psqfiltro) {
                tf_psqfiltro.setText("");
                txtSql = "";
                for (int idx=0;idx<10;idx++) {
                     txtPesqs[idx] = "";
                }
            }
            else if (acao.getSource() == bt_copyDescr) {
                String wdesc = tf_descricao.getText();
                if (wdesc.length() > 60) {
                    wdesc = wdesc.substring(0, 60);
                }
                tf_nome_reduzido.setText(wdesc);
            }
            else if (acao.getSource() == botao_pesquisa) {
                //pesquisadigitacao();
                clicado = false;
                preencher_cb_pesquisa("filtrar");
                cb_pesquisa.setSelectedItem(tf_descricao.getText());
                preencher_jtable("filtrar");
                clicado = true;
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
                this.dispose();
           }
            else if (acao.getSource() == cb_unidade){
               if ( stat_aux)
                   trata_cb_auxiliar(st_unidade, rs_unidade, cb_unidade, tf_unidade, 2);
            }
            else if (acao.getSource() == botao_buscaImg){
                escolheFoto();
            }
            else if (acao.getSource() == botao_imagem){
                if (!botao_alterar.isEnabled()) {
                   JOptionPane.showMessageDialog(null, "Edite um Registro antes de Clicar em Imagem!");
                } else {
                    String txtImagem;  // = tf_images.getText().substring(0, tf_images.getText().indexOf(".jpg")+4);
//                      String[] parts = tf_images.getText().split(".jpg");
//                      //  String part1 = parts[0]; // 004
//                      //  String part2 = parts[1]; // 034556
//                    //JOptionPane.showMessageDialog(null, "Vai exibir as imagens deste produto . . . Total de imagens: "+parts.length);
//                    for (int i=(parts.length-1);i>=0;i--) {
//                        txtImagem = parts[i].trim();  //+".jpg";
//
//                        //JOptionPane.showMessageDialog(null, "Imagem ["+i+"] : "+txtImagem);
//                        new ExibeImagemProduto(i, parts.length, txtImagem).show();
//                    }
                  new ExibeImagemProduto(0, 1, tf_images.getText()).show();
                }
            }
      }
      
      private void escolheFoto() {
        try {
            String pastaDestino = System.getProperty("user.dir")+fsep+"imagens"+fsep;
            JFileChooser busca_foto = new JFileChooser();
            busca_foto.setCurrentDirectory(new java.io.File(pastaDestino));        
            busca_foto.setDialogTitle("Carregar imagem do Cliente");         
            busca_foto.showOpenDialog(this);
             //System.out.println("Arquivo: "+busca_foto.getSelectedFile().getName());
            if (busca_foto.getSelectedFile().getName().equals("")){
                System.out.println("Nenhuma imagem foi escolhida!");
            } else {
                String foto = ""+busca_foto.getSelectedFile().getName();
                String pastaOrigem = busca_foto.getSelectedFile().getAbsolutePath();
                pastaOrigem = pastaOrigem.substring(0, pastaOrigem.indexOf(busca_foto.getSelectedFile().getName()));
                //System.out.println("Arquivo: "+busca_foto.getSelectedFile().getName()+" - Origem: "+pastaOrigem+" - Destino: "+pastaDestino);
                tf_images.setText(foto);
                File f = new File(pastaDestino+foto);
                Image image;
                try {
                    image = ImageIO.read(f);
                  jlFoto.setIcon(new ImageIcon(image.getScaledInstance(jlFoto.getWidth(),jlFoto.getHeight(), Image.SCALE_DEFAULT)));  //("/imagens/produtos/semfoto.jpg"));
                } catch (IOException ex) {
                    System.out.println("Caminho: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+tf_images.getText()+"Erro ao carregar foto: "+ex);
                }
                // copia do arquivo para a pasta de imagens do sistema
                if (pastaOrigem.compareTo(pastaDestino) != 0) {
                    //JOptionPane.showMessageDialog(null,"Copiando origem: "+pastaOrigem+foto+" - destino: "+pastaDestino+foto);
                    try {
                        FileInputStream fis = null;
                        fis = new FileInputStream(pastaOrigem+foto);
                        FileChannel origem = null;
                        origem = fis.getChannel();
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(pastaDestino+foto);
                        FileChannel destino = null;
                        destino = fos.getChannel();

                        //copia o arquivo
                        destino.transferFrom(origem, 0, origem.size());
                        origem.close();
                        destino.close();
                        System.out.println("Copia concluida!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Nao foi possível copiar a imagem para a pasta destino!");
                    }
                }
            }
        }        
        catch(Exception erro) {
            JOptionPane.showMessageDialog(null,"Nao foi possível carregar a imagem do produto.");
        }
    }

      private boolean checkNfProd() {
          boolean retorno = true;
        try
        {
            String sqlquery = "select * from nf_produtos where cod_produto = "+tf_codigo.getText();
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            int qtRegProd = 0;
            while (resultset.next()) {
                if (resultset.getInt("numeronfe") > 0) {
                    qtRegProd++;
                }
                //break;
            }
            if (qtRegProd > 0) {
                retorno = false;
                JOptionPane.showMessageDialog(null, "Produto não pode ser Excluído porque foi utilizado [ "+qtRegProd+"] vezes em NFes!");
            }
        }
        catch(SQLException erro)
        {
             JOptionPane.showMessageDialog(null, "Erro ao buscar existencia de utilizacao deste produto em NFes = "+erro);
        }
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
        try
        {
            String sqlquery = "select * from produto order by codigo";
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
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
        tf_codigo_fornec      .setText("");
        tf_fornecedor         .setText("");
        tf_descricao          .setText("");
        tf_unidade            .setText("");
        tf_nome_reduzido      .setText("");
        tf_seg_name           .setText("");
        tf_marca              .setText("");
        tf_garantia           .setText("0.00");
        tf_ean                .setText("");
        tf_ncm                .setText("");
        tf_tipo_ncm           .setText("0");
        tf_cest               .setText("");
        tf_origem             .setText("0");
        tf_preco_compra       .setText("0.00");
        tf_preco              .setText("0.00");
        tf_peso               .setText("0.000");
        tf_icms_cst           .setText("00");
        tf_icms_perc          .setText("0.00");
        tf_icms_pred          .setText("0.00");
        tf_ipi_cst            .setText("00");
        tf_ipi_perc           .setText("0.00");
        tf_pis_cst            .setText("00");
        tf_pis_perc           .setText("0.00");
        tf_cofins_cst         .setText("00");
        tf_cofins_perc        .setText("0.00");
        tf_images             .setText("");
      posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
          tf_codigo.setEditable(false);
//Limpa filtros da inclusao anterior nos comboBox auxiliares 
          if (!tf_psqunidade.getText().equals("")){
             tf_psqunidade.setText("");
             preenche_cb_auxiliar(st_unidade, rs_unidade, cb_unidade, 2);
          }
           botao_gravar.setEnabled(true);
           botao_imagem.setEnabled(true);
           botao_buscaImg.setEnabled(true);
          botao_alterar.setEnabled(false);
          botao_excluir.setEnabled(false);
          rb_inic_psqproduto.setSelected(true);
          tf_pesquisa.setText("");

          jlFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.JPG")));
        }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        String msg_erro = "";
        if ( tf_codigo.getText() == null ) tf_codigo.setText("");
        if ( tf_codigo_fornec.getText() == null ) tf_codigo_fornec.setText("");
        if ( tf_fornecedor.getText() == null ) tf_fornecedor.setText("");
        if ( tf_descricao.getText() == null ) tf_descricao.setText("");
        if ( tf_unidade.getText() == null ) tf_unidade.setText("");
        if (tf_unidade.getText().equals("")){
           msg_erro += "Um Codigo válido precisa ser informado!\n";
           retorno = false;
        }
        if ( tf_nome_reduzido.getText() == null ) tf_nome_reduzido.setText("");
        if ( tf_seg_name.getText() == null ) tf_seg_name.setText("");
        if ( tf_marca.getText() == null ) tf_marca.setText("");
        if ( tf_garantia.getText() == null ) tf_garantia.setText("0.00");
        if ( tf_ean.getText() == null ) tf_ean.setText("");
        if ( tf_ncm.getText() == null ) tf_ncm.setText("");
        if ( tf_tipo_ncm.getText() == null ) tf_tipo_ncm.setText("0");
        if ( tf_cest.getText() == null ) tf_cest.setText("");
        if ( tf_origem.getText() == null ) tf_origem.setText("");
        if ( tf_preco_compra.getText() == null ) tf_preco_compra.setText("0.00");
        if ( tf_preco.getText() == null ) tf_preco.setText("0.00");
        if ( tf_peso.getText() == null ) tf_peso.setText("0.000");
        if ( tf_icms_cst.getText() == null ) tf_icms_cst.setText("102");
        if ( tf_icms_perc.getText() == null ) tf_icms_perc.setText("0.00");
        if ( tf_icms_pred.getText() == null ) tf_icms_pred.setText("0.00");
        if ( tf_ipi_cst.getText() == null ) tf_ipi_cst.setText("");
        if ( tf_ipi_perc.getText() == null ) tf_ipi_perc.setText("0.00");
        if ( tf_pis_cst.getText() == null ) tf_pis_cst.setText("08");
        if ( tf_pis_perc.getText() == null ) tf_pis_perc.setText("0.00");
        if ( tf_cofins_cst.getText() == null ) tf_cofins_cst.setText("08");
        if ( tf_cofins_perc.getText() == null ) tf_cofins_perc.setText("0.00");
        if ( tf_images.getText() == null ) tf_images.setText("");
        if (tf_nome_reduzido.getText().equals("")) {
            if (tf_descricao.getText().length() > 100) {
                tf_nome_reduzido.setText(tf_descricao.getText().substring(0,100));
            } else {
                tf_nome_reduzido.setText(tf_descricao.getText().trim());
            }
        }
        if (tf_preco.getText().equals("0.00")) {
           msg_erro += "Um Preço de Venda MAIOR que 0,00 precisa ser informado!\n";
           retorno = false;
        }
         if ( tf_ean.getText().equals("")) {
           msg_erro += "CUIDADO! Produto sem Código EAN (Código de Barras)\n";
         }
         if (tf_ncm.getText().equals("")) {
            tf_ncm.setText("07099990");
            msg_erro += "ATENCAO! Produto informado sem NCM (Obrigatório para NFe/NFCe). Será assumido o valor genéricoo: 07099990\n";
         }
         if (tf_tipo_ncm.getText().equals("")) {
            tf_tipo_ncm.setText("0");
         }
         if (tf_icms_cst.getText().length() > 3) {
             msg_erro += "Código CST do ICMS com mais de 3 caracteres.\n";
           retorno = false;
         }
         if (tf_pis_cst.getText().length() > 2) {
             msg_erro += "Código CST do PIS com mais de 2 caracteres.\n";
           retorno = false;
         }
         if (tf_cofins_cst.getText().length() > 2) {
             msg_erro += "Código CST do COFINS com mais de 2 caracteres.\n";
           retorno = false;
         }
        tf_descricao.setText(tf_descricao.getText().toUpperCase());
        tf_nome_reduzido.setText(tf_nome_reduzido.getText().toUpperCase());
        if (!msg_erro.equals("")) {
           JOptionPane.showMessageDialog(null, "Problema(s) encontrado(s) na validação dos campos:\n"+msg_erro);
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
                tf_descricao.setText(tf_descricao.getText());  //.toUpperCase());
                tf_nome_reduzido.setText(tf_nome_reduzido.getText());  //.toUpperCase());
                tf_seg_name.setText(tf_seg_name.getText());  //.toUpperCase());
                tf_marca.setText(tf_marca.getText());  //.toUpperCase());
                tf_garantia.setText(tf_garantia.getText().replaceAll(",", "."));
                tf_preco_compra.setText(tf_preco_compra.getText().replaceAll(",", "."));
                tf_preco.setText(tf_preco.getText().replaceAll(",", "."));
                tf_peso.setText(tf_peso.getText().replaceAll(",", "."));
                tf_icms_perc.setText(tf_icms_perc.getText().replaceAll(",", "."));
                tf_icms_pred.setText(tf_icms_pred.getText().replaceAll(",", "."));
                tf_ipi_perc.setText(tf_ipi_perc.getText().replaceAll(",", "."));
                tf_pis_perc.setText(tf_pis_perc.getText().replaceAll(",", "."));
                tf_cofins_perc.setText(tf_cofins_perc.getText().replaceAll(",", "."));
                 String sql_insert = "insert into produto ( "+
                        "codigo, "+"codigo_fornec, "+"fornecedor, "+"descricao, "+"unidade, "+
                        "nome_reduzido, "+"seg_name, "+"marca, "+"garantia, "+"ean, "+
                        "ncm, "+"tipo_ncm, "+"cest, "+"origem, "+"preco_compra, "+"preco, "+"peso, "+"icms_cst, "+
                        "icms_perc, "+"icms_pred, "+"ipi_cst, "+"ipi_perc, "+"pis_cst, "+"pis_perc, "+"cofins_cst, "+
                        "cofins_perc, "+"images "+")"+
                        " values ("
                        +tf_codigo.getText() + ", "
                        +"'"+tf_codigo_fornec.getText() + "', "
                        +"'"+tf_fornecedor.getText() + "', "
                        +"'"+tf_descricao.getText() + "', "
                        +"'"+tf_unidade.getText() + "', "
                        +"'"+tf_nome_reduzido.getText() + "', "
                        +"'"+tf_seg_name.getText() + "', "
                        +"'"+tf_marca.getText() + "', "
                        +""+tf_garantia.getText() + ", "
                        +"'"+tf_ean.getText() + "', "
                        +"'"+tf_ncm.getText() + "', "
                        +"'"+tf_tipo_ncm.getText() + "', "
                        +"'"+tf_cest.getText() + "', "
                        +"'"+tf_origem.getText() + "', "
                        +""+tf_preco_compra.getText() + ", "
                        +""+tf_preco.getText() + ", "
                        +""+tf_peso.getText() + ", "
                        +"'"+tf_icms_cst.getText() + "', "
                        +""+tf_icms_perc.getText() + ", "
                        +""+tf_icms_pred.getText() + ", "
                        +"'"+tf_ipi_cst.getText() + "', "
                        +""+tf_ipi_perc.getText() + ", "
                        +"'"+tf_pis_cst.getText() + "', "
                        +""+tf_pis_perc.getText() + ", "
                        +"'"+tf_cofins_cst.getText() + "', "
                        +""+tf_cofins_perc.getText() + ", "
                        +"'"+tf_images.getText() + "' "
                        + ")";
            System.out.println("Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from produto order by codigo");
                 resultset.last();
                 //mostra_conteudo_nos_campos();
                 posiciona_combo_e_jtable();
                 clicado = true;
                 novo_registro();
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 //JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o registro.\nErro: "+erro);
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
                tf_descricao.setText(tf_descricao.getText());  //.toUpperCase());
                tf_nome_reduzido.setText(tf_nome_reduzido.getText());  //.toUpperCase());
                tf_seg_name.setText(tf_seg_name.getText());  //.toUpperCase());
                tf_marca.setText(tf_marca.getText());  //.toUpperCase());
                tf_garantia.setText(tf_garantia.getText().replaceAll(",", "."));
                tf_preco_compra.setText(tf_preco_compra.getText().replaceAll(",", "."));
                tf_preco.setText(tf_preco.getText().replaceAll(",", "."));
                tf_peso.setText(tf_peso.getText().replaceAll(",", "."));
                tf_icms_perc.setText(tf_icms_perc.getText().replaceAll(",", "."));
                tf_icms_pred.setText(tf_icms_pred.getText().replaceAll(",", "."));
                tf_ipi_perc.setText(tf_ipi_perc.getText().replaceAll(",", "."));
                tf_pis_perc.setText(tf_pis_perc.getText().replaceAll(",", "."));
                tf_cofins_perc.setText(tf_cofins_perc.getText().replaceAll(",", "."));
                 String sql_alterar = "update produto set "
                        +"codigo_fornec = '"+ tf_codigo_fornec.getText()+"', "
                        +"fornecedor = '"+ tf_fornecedor.getText()+"', "
                        +"descricao = '"+ tf_descricao.getText()+"', "
                        +"unidade = '"+ tf_unidade.getText()+"', "
                        +"nome_reduzido = '"+ tf_nome_reduzido.getText()+"', "
                        +"seg_name = '"+ tf_seg_name.getText()+"', "
                        +"marca = '"+ tf_marca.getText()+"', "
                        +"garantia = "+ tf_garantia.getText()+", "
                        +"ean = '"+ tf_ean.getText()+"', "
                        +"ncm = '"+ tf_ncm.getText()+"', "
                        +"tipo_ncm = '"+ tf_tipo_ncm.getText()+"', "
                        +"cest = '"+ tf_cest.getText()+"', "
                        +"origem = '"+ tf_origem.getText()+"', "
                        +"preco_compra = "+ tf_preco_compra.getText()+", "
                        +"preco = "+ tf_preco.getText()+", "
                        +"peso = "+ tf_peso.getText()+", "
                        +"icms_cst = '"+ tf_icms_cst.getText()+"', "
                        +"icms_perc = "+ tf_icms_perc.getText()+", "
                        +"icms_pred = "+ tf_icms_pred.getText()+", "
                        +"ipi_cst = '"+ tf_ipi_cst.getText()+"', "
                        +"ipi_perc = "+ tf_ipi_perc.getText()+", "
                        +"pis_cst = '"+ tf_pis_cst.getText()+"', "
                        +"pis_perc = "+ tf_pis_perc.getText()+", "
                        +"cofins_cst = '"+ tf_cofins_cst.getText()+"', "
                        +"cofins_perc = "+ tf_cofins_perc.getText()+", "
                        +"images = '"+ tf_images.getText()+"'"
                        +" where codigo = " + tf_codigo.getText() ;
                  System.out.println("sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from produto order by codigo");
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
           String nome = "Deletar produto : "+tf_descricao.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM produto Where codigo = " + tf_codigo.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from produto order by codigo");
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
        //JOptionPane.showMessageDialog(null, "Impressao da Tabela - Falta Implementar este Botao");
        Impressao impr = new Impressao(""+empresa, "produto.jasper", connection, razaoEmp, enderEmp);
        impr.imprimeRelJasper();
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
    public void pesquisadigitacao() {
       try {
JOptionPane.showMessageDialog(null, "Conteudo do sql = "+"select * from produto "+txtSql+" order by codigo");
           resultset  = statement.executeQuery("select * from produto "+txtSql+" order by codigo");
           resultset.first();
           boolean achou = false;
           int reg = 0;
           String cpo_tab;
           while(resultset.next()) {
               cpo_tab = resultset.getString(cpo_tabela[cb_pesq_lab.getSelectedIndex()]);
               String texto = "";
               //String[] txtPesqs = new String[10];
               for (int idx=0;idx<10;idx++) {
                   //txtPesqs[0] = "";
                   if (!txtPesqs[idx].equals("")) {
JOptionPane.showMessageDialog(null, "Conteudo do Filtro ["+idx+"] = "+txtPesqs[idx]);
                       if (rb_mixOr_psqproduto.isSelected() || rb_mixAnd_psqproduto.isSelected()) {
                           String[] parts = txtPesqs[idx].split(";");  //tf_pesquisa.getText().split(";");
                           int qtOcorr = 0;
                           for (int i=0;i<parts.length;i++) {
                               if (cpo_tab.toUpperCase().indexOf(parts[i].toUpperCase()) != -1) {
                                   qtOcorr++;
                                   reg = resultset.getRow();
                                   //break;
                                }
                           }
                           if (qtOcorr>0) {
                               if (rb_mixAnd_psqproduto.isSelected()) {
                                  if (qtOcorr == parts.length) {
                                    achou = true;
                                  }
                               } else {
                                   achou = true;
                               }
                           }
                       } else {
                          texto = txtPesqs[idx];  //tf_pesquisa.getText();
                       //System.out.println("Pesquisando: "+cpo_tab.toUpperCase()+" - e comparando com: "+tf_pesquisa.getText().toUpperCase());
                           if (cpo_tab.toUpperCase().indexOf(texto.toUpperCase()) != -1) {
                               achou = true;
                               reg = resultset.getRow();
                               break;
                           }
                       }
                   }
               }
               //resultset.next();
            }
           if (achou){
                resultset.first();
                for (int i=1; i<reg; i++){
                    resultset.next();
                }
                mostra_conteudo_nos_campos();
                clicado = false;
                preencher_cb_pesquisa("filtrar");
                cb_pesquisa.setSelectedItem(tf_descricao.getText());
                preencher_jtable("filtrar");
                clicado = true;
           } else {
             JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o Texto no Campo informado!");
          }
        } catch(Exception erro) {
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
        String sql = "select * from produto where Descricao = "+cb_pesquisa.getSelectedItem();
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
            String sql_query = "";
            try {
                if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
/*
                     String cpoSql = tf_pesquisa.getText(), like = "";
                     if (cb_pesq_lab.getSelectedIndex() == 3 || cb_pesq_lab.getSelectedIndex() == 5) {
                         cpoSql = cpoSql.toUpperCase();
                     }
                     if (rb_meio_psqproduto.isSelected()) {
                         like = "%";
                     }
                    String sql_query = "select * from produto where "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+cpoSql+"%'"
                                +" order by codigo";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
 *
 */
                  String cpoSql = tf_pesquisa.getText(), like = "";
                 String texto = "";
                 if (cb_pesq_lab.getSelectedIndex() == 3 || cb_pesq_lab.getSelectedIndex() == 5) {
                     cpoSql = cpoSql.toUpperCase();
                 }
                 if (rb_meio_psqproduto.isSelected()) {
                     like = "%";
                     texto = cpoSql;
                 } else if (rb_mixOr_psqproduto.isSelected() || rb_mixAnd_psqproduto.isSelected()) {
                     cpoSql = cpoSql.replaceAll(",", ";");
                     cpoSql = cpoSql.replaceAll("\\+", ";");
                     String[] parts = cpoSql.split(";");
                     String cmdMix = "";
                     String andOr = "and";
                     if (rb_mixOr_psqproduto.isSelected()) {
                         andOr = "or";
                     }
                      //  String part1 = parts[0]; // 004
                      //  String part2 = parts[1]; // 034556
                     like = "%";
//JOptionPane.showMessageDialog(null, "cpoSql: ["+cpoSql+"] - Total de parts: "+parts.length);
                     for (int i=0;i<parts.length;i++) {
//JOptionPane.showMessageDialog(null, "parts["+i+"]: "+parts[i]);
                        if (i>0) {
                            cmdMix += "%' "+andOr+" "+cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%";
                        }
                        cmdMix += parts[i];
                     }
                     texto = cmdMix;
                 }
                  //sql_query = "select * from produto where "
                  //               +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '"+like+texto+"%'"
                  //               +" order by codigo";
//JOptionPane.showMessageDialog(null, "Comando sqlQuery no mouseClicked: "+sql_query);
                   // resultset = statement.executeQuery(sql_query);
               } else {
                    //resultset  = statement.executeQuery("select * from produto order by codigo");
                }
                sql_query = "select * from produto "+txtSql+" order by codigo";
                resultset = statement.executeQuery(sql_query);
                //resultset = statement.executeQuery("select * from produto");
                 int qtreg = 0;
                 while(resultset.next()){
                     if (jTable1.getSelectedRow() == qtreg) {
                        //JOptionPane.showMessageDialog(null, "Achou registro: "+qtreg);
                         mostra_conteudo_nos_campos();
                         break;
                     }
                     qtreg++;
                 }
//JOptionPane.showMessageDialog(null, "trataEvento_mouse_key_jTable1() - Tamanho do resultset: "+qtRegTab+" - Comando sql_Query(filtro): "+sql_query);
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
       } else if(e.getSource() == tf_unidade) {
           System.out.println("Teclou enter no campo unidade . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2);
               tf_ean.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqunidade){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_unidade, rs_unidade, cb_unidade, tf_psqunidade, rb_inic_psqunidade, rb_meio_psqunidade, 2);
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
       if (e.getSource() == tf_unidade){
           System.out.println("Perdeu o foco do campo unidade . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2))
        tf_ean.requestFocus();
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
        JFrame form = new Produto(3, null, null, null);
            form.setVisible(true);
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_produto extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_produto()
    {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 1) {
            this.setHorizontalAlignment(LEFT);
        } else {
            this.setHorizontalAlignment(CENTER);

        }

        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
