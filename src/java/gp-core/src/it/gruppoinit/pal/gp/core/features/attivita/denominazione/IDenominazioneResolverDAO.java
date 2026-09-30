package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

public interface IDenominazioneResolverDAO {

    /**
     * Viene eseguita la query presa dal parametro della verticalizzazione QUERYDENOMINAZIONE
     * 
     * @param queryDenominazioneStr
     * @param codiceistanza
     * @return la denominazione dell'attività
     */
    public String findDenominazioneDaQuery(String queryDenominazioneStr);
}
