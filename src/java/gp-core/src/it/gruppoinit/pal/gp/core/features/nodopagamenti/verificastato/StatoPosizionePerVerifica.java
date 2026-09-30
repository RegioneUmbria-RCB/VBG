package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

import com.paevolution.ws.pagamenti_types.StatoPosizioneType;

public class StatoPosizionePerVerifica implements IStatoPosizionePerVerifica {

    StatoPosizioneType statoPosizioneType;
    int idModalitaPagamento;

    public StatoPosizionePerVerifica(StatoPosizioneType statoPosizioneType, int idModalitaPagamento) {

	this.statoPosizioneType = statoPosizioneType;
	this.idModalitaPagamento = idModalitaPagamento;
    }

    @Override
    public StatoPosizioneType getStatoPosizioneType() {

	return this.statoPosizioneType;
    }

    @Override
    public int getIdModalitaPagamento() {

	return this.idModalitaPagamento;
    }
}
