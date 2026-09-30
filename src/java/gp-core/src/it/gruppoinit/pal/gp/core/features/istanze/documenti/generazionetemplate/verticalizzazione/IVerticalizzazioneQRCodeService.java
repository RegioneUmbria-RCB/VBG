package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

public interface IVerticalizzazioneQRCodeService {

    boolean isAttiva();

    boolean isAttivoTemplate();

    String getMac();

    String getUrl();
}
