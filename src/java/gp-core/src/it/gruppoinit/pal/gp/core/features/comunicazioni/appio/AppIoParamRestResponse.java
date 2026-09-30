package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "param")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoParamRestResponse {

    @XmlElement(name = "parametro")
    private String parametro;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "identificativoServizio")
    private String identificativoServizio;

    public String getParametro() {

	return parametro;
    }

    public void setParametro(String parametro) {

	this.parametro = parametro;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }
}
