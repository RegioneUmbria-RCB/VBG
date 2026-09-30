package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveTAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveTLettere;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "allegato")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneAllegatiModel {

    @XmlElement(name = "codice_oggetto")
    private Integer codiceOggetto;

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    public static ComunicazioneAllegatiModel fromMassiveTAllegati(MassiveTAllegati allegato) {

	if (allegato == null || allegato.getOggetti() == null || allegato.getOggetti().getId() == null) {
	    return null;
	}
	ComunicazioneAllegatiModel model = new ComunicazioneAllegatiModel();
	model.setCodiceOggetto(allegato.getOggetti().getId().getCodice());
	return model;
    }

    public static ComunicazioneAllegatiModel fromMassiveTLettere(MassiveTLettere lettera) {

	if (lettera == null || lettera.getLetteretipo() == null || lettera.getLetteretipo().getFile() == null
		|| lettera.getLetteretipo().getFile().getId() == null) {
	    return null;
	}
	ComunicazioneAllegatiModel model = new ComunicazioneAllegatiModel();
	model.setCodiceOggetto(lettera.getLetteretipo().getFile().getId().getCodice());
	return model;
    }

    public static ComunicazioneAllegatiModel fromMassiveDAllegati(MassiveDAllegati allegato) {

	if (allegato == null || allegato.getCodiceOggetto() == null) {
	    return null;
	}
	ComunicazioneAllegatiModel model = new ComunicazioneAllegatiModel();
	model.setCodiceOggetto(allegato.getCodiceOggetto());
	return model;
    }
}
