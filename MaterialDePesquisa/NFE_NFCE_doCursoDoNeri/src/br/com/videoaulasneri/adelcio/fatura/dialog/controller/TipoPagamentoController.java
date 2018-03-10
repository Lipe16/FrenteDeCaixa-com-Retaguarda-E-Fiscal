/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.controller;

import br.com.videoaulasneri.adelcio.fatura.bean.TipoPagamentoVO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TipoPagamentoController {

    Statement stm;
    ResultSet rs;
    Connection conexao;
    //AcessoBanco bd = new AcessoBanco();
    public TipoPagamentoController(Connection confat) {
        this.conexao = confat;
    }

    public List<TipoPagamentoVO> consulta() {
        String consultaSQL = "select * from tipo_pgto order by TEF, ID";

        try {
            List<TipoPagamentoVO> listaTipoPagamento = new ArrayList<TipoPagamentoVO>();

            //stm = conexao.createStatement();  //bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            while (rs.next()) {
                TipoPagamentoVO tipoPagamento = new TipoPagamentoVO();
                tipoPagamento.setId(rs.getInt("ID"));
                tipoPagamento.setCodigo(rs.getString("CODIGO").trim());
                tipoPagamento.setDescricao(rs.getString("DESCRICAO").trim());
                tipoPagamento.setTEF(rs.getString("TEF"));
                //tipoPagamento.setImprimeVinculado(rs.getString("IMPRIME_VINCULADO"));
                listaTipoPagamento.add(tipoPagamento);
            }
            return listaTipoPagamento;
        } catch (Exception e) {
            //e.printStackTrace();
JOptionPane.showMessageDialog(null, "TipoPagamentoController.consulta(). Erro: "+e);
            return null;
        } finally {
            //bd.desconectar();
        }
    }

    public TipoPagamentoVO consultaPeloId(Integer pId) {
        String consultaSQL = "select * from tipo_pgto where ID=" + pId;

        try {
            //stm = conexao.createStatement();  //bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            rs.next();
            TipoPagamentoVO tipoPagamento = new TipoPagamentoVO();
            tipoPagamento.setId(rs.getInt("ID"));
            tipoPagamento.setCodigo(rs.getString("CODIGO").trim());
            tipoPagamento.setDescricao(rs.getString("DESCRICAO").trim());
            tipoPagamento.setTEF(rs.getString("TEF"));
            //tipoPagamento.setImprimeVinculado(rs.getString("IMPRIME_VINCULADO"));
            return tipoPagamento;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }

    //mÃ©todo implementado por douglas morato
    public TipoPagamentoVO consultaPeloCodigo(String pCodigo) {
        String consultaSQL = "select * from tipo_pgto where CODIGO='" + pCodigo+"'";

        try {
            //stm = conexao.createStatement();  //bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            rs.next();
            TipoPagamentoVO tipoPagamento = new TipoPagamentoVO();
            tipoPagamento.setId(rs.getInt("ID"));
            tipoPagamento.setCodigo(rs.getString("CODIGO").trim());
            tipoPagamento.setDescricao(rs.getString("DESCRICAO").trim());
            tipoPagamento.setTEF(rs.getString("TEF"));
            //tipoPagamento.setImprimeVinculado(rs.getString("IMPRIME_VINCULADO"));
            return tipoPagamento;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }

    public TipoPagamentoVO consultaPeloNome(String pNome) {
        String consultaSQL = "select * from tipo_pgto where DESCRICAO='" + pNome + "'";

        try {
            //stm = conexao.createStatement();  //bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            rs.next();
            TipoPagamentoVO tipoPagamento = new TipoPagamentoVO();
            tipoPagamento.setId(rs.getInt("ID"));
            tipoPagamento.setCodigo(rs.getString("CODIGO").trim());
            tipoPagamento.setDescricao(rs.getString("DESCRICAO").trim());
            tipoPagamento.setTEF(rs.getString("TEF"));
            //tipoPagamento.setImprimeVinculado(rs.getString("IMPRIME_VINCULADO"));
            return tipoPagamento;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }

}
