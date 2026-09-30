package it.gruppoinit.nlapec.util;

import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.Init;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMESignedParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public final class PECVerifier {

    private static Logger log = LoggerFactory.getLogger(PECVerifier.class);

    public PECVerifier() {

	Provider bcprov = Security.getProvider("BC");
	if (bcprov == null) {
	    bcprov = new BouncyCastleProvider();
	    Security.addProvider(bcprov);
	}
	if (!Init.isInitialized()) {
	    Init.init();
	}
    }

    public PECMessageInfos verifyAnalizePEC(final MimeMessage msg) throws Exception {

	Document document = null;
	PECBodyParts bodyMessage = null;
	Set<Certificate> signatures = null;
	if (msg.isMimeType("multipart/signed")) {
	    final SMIMESignedParser s = new SMIMESignedParser((MimeMultipart) msg.getContent());
	    document = PECVerifier.extractXMLCert(s);
	    bodyMessage = PECVerifier.extractBodyMessage(s.getContent());
	    signatures = verify(s);
	} else {
	    ContentType ct = new ContentType(msg.getContentType());
	    log.warn("MimeType expected: [multipart/signed] found: [{}]", ct.getBaseType());
	}
	final PECMessageInfos docVer = new PECMessageInfos();
	docVer.setSignatures(signatures);
	docVer.setCertificate(document);
	docVer.setBodyParts(bodyMessage);
	return docVer;
    }

    private static Document extractXMLCert(final SMIMESignedParser s) throws Exception {

	final MimeBodyPart mimePart = s.getContent();
	final DataHandler data = mimePart.getDataHandler();
	final MimeMultipart multiPart = (MimeMultipart) data.getContent();
	int partsCount = multiPart.getCount();
	if (partsCount < 1) {
	    throw new MessagingException("Missing attachments");
	}
	for (int i = 0; i < partsCount; i++) {
	    final BodyPart bodyCert = multiPart.getBodyPart(i);
	    if ("daticert.xml".equals(bodyCert.getFileName())) {
		final DataHandler dataCert = bodyCert.getDataHandler();
		final DataSource dataSourceCert = dataCert.getDataSource();
		final InputStream idataCert = dataSourceCert.getInputStream();
		final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder parser = builderFactory.newDocumentBuilder();
		final InputSource source = new InputSource(idataCert);
		final Document domCert = parser.parse(source);
		return domCert;
	    }
	}
	throw new MessagingException("Missing attachment daticert.xml");
    }

    private static PECBodyParts extractBodyMessage(final MimeBodyPart mimePart) throws Exception {

	final PECBodyParts bodyPartPieces = new PECBodyParts();
	final DataHandler data = mimePart.getDataHandler();
	final MimeMultipart multiPart = (MimeMultipart) data.getContent();
	for (int i = 0; i < multiPart.getCount(); i++) {
	    final BodyPart bodyPiece = multiPart.getBodyPart(i);
	    bodyPartPieces.putBodyPart(bodyPiece);
	}
	return bodyPartPieces;
    }

    @SuppressWarnings("unchecked")
    private Set<Certificate> verify(final SMIMESignedParser s) throws Exception {

	final Set<Certificate> certificates = new HashSet<Certificate>();
	final CertStore certs = s.getCertificatesAndCRLs("Collection", "BC");
	final SignerInformationStore signers = s.getSignerInfos();
	final Collection<SignerInformation> c = signers.getSigners();
	for (SignerInformation signer : c) {
	    final Collection<X509Certificate> certCollection = (Collection<X509Certificate>) certs.getCertificates(signer.getSID());
	    final X509Certificate cert = (X509Certificate) certCollection.iterator().next();
	    //if (!signer.verify(cert.getPublicKey(), "BC")) {
	    //throw new Exception("Signature invalid");
	    //}
	    certificates.add(cert);
	}
	return certificates;
    }
}