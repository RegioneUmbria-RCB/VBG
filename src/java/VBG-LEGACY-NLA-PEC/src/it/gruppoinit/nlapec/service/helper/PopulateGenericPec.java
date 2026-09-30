package it.gruppoinit.nlapec.service.helper;

import it.gruppoinit.nlapec.schema.mailservice.AttachmentType;
import it.gruppoinit.nlapec.schema.mailservice.AttachmentsType;
import it.gruppoinit.nlapec.schema.mailservice.MailMessageType;
import it.gruppoinit.nlapec.util.FileUtil;
import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulateGenericPec {

    private static final Logger log = LoggerFactory.getLogger(PopulateGenericPec.class);
    private InserimentoPraticaNLARequest request;
    private MailtipoResponse mailtipo;
    private MailMessageType message;

    private PopulateGenericPec() {

	super();
    }

    public PopulateGenericPec(InserimentoPraticaNLARequest request, MailtipoResponse mailtipo) {

	this();
	this.message = new MailMessageType();
	this.request = request;
	this.mailtipo = mailtipo;
    }

    public MailMessageType populateMail() throws Exception {

	String destinatari = request.getSportelloDestinatario().getPecSportello();
	if (StringUtils.isBlank(destinatari)) {
	    log.error("populateMail: il campo pecSportello dello sportello destinatario è vuoto");
	    throw new RuntimeException("Nessun destinatario specificato");
	}
	message.setDestinatari(destinatari);
	if (mailtipo == null) {
	    throw new RuntimeException("Mail tipo non indicata");
	}
	message.setOggetto(mailtipo.getOggetto());
	message.setCorpoMail(mailtipo.getCorpo());
	message.setInviaComeHtml(true);
	populateAllegatiMail();
	return message;
    }

    private void populateAllegatiMail() {

	if (request.getDettaglioPratica().getDocumenti() != null) {
	    if (request.getDettaglioPratica().getDocumenti().size() > 0) {
		File tempDir = FileUtil.createFolder(String.valueOf(System.currentTimeMillis()));
		AttachmentsType atts = new AttachmentsType();
		for (DocumentiType doc : request.getDettaglioPratica().getDocumenti()) {
		    if (doc.getAllegati() != null && doc.getAllegati().getFile() != null && doc.getAllegati().getFile().getBinaryData() != null) {
			String filename = doc.getAllegati().getAllegato();
			if (StringUtils.isNotBlank(doc.getAllegati().getFile().getFileName())) {
			    filename = getHashText(
				    doc.getAllegati().getId() + "-" + doc.getAllegati().getFile().getFileName() + "-" + System.currentTimeMillis(),
				    "MD5", false);
			}
			File tmpFile = null;
			try {
			    tmpFile = FileUtil.saveFile(filename, doc.getAllegati().getFile().getBinaryData().getInputStream(), tempDir);
			    AttachmentType att = new AttachmentType();
			    att.setMimeType(doc.getAllegati().getFile().getMimeType());
			    att.setFileName(doc.getAllegati().getFile().getFileName());
			    att.setBinaryData(FileUtil.getBytesFromFile(tmpFile));
			    atts.getAttachment().add(att);
			} catch (IOException e) {
			    log.error("populateAllegatiMail# Non è stato possibile inviare il file {},{}", doc.getAllegati().getAllegato(), e);
			    throw new RuntimeException("Non è stato possibile inviare il file " + doc.getAllegati().getAllegato() + " a causa di "
				    + e.getMessage(), e);
			}
		    }
		}
		if (atts.getAttachment() != null) {
		    if (atts.getAttachment().size() > 0) {
			message.setAttachments(atts);
		    }
		}
		FileUtil.deleteAllFiles(tempDir);
	    }
	}
    }

    public static String getHashText(String plainText, String algorithmType, boolean isUpperCase) {

	MessageDigest algorithm = null;
	try {
	    algorithm = MessageDigest.getInstance(algorithmType);
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e.getMessage(), e.getCause());
	}
	algorithm.update(plainText.getBytes());
	byte[] digest = algorithm.digest();
	StringBuffer hexString = new StringBuffer();
	for (int i = 0; i < digest.length; i++) {
	    String hex = Integer.toHexString(0xFF & digest[i]);
	    if (hex.length() == 1) {
		hexString.append('0');
	    }
	    hexString.append(hex);
	}
	if (isUpperCase) {
	    return hexString.toString().toUpperCase();
	}
	return hexString.toString();
    }
}
