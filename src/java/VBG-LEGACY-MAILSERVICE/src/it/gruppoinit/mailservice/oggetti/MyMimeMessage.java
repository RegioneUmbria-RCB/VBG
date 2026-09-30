package it.gruppoinit.mailservice.oggetti;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;

public class MyMimeMessage extends MimeMessage {

    private static final String BRACKET_SUFFIX = ">";
    private static final String BRACKET_PREFIX = "<";

    public MyMimeMessage(Session arg0) {

	super(arg0);
    }

    String myMessageId = "";

    @Override
    protected void updateMessageID() throws MessagingException {

	setHeader("Message-ID", myMessageId);
	setHeader("References", myMessageId);
    }

    public String getMyMessageId() {

	return myMessageId;
    }

    public void setMyMessageId(String pMyMessageId) {

	if (StringUtils.isBlank(pMyMessageId)) {
	    return;
	}
	if (!pMyMessageId.startsWith(BRACKET_PREFIX)) {
	    pMyMessageId = BRACKET_PREFIX + pMyMessageId;
	}
	if (!pMyMessageId.endsWith(BRACKET_SUFFIX)) {
	    pMyMessageId = pMyMessageId + BRACKET_SUFFIX;
	}
	this.myMessageId = pMyMessageId;
    }
}