package it.gruppoinit.pal.gp.core.features.anagrafe.verticalizzazioni;

public interface IVertRicercaAnagrafeCollegataInternalService {

    public static final String NOME_VERTICALIZZAZIONE = "RIC_ANAG_COLL_INTERNAL";
    public static final String PAR_STRATEGIA_RICERCA = "STRATEGIA_RICERCA";

    enum STRATEGIA_RICERCA {
	DEFAULT
    }

    boolean isAttiva();

    STRATEGIA_RICERCA getStrategiaRicerca();
}
