package it.gruppoinit.pal.gp.core.features.areariservata;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public interface IVerticalizzazioneAreaRiservataService {

    boolean isAttiva();

    boolean isAreaRiservataJavaAttiva();

    boolean isCentroServizi();

    boolean isCentroServiziSoloUrlBrevi();

    boolean isInvioMailNuovoUtenteRegistrato();

    String getIDCallersPerInvioMailNuovoUtenteRegistrato();

    Mailtipo getModelloPerInvioMailNuovoUtenteRegistrato();

    Mailtipo getModelloPerInvioMailResetCredenziali();

    String getUrlServletPerResetCredenziali();

    String getUrlAuthenticationOverride();

    String getUrlRigenerazioneRicevutaPratica();

    String getNomeFileRicevuta();

    String getDescrizioneFileRicevuta();

    String getUrlAvvioProcedimento();

    String getUrlJsonServiziCondivisi();

    boolean isMostraPosizioneArchivioSuIstanzePresentate();
}
