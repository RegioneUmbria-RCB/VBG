package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

import com.paevolution.ws.pagamenti_types.StatoPosizioneType;

public interface IStatoPosizionePerVerifica {

    StatoPosizioneType getStatoPosizioneType();

    int getIdModalitaPagamento();
}
