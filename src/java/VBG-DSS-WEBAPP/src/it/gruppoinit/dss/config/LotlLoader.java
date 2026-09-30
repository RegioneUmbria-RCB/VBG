package it.gruppoinit.dss.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.InitializingBean;

import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.spi.tsl.TrustedListsCertificateSource;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import eu.europa.esig.dss.spi.x509.KeyStoreCertificateSource;
import eu.europa.esig.dss.spi.x509.aia.DefaultAIASource;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.service.http.commons.FileCacheDataLoader;
import eu.europa.esig.dss.service.http.commons.OCSPDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.tsl.function.OfficialJournalSchemeInformationURI;
import eu.europa.esig.dss.tsl.job.TLValidationJob;
import eu.europa.esig.dss.tsl.source.LOTLSource;

/**
 * Inizializza la LOTL (List of Trusted Lists) europea e la registra come trust anchor
 * nel CertificateVerifier, così che le firme CIE e degli altri provider italiani
 * (e tutti i certificati qualificati UE) possano essere validate correttamente.
 * Opzionalmente carica un keystore aggiuntivo (es. radice CIE) come trust anchor.
 */
public class LotlLoader implements InitializingBean {

	private static final Logger LOG = Logger.getLogger(LotlLoader.class.getName());

	/** URL ufficiale della LOTL UE (include le Trusted List di tutti gli Stati membri, incluso l'Italia). */
	private static final String EU_LOTL_URL = "https://ec.europa.eu/tools/lotl/eu-lotl.xml";
	/** URL Gazzetta Ufficiale UE per i certificati di firma della LOTL (come nella demo DSS). */
	private static final String DEFAULT_OJ_URL = "https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=uriserv:OJ.C_.2019.276.01.0001.01.ENG";

	private CommonCertificateVerifier certificateVerifier;

	/** Path keystore certificati OJ (Official Journal) per verificare la firma della LOTL. Obbligatorio per caricare correttamente le TSL (es. Italia/CIE). */
	private String ojKeystorePath;
	private String ojKeystorePassword;
	private String ojKeystoreType = "PKCS12";
	/** URL pubblicazione Gazzetta Ufficiale UE dei certificati di firma LOTL. */
	private String ojUrl = DEFAULT_OJ_URL;

	/** Path opzionale keystore con radici aggiuntive (es. CIE). Classpath: nomefile.p12 o path assoluto. */
	private String trustedKeystorePath;
	/** Password keystore aggiuntivo (opzionale, può essere null o "" per keystore solo certificati). */
	private String trustedKeystorePassword;
	/** Tipo keystore (default PKCS12). */
	private String trustedKeystoreType = "PKCS12";

	public void setCertificateVerifier(CommonCertificateVerifier certificateVerifier) {
		this.certificateVerifier = certificateVerifier;
	}

	public void setOjKeystorePath(String ojKeystorePath) {
		this.ojKeystorePath = ojKeystorePath;
	}

	public void setOjKeystorePassword(String ojKeystorePassword) {
		this.ojKeystorePassword = ojKeystorePassword;
	}

	public void setOjKeystoreType(String ojKeystoreType) {
		this.ojKeystoreType = ojKeystoreType != null ? ojKeystoreType : "PKCS12";
	}

	public void setOjUrl(String ojUrl) {
		this.ojUrl = ojUrl != null ? ojUrl : DEFAULT_OJ_URL;
	}

	public void setTrustedKeystorePath(String trustedKeystorePath) {
		this.trustedKeystorePath = trustedKeystorePath;
	}

	public void setTrustedKeystorePassword(String trustedKeystorePassword) {
		this.trustedKeystorePassword = trustedKeystorePassword;
	}

	public void setTrustedKeystoreType(String trustedKeystoreType) {
		this.trustedKeystoreType = trustedKeystoreType != null ? trustedKeystoreType : "PKCS12";
	}

	@Override
	public void afterPropertiesSet() {
		LOG.info("[LOTL] Inizializzazione LOTL (List of Trusted Lists) UE...");
		if (certificateVerifier == null) {
			LOG.warning("[LOTL] CertificateVerifier non iniettato: skip inizializzazione LOTL. Le firme risulteranno INDETERMINATE.");
			return;
		}
		try {
			TrustedListsCertificateSource trustedListsCertificateSource = new TrustedListsCertificateSource();

			LOTLSource lotlSource = new LOTLSource();
			lotlSource.setUrl(EU_LOTL_URL);
			lotlSource.setPivotSupport(true);
			// Come nella demo DSS: senza certificati OJ la firma della LOTL non è verificata e le TSL nazionali (es. Italia/CIE) non vengono caricate.
			CertificateSource ojSource = loadOjKeystore();
			if (ojSource != null) {
				lotlSource.setCertificateSource(ojSource);
				lotlSource.setSigningCertificatesAnnouncementPredicate(new OfficialJournalSchemeInformationURI(ojUrl));
				LOG.info("[LOTL] Keystore OJ in uso: " + ojKeystorePath + " (verifica firma LOTL e caricamento TSL Italia/CIE)");
				LOG.info("[LOTL] Certificati OJ (Official Journal) configurati per la verifica della firma LOTL: " + ojUrl);
			} else {
				LOG.warning("[LOTL] Keystore OJ non configurato: la LOTL potrebbe non essere validata e le TSL (es. Italia/CIE) non essere caricate. Impostare ojKeystorePath (es. classpath:keystore.p12).");
			}

			CommonsDataLoader httpLoader = new CommonsDataLoader();
			FileCacheDataLoader fileLoader = new FileCacheDataLoader(httpLoader);

			TLValidationJob job = new TLValidationJob();
			job.setTrustedListCertificateSource(trustedListsCertificateSource);
			job.setListOfTrustedListSources(lotlSource);
			job.setOnlineDataLoader(fileLoader);
			job.setOfflineDataLoader(fileLoader);

			LOG.info("[LOTL] Download e parsing LOTL UE in corso: " + EU_LOTL_URL);
			job.onlineRefresh();

			eu.europa.esig.dss.model.tsl.TLValidationJobSummary summary = job.getSummary();
			if (summary != null) {
				LOG.info("[LOTL] Risultato caricamento: " + summary.toString());
			}

			certificateVerifier.addTrustedCertSources(trustedListsCertificateSource);
			int sourceCount = certificateVerifier.getTrustedCertSources() != null
				? certificateVerifier.getTrustedCertSources().getSources().size()
				: 0;
			LOG.info("[LOTL] LOTL UE caricata e registrata come trust anchor. Fonti trusted nel verifier: " + sourceCount);

			// AIA: consente di scaricare certificati intermedi mancanti (es. catena CIE incompleta nel PDF)
			CommonsDataLoader aiaDataLoader = new CommonsDataLoader();
			DefaultAIASource aiaSource = new DefaultAIASource();
			aiaSource.setDataLoader(aiaDataLoader);
			certificateVerifier.setAIASource(aiaSource);
			LOG.info("[LOTL] AIA (Authority Information Access) abilitato per il recupero certificati intermedi.");

			// CRL e OCSP: necessari per il controllo revoche (senza questi: "No revocation data found" → CERTIFICATE_CHAIN_GENERAL_FAILURE)
			CommonsDataLoader crlDataLoader = new CommonsDataLoader();
			OnlineCRLSource crlSource = new OnlineCRLSource();
			crlSource.setDataLoader(crlDataLoader);
			certificateVerifier.setCrlSource(crlSource);
			OCSPDataLoader ocspDataLoader = new OCSPDataLoader();
			OnlineOCSPSource ocspSource = new OnlineOCSPSource();
			ocspSource.setDataLoader(ocspDataLoader);
			certificateVerifier.setOcspSource(ocspSource);
			certificateVerifier.setCheckRevocationForUntrustedChains(false);
			LOG.info("[LOTL] CRL e OCSP configurati per il controllo revoche (CIE/OCSP italiano raggiungibile in rete).");

			// Keystore opzionale con radici aggiuntive (es. CIE): se la LOTL/TSL non contiene il root CIE,
			// aggiungere qui un keystore con il certificato radice CIE (es. da cartaidentita.interno.gov.it).
			if (trustedKeystorePath != null && !trustedKeystorePath.isEmpty()) {
				loadAdditionalTrustedKeystore();
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "[LOTL] Errore durante il caricamento della LOTL: le firme potrebbero risultare INDETERMINATE (NO_CERTIFICATE_CHAIN_FOUND).", e);
			throw new IllegalStateException("Impossibile inizializzare la LOTL per la validazione firme.", e);
		}
	}

	/**
	 * Carica il keystore OJ (Official Journal) per la verifica della firma della LOTL.
	 * Restituisce null se ojKeystorePath non è impostato o il caricamento fallisce.
	 */
	private CertificateSource loadOjKeystore() {
		if (ojKeystorePath == null || ojKeystorePath.isEmpty()) {
			return null;
		}
		String path = ojKeystorePath.trim();
		char[] password = (ojKeystorePassword != null) ? ojKeystorePassword.toCharArray() : new char[0];
		boolean fromClasspath = path.startsWith("classpath:");
		String resourcePath = fromClasspath ? path.substring("classpath:".length()).trim() : path;
		InputStream is = null;
		try {
			if (fromClasspath) {
				String cpPath = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
				is = getClass().getResourceAsStream(cpPath);
			} else if (path.startsWith("file:")) {
				is = new URL(path).openStream();
			} else {
				is = new FileInputStream(new File(resourcePath));
			}
			if (is == null) {
				LOG.warning("[LOTL] Keystore OJ non trovato: " + path);
				return null;
			}
			return new KeyStoreCertificateSource(is, ojKeystoreType, password);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "[LOTL] Impossibile caricare il keystore OJ " + path + ": " + e.getMessage(), e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
					// ignore
				}
			}
		}
	}

	/**
	 * Carica il keystore aggiuntivo (es. radice CIE) e lo registra come trust anchor.
	 * Il path può essere "classpath:file.p12" per risorse in classpath o un path di file assoluto.
	 */
	private void loadAdditionalTrustedKeystore() {
		String path = trustedKeystorePath.trim();
		char[] password = (trustedKeystorePassword != null) ? trustedKeystorePassword.toCharArray() : new char[0];
		boolean fromClasspath = path.startsWith("classpath:");
		String resourcePath = fromClasspath ? path.substring("classpath:".length()).trim() : path;

		InputStream is = null;
		try {
			if (fromClasspath) {
				String cpPath = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
				is = getClass().getResourceAsStream(cpPath);
			} else if (path.startsWith("file:")) {
				is = new URL(path).openStream();
			} else {
				is = new FileInputStream(new File(resourcePath));
			}
			if (is == null) {
				LOG.warning("[LOTL] Keystore aggiuntivo non trovato: " + path);
				return;
			}
			KeyStoreCertificateSource ksSource = new KeyStoreCertificateSource(is, trustedKeystoreType, password);
			int count = ksSource.getNumberOfCertificates();
			certificateVerifier.addTrustedCertSources(ksSource);
			LOG.info("[LOTL] Keystore aggiuntivo caricato come trust anchor: " + path + " (certificati: " + count + ")");
		} catch (Exception e) {
			LOG.log(Level.WARNING, "[LOTL] Impossibile caricare il keystore aggiuntivo " + path + ": " + e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
					// ignore
				}
			}
		}
	}
}
