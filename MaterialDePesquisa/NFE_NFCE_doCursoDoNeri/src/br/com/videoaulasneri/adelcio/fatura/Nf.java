/*

Descrição: Digitação dos Dados Principais para Geração da NFe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Relatorios.Impressao;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfe;
import br.com.videoaulasneri.adelcio.nfefacil.NFefacil;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getFile_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getSenha_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getStc_drive;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getTipoAmbiente;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.ConsultarCadastro;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import br.inf.portalfiscal.nfe.schema.retConsCad.TRetConsCad;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Nf extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    NFefacil nfem2;
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    int wcodcliente = 0;
    JLabel lb_titulo = new JLabel("Digitação da Nota Fiscal ");
    JLabel lb_ambiente = new JLabel(nfem2.getLb_ambiente().getText());
    JLabel lb_status = new JLabel(nfem2.getMotivo());

    //Labels dos campos da tabela;
    JLabel label_pedido              = new JLabel("Pedido: ");
    JLabel label_cod_cliente         = new JLabel("Cod.Cliente: ");
    JLabel label_cod_forma_pgto      = new JLabel("Cod.Forma Pgto: ");
    JLabel label_cod_tipo_doc        = new JLabel("Cod.Tipo Doc: ");
    JLabel label_cod_banco           = new JLabel("Cod.Banco: ");
    JLabel label_data_digitacao      = new JLabel("Data Digitacao: ");
    JLabel label_valor_produtos      = new JLabel("Valor Produtos: ");
    JLabel label_valor_descontos     = new JLabel("Valor Descontos: ");
    JLabel label_valor_total         = new JLabel("Valor Total: ");
    JLabel label_cod_transportador   = new JLabel("Cod.Transportador: ");
    JLabel label_dados_adicionais    = new JLabel("Dados Adicionais: ");
    JLabel label_qtde_volume         = new JLabel("Qtde Volumes: ");
    JLabel label_peso_volume         = new JLabel("Peso Volumes: ");
    JLabel label_placa_veiculo       = new JLabel("Placa Veiculo: ");
    JLabel label_uf_placa            = new JLabel("Uf Placa: ");
    JLabel label_pedido_cliente      = new JLabel("Pedido Cliente: ");
    JLabel label_numero_nfe          = new JLabel("Numero NFe/NFCe: ");
    JLabel label_serie_nfe           = new JLabel("Serie: ");
    JLabel label_modelonfe           = new JLabel("Mod: ");
    JLabel label_data_emissao        = new JLabel("Emissao: ");
    JLabel label_chave_nfe           = new JLabel("Chave: ");
    JLabel label_icms_bc             = new JLabel("Icms BC: ");
    JLabel label_icms_vlr            = new JLabel("Icms Vlr: ");
    JLabel label_ipi_bc              = new JLabel("Ipi BC: ");
    JLabel label_ipi_vlr             = new JLabel("Ipi Vlr: ");
    JLabel label_pis_bc              = new JLabel("Pis BC: ");
    JLabel label_pis_vlr             = new JLabel("Pis Vlr: ");
    JLabel label_cofins_bc           = new JLabel("Cofins BC: ");
    JLabel label_cofins_vlr          = new JLabel("Cofins Vlr: ");
    JLabel label_fin_nfe             = new JLabel("Tipo da NFe: ");
    JLabel label_num_nfe_fat         = new JLabel("Num.da NFe Fat.: ");
    JLabel label_data_canc           = new JLabel("Data Cancelamento: ");

    //JTextFields dos campos das tabelas
    JTextField tf_pedido             = new JTextField();
    JTextField tf_cod_cliente        = new JTextField();
    JTextField tf_cod_forma_pgto     = new JTextField();
    JTextField tf_cod_tipo_doc       = new JTextField();
    JTextField tf_cod_banco          = new JTextField();
    //JTextField tf_data_digitacao     = new JTextField();
    public static JTextField tf_valor_produtos     = new JTextField();
    public static JTextField tf_valor_descontos    = new JTextField();
    public static JTextField tf_valor_total        = new JTextField();
    JTextField tf_cod_transportador  = new JTextField();
    JTextField tf_dados_adicionais   = new JTextField();
    public static JTextField tf_qtde_volume        = new JTextField();
    public static JTextField tf_peso_volume        = new JTextField();
    JTextField tf_placa_veiculo      = new JTextField();
    JTextField tf_uf_placa           = new JTextField();
    JTextField tf_pedido_cliente     = new JTextField();
    JTextField tf_numero_nfe         = new JTextField();
    JTextField tf_serie_nfe          = new JTextField();
    JTextField tf_modelonfe          = new JTextField();
    //JTextField tf_data_emissao       = new JTextField();
    JTextField tf_chave_nfe          = new JTextField();
    JTextField tf_icms_bc            = new JTextField();
    JTextField tf_icms_vlr           = new JTextField();
    JTextField tf_ipi_bc             = new JTextField();
    JTextField tf_ipi_vlr            = new JTextField();
    JTextField tf_pis_bc             = new JTextField();
    JTextField tf_pis_vlr            = new JTextField();
    JTextField tf_cofins_bc          = new JTextField();
    JTextField tf_cofins_vlr         = new JTextField();
    JTextField tf_tipo_nfe            = new JTextField();
    JTextField tf_num_nfe_fat        = new JTextField();
    JTextField tf_data_canc          = new JTextField();

    JFormattedTextField  tf_data_digitacao, tf_data_emissao;
    MaskFormatter dtf_data;

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
    JButton botao_salvar             = new JButton("Salvar");
    JButton botao_prod               = new JButton("Produtos");
    JButton botao_prazo              = new JButton("Prazos");
    JButton botao_geraXML            = new JButton("Gera XML");
    JButton botao_geraBoleto         = new JButton("Gera Boleto");
    JButton botao_conscad            = new JButton("Consulta Cliente");

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox<String> cb_pesquisa            = new JComboBox<>();
    JComboBox<String> cb_pesq_lab            = new JComboBox<>();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[29];
    String [] lab_tabela             = new String[29];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[29];
    String [] tab_cb_cpo_exibe       = new String[29];
    String [] tab_cb_cpo_assoc       = new String[29];
    JTextField [] tab_cb_tf          = new JTextField[29];
    Statement [] tab_cb_st           = new Statement[29];
    ResultSet [] tab_cb_rs           = new ResultSet[29];
    JComboBox [] tab_cb_cb   = new JComboBox[29];

    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 0;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_cod_cliente;
    PreparedStatement ps_aux;
    int empresa = 0;
    String pedido = "";
    int cod_forma_old = 0;
    private String cli_pessoa;
    private String cli_cnpj;
    private String cli_uf;

    ResultSet rs_cod_cliente;
    Statement st_cod_cliente, stat_prazo, st_dup;
    JComboBox<String> cb_cod_cliente        = new JComboBox<>();
    JLabel label_psqcod_cliente           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_cliente          = new JTextField();
    ButtonGroup bg_psqcod_cliente         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_cliente   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_cliente   = new JRadioButton("Meio");
    ResultSet rs_cod_forma_pgto;
    Statement st_cod_forma_pgto;
    JComboBox<String> cb_cod_forma_pgto     = new JComboBox<>();
    JLabel label_psqcod_forma_pgto           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_forma_pgto          = new JTextField();
    ButtonGroup bg_psqcod_forma_pgto         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_forma_pgto   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_forma_pgto   = new JRadioButton("Meio");
    ResultSet rs_cod_tipo_doc;
    Statement st_cod_tipo_doc;
    JComboBox<String> cb_cod_tipo_doc       = new JComboBox<>();
    JLabel label_psqcod_tipo_doc           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_tipo_doc          = new JTextField();
    ButtonGroup bg_psqcod_tipo_doc         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_tipo_doc   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_tipo_doc   = new JRadioButton("Meio");
    ResultSet rs_cod_banco;
    Statement st_cod_banco;
    JComboBox<String> cb_cod_banco          = new JComboBox<>();
    JLabel label_psqcod_banco           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_banco          = new JTextField();
    ButtonGroup bg_psqcod_banco         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_banco   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_banco   = new JRadioButton("Meio");
    ResultSet rs_cod_transportador;
    Statement st_cod_transportador;
    JComboBox<String> cb_cod_transportador  = new JComboBox<>();
    JLabel label_psqcod_transportador           = new JLabel("Pesquisar: ");
    JTextField tf_psqcod_transportador          = new JTextField();
    ButtonGroup bg_psqcod_transportador         = new ButtonGroup();
    JRadioButton rb_inic_psqcod_transportador   = new JRadioButton("Inicio");
    JRadioButton rb_meio_psqcod_transportador   = new JRadioButton("Meio");
    String wserie = "";
    String fin_nfe = "1";  //  1 = Normal, 4 = Devolucao
    int num_nfe_fat = 0;
    String emiteBoleto = "N";
    String fuso_horario = "";
    private String tipoNF = "1";
    String UFEmit = "41";
    String numNFe_old = "0";
    String contatoCupom = "";

    @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    public Nf(int empresa, Connection confat, String fuso_horario, String UFEmit, String contatoCupom)
    {
        setTitle("Formulario de Manutencao de nf");
        setSize(1010, 680);
        setLocation(135,5);
        setResizable(true);
//        if (motivo.indexOf("em Operacao") != -1) {
//            motivo = "";
//        }
        this.empresa        = empresa;
        this.connection     = confat;
        this.fuso_horario   = fuso_horario;
        this.UFEmit         = UFEmit;
        this.contatoCupom   = contatoCupom;
//System.out.println("nf - UFEmit: "+UFEmit);        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Nao");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane()        .setLayout(null);
        panel_pesquisa          .setLayout(null);
        panel_pesquisa          .setBounds(140,30,780,50);
        lb_pesquisa             .setBounds(5,20,100,20);
        cb_pesq_lab             .setBounds(75,20,150,20);
        tf_pesquisa             .setBounds(235,20,200,20);
        botao_pesquisa          .setBounds(445,15,50,30);
        cb_pesquisa             .setBounds(515,20,200,20);
        lb_titulo               .setBounds(10,10,250,30);
        lb_titulo               .setFont(new Font("Arial",Font.BOLD,16));
        lb_titulo               .setForeground(Color.black);
        tf_pesquisa.setToolTipText("Informe data no formato: aaaa-mm-dd Ex: 2018-01-23");

        lb_ambiente             .setBounds(260,10,200,30);
        lb_ambiente             .setFont(new Font("Arial",Font.BOLD,16));
        lb_ambiente             .setForeground(Color.blue);
        
        lb_status               .setBounds(470,10,400,30);
        lb_status               .setFont(new Font("Arial",Font.BOLD,16));
        lb_status               .setForeground(new java.awt.Color(51, 204,0));
        
//JOptionPane.showMessageDialog(null, "Ambiente: " + lb_ambiente.getText() + " - Motivo: " + lb_status.getText());
        if (lb_ambiente.getText().indexOf("PRODU") == -1) {
            lb_ambiente.setForeground(Color.RED);
            lb_ambiente.setBackground(new java.awt.Color(200, 240,30));
        }
        if (lb_status.getText().indexOf("em Opera") == -1) {
            lb_status.setForeground(Color.RED);
            lb_status.setBackground(Color.CYAN);
        }
        try {
            dtf_data           = new MaskFormatter("##/##/####");
             dtf_data           .setValidCharacters("0123456789");
             tf_data_digitacao     = new JFormattedTextField(dtf_data);
             tf_data_emissao     = new JFormattedTextField(dtf_data);
        } catch (ParseException ex) {
            //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    //Gera cor frente dos lanbels setForegroundColor()
        label_pedido            .setForeground(Color.black);
        label_cod_cliente       .setForeground(Color.black);
        label_cod_forma_pgto    .setForeground(Color.black);
        label_cod_tipo_doc      .setForeground(Color.black);
        label_cod_banco         .setForeground(Color.black);
        label_data_digitacao    .setForeground(Color.black);
        label_valor_produtos    .setForeground(Color.black);
        label_valor_descontos   .setForeground(Color.black);
        label_valor_total       .setForeground(Color.black);
        label_cod_transportador .setForeground(Color.black);
        label_dados_adicionais  .setForeground(Color.black);
        label_qtde_volume       .setForeground(Color.black);
        label_peso_volume       .setForeground(Color.black);
        label_placa_veiculo     .setForeground(Color.black);
        label_uf_placa          .setForeground(Color.black);
        label_pedido_cliente    .setForeground(Color.black);
        label_numero_nfe        .setForeground(Color.black);
        label_serie_nfe         .setForeground(Color.black);
        label_modelonfe         .setForeground(Color.black);
        label_data_emissao      .setForeground(Color.black);
        label_chave_nfe         .setForeground(Color.black);
        label_icms_bc           .setForeground(Color.black);
        label_icms_vlr          .setForeground(Color.black);
        label_ipi_bc            .setForeground(Color.black);
        label_ipi_vlr           .setForeground(Color.black);
        label_pis_bc            .setForeground(Color.black);
        label_pis_vlr           .setForeground(Color.black);
        label_cofins_bc         .setForeground(Color.black);
        label_cofins_vlr        .setForeground(Color.black);
        label_fin_nfe           .setForeground(Color.black);
        label_num_nfe_fat       .setForeground(Color.black);
        label_data_canc         .setForeground(Color.black);


    //alinha os labels a direita
        label_pedido            .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_cliente       .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_forma_pgto    .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_tipo_doc      .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_banco         .setHorizontalAlignment(JLabel.RIGHT);
        label_data_digitacao    .setHorizontalAlignment(JLabel.RIGHT);
        label_valor_produtos    .setHorizontalAlignment(JLabel.RIGHT);
        label_valor_descontos   .setHorizontalAlignment(JLabel.RIGHT);
        label_valor_total       .setHorizontalAlignment(JLabel.RIGHT);
        label_cod_transportador .setHorizontalAlignment(JLabel.RIGHT);
        label_dados_adicionais  .setHorizontalAlignment(JLabel.RIGHT);
        label_qtde_volume       .setHorizontalAlignment(JLabel.RIGHT);
        label_peso_volume       .setHorizontalAlignment(JLabel.RIGHT);
        label_placa_veiculo     .setHorizontalAlignment(JLabel.RIGHT);
        label_uf_placa          .setHorizontalAlignment(JLabel.RIGHT);
        label_pedido_cliente    .setHorizontalAlignment(JLabel.RIGHT);
        label_numero_nfe        .setHorizontalAlignment(JLabel.RIGHT);
        label_serie_nfe         .setHorizontalAlignment(JLabel.RIGHT);
        label_modelonfe         .setHorizontalAlignment(JLabel.RIGHT);
        label_data_emissao      .setHorizontalAlignment(JLabel.RIGHT);
        label_chave_nfe         .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_bc           .setHorizontalAlignment(JLabel.RIGHT);
        label_icms_vlr          .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_bc            .setHorizontalAlignment(JLabel.RIGHT);
        label_ipi_vlr           .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_bc            .setHorizontalAlignment(JLabel.RIGHT);
        label_pis_vlr           .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_bc         .setHorizontalAlignment(JLabel.RIGHT);
        label_cofins_vlr        .setHorizontalAlignment(JLabel.RIGHT);
        label_fin_nfe           .setHorizontalAlignment(JLabel.RIGHT);
        label_num_nfe_fat       .setHorizontalAlignment(JLabel.RIGHT);
        label_data_canc         .setHorizontalAlignment(JLabel.RIGHT);

    //Gera cor frente dos texfields setForegroundColor()
        tf_pedido               .setForeground(Color.black);
        tf_cod_cliente          .setForeground(Color.black);
        tf_cod_forma_pgto       .setForeground(Color.black);
        tf_cod_tipo_doc         .setForeground(Color.black);
        tf_cod_banco            .setForeground(Color.black);
        tf_data_digitacao       .setForeground(Color.black);
        tf_valor_produtos       .setForeground(Color.black);
        tf_valor_descontos      .setForeground(Color.black);
        tf_valor_total          .setForeground(Color.black);
        tf_cod_transportador    .setForeground(Color.black);
        tf_dados_adicionais     .setForeground(Color.black);
        tf_qtde_volume          .setForeground(Color.black);
        tf_peso_volume          .setForeground(Color.black);
        tf_placa_veiculo        .setForeground(Color.black);
        tf_uf_placa             .setForeground(Color.black);
        tf_pedido_cliente       .setForeground(Color.black);
        tf_numero_nfe           .setForeground(Color.black);
        tf_modelonfe            .setForeground(Color.black);
        tf_serie_nfe            .setForeground(Color.black);
        tf_data_emissao         .setForeground(Color.black);
        tf_chave_nfe            .setForeground(Color.black);
        tf_icms_bc              .setForeground(Color.black);
        tf_icms_vlr             .setForeground(Color.black);
        tf_ipi_bc               .setForeground(Color.black);
        tf_ipi_vlr              .setForeground(Color.black);
        tf_pis_bc               .setForeground(Color.black);
        tf_pis_vlr              .setForeground(Color.black);
        tf_cofins_bc            .setForeground(Color.black);
        tf_cofins_vlr           .setForeground(Color.black);
        tf_tipo_nfe              .setForeground(Color.black);
        tf_num_nfe_fat          .setForeground(Color.black);
        tf_data_canc            .setForeground(Color.black);

    //Gera cor frente de fundo texfields setBackroundColor()
        tf_pedido               .setBackground(Color.white);
        tf_cod_cliente          .setBackground(new Color(255, 255, 158));
        tf_cod_forma_pgto       .setBackground(new Color(255, 255, 158));
        tf_cod_tipo_doc         .setBackground(new Color(255, 255, 158));
        tf_cod_banco            .setBackground(new Color(255, 255, 158));
        tf_data_digitacao       .setBackground(Color.white);
        tf_valor_produtos       .setBackground(Color.white);
        tf_valor_descontos      .setBackground(Color.white);
        tf_valor_total          .setBackground(Color.white);
        tf_cod_transportador    .setBackground(new Color(255, 255, 158));
        tf_dados_adicionais     .setBackground(new Color(255, 255, 158));
        tf_qtde_volume          .setBackground(Color.white);
        tf_peso_volume          .setBackground(Color.white);
        tf_placa_veiculo        .setBackground(new Color(255, 255, 158));
        tf_uf_placa             .setBackground(new Color(255, 255, 158));
        tf_pedido_cliente       .setBackground(new Color(255, 255, 158));
        tf_numero_nfe           .setBackground(Color.white);
        tf_serie_nfe            .setBackground(Color.white);
        tf_modelonfe            .setBackground(Color.white);
        tf_data_emissao         .setBackground(Color.white);
        tf_chave_nfe            .setBackground(Color.white);
        tf_icms_bc              .setBackground(Color.white);
        tf_icms_vlr             .setBackground(Color.white);
        tf_ipi_bc               .setBackground(Color.white);
        tf_ipi_vlr              .setBackground(Color.white);
        tf_pis_bc               .setBackground(Color.white);
        tf_pis_vlr              .setBackground(Color.white);
        tf_cofins_bc            .setBackground(Color.white);
        tf_cofins_vlr           .setBackground(Color.white);
        tf_tipo_nfe              .setBackground(Color.white);
        tf_num_nfe_fat          .setBackground(Color.white);
        tf_data_canc            .setBackground(Color.white);

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
        botao_salvar            .setBackground(Color.red);
        botao_prod              .setBackground(Color.red);
        panel_pesquisa          .setBackground(Color.red);
        botao_prazo             .setBackground(Color.red);
        botao_geraXML           .setBackground(Color.red);
        botao_geraBoleto        .setBackground(Color.red);
        botao_conscad           .setBackground(Color.red);

        //Posicionando os botoes com setBounds()
        botao_primeiro_registro  .setBounds(100,85,40,30);
        botao_registro_anterior  .setBounds(150,85,40,30);
        botao_proximo_registro   .setBounds(200,85,40,30);
        botao_ultimo_registro    .setBounds(250,85,40,30);
        botao_novo               .setBounds(300,85,40,30);
        botao_gravar             .setBounds(350,85,40,30);
        botao_alterar            .setBounds(400,85,40,30);
        botao_excluir            .setBounds(450,85,40,30);
        botao_imprimir           .setBounds(500,85,40,30);
        botao_sair               .setBounds(560,85,40,30);
        botao_salvar             .setBounds(605,85,80,30);
        botao_prod               .setBounds(690,85,90,30);
        botao_prazo              .setBounds(785,85,80,30);
        botao_geraXML            .setBounds(870,85,90,30);
        botao_geraBoleto         .setBounds(965,85,100,30);

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
        botao_salvar             .setToolTipText("Salva o Pedido Atual");
        botao_prod               .setToolTipText("Acessar os Itens ou Produtos deste Pedido");
        botao_pesquisa           .setToolTipText("Pesquisa/Filtra o Texto digitado para o Campo a Pesquisar");
        botao_prazo              .setToolTipText("Acessar os Prazos deste Pedido");
        botao_geraXML            .setToolTipText("Gerar Arquivo XML para Autorizar NFe");
        botao_geraBoleto         .setToolTipText("Gerar Arquivo XML para Imprimir Boleto");
        botao_conscad            .setToolTipText("Consulta Situação do Destinatário na Receita Estadual");

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
        botao_prod               .addActionListener(this);
        botao_prazo              .addActionListener(this);
        botao_geraXML            .addActionListener(this);
        botao_geraBoleto         .addActionListener(this);
        botao_conscad            .addActionListener(this);
        cb_pesq_lab              .addActionListener(this);
        //tf_pesquisa              .addActionListener(this);
        botao_pesquisa           .addActionListener(this);
        cb_pesquisa              .addActionListener(this);
        jTable1                  .addMouseListener(this);
        jTable1                  .addKeyListener(this);
        tf_psqcod_cliente                  .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_cliente                  .addKeyListener(this);
        tf_cod_cliente                  .addKeyListener(this);
        tf_cod_cliente                  .addFocusListener(this);
        tf_psqcod_forma_pgto               .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_forma_pgto               .addKeyListener(this);
        tf_cod_forma_pgto               .addKeyListener(this);
        tf_cod_forma_pgto               .addFocusListener(this);
        tf_psqcod_tipo_doc                 .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_tipo_doc                 .addKeyListener(this);
        tf_cod_tipo_doc                 .addKeyListener(this);
        tf_cod_tipo_doc                 .addFocusListener(this);
        tf_psqcod_banco                    .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_banco                    .addKeyListener(this);
        tf_cod_banco                    .addKeyListener(this);
        tf_cod_banco                    .addFocusListener(this);
        tf_psqcod_transportador            .setToolTipText("Informe parte do texto para Filtrar. <em branco> mostra tudo.");
        tf_psqcod_transportador            .addKeyListener(this);
        tf_cod_transportador            .addKeyListener(this);
        tf_cod_transportador            .addFocusListener(this);
        cb_cod_cliente       .addActionListener(this);
        cb_cod_forma_pgto    .addActionListener(this);
        cb_cod_tipo_doc      .addActionListener(this);
        cb_cod_banco         .addActionListener(this);
        cb_cod_transportador .addActionListener(this);

        
        //Posicionando os componentes labels e textfields da tabela
        label_pedido             .setBounds(70,130,130,20);
        tf_pedido                .setBounds(200,130,88,20);
        label_cod_cliente        .setBounds(295,130,97,20);
        tf_cod_cliente           .setBounds(390,130,88,20);
        cb_cod_cliente           .setBounds(488,130, 200,20);
        label_psqcod_cliente     .setBounds(688,130, 100,20);
        tf_psqcod_cliente        .setBounds(755,130, 100,20);
        rb_inic_psqcod_cliente   .setBounds(885,130, 60,20);
        rb_meio_psqcod_cliente   .setBounds(940,130, 55,20);

        label_cod_forma_pgto     .setBounds(70,155,130,20);
        tf_cod_forma_pgto        .setBounds(200,155,88,20);
        cb_cod_forma_pgto        .setBounds(298,155, 250,20);
        label_psqcod_forma_pgto  .setBounds(558,155, 100,20);
        tf_psqcod_forma_pgto     .setBounds(623,155, 100,20);
        rb_inic_psqcod_forma_pgto.setBounds(718,155, 60,20);
        rb_meio_psqcod_forma_pgto.setBounds(778,155, 55,20);

        label_cod_tipo_doc       .setBounds(70,180,130,20);
        tf_cod_tipo_doc          .setBounds(200,180,31,20);
        cb_cod_tipo_doc          .setBounds(241,180, 200,20);
        label_psqcod_tipo_doc    .setBounds(451,180, 100,20);
        tf_psqcod_tipo_doc       .setBounds(516,180, 100,20);
        rb_inic_psqcod_tipo_doc  .setBounds(611,180, 60,20);
        rb_meio_psqcod_tipo_doc  .setBounds(671,180, 55,20);
        botao_conscad            .setBounds(731,180,130,30);

        label_cod_banco          .setBounds(70,205,130,20);
        tf_cod_banco             .setBounds(200,205,31,20);
        cb_cod_banco             .setBounds(241,205, 200,20);
        label_psqcod_banco       .setBounds(451,205, 100,20);
        tf_psqcod_banco          .setBounds(516,205, 100,20);
        rb_inic_psqcod_banco     .setBounds(611,205, 60,20);
        rb_meio_psqcod_banco     .setBounds(671,205, 55,20);

        label_cod_transportador   .setBounds(70,225,130,20);
        tf_cod_transportador      .setBounds(200,225,31,20);
        cb_cod_transportador      .setBounds(298,225, 200,20);
        label_psqcod_transportador.setBounds(508,225, 100,20);
        tf_psqcod_transportador   .setBounds(573,225, 100,20);
        rb_inic_psqcod_transportador   .setBounds(668,225, 60,20);
        rb_meio_psqcod_transportador   .setBounds(728,225, 55,20);

        label_placa_veiculo      .setBounds(70,250,130,20);
        tf_placa_veiculo         .setBounds(200,250,79,20);
        label_uf_placa           .setBounds(304,250,76,20);
        tf_uf_placa              .setBounds(380,250,31,20);
        label_pedido_cliente     .setBounds(436,250,118,20);
        tf_pedido_cliente        .setBounds(554,250,200,20);

        label_dados_adicionais   .setBounds(70,275,130,20);
        tf_dados_adicionais      .setBounds(200,275,500,20);

        label_data_digitacao     .setBounds(70,300,130,20);
        tf_data_digitacao        .setBounds(200,300,104,20);
        label_valor_produtos     .setBounds(329,300,118,20);
        tf_valor_produtos        .setBounds(447,300,192,20);
        label_fin_nfe            .setBounds(730,300,130,20);
        tf_tipo_nfe               .setBounds(860,300,120,20);

        label_valor_descontos    .setBounds(70,325,130,20);
        tf_valor_descontos       .setBounds(200,325,192,20);
        label_valor_total        .setBounds(417,325,97,20);
        tf_valor_total           .setBounds(514,325,192,20);
        label_num_nfe_fat        .setBounds(730,325,130,20);
        tf_num_nfe_fat           .setBounds(860,325,120,20);

        label_qtde_volume        .setBounds(70,350,130,20);
        tf_qtde_volume           .setBounds(200,350,88,20);
        label_peso_volume        .setBounds(313,350,97,20);
        tf_peso_volume           .setBounds(410,350,192,20);
        label_data_canc          .setBounds(730,350,130,20);
        tf_data_canc             .setBounds(860,350,120,20);

        label_numero_nfe         .setBounds(70,375,130,20);
        tf_numero_nfe            .setBounds(200,375,80,20);
        label_serie_nfe          .setBounds(275,375,43,20);
        tf_serie_nfe             .setBounds(320,375,39,20);
        label_modelonfe          .setBounds(360,375,35,20);
        tf_modelonfe             .setBounds(395,375,35,20);
        label_data_emissao       .setBounds(430,375,65,20);
        tf_data_emissao          .setBounds(505,375,75,20);
        label_chave_nfe          .setBounds(610,375,50,20);
        tf_chave_nfe             .setBounds(660,375,320,20);
/*
        label_icms_bc            .setBounds(100,450,100,20);
        tf_icms_bc               .setBounds(200,450,192,20);
        label_icms_vlr           .setBounds(417,450,76,20);
        tf_icms_vlr              .setBounds(493,450,192,20);
        label_ipi_bc             .setBounds(100,475,100,20);
        tf_ipi_bc                .setBounds(200,475,192,20);
        label_ipi_vlr            .setBounds(417,475,69,20);
        tf_ipi_vlr               .setBounds(486,475,192,20);
        label_pis_bc             .setBounds(100,500,100,20);
        tf_pis_bc                .setBounds(200,500,192,20);
        label_pis_vlr            .setBounds(417,500,69,20);
        tf_pis_vlr               .setBounds(486,500,192,20);
        label_cofins_bc          .setBounds(100,525,100,20);
        tf_cofins_bc             .setBounds(200,525,192,20);
        label_cofins_vlr         .setBounds(417,525,90,20);
        tf_cofins_vlr            .setBounds(507,525,192,20);
 *
 */
        label_icms_bc            .setBounds(70,400,130,20);
        tf_icms_bc               .setBounds(200,400,92,20);
        label_icms_vlr           .setBounds(300,400,100,20);
        tf_icms_vlr              .setBounds(400,400,92,20);


        label_ipi_bc             .setBounds(500,400,130,20);
        tf_ipi_bc                .setBounds(6500,400,92,20);
        label_ipi_vlr            .setBounds(750,400,100,20);
        tf_ipi_vlr               .setBounds(850,400,92,20);

        label_pis_bc             .setBounds(70,425,130,20);
        tf_pis_bc                .setBounds(200,425,92,20);
        label_pis_vlr            .setBounds(300,425,100,20);
        tf_pis_vlr               .setBounds(400,425,92,20);

        label_cofins_bc          .setBounds(500,425,130,20);
        tf_cofins_bc             .setBounds(650,425,92,20);
        label_cofins_vlr         .setBounds(750,425,100,20);
        tf_cofins_vlr            .setBounds(850,425,92,20);

        posUltLabel           = 425;
        panel_pesquisa        .add(lb_pesquisa);
        panel_pesquisa        .add(cb_pesq_lab);
        panel_pesquisa        .add(tf_pesquisa);
        panel_pesquisa        .add(botao_pesquisa);
        panel_pesquisa        .add(cb_pesquisa);
        getContentPane()      .add(panel_pesquisa);
        getContentPane()      .add(lb_titulo);
        getContentPane()      .add(lb_ambiente);
        getContentPane()      .add(lb_status);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
           },
           new String [] {
                "Pedido",
                "Cod_cliente",
                "Cod_forma_pgto",
                "Cod_tipo_doc",
                "Data_digitacao",
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_nf());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_pedido);
        getContentPane()      .add(tf_pedido);
        getContentPane()      .add(label_cod_cliente);
        getContentPane()      .add(tf_cod_cliente);
        getContentPane()      .add(cb_cod_cliente);
        getContentPane()      .add(label_psqcod_cliente);
        getContentPane()      .add(tf_psqcod_cliente);
        getContentPane()      .add(rb_inic_psqcod_cliente);
        getContentPane()      .add(rb_meio_psqcod_cliente);
        bg_psqcod_cliente          .add(rb_inic_psqcod_cliente);
        bg_psqcod_cliente          .add(rb_meio_psqcod_cliente);
        getContentPane()      .add(label_cod_forma_pgto);
        getContentPane()      .add(tf_cod_forma_pgto);
        getContentPane()      .add(cb_cod_forma_pgto);
        getContentPane()      .add(label_psqcod_forma_pgto);
        getContentPane()      .add(tf_psqcod_forma_pgto);
        getContentPane()      .add(rb_inic_psqcod_forma_pgto);
        getContentPane()      .add(rb_meio_psqcod_forma_pgto);
        bg_psqcod_forma_pgto          .add(rb_inic_psqcod_forma_pgto);
        bg_psqcod_forma_pgto          .add(rb_meio_psqcod_forma_pgto);

        getContentPane()      .add(label_cod_tipo_doc);
        getContentPane()      .add(tf_cod_tipo_doc);
        getContentPane()      .add(cb_cod_tipo_doc);
        getContentPane()      .add(label_psqcod_tipo_doc);
        getContentPane()      .add(tf_psqcod_tipo_doc);
        getContentPane()      .add(rb_inic_psqcod_tipo_doc);
        getContentPane()      .add(rb_meio_psqcod_tipo_doc);
        bg_psqcod_tipo_doc          .add(rb_inic_psqcod_tipo_doc);
        bg_psqcod_tipo_doc          .add(rb_meio_psqcod_tipo_doc);

        getContentPane()      .add(label_cod_banco);
        getContentPane()      .add(tf_cod_banco);
        getContentPane()      .add(cb_cod_banco);
        getContentPane()      .add(label_psqcod_banco);
        getContentPane()      .add(tf_psqcod_banco);
        getContentPane()      .add(rb_inic_psqcod_banco);
        getContentPane()      .add(rb_meio_psqcod_banco);
        bg_psqcod_banco          .add(rb_inic_psqcod_banco);
        bg_psqcod_banco          .add(rb_meio_psqcod_banco);

        getContentPane()      .add(label_data_digitacao);
        getContentPane()      .add(tf_data_digitacao);
        getContentPane()      .add(label_valor_produtos);
        getContentPane()      .add(tf_valor_produtos);
        getContentPane()      .add(label_valor_descontos);
        getContentPane()      .add(tf_valor_descontos);
        getContentPane()      .add(label_valor_total);
        getContentPane()      .add(tf_valor_total);
        getContentPane()      .add(label_cod_transportador);
        getContentPane()      .add(tf_cod_transportador);
        getContentPane()      .add(cb_cod_transportador);
        getContentPane()      .add(label_psqcod_transportador);
        getContentPane()      .add(tf_psqcod_transportador);
        getContentPane()      .add(rb_inic_psqcod_transportador);
        getContentPane()      .add(rb_meio_psqcod_transportador);
        bg_psqcod_transportador          .add(rb_inic_psqcod_transportador);
        bg_psqcod_transportador          .add(rb_meio_psqcod_transportador);
        getContentPane()      .add(label_dados_adicionais);
        getContentPane()      .add(tf_dados_adicionais);
        getContentPane()      .add(label_fin_nfe);
        getContentPane()      .add(tf_tipo_nfe);
        getContentPane()      .add(label_num_nfe_fat);
        getContentPane()      .add(tf_num_nfe_fat);
        getContentPane()      .add(label_data_canc);
        getContentPane()      .add(tf_data_canc);
        getContentPane()      .add(label_qtde_volume);
        getContentPane()      .add(tf_qtde_volume);
        getContentPane()      .add(label_peso_volume);
        getContentPane()      .add(tf_peso_volume);
        getContentPane()      .add(label_placa_veiculo);
        getContentPane()      .add(tf_placa_veiculo);
        getContentPane()      .add(label_uf_placa);
        getContentPane()      .add(tf_uf_placa);
        getContentPane()      .add(label_pedido_cliente);
        getContentPane()      .add(tf_pedido_cliente);
        getContentPane()      .add(label_numero_nfe);
        getContentPane()      .add(tf_numero_nfe);
        getContentPane()      .add(label_serie_nfe);
        getContentPane()      .add(tf_serie_nfe);
        getContentPane()      .add(label_modelonfe);
        getContentPane()      .add(tf_modelonfe);
        getContentPane()      .add(label_data_emissao);
        getContentPane()      .add(tf_data_emissao);
        getContentPane()      .add(label_chave_nfe);
        getContentPane()      .add(tf_chave_nfe);

        getContentPane()      .add(label_icms_bc);
        getContentPane()      .add(tf_icms_bc);
        getContentPane()      .add(label_icms_vlr);
        getContentPane()      .add(tf_icms_vlr);
        getContentPane()      .add(label_ipi_bc);
        getContentPane()      .add(tf_ipi_bc);
        getContentPane()      .add(label_ipi_vlr);
        getContentPane()      .add(tf_ipi_vlr);
        getContentPane()      .add(label_pis_bc);
        getContentPane()      .add(tf_pis_bc);
        getContentPane()      .add(label_pis_vlr);
        getContentPane()      .add(tf_pis_vlr);
        getContentPane()      .add(label_cofins_bc);
        getContentPane()      .add(tf_cofins_bc);
        getContentPane()      .add(label_cofins_vlr);
        getContentPane()      .add(tf_cofins_vlr);
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
        getContentPane()      .add(botao_salvar);
        getContentPane()      .add(botao_prod);
        getContentPane()      .add(botao_prazo);
        getContentPane()      .add(botao_geraXML);
        getContentPane()      .add(botao_geraBoleto);
        getContentPane()      .add(botao_conscad);

        cpo_tabela[0]         = "pedido";
        cpo_tabela[1]         = "cod_cliente";
        cpo_tabela[2]         = "cod_forma_pgto";
        cpo_tabela[3]         = "cod_tipo_doc";
        cpo_tabela[4]         = "cod_banco";
        cpo_tabela[5]         = "data_digitacao";
        cpo_tabela[6]         = "valor_produtos";
        cpo_tabela[7]         = "valor_descontos";
        cpo_tabela[8]         = "valor_total";
        cpo_tabela[9]         = "cod_transportador";
        cpo_tabela[10]         = "dados_adicionais";
        cpo_tabela[11]         = "qtde_volume";
        cpo_tabela[12]         = "peso_volume";
        cpo_tabela[13]         = "placa_veiculo";
        cpo_tabela[14]         = "uf_placa";
        cpo_tabela[15]         = "pedido_cliente";
        cpo_tabela[16]         = "numero_nfe";
        cpo_tabela[17]         = "serie_nfe";
        cpo_tabela[18]         = "modelonfe";
        cpo_tabela[19]         = "data_emissao";
        cpo_tabela[20]         = "chave_nfe";
        cpo_tabela[21]         = "icms_bc";
        cpo_tabela[22]         = "icms_vlr";
        cpo_tabela[23]         = "ipi_bc";
        cpo_tabela[24]         = "ipi_vlr";
        cpo_tabela[25]         = "pis_bc";
        cpo_tabela[26]         = "pis_vlr";
        cpo_tabela[27]         = "cofins_bc";
        cpo_tabela[28]         = "cofins_vlr";
        lab_tabela[0]         = "Pedido";
        lab_tabela[1]         = "Cod.Cliente";
        lab_tabela[2]         = "Cod.Forma Pgto";
        lab_tabela[3]         = "Cod.Tipo Doc";
        lab_tabela[4]         = "Cod.Banco";
        lab_tabela[5]         = "Data Digitacao";
        lab_tabela[6]         = "Valor Produtos";
        lab_tabela[7]         = "Valor Descontos";
        lab_tabela[8]         = "Valor Total";
        lab_tabela[9]         = "Cod.Transportador";
        lab_tabela[10]         = "Dados Adicionais";
        lab_tabela[11]         = "Qtde Volume";
        lab_tabela[12]         = "Peso Volume";
        lab_tabela[13]         = "Placa Veiculo";
        lab_tabela[14]         = "Uf_placa";
        lab_tabela[15]         = "Pedido Cliente";
        lab_tabela[16]         = "Numero NFe";
        lab_tabela[17]         = "Serie NFe";
        lab_tabela[18]         = "Modelonfe";
        lab_tabela[19]         = "Data Emissao";
        lab_tabela[20]         = "Chave NFe";
        lab_tabela[21]         = "Icms_bc";
        lab_tabela[22]         = "Icms_vlr";
        lab_tabela[23]         = "Ipi_bc";
        lab_tabela[24]         = "Ipi_vlr";
        lab_tabela[25]         = "Pis_bc";
        lab_tabela[26]         = "Pis_vlr";
        lab_tabela[27]         = "Cofins_bc";
        lab_tabela[28]         = "Cofins_vlr";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[1]          = tf_cod_cliente;
        tab_cb_st[1]          = st_cod_cliente;
        tab_cb_rs[1]          = rs_cod_cliente;
        tab_cb_cb[1]          = cb_cod_cliente;
        tab_cb_tf[2]          = tf_cod_forma_pgto;
        tab_cb_st[2]          = st_cod_forma_pgto;
        tab_cb_rs[2]          = rs_cod_forma_pgto;
        tab_cb_cb[2]          = cb_cod_forma_pgto;
        tab_cb_tf[3]          = tf_cod_tipo_doc;
        tab_cb_st[3]          = st_cod_tipo_doc;
        tab_cb_rs[3]          = rs_cod_tipo_doc;
        tab_cb_cb[3]          = cb_cod_tipo_doc;
        tab_cb_tf[4]          = tf_cod_banco;
        tab_cb_st[4]          = st_cod_banco;
        tab_cb_rs[4]          = rs_cod_banco;
        tab_cb_cb[4]          = cb_cod_banco;
        tab_cb_tf[9]          = tf_cod_transportador;
        tab_cb_st[9]          = st_cod_transportador;
        tab_cb_rs[9]          = rs_cod_transportador;
        tab_cb_cb[9]          = cb_cod_transportador;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[1]     = "cliente";
        tab_cb_cpo_assoc[1]   = "codigo";
        tab_cb_cpo_exibe[1]   = "razaosocial";
        tab_cb_tab_box[2]     = "forma_pgto";
        tab_cb_cpo_assoc[2]   = "codigo";
        tab_cb_cpo_exibe[2]   = "descricao";
        tab_cb_tab_box[3]     = "tipo_doc";
        tab_cb_cpo_assoc[3]   = "codigo";
        tab_cb_cpo_exibe[3]   = "descricao";
        tab_cb_tab_box[4]     = "banco";
        tab_cb_cpo_assoc[4]   = "codigo";
        tab_cb_cpo_exibe[4]   = "descricao";
        tab_cb_tab_box[9]     = "cliente";
        tab_cb_cpo_assoc[9]   = "codigo";
        tab_cb_cpo_exibe[9]   = "razaosocial";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        try
        {
              //conexao conn = new conexao();
              //connection = conn.conecta("", "");
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_cod_cliente  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_cod_forma_pgto  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_cod_tipo_doc  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_cod_banco  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_cod_transportador  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              stat_prazo  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              st_dup  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
              preenche_cb_auxiliar(st_cod_cliente, rs_cod_cliente, cb_cod_cliente, 1);
              preenche_cb_auxiliar(st_cod_forma_pgto, rs_cod_forma_pgto, cb_cod_forma_pgto, 2);
              preenche_cb_auxiliar(st_cod_tipo_doc, rs_cod_tipo_doc, cb_cod_tipo_doc, 3);
              preenche_cb_auxiliar(st_cod_banco, rs_cod_banco, cb_cod_banco, 4);
              preenche_cb_auxiliar(st_cod_transportador, rs_cod_transportador, cb_cod_transportador, 9);

              //mostra_conteudo_nos_campos();
              preencher_jtable("tudo");
              //cb_pesquisa.addActionListener(this);
              stat_aux = true;
              clicado  = true;
              tf_data_digitacao.setEnabled(false);
              tf_valor_produtos.setEnabled(false);
              tf_valor_descontos.setEnabled(false);
              tf_valor_total.setEnabled(false);
              tf_qtde_volume.setEnabled(false);
              tf_peso_volume.setEnabled(false);
              tf_numero_nfe.setEnabled(false);
              tf_modelonfe.setEnabled(false);
              tf_serie_nfe.setEnabled(false);
              tf_data_emissao.setEnabled(false);
              tf_chave_nfe.setEnabled(false);
              tf_icms_bc.setEnabled(false);
              tf_icms_vlr.setEnabled(false);
              tf_ipi_bc.setEnabled(false);
              tf_ipi_vlr.setEnabled(false);
              tf_pis_bc.setEnabled(false);
              tf_pis_vlr.setEnabled(false);
              tf_cofins_bc.setEnabled(false);
              tf_cofins_vlr.setEnabled(false);
              tf_tipo_nfe.setEnabled(false);
              tf_num_nfe_fat.setEnabled(false);
              tf_data_canc.setEnabled(false);
              botao_geraBoleto.setVisible(false);
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
                 try {
                     int numero = Integer.parseInt(tf_pesquisa.getText());
                 } catch(Exception e) {
                     isString = true;
                 }
                 if (isString) {
                    if (cpo_tabela[cb_pesq_lab.getSelectedIndex()].indexOf("data") != -1){
                        sql_query = "select * from nf where "
                                       +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = '"+tf_pesquisa.getText()+"' "
                                       +" and empresa = "+empresa
                                       + " order by pedido desc";
                    } else {
                        sql_query = "select * from nf where "
                                       +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%' "
                                       +" and empresa = "+empresa
                                       + " order by pedido desc";
                    }
                 } else {
                  sql_query = "select * from nf where "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+" "
                                 +" and empresa = "+empresa
                                 + " order by pedido desc";
                 }
             } else {
                  sql_query = "select * from nf where empresa = "+empresa+" order by pedido desc";
             }
