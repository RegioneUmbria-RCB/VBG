package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaOccupante;

public class ValidazioneOccupanteCommand {

    private String returnTo;
    private Integer idAuOConc;
    private String estremiAtto;
    private IdentificativoDescrizioneBean vecchioOccupante;
    private IdentificativoDescrizioneBean vecchioTitolare;
    private Integer codiceAnagrafeNuovoOccupante;
    private String descrizioneNuovoOccupante;
    private boolean concessione;
    private EsitoModificaOccupante esito;
    private boolean attoCollegato;

    public ValidazioneOccupanteCommand(Autorizzazioni autorizzazione, String returnTo) {

	this.returnTo = returnTo;
	this.idAuOConc = autorizzazione.getId().getCodice();
	this.concessione = !autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty();
	this.estremiAtto = autorizzazione.getTransientEstremiAut();
	this.vecchioOccupante = recuperaAnagrafe(autorizzazione.getOccupante());
	this.vecchioTitolare = recuperaAnagrafe(autorizzazione.getAnagrafe());
	this.attoCollegato = !autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutcoll().isEmpty();
	if (concessione && !autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
	    StringBuilder estremi = new StringBuilder();
	    for (AutorizzazioniConcessioni conc : autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutatt()) {
		estremi.append("Concessione: ").append(conc.getTransientEstremiConcessione()).append("\n");
	    }
	    this.estremiAtto = estremi.toString();
	}
    }

    private IdentificativoDescrizioneBean recuperaAnagrafe(Anagrafe anagrafe) {

	return new IdentificativoDescrizioneBean(anagrafe.getId().getCodice(), anagrafe.getDescrizioneRichiedente());
    }

    public Integer getIdAuOConc() {

	return idAuOConc;
    }

    public Integer getCodiceAnagrafeNuovoOccupante() {

	return codiceAnagrafeNuovoOccupante;
    }

    public void setCodiceAnagrafeNuovoOccupante(Integer codiceAnagrafeNuovoOccupante) {

	this.codiceAnagrafeNuovoOccupante = codiceAnagrafeNuovoOccupante;
    }

    public EsitoModificaOccupante getEsito() {

	return esito;
    }

    public void setEsito(EsitoModificaOccupante esito) {

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

    public boolean isConcessione() {

	return concessione;
    }

    public String getEstremiAtto() {

	return estremiAtto;
    }

    public IdentificativoDescrizioneBean getVecchioOccupante() {

	return vecchioOccupante;
    }

    public IdentificativoDescrizioneBean getVecchioTitolare() {

	return vecchioTitolare;
    }

    public String getReturnTo() {

	return returnTo;
    }

    public String getDescrizioneNuovoOccupante() {

	return descrizioneNuovoOccupante;
    }

    public void setDescrizioneNuovoOccupante(String descrizioneNuovoOccupante) {

	this.descrizioneNuovoOccupante = descrizioneNuovoOccupante;
    }

    public boolean isAttoCollegato() {

	return attoCollegato;
    }

    public void setAttoCollegato(boolean attoCollegato) {

	this.attoCollegato = attoCollegato;
    }
}
