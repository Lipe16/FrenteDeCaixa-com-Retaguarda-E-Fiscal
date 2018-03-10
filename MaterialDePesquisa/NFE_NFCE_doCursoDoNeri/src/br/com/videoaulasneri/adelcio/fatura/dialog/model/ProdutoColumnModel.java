/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import java.awt.FontMetrics;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ProdutoColumnModel extends DefaultTableColumnModel {

    public ProdutoColumnModel(FontMetrics fm) {
        addColumn(criaColuna(0, 50, fm, false, "Codigo"));
        addColumn(criaColuna(1, 110, fm, false, "EAN"));
        addColumn(criaColuna(2, 400, fm, false, "Nome_reduzido"));
        addColumn(criaColuna(3, 50, fm, false, "Unidade"));
        addColumn(criaColuna(4, 80, fm, false, "Preco"));
    }

    private TableColumn criaColuna(int columnIndex, int largura, FontMetrics fm, boolean resizable, String titulo) {
        int larguraTitulo = fm.stringWidth(titulo + "  ");
        if (largura < larguraTitulo) {
            largura = larguraTitulo;
        }

        TableColumn col = new TableColumn(columnIndex);
        col.setCellRenderer(new ProdutoCellRenderer());
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