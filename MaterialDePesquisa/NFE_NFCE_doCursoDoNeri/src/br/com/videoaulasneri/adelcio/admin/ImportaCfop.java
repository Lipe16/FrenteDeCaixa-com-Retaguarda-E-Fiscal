/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
//import Importar.ImportaCadastros;
import br.com.videoaulasneri.adelcio.fatura.bean.Cfop;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ImportaCfop
{
	private Scanner input;
        Cfop reg;
        Connection connection;
        Statement statement;
        ResultSet rs_ibge;
        String driver, url, usuario, senha, uf;
//        public int qtreg = 0;
        private int qtregi;
        
        public ImportaCfop(Connection confat) {
            this.connection = confat;
            
        }

	public void ImportaArquivo()
	{
            conecta_bd();
            processaCfop();
            fechaArquivo();
	}
        public void conecta_bd(){
            try
            {
                  //Class.forName(driver);
                  //connection = DriverManager.getConnection(url,usuario,senha);
                  statement  = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao tentar conectar o BD "+ e);
            }
        }
        public void processaCfop()
        {
		String arqcsv = System.getProperty("user.dir")+System.getProperty("file.separator")+"cfop.csv";
//		JOptionPane.showMessageDialog(null, "Nome do arquivo texto: " + arqcsv );
//		System.out.println("Nome do arquivo texto: " + arqcsv );
		
		try
		{
                    input = new Scanner(new File(arqcsv));
                    leRegistros();
		}
		catch (FileNotFoundException e)
		{
                    JOptionPane.showMessageDialog(null, "Não encontrou o arquivo: " + arqcsv + " e a Importação de Cidades não será efetuada!");
		}
            
        }
	public void leRegistros()
	{
            try {
                //  Abre o banco de dados Cadastros
                rs_ibge = statement.executeQuery("select * from cfop");
            } catch (SQLException ex) {
                Logger.getLogger(ImportaCfop.class.getName()).log(Level.SEVERE, null, ex);
            }
     	
            reg = new Cfop();
            int qtReg = 0;
            try
            {
                String frase = input.nextLine();
		while ( input.hasNextLine())
		{
                    frase = input.nextLine();
                    String delimita = ";";
                    qtReg++;
//System.out.println("qtReg: "+qtReg);			
                    StringTokenizer token = new StringTokenizer( frase , delimita);
                    int cpo = 0;
                    while ( token.hasMoreTokens() )
                    {
                        cpo++;
                        String wconteudo = token.nextToken();
                        //if (qtReg < 3) {
                        //    JOptionPane.showMessageDialog(null, "Num.Cpo: " + cpo + " / 9" +  " - Conteudo: " + wconteudo);
                        //}
                        switch (cpo%7)
                        {
                                case 1: 
                                    reg.setCfop(Integer.parseInt(wconteudo)); 
                                    System.out.print("Cfop: "+reg.getCfop());
                                    break;
                                case 2: 
                                    reg.setSeqcfop(Integer.parseInt(wconteudo)); 
                                    System.out.print("seqcfop: "+reg.getSeqcfop());
                                    break;
                                case 3: 
                                    reg.setDescricao(wconteudo.toUpperCase().trim()); 
                                    System.out.println("descricao: "+reg.getDescricao());
                                    break;
                                case 5: 
                                    reg.setOperacao(wconteudo.toUpperCase().trim()); 
                                    System.out.println("operacao: "+reg.getOperacao());
                                    insereRegistro(); 
                                    break;
                    	}
                    }
                }
//   		JOptionPane.showMessageDialog(null,"Foram Importados " + qtreg + " Registros do Vendedor "+ vendedor + " com Sucesso!");
                JOptionPane.showMessageDialog(null, "Foram Importados " + qtregi + " Registros de CFOP com Sucesso!");
	    }
            catch( NoSuchElementException e)
            {
            	System.err.println("Erro de tipo de dado incompativel!");
		System.exit(1);
            }
            catch (IllegalStateException e)
            {
            	System.err.println("Erro ao ler o arquivo!");
		System.exit(1);
            }
        }    
 		
        public void insereRegistro()
        {
	//  Inclusao do registro no banco de dados
        try
        {
                    int codigo = 0;
                    rs_ibge = statement.executeQuery("select * from cfop where cfop = "+reg.getCfop()+"  and seqcfop = "+reg.getSeqcfop());
                    if (!rs_ibge.first()){
                        rs_ibge = statement.executeQuery("select max(codigo) as ultCod from cfop");
                        while (rs_ibge.next()) {
                            codigo = rs_ibge.getInt("ultCod");
                        }
                        codigo++;
                        String sqlinsert ="insert into cfop ("+
                            "codigo, "+
                            "cfop, "+
                            "descricao, "+
                            "observacao, "+
                            "operacao, "+
                            "faturamento,"+
                            "financeiro, "+
                            "seqcfop "+
                            ") "+
                            "values ("+
                             codigo+", "+
                             reg.getCfop()+", "+
                             "'"+reg.getDescricao()+"', "+
                             "'', "+
                             "'"+reg.getOperacao()+"', "+
                             true + ", "+
                             false + ", "+
                             reg.getSeqcfop() +
                             ")";

                        System.out.println("Sql: "+sqlinsert);
                        statement.executeUpdate(sqlinsert);
                        qtregi = qtregi + 1;
//        		  JOptionPane.showMessageDialog(null,"Gravacao realizado com sucesso!");
                    }
                }
                catch (Exception erro)
                {
                    JOptionPane.showMessageDialog(null,"Erro a tentar Gravar o registro com codigo: "+reg.getCodigo()+" - Cfop: "+reg.getCfop()+" - Erro: "+erro);
                }
             //}
		
        }
        public void fechaArquivo()
	{
		if (input != null)
			input.close();
	}
	
	// Teste da classe
	public static void main( String args[] )
	{
		ImportaCfop leCfop = new ImportaCfop(null);
		leCfop.ImportaArquivo();
		//leIbge.leRegistros();
		//leIbge.fechaArquivo();
	}
}