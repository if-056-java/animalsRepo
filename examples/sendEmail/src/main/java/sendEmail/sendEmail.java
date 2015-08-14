package sendEmail;

import sendEmail.MailSender;

public class sendEmail {

    public static void main(String[] args) {
        MailSender tlsSender = new MailSender( );

           //     tlsSender.newsSend("mama1@i.ua", "test1");
             //   tlsSender.newsSend("mama1@i.ua", "test1", "D:\\mailSetup.txt");

                tlsSender.feedback("mama1@i.ua", "Навіщо Ви робите цеєї. ", "Юрій");

    }
}