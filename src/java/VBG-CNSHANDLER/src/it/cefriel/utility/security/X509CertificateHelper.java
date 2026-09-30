package it.cefriel.utility.security;

import it.cefriel.utility.security.exceptions.InvalidCertChainException;
import it.cefriel.utility.security.exceptions.NoCertChainException;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ca.commons.security.asn1.ASN1Object;
import com.ca.commons.security.asn1.ASN1Type;
import com.ca.commons.security.asn1.DERCoder;
import com.ca.commons.security.cert.extensions.CRLDistributionPoints;
import com.ca.commons.security.cert.extensions.V3Extension;

public class X509CertificateHelper {

    private static final Logger logger = Logger.getLogger(X509CertificateHelper.class);

    public static boolean isCertificateChainValid(X509Certificate[] certificateChain) {

	boolean isValid = true;
	for (int i = 0; i < certificateChain.length; i++) {
	    try {
		certificateChain[i].checkValidity();
	    } catch (CertificateExpiredException ex) {
		logger.error("CertificateUtils::isCertificateChainValid() - The certificate chain is not valid. The certificate "
			+ certificateChain[i] + " has expired!");
		isValid = false;
		break;
	    } catch (CertificateNotYetValidException ex) {
		logger.error("CertificateUtils::isCertificateChainValid() - The certificate chain is not valid. The certificate "
			+ certificateChain[i] + " is not yet valid!");
		isValid = false;
		break;
	    }
	}
	return isValid;
    }

    private static List parseCrlDistributionPoints(String crlDistributionPointsString) {

	int pos = 0;
	String workString = new String(crlDistributionPointsString);
	List crlDistributionPoints = new ArrayList();
	while ((pos = workString.indexOf("uRID:")) >= 0) {
	    workString = workString.substring(pos + 5);
	    int nextPos = workString.indexOf("uRID:");
	    String token = null;
	    if (nextPos >= 0)
		token = workString.substring(0, nextPos);
	    else {
		token = workString;
	    }
	    if (token.endsWith("\n")) {
		token = token.substring(0, token.length() - 1);
	    }
	    token = URLDecoder.decode(token.trim());
	    crlDistributionPoints.add(token);
	}
	return crlDistributionPoints;
    }

    public static boolean isSelfSigned(X509Certificate cert) {

	Principal issuer = cert.getIssuerDN();
	Principal subject = cert.getSubjectDN();
	return issuer.toString().equals(subject.toString());
    }

    public static X509Certificate findIssuerCert(X509Certificate cert, List chain) {

	for (int i = 0; i < chain.size(); i++) {
	    X509Certificate chainCert = (X509Certificate) chain.get(i);
	    String issuer = cert.getIssuerDN().toString();
	    String subject = chainCert.getSubjectDN().toString();
	    if (issuer.equals(subject)) {
		return chainCert;
	    }
	}
	return null;
    }

    public static List createOrderedCertChain(List unorderedChain, X509Certificate leafCert) throws Exception {

	if (unorderedChain.size() == 1) {
	    throw new NoCertChainException("The certificate chain is empty");
	}
	List tempList = new ArrayList();
	List orderedList = new ArrayList();
	orderedList.add(leafCert);
	X509Certificate tempCert = leafCert;
	for (int i = 0; i < unorderedChain.size(); i++) {
	    tempList.add(unorderedChain.get(i));
	}
	while (!isSelfSigned(tempCert)) {
	    orderedList.add(tempCert);
	    tempList.remove(tempCert);
	    tempCert = findIssuerCert(tempCert, tempList);
	    if (tempCert == null) {
		throw new InvalidCertChainException("No signing certificate found for certificate " + tempCert);
	    }
	}
	orderedList.add(tempCert);
	return orderedList;
    }

    public static List getCRLDistributionPoints(X509Certificate cert) {

	byte[] crlDistPoints = cert.getExtensionValue("2.5.29.31");
	try {
	    DERCoder derCoder = new DERCoder();
	    ASN1Object ext = derCoder.decode(crlDistPoints);
	    ASN1Object rext = null;
	    if (ext.isASN1Type(ASN1Type.OCTET_STRING)) {
		rext = derCoder.decode((byte[]) ext.getValue());
	    }
	    V3Extension v3e = new CRLDistributionPoints();
	    v3e.init(rext);
	    if (v3e.toString() == null) {
		throw new Exception("Could not read CRL distribution point");
	    }
	    return parseCrlDistributionPoints(v3e.toString());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return null;
    }

    public static String getIssuerCN(X509Certificate certificate) {

	String issuerDN = certificate.getIssuerDN().getName();
	return getIssuerCN(issuerDN);
    }

    public static String getIssuerCN(String issuerDN) {

	String l_token = "CN=";
	char l_separator = ',';
	int l_indx1 = issuerDN.indexOf(l_token);
	int l_indx2 = issuerDN.indexOf(l_separator, l_indx1 + 2);
	if (l_indx2 == -1)
	    l_indx2 = issuerDN.length();
	return issuerDN.substring(l_indx1 + l_token.length(), l_indx2);
    }

    public static Certificate buildCertificate(byte[] certificateBytes) {

	try {
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    return cf.generateCertificate(new ByteArrayInputStream(certificateBytes));
	} catch (CertificateException e) {
	    e.printStackTrace();
	}
	return null;
    }
}