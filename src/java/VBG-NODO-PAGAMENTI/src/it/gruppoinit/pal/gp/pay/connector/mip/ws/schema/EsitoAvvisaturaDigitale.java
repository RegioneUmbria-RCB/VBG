package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoAvvisaturaDigitale", propOrder = { "tipoCanaleEsito", "identificativoCanale", "dataEsito", "codiceEsito", "descrizioneEsito" })
public class EsitoAvvisaturaDigitale {

    @XmlElement(name = "TipoCanaleEsito")
    private String tipoCanaleEsito;
    @XmlElement(name = "IdentificativoCanale")
    private String identificativoCanale;
    @XmlElement(name = "DataEsito")
    private String dataEsito;
    @XmlElement(name = "CodiceEsito")
    private String codiceEsito;
    @XmlElement(name = "DescrizioneEsito")
    private String descrizioneEsito;

    public String getTipoCanaleEsito() {

	return tipoCanaleEsito;
    }

    public void setTipoCanaleEsito(String tipoCanaleEsito) {

	this.tipoCanaleEsito = tipoCanaleEsito;
    }

    public String getIdentificativoCanale() {

	return identificativoCanale;
    }

    public void setIdentificativoCanale(String identificativoCanale) {

	this.identificativoCanale = identificativoCanale;
    }

    public String getDataEsito() {

	return dataEsito;
    }

    public void setDataEsito(String dataEsito) {

	this.dataEsito = dataEsito;
    }

    public String getCodiceEsito() {

	return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {

	this.codiceEsito = codiceEsito;
    }

    public String getDescrizioneEsito() {

	return descrizioneEsito;
    }

    public void setDescrizioneEsito(String descrizioneEsito) {

	this.descrizioneEsito = descrizioneEsito;
    }
}
