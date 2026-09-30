package it.gruppoinit.pal.gp.core.features.bollettazione;

public interface IVerticalizzazioneBollettazioneService {

    boolean isAttiva();

    boolean mostraFunzioneRettifica();

    boolean mostraFunzioneAggiungiRiga();

    boolean mostraFunzioneComunicazioniMassive();

    long delayInvioPosizioni();
}
