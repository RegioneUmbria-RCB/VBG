package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniConcessioniDatiGenerali;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConcessioniCommand extends BaseCommand {

    private Istanze istanza;
    private List<AutorizzazioniConcessioni> concessionis;
    private AutorizzazioniConcessioni concessioneInsert;
    private AutorizzazioniConcessioniDatiGenerali entity = null;
    private Autorizzazioni autorizzazione;
    private AutorizzazioniSubentri subentro;
    private AutorizzazioniSubentri subentroAutCollegata;
    private boolean inserisciAutorizzazione;
    private boolean registroConcessione;
    private boolean registroAutorizzazione;
    private boolean registroConcessioneProtocollo;
    private boolean registroAutorizzazioneProtocollo;
    private int viewMode;
    public static final int CONCESSIONE_VIEW = 0;
    public static final int AUTORIZZAZIONE_VIEW = 1;
    private boolean subentroPresente;
    private Autorizzazioni autorizzazioneAssociata;
    private Boolean isManifestazioneDaProcedimentoPresente;
    private Tipologiaregistri tipologiaregistri;
    private Date dateRilascio;
    // Utilizzatio nella funzionalità scambio posteggio
    private MercatiD posteggioDestinazione;

    public ConcessioniCommand() {

	this.istanza = new Istanze();
	this.subentro = new AutorizzazioniSubentri();
	this.setSubentroAutCollegata(new AutorizzazioniSubentri());
	this.autorizzazioneAssociata = new Autorizzazioni();
	this.tipologiaregistri = new Tipologiaregistri();
	this.dateRilascio = new Date();
	this.posteggioDestinazione = new MercatiD();
    }

    public AutorizzazioniConcessioni getConcessioneInsert() {

	return concessioneInsert;
    }

    public void setConcessioneInsert(AutorizzazioniConcessioni concessioneInsert) {

	this.concessioneInsert = concessioneInsert;
    }

    public AutorizzazioniConcessioniDatiGenerali getEntity() {

	return entity;
    }

    public void setEntity(AutorizzazioniConcessioniDatiGenerali entity) {

	this.entity = entity;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public void setInserisciAutorizzazione(boolean inserisciAutorizzazione) {

	this.inserisciAutorizzazione = inserisciAutorizzazione;
    }

    public List<AutorizzazioniConcessioni> getConcessionis() {

	if (concessionis == null) {
	    concessionis = new ArrayList<AutorizzazioniConcessioni>();
	}
	return concessionis;
    }

    public void setConcessionis(List<AutorizzazioniConcessioni> concessionis) {

	this.concessionis = concessionis;
    }

    public boolean isInserisciAutorizzazione() {

	return inserisciAutorizzazione;
    }

    public boolean isRegistroConcessione() {

	this.registroConcessione = false;
	List<AutorizzazioniConcessioni> concessionis = this.getConcessionis();
	if (concessionis != null && concessionis.size() > 0) {
	    AutorizzazioniConcessioni concessione = concessionis.get(0);
	    if (EntityUtils.getNestedProperty(concessione, "autorizzazioniByFkAutconcAutatt.id.codice") != null) {
		if (EntityUtils.getNestedProperty(concessione, "autorizzazioniByFkAutconcAutatt.tipologiaregistro.id.codice") != null)
		    this.registroConcessione = true;
	    }
	}
	return registroConcessione;
    }

    public boolean isRegistroAutorizzazione() {

	this.registroAutorizzazione = false;
	List<AutorizzazioniConcessioni> concessionis = this.getConcessionis();
	if (concessionis != null && concessionis.size() > 0) {
	    AutorizzazioniConcessioni concessione = concessionis.get(0);
	    Autorizzazioni autorizzazioneCollegata = concessione.getAutorizzazioniByFkAutconcAutcoll();
	    if (autorizzazioneCollegata != null) {
		if (autorizzazioneCollegata.getTipologiaregistro().getId() != null) {
		    if (autorizzazioneCollegata.getTipologiaregistro().getId().getCodice() != null) {
			this.registroAutorizzazione = true;
		    }
		}
	    }
	}
	return registroAutorizzazione;
    }

    public boolean isRegistroConcessioneProtocollo() {

	this.registroConcessioneProtocollo = false;
	Autorizzazioni autorizzazione = this.getAutorizzazione();
	if (autorizzazione != null) {
	    if (autorizzazione.getTipologiaregistro() != null) {
		if (autorizzazione.getTipologiaregistro().getId() != null) {
		    if (autorizzazione.getTipologiaregistro().getId().getCodice() != null) {
			Boolean returnValue = autorizzazione.getTipologiaregistro().getTrFlagprotocollo();
			if (null != returnValue) {
			    this.registroConcessioneProtocollo = returnValue.booleanValue();
			}
		    }
		}
	    }
	}
	return this.registroConcessioneProtocollo;
    }

    public boolean isRegistroAutorizzazioneProtocollo() {

	this.registroAutorizzazioneProtocollo = false;
	Autorizzazioni autorizzazioneCollegata = this.getAutorizzazioneAssociata();
	if (autorizzazioneCollegata != null) {
	    if (autorizzazioneCollegata.getTipologiaregistro().getId() != null) {
		if (autorizzazioneCollegata.getTipologiaregistro().getId().getCodice() != null) {
		    Boolean returnValue = autorizzazioneCollegata.getTipologiaregistro().getTrFlagprotocollo();
		    if (null != returnValue) {
			this.registroAutorizzazioneProtocollo = returnValue.booleanValue();
		    }
		}
	    }
	}
	return this.registroAutorizzazioneProtocollo;
    }

    public void setViewMode(int viewMode) {

	if (viewMode != CONCESSIONE_VIEW && viewMode != AUTORIZZAZIONE_VIEW) {
	    throw new InvalidParameterException("view mode " + viewMode + " non riconosciuto");
	}
	this.viewMode = viewMode;
    }

    public int getViewMode() {

	return viewMode;
    }

    public void setSubentroPresente(boolean subentroPresente) {

	this.subentroPresente = subentroPresente;
    }

    public boolean isSubentroPresente() {

	return subentroPresente;
    }

    public void setSubentro(AutorizzazioniSubentri subentro) {

	this.subentro = subentro;
    }

    public AutorizzazioniSubentri getSubentro() {

	return subentro;
    }

    public void setSubentroAutCollegata(AutorizzazioniSubentri subentroAutCollegata) {

	this.subentroAutCollegata = subentroAutCollegata;
    }

    public AutorizzazioniSubentri getSubentroAutCollegata() {

	return subentroAutCollegata;
    }

    public void setAutorizzazioneAssociata(Autorizzazioni autorizzazioneAssociata) {

	this.autorizzazioneAssociata = autorizzazioneAssociata;
    }

    public Autorizzazioni getAutorizzazioneAssociata() {

	return this.autorizzazioneAssociata;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public Autorizzazioni getAutorizzazione() {

	List<AutorizzazioniConcessioni> concessionis = this.getConcessionis();
	if (concessionis != null && concessionis.size() > 0) {
	    AutorizzazioniConcessioni concessione = concessionis.get(0);
	    this.autorizzazione = concessione.getAutorizzazioniByFkAutconcAutatt();
	}
	return this.autorizzazione;
    }

    public Boolean getIsManifestazioneDaProcedimentoPresente() {

	return isManifestazioneDaProcedimentoPresente;
    }

    public void setIsManifestazioneDaProcedimentoPresente(Boolean isManifestazioneDaProcedimentoPresente) {

	this.isManifestazioneDaProcedimentoPresente = isManifestazioneDaProcedimentoPresente;
    }

    public Tipologiaregistri getTipologiaregistri() {

	return tipologiaregistri;
    }

    public void setTipologiaregistri(Tipologiaregistri tipologiaregistri) {

	this.tipologiaregistri = tipologiaregistri;
    }

    public Date getDateRilascio() {

	return dateRilascio;
    }

    public void setDateRilascio(Date dateRilascio) {

	this.dateRilascio = dateRilascio;
    }

    public MercatiD getPosteggioDestinazione() {

	return posteggioDestinazione;
    }

    public void setPosteggioDestinazione(MercatiD posteggioDestinazione) {

	this.posteggioDestinazione = posteggioDestinazione;
    }
}
