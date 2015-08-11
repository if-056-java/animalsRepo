package sendEmail;

import sendEmail.GmailSender;

public class sendEmail {

    private static GmailSender tlsSender = new GmailSender(
            "Test 2", // Subject
            "mama1@i.ua"); // To


    public static void main(String[] args) {

                tlsSender.send();

                tlsSender.send("d:\\startup.txt");

    }
}