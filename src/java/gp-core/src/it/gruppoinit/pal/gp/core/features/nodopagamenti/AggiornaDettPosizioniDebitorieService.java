package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

public interface AggiornaDettPosizioniDebitorieService {

    void aggiornaDettaglioPosizioniDebitorie(Integer numeroMassimoPos);

    void aggiornaDettaglioPosizioneDebitoria(Integer idPosizioneDebitoria) throws FunzioneBusinessRemotaException;
}
