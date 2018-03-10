/*

DescriÁ„o: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import br.com.videoaulasneri.adelcio.fatura.bean.TipoPagamentoVO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EfetuaPagamentoTableModel extends AbstractTableModel {

    private List<TipoPagamentoVO> listaTipoPagamento;

    public EfetuaPagamentoTableModel(List<TipoPagamentoVO> listaTipoPagamento) {
        this.listaTipoPagamento = listaTipoPagamento;
    }

    /**
     * Obtem o valor na linha e coluna.
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoPagamentoVO tipoPagamento = listaTipoPagamento.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipoPagamento.getDescricao();
            case 1:
                return tipoPagamento.getValor();
        }
        return null;
    }

    /**
     * Retorna o numero de linhas no modelo.
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return listaTipoPagamento.size();
    }

    /**
     * Retorna o numero de colunas no modelo.
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 2;
    }

    public TipoPagamentoVO getValues(int rowIndex) {
        return listaTipoPagamento.get(rowIndex);
    }

    public boolean isCellEditable(int row, int col) {
        //informa as colunas que n√£o desejamos edi√ß√£o
        if (col == 0 || col == 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        //TODO : o que pode ocorrer de errado aqui?
        if (value.toString().equals("")) {
            value = "0.00";
        }
        listaTipoPagamento.get(row).setValor(Double.valueOf(value.toString()));
        fireTableCellUpdated(row, col);
    }
}
