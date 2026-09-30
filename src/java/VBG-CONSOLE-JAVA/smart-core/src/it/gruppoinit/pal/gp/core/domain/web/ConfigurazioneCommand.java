package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;

import java.util.List;

public class ConfigurazioneCommand extends BaseCommand {

    private Configurazione configurazione;
    private ProtocolloConfigurazione protocolloConfigurazione;
    private Comuniassociati comuniassociati;
    // Il campo è utilizzato per inserire/mostrare il record comuniassociatosoftware
    // comune a tutti i comuni associati sul form principale della funzionalità configurazione
    private Comuniassociatisoftware comuniassociatisoftware;
    // I due campi sono utilizzati per la gestione dell'inserimento di un oggetto comuni associati software
    // in particolare sono utilizzati per vedere per quali comuni associati l'operatore è loggato è un responsabile
    private Responsabilicomuni responsabilicomuni;
    private List<Responsabilicomuni> responsabilicomunis;

    public ConfigurazioneCommand() {

	super();
	this.configurazione = new Configurazione();
	this.comuniassociatisoftware = new Comuniassociatisoftware();
	this.responsabilicomuni = new Responsabilicomuni();
	this.protocolloConfigurazione = new ProtocolloConfigurazione();
	this.comuniassociati = new Comuniassociati();
    }

    public Configurazione getConfigurazione() {

	return configurazione;
    }

    public void setConfigurazione(Configurazione configurazione) {

	this.configurazione = configurazione;
    }

    public ProtocolloConfigurazione getProtocolloConfigurazione() {

	return protocolloConfigurazione;
    }

    public void setProtocolloConfigurazione(ProtocolloConfigurazione protocolloConfigurazione) {

	this.protocolloConfigurazione = protocolloConfigurazione;
    }

    public Comuniassociati getComuniassociati() {

	return comuniassociati;
    }

    public void setComuniassociati(Comuniassociati comuniassociati) {

	this.comuniassociati = comuniassociati;
    }

    public Comuniassociatisoftware getComuniassociatisoftware() {

	return comuniassociatisoftware;
    }

    public void setComuniassociatisoftware(Comuniassociatisoftware comuniassociatisoftware) {

	this.comuniassociatisoftware = comuniassociatisoftware;
    }

    public Responsabilicomuni getResponsabilicomuni() {

	return responsabilicomuni;
    }

    public void setResponsabilicomuni(Responsabilicomuni responsabilicomuni) {

	this.responsabilicomuni = responsabilicomuni;
    }

    public List<Responsabilicomuni> getResponsabilicomunis() {

	return responsabilicomunis;
    }

    public void setResponsabilicomunis(List<Responsabilicomuni> responsabilicomunis) {

	this.responsabilicomunis = responsabilicomunis;
    }
}
