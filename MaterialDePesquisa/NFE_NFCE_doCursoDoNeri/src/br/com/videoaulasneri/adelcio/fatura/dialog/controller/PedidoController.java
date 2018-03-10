/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.controller;

//import com.pil.pafecf.vo.VendaDetalheVO;
import br.com.videoaulasneri.adelcio.fatura.NFCe;
import br.com.videoaulasneri.adelcio.fatura.bean.Ped65VO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoController {

    String consultaSQL;
    Statement stm;
    PreparedStatement pstm;
    ResultSet rs;
    boolean display = false;
    //AcessoBanco bd = new AcessoBanco();

    public Ped65VO consulta(String codigo, int codcaixa, Connection conexao) {
        consultaSQL =
                "select * "
                + "from pedidos65 "
                + "where "
                + "codcaixa = " + codcaixa
                + "and numero_nfe = 0 "
                + "and pedido = '" + codigo + "' "
                + "and cancelado = false"
                ;
        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            if (rs.next()) {
                Ped65VO pedidoVO = new Ped65VO();
                pedidoVO.setPedido(rs.getInt("pedido"));
                pedidoVO.setNumero_nfe(rs.getInt("numero_nfe"));
                pedidoVO.setData_digitacao(rs.getDate("data_digitacao"));
                pedidoVO.setValor_total(rs.getDouble("valor_total"));
                pedidoVO.setDocumento(rs.getString("documento"));
                pedidoVO.setQtde_itens(rs.getInt("qtde_itens"));
                //pedidoVO.setPeso(rs.getFloat("peso"));
                return pedidoVO;
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

    public Ped65VO consultaId(Integer pId, int codcaixa, Connection conexao) {
        Ped65VO produto = new Ped65VO();
        consultaSQL =
                "select *"
                + "from pedidos65 "
                + "codcaixa = " + codcaixa
                + "and numero_nfe = 0 "
                + "and pedido =" + pId + " "
                + "and cancelado = false"
               ;

        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            if (rs.next()) {
                Ped65VO pedidoVO = new Ped65VO();
                pedidoVO.setPedido(rs.getInt("pedido"));
                pedidoVO.setNumero_nfe(rs.getInt("numero_nfe"));
                pedidoVO.setData_digitacao(rs.getDate("data_digitacao"));
                pedidoVO.setValor_total(rs.getDouble("valor_total"));
                pedidoVO.setDocumento(rs.getString("documento"));
                pedidoVO.setQtde_itens(rs.getInt("qtde_itens"));
                //pedidoVO.setPeso(rs.getFloat("peso"));
                return pedidoVO;
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

    public List<Ped65VO> tabelaPedido(int codcaixa, Connection conexao, boolean autorizada) {
if (display) JOptionPane.showMessageDialog(null, "PedidoController -  tabelaPedido()  . . . "+autorizada);
        if (autorizada) {
            consultaSQL =
                "select count(*) as TOTAL "
                + "from pedidos65 "
                + "where "
                + "codcaixa = " + codcaixa
                + " and valor_total > 0 "
                + " and numero_nfe > 0 "
                + "and cancelado = false"
                ;
        } else {
            consultaSQL =
                "select count(*) as TOTAL "
                + "from pedidos65 "
                + "where "
                + "codcaixa = " + codcaixa
                + " and valor_total > 0 "
                + " and numero_nfe = 0 "
                + "and cancelado = false"
                ;
        }
if (display) JOptionPane.showMessageDialog(null, "PedidoController -  tabelaPedido()  comando sql(1): "+consultaSQL);

        try {
            //stm = bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = rs.getInt("TOTAL");
System.out.println( "PedidoController -  tabelaPedido()  Total: "+totalRegistros+" - Autorizada: "+autorizada);
            if (totalRegistros > 0) {
                List<Ped65VO> listaPedido = new ArrayList<Ped65VO>();
                if (autorizada) {
                    consultaSQL =
                        "select * "
                        + "from pedidos65 "
                        + " where "
                        + "codcaixa = " + codcaixa
                        + " and valor_total > 0 "
                        + " and numero_nfe > 0 "
                        + " and cancelado = false"
                        +" order by pedido desc"
                        ;
                } else {
                    consultaSQL =
                        "select * "
                        + "from pedidos65 "
                        + " where "
                        + "codcaixa = " + codcaixa
                        + " and valor_total > 0 "
                        + " and numero_nfe = 0 "
                        + "and cancelado = false"
                        +" order by pedido desc"
                        ;
                }
if (display) JOptionPane.showMessageDialog(null, "PedidoController -  tabelaPedido()  comando sql(2): "+consultaSQL);

                //stm = conexao.createStatement();
                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                while (rs.next()) {
                    Ped65VO pedidoVO = new Ped65VO();
                    pedidoVO.setPedido(rs.getInt("pedido"));
                    pedidoVO.setNumero_nfe(rs.getInt("numero_nfe"));
                    pedidoVO.setData_digitacao(rs.getDate("data_digitacao"));
                    pedidoVO.setValor_total(rs.getDouble("valor_total"));
                    pedidoVO.setDocumento(rs.getString("documento"));
                    pedidoVO.setQtde_itens(rs.getInt("qtde_itens"));
                    //pedidoVO.setPeso(rs.getFloat("peso"));
                    listaPedido.add(pedidoVO);
                }
                return listaPedido;
            } else {
                //caso nao existam registros retorna nulo
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }
    public List<Ped65VO> pedidoFiltro(String filtroNome, int codcaixa, Connection conexao, boolean autorizada) {
        if (filtroNome == null || filtroNome.equals("")) {
            filtroNome = "";
        }
        String procurePor = filtroNome;
        consultaSQL =
                "SELECT COUNT(*) as TOTAL FROM pedidos65 "
                + "WHERE "
                + "codcaixa = " + codcaixa;
        if (autorizada) {
            consultaSQL += " and numero_nfe > 0 ";
        } else {
            consultaSQL += " and numero_nfe = 0 ";
        }
        consultaSQL +=
                 " and cancelado = false"
                + " and valor_total > 0 ";
        if (procurePor != null && procurePor.length() > 0) {
            consultaSQL += " and pedido = " + Integer.parseInt(procurePor);
        }
        try {
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = 0;
            //while (rs.next()) {
                totalRegistros = rs.getInt("TOTAL");
            //}

            if (totalRegistros > 0) {
                List<Ped65VO> listaPedido = new ArrayList<Ped65VO>();
                consultaSQL =
                        "SELECT * from pedidos65 "
                        + "WHERE "
                        + "codcaixa = " + codcaixa;
                if (autorizada) {
                    consultaSQL += " and numero_nfe > 0 ";
                } else {
                    consultaSQL += " and numero_nfe = 0 ";
                }
                if (procurePor != null && procurePor.length() > 0) {
                    consultaSQL += " and pedido = " + Integer.parseInt(procurePor);
                }
                consultaSQL +=
                          " and valor_total > 0 "
                        + " and cancelado = false "
                        + " ORDER BY empresa, pedido";

                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                while (rs.next()) {
                    Ped65VO pedidoVO = new Ped65VO();
                    pedidoVO.setPedido(rs.getInt("pedido"));
                    pedidoVO.setNumero_nfe(rs.getInt("numero_nfe"));
                    pedidoVO.setData_digitacao(rs.getDate("data_digitacao"));
                    pedidoVO.setValor_total(rs.getDouble("valor_total"));
                    pedidoVO.setDocumento(rs.getString("documento"));
                    pedidoVO.setQtde_itens(rs.getInt("qtde_itens"));
                    //pedidoVO.setPeso(rs.getFloat("peso"));
                    listaPedido.add(pedidoVO);
                }
                return listaPedido;
            } else {
                //caso nao existam registros retorna nulo
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //bd.desconectar();
        }
    }
    public List<Ped65VO> pedidoFiltro(int codcaixa, String filtroNome, String ippt, Connection conexao, boolean autorizada) {
        String procurePor = "%" + filtroNome + "%";
        consultaSQL =
                "SELECT COUNT(*) as TOTAL FROM pedidos65 "
                 + "WHERE "
                + "codcaixa = " + codcaixa;
                if (autorizada) {
                    consultaSQL += " and numero_nfe > 0 ";
                } else {
                    consultaSQL += " and numero_nfe = 0 ";
                }
                consultaSQL +=
                 " and cancelado = false "
                 + " and valor_total > 0 "
                 + "and pedido = " + Integer.parseInt(procurePor);

        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = rs.getInt("TOTAL");

            if (totalRegistros > 0) {
                List<Ped65VO> listaPedido = new ArrayList<Ped65VO>();
                consultaSQL =
                        "SELECT * FROM pedidos65 "
                        + "WHERE "
                        + "codcaixa = " + codcaixa;
                if (autorizada) {
                    consultaSQL += " and numero_nfe > 0 ";
                } else {
                    consultaSQL += " and numero_nfe = 0 ";
                }
                consultaSQL +=
                         "and pedido = " + Integer.parseInt(procurePor)
                        + " and valor_total > 0 "
                        + " and cancelado = false "
                        + " ORDER BY empresa, pedido";

                //stm = conexao.createStatement();
                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                while (rs.next()) {
                    Ped65VO pedidoVO = new Ped65VO();
                    pedidoVO.setPedido(rs.getInt("pedido"));
                    pedidoVO.setNumero_nfe(rs.getInt("numero_nfe"));
                    pedidoVO.setData_digitacao(rs.getDate("data_digitacao"));
                    pedidoVO.setValor_total(rs.getDouble("valor_total"));
                    pedidoVO.setDocumento(rs.getString("documento"));
                    pedidoVO.setQtde_itens(rs.getInt("qtde_itens"));
                    listaPedido.add(pedidoVO);
                }
                return listaPedido;
            } else {
                //caso nao existam registros retorna nulo
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
