/**
 * 
 */
package it.gruppoinit.pal.gp.core.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author Franco.Leone
 *
 */
public class FileHelperFactory {

    public enum Protocols {
	FILE,
	FTP;
    }

    public static IFileHelper getFileHelper(String accessUrl, String userName, String password) throws IOException {

	IFileHelper fh = null;
	if (StringUtils.isBlank(accessUrl)) {
	    throw new RuntimeException("URL della risorsa non specificato, impossibile determinare il file helper da utilizzare");
	}
	URL url = new URL(accessUrl);
	String protocol = url.getProtocol();
	if (protocol != null) {
	    if (protocol.equalsIgnoreCase(Protocols.FILE.name())) {
		fh = new FileSystemHelper(accessUrl, userName, password);
	    } else if (protocol.equalsIgnoreCase(Protocols.FTP.name())) {
		fh = new FTPHelper(accessUrl, userName, password);
	    }
	    else {
		throw new IllegalArgumentException("protocollo " + protocol + " non supportato");
	    }
	}
	return fh;
    }
}
