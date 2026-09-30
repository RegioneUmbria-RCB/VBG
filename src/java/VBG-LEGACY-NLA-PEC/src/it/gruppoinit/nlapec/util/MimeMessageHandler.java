package it.gruppoinit.nlapec.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MimeMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(MimeMessageHandler.class);

    public static OriginalMessage getOriginalMessage(PECMessage pecMessage) {

	OriginalMessage om = new OriginalMessage();
	Session session = Session.getDefaultInstance(System.getProperties(), null);
	try {
	    BodyPart bodyPart = pecMessage.getOriginalMessage();
	    final MimeMessage msg = new MimeMessage(session, bodyPart.getInputStream());
	    om.setId(pecMessage.getId());
	    om.setSubject(msg.getSubject());
	    om.setFrom(AddressUtil.getAsList(msg.getFrom()));
	    om.setTo(AddressUtil.getAsList(msg.getAllRecipients()));
	    om.setContentType(msg.getContentType());
	    MimeMessageHandler.handleMimeMessage(msg, om);
	    return om;
	} catch (Exception e) {
	    log.error("getOriginalMessage(): {}", e.getMessage());
	}
	return null;
    }

    public static String getMessageBody(BodyPart bodyPart) {

	OriginalMessage om = new OriginalMessage();
	Session session = Session.getDefaultInstance(System.getProperties(), null);
	try {
	    final MimeMessage msg = new MimeMessage(session, bodyPart.getInputStream());
	    om.setSubject(msg.getSubject());
	    om.setFrom(AddressUtil.getAsList(msg.getFrom()));
	    om.setTo(AddressUtil.getAsList(msg.getAllRecipients()));
	    om.setContentType(msg.getContentType());
	    MimeMessageHandler.handleMimeMessage(msg, om);
	    return StringUtils.defaultIfEmpty(om.getContentPlain(), om.getContentHtml());
	} catch (Exception e) {
	    log.error("getMessageBody(): {}", e.getMessage());
	}
	return null;
    }

    private static void handleMimeMessage(MimeMessage msg, OriginalMessage om) throws Exception {

	Object content = msg.getContent();
	if (content instanceof Multipart) {
	    handleMultipart((Multipart) content, om);
	} else {
	    handlePart(msg, om);
	}
    }

    private static void handleMultipart(Multipart multipart, OriginalMessage om) throws MessagingException, IOException {

	for (int i = 0, n = multipart.getCount(); i < n; i++) {
	    handlePart(multipart.getBodyPart(i), om);
	}
    }

    private static void handlePart(Part part, OriginalMessage om) throws MessagingException, IOException {

	String disposition = part.getDisposition();
	String contentType = part.getContentType();
	log.debug("handlePart(): [disposition:{}, contentType:{}, filename:{}]", new Object[] { disposition, contentType, part.getFileName() });
	if (disposition == null) { // When just body
	    // Check if plain
	    if (part.isMimeType("text/plain")) {
		// Handle plain
		//TODO use charset???
		//String charset = getCharset(part);
		om.setContentPlain((String) part.getContent());
	    } else {
		// Special non-attachment cases here of 
		// image/gif, text/html, ...
		if (part.isMimeType("text/html")) {
		    //TODO use charset???
		    //String charset = getCharset(part);
		    om.setContentHtml((String) part.getContent());
		}
	    }
	} else if (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE)) {
	    if (part.getFileName() != null) {
		om.addAttachment(getOMA(part));
	    }
	} else { // Should never happen
	    log.error("handlePart(): unknown disposition {}", disposition);
	}
    }

    @SuppressWarnings("rawtypes")
    private static String getEncoding(Part part) {

	String encoding = "";
	try {
	    Enumeration e = part.getAllHeaders();
	    while (e.hasMoreElements()) {
		Header h = (Header) e.nextElement();
		if ("Content-Transfer-Encoding".equals(h.getName())) {
		    encoding = h.getValue();
		    break;
		}
	    }
	    log.debug("getEncoding(): [encoding:{}]", encoding);
	} catch (MessagingException e) {
	    log.error("getEncoding(): {}", e.getMessage());
	}
	return encoding;
    }

    private static String getCharset(Part part) {

	String charset = "";
	try {
	    ContentType ct = new ContentType(part.getContentType());
	    charset = ct.getParameter("charset");
	    log.debug("getCharset(): [{}]", charset);
	} catch (MessagingException e) {
	    log.error("getCharset(): {}", e.getMessage());
	}
	return charset;
    }

    private static OriginalMessageAttachment getOMA(Part part) throws MessagingException, IOException {

	OriginalMessageAttachment oma = new OriginalMessageAttachment();
	oma.setFilename(part.getFileName());
	String encoding = getEncoding(part);
	oma.setEncoding(encoding);
	ContentType ct = new ContentType(part.getContentType());
	oma.setContentType(ct.getBaseType());
	oma.setFile(FileUtil.saveFile(part.getFileName(), part.getInputStream()));
	return oma;
    }
}
