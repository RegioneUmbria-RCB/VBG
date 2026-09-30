package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
public class TipologiaRicaricaModel {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "descrizione")
    private String descrizione;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public static TipologiaRicaricaModel fromValue(String tipoRicarica) {

	if (StringUtils.isBlank(tipoRicarica)) {
	    return new TipologiaRicaricaModel();
	}
	TipoRicaricaEnum tipo = TipoRicaricaEnum.valueOf(tipoRicarica);
	TipologiaRicaricaModel model = new TipologiaRicaricaModel();
	model.setCodice(tipo.toString());
	model.setDescrizione(tipo.getValore());
	return model;
    }
}
