package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;

public class AzioniProtocollazioneCommand extends ProtocollazioneCommand {

    private DatiProtocolloLettoResponseType datiProtocolloLetto;
    private boolean inviaAllegati;
    private String uo;
    private String ruolo;
    private String tipoProtocollo;

    public boolean isInviaAllegati() {

	return inviaAllegati;
    }

    public void setInviaAllegati(boolean inviaAllegati) {

	this.inviaAllegati = inviaAllegati;
    }

    private boolean scompattaAllegati;

    public boolean isScompattaAllegati() {

	return scompattaAllegati;
    }

    public void setScompattaAllegati(boolean scompattaAllegati) {

	this.scompattaAllegati = scompattaAllegati;
    }

    public DatiProtocolloLettoResponseType getDatiProtocolloLetto() {

	return datiProtocolloLetto;
    }

    public void setDatiProtocolloLetto(DatiProtocolloLettoResponseType datiProtocolloLetto) {

	this.datiProtocolloLetto = datiProtocolloLetto;
    }

    public String getTipoProtocollo() {

	return tipoProtocollo;
    }

    public void setTipoProtocollo(String tipoProtocollo) {

	this.tipoProtocollo = tipoProtocollo;
    }

    public String getUo() {

	return uo;
    }

    public void setUo(String uo) {

	this.uo = uo;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }
}
