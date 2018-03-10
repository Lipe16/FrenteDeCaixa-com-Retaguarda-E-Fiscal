/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.controller;

import br.com.videoaulasneri.adelcio.fatura.bean.MeiosPagamentoVO;
import br.com.videoaulasneri.adelcio.fatura.bean.TotalTipoPagamentoVO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TotalTipoPagamentoController {

    String consultaSQL;
    Statement stm;
    PreparedStatement pstm;
    ResultSet rs;
    Connection conexao;
    int empresa, pedido;

    public void gravaTotaisVenda(ArrayList<TotalTipoPagamentoVO> pListaTotalTipoPagamento, int empresa, int pedido, Connection conexao, BigDecimal totalVenda) {
        this.conexao = conexao;
        this.empresa = empresa;
        this.pedido = pedido;

        TotalTipoPagamentoVO totalTipoPagamento;

        try {
            int coo = 0;  //Integer.valueOf(Caixa.aCBrECF.getNumCOO());
            int ccf = 0;  //Integer.valueOf(Caixa.aCBrECF.getNumCCF());
            int gnf = 0;  //Integer.valueOf(Caixa.aCBrECF.getNumGNF());

            for (int i = 0; i < pListaTotalTipoPagamento.size(); i++) {
                totalTipoPagamento = pListaTotalTipoPagamento.get(i);
                double wvalor = totalTipoPagamento.getValor();
                double totVenda = totalVenda.doubleValue();
                //if (wvalor > totVenda) {
                //    wvalor = totVenda;
                //}
                consultaSQL =
                        "insert into tipopgto65 ("
                        + "empresa, "
                        + "pedido, "
                        + "id_tipo_pgto, "
                        + "valor) "
                        + "values (?, ?, ?, ?)";
//JOptionPane.showMessageDialog(null, "TotalTipoPagamentoController.gravaTotaisVenda() - comando sql: "+consultaSQL);
                pstm = conexao.prepareStatement(consultaSQL);
                pstm.setInt(1, empresa);
                pstm.setInt(2, pedido);
                pstm.setInt(3, totalTipoPagamento.getTipoPagamentoVO().getId()); 
                pstm.setDouble(4, wvalor);

                pstm.executeUpdate();
            }
        //JOptionPane.showMessageDialog(null, "TotalTipoPagamentoController - executou metodo: gravaTotaisVenda() . . .");
        } catch (Exception e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "TotalTipoPagamentoController.gravaTotaisVenda() - Erro: "+e);
        } finally {
            //bd.desconectar();
        }
    }

    public List<MeiosPagamentoVO> meiosPagamento(String pDataInicio, String pDataFim, Integer pIdImpressora) {
        List<MeiosPagamentoVO> listaMeiosPagamento = new ArrayList<MeiosPagamentoVO>();
/*
        consultaSQL =
                "SELECT V.DATA_VENDA, M.ID_ECF_IMPRESSORA, P.DESCRICAO, SUM(TP.VALOR) AS TOTAL "
                + "FROM "
                + "ECF_VENDA_CABECALHO V, ECF_MOVIMENTO M, ECF_TIPO_PAGAMENTO P, ECF_TOTAL_TIPO_PGTO TP "
                + "WHERE "
                + "V.ID_ECF_MOVIMENTO = M.ID AND "
                + "TP.ID_ECF_VENDA_CABECALHO=V.ID AND "
                + "TP.ID_ECF_TIPO_PAGAMENTO = P.ID AND "
                + "M.ID_ECF_IMPRESSORA = " + pIdImpressora + " AND "
                + "(V.DATA_VENDA BETWEEN '" + pDataInicio + "' and '" + pDataFim
                + "') GROUP BY "
                + "P.DESCRICAO,V.DATA_VENDA,M.ID_ECF_IMPRESSORA";

        try {

            //stm = bd.conectar().createStatement();
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            while (rs.next()) {
                MeiosPagamentoVO meiosPagamento = new MeiosPagamentoVO();
                meiosPagamento.setDescricao(rs.getString("DESCRICAO"));
                meiosPagamento.setDataHora(rs.getDate("DATA_VENDA"));
                meiosPagamento.setTotal(rs.getDouble("TOTAL"));
                listaMeiosPagamento.add(meiosPagamento);
            }
            return listaMeiosPagamento;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
 * */
        JOptionPane.showMessageDialog(null, "TipoPagamentoController - executou metodo: meiosPagamento() . . .");
    return listaMeiosPagamento;
    }
}
