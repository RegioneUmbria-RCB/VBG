package it.paevolution.fileconverter2.health.model;

public class GotenbergHealthElementStatus {

    private String status;
    private String timestamp;

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getTimestamp() {

	return timestamp;
    }

    public void setTimestamp(String timestamp) {

	this.timestamp = timestamp;
    }

    @Override
    public String toString() {

	return "status: " + getStatus() + ", timestamp: " + getTimestamp();
    }
}
