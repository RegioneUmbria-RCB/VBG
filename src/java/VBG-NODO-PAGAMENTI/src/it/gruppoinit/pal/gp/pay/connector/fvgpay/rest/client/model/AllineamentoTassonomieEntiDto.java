package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

public class AllineamentoTassonomieEntiDto {

    private Long ente = null;
    private List<Long> idTassonomie = null;
    private List<String> idTipiEnteCreditore = null;

    @XmlType(name = "TipoAllineamentoEnum")
    @XmlEnum(String.class)
    public enum TipoAllineamentoEnum {

	@XmlEnumValue("PER_TIPO_ENTE")
	TIPO_ENTE(String.valueOf("PER_TIPO_ENTE")),
	@XmlEnumValue("PER_ENTE")
	ENTE(String.valueOf("PER_ENTE"));

	private String value;

	TipoAllineamentoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoAllineamentoEnum fromValue(String v) {

	    for (TipoAllineamentoEnum b : TipoAllineamentoEnum.values()) {
		if (String.valueOf(b.value).equals(v)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    private TipoAllineamentoEnum tipoAllineamento = null;

    /**
     * Get ente
     * 
     * @return ente
     **/
    @XmlElement(name = "ente")
    public Long getEnte() {

	return ente;
    }

    public void setEnte(Long ente) {

	this.ente = ente;
    }

    public AllineamentoTassonomieEntiDto ente(Long ente) {

	this.ente = ente;
	return this;
    }

    /**
     * Get idTassonomie
     * 
     * @return idTassonomie
     **/
    @XmlElement(name = "idTassonomie")
    public List<Long> getIdTassonomie() {

	return idTassonomie;
    }

    public void setIdTassonomie(List<Long> idTassonomie) {

	this.idTassonomie = idTassonomie;
    }

    public AllineamentoTassonomieEntiDto idTassonomie(List<Long> idTassonomie) {

	this.idTassonomie = idTassonomie;
	return this;
    }

    public AllineamentoTassonomieEntiDto addIdTassonomieItem(Long idTassonomieItem) {

	this.idTassonomie.add(idTassonomieItem);
	return this;
    }

    /**
     * Get idTipiEnteCreditore
     * 
     * @return idTipiEnteCreditore
     **/
    @XmlElement(name = "idTipiEnteCreditore")
    public List<String> getIdTipiEnteCreditore() {

	return idTipiEnteCreditore;
    }

    public void setIdTipiEnteCreditore(List<String> idTipiEnteCreditore) {

	this.idTipiEnteCreditore = idTipiEnteCreditore;
    }

    public AllineamentoTassonomieEntiDto idTipiEnteCreditore(List<String> idTipiEnteCreditore) {

	this.idTipiEnteCreditore = idTipiEnteCreditore;
	return this;
    }

    public AllineamentoTassonomieEntiDto addIdTipiEnteCreditoreItem(String idTipiEnteCreditoreItem) {

	this.idTipiEnteCreditore.add(idTipiEnteCreditoreItem);
	return this;
    }

    /**
     * Get tipoAllineamento
     * 
     * @return tipoAllineamento
     **/
    @XmlElement(name = "tipoAllineamento")
    public String getTipoAllineamento() {

	if (tipoAllineamento == null) {
	    return null;
	}
	return tipoAllineamento.value();
    }

    public void setTipoAllineamento(TipoAllineamentoEnum tipoAllineamento) {

	this.tipoAllineamento = tipoAllineamento;
    }

    public AllineamentoTassonomieEntiDto tipoAllineamento(TipoAllineamentoEnum tipoAllineamento) {

	this.tipoAllineamento = tipoAllineamento;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AllineamentoTassonomieEntiDto {\n");
	sb.append("    ente: ").append(toIndentedString(ente)).append("\n");
	sb.append("    idTassonomie: ").append(toIndentedString(idTassonomie)).append("\n");
	sb.append("    idTipiEnteCreditore: ").append(toIndentedString(idTipiEnteCreditore)).append("\n");
	sb.append("    tipoAllineamento: ").append(toIndentedString(tipoAllineamento)).append("\n");
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
