/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.mvc.controller;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Guilherme
 */

public class Email {
  private static final String SMTP_HOST_NAME = "smtp.dominio";
  private static final String SMTP_AUTH_USER = "teste@dominio";
  private static final String SMTP_AUTH_PWD  = "senha";

  private static String emailMsgTxt      = "Controle de Mensagens via Servlet - JAVA.";
  private static String emailSubjectTxt  = "Subject da Mensagem";
  private static String emailFromAddress = "teste@dominio";

  // Incluindo a lista de e-mails que receberão as mensagens

  private static final String[] emailList = {"destino@dominio", "segundodestino@dominio"};
  

  public void postMail( String recipients[ ], String subject,
                            String message , String from) throws MessagingException
  {

    boolean debug = false;

     //Inserindo os dados de SMTP
     Properties props = new Properties();
     props.put("mail.smtp.host", SMTP_HOST_NAME);
     props.put("mail.smtp.auth", "true");

    Authenticator auth = new SMTPAuthenticator();
    Session session = Session.getDefaultInstance(props, auth);

    session.setDebug(debug);

    // Criando a Mensagem
    Message msg = new MimeMessage(session);

    // Configurando o from and para endereco
    InternetAddress addressFrom = new InternetAddress(from);
    msg.setFrom(addressFrom);

    InternetAddress[] addressTo = new InternetAddress[recipients.length];

    for (int i = 0; i < recipients.length; i++)

    {
        addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);


    // Configurando o  Subject e o  Content Type

    msg.setSubject(subject);
    msg.setContent(message, "text/plain");
    Transport.send(msg);

 }

/**

* Autenticacao simples

* Caso o servidor solicite

*/

private class SMTPAuthenticator extends javax.mail.Authenticator

{
    public PasswordAuthentication getPasswordAuthentication()

    {

        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);

    }

}
}
