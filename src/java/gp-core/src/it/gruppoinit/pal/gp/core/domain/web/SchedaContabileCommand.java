package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;

import java.util.ArrayList;
import java.util.List;

public class SchedaContabileCommand extends BaseCommand {

    private Anagrafe entity;
    private List<Registrazioni> registrazioniList;
    private List<RegistrazioniImporti> registrazioniImportiList;
    /**
     * serve per impostare la visualizzazione della scheda contabile al tab raggruppata ossia visualizzazione incentrata
     * sulle registrazioni
     */
    public static final String TAB_RAGGRUPPATO = "TAB_RAGGRUPPATO";
    /**
     * serve per impostare la visualizzazione della scheda contabile al tab dettaglio ossia la visualizzaione incentrata
     * sulle righe di importo
     */
    public static final String TAB_DETTAGLIO = "TAB_DETTAGLIO";
    /**
     * Serve per impostare / visualizzare il checkbox per mostrare tutte le registrazioni
     */
    public static final String SHOW_ALL = "SHOW_ALL";
    /**
     * Serve per impostare / visualizzare il checkbox per mostrare solamente le registrazioni chiuse
     */
    public static final String SHOW_CLOSED = "SHOW_CLOSED";

    public SchedaContabileCommand() {

	entity = new Anagrafe();
	registrazioniList = new ArrayList<Registrazioni>();
	registrazioniImportiList = new ArrayList<RegistrazioniImporti>();
    }

    public Anagrafe getEntity() {

	return entity;
    }

    public void setEntity(Anagrafe entity) {

	this.entity = entity;
    }

    public void setRegistrazioniList(List<Registrazioni> registrazioniList) {

	this.registrazioniList = registrazioniList;
    }

    public List<Registrazioni> getRegistrazioniList() {

	return registrazioniList;
    }

    public void setRegistrazioniImportiList(List<RegistrazioniImporti> registrazioniImportiList) {

	this.registrazioniImportiList = registrazioniImportiList;
    }

    public List<RegistrazioniImporti> getRegistrazioniImportiList() {

	return registrazioniImportiList;
    }
}
