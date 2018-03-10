/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.controller;

//import com.pil.pafecf.bd.AcessoBanco;
import br.com.videoaulasneri.adelcio.fatura.bean.FuncionarioVO;
import br.com.videoaulasneri.adelcio.fatura.bean.OperadorVO;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperadorController {

    String consultaSQL;
    Statement stm;
    //PreparedStatement pstm;
    ResultSet rs;
    //AcessoBanco bd = new AcessoBanco();

    public OperadorVO consultaUsuario(String pLogin, String pSenha, Connection conexao, int empresa) {
        OperadorVO operador = new OperadorVO();
        FuncionarioVO funcionario = new FuncionarioVO();
        operador.setFuncionarioVO(funcionario);
        consultaSQL =
                "select * "
                + "from login where "
                + "usuario='" + pLogin 
                + "' and senha='" + pSenha + "'"
                + " and empresa = "+empresa;
        try {
            //stm = bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            if (rs.next()) {
                operador.setId(rs.getInt("codigo"));
                operador.setLogin(rs.getString("usuario"));
                operador.setSenha(rs.getString("senha"));
                //operador.getFuncionarioVO().setId(rs.getInt("ID_ECF_FUNCIONARIO"));
                operador.getFuncionarioVO().setNivelAutorizacao(rs.getString("nivel"));
                return operador;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }
}