System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else {
                     //cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                    cb_pesquisa.addItem(resultset.getString("pedido"));
                 }
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
        if ((qtRegTab*18) >= (740-(posUltLabel+30))) {
//JOptionPane.showMessageDialog(null,"Critica 1 ");
            jScrollPane           .setBounds(5, posUltLabel+30,990, 740-(posUltLabel+30));
        }  else if ((qtRegTab * 18) > 150) {
//JOptionPane.showMessageDialog(null,"Critica 2 ");
            jScrollPane           .setBounds(5, 740-(qtRegTab*18),990, (qtRegTab*18));
        }  else {
//JOptionPane.showMessageDialog(null,"Critica 3 ");
            jScrollPane           .setBounds(5, posUltLabel+30, 1190, 250);
        }
        repaint();
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(33);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(6);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(39);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from nf");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar nf: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                boolean isString = false;
                 try {
                     int numero = Integer.parseInt(tf_pesquisa.getText());
                 } catch(Exception e) {
                     isString = true;
                 }
                 if (isString) {
                    if (cpo_tabela[cb_pesq_lab.getSelectedIndex()].indexOf("data") != -1){
                        sqlquery = "select * from nf where "
                                   +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = '"+tf_pesquisa.getText()+"'"
                                   +" and empresa = "+empresa
                                   + " order by pedido desc";
                    } else {
                        sqlquery = "select * from nf where "
                                   +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                   +" and empresa = "+empresa
                                   + " order by pedido desc";
                    }  
                 } else {
                    sqlquery = "select * from nf where "
                               +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+" "
                               +" and empresa = "+empresa
                               + " order by pedido desc";
                     }
            } else {
                sqlquery = "select * from nf where empresa = "+empresa+" order by pedido desc";
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("pedido"),
                resultset.getString("cod_cliente"),
                resultset.getString("cod_forma_pgto"),
                resultset.getString("cod_tipo_doc"),
                resultset.getString("data_digitacao"),
                resultset.getString("valor_produtos"),
                resultset.getString("valor_descontos"),
                                            }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: nf: "+qtreg);
            sqlquery = "select * from pardigital where empresa = "+empresa;
            resultset = statement.executeQuery(sqlquery);
            while (resultset.next()) {
                wserie = resultset.getString("serie55");
            }

            jTable1.setEditingRow(jTable1.getRowCount());
            sqlquery = "select * from nf where empresa = "+empresa+" order by pedido";
            resultset = statement.executeQuery(sqlquery);
            resultset.last();
        }
        catch (SQLException erro){
            JOptionPane.showMessageDialog(null,"Erro ao listar no JTable "+erro);
        }
    }
    @SuppressWarnings("UseSpecificCatch")
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
        //metodo para mostrar para o usuario
    @SuppressWarnings("UseSpecificCatch")
        public static String formatDateUser(String data){
            if (data.equals("  /  /    ")) return "";
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d1 = null;
            try {
                d1 = f.parse(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            data = format.format(d1);

            return data;

        }

    public void mostra_conteudo_nos_campos()
    {
           clicado = false;
           try
           {
               String data;
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 1 . . .");
               data = resultset.getString("data_digitacao");
               if (data == null) data = "  /  /    ";
               tf_data_digitacao     .setText(formatDateUser(data));
               data = resultset.getString("data_emissao");
               if (data == null) data = "  /  /    ";
               tf_data_emissao     .setText(formatDateUser(data));
               cod_forma_old = Integer.parseInt(resultset.getString("cod_forma_pgto"));
               tf_pedido             .setText(resultset.getString("pedido"));
               tf_cod_cliente        .setText(resultset.getString("cod_cliente"));
               if (tf_cod_cliente.getText() == null) tf_cod_cliente.setText("0");
               tf_cod_forma_pgto     .setText(resultset.getString("cod_forma_pgto"));
               tf_cod_tipo_doc       .setText(resultset.getString("cod_tipo_doc"));
               tf_cod_banco          .setText(resultset.getString("cod_banco"));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2 . . .");
               //tf_data_digitacao     .setText(resultset.getString("data_digitacao"));
               tf_valor_produtos     .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("valor_produtos")), 2, 0));
               tf_valor_descontos    .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("valor_descontos")), 2, 0));
               tf_valor_total        .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("valor_total")), 2, 0));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.1 . . .");
               tf_cod_transportador  .setText(resultset.getString("cod_transportador"));
               tf_dados_adicionais   .setText(resultset.getString("dados_adicionais"));
               tf_qtde_volume        .setText(resultset.getString("qtde_volume"));
               tf_peso_volume        .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("peso_volume")), 3, 0));
               tf_placa_veiculo      .setText(resultset.getString("placa_veiculo"));
               tf_uf_placa           .setText(resultset.getString("uf_placa"));
               tf_pedido_cliente     .setText(resultset.getString("pedido_cliente"));
               tf_numero_nfe         .setText(resultset.getString("numero_nfe"));
               tf_serie_nfe          .setText(resultset.getString("serie_nfe"));
               tf_modelonfe          .setText(resultset.getString("modelonfe"));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.2 . . .");
               //tf_data_emissao       .setText(resultset.getString("data_emissao"));
