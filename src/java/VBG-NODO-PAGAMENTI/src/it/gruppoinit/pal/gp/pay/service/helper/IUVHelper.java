/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.helper;

/**
 * @author francol
 *
 */
public class IUVHelper {

    public static final String DEFAULT_AUX_DIGIT = "3";
    public static final String DEFAULT_APPLICATION_CODE = "";
    private String iuv;
    private String auxDigit;
    private String applicationCode;

    public IUVHelper(String iuv) {

	this.iuv = iuv;
	this.auxDigit = DEFAULT_AUX_DIGIT;
	this.applicationCode = DEFAULT_APPLICATION_CODE;
    }

    public IUVHelper(String iuv, String auxDigit, String applicationCode) {

	this.iuv = iuv;
	this.auxDigit = auxDigit;
	this.applicationCode = applicationCode;
    }

    public String getIUV() {

	return this.iuv;
    }
}
