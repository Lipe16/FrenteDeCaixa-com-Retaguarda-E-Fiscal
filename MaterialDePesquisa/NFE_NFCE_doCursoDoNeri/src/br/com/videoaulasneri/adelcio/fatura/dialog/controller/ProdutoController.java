/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.fatura.dialog.controller;

//import com.pil.pafecf.vo.VendaDetalheVO;
import br.com.videoaulasneri.adelcio.fatura.NFCe;
import br.com.videoaulasneri.adelcio.fatura.bean.ProdutoVO;
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

public class ProdutoController {

    String consultaSQL;
    Statement stm;
    PreparedStatement pstm;
    ResultSet rs;
    //AcessoBanco bd = new AcessoBanco();

    public ProdutoVO consulta(String codigo, Connection conexao) {
        consultaSQL =
                "select * "
                + "from "
                + "PRODUTO "
                + "where "
                + "ean like %'" + codigo + "'% "
                + " or descricao like %'" + codigo + "'%"
                + " or nome_reduzido LIKE %'" + codigo + "'%"
                ;
        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            if (rs.next()) {
                ProdutoVO produtoVO = new ProdutoVO();
                produtoVO.setCodigo(rs.getInt("codigo"));
                produtoVO.setEan(rs.getString("ean"));
                produtoVO.setNome_reduzido(rs.getString("nome_reduzido"));
                produtoVO.setPreco(rs.getDouble("preco"));
                produtoVO.setUnidade(rs.getString("unidade"));
                produtoVO.setPeso(rs.getFloat("peso"));
                return produtoVO;
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

    public ProdutoVO consultaId(Integer pId, Connection conexao) {
        ProdutoVO produto = new ProdutoVO();
        consultaSQL =
                "select *"
                + "from "
                + "PRODUTO "
                + "where codigo =" + pId + " ";

        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            if (rs.next()) {
                ProdutoVO produtoVO = new ProdutoVO();
                produtoVO.setCodigo(rs.getInt("codigo"));
                produtoVO.setEan(rs.getString("ean"));
                produtoVO.setNome_reduzido(rs.getString("nome_reduzido"));
                produtoVO.setPreco(rs.getDouble("preco"));
                produtoVO.setUnidade(rs.getString("unidade"));
                produtoVO.setPeso(rs.getFloat("peso"));
                return produto;
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

    public List<ProdutoVO> tabelaProduto(Connection conexao) {
        consultaSQL =
                "select count(*) as TOTAL "
                + "from PRODUTO";

        try {
            //stm = bd.conectar().createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = rs.getInt("TOTAL");

            if (totalRegistros > 0) {
                List<ProdutoVO> listaProduto = new ArrayList<ProdutoVO>();
                consultaSQL =
                        "select * "
                        + "from PRODUTO ";


                //stm = conexao.createStatement();
                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                while (rs.next()) {
                    ProdutoVO produto = new ProdutoVO();
                    produto.setCodigo(rs.getInt("codigo"));
                    produto.setEan(rs.getString("ean"));
                    produto.setNome_reduzido(rs.getString("nome_reduzido"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setUnidade(rs.getString("unidade"));
                    produto.setPeso(rs.getFloat("peso"));
                    listaProduto.add(produto);
                }
                return listaProduto;
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
    public List<ProdutoVO> produtoFiltro(String filtroNome, Connection conexao) {
        if (filtroNome == null || filtroNome.equals("")) {
            filtroNome = "";
        }
        String procurePor = "";
        if (filtroNome.equals("")) {  //  sem filtro
            consultaSQL =
                    "SELECT COUNT(*) as TOTAL FROM PRODUTO "
                    + "WHERE preco > 0";
//                    + " and ean != '' and ean != '              '";
        } else {
            procurePor = "%" + filtroNome + "%";
            consultaSQL =
                    "SELECT COUNT(*) as TOTAL FROM PRODUTO "
                    + "WHERE preco > 0 "
                    + " and ( UPPER(nome_reduzido) LIKE '%" + procurePor.toUpperCase() + "%'"
                    + " or UPPER(descricao) LIKE '%" + procurePor.toUpperCase() + "%')"
                    ;
//                    + " and ean != '' and ean != '              '";
        }
        try {
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = 0;
            //while (rs.next()) {
                totalRegistros = rs.getInt("TOTAL");
            //}
//JOptionPane.showMessageDialog(null, " ProdutoController.produtoFiltro() - Tot.Regs: "+totalRegistros);
            if (totalRegistros > 0) {
                List<ProdutoVO> listaProduto = new ArrayList<ProdutoVO>();
        if (filtroNome.equals("")) {  //  sem filtro
            consultaSQL =
                    "SELECT * from produto "
                    + "WHERE preco > 0"
//                    + " and ean != '' and ean != '              '"
                    + " ORDER BY NOME_reduzido";
        } else {
                consultaSQL =
                        "SELECT * from produto "
                        + "WHERE preco > 0 "
                        + " and ( UPPER(nome_reduzido) LIKE '%" + procurePor.toUpperCase() + "%'"
                        + " or UPPER(descricao) like '%" + procurePor.toUpperCase() + "%')"
//                        + " and ean != '' and ean != '              '"
                        + " ORDER BY NOME_reduzido";
        }
//JOptionPane.showMessageDialog(null, " ProdutoController.produtoFiltro() - sql: "+consultaSQL);
                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    i++;
                    if (i == 1) {
//JOptionPane.showMessageDialog(null, " ProdutoController.produtoFiltro() - 1. Reg: "+rs.getString("ean")+"  - "+rs.getString("nome_reduzido"));
                    }
                    ProdutoVO produto = new ProdutoVO();
                    produto.setCodigo(rs.getInt("codigo"));
                    produto.setEan(rs.getString("ean"));
                    produto.setNome_reduzido(rs.getString("nome_reduzido"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setUnidade(rs.getString("unidade"));
                    produto.setPeso(rs.getFloat("peso"));
                    listaProduto.add(produto);
                }
                return listaProduto;
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
/*
    public void atualizaEstoque(List<VendaDetalheVO> listaVendaDetalhe, boolean vendaCancelada) {
        ProdutoVO produto;
        BigDecimal quantidade;
        BigDecimal quantidadeFichaTecnica;
        for (int i = 0; i < listaVendaDetalhe.size(); i++) {
            if (listaVendaDetalhe.get(i).getCancelado().equals("N")) {
                produto = consultaId(listaVendaDetalhe.get(i).getIdProduto());

                if (produto.getIPPT().equals("P")) {
                    FichaTecnicaController fichaControl = new FichaTecnicaController();
                    List<FichaTecnicaVO> listaFichaTecnica = fichaControl.consultaFichaTecnica(produto.getId());

                    for (int j = 0; j < listaFichaTecnica.size(); j++) {
                        produto = consultaId(listaFichaTecnica.get(j).getIdProdutoFilho());
                        quantidade = BigDecimal.valueOf(listaVendaDetalhe.get(i).getQuantidade());
                        quantidadeFichaTecnica = BigDecimal.valueOf(listaFichaTecnica.get(j).getQuantidade());

                        quantidade = quantidade.multiply(quantidadeFichaTecnica).setScale(Caixa.configuracao.getDecimaisQuantidade(), RoundingMode.DOWN);

                        atualizaEstoque(produto, quantidade, vendaCancelada);
                    }
                } else {
                    quantidade = BigDecimal.valueOf(listaVendaDetalhe.get(i).getQuantidade());
                    atualizaEstoque(produto, quantidade, vendaCancelada);
                }
            }
        }
    }

    private void atualizaEstoque(ProdutoVO produto, BigDecimal quantidade, boolean vendaCancelada) {
        try {
            String atualizaProduto = "update PRODUTO set "
                    + "QTD_ESTOQUE = ?, "
                    + "HASH_TRIPA = ?, "
                    + "HASH_INCREMENTO = ? "
                    + "where ID = ? ";

            Connection con = bd.conectar();
            BigDecimal estoqueAtual;
            String tripa;
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

            estoqueAtual = BigDecimal.valueOf(produto.getQuantidadeEstoque());
            if (vendaCancelada) {
                estoqueAtual = estoqueAtual.add(quantidade);
            } else {
                estoqueAtual = estoqueAtual.subtract(quantidade);
            }
            estoqueAtual = estoqueAtual.setScale(Caixa.configuracao.getDecimaisQuantidade(), RoundingMode.DOWN);

            //produto.setQuantidadeEstoqueAnterior(produto.getQuantidadeEstoque());
            produto.setQuantidadeEstoque(estoqueAtual.doubleValue());
            //produto.setDataEstoque(new Date());

            tripa = produto.getGTIN()
                    + produto.getDescricao()
                    + produto.getDescricaoPDV()
                    + Biblioteca.formatoDecimal("Q", produto.getQuantidadeEstoque())
                    + formatoData.format(produto.getDataEstoque())
                    + produto.getSituacaoTributaria()
                    + Biblioteca.formatoDecimal("V", produto.getTaxaICMS())
                    + Biblioteca.formatoDecimal("V", produto.getValorVenda())
                    + "0";
            produto.setHashTripa(Biblioteca.MD5String(tripa));
            produto.setHashIncremento(-1);

            pstm = con.prepareStatement(atualizaProduto);
            pstm.setDouble(1, produto.getQuantidadeEstoque());
            //pstm.setDouble(2, produto.getQuantidadeEstoqueAnterior());
            //pstm.setDate(3, new java.sql.Date(produto.getDataEstoque().getTime()));
            pstm.setString(2, produto.getHashTripa());
            pstm.setInt(3, produto.getHashIncremento());
            pstm.setInt(4, produto.getId());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.desconectar();
        }
    }

    public void atualizaEstoqueGeral(List<VendaDetalheVO> listaVendaDetalhe, Date dataEstoque) {
        Connection con = null;
        try {
            String atualizaProduto = "update PRODUTO set "
                    + "QTD_ESTOQUE_ANTERIOR = ?, "
                    + "DATA_ESTOQUE = ?, "
                    + "HORA_ESTOQUE = ?, "
                    + "HASH_TRIPA = ?, "
                    + "HASH_INCREMENTO = ? "
                    + "where ID = ? ";

            con = bd.conectar();
            String tripa;
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

            List<ProdutoVO> listaProduto = tabelaProduto();
            ProdutoVO produto;
            for (int i = 0; i < listaProduto.size(); i++) {
                produto = listaProduto.get(i);
                produto.setQuantidadeEstoqueAnterior(produto.getQuantidadeEstoque());
                //se o primeiro documento do dia for uma venda, desconsidera as quantidades vendidas para atualizar o estoque
                if (listaVendaDetalhe != null) {
                    for (int j = 0; j < listaVendaDetalhe.size(); j++) {
                        if (produto.getId() == listaVendaDetalhe.get(j).getIdProduto()) {
                            produto.setQuantidadeEstoqueAnterior(produto.getQuantidadeEstoque() + listaVendaDetalhe.get(j).getQuantidade());
                            break;
                        }
                    }
                }

                produto.setDataEstoque(dataEstoque);
                produto.setHoraEstoque(formatoHora.format(dataEstoque));

                tripa = produto.getGTIN()
                        + produto.getDescricao()
                        + produto.getDescricaoPDV()
                        + Biblioteca.formatoDecimal("Q", produto.getQuantidadeEstoque())
                        + formatoData.format(produto.getDataEstoque())
                        + produto.getSituacaoTributaria()
                        + Biblioteca.formatoDecimal("V", produto.getTaxaICMS())
                        + Biblioteca.formatoDecimal("V", produto.getValorVenda())
                        + "0";
                produto.setHashTripa(Biblioteca.MD5String(tripa));
                produto.setHashIncremento(-1);

                pstm = con.prepareStatement(atualizaProduto);
                pstm.setDouble(1, produto.getQuantidadeEstoqueAnterior());
                pstm.setDate(2, new java.sql.Date(produto.getDataEstoque().getTime()));
                pstm.setString(3, produto.getHoraEstoque());
                pstm.setString(4, produto.getHashTripa());
                pstm.setInt(5, produto.getHashIncremento());
                pstm.setInt(6, produto.getId());

                pstm.executeUpdate();
            }
            String atualizaEstoque = "update ECF_ESTOQUE set "
                    + "ID_ECF_EMPRESA = ?, "
                    + "ID_ECF_IMPRESSORA = ?, "
                    + "NUMERO_SERIE_ECF = ?, "
                    + "DATA_ATUALIZACAO = ?, "
                    + "HORA_ATUALIZACAO = ?, "
                    + "HASH_TRIPA = ?, "
                    + "HASH_INCREMENTO = ? ";
            tripa = Caixa.configuracao.getImpressoraVO().getSerie()
                    + formatoData.format(dataEstoque)
                    + formatoHora.format(dataEstoque)
                    + "0";
            pstm = con.prepareStatement(atualizaEstoque);
            pstm.setInt(1, Caixa.configuracao.getIdEmpresa());
            pstm.setInt(2, Caixa.configuracao.getImpressoraVO().getId());
            pstm.setString(3, Caixa.configuracao.getImpressoraVO().getSerie());
            pstm.setDate(4, new java.sql.Date(dataEstoque.getTime()));
            pstm.setString(5, formatoHora.format(dataEstoque));
            pstm.setString(6, Biblioteca.MD5String(tripa));
            pstm.setInt(7, -1);

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            bd.desconectar();
        }
    }
*/

    public List<ProdutoVO> produtoFiltro(String filtroNome, String ippt, Connection conexao) {
        String procurePor = "%" + filtroNome + "%";
        consultaSQL =
                "SELECT COUNT(*) as TOTAL FROM PRODUTO "
                + "WHERE preco > 0 and "
                + " (nome_reduzido LIKE %'" + procurePor + "'%"
                + " or descricao like %'" + procurePor + "'%)"
                ; 

        try {
            //stm = conexao.createStatement();
            stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = stm.executeQuery(consultaSQL);
            rs.first();
            Integer totalRegistros = rs.getInt("TOTAL");

            if (totalRegistros > 0) {
                List<ProdutoVO> listaProduto = new ArrayList<ProdutoVO>();
                consultaSQL =
                        "SELECT * FROM PRODUTO "
                        + "WHERE preco > 0 and "
                        + " (nome_reduzido LIKE %'" + procurePor + "'% "
                        + " or descricao like %'" + procurePor + "'%)"
                        + " ORDER BY P.NOME";

                //stm = conexao.createStatement();
                stm = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                rs = stm.executeQuery(consultaSQL);
                rs.beforeFirst();
                while (rs.next()) {
                    ProdutoVO produto = new ProdutoVO();
                produto.setCodigo(rs.getInt("codigo"));
                produto.setEan(rs.getString("ean"));
                produto.setNome_reduzido(rs.getString("nome_reduzido"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setPeso(rs.getFloat("peso"));
                    listaProduto.add(produto);
                }
                return listaProduto;
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
