package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoConcessioneInserita implements IEvent {

    private Integer idConcessione;
    private String codiceComuneRiferimento;

    public EventoConcessioneInserita(Integer idConcessione, String codiceComuneRiferimento) {

	if (idConcessione == null) {
	    throw new IllegalArgumentException("L'id della concessione inserita non può essere nullo");
	}
	this.idConcessione = idConcessione;
	this.codiceComuneRiferimento = codiceComuneRiferimento;
    }

    public Integer getIdConcessione() {

	return idConcessione;
    }

    public String getCodiceComuneRiferimento() {

	return codiceComuneRiferimento;
    }
}
