package it.gruppoinit.dss.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility da eseguire da riga di comando: aggiunge al keystore dell'utente
 * solo i certificati OJ presenti nella keystore della demo DSS che mancano.
 * <p>
 * Uso: {@code java -cp ... it.gruppoinit.dss.config.MergeOjKeystore <tuo-keystore.p12> [password] [path-demo-keystore]}
 * Esempio: {@code mvn exec:java -Dexec.mainClass="it.gruppoinit.dss.config.MergeOjKeystore" -Dexec.args="src/keystore.p12"}
 * Se la password non è passata, viene richiesta a console.
 * Password keystore demo: dss-password.
 */
public final class MergeOjKeystore {

	private static final String DEFAULT_DEMO_KEYSTORE_PATH = "../dss-demonstrations/dss-demo-webapp/src/main/resources/keystore.p12";
	private static final String DEMO_KEYSTORE_PASSWORD = "dss-password";
	private static final String KEYSTORE_TYPE = "PKCS12";

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Uso: MergeOjKeystore <path-tuo-keystore.p12> [password] [path-keystore-demo]");
			System.err.println("  Aggiunge i certificati OJ della demo DSS che mancano nel tuo keystore.");
			System.err.println("  Esempio: mvn exec:java -Dexec.mainClass=\"it.gruppoinit.dss.config.MergeOjKeystore\" -Dexec.args=\"src/keystore.p12\"");
			System.exit(1);
		}
		String userKeystorePath = args[0];
		char[] userPassword = args.length >= 2 ? args[1].toCharArray() : readPassword("Password del TUO keystore: ");
		String demoKeystorePath = args.length >= 3 ? args[2] : DEFAULT_DEMO_KEYSTORE_PATH;

		KeyStore demoKs = KeyStore.getInstance(KEYSTORE_TYPE);
		try (FileInputStream fis = new FileInputStream(demoKeystorePath)) {
			demoKs.load(fis, DEMO_KEYSTORE_PASSWORD.toCharArray());
		}

		KeyStore userKs = KeyStore.getInstance(KEYSTORE_TYPE);
		try (FileInputStream fis = new FileInputStream(userKeystorePath)) {
			userKs.load(fis, userPassword);
		}

		Set<String> userFingerprints = new HashSet<>();
		Enumeration<String> userAliases = userKs.aliases();
		while (userAliases.hasMoreElements()) {
			String alias = userAliases.nextElement();
			if (userKs.isCertificateEntry(alias)) {
				Certificate cert = userKs.getCertificate(alias);
				if (cert != null) {
					userFingerprints.add(fingerprint(cert));
				}
			}
		}

		int added = 0;
		Enumeration<String> demoAliases = demoKs.aliases();
		while (demoAliases.hasMoreElements()) {
			String alias = demoAliases.nextElement();
			if (!demoKs.isCertificateEntry(alias)) continue;
			Certificate cert = demoKs.getCertificate(alias);
			if (cert == null) continue;
			String fp = fingerprint(cert);
			if (userFingerprints.contains(fp)) continue;
			// Evita alias duplicati nel keystore utente
			String targetAlias = alias;
			int suffix = 0;
			while (userKs.containsAlias(targetAlias)) {
				targetAlias = alias + "-oj-" + (++suffix);
			}
			userKs.setCertificateEntry(targetAlias, cert);
			userFingerprints.add(fp);
			added++;
			System.out.println("Aggiunto: " + targetAlias);
		}

		if (added > 0) {
			try (FileOutputStream fos = new FileOutputStream(userKeystorePath)) {
				userKs.store(fos, userPassword);
			}
			System.out.println("Scritti " + added + " certificati mancanti in " + userKeystorePath);
		} else {
			System.out.println("Nessun certificato da aggiungere: il tuo keystore contiene già tutti quelli della demo.");
		}
	}

	private static String fingerprint(Certificate cert) throws Exception {
		byte[] enc = cert.getEncoded();
		java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(enc);
		return Arrays.toString(digest);
	}

	private static char[] readPassword(String prompt) throws Exception {
		System.out.print(prompt);
		System.out.flush();
		java.io.Console con = System.console();
		if (con != null) {
			char[] pwd = con.readPassword();
			return pwd != null ? pwd : new char[0];
		}
		try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
			String line = sc.nextLine();
			return line != null ? line.toCharArray() : new char[0];
		}
	}
}
