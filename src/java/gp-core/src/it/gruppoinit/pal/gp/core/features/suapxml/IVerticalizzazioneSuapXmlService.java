package it.gruppoinit.pal.gp.core.features.suapxml;

public interface IVerticalizzazioneSuapXmlService {

    boolean isAttiva();

    String url();

    boolean generaSuInserimentoIstanza();

    boolean visualizzaBottoneSuProtocollazioneMovimento();

    boolean valida();
}
