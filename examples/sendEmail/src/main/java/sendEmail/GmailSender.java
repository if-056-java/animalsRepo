package sendEmail;
/**
 * Created by aquarius on 8/7/2015.
 */

    import java.io.IOException;
    import java.io.InputStream;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.Properties;

    import javax.activation.DataHandler;
    import javax.activation.FileDataSource;
    import javax.mail.*;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeBodyPart;
    import javax.mail.internet.MimeMessage;
    import javax.mail.internet.MimeMultipart;


public class GmailSender {

        String subject;
        private String fromEmail;
        private String toEmail;
        private String file;

        public GmailSender(String subject, String toEmail) {
            this.subject = subject;
            this.toEmail = toEmail;
        }

      public void send(){

            Session session = Session.getInstance(MailServerConfig, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MailServerConfig.getProperty("mail.user"), MailServerConfig.getProperty("mail.userpassword"));
                }
            });

            try {
                Message message = new MimeMessage(session);
                // From
                message.setFrom(new InternetAddress(MailServerConfig.getProperty("mail.from")));
                // To
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                // Subject
                message.setSubject(subject);

                // Mail forming
                // Body
                MimeBodyPart p1 = new MimeBodyPart();
                p1.setText("This is part one of a multipart e-mail." +
                        "The second part is file as an attachment");

                Multipart mp = new MimeMultipart();
                mp.addBodyPart(p1);

                // Mail creating
                message.setContent(mp);

                // Mail sending
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    public void send(String file){

        Session session = Session.getInstance(MailServerConfig, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailServerConfig.getProperty("mail.user"), MailServerConfig.getProperty("mail.userpassword"));
            }
        });

        try {
            Message message = new MimeMessage(session);
            // From
            message.setFrom(new InternetAddress(MailServerConfig.getProperty("mail.from")));
            // To
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            // Subject
            message.setSubject(subject);

            // Mail forming
            // Body
            MimeBodyPart p1 = new MimeBodyPart();
            p1.setText("This is part one of a multipart e-mail." +
                    "The second part is file as an attachment");

            // Attachments
            MimeBodyPart p2 = new MimeBodyPart();

            // Adding file
            FileDataSource fds = new FileDataSource(file);
            p2.setDataHandler(new DataHandler(fds));
            p2.setFileName(fds.getName());

            // Mail forming
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(p1);
            mp.addBodyPart(p2);

            // Mail creating
            message.setContent(mp);


            // Mail sending
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



        public static void refreshConfig() {
            MailServerConfig.clear();
            fetchConfig();
        }

    private static Properties MailServerConfig = new Properties();
    static {
        fetchConfig();
    }

    /**
     * Open a specific text file containing mail server
     * parameters, and populate a corresponding Properties object.
     */
    private static void fetchConfig() {
        //This file contains the javax.mail config properties mentioned above.
        Path path = Paths.get("D:\\mailSetup.txt");
        try (InputStream input = Files.newInputStream(path)) {
            MailServerConfig.load(input);
        }
        catch (IOException ex){
            System.err.println("Cannot open and load mail server properties file.");
        }
    }
 }

