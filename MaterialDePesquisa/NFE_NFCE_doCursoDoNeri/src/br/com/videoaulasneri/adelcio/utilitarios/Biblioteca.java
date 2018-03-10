/*

DescriÁ„o: Biblioteca de mÈtodos ˙teis para formataÁ„o de dados do sistema

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/
package br.com.videoaulasneri.adelcio.utilitarios;

import br.com.videoaulasneri.adelcio.nfeweb.util.Estados;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Biblioteca {

    public static String repete(String string, int quantidade) {
        StringBuffer retorno = new StringBuffer();
        for (int j = 0; j < quantidade; j++) {
            retorno.append(string);
        }
        return retorno.toString();
    }

    public static boolean validaCpfCnpj(String s_aux) {
        if (s_aux.length() == 11) {
            int d1, d2;
            int digito1, digito2, resto;
            int digitoCPF;
            String nDigResult;
            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;
            for (int n_Count = 1; n_Count < s_aux.length() - 1; n_Count++) {
                digitoCPF = Integer.valueOf(s_aux.substring(n_Count - 1, n_Count)).intValue();
                d1 = d1 + (11 - n_Count) * digitoCPF;
                d2 = d2 + (12 - n_Count) * digitoCPF;
            }

            resto = (d1 % 11);
            if (resto < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - resto;
            }
            d2 += 2 * digito1;
            resto = (d2 % 11);
            if (resto < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - resto;
            }
            String nDigVerific = s_aux.substring(s_aux.length() - 2, s_aux.length());
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
            return nDigVerific.equals(nDigResult);
        } else if (s_aux.length() == 14) {
            int soma = 0, aux, dig;
            String cnpj_calc = s_aux.substring(0, 12);
            char[] chr_cnpj = s_aux.toCharArray();

            for (int i = 0; i < 4; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11)
                    ? "0" : Integer.toString(dig);
            soma = 0;
            for (int i = 0; i < 5; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                    soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11)
                    ? "0" : Integer.toString(dig);
            return s_aux.equals(cnpj_calc);
        } else {
            return false;
        }
    }

    public static String MD5String(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5File(String arquivo)
            throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        File f = new File(arquivo);
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[8192];
        int read = 0;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            return output.toUpperCase();
        } catch (IOException e) {
            throw new RuntimeException("Imposs√≠vel processar o arquivo.", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public static void removeLineFromFile(String file, String lineToRemove) {
        try {
            File inFile = new File(file);
            if (!inFile.isFile()) {
                System.out.println("Arquivo n√£o localizado!");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp2");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("N√£o foi poss√≠vel apagar o arquivo");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("N√£o foi poss√≠vel renomear o arquivo!");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addLineFromFile(String file, String lineToAdd) {
        try {
            File inFile = new File(file);
            if (!inFile.isFile()) {
                System.out.println("Arquivo n√£o localizado!");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                pw.println(line);
                pw.flush();
            }
            pw.println(lineToAdd);
            pw.flush();
            pw.close();
            br.close();
            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("N√£o foi poss√≠vel apagar o arquivo");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("N√£o foi poss√≠vel renomear o arquivo!");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * M√©todo que verifica se uma data no formato dd/mm/aaaa √© valida.
     *
     * @param data a ser validada
     * @return true se a data √© valida, false se a data √© inv√°lida
     */
    public static boolean isDataValida(String strData) {
        try {
            if ((strData.length() != 10)) {
                return false;
            }
            Calendar dataValida = Calendar.getInstance();
            dataValida.setLenient(false);
            int dia = Integer.valueOf(strData.substring(0, 2));
            int mes = Integer.valueOf(strData.substring(3, 5));
            int ano = Integer.valueOf(strData.substring(6, 10));

            dataValida.set(Calendar.DAY_OF_MONTH, dia);
            dataValida.set(Calendar.MONTH, mes - 1);
            dataValida.set(Calendar.YEAR, ano);

            dataValida.getTime();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retorna uma strig formatada de acordo com a configura√ß√£o do sistema
     *
     * @param tipo Q para quantidade ou V para valor
     * @return string formatada
     */
    public static String formatoDecimal(String tipo, double valor) {

        String mascara = "0.";
        if (tipo.equals("Q")) {
            for (int i = 0; i < 3; i++) {
                mascara += "0";
            }
        } else if (tipo.equals("V")) {
            for (int i = 0; i < 2; i++) {
                mascara += "0";
            }
        }
        DecimalFormat formato = new DecimalFormat(mascara);
        return formato.format(valor);
    }
    public static boolean check_certificados(String file_keystore, String file_truststore){
        boolean retorno = true;
         File certificado;
         System.out.println("check_certificados() / file_keystore: "+file_keystore);  //+" / file_keystore: "+file_truststore);
         if (file_keystore.indexOf("token.cfg") != -1) { // o certificado È do tipo A3 (token)
             certificado = new File(file_keystore);
             if (!certificado.exists()) {
                 JOptionPane.showMessageDialog(null, "AtenÁ„o! Arquivo de Certificado: "+file_keystore+" n„o encontrado!\nFavor informar o administrador do Sistema.");
                 retorno = false;
             }
         }else{  //  O certificado È do tipo A1
             certificado = new File(file_keystore);
             if (!certificado.exists()) {
                 JOptionPane.showMessageDialog(null, "AtenÁ„o! Arquivo de Certificado: "+file_keystore+" n„o encontrado!\nFavor informar o administrador do Sistema.");
                 retorno = false;
             }
/*             
             certificado = new File(file_truststore);
             if (!certificado.exists()) {
                 JOptionPane.showMessageDialog(null, "AtenÁ„o! Arquivo de Certificado: "+file_truststore+" n„o encontrado!\nFavor informar o administrador do Sistema.");
                 retorno = false;
             }
*/        
         }
         return retorno;
    }
    private String alinhaTexto(String texto, int tamanho, String lado) {
        String retorno = texto.trim();
        int tamInic = retorno.length();
        if (tamInic > tamanho) {
            retorno = retorno.substring(0, tamanho);
        }
        for (int i=tamInic; i<tamanho; i++) {
            if (lado.equals("R")) {
                retorno = " "+retorno;
            } else {
                retorno += " ";
            }
        }
//JOptionPane.showMessageDialog(null, "NFCe.alinhaTextoCupom() - Texto Origem ["+texto.trim()+"] - Texto Alinhado("+lado+") ["+retorno+"]");
        return retorno;
    }
    public String pegaHexa(String entrada) {
        String retorno = "";
         StringBuilder hexString = new StringBuilder();
         for (byte b : entrada.getBytes()) {
           hexString.append(String.format("%02X", 0xFF & b));
         }
         retorno = hexString.toString().toLowerCase();
        return retorno;
    }
    public static String pegaHashSha1(String entrada) {
        String retorno = "";
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("SHA-1");
// Os algoritmos possÌveis s„o: "MD4", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "Tiger", "DHA256", e "FORK256".
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null,"Erro(1) ao tentar gerar o hash sha-1. Erro: "+ ex);
        }
        try {
            byte[] messageDigest = algorithm.digest(entrada.getBytes("UTF-8"));
            //  converte o resultado para hexadecimal
             StringBuilder hexString = new StringBuilder();
             for (byte b : messageDigest) {
               hexString.append(String.format("%02X", 0xFF & b));
             }
             retorno = hexString.toString().toLowerCase();
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null,"Erro(2) ao tentar gerar o hash sha-1. Erro: "+ ex);
        }

        return retorno;
    }

    public static String trataTiraPonto( String wcampo )
    {
            char cpo[] = new char[wcampo.length()];
            int j = 0;
            for ( int i=0; i<wcampo.length(); i++ )
            {
                    if ( wcampo.charAt( i ) == '.' ) {  }
                    else if ( wcampo.charAt( i ) == ' ' ) {  }
                    else if ( wcampo.charAt( i ) == '-' ) {  }
                    else if ( wcampo.charAt( i ) == '(' ) {  }
                    else if ( wcampo.charAt( i ) == ')' ) {  }
                    else if ( wcampo.charAt( i ) == '/' ) {  }
                    else if ( wcampo.charAt( i ) == ',' ) {  }
                    else if ( wcampo.charAt( i ) == '*' ) {  }
                    else { cpo[j] = wcampo.charAt( i ); j++; }
            }
            String resultado = "";
            for (int i=0;i<j;i++)
            {
                    resultado = resultado + cpo[i];
           }
            return resultado;
    }

    public static String tiraEspacos(String texto) {
        String retorno = "";
        int len = texto.length();
        for (int i=0; i<len; i++) {
            if (!texto.substring(i, i+1).equals(" ")) {
                retorno += texto.substring(i, i+1);
            }
        }
        return retorno;
    }
    public static String normalizeXML(String xml) {
        if ((xml != null) && (!"".equals(xml))) {
            xml = xml.replaceAll("\\r\\n", "");
            xml = xml.replaceAll(" standalone=\"no\"", "");
            while (xml.indexOf(" <") != -1){
                    xml = xml.replaceAll(" <", "<");
            }
        }
            return xml;
    }
    public static String tiraEspacoFinal(String texto){
        //System.out.println("texto original: ["+texto+"]");
        int tamtxt = texto.length();
        try {
            for (int i=tamtxt;(texto.substring(texto.length()-1, texto.length()).indexOf(" ") != -1);i--){
                texto = texto.substring(0,texto.length()-1);
                //System.out.println("texto modificado: ["+texto+"]");
            }
        } catch (Exception e) {
            
        }
        return texto;
    }
    public static String tiraEspacoInicial(String texto){
        int tamtxt = texto.length(), inic = 0;
        //System.out.println("texto original: ["+texto+"] - Tamanho: "+tamtxt);
        try {
            for (int i=0;(texto.substring(i, i+1).indexOf(" ") != -1);i++){
                inic = i;
            }
            texto = texto.substring(inic,texto.length()-inic);
        } catch(Exception e){

        }
        return texto;
    }
    public static Estados pegaEstado(String cUF) {
        Estados retorno = null;
        //int opcao = Integer.parseInt(cUF);
        switch( cUF )
       {
           case "11":
                   retorno = Estados.RO;
                   break;
           case "12":
                   retorno = Estados.AC;
                   break;
           case "13":
                   retorno = Estados.AM;
                   break;
           case "14":
                   retorno = Estados.RR;
                   break;
           case "15":
                   retorno = Estados.PA;
                   break;
           case "16":
                   retorno = Estados.AP;
                   break;
           case "17":
                   retorno = Estados.TO;
                   break;
           case "21":
                   retorno = Estados.MA;
                   break;
           case "22":
                   retorno = Estados.PI;
                   break;
           case "23":
                   retorno = Estados.CE;
                   break;
           case "24":
                   retorno = Estados.RN;
                   break;
           case "25":
                   retorno = Estados.PB;
                   break;
           case "26":
                   retorno = Estados.PE;
                   break;
           case "27":
                   retorno = Estados.AL;
                   break;
           case "28":
                   retorno = Estados.SE;
                   break;
           case "29":
                   retorno = Estados.BA;
                   break;
           case "31":
                   retorno = Estados.MG;
                   break;
           case "32":
                   retorno = Estados.ES;
                   break;
           case "33":
                   retorno = Estados.RJ;
                   break;
           case "35":
                   retorno = Estados.SP;
                   break;
           case "41":
                   retorno = Estados.PR;
                   break;
           case "42":
                   retorno = Estados.SC;
                   break;
           case "43":
                   retorno = Estados.RS;
                   break;
           case "50":
                   retorno = Estados.MS;
                   break;
           case "51":
                   retorno = Estados.MT;
                   break;
           case "52":
                   retorno = Estados.GO;
                   break;
           case "53":
                   retorno = Estados.DF;
                   break;
           default:
                   retorno = null;
       }
       return retorno;
    }
    public static String extrair_TAG(String arq_consulta, String nodePai, String tipo, String nodeAlvo, int nOcorrencia){
        String retorno = "";
        try {
            XMLReaderUmaTag lerxml = new XMLReaderUmaTag();
            lerxml = new XMLReaderUmaTag();
            retorno = lerxml.trataXML(arq_consulta, nodePai, tipo, nodeAlvo, nOcorrencia);
        } catch (Exception e) {
                e.printStackTrace();
                retorno = "";
        }
        return retorno;

    }

    public static String trataString( String wcampo )
    {
        if (wcampo != null){
            char cpo[] = new char[wcampo.length()];
            int j = 0;
            for ( int i=0; i<wcampo.length(); i++ )
            {
                   // System.out.println("Caracter lido: "+wcampo.charAt( i ));
                    if ( wcampo.charAt( i ) == '_' ) { cpo[j] = ' '; j++; }
                    else if ( wcampo.charAt( i ) == '\'' ) { cpo[j] = ' '; j++; }
                    else if ( wcampo.charAt( i ) == '\"' ) { cpo[j] = ' '; j++; }
                    //else if ( wcampo.charAt( i ) == '\n' ) { cpo[j] = ' '; j++; }
                    else if ( wcampo.charAt( i ) == '\r' ) { cpo[j] = ' '; j++; }
                   // else if ( wcampo.charAt( i ) == ':' ) { cpo[j] = ' '; j++; }
                    else if ( wcampo.charAt( i ) == '·' || wcampo.charAt( i ) == '„') { cpo[j] = 'a'; j++; }
                    else if ( wcampo.charAt( i ) == '¡' || wcampo.charAt( i ) == '√') { cpo[j] = 'A'; j++; }
                    else if ( wcampo.charAt( i ) == '˙' ) { cpo[j] = 'u'; j++; }
                    else if ( wcampo.charAt( i ) == '˚' ) { cpo[j] = 'u'; j++; }
                    else if ( wcampo.charAt( i ) == '˘' ) { cpo[j] = 'u'; j++; }
                    else if ( wcampo.charAt( i ) == '⁄' ) { cpo[j] = 'U'; j++; }
                    else if ( wcampo.charAt( i ) == '€' ) { cpo[j] = 'U'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ÿ' ) { cpo[j] = 'U'; j++; }
                    else if ( wcampo.charAt( i ) == 'œ' ) { cpo[j] = 'I'; j++; }
                    else if ( wcampo.charAt( i ) == 'Œ' ) { cpo[j] = 'I'; j++; }
                    else if ( wcampo.charAt( i ) == '¿' ) { cpo[j] = 'A'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ì' ) { cpo[j] = 'i'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ô' ) { cpo[j] = 'i'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ó' ) { cpo[j] = 'i'; j++; }
                    else if ( wcampo.charAt( i ) == 'Õ' ) { cpo[j] = 'I'; j++; }
                    else if ( wcampo.charAt( i ) == 'Û' ) { cpo[j] = 'o'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ù' ) { cpo[j] = 'o'; j++; }
                    else if ( wcampo.charAt( i ) == 'ı' ) { cpo[j] = 'o'; j++; }
                    else if ( wcampo.charAt( i ) == '”' ) { cpo[j] = 'O'; j++; }
                    else if ( wcampo.charAt( i ) == '‘' ) { cpo[j] = 'O'; j++; }
                    else if ( wcampo.charAt( i ) == 'È' ) { cpo[j] = 'e'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ë' ) { cpo[j] = 'e'; j++; }
                    else if ( wcampo.charAt( i ) == 'Í' ) { cpo[j] = 'e'; j++; }
                    else if ( wcampo.charAt( i ) == '‡' ) { cpo[j] = 'a'; j++; }
                    else if ( wcampo.charAt( i ) == '‚' ) { cpo[j] = 'a'; j++; }
                    else if ( wcampo.charAt( i ) == 'Î' ) { cpo[j] = 'e'; j++; }
                    else if ( wcampo.charAt( i ) == '»' ) { cpo[j] = 'E'; j++; }
                    else if ( wcampo.charAt( i ) == '…' ) { cpo[j] = 'E'; j++; }
                    else if ( wcampo.charAt( i ) == ' ' ) { cpo[j] = 'E'; j++; }
                    else if ( wcampo.charAt( i ) == 'À' ) { cpo[j] = 'E'; j++; }
                    else if ( wcampo.charAt( i ) == 'Á' ) { cpo[j] = 'c'; j++; }
                    else if ( wcampo.charAt( i ) == 'á' ) { cpo[j] = 'c'; j++; }
                    else if ( wcampo.charAt( i ) == '«' ) { cpo[j] = 'C'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ä' ) { cpo[j] = 'C'; j++; }
                    else if ( wcampo.charAt( i ) == 'ß' ) { cpo[j] = '.'; j++; }
                    else if ( wcampo.charAt( i ) == '∞' ) { cpo[j] = '.'; j++; }
                    else if ( wcampo.charAt( i ) == '’' ) { cpo[j] = 'O'; j++; }
                    else if ( wcampo.charAt( i ) == 'Ú' ) { cpo[j] = 'o'; j++; }
                    else if ( wcampo.charAt( i ) == '√' ) { cpo[j] = 'A'; j++; }
                    else if ( wcampo.charAt( i ) == '¬' ) { cpo[j] = 'A'; j++; }
                    else if ( wcampo.charAt( i ) == '„' ) { cpo[j] = 'a'; j++; }
                    else if ( wcampo.charAt( i ) == '∫' ) { cpo[j] = '.'; j++; }
                    else { cpo[j] = wcampo.charAt( i ); j++; }
            }
            String resultado = "";
            for (int i=0;i<j;i++)
            {
                    resultado = resultado + cpo[i];
           }
            return resultado;
        } else {
            return "";
        }
    }

     private static final char[] HEXAS = "0123456789ABCDEF".toCharArray();

     // converte um byte para uma String hexadecimal
     public static final String toHexa(final byte bits) {
         final int lo = bits & 0xF;
         final int hi = bits >>> 4 & 0xF;
         return String.valueOf(HEXAS[hi]) + String.valueOf(HEXAS[lo]);
     }

     // converte um array de byte para uma String hexadecimal
     public static final String toHexaString(final byte[] bytes) {
         final StringBuffer buffer = new StringBuffer();
         final int length = bytes.length;
         for (int i = 0; i < length; i++) {
             buffer.append(toHexa(bytes[i]));
         }
         return buffer.toString();
     }

     // converte uma String hexadecimal para um byte
     public static final byte toByte(final String hexa) {
         return (byte) Short.parseShort(hexa.toUpperCase(), 16);
     }

     // converte uma String hexadecimal para um array de byte
     public static final byte[] toByteArray(final String hexaString) {
         final char[] chars = hexaString.toCharArray();
         final int length = chars.length;
         final byte[] byteArray = new byte[length / 2];
         String hexa = null;
         for (int i = 0, j = 0; i < length; i += 2, j++) {
             hexa = new String(new char[] {chars[i], chars[i + 1]});
             byteArray[j] = toByte(hexa);
         }
         return byteArray;
     }
    
     public static java.util.Date  adicionarDias(Date data, int dias){
        //JOptionPane.showMessageDialog(null, "Adicionando dias ["+dias+"] para a Data: "+DateFormat.getDateInstance().format(data));
         //Passe ela para um java.util.Calendar
         Date utilData = null;
         Calendar c = null;
         try {
        c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, dias);
        //utilData = (Date) c.getTime();
        //JOptionPane.showMessageDialog(null, "Adicionando dias . . .");
         } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Metodo: Rotinas.adicionarDias() - erro: "+e);
         }
        return c.getTime();
      }
//Par‚metros:
/**
 *  1 - Valor a arredondar.
 *  2 - Quantidade de casas depois da vÌrgula.
 *  3 - Arredondar para cima ou para baixo?
 *          Para cima = 0 (ceil)
 *          Para baixo = 1 ou qualquer outro inteiro (floor)
 **/
    public static double arredondar(double valor, int casas, int ceilOrFloor) {
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (ceilOrFloor == 0) {
            arredondado = Math.ceil(arredondado);
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

	   private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	   private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	 
	   private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	   }
	 
	   public static boolean isValidCPF(String cpf) {
	      if ((cpf==null) || (cpf.length()!=11)) return false;
	 
	      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
	      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
	      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	   }
	 
	   public static boolean isValidCNPJ(String cnpj) {
	      if ((cnpj==null)||(cnpj.length()!=14)) return false;
	 
	      Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
	      Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
	      return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	   }
	 
    private static final String PLAIN_ASCII =
      "AaEeIiOoUu"    // grave
    + "AaEeIiOoUuYy"  // acute
    + "AaEeIiOoUuYy"  // circumflex
    + "AaOoNn"        // tilde
    + "AaEeIiOoUuYy"  // umlaut
    + "Aa"            // ring
    + "Cc"            // cedilla
    + "OoUu"          // double acute
    + "xyz"             // simbolo de grau
    ;

    private static final String UNICODE =
     "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
    + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
    + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
    + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
    + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
    + "\u00C5\u00E5"
    + "\u00C7\u00E7"
    + "\u0150\u0151\u0170\u0171"
    + "\u00B0\u00BA\u25E6"
    ;

    // remove accentued from a string and replace with ascii equivalent
    public static String convertNonAscii(String s) {
       if (s == null) return null;
       StringBuilder sb = new StringBuilder();
       int n = s.length();
       for (int i = 0; i < n; i++) {
          char c = s.charAt(i);
          int pos = UNICODE.indexOf(c);
          if (pos > -1){
              sb.append(PLAIN_ASCII.charAt(pos));
          }
          else {
              sb.append(c);
          }
       }
       return sb.toString();
    }
    public static Long compararDatas(String data1,String data2){
     SimpleDateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
     Date d1 = null;
     Date d2 = null;
     try {
            d1 = df.parse (data1);
            d2 = df.parse (data2);
      } catch (java.text.ParseException evt ) {}

      long dt = (d2.getTime() - d1.getTime()) + 3600000;
      long dias = (dt / 86400000L) + 1;

      return dias;
    }
    public static String checkDataInformada(String data, String anomesdia){
        String data_return = "", wano, wmes, wdia;
        int wdiaint, wmesint, wanoint;
        wano = data.substring(6,10);
        wmes = data.substring(3,5);
        wdia = data.substring(0,2);
        System.out.println("ano: "+wano+" - mes: "+wmes+" - dia: "+wdia);
        data_return = wano+wmes+wdia;
        String erro = "";
        try {
            Integer.parseInt(data_return);
            wdiaint = Integer.parseInt(wdia);
            wmesint = Integer.parseInt(wmes);
            wanoint = Integer.parseInt(wano);
            // valida ano
            if (wanoint<Integer.parseInt(anomesdia.substring(0,4))-5 || wanoint>Integer.parseInt(anomesdia.substring(0,4))){
                erro = erro+"Ano inv·lido!\n";
            }
            // valida mes
            if (wmesint<1 || wmesint>12){
                erro = erro+"Mes inv·lido!\n";
            }
            // valida dia
            int tabmes[] = new int[12];
            int tmes[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            for (int i=0;i<12;i++){
                if (i == 1){  // i = 1 significa mes = 02
                    if ((wanoint%4) == 0){  // ano bissexto, fevereiro tem 29 dias
                        tabmes[i] = 29;
                    } else { //
                        tabmes[i] = tmes[i];
                    }
                } else {
                    tabmes[i] = tmes[i];
                }
            }
            if (wdiaint == 0 ){
               erro = erro+"Dia inv·lido!\n";
            } else {
                if (wmesint>=1 && wmesint<=12){
                    if (wdiaint > tabmes[wmesint-1]){
                        erro = erro+"Dia inv·lido!\n";
                    }
                }
            }
        } catch (NumberFormatException e){
            erro = erro+"O data informada n„o est· no formato valido [dd/mm/aaaa] !\n";
        }
        //  se houve erro, mostrar o erro
        if (!erro.equals("")){
           JOptionPane.showMessageDialog(null, erro);
           data_return = "erro";
        }
        return data_return;
    }
    public static String formataTexto(String txt){
            String retorno = "";
             txt = txt.replaceAll("&#xd;", "");
             //System.out.println("\n\nTexto fim: "+txt);
             normalizeXML(txt);
            while (txt.indexOf(" <") != -1){
                    txt = txt.replaceAll(" <", "<");
            }
            while (txt.indexOf("\n") != -1){
                    txt = txt.replaceAll("\n", "");
            }
            while (txt.indexOf("\r") != -1){
                    txt = txt.replaceAll("\r", "");
            }
            retorno = txt;
            return retorno;
        }
    public static char tipoImpressao(){
        char tipoImp = 'V';
        UIManager.put("OptionPane.yesButtonText", "Visualizar na Tela");
        UIManager.put("OptionPane.noButtonText", "Imprimir em Papel");
        int escolha = JOptionPane.showConfirmDialog(null, "Escolha uma das Opcoes Abaixo", "Impress„o do RelatÛrio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if ( escolha == JOptionPane.YES_OPTION)
            tipoImp = 'V';
        else if ( escolha == JOptionPane.NO_OPTION)
            tipoImp = 'I';
        else
            tipoImp = ' ';
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "N„o");

        return tipoImp;
    }

    public static boolean verSeEhNumero(String valor) {
        boolean retorno = true;
        try {
            Integer.parseInt(valor);
        } catch(Exception e) {
            retorno = false;
        }
        return retorno;
    }

}
