package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaDataCessazione;

public class ValidazioneDataCessazioneSubentroCommand {

    private String returnTo;
    private Integer idAutorizzazioniSubentri;
    private String estremiAtto;
    private IdentificativoDescrizioneBean occupante;
    private IdentificativoDescrizioneBean titolare;
    private boolean concessione;
    private String estremiSubentro;
    private IdentificativoDescrizioneBean occupanteSubentro;
    private IdentificativoDescrizioneBean titolareSubentro;
    private Date nuovaDataCessazione;
    private Date dataCessazionePrecedente;
    private EsitoModificaDataCessazione esito;

    public ValidazioneDataCessazioneSubentroCommand(AutorizzazioniSubentri autorizzazioneSubentro, String returnTo) {

	this.returnTo = returnTo;
	this.idAutorizzazioniSubentri = autorizzazioneSubentro.getId().getCodice();
	this.dataCessazionePrecedente = autorizzazioneSubentro.getDataCessazione();
	this.concessione = !autorizzazioneSubentro.getAutorizzazioni().getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty();
	this.estremiAtto = autorizzazioneSubentro.getAutorizzazioni().getTransientEstremiAut();
	this.estremiSubentro = autorizzazioneSubentro.getTransientEstremiAut();
	this.occupante = recuperaAnagrafe(autorizzazioneSubentro.getAutorizzazioni().getOccupante());
	this.titolare = recuperaAnagrafe(autorizzazioneSubentro.getAutorizzazioni().getAnagrafe());
	this.occupanteSubentro = recuperaAnagrafe(autorizzazioneSubentro.getOccupante());
	this.titolareSubentro = recuperaAnagrafe(autorizzazioneSubentro.getAnagrafe());
	if (concessione && !autorizzazioneSubentro.getAutorizzazioni().getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
	    StringBuilder estremi = new StringBuilder();
	    for (AutorizzazioniConcessioni conc : autorizzazioneSubentro.getAutorizzazioni().getAutorizzazioniConcessionisForFkAutconcAutatt()) {
		estremi.append("Concessione: ").append(conc.getTransientEstremiConcessione()).append("\n");
	    }
	    this.estremiAtto = estremi.toString();
	}
	if (concessione && !autorizzazioneSubentro.getAutSubentrisConcs().isEmpty()) {
	    StringBuilder estremi = new StringBuilder();
	    for (AutorizzazioniSubentriConc conc : autorizzazioneSubentro.getAutSubentrisConcs()) {
		estremi.append("Concessione: ").append(conc.getTransientEstremiConcessione()).append("\n");
	    }
	    this.estremiSubentro = estremi.toString();
	}
    }

    public String getReturnTo() {

	return returnTo;
    }

    public void setReturnTo(String returnTo) {

	this.returnTo = returnTo;
    }

    public Integer getIdAutorizzazioniSubentri() {

	return idAutorizzazioniSubentri;
    }

    public void setIdAutorizzazioniSubentri(Integer idAutorizzazioniSubentri) {

	this.idAutorizzazioniSubentri = idAutorizzazioniSubentri;
    }

    public String getEstremiAtto() {

	return estremiAtto;
    }

    public void setEstremiAtto(String estremiAtto) {

	this.estremiAtto = estremiAtto;
    }

    public IdentificativoDescrizioneBean getOccupante() {

	return occupante;
    }

    public void setOccupante(IdentificativoDescrizioneBean occupante) {

	this.occupante = occupante;
    }

    public IdentificativoDescrizioneBean getTitolare() {

	return titolare;
    }

    public void setTitolare(IdentificativoDescrizioneBean titolare) {

	this.titolare = titolare;
    }

    public boolean isConcessione() {

	return concessione;
    }

    public void setConcessione(boolean concessione) {

	this.concessione = concessione;
    }

    public String getEstremiSubentro() {

	return estremiSubentro;
    }

    public void setEstremiSubentro(String estremiSubentro) {

	this.estremiSubentro = estremiSubentro;
    }

    private IdentificativoDescrizioneBean recuperaAnagrafe(Anagrafe anagrafe) {

	return new IdentificativoDescrizioneBean(anagrafe.getId().getCodice(), anagrafe.getDescrizioneRichiedente());
    }

    public IdentificativoDescrizioneBean getOccupanteSubentro() {

	return occupanteSubentro;
    }

    public void setOccupanteSubentro(IdentificativoDescrizioneBean occupanteSubentro) {

	this.occupanteSubentro = occupanteSubentro;
    }

    public IdentificativoDescrizioneBean getTitolareSubentro() {

	return titolareSubentro;
    }

    public void setTitolareSubentro(IdentificativoDescrizioneBean titolareSubentro) {

	this.titolareSubentro = titolareSubentro;
    }

    public Date getNuovaDataCessazione() {

	return nuovaDataCessazione;
    }

    public void setNuovaDataCessazione(Date nuovaDataCessazione) {

	this.nuovaDataCessazione = nuovaDataCessazione;
    }

    public EsitoModificaDataCessazione getEsito() {

	return esito;
    }

    public void setEsito(EsitoModificaDataCessazione esito) {

	this.esito = esito;
    }

    public boolean isPresenteEsito() {

	if (esito == null) {
	    return false;
	}
	return esito.isErroreOWarning();
    }

    public boolean isPossoModificare() {

	if (esito == null) {
	    return false;
	}
	return !esito.isErrore();
    }

    public Date getDataCessazionePrecedente() {

	return dataCessazionePrecedente;
    }

    public void setDataCessazionePrecedente(Date dataCessazionePrecedente) {

	this.dataCessazionePrecedente = dataCessazionePrecedente;
    }
}
