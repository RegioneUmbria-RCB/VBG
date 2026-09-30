package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class TempirispostaContainerJson {

    @XmlElement
    private String tipomovimento;
    @XmlElement
    private String tipocontromovimento;
    @XmlElement(name = "tempi")
    private List<TempiRispostaJson> tempi;

    public List<TempiRispostaJson> getTempi() {

	if (null == tempi) {
	    tempi = new ArrayList<TempiRispostaJson>();
	}
	return tempi;
    }

    public void setTempi(List<TempiRispostaJson> tempi) {

	this.tempi = tempi;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getTipocontromovimento() {

	return tipocontromovimento;
    }

    public void setTipocontromovimento(String tipocontromovimento) {

	this.tipocontromovimento = tipocontromovimento;
    }
}
