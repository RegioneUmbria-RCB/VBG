package it.gruppoinit.pal.gp.areariservata.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;

// @Controller
public class FirmaDigitaleController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitaleController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ContenttypesService contenttypesService;
    //    public FirmaDigitaleController() {
    //
    //	log.debug("Insert BC provider...");
    //	Security.insertProviderAt(new BouncyCastleProvider(), 2);
    //    }
    //
    //    @RequestMapping
    //    public void process(@RequestParam() Integer codiceoggetto, HttpServletRequest request, HttpServletResponse response) throws IOException {
    //
    //	log.info("process: codiceoggetto={}", codiceoggetto);
    //	Oggetti ogg = oggettiService.findById(new PkId(Integer.valueOf(codiceoggetto)));
    //	boolean exit = false;
    //	try {
    //	    exit = loadData(request, response, ogg);
    //	    log.debug("loadData: exit=true");
    //	} catch (Exception e) {
    //	    log.error("loadData:", e);
    //	    exit = true;
    //	}
    //	if (exit) {
    //	    return;
    //	}
    //	String base64Certificate = (String) request.getParameter("certificate");
    //	String base64Signature = (String) request.getParameter("signature");
    //	PrintWriter out = response.getWriter();
    //	if ((base64Certificate != null) && (base64Signature != null)) {
    //	    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    //	    byte[] sigBytes = decoder.decodeBuffer(base64Signature);
    //	    byte[] certBytes = decoder.decodeBuffer(base64Certificate);
    //	    String storeKey = deriveStoreKey(sigBytes, certBytes);
    //	    ExternalSignatureSignerInfoGenerator info = retriveSignerInfoGenerator(storeKey, request);
    //	    CMSSignedData signedData = buildCMSSignedData(info, sigBytes, certBytes, ogg.getOggetto());
    //	    if (signedData != null) {
    //		if (saveFile(signedData, ogg)) {
    //		    out.print("OK-file saved. codiceoggetto:'" + codiceoggetto + "'");
    //		} else {
    //		    out.print("ERROR-file NOT saved! codiceoggetto:'" + codiceoggetto + "'");
    //		}
    //	    } else {
    //		out.print("ERROR-signedData not verified, file NOT saved!");
    //	    }
    //	} else {
    //	    out.print("ERROR-certificate or signature not available.");
    //	}
    //	out.flush();
    //    }
    //
    //    /**
    //     * Implementation of the GET method; returns informations to the client and stores SignerInfoGenerator-related
    //     * informations; see {@link CMSServlet} for details.
    //     * 
    //     * @see CMSServlet
    //     */
    //    private boolean loadData(HttpServletRequest request, HttpServletResponse response, Oggetti ogg) throws Exception {
    //
    //	log.info("loadData: codiceoggetto={}", ogg.getId().getCodice());
    //	boolean exit = false;
    //	String retrieve = (String) request.getParameter("retrieve");
    //	log.info("loadData: retrieve={}", retrieve);
    //	if (retrieve != null) {
    //	    PrintWriter out = response.getWriter();
    //	    if (retrieve.equals("DATA")) {
    //		//@fabrizioc non invio i dati all'applet dato che li recupera direttamente
    //		out.print("data");
    //	    } else if (retrieve.equals("ENCODED_AUTHENTICATED_ATTRIBUTES")) {
    //		ExternalSignatureSignerInfoGenerator gen = buildSignerInfoGenerator();
    //		// Patch from Alessandro (thanks!)
    //		// Decode and set certificate
    //		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    //		log.info("loadData: certificate\n{}", request.getParameter("certificate"));
    //		byte[] certificate = decoder.decodeBuffer(request.getParameter("certificate"));
    //		java.security.cert.CertificateFactory cf;
    //		try {
    //		    cf = java.security.cert.CertificateFactory.getInstance("X.509");
    //		    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certificate);
    //		    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
    //		    gen.setCertificate(javaCert);
    //		} catch (CertificateException e1) {
    //		    log.error("loadData: ", e1);
    //		    throw e1;
    //		}
    //		// s.setAttribute("signerInfo", infoGen);
    //		byte[] bytesToSign = getAuthenticatedAttributesBytes(gen, ogg.getOggetto());
    //		String attrPrintout = getAuthenticatedAttributesPrintout(bytesToSign);
    //		byte[] digestBytes = null;
    //		// calculate digest
    //		java.security.MessageDigest md;
    //		try {
    //		    md = java.security.MessageDigest.getInstance(CMSSignedDataGenerator.DIGEST_SHA256);
    //		    md.update(bytesToSign);
    //		    digestBytes = md.digest();
    //		} catch (NoSuchAlgorithmException e) {
    //		    log.error("loadData: ", e);
    //		    throw e;
    //		}
    //		byte[] digestInfoBytes = encapsulateInDigestInfo(CMSSignedDataGenerator.DIGEST_SHA256, digestBytes);
    //		String storeKey = formatAsString(digestInfoBytes, "");
    //		SignerInfoGeneratorItem s = new SignerInfoGeneratorItem(gen, attrPrintout);
    //		// save generator and printout
    //		// this.signerInfoGeneratorTable.put(storeKey, s);
    //		request.getSession().setAttribute(storeKey, s);
    //		out.print(base64Encode(digestInfoBytes));
    //	    } else if (retrieve.equals("AUTHENTICATED_ATTRIBUTES_PRINTOUT")) {
    //		String base64Hash = (String) request.getParameter("encodedhash");
    //		log.info("loadData: encodedhash={}", request.getParameter("encodedhash"));
    //		if (base64Hash != null) {
    //		    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    //		    byte[] hash = decoder.decodeBuffer(base64Hash);
    //		    //SignerInfoGeneratorItem s = (SignerInfoGeneratorItem) this.signerInfoGeneratorTable.get(formatAsString(hash, ""));
    //		    SignerInfoGeneratorItem s = (SignerInfoGeneratorItem) request.getSession().getAttribute(formatAsString(hash, ""));
    //		    out.print(s.getAttrPrintout());
    //		}
    //	    } else {
    //		out.println("Error: value '" + retrieve + "' for required parameter 'retrive' not expected.");
    //	    }
    //	    out.flush();
    //	    exit = true;
    //	} else {
    //	    //PrintWriter out = response.getWriter();
    //	    //out.println("Error: required parameter 'retrive' not found.");
    //	    //out.flush();
    //	}
    //	log.info("loadData: exit={}", exit);
    //	return exit;
    //    }
    //
    //    /**
    //     * Formats a byte[] as an hexadecimal String, interleaving bytes with a separator string.
    //     * 
    //     * @param bytes
    //     *            the byte[] to format.
    //     * @param byteSeparator
    //     *            the string to be used to separate bytes.
    //     * 
    //     * @return the formatted string.
    //     */
    //    public String formatAsString(byte[] bytes, String byteSeparator) {
    //
    //	int n, x;
    //	String w = new String();
    //	String s = new String();
    //	for (n = 0; n < bytes.length; n++) {
    //	    x = (int) (0x000000FF & bytes[n]);
    //	    w = Integer.toHexString(x).toUpperCase();
    //	    if (w.length() == 1)
    //		w = "0" + w;
    //	    s = s + w + ((n + 1 == bytes.length) ? "" : byteSeparator);
    //	} // for
    //	return s;
    //    }
    //
    //    /**
    //     * Class encapsulating a SignerInfoGenerator-related informations to be stored after a signature request.
    //     * 
    //     * @author Roberto Resoli
    //     */
    //    private class SignerInfoGeneratorItem {
    //
    //	private String attrPrintout = null;
    //	private ExternalSignatureSignerInfoGenerator sig = null;
    //
    //	/**
    //	 * Constructor.
    //	 * 
    //	 * @param aSig
    //	 *            the {@link ExternalSignatureSignerInfoGenerator}
    //	 * @param aPrintOut
    //	 *            the authenticated attributes textual dump at the time of the request.
    //	 */
    //	public SignerInfoGeneratorItem(ExternalSignatureSignerInfoGenerator aSig, String aPrintOut) {
    //
    //	    sig = aSig;
    //	    attrPrintout = aPrintOut;
    //	}
    //
    //	/**
    //	 * The attributes printout getter.
    //	 * 
    //	 * @return the authenticated attributes textual dump at the time of the request.
    //	 */
    //	public String getAttrPrintout() {
    //
    //	    return attrPrintout;
    //	}
    //
    //	/**
    //	 * The ExternalSignatureSignerInfoGenerator getter.
    //	 * 
    //	 * @return the ExternalSignatureSignerInfoGenerator for encapsulating signer informations.
    //	 */
    //	public ExternalSignatureSignerInfoGenerator getSig() {
    //
    //	    return sig;
    //	}
    //    }
    //
    //    /**
    //     * Converts the provided <code>certBytes</code> in a <code>java.security.cert.X509Certificate</code>, gets from it
    //     * the signer public key, and uses it to decrypt <code>sigBytes</code>. The decryption result is returned as a
    //     * formatted exadecimal string; see {@link CMSServlet} for details.
    //     * 
    //     * @param sigBytes
    //     *            signature bytes
    //     * @param certBytes
    //     *            certificate bytes
    //     * @return the decryption of sigBytes using the RSA/ECB/PKCS1PADDING Algorithm.
    //     */
    //    private String deriveStoreKey(byte[] sigBytes, byte[] certBytes) {
    //
    //	String key = null;
    //	java.security.cert.CertificateFactory cf;
    //	try {
    //	    cf = java.security.cert.CertificateFactory.getInstance("X.509");
    //	    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certBytes);
    //	    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
    //	    PublicKey pubKey = javaCert.getPublicKey();
    //	    try {
    //		//log.debug("Deriving store key from signature and certificate.");
    //		//log.debug("N.B.:This serves also as signature verification!");
    //		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1PADDING", "BC");
    //		c.init(Cipher.DECRYPT_MODE, pubKey);
    //		byte[] decBytes = null;
    //		/*
    //		if (false)
    //			decBytes = derDecode(c.doFinal(sigBytes));
    //		else
    //		*/
    //		decBytes = c.doFinal(sigBytes);
    //		key = formatAsString(decBytes, "");
    //	    } catch (NoSuchAlgorithmException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (NoSuchPaddingException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (InvalidKeyException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (IllegalStateException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (IllegalBlockSizeException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (BadPaddingException e) {
    //		log.error("deriveStoreKey()", e);
    //	    } catch (NoSuchProviderException e) {
    //		log.error("deriveStoreKey()", e);
    //	    }
    //	} catch (CertificateException e) {
    //	    log.error("deriveStoreKey()", e);
    //	}
    //	return key;
    //    }
    //
    //    /**
    //     * Gets the <code>ExternalSignatureSignerInfoGenerator</code> generator which originally produced the given
    //     * <code>storeKey</code>.
    //     * 
    //     * @param storeKey
    //     * @return the {@link ExternalSignatureSignerInfoGenerator} associated with the<code>storeKey</code>
    //     */
    //    private ExternalSignatureSignerInfoGenerator retriveSignerInfoGenerator(String storeKey, HttpServletRequest request) {
    //
    //	log.debug("Retrieving signerInfoGenerator using key: {}", storeKey);
    //	// ExternalSignatureSignerInfoGenerator info = ((SignerInfoGeneratorItem) this.signerInfoGeneratorTable.get(storeKey)).getSig();
    //	ExternalSignatureSignerInfoGenerator info = ((SignerInfoGeneratorItem) request.getSession().getAttribute(storeKey)).getSig();
    //	if (info != null)
    //	    log.debug("Generator found. Signature is verified.");
    //	else
    //	    log.debug("Generator not found! Signature is NOT verified!");
    //	// remove infos from store
    //	// this.signerInfoGeneratorTable.remove(storeKey);
    //	request.getSession().removeAttribute(storeKey);
    //	return info;
    //    }
    //
    //    /**
    //     * Builds the CMS signed data message.
    //     * 
    //     * @param infoGen
    //     *            the {@link ExternalSignatureSignerInfoGenerator} wrapping signer informations
    //     * @param sigBytes
    //     *            the digest encrypted with signer private key.
    //     * @param certBytes
    //     *            the signer certificate.
    //     * @return the {@link CMSSignedData} message.
    //     */
    //    @SuppressWarnings({ "rawtypes", "unchecked" })
    //    private CMSSignedData buildCMSSignedData(ExternalSignatureSignerInfoGenerator infoGen, byte[] sigBytes, byte[] certBytes, byte[] rawBytes) {
    //
    //	CMSSignedData result = null;
    //	log.debug("building CMSSignedData.");
    //	CMSProcessable msg = new CMSProcessableByteArray(rawBytes);
    //	// questa versione del generatore è priva della classe interna per
    //	// la generazione delle SignerInfo, che è stata promossa a classe a
    //	// sè.
    //	ExternalSignatureCMSSignedDataGenerator gen = new ExternalSignatureCMSSignedDataGenerator();
    //	// Conterrà la lista dei certificati; come minimo dovrà
    //	// contenere i certificati dei firmatari; opzionale, ma
    //	// consigliabile,
    //	// l'aggiunta dei certificati root per completare le catene di
    //	// certificazione.
    //	ArrayList certList = new ArrayList();
    //	// get Certificate
    //	java.security.cert.CertificateFactory cf;
    //	try {
    //	    cf = java.security.cert.CertificateFactory.getInstance("X.509");
    //	    java.io.ByteArrayInputStream bais1 = new java.io.ByteArrayInputStream(certBytes);
    //	    java.security.cert.X509Certificate javaCert = (java.security.cert.X509Certificate) cf.generateCertificate(bais1);
    //	    infoGen.setCertificate(javaCert);
    //	    infoGen.setSignedBytes(sigBytes);
    //	    certList.add(javaCert);
    //	    gen.addSignerInf(infoGen);
    //	    if (certList.size() != 0) {
    //		// Per passare i certificati al generatore li si incapsula in un
    //		// CertStore.
    //		CertStore store;
    //		store = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
    //		log.debug("Adding certificates ... ");
    //		gen.addCertificatesAndCRLs(store);
    //		// Finalmente, si può creare il l'oggetto CMS.
    //		log.debug("Generating CMSSignedData ");
    //		result = gen.generate(msg, true);
    //	    }
    //	} catch (CertificateException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	} catch (InvalidAlgorithmParameterException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	} catch (NoSuchAlgorithmException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	} catch (NoSuchProviderException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	} catch (CertStoreException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	} catch (CMSException e) {
    //	    log.error("buildCMSSignedData()", e);
    //	}
    //	return result;
    //    }
    //
    //    /**
    //     * Saves a CMS signed data file on the server file system; the extension should be ".p7m" according to italian
    //     * rules.
    //     * 
    //     * @param s
    //     *            the {@link CMSSignedData} object to save.
    //     * @param filePath
    //     *            full path of the file.
    //     * @return true if the file was correctly saved, false otherwise.
    //     */
    //    private boolean saveFile(CMSSignedData s, Oggetti ogg) {
    //
    //	try {
    //	    log.info("saveFile(): codiceoggetto={}", ogg.getId().getCodice());
    //	    ogg.setNomefile(ogg.getNomefile() + ".p7m");
    //	    log.info("saveFile(): nomefile={}", ogg.getNomefile());
    //	    ogg.setOggetto(s.getEncoded());
    //	    oggettiService.update(ogg);
    //	    return true;
    //	} catch (Exception e) {
    //	    log.error("saveFile(): codiceoggetto={}", ogg.getId().getCodice(), e);
    //	    return false;
    //	}
    //    }
    //
    //    /**
    //     * Creates a {@link ExternalSignatureSignerInfoGenerator} with a <code>MD5</code> digest algorithm and
    //     * <code>RSA</code> encryption algorithm.
    //     * 
    //     * @return the <code>ExternalSignatureSignerInfoGenerator</code> object
    //     */
    //    private ExternalSignatureSignerInfoGenerator buildSignerInfoGenerator() {
    //
    //	log.debug("Building SignerInfoGenerator.");
    //	ExternalSignatureSignerInfoGenerator gen = new ExternalSignatureSignerInfoGenerator(CMSSignedDataGenerator.DIGEST_SHA256,
    //		CMSSignedDataGenerator.ENCRYPTION_RSA);
    //	return gen;
    //    }
    //
    //    /**
    //     * A BASE64 encoding function, using the <code>sun.misc.BASE64Encoder</code> implementation.
    //     * 
    //     * @param bytes
    //     *            the bytes to encode.
    //     * @return the <code>BASE64</code> encoding of <code>bytes</code>.
    //     */
    //    private String base64Encode(byte[] bytes) {
    //
    //	sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    //	return encoder.encode(bytes);
    //    }
    //
    //    /**
    //     * Uses the provided {@link ExternalSignatureSignerInfoGenerator} for calculating the authenticated attributes bytes
    //     * to be digested-encrypted by the signer. Note that the attributes include a timestamp, so the result is
    //     * time-dependent!
    //     * 
    //     * @param signerGenerator
    //     *            the <code>ExternalSignatureSignerInfoGenerator</code> object that does the job.
    //     * @return the bytes to be signed.
    //     */
    //    private byte[] getAuthenticatedAttributesBytes(ExternalSignatureSignerInfoGenerator signerGenerator, byte[] rawBytes) {
    //
    //	log.debug("Building AuthenticatedAttributes.");
    //	byte[] bytesToSign = null;
    //	try {
    //	    CMSProcessable msg = new CMSProcessableByteArray(rawBytes);
    //	    bytesToSign = signerGenerator.getBytesToSign(PKCSObjectIdentifiers.data, msg, "BC");
    //	} catch (Exception e) {
    //	    log.error("getAuthenticatedAttributesBytes()", e);
    //	}
    //	return bytesToSign;
    //    }
    //
    //    /**
    //     * A text message resulting from a dump of provided authenticated attributes data. Shows, among other things, the
    //     * embedded timestamp attribute.
    //     * 
    //     * @param bytes
    //     *            the ASN.1 DER set of authenticated attributes.
    //     * @return the attributes textual dump.
    //     */
    //    @SuppressWarnings("rawtypes")
    //    private String getAuthenticatedAttributesPrintout(byte[] bytes) {
    //
    //	log.debug("getAuthenticatedAttributesPrintout");
    //	StringWriter printout = new StringWriter();
    //	PrintWriter pw = new PrintWriter(printout);
    //	try {
    //	    ASN1StreamParser a1p = new ASN1StreamParser(bytes);
    //	    DERSetParser signedAttributesParser = (DERSetParser) a1p.readObject();
    //	    ASN1Set set = ASN1Set.getInstance(signedAttributesParser.getDERObject());
    //	    AttributeTable attr = new AttributeTable(set);
    //	    Iterator iter = attr.toHashtable().values().iterator();
    //	    pw.println("Listing authenticated attributes:");
    //	    int count = 1;
    //	    while (iter.hasNext()) {
    //		Attribute a = (Attribute) iter.next();
    //		pw.println("Attribute " + count + ":");
    //		if (a.getAttrType().getId().equals(CMSAttributes.signingTime.getId())) {
    //		    Time time = Time.getInstance(a.getAttrValues().getObjectAt(0));
    //		    pw.println("Authenticated time (SERVER local time): " + time.getDate());
    //		}
    //		if (a.getAttrType().getId().equals(CMSAttributes.contentType.getId())) {
    //		    if (CMSObjectIdentifiers.data.getId().equals(DERObjectIdentifier.getInstance(a.getAttrValues().getObjectAt(0)).getId()))
    //			pw.println("Content Type: PKCS7_DATA");
    //		}
    //		if (a.getAttrType().getId().equals(CMSAttributes.messageDigest.getId())) {
    //		    byte[] md = DEROctetString.getInstance(a.getAttrValues().getObjectAt(0)).getOctets();
    //		    pw.println("Message Digest (SHA-256 hash of data content): " + formatAsString(md, " "));
    //		}
    //		if (a.getAttrType().getId().equals(PKCSObjectIdentifiers.id_aa_signingCertificateV2.getId())) {
    //		    pw.println("Signing Certificate V2");
    //		}
    //		pw.println("\nAttribute dump follows:");
    //		pw.println(ASN1Dump.dumpAsString(a) + "\n");
    //		count++;
    //	    }
    //	} catch (Exception e) {
    //	    log.error("getAuthenticatedAttributesPrintout()", e);
    //	    pw.println(e);
    //	    return null;
    //	}
    //	pw.flush();
    //	return printout.toString();
    //    }
    //
    //    private byte[] encapsulateInDigestInfo(String digestAlg, byte[] digestBytes) throws IOException {
    //
    //	ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    //	DEROutputStream dOut = new DEROutputStream(bOut);
    //	DERObjectIdentifier digestObjId = new DERObjectIdentifier(digestAlg);
    //	AlgorithmIdentifier algId = new AlgorithmIdentifier(digestObjId, null);
    //	DigestInfo dInfo = new DigestInfo(algId, digestBytes);
    //	dOut.writeObject(dInfo);
    //	return bOut.toByteArray();
    //    }
}
