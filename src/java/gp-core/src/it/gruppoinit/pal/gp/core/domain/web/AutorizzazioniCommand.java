package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;

public class AutorizzazioniCommand extends BaseCommand {

    private Autorizzazioni entity;
    private AutorizzazioniSubentri subentro;
    private boolean registroAutorizzazione;
    private boolean registroAutorizzazioneProtocollo;
    private Statiistanza statoistanza;
    private Concessionicausali causaleCessazioneMassiva;
    private Date dataCessazioneMassiva;
    private boolean manifestazioniConfigurate;
    private MercatiUso mercatiUso;
    private DehorsMqIstanze dehorsMqIstanze;
    private String codiceFirmatario;

    public AutorizzazioniCommand() {

	this.setEntity(new Autorizzazioni());
	this.setSubentro(new AutorizzazioniSubentri());
	this.setStatoistanza(new Statiistanza());
	this.causaleCessazioneMassiva = new Concessionicausali();
	this.mercatiUso = new MercatiUso();
	this.dehorsMqIstanze = new DehorsMqIstanze();
    }

    public String getCodiceFirmatario() {

	return codiceFirmatario;
    }

    public void setCodiceFirmatario(String codiceFirmatario) {

	this.codiceFirmatario = codiceFirmatario;
    }

    public void setEntity(Autorizzazioni entity) {

	this.entity = entity;
    }

    public Autorizzazioni getEntity() {

	return entity;
    }

    public void setSubentro(AutorizzazioniSubentri subentro) {

	this.subentro = subentro;
    }

    public AutorizzazioniSubentri getSubentro() {

	return subentro;
    }

    public void setRegistroAutorizzazione(boolean registroAutorizzazione) {

	this.registroAutorizzazione = registroAutorizzazione;
    }

    public boolean isRegistroAutorizzazione() {

	return registroAutorizzazione;
    }

    public void setRegistroAutorizzazioneProtocollo(boolean registroAutorizzazioneProtocollo) {

	this.registroAutorizzazioneProtocollo = registroAutorizzazioneProtocollo;
    }

    public boolean isRegistroAutorizzazioneProtocollo() {

	this.registroAutorizzazioneProtocollo = false;
	Autorizzazioni aut = this.getEntity();
	if (aut != null) {
	    if (aut.getTipologiaregistro().getId() != null) {
		if (aut.getTipologiaregistro().getId().getCodice() != null) {
		    Boolean returnValue = aut.getTipologiaregistro().getTrFlagprotocollo();
		    if (null != returnValue) {
			this.registroAutorizzazioneProtocollo = returnValue.booleanValue();
		    }
		}
	    }
	}
	return this.registroAutorizzazioneProtocollo;
    }

    public void setStatoistanza(Statiistanza statoistanza) {

	this.statoistanza = statoistanza;
    }

    public Statiistanza getStatoistanza() {

	return statoistanza;
    }

    public Concessionicausali getCausaleCessazioneMassiva() {

	return causaleCessazioneMassiva;
    }

    public void setCausaleCessazioneMassiva(Concessionicausali causaleCessazioneMassiva) {

	this.causaleCessazioneMassiva = causaleCessazioneMassiva;
    }

    public Date getDataCessazioneMassiva() {

	return dataCessazioneMassiva;
    }

    public void setDataCessazioneMassiva(Date dataCessazioneMassiva) {

	this.dataCessazioneMassiva = dataCessazioneMassiva;
    }

    public void setManifestazioniConfigurate(boolean manifestazioniConfigurate) {

	this.manifestazioniConfigurate = manifestazioniConfigurate;
    }

    public boolean isManifestazioniConfigurate() {

	return manifestazioniConfigurate;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public DehorsMqIstanze getDehorsMqIstanze() {

	return dehorsMqIstanze;
    }

    public void setDehorsMqIstanze(DehorsMqIstanze dehorsMqIstanze) {

	this.dehorsMqIstanze = dehorsMqIstanze;
    }
}
