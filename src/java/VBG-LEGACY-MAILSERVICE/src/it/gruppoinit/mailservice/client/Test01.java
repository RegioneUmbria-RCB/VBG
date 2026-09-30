package it.gruppoinit.mailservice.client;

import it.gruppoinit.mailservice.MailServiceStub;
import it.gruppoinit.mailservice.MailServiceStub.MailMessageType;
import it.gruppoinit.mailservice.MailServiceStub.MessageRequest;
import it.gruppoinit.mailservice.MailServiceStub.MessageResponse;

public class Test01 {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

	MailServiceStub sender = new MailServiceStub("http://localhost:8080/MailService/services/MailService");
	MessageRequest message = new MessageRequest();
	message.setSoftware("SS");
	message.setToken("fdsfsdfds");
	MailMessageType mailMessage = new MailMessageType();
	mailMessage.setCorpoMail("<h1>fava</h1>");
	mailMessage.setDestinatari("mirko.calandrini@gruppoinit.it");
	mailMessage.setInviaComeHtml(true);
	mailMessage.setMittente("mirko.calandrini@gruppoinit.it");
	mailMessage.setOggetto("test");
	message.setMailMessage(mailMessage);
	MessageResponse response = sender.sendMail(message);
	System.out.println("END (" + response.getEsito() + ")");
    }
}
