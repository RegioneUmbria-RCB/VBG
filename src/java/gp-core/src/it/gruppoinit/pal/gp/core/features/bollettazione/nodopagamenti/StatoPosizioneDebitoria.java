package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.Date;

public class StatoPosizioneDebitoria {

    private String codiceStato;
    private String descrizioneStato;
    private Date dataRiferimentoStato;

    public StatoPosizioneDebitoria(String codiceStato, String descrizioneStato, Date dataRiferimentoStato) {

	super();
	this.codiceStato = codiceStato;
	this.descrizioneStato = descrizioneStato;
	this.dataRiferimentoStato = dataRiferimentoStato;
    }

    public String getCodiceStato() {

	return codiceStato;
    }

    public String getDescrizioneStato() {

	return descrizioneStato;
    }

    public Date getDataRiferimentoStato() {

	return dataRiferimentoStato;
    }
}
