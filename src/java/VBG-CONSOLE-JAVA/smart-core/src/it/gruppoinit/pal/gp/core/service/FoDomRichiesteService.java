package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface FoDomRichiesteService extends BaseService<FoDomRichieste, PkId> {

    public static String RICHIESTA_INTEGRAZIONE = "I";
    public static String INOLTRO_INTEGRAZIONE = "R";
    public static String RICHIESTA_CONFORMAZIONE = "C";
    public static String INOLTRO_CONFORMAZIONE = "S";
    public static String RICHIESTA_COMUNICAZIONE = "M";
    public static String INOLTRO_COMUNICAZIONE = "T";
    public static String DINIEGO = "D";
    public static String INVIO_PROVVEDIMENTO = "P";

    public static enum VERSO_IN_OUT {
	IN, OUT, TUTTE
    };

    public static String VERSO_IN_VALUE = "I";
    public static String VERSO_OUT_VALUE = "O";

    public java.util.List<FoDomRichieste> findAll(Integer firstResult, Integer maxResult);

    public List<FoDomRichieste> findByIdpratica(String identificativoPratica, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso);

    public List<FoDomRichieste> findByFoDomandeId(Integer foDomandeId, String idcomune, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso);

    public List<FoDomRichieste> findRichiesteNonLettePerUtente(String cf, String idente, Integer firstResult, Integer maxResult, VERSO_IN_OUT verso,
	    Boolean completate);

    public int countRichiesteNonLettePerUtente(String cf, String idente, VERSO_IN_OUT verso, Boolean completate);

    public List<FoDomRichieste> findRisposteByFoDomRichiesteId(Integer foDomrichiestaId, String idcomune, Integer firstResult, Integer maxResult);

    public void insertInviaRichiesta(FoDomRichieste richas);

    public FoDomRichieste findByIdmessaggio(String idMessaggio);
}
