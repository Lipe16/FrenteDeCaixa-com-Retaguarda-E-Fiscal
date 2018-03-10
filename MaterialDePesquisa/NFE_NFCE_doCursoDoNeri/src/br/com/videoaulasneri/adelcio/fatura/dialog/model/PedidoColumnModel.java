/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import java.awt.FontMetrics;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class PedidoColumnModel extends DefaultTableColumnModel {

    public PedidoColumnModel(FontMetrics fm) {
        addColumn(criaColuna(0, 70, fm, false, "Pedido"));
        addColumn(criaColuna(1, 50, fm, false, "Num.NFCe"));
        addColumn(criaColuna(2, 100, fm, false, "Data"));
        addColumn(criaColuna(3, 150, fm, false, "Valor"));
        addColumn(criaColuna(4, 100, fm, false, "Documento"));
        addColumn(criaColuna(5, 70, fm, false, "Qt.Itens"));
    }

    private TableColumn criaColuna(int columnIndex, int largura, FontMetrics fm, boolean resizable, String titulo) {
        int larguraTitulo = fm.stringWidth(titulo + "  ");
        if (largura < larguraTitulo) {
            largura = larguraTitulo;
        }

        TableColumn col = new TableColumn(columnIndex);
        col.setCellRenderer(new PedidoCellRenderer());
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