package it.sgp.middleware.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "security")
@Configuration
public class SecurityConfiguration {

    private boolean deleteTokenEnabled;
    private boolean deleteTokenSaveFile;
    private String deleteTokenCron;
    private String logsFileDir;

    public boolean isDeleteTokenEnabled() {

	return deleteTokenEnabled;
    }

    public void setDeleteTokenEnabled(boolean deleteTokenEnabled) {

	this.deleteTokenEnabled = deleteTokenEnabled;
    }

    public String getDeleteTokenCron() {

	return deleteTokenCron;
    }

    public void setDeleteTokenCron(String deleteTokenCron) {

	this.deleteTokenCron = deleteTokenCron;
    }

    public boolean isDeleteTokenSaveFile() {

	return deleteTokenSaveFile;
    }

    public void setDeleteTokenSaveFile(boolean deleteTokenSaveFile) {

	this.deleteTokenSaveFile = deleteTokenSaveFile;
    }

    public String getLogsFileDir() {

	return logsFileDir;
    }

    public void setLogsFileDir(String logsFileDir) {

	this.logsFileDir = logsFileDir;
    }
}