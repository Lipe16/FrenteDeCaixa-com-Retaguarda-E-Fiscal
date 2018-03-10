/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.view;

import br.com.videoaulasneri.adelcio.utilitarios.Biblioteca;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class DocumentoCliente extends javax.swing.JDialog {

    private boolean cancelado = false;
    String documento, nomeCliente;

    public DocumentoCliente(java.awt.Frame parent, boolean modal, String documento, String nomeCliente) {
        super(parent, modal);
        this.documento = documento;
        this.nomeCliente = nomeCliente;
        initComponents();

        int r = 224;  //  Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(0, 3));
        int g = 223;  //  Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(4, 7));
        int b = 227;  //  Integer.valueOf(Caixa.configuracao.getCorJanelasInternas().substring(8, 11));

        //TODO : Ã‰ necessÃ¡rio configurar a cor de cada Panel?
        panelPrincipal.setBackground(new Color(r, g, b));
        panelBotoes.setBackground(new Color(r, g, b));
        panelComponentes.setBackground(new Color(r, g, b));
        panelGerente.setBackground(new Color(r, g, b));

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

        MenuFiscalAction menuFiscalAction = new MenuFiscalAction();
        editDocumento.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "menuFiscalAction");
        editDocumento.getActionMap().put("menuFiscalAction", menuFiscalAction);

        editDocumento.setText(this.documento);
        editNomeCliente.setText(this.nomeCliente);
        this.setPreferredSize(new Dimension(500, 280));
        this.pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelComponentes = new javax.swing.JPanel();
        panelGerente = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        editDocumento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        editNomeCliente = new javax.swing.JTextField();
        panelBotoes = new javax.swing.JPanel();
        botaoConfirma = new javax.swing.JButton();
        botaoCancela = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Informa Dados do Cliente");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        panelPrincipal.setMinimumSize(new java.awt.Dimension(500, 200));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(500, 200));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/telaUsuarioSenha01.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panelPrincipal.add(jLabel1, gridBagConstraints);

        panelComponentes.setLayout(new java.awt.GridBagLayout());

        panelGerente.setBackground(new Color(255,255,255,0));
        panelGerente.setBorder(javax.swing.BorderFactory.createTitledBorder("Identificação do Cliente"));
        panelGerente.setMinimumSize(new java.awt.Dimension(300, 129));
        panelGerente.setPreferredSize(new java.awt.Dimension(300, 129));
        panelGerente.setLayout(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Documento:");
        panelGerente.add(jLabel2);
        jLabel2.setBounds(20, 40, 90, 14);

        editDocumento.setPreferredSize(new java.awt.Dimension(60, 20));
        panelGerente.add(editDocumento);
        editDocumento.setBounds(130, 40, 180, 20);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nome:");
        panelGerente.add(jLabel3);
        jLabel3.setBounds(20, 70, 90, 14);
        panelGerente.add(editNomeCliente);
        editNomeCliente.setBounds(130, 70, 270, 20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panelComponentes.add(panelGerente, gridBagConstraints);
        panelGerente.getAccessibleContext().setAccessibleName("Documento/Nome do Cliente");

        panelBotoes.setBackground(new Color(255,255,255,0));
        panelBotoes.setLayout(new java.awt.GridBagLayout());

        botaoConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoConfirmar.png"))); // NOI18N
        botaoConfirma.setText("Confirma (F12)");
        botaoConfirma.setMaximumSize(new java.awt.Dimension(140, 25));
        botaoConfirma.setPreferredSize(new java.awt.Dimension(140, 25));
        botaoConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConfirmaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panelBotoes.add(botaoConfirma, gridBagConstraints);

        botaoCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoCancelar.png"))); // NOI18N
        botaoCancela.setText("Cancela (ESC)");
        botaoCancela.setMaximumSize(new java.awt.Dimension(140, 25));
        botaoCancela.setPreferredSize(new java.awt.Dimension(140, 25));
        botaoCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
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
        dispose();
}//GEN-LAST:event_botaoCancelaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoConfirma;
    private javax.swing.JTextField editDocumento;
    private javax.swing.JTextField editNomeCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelBotoes;
    private javax.swing.JPanel panelComponentes;
    private javax.swing.JPanel panelGerente;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables

    private class MenuFiscalAction extends AbstractAction {

        public MenuFiscalAction() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //Caixa caixa = Caixa.getCaixa();
            //caixa.fechaMenus();
            //caixa.acionaMenuFiscal();
            cancelado = true;
            dispose();
        }
    }

    private class ConfirmaAction extends AbstractAction {

        public ConfirmaAction() {
        }

        public void actionPerformed(ActionEvent e) {
            cancelado = false;
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

    public void setLogin(String login, String senha) {
        editDocumento.setText(login);
        editNomeCliente.setText(senha);
    }
    public String validaDados(){
        String retorno = null;
        if (!cancelado) {
            if (validaDoc(editDocumento.getText())) {
                retorno = editDocumento.getText()+"*"+editNomeCliente.getText();
            }
        }
        return retorno;
    }
    private boolean validaDoc(String texto) {
        boolean retorno = true;
        //if (numDoc == null || numDoc.length() == 0) {
        //    numDoc = "";
        //} else {
            if (texto != null && texto.length() > 0) {
                String cnpj_cpf = tiraPontuacao(texto);
                if (cnpj_cpf.length() != 11 && cnpj_cpf.length() != 14) {
                    JOptionPane.showMessageDialog(null, "Documento Inválido! Informe 11 carac.para CPF ou 14 para CNPJ");
                    retorno = false;
                } else {
                    //JOptionPane.showMessageDialog(null, "Documento Sem Pontuacao: ["+ cnpj_cpf+"]");
                    boolean dv_valido = false;
                    //System.out.println("Cnpj/Cpf sem pontuacao: "+cnpj_cpf);
                    if (cnpj_cpf.length() == 11){
                        if (Biblioteca.isValidCPF(cnpj_cpf)) dv_valido = true;
                    } else {
                        if (Biblioteca.isValidCNPJ(cnpj_cpf)) dv_valido = true;
                    }
                    if (!dv_valido){
                        JOptionPane.showMessageDialog(null, "\n O Documento informado não é válido!");
                        retorno = false;
                    } else {
                        editDocumento.setText(cnpj_cpf);
                    }
                }
            //}
        }

        return retorno;
    }
    private String tiraPontuacao( String wcampo )
    {
            char cpo[] = new char[wcampo.length()];
            int j = 0;
            for ( int i=0; i<wcampo.length(); i++ )
            {
                    if ( wcampo.charAt( i ) == '.' ) {  }
                    else if ( wcampo.charAt( i ) == ' ' ) {  }
                    else if ( wcampo.charAt( i ) == '-' ) {  }
                    else if ( wcampo.charAt( i ) == '(' ) {  }
                    else if ( wcampo.charAt( i ) == ')' ) {  }
                    else if ( wcampo.charAt( i ) == '/' ) {  }
                    else if ( wcampo.charAt( i ) == ',' ) {  }
                    else if ( wcampo.charAt( i ) == '*' ) {  }
                    else { cpo[j] = wcampo.charAt( i ); j++; }
            }
            String resultado = "";
            for (int i=0;i<j;i++)
            {
                    resultado = resultado + cpo[i];
           }
            return resultado;
    }

}
