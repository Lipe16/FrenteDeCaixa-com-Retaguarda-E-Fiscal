/*

Descrição: Manutenção da Tabela de Usuários do Sistema

Autor: Videoaulasneri - email: videoaulasneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Login extends JFrame implements ActionListener, MouseListener, KeyListener
{
    int navega = 0;
    boolean ehnovo = false;
    DatabaseMetaData dmd_aux;
    ResultSet resultset;
    Statement statement;
    Connection connection;
    int empresa = 0;
    String driver, url, usuario, senha;
    String acaoFiltro;
    JLabel lb_titulo = new JLabel("Manutencao de login - videoaulas Neri e Adelcio");

    //Labels dos campos da tabela;
    JLabel label_codigo              = new JLabel("Codigo..: ");
    JLabel label_empresa             = new JLabel("Empresa..: ");
    JLabel label_usuario             = new JLabel("Usuario..: ");
    JLabel label_senha               = new JLabel("Senha..: ");
    JLabel label_conf_senha          = new JLabel("Confirme a Senha: ");
    JLabel label_nome                = new JLabel("Nome..: ");
    JLabel label_modelonfe           = new JLabel("Modelo NFe: ");
    JLabel label_nivel               = new JLabel("Nivel(A/G/U): ");
    JLabel label_alterar_qtde        = new JLabel("Alterar Qtde(NFCe): ");

    //Gerando os Bot?µes
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
    //Ira gerar todos os textfields dos campos das tabelas
    JTextField tf_codigo             = new JTextField();
    JTextField tf_empresa            = new JTextField();
    JTextField tf_usuario            = new JTextField();
    JPasswordField tf_senha          = new JPasswordField();
    JPasswordField tf_conf_senha     = new JPasswordField();
    JTextField tf_nome               = new JTextField();
    JTextField tf_modelonfe          = new JTextField();
    JTextField tf_nivel              = new JTextField();
    JTextField tf_alterar_qtde       = new JTextField();

    JPanel panel_pesquisa            = new JPanel();
    JLabel lb_pesquisa               = new JLabel("Pesquisar ");
    JTextField tf_pesquisa           = new JTextField();
    JComboBox cb_pesquisa            = new JComboBox();
    JComboBox cb_pesq_lab            = new JComboBox();
    JScrollPane jScrollPane          = new JScrollPane();
    JTable jTable1                   = new JTable();
    String [] cpo_tabela             = new String[8];
    String [] lab_tabela             = new String[8];

    // criacao de componentes de combobox auxiliares
    String [] tab_cb_tab_box         = new String[8];
    String [] tab_cb_cpo_exibe       = new String[8];
    String [] tab_cb_cpo_assoc       = new String[8];
    JTextField [] tab_cb_tf          = new JTextField[8];
    Statement [] tab_cb_st           = new Statement[8];
    ResultSet [] tab_cb_rs           = new ResultSet[8];
    JComboBox [] tab_cb_cb           = new JComboBox[8];
    boolean stat_aux;
    boolean clicado                  = true;
    int indice_pesquisa              = 01;
    JTextField tf_aux_pesq           = tf_usuario;

    ResultSet rs_empresa;
    Statement st_empresa;
    JComboBox cb_empresa            = new JComboBox();


    public Login(Connection connection, int empresa)
    {
        setTitle("Formulario de Manutencao de login - videoaulas Neri e Adelcio");
        this.connection = connection;
        this.empresa = empresa;
        setSize(1000, 550);
        setLocation(140,190);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //getContentPane().setBackground(new java.awt.Color(255, 255, 102));
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

    //Gera cor frente dos labels setForegroundColor()
        label_codigo            .setForeground(Color.black);
        label_empresa           .setForeground(Color.black);
        label_usuario           .setForeground(Color.black);
        label_senha             .setForeground(Color.black);
        label_conf_senha        .setForeground(Color.black);
        label_nome              .setForeground(Color.black);
        label_modelonfe         .setForeground(Color.black);
        label_nivel             .setForeground(Color.black);
        label_alterar_qtde      .setForeground(Color.black);

    //Alinhamento dos Labes a direita
        label_codigo            .setHorizontalAlignment(JLabel.RIGHT);
        label_empresa           .setHorizontalAlignment(JLabel.RIGHT);
        label_usuario           .setHorizontalAlignment(JLabel.RIGHT);
        label_senha             .setHorizontalAlignment(JLabel.RIGHT);
        label_conf_senha        .setHorizontalAlignment(JLabel.RIGHT);
        label_nome              .setHorizontalAlignment(JLabel.RIGHT);
        label_modelonfe         .setHorizontalAlignment(JLabel.RIGHT);
        label_nivel             .setHorizontalAlignment(JLabel.RIGHT);
        label_alterar_qtde      .setHorizontalAlignment(JLabel.RIGHT);
    //Gera cor frente dos texfields setForegroundColor()
        tf_codigo               .setForeground(Color.black);
        tf_empresa              .setForeground(Color.black);
        tf_usuario              .setForeground(Color.black);
        tf_senha                .setForeground(Color.black);
        tf_conf_senha           .setForeground(Color.black);
        tf_nome                 .setForeground(Color.black);
        tf_modelonfe            .setForeground(Color.black);
        tf_nivel                .setForeground(Color.black);
        tf_alterar_qtde         .setForeground(Color.black);

    //Gera cor frente de fundo texfields setBackroundColor()
        tf_codigo               .setBackground(Color.white);
        tf_empresa              .setBackground(Color.white);
        tf_usuario              .setBackground(Color.white);
        tf_senha                .setBackground(Color.white);
        tf_conf_senha           .setBackground(Color.white);
        tf_nome                 .setBackground(Color.white);
        tf_modelonfe            .setBackground(Color.white);
        tf_nivel                .setBackground(Color.white);
        tf_alterar_qtde         .setBackground(Color.white);

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
        botao_primeiro_registro  .setBounds(200,85,40,30);
        botao_registro_anterior  .setBounds(250,85,40,30);
        botao_proximo_registro   .setBounds(300,85,40,30);
        botao_ultimo_registro    .setBounds(350,85,40,30);
        botao_novo               .setBounds(400,85,40,30);
        botao_gravar             .setBounds(450,85,40,30);
        botao_alterar            .setBounds(500,85,40,30);
        botao_excluir            .setBounds(550,85,40,30);
        botao_imprimir           .setBounds(600,85,40,30);
        botao_sair               .setBounds(670,85,40,30);

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
        tf_senha                 .setToolTipText("Informe a senha de usuario!");
        tf_conf_senha            .setToolTipText("Informe a senha novamente!");
        tf_alterar_qtde          .setToolTipText("Permite que o Operador do Caixa altere a Quantidade dos Produtos");
        tf_nivel                 .setToolTipText("A-Administrador, G-Gerente, U-Usuario comum");

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
        //adActionListener(this) para os combobox auxiliares
        cb_empresa           .addActionListener(this);

        
        //Posicionando os componentes labels e textfields da tabela
        label_codigo          .setBounds(100,125,150,20);
        tf_codigo             .setBounds(250,125,88,20);
        label_empresa         .setBounds(100,150,150,20);
        tf_empresa            .setBounds(250,150,88,20);
        cb_empresa            .setBounds(348,150, 200,20);
        label_usuario         .setBounds(100,175,150,20);
        tf_usuario            .setBounds(250,175,80,20);
        label_senha           .setBounds(100,200,150,20);
        tf_senha              .setBounds(250,200,80,20);
        label_conf_senha      .setBounds(350,200,200,20);
        tf_conf_senha         .setBounds(550,200,80,20);
        label_nome            .setBounds(100,225,150,20);
        tf_nome               .setBounds(250,225,400,20);
        label_modelonfe       .setBounds(100,250,150,20);
        tf_modelonfe          .setBounds(250,250,30,20);
        label_nivel           .setBounds(100,275,150,20);
        tf_nivel              .setBounds(250,275,20,20);
        label_alterar_qtde    .setBounds(100,300,150,20);
        tf_alterar_qtde       .setBounds(250,300,20,20);
        jScrollPane           .setBounds(5, 325,980, 250);
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
                "codigo",
                "empresa",
                "usuario",
                "nome",
                "nivel",
                "alterar_qtde"
            }
        ));
        jTable1               .setAutoscrolls(true);
        jScrollPane           .setViewportView(jTable1);
        
        //Adicionando Labels no GetContenPane()
        //getContentPane()      .add(label_codigo);
        //getContentPane()      .add(tf_codigo);
        getContentPane()      .add(label_empresa);
        getContentPane()      .add(tf_empresa);
        getContentPane()      .add(cb_empresa);
        getContentPane()      .add(label_usuario);
        getContentPane()      .add(tf_usuario);
        getContentPane()      .add(label_senha);
        getContentPane()      .add(tf_senha);
        getContentPane()      .add(label_conf_senha);
        getContentPane()      .add(tf_conf_senha);
        getContentPane()      .add(label_nome);
        getContentPane()      .add(tf_nome);
        getContentPane()      .add(label_modelonfe);
        getContentPane()      .add(tf_modelonfe);
        getContentPane()      .add(label_nivel);
        getContentPane()      .add(tf_nivel);
        getContentPane()      .add(label_alterar_qtde);
        getContentPane()      .add(tf_alterar_qtde);
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
        cpo_tabela[2]         = "usuario";
        cpo_tabela[3]         = "senha";
        cpo_tabela[4]         = "nome";
        cpo_tabela[5]         = "modelonfe";
        cpo_tabela[6]         = "nivel";
        cpo_tabela[7]         = "alterar_qtde";

        lab_tabela[0]         = "codigo";
        lab_tabela[1]         = "empresa";
        lab_tabela[2]         = "usuario";
        lab_tabela[3]         = "senha";
        lab_tabela[4]         = "nome";
        lab_tabela[5]         = "modelonfe";
        lab_tabela[6]         = "nivel";
        lab_tabela[6]         = "alterar_qtde";

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tf[1]          = tf_empresa;
        tab_cb_st[1]          = st_empresa;
        tab_cb_rs[1]          = rs_empresa;
        tab_cb_cb[1]          = cb_empresa;

        // inicializa tabelas com informacoes das tabelas para os combobox auxiliares
        tab_cb_tab_box[1]     = "empresa";
        tab_cb_cpo_assoc[1]   = "codigo";
        tab_cb_cpo_exibe[1]   = "fantasia";
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();

        //driver                = "sun.jdbc.odbc.JdbcOdbcDriver";
        //url                   = "jdbc:odbc:NFefacil";
        //usuario               = "";
        //senha                 = "";
        try
        {
              //Class.forName(driver);
              //connection = DriverManager.getConnection(url,usuario,senha);
              dmd_aux = connection.getMetaData();
              statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
              //statement para as tabelas dos combobox auxiliares
              st_empresa  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

              clicado = false;
//JOptionPane.showMessageDialog(null, "login - passou 1 . . .");
              preencher_cb_pesq_lab();
//JOptionPane.showMessageDialog(null, "login - passou 2 . . .");
              preencher_cb_pesquisa("tudo");
//JOptionPane.showMessageDialog(null, "login - passou 3 . . .");
              stat_aux = false;
              //chamada de metodo para preencher os combobox auxiliares
             preenche_cb_auxiliar(st_empresa, rs_empresa, cb_empresa, 1);
//JOptionPane.showMessageDialog(null, "login - passou 4 . . .");

              mostra_conteudo_nos_campos();
//JOptionPane.showMessageDialog(null, "login - passou 5 . . .");
              preencher_jtable("tudo");
//JOptionPane.showMessageDialog(null, "login - passou 6 . . .");
              cb_pesquisa.addActionListener(this);
              stat_aux = true;
              clicado  = true;
        }
        //catch(ClassNotFoundException erro_class)
        //{
        //       JOptionPane.showMessageDialog(null,"Driver nao localizado: "+erro_class);
        //}
        catch(Exception erro_sql)
        {
             JOptionPane.showMessageDialog(null,"Nao conseguiu conectar ao banco "+erro_sql);
        }
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
             if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 if (cb_pesq_lab.getSelectedIndex() != 0 && cb_pesq_lab.getSelectedIndex() != 1) {
                    sql_query = "select * from login where "  //empresa = "+empresa+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'";
                 } else {
                    sql_query = "select * from login where "  //empresa = "+empresa+" and "
                                 +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+"";
                 }
             } else {
                  sql_query = "select * from login";  // where empresa = "+empresa;
             }
             System.out.println("Comando sql_Query: "+sql_query);
             resultset = statement.executeQuery(sql_query);
             while(resultset.next())
                 if (cb_pesq_lab.getSelectedIndex() > 0)
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));  //cb_pesq_lab.getSelectedIndex()]));
                 else
                     cb_pesquisa.addItem(resultset.getString(cpo_tabela[indice_pesquisa]));
                 //cb_pesquisa.addItem(resultset.getString("empresa"));
             resultset.first();
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
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(20);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(20);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(20);

        this.acaoFiltro = acaoFiltro;
        //try {
        //    resultset = statement.executeQuery("select * from login");
        //} catch (SQLException ex) {
        //    JOptionPane.showMessageDialog(null,"Erro ao listar login: "+ex);
        //}
        DefaultTableModel modelo = (DefaultTableModel)jTable1.getModel();
        modelo.setNumRows(0);

        try
        {
            //JOptionPane.showMessageDialog(null, "login - passou 1 . . .");
            int qtreg = 0;
            String sqlquery = "";
            if (acaoFiltro.equals("filtrar") && !tf_pesquisa.getText().equals("")){
                 if (cb_pesq_lab.getSelectedIndex() != 0 && cb_pesq_lab.getSelectedIndex() != 1) {
                    sqlquery = "select * from login where "  //empresa = "+empresa+" and "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                        +" order by empresa, usuario";
                 } else {
                sqlquery = "select * from login where "  //empresa = "+empresa+" and "
                           +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" = "+tf_pesquisa.getText()+""
                        +" order by empresa, usuario";
                 }
            } else {
                sqlquery = "select * from login order by empresa, usuario";  // where empresa = "+empresa;
            }
            System.out.println("Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
             //JOptionPane.showMessageDialog(null, "login - passou 2 . . .");
           while (resultset.next()){
                modelo.addRow(new Object [] {
                resultset.getString("codigo"),
                resultset.getString("empresa"),
                resultset.getString("usuario"),
                resultset.getString("nome"),
                resultset.getString("nivel"),
                resultset.getString("alterar_qtde")
                                            }
                );
                qtreg++;
             //JOptionPane.showMessageDialog(null, "login - passou 3 . . .");
           }
            //JOptionPane.showMessageDialog(null,"Qtde regs da tabela: login: "+qtreg);
            resultset.first();
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
            JOptionPane.showMessageDialog(null, "Nao conseguiu setar o novo LookAndFeel!!!");
        }
    }
    public void mostra_conteudo_nos_campos()
    {
           clicado = false;
           try
           {
               tf_codigo             .setText(resultset.getString("codigo"));
               tf_empresa            .setText(resultset.getString("empresa"));
               tf_usuario            .setText(resultset.getString("usuario"));
               tf_senha              .setText(resultset.getString("senha"));
               tf_conf_senha         .setText(tf_senha.getText());
               tf_nome               .setText(resultset.getString("nome"));
               tf_modelonfe          .setText(resultset.getString("modelonfe"));
               tf_nivel              .setText(resultset.getString("nivel"));
               tf_alterar_qtde       .setText(resultset.getString("alterar_qtde"));
//JOptionPane.showMessageDialog(null,"login - passou 4.1 . . .");
               posiciona_combo_e_jtable();
//JOptionPane.showMessageDialog(null,"login - passou 4.2 . . .");
               navega =0;
           }
           catch(Exception erro_sql)
           {
               if (navega == 1) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel retornar pois voce ja esta no primeiro registro da tabela");
               else if (navega == 2) 
                   JOptionPane.showMessageDialog(null,"Nao foi possivel avancar pois voce ja esta no ultimo registro da tabela");
               //else
               //    JOptionPane.showMessageDialog(null,"Nao foi possivel mostrar os dados! \nErro: "+erro_sql);
           }
           clicado = true;
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
                 System.out.println("selecionaJtable() reg: "+reg);
                 //selecionaJtable(reg);
            }
//JOptionPane.showMessageDialog(null,"login - passou 4.1.1 . . .");
            ehnovo = false;

            for (int i=0; i<tab_cb_tab_box.length; i++){
                if ( tab_cb_tab_box[i] != null ) {
                    if (!tab_cb_tf[i].getText().equals("")){
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2 . . .");
                        mostra_tf_ref_cb_aux(tab_cb_st[i], tab_cb_rs[i], tab_cb_cb[i], tab_cb_tf[i], i);
//JOptionPane.showMessageDialog(null,"login - passou 4.1.3 . . .");
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
                if (check_textField()) {
                    gravar();
                }
            }
            else if (acao.getSource() == botao_excluir)
                excluir();
            else if (acao.getSource() == botao_alterar){
                if (check_textField()) {
                    alterar();
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
            else if (acao.getSource() == botao_imprimir){
                imprimir();
      }
            else if (acao.getSource() == botao_sair) {
                this.dispose();
            }
            else if (acao.getSource() == cb_empresa){
               if ( stat_aux) {
                   trata_cb_auxiliar(st_empresa, rs_empresa, cb_empresa, tf_empresa, 1);
                }
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
/*
        try
        {
            String sqlquery = "select * from login where empresa = "+empresa+" order by codigo";
            JOptionPane.showMessageDialog(null, "Comando sqlQuery: "+sqlquery);
            resultset = statement.executeQuery(sqlquery);
            int codigo = 0;
            //resultset.last();
            while (resultset.next()) {
                codigo = resultset.getInt("codigo");
                JOptionPane.showMessageDialog(null, "Codigo lido = "+codigo);
            }
            ult_cod = codigo + 1;
        }
        catch(SQLException erro)
        {
             JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
            ult_cod = 1;
        }
 * 
 */
        tf_codigo             .setText(""+ult_cod);
        tf_empresa            .setText("");
        tf_usuario            .setText("");
        tf_senha              .setText("");
        tf_conf_senha         .setText("");
        tf_nome               .setText("");
        tf_modelonfe          .setText("");
        tf_nivel              .setText("");
        tf_alterar_qtde       .setText("");
        tf_codigo.setEditable(false);
           botao_gravar.setEnabled(true);
           botao_alterar.setEnabled(false);
           botao_excluir.setEnabled(false);
        tf_usuario.requestFocus();
          ehnovo = true;
     }

    //  check para verificar se existe campo da tela com conteudo nulo
    //  porque gera erro ao tentar gravar/atualizar o BD
    // campos com conteudo nulo serao substituidos por ""
    //metodo para gravar no banco registro
    public boolean check_textField() {
        boolean retorno = true;
        if ( tf_codigo.getText() == null ) tf_codigo.setText("");
        if ( tf_empresa.getText() == null ) tf_empresa.setText("");
        if ( tf_usuario.getText() == null ) tf_usuario.setText("");
        if ( tf_senha.getText() == null ) tf_senha.setText("");
        if ( tf_conf_senha.getText() == null ) tf_conf_senha.setText("");
        if ( tf_nome.getText() == null ) tf_nome.setText("");
        if ( tf_modelonfe.getText() == null ) tf_modelonfe.setText("55");
        if ( tf_nivel.getText() == null ) tf_nivel.setText("U");
        if ( tf_alterar_qtde.getText() == null ) tf_alterar_qtde.setText("N");
        tf_nivel.setText(tf_nivel.getText().toUpperCase());
        if ( 
                !tf_nivel.getText().equals("A") && 
                //!tf_nivel.getText().equals("S") && 
                !tf_nivel.getText().equals("G") && 
                !tf_nivel.getText().equals("U")  
            ) {
            JOptionPane.showMessageDialog(null, "O Nivel deve ser: A(Administrador), G(Gerente) ou U(Usuario)");
            retorno = false;
        }
        tf_alterar_qtde.setText(tf_alterar_qtde.getText().toUpperCase());
        if ( 
                !tf_alterar_qtde.getText().equals("N") && 
                !tf_alterar_qtde.getText().equals("S") 
            ) {
            JOptionPane.showMessageDialog(null, "O Campo Alterar Qtde na Tela da Frente de Caixa só pode ser N (Não) ou S (Sim)");
            retorno = false;
        }
/*        
        if (tf_senha.getText().length() < 6) {
            JOptionPane.showMessageDialog(null, "O campo [Senha] tem que ter pelo menos 6 caracteres!");
            tf_senha.setText("");
            tf_conf_senha.setText("");
            retorno = false;
        } else 
*/        
        if (!tf_senha.getText().equals(tf_conf_senha.getText())) {
            JOptionPane.showMessageDialog(null, "A conteudo do campo [Senha] difere do campo [Confirme a Senha]  !!!");
            tf_senha.setText("");
            tf_conf_senha.setText("");
            retorno = false;
        }
        return retorno;
    }

    //metodo para gravar no banco registro
    public void gravar()
    {
        if (ehnovo)
            {
            try
            {
                 String sql_insert = "insert into login ( "+
                        "empresa, "+"usuario, "+"senha, "+"nome, "+"modelonfe, "+"nivel, "+"alterar_qtde"+")"+
                        "values ("
                        +tf_empresa.getText() + ", "+"'"
                        +tf_usuario.getText() + "', '"
                        +tf_senha.getText() + "', '"
                        +tf_nome.getText()+ "', '"
                        +tf_modelonfe.getText() + "', '"
                        +tf_nivel.getText() + "', '"
                        +tf_alterar_qtde.getText() + "' "
                        + ")";
                 statement.executeUpdate(sql_insert);
                 JOptionPane.showMessageDialog(null, "Gravacao realizada com sucesso!");
                 clicado = false;
                 preencher_cb_pesquisa("tudo");
                 preencher_jtable("tudo");
                 resultset  = statement.executeQuery("select * from login");  // where empresa = "+empresa);
                 resultset.last();
                 //mostra_conteudo_nos_campos();
                 posiciona_combo_e_jtable();
                 clicado = true;
            }
            catch(SQLException erro)
            {
                 erro.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Erro no novo registro = "+erro);
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
                 String sql_alterar = "update login set " 
                         +"empresa = "+ tf_empresa.getText()+", "
                         +"usuario = '"+ tf_usuario.getText()+"', "
                         +"senha = '"+ tf_senha.getText()+"', "
                         +"nome = '"+ tf_nome.getText()+"', "
                         +"modelonfe = '"+ tf_modelonfe.getText()+"', "
                         +"nivel = '"+ tf_nivel.getText()+"', "
                         +"alterar_qtde = '"+ tf_alterar_qtde.getText()+"'"
                         +" where codigo = " + tf_codigo.getText();
                  System.out.println("sql_altera = " + sql_alterar);
                  statement.executeUpdate(sql_alterar);
                  JOptionPane.showMessageDialog(null, "Alteracao realizada com sucesso!");
                  clicado = false;
                  resultset  = statement.executeQuery("select * from login");  // where empresa = "+empresa);
                  resultset.first();
                  mostra_conteudo_nos_campos();
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  posiciona_combo_e_jtable();
                  tf_pesquisa.setText("");
                  clicado = true;
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
           String nome = "Deletar login : "+tf_usuario.getText()+" ?";
           System.out.println("nome = " + nome);
           int opcao_escolhida = JOptionPane.showConfirmDialog(null,nome,"Exclusao ",JOptionPane.YES_NO_OPTION);
           if (opcao_escolhida == JOptionPane.YES_OPTION)
           {
               String  sql_delete = "DELETE FROM login Where empresa = "+tf_empresa.getText()+" and " 
                       +"codigo = " + tf_codigo.getText() ;
               System.out.println("sql_delete = " + sql_delete);
               int conseguiu_excluir = statement.executeUpdate(sql_delete);
               if (conseguiu_excluir > 0)
               {
                  JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso!");
                  clicado = false;
                  preencher_cb_pesquisa("tudo");
                  preencher_jtable("tudo");
                  resultset  = statement.executeQuery("select * from login");  // where empresa = "+empresa);
                  resultset.first();
                  mostra_conteudo_nos_campos();
                  clicado = true;
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
    }

    public void preenche_cb_auxiliar(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, int indice) {
        System.out.println("Preenchendo o ComboBox associado ao campo: "+cpo_tabela[indice]);
        try {
            cb_aux.removeAllItems();
            String sql_aux = "select * from "+tab_cb_tab_box[indice];
            System.out.println("Sql_aux: "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
            while(rs_aux.next())
                 cb_aux.addItem(rs_aux.getString(tab_cb_cpo_exibe[indice]));
            rs_aux.first();
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
                String sql_aux = "";
                if (j >0){
                    System.out.println("Tipo do campo ["+rs_aux.getMetaData().getColumnName(j)+"] = "+rs_aux.getMetaData().getColumnType(j));
                    if (rs_aux.getMetaData().getColumnType(j) == 4 )
                        sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" = "+cb_aux.getSelectedItem();
                    else 
                        sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" = '"+cb_aux.getSelectedItem()+"'";
                } else {
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_exibe[indice]+" = '"+cb_aux.getSelectedItem()+"'";
                }
                System.out.println("Sql_aux: "+sql_aux);
                rs_aux = st_aux.executeQuery(sql_aux);
                while(rs_aux.next())
                     tf_aux.setText(rs_aux.getString(tab_cb_cpo_assoc[indice]));
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o textField  "+tf_aux+": "+ex);
        }
    }
    public void mostra_tf_ref_cb_aux(Statement st_aux, ResultSet rs_aux, JComboBox cb_aux, JTextField tf_aux, int indice) {
        //System.out.println("preenchimento do textfield para o ComboBox: cb_"+cpo_tabela[indice]);
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.0 . . .");
        String sql_aux;
        try {
            sql_aux = "select * from "+tab_cb_tab_box[indice];
            //System.out.println("Sql_aux do metodo mostra_tf_ref_cb_aux() para getMetaData: "+sql_aux);
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
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.3 . . .");
            if (j >0){
                if (rs_aux.getMetaData().getColumnType(j) == 4 )
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = "+tf_aux.getText();
                else
                    sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = '"+tf_aux.getText()+"'";
            } else {
                 sql_aux = "select * from "+tab_cb_tab_box[indice]+" where "+tab_cb_cpo_assoc[indice]+" = '"+tf_aux.getText()+"'";
            }
            //System.out.println("Sql_aux do metodo mostra_tf_ref_cb_aux(): "+sql_aux);
            rs_aux = st_aux.executeQuery(sql_aux);
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.4 . . .");
            while(rs_aux.next()){
                 cb_aux.setSelectedItem(rs_aux.getString(tab_cb_cpo_exibe[indice]));
                 break;
            }
//JOptionPane.showMessageDialog(null,"login - passou 4.1.2.5 . . .");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }
    }
    public void pesquisadigitacao()
    {
       try
       {
           resultset  = statement.executeQuery("select * from login");  // where empresa = "+empresa);
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
             JOptionPane.showMessageDialog(null, "Nao conseguiu localizar o Texto no Campo informado!\nErro:"+erro);
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
            if (achou){;
                mostra_conteudo_nos_campos();
                //preencher_jtable();
                clicado = false;
                cb_pesquisa.setSelectedItem(tf_aux_pesq.getText());
                //System.out.println("selecionaJtable() reg: "+reg);
                selecionaJtable(reg);
            }
            clicado = true;
/*
        String sql = "select * from login where Descricao = "+cb_pesquisa.getSelectedItem();
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
                    String sql_query = "select * from login where " //empresa = "+empresa+" and "
                                +cpo_tabela[cb_pesq_lab.getSelectedIndex()]+" like '%"+tf_pesquisa.getText()+"%'"
                            +" order by empresa, usuario";
                    System.out.println("Comando sqlQuery no mouseClicked: "+sql_query);
                    resultset = statement.executeQuery(sql_query);
                } else {
                    resultset  = statement.executeQuery("select * from login order by empresa, usuario");  // where empresa = "+empresa);
                }
                //resultset = statement.executeQuery("select * from login");
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
        //if (e.getSource() == jTable1){
       //   System.out.println("acao do keylistener: keyReleased");
       // }
    }

    public static void main(String args[])
        {
        JFrame form = new Login(null, 0);
            form.setVisible(true);
    }
}
