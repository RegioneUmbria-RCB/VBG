package it.gruppoinit.dss.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Estrae la webapp (contenuto di {@code WebContent}) dalla classpath in una directory
 * temporanea su filesystem. Necessario per JSP (Jasper) e per {@code ServletContext.getRealPath}
 * usato da {@link it.gruppoinit.dss.servlet.ValidationServlet} e {@link it.gruppoinit.dss.servlet.CleanupSessionListener}.
 */
public final class WebappExtractor {

	private static final String WEBAPP_PREFIX = "webapp/";

	private WebappExtractor() {
	}

	public static Path extract() throws IOException {
		ClassLoader classLoader = WebappExtractor.class.getClassLoader();
		URL webappRoot = classLoader.getResource(WEBAPP_PREFIX);
		if (webappRoot == null) {
			throw new IOException("Risorsa classpath '" + WEBAPP_PREFIX + "' non trovata");
		}

		Path targetDir = Files.createTempDirectory("dss-webapp-");
		targetDir.toFile().deleteOnExit();

		if ("jar".equals(webappRoot.getProtocol())) {
			extractFromJar(webappRoot, targetDir);
		} else if ("file".equals(webappRoot.getProtocol())) {
			try {
				copyFromDirectory(Path.of(webappRoot.toURI()), targetDir);
			} catch (URISyntaxException e) {
				throw new IOException("URI webapp non valido: " + webappRoot, e);
			}
		} else {
			throw new IOException("Protocollo non supportato per webapp: " + webappRoot.getProtocol());
		}

		Files.createDirectories(targetDir.resolve("WEB-INF/files"));
		return targetDir;
	}

	private static void extractFromJar(URL webappRoot, Path targetDir) throws IOException {
		JarURLConnection connection = (JarURLConnection) webappRoot.openConnection();
		try (JarFile jarFile = connection.getJarFile()) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();
				if (!entryName.startsWith(WEBAPP_PREFIX) || entry.isDirectory()) {
					continue;
				}
				String relativePath = entryName.substring(WEBAPP_PREFIX.length());
				Path destination = targetDir.resolve(relativePath);
				Files.createDirectories(destination.getParent());
				try (InputStream inputStream = jarFile.getInputStream(entry)) {
					Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
	}

	private static void copyFromDirectory(Path sourceRoot, Path targetDir) throws IOException {
		try (var paths = Files.walk(sourceRoot)) {
			paths.forEach(sourcePath -> {
				try {
					Path relative = sourceRoot.relativize(sourcePath);
					Path destination = targetDir.resolve(relative.toString());
					if (Files.isDirectory(sourcePath)) {
						Files.createDirectories(destination);
					} else {
						Files.createDirectories(destination.getParent());
						Files.copy(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					throw new RuntimeException("Errore copia webapp da " + sourcePath, e);
				}
			});
		} catch (RuntimeException e) {
			if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			}
			throw e;
		}
	}
}
