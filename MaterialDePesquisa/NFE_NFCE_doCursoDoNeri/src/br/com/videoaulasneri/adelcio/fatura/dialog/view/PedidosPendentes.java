/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.view;

import br.com.videoaulasneri.adelcio.fatura.MenuFiscalAction;
import br.com.videoaulasneri.adelcio.fatura.dialog.controller.PedidoController;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.PedidoColumnModel;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.PedidoTableModel;
import br.com.videoaulasneri.adelcio.fatura.bean.Ped65VO;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import br.com.videoaulasneri.adelcio.utilitarios.Conexao_fatura;

public class PedidosPendentes extends javax.swing.JDialog {

    private List<Ped65VO> listaPedido = new ArrayList<Ped65VO>();
    boolean cancelado = false;
    boolean autorizada = false;
    int codcaixa = 0;
    Connection conexao;
    boolean display = false;

    public PedidosPendentes(java.awt.Frame parent, boolean modal, int codcaixa, Connection conexao, boolean autorizada) {
        super(parent, modal);
        this.codcaixa = codcaixa;
        this.conexao = conexao;
        this.autorizada = autorizada;
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes 1 . . .");
        initComponents();
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes 2 . . .");
        configuraTela();
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes 3 . . .");
    }

    public PedidosPendentes(java.awt.Frame parent, boolean modal, boolean produtoProprio, boolean produtoTerceiro, int codcaixa, Connection conexao, boolean autorizada) {
        super(parent, modal);
        initComponents();
        this.codcaixa = codcaixa;
        this.conexao = conexao;
        this.autorizada = autorizada;
        configuraTela();
    }

    private void configuraTela(){
        int r = 224;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(0, 3));
        int g = 223;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(4, 7));
        int b = 227;  //Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(8, 11));

        //TODO : Ã‰ necessÃ¡rio configurar a cor de cada Panel?
        panelPrincipal.setBackground(new Color(r, g, b));
        panelComponentes.setBackground(new Color(r, g, b));
        panelGrid.setBackground(new Color(r, g, b));
        panelLocaliza.setBackground(new Color(r, g, b));
        panelBotoes.setBackground(new Color(r, g, b));
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes  -  configuraTela() 1 . . .");

        PedidoController pedidoControl = new PedidoController();
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes -  configuraTela() 2 . . .");
        //TODO : O que pode dar errado nessa rotina?
        //Conexao_fatura con = new Conexao_fatura();
        //conexao = con.conecta("postgres", "nerizon", "org.postgresql.Driver", "jdbc:postgresql://localhost/NFefacil");

        //configuraGridPedido(pedidoControl.pedidoFiltro("1", conexao));
        boolean result = configuraGridPedido(pedidoControl.tabelaPedido(codcaixa, conexao, autorizada));
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes -  configuraTela() 3 . . .");

        CancelaAction cancelaAction = new CancelaAction();
        botaoCancela.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelaAction");
        botaoCancela.getActionMap().put("cancelaAction", cancelaAction);

        ConfirmaAction confirmaAction = new ConfirmaAction();
        botaoConfirma.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "confirmaAction");
        botaoConfirma.getActionMap().put("confirmaAction", confirmaAction);

        LocalizaAction localizaAction = new LocalizaAction();
        botaoConfirma.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "localizaAction");
        botaoConfirma.getActionMap().put("localizaAction", localizaAction);

        MenuFiscalAction menuFiscalAction = new MenuFiscalAction(this);
        editLocaliza.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "menuFiscal");
        editLocaliza.getActionMap().put("menuFiscal", menuFiscalAction);
        //troca TAB por ENTER
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
        gridPedido.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes -  configuraTela() 4 . . .");

        editLocaliza.requestFocus();

        this.setPreferredSize(new Dimension(850, 430));
        //this.pack();
