package it.alveo.firmaremota.aruba.configurazione;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "aruba")
@Configuration
public class ArubaParams {

    private String tempPath;
    private String processatiPath;
    private String processFileName;

    public String getTempPath() {

	return tempPath;
    }

    public void setTempPath(String tempPath) {

	this.tempPath = tempPath;
    }

    public String getProcessatiPath() {

	return processatiPath;
    }

    public void setProcessatiPath(String processatiPath) {

	this.processatiPath = processatiPath;
    }

    public String getProcessFileName() {

	return processFileName;
    }

    public void setProcessFileName(String processFileName) {

	this.processFileName = processFileName;
    }
}
