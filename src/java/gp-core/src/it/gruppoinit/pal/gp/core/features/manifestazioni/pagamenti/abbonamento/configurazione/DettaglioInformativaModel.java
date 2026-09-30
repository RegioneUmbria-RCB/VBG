package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;

@XmlAccessorType(XmlAccessType.FIELD)
public class DettaglioInformativaModel {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "scadenza")
    private Date dataScadenza;
    @XmlElement(name = "informativa")
    private String informativa;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getInformativa() {

	return informativa;
    }

    public void setInformativa(String informativa) {

	this.informativa = informativa;
    }

    public static DettaglioInformativaModel fromBorsellinoInformative(BorsellinoInformative informativa) {

	if (informativa == null || informativa.getId() == null || informativa.getId().getCodice() == null) {
	    return new DettaglioInformativaModel();
	}
	DettaglioInformativaModel model = new DettaglioInformativaModel();
	model.setId(informativa.getId().getCodice());
	model.setDataScadenza(informativa.getDataFineValidita());
	model.setInformativa(informativa.getInformativa());
	return model;
    }
}
