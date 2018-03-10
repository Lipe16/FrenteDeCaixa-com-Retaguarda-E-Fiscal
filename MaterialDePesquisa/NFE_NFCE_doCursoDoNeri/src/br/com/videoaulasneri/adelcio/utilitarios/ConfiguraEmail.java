/*

DescriÁ„o: ManutenÁ„o da ConfiguraÁ„o dos Dados para o Envio de Email do XML ao Destinat·rio

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import br.com.videoaulasneri.adelcio.fatura.bean.Email;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConfiguraEmail  extends JFrame implements ActionListener{
    JButton botaoSair, botaoConfirma;
    JLabel lb_hostName, lb_nomeRemet, lb_emailRemet, lb_senhaEmail, lb_portaEmail, lb_ssl;
    JLabel lb_ajuda0, lb_ajuda1, lb_ajuda2, lb_ajuda4, lb_ajuda5, lb_ajuda6;
    JTextField tf_hostName, tf_nomeRemet, tf_emailRemet, tf_portaEmail, tf_ssl;
    JPasswordField tf_senhaEmail;
    Email obj_confEmail =  new Email();
    String arquivo = "";
    boolean vlrSSL = false;

    public ConfiguraEmail(String arq){
        super("Configuracoes do email do Remetente");
        this.arquivo = arq;
        setSize(600, 450);
        setLayout( new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(new Color(210, 210, 210));
        getContentPane()        .setLayout(null);
        initComponents();
        le_configEmail(arquivo);
        show();
    }

    public void initComponents(){
    // definicao dos labels e textfields
        lb_hostName         = new JLabel("Servidor de Envio(hostname):");
        lb_hostName         .setBounds(10, 30, 190, 25);
        lb_hostName         .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_hostName );

        tf_hostName         = new JTextField();
        tf_hostName         .setBounds(220, 30, 350, 25);
        getContentPane()    .add(tf_hostName );

        lb_ajuda0           = new JLabel("Ex: smtp.terra.com.br / smtp.gmail.com / smtps.sercomtel.com.br");
        lb_ajuda0           .setBounds(220,50, 350, 20);
        lb_ajuda0           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda0           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda0);
//-------------
        lb_nomeRemet        = new JLabel("Nome do Remetente:");
        lb_nomeRemet        .setBounds(50, 90, 150, 25);
        lb_nomeRemet        .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_nomeRemet);

        tf_nomeRemet        = new JTextField();
        tf_nomeRemet        .setBounds(220, 90, 350, 25);
        getContentPane()    .add(tf_nomeRemet);

        lb_ajuda1           = new JLabel("Ex: Antonio,  Carlos,  Mario, . . .");
        lb_ajuda1           .setBounds(220,110, 350, 20);
        lb_ajuda1           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda1           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda1);
//-------------
        lb_emailRemet       = new JLabel("Email do Remetente:");
        lb_emailRemet       .setBounds(50, 150, 150, 25);
        lb_emailRemet       .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_emailRemet);

        tf_emailRemet       = new JTextField();
        tf_emailRemet       .setBounds(220, 150, 350, 25);
        getContentPane()    .add(tf_emailRemet);

        lb_ajuda2           = new JLabel("Ex: contatoXYZ@empresa.com.br");
        lb_ajuda2           .setBounds(220, 170, 350, 20);
        lb_ajuda2           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda2           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda2);

//-------------
//-------------
        lb_senhaEmail       = new JLabel("Senha do Email:");
        lb_senhaEmail       .setBounds(50, 210, 150, 25);
        lb_senhaEmail       .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_senhaEmail);

        tf_senhaEmail       = new JPasswordField();
        tf_senhaEmail       .setBounds(220, 210, 350, 25);
        getContentPane()    .add(tf_senhaEmail);

        lb_ajuda4           = new JLabel("Ex: senha do email");
        lb_ajuda4           .setBounds(220, 230, 350, 20);
        lb_ajuda4           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda4           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda4);

        lb_portaEmail       = new JLabel("Porta do Email:");
        lb_portaEmail       .setBounds(50, 270, 150, 25);
        lb_portaEmail       .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_portaEmail);

        tf_portaEmail       = new JTextField();
        tf_portaEmail       .setBounds(220, 270, 350, 25);
        getContentPane()    .add(tf_portaEmail);

        lb_ajuda5           = new JLabel("Ex: 465(p/gmail), 587(p/sercomtel), etc");
        lb_ajuda5           .setBounds(220, 290, 350, 20);
        lb_ajuda5           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda5           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda5);

        lb_ssl              = new JLabel("SSL:");
        lb_ssl              .setBounds(50, 330, 150, 25);
        lb_ssl              .setHorizontalAlignment(JLabel.RIGHT);
        getContentPane()    .add(lb_ssl);

        tf_ssl              = new JTextField();
        tf_ssl              .setBounds(220, 330, 350, 25);
        getContentPane()    .add(tf_ssl);

        lb_ajuda6           = new JLabel("Informe V para verdadeiro ou F para falso");
        lb_ajuda6           .setBounds(220, 350, 350, 20);
        lb_ajuda6           .setFont(new Font("Arial",Font.BOLD, 10));
        lb_ajuda6           .setForeground(Color.BLUE);
        getContentPane()    .add(lb_ajuda6);

        //definicao dos botoes
        botaoConfirma       = new JButton("Confirma");
        botaoConfirma       .addActionListener(this);
        botaoConfirma       .setBounds(180, 380, 100, 25);
        getContentPane()    .add(botaoConfirma);

        botaoSair           = new JButton("Sair");
        botaoSair           .addActionListener(this);
        botaoSair           .setBounds(330, 380, 100, 25);
        getContentPane()    .add(botaoSair);
    }
    public void atualiza_configEmail(String arqcfg){
        if (tf_ssl.getText() == null) {
            JOptionPane.showMessageDialog(null, "Um valor V ou F precisa ser informado para SSL");
        } else if (tf_ssl.getText().equals("V") || tf_ssl.getText().equals("v")) {
            vlrSSL = true;
        } else if (tf_ssl.getText().equals("F") || tf_ssl.getText().equals("f")) {
            vlrSSL = false;
        } else {
            JOptionPane.showMessageDialog(null, "Um valor V ou F precisa ser informado para SSL");
        }
          obj_confEmail.setHostName(tf_hostName.getText());;
          obj_confEmail.setNomeRemet(tf_nomeRemet.getText());;
          obj_confEmail.setEmailRemet(tf_emailRemet.getText());
          obj_confEmail.setSenhaEmail(tf_senhaEmail.getText());
          obj_confEmail.setPortaEmail(tf_portaEmail.getText());
          obj_confEmail.setSsl(vlrSSL);

          try
          {
             FileOutputStream arquivo = new FileOutputStream (arqcfg);
             ObjectOutputStream  obj_dados  = new ObjectOutputStream(arquivo);
             obj_dados.writeObject(obj_confEmail);
             obj_dados.flush();
             obj_dados.close();
             JOptionPane.showMessageDialog(null, "Configuracoes do email atualizadas com sucesso!");
           }
          catch(Exception erro_grava)
          {
             JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar as Configuracoes do email! Erro: "+erro_grava);
          }

    }
    public void le_configEmail(String arqcfg)
    {
         try {
             //JOptionPane.showMessageDialog(null, "Arquivo de config.de email: "+arqcfg);
            FileInputStream arquivo         = new FileInputStream (arqcfg);
            ObjectInputStream  obj_dados    = new ObjectInputStream(arquivo);
            Email obj_leitura         = (Email)obj_dados.readObject();
            tf_hostName                     .setText(obj_leitura.getHostName());
            tf_nomeRemet                    .setText(obj_leitura.getNomeRemet());
            tf_emailRemet                   .setText(obj_leitura.getEmailRemet());
            tf_senhaEmail                   .setText(obj_leitura.getSenhaEmail());
            tf_portaEmail                   .setText(obj_leitura.getPortaEmail());
            vlrSSL = obj_leitura.isSsl();
            if (vlrSSL) {
                tf_ssl.setText("V");
            } else {
                tf_ssl.setText("F");
            }
            
         } catch (Exception erro_ler) {
//             atualiza_configEmail(arqcfg);
             //JOptionPane.showMessageDialog(null, "O arquivo: "+arqcfg+" ainda n√£o existe e ser√° \ncriado ap√≥s o preenchimento dos campos e a confirmac√£o!");
         }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoSair) {
            //System.out.println("Clicou no Botao Sair . . .");
            dispose();
        } else if (e.getSource() == botaoConfirma) {
           //System.out.println("Clicou no Botao Confirma . . .");
            if (vericpo()) {
                atualiza_configEmail(arquivo);
                dispose();
            }
        }
   }
    public boolean vericpo(){
        if (
            tf_hostName.getText().equals("") ||
            tf_emailRemet.getText().equals("") ||
            tf_nomeRemet.getText().equals("") ||
            tf_senhaEmail.getText().equals("") ||
            tf_portaEmail.getText().equals("")
        ) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!");
            return false;
        }
        return true;
    }
    public static void main(String args[]){
        JFrame config = new ConfiguraEmail("c:\\configEmail.cfg");
        config.setVisible(true);
    }
}
