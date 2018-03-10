/*

Descri��o: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.view;

import br.com.videoaulasneri.adelcio.fatura.dialog.controller.TotalTipoPagamentoController;
import br.com.videoaulasneri.adelcio.fatura.dialog.controller.TipoPagamentoController;
import br.com.videoaulasneri.adelcio.fatura.dialog.ComparatorTipoPagamento;
import br.com.videoaulasneri.adelcio.fatura.NFCe;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.EfetuaPagamentoColumnModel;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.EfetuaPagamentoTableModel;
import br.com.videoaulasneri.adelcio.fatura.dialog.MonetarioDocument;
import br.com.videoaulasneri.adelcio.fatura.bean.TipoPagamentoVO;
import br.com.videoaulasneri.adelcio.fatura.bean.TotalTipoPagamentoVO;
import br.com.videoaulasneri.adelcio.fatura.dialog.ComparatorTipoPagamento;
import br.com.videoaulasneri.adelcio.fatura.dialog.MonetarioDocument;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class EfetuaPagamento extends javax.swing.JDialog {

    List<TipoPagamentoVO> listaTipoPagamento = new ArrayList<TipoPagamentoVO>();
    List<TotalTipoPagamentoVO> listaTotalTipoPagamento = new ArrayList<TotalTipoPagamentoVO>();
    NumberFormat formatter = new DecimalFormat("#,###,##0.00");
    NumberFormat formataTef = new DecimalFormat("0.00");
    //
    boolean transacaoComTef, multTef = false;
    public static boolean pagamentoOK;
    public static boolean temVdaCartao;
    public static boolean pagamentoCancelado = false;
    private String ultimoNSU = "";
    private BigDecimal totalVenda;
    private BigDecimal desconto;
    private BigDecimal acrescimo;
    private BigDecimal totalReceber;
    private BigDecimal troco;
    private BigDecimal totalRecebido;
    private BigDecimal Restante;
    public static boolean vendaComProblema = false;
    Connection conexao;
    int empresa, pedido;

    public EfetuaPagamento(java.awt.Frame parent, boolean modal, int empresa, int pedido, Connection conexao) {
        super(parent, modal);
        this.empresa = empresa;
        this.pedido = pedido;
        this.conexao = conexao;
//JOptionPane.showMessageDialog(null, "EfetuaPagamento - Passou 0 . . .");
        try {
            initComponents();
        } catch(Exception e) {
//JOptionPane.showMessageDialog(null, "EfetuaPagamento - Erro ao tentar inicializar componentes. Erro: "+e);
        }
        totalVenda = new BigDecimal(0);
        desconto = new BigDecimal(0);
        acrescimo = new BigDecimal(0);
        totalReceber = new BigDecimal(0);
        troco = new BigDecimal(0);
        totalRecebido = new BigDecimal(0);
        Restante = new BigDecimal(0);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jValor.setDocument(new MonetarioDocument());
        jValor.setText("0");

        //TODO : Pra que serve essa variável?
        pagamentoOK = false;
        temVdaCartao = false;

        int r = 224;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(0, 3));
        int g = 223;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(4, 7));
        int b = 227;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(8, 11));

        //TODO : É necessário configurar a cor de cada Panel?
        panelPrincipal.setBackground(new Color(r, g, b));
        panelComponentes.setBackground(new Color(r, g, b));
        panelDados.setBackground(new Color(r, g, b));
        panelBotoes.setBackground(new Color(r, g, b));
        panelValores.setBackground(new Color(r, g, b));
        jPanel1.setBackground(new Color(r, g, b));
        panelResumoVenda.setBackground(new Color(r, g, b));
        panelTotalReceber.setBackground(new Color(r, g, b));
        panelTotalRecebido.setBackground(new Color(r, g, b));
        panelTotalVenda.setBackground(new Color(r, g, b));
        panelDesconto.setBackground(new Color(r, g, b));
        panelTroco.setBackground(new Color(r, g, b));
        panelTroco1.setBackground(new Color(r, g, b));
        panelAcrescimo.setBackground(new Color(r, g, b));

        CancelaAction cancelaAction = new CancelaAction();
        botaoCancela.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelaAction");
        botaoCancela.getActionMap().put("cancelaAction", cancelaAction);

        ConfirmaAction confirmaAction = new ConfirmaAction();
        botaoConfirma.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "confirmaAction");
        botaoConfirma.getActionMap().put("confirmaAction", confirmaAction);

        //troca TAB por ENTER
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
        //guarda valores para calculo
        totalVenda = totalVenda.add(BigDecimal.valueOf(NFCe.getTotCupom()));
        totalReceber = totalReceber.add(BigDecimal.valueOf(NFCe.getTotCupom())).setScale(2, RoundingMode.DOWN);
        totalReceber = totalReceber.subtract(BigDecimal.valueOf(NFCe.getTotDesconto())).setScale(2, RoundingMode.DOWN);
        //formata valores para exibição
        labelTotalVenda.setText(formatter.format(NFCe.getTotCupom()));
        labelDesconto.setText(formatter.format(NFCe.getTotDesconto()));  //(Caixa.vendaCabecalho.getDesconto()));
        labelAcrescimo.setText(formatter.format(0.00));  //(Caixa.vendaCabecalho.getAcrescimo()));
        //labelTotalReceber.setText(formatter.format(Caixa.vendaCabecalho.getValorVenda() + Caixa.vendaCabecalho.getAcrescimo() - Caixa.vendaCabecalho.getDesconto()));
        labelTotalReceber.setText(formatter.format(NFCe.getTotCupom() - NFCe.getTotDesconto()));
        jValor.setText(formatter.format(NFCe.getTotCupom() - NFCe.getTotDesconto()));
//JOptionPane.showMessageDialog(null, "EfetuaPagamento - Passou 1 . . .");

        //
        TipoPagamentoController tipoPagamentoControl = new TipoPagamentoController(conexao);
//JOptionPane.showMessageDialog(null, "EfetuaPagamento - Passou 2 . . .");
        configuraGridValores(tipoPagamentoControl.consulta());
//JOptionPane.showMessageDialog(null, "EfetuaPagamento - Passou 3 . . .");

        //foco no primeiro valor da grid
//        cboTiposPagamento.requestFocus();
        jValor.requestFocus();
        //gridValores.editCellAt(0, 1);

        setPreferredSize(new Dimension(760,350));
        this.pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelComponentes = new javax.swing.JPanel();
        panelDados = new javax.swing.JPanel();
        panelResumoVenda = new javax.swing.JPanel();
        panelTotalVenda = new javax.swing.JPanel();
        labelDescricaoTotalVenda = new javax.swing.JLabel();
        labelTotalVenda = new javax.swing.JLabel();
        panelDesconto = new javax.swing.JPanel();
        labelDescricaoDesconto = new javax.swing.JLabel();
        labelDesconto = new javax.swing.JLabel();
        panelAcrescimo = new javax.swing.JPanel();
        labelDescricaoAcrescimo = new javax.swing.JLabel();
        labelAcrescimo = new javax.swing.JLabel();
        panelTotalReceber = new javax.swing.JPanel();
        labelDescricaoTotalReceber = new javax.swing.JLabel();
        labelTotalReceber = new javax.swing.JLabel();
        panelTotalRecebido = new javax.swing.JPanel();
        labelDescricaoTotalRecebido = new javax.swing.JLabel();
        labelTotalRecebido = new javax.swing.JLabel();
        panelTroco = new javax.swing.JPanel();
        labelDescricaoTroco = new javax.swing.JLabel();
        labelTroco = new javax.swing.JLabel();
        panelTroco1 = new javax.swing.JPanel();
        labelDescricaoTroco1 = new javax.swing.JLabel();
        labelRestante = new javax.swing.JLabel();
        panelValores = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridValores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboTiposPagamento = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jValor = new javax.swing.JFormattedTextField();
        panelBotoes = new javax.swing.JPanel();
        botaoConfirma = new javax.swing.JButton();
        botaoCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Efetua Pagamento para Encerrar Venda");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        panelPrincipal.setMinimumSize(new java.awt.Dimension(678, 366));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(690, 326));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panelPrincipal.add(jLabel1, gridBagConstraints);

        panelComponentes.setLayout(new java.awt.GridBagLayout());

        panelDados.setMinimumSize(new java.awt.Dimension(590, 306));
        panelDados.setPreferredSize(new java.awt.Dimension(606, 266));
        panelDados.setLayout(new java.awt.GridBagLayout());

        panelResumoVenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumo da Venda:"));
        panelResumoVenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        panelResumoVenda.setPreferredSize(new java.awt.Dimension(200, 220));
        panelResumoVenda.setLayout(new java.awt.GridBagLayout());

        panelTotalVenda.setLayout(new java.awt.GridBagLayout());

        labelDescricaoTotalVenda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoTotalVenda.setForeground(new java.awt.Color(0, 0, 255));
        labelDescricaoTotalVenda.setText("Total Venda:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalVenda.add(labelDescricaoTotalVenda, gridBagConstraints);

        labelTotalVenda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalVenda.setForeground(new java.awt.Color(0, 0, 255));
        labelTotalVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalVenda.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalVenda.add(labelTotalVenda, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelResumoVenda.add(panelTotalVenda, gridBagConstraints);

        panelDesconto.setLayout(new java.awt.GridBagLayout());

        labelDescricaoDesconto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoDesconto.setForeground(new java.awt.Color(255, 0, 0));
        labelDescricaoDesconto.setText("Desconto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDesconto.add(labelDescricaoDesconto, gridBagConstraints);

        labelDesconto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDesconto.setForeground(new java.awt.Color(255, 0, 0));
        labelDesconto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelDesconto.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDesconto.add(labelDesconto, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelDesconto, gridBagConstraints);

        panelAcrescimo.setLayout(new java.awt.GridBagLayout());

        labelDescricaoAcrescimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoAcrescimo.setForeground(new java.awt.Color(0, 0, 255));
        labelDescricaoAcrescimo.setText("Acr�scimo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAcrescimo.add(labelDescricaoAcrescimo, gridBagConstraints);

        labelAcrescimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAcrescimo.setForeground(new java.awt.Color(0, 0, 255));
        labelAcrescimo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAcrescimo.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAcrescimo.add(labelAcrescimo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelAcrescimo, gridBagConstraints);

        panelTotalReceber.setLayout(new java.awt.GridBagLayout());

        labelDescricaoTotalReceber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoTotalReceber.setForeground(new java.awt.Color(0, 0, 255));
        labelDescricaoTotalReceber.setText("Total a Receber:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalReceber.add(labelDescricaoTotalReceber, gridBagConstraints);

        labelTotalReceber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalReceber.setForeground(new java.awt.Color(0, 0, 255));
        labelTotalReceber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalReceber.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalReceber.add(labelTotalReceber, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelTotalReceber, gridBagConstraints);

        panelTotalRecebido.setLayout(new java.awt.GridBagLayout());

        labelDescricaoTotalRecebido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoTotalRecebido.setForeground(new java.awt.Color(0, 153, 0));
        labelDescricaoTotalRecebido.setText("Total Recebido:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalRecebido.add(labelDescricaoTotalRecebido, gridBagConstraints);

        labelTotalRecebido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalRecebido.setForeground(new java.awt.Color(0, 153, 0));
        labelTotalRecebido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalRecebido.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTotalRecebido.add(labelTotalRecebido, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelTotalRecebido, gridBagConstraints);

        panelTroco.setLayout(new java.awt.GridBagLayout());

        labelDescricaoTroco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoTroco.setForeground(new java.awt.Color(255, 0, 0));
        labelDescricaoTroco.setText("Troco:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTroco.add(labelDescricaoTroco, gridBagConstraints);

        labelTroco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTroco.setForeground(new java.awt.Color(255, 0, 0));
        labelTroco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTroco.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTroco.add(labelTroco, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelTroco, gridBagConstraints);

        panelTroco1.setLayout(new java.awt.GridBagLayout());

        labelDescricaoTroco1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelDescricaoTroco1.setForeground(new java.awt.Color(0, 0, 204));
        labelDescricaoTroco1.setText("Restante:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTroco1.add(labelDescricaoTroco1, gridBagConstraints);

        labelRestante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelRestante.setForeground(new java.awt.Color(0, 0, 153));
        labelRestante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelRestante.setText("0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelTroco1.add(labelRestante, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panelResumoVenda.add(panelTroco1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelDados.add(panelResumoVenda, gridBagConstraints);

        panelValores.setBorder(javax.swing.BorderFactory.createTitledBorder("Informe os valores pagos:"));
        panelValores.setMinimumSize(new java.awt.Dimension(216, 220));
        panelValores.setPreferredSize(new java.awt.Dimension(200, 180));
        panelValores.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 220));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 220));

        gridValores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(gridValores);

        panelValores.add(jScrollPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelDados.add(panelValores, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tipo de Pagamento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        cboTiposPagamento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel1.add(cboTiposPagamento, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        jValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jValorFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel1.add(jValor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelDados.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelComponentes.add(panelDados, gridBagConstraints);

        panelBotoes.setBackground(new Color(255,255,255,0));
        panelBotoes.setMinimumSize(new java.awt.Dimension(261, 30));
        panelBotoes.setPreferredSize(new java.awt.Dimension(261, 30));
        panelBotoes.setLayout(new java.awt.GridBagLayout());

        botaoConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoConfirmar.png"))); // NOI18N
        botaoConfirma.setText("Confirma (F12)");
        botaoConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panelBotoes.add(botaoConfirma, gridBagConstraints);

        botaoCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoCancelar.png"))); // NOI18N
        botaoCancela.setText("Cancela (ESC)");
        botaoCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 5);
        panelBotoes.add(botaoCancela, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panelComponentes.add(panelBotoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelPrincipal.add(panelComponentes, gridBagConstraints);

        getContentPane().add(panelPrincipal, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmaActionPerformed
        confirma(true);
}//GEN-LAST:event_botaoConfirmaActionPerformed

    private void botaoCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelaActionPerformed
        cancela(false);
}//GEN-LAST:event_botaoCancelaActionPerformed

    private void jValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jValorFocusLost
        SaiuCampoValor(jValor.getText());
    }//GEN-LAST:event_jValorFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoConfirma;
    private javax.swing.JComboBox cboTiposPagamento;
    private javax.swing.JTable gridValores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField jValor;
    private javax.swing.JLabel labelAcrescimo;
    private javax.swing.JLabel labelDesconto;
    private javax.swing.JLabel labelDescricaoAcrescimo;
    private javax.swing.JLabel labelDescricaoDesconto;
    private javax.swing.JLabel labelDescricaoTotalReceber;
    private javax.swing.JLabel labelDescricaoTotalRecebido;
    private javax.swing.JLabel labelDescricaoTotalVenda;
    private javax.swing.JLabel labelDescricaoTroco;
    private javax.swing.JLabel labelDescricaoTroco1;
    private javax.swing.JLabel labelRestante;
    private javax.swing.JLabel labelTotalReceber;
    private javax.swing.JLabel labelTotalRecebido;
    private javax.swing.JLabel labelTotalVenda;
    private javax.swing.JLabel labelTroco;
    private javax.swing.JPanel panelAcrescimo;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelComponentes;
    private javax.swing.JPanel panelDados;
    private javax.swing.JPanel panelDesconto;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelResumoVenda;
    private javax.swing.JPanel panelTotalReceber;
    private javax.swing.JPanel panelTotalRecebido;
    private javax.swing.JPanel panelTotalVenda;
    private javax.swing.JPanel panelTroco;
    private javax.swing.JPanel panelTroco1;
    private javax.swing.JPanel panelValores;
    // End of variables declaration//GEN-END:variables

    private class ConfirmaAction extends AbstractAction {

        public ConfirmaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            confirma(true);
        }
    }

    private class CancelaAction extends AbstractAction {

        public CancelaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            cancela(false);
        }
    }

    private void cancela(boolean auto) {
        String[] opcoes = {"Sim", "Nao"};
        int escolha = JOptionPane.YES_OPTION;
        //Auto opção para não exibir a mensagem a baixo e cancelar direto
        if (!auto) {
            escolha = JOptionPane.showOptionDialog(null, "Confirma o cancelamento dos pagamentos?" + (char) 13 + (char) 10
                    + "Deseja Continuar?", "Encerrar venda.", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
        }
        if (escolha == JOptionPane.YES_OPTION) {
            try {
                // CNC ou NCF
                //Tef.cancelaTefPendentes();
                //Tef.limpaArquivosTemps();
                pagamentoOK = false;
                temVdaCartao = false;
                pagamentoCancelado = true;
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    private void confirma(Boolean solicitaConfirmacao) {
        if (totalRecebido.compareTo(totalReceber) < 0) {
            JOptionPane.showMessageDialog(rootPane, "Valores pagos pelo cliente nao sao suficientes para efetuar o total do pagamento.", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    cboTiposPagamento.requestFocus();
                }
            });

        } else {
            labelTroco.setText(formatter.format(troco));
            if (solicitaConfirmacao) {
                String[] opcoes = {"Sim", "Nao"};
                int escolha = JOptionPane.showOptionDialog(this, "Confirma os valores e encerra venda?", "Encerrar venda.",
                        JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, opcoes, null);
                if (escolha == JOptionPane.YES_OPTION) {
                    sim();
                } else {
                    nao();
                }
            } else {
                sim();
            }
        }
    }

    private void sim() {
        try {
            String pValorPago = "";
            String pNumeroCupom = "";
            String pIdentificacao = "";
            String pNumeroTransacao = "";
            String pFormaPagamento = "02";
            Integer ultimaTrans = -1;
            boolean imprimiu = true;
            boolean problemaTef = false;

            TotalTipoPagamentoVO totalTipoPagamento = new TotalTipoPagamentoVO();

            if (transacaoComTef) {
                Collections.sort(listaTotalTipoPagamento, new ComparatorTipoPagamento());
            }

            for (int i = 0; i < listaTotalTipoPagamento.size(); i++) {
                totalTipoPagamento = listaTotalTipoPagamento.get(i);
            }
                //limpesa do buffer;
                for (int i = 0; i < listaTotalTipoPagamento.size(); i++) {
                    totalTipoPagamento = listaTotalTipoPagamento.get(i);
                    if (totalTipoPagamento.getTipoPagamentoVO().getTEF().equals("S")) {
                        //Tef.limpaBuffer(totalTipoPagamento.getNSU());
                    }
                }


            //grava dados de detalhe no banco
            TotalTipoPagamentoController totalTipoPagamentoControl = new TotalTipoPagamentoController();
            totalTipoPagamentoControl.gravaTotaisVenda((ArrayList<TotalTipoPagamentoVO>) listaTotalTipoPagamento, empresa, pedido, conexao, totalVenda);
            if (problemaTef) {
                pagamentoOK = false;
            } else {
                pagamentoOK = true;
            }
            dispose();
            
        } catch (Throwable t) {
           t.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, t.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nao() {
        gridValores.editCellAt(0, 1);
    }

    private void configuraGridValores(List<TipoPagamentoVO> listaTipoPagamento) {

        try {
            DefaultComboBoxModel box = new DefaultComboBoxModel();

            for (int i = 0; i < listaTipoPagamento.size(); i++) {
                box.addElement(listaTipoPagamento.get(i).getCodigo().toString() + " - " + listaTipoPagamento.get(i).getDescricao().toString());
            }

            cboTiposPagamento.setModel(box);

            TipoPagamentoVO tipovazio = new TipoPagamentoVO();

            List<TipoPagamentoVO> listavazia = new ArrayList<TipoPagamentoVO>();

            //listavazia.set(0, tipovazio);

            gridValores.setModel(new EfetuaPagamentoTableModel(listavazia));
            gridValores.setSelectionModel(new DefaultListSelectionModel() {

                public String toString() {
                    return "gridValores";
                }
            });

            gridValores.setAutoCreateColumnsFromModel(false);
            gridValores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            FontMetrics fm = gridValores.getFontMetrics(gridValores.getFont());
            gridValores.setColumnModel(new EfetuaPagamentoColumnModel(fm));
        } catch(Exception e) {
JOptionPane.showMessageDialog(null, "EfetuaPagamento.configuraGridValores() - Erro: "+e);
        }
    }

    public void incluiPagamento(String codigo, double valor) {  // throws ACBrException {
        String NSU = "", nomeRede = "", data = "", hora = "";
        Integer retorno = 1;

        TipoPagamentoVO tipoPagamento = new TipoPagamentoVO();
        TipoPagamentoController tipoPagamentoControl = new TipoPagamentoController(conexao);
        // consulta o tipo de pagamento pelo codigo
        tipoPagamento = tipoPagamentoControl.consultaPeloCodigo(codigo);
        tipoPagamento.setValor(valor);
        if (
                tipoPagamento.getDescricao().equals("CARTAO DE CREDITO") ||
                tipoPagamento.getDescricao().equals("CARTAO DE DEBITO")
                ) {
            temVdaCartao = true;
        }

        if (totalReceber.compareTo(totalRecebido) <= 0) {
            JOptionPane.showMessageDialog(rootPane, "Valor recebido � suficiente para realizar o pagamento!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            retorno = -1;
        } else if ((multTef) && (valor > (totalReceber.subtract(totalRecebido).doubleValue()))) {
            JOptionPane.showMessageDialog(rootPane, "Pagamento que envolve TEF nao pode ter troco!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            retorno = -1;
        } else {
        }

        if (retorno == 1) {
            TotalTipoPagamentoVO totalTipoPagamento = new TotalTipoPagamentoVO();
            totalTipoPagamento.setTipoPagamentoVO(new TipoPagamentoVO());
            totalTipoPagamento.getTipoPagamentoVO().setId(tipoPagamento.getId());
            totalTipoPagamento.setValor(valor);
            totalTipoPagamento.getTipoPagamentoVO().setCodigo(tipoPagamento.getCodigo());
            totalTipoPagamento.getTipoPagamentoVO().setTEF(tipoPagamento.getTEF());
            totalTipoPagamento.getTipoPagamentoVO().setDescricao(tipoPagamento.getDescricao());
            totalTipoPagamento.getTipoPagamentoVO().setValor(tipoPagamento.getValor());

            if (listaTotalTipoPagamento.isEmpty()) {
                listaTotalTipoPagamento.add(totalTipoPagamento);
            } else {
                boolean contemTipoPagamento = false;
                for (int i = 0; i < listaTotalTipoPagamento.size(); i++) {
                    if (listaTotalTipoPagamento.get(i).getTipoPagamentoVO().getCodigo().equals(totalTipoPagamento.getTipoPagamentoVO().getCodigo())) {
                        BigDecimal valorAtual = BigDecimal.valueOf(listaTotalTipoPagamento.get(i).getTipoPagamentoVO().getValor());
                        valorAtual = valorAtual.add(BigDecimal.valueOf(totalTipoPagamento.getTipoPagamentoVO().getValor()));
                        valorAtual.setScale(2, RoundingMode.DOWN);
                        listaTotalTipoPagamento.get(i).getTipoPagamentoVO().setValor(valorAtual.doubleValue());
                        listaTotalTipoPagamento.get(i).setValor(valorAtual.doubleValue());
                        contemTipoPagamento = true;
                    }
                }
                if (!contemTipoPagamento) {
                    listaTotalTipoPagamento.add(totalTipoPagamento);
                }
            }

            listaTipoPagamento.add(tipoPagamento);
            totalRecebido = totalRecebido.add(BigDecimal.valueOf(valor));
            if ((totalReceber.compareTo(totalRecebido)) > 0) {
                Restante = totalReceber.add(totalRecebido.negate());
            } else {
                Restante = BigDecimal.ZERO;
                troco = totalRecebido.add(totalReceber.negate());
            }

            labelTotalRecebido.setText(formatter.format(totalRecebido));
            labelRestante.setText(formatter.format(Restante));
            labelTroco.setText(formatter.format(troco));
            gridValores.setModel(new EfetuaPagamentoTableModel(listaTipoPagamento));

            if ((totalReceber.compareTo(totalRecebido) == 0) && multTef) {
                //finalizar pagamento
                confirma(false);
            }
        }
    }

    private void SaiuCampoValor(String ValorCampo) {
        String tipopag = cboTiposPagamento.getSelectedItem().toString().substring(0, 2);
        double valor = (Double.parseDouble(ValorCampo.replace(",", "").replace(".", ""))) / 100;
        if (valor > 0) {
            try {
                incluiPagamento(tipopag, valor);
            } catch (Exception e) {
                e.printStackTrace();
                //JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(rootPane, "Erro do Sistema: "+e);
            }
            ;
            jValor.setText("0");
            //cboTiposPagamento.requestFocus();
            jValor.requestFocus();
        } else {
            cboTiposPagamento.requestFocus();
        }
    }
}
