package it.gruppoinit.pal.gp.core.service.helper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZipLogicoLinkHelper {

    public ZipLogicoLinkHelper() {

	super();
    }

    public ZipLogicoLinkHelper(String url, Integer pin) {

	this();
	this.url = url;
	this.pin = pin;
    }

    @XmlElement
    private String url;
    @XmlElement
    private Integer pin;
    @XmlElement
    private boolean usaPin;

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public Integer getPin() {

	return pin;
    }

    public void setPin(Integer pin) {

	this.pin = pin;
    }

    public boolean isUsaPin() {

	return usaPin;
    }

    public void setUsaPin(boolean usaPin) {

	this.usaPin = usaPin;
    }
}
