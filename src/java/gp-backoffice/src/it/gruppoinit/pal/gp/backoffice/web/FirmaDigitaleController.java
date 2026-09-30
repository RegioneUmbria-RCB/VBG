package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSetParser;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.trento.comune.j4sign.cms.ExternalSignatureCMSSignedDataGenerator;
import it.trento.comune.j4sign.cms.ExternalSignatureSignerInfoGenerator;

@Controller
public class FirmaDigitaleController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitaleController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DocumentiDaFirmareService documentiDaFirmareService;

    public FirmaDigitaleController() {

	log.debug("Insert BC provider...");
	Security.insertProviderAt(new BouncyCastleProvider(), 2);
    }

    @RequestMapping
    public String start(Model model, @RequestParam("codiceoggetto") Integer[] codiceoggetto, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	// §§§BEGIN§§§
	log.debug("start() codiceoggetto: {}", codiceoggetto);
	List<CodiceDescrizioneBean> listaFiles = new ArrayList<CodiceDescrizioneBean>();
	if (codiceoggetto.length == 1) {
	    Oggetti ogg = oggettiService.findByIdLazy(new PkId(Integer.valueOf(codiceoggetto[0])));
	    model.addAttribute("codiceoggetto", codiceoggetto[0]);
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(codiceoggetto[0].toString());
	    cdb.setDescrizione(Utilities.eliminaCaratteriNonAscii(ogg.getNomefile()));
	    listaFiles.add(cdb);
	    String mimeType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
	    model.addAttribute("mime_type", mimeType);
	    model.addAttribute("FIRMA_MULTIPLA", Boolean.FALSE);
	} else {
	    String listaCodiciOggetto = "";
	    for (Integer co : codiceoggetto) {
		Oggetti ogg = oggettiService.findByIdLazy(new PkId(Integer.valueOf(co)));
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice(co.toString());
		cdb.setDescrizione(Utilities.eliminaCaratteriNonAscii(ogg.getNomefile()));
		listaFiles.add(cdb);
		listaCodiciOggetto += co.intValue() + ",";
	    }
	    if (listaCodiciOggetto.endsWith(",")) {
		listaCodiciOggetto = listaCodiciOggetto.substring(0, listaCodiciOggetto.length() - 1);
	    }
	    model.addAttribute("codiceoggetto", listaCodiciOggetto);
	    model.addAttribute("FIRMA_MULTIPLA", Boolean.TRUE);
	}
	String codiceoggettoReqParam = "";
	for (Integer co : codiceoggetto) {
	    codiceoggettoReqParam += "&codiceoggetto=" + co.toString();
	}
	model.addAttribute("listaFilesDaFirmare", listaFiles);
	model.addAttribute("listaCodicioggettoReqParam", codiceoggettoReqParam);
	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	oggettiService.updateFilesBloccaModifica(codiceoggetto, codiceResponsabile);
	model.addAttribute("func", StringUtils.defaultIfEmpty(request.getParameter("func"), ""));
	return "firmadigitale/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String startDocumentiDaFirmare(Model model, @RequestParam("codiceoggetto") Integer[] codiceoggetto, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	log.debug("startDocumentiDaFirmare() codiceoggetto documento da firmare: {}", codiceoggetto);
	// devo creare una stringa conteneti i codici passati per poter effettuare il chiudi e toenare alla pagina 
	// DocumentiDaFirmareController.mettiallafirmamultipla().
	String listaCodiciOggetto = "";
	List<DocumentiDaFirmare> listaFiles = new ArrayList<DocumentiDaFirmare>();
	Integer[] codiceOggettoFile = new Integer[codiceoggetto.length];
	if (codiceoggetto.length == 1) {
	    DocumentiDaFirmare documentiDaFirmare = documentiDaFirmareService.findById(new PkId(Integer.valueOf(codiceoggetto[0])));
	    codiceOggettoFile[0] = documentiDaFirmare.getOggetti().getId().getCodice();
	    model.addAttribute("codiceOggettoFile", codiceOggettoFile[0]);
	    listaFiles.add(documentiDaFirmare);
	    //	    String mimeType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
	    //	    model.addAttribute("mime_type", mimeType);
	    listaCodiciOggetto = codiceoggetto[0].toString();
	    model.addAttribute("FIRMA_MULTIPLA", Boolean.FALSE);
	} else {
	    int index = 0;
	    for (Integer co : codiceoggetto) {
		DocumentiDaFirmare documentiDaFirmare = documentiDaFirmareService.findById(new PkId(Integer.valueOf(co)));
		// devo creare un array di codici che rappresenta gli id degli oggetti (file contenuti nei record di documentidaFirmare)
		// in modo che possono essere bloccati e non modificati durante il processo di firma
		codiceOggettoFile[index] = documentiDaFirmare.getOggetti().getId().getCodice();
		index++;
		listaFiles.add(documentiDaFirmare);
		listaCodiciOggetto += co.intValue() + ",";
	    }
	    if (listaCodiciOggetto.endsWith(",")) {
		listaCodiciOggetto = listaCodiciOggetto.substring(0, listaCodiciOggetto.length() - 1);
	    }
	    model.addAttribute("FIRMA_MULTIPLA", Boolean.TRUE);
	}
	String codiceoggettoReqParam = "";
	for (Integer co : codiceoggetto) {
	    codiceoggettoReqParam += "&codiceoggetto=" + co.toString();
	}
	model.addAttribute("codiceoggetto", listaCodiciOggetto);
	model.addAttribute("listaFilesDaFirmare", listaFiles);
	model.addAttribute("listaCodicioggettoReqParam", codiceoggettoReqParam);
	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	// Blocco i file durante il processo di firma, in modo che non possano essere modifica 
	oggettiService.updateFilesBloccaModifica(codiceOggettoFile, codiceResponsabile);
	model.addAttribute("func", StringUtils.defaultIfEmpty(request.getParameter("func"), ""));
	return "firmadigitale/formDocumentiDaFirmare";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void process(@RequestParam() Integer codiceoggetto, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	Oggetti ogg = oggettiService.findById(new PkId(Integer.valueOf(codiceoggetto)));
	boolean exit = loadData(request, response, ogg);
	if (exit)
	    return;
	log.debug("==================== DO POST METHOD START =========================");
	String base64Certificate = (String) request.getParameter("certificate");
	String base64Signature = (String) request.getParameter("signature");
	PrintWriter out = response.getWriter();
	if ((base64Certificate != null) && (base64Signature != null)) {
	    byte[] sigBytes = base64Decode(base64Signature);
	    byte[] certBytes = base64Decode(base64Certificate);
	    String storeKey = deriveStoreKey(sigBytes, certBytes);
	    ExternalSignatureSignerInfoGenerator info = retriveSignerInfoGenerator(storeKey, request);
	    if (info != null) {
		CMSSignedData signedData = buildCMSSignedData(info, sigBytes, certBytes, ogg.getOggetto());
		out.print("OK-SignedData built -");
		if (signedData != null) {
		    saveFile(signedData, ogg);
		    out.print(" saved file: codiceoggetto:'" + codiceoggetto + "'");
		} else
		    out.print("signedData not verified, file NOT saved!");
	    }
	} else
	    out.print("ERROR-certificate or signature not available.");
	out.flush();
	log.debug("==================== DO POST METHOD END =========================");
	// §§§END§§§
    }

    @RequestMapping
    public void processDocDaFirmare(@RequestParam("codiceoggetto") Integer codicedocumantodafirmare, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	// 1) recuperodocdafirmare da codicedocumantodafirmare
	// 2) recupero oggetto
	// 3) firmo oggetto
	// 4) salvo firma completa e note
	DocumentiDaFirmare documentiDaFirmare = documentiDaFirmareService.findById(new PkId(codicedocumantodafirmare));
	Oggetti oggetti = oggettiService.findById(new PkId(documentiDaFirmare.getOggetti().getId().getCodice()));
	boolean exit = loadData(request, response, oggetti);
	if (exit)
	    return;
	log.debug("==================== DO POST METHOD START =========================");
	String base64Certificate = (String) request.getParameter("certificate");
	String base64Signature = (String) request.getParameter("signature");
	PrintWriter out = response.getWriter();
	if ((base64Certificate != null) && (base64Signature != null)) {
	    byte[] sigBytes = base64Decode(base64Signature);
	    byte[] certBytes = base64Decode(base64Certificate);
	    String storeKey = deriveStoreKey(sigBytes, certBytes);
	    ExternalSignatureSignerInfoGenerator info = retriveSignerInfoGenerator(storeKey, request);
	    if (info != null) {
		CMSSignedData signedData = buildCMSSignedData(info, sigBytes, certBytes, oggetti.getOggetto());
		out.print("OK-SignedData built -");
		if (signedData != null) {
		    saveFile(signedData, oggetti);
		    out.print(" saved file: codiceoggetto:'" + oggetti.getId().getCodice() + "'");
		    // salvo il record domuneti da firmare come firmato correttamente
		    List<Integer> id_doc_da_firmare = new ArrayList<Integer>();
		    id_doc_da_firmare.add(codicedocumantodafirmare);
		    documentiDaFirmareService.updateMutiplo(StatiDocumentiDaFirmare.FIRMA_COMPLETA.name(), "", id_doc_da_firmare);
		} else
		    out.print("signedData not verified, file NOT saved!");
	    }
	} else
	    out.print("ERROR-certificate or signature not available.");
	out.flush();
	log.debug("==================== DO POST METHOD END =========================");
    }

    /**
     * Implementation of the GET method; returns informations to the client and stores SignerInfoGenerator-related
     * informations; see {@link CMSServlet} for details.
     * 
     * @see CMSServlet
     */
    private boolean loadData(HttpServletRequest request, HttpServletResponse response, Oggetti ogg) throws IOException {

	log.debug("==================== DO GET METHOD START=========================");
	boolean exit = false;
	// §§§BEGIN§§§
	String retrieve = (String) request.getParameter("retrieve");
	if (retrieve != null) {
	    PrintWriter out = response.getWriter();
	    log.debug("Retrieving: " + retrieve);
	    if (retrieve.equals("DATA")) {
		//@fabrizioc non invio i dati all'applet dato che li recupera direttamente
		out.print("data");
	    } else if (retrieve.equals("ENCODED_AUTHENTICATED_ATTRIBUTES")) {
		ExternalSignatureSignerInfoGenerator gen = buildSignerInfoGenerator();
		// Patch from Alessandro (thanks!)
		// Decode and set certificate
		byte[] certificate = base64Decode(request.getParameter("certificate"));
		java.security.cert.CertificateFactory cf;
		try {
		    cf = java.security.cert.CertificateFactory.getInstance("X.509");
		    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certificate);
		    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
		    gen.setCertificate(javaCert);
		} catch (CertificateException e1) {
		    e1.printStackTrace();
		}
		// s.setAttribute("signerInfo", infoGen);
		byte[] bytesToSign = getAuthenticatedAttributesBytes(gen, ogg.getOggetto());
		String attrPrintout = getAuthenticatedAttributesPrintout(bytesToSign);
		log.debug("Authenticated Attributes printout follows:\n" + attrPrintout);
		byte[] digestBytes = null;
		// calculate digest
		java.security.MessageDigest md;
		try {
		    md = java.security.MessageDigest.getInstance(CMSSignedDataGenerator.DIGEST_SHA256);
		    md.update(bytesToSign);
		    digestBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		log.debug("Encapsulating digest in digestInfo ...");
		byte[] digestInfoBytes = encapsulateInDigestInfo(CMSSignedDataGenerator.DIGEST_SHA256, digestBytes);
		log.debug(formatAsString(digestInfoBytes, " "));
		String storeKey = formatAsString(digestInfoBytes, "");
		log.debug("Saving SignerInfoGenerator with key: " + storeKey);
		log.debug("The key the string representation of digestInfo!");
		SignerInfoGeneratorItem s = new SignerInfoGeneratorItem(gen, attrPrintout);
		// save generator and printout
		// this.signerInfoGeneratorTable.put(storeKey, s);
		request.getSession().setAttribute(storeKey, s);
		log.debug("Returning digestInfo to client ...");
		out.print(base64Encode(digestInfoBytes));
	    } else if (retrieve.equals("AUTHENTICATED_ATTRIBUTES_PRINTOUT")) {
		String base64Hash = (String) request.getParameter("encodedhash");
		if (base64Hash != null) {
		    byte[] hash = base64Decode(base64Hash);
		    //SignerInfoGeneratorItem s = (SignerInfoGeneratorItem) this.signerInfoGeneratorTable.get(formatAsString(hash, ""));
		    SignerInfoGeneratorItem s = (SignerInfoGeneratorItem) request.getSession().getAttribute(formatAsString(hash, ""));
		    out.print(s.getAttrPrintout());
		}
	    } else {
		out.println("Error: value '" + retrieve + "' for required parameter 'retrive' not expected.");
	    }
	    out.flush();
	    exit = true;
	} else {
	    //PrintWriter out = response.getWriter();
	    //out.println("Error: required parameter 'retrive' not found.");
	    //out.flush();
	}
	log.debug("==================== DO GET METHOD END=========================");
	// §§§END§§§
	return exit;
    }

    /**
     * Formats a byte[] as an hexadecimal String, interleaving bytes with a separator string.
     * 
     * @param bytes
     *            the byte[] to format.
     * @param byteSeparator
     *            the string to be used to separate bytes.
     * 
     * @return the formatted string.
     */
    public String formatAsString(byte[] bytes, String byteSeparator) {

	int n, x;
	String w = new String();
	String s = new String();
	for (n = 0; n < bytes.length; n++) {
	    x = (int) (0x000000FF & bytes[n]);
	    w = Integer.toHexString(x).toUpperCase();
	    if (w.length() == 1)
		w = "0" + w;
	    s = s + w + ((n + 1 == bytes.length) ? "" : byteSeparator);
	} // for
	return s;
    }

    /**
     * Class encapsulating a SignerInfoGenerator-related informations to be stored after a signature request.
     * 
     * @author Roberto Resoli
     */
    private class SignerInfoGeneratorItem {

	private String attrPrintout = null;
	private ExternalSignatureSignerInfoGenerator sig = null;

	/**
	 * Constructor.
	 * 
	 * @param aSig
	 *            the {@link ExternalSignatureSignerInfoGenerator}
	 * @param aPrintOut
	 *            the authenticated attributes textual dump at the time of the request.
	 */
	public SignerInfoGeneratorItem(ExternalSignatureSignerInfoGenerator aSig, String aPrintOut) {

	    sig = aSig;
	    attrPrintout = aPrintOut;
	}

	/**
	 * The attributes printout getter.
	 * 
	 * @return the authenticated attributes textual dump at the time of the request.
	 */
	public String getAttrPrintout() {

	    return attrPrintout;
	}

	/**
	 * The ExternalSignatureSignerInfoGenerator getter.
	 * 
	 * @return the ExternalSignatureSignerInfoGenerator for encapsulating signer informations.
	 */
	public ExternalSignatureSignerInfoGenerator getSig() {

	    return sig;
	}
    }

    /**
     * Converts the provided <code>certBytes</code> in a <code>java.security.cert.X509Certificate</code>, gets from it
     * the signer public key, and uses it to decrypt <code>sigBytes</code>. The decryption result is returned as a
     * formatted exadecimal string; see {@link CMSServlet} for details.
     * 
     * @param sigBytes
     *            signature bytes
     * @param certBytes
     *            certificate bytes
     * @return the decryption of sigBytes using the RSA/ECB/PKCS1PADDING Algorithm.
     */
    private String deriveStoreKey(byte[] sigBytes, byte[] certBytes) {

	// §§§BEGIN§§§
	String key = null;
	java.security.cert.CertificateFactory cf;
	try {
	    cf = java.security.cert.CertificateFactory.getInstance("X.509");
	    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certBytes);
	    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
	    PublicKey pubKey = javaCert.getPublicKey();
	    try {
		log.debug("Deriving store key from signature and certificate.");
		log.debug("N.B.:This serves also as signature verification!");
		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "BC");
		c.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] decBytes = null;
		/*
		if (false)
			decBytes = derDecode(c.doFinal(sigBytes));
		else
		*/
		decBytes = c.doFinal(sigBytes);
		key = formatAsString(decBytes, "");
	    } catch (NoSuchAlgorithmException e) {
		log.error("deriveStoreKey()", e);
	    } catch (NoSuchPaddingException e) {
		log.error("deriveStoreKey()", e);
	    } catch (InvalidKeyException e) {
		log.error("deriveStoreKey()", e);
	    } catch (IllegalStateException e) {
		log.error("deriveStoreKey()", e);
	    } catch (IllegalBlockSizeException e) {
		log.error("deriveStoreKey()", e);
	    } catch (BadPaddingException e) {
		log.error("deriveStoreKey()", e);
	    } catch (NoSuchProviderException e) {
		log.error("deriveStoreKey()", e);
	    }
	} catch (CertificateException e) {
	    log.error("deriveStoreKey()", e);
	}
	return key;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Gets the <code>ExternalSignatureSignerInfoGenerator</code> generator which originally produced the given
     * <code>storeKey</code>.
     * 
     * @param storeKey
     * @return the {@link ExternalSignatureSignerInfoGenerator} associated with the<code>storeKey</code>
     */
    private ExternalSignatureSignerInfoGenerator retriveSignerInfoGenerator(String storeKey, HttpServletRequest request) {

	// §§§BEGIN§§§
	log.debug("Retrieving signerInfoGenerator using key: " + storeKey);
	// ExternalSignatureSignerInfoGenerator info = ((SignerInfoGeneratorItem) this.signerInfoGeneratorTable.get(storeKey)).getSig();
	ExternalSignatureSignerInfoGenerator info = ((SignerInfoGeneratorItem) request.getSession().getAttribute(storeKey)).getSig();
	if (info != null)
	    log.debug("Generator found. Signature is verified.");
	else
	    log.debug("Generator not found! Signature is NOT verified!");
	// remove infos from store
	// this.signerInfoGeneratorTable.remove(storeKey);
	request.getSession().removeAttribute(storeKey);
	return info;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Builds the CMS signed data message.
     * 
     * @param infoGen
     *            the {@link ExternalSignatureSignerInfoGenerator} wrapping signer informations
     * @param sigBytes
     *            the digest encrypted with signer private key.
     * @param certBytes
     *            the signer certificate.
     * @return the {@link CMSSignedData} message.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CMSSignedData buildCMSSignedData(ExternalSignatureSignerInfoGenerator infoGen, byte[] sigBytes, byte[] certBytes, byte[] rawBytes) {

	// §§§BEGIN§§§
	CMSSignedData result = null;
	log.debug("building CMSSignedData.");
	CMSProcessable msg = new CMSProcessableByteArray(rawBytes);
	// questa versione del generatore è priva della classe interna per
	// la generazione delle SignerInfo, che è stata promossa a classe a
	// sè.
	ExternalSignatureCMSSignedDataGenerator gen = new ExternalSignatureCMSSignedDataGenerator();
	// Conterrà la lista dei certificati; come minimo dovrà
	// contenere i certificati dei firmatari; opzionale, ma
	// consigliabile,
	// l'aggiunta dei certificati root per completare le catene di
	// certificazione.
	ArrayList certList = new ArrayList();
	// get Certificate
	java.security.cert.CertificateFactory cf;
	try {
	    cf = java.security.cert.CertificateFactory.getInstance("X.509");
	    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certBytes);
	    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
	    infoGen.setCertificate(javaCert);
	    infoGen.setSignedBytes(sigBytes);
	    certList.add(javaCert);
	    gen.addSignerInf(infoGen);
	    if (certList.size() != 0) {
		// Per passare i certificati al generatore li si incapsula in un
		// CertStore.
		CertStore store;
		store = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
		log.debug("Adding certificates ... ");
		gen.addCertificatesAndCRLs(store);
		// Finalmente, si può creare il l'oggetto CMS.
		log.debug("Generating CMSSignedData ");
		result = gen.generate(msg, true);
	    }
	} catch (CertificateException e) {
	    log.error("buildCMSSignedData()", e);
	} catch (InvalidAlgorithmParameterException e) {
	    log.error("buildCMSSignedData()", e);
	} catch (NoSuchAlgorithmException e) {
	    log.error("buildCMSSignedData()", e);
	} catch (NoSuchProviderException e) {
	    log.error("buildCMSSignedData()", e);
	} catch (CertStoreException e) {
	    log.error("buildCMSSignedData()", e);
	} catch (CMSException e) {
	    log.error("buildCMSSignedData()", e);
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Saves a CMS signed data file on the server file system; the extension should be ".p7m" according to italian
     * rules.
     * 
     * @param s
     *            the {@link CMSSignedData} object to save.
     * @param filePath
     *            full path of the file.
     * @return true if the file was correctly saved, false otherwise.
     */
    private boolean saveFile(CMSSignedData s, Oggetti ogg) {

	try {
	    log.debug("saveFile(): " + ogg.getId().getCodice());
	    ogg.setNomefile(ogg.getNomefile() + ".p7m");
	    ogg.setOggetto(s.getEncoded());
	    oggettiService.update(ogg);
	    return true;
	} catch (Exception e) {
	    log.debug("saveFile() Error: ", e);
	    return false;
	}
    }

    /**
     * Creates a {@link ExternalSignatureSignerInfoGenerator} with a <code>MD5</code> digest algorithm and
     * <code>RSA</code> encryption algorithm.
     * 
     * @return the <code>ExternalSignatureSignerInfoGenerator</code> object
     */
    private ExternalSignatureSignerInfoGenerator buildSignerInfoGenerator() {

	// §§§BEGIN§§§
	log.debug("Building SignerInfoGenerator.");
	ExternalSignatureSignerInfoGenerator gen = new ExternalSignatureSignerInfoGenerator(CMSSignedDataGenerator.DIGEST_SHA256,
		CMSSignedDataGenerator.ENCRYPTION_RSA);
	return gen;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * A BASE64 encoding function, using the apache commons codec implementation.
     * 
     * @param bytes
     *            the bytes to encode.
     * @return the <code>BASE64</code> encoding of <code>bytes</code>.
     */
    private String base64Encode(byte[] bytes) {

	try {
	    return new String(Base64.encodeBase64(bytes), "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * A BASE64 decoding function, using the apache commons codec implementation.
     * 
     * @param encoded
     *            the string to decode.
     * @return the <code>bytes</code> decoded.
     */
    private byte[] base64Decode(String encoded) {

	try {
	    return Base64.decodeBase64(encoded.getBytes("UTF-8"));
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    /**
     * Uses the provided {@link ExternalSignatureSignerInfoGenerator} for calculating the authenticated attributes bytes
     * to be digested-encrypted by the signer. Note that the attributes include a timestamp, so the result is
     * time-dependent!
     * 
     * @param signerGenerator
     *            the <code>ExternalSignatureSignerInfoGenerator</code> object that does the job.
     * @return the bytes to be signed.
     */
    private byte[] getAuthenticatedAttributesBytes(ExternalSignatureSignerInfoGenerator signerGenerator, byte[] rawBytes) {

	// §§§BEGIN§§§
	log.debug("Building AuthenticatedAttributes.");
	byte[] bytesToSign = null;
	try {
	    CMSProcessable msg = new CMSProcessableByteArray(rawBytes);
	    bytesToSign = signerGenerator.getBytesToSign(PKCSObjectIdentifiers.data, msg, "BC");
	} catch (Exception e) {
	    log.error("getAuthenticatedAttributesBytes()", e);
	}
	return bytesToSign;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * A text message resulting from a dump of provided authenticated attributes data. Shows, among other things, the
     * embedded timestamp attribute.
     * 
     * @param bytes
     *            the ASN.1 DER set of authenticated attributes.
     * @return the attributes textual dump.
     */
    @SuppressWarnings("rawtypes")
    private String getAuthenticatedAttributesPrintout(byte[] bytes) {

	// §§§BEGIN§§§
	StringWriter printout = new StringWriter();
	PrintWriter pw = new PrintWriter(printout);
	try {
	    ASN1StreamParser a1p = new ASN1StreamParser(bytes);
	    log.debug("ASN1 parser built: " + a1p);
	    DERSetParser signedAttributesParser = (DERSetParser) a1p.readObject();
	    log.debug("DERSetParser object read: " + signedAttributesParser);
	    ASN1Set set = ASN1Set.getInstance(signedAttributesParser.getDERObject());
	    AttributeTable attr = new AttributeTable(set);
	    log.debug("Attribute table created: " + attr);
	    Iterator iter = attr.toHashtable().values().iterator();
	    pw.println("Listing authenticated attributes:");
	    int count = 1;
	    while (iter.hasNext()) {
		Attribute a = (Attribute) iter.next();
		pw.println("Attribute " + count + ":");
		if (a.getAttrType().getId().equals(CMSAttributes.signingTime.getId())) {
		    Time time = Time.getInstance(a.getAttrValues().getObjectAt(0));
		    pw.println("Authenticated time (SERVER local time): " + time.getDate());
		}
		if (a.getAttrType().getId().equals(CMSAttributes.contentType.getId())) {
		    if (CMSObjectIdentifiers.data.getId().equals(DERObjectIdentifier.getInstance(a.getAttrValues().getObjectAt(0)).getId()))
			pw.println("Content Type: PKCS7_DATA");
		}
		if (a.getAttrType().getId().equals(CMSAttributes.messageDigest.getId())) {
		    byte[] md = DEROctetString.getInstance(a.getAttrValues().getObjectAt(0)).getOctets();
		    pw.println("Message Digest (SHA-256 hash of data content): " + formatAsString(md, " "));
		}
		if (a.getAttrType().getId().equals(PKCSObjectIdentifiers.id_aa_signingCertificateV2.getId())) {
		    pw.println("Signing Certificate V2");
		}
		pw.println("\nAttribute dump follows:");
		pw.println(ASN1Dump.dumpAsString(a) + "\n");
		count++;
	    }
	} catch (Exception e) {
	    log.error("getAuthenticatedAttributesPrintout()", e);
	    pw.println(e);
	    return null;
	}
	pw.flush();
	return printout.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private byte[] encapsulateInDigestInfo(String digestAlg, byte[] digestBytes) throws IOException {

	// §§§BEGIN§§§
	//byte[] bcDigestInfoBytes = null;
	ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	DEROutputStream dOut = new DEROutputStream(bOut);
	DERObjectIdentifier digestObjId = new DERObjectIdentifier(digestAlg);
	AlgorithmIdentifier algId = new AlgorithmIdentifier(digestObjId, null);
	DigestInfo dInfo = new DigestInfo(algId, digestBytes);
	dOut.writeObject(dInfo);
	return bOut.toByteArray();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }
}
