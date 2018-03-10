/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.view;

import br.com.videoaulasneri.adelcio.fatura.MenuFiscalAction;
import br.com.videoaulasneri.adelcio.fatura.dialog.controller.ProdutoController;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.ProdutoColumnModel;
import br.com.videoaulasneri.adelcio.fatura.dialog.model.ProdutoTableModel;
import br.com.videoaulasneri.adelcio.fatura.bean.ProdutoVO;
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

public class ImportaProduto extends javax.swing.JDialog {

    private List<ProdutoVO> listaProduto = new ArrayList<ProdutoVO>();
    public boolean cancelado = false;
    boolean produtoProprio = false;
    boolean produtoTerceiro = false;
    Connection conexao;
    boolean display = false;

    public ImportaProduto(java.awt.Frame parent, boolean modal, Connection conexao) {
        super(parent, modal);
        this.conexao = conexao;
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto 1 . . .");
        initComponents();
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto 2 . . .");
        configuraTela();
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto 3 . . .");
    }

    public ImportaProduto(java.awt.Frame parent, boolean modal, boolean produtoProprio, boolean produtoTerceiro, Connection conexao) {
        super(parent, modal);
        initComponents();
        this.produtoProprio = produtoProprio;
        this.produtoTerceiro = produtoTerceiro;
        this.conexao = conexao;
//        Conexao_fatura con = new Conexao_fatura();
//        conexao = con.conecta("postgres", "nerizon", "org.postgresql.Driver", "jdbc:postgresql://localhost/NFefacil");
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
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto -  configuraTela() 1 . . .");

        ProdutoController produtoControl = new ProdutoController();
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto -  configuraTela() 2 . . .");
        //TODO : O que pode dar errado nessa rotina?
        //Conexao_fatura con = new Conexao_fatura();
        //conexao = con.conecta("postgres", "nerizon", "org.postgresql.Driver", "jdbc:postgresql://localhost/NFefacil");

//        if (produtoProprio) {
//            configuraGridProduto(produtoControl.produtoFiltro("a", "P", conexao));
//        } else if (produtoTerceiro) {
//            configuraGridProduto(produtoControl.produtoFiltro("a", "T", conexao));
//        } else {
            configuraGridProduto(produtoControl.produtoFiltro("", conexao));
//        }
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto -  configuraTela() 3 . . .");

        DigitaAction digitaAction = new DigitaAction();
        botaoFocoEdit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "digitaAction");
        botaoFocoEdit.getActionMap().put("digitaAction", digitaAction);

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
        gridProduto.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto -  configuraTela() 4 . . .");

        //editLocaliza.requestFocus();

        this.setPreferredSize(new Dimension(850, 430));
        //this.pack();
if (display) JOptionPane.showMessageDialog(null, "ImportaProduto -  configuraTela() 5 . . .");
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
        gridProduto = new javax.swing.JTable();
        panelLocaliza = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        botaoLocaliza = new javax.swing.JButton();
        editLocaliza = new javax.swing.JTextField();
        panelBotoes = new javax.swing.JPanel();
        botaoFocoEdit = new javax.swing.JButton();
        botaoCancela = new javax.swing.JButton();
        botaoConfirma = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Localiza e Importa Produto");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        panelPrincipal.setMinimumSize(new java.awt.Dimension(918, 409));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(918, 409));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panelPrincipal.add(jLabel1, gridBagConstraints);

        panelComponentes.setLayout(new java.awt.GridBagLayout());

        panelGrid.setBackground(new Color(255,255,255,0));
        panelGrid.setBorder(javax.swing.BorderFactory.createTitledBorder("Relação de Produtos:"));
        panelGrid.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(452, 200));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 200));

        gridProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5"
            }
        ));
        jScrollPane1.setViewportView(gridProduto);

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

        botaoFocoEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoLocalizar.png"))); // NOI18N
        botaoFocoEdit.setText("Habilita Digitação(F3)");
        botaoFocoEdit.setToolTipText("Coloca o Cursor no Campo de Filtro");
        botaoFocoEdit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botaoFocoEdit.setMaximumSize(new java.awt.Dimension(180, 57));
        botaoFocoEdit.setPreferredSize(new java.awt.Dimension(180, 25));
        botaoFocoEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFocoEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelBotoes.add(botaoFocoEdit, gridBagConstraints);
        botaoFocoEdit.getAccessibleContext().setAccessibleName("Foco no Filtro");

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

    private void botaoFocoEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFocoEditActionPerformed
        editLocaliza.requestFocus();
}//GEN-LAST:event_botaoFocoEditActionPerformed

    private void botaoCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelaActionPerformed
        cancelado = true;
        dispose();
}//GEN-LAST:event_botaoCancelaActionPerformed

    private void botaoLocalizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLocalizaActionPerformed
        localiza();
    }//GEN-LAST:event_botaoLocalizaActionPerformed

    private void botaoConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConfirmaActionPerformed
        cancelado = false;
        dispose();

    }//GEN-LAST:event_botaoConfirmaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ImportaProduto dialog = new ImportaProduto(new javax.swing.JFrame(), true, null);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoConfirma;
    private javax.swing.JButton botaoFocoEdit;
    private javax.swing.JButton botaoLocaliza;
    private javax.swing.JTextField editLocaliza;
    private javax.swing.JTable gridProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelComponentes;
    private javax.swing.JPanel panelGrid;
    private javax.swing.JPanel panelLocaliza;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables

    private class DigitaAction extends AbstractAction {

        public DigitaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            editLocaliza.requestFocus();
        }
    }

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
        ProdutoController produtoControl = new ProdutoController();
        if (produtoProprio) {
            listaProduto = produtoControl.produtoFiltro(editLocaliza.getText().toUpperCase(), "P", conexao);
        } else if (produtoTerceiro) {
            listaProduto = produtoControl.produtoFiltro(editLocaliza.getText().toUpperCase(), "T", conexao);
        } else {
            listaProduto = produtoControl.produtoFiltro(editLocaliza.getText().toUpperCase(), conexao);
        }
        if (listaProduto != null) {
            configuraGridProduto(listaProduto);
            gridProduto.setRowSelectionInterval(0, 0);
            gridProduto.requestFocus();
        } else {
            configuraGridProduto(new ArrayList<ProdutoVO>());
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado!", "Aviso do sistema", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void configuraGridProduto(List<ProdutoVO> listaProduto) {
        this.listaProduto = listaProduto;

        gridProduto.setModel(new ProdutoTableModel(listaProduto));
        gridProduto.setSelectionModel(new DefaultListSelectionModel() {

            public String toString() {
                return "gridProduto";
            }
        });

        gridProduto.setAutoCreateColumnsFromModel(false);
        gridProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        FontMetrics fm = gridProduto.getFontMetrics(gridProduto.getFont());
        gridProduto.setColumnModel(new ProdutoColumnModel(fm));
    }

    public int getGTIN() {
//JOptionPane.showMessageDialog(this, "importaProduto() - metodo: getGTIN()");
        if (gridProduto.getSelectedRow() != -1) {
            AbstractTableModel modelo = (AbstractTableModel) gridProduto.getModel();
            return Integer.parseInt(modelo.getValueAt(gridProduto.getSelectedRow(), 0).toString());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum produto selecionado!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public ProdutoVO getProduto(){
        if (gridProduto.getSelectedRow() != -1) {
            ProdutoTableModel modelo = (ProdutoTableModel) gridProduto.getModel();
            return modelo.getValues(gridProduto.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum produto selecionado!", "Aviso do Sistema", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
