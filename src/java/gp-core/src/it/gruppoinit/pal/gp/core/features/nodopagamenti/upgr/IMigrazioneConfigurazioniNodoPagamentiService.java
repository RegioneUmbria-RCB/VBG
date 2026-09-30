package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;

public interface IMigrazioneConfigurazioniNodoPagamentiService {

    List<String> migraConfigurazioniDaContiAParametri();

    List<String> upgrCodiceVersamento();
}
