/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import java.awt.FontMetrics;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class EfetuaPagamentoColumnModel extends DefaultTableColumnModel {

    /** Cria um novo Modelo de Coluna.
     */
    public EfetuaPagamentoColumnModel(FontMetrics fm) {
        addColumn(criaColuna(0, 140, fm, false, "Descricao"));
        addColumn(criaColuna(1, 90, fm, false, "Valor"));
    }

    /* Cria uma nova coluna.
     */
    private TableColumn criaColuna(int columnIndex, int largura, FontMetrics fm, boolean resizable, String titulo) {
        int larguraTitulo = fm.stringWidth(titulo + "  ");
        if (largura < larguraTitulo) {
            largura = larguraTitulo;
        }

        TableColumn col = new TableColumn(columnIndex);
        col.setCellRenderer(new EfetuaPagamentoCellRenderer());
        col.setHeaderRenderer(null);
        col.setHeaderValue(titulo);
        col.setPreferredWidth(largura);
        if (!resizable) {
            col.setMaxWidth(largura);
            col.setMinWidth(largura);
        }
        col.setResizable(resizable);
        return col;
    }
}
