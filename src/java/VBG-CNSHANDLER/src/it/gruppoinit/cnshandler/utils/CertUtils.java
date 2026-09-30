package it.gruppoinit.cnshandler.utils;

import it.cefriel.utility.security.X509CertificateHelper;
import it.cefriel.utility.security.exceptions.CertificateUnknownException;
import it.cefriel.utility.smartcard.CardUserData;
import it.people.sirac.smartcardprofile.SmartCardProfile;
import it.people.sirac.smartcardprofile.SmartCardProfiles;

import java.security.cert.X509Certificate;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertUtils {

    private static final Log log = LogFactory.getLog(CertUtils.class);

    public static SmartCardProfile getCertificateProfile(X509Certificate sessionCert, SmartCardProfiles smartCardProfiles) {

	String issuerDN = sessionCert.getIssuerDN().getName();
	String certDN = sessionCert.getSubjectDN().getName();
	return getCertificateProfile(issuerDN, certDN, smartCardProfiles);
    }

    public static SmartCardProfile getCertificateProfile(String issuerDN, String certDN, SmartCardProfiles smartCardProfiles) {

	SmartCardProfile smartCardProfile = null;
	String issuerCN = X509CertificateHelper.getIssuerCN(issuerDN);
	try {
	    smartCardProfile = smartCardProfiles.getProfileByIssuerAndCertDN(issuerCN, certDN);
	} catch (Exception e) {
	    log.error("getCertificateProfile() issuerDN: " + issuerDN + ", subjectDN: " + certDN, e);
	}
	return smartCardProfile;
    }

    public static CardUserData getUserInfo(X509Certificate sessionCert, SmartCardProfile smartCardProfile) throws CertificateUnknownException {

	String issuerDN = sessionCert.getIssuerDN().getName();
	String certDN = sessionCert.getSubjectDN().getName();
	return getUserInfo(issuerDN, certDN, smartCardProfile);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static CardUserData getUserInfo(String issuerDN, String certDN, SmartCardProfile smartCardProfile) throws CertificateUnknownException {

	HashMap certificateInfoMap = null;
	try {
	    certificateInfoMap = SmartCardProfiles.parseCertDN(certDN, smartCardProfile.getDNPattern());
	} catch (Exception e) {
	    log.error("getUserInfo() - Unknown certificate received.", e);
	    String issuerCN = X509CertificateHelper.getIssuerCN(issuerDN);
	    throw new CertificateUnknownException("Certificate Issuer: " + issuerCN + ", Certificate DN: " + certDN);
	}
	certificateInfoMap.put("TipoCarta", smartCardProfile.getCardType());
	certificateInfoMap.put("DatePattern", smartCardProfile.getDatePattern());
	return new CardUserData(certificateInfoMap);
    }

    public static HashMap parseDatiPersonaliRawFixed(byte[] datiPersonaliRaw0, String cardType) {

	HashMap datiPersonaliMap = new HashMap();
	if (datiPersonaliRaw0 == null) {
	    return datiPersonaliMap;
	}
	int datiPersonaliLength = 0;
	try {
	    if ("CIE".equalsIgnoreCase(cardType)) {
		datiPersonaliLength = Integer.parseInt(new String(datiPersonaliRaw0, 0, 6), 16);
	    } else if (("CNS".equalsIgnoreCase(cardType)) || ("CRS".equalsIgnoreCase(cardType)) || ("Actalis".equalsIgnoreCase(cardType))) {
		datiPersonaliLength = Integer.parseInt(new String(datiPersonaliRaw0, 0, 6), 16) - 6;
	    }
	    byte[] datiPersonaliRaw = new byte[datiPersonaliLength];
	    System.arraycopy(datiPersonaliRaw0, 6, datiPersonaliRaw, 0, datiPersonaliLength);
	    String[] personalDataAttributeNames = { "ComuneEmittente", "DataEmissione", "DataScadenza", "Cognome", "Nome", "DataNascita", "Sesso",
		    "Statura", "CodiceFiscale", "CodiceCittadinanza", "ComuneNascita", "StatoEsteroNascita", "EstremiAttoNascita", "ComuneResidenza",
		    "IndirizzoResidenza", "ValiditaEspatrio" };
	    int curFieldLength = 0;
	    int curFieldOffset = 0;
	    int curFieldNameIndex = 0;
	    String curFieldName = null;
	    String curFieldValue = null;
	    while ((curFieldOffset < datiPersonaliLength) && (curFieldNameIndex < personalDataAttributeNames.length)) {
		curFieldLength = Integer.parseInt(new String(datiPersonaliRaw, curFieldOffset, 2), 16);
		curFieldOffset += 2;
		if (curFieldLength > 0) {
		    curFieldName = personalDataAttributeNames[curFieldNameIndex];
		    curFieldValue = new String(datiPersonaliRaw, curFieldOffset, curFieldLength);
		} else {
		    curFieldName = personalDataAttributeNames[curFieldNameIndex];
		    curFieldValue = "";
		}
		datiPersonaliMap.put(curFieldName, curFieldValue);
		if (log.isDebugEnabled()) {
		    log.debug("Offset=" + curFieldOffset + ", Len Campo=" + curFieldLength + ", Nome=" + curFieldName + ", Valore=" + curFieldValue);
		}
		curFieldOffset += curFieldLength;
		++curFieldNameIndex;
	    }
	} catch (Exception e) {
	    log.error("parseDatiPersonaliRawFixed: ", e);
	}
	return datiPersonaliMap;
    }
}
