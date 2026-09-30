package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.types.SportelloType;

public interface NlaManager {

    /**
     * <pre>
     * 1- Il metodo inserisce un istanza sul SiGePro creadola dalla pratica inviata da people.
     * 2- Il metodo inserisce un oggetto domanda stc che traccia l'evento di tentato inserimento di una pratica da una chiamata 
     * 	  PEOPLE.
     *    Se la domanda è inserita 	: flagImportata=true
     *    Se la domanda non è inserita	: flagImportata=false
     *    
     * @param request
     * @param token
     * @return
     * </pre>
     */
    public InserimentoPraticaNLAResponse inserimentoPratica(InserimentoPraticaNLARequest request, String token) throws Exception;

    /**
     * <pre>
     * Il metodo  inserisce nuovamente una pratica inviata dal nodo NLA-STC salvata sulla tabella DOMANDESTC
     * 
     * @param request
     * @param token
     * @return
     * </pre>
     */
    public void inserimentoPraticaDaLocale(Domandestc domandestc, String token);

    /**
     * Gestisci il recupero delle schede dinamiche da una pratica inviata tramite un nodo NLA
     * 
     * @param istanza
     * @param request
     */
    public void gestioneApplicazioneMappatureSchedeDinamiche(Istanze istanza, InserimentoPraticaNLARequest request);

    @DeletableCacheElements
    public void resetObjectCached();

    public void downloadAllegatiSTCIstanza(Integer codiceIstanza, SportelloType sportelloDestinatario, String idPraticaDestinatario);

    public void downloadAllegatiSTCMovimento(Integer codiceMovimento, SportelloType sportelloDestinatario, String idPraticaDestinatario,
	    String idAttivitaDestinatario);
}
