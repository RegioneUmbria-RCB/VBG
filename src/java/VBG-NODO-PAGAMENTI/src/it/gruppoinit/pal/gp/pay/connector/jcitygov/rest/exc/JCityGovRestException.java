package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.exc;

public class JCityGovRestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6783585491724713982L;
	private Integer errorCode;
	private String bodyResponse;
	
	public JCityGovRestException() {
		super();
	}

	public JCityGovRestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JCityGovRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public JCityGovRestException(String message) {
		super(message);
	}

	public JCityGovRestException(Throwable cause) {
		super(cause);
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getBodyResponse() {
		return bodyResponse;
	}

	public void setBodyResponse(String bodyResponse) {
		this.bodyResponse = bodyResponse;
	}

	public JCityGovRestException errorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	
	public JCityGovRestException bodyResponse(String bodyResponse) {
		this.bodyResponse = bodyResponse;
		return this;
	}
	
}
