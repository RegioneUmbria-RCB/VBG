package it.gruppoinit.dss.launcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

/**
 * Avvia Tomcat embedded utilizzando la web application precedentemente
 * distribuita come WAR.
 */
public final class EmbeddedTomcatLauncher {

	private static final Logger LOG = Logger.getLogger(EmbeddedTomcatLauncher.class.getName());

	private static final int DEFAULT_PORT = 8080;
	private static final String CONTEXT_PATH = "/dss-webapp-2";

	private EmbeddedTomcatLauncher() {
	}

	public static void main(String[] args) throws Exception {

		int port = resolvePort();

		Path webappBase = WebappExtractor.extract();

		/*
		 * Directory di lavoro temporanea e univoca per questa istanza. Non è
		 * un'installazione di Tomcat: è semplicemente la sua directory runtime.
		 */
		Path tomcatBase = Files.createTempDirectory("dss-tomcat-");

		Tomcat tomcat = new Tomcat();

		tomcat.setBaseDir(tomcatBase.toAbsolutePath().toString());
		tomcat.setPort(port);

		/*
		 * Inizializza il connector HTTP.
		 */
		tomcat.getConnector();

		/*
		 * Registra la web application.
		 *
		 * Il web.xml continua a definire servlet, listener, mapping, session
		 * configuration, ecc.
		 */
		Context context = tomcat.addWebapp(CONTEXT_PATH, webappBase.toAbsolutePath().toString());

		context.setParentClassLoader(EmbeddedTomcatLauncher.class.getClassLoader());

		context.setReloadable(false);

		/*
		 * Avvio del Tomcat embedded.
		 */
		tomcat.start();

		LOG.info(() -> "DSS Web Application avviata sulla porta " + port + " con context path " + CONTEXT_PATH);

		LOG.info(() -> "Endpoint disponibili sotto " + CONTEXT_PATH + ": /api/*, /validazione, /wservice/*");

		/*
		 * Mantiene vivo il processo.
		 */
		tomcat.getServer().await();
	}

	private static int resolvePort() {

		String portValue = System.getenv("DSS_2_SERVER_PORT");

		if (portValue == null || portValue.isBlank()) {
			return DEFAULT_PORT;
		}

		try {
			int port = Integer.parseInt(portValue.trim());

			if (port < 1 || port > 65535) {
				throw new IllegalArgumentException("La porta deve essere compresa tra 1 e 65535: " + port);
			}

			return port;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Valore porta non valido: " + portValue, e);
		}
	}
}