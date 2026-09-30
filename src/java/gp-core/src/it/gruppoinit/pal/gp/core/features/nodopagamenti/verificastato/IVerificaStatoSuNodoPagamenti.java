package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

public interface IVerificaStatoSuNodoPagamenti {

    List<VerificaStatoPosizioniDebitorie> verificaStato(Set<IIdPosizioneSuNodoPagamenti> idPosizioniDebitorie) throws FunzioneBusinessRemotaException;
}