if (display) JOptionPane.showMessageDialog(null, "PedidosPendentes -  configuraTela() 5 . . .");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelComponentes = new javax.swing.JPanel();
        panelGrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridPedido = new javax.swing.JTable();
        panelLocaliza = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        botaoLocaliza = new javax.swing.JButton();
        editLocaliza = new javax.swing.JTextField();
        panelBotoes = new javax.swing.JPanel();
        botaoConfirma = new javax.swing.JButton();
        botaoCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Localiza e Importa Pedido");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        panelPrincipal.setMinimumSize(new java.awt.Dimension(918, 409));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(618, 409));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panelPrincipal.add(jLabel1, gridBagConstraints);

        panelComponentes.setLayout(new java.awt.GridBagLayout());

        panelGrid.setBackground(new Color(255,255,255,0));
        panelGrid.setBorder(javax.swing.BorderFactory.createTitledBorder("Relação de Pedidos:"));
        panelGrid.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(452, 200));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 200));

        gridPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6"
            }
        ));
        jScrollPane1.setViewportView(gridPedido);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelGrid.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panelComponentes.add(panelGrid, gridBagConstraints);

        panelLocaliza.setBackground(new Color(255,255,255,0));
        panelLocaliza.setBorder(javax.swing.BorderFactory.createTitledBorder("Informe dados para localização:"));
        panelLocaliza.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Procurar por:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        panelLocaliza.add(jLabel2, gridBagConstraints);

        botaoLocaliza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoLocalizar.png"))); // NOI18N
        botaoLocaliza.setText("Localiza (F2)");
        botaoLocaliza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLocalizaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panelLocaliza.add(botaoLocaliza, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panelLocaliza.add(editLocaliza, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panelComponentes.add(panelLocaliza, gridBagConstraints);

        panelBotoes.setBackground(new Color(255,255,255,0));
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
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
        dispose();
}//GEN-LAST:event_botaoConfirmaActionPerformed

    private void botaoCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelaActionPerformed
        cancelado = true;
        dispose();
}//GEN-LAST:event_botaoCancelaActionPerformed

    private void botaoLocalizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLocalizaActionPerformed
        localiza();
    }//GEN-LAST:event_botaoLocalizaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PedidosPendentes dialog = new PedidosPendentes(new javax.swing.JFrame(), true, 0, null, false);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoConfirma;
    private javax.swing.JButton botaoLocaliza;
    private javax.swing.JTextField editLocaliza;
    private javax.swing.JTable gridPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelComponentes;
    private javax.swing.JPanel panelGrid;
    private javax.swing.JPanel panelLocaliza;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables

    private class ConfirmaAction extends AbstractAction {

        public ConfirmaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class CancelaAction extends AbstractAction {

        public CancelaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            cancelado = true;
            dispose();
        }
    }

    private class LocalizaAction extends AbstractAction {

        public LocalizaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            localiza();
        }
    }

    private void localiza() {
        PedidoController pedidoControl = new PedidoController();
        listaPedido = pedidoControl.pedidoFiltro(editLocaliza.getText(), codcaixa, conexao, autorizada);
        if (listaPedido != null) {
            configuraGridPedido(listaPedido);
            gridPedido.setRowSelectionInterval(0, 0);
            gridPedido.requestFocus();
        } else {
            configuraGridPedido(new ArrayList<Ped65VO>());
            JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado!", "Aviso do sistema", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean configuraGridPedido(List<Ped65VO> listaPedido) {
        boolean retorno = true;
        this.listaPedido = listaPedido;
        if (listaPedido != null && listaPedido.size() > 0) {
            gridPedido.setModel(new PedidoTableModel(listaPedido));
            gridPedido.setSelectionModel(new DefaultListSelectionModel() {

                public String toString() {
                    return "gridPedido";
                }
            });

            gridPedido.setAutoCreateColumnsFromModel(false);
            gridPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            FontMetrics fm = gridPedido.getFontMetrics(gridPedido.getFont());
            gridPedido.setColumnModel(new PedidoColumnModel(fm));
        } else {
            //JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum registro na tabela para exibir!");
            retorno = false;
        }
        return retorno;
    }

    public String getPedido() {
//JOptionPane.showMessageDialog(this, "PedidosPendentes() - metodo: getPedido() - entrou ");
        if (gridPedido.getSelectedRow() != -1) {
//JOptionPane.showMessageDialog(this, "PedidosPendentes() - metodo: getPedido() - selecionou linha: "+gridPedido.getSelectedRow());
            AbstractTableModel modelo = (AbstractTableModel) gridPedido.getModel();
//JOptionPane.showMessageDialog(this, "PedidosPendentes() - metodo: getPedido() - instanciou modelo . . . ");
//JOptionPane.showMessageDialog(this, "PedidosPendentes() - metodo: getPedido() - capturou pedido: "+modelo.getValueAt(gridPedido.getSelectedRow(), 0));
            //String numPedido = ((String) modelo.getValueAt(gridPedido.getSelectedRow(), 0));
            String numPedido = modelo.getValueAt(gridPedido.getSelectedRow(), 0).toString();
//JOptionPane.showMessageDialog(this, "PedidosPendentes() - metodo: getPedido() - Pedido capturado: "+numPedido);
            return numPedido;
        } else {
            //JOptionPane.showMessageDialog(this, "Nenhum pedido selecionado!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    public Ped65VO getPed65(){
        if (gridPedido.getSelectedRow() != -1) {
            PedidoTableModel modelo = (PedidoTableModel) gridPedido.getModel();
            return modelo.getValues(gridPedido.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum pedido selecionado!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
