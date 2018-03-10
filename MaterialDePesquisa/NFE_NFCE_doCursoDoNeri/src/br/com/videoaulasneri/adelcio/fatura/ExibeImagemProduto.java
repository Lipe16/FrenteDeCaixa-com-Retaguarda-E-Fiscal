/*

Descrição: Esta classe é responsável por exibir as imagens dos Produtos informados na Tabela de Produtos

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.fatura;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Porto
 */
public class ExibeImagemProduto extends JFrame  implements ActionListener {
    JLabel lb_titulo = new JLabel("Imagem do Produto");
    String imagem = "";
    JButton botao_sair = new JButton(new javax.swing.ImageIcon(getClass().getResource("/imagens/sair.gif")));
    JLabel lb_icon_prod;
    ImageIcon icon_prod;
    int index = 0;
    String fsep = System.getProperty("file.separator");

    public ExibeImagemProduto(int index, int totIndex, String imagem)
    {
        this.index = index;
        this.imagem = imagem;
        int posH = 0 + ((totIndex -index) * 15);
        int posV = 0 + ((totIndex -index) * 10);
        setTitle("Imagem do Produto");
        setSize(1200, 800);
        setLocation(posH, posV);
        setResizable(true);

        //getContentPane()      .add(lb_titulo);
        icon_prod             = new ImageIcon("");
        //lb_icon_prod          .setBackground(Color.WHITE);
        //lb_icon_prod          .setForeground(Color.black);
        lb_icon_prod          = new JLabel(icon_prod);
        lb_icon_prod          .setBounds(10, 10, 1100, 790);
        getContentPane()      .add(lb_icon_prod);

        botao_sair            .setBackground(Color.red);
        botao_sair            .setBounds(10,10,40,30);
        botao_sair            .setToolTipText("Voltar a Tela Anterior");
        botao_sair            .addActionListener(this);
        getContentPane()      .add(botao_sair);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lookandfeel();
        exibeImagem();
    }
   public void lookandfeel()
   {
        try
        {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception erro)
        {
            JOptionPane.showMessageDialog(null, "N??o conseguiu setar o novo LookAndFeel!!!");
        }
    }
    public void actionPerformed(ActionEvent acao) {
        if (acao.getSource() == botao_sair) {
                this.dispose();
            
        }
    }
    private void exibeImagem() {
        //if (this.imagem == null || this.imagem.length() == 0) {
        //   lb_icon_prod.setIcon(new ImageIcon(System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem+".jpg"));  //("Imagens//produtos//semfoto.jpg"));

        //} else {
//            String urlName = this.imagem;  //"https://db-image-upload.s3.amazonaws.com/produtos/60910/550/6.jpg";
              try {
//                 URL url = new URL(urlName);
//                 HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                 urlConnection.disconnect();

//                Image img = null;
//                img = ImageIO.read(url);
//                lb_icon_prod.setIcon(new ImageIcon(img));
//                                    File f = new File(System.getProperty("user.dir")+fsep+"imagens"+fsep+foto);
//                                    Image image = ImageIO.read(f);
//                                    jLFoto.setIcon(new ImageIcon(image.getScaledInstance(jLFoto.getWidth(),jLFoto.getHeight(), Image.SCALE_DEFAULT)));
                  System.out.println("Imagem: "+System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem);
                  lb_icon_prod.setIcon(new ImageIcon(System.getProperty("user.dir")+fsep+"imagens"+fsep+imagem));  //("Imagens//produtos//semfoto.jpg"));

//              } catch (MalformedURLException e){
//                 JOptionPane.showMessageDialog(null,"Erro ao tentar exibir a imagem. Formato inválido.");
//                 //System.exit(1);
              } catch (Exception e2) {
                 JOptionPane.showMessageDialog(null,"Erro ao acessar a imagem.");
                 //System.exit(1);
              }
        //}
    }
    public static void main(String args[])
        {
        JFrame form = new ExibeImagemProduto(0, 1, null);
            form.setVisible(true);
    }

}
