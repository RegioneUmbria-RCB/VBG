package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Descrizione dell'imputazione della singola entrata in formato tipo/codice dove tipo indica il tipo diclassificazione
 * usato
 **/
public class DatiSpecificiRiscossioneType {

    /**
     * Codice contabile conforme al tipo di contabilita' dichiarato dall'Ente Creditore per classificare l'incasso
     **/
    private String codiceContabilita = null;

    @XmlType(name = "TipoContabilitaEnum")
    @XmlEnum(String.class)
    public enum TipoContabilitaEnum {

	@XmlEnumValue("0")
	_0(String.valueOf("0")),
	@XmlEnumValue("1")
	_1(String.valueOf("1")),
	@XmlEnumValue("2")
	_2(String.valueOf("2")),
	@XmlEnumValue("9")
	_9(String.valueOf("9"));

	private String value;

	TipoContabilitaEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoContabilitaEnum fromValue(String v) {

	    for (TipoContabilitaEnum b : TipoContabilitaEnum.values()) {
		if (String.valueOf(b.value).equals(v)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    /**
     * Indica il tipo di classificazione contabile usato. Al momento non va valorizzato - 0: Capitolo e articolo di
     * Entrata Bilancio dello Stato - 1: Numero della contabilita' speciale - 2: Codice SIOPE - 9: Altro codice Ente
     * Creditore\"
     **/
    private TipoContabilitaEnum tipoContabilita = null;

    /**
     * Codice contabile conforme al tipo di contabilita&#39; dichiarato dall&#39;Ente Creditore per classificare
     * l&#39;incasso
     * 
     * @return codiceContabilita
     **/
    @XmlElement(name = "codice_contabilita")
    public String getCodiceContabilita() {

	return codiceContabilita;
    }

    public void setCodiceContabilita(String codiceContabilita) {

	this.codiceContabilita = codiceContabilita;
    }

    public DatiSpecificiRiscossioneType codiceContabilita(String codiceContabilita) {

	this.codiceContabilita = codiceContabilita;
	return this;
    }

    /**
     * Indica il tipo di classificazione contabile usato. Al momento non va valorizzato - 0: Capitolo e articolo di
     * Entrata Bilancio dello Stato - 1: Numero della contabilita&#39; speciale - 2: Codice SIOPE - 9: Altro codice Ente
     * Creditore\&quot;
     * 
     * @return tipoContabilita
     **/
    @XmlElement(name = "tipo_contabilita")
    public String getTipoContabilita() {

	if (tipoContabilita == null) {
	    return null;
	}
	return tipoContabilita.value();
    }

    public void setTipoContabilita(String tipoContabilita) {

	this.tipoContabilita = TipoContabilitaEnum.fromValue(tipoContabilita);
    }

    public void setTipoContabilita(TipoContabilitaEnum tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
    }

    public DatiSpecificiRiscossioneType tipoContabilita(TipoContabilitaEnum tipoContabilita) {

	this.tipoContabilita = tipoContabilita;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class DatiSpecificiRiscossioneType {\n");
	sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
	sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
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
