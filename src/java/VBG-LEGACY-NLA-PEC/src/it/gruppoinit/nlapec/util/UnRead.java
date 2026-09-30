package it.gruppoinit.nlapec.util;

import it.gruppoinit.nlapec.service.helper.GestoreCasellaMailHelper;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigResponse;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.ProtocolType;

import java.math.BigInteger;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.HeaderTerm;

public class UnRead {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

	Validator v = new Validator();
	// boolean b = v.validateMailSubjectPecNonFormatta("POSTA CERTIFICATA: prova 02", "POSTA CERTIFICATA: prova 02");
	System.out.println("Starting...");
	MailConfigResponse mailConfigResponse = new MailConfigResponse();
	mailConfigResponse.setUrl("mbox.cert.legalmail.it");
	mailConfigResponse.setUseAuthentication(true);
	mailConfigResponse.setPort(new BigInteger("993"));
	mailConfigResponse.setProtocol(ProtocolType.IMAP);
	mailConfigResponse.setSenderEmailAddress("gruppoinit@legalmail.it");
	mailConfigResponse.setUser("*****");
	mailConfigResponse.setPassword("*******");
	//
	//
	String protocol = "imap";
	if (mailConfigResponse.getProtocol().equals(ProtocolType.POP_3) || mailConfigResponse.getProtocol().equals(ProtocolType.SSL_POP_3)) {
	    protocol = "pop3";
	}
	Properties propServerMail = populatePropertyMail(protocol, mailConfigResponse.getPort().toString(), mailConfigResponse.getUrl());
	Session session = getSession(propServerMail, mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	Store store = session.getStore();
	store.connect(mailConfigResponse.getUrl(), mailConfigResponse.getUser(), mailConfigResponse.getPassword());
	Folder folder = store.getFolder(GestoreCasellaMailHelper.INBOX_FOLDER_DEFAULT);
	folder.open(Folder.READ_WRITE);
	//folder.open(Folder.READ_ONLY);
	//Message[] messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true));
	Message[] messages = folder.search(new HeaderTerm("Message-ID", "606958006.786664389.1360577126155vliaspec03@legalmail.it"));
	//Message[] messages = folder.search(new HeaderTerm("Message-ID", "<516658437.764718183.1343895645460liaspec02@legalmail.it>"));
	if (messages != null && messages.length > 0) {
	    int i = 1;
	    for (Message msg : messages) {
		System.out.print("Messaggio " + i + " : " + msg.getSubject());
		msg.setFlag(Flag.SEEN, false);
		System.out.println(" set UNREAD.");
		i++;
	    }
	}
	folder.close(false);
	store.close();
	System.out.println("End.");
    }

    public static Properties populatePropertyMail(String storeProtocol, String storePort, String storeHost) {

	Properties props = new Properties();
	String protocollRead = "pop3";
	if (storeProtocol != null && storeProtocol.equalsIgnoreCase("imap")) {
	    protocollRead = "imap";
	}
	props.setProperty("mail." + protocollRead + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.setProperty("mail." + protocollRead + ".socketFactory.fallback", "false");
	if (storePort != null) {
	    props.setProperty("mail." + protocollRead + ".port", storePort);
	    props.setProperty("mail." + protocollRead + ".socketFactory.port", storePort);
	}
	if (storeHost != null) {
	    props.put("mail." + protocollRead + ".host", storeHost);//
	}
	props.put("mail.store.protocol", protocollRead);
	return props;
    }

    public static Session getSession(Properties propServerMail, String userId, String password) {

	Authenticator auth = null;
	//if (serverMailConfig.getParametriSmtp().isSmtpUseAuth()) {
	auth = getAuthenticator(userId, password);
	// }
	return Session.getInstance(propServerMail, auth);
    }

    private static Authenticator getAuthenticator(final String userName, final String password) {

	Authenticator auth = new Authenticator() {

	    protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(userName, password);
	    }
	};
	return auth;
    }
}
