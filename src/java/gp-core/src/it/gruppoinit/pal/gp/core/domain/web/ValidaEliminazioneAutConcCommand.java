package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;

public class ValidaEliminazioneAutConcCommand {

    private String returnTo;
    private Integer idAutorizzazioni;
    private Integer codiceIstanza;
    private String estremiAtto;
    private IdentificativoDescrizioneBean occupante;
    private IdentificativoDescrizioneBean titolare;
    private boolean concessione;
    private EsitoCancellazioneAutOConc esito;

    public ValidaEliminazioneAutConcCommand(String returnTo, Autorizzazioni autorizzazioneOConc) {

	this.returnTo = returnTo;
	this.idAutorizzazioni = autorizzazioneOConc.getId().getCodice();
	this.codiceIstanza = autorizzazioneOConc.getIstanza().getId().getCodice();
	this.concessione = !autorizzazioneOConc.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty();
	this.estremiAtto = autorizzazioneOConc.getTransientEstremiAut();
	this.occupante = recuperaAnagrafe(autorizzazioneOConc.getOccupante());
	this.titolare = recuperaAnagrafe(autorizzazioneOConc.getAnagrafe());
	this.occupante = recuperaAnagrafe(autorizzazioneOConc.getOccupante());
	this.titolare = recuperaAnagrafe(autorizzazioneOConc.getAnagrafe());
	if (concessione) {
	    StringBuilder estremi = new StringBuilder();
	    for (AutorizzazioniConcessioni conc : autorizzazioneOConc.getAutorizzazioniConcessionisForFkAutconcAutatt()) {
		estremi.append("Concessione: ").append(conc.getTransientEstremiConcessione()).append("\n");
	    }
	    this.estremiAtto = estremi.toString();
	}
    }

    public String getReturnTo() {

	return returnTo;
    }

    public void setReturnTo(String returnTo) {

	this.returnTo = returnTo;
    }

    public Integer getIdAutorizzazioni() {

	return idAutorizzazioni;
    }

    public void setIdAutorizzazioni(Integer idAutorizzazioni) {

	this.idAutorizzazioni = idAutorizzazioni;
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

    public EsitoCancellazioneAutOConc getEsito() {

	return esito;
    }

    public void setEsito(EsitoCancellazioneAutOConc esito) {

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

    private IdentificativoDescrizioneBean recuperaAnagrafe(Anagrafe anagrafe) {

	return new IdentificativoDescrizioneBean(anagrafe.getId().getCodice(), anagrafe.getDescrizioneRichiedente());
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }
}
