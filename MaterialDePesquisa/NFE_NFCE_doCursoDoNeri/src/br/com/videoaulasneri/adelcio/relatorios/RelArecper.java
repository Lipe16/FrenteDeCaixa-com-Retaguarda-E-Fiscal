/*

Descrição: 

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.relatorios;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class RelArecper extends JFrame implements ActionListener{
    JLabel lb_dataini                = new JLabel("Data Inicial: ");
    JLabel lb_datafim                = new JLabel("Data Final..: ");
    JFormattedTextField tf_dataini;
    JFormattedTextField tf_datafim;
    JLabel lb_formato                = new JLabel("Formato da Data: dd/mm/aaaa ");
    JButton botao_Ok                 = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/print.gif")));
    JButton botao_Cancel             = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/sair.gif")));
    String fsep = System.getProperty("file.separator");
    String drive = "c:\\nfe";
    String emp4dig = "0002", txtErro = "";
    //conexao conn;
    Connection confat;
    int empresa;
    String sempresa = "";
    String nomeBanco, razaoEmp, enderEmp, ambiente;
    Date dateIni, dateFim;
    public RelArecper(int empresa, Connection conex, String razaoEmpresa, String enderEmpresa, String ambiente){
        try {
            this.empresa = empresa;
            this.confat = conex;
            this.razaoEmp = razaoEmpresa;
            this.enderEmp = enderEmpresa;
            this.ambiente = ambiente;
            tf_dataini = new JFormattedTextField(Mascara("##/##/####"));
            tf_datafim = new JFormattedTextField(Mascara("##/##/####"));
            initComponents();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "RelArecper() - erro: "+e);
        }
    }

    private MaskFormatter Mascara(String Mascara){
            MaskFormatter F_Mascara = new MaskFormatter();
            try{
                F_Mascara.setMask(Mascara); //Atribui a mascara
                F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento 
            }
            catch (Exception excecao) {
            excecao.printStackTrace();
            } 
            return F_Mascara;
     }
    private void initComponents(){
        try {
            setTitle("Impressão A Receber no Período");
            setSize(400, 240);
            setLocation(400,250);  //(135,85);
            setResizable(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.LIGHT_GRAY);
            getContentPane()        .setLayout(null);
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.noButtonText", "Não");
             sempresa = ""+empresa;
             for (int i=(""+empresa).length();i<4;i++){
                 sempresa="0"+sempresa;
             }

            lb_dataini            .setBounds(045, 025, 100, 20);
            tf_dataini            .setBounds(120, 022,  90, 25);
            lb_datafim            .setBounds(045, 062, 100, 20);
            tf_datafim            .setBounds(120, 057,  90, 25);
            lb_formato            .setBounds(045, 112, 250, 20);
            botao_Ok              .setToolTipText("Imprime o Periodo");
            botao_Cancel          .setToolTipText("Desiste da Impressão");
            botao_Ok              .setBounds(120, 170, 040, 30);
            botao_Cancel          .setBounds(180, 170, 040, 30);
            tf_dataini            .setFont(new Font("Arial",Font.BOLD,14));
            tf_datafim            .setFont(new Font("Arial",Font.BOLD,14));
            //tf_dataini.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
            //tf_datafim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

            lb_formato            .setForeground(Color.red);
            lb_formato            .setFont(new Font("Arial",Font.BOLD,14));
            //rb_todos              .addActionListener(this);
            //rb_especiais          .addActionListener(this);
            botao_Ok              .addActionListener(this);
            botao_Cancel          .addActionListener(this);

            getContentPane()      .add(lb_dataini);
            getContentPane()      .add(tf_dataini);
            getContentPane()      .add(lb_datafim);
            getContentPane()      .add(tf_datafim);
            getContentPane()      .add(lb_formato);
            getContentPane()      .add(botao_Ok);
            getContentPane()      .add(botao_Cancel);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "RelArecper().initComponents() - erro: "+e);
        }
    }
      public void actionPerformed(ActionEvent acao)
      {
            if (acao.getSource() == botao_Ok){
                System.out.println("Pressionou OK . . .");
                //if (checkFields()){
              if (checkDatas(tf_dataini, tf_datafim)){
                 imprime_areceber();
              } else {
                    JOptionPane.showMessageDialog(null, "Erros Encontrados: \n"+txtErro);
                }
            }
            if (acao.getSource() == botao_Cancel){
                System.out.println("Pressionou Cancel . . .");
                this.dispose();
            }
    }
      private boolean checkDatas(JTextField data1, JTextField data2){
            if (data1.getText().equals("") || data2.getText().equals("")) {
                //JOptionPane.showMessageDialog(null, "As Datas Inicial e Final de "+tipo+" devem ser informadas!");
                txtErro = txtErro + "\n"+"As Datas Inicial e Final devem ser informadas!";
                return false;
            } else {
                String datastr = "";
                if (data1.getText().indexOf("/") == -1 && data1.getText().length() == 8) {
                    datastr = data1.getText();
                    data1.setText(datastr.substring(0,2)+"/"+datastr.substring(2,4)+"/"+datastr.substring(4,8));
                    datastr = "";
                }
                if (data2.getText().indexOf("/") == -1 && data2.getText().length() == 8) {
                    datastr = data2.getText();
                    data2.setText(datastr.substring(0,2)+"/"+datastr.substring(2,4)+"/"+datastr.substring(4,8));
                    datastr = "";
                }
                Date dataTst1 = null, dataTst2 = null;
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dataTst1 = f.parse(data1.getText());
                    dataTst2 = f.parse(data2.getText());
                    if (dataTst2.before(dataTst1)) {
                        //JOptionPane.showMessageDialog(null, "Data Final de "+tipo+" não pode ser Menor que Data Inicial!");
                        txtErro = txtErro + "\n"+"Data Final não pode ser Menor que Data Inicial!";
                        return false;
                    } else
                        return true;
                } catch (ParseException ex) {
                    //JOptionPane.showMessageDialog(null, "Data de "+tipo+" no formato invalido!");
                    txtErro = txtErro + "\n"+"Data de no formato invalido!";
                    return false;
                }
            }
      }
      private boolean checkFields(){
        if (tf_dataini.getText().equals("") || tf_datafim.getText().equals("")) {
            JOptionPane.showMessageDialog(null, " As Datas Inicial e Final devem ser informadas!");
            return false;
        } else {
            String datastr = "";
            Date dataTst1 = null, dataTst2 = null;
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dataTst1 = f.parse(tf_dataini.getText());
                dataTst2 = f.parse(tf_datafim.getText());
                if (dataTst2.before(dataTst1)) {
                    JOptionPane.showMessageDialog(null, "Data Final não pode ser Menor que Data Inicial!");
                    return false;
                } else
                    return true;
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Data no formato inválido!");
                return false;
            }
          }
      }
     private void imprime_areceber(){
        try{
            HashMap parameterMap = new HashMap();
            //String report_dir = drive+fsep+"dados"+fsep+"empr"+emp4dig+fsep+"relatorio"+fsep;
            String report_dir = System.getProperty("user.dir")+fsep+"Relatorios"+fsep;
            String arquivo = report_dir+"areceber.jasper"; 
//JOptionPane.showMessageDialog(null, "Arquivo Jasper: "+arquivo);
            boolean retorno = true;
            if (!convertData(tf_dataini.getText(), 1)) {
                JOptionPane.showMessageDialog(null, "Data inválida! Data: "+ tf_dataini.getText()+" - Formato: dd/mm/aaaa");
                retorno = false;
            }
            if (!convertData(tf_datafim.getText(), 2)) {
                JOptionPane.showMessageDialog(null, "Data inválida! Data: "+ tf_datafim.getText()+" - Formato: dd/mm/aaaa");
                retorno = false;
            }
            if (retorno) {
                parameterMap.put("data_ini", dateIni);
                parameterMap.put("data_fim", dateFim);
                parameterMap.put("empresa", razaoEmp);
                parameterMap.put("endereco", enderEmp);
                parameterMap.put("tipoAmbiente", ambiente);
                parameterMap.put("ambiente", ambiente == "1" ? "1 - Produção" : " 2 - Homologação");
//                parameterMap.put("SUBREPORT_DIR", report_dir);
    //JOptionPane.showMessageDialog(null, "Data ini: "+tf_dataini.getText()+" - Data Fim: "+tf_datafim.getText()+" - Pasta: "+report_dir+" - Empresa: "+razaoEmp);
                JasperPrint jp = JasperFillManager.fillReport(arquivo, parameterMap, confat);
    //JOptionPane.showMessageDialog(null, "Executou JasperPrint . . . ");

                 //exibe o relatório com viewReport
                JasperViewer.viewReport(jp, false);
//JOptionPane.showMessageDialog(null, "Executou ViewReport . . . ");
            }
        } catch (JRException ejr) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar imprimir o relatorio.jasper. Erro(2): "+ ejr);
            ejr.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar imprimir o relatorio.jasper. Erro(3): "+ e);
        }
    }
     private boolean convertData(String data, int seq) {
         boolean retorno = true;
         int[] tabdia = new int[13];
         tabdia[1] = 31;
         tabdia[2] = 28;
         tabdia[3] = 31;
         tabdia[4] = 30;
         tabdia[5] = 31;
         tabdia[6] = 30;
         tabdia[7] = 31;
         tabdia[8] = 31;
         tabdia[9] = 30;
         tabdia[10] = 31;
         tabdia[11] = 30;
         tabdia[12] = 31;
         try {
             if (data == null || data.length() < 10) {
                 retorno = false;
             } else {
                int dia = Integer.parseInt(data.substring(0,2));
                int mes = Integer.parseInt(data.substring(3,5));
                int ano = Integer.parseInt(data.substring(6,10));
                if (ano < 2016 || ano > 2030) {
                    retorno = false;
                } else {
                    if (mes < 1 || mes > 12) {
                        retorno = false;
                    } else {
                        if (mes == 2 && ano%4 == 0 && ano%100 != 0) {
                            tabdia[2] = 29;
                        }
                        if (dia < 1 || dia > tabdia[mes]) {
                            retorno = false;
                        } else {
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            if (seq == 1) {
                                dateIni = (Date) formatter.parse(data);
                            } else {
                                dateFim = (Date) formatter.parse(data);
                            }
                        }
                     }
                 }
             }
         } catch (ParseException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao tentar imprimir o relatorio.jasper. Erro(1): "+ ex);
             retorno = false;
         }
         return retorno;
     }
    public static void main(String args[]) throws SQLException
        {
        //Relfatcli rfat = new Relfatcli();
        //rfat.alteraCpoTipocli();
        JFrame relfat = new RelArecper(2, null, "", "", "2");
        relfat.setVisible(true);
    }

}
