/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import br.com.videoaulasneri.adelcio.fatura.bean.Ped65VO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PedidoTableModel extends AbstractTableModel {

    private List<Ped65VO> listaPedido;

    public PedidoTableModel(List<Ped65VO> listaCliente) {
        this.listaPedido = listaCliente;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Ped65VO pedido = listaPedido.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return pedido.getPedido();
            case 1:
                return pedido.getNumero_nfe();
            case 2:
                return pedido.getData_digitacao();
            case 3:
                return pedido.getValor_total();
            case 4:
                return pedido.getDocumento();
            case 5:
                return pedido.getQtde_itens();
        }
        return null;
    }

    @Override
    public int getRowCount() {

        return listaPedido.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    public Ped65VO getValues(int rowIndex) {
        return listaPedido.get(rowIndex);
    }

}
