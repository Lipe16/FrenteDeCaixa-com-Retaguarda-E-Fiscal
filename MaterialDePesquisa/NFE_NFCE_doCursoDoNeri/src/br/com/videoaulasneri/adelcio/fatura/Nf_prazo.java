/*

Descrição: Digitação dos Prazos da NFe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;
//import Relatorios.Impressao;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
public class Nf_prazo extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de Cadastro: nf_prazo");

    //Labels dos campos da tabela;
    JLabel label_pedido              = new JLabel("Pedido: ");
    JLabel label_parcela             = new JLabel("Parcela: ");
    JLabel label_documento           = new JLabel("Documento: ");
    JLabel label_datavcto            = new JLabel("Data de Vencimento: ");
    JLabel label_vlr_parcela         = new JLabel("Valor da Parcela: ");
    JLabel label_datapgto            = new JLabel("Data do Pagamento: ");
    JLabel label_vlr_pago            = new JLabel("Valor Pago: ");

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
    JTextField tf_pedido             = new JTextField();
    JTextField tf_parcela            = new JTextField();
    JTextField tf_documento          = new JTextField();
    //JTextField tf_datavcto           = new JTextField();
    JTextField tf_vlr_parcela        = new JTextField();
    //JTextField tf_datapgto           = new JTextField();
    JTextField tf_vlr_pago           = new JTextField();
    JFormattedTextField  tf_datavcto, tf_datapgto;
    MaskFormatter dtf_data;

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();

    JComboBox<String> cb_pesquisa            = new JComboBox<>();
    JComboBox<String> cb_pesq_lab            = new JComboBox<>();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[7];
    String [] lab_tabela             = new String[7];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[7];
    String [] tab_cb_cpo_exibe       = new String[7];
    String [] tab_cb_cpo_assoc       = new String[7];
    JTextField [] tab_cb_tf          = new JTextField[7];
    Statement [] tab_cb_st           = new Statement[7];
    ResultSet [] tab_cb_rs           = new ResultSet[7];
    JComboBox [] tab_cb_cb           = new JComboBox[7];
    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 01;
    int posUltLabel                  = 0;
    int qtRegTab                     = 0;
    JTextField tf_aux_pesq           = tf_parcela;
    PreparedStatement ps_aux;
    int empresa = 0;
    String pedido = "";
    double vlr_parc_old = 0.00, vlr_pago_old = 0.00;


    public Nf_prazo(int empresa, Connection confat, String pedido)
    {
        setTitle("Formulario de Manutencao de nf_prazo");
        setSize(1010, 680);
        setLocation(135,45);
        setResizable(true);
        this.empresa = empresa;
        this.connection = confat;
        this.pedido = pedido;
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
        tf_pesquisa.setToolTipText("Informe data no formato: aaaa-mm-dd Ex: 2018-01-23");

        try {
            dtf_data           = new MaskFormatter("##/##/####");
             dtf_data           .setValidCharacters("0123456789");
             tf_datavcto     = new JFormattedTextField(dtf_data);
             tf_datapgto     = new JFormattedTextField(dtf_data);
        } catch (ParseException ex) {
            //Logger.getLogger(nfemenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    //Gera cor frente dos lanbels setForegroundColor()
        label_pedido            .setForeground(Color.black);
        label_parcela           .setForeground(Color.black);
        label_documento         .setForeground(Color.black);
        label_datavcto          .setForeground(Color.black);
        label_vlr_parcela       .setForeground(Color.black);
        label_datapgto          .setForeground(Color.black);
        label_vlr_pago          .setForeground(Color.black);


    //alinha os labels a direita
        label_pedido            .setHorizontalAlignment(JLabel.RIGHT);
        label_parcela           .setHorizontalAlignment(JLabel.RIGHT);
        label_documento         .setHorizontalAlignment(JLabel.RIGHT);
        label_datavcto          .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_parcela       .setHorizontalAlignment(JLabel.RIGHT);
        label_datapgto          .setHorizontalAlignment(JLabel.RIGHT);
        label_vlr_pago          .setHorizontalAlignment(JLabel.RIGHT);


    //Gera cor frente dos texfields setForegroundColor()
        tf_pedido               .setForeground(Color.black);
        tf_parcela              .setForeground(Color.black);
        tf_documento            .setForeground(Color.black);
        tf_datavcto             .setForeground(Color.black);
        tf_vlr_parcela          .setForeground(Color.black);
        tf_datapgto             .setForeground(Color.black);
        tf_vlr_pago             .setForeground(Color.black);


    //Gera cor frente de fundo texfields setBackroundColor()
        tf_pedido               .setBackground(Color.white);
        tf_parcela              .setBackground(Color.white);
        tf_documento            .setBackground(Color.white);
        tf_datavcto             .setBackground(Color.white);
        tf_vlr_parcela          .setBackground(Color.white);
        tf_datapgto             .setBackground(Color.white);
        tf_vlr_pago             .setBackground(Color.white);

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

        
        //Posicionando os componentes labels e textfields da tabela
        label_pedido             .setBounds(100,165,150,20);
        tf_pedido                .setBounds(260,165,88,20);
        label_parcela            .setBounds(100,190,150,20);
        tf_parcela               .setBounds(260,190,88,20);
        label_documento          .setBounds(100,215,150,20);
        tf_documento             .setBounds(260,215,96,20);
        label_datavcto           .setBounds(100,240,150,20);
        tf_datavcto              .setBounds(260,240,104,20);
        label_vlr_parcela        .setBounds(100,265,150,20);
        tf_vlr_parcela           .setBounds(260,265,192,20);
        label_datapgto           .setBounds(100,290,150,20);
        tf_datapgto              .setBounds(260,290,104,20);
        label_vlr_pago           .setBounds(100,315,150,20);
        tf_vlr_pago              .setBounds(260,315,192,20);
//Posicao do ultimo cpo da Tabela no Formulario. Utiliz.para Iniciar a exibicao do JTable
        posUltLabel           = 315;
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
           },
           new String [] {
                "Parcela",
                "Data Vcto",
                "Valor Parcela",
                "Data Pgto",
                "Valor Pago",
            }
        ));
        jTable1               .setAutoscrolls(true);
        jTable1.setDefaultRenderer(Object.class, new CellRenderer_nf_prazo());
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        getContentPane()      .add(label_pedido);
        getContentPane()      .add(tf_pedido);
        getContentPane()      .add(label_parcela);
        getContentPane()      .add(tf_parcela);
        getContentPane()      .add(label_documento);
        getContentPane()      .add(tf_documento);
        getContentPane()      .add(label_datavcto);
        getContentPane()      .add(tf_datavcto);
        getContentPane()      .add(label_vlr_parcela);
        getContentPane()      .add(tf_vlr_parcela);
        getContentPane()      .add(label_datapgto);
        getContentPane()      .add(tf_datapgto);
        getContentPane()      .add(label_vlr_pago);
        getContentPane()      .add(tf_vlr_pago);
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

        cpo_tabela[0]         = "pedido";
        cpo_tabela[1]         = "parcela";
        cpo_tabela[2]         = "documento";
        cpo_tabela[3]         = "datavcto";
        cpo_tabela[4]         = "vlr_parcela";
        cpo_tabela[5]         = "datapgto";
        cpo_tabela[6]         = "vlr_pago";
        lab_tabela[0]         = "Pedido";
        lab_tabela[1]         = "Parcela";
        lab_tabela[2]         = "Documento";
        lab_tabela[3]         = "Data de Vencimento";
        lab_tabela[4]         = "Valor da Parcela";
        lab_tabela[5]         = "Data do Pagamento";
        lab_tabela[6]         = "Valor Pago";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        try
        {
              //conexao conn = new conexao();
              //connection = conn.conecta("", "");
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares

              clicado = false;
              preencher_cb_pesq_lab();
              preencher_cb_pesquisa("tudo");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares

              //mostra_conteudo_nos_campos();
              preencher_jtable("tudo");
              //cb_pesquisa.addActionListener(this);
              stat_aux = true;
              clicado  = true;

              botao_novo.setEnabled(false);
              botao_gravar.setEnabled(false);
              botao_excluir.setEnabled(false);
              tf_pedido.setEditable(false);
              tf_parcela.setEditable(false);
              tf_vlr_parcela.setEditable(false);
              //tf_vlr_pago.setEditable(false);
              novo_registro();
              tf_documento.requestFocus();
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
                 boolean ehNum = true;
                 try {
                     int cpo = Integer.parseInt(tf_pesquisa.getText());
                 } catch(Exception e) {
                     ehNum = false;
                 }
                 if (ehNum) {
                    sql_query = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()
                                 +" order by pedido, parcela";
                 } else if (cpo_tabela[cb_pesq_lab.getSelectedIndex()].indexOf("data") != -1){
                    sql_query = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = '"+tf_pesquisa.getText() + "' "
                                 +" order by pedido, parcela";
                 } else {
                    sql_query = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                 +" order by pedido, parcela";
                 }
             } else {
                  sql_query = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido
                                 +" order by pedido, parcela";
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next()) {
                 qtRegTab++;
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("parcela"));
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
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(35);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(35);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from nf_prazo");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar nf_prazo: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 boolean ehNum = true;
                 try {
                     int cpo = Integer.parseInt(tf_pesquisa.getText());
                 } catch(Exception e) {
                     ehNum = false;
                 }
                 if (ehNum) {
                    sqlquery = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()
                                 +" order by pedido, parcela";
                 } else if (cpo_tabela[cb_pesq_lab.getSelectedIndex()].indexOf("data") != -1){
                    sqlquery = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = '"+tf_pesquisa.getText() + "' "
                                 +" order by pedido, parcela";
                 } else {
                    sqlquery = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido+" and "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                 +" order by pedido, parcela";
                 }
            } else {
                sqlquery = "select * from nf_prazo where empresa = "+empresa+" "
                          +" and pedido = "+pedido
                                 +" order by pedido, parcela";
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            String datavcto, datapgto;
            while (resultset.next()){
               datavcto = resultset.getString("datavcto");
               if (datavcto == null) datavcto = "  /  /    ";
               datapgto = resultset.getString("datapgto");
               if (datapgto == null) datapgto = "  /  /    ";
                modelo.addRow(new Object [] {
                resultset.getString("parcela"),
                formatDateUser(datavcto),
                Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_parcela")), 2, 0),
                formatDateUser(datapgto),
                Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_pago")), 2, 0)
                                            }
                );
                qtreg++;
            }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: nf_prazo: "+qtreg);
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
               data = resultset.getString("datavcto");
               if (data == null) data = "  /  /    ";
               tf_datavcto     .setText(formatDateUser(data));
               data = resultset.getString("datapgto");
               if (data == null) data = "  /  /    ";
               tf_datapgto     .setText(formatDateUser(data));
               tf_pedido             .setText(resultset.getString("pedido"));
               tf_parcela            .setText(resultset.getString("parcela"));
               tf_documento          .setText(resultset.getString("documento"));
               vlr_parc_old = Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_parcela")), 2, 0);
               vlr_pago_old = Biblioteca.arredondar(Double.parseDouble(resultset.getString("vlr_pago")), 2, 0);
               //tf_datavcto           .setText(resultset.getString("datavcto"));
               tf_vlr_parcela        .setText(""+vlr_parc_old);
               //tf_datapgto           .setText(resultset.getString("datapgto"));
               tf_vlr_pago           .setText(""+vlr_pago_old);

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
                   JOptionPane.showMessageDialog(null,"Erro ao tentar exibir o conteudo. Erro: "+erro_sql);
                   novo_registro();
               }
           }
           clicado = true;
//Desabilita campo chave para alteracao/exclusao 
           tf_pedido.setEditable(false);
           tf_parcela.setEditable(false);
           tf_documento.requestFocus();
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
            String parc = resultset.getString("parcela");
            ult_cod = Integer.parseInt(parc) + 1;
        }
        catch(SQLException erro)
        {
             //JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
            ult_cod = 1;
        }
        tf_pedido             .setText(pedido);
        tf_parcela            .setText(""+ult_cod);
        tf_documento          .setText("");
        //tf_datavcto           .setText("");
        tf_vlr_parcela        .setText("0.00");
        //tf_datapgto           .setText("");
        tf_vlr_pago           .setText("0.00");
        String data = "  /  /    ";
        tf_datavcto       .setText(formatDateUser(data));
        data = "  /  /    ";
        tf_datapgto     .setText(formatDateUser(data));
      posiciona_combo_e_jtable();
      ehnovo = true;
//Habilita campo chave para inclusao 
//       tf_pedido.setEditable(false);
//       tf_parcela.setEditable(false);
       tf_documento.requestFocus();
     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_pedido.getText() == null ) tf_pedido.setText("");
        if ( tf_parcela.getText() == null ) tf_parcela.setText("");
        if ( tf_documento.getText() == null ) tf_documento.setText("");
        if ( tf_datavcto.getText() == null ) tf_datavcto.setText("");
        if ( tf_vlr_parcela.getText() == null ) tf_vlr_parcela.setText("");
        if ( tf_datapgto.getText() == null ) tf_datapgto.setText("");
        if ( tf_vlr_pago.getText() == null ) tf_vlr_pago.setText("");
        return retorno;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                java.util.Date datavcto = null, datapgto = null;  //new Date();
                //String sdatamvto = "";
                try {
                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    datavcto = f.parse(tf_datavcto.getText());
                    datapgto = f.parse(tf_datapgto.getText());
                    //SimpleDateFormat f_inv = new SimpleDateFormat("yyyy-MM-dd");
                    //sdatamvto = f_inv.format(datadigi);
                } catch (Exception ex) {
                    //Logger.getLogger(mvpeso.class.getName()).log(Level.SEVERE, null, ex);
                }
                tf_vlr_parcela.setText(tf_vlr_parcela.getText().replaceAll(",", "."));
                tf_vlr_pago.setText(tf_vlr_pago.getText().replaceAll(",", "."));
                String sql_insert = "insert into nf_prazo ( "+
                        "empresa, pedido, "+"parcela, "+"documento, "+"datavcto, "+"vlr_parcela, "+"vlr_pago "+
                        //"datapgto, "+"vlr_pago "+
                        ")"+
                        " values ("+ empresa + ", " + tf_pedido.getText() + ", "+tf_parcela.getText() + ", "+"'"+tf_documento.getText() + "', "+
                        "'"+datavcto + "', "+tf_vlr_parcela.getText() + ", "+tf_vlr_pago.getText()+
                 //+ ", "+"'"+datapgto + "', " +
                        ")";
            System.out.println("Comando sql_insert = " + sql_insert);
            //JOptionPane.showMessageDialog(null, "Comando sql_insert = " + sql_insert);
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from nf_prazo where empresa = "+empresa+" and pedido = "+pedido
                                 +" order by pedido, parcela"
                         );
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
                java.util.Date datavcto = null, datapgto = null;  //new Date();
                //String sdatamvto = "";
                try {
                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    datavcto = f.parse(tf_datavcto.getText());
                    datapgto = f.parse(tf_datapgto.getText());
                    //SimpleDateFormat f_inv = new SimpleDateFormat("yyyy-MM-dd");
                    //sdatamvto = f_inv.format(datadigi);
                } catch (Exception ex) {
                 //JOptionPane.showMessageDialog(null, "Erro ao tentar converter data! Erro: "+ex);
                }
                tf_vlr_parcela.setText(tf_vlr_parcela.getText().replaceAll(",", "."));
                tf_vlr_pago.setText(tf_vlr_pago.getText().replaceAll(",", "."));
                 String sql_alterar = "update nf_prazo set " +"parcela = "+ tf_parcela.getText()+", "
                        +"documento = '"+ tf_documento.getText()+"', ";
                        sql_alterar += "datavcto = '"+ datavcto+"', ";
                        sql_alterar += "vlr_parcela = "+ tf_vlr_parcela.getText();
                 if (datapgto != null && !datapgto.equals("")) {
                    sql_alterar += ", "+"datapgto = '"+ datapgto+"', "+"vlr_pago = "+ tf_vlr_pago.getText();
                }
                sql_alterar += " where pedido = " + tf_pedido.getText()+" and parcela = "+tf_parcela.getText();
                  System.out.println("sql_altera = " + sql_alterar);
                  //JOptionPane.showMessageDialog(null, "sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from nf_prazo where empresa = "+empresa+" and pedido = "+pedido
                                 +" order by pedido, parcela"
                          );
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
           String nome = "Excluir prazo  ref. parcela: "+tf_parcela.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM nf_prazo where empresa = "+empresa+" and pedido = " + tf_pedido.getText()+" and parcela = "+tf_parcela.getText();
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from nf_prazo where empresa = "+empresa+" and pedido = "+pedido
                                 +" order by pedido, parcela"
                          );
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
        //Impressao impr = new Impressao(empresa, "rel_nf_prazo.jasper");
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
           resultset  = statement.executeQuery("select * from nf_prazo where empresa = "+empresa+" and pedido = "+pedido
                                 +" order by pedido, parcela"
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
                cb_pesquisa.setSelectedItem(tf_parcela.getText());
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
        String sql = "select * from nf_prazo where Descricao = "+cb_pesquisa.getSelectedItem();
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
                    String sql_query = "select * from nf_prazo where empresa = "+empresa+" "
                            +" and pedido = "+pedido
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                                 +" order by pedido, parcela";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from nf_prazo where empresa = "+empresa+" and pedido = "+pedido
                                 +" order by pedido, parcela"
                            );
                }
                //resultset = statement.executeQuery("select * from nf_prazo");
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
        }
    }

    public void keyReleased(KeyEvent e) {
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
    }
    public static void main(String args[])
        {
        JFrame form = new Nf_prazo(3, null, null);
            form.setVisible(true);
    }
}
////classe auxiliar para centralizar as colunas dp JTable
class CellRenderer_nf_prazo extends DefaultTableCellRenderer {

/*
*
*/
private static final long   serialVersionUID    = 1L;

    public CellRenderer_nf_prazo()
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