//System.out.println("icms_bc: "+resultset.getString("icms_bc"));
               tf_chave_nfe          .setText(resultset.getString("chave_nfe"));
               tf_icms_bc            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_bc")), 2, 0));
               tf_icms_vlr           .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("icms_vlr")), 2, 0));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.2.1 . . .");
//               tf_ipi_bc             .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("ipi_bc")), 2, 0));
//               tf_ipi_vlr            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("ipi_vlr")), 2, 0));
               tf_ipi_bc             .setText(""+Biblioteca.arredondar(0.00, 2, 0));
               tf_ipi_vlr            .setText(""+Biblioteca.arredondar(0.00, 2, 0));
               tf_pis_bc             .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("pis_bc")), 2, 0));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.2.2 . . .");
               tf_pis_vlr            .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("pis_vlr")), 2, 0));
               tf_cofins_bc          .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("cofins_bc")), 2, 0));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.2.3 . . .");
               tf_cofins_vlr         .setText(""+Biblioteca.arredondar(Double.parseDouble(resultset.getString("cofins_vlr")), 2, 0));
               tf_num_nfe_fat        .setText(resultset.getString("num_nfe_fat"));
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 2.3 . . .");
               data = resultset.getString("data_cancelamento");
               if (data == null) data = "  /  /    ";
               tf_data_canc          .setText(formatDateUser(data));
               fin_nfe               = resultset.getString("fin_nfe");
               tf_num_nfe_fat        .setBackground(Color.white);
               tf_data_canc          .setBackground(Color.white);
               tf_tipo_nfe            .setBackground(Color.white);
               numNFe_old = tf_numero_nfe.getText();
               if (Integer.parseInt(tf_numero_nfe.getText()) > 0) {
                    tf_numero_nfe.setEnabled(true);
               }
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 3 . . .");
               if (fin_nfe.equals("1")) {
                   tf_tipo_nfe.setText("Normal");
               } else if (fin_nfe.equals("4")) {
                   tf_tipo_nfe.setText("Devolucao");
                   tf_tipo_nfe              .setBackground(Color.yellow);
                   tf_num_nfe_fat          .setBackground(Color.yellow);
               } else {
                   tf_tipo_nfe.setText("* Desconhecido *");
                   tf_tipo_nfe              .setBackground(Color.red);
               }
               if (tf_data_canc.getText() != null && tf_data_canc.getText().length() > 2 ) {
                   tf_data_canc              .setBackground(Color.yellow);
               }
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 4 . . .");
               posiciona_combo_e_jtable();
