package it.gruppoinit.pal.gp.core.features.rest;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement
public class RestPrivateAuth {

    @XmlElement(name = "token")
    private String token;
    @XmlElement(name = "software")
    private String software;

    public static RestPrivateAuth current() {

	RestPrivateAuth auth = new RestPrivateAuth();
	auth.token = ORMHelper.getToken();
	auth.software = ORMHelper.getSoftware();
	return auth;
    }

    public String getToken() {

	return token;
    }

    public String getSoftware() {

	return software;
    }

    public String toBase64() {

	try {
	    String json = Utilities.marshalJsonObject(this, this.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return DatatypeConverter.printBase64Binary(json.getBytes("UTF-8"));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
