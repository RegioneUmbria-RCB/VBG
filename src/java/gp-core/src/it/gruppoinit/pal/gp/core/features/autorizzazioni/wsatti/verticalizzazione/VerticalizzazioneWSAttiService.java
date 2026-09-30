package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public interface VerticalizzazioneWSAttiService {

    boolean isAttiva();

    String getClassifica();

    String getCodiceDirigente();

    String getCodiceProponente();

    Mailtipo getTestoTipoOggetto();

    String getTipoConnettore();

    String getTrattamento();

    String getUrl();

    String getUrlFirmatari();

    String getUtente();

    String getRuolo();

    String getMovPrecompilaDocAut();

    String getMovimentoCompletamentoDetermina();

    boolean isFascicolaAtto();
}
