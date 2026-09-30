package it.gruppoinit.pal.gp.pay.service.helper;

public class CodiceAvvisoHelper {

    public static final String DEFAULT_AUX_DIGIT = "3";
    public static final String DEFAULT_APPLICATION_CODE = "";
    private String iuv;
    private String auxDigit;
    private String applicationCode;

    private CodiceAvvisoHelper() {

	super();
    }

    public CodiceAvvisoHelper(String iuv) {

	this(iuv, DEFAULT_AUX_DIGIT, DEFAULT_APPLICATION_CODE);
    }

    public CodiceAvvisoHelper(String iuv, String auxDigit, String applicationCode) {

	this();
	this.iuv = iuv;
	this.auxDigit = auxDigit;
	this.applicationCode = applicationCode;
    }

    public String getCodiceAvviso() {

	StringBuilder sbIuv = new StringBuilder(this.auxDigit).append(this.applicationCode).append(iuv);
	return sbIuv.toString();
    }
}
