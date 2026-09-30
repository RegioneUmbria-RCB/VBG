package it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;

public interface IVerticalizzazioneIAttivitaService {

    boolean isAttiva();

    boolean isAggiornaDenominazione();

    Dyn2Campi getCampoDynFineAtt();

    String getGruppoSoftware();

    boolean isInvertiRichiedenteStorico();

    boolean isNonConsiderareIstanzeCollegate();

    boolean isPrecompilaIndirizzoCivico();

    String getQueryDenominazione();
}
