package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;

public interface IMigrazioneConfigurazioniDAO {

    List<String> eseguiMigrazione();

    List<String> upgrCodiceVersamentoPerCausaliSingole();
}
