/*

Descrição: Classe Auxiliar das Classes Modal da Frente de Caixa

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura;

import br.com.videoaulasneri.adelcio.fatura.NFCe;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

public class MenuFiscalAction extends AbstractAction {

    private JDialog dialog;

    public MenuFiscalAction() {
    }

    public MenuFiscalAction(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.dialog != null) {
            NFCe caixa = NFCe.getCaixa();
            //caixa.fechaMenus();
            //caixa.acionaMenuFiscal();
            this.dialog.dispose();
        }
    }
}
