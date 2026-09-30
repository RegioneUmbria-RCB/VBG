package it.gruppoinit.infocamere.schema.legaldocs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gianpaolot
 *
 */
@XmlRootElement(name = "loginResponse")
public class LoginResponse {

    private String code;
    private String LDSessionId;
    private String pdv;

    public String getCode() {

	return code;
    }

    public void setCode(String code) {

	this.code = code;
    }

    public String getLDSessionId() {

	return LDSessionId;
    }

    public void setLDSessionId(String lDSessionId) {

	LDSessionId = lDSessionId;
    }

    public String getPdv() {

	return pdv;
    }

    public void setPdv(String pdv) {

	this.pdv = pdv;
    }
}
