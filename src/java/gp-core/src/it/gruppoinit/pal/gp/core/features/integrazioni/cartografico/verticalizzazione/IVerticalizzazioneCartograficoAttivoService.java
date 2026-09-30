package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione;

public interface IVerticalizzazioneCartograficoAttivoService {

    boolean isAttiva();

    String connettore();

    String serviceUrl();

    String modelloAltriDati();

    String campoAltriDati();

    int posizioneLatitudine();

    int posizioneLongitudine();
}
