package it.paevolution.fileconverter2.health.model;

/**
 * 
 */
public class GotenbergHealthStatus {

    private String status;
    private GotenbergHealthDetails details;

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public GotenbergHealthDetails getDetails() {

	return details;
    }

    public void setDetails(GotenbergHealthDetails details) {

	this.details = details;
    }

    @Override
    public String toString() {

	return "status: " + getStatus() + ", details: [" + getDetails() + "]";
    }
}
