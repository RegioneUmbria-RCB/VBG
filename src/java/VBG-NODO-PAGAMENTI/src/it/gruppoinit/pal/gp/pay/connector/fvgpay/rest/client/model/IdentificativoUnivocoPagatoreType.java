package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

public class IdentificativoUnivocoPagatoreType {

    /**
     * Codice identificativo univoco: Codice Fiscale, Partita IVA o 'ANONIMO'
     **/
    private String codiceIdentificativoUnivoco = null;

    @XmlType(name = "TipoIdentificativoUnivocoEnum")
    @XmlEnum(String.class)
    public enum TipoIdentificativoUnivocoEnum {

	@XmlEnumValue("F")
	F(String.valueOf("F")),
	@XmlEnumValue("G")
	G(String.valueOf("G")),
	@XmlEnumValue("A")
	A(String.valueOf("A"));

	private String value;

	TipoIdentificativoUnivocoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoIdentificativoUnivocoEnum fromValue(String v) {

	    for (TipoIdentificativoUnivocoEnum b : TipoIdentificativoUnivocoEnum.values()) {
		if (String.valueOf(b.value).equals(v)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    /**
     * Definisce il tipo di identificatore usato - F: Persona Fisica - G: Persona Giuridica - A: Anonimo
     **/
    private TipoIdentificativoUnivocoEnum tipoIdentificativoUnivoco = null;

    /**
     * Codice identificativo univoco: Codice Fiscale, Partita IVA o &#39;ANONIMO&#39;
     * 
     * @return codiceIdentificativoUnivoco
     **/
    @XmlElement(name = "codice_identificativo_univoco")
    public String getCodiceIdentificativoUnivoco() {

	return codiceIdentificativoUnivoco;
    }

    public void setCodiceIdentificativoUnivoco(String codiceIdentificativoUnivoco) {

	this.codiceIdentificativoUnivoco = codiceIdentificativoUnivoco;
    }

    public IdentificativoUnivocoPagatoreType codiceIdentificativoUnivoco(String codiceIdentificativoUnivoco) {

	this.codiceIdentificativoUnivoco = codiceIdentificativoUnivoco;
	return this;
    }

    /**
     * Definisce il tipo di identificatore usato - F: Persona Fisica - G: Persona Giuridica - A: Anonimo
     * 
     * @return tipoIdentificativoUnivoco
     **/
    @XmlElement(name = "tipo_identificativo_univoco")
    public String getTipoIdentificativoUnivoco() {

	if (tipoIdentificativoUnivoco == null) {
	    return null;
	}
	return tipoIdentificativoUnivoco.value();
    }

    public void setTipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {

	this.tipoIdentificativoUnivoco = TipoIdentificativoUnivocoEnum.fromValue(tipoIdentificativoUnivoco);
    }

    public void setTipoIdentificativoUnivoco(TipoIdentificativoUnivocoEnum tipoIdentificativoUnivoco) {

	this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
    }

    public IdentificativoUnivocoPagatoreType tipoIdentificativoUnivoco(TipoIdentificativoUnivocoEnum tipoIdentificativoUnivoco) {

	this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class IdentificativoUnivocoPagatoreType {\n");
	sb.append("    codiceIdentificativoUnivoco: ").append(toIndentedString(codiceIdentificativoUnivoco)).append("\n");
	sb.append("    tipoIdentificativoUnivoco: ").append(toIndentedString(tipoIdentificativoUnivoco)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
