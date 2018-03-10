/*

Descrição: Frente de Caixa. Utilizada para Registrar os Produtos no Caixa da Loja e
            emissão da NFCe

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura;
import br.com.videoaulasneri.adelcio.fatura.bean.DescontoVO;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.ImportaProduto;
import br.com.videoaulasneri.adelcio.fatura.bean.ProdutoVO;
import br.com.videoaulasneri.adelcio.fatura.bean.VendaDetalheVO;
import br.com.videoaulasneri.adelcio.fatura.bean.SangriaVO;
import br.com.videoaulasneri.adelcio.fatura.bean.SuprimentoVO;
import br.com.videoaulasneri.adelcio.fatura.bean.Ped65VO;
import br.com.videoaulasneri.adelcio.fatura.bean.Prod65VO;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.AutorizacaoTEF;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.CancelaItem;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.DocumentoCliente;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.EfetuaPagamento;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.LoginGerenteSupervisor;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.PedidosPendentes;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.ValorQtde;
import br.com.videoaulasneri.adelcio.fatura.dialog.view.ValorReal;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import br.com.videoaulasneri.adelcio.nfefacil.NFefacil;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_assinatura;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getCaminho_nfeprot;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getFile_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getSenha_keystore;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getStc_senhaCert;
import static br.com.videoaulasneri.adelcio.nfefacil.NFefacil.getTipoAmbiente;
import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import br.com.videoaulasneri.adelcio.utilitarios.ImpressaoNFCe;
import br.com.videoaulasneri.adelcio.utilitarios.GeraXML_nfce;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.MontarEnviarNFe;
import br.com.videoaulasneri.adelcio.nfeweb.eventos.StatusServico;
import br.com.videoaulasneri.adelcio.nfeweb.util.ConstantesUtil;
import br.com.videoaulasneri.adelcio.relatorios.RelLeituraX;
import br.com.videoaulasneri.adelcio.utilitarios.XMLReaderUmaTag;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
public class NFCe extends javax.swing.JFrame {  

    /**
     * @return the totDesconto
     */
    public static double getTotDesconto() {
        return totDesconto;
    }

    /**
     * @param aTotDesconto the totDesconto to set
     */
    public static void setTotDesconto(double aTotDesconto) {
        totDesconto = aTotDesconto;
    }

    private boolean vendaIniciada = false;
    private String status, motivo;
    private ResultSet resultset;
    private Statement statement;
    private Connection connection;
    private int empresa = 0;
    private double precoUnit = 0.0;
    private int item = 0;
    private static double totCupom = 0.0;
    private static double totDesconto = 0.0;
    private double totProduto = 0;
    private ImageIcon fotoProduto;
    private static ProdutoVO produtoVO;
    public static NFCe c;
    private int codProduto = 0;
    private int pedido = 0;
    private int codCaixa, codLogin, codGerente, codMaquina, cod_forma_pgto;
    Ped65VO ped65;
    private String operador, caixa;
    NFefacil nfem2;
    private int qtdVol = 0;
    public static int statusCaixa = 0; // 0-aberto | 1-venda em andamento | 2-venda em recuperação ou importação de PV/DAV | 3-somente consulta
    private boolean habilitouMenu = false;
    private boolean gerenteAutorizou = false;
    private String resultado = "";
    private XMLReaderUmaTag lerxml;
    private String UFEmit = "41";
    private String ultimo_lote = "";
    private DefaultListModel modelBobina;
    private String descProduto;
    boolean status_servico = false;
    private String arq_saida_xml = "";
    boolean digitouEAN = false;
    String nome_arq_assinado = "";
    String wtpAmb = "1", wtipoEmissao = "1";
    String fuso_horario = "";
    String contatoCupom = "Porto Informatica Ltda - portoinfo@sercomtel.com.br";
    String uriCupom = "http://www.sped.fazenda.pr.gov.br/modules/conteudo/conteudo.php?conteudo=100";
    String informarCnpjCartao = "N";
    String cupomNaTela = "N";
    boolean display = false;
    String nomeArquivo = "";
    String arq_assinado = "";
    boolean semCSC = false;
    String token_csc = "";
    String alterar_qtde = "N";
    String fsep = System.getProperty("file.separator");
    Image image = null;
    File f = null;
    String nome_empresa, endereco_empresa;
    boolean temVdaCartao = false;
    String eAutorizacaoTEF, eCnpjOperadora;
    private String serie;

    public NFCe(Connection connection, int empresa, String operador, String caixa, 
            String fuso_horario, String UFEmit, String nome_empresa, String endereco_empresa,
            String contatoCupom, String uriCupom, String informarCnpjCartao, String cupomNaTela) {
        this.connection     = connection;
        this.empresa        = empresa;
        this.operador       = operador.trim();
        this.caixa          = caixa.trim();
        this.fuso_horario   = fuso_horario;
        this.UFEmit         = UFEmit;
        this.nome_empresa   = nome_empresa;
        this.endereco_empresa = endereco_empresa;
        this.contatoCupom   = contatoCupom;
        this.uriCupom       = uriCupom;
        this.informarCnpjCartao = informarCnpjCartao;
        this.cupomNaTela     = cupomNaTela;

////  inicio de desativacao do efeito LookAndFeel para botoes arredondados           
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");  //("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  //  
//            SwingUtilities.updateComponentTreeUI(jPanel1);
//        } catch (Exception ex) {
//            System.out.println("erro ao setar lookandfeel. Erro: " + ex);
//        }
////  fim de desativacao do efeito LookAndFeel para botoes arredondados           

        initComponents();

// //  inicio altera cor-botao       
//        java.awt.Component[] cpcount = jPanel1.getComponents();
//        for(int i=0;i<cpcount.length;i++) {
//            if(cpcount[i] instanceof javax.swing.JButton) {
//                cpcount[i].setBackground(new Color(154, 255, 153));
//                System.out.println("JButton: " + cpcount[i].getName());
//            }
//        }
//        cpcount = jPMenuAux.getComponents();
//        for(int i=0;i<cpcount.length;i++) {
//            if(cpcount[i] instanceof javax.swing.JButton) {
//                cpcount[i].setBackground(new Color(154, 255, 153));
//                System.out.println("JButton: " + cpcount[i].getName());
//            }
//        }
//        jBProcuraProduto    .setBackground(new Color(154, 255, 153));
//        jBTotDesconto       .setBackground(new Color(154, 255, 153));
//        jBSomaQtde          .setBackground(new Color(154, 255, 153));
// //  fim altera cor-botao       
    System.out.println("NFCe - fuso horario: "+this.fuso_horario);  
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamTela = kit.getScreenSize();
        int larg = tamTela.width;
        int alt = tamTela.height;
        
        int minhaLargura= (int) (larg);
        int minhaAltura = (int) (alt);
        setSize(minhaLargura,minhaAltura);
        setLocation(0,0);
        setResizable(false);
        jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
        jLOperador.setText("Operador: "+this.operador);
        jLCaixa.setText("Caixa: "+this.caixa);
        jTDescricao.setEditable(false);
        jTTotProd.setEditable(false);
        jTTotDesc.setEditable(false);
        jTNumPedido.setEditable(false);
        jTTotCupom.setEditable(false);
        habilitarProduto(false, true);
        jPMenuAux.setVisible(false);
        inicializarBotoes(false);
        jBIniciar.requestFocus();
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Logger.getLogger(NFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelBobina = new DefaultListModel();
        bobina.setModel(modelBobina);
        programarTeclas();
        status_servico = check_status_servico();

        setCodLogin(pegaCodLogin());
        setCodCaixa(pegaCodCaixa());
        processarIniciarVenda();
        jTQtde.setEnabled(false);
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Nao");
        UIManager.put("OptionPane.okButtonText", "Sim");
        UIManager.put("OptionPane.cancelButtonText", "Nao");
        jTEAN.requestFocus();
        token_csc = pegaTokenCSC();
        if (token_csc == null || token_csc.length() < 15 ) {
            semCSC = true;
            JOptionPane.showMessageDialog(null, 
                    "Antes de uilizar esta Tela, o Administrador do Sistema precisa Informar \n"
                    + "corretamente o Id.Token e o codigo CSC do Emitente no Cad.de Param.Digitais.");
        }
        //jBMALeituraX.setVisible(false);
        jLAmbiente.setText(nfem2.getLb_ambiente().getText());
        if (jLAmbiente.getText().indexOf("PRODU") == -1) {
            jLAmbiente.setForeground(Color.RED);
        }
        

    }
    public boolean check_status_servico(){
        jLStatusServico.setForeground(Color.blue);
        jLStatusServico.setText("Aguarde! Verificando o status. . .");
        String msg_stat = "";
        boolean retorno = false;
        try {
            if (Biblioteca.check_certificados(nfem2.getFile_keystore(), nfem2.getFile_truststore())) {
                StatusServico statServ = new StatusServico(
                    nfem2.getFile_keystore(), 
                    nfem2.getSenha_keystore(), 
                    nfem2.getTipoAmbiente(), 
                    Biblioteca.pegaEstado(UFEmit)
                );
                resultado = statServ.processar(ConstantesUtil.NFCE);
                if (resultado != null && resultado.indexOf("-") != -1){
                    status = resultado.substring(0,resultado.indexOf("-"));
                    motivo = resultado.substring(resultado.indexOf("-")+1,resultado.length());
                }
                if ( status.equals("107") ){
                    jLStatusServico.setForeground(new java.awt.Color(51, 204,0));   //Color.green);
                    msg_stat = motivo;
                    retorno = true;
                }else{
                    jLStatusServico.setForeground(Color.red);
                    if (status.equals("")){
                        msg_stat = "Emissão em Contingencia . . .";
                    }else{
                        msg_stat = status+" - "+motivo;
                        if (motivo.indexOf("em opera") == -1)
                            JOptionPane.showMessageDialog(null, "Status do Servico: "+status+" - Motivo: "+motivo);
                    }
                }

            } else {
                jLStatusServico.setForeground(Color.red);
                msg_stat = "Não conseguiu verificar Status do Serviço . . .";
            }
            if (status.equals("107")) {  //  107 = em operacao
                wtipoEmissao = "1";
            } else {
                wtipoEmissao = "9";
            }
            int tamtxt = msg_stat.length();
            int i = 0;
            for ( i=0; i<(30-tamtxt);i++) {msg_stat = " "+msg_stat;}
            jLStatusServico.setText(msg_stat.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar o status do serviço!");
        }
        return retorno;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBCpfCnpj = new javax.swing.JButton();
        jBFinalizar = new javax.swing.JButton();
        jBCancItem = new javax.swing.JButton();
        jBIniciar = new javax.swing.JButton();
        jBCancVenda = new javax.swing.JButton();
        jBMenu = new javax.swing.JButton();
        jBSair = new javax.swing.JButton();
        jBEAN = new javax.swing.JButton();
        jLEAN = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPFoto = new javax.swing.JPanel();
        jLFoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLStatusServico = new javax.swing.JLabel();
        jLAmbiente = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLOperador = new javax.swing.JLabel();
        jLData = new javax.swing.JLabel();
        jLCaixa = new javax.swing.JLabel();
        jLHora = new javax.swing.JLabel();
        jTEAN = new javax.swing.JTextField();
        jTDescricao = new javax.swing.JTextField();
        jTQtde = new javax.swing.JTextField();
        jTPreco = new javax.swing.JTextField();
        jTTotProd = new javax.swing.JTextField();
        jTUnid = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTNumPedido = new javax.swing.JTextField();
        jPMenuAux = new javax.swing.JPanel();
        jBMASuprim = new javax.swing.JButton();
        jBMASangria = new javax.swing.JButton();
        jBMAPPend = new javax.swing.JButton();
        jBMACalc = new javax.swing.JButton();
        jBMAFechar = new javax.swing.JButton();
        jBMAAbreCaixa = new javax.swing.JButton();
        jBMALeituraX = new javax.swing.JButton();
        jBMAVazio = new javax.swing.JButton();
        jBProcuraProduto = new javax.swing.JButton();
        jBSomaQtde = new javax.swing.JButton();
        jLTotCupom = new javax.swing.JLabel();
        jTTotCupom = new javax.swing.JTextField();
        panelBobina = new javax.swing.JScrollPane();
        bobina = new javax.swing.JList();
        jLDocCli = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLStatusVda = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTTotDesc = new javax.swing.JTextField();
        jBTotDesconto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FRENTE DE CAIXA");
        setBackground(new java.awt.Color(204, 204, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(1509, 52));

        jBCpfCnpj.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCpfCnpj.setForeground(new java.awt.Color(0, 0, 255));
        jBCpfCnpj.setText("[F4]C P F / C N P J");
        jBCpfCnpj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCpfCnpjActionPerformed(evt);
            }
        });

        jBFinalizar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBFinalizar.setForeground(new java.awt.Color(0, 0, 255));
        jBFinalizar.setText("[F8]FINALIZAR");
        jBFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarActionPerformed(evt);
            }
        });

        jBCancItem.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCancItem.setForeground(new java.awt.Color(0, 0, 255));
        jBCancItem.setText("[F5]CANCELA ITEM");
        jBCancItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancItemActionPerformed(evt);
            }
        });

        jBIniciar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBIniciar.setForeground(new java.awt.Color(0, 0, 255));
        jBIniciar.setText("[ENTER]INICIAR VENDA");
        jBIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIniciarActionPerformed(evt);
            }
        });

        jBCancVenda.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCancVenda.setForeground(new java.awt.Color(0, 0, 255));
        jBCancVenda.setText("[F6]CANCELA VDA");
        jBCancVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancVendaActionPerformed(evt);
            }
        });

        jBMenu.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMenu.setForeground(new java.awt.Color(0, 0, 255));
        jBMenu.setText("[F7]Menu");
        jBMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMenuActionPerformed(evt);
            }
        });

        jBSair.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSair.setForeground(new java.awt.Color(0, 0, 255));
        jBSair.setText("[F10]SAIR");
        jBSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSairActionPerformed(evt);
            }
        });

        jBEAN.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEAN.setForeground(new java.awt.Color(0, 0, 255));
        jBEAN.setText("[F9]EAN");
        jBEAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEANActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jBIniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCpfCnpj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCancItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCancVenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBFinalizar)
                .addGap(18, 18, 18)
                .addComponent(jBEAN, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBSair, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jBSair, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBCpfCnpj, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBCancItem, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBCancVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addComponent(jBEAN, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 677, 1040, -1));

        jLEAN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLEAN.setForeground(new java.awt.Color(224, 223, 202));
        jLEAN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLEAN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, 164, 31));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Descrição do Produto:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 222, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Quantidade:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 118, 31));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Preço Unitário:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 164, 23));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Total do Produto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 134, 29));

        jPFoto.setBackground(new java.awt.Color(102, 102, 102));
        jPFoto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPFoto.setToolTipText("Foto do Produto");

        jLFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfoto.JPG"))); // NOI18N
        jLFoto.setToolTipText("Foto do Produto");

        javax.swing.GroupLayout jPFotoLayout = new javax.swing.GroupLayout(jPFoto);
        jPFoto.setLayout(jPFotoLayout);
        jPFotoLayout.setHorizontalGroup(
            jPFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        );
        jPFotoLayout.setVerticalGroup(
            jPFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        );

        getContentPane().add(jPFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 195, 270, 240));

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FRENTE DE CAIXA");

        jLStatusServico.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLStatusServico.setForeground(new java.awt.Color(0, 0, 255));
        jLStatusServico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLStatusServico.setText("Status do Serviço");

        jLAmbiente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLAmbiente.setForeground(new java.awt.Color(0, 0, 255));
        jLAmbiente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAmbiente.setText("Ambiente");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLStatusServico, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                    .addComponent(jLAmbiente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLAmbiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLStatusServico))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("jLabel1");

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 674, 70));

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLOperador.setBackground(new java.awt.Color(204, 204, 204));
        jLOperador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLOperador.setForeground(new java.awt.Color(0, 0, 255));
        jLOperador.setText("Operador:");
        jLOperador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLData.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLData.setForeground(new java.awt.Color(0, 0, 255));
        jLData.setText("Data:");
        jLData.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLCaixa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLCaixa.setForeground(new java.awt.Color(0, 0, 255));
        jLCaixa.setText("Caixa:");
        jLCaixa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLHora.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLHora.setForeground(new java.awt.Color(0, 0, 255));
        jLHora.setText("Hora:");
        jLHora.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLOperador, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(jLCaixa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLHora, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLData)
                    .addComponent(jLOperador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLCaixa)
                    .addComponent(jLHora))
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 14, 350, 70));

        jTEAN.setBackground(new java.awt.Color(238, 237, 236));
        jTEAN.setColumns(13);
        jTEAN.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTEAN.setForeground(new java.awt.Color(224, 223, 227));
        jTEAN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEANFocusLost(evt);
            }
        });
        jTEAN.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTEANCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTEANInputMethodTextChanged(evt);
            }
        });
        jTEAN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTEANActionPerformed(evt);
            }
        });
        getContentPane().add(jTEAN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 173, 38));

        jTDescricao.setBackground(new java.awt.Color(255, 248, 182));
        jTDescricao.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTDescricao.setForeground(new java.awt.Color(0, 0, 255));
        jTDescricao.setText("teste de descricao do produto");
        getContentPane().add(jTDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 580, 50));

        jTQtde.setBackground(new java.awt.Color(255, 255, 153));
        jTQtde.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTQtde.setForeground(new java.awt.Color(0, 0, 255));
        jTQtde.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(jTQtde, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 66, -1));

        jTPreco.setBackground(new java.awt.Color(255, 248, 182));
        jTPreco.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTPreco.setForeground(new java.awt.Color(0, 0, 255));
        jTPreco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        getContentPane().add(jTPreco, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 119, -1));

        jTTotProd.setBackground(new java.awt.Color(255, 248, 182));
        jTTotProd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTTotProd.setForeground(new java.awt.Color(0, 0, 255));
        jTTotProd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTTotProdActionPerformed(evt);
            }
        });
        getContentPane().add(jTTotProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 119, -1));

        jTUnid.setBackground(new java.awt.Color(255, 255, 153));
        jTUnid.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTUnid.setForeground(new java.awt.Color(0, 0, 255));
        getContentPane().add(jTUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 45, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Numero Pedido:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 411, 113, 29));

        jTNumPedido.setBackground(new java.awt.Color(255, 248, 182));
        jTNumPedido.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTNumPedido.setForeground(new java.awt.Color(0, 0, 255));
        jTNumPedido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        getContentPane().add(jTNumPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 119, -1));

        jPMenuAux.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBMASuprim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMASuprim.setForeground(new java.awt.Color(0, 0, 255));
        jBMASuprim.setText("[F1]SUPRIMENTO");
        jBMASuprim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMASuprimActionPerformed(evt);
            }
        });

        jBMASangria.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMASangria.setForeground(new java.awt.Color(0, 0, 255));
        jBMASangria.setText("[F2]SANGRIA");
        jBMASangria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMASangriaActionPerformed(evt);
            }
        });

        jBMAPPend.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMAPPend.setForeground(new java.awt.Color(0, 0, 255));
        jBMAPPend.setText("[F3]Reimpressão");
        jBMAPPend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMAPPendActionPerformed(evt);
            }
        });

        jBMACalc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMACalc.setForeground(new java.awt.Color(0, 0, 255));
        jBMACalc.setText("[F4]CALCULADORA");
        jBMACalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMACalcActionPerformed(evt);
            }
        });

        jBMAFechar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMAFechar.setForeground(new java.awt.Color(0, 0, 255));
        jBMAFechar.setText("[F7]FECHAR");
        jBMAFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMAFecharActionPerformed(evt);
            }
        });

        jBMAAbreCaixa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBMAAbreCaixa.setForeground(new java.awt.Color(0, 0, 255));
        jBMAAbreCaixa.setText("[F5]Abre Caixa");
        jBMAAbreCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMAAbreCaixaActionPerformed(evt);
            }
        });

        jBMALeituraX.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBMALeituraX.setForeground(new java.awt.Color(0, 0, 255));
        jBMALeituraX.setText("[F6]Leitura X");
        jBMALeituraX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMALeituraXActionPerformed(evt);
            }
        });

        jBMAVazio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBMAVazio.setForeground(new java.awt.Color(0, 0, 255));
        jBMAVazio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMAVazioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPMenuAuxLayout = new javax.swing.GroupLayout(jPMenuAux);
        jPMenuAux.setLayout(jPMenuAuxLayout);
        jPMenuAuxLayout.setHorizontalGroup(
            jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMenuAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBMAAbreCaixa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBMASuprim, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBMALeituraX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBMASangria, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPMenuAuxLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jBMAPPend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPMenuAuxLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jBMAVazio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBMAFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBMACalc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPMenuAuxLayout.setVerticalGroup(
            jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPMenuAuxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBMASuprim, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMACalc, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMASangria, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMAPPend, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPMenuAuxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBMAAbreCaixa, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMAFechar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMALeituraX, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBMAVazio, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPMenuAux, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 543, -1, -1));

        jBProcuraProduto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBProcuraProduto.setForeground(new java.awt.Color(0, 0, 255));
        jBProcuraProduto.setText("[F1]PROCURA PRODUTO");
        jBProcuraProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProcuraProdutoActionPerformed(evt);
            }
        });
        getContentPane().add(jBProcuraProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, -1, -1));

        jBSomaQtde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBSomaQtde.setForeground(new java.awt.Color(0, 0, 255));
        jBSomaQtde.setText("[F2] Altera Qtde");
        jBSomaQtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSomaQtdeActionPerformed(evt);
            }
        });
        getContentPane().add(jBSomaQtde, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 210, -1));

        jLTotCupom.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLTotCupom.setForeground(new java.awt.Color(0, 0, 255));
        jLTotCupom.setText("Total :");
        getContentPane().add(jLTotCupom, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 610, -1, -1));

        jTTotCupom.setBackground(new java.awt.Color(255, 248, 182));
        jTTotCupom.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jTTotCupom.setForeground(new java.awt.Color(0, 0, 255));
        jTTotCupom.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotCupom.setText("0.00");
        jTTotCupom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTTotCupomActionPerformed(evt);
            }
        });
        getContentPane().add(jTTotCupom, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 601, 208, 60));

        bobina.setBackground(new java.awt.Color(255, 248, 182));
        bobina.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        bobina.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        bobina.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        panelBobina.setViewportView(bobina);

        getContentPane().add(panelBobina, new org.netbeans.lib.awtextra.AbsoluteConstraints(621, 130, 430, 400));

        jLDocCli.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLDocCli.setForeground(new java.awt.Color(0, 0, 255));
        jLDocCli.setText("Documento:");
        getContentPane().add(jLDocCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 516, 664, 21));

        jLStatusVda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLStatusVda.setForeground(new java.awt.Color(0, 0, 153));
        jLStatusVda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLStatusVda.setText("Venda Aberta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLStatusVda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLStatusVda, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 87, 289, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Desconto:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 550, 134, 40));

        jTTotDesc.setBackground(new java.awt.Color(255, 248, 182));
        jTTotDesc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTTotDesc.setForeground(new java.awt.Color(0, 0, 255));
        jTTotDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTTotDescActionPerformed(evt);
            }
        });
        getContentPane().add(jTTotDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(839, 550, 210, 40));

        jBTotDesconto.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jBTotDesconto.setForeground(new java.awt.Color(0, 0, 255));
        jBTotDesconto.setText("[F11]Desconto");
        jBTotDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTotDescontoActionPerformed(evt);
            }
        });
        getContentPane().add(jBTotDesconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 463, 220, 50));

        setSize(new java.awt.Dimension(1139, 780));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBCancItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancItemActionPerformed
        processarCancelarItem();

    }//GEN-LAST:event_jBCancItemActionPerformed

    private void jBCpfCnpjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCpfCnpjActionPerformed
        processarCpfCnpj();

    }//GEN-LAST:event_jBCpfCnpjActionPerformed

    private void jBFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinalizarActionPerformed
        if (checkCSC()) {
            processarFinalizarVenda();
        }
    }//GEN-LAST:event_jBFinalizarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date dataTela = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        jLData.setText("Data: "+formato.format(dataTela));
        Timer timer = new Timer(1000, new hora());
        timer.start();
    }//GEN-LAST:event_formWindowOpened

    private void jBIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIniciarActionPerformed
        //JOptionPane.showMessageDialog(null, "Clicou em: INICIAR VENDA . . .");
        habilitarProduto(false, true);
        processarIniciarVenda();

    }//GEN-LAST:event_jBIniciarActionPerformed

    private void jBCancVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancVendaActionPerformed
        processarCancelarVenda();

    }//GEN-LAST:event_jBCancVendaActionPerformed

    private void jBMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMenuActionPerformed
        processarAbrirMenu();
    }//GEN-LAST:event_jBMenuActionPerformed

    private void jBMAPPendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMAPPendActionPerformed
        processarPedPendentes();

    }//GEN-LAST:event_jBMAPPendActionPerformed

    private void jBMAFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMAFecharActionPerformed
        processarFecharMenu();

    }//GEN-LAST:event_jBMAFecharActionPerformed


    private void jBMASuprimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMASuprimActionPerformed
        processarSuprimento();

    }//GEN-LAST:event_jBMASuprimActionPerformed

    private void jBMASangriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMASangriaActionPerformed
        processarSangria();

    }//GEN-LAST:event_jBMASangriaActionPerformed

    private void jBMACalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMACalcActionPerformed
        processarCalc();

    }//GEN-LAST:event_jBMACalcActionPerformed


    private void jBSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSairActionPerformed
        processarSair();

    }//GEN-LAST:event_jBSairActionPerformed

    private void jTEANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTEANActionPerformed
        if (checkCSC()) {
            trataEAN();
        }
    }//GEN-LAST:event_jTEANActionPerformed

    private void jBProcuraProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProcuraProdutoActionPerformed
        if (checkCSC()) {
            procurarProduto();
        }
    }//GEN-LAST:event_jBProcuraProdutoActionPerformed

    private void jBSomaQtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSomaQtdeActionPerformed
        processarQtde();

    }//GEN-LAST:event_jBSomaQtdeActionPerformed


    private void jTEANFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEANFocusLost

    }//GEN-LAST:event_jTEANFocusLost

    private void jTEANCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTEANCaretPositionChanged
    }//GEN-LAST:event_jTEANCaretPositionChanged

    private void jTEANInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTEANInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTEANInputMethodTextChanged

    private void jBMAAbreCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMAAbreCaixaActionPerformed
        processarAbrirCaixa();
    }//GEN-LAST:event_jBMAAbreCaixaActionPerformed

    private void jBMALeituraXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMALeituraXActionPerformed
        processarLeituraX();
    }//GEN-LAST:event_jBMALeituraXActionPerformed

    private void jTTotCupomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTTotCupomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTTotCupomActionPerformed

    private void jTTotProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTTotProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTTotProdActionPerformed

    private void jBEANActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEANActionPerformed
        digitarEAN();
    }//GEN-LAST:event_jBEANActionPerformed

    private void jTTotDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTTotDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTTotDescActionPerformed

    private void jBTotDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTotDescontoActionPerformed
        processarDesconto();
    }//GEN-LAST:event_jBTotDescontoActionPerformed

    private void jBMAVazioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMAVazioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBMAVazioActionPerformed

    private void atualizarCupom(String texto) {
        modelBobina.addElement(texto);
        bobina.setSelectedIndex(modelBobina.getSize() - 1);
        bobina.ensureIndexIsVisible(modelBobina.getSize() - 1);

    }
    private void iniciarVenda() {
        jPMenuAux.setVisible(false);
        vendaIniciada = true;
        jBIniciar.setEnabled(false);
        jLStatusVda.setText("Venda Aberta");
        habilitarProduto(true, true);
        item = 0;
        setQtdVol(0);
        setTotCupom(0.0);
        setTotDesconto(0.0);
        modelBobina.addElement("\n*------ Venda Aberta ------*\n=========================\n");
        modelBobina.addElement(Biblioteca.repete("*", 48));
        inicializarBotoes(true);
        setPedido(pegarProxPedido());
        salvarPedido();
        modelBobina.clear();
        jLDocCli.setText("");
        arq_saida_xml = "";
        jTQtde.setEnabled(false);
        temVdaCartao = false;
        eAutorizacaoTEF = null;
        eCnpjOperadora = null;
        jTEAN.setCaretColor(jTEAN.getBackground());
        jTEAN.requestFocus();
    }
    private void informarCpfCnpj() {
        if (item == 0) {
            JOptionPane.showMessageDialog(null, "Para Informar o Documento/Nome, Registre pelo menos 1 Item!");
        } else {
            if (chamarJanelaDoccli()) {
                try {
                    String sql_update = "update pedidos65 set "
                        +"documento = '"+ped65.getDocumento()+"', "
                        +"nome_documento = '"+ped65.getNome_documento()+"' "
                        +"where pedido = "+getPedido()+" and empresa = "+this.empresa;
                    statement.executeUpdate(sql_update);
                    jLDocCli.setText("Documento: "+ped65.getDocumento());
                    if (ped65.getNome_documento() != null) {
                        jLDocCli.setText(jLDocCli.getText()+" - "+ped65.getNome_documento());
                    }
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o documento no pedido ["+getPedido()+"] ! Erro: "+e);
                }
            }
        }
        jTEAN.requestFocus();
    }

    private boolean chamarJanelaDoccli() {
        boolean retorno = false;
        DocumentoCliente janelaDoccli = new DocumentoCliente(this, true, ped65.getDocumento(), ped65.getNome_documento());
        janelaDoccli.setLocationRelativeTo(null);
        janelaDoccli.setVisible(true);
        String eDocNomeCliente = janelaDoccli.validaDados();
        if ( eDocNomeCliente != null && eDocNomeCliente.indexOf("*") != -1) {
            String eDocumento = eDocNomeCliente.substring(0, eDocNomeCliente.indexOf("*"));
            String eNomeCliente = eDocNomeCliente.substring(eDocNomeCliente.indexOf("*")+1, eDocNomeCliente.length());
            ped65.setDocumento(eDocumento);
            ped65.setNome_documento(eNomeCliente);
           retorno = true;
        }
        return retorno;
      }
    private void informarAutTEF() {
        if (chamarJanelaAutTEF()) {
            try {
                String sql_update = "update pedidos65 set "
                    +"docAutTEF = '"+ped65.getDocAutTEF()+"', "
                    +"cnpjTEF = '"+ped65.getCnpjTEF()+"' "
                    +"where pedido = "+getPedido()+" and empresa = "+this.empresa;
                statement.executeUpdate(sql_update);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar os dados do cartão no pedido ["+getPedido()+"] ! Erro: "+e);
            }
        }
    }

    private boolean chamarJanelaAutTEF() {
        boolean retorno = false;
        while (true) {

            AutorizacaoTEF janelaAutTEF = new AutorizacaoTEF(this, true, ped65.getDocAutTEF(), ped65.getCnpjTEF());
            janelaAutTEF.setLocationRelativeTo(this);
            janelaAutTEF.setVisible(true);
            String eAutCnpj = janelaAutTEF.validaDados();
            if ( eAutCnpj != null ) {
                if (eAutCnpj.contains("*")) {
                    eAutorizacaoTEF = eAutCnpj.substring(0, eAutCnpj.indexOf("*"));
                    eCnpjOperadora = eAutCnpj.substring(eAutCnpj.indexOf("*")+1, eAutCnpj.length());
                    ped65.setDocAutTEF(eAutorizacaoTEF);
                    ped65.setCnpjTEF(eCnpjOperadora);
                    retorno = true;
                    break;
                } else if (!eAutCnpj.equals("invalido")) {
                    
                }
            } else {
                break;
            }
        }
        return retorno;
      }
    private void cancelarItem() {
        String itemCancStr = ""+item;
        while (true) {
            CancelaItem cancItem = new CancelaItem(this, true, item);
            cancItem.setLocationRelativeTo(null);
            cancItem.setVisible(true);
            itemCancStr = cancItem.getItem();
            if (!cancItem.cancelado) {
                int itemCancelado = Integer.parseInt(itemCancStr);
                String sql_query = "select cancelado, vlr_total from produtos65 where pedido = "+getPedido()+" and item = "+itemCancelado;
                boolean achouItem = false, jaCancelado = false;
                try {
                    resultset = statement.executeQuery(sql_query);
                    while (resultset.next()) {
                        achouItem = true;
                        jaCancelado = resultset.getBoolean("cancelado");
                        totProduto = resultset.getDouble("vlr_total");
                        setQtdVol(getQtdVol() - resultset.getInt("quantidade"));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar executar a Sql_query para a tabela pedidos65: "+e);
                    break;
                }
                if (!achouItem) {
                    JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O ITEM ["+itemCancStr+"] NÃO FOI ENCONTRADO!");
                } else if (jaCancelado) {
                    JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O ITEM ["+itemCancStr+"] JÁ ESTÁ CANCELADO!");
                } else {
                   UIManager.put("OptionPane.okButtonText", "Ok");
                   UIManager.put("OptionPane.cancelButtonText", "Desiste");
                    if (JOptionPane.showConfirmDialog(null, "Confirma o Cancelamento do Item [ "+itemCancStr+" ] ?","Cancelamento do Item", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            String sql_update = "update produtos65 set "
                                    +"cancelado = true "
                                    +"where pedido = "+getPedido()+" and item = "+itemCancelado;
                            statement.executeUpdate(sql_update);
                            totalizarCupom("tira");
                            atualizarPedido("item");
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao tentar cancelar o Item ["+itemCancelado+"] do Pedido ["+getPedido()+"] ! Erro: "+e);
                        }
                        modelBobina.addElement(Biblioteca.repete("*", 48));
                        atualizarCupom("ITEM "+itemCancStr+" CANCELADO\n");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "*** ATENÇÃO ***  Voce desistiu e o Item NÃO foi Cancelado!");
                        break;
                    }
                }
            }
        }
    }
    private void cancelarVenda() {
           UIManager.put("OptionPane.okButtonText", "Ok");
           UIManager.put("OptionPane.cancelButtonText", "Desiste");
            try {
                String sql_update = "update pedidos65 set "
                        +"cancelado = true "
                        +"where pedido = "+getPedido();
                statement.executeUpdate(sql_update);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o Pedido! Erro: "+e);
            }
            vendaIniciada = false;
            jBIniciar.setEnabled(true);
            jLStatusVda.setText("Venda Cancelada");
            jBMenu.setEnabled(true);
            habilitarProduto(false, true);
            item = 0;
            jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
            setQtdVol(0);
            jTTotCupom.setText("0,00");
            jTTotDesc.setText("0,00");
            modelBobina.addElement(Biblioteca.repete("*", 48));
            modelBobina.addElement("VENDA CANCELADA");
    }
    private boolean finalizarVenda() {
        boolean retorno = true;

        EfetuaPagamento telaEfetuaPagamento = new EfetuaPagamento(this, true, empresa, getPedido(), connection);
        telaEfetuaPagamento.setLocationRelativeTo(null);
        telaEfetuaPagamento.setVisible(true);
        if (telaEfetuaPagamento.pagamentoOK == true) {
            if (telaEfetuaPagamento.temVdaCartao == true && !informarCnpjCartao.equals("N")){
                informarAutTEF();
            }
            concluiEncerramentoVenda();
            vendaIniciada = false;
            jBIniciar.setEnabled(true);
            jLStatusVda.setText("Venda Finalizada");
            jBMenu.setEnabled(true);
            habilitarProduto(false, true);
            jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
            item = 0;
            setQtdVol(0);
            jTTotCupom.setText("0,00");
            jTTotDesc.setText("0,00");
            inicializarBotoes(false);
            temVdaCartao = false;
            eAutorizacaoTEF = null;
            eCnpjOperadora = null;
        } else if (!telaEfetuaPagamento.pagamentoCancelado) {
JOptionPane.showMessageDialog(null, "O Pagamento da Compra foi Cancelado!");
            retorno = false;
        }
        return retorno;
    }

    private void concluiEncerramentoVenda() {
        if (totDesconto > 0) {
            ratearDesconto();
        }
        chamarImpressaoDoCupom(""+getPedido(), false, true);
    }

    private void ratearDesconto() {
        String sql_ped = "select * from produtos65 where pedido = "+ped65.getPedido()+" and empresa = "+this.empresa;
        System.out.println("NFCe.ratearDesconto() - sql_ped: "+sql_ped);
        String sql_prod = "";
        double valor_descProd = 0.0, valor_produto = 0.0, valor_totProd = 0.0;
        try {
            resultset = statement.executeQuery(sql_ped);
            double valor_restante = totDesconto;
            while (resultset.next()) {
                valor_produto = resultset.getDouble("vlr_produto");
                int itemProd = resultset.getInt("item");
                if (itemProd == item) {  //  jogar o restante do desconto no ultimo item
                   valor_descProd =  valor_restante;
                } else {
                    valor_descProd = Double.valueOf(String.format(Locale.US, "%.2f", ((valor_produto / totCupom) * totDesconto)));
                    valor_restante -= valor_descProd;
                }
                sql_prod = "update produtos65 set "
                        + "vlr_desconto = " + valor_descProd + ", "
                        + "vlr_total = " + (valor_produto - valor_descProd) +
                        " where pedido = " + ped65.getPedido() + 
                        " and empresa = "+this.empresa +
                        " and item = " + itemProd;
         System.out.println("NFCe.ratearDesconto() - sql_prod: "+sql_prod);
               try {
                    Statement st_p65 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    int result = st_p65.executeUpdate(sql_prod);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o desconto no Item: "+itemProd+" do Pedido. Erro: "+ex);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar a consulta na tabela produtos65: "+e);
        }
    }
    private void chamarImpressaoDoCupom(String numeroPedido, boolean pegarPedido, boolean gerarXML) {
        boolean continuar = true;
        try {
            if (pegarPedido) {
                ped65 = pegarPedido(numeroPedido);
            }
           if (gerarXML) {
               UIManager.put("OptionPane.okButtonText", "Agora");
               UIManager.put("OptionPane.cancelButtonText", "Depois");
                if (JOptionPane.showConfirmDialog(null, "Autorizar e Imprimir o Cupom Agora ou Depois?","Autorização do Cupom", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
                    {
                    UIManager.put("OptionPane.okButtonText", "OK");
                    GeraXML_nfce gxml = new GeraXML_nfce(
                            nfem2.getTipoAmbiente(),
                            ped65.getData_digitacao().toString(),
                            nfem2.getStc_camLerAss(),
                            this.connection,
                            this.empresa, nfem2.getStc_drive(),
                            nfem2.getStc_anomesdia(),
                            nfem2.isStc_exibirDsp(),
                            ""+numeroPedido,
                            UFEmit,
                            UFEmit,
                            fuso_horario,
                            eAutorizacaoTEF,
                            eCnpjOperadora,
                            getSerie()
                            );
                    String tipoEmis = "1";
                    while (true) {
                        if (status_servico) {
                            tipoEmis = "1";
                            break;
                        } else {
                            UIManager.put("OptionPane.okButtonText", "Ok");
                            UIManager.put("OptionPane.cancelButtonText", "Desiste");
                             if (JOptionPane.showConfirmDialog(null, "Serviço Indisponível! Tenta Novamente? ","Verificação do Serviço", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                                status_servico = check_status_servico();
                             } else {
                                tipoEmis = "9";
                                break;
                             }
                        }
                    }
                    nomeArquivo = gxml.trataTexto("Gerando texto", tipoEmis);
                    if (nomeArquivo != null && nomeArquivo.indexOf(".xml")!= -1) {
                        if (!vaiTratarEnviaLote(nomeArquivo)) {
                            continuar = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "O arquivo XML não foi gerado e não será transmitido!!");
                        continuar = false;
                    }
                } else {
                    UIManager.put("OptionPane.okButtonText", "OK");
                    JOptionPane.showMessageDialog(null, "Voce desistiu e o Cupom NÃO Será Autorizado/Impresso Agora!!");
                    continuar = false;
                }
               UIManager.put("OptionPane.yesButtonText", "Sim");
               UIManager.put("OptionPane.noButtonText", "Nao");
            }
           if (continuar) {
//               if (temVdaCartao && !informarCnpjCartao.equals("N")) {
//                   informarAutTEF();
//               }
                imprimeCupom(ped65);
           }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "NFCe.chamarImpressaoDoCupom() - Erro: "+e);
        }
    }
    public boolean vaiTratarEnviaLote(String nomeArquivo){
        boolean retornoEnviar = false;
        try {
            int numLote = pegaNumLote();
            String[] nrecibo = new String[30];
            retornoEnviar = tratar_enviaLoteNfe(numLote, nomeArquivo);
            apaga_arquivos_temporarios(nomeArquivo, nfem2.getCaminho_grava_assinar(), "ASSINAR");
            apaga_arquivos_temporarios(arq_assinado, getCaminho_assinatura()+"\\"+"BKPNFE", "ASSINADA");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Metodo vaiTratarEnviaLote() - Erro: "+e);

        }
        return retornoEnviar;
    }
    public void apaga_arquivos_temporarios(String arq_xml, String caminho, String pasta){
        File arqudel    = new File(arq_xml);
        if (arqudel.isFile()){
            char chartxt[]  = new char[(int)arqudel.length()];
            String arq_saida_xml = caminho+"\\"+nfem2.getStc_anomesdia()+"\\"+arq_xml.substring(arq_xml.indexOf(pasta)+(pasta.length()+1));
//if (display) 
//JOptionPane.showMessageDialog(null, "NFCe.apaga_arquivos_temporarios() - Pasta: "+pasta+"  - Vai apagar arq.: "+arq_xml+" - e copiar para: "+arq_saida_xml);
            FileReader entrada;
            try {
                entrada = new FileReader(arq_xml);
                entrada.read(chartxt);  //(res);
                entrada.close();

                String texto_string = new String(chartxt);  //chartxt.toString().getBytes();
                byte[] texto_byte = texto_string.getBytes();
                FileOutputStream arquivo_gerado = new FileOutputStream(arq_saida_xml);
                arquivo_gerado.write(texto_byte);
                arquivo_gerado.close();

            } catch (Exception ex) {
                System.out.println("Erro ao processar arq.xml(apaga_arquivos_temporarios): "+ex);
            }
            try {
                arqudel.delete();
                System.out.println("Apagou o arquivo: "+arqudel);
            } catch (Exception ex) {
                System.out.println("Erro ao tentar apagar o arquivo: "+arqudel+"\nErro: "+ex);
            }
         }
    }
     public int pegaNumLote(){
        int numLote = 0;
        try {
            Statement st_nfe = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st_nfe = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select * from numerolote where empresa = "+empresa;
            ResultSet rs_nfe = st_nfe.executeQuery(sql);
            while (rs_nfe.next()) {
                numLote = Integer.parseInt(rs_nfe.getString("numeroproximolote"));
            }

            if (numLote == 0){
               JOptionPane.showMessageDialog(null, "Nao encontrou registro de num.lote para esta empresa! ");
            } else {
                String str_nlote = ""+numLote;
                numLote = Integer.parseInt(str_nlote);  //.substring(4, str_nlote.length()));
                atualiza_numLote(numLote);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler a tabela numerolote: "+ex);
            numLote = 0;
        }
if (display) JOptionPane.showMessageDialog(null, "\nPegou o numero ["+numLote+"] do Lote para Enviar . . . \n");
        return numLote;
    }
    public boolean tratar_enviaLoteNfe(int numLote, String nomeArquivo){

        boolean resultNFCe = false;
        try {
            if (display) {
JOptionPane.showMessageDialog(null,"\nNFCe.tratar_enviaLoteNfe() - Tipo de Ambiente: "+nfem2.getTipoAmbiente()+"\n");
JOptionPane.showMessageDialog(null,"\nNFCe.tratar_enviaLoteNfe() - Enviando o Lote: "+numLote+"\n");
JOptionPane.showMessageDialog(null,"\nNFCe.tratar_enviaLoteNfe() - Caminho assinatura: "+nfem2.getStc_camAssinat()+"\n");
            }
            String caminho_grava_impDanfe = nfem2.getStc_camImpdanfe();

if (display) JOptionPane.showMessageDialog(null,"Vai pegar chaveNFe do arquivo: "+nomeArquivo);
            String chNfe = Biblioteca.extrair_TAG(nomeArquivo, "infNFe", "A", "Id", 1);
            chNfe = chNfe.substring(3, chNfe.length());
if (display) JOptionPane.showMessageDialog(null,"Passou chNFe: "+chNfe);
            arq_assinado = getCaminho_assinatura()+"\\"+chNfe+"_sign.xml";
            String arq_prot     = getCaminho_nfeprot()+"\\"+nfem2.getStc_anomesdia()+"\\"+chNfe+"_prot.xml"; 
            String arq_danfe    = nfem2.getStc_camImpdanfe()+"\\"+"BKP"+"\\"+nfem2.getStc_anomesdia()+"\\"+chNfe+"_danfe.xml";
//if (display) JOptionPane.showMessageDialog(null,"arq_assinado: "+arq_assinado+" - arq_prot: "+arq_prot+" - ard_danfe: "+arq_danfe);
            if (!semCSC) {
                String cIdToken = token_csc.substring(0, 6);
                String csc = token_csc.substring(6, token_csc.length());
if (display) {
                JOptionPane.showMessageDialog(null,"MontarEnviarNFe:"
                            +"\narquivoNfe: "+nomeArquivo
                            + "\narq_assinado: "+arq_assinado 
                            + "\narq_danfe: "+arq_danfe 
                            + "\narq_prot: "+arq_prot
                            + "\ngetFile_keystore(): "+getFile_keystore() 
                            + "\ngetSenha_keystore(): "+getSenha_keystore() 
                            + "\ngetTipoAmbiente(): "+getTipoAmbiente()
                            + "\ncUF: "+UFEmit
                            + "\nNFe: "+"NFCe"
                            + "\nIdToken: "+cIdToken
                            + "\nCSC: "+csc
                            + "\nBiblioteca.pegaEstado(cUF): "+Biblioteca.pegaEstado(UFEmit)
                     + "");
    }            
                MontarEnviarNFe ass = new MontarEnviarNFe(
                    nomeArquivo, 
                    arq_assinado, 
                    arq_danfe, 
                    arq_prot,
                    getFile_keystore(), 
                    getSenha_keystore(), 
                    getTipoAmbiente(),
                    UFEmit,
                    "NFCe",
                    Biblioteca.pegaEstado(UFEmit),
                    cIdToken,
                    csc,
                    connection
                ); 

                resultNFCe = ass.processa();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NFefacil.class.getName()).log(Level.SEVERE, null, ex);
                }
if (display) 
    JOptionPane.showMessageDialog(null,"Retorno do envio da NFCe: "+resultNFCe);
            } else {
                JOptionPane.showMessageDialog(null,"Os campos idToken e CSC não estão informados corretamente no Banco de Dados(Pardigital). \nInforme o Administrador do Sistema! \nO envio desta NFCe não será feito!: "+resultNFCe);
            }
            if (!resultNFCe){
                JOptionPane.showMessageDialog(null, "Ocorreu algum erro durante a transmissão e o Cupom não foi Autorizado!");
            } else {
                String protocolo = Biblioteca.extrair_TAG(arq_prot, "infProt", "T", "nProt", 1);
                if (protocolo != null && protocolo.length() > 10) {
                   String numNFe = Biblioteca.extrair_TAG(arq_prot, "ide", "T", "nNF", 1);
                   String serNFe = Biblioteca.extrair_TAG(arq_prot, "ide", "T", "serie", 1);
                   String sql_update = "update pedidos65 set"
                        +" numero_nfe = " + numNFe + ", "
                        +" serienfe = '" + serNFe + "' "
                        +" where pedido = "+pedido+" "
                        +" and empresa = "+empresa;
    if (display) JOptionPane.showMessageDialog(null,"Comando sql_update(pedidos65): "+sql_update);
                    Statement stateUpFat = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    int result = stateUpFat.executeUpdate(sql_update);
                    if (display) JOptionPane.showMessageDialog(null,"Tabela pedidos65 atualizado com numeronf: "+numNFe);
                }               
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Metodo: tratar_enviaLoteNfe() - Erro durante a transmissao: "+e);

        }
        return resultNFCe;
    }
    public void gravar_retorno(String nome_arquivo, String texto_retornado){
        try
        {
             byte [] texto = texto_retornado.getBytes();
             FileOutputStream arquivo_gerado = new FileOutputStream(nome_arquivo);
             arquivo_gerado.write(texto);
             arquivo_gerado.close();
        }
        catch(IOException erro_arquivo)
        {
            JOptionPane.showMessageDialog(null, "Não foi possível gravar esse arquivo: "+erro_arquivo);
        }

    }
    public void atualiza_numLote( int numLote){
        String sql = "update numerolote set numeroproximolote = "+(numLote+1)+" where empresa = "+empresa;
        try {
            Statement st_nfe = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int result = st_nfe.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o Num.do Lote. Erro: "+ex);
        }
    }
    private String pega_cDest(String chNFCe) {
        String retorno = null;
        try {
            String sql_cdest = "select * from nf"
                    +" where "
                    +" chave_nfe = '"+chNFCe+"'";
            Statement st_cdest = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs_cdest = st_cdest.executeQuery(sql_cdest);
            while(rs_cdest.next()){
                retorno = rs_cdest.getString("documento").trim();
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"pega_cDest() - Erro ao tentar ler tabela NF para capturar Docto do Destinatario. Erro: "+e);
        }
        return retorno;
    }
    private String pegaTokenCSC() {
        String retorno = "";
        try {
            String sql_param = "select * from pardigital"
                    +" where "
                    +" empresa = "+empresa;
            Statement st_param = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs_param = st_param.executeQuery(sql_param);
            while(rs_param.next()){
                if (nfem2.getTipoAmbiente().equals("2")) {  //  homologacao
                    retorno = rs_param.getString("idtoken")+rs_param.getString("csc");
                } else {  //  producao
                    retorno = rs_param.getString("idtoken")+rs_param.getString("csc_prod");
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"pegaTokenCSC() - Erro ao tentar ler tabela Pardigital. Erro: "+e);
        }

        return retorno;
    }
    private void imprimeCupom(Ped65VO ped65) {
        String sql_query = "select * from nf where pedido = "+ped65.getPedido()+" and empresa = "+this.empresa;
        boolean achouNf = false;
        try {
            resultset = statement.executeQuery(sql_query);
            while (resultset.next()) {
                achouNf = true;
                ped65.setTpemis(resultset.getString("tpemis"));
                ped65.setTpamb(resultset.getString("tpamb"));
                ped65.setQrcode(resultset.getString("qrcode"));
                ped65.setChave_nfe(resultset.getString("chave_nfe"));
                ped65.setData_emissao(resultset.getDate("data_emissao"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar a consulta na tabela nf: "+e);
        }
        if (!achouNf) {
            JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O CUPOM DO PEDIDO ["+ped65.getPedido()+"] NÃO FOI ENCONTRADO!");
        }
        if (arq_saida_xml == null || arq_saida_xml.equals("")) {
            String dataStr = ped65.getData_emissao().toString();
            String anomesdiaEmi = dataStr.substring(0, 4)+dataStr.substring(5, 7)+dataStr.substring(8, 10);
            String chNFCe = ped65.getChave_nfe().replace(" ", "");
            arq_saida_xml = nfem2.getCaminho_nfeprot()+"\\"+anomesdiaEmi+"\\"+chNFCe+"_prot.xml";  //"C:\\NFCe\\XML Automaq\\41161025258952000130650050000000081000000083-nfe.xml";
if (display) JOptionPane.showMessageDialog(null, "NFCe.imprimeCupom() - arq_saida_xml: "+arq_saida_xml);
        }
        String tipovia = "VIA CONSUMIDOR";
        boolean gerarPDF = false;
        ImpressaoNFCe impr = new ImpressaoNFCe(empresa, "NFCe.jasper", connection, ped65.getPedido(), ped65.getQrcode(), tipovia, 
                jLCaixa.getText().substring(7, jLCaixa.getText().length()), arq_saida_xml, contatoCupom, uriCupom, gerarPDF, cupomNaTela);
        impr.imprimeRelJasper();
        if (!status_servico) {  
            tipovia = "VIA ESTABELECIMENTO";
            impr = new ImpressaoNFCe(
                    empresa, 
                    "NFCe.jasper", 
                    connection, 
                    ped65.getPedido(), 
                    ped65.getQrcode(), 
                    tipovia, 
                    jLCaixa.getText().substring(7, jLCaixa.getText().length()),
                    arq_saida_xml, 
                    contatoCupom, 
                    uriCupom, 
                    gerarPDF,
                    cupomNaTela
                );
            impr.imprimeRelJasper();
        }
    }

    private Ped65VO pegarPedido(String numeroPedido) {
        Ped65VO retorno = new Ped65VO();
        String sql_query = "select * from pedidos65 where pedido = "+numeroPedido+" and modelonfe = '65' and empresa = "+this.empresa;
        boolean achouPedido = false;
        try {
            resultset = statement.executeQuery(sql_query);
            while (resultset.next()) {
                achouPedido = true;
                retorno.setCancelado(resultset.getBoolean("cancelado"));
                retorno.setCodcaixa(resultset.getInt("codcaixa"));
                retorno.setCodmaquina(resultset.getInt("codmaquina"));
                retorno.setDocumento(resultset.getString("documento"));
                retorno.setNome_documento(resultset.getString("nome_documento"));
                retorno.setData_digitacao(resultset.getDate("data_digitacao"));
                retorno.setPedido(resultset.getInt("pedido"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar a consulta na tabela pedidos65: "+e);
        }
        if (!achouPedido) {
            JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O PEDIDO ["+numeroPedido+"] NÃO FOI ENCONTRADO!");
         }
        return retorno;
    }
        private void executarSuprimento() {
            //jBMASuprim.setEnabled(true);
            jBMASangria.setEnabled(true);
            jBMAPPend.setEnabled(true);
            jBMACalc.setEnabled(true);
            jBMAFechar.setEnabled(true);
            suprimento();
        }
        private void executarSangria() {
            //JOptionPane.showMessageDialog(null, "Falta Implementar o metodo: executarSangria() . . .");
            jBMASuprim.setEnabled(true);
            //jBMASangria.setEnabled(true);
            jBMAPPend.setEnabled(true);
            jBMACalc.setEnabled(true);
            jBMAFechar.setEnabled(true);
            sangria();
        }
        private void executarDesconto() {
            jBMASuprim.setEnabled(true);
            jBMASangria.setEnabled(true);
            jBMAPPend.setEnabled(true);
            jBMACalc.setEnabled(true);
            jBMAFechar.setEnabled(true);
            desconto();
        }
        private void executarCalc() {
            //JOptionPane.showMessageDialog(null, "Falta Implementar o metodo: executarCalc() . . .");
            jBMASuprim.setEnabled(true);
            jBMASangria.setEnabled(true);
            jBMAPPend.setEnabled(true);
            //jBMACalc.setEnabled(true);
            jBMAFechar.setEnabled(true);
            java.util.Date data = new java.util.Date();
            java.sql.Timestamp agora = new java.sql.Timestamp(data.getTime());
            JOptionPane.showMessageDialog(null, "Data e Hora agora: "+agora.toString());
            try {
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception e) {
                
            }

        }
        private void exibirPedidos() {
            jBMASuprim.setEnabled(true);
            jBMASangria.setEnabled(true);
            //jBMAPPend.setEnabled(true);
            jBMACalc.setEnabled(true);
            jBMAFechar.setEnabled(true);
            arq_saida_xml = "";

            String numPedido = null;
            UIManager.put("OptionPane.yesButtonText", "Autorizar");
            UIManager.put("OptionPane.noButtonText", "Reimprimir");
            UIManager.put("OptionPane.cancelButtonText", "Desistir");
            int escolha = JOptionPane.showConfirmDialog(null, "Autorizar / Reimprimir Cupom?","Autorização/Reimpressão do Cupom", JOptionPane.YES_NO_CANCEL_OPTION);
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.cancelButtonText", "Cancela");
            UIManager.put("OptionPane.noButtonText", "Nao");
            PedidosPendentes janelaPedPendentes;
            if (escolha == JOptionPane.YES_OPTION) {
                janelaPedPendentes = new PedidosPendentes(this, true, getCodCaixa(), connection, false);
                janelaPedPendentes.setLocationRelativeTo(null);
                janelaPedPendentes.setVisible(true);
                numPedido = janelaPedPendentes.getPedido();
                if (numPedido == null || numPedido.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nenhum Pedido foi capturado! ");
                } else {
                    chamarImpressaoDoCupom(numPedido, true, true);
                }
            } else if (escolha == JOptionPane.NO_OPTION) {
               janelaPedPendentes = new PedidosPendentes(this, true, getCodCaixa(), connection, true);
                janelaPedPendentes.setLocationRelativeTo(null);
                janelaPedPendentes.setVisible(true);
                numPedido = janelaPedPendentes.getPedido();
                if (numPedido == null || numPedido.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nenhum Pedido foi capturado! ");
                } else {
                    chamarImpressaoDoCupom(numPedido, true, false);
                }
           } else {  //  cancel_option
                JOptionPane.showMessageDialog(null, "Voce desistiu de Autorizar/Reimprimir o Pedido!!");
           }
            //}
        //}
        }
        private void habilitarProduto(boolean acao, boolean altQtdePara1) {
            if (acao) {
                if (altQtdePara1) {
                    jTQtde.setText("1.00");
                }
                jTDescricao.setText("");
                jTUnid.setText("");
                jTPreco.setText("0,00");
                jTTotProd.setText("0,00");
                jTTotDesc.setText("0,00");
                jTEAN.setText("");
                precoUnit = 0;
                //fotoProduto = new ImageIcon("/imagens/produtos/semfoto.JPG");
                //jLFoto.setIcon(fotoProduto);
                jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
//                jBConfirmaItem.setEnabled(!acao);
                setCodProduto(0);
            }
            jTEAN.setEnabled(acao);
            jTPreco.setEnabled(acao);
            jTQtde.setEnabled(acao);
            jTUnid.setEnabled(acao);
            jBProcuraProduto.setEnabled(acao);
       }
        private int buscarProdPorEan() {
            int retorno = 0;
            if (jTEAN.getText() == null || jTEAN.getText().trim().length() == 0) {
                return retorno;
            }
            if (jTEAN.getText().substring(0,1).equals("2")) { //  codigo de produto balança
                retorno = 1;
            } else {
                String sql = "select * from produto where ean = '"+jTEAN.getText()+"' and preco > 0";
                try {
                    boolean achouProd = false;
                    resultset = statement.executeQuery(sql);
                    while (resultset.next()) {
                        achouProd = true;
                        precoUnit = resultset.getDouble("preco");
                        if (precoUnit > 0.0) {
                            retorno = resultset.getInt("codigo");
                        }
                    }
                } catch(Exception e) {

                }
            }
            return retorno;
        }
        private String buscarProdPorCod(int codProd) {
            String retorno = "";
            String sql = "select * from produto where codigo = "+codProd+" and preco > 0";
            try {
                boolean achouProd = false;
                resultset = statement.executeQuery(sql);
                while (resultset.next()) {
                    achouProd = true;
                    precoUnit = resultset.getDouble("preco");
                    if (precoUnit > 0.0) {
                        retorno = resultset.getString("ean");
                    }
                }
                if (!achouProd) {
                    JOptionPane.showMessageDialog(null, "O produto escolhido [" + codProd + "] está com o preço ZERADO!");
                }
            } catch(Exception e) {
                
            }
            return retorno;
        }
        private boolean buscarProduto(int codProd) {
            boolean retorno = true;
            produtoVO = new ProdutoVO();
            if (jTEAN.getText() == null) {
                jTEAN.setText("");
            }
            jTEAN.setText(jTEAN.getText().trim());
            float qtde = Float.parseFloat(jTQtde.getText());
            String codigoEAN = jTEAN.getText();
//            if (codigoEAN == null || codigoEAN.length() == 0) {
//                JOptionPane.showMessageDialog(null, "Produto SEM Código de Barras(EAN)!");
//                retorno = false;
//            } else {
            String sql = "";
            if (codigoEAN != null && !codigoEAN.equals("")) {        
                if (codigoEAN.substring(0,1).equals("2")) {  //  produto fracionado
                    totProduto = Float.parseFloat(codigoEAN.substring(7,12)) / 100;
                    codigoEAN = codigoEAN.substring(0,7)+"000000";
                }
                sql = "select * from produto where ean = '"+codigoEAN+"' and preco > 0";
            } else {
                sql = "select * from produto where codigo = "+codProd+" and preco > 0";
            }
                try {
                    boolean achouProd = false;
                    resultset = statement.executeQuery(sql);
                    while (resultset.next()) {
                        achouProd = true;
                        precoUnit = resultset.getDouble("preco");
                        if (precoUnit > 0.0) {
                            setCodProduto(resultset.getInt("codigo"));
                            descProduto = resultset.getString("nome_reduzido");
                            String foto = resultset.getString("images");
                            String wdesc = descProduto;
                            if (wdesc.length() > 40) {
                                wdesc = wdesc.substring(0, 40);
                            }
                            jTDescricao.setText(wdesc);
                            jTUnid.setText(resultset.getString("unidade"));
                            produtoVO.setUnidade(resultset.getString("unidade"));
                            jTPreco.setText(""+String.format("%.2f", precoUnit));
                            //String foto = jTEAN.getText()+".jpg";  //    resultset.getString("unidade");
                            if (foto != null && foto.length() > 4 && foto.indexOf("http") == -1) {
                                //jLFoto.setBounds(new Rectangle(6, 8, 106, 112));
                                try {
                                    //fotoProduto = new ImageIcon("/imagens/produtos//"+foto);
                                    //jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/"+foto)));
                                    System.out.println(" Imagem: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+foto);
                                    f = new File(System.getProperty("user.dir")+fsep+"imagens"+fsep+foto);
                                    image = ImageIO.read(f);
                                    jLFoto.setIcon(new ImageIcon(image.getScaledInstance(264,234, Image.SCALE_DEFAULT)));  //(jLFoto.getWidth(),jLFoto.getHeight(), Image.SCALE_DEFAULT)));
                                } catch(Exception e) {
                                    //fotoProduto = new ImageIcon("/imagens/produtos/semfoto.JPG");
                                    System.out.println("Erro ao tentar exibir foto do produto ["+foto+"]. Erro: "+e);
                                    jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
                                }
                                //jLFoto.setIcon(fotoProduto);

                            } else {
                               jLFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/produtos/semfotoNFCe.jpg")));
                            }
                            calcularProduto(qtde);
                            confirmarItem();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Produto SEM Preco!");
                            retorno = false;
                            break;
                        }
                    }
                    if (!achouProd) {
                        UIManager.put("OptionPane.okButtonText", "Ok");
                        UIManager.put("OptionPane.yesButtonText", "Ok");
                        JOptionPane.showMessageDialog(null, "Produto ["+codigoEAN+"] não cadastrado!");
                        jTEAN.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NFCe.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erro ao procurar o Produto! Erro: "+ex);
                    retorno = false;
                }
//            }
            if (digitouEAN) {
                jLEAN.setForeground(new Color(224, 223, 227));
                jLEAN.setText("");
                jTEAN.setSize(1, 1);
                jTEAN.setForeground(new Color(224, 223, 227));
                jTEAN.setBackground(new Color(238, 237, 236));
                //jTEAN.requestFocus();
                jTEAN.setCaretColor(jTEAN.getBackground());
                digitouEAN = false;
            }
            return retorno;
        }
        
        private void calcularProduto(float qtde) {
            if (jTEAN.getText() != null && jTEAN.getText().length() > 3 && jTEAN.getText().substring(0,1).equals("2")) {
                qtde = (float) (totProduto / precoUnit);
                qtde = new BigDecimal(Float.toString(qtde)).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
                jTQtde.setText(""+qtde);
            } else {
                totProduto = new BigDecimal(qtde * precoUnit).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            }
            jTTotProd.setText(""+String.format("%.2f", totProduto));
        }
        private void salvarPedido() {
            try {
                ped65 = new Ped65VO();
                ped65.setPedido(getPedido());
                ped65.setDocumento("");
                ped65.setNome_documento("");
                ped65.setData_digitacao(new java.sql.Date(new Date().getTime()));
                ped65.setCancelado(false);
                ped65.setCodcaixa(getCodCaixa());
                ped65.setCodlogin(getCodLogin());
                ped65.setCodmaquina(getCodMaquina());
                ped65.setValor_descontos(0.00);
                ped65.setValor_produtos(0.00);
                ped65.setValor_total(0.00);
                String sql_insert = "insert into pedidos65 ("
                  +"pedido, "
                  +"empresa, "
                  +"documento, "
                  +"nome_documento, "
                  +"cod_forma_pgto, "
                  +"data_digitacao, "
                  +"qtde_itens, "
                  +"cancelado, "
                  +"codcaixa, "
                  +"codlogin, "
                  +"codmaquina, "
                  +"numero_nfe, "
                  +"serienfe, "
                  +"modelonfe, "
                  +"valor_descontos, "
                  +"valor_produtos, "
                  +"valor_total"
                  +") values ("
                  +ped65.getPedido()+", "
                  +this.empresa+", "
                  +"'"+ped65.getDocumento()+"', "  //  documento
                  +"'"+ped65.getNome_documento()+"', "  //  nome_documento
                  +0+", "
                  +"'"+ped65.getData_digitacao()+"', "
                  +0+", "
                  +ped65.isCancelado()+", "
                  +ped65.getCodcaixa()+", "
                  +ped65.getCodlogin()+", "
                  +ped65.getCodmaquina()+", "
                  +0+", "
                  +"'   ', "
                  +"'65', "
                  +ped65.getValor_descontos()+", "
                  +ped65.getValor_produtos()+", "
                  +ped65.getValor_total()
                  +")";
System.out.println("NFCe.salvarPedido() - comando de insercao de pedido: "+sql_insert);                
                statement.executeUpdate(sql_insert);
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar salvar o Pedido! Erro: "+e);
            }
        }
        private void atualizarPedido(String tipo) {
            String sql_query = "select * from pedidos65 where pedido = "+getPedido()+" and empresa = "+this.empresa;
            boolean achouPedido = false;
            try {
                resultset = statement.executeQuery(sql_query);
                while (resultset.next()) {
                    achouPedido = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar executar a Sql_query para a tabela pedidos65: "+e);
            }
            if (!achouPedido) {
                JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O PEDIDO ["+getPedido()+"] NÃO FOI ENCONTRADO!");
            } else {
                try {
                    String sql_update = "update pedidos65 set "
                        +"valor_produtos = "+getTotCupom()+", "
                        +"valor_descontos = "+getTotDesconto()+", "
                        +"valor_total = "+(getTotCupom() - getTotDesconto())+", "
                        +"qtde_itens = "+item+", "
                        +"qtde_volume = "+getQtdVol()+", "
                        +"codcaixa = "+getCodCaixa()+", "
                        +"codmaquina = "+getCodMaquina()+", "
                        +"codlogin = "+getCodLogin()+" "
                        +"where pedido = "+getPedido()+" and empresa = "+this.empresa;
                    statement.executeUpdate(sql_update);
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o pedido ["+getPedido()+"] ! Erro: "+e);
                }
            }
        }
        private void salvarItem() {
            try {
                Prod65VO prod65 = new Prod65VO();
                prod65.setCancelado(false);
                prod65.setCod_produto(getCodProduto());
                prod65.setCod_cfop(5102);  //(5405);
                prod65.setItem(item);
                prod65.setPedido(getPedido());
                prod65.setPeso(0.0f);
                prod65.setQuantidade(Float.parseFloat(jTQtde.getText()));
                prod65.setVlr_desconto(0.0);
                //prod65.setVlr_produto(Double.parseDouble(trocaVgParaPto(jTTotProd.getText())));
                //prod65.setVlr_total(Double.parseDouble(trocaVgParaPto(jTTotProd.getText())));
                prod65.setVlr_produto(Double.parseDouble(jTTotProd.getText().replace(",", ".")));
                prod65.setVlr_desconto(Double.parseDouble(jTTotDesc.getText().replace(",", ".")));
                prod65.setVlr_total(Double.parseDouble(jTTotProd.getText().replace(",", ".")) - prod65.getVlr_desconto());
                prod65.setVlr_unitario(precoUnit);
                String sql_insert = "insert into produtos65 ("
                    +"pedido, "
                    +"empresa, "
                    +"item, "
                    +"cod_produto, "
                    +"cod_cfop, "
                    +"quantidade, "
                    +"peso, "
                    +"vlr_unitario, "
                    +"vlr_produto, "
                    +"vlr_desconto, "
                    +"vlr_total, "
                    +"cancelado "
                    +") values ("
                    +prod65.getPedido() + ", "
                    +this.empresa + ", "
                    +prod65.getItem() + ", "
                    +prod65.getCod_produto() + ", "
                    +prod65.getCod_cfop() + ", "
                    +prod65.getQuantidade() + ", "
                    +0.00 + ", "
                    +prod65.getVlr_unitario() + ", "
                    +prod65.getVlr_produto() + ", "
                    +prod65.getVlr_desconto() + ", "
                    +prod65.getVlr_total() + ", "
                    +prod65.isCancelado() + " "
                        +")";
             statement.executeUpdate(sql_insert);
             if (produtoVO.getUnidade().equals("KG")) {
                 setQtdVol(getQtdVol() + 1);
             } else {
                 setQtdVol(getQtdVol() + (int)prod65.getQuantidade());
             }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar salvar o Item! Erro: "+e);
            }
        }
        private void totalizarCupom(String acao) {
            if (acao.equals("soma")) {
                setTotCupom(getTotCupom() + totProduto);
            } else {
                setTotCupom(getTotCupom() - totProduto);
            }
            jTTotDesc.setText(""+String.format("%.2f", getTotDesconto()));
            jTTotCupom.setText(""+String.format("%.2f", (getTotCupom() - getTotDesconto())));
        }
        private void inicializarBotoes(boolean acao) {
            jBIniciar.setEnabled(!acao);
            jBMenu.setEnabled(acao);
            jBCpfCnpj.setEnabled(acao);
            jBCancItem.setEnabled(acao);
            jBCancVenda.setEnabled(acao);
            jBFinalizar.setEnabled(acao);

        }
        private void procurarProduto() {
            ImportaProduto janelaImportaProduto = new ImportaProduto(this, true, connection);
            janelaImportaProduto.setLocationRelativeTo(null);
            janelaImportaProduto.setVisible(true);
            if (!janelaImportaProduto.cancelado) {
//                jTEAN.setText(janelaImportaProduto.getGTIN());
//                if (jTEAN.getText() != null && !jTEAN.getText().equals("")) {
//                    buscarProduto();
//                }
                int codProd = janelaImportaProduto.getGTIN();
                if (codProd > 0) {
                    jTEAN.setText(buscarProdPorCod(codProd));
                    buscarProduto(codProd);
                }
            }
       }
        private void trataEAN() {
            jTDescricao.setText("");
            jTUnid.setText("");
            jTPreco.setText("0,00");
            jTTotDesc.setText("0,00");
            jTTotProd.setText("0,00");
            if (jTEAN.getText() == null || jTEAN.getText().equals("")){   // || jTEAN.getText().length() < 13) {
//               JOptionPane.showMessageDialog(null, "(1)Código Inválido! ");
                jLEAN.setForeground(new Color(224, 223, 227));
                jLEAN.setText("");
                jTEAN.setSize(1, 1);
                jTEAN.setForeground(new Color(224, 223, 227));
                jTEAN.setBackground(new Color(238, 237, 236));
                //jTEAN.requestFocus();
                jTEAN.setCaretColor(jTEAN.getBackground());
                digitouEAN = false;
               jTEAN.setText("");
               jTEAN.requestFocus();
            } else {
                int codProd = buscarProdPorEan();
                if (codProd > 0) {
                    if (!buscarProduto(codProd)) {
                        jTEAN.setText("");
                        jTEAN.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Produto inexistente!");
                    jTEAN.setText("");
                    jTEAN.requestFocus();
                }
            }
        }
    public static NFCe getCaixa() {
        return c;
    }

    private void confirmarItem() {
        try {
            if (totProduto > 0.0) {
                item++;
                salvarItem();
                String strItem = ""+item;
                atualizarCupom(
                        ""+Biblioteca.repete("0", 3 - strItem.length())+strItem
                        +Biblioteca.repete(" ", 7 - jTQtde.getText().length())+jTQtde.getText()
                        +Biblioteca.repete(" ", 8 - jTPreco.getText().length())+jTPreco.getText()
                        +Biblioteca.repete(" ", 8 - jTTotProd.getText().length())+jTTotProd.getText()
                        +" "+getDescProduto()  //+Biblioteca.repete(" ", 25 - wdesc.length())
                        +"\n");
                totalizarCupom("soma");
                atualizarPedido("item");
                jTEAN.setText("");
                jTQtde.setText("1.00");
                jTEAN.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Item sem Totais NÃO pode ser Salvo! . . .");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "confirmatItem() - Erro: "+e);
        }
    }
    private int pegarProxPedido() {
        int retorno = 0;
        String sql = "select max(pedido) as numped from nf where empresa = "+this.empresa;
        try {
            resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                retorno = resultset.getInt("numped");
            }
        } catch (Exception e) {

        }
        int retorno65 = 0;
        sql = "select max(pedido) as numped from pedidos65 where empresa = "+this.empresa;
        try {
            resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                retorno65 = resultset.getInt("numped");
            }
        } catch (Exception e) {

        }
        if (retorno65 > retorno) retorno = retorno65;
        
        retorno++;
        return retorno;
    }
    private String formatVlrCupom(String texto, int totColunas) {
        String retorno = texto;
       int tam = texto.trim().length();
       if (tam > totColunas) {
           retorno = texto.substring(0, totColunas);
       } else {
           for (int i=tam; i<totColunas;i++) {
                retorno = " "+retorno;
           }
        }
        return retorno;
    }
    private int pegaCodLogin() {
       int retorno = 0;
       String sql_query = "select * from login where usuario = '"+operador+"' and empresa = "+this.empresa;
        boolean achouLogin = false;
        try {
            resultset = statement.executeQuery(sql_query);
            while (resultset.next()) {
                achouLogin = true;
                alterar_qtde = resultset.getString("alterar_qtde");
                if (alterar_qtde == null) alterar_qtde = "N";
                retorno = resultset.getInt("codigo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar a Sql_query para a tabela login: "+e);
        }
        if (!achouLogin) {
            JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O login ["+operador+"] NÃO FOI ENCONTRADO!");
        }
        return retorno;
    }
    private int pegaCodCaixa() {
       int retorno = 0;
       String sql_query = "select * from caixa where nome = '"+caixa+"' and empresa = "+this.empresa;
        boolean achouCaixa = false;
        try {
            resultset = statement.executeQuery(sql_query);
            while (resultset.next()) {
                achouCaixa = true;
                setCodMaquina(resultset.getInt("codmaquina"));
                setSerie(resultset.getString("serie65"));
//           JOptionPane.showMessageDialog(null, "Serie da tabela caixa: "+getSerie());
                retorno = resultset.getInt("codigo");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar executar a Sql_query para a tabela caixa: "+e);
        }
        if (!achouCaixa) {
            JOptionPane.showMessageDialog(null, "*** ATENÇÃO *** O caixa ["+caixa+"] NÃO FOI ENCONTRADO!");
        }
        return retorno;
    }
    private void suprimento() {

        setGerenteAutorizou(autorizarOperacao());
        if (isGerenteAutorizou()) {
            ValorReal janelaValorReal = new ValorReal(this, true, "SUPRIMENTO");
            janelaValorReal.setLocationRelativeTo(null);
            janelaValorReal.setVisible(true);
            Double valorSuprimento = janelaValorReal.retornaValor();
            if (valorSuprimento > 0) {
                try {
                    SuprimentoVO suprimento = new SuprimentoVO();
                    java.util.Date data = new java.util.Date();
                    java.sql.Date hoje = new java.sql.Date(data.getTime());
                    suprimento.setCodcaixa(getCodCaixa());
                    suprimento.setCodgerente(getCodGerente());
                    suprimento.setCodlogin(getCodLogin());
                    suprimento.setDataSuprimento(hoje);
                    suprimento.setValor(valorSuprimento);
                    String sql = "insert into suprimento ("
                            + "empresa, "
                            + "codcaixa, "
                            + "codlogin, "
                            + "codgerente, "
                            + "data_suprimento, "
                            + "valor"
                            + ") values ("
                            + empresa+", "
                            + suprimento.getCodcaixa()+ ", "
                            + suprimento.getCodlogin()+ ", "
                            + suprimento.getCodgerente()+ ", "
                            + "'"+suprimento.getDataSuprimento()+ "', "
                            + suprimento.getValor()+" "
                            + ")";
                    statement.executeUpdate(sql);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro do sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void sangria() {
        setGerenteAutorizou(autorizarOperacao());
        if (isGerenteAutorizou()) {
            ValorReal janelaValorReal = new ValorReal(this, true, "SANGRIA");
            janelaValorReal.setLocationRelativeTo(null);
            janelaValorReal.setVisible(true);
            Double valorSangria = janelaValorReal.retornaValor();
            if (valorSangria > 0) {
                try {
                    SangriaVO sangria = new SangriaVO();
                    java.util.Date data = new java.util.Date();
                    java.sql.Date hoje = new java.sql.Date(data.getTime());
                    sangria.setDataSangria(hoje);
                    sangria.setValor(valorSangria);
                    sangria.setCodcaixa(getCodCaixa());
                    sangria.setCodgerente(getCodGerente());
                    sangria.setCodlogin(getCodLogin());
                    String sql = "insert into sangria ("
                            + "empresa, "
                            + "codcaixa, "
                            + "codlogin, "
                            + "codgerente, "
                            + "data_sangria, "
                            + "valor"
                            + ") values ("
                            + empresa +", "
                            + sangria.getCodcaixa()+ ", "
                            + sangria.getCodlogin()+ ", "
                            + sangria.getCodgerente()+ ", "
                            + "'"+sangria.getDataSangria()+ "', "
                            + sangria.getValor()+" "
                            + ")";
                    statement.executeUpdate(sql);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro do sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    private void desconto() {

        setGerenteAutorizou(autorizarOperacao());
        if (isGerenteAutorizou()) {
            ValorReal janelaValorReal = new ValorReal(this, true, "DESCONTO");
            janelaValorReal.setValor(totDesconto);
            janelaValorReal.setLocationRelativeTo(null);
            janelaValorReal.setVisible(true);
            Double valorDesconto = janelaValorReal.retornaValor();
            System.out.println("Valor Desconto: "+valorDesconto);
            System.out.println("Status de Retorno do desconto: "+janelaValorReal.isRetorno());
            if (janelaValorReal.isRetorno()) {
                if (valorDesconto > 0) {
                    if (valorDesconto <= totCupom * 0.5) {
                        try {
                            DescontoVO desconto = new DescontoVO();
                            java.util.Date data = new java.util.Date();
                            java.sql.Date hoje = new java.sql.Date(data.getTime());
                            desconto.setCodcaixa(getCodCaixa());
                            desconto.setCodgerente(getCodGerente());
                            desconto.setCodlogin(getCodLogin());
                            desconto.setDataDesconto(hoje);
                            desconto.setValor(valorDesconto);
                            totDesconto = valorDesconto;
                            jTTotDesc.setText(""+String.format("%.2f", totDesconto));
                            jTTotCupom.setText(""+String.format("%.2f", (totCupom - totDesconto)));
                            atualizarPedido("item");
                         } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro do sistema", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Valor Desconto não pode ser maior que 50% do Total do Cupom!", "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Valor Desconto não pode ser Negativo!", "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void alteraQtde() {
        
        if (alterar_qtde.equals("N")) {
            setGerenteAutorizou(autorizarOperacao());
        } else {
            setGerenteAutorizou(true);
        }
        System.out.println("NFCe.alteraQtde: " + isGerenteAutorizou());
        if (isGerenteAutorizou()) {
            
            ValorQtde janelaValorReal = new ValorQtde(this, true, "QUANTIDADE", Double.parseDouble(jTQtde.getText()));
            janelaValorReal.setLocationRelativeTo(null);
//            janelaValorReal.setValor(Double.parseDouble(jTQtde.getText().replaceAll(",", ".")));
            janelaValorReal.setVisible(true);
            Double valorQtde = janelaValorReal.retornaValor();
            if (valorQtde > 0) {
                try {
                    jTQtde.setText("" + String.format("%.2f", valorQtde).replaceAll(",", "."));
                    habilitarProduto(true, false);
                    jTEAN.requestFocus();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro do sistema", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Quantidade não pode ser ZERO. Qtde não será alterada!");
            }
        }
    }
    private boolean autorizarOperacao() {
        boolean retorno = false;
        LoginGerenteSupervisor janelaLogin = new LoginGerenteSupervisor(this, true, connection, empresa);
        janelaLogin.setLocationRelativeTo(null);
        janelaLogin.setVisible(true);
        if (janelaLogin.loginGerenteSupervisor()) {
           retorno = true;
      }
        return retorno;
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NFCe(null, 0, null, null, null, "", null, null, null, "", "N", "N").setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList bobina;
    private javax.swing.JButton jBCancItem;
    private javax.swing.JButton jBCancVenda;
    private javax.swing.JButton jBCpfCnpj;
    private javax.swing.JButton jBEAN;
    private javax.swing.JButton jBFinalizar;
    private javax.swing.JButton jBIniciar;
    private javax.swing.JButton jBMAAbreCaixa;
    private javax.swing.JButton jBMACalc;
    private javax.swing.JButton jBMAFechar;
    private javax.swing.JButton jBMALeituraX;
    private javax.swing.JButton jBMAPPend;
    private javax.swing.JButton jBMASangria;
    private javax.swing.JButton jBMASuprim;
    private javax.swing.JButton jBMAVazio;
    private javax.swing.JButton jBMenu;
    private javax.swing.JButton jBProcuraProduto;
    private javax.swing.JButton jBSair;
    private javax.swing.JButton jBSomaQtde;
    private javax.swing.JButton jBTotDesconto;
    private javax.swing.JLabel jLAmbiente;
    private javax.swing.JLabel jLCaixa;
    private javax.swing.JLabel jLData;
    private javax.swing.JLabel jLDocCli;
    private javax.swing.JLabel jLEAN;
    private javax.swing.JLabel jLFoto;
    private javax.swing.JLabel jLHora;
    private javax.swing.JLabel jLOperador;
    private javax.swing.JLabel jLStatusServico;
    private javax.swing.JLabel jLStatusVda;
    private javax.swing.JLabel jLTotCupom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPFoto;
    private javax.swing.JPanel jPMenuAux;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTDescricao;
    private javax.swing.JTextField jTEAN;
    private javax.swing.JTextField jTNumPedido;
    private javax.swing.JTextField jTPreco;
    private javax.swing.JTextField jTQtde;
    private javax.swing.JTextField jTTotCupom;
    private javax.swing.JTextField jTTotDesc;
    private javax.swing.JTextField jTTotProd;
    private javax.swing.JTextField jTUnid;
    private javax.swing.JScrollPane panelBobina;
    // End of variables declaration//GEN-END:variables

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getCodCaixa() {
        return codCaixa;
    }

    public void setCodCaixa(int codCaixa) {
        this.codCaixa = codCaixa;
    }

    public int getCodLogin() {
        return codLogin;
    }

    public void setCodLogin(int codLogin) {
        this.codLogin = codLogin;
    }

    public int getCodMaquina() {
        return codMaquina;
    }

    public void setCodMaquina(int codMaquina) {
        this.codMaquina = codMaquina;
    }

    public int getCod_forma_pgto() {
        return cod_forma_pgto;
    }

    public void setCod_forma_pgto(int cod_forma_pgto) {
        this.cod_forma_pgto = cod_forma_pgto;
    }
    public static ProdutoVO getProdutoVO() {
        return produtoVO;
    }

    public static void setProdutoVO(ProdutoVO aProdutoVO) {
        produtoVO = aProdutoVO;
    }

    public static double getTotCupom() {
        return totCupom;
    }

    public static void setTotCupom(double aTotCupom) {
        totCupom = aTotCupom;
    }

    public int getQtdVol() {
        return qtdVol;
    }

    public void setQtdVol(int qtdVol) {
        this.qtdVol = qtdVol;
    }

    public int getCodGerente() {
        return codGerente;
    }

    public void setCodGerente(int codGerente) {
        this.codGerente = codGerente;
    }

    public boolean isGerenteAutorizou() {
        return gerenteAutorizou;
    }

    public void setGerenteAutorizou(boolean gerenteAutorizou) {
        this.gerenteAutorizou = gerenteAutorizou;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    // End of variables declaration
    class hora implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            jLHora.setText("Hora: "+String.format("%1$tH:%1$tM:%1$tS", now));
        }
    }
    private void processarIniciarVenda() {
        if (vendaIniciada) {
           JOptionPane.showMessageDialog(null, "Para inicar uma venda é preciso Finalizar/Cancelar a Venda em Aberto!");
        } else {
            iniciarVenda();
        }
    }
    private void processarAbrirMenu() {
        jBMAFechar.setEnabled(true);
        jPMenuAux.setVisible(true);

        jBProcuraProduto.setEnabled(false);
        jBCpfCnpj.setEnabled(false);
        jBCancItem.setEnabled(false);
        jBCancVenda.setEnabled(false);
        jBMenu.setEnabled(false);
        habilitouMenu = true;
    }
    private void processarFecharMenu() {
        jBMAFechar.setEnabled(false);
        jPMenuAux.setVisible(false);
        if (vendaIniciada) {
            jBProcuraProduto.setEnabled(true);
            jBCpfCnpj.setEnabled(true);
            jBCancItem.setEnabled(true);
            jBCancVenda.setEnabled(true);
        }
        jBMenu.setEnabled(true);
        habilitouMenu = false;
    }
    private void processarQtde() {
        alteraQtde();
    }
    private void processarCpfCnpj() {
        if (!vendaIniciada) {
           JOptionPane.showMessageDialog(null, "Para informar CPF/CNPJ é preciso iniciar uma venda!");
        } else {
            informarCpfCnpj();
        }
    }
    private void processarCancelarItem() {
        if (!vendaIniciada) {
           JOptionPane.showMessageDialog(null, "Para cancelar um Item é preciso iniciar uma venda!");
        } else if (item == 0) {
           JOptionPane.showMessageDialog(null, "Não existe nenhum item digitado para esta venda!");
        } else {
            cancelarItem();
        }
    }
    private void processarCancelarVenda() {
        if (!vendaIniciada) {
           JOptionPane.showMessageDialog(null, "Para cancelar uma venda é preciso iniciar uma venda!");
        } else {
           UIManager.put("OptionPane.okButtonText", "Ok");
           UIManager.put("OptionPane.cancelButtonText", "Desiste");
            if (JOptionPane.showConfirmDialog(null, "Confirma o Cancelamento da Venda?","Cancelamento da Venda", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                cancelarVenda();
            } else {
                JOptionPane.showMessageDialog(null, "Voce desistiu e a Venda NÃO foi Cancelada!");
            }
        }
    }
    private void processarFinalizarVenda() {
        if (!vendaIniciada) {
           JOptionPane.showMessageDialog(null, "Para finalizar uma venda é preciso iniciar uma venda!");
        } else if (item == 0) {
           JOptionPane.showMessageDialog(null, "Não existe nenhum item digitado para esta venda! Escolha CANCELAR VENDA");
        } else {
            if (finalizarVenda()) {
                habilitarProduto(false, true);
                processarIniciarVenda();
            }

        }
    }
    private void processarSuprimento() {
        jBMASangria.setEnabled(false);
        jBMAPPend.setEnabled(false);
        jBTotDesconto.setEnabled(false);
        jBMACalc.setEnabled(false);
        jBMAFechar.setEnabled(false);
        executarSuprimento();
    }
    private void processarSangria() {
        jBMASuprim.setEnabled(false);
        jBMAPPend.setEnabled(false);
        jBTotDesconto.setEnabled(false);
        jBMACalc.setEnabled(false);
        jBMAFechar.setEnabled(false);
        executarSangria();
    }
    private void processarPedPendentes() {
        jBMASuprim.setEnabled(false);
        jBMASangria.setEnabled(false);
        jBTotDesconto.setEnabled(false);
        jBMACalc.setEnabled(false);
        jBMAFechar.setEnabled(false);
        exibirPedidos();
    }
    private void processarCalc() {
        jBMASuprim.setEnabled(false);
        jBMASangria.setEnabled(false);
        jBMAPPend.setEnabled(false);
        jBTotDesconto.setEnabled(false);
        jBMAFechar.setEnabled(false);
        executarCalc();
   }
    private void processarDesconto() {
        jBMASuprim.setEnabled(false);
        jBMASangria.setEnabled(false);
        jBMAPPend.setEnabled(false);
        jBMACalc.setEnabled(false);
        jBMAFechar.setEnabled(false);
        executarDesconto();
    }
    private void digitarEAN() {
        if (checkCSC()) {
            jLEAN.setForeground(new Color(0, 0, 255));
            jLEAN.setText("Descrição do Produto:");
            jTEAN.setSize(173, 38);
            jTEAN.setForeground(new Color(0, 0, 255));
            jTEAN.setBackground(new Color(255, 255, 153));
            jTEAN.requestFocus();
            jTEAN.setCaretColor(new Color(0, 0, 0));
            digitouEAN = true;
        }
    }
    private boolean checkCSC() {
        boolean retorno = false;
        if (semCSC) {
            JOptionPane.showMessageDialog(null, 
                    "Antes de uilizar esta Tela, o Administrador do Sistema precisa Informar \n"
                    + "corretamente o Id.Token e o codigo CSC do Emitente no Cad.de Param.Digitais.");
        } else {
            retorno = true;
        }
        return retorno;
    }
    private void processarLeituraX() {
        RelLeituraX leituraX = new RelLeituraX(empresa, connection, nome_empresa, endereco_empresa);
        leituraX.setVisible(true); //show();
    }
    private void processarAbrirCaixa() {
        processarAbrirCaixa();
    }
    private void processarSair() {
        if (vendaIniciada && item > 0) {
            JOptionPane.showMessageDialog(null, "Para fechar a Tela é preciso Finalizar/Cancelar a Venda em Aberto!");
        } else {
           UIManager.put("OptionPane.okButtonText", "Ok");
           UIManager.put("OptionPane.cancelButtonText", "Desiste");
            if (JOptionPane.showConfirmDialog(null, "Confirma o Abandono da Tela?","Abandono de Tela", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                apagarPedZerado();
                //this.dispose();
                System.exit(0);
            } else {
                jTEAN.requestFocus();
            }
        }

    }
    private void apagarPedZerado() {
        try {
            String sql_update = "delete from pedidos65 "
                    +"where valor_produtos = 0 and codlogin = "+getCodLogin();
            System.out.println("NFCe.apagarPedZerado() - comando sql: "+sql_update);
            statement.executeUpdate(sql_update);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar apagar os pedidos não utilizados ! Erro: "+e);
        }
        
    }
    private void programarTeclas() {
//  Programa as Teclas F1..F12 para acoes especificas       
        F1Action f1Action = new F1Action();
        jBProcuraProduto.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1Action");
        jBProcuraProduto.getActionMap().put("F1Action", f1Action);
        
        jBMASuprim.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1Action");
        jBMASuprim.getActionMap().put("F1Action", f1Action);

        F2Action f2Action = new F2Action();
        jBSomaQtde.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2Action");
        jBSomaQtde.getActionMap().put("F2Action", f2Action);
        
        jBMASangria.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2Action");
        jBMASangria.getActionMap().put("F2Action", f2Action);

        F3Action f3Action = new F3Action();
        
        jBMAPPend.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "F3Action");
        jBMAPPend.getActionMap().put("F3Action", f3Action);

        F4Action f4Action = new F4Action();
        jBCpfCnpj.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "F4Action");
        jBCpfCnpj.getActionMap().put("F4Action", f4Action);

        jBMACalc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "F4Action");
        jBMACalc.getActionMap().put("F4Action", f4Action);
        
        F5Action f5Action = new F5Action();
        jBCancItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5Action");
        jBCancItem.getActionMap().put("F5Action", f5Action);
        
        jBMAAbreCaixa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "F5Action");
        jBMAAbreCaixa.getActionMap().put("F5Action", f5Action);

        F6Action f6Action = new F6Action();
        jBCancVenda.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "F6Action");
        jBCancVenda.getActionMap().put("F6Action", f6Action);
        
        jBMALeituraX.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "F6Action");
        jBMALeituraX.getActionMap().put("F6Action", f6Action);

        F7Action f7Action = new F7Action();
        jBMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "F7Action");
        jBMenu.getActionMap().put("F7Action", f7Action);

        jBMAFechar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "F7Action");
        jBMAFechar.getActionMap().put("F7Action", f7Action);
        
        F8Action f8Action = new F8Action();
        jBFinalizar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "F8Action");
        jBFinalizar.getActionMap().put("F8Action", f8Action);
        
        F9Action f9Action = new F9Action();
        jTEAN.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "F9Action");
        jTEAN.getActionMap().put("F9Action", f9Action);
        
        F10Action f10Action = new F10Action();
        jBSair.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "F10Action");
        jBSair.getActionMap().put("F10Action", f10Action);
        
        F11Action f11Action = new F11Action();
        jBTotDesconto.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "F11Action");
        jBTotDesconto.getActionMap().put("F11Action", f11Action);

        EnterAction enterAction = new EnterAction();
        jBIniciar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EnterAction");
        jBIniciar.getActionMap().put("EnterAction", enterAction);

    }
// ***************************************************************************//
// Actions vinculadas ao pressionamento de teclas                             //
// ***************************************************************************//
    private class F1Action extends AbstractAction {

        public F1Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarSuprimento();
            } else {
                procurarProduto();
            }
        }
    }

    private class F2Action extends AbstractAction {

        public F2Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarSangria();
            } else {
                processarQtde();
            }
        }
    }

    private class F3Action extends AbstractAction {

        public F3Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarPedPendentes();
            } else {

            }
        }
    }

    private class F4Action extends AbstractAction {

        public F4Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarCalc();
            } else {
                if (nfem2.getTipoAmbiente().equals("1")) {
                    processarCpfCnpj();
                } else {
                   JOptionPane.showMessageDialog(null, "Proibido informar Documento e Nome de Cliente em ambiente de homologação!"); 
                }
            }
        }
    }

    private class F5Action extends AbstractAction {

        public F5Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarAbrirCaixa();
            } else {
                processarCancelarItem();
            }
        }
    }

    private class F6Action extends AbstractAction {

        public F6Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {
                processarLeituraX();
            } else {
                processarCancelarVenda();
            }
        }
    }

    private class F7Action extends AbstractAction {

        public F7Action() {
        }

        public void actionPerformed(ActionEvent e) {
            if (habilitouMenu) {  // O Menu Auxiliar está habilitado
                processarFecharMenu();
            } else {  // O Menu Auxiliar está desabilitado
                processarAbrirMenu();
            }
        }
    }

    private class F8Action extends AbstractAction {

        public F8Action() {
        }

        public void actionPerformed(ActionEvent e) {
            processarFinalizarVenda();
        }
    }

    private class F9Action extends AbstractAction {

        public F9Action() {
        }

        public void actionPerformed(ActionEvent e) {
            digitarEAN();
        }
    }

    private class F10Action extends AbstractAction {

        public F10Action() {
        }

        public void actionPerformed(ActionEvent e) {
            processarSair();
        }
    }

     private class F11Action extends AbstractAction {

        public F11Action() {
        }

        public void actionPerformed(ActionEvent e) {
            processarDesconto();
       }
    }

   private class EnterAction extends AbstractAction {

        public EnterAction() {
        }

        public void actionPerformed(ActionEvent e) {
            processarIniciarVenda();
        }
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }
}