//System.out.println("nf.mostra_conteudo_nos_campos() - Passou 5 . . .");
               navega =0;

               pedido = tf_pedido.getText();
           }
           catch(Exception erro_sql)
           {
               if (navega == 1) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
               else if (navega == 2) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel avancar pois voce ja esta no ultimo registro da tabela");
               else {
                   JOptionPane.showMessageDialog(null,"Nenhum Registro foi Encontrado! Erro: "+erro_sql);
                   novo_registro();
               }
           }
           clicado = true;
           try {
                wcodcliente = Integer.parseInt(tf_cod_cliente.getText());
           } catch(Exception e) {
               wcodcliente = 0;
           }
//Desabilita campo chave para alteracao/exclusao 
           tf_pedido.setEditable(false);
           botao_gravar.setEnabled(false);
           if (fin_nfe.equals("1")) {  //  habilita botoes de atualizacao somente p/fin_nfe NORMAL
               botao_alterar.setEnabled(true);
               botao_excluir.setEnabled(true);
               botao_geraXML.setEnabled(true);
            }
           if (emiteBoleto.equals("S")) {
               botao_geraBoleto.setVisible(true);
           } else {
               botao_geraBoleto.setVisible(false);
           }
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
            else if (acao.getSource() == botao_gravar || acao.getSource() == botao_salvar){
                if (Integer.parseInt(tf_numero_nfe.getText()) > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    if (check_textField()) {
                        if (gravar()) {
                            JOptionPane.showMessageDialog(null,"Informe agora os Produtos deste Pedido!");
                            new br.com.videoaulasneri.adelcio.fatura.Nf_produtos(empresa, connection, pedido, tf_numero_nfe.getText(), UFEmit, pegaUfDest()).show();
                            //JOptionPane.showMessageDialog(null, "Voltou da Tela de Manutenção de Produtos . . .");
                            try {
                                String sql_query = "select * from nf where "
                                            +" pedido = "+pedido+"  and "
                                            +" empresa = "+empresa;
                                System.out.println("nf - Comando sqlQuery : "+sql_query);
                                resultset = statement.executeQuery(sql_query);
                                int qtreg = 0;
                                while(resultset.next()){
                                    mostra_conteudo_nos_campos();
                                    break;
                                }
                            } catch(Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar o registro deste pedido: "+pedido);
                            }
                        }
                    }
                }
            }
            else if (acao.getSource() == botao_excluir)
                if (Integer.parseInt(tf_numero_nfe.getText()) > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser excluido!");
                } else {
                    excluir();
                }
            else if (acao.getSource() == botao_alterar){
                if (Integer.parseInt(tf_numero_nfe.getText()) > 0) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode ser alterado!");
                } else {
                    if (check_textField()) {
                        alterar();
                    }
//JOptionPane.showMessageDialog(null,"pedido(2): "+pedido);
                }
            }
            else if (acao.getSource() == botao_prod) {
                if (pedido != null && !pedido.equals("") && tf_cod_cliente.getText() != null && !tf_cod_cliente.getText().equals("")) {
                    new br.com.videoaulasneri.adelcio.fatura.Nf_produtos(empresa, connection, pedido, tf_numero_nfe.getText(), UFEmit, pegaUfDest()).show();
                } else {
                    JOptionPane.showMessageDialog(null,"Para acessar os produtos e necessário selecionar um pedido existente\n ou gravar um pedido novo! \nSe voce digitou um pedido agora, clique em Salvar e depois em Produtos!");
                }
            }
             else if (acao.getSource() == botao_prazo) {
                if (pedido != null && !pedido.equals("")) {
                    new br.com.videoaulasneri.adelcio.fatura.Nf_prazo(empresa, connection, pedido).show();
                } else {
                    JOptionPane.showMessageDialog(null,"Para acessar os prazos e necessário selecionar um pedido existente\n ou gravar um pedido novo! ");
                }
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
            else if (acao.getSource() == botao_imprimir) {
                imprimir();
            }
            else if (acao.getSource() == botao_geraBoleto) {
                if (tf_numero_nfe.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null,"Pedido sem NFe emitida não pode gerar Boleto!");
                } else if (tf_cod_cliente.getText() == null || tf_cod_cliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Para gerar Boleto, escolha um dos Pedidos abaixo!");
                } else {
                    verificarGerarBoleto();
                }
            }
            else if (acao.getSource() == botao_conscad) {
                if (!tf_cod_cliente.getText().equals("")) {
                    ConsultarCadastro conscad = new ConsultarCadastro(
                            getFile_keystore(), 
                            getSenha_keystore(), 
                            getTipoAmbiente(), 
                            Biblioteca.pegaEstado(UFEmit)
                        );
                    String resultado = null;
                    TRetConsCad retorno;
                    wcodcliente = Integer.parseInt(tf_cod_cliente.getText());
                    pegaUfDest();
                    if (this.getCli_cnpj() != null) {
                        if (this.getCli_pessoa().equals("J")) {
                            retorno = conscad.consultar(this.getCli_uf(), this.getCli_cnpj(), null);
                        } else {
                            retorno = conscad.consultar(this.getCli_uf(), null, this.getCli_cnpj());
                        }
        		if(retorno.getInfCons().getCStat().equals("111")){
                            if (this.getCli_pessoa().equals("J")) {
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
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum cliente foi encontrado para consultar!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Informe um cliente para consultar cliente na receita estadual!");
                }
            }
            else if (acao.getSource() == botao_geraXML) {
                //JOptionPane.showMessageDialog(null,"Pressionou o Botão Gera XML");
                if (!tf_numero_nfe.getText().equals("0") && !tf_numero_nfe.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Pedido com NFe emitida não pode gerar XML!");
                } else if (tf_cod_cliente.getText() == null || tf_cod_cliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"Para gerar XML, escolha um dos Pedidos abaixo!");
                } else {
                    verificarGerarXML();
                }
            }
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
            else if (acao.getSource() == cb_cod_cliente){
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_cliente, rs_cod_cliente, cb_cod_cliente, tf_cod_cliente, 1);
            }
            else if (acao.getSource() == cb_cod_forma_pgto){
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_forma_pgto, rs_cod_forma_pgto, cb_cod_forma_pgto, tf_cod_forma_pgto, 2);
            }
            else if (acao.getSource() == cb_cod_tipo_doc){
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_tipo_doc, rs_cod_tipo_doc, cb_cod_tipo_doc, tf_cod_tipo_doc, 3);
            }
            else if (acao.getSource() == cb_cod_banco){
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_banco, rs_cod_banco, cb_cod_banco, tf_cod_banco, 4);
            }
            else if (acao.getSource() == cb_cod_transportador){
               if ( stat_aux)
                   trata_cb_auxiliar(st_cod_transportador, rs_cod_transportador, cb_cod_transportador, tf_cod_transportador, 9);
            }
      }
    private void verificarGerarBoleto() {
           UIManager.put("OptionPane.yesButtonText", "Gerar Boleto agora");
           UIManager.put("OptionPane.noButtonText", "Gerar Boleto depois");
           int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Geração do Boleto Bancário", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
           if ( escolha == JOptionPane.YES_OPTION){
JOptionPane.showMessageDialog(null, "Clicou em Gerar Boleto . . .");
/*
                geraXML_fatura gxml = new geraXML_fatura(
                        nfem2.getTipoAmbiente(),
                        tf_data_digitacao.getText(),  //  nfem2.getStc_data(),
                        nfem2.getStc_camLerAss(),
                        this.connection,
                        this.empresa, nfem2.getStc_drive(),
                        nfem2.getStc_anomesdia(),
                        nfem2.getStc_tipoEmis(),
                        nfem2.isStc_exibirDsp(),
                        tf_pedido.getText(),
                        pegaUfEmit(),
                        pegaUfDest()
                        );
                gxml.trataTexto("Gerando texto");
 *
 */
           } else {
                JOptionPane.showMessageDialog(null, "O Boleto Bancário pode ser gerado futuramente editando-se o Registro desejado e clicando no botão: Gerar Boleto!");
           }

           UIManager.put("OptionPane.yesButtonText", "Sim");
           UIManager.put("OptionPane.noButtonText", "Não");

    }

    private void gerarBoleto() {
    }
      private String pegaUfDest() {
          String retorno = "PR";
          //if (wcodcliente > 0) {
            String sql = "select c.cnpj as cli_cnpj, c.pessoa as cli_pessoa, cid.codcidade as cid_codcidade, cid.uf as cid_uf from cliente as c join ibge as cid on c.codcidade = cid.codigo where c.codigo = "+wcodcliente;
            try {
                resultset = statement.executeQuery(sql);
                resultset.last();
                this.setCli_pessoa(resultset.getString("cli_pessoa"));
                this.setCli_cnpj(resultset.getString("cli_cnpj"));
                this.setCli_uf(resultset.getString("cid_uf"));
                retorno = resultset.getString("cid_codcidade").substring(0, 2);
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
        try
        {
            String sql_query = "select * from nf where empresa = "+empresa+" order by pedido";
            resultset = statement.executeQuery(sql_query);
            resultset.last();
            String codigo = resultset.getString("pedido");
            ult_cod = Integer.parseInt(codigo) + 1;
        }
        catch(SQLException erro)
        {
             //JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
            ult_cod = 1;
        }
        tf_pedido             .setText(""+ult_cod);
        tf_cod_cliente        .setText("");
        tf_cod_forma_pgto     .setText("");
        tf_cod_tipo_doc       .setText("");
        tf_cod_banco          .setText("");
        String data = "  /  /    ";
        tf_data_emissao       .setText(formatDateUser(data));
        tf_data_canc          .setText(formatDateUser(data));
        
        //java.util.Date dataHoje = new java.util.Date();
        //data = dataHoje.toString();
        
        tf_data_digitacao     .setText(formatDateUser(data));
        tf_valor_produtos     .setText("0.00");
        tf_valor_descontos    .setText("0.00");
        tf_valor_total        .setText("0.00");
        tf_cod_transportador  .setText("");
        tf_dados_adicionais   .setText("");
        tf_qtde_volume        .setText("0");
        tf_peso_volume        .setText("0.000");
        tf_placa_veiculo      .setText("");
        tf_uf_placa           .setText("");
        tf_pedido_cliente     .setText("");
        tf_numero_nfe         .setText("0");
        tf_modelonfe          .setText("55");
        tf_serie_nfe          .setText("");
        tf_num_nfe_fat        .setText("0");
        tf_tipo_nfe            .setText("");
        tf_chave_nfe          .setText("");
        tf_icms_bc            .setText("0.00");
        tf_icms_vlr           .setText("0.00");
        tf_ipi_bc             .setText("0.00");
        tf_ipi_vlr            .setText("0.00");
        tf_pis_bc             .setText("0.00");
        tf_pis_vlr            .setText("0.00");
        tf_cofins_bc          .setText("0.00");
        tf_cofins_vlr         .setText("0.00");
        emiteBoleto = "N";
      posiciona_combo_e_jtable();
      ehnovo = true;
        wcodcliente = 0;
//Habilita campo chave para inclusao 
          tf_pedido.setEditable(false);
//Limpa filtros da inclusao anterior nos comboBox auxiliares 
          if (!tf_psqcod_cliente.getText().equals("")){
             tf_psqcod_cliente.setText("");
             preenche_cb_auxiliar(st_cod_cliente, rs_cod_cliente, cb_cod_cliente, 1);
          }
          if (!tf_psqcod_forma_pgto.getText().equals("")){
             tf_psqcod_forma_pgto.setText("");
             preenche_cb_auxiliar(st_cod_forma_pgto, rs_cod_forma_pgto, cb_cod_forma_pgto, 2);
          }
          if (!tf_psqcod_tipo_doc.getText().equals("")){
             tf_psqcod_tipo_doc.setText("");
             preenche_cb_auxiliar(st_cod_tipo_doc, rs_cod_tipo_doc, cb_cod_tipo_doc, 3);
          }
          if (!tf_psqcod_banco.getText().equals("")){
             tf_psqcod_banco.setText("");
             preenche_cb_auxiliar(st_cod_banco, rs_cod_banco, cb_cod_banco, 4);
          }
          if (!tf_psqcod_transportador.getText().equals("")){
             tf_psqcod_transportador.setText("");
             preenche_cb_auxiliar(st_cod_transportador, rs_cod_transportador, cb_cod_transportador, 9);
          }
          botao_gravar.setEnabled(true);
          botao_alterar.setEnabled(false);
          botao_excluir.setEnabled(false);
          //botao_geraXML.setEnabled(false);
          botao_geraBoleto.setVisible(false);
          fin_nfe = "1";
        tf_tipo_nfe              .setBackground(Color.white);
        tf_num_nfe_fat          .setBackground(Color.white);
        tf_data_canc            .setBackground(Color.white);

     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        String txtMsg = "";
        if ( tf_pedido.getText() == null ) tf_pedido.setText("");
        if ( tf_cod_cliente.getText() == null ) tf_cod_cliente.setText("");
        if (tf_cod_cliente.getText().equals("")){
           txtMsg += "Um Codigo de Cliente válido precisa ser informado!";
        }
        if ( tf_cod_forma_pgto.getText() == null ) tf_cod_forma_pgto.setText("");
        if (tf_cod_forma_pgto.getText().equals("")){
           //txtMsg +=  "\nUm Codigo de Forma de Pgto válido precisa ser informado!";
            tf_cod_forma_pgto.setText("1");
        }
        if ( tf_cod_tipo_doc.getText() == null || tf_cod_tipo_doc.getText().equals("")) {
            if (tf_cod_forma_pgto.getText().equals("1")) {
                tf_cod_tipo_doc.setText("AV");
            } else {
                tf_cod_tipo_doc.setText("");
            }
        }
        if (tf_cod_tipo_doc.getText().equals("")){
           txtMsg +=  "\nUm Codigo de Tipo de Docto válido precisa ser informado!";
        }
        if ( tf_cod_banco.getText() == null ) tf_cod_banco.setText("");
        if (tf_cod_banco.getText().equals("")){
            if (tf_cod_forma_pgto.getText().equals("1")) {
                tf_cod_banco.setText("1");
            } else {
               txtMsg +=  "\nUm Codigo de Banco válido precisa ser informado!";
            }
        }
/*        
        else if (
                (Integer.parseInt(tf_cod_banco.getText()) == 1) &&
                (!tf_cod_tipo_doc.getText().equals("AV") || Integer.parseInt(tf_cod_forma_pgto.getText()) != 1)
                ){
                txtMsg +=  "\nSe Banco é [A Vista], Tipo de Documento e Pgto também tem que ser [A Vista]"
                        +"\nCod.Banco: "+tf_cod_banco.getText()
                        +"\nCod.Tipo Doc: "+tf_cod_tipo_doc.getText()
                        +"\nCod.Forma Pgto: "+tf_cod_forma_pgto.getText()
                        ;
        } else if (!tf_cod_banco.getText().equals("0") &&
                tf_cod_tipo_doc.getText().equals("0") || tf_cod_forma_pgto.getText().equals("0")
                ){
                txtMsg +=  "\nSe Banco NÃO é [A Vista], Tipo de Documento e Pgto também NÃO podem que ser [A Vista]";
        }
*/
        
        if (emiteBoleto.equals("S")) {
           botao_geraBoleto.setVisible(true);
        } else {
           botao_geraBoleto.setVisible(false);
        }
        if ( tf_data_digitacao.getText() == null ) tf_data_digitacao.setText("");
        if ( tf_valor_produtos.getText() == null ) tf_valor_produtos.setText("");
        if ( tf_valor_descontos.getText() == null ) tf_valor_descontos.setText("");
        if ( tf_valor_total.getText() == null ) tf_valor_total.setText("");
        if ( tf_cod_transportador.getText() == null ||  tf_cod_transportador.getText().equals(""))
            tf_cod_transportador.setText("0");
        //if (tf_cod_transportador.getText().equals("")){
        //   txtMsg += "\nUm Codigo de Transportador válido precisa ser informado!";
        //}
        try {
            int ctransp = Integer.parseInt(tf_cod_transportador.getText());
        } catch (Exception e) {
            tf_cod_transportador.setText("0");
        }
        if ( tf_dados_adicionais.getText() == null ) tf_dados_adicionais.setText("");
        if ( tf_qtde_volume.getText() == null ) tf_qtde_volume.setText("");
        if ( tf_peso_volume.getText() == null ) tf_peso_volume.setText("");
        if ( tf_placa_veiculo.getText() == null ) tf_placa_veiculo.setText("");
        if ( tf_uf_placa.getText() == null ) tf_uf_placa.setText("");
        if ( tf_pedido_cliente.getText() == null ) tf_pedido_cliente.setText("");
        if ( tf_numero_nfe.getText() == null || tf_numero_nfe.getText().equals("") ) tf_numero_nfe.setText("0");
        //if ( tf_serie_nfe.getText() == null || tf_serie_nfe.getText().equals("")) tf_serie_nfe.setText("1  ");
        if ( tf_serie_nfe.getText() == null) {
            tf_serie_nfe.setText("");
        }
        if ( tf_modelonfe.getText() == null) {
            tf_modelonfe.setText("55");
        }
        if (!numNFe_old.equals(tf_numero_nfe.getText())) {
            //JOptionPane.showMessageDialog(null, "CUIDADO! O Numero da NFe foi alterado de [ "+numNFe_old+" ] para [ "+tf_numero_nfe.getText()+" ] !!!");
        }
        if ( tf_data_emissao.getText() == null ) tf_data_emissao.setText("");
        if ( tf_chave_nfe.getText() == null ) tf_chave_nfe.setText("");
        if ( tf_icms_bc.getText() == null ) tf_icms_bc.setText("0");
        if ( tf_icms_vlr.getText() == null ) tf_icms_vlr.setText("0");
        if ( tf_ipi_bc.getText() == null ) tf_ipi_bc.setText("0");
        if ( tf_ipi_vlr.getText() == null ) tf_ipi_vlr.setText("0");
        if ( tf_pis_bc.getText() == null ) tf_pis_bc.setText("0");
        if ( tf_pis_vlr.getText() == null ) tf_pis_vlr.setText("0");
        if ( tf_cofins_bc.getText() == null ) tf_cofins_bc.setText("0");
        if ( tf_cofins_vlr.getText() == null ) tf_cofins_vlr.setText("0");
        if (txtMsg.length() > 0) {
           JOptionPane.showMessageDialog(null, "Campos inválidos: "+txtMsg);
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
    public boolean gravar()
    {
        boolean retorno = false;
        String sql_insert = "";
        if (ehnovo)
            {
            try
            {
                java.util.Date datadigi = null, dataemis = null;  //new Date();
                //String sdatamvto = "";
                try {
                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    if (tf_data_digitacao.getText() != null && tf_data_digitacao.getText().length() == 10) {
                        datadigi = f.parse(tf_data_digitacao.getText());
                    } else {
                        datadigi = new java.util.Date();
                    }
/*
                    if (tf_data_emissao.getText() != null && tf_data_digitacao.getText().length() == 10) {
                        dataemis = f.parse(tf_data_emissao.getText());
                    }
 * 
 */
                    //SimpleDateFormat f_inv = new SimpleDateFormat("yyyy-MM-dd");
                    //sdatamvto = f_inv.format(datadigi);
                } catch (Exception ex) {
                    //Logger.getLogger(mvpeso.class.getName()).log(Level.SEVERE, null, ex);
                }
                tf_placa_veiculo.setText(tf_placa_veiculo.getText().toUpperCase());
                tf_uf_placa.setText(tf_uf_placa.getText().toUpperCase());
                tf_valor_produtos.setText(tf_valor_produtos.getText().replaceAll(",", "."));
                tf_valor_descontos.setText(tf_valor_descontos.getText().replaceAll(",", "."));
                tf_valor_total.setText(tf_valor_total.getText().replaceAll(",", "."));
                tf_icms_bc.setText(tf_icms_bc.getText().replaceAll(",", "."));
                tf_ipi_bc.setText(tf_ipi_bc.getText().replaceAll(",", "."));
                tf_pis_bc.setText(tf_pis_bc.getText().replaceAll(",", "."));
                tf_cofins_bc.setText(tf_cofins_bc.getText().replaceAll(",", "."));
                tf_icms_vlr.setText(tf_icms_vlr.getText().replaceAll(",", "."));
                tf_ipi_vlr.setText(tf_ipi_vlr.getText().replaceAll(",", "."));
                tf_pis_vlr.setText(tf_pis_vlr.getText().replaceAll(",", "."));
                tf_cofins_vlr.setText(tf_cofins_vlr.getText().replaceAll(",", "."));
                tf_serie_nfe.setText(wserie);
                pedido = tf_pedido.getText();
                sql_insert = "insert into nf ( "
                        +"empresa, "
                         +"pedido, "
                         +"cod_cliente, "
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
                         +"pedido_cliente, "
                         +"numero_nfe, "
                         +"serie_nfe, "
                         +"modelonfe, ";
                 sql_insert += "chave_nfe, "+
                        "icms_bc, "+"icms_vlr, "+"ipi_bc, "+"ipi_vlr, "+"pis_bc, "+"pis_vlr, "
                        +"cofins_bc, "+"cofins_vlr, num_NFe_fat, fin_NFe "
                        +") values ("
                        +empresa+", "
                        +tf_pedido.getText() + ", "
                        +tf_cod_cliente.getText() + ", "
                        +tf_cod_forma_pgto.getText() + ", "
                        +"'"+tf_cod_tipo_doc.getText() + "', "
                        +tf_cod_banco.getText() + ", "
                        +"'"+datadigi + "', "
                        +tf_valor_produtos.getText() + ", "
                        +tf_valor_descontos.getText() + ", "
                        +tf_valor_total.getText() + ", "
                        +tf_cod_transportador.getText() + ", "
                        +"'"+tf_dados_adicionais.getText() + "', "
                        +tf_qtde_volume.getText() + ", "
                        +tf_peso_volume.getText() + ", "
                        +"'"+tf_placa_veiculo.getText() + "', "
                        +"'"+tf_uf_placa.getText() + "', "
                        +"'"+tf_pedido_cliente.getText() + "', "
                        +tf_numero_nfe.getText() + ", "
                        +"'"+tf_serie_nfe.getText() + "', "
                        +"'"+tf_modelonfe.getText() + "', ";
                  sql_insert += "'"+tf_chave_nfe.getText() + "', "
                        +tf_icms_bc.getText() + ", "
                        +tf_icms_vlr.getText() + ", "
                        +tf_ipi_bc.getText() + ", "
                        +tf_ipi_vlr.getText() + ", "
                        +tf_pis_bc.getText() + ", "
                        +tf_pis_vlr.getText() + ", "
                        +tf_cofins_bc.getText() + ", "
                        +tf_cofins_vlr.getText() + ", "
                        +num_nfe_fat+ ", "
                        +"'"+fin_nfe+"' "
                        + ")";
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 //verificarGerarXML();
                 wcodcliente = Integer.parseInt(tf_cod_cliente.getText());
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from nf where empresa = "+empresa+" order by pedido");
                 resultset.last();
                 //mostra_conteudo_nos_campos();
                 posiciona_combo_e_jtable();
                 clicado = true;
                 //novo_registro();
                 retorno = true;
            }
            catch(SQLException erro)
            {
                 //erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro ao tentar incluir o registro!\n Erro: "+erro);
    JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
                 //JOptionPane.showMessageDialog(null, "Registro já existe na Base de Dados!");
            }
         }else
         {
             JOptionPane.showMessageDialog(null,"Registro existente nao pode ser criado!\nEscolha o Botao para Regravar o Registro");
         }
        return retorno;
     }

    private void verificarGerarXML() {
        System.out.println("nf - tpEmis: "+nfem2.getStc_tipoEmis());
        if (!nfem2.getStc_tipoEmis().equals("0")) {
           UIManager.put("OptionPane.yesButtonText", "Gerar XML agora");
           UIManager.put("OptionPane.noButtonText", "Gerar XML depois");
           int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Geração do XML para Emissão da NFe", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
           if ( escolha == JOptionPane.YES_OPTION){
/*
                JOptionPane.showMessageDialog(null,
                        "Pressionou o Botão Gerar XML com exibir Display = "+nfem2.isStc_exibirDsp()
                        +" - tipoAmb = "+nfem2.getTipoAmbiente()
                        +" - data Mvto = "+tf_data_digitacao.getText()  // nfem2.getStc_data()
                        +" - Stc_camLerAss = "+nfem2.getStc_camLerAss()
                        //+" - connection = "+this.connection
                        +" - Empresa = "+this.empresa
                        +" - stc_drive = "+nfem2.getStc_drive()
                        +" - Stc_anomesdia = "+nfem2.getStc_anomesdia()
                        +" - Stc_tipoEmis = "+nfem2.getStc_tipoEmis()
                        +" - Stc_exibirDsp = "+nfem2.isStc_exibirDsp()
                        +" - Pedido = "+tf_pedido.getText()
                        );
 * 
 */
                GeraXML_nfe gxml = new GeraXML_nfe(
                        getTipoNF(),
                        nfem2.getTipoAmbiente(),
                        tf_data_digitacao.getText(),  //  nfem2.getStc_data(),
                        nfem2.getStc_camLerAss(),
                        this.connection,
                        this.empresa, nfem2.getStc_drive(),
                        nfem2.getStc_anomesdia(),
                        nfem2.getStc_tipoEmis(),
                        nfem2.isStc_exibirDsp(),
                        tf_pedido.getText(),
                        UFEmit,
                        pegaUfDest(),
                        this.fuso_horario,
                        contatoCupom
                        );
//JOptionPane.showMessageDialog(null,"nf().verificarGerarXML() - Vai chamar metodo: gxml.trataTexto(null) . . .");
                String retorno = gxml.trataTexto("Gerando texto");
                if (retorno.equals("erro")) {
                    JOptionPane.showMessageDialog(null, "Ocorreu algum erro durante a Geração do arquivo XML para transmissão/Autorização do Documento!\n\nO arquivo XML pode ser gerado futuramente editando-se o Registro desejado e clicando no botão: Gerar XML!");
                }
//JOptionPane.showMessageDialog(null,"nf().verificarGerarXML() - Voltou do metodo: gxml.trataTexto(null) . . .");
           } else {
                JOptionPane.showMessageDialog(null, "O arquivo XML pode ser gerado futuramente editando-se o Registro desejado e clicando no botão: Gerar XML!");
           }
           
           UIManager.put("OptionPane.yesButtonText", "Sim");
           UIManager.put("OptionPane.noButtonText", "Não");
        } else {
           JOptionPane.showMessageDialog(null, "Você está Off Line! Para a Geração do arquivo XML é necessário escolher a Opção Normal no Painel Principal!\n\nO arquivo XML pode ser gerado futuramente editando-se o Registro desejado e clicando no botão: Gerar XML!");
        }
    }

    //metodo para regravar no banco registro
    public void alterar()
    {
        if (!ehnovo)
        {
             try
            {
                java.util.Date datadigi = null, dataemis = null;  //new Date();
                String sdatamvto = "";
                try {
                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    datadigi = f.parse(tf_data_digitacao.getText());
                    dataemis = f.parse(tf_data_emissao.getText());
                    //SimpleDateFormat f_inv = new SimpleDateFormat("yyyy-MM-dd");
                    //sdatamvto = f_inv.format(datadigi);
                } catch (Exception ex) {
                    //Logger.getLogger(mvpeso.class.getName()).log(Level.SEVERE, null, ex);
                }
                tf_uf_placa.setText(tf_uf_placa.getText().toUpperCase());
                tf_valor_produtos.setText(tf_valor_produtos.getText().replaceAll(",", "."));
                tf_valor_descontos.setText(tf_valor_descontos.getText().replaceAll(",", "."));
                tf_valor_total.setText(tf_valor_total.getText().replaceAll(",", "."));
//JOptionPane.showMessageDialog(null,"tf_icms_bc(1): "+tf_icms_bc.getText());
                tf_icms_bc.setText(tf_icms_bc.getText().replaceAll(",", "."));
//JOptionPane.showMessageDialog(null,"tf_icms_bc(2): "+tf_icms_bc.getText());
                tf_ipi_bc.setText(tf_ipi_bc.getText().replaceAll(",", "."));
                tf_pis_bc.setText(tf_pis_bc.getText().replaceAll(",", "."));
                tf_cofins_bc.setText(tf_cofins_bc.getText().replaceAll(",", "."));
                tf_icms_vlr.setText(tf_icms_vlr.getText().replaceAll(",", "."));
                tf_ipi_vlr.setText(tf_ipi_vlr.getText().replaceAll(",", "."));
                tf_pis_vlr.setText(tf_pis_vlr.getText().replaceAll(",", "."));
                tf_cofins_vlr.setText(tf_cofins_vlr.getText().replaceAll(",", "."));
                pedido = tf_pedido.getText();
//JOptionPane.showMessageDialog(null,"tf_pedido: "+tf_pedido.getText()+" - pedido(1): "+pedido);
                String wchavenfe = tf_chave_nfe.getText();
                boolean continuar = true;
                if (!numNFe_old.equals(tf_numero_nfe.getText())) {
                    String emp4dig = ""+empresa;
                    int tamtxt = emp4dig.length();
                    for (int i=0;i<(4-tamtxt);i++){
                        emp4dig = "0"+emp4dig;
                    }
                    String anomesdia;
                    java.util.Date date = null;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date = format.parse(tf_data_emissao.getText());
                    } catch (ParseException ex) {
                        Logger.getLogger(Nf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String formato     = "yyyyMMdd";
                    SimpleDateFormat   formatter = new SimpleDateFormat(formato);
                    anomesdia = formatter.format(date);
                    String fsep = System.getProperty("file.separator");
                    //String arqXML = System.getProperty("user.dir")+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"nfeprot"+fsep+"bkp"+fsep+anomesdia+tf_chave_nfe.getText().replaceAll(" ","")+"_prot.xml";
                    String arqXML = getStc_drive()+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"nfeprot"+fsep+"bkp"+fsep+anomesdia+fsep+tf_chave_nfe.getText().replaceAll(" ","")+"_prot.xml";
                    System.out.println("Arquivo de protocolo: "+arqXML);
                    String statProt = Biblioteca.extrair_TAG(arqXML, "infProt", "T", "cStat", 1);
                    if (statProt != null) { //  a NFe já foi autorizada e seu numero não pode ser alterado
                        tf_numero_nfe.setText(numNFe_old);
                        JOptionPane.showMessageDialog(null,"CUIDADO! A NFe já foi autorizada e seu número não deve ser alterado!");
                        continuar = false;
                        if (confirmaAltNFe()) {
                            continuar = true;
                        }
                    }
                    if (continuar) {
                        if (Integer.parseInt(tf_numero_nfe.getText()) == 0 ) {
                            wchavenfe = "";
                            tf_numero_nfe.setText("0");
                            JOptionPane.showMessageDialog(null,"O número do pedido foi zerado e o XML pode ser gerado novamente!");
                        }
                    }
                }
                if (continuar) {
                 String sql_alterar = "update nf set "
                        +"cod_cliente = "+ tf_cod_cliente.getText()+", "
                        +"cod_forma_pgto = "+ tf_cod_forma_pgto.getText()+", "
                        +"cod_tipo_doc = '"+ tf_cod_tipo_doc.getText()+"', "
                        +"cod_banco = "+ tf_cod_banco.getText()+", "
                        +"data_digitacao = '"+ datadigi+"', "
                        +"valor_produtos = "+ tf_valor_produtos.getText()+", "
                        +"valor_descontos = "+ tf_valor_descontos.getText()+", "
                        +"valor_total = "+ tf_valor_total.getText()+", "
                        +"cod_transportador = "+ tf_cod_transportador.getText()+", "
                        +"dados_adicionais = '"+ tf_dados_adicionais.getText()+"', "
                        +"qtde_volume = "+ tf_qtde_volume.getText()+", "
                        +"peso_volume = "+ tf_peso_volume.getText()+", "
                        +"placa_veiculo = '"+ tf_placa_veiculo.getText()+"', "
                        +"uf_placa = '"+ tf_uf_placa.getText()+"', "
                        +"chave_nfe = '"+ wchavenfe+"', "
                        +"numero_nfe = "+ tf_numero_nfe.getText()+", "
                        +"pedido_cliente = '"+ tf_pedido_cliente.getText()+"' "
                        +" where pedido = " + tf_pedido.getText()
                        ;
/*
                        //+"numero_nfe = "+ tf_numero_nfe.getText()+", "
                        //+"serie_nfe = '"+ tf_serie_nfe.getText()+"', ";
                 if (dataemis != null && !dataemis.equals("")) {
                    sql_alterar += "data_emissao = '" + dataemis+"', ";
                }
                sql_alterar += " "
                        //"chave_nfe = '" + tf_chave_nfe.getText() + "', "
                        +"icms_bc = "+ tf_icms_bc.getText()+", "
                        +"icms_vlr = "+ tf_icms_vlr.getText()+", "
                        +"ipi_bc = "+ tf_ipi_bc.getText()+", "
                        +"ipi_vlr = "+ tf_ipi_vlr.getText()+", "
                        +"pis_bc = "+ tf_pis_bc.getText()+", "
                        +"pis_vlr = "+ tf_pis_vlr.getText()+", "
                        +"cofins_bc = "+ tf_cofins_bc.getText()+", "
                        +"cofins_vlr = "+ tf_cofins_vlr.getText()+" "
      *
      */
//JOptionPane.showMessageDialog(null,"sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                }
                  clicado = false;
                  resultset  = statement.executeQuery("select * from nf where empresa = "+empresa+" order by pedido");
                  resultset.last();
                  //mostra_conteudo_nos_campos();
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  posiciona_combo_e_jtable();
                  tf_pesquisa.setText("");
                  clicado = true;
                  if (Integer.parseInt(tf_cod_forma_pgto.getText()) != cod_forma_old) {
                      calcular_prazos(Integer.parseInt(tf_cod_forma_pgto.getText()), datadigi, Double.parseDouble(tf_valor_total.getText()));
                  }
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
    
    private boolean confirmaAltNFe() {
        boolean retorno = false;
        int opcao_escolhida = JOptionPane.showConfirmDialog(null,"Confirma?","Confirma a Alteração do Numero da NFe deste Pedido ",JOptionPane.YES_NO_OPTION);
        if (opcao_escolhida == JOptionPane.YES_OPTION) {
            retorno = true;
        }
        return retorno;
    }

         // procedimento para exclusao de registro
     public void excluir()
     {
       try
       {
           String nome = "Excluir nf ref. Pedido: "+tf_pedido.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Confirma a Exclusão deste Pedido e todos os seus Produtos ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM nf_prazo where empresa = "+empresa+" and pedido = " + tf_pedido.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);

               sql_delete = "DELETE FROM nf_produtos  where empresa = "+empresa+" and pedido = " + tf_pedido.getText();
               System.out.println("sql_delete = " + sql_delete);
               conseguiu_excluir = statement.executeUpdate(sql_delete);

               sql_delete = "DELETE FROM nf where empresa = "+empresa+" and pedido = " + tf_pedido.getText();
               System.out.println("sql_delete = " + sql_delete);
               conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from nf where empresa = "+empresa+" order by pedido");
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
        //Impressao impr = new Impressao(empresa, "rel_nf.jasper");
        //impr.imprimeRelJasper();
    }
    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox<String> cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            cb_aux.removeAllItems();
            String varWhere = "";
            if (tab_cb_tab_box[indice].equals("cliente") ) {
                if (indice == 1 ) {
                    varWhere = " where codigo > 0";
                } else if (indice == 9) {  //  eh transportadora
                    varWhere = " where ehtransp = 'S'";
                }
            }
            String sql_aux = "select * from "+tab_cb_tab_box[indice]+varWhere+" order by codigo";
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
                String sql = "select * from "+tab_cb_tab_box[indice]+" order by codigo";
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
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.1 . . ."+indice+" - "+tab_cb_tab_box[indice]+" - "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.2 . . .");
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
                if (tab_cb_tab_box[indice].equals("banco")) {
                    emiteBoleto = rs_aux.getString("emite_boleto");
                    if (emiteBoleto.equals("S")) {
                       botao_geraBoleto.setVisible(true);
                    } else {
                       botao_geraBoleto.setVisible(false);
                    }
                //JOptionPane.showMessageDialog(null, "Valor de emiteBoleto: "+emiteBoleto);
                }
                 break;
            }
            if (cb_aux.getSelectedIndex() == -1){
                if (!tab_cb_tab_box[indice].equals("cliente")) {
                    JOptionPane.showMessageDialog(null, "Registro nao encontrado!");
                    tf_aux.requestFocus();
                }
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
           resultset  = statement.executeQuery("select * from nf where empresa = "+empresa+" order by pedido desc");
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
                cb_pesquisa.setSelectedItem(tf_cod_cliente.getText());
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
        String sql = "select * from nf where Descricao = "+cb_pesquisa.getSelectedItem();
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
                    String sql_query = "select * from nf where "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                +" empresa = "+empresa
                                + " order by pedido desc";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from nf where empresa = "+empresa+" order by pedido desc");
                }
                //resultset = statement.executeQuery("select * from nf");
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
       } else if(e.getSource() == tf_cod_cliente) {
           System.out.println("Teclou enter no campo cod_cliente . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[1], tab_cb_cb[1], tab_cb_tf[1], 1);
               tf_cod_forma_pgto.requestFocus();
           }
       } else if(e.getSource() == tf_cod_forma_pgto) {
           System.out.println("Teclou enter no campo cod_forma_pgto . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2);
               tf_cod_tipo_doc.requestFocus();
           }
       } else if(e.getSource() == tf_cod_tipo_doc) {
           System.out.println("Teclou enter no campo cod_tipo_doc . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3);
               tf_cod_banco.requestFocus();
           }
       } else if(e.getSource() == tf_cod_banco) {
           System.out.println("Teclou enter no campo cod_banco . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3);
               tf_data_digitacao.requestFocus();
           }
       } else if(e.getSource() == tf_cod_transportador) {
           System.out.println("Teclou enter no campo cod_transportador . . .");
           if (e.getKeyCode() == 10){
               //posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[9], tab_cb_cb[9], tab_cb_tf[9], 9);
               tf_dados_adicionais.requestFocus();
           }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == tf_psqcod_cliente){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_cliente, rs_cod_cliente, cb_cod_cliente, tf_psqcod_cliente, rb_inic_psqcod_cliente, rb_meio_psqcod_cliente, 1);
        }
        else 
        if (e.getSource() == tf_psqcod_forma_pgto){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_forma_pgto, rs_cod_forma_pgto, cb_cod_forma_pgto, tf_psqcod_forma_pgto, rb_inic_psqcod_forma_pgto, rb_meio_psqcod_forma_pgto, 2);
        }
        else 
        if (e.getSource() == tf_psqcod_tipo_doc){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_tipo_doc, rs_cod_tipo_doc, cb_cod_tipo_doc, tf_psqcod_tipo_doc, rb_inic_psqcod_tipo_doc, rb_meio_psqcod_tipo_doc, 3);
        }
        else 
        if (e.getSource() == tf_psqcod_banco){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_banco, rs_cod_banco, cb_cod_banco, tf_psqcod_banco, rb_inic_psqcod_banco, rb_meio_psqcod_banco, 4);
        }
        else
        if (e.getSource() == tf_psqcod_transportador){
            System.out.println("Teclou ENTER com cpo preenchido . . .");
            preenche_cb_auxPesq(st_cod_transportador, rs_cod_transportador, cb_cod_transportador, tf_psqcod_transportador, rb_inic_psqcod_transportador, rb_meio_psqcod_transportador, 9);
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
       if (e.getSource() == tf_cod_cliente){
           System.out.println("Perdeu o foco do campo cod_cliente . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[1], tab_cb_cb[1], tab_cb_tf[1], 1))
        tf_cod_forma_pgto.requestFocus();
       }
       if (e.getSource() == tf_cod_forma_pgto){
           System.out.println("Perdeu o foco do campo cod_forma_pgto . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[2], tab_cb_cb[2], tab_cb_tf[2], 2))
        tf_cod_tipo_doc.requestFocus();
       }
       if (e.getSource() == tf_cod_tipo_doc){
           System.out.println("Perdeu o foco do campo cod_tipo_doc . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[3], tab_cb_cb[3], tab_cb_tf[3], 3))
        tf_cod_banco.requestFocus();
       }
       if (e.getSource() == tf_cod_banco){
           System.out.println("Perdeu o foco do campo cod_banco . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[4], tab_cb_cb[4], tab_cb_tf[4], 4))
        tf_data_digitacao.requestFocus();
       }
       if (e.getSource() == tf_cod_transportador){
           System.out.println("Perdeu o foco do campo cod_transportador . . .");
           if(posiciona_combo_geral(tab_cb_st[1], tab_cb_rs[9], tab_cb_cb[9], tab_cb_tf[9], 9))
        tf_dados_adicionais.requestFocus();
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
                if (Biblioteca.verSeEhNumero(tf_Aux.getText())) {
                    ps.setInt(1, Integer.parseInt(tf_Aux.getText()));
                } else {
                    ps.setString(1, tf_Aux.getText());
                }
                rs_aux = ps.executeQuery();
                //emiteBoleto = "N";
                while (rs_aux.next()){
                    cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_assoc[indice])+" - "+rs_aux.getString(tab_cb_cpo_exibe[indice]));
                    if (tab_cb_tab_box[indice].equals("banco")) {
                        emiteBoleto = rs_aux.getString("emite_boleto");
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
    private void calcular_prazos(int cod_forma_pgto, java.util.Date dataemis, Double vlrtotal) {
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
            ResultSet rs_dup = st_dup.executeQuery(sql_dup);
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
                                  + empresa +", "
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
                          resultparc = resultparc + (i+1)+"a. duplicata com vcto em: "+datastr+" Vlr: "+formataNumDec(""+vlparc,2)+"\n";
                          totvlparc = totvlparc + vlparc;
                          //JOptionPane.showMessageDialog(null, "Gravacao da "+(i+1)+"a. duplicata realizada com sucesso para a data: "+datastr);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao tentar gravar as duplicatas desta NF: "+ex);
                        ex.printStackTrace();
                    }
                  }
                  JOptionPane.showMessageDialog(null, resultparc);
    }
    public static void main(String args[])
        {
        JFrame form = new Nf(3, null, null, null, "");
            form.setVisible(true);
    }

    /**
     * @return the tipoNF
     */
    public String getTipoNF() {
        return tipoNF;
    }

    /**
     * @param tipoNF the tipoNF to set
     */
    public void setTipoNF(String tipoNF) {
        this.tipoNF = tipoNF;
    }

    public String getCli_pessoa() {
        return cli_pessoa;
    }

    public void setCli_pessoa(String cli_pessoa) {
        this.cli_pessoa = cli_pessoa;
    }

    public String getCli_cnpj() {
        return cli_cnpj;
    }

    public void setCli_cnpj(String cli_cnpj) {
        this.cli_cnpj = cli_cnpj;
    }

    public String getCli_uf() {
        return cli_uf;
    }

    public void setCli_uf(String cli_uf) {
        this.cli_uf = cli_uf;
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_nf extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_nf()
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
