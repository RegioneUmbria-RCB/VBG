package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;

public interface IVerificaStatoSuVBG {

    List<VerificaStatoPosizioniDebitorie> verificaStato(Set<Integer> idDettPosizioniDebitorie) throws FunzioneBusinessRemotaException;
}
