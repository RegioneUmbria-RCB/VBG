package it.gruppoinit.pal.gp.core.service.helper;

public class MessageTracciato450Helper {

    private String message;
    private boolean isError;

    public MessageTracciato450Helper(String message, boolean isError) {

	this.isError = isError;
	this.message = message;
    }

    public String getMessage() {

	return message;
    }

    public void setMessage(String message) {

	this.message = message;
    }

    public boolean getIsError() {

	return isError;
    }

    public void setError(boolean isError) {

	this.isError = isError;
    }
}
