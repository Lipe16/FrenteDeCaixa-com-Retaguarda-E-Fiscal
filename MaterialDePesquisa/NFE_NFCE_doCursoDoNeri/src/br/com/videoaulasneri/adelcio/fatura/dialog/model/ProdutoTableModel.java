/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.model;

import br.com.videoaulasneri.adelcio.fatura.bean.ProdutoVO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProdutoTableModel extends AbstractTableModel {

    private List<ProdutoVO> listaProduto;

    public ProdutoTableModel(List<ProdutoVO> listaCliente) {
        this.listaProduto = listaCliente;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        ProdutoVO produto = listaProduto.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return produto.getCodigo();
            case 1:
                return produto.getEan();
            case 2:
                return produto.getNome_reduzido();
            case 3:
                return produto.getUnidade();
            case 4:
                return produto.getPreco();
//            case 5:
//                return produto.getPeso();
        }
        return null;
    }

    @Override
    public int getRowCount() {

        return listaProduto.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    public ProdutoVO getValues(int rowIndex) {
        return listaProduto.get(rowIndex);
    }

}
