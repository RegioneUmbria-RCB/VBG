package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoServiziRestResponse {

    @XmlElement(name = "descrizioneServizio")
    String descrizioneServizio;
    @XmlElement(name = "identificativoServizio")
    String identificativoServizio;
    @XmlElement(name = "precIdentificativoServizio")
    String precIdentificativoServizio;

    public String getDescrizioneServizio() {

	return descrizioneServizio;
    }

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setDescrizioneServizio(String descrizioneServizio) {

	this.descrizioneServizio = descrizioneServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public String getPrecIdentificativoServizio() {

	return precIdentificativoServizio;
    }

    public void setPrecIdentificativoServizio(String precIdentificativoServizio) {

	this.precIdentificativoServizio = precIdentificativoServizio;
    }
}
