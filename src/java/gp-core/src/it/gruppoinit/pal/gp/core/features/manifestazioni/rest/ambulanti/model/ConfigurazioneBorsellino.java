package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;

public class ConfigurazioneBorsellino {

    @XmlElement(name = "attivo")
    private boolean attivo;
    @XmlElement(name = "messaggio_predefinito_non_ricaricabile")
    private String messaggioBorsellinoNonRicaricabile;
    @XmlElement(name = "tipo_gestione")
    private String tipoGestione;

    public boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }

    public String getMessaggioBorsellinoNonRicaricabile() {

	return messaggioBorsellinoNonRicaricabile;
    }

    public void setMessaggioBorsellinoNonRicaricabile(String messaggioBorsellinoNonRicaricabile) {

	this.messaggioBorsellinoNonRicaricabile = messaggioBorsellinoNonRicaricabile;
    }

    public String getTipoGestione() {

	return tipoGestione;
    }

    public void setTipoGestione(String tipoGestione) {

	this.tipoGestione = tipoGestione;
    }

    public static ConfigurazioneBorsellino fromBorsellinoConfigurazione(BorsellinoConfigurazione borsellinoConfigurazione) {

	ConfigurazioneBorsellino ret = new ConfigurazioneBorsellino();
	ret.setAttivo(BooleanUtils.toBoolean(borsellinoConfigurazione.getGestioneFO()));
	ret.setMessaggioBorsellinoNonRicaricabile(borsellinoConfigurazione.getMsgNodoPagNonDisp());
	ret.setTipoGestione(borsellinoConfigurazione.getTipoInstallazione());
	return ret;
    }
}
