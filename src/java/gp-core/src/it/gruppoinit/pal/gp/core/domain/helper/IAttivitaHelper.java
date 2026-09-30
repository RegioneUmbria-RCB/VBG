package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class IAttivitaHelper {

    //    IAttivita infoTestaAttivitaPrecedente = new IAttivita();
    //	infoTestaAttivitaPrecedente.setDenominazione(attivita.getDenominazione());
    //	infoTestaAttivitaPrecedente.setIstanza(attivita.getIstanza());
    //	infoTestaAttivitaPrecedente.setOperante(attivita.getOperante());
    //	infoTestaAttivitaPrecedente.setAttiva(attivita.getAttiva());
    //	//Per ora non usato
    //	infoTestaAttivitaPrecedente.setTipologiaAttivita(attivita.getTipologiaAttivita());
    private IAttivita iAttivita;
    private String denominazione;
    private Istanze istanze;
    private Boolean attiva;
    private Boolean operante;
    private IAttivitaTipologie iAttivitaTipologie;

    public IAttivitaHelper(IAttivita iAttivita) {

	this.iAttivita = iAttivita;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = iAttivita.getDenominazione();
    }

    public Istanze getIstanze() {

	return istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = iAttivita.getIstanza();
    }

    public Boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(Boolean attiva) {

	this.attiva = iAttivita.getAttiva();
    }

    public Boolean getOperante() {

	return operante;
    }

    public void setOperante(Boolean operante) {

	this.operante = iAttivita.getOperante();
    }

    public IAttivitaTipologie getiAttivitaTipologie() {

	return iAttivitaTipologie;
    }

    public void setiAttivitaTipologie(IAttivitaTipologie iAttivitaTipologie) {

	this.iAttivitaTipologie = iAttivita.getTipologiaAttivita();
    }
}
