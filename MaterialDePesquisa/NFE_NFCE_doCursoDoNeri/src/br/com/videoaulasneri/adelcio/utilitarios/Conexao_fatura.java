/*

Descrição: Faz a conexao com o banco de dados e cria as tabelas automaticamente na implantação

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.utilitarios;
import br.com.videoaulasneri.adelcio.admin.ImportaCfop;
import br.com.videoaulasneri.adelcio.admin.ImportaCidades;
import java.sql.*;
import javax.swing.*;
import br.com.videoaulasneri.adelcio.nfefacil.NFefacil;
import org.postgresql.util.PSQLException;
import java.util.Date;

public class Conexao_fatura
{
        //final private String driver ="org.postgresql.Driver";  //"sun.jdbc.odbc.JdbcOdbcDriver";
        private String usuario = "";  //postgres";  //"";
        private String senha = "";  //  nerizon";  //"";
        private String driver = "";
        private String url = "";
        static public Connection conexao;
        public Statement statement;
        public ResultSet resultset;
        NFefacil nfe2;
        
       public Connection conecta( String usuarioBD, String senhaBD, String driver, String url )
       {
            conexao = null;
            try 
            {
                //String url = "jdbc:postgresql://"+localSgbd+"/cadastros";  //"jdbc:odbc:cadastros";
                nfe2.setStatus_fatura(false);
                this.usuario=usuarioBD;
                this.senha=senhaBD;
                this.driver = driver;
                this.url = url;
                //System.out.println("driver: ["+this.driver+"] \nurl: ["+this.url+"]\nusuario: ["+usuario+"]\nSenha: ["+senha+"]");
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, usuario, senha);
//JOptionPane.showMessageDialog(null,"conectou Banco de Dados fatura");
                checkTables();
                nfe2.setStatus_fatura(true);
            }
            catch(ClassNotFoundException Driver) 
            {
               JOptionPane.showMessageDialog(null,"Driver nao localizado: "+Driver);
            }
            catch(SQLException Fonte) 
            {
                JOptionPane.showMessageDialog(null,"Deu erro na conexao "+
                        "com a fonte de dados!\n Verifique se o local ["+url+"] esta correto!"+
                        "\nErro: "+Fonte.getMessage());
                System.out.println("Deu erro na conexao com a fonte de dados: "+Fonte);
            }
            return conexao;
       }
       
       public void desconecta()
       {
            boolean result = true;
            try 
            {
                conexao.close();
                //JOptionPane.showMessageDialog(null,"Banco de Dados siscom Desconectado");
            }
            catch(SQLException fecha) 
            {
                JOptionPane.showMessageDialog(null,"Nao foi possivel "+
                        "fechar o banco de dados siscom: "+fecha);
                result = false;
            }

       }
       
       public ResultSet executeSQL(String sql)
       {
           ResultSet rs = null;
            try 
            {
                statement = conexao.createStatement();  //(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs = statement.executeQuery(sql);
            }
            catch(SQLException sqlex) 
            {
               JOptionPane.showMessageDialog(null,"Nao foi possivel "+
                       "executar o comando sql,"+sqlex+", o sql passado foi "+sql);
            }
            return rs;
       }
       public int execute(String sql)
       {
           int qtReg = 0;
            try
            {
                statement = conexao.createStatement();
                qtReg = statement.executeUpdate(sql);
            }
            catch(SQLException sqlex)
            {
               JOptionPane.showMessageDialog(null,"Nao foi possivel "+
                       "executar o comando sql,"+sqlex+", o sql passado foi "+sql);
            }
            return qtReg;
       }
       private int checkTables() {
         int retorno = 0;
         int nTab = 0;
         Statement st_cria = null;
         try {
             st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ec) {
             
         }

            String sql = "";  //"CREATE DATABASE \"testNFe\" WITH OWNER = postgres ENCODING = 'SQL_ASCII' CONNECTION LIMIT = -1";
            //int retorno = st_cria.executeUpdate(sql);
            try {
               sql = "CREATE TABLE nfe_lido ("
                       + "id integer, "
                       + "data timestamp "
                   +") WITH ( OIDS=FALSE )";
               retorno = st_cria.executeUpdate(sql);
               Date dataAtual = new Date();
               java.sql.Timestamp sq = new java.sql.Timestamp(dataAtual.getTime());
               sql = "INSERT INTO nfe_lido (id, data) VALUES (1, '" + sq  + "');";
               retorno = st_cria.executeUpdate(sql);

                sql = "CREATE TABLE nfce_lido ("
                       + "id integer, "
                       + "data timestamp "
                   +") WITH ( OIDS=FALSE )";
               retorno = st_cria.executeUpdate(sql);
               dataAtual = new Date();
               sq = new java.sql.Timestamp(dataAtual.getTime());
               sql = "INSERT INTO nfce_lido (id, data) VALUES (1, '" + sq  + "');";

               retorno = st_cria.executeUpdate(sql);
               sql = "CREATE TABLE leiturax ("
                       + "tipo character(10), "
                       + "data date, "
                       + "valor double precision, "
                       + "numeronfce integer, "
                       + "serienfce character(3)"
                   +") WITH ( OIDS=FALSE )";
               retorno = st_cria.executeUpdate(sql);
           } catch (Exception e) {
           }
System.out.println("Vai criar tabela ibpt . . .");            
         try {
             st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            nTab++;  //3
            sql = "CREATE TABLE ibpt ( "
                  +"uf character (2), "
                  +"ncm character (8), "
                  +"tipo character varying(5), "
                  +"descricao character varying(500), "
                  +"aliqNacionalFederal double precision, "  
                  +"aliqImportadosFederal double precision, "  
                  +"aliqEstadual double precision, "  
                  +"aliqMunicipal double precision, "  
                  +"vigenciaInicio date, "  
                  +"vigenciaFim date, "  
                  +"chave character varying(10), "
                  +"versao character varying(10), "
                  +"fonte character varying(50), "
                  +"CONSTRAINT ibpt_pkey PRIMARY KEY (uf,ncm,tipo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);
           } catch (Exception e) {
           }
         try {
             st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            nTab++;  //2
            sql = "CREATE TABLE cfop ( "
                  +"codigo integer NOT NULL, "
                  +"cfop integer, "
                  +"descricao character varying(120), "
                  +"observacao character varying(120), "
                  +"faturamento boolean NOT NULL, "
                  +"financeiro boolean, "
                  +"seqcfop integer, "
                  +"operacao character(1), "
                  +"CONSTRAINT cfop_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);
/*
            sql = "INSERT INTO cfop (codigo, cfop, descricao, observacao, faturamento, financeiro, seqcfop, operacao) VALUES (2, 5411, 'DEVOLUCAO DE MERCADORIA - REG.SUBST.TRIBUTARIA', '', true, false, 1, 'E'); "
                    +"INSERT INTO cfop (codigo, cfop, descricao, observacao, faturamento, financeiro, seqcfop, operacao) VALUES (1, 5102, 'VENDA DE MERCADORIA ADQ. TERCEIROS', '', true, true, 0, 'E'); "
                    +"INSERT INTO cfop (codigo, cfop, descricao, observacao, faturamento, financeiro, seqcfop, operacao) VALUES (3, 6102, 'VENDA DE MERCADORIA  ADQ.TERCEIROS', '', false, false, 0, 'I'); "
                    +"INSERT INTO cfop (codigo, cfop, descricao, observacao, faturamento, financeiro, seqcfop, operacao) VALUES (4, 6499, 'outras operacoes saida', '', true, true, 0, 'I');";
            retorno = st_cria.executeUpdate(sql);
*/
            nTab++;  //3
            sql = "CREATE TABLE ibge ( "
                  +"codigo integer NOT NULL, "
                  +"cidade character varying(40), "
                  +"codcidade integer, "
                  +"distrito character varying(40), "
                  +"uf character varying(2), "
                  +"CONSTRAINT ibge_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            //sql = "INSERT INTO ibge (codigo, cidade, codcidade, distrito, uf) VALUES (1, 'LONDRINA', 4113700, 'LONDRINA', 'PR');";
            //retorno = st_cria.executeUpdate(sql);

            ImportaCidades leibge = new ImportaCidades(conexao);
            leibge.ImportaArquivo("XX");

            ImportaCfop lecfop = new ImportaCfop(conexao);
            lecfop.ImportaArquivo();
            
            sql = "CREATE TABLE aliquf ("
                +"codigo character(2) NOT NULL, "
                +"descricao character varying(25), "
                +"aliqicms double precision, "
                +"CONSTRAINT aliquf_pkey PRIMARY KEY (codigo)"
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Voce está executando este aplicativo pela 1a vez.\n"
                    + "As tabelas serão criadas agora no Banco de Dados. \n"
                    + "Clique em OK e aguarde alguns instantes até aparecer a Tela de Login.");
            nTab++;  //1
            sql = "CREATE TABLE banco ( "
                  +"codigo integer NOT NULL, "
                  +"banco character varying(4), "
                  +"descricao character varying(50), "
                  +"nome_reduzido character varying(20), "
                  +"emite_boleto character(1), "
                  +"CONSTRAINT banco_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO banco (codigo, banco, descricao, nome_reduzido, emite_boleto) VALUES (1, '0000', 'A VISTA', 'A VISTA', 'N'); "
                    +"INSERT INTO banco (codigo, banco, descricao, nome_reduzido, emite_boleto) VALUES (2, '9999', 'SEM BANCO', '.', 'N');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //4
            sql = "CREATE TABLE cliente ( "
                  +"codigo integer NOT NULL, "
                  +"razaosocial character varying(60), "
                  +"fantasia character varying(60), "
                  +"pessoa character(1), "
                  +"cnpj character varying(18), "
                  +"inscest character varying(20), "
                  +"endereco character varying(45), "
                  +"numero character varying(8), "
                  +"bairro character varying(25), "
                  +"cep integer, "
                  +"codcidade integer NOT NULL, "
                  +"uf character(2), "
                  +"telefone character varying(20), "
                  +"contato character varying(20), "
                  +"ramalcontato character varying(15), "
                  +"email character varying(50), "
                  +"consufinal character(1), "
                  +"diferido character(1), "
                  +"ehtransp character(1),"
                  +"CONSTRAINT cliente_pkey PRIMARY KEY (codigo), "
                  +"CONSTRAINT fk_cliente_cidade FOREIGN KEY (codcidade) " 
                  +"REFERENCES ibge (codigo) MATCH SIMPLE " 
                  +"ON UPDATE NO ACTION ON DELETE NO ACTION "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO cliente (codigo, razaosocial,          fantasia,                 pessoa, cnpj,             inscest, endereco,      numero, bairro,  cep,       codcidade, telefone,        contato,  ramalcontato, email, consufinal, diferido, ehtransp) "
                             + "VALUES (0,      'SEM TRANSPORTADORA', 'SEM TRANSPORTADORA',     'J',    '00000000000000', '',      '',            '',     'X',     0,          4105,         '(  )    -    ', '.',     '',           '', 'N',       'N', 'S'); "
                  +"INSERT INTO cliente (codigo, razaosocial,         fantasia,                 pessoa, cnpj,             inscest, endereco,      numero, bairro,  cep,        codcidade, telefone,        contato, ramalcontato, email,                        consufinal, diferido, ehtransp) "
                             + "values (1,      'EMPRESA DESTINO LTDA', 'EMPRESA DESTINO LTDA', 'J',    '11222333444455', '',      'RUA CENTRAL', '123', 'CENTRO', 86100010,   4105,         '(  )    -    ', '',      '',        'portoinfo@sercomtel.com.br', 'N',       'N', 'N')";

            retorno = st_cria.executeUpdate(sql);

            nTab++;  //5
            sql = "CREATE TABLE empresa ( "
                  +"codigo integer NOT NULL, "
                  +"bairro character varying(30), "
                  +"cep character varying(9), "
                  +"cnpj character varying(18), "
                  +"codempresa integer NOT NULL, "
                  +"codigo_pais_nfe character varying(4), "
                  +"complemento character varying(30), "
                  +"contato character varying(30), "
                  +"endereco character varying(50), "
                  +"fantasia character varying(50), "
                  +"telefone character varying(20), "
                  +"inscest character varying(20), "
                  +"numero character varying(10), "
                  +"razao character varying(50), "
                  +"tipo_nf character varying(1), "
                  +"codcidade integer NOT NULL, "
                  +"crt character(1), "
                  +"margem_lucro double precision DEFAULT 15.0, "
                  +"CONSTRAINT empresa_pkey PRIMARY KEY (codigo), "
                  +"CONSTRAINT fk_empresa_cidade FOREIGN KEY (codcidade) "
                  +"    REFERENCES ibge (codigo) MATCH SIMPLE "
                  +"    ON UPDATE NO ACTION ON DELETE NO ACTION "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO empresa (codigo, bairro, cep, cnpj, codempresa, codigo_pais_nfe, complemento, contato, endereco, fantasia, telefone, inscest, numero, razao, tipo_nf, codcidade, crt, margem_lucro) VALUES (1, 'CENTRO', '', '', 1, '1058', ' ', 'NOME DO CONTATO', 'RUA DO ENDERECO', 'EMPRESA PADRAO LTDA', '(11)2222-3333', '', '123', 'EMPRESA PADRAO LTDA', '1', 4105, '1', 25);";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //5
            sql = "CREATE TABLE pardigital ( "
                  +"codigo integer NOT NULL, "
                  +"empresa integer NOT NULL, "
                  +"senha_truststore character varying(20), "
                  +"senha_token character varying(20), "
                  +"senha_keystore character varying(20), "
                  +"serie55 character(3), "
                  +"serie65 character(3), "
                  +"verproc character(30), "
                  +"idtoken character(6), "
                  +"csc character varying(40), "
                  +"CONSTRAINT pk_pardigital PRIMARY KEY (codigo), "
                  +"CONSTRAINT fk_pardigital_empresa FOREIGN KEY (empresa) "
                  +"    REFERENCES empresa (codigo) MATCH SIMPLE "
                  +"    ON UPDATE NO ACTION ON DELETE NO ACTION "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO pardigital (codigo, empresa, senha_truststore, senha_token, senha_keystore, serie55, serie65, verproc, idtoken, csc) VALUES (1, 1, '123456', '', '', '001', '001', 'NFe-Porto V:3.10', '000001', '');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //6
            sql = "CREATE TABLE forma_pgto ( "
                  +"codigo integer NOT NULL, "
                  +"descricao character(20) NOT NULL, "
                  +"qtde_parcelas integer DEFAULT 0, "
                  +"dias_inicial integer DEFAULT 0, "
                  +"dias_intervalo integer DEFAULT 0, "
                  +"CONSTRAINT fpgto_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO forma_pgto (codigo, descricao, qtde_parcelas, dias_inicial, dias_intervalo) VALUES (1, 'A VISTA', 1, 0, 0); "
                    +"INSERT INTO forma_pgto (codigo, descricao, qtde_parcelas, dias_inicial, dias_intervalo) VALUES (2, 'A PRAZO', 1, 30, 30);";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //8
            sql = "CREATE TABLE login ( "
                  +"codigo serial NOT NULL, "
                  +"empresa integer, "
                  +"usuario character varying(10), "
                  +"senha character varying(10), "
                  +"nome character varying(50), "
                  +"modelonfe character(2), "
                  +"nivel character(1), "
                  +"alterar_qtde character(1), "
                  +"CONSTRAINT pk_login PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql  = "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'admin', 'admin', 'Administrador', '55', 'A', 'N'); ";
            sql += "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'porto', '437341', 'Adelcio Porto', '55', 'A', 'N'); ";
            sql += "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'neri', 'nerizon', 'Neri Aldoir Neitzke', '55', 'A', 'N'); ";
            sql += "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'gerente', '', 'Gerente 01', '55', 'G', 'N'); ";
            sql += "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'caixa01', 'caixa01', 'Caixa 01', '65', 'U', 'N'); ";
            sql += "INSERT INTO login (empresa, usuario, senha, nome, modelonfe, nivel, alterar_qtde) VALUES (1, 'caixa02', 'caixa02', 'Caixa 02', '65', 'U', 'S'); ";
            retorno = st_cria.executeUpdate(sql);

            sql = "CREATE TABLE margem ( "
                  +"cnpj character(14) NOT NULL, "
                  +"margem double precision, "
                  +"CONSTRAINT pk_margem PRIMARY KEY (cnpj) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

           } catch (Exception e) {
           }
         try {
            nTab++;  //9
            sql = "CREATE TABLE nf ( "
                  +"empresa integer NOT NULL, "
                  +"pedido integer NOT NULL, "
                  +"cod_cliente integer NOT NULL, "
                  +"doc_cliente character(14), "
                  +"nome_documento character varying(60), "
                  +"cod_forma_pgto integer NOT NULL, "
                  +"cod_tipo_doc character(2), "
                  +"cod_banco integer NOT NULL, "
                  +"data_digitacao date, "
                  +"valor_produtos double precision, "
                  +"valor_descontos double precision, "
                  +"valor_total double precision, "
                  +"cod_transportador integer NOT NULL, "
                  +"dados_adicionais character varying(255), "
                  +"qtde_volume integer DEFAULT 0, "
                  +"peso_volume double precision, "
                  +"placa_veiculo character(8), "
                  +"uf_placa character(2), "
                  +"pedido_cliente character(25), "
                  +"modelonfe character(2), "
                  +"numero_nfe numeric(9,0) DEFAULT 0, "
                  +"serie_nfe character varying(3), "
                  +"data_emissao date, "
                  +"chave_nfe character(54), "
                  +"icms_bc double precision, "
                  +"icms_vlr double precision, "
                  +"ipi_bc double precision, "
                  +"ipi_vlr double precision, "
                  +"pis_bc double precision, "
                  +"pis_vlr double precision, "
                  +"cofins_bc double precision, "
                  +"cofins_vlr double precision, "
                  +"vtottrib double precision, "
                  +"num_nfe_fat numeric(9) DEFAULT 0, "
                  +"fin_nfe character(1), "
                  +"data_cancelamento date, "
                  +"num_nfe_dev numeric(9) DEFAULT 0, "
                  +"data_devolucao date, "
                  +"qrcode character varying(1024), "
                  +"tpemis character(1), "
                  +"tpamb character(1), "
                  +"CONSTRAINT nf_pkey PRIMARY KEY (empresa, pedido) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //10
            sql = "CREATE TABLE nf_entrada ( "
                  +"empresa integer NOT NULL, "
                  +"pedido integer NOT NULL, "
                  +"cod_fornecedor integer NOT NULL, "
                  +"cod_forma_pgto integer NOT NULL, "
                  +"cod_tipo_doc character(2), "
                  +"cod_banco integer NOT NULL, "
                  +"data_digitacao date, "
                  +"valor_produtos double precision, "
                  +"valor_descontos double precision, "
                  +"valor_total double precision, "
                  +"cod_transportador integer NOT NULL, "
                  +"dados_adicionais character varying(1024), "
                  +"qtde_volume integer DEFAULT 0, "
                  +"peso_volume double precision, "
                  +"placa_veiculo character(8), "
                  +"uf_placa character(2), "
                  +"pedido_fornecedor character(25), "
                  +"numero_nfe numeric(9) NOT NULL DEFAULT 0, "
                  +"serie_nfe character varying(3), "
                  +"data_emissao date, "
                  +"chave_nfe character(54), "
                  +"icms_bc double precision, "
                  +"icms_vlr double precision, "
                  +"ipi_bc double precision, "
                  +"ipi_vlr double precision, "
                  +"pis_bc double precision, "
                  +"pis_vlr double precision, "
                  +"cofins_bc double precision, "
                  +"cofins_vlr double precision, "
                  +"num_nfe_fat numeric(9) DEFAULT 0, "
                  +"fin_nfe character(1), "
                  +"data_cancelamento date, "
                  +"num_nfe_dev numeric(9) DEFAULT 0, "
                  +"data_devolucao date, "
                  +"CONSTRAINT pk_nf_ent PRIMARY KEY (empresa, cod_fornecedor, numero_nfe, serie_nfe) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //11
            sql = "CREATE TABLE nf_prazo ( "
                  +"empresa integer NOT NULL, "
                  +"pedido integer NOT NULL, "
                  +"parcela integer NOT NULL DEFAULT 1, "
                  +"documento character(12), "
                  +"datavcto date, "
                  +"vlr_parcela double precision, "
                  +"datapgto date, "
                  +"vlr_pago double precision, "
                  +"datavcto_orig date, "
                  +"CONSTRAINT nfprazo_pkey PRIMARY KEY (empresa, pedido, parcela) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //12
            sql = "CREATE TABLE nf_prod_entra ( "
                  +"empresa integer NOT NULL, "
                  +"cod_fornecedor integer NOT NULL, "
                  +"numero_nfe numeric(9) NOT NULL DEFAULT 0, "
                  +"serie_nfe character varying(3) NOT NULL, "
                  +"item integer NOT NULL DEFAULT 0, "
                  +"pedido integer NOT NULL, "
                  +"cod_produto integer NOT NULL, "
                  +"cod_cfop integer, "
                  +"quantidade integer NOT NULL DEFAULT 0, "
                  +"peso numeric(10,3), "
                  +"vlr_unitario double precision NOT NULL, "
                  +"vlr_produto double precision, "
                  +"vlr_desconto double precision, "
                  +"vlr_total double precision, "
                  +"icms_bc double precision, "
                  +"icms_perc double precision, "
                  +"icms_pred double precision, "
                  +"icms_vlr double precision, "
                  +"icms_cst character(3), "
                  +"ipi_bc double precision, "
                  +"ipi_perc double precision, "
                  +"ipi_vlr double precision, "
                  +"ipi_cst character(3), "
                  +"pis_bc double precision, "
                  +"pis_perc double precision, "
                  +"pis_vlr double precision, "
                  +"pis_cst character(3), "
                  +"cofins_bc double precision, "
                  +"cofins_perc double precision, "
                  +"cofins_vlr double precision, "
                  +"cofins_cst character(3), "
                  +"CONSTRAINT nfprodent_pkey PRIMARY KEY (empresa, cod_fornecedor, numero_nfe, serie_nfe, item) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //13
            sql = "CREATE TABLE nf_produtos ( "
                  +"empresa integer NOT NULL, "
                   +"pedido integer NOT NULL, "
                   +"item integer NOT NULL DEFAULT 0, "
                   +"cod_produto integer NOT NULL, "
                   +"cod_interno character(13), "
                   +"cod_cfop integer, "
                   +"quantidade integer NOT NULL DEFAULT 0, "
                   +"peso numeric(10,3), "
                   +"vlr_unitario double precision NOT NULL, "
                   +"vlr_produto double precision, "
                   +"vlr_desconto double precision, "
                   +"vlr_total double precision, "
                   +"icms_bc double precision, "
                   +"icms_perc double precision, "
                   +"icms_pred double precision, "
                   +"icms_vlr double precision, "
                   +"icms_cst character(3), "
                   +"ipi_bc double precision, "
                   +"ipi_perc double precision, "
                   +"ipi_vlr double precision, "
                   +"ipi_cst character(3), "
                   +"pis_bc double precision, "
                   +"pis_perc double precision, "
                   +"pis_vlr double precision, "
                   +"pis_cst character(3), "
                   +"cofins_bc double precision, "
                   +"cofins_perc double precision, "
                   +"cofins_vlr double precision, "
                   +"cofins_cst character(3), "
                  +"vtottrib double precision, "
                   +"preco_custo double precision, "
                   +"CONSTRAINT nfprod_pkey PRIMARY KEY (empresa, pedido, item) "
                   +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //15
            sql = "CREATE TABLE numerolote ( "
                  +"empresa integer, "
                  +"numeroproximolote integer "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO numerolote (empresa, numeroproximolote) VALUES (1, 1);";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //16
            sql = "CREATE TABLE numeronfe ( "
                  +"empresa integer NOT NULL, "
                  +"serienfe character(3), "
                  +"modelonfe character(2), "
                  +"ambiente character(1) NOT NULL, "
                  +"numeronfe integer NOT NULL, "
                  +"CONSTRAINT numeronfe_pkey PRIMARY KEY (empresa, serienfe, modelonfe, ambiente), "
                  +"CONSTRAINT fk_numeronfe_empresa FOREIGN KEY (empresa) "
                  +"REFERENCES empresa (codigo) MATCH SIMPLE "
                  +"ON UPDATE NO ACTION ON DELETE NO ACTION "
                    +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql =     "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES (1, '001', '55', '1', 0); "
                    + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES (1, '001', '55', '2', 0); "
                    + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES (1, '001', '65', '1', 0); "
                    + "INSERT INTO numeronfe (empresa, serienfe, modelonfe, ambiente, numeronfe) VALUES (1, '001', '65', '2', 0); ";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //18
            sql = "CREATE TABLE pessoa ( "
                  +"codigo character(1) NOT NULL, "
                  +"descricao character varying(10), "
                  +"CONSTRAINT pessoa_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO pessoa (codigo, descricao) VALUES ('F', 'FISICA');"
                  +"INSERT INTO pessoa (codigo, descricao) VALUES ('J', 'JURIDICA');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //19
            sql = "CREATE TABLE produto ( "
                  +"codigo integer NOT NULL, "
                  +"nome_reduzido character varying(60), "
                  +"seg_name character varying(1024), "
                  +"unidade character(2), "
                  +"ean character(14), "
                  +"ncm character(8), "
                  +"cest character(7), "
                  +"origem character(1), "
                  +"preco_compra double precision, "
                  +"preco double precision, "
                  +"peso numeric(10,3), "
                  +"codigo_fornec character(12), "
                  +"fornecedor character(15), "
                  +"marca character varying(25), "
                  +"link character varying(1024), "
                  +"images character varying(1024), "
                  +"source_fat character(2), "
                  +"estoque integer, "
                  +"garantia double precision, "
                  +"icms_cst character varying(3), "
                  +"icms_perc double precision, "
                  +"icms_pred double precision, "
                  +"ipi_cst character varying(2), "
                  +"ipi_perc double precision, "
                  +"pis_cst character varying(2), "
                  +"pis_perc double precision, "
                  +"cofins_cst character varying(2), "
                  +"cofins_perc double precision, "
                  +"trib_st_perc double precision, "
                  +"descnovo character(1024), "
                  +"descricao character(1024), "
                  +"cnpj_fornecedor character(14), "
                  +"CONSTRAINT produto_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO produto (codigo, nome_reduzido, seg_name, unidade, ean, ncm, cest, origem, preco_compra, preco, peso, codigo_fornec, fornecedor, marca, link, images, source_fat, estoque, garantia, icms_cst, icms_perc, icms_pred, ipi_cst, ipi_perc, pis_cst, pis_perc, cofins_cst, cofins_perc, trib_st_perc, descnovo, descricao, cnpj_fornecedor) "
                    + "VALUES (1, 'CURSO DE JAVA SE I', '', 'UN', '              ', '07099990', '       ', '0', 0, 130, 0.200, '            ', '               ', '', NULL, '', NULL, NULL, 0, '102', 0, 0, '00', 0, '08', 0, '08', 0, NULL, NULL, 'CURSO DE JAVA SE I ', NULL);"
                 +"INSERT INTO produto (codigo, nome_reduzido, seg_name, unidade, ean, ncm, cest, origem, preco_compra, preco, peso, codigo_fornec, fornecedor, marca, link, images, source_fat, estoque, garantia, icms_cst, icms_perc, icms_pred, ipi_cst, ipi_perc, pis_cst, pis_perc, cofins_cst, cofins_perc, trib_st_perc, descnovo, descricao, cnpj_fornecedor) "
                    + "VALUES (2, 'PRODUTO GENERICO',       '', 'UN', '7501007413914', '07099990', '       ', '0', 0, 12.50, 0.500, '            ', '               ', '', NULL, '', NULL, NULL, 0, '102', 0, 0, '00', 0, '08', 0, '08', 0, NULL, NULL, 'PRODUTO GENERICO', NULL);"
                    ;
            retorno = st_cria.executeUpdate(sql);

            sql = "CREATE TABLE tipo_doc ( "
                  +"codigo character(2) NOT NULL, "
                  +"descricao character(20) NOT NULL, "
                  +"CONSTRAINT tdoc_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO tipo_doc (codigo, descricao) VALUES ('AV', 'A VISTA             '); "
                    +"INSERT INTO tipo_doc (codigo, descricao) VALUES ('CA', 'CARTAO              '); "
                    +"INSERT INTO tipo_doc (codigo, descricao) VALUES ('BL', 'BOLETO              '); "
                    +"INSERT INTO tipo_doc (codigo, descricao) VALUES ('CC', 'CREDITO EM CTA      ');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //20
            sql = "CREATE TABLE uf ( "
                  +"codigo character(2) NOT NULL, "
                  +"descricao character varying(25), "
                  +"CONSTRAINT uf_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO uf (codigo, descricao) VALUES ('AC', 'ACRE'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('AL', 'ALAGOAS'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('AP', 'AMAPA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('AM', 'AMAZONAS'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('BA', 'BAHIA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('CE', 'DISTRITO FEDERAL'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('ES', 'ESPIRITO SANTO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('GO', 'GOIAS'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('MA', 'MARANHAO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('MT', 'MATO GROSSO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('MS', 'MATO GROSSO DO SUL'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('MG', 'MINAS GERAIS'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('PA', 'PARA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('PB', 'PARAIBA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('PR', 'PARANA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('PE', 'PERNAMBUCO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('PI', 'PIAUI'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('RJ', 'RIO DE JANEIRO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('RN', 'RIO GRANDE DO NORTE'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('RS', 'RIO GRANDE DO SUL'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('RO', 'RONDONIA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('RR', 'RORAIMA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('SC', 'SANTA CATARINA'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('SP', 'SAO PAULO'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('SE', 'SERGIPE'); "
                    +"INSERT INTO uf (codigo, descricao) VALUES ('TO', 'TOCANTINS');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //21
            sql = "CREATE TABLE unidade ( "
                  +"codigo character(2) NOT NULL, "
                  +"descricao character(25), "
                  +"CONSTRAINT unidade_pkey PRIMARY KEY (codigo) "
                  +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO unidade (codigo, descricao) VALUES ('UN', 'UNIDADE                  '); "
                    +"INSERT INTO unidade (codigo, descricao) VALUES ('PC', 'PEÇA                     '); "
                    +"INSERT INTO unidade (codigo, descricao) VALUES ('CX', 'CAIXA                    ');";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //22
            sql = "CREATE TABLE boleto ( "
                +"ordem character(5), "
                +"banco character(30), "
                +"nome_banco character(10), "
                +"dataemis character(10), "
                +"datavcto character(10), "
                +"numbloq character(13), "
                +"agencia character(4), "
                +"dac_agencia character(1), "
                +"conta character(8), "
                +"carteira character(2), "
                +"cod_cedente character(7), "
                +"pac_cedente character(2), "
                +"nome_cedente character(50), "
                +"cnpj_cedente character(24), "
                +"numdoc character(12), "
                +"tipodoc character(10), "
                +"moeda character(4), "
                +"valor character(20), "
                +"jurodia character(12), "
                +"obs1 character(80), "
                +"obs2 character(80), "
                +"obs3 character(50), "
                +"obs4 character(50), "
                +"cod_sacado character(7), "
                +"nome_sacado character(50), "
                +"ende_sacado character(50), "
                +"cep_sacado character(9), "
                +"cidade_sacado character(30), "
                +"uf_sacado character(2), "
                +"doc_sacado character(25), "
                +"vendedor character(30), "
                +"carro character(5), "
                +"dias_protesto character(2), "
                +"cod_barra character(46), "
                +"linha_digitavel character(60) "
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //23
            sql = "CREATE TABLE maquina ("
                +"codigo serial NOT NULL, "
                +"empresa integer, "
                +"nome character(20), "
                +"modelo character(20), "
                +"numero_serie character(20), "
                +"observacao character varying(255), "
                +"data_compra date, "
                +"CONSTRAINT maq_pkey PRIMARY KEY (codigo) "
                 +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = "INSERT INTO maquina(codigo, empresa, nome, modelo, numero_serie, observacao, data_compra)\n" +
                    " VALUES (1, 1, 'maquina01', 'modelo01', 'serie01', '', '2017-01-01');"
                    ;
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //24
            sql = "CREATE TABLE caixa ("
                +"codigo serial NOT NULL, "
                +"empresa integer, "
                +"nome character(15), "
                +"codmaquina integer, "
                +"CONSTRAINT cxa_pkey PRIMARY KEY (codigo)"
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql = " INSERT INTO caixa(codigo, empresa, nome, codmaquina) VALUES (1, 1, 'caixa01', 1);";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //25
            sql = "CREATE TABLE numeropedido ("
                +"empresa integer, "
                +"pedido integer "
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //26
            sql = "CREATE TABLE pedidos65 ( "
                +"empresa integer, "
                +"pedido integer NOT NULL, "
                +"documento character(14), "
                +"nome_documento character varying (60), "
                +"cod_forma_pgto integer NOT NULL, "
                +"data_digitacao date, "
                +"qtde_itens integer NOT NULL, "
                +"valor_produtos double precision, "
                +"valor_descontos double precision, "
                +"valor_total double precision, "
                +"dados_adicionais character varying(255), "
                +"qtde_volume integer DEFAULT 0, "
                +"peso_volume double precision, "
                +"numero_nfe numeric(9) DEFAULT 0, "
                +"serienfe character varying(3), "
                +"modelonfe character varying(2), "
                +"data_emissao date, "
                +"cancelado boolean, "
                +"codcaixa integer, "
                +"codlogin integer, "
                +"codmaquina integer, "
                +"docAutTEF character varying(10), "
                +"cnpjTEF character(14), "    
                +"CONSTRAINT ped65_pkey PRIMARY KEY (empresa, pedido) "
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //27
            sql = "CREATE TABLE produtos65 ( "
                +"empresa integer, "
                +"pedido integer NOT NULL, "
                +"item integer NOT NULL DEFAULT 0, "
                +"cod_produto integer NOT NULL, "
                +"cod_cfop integer, "
                +"quantidade integer NOT NULL DEFAULT 0, "
                +"peso numeric(10,3), "
                +"vlr_unitario double precision NOT NULL, "
                +"vlr_produto double precision, "
                +"vlr_desconto double precision, "
                +"vlr_total double precision, "
                +"cancelado boolean, "
                +"CONSTRAINT prod65_pkey PRIMARY KEY (empresa, pedido, item) "
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //28
            sql = "CREATE TABLE tipo_pgto ("
                    + "id serial NOT NULL, "
                    + "codigo character(10), "
                    + "descricao character(30), "
                    + "tef character(1), "
                    + "CONSTRAINT tipopgto_pkey PRIMARY KEY (id) "
                +") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            sql =
                    "INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('01', 'DINHEIRO', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('02', 'CHEQUE', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('03', 'CARTAO DE CREDITO', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('04', 'CARTAO DE DEBITO', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('05', 'CREDITO LOJA', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('10', 'VALE ALIMENTACAO', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('11', 'VALE REFEICAO', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('12', 'VALE PRESENTE', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('13', 'VALE COMBUSTIVEL', 'N'); "
                    +"INSERT INTO tipo_pgto (codigo, descricao, tef) VALUES ('99', 'OUTROS', 'N'); "
                    ;
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //29
            sql =
                    "CREATE TABLE tipopgto65 ("
                    + " empresa integer NOT NULL,"
                    + " pedido integer NOT NULL,"
                    + " id_tipo_pgto integer NOT NULL,"
                    + " valor double precision,"
                    + " CONSTRAINT idtpgto_pkey PRIMARY KEY (empresa, pedido, id_tipo_pgto)"
                    + " )"
                    + " WITH ("
                    + " OIDS=FALSE"
                    + ")";
            retorno = st_cria.executeUpdate(sql);

            
            nTab++;  //30
            sql =
                    "CREATE TABLE suprimento ("
                    + " id serial NOT NULL,"
                    + " empresa integer NOT NULL, "
                    + " codcaixa integer NOT NULL,"
                    + " codlogin integer NOT NULL,"
                    + " codgerente integer NOT NULL,"
                    + " data_suprimento date,"
                    + " valor double precision,"
                    + " CONSTRAINT supri_pkey PRIMARY KEY (id)"
                    + ") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);

            nTab++;  //31
            sql =
                    "CREATE TABLE sangria ("
                    + " id serial NOT NULL,"
                    + " empresa integer NOT NULL, "
                    + " codcaixa integer NOT NULL,"
                    + " codlogin integer NOT NULL,"
                    + " codgerente integer NOT NULL,"
                    + " data_sangria date,"
                    + " valor double precision,"
                    + " CONSTRAINT sang_pkey PRIMARY KEY (id)"
                    + ") WITH ( OIDS=FALSE )";
            retorno = st_cria.executeUpdate(sql);
            
        } catch (SQLException ec) {
        } catch (Exception ec) {
            JOptionPane.showMessageDialog(null, "Erro ("+nTab+") na criacao das tabelas: "+ec);
        }
         try { 
            st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sql = "alter table cliente add column ehtransp character(1);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
             
         }
         try { 
            st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sql = "alter table pedidos65 add column docAutTEF character varying(10);";
            retorno = st_cria.executeUpdate(sql);
            sql = "alter table pedidos65 add column cnpjTEF character(14);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
             
         }
         try { 
            st_cria = conexao.createStatement();  //ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sql = "alter table pardigital add column csc_prod character varying(40);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
             
         }
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf_produtos ALTER COLUMN quantidade TYPE double precision;";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE produtos65 ALTER COLUMN quantidade TYPE double precision;";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf_produtos ADD COLUMN cod_interno character(13);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf_produtos ALTER COLUMN cod_interno TYPE character(14);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
            //System.out.println("Conexao_fatura - erro: " + ec);
         }
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf ADD COLUMN pedido_interno character(15);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
//tamanho da coluna alterada em 01/03/18         
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf ADD COLUMN pedido_interno character(15);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
//coluna incluida em 03/03/18         
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE caixa ADD COLUMN serie65 character(3);";
            retorno = st_cria.executeUpdate(sql);
            sql = "update caixa set serie65 = '001' where serie65 IS NULL";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
         }
// fim inclusao coluna         
         try { 
            st_cria = conexao.createStatement();  
            sql = "ALTER TABLE nf_produtos ADD COLUMN vTotIbpt double precision;";
            retorno = st_cria.executeUpdate(sql);
            sql = "ALTER TABLE nf ADD COLUMN vTotIbpt double precision;";
            retorno = st_cria.executeUpdate(sql);
            sql = "ALTER TABLE produto ADD COLUMN tipo_ncm character varying(5);";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
            //System.out.println("Erro conexao_fatura: "+ ec);
        }
         try { 
            st_cria = conexao.createStatement();  
            sql = "UPDATE produto SET tipo_ncm = '0' where tipo_ncm IS NULL;";
            retorno = st_cria.executeUpdate(sql);
        } catch (SQLException ec) {
            System.out.println("Erro conexao_fatura: "+ ec);
        }
         return retorno;
       }
       
}
