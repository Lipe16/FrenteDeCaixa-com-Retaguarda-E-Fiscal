/*

Descrição: Classe Responsável pelo envio do email do XML para o Destinatário

Autor: Videoaulasneri - email: videoaulaneri@gmail.com   - Fone: (54) 3329-5400
     e Adelcio Porto  - email: portoinfo@sercomtel.com.br - Fone: (43) 99994-6037
  
*/

package br.com.videoaulasneri.adelcio.utilitarios;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class EnviaEmail {
    String hostName, nomeRemet, emailRemet,nomeDest, emailDest, subject, 
            textoEmail, anexo, nomeEmail, senhaEmail, nomeArq;
    int porta;
    boolean ssl;
    public EnviaEmail(
            String hostName,
            String nomeRemet,
            String emailRemet,
            String nomeDest,
            String emailDest,
            String subject,
            String textoEmail,
            String anexo,
            String nomeEmail,
            String senhaEmail,
            String nomeArq,
            int porta,
            boolean ssl
        ) throws MalformedURLException, EmailException {
        this.hostName   = hostName;
        this.nomeRemet  = nomeRemet;
        this.emailRemet = emailRemet;
        this.nomeDest   = nomeDest;
        this.emailDest  = emailDest;
        this.subject    = subject;
        this.textoEmail = textoEmail;
        this.anexo      = anexo;
        this.nomeEmail  = nomeEmail;
        this.senhaEmail = senhaEmail;
        this.nomeArq    = nomeArq;
        this.porta      = porta;
        this.ssl        = ssl;
//        enviaEmailSimples();
        enviaEmailComAnexo();
    }

    /**
     * envia email simples(somente texto)
     * @throws EmailException
     */
    public void enviaEmailSimples() throws EmailException {

        SimpleEmail email = new SimpleEmail();
        email.setHostName(hostName); // o servidor SMTP para envio do e-mail
        email.addTo(emailDest, nomeDest); //destinatario
        email.setFrom(emailRemet, nomeRemet); // remetente
        email.setSubject(subject); // assunto do e-mail
        email.setMsg(textoEmail); //conteudo do e-mail
        email.setAuthentication(nomeEmail, senhaEmail);
        email.setSmtpPort(porta);
        email.setSSL(ssl);
        email.setTLS(false);
        email.send();
    }


    /**
     * envia email com arquivo anexo
     * @throws EmailException
     */

    public void enviaEmailComAnexo() throws EmailException {
        try {
            // cria o anexo 1.
            EmailAttachment anexo1 = new EmailAttachment();
            anexo1.setPath(anexo);
            anexo1.setDisposition(EmailAttachment.ATTACHMENT);
            anexo1.setDescription("Arquivo xml da NFe");
            anexo1.setName(nomeArq);

            // configura o email
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(hostName); // o servidor SMTP para envio do e-mail
            email.addTo(emailDest, nomeDest); //destinatario
            email.setFrom(emailRemet, nomeRemet); // remetente
            email.setSubject(subject); // assunto do e-mail
            email.setMsg(textoEmail); //conteudo do e-mail
            email.setAuthentication(nomeEmail, senhaEmail);
            email.setSmtpPort(porta);  // gmail = (465);  //  padrao = (25);  //  sercomtel = (587)
            email.setSSL(ssl);
            //email.setTLS(true);
            email.setStartTLSEnabled(false);
            //email.setSSLOnConnect(true);
            email.setStartTLSRequired(false);
//JOptionPane.showMessageDialog(null,  " ssl: " + ssl + " - hostName: "+hostName+
//        " - emailRemet: "+emailRemet+ " - nomeRemet: "+nomeRemet+
//        " \nemailDest: "+emailDest+ " - nomeDest: "+nomeDest+
//        " - nomeEmail: "+nomeEmail+" - senhaEmail: "+senhaEmail+" - portaEmail: "+porta);
            // adiciona arquivo(s) anexo(s)
            email.attach(anexo1);
            // envia o email
            email.send();
            JOptionPane.showMessageDialog(null, "Email enviado com Sucesso!");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar enviar email. Erro: "+e);

        }
    }
/*
public static boolean validaEmail(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return false;
        } else {
            return true;
        }
    }
public static HtmlEmail conectaEmail() throws EmailException, NoSuchProviderException, MessagingException {
        String hostname = "smtp.gmail.com";
        String username = "seu@gmail.com";
        String password = "****";
        String emailOrigem = "seu@gmail.com";
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostname);
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setTLS(true);
        email.setFrom(emailOrigem, "Destinatário");
        email.setDebug(true);
        email.setCharset(HtmlEmail.ISO_8859_1);
        Properties props = new Properties();
        props.setProperty(hostname, "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "" + 587);
        props.setProperty("mail.smtp.starttls.enable", "true");
        Session mailSession = Session.getInstance(props, new DefaultAuthenticator(username, password));
        email.setMailSession(mailSession);
        return email;
}
public static void enviaEmailHtml(String email, String titulo, String texto) {
        try {
            HtmlEmail mensagem = conectaEmail();
            mensagem.addTo(email); 
            mensagem.setSubject(titulo);
            ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File img = new File(sc.getRealPath("") + "/resources/imagens/logoMarca.png");
            mensagem.setHtmlMsg("<html><center><p>"
                    + "<img src=cid:" + mensagem.embed(img) + ">"
                    + texto + "</p></center></html>");
            mensagem.buildMimeMessage();
            Message m = mensagem.getMimeMessage();
            Transport transport = mensagem.getMailSession().getTransport("smtp");
            transport.connect(mensagem.getHostName(), 587, null, null);
            transport.sendMessage(m, m.getAllRecipients());
            System.out.println("E-mail enviado para: " + email);
        } catch (MessagingException ex) {
            Logger.getLogger(FacesUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmailException ex) {
            Logger.getLogger(FacesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
}    
    */

    public static void main(String[] args) throws EmailException, MalformedURLException {
        new EnviaEmail("smtps.sercomtel.com.br", "Porto", "portoinfo@sercomtel.com.br", "Marcilio", "mrcdaluz@terra.com.br", "Teste de envio de e-mail", "Texto do e-mail", "c:/email/cadastro08.gz","portoinfo", "33425492","arquivo", 587, true);
    }
}
