package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public class BaseFolderCaricamento {

    public BaseFolderCaricamento(String endpointURL, String endpointUsername, String endpointPassword) {

	super();
	this.endpointURL = endpointURL;
	this.endpointUsername = endpointUsername;
	this.endpointPassword = endpointPassword;
    }

    private String endpointURL;
    private String endpointUsername;
    private String endpointPassword;

    public String getEndpointURL() {

	return endpointURL;
    }

    public void setEndpointURL(String endpointURL) {

	this.endpointURL = endpointURL;
    }

    public String getEndpointUsername() {

	return endpointUsername;
    }

    public void setEndpointUsername(String endpointUsername) {

	this.endpointUsername = endpointUsername;
    }

    public String getEndpointPassword() {

	return endpointPassword;
    }

    public void setEndpointPassword(String endpointPassword) {

	this.endpointPassword = endpointPassword;
    }
}
