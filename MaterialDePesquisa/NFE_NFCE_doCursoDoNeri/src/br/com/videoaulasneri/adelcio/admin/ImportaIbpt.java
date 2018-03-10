/*

Descrição: Importa Tabela de Impostos (IBPT) para Exibir no Cupom(NFCe)
site para baixar as tabelas por UF: https://deolhonoimposto.ibpt.org.br/Site/PassoPasso

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.admin;
import br.com.videoaulasneri.adelcio.fatura.bean.Ibpt;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ImportaIbpt
{
	private Scanner input;
        Ibpt reg;
        Connection connection;
        Statement statement;
        ResultSet rs_ibge;
        String driver, url, usuario, senha;
        boolean gravar = false;
        private String ufEmitente = "PR";
        private String arqIbpt = "";
//        public int qtreg = 0;
        private int qtregi;
        
        public ImportaIbpt(Connection confat) {
            this.connection = confat;
            
        }

	public void ImportaArquivo(String arqIbpt, String uf)
	{
            this.ufEmitente = uf;
            this.arqIbpt = arqIbpt;
            if (uf == null){
                JOptionPane.showMessageDialog(null, "Favor informar a UF para importar . . .");
            } else {
                JOptionPane.showMessageDialog(null, "Tecle ENTER e aguarde alguns segundos até aparecer o resultado da importação da Tabela  . . .");
                conecta_bd();
                int idx = 30;
                for (int i=0; i< 27; i++) {
                    if (Integer.parseInt(ufEmitente) == tabUfNum[i]) {
                        idx = i;
                        i = 27;
                    }
                }
                if (idx != 30) {
                    ufEmitente = tabUf[idx];
                }
                System.out.println("ImportaIbpt - uf emitente: " + ufEmitente);
                apaga_uf();
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
        private void apaga_uf() {
            String sqldelete = "delete from ibpt where uf = '" + ufEmitente + "'";
            try {
                 statement.executeUpdate(sqldelete);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar apagar os registros da tabela ibpt para a uf: " + ufEmitente);
            }
        }
        public void processaIbge()
        {
		String arqcsv = arqIbpt;
//		JOptionPane.showMessageDialog(null, "Nome do arquivo texto: " + arqcsv );
		System.out.println("Nome do arquivo texto: " + arqcsv );
		
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
                rs_ibge = statement.executeQuery("select * from ibpt");
            } catch (SQLException ex) {
                Logger.getLogger(ImportaIbpt.class.getName()).log(Level.SEVERE, null, ex);
            }
     	
            reg = new Ibpt();
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
                    //StringTokenizer token = new StringTokenizer( frase , delimita);
                    String[] campos = frase.split(";");
                    int cpo = 0;
                    gravar = false;
//                    while ( token.hasMoreTokens() )
                    for (String campo : campos)
                    {
                        cpo++;
//                        String wconteudo = token.nextToken();
                        String wconteudo = campo;
                        //if (qtReg < 3) {
//System.out.println("Num.Cpo: " + cpo + " / 13" +  " - Conteudo: " + wconteudo);
                        //}
                        switch (cpo%14)
                        {
                                case 1: 
                                    if (wconteudo.length() == 8) {
                                        reg.setNcm(wconteudo); 
                                        //System.out.print("NCM: "+reg.getNcm());
                                        gravar = true;
                                    }
                                    break;
                                case 3: 
                                    reg.setTipo(wconteudo); 
                                    break;
                                case 4: 
                                    reg.setDescricao(wconteudo.toUpperCase()); 
                                    break;
                                case 5: 
                                    reg.setAliqNacionalFederal(Double.parseDouble(wconteudo)); 
                                    break;
                                case 6: 
                                    reg.setAliqImportadosFederal(Double.parseDouble(wconteudo)); 
                                    break;
                                case 7: 
                                    reg.setAliqEstadual(Double.parseDouble(wconteudo)); 
                                    break;
                                case 8: 
                                    reg.setAliqMunicipal(Double.parseDouble(wconteudo)); 
                                    break;
                                case 9: 
                                    reg.setVigenciaInicio(stringToDate(wconteudo)); 
                                    break;
                                case 10: 
                                    reg.setVigenciaFim(stringToDate(wconteudo)); 
                                    break;
                                case 11: 
                                    reg.setChave(wconteudo); 
                                    break;
                                case 12: 
                                    reg.setVersao(wconteudo); 
                                    break;
                                case 13: 
                                    reg.setFonte(wconteudo); 
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
            	System.err.println("Erro de tipo de dado incompativel!" + e);
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
                        "MG", "ES", "RJ", "SP", 
                        "PR", "SC", "RS", 
                        "MS", "MT", "GO", "DF"
                
        };
        public void insereRegistro()
        {
            if (gravar) {
                try
                {
                    reg.setUf(ufEmitente);

                    boolean exist = false;
                    String sql = "select ncm from ibpt where uf = '" + reg.getUf() + "' and ncm = '" + reg.getNcm() + "' and tipo = '" + reg.getTipo() + "'";
                    ResultSet rset = statement.executeQuery(sql);
                    while (rset.next()) {
                        exist = true;
                    }
                    
                    if (!exist) {
                        String sqlinsert ="insert into ibpt ("+
                            "UF, "+
                            "ncm, "+
                            "tipo, "+
                            "descricao, "+
                            "aliqNacionalFederal, "+
                            "aliqImportadosFederal, "+
                            "aliqEstadual, "+
                            "aliqMunicipal, "+
                            "vigenciaInicio, "+
                            "vigenciaFim, "+
                            "chave, "+
                            "versao, "+
                            "fonte "+
                            ") "+
                                "values ("+
                             "'"+reg.getUf()+"', "+
                             "'"+reg.getNcm()+"', "+
                             "'"+reg.getTipo()+"', "+
                             "'"+reg.getDescricao()+"', "+
                             reg.getAliqNacionalFederal()+", "+
                             reg.getAliqImportadosFederal()+", "+
                             reg.getAliqEstadual()+", "+
                             reg.getAliqMunicipal()+", "+
                             "'"+reg.getVigenciaInicio()+"', "+
                             "'"+reg.getVigenciaFim()+"', "+
                             "'"+reg.getChave()+"', "+
                             "'"+reg.getVersao()+"', "+
                             "'"+reg.getFonte()+"' "+
                             ")";

//    System.out.println("Sql: "+sqlinsert);
                        statement.executeUpdate(sqlinsert);

                        qtregi = qtregi + 1;
                    }
                }
                catch (Exception erro)
                {
                    JOptionPane.showMessageDialog(null,"Erro a tentar Gravar o registro com codigo: "+reg.getNcm()+" - Erro: "+erro);
                }
            }
		
        }
        public void fechaArquivo()
	{
		if (input != null)
			input.close();
	}
        private Date stringToDate(String dateStr) {
            Date retorno = null;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                retorno = formatter.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return retorno;
        }
	
	// Teste da classe
	public static void main( String args[] )
	{
		ImportaIbpt leIbge = new ImportaIbpt(null);
		leIbge.ImportaArquivo("", "PR");
		//leIbge.leRegistros();
		//leIbge.fechaArquivo();
	}
}