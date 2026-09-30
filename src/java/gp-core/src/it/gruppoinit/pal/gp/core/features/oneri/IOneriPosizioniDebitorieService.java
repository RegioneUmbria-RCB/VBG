package it.gruppoinit.pal.gp.core.features.oneri;

public interface IOneriPosizioniDebitorieService {

    public boolean posizioneDebitoriaAppartieneAOnere(Integer idDettaglioPosizioneDebitoria);

    /*
     * Quando una posizione debitoria legata ad un onere viene annullata allora il messaggio di annullamento va salvato 
     * nelle note dell'onere
     */
    public void impostaNoteOnerePerPosizioneDebitoriaAnnullata(Integer idDettaglioPosizioneDebitoria, String noteAnnullamento);
}
