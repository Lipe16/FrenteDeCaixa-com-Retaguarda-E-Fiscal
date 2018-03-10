/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
//import Importar.ImportaCadastros;
import br.com.videoaulasneri.adelcio.fatura.bean.Ibge;
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

public class ImportaCidades
{
	private Scanner input;
        Ibge reg;
        Connection connection;
        Statement statement;
        ResultSet rs_ibge;
        String driver, url, usuario, senha, uf;
//        public int qtreg = 0;
        private int qtregi;
        
        public ImportaCidades(Connection confat) {
            this.connection = confat;
            
        }

	public void ImportaArquivo(String uf)
	{
            this.uf = uf;
            if (uf == null){
                JOptionPane.showMessageDialog(null, "Favor informar a UF para importar . . .");
            } else {
                JOptionPane.showMessageDialog(null, "Tecle ENTER e aguarde alguns segundos até aparecer o resultado da importação da Tabela  . . .");
                conecta_bd();
                processaIbge();
                fechaArquivo();
            }
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
        public void processaIbge()
        {
		String arqcsv = System.getProperty("user.dir")+System.getProperty("file.separator")+"ibge.csv";
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
                rs_ibge = statement.executeQuery("select * from ibge");
            } catch (SQLException ex) {
                Logger.getLogger(ImportaCidades.class.getName()).log(Level.SEVERE, null, ex);
            }
     	
            reg = new Ibge();
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
                        switch (cpo%10)
                        {
                                case 1: 
                                    reg.setUfNum(Integer.parseInt(wconteudo)); 
                                    //System.out.print("uf num: "+reg.getUfNum());
                                    break;
                                case 8: 
                                    reg.setCodcidade(Integer.parseInt(wconteudo)); 
                                    //System.out.print("codcid: "+reg.getCodcidade());
                                    break;
                                case 9: 
                                    reg.setCidade(wconteudo.toUpperCase()); 
                                    //System.out.println("cidade: "+reg.getCidade());
                                    insereRegistro(); 
                                    break;
                    	}
                    }
                }
//   		JOptionPane.showMessageDialog(null,"Foram Importados " + qtreg + " Registros do Vendedor "+ vendedor + " com Sucesso!");
                JOptionPane.showMessageDialog(null, "Foram Importados " + qtregi + " Registros de Cidades com Sucesso!");
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
 		
        int[] tabUfNum = {11, 12, 13, 14, 15, 16, 17,
                        21, 22, 23, 24, 25, 26, 27, 28, 29,
                        31, 32, 33, 35,
                        41, 42, 43,
                        50, 51, 52, 53
        };
        String[] tabUf = {"RO", "AC", "AM", "RR", "PA", "AP", "TO", 
                        "MA", "PI", "CE", "RN", "PB", "PE", "AL", "SE", "BA", 
                        "MG", "ES", "RJ", "SP", "PR", "SC", "RS", 
                        "MS", "MT", "GO", "DF"
                
        };
        public void insereRegistro()
        {
	//  Inclusao do registro no banco de dados
            //if (uf == null || uf.equals("")) {  // || 
                try
                {
                    int idx = 30;
                    for (int i=0; i< 27; i++) {
                        if (reg.getUfNum() == tabUfNum[i]) {
                            idx = i;
                            i = 27;
                        }
                    }
                    if (idx != 30) {
                    reg.setUf(tabUf[idx]);
                        //System.out.println("UF str: "+reg.getUf());

                        if (uf.equals("XX") || reg.getUf().toUpperCase().equals(uf.toUpperCase())){
                            if ((""+reg.getCodcidade()).length() > 7) {
                                String codcid = (""+reg.getCodcidade());
                                codcid = codcid.substring(0, 7);
                                reg.setCodcidade(Integer.parseInt(codcid));
                            }
                            reg.setCodigo(reg.getCodcidade());
                            reg.setDistrito(reg.getCidade());
                            int codigo = 0;
                            rs_ibge = statement.executeQuery("select * from ibge where codcidade = "+reg.getCodcidade());
                            if (!rs_ibge.first()){
                                rs_ibge = statement.executeQuery("select max(codigo) as ultCod from ibge");
                                while (rs_ibge.next()) {
                                    codigo = rs_ibge.getInt("ultCod");
                                }
                                codigo++;
                                String sqlinsert ="insert into ibge ("+
                                    "codigo, "+
                                    "codcidade, "+
                                    "cidade, "+
                                    "distrito, "+
                                    "uf "+
                                    ") "+
                                    "values ("+
                                     codigo+", "+
                                     reg.getCodcidade()+", "+
                                     "'"+reg.getCidade()+"', "+
                                     "'"+reg.getDistrito()+"', "+
                                     "'"+reg.getUf()+"' "+
                                     ")";

                                //if (qtregi < 5) {
                                //    JOptionPane.showMessageDialog(null,sqlinsert);
                                //}
                                //System.out.println("Sql: "+sqlinsert);
                                statement.executeUpdate(sqlinsert);
                                qtregi = qtregi + 1;
        //        		  JOptionPane.showMessageDialog(null,"Gravacao realizado com sucesso!");
                            }
                        }
                    }
                }
                catch (Exception erro)
                {
                    JOptionPane.showMessageDialog(null,"Erro a tentar Gravar o registro com codigo: "+reg.getCodcidade()+" - Erro: "+erro);
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
		ImportaCidades leIbge = new ImportaCidades(null);
		leIbge.ImportaArquivo("PR");
		//leIbge.leRegistros();
		//leIbge.fechaArquivo();
	}
}