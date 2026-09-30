package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;

public class InformativaModel {

    private String messaggio;
    private Date dataFineValidita;

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public Date getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public static InformativaModel fromBorsellinoInformative(BorsellinoInformative info) {

	if (info == null) {
	    return new InformativaModel();
	}
	InformativaModel im = new InformativaModel();
	im.messaggio = info.getInformativa();
	im.dataFineValidita = info.getDataFineValidita();
	return im;
    }
}
