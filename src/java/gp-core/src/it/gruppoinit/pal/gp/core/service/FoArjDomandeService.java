package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface FoArjDomandeService extends BaseService<FoArjDomande, PkId> {

    public List<FoArjDomande> findAll(Integer firstResult, Integer maxResult);

    public List<FoArjDomande> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    public void evict(FoArjDomande foArjDomande);

    /**
     * Ritorna tutte le domande per le quali FO_ARJ_DOMANDE.DATA_INVIO == NULLO E FLAG_INVALIDATA<>1 se software non è
     * specificato allora ricerca le domande sul software corrente
     * 
     * @return
     */
    public List<FoArjDomande> findDomandePerSoftware(String software, Integer firstResult, Integer maxResult);

    /**
     * Ritorna tutte le domande dell'utente autenticato per il software corrente dove la data_invio è vuota. Le domande
     * sono ordinate per dataUltimaModifica e codice DESC
     * 
     * 
     * @return
     */
    public List<FoArjDomande> findDomandePerUtente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Ritorna tutte le domande dell'utente autenticato per il servizio specificato e per il software corrente dove la
     * data_invio è vuota. Le domande sono ordinate per dataUltimaModifica e codice DESC
     * 
     * 
     * @return
     */
    public List<FoArjDomande> findDomandePerUtenteEServizio(Integer codiceAnagrafe, Integer codiceServizio, Integer firstResult, Integer maxResult);
}
