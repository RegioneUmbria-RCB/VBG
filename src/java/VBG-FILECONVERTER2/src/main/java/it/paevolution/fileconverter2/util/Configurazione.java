package it.paevolution.fileconverter2.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "config")
@Component
public class Configurazione {

    private String exportHtmlMatcherRegexp;
    private String exportHtmlReplaceString;
    private boolean gotenbergAttivo;
    private String gotenbergBaseUrl;
    private boolean gotenbergPdfA;
    private String gotenbergPdfAVersion;

    

	public String getExportHtmlMatcherRegexp() {

	return exportHtmlMatcherRegexp;
    }

    public void setExportHtmlMatcherRegexp(String exportHtmlMatcherRegexp) {

	this.exportHtmlMatcherRegexp = exportHtmlMatcherRegexp;
    }

    public String getExportHtmlReplaceString() {

	return exportHtmlReplaceString;
    }

    public void setExportHtmlReplaceString(String exportHtmlReplaceString) {

	this.exportHtmlReplaceString = exportHtmlReplaceString;
    }

    public boolean isGotenbergAttivo() {

	return gotenbergAttivo;
    }

    public void setGotenbergAttivo(boolean gotenbergAttivo) {

	this.gotenbergAttivo = gotenbergAttivo;
    }

    public String getGotenbergBaseUrl() {

	return gotenbergBaseUrl;
    }

    public void setGotenbergBaseUrl(String gotenbergBaseUrl) {

	this.gotenbergBaseUrl = gotenbergBaseUrl;
    }

    public boolean isGotenbergPdfA() {

	return gotenbergPdfA;
    }

    public void setGotenbergPdfA(boolean gotenbergPdfA) {

	this.gotenbergPdfA = gotenbergPdfA;
    }
    
    public String getGotenbergPdfAVersion() {
		return gotenbergPdfAVersion;
	}

	public void setGotenbergPdfAVersion(String gotenbergPdfAVersion) {
		this.gotenbergPdfAVersion = gotenbergPdfAVersion;
	}
}
