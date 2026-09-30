package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IstitutoAttestante", propOrder = { "tipoIdentificativo", "identificativo", "denominazione" })
public class IstitutoAttestante {

    public enum TipoIdentificativo {

	PERSONA_GIURIDICA("G"),
	CODICE_ABI("A"),
	CODICE_BIC("B");

	TipoIdentificativo(String value) {

	    this.value = value;
	}

	private String value;

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoIdentificativo fromValues(String input) {

	    for (TipoIdentificativo t : TipoIdentificativo.values()) {
		if (StringUtils.equals(t.value, input)) {
		    return t;
		}
	    }
	    return null;
	}
    }

    @XmlElement(name = "TipoIdentificativo")
    private TipoIdentificativo tipoIdentificativo;
    @XmlElement(name = "Identificativo")
    private String identificativo;
    @XmlElement(name = "Denominazione")
    private String denominazione;

    public TipoIdentificativo getTipoIdentificativo() {

	return tipoIdentificativo;
    }

    public void setTipoIdentificativo(TipoIdentificativo tipoIdentificativo) {

	this.tipoIdentificativo = tipoIdentificativo;
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
