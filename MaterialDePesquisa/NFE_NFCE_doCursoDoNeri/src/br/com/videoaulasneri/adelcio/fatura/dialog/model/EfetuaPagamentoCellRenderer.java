/*

Descri��o: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

public class EfetuaPagamentoCellRenderer extends DefaultTableCellRenderer {

    /** Cria uma nova renderização da tabela.
     */
    public EfetuaPagamentoCellRenderer() {
        super();
    }

    /** Retorna a personalização da tabela para que possa ser mostrada ao usuário.
     */
    public Component getTableCellRendererComponent(javax.swing.JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        Color corFundoZebrado = new Color(240, 240, 240);
        Color corFundoNormal = new Color(255, 255, 230);

        label.setFont(new java.awt.Font("Tahoma", 0, 12));

        if ((row % 2) == 0) {
            label.setBackground(corFundoNormal);
        } else {
            label.setBackground(corFundoZebrado);
        }

        if (isSelected) {
            label.setBackground(new Color(255, 255, 172));
        }

        if (column == 0) {
            label.setHorizontalAlignment(LEFT);
        } else if (column == 1) {
            label.setHorizontalAlignment(RIGHT);
        }

        return label;
    }
}
