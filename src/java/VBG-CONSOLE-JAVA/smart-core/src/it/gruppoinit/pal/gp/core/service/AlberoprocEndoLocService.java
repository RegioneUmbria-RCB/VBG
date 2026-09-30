package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface AlberoprocEndoLocService extends BaseService<AlberoprocEndoLoc, PkId> {

    /**
     * Torna i record per i quali flag_intervento=1 e per la voce dell'albero specificata, indipendentemente dal fatto
     * che siano o meno pubblicati
     * 
     * @param idcomune
     * @param codiceAlbero
     * @return
     */
    public List<AlberoprocEndoLoc> findInterventiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero);

    /**
     * Torna i record per i quali flag_intervento=1 e per la voce dell'albero specificata, indipendentemente dal fatto
     * che siano o meno pubblicati
     * 
     * @param idcomune
     * @param codiceAlbero
     * @return
     */
    public List<AlberoprocEndoLoc> findInterventiPubblicatiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero);

    /**
     * Torna i record per i quali flag_intervento=1 e per la voce dell'albero specificata, indipendentemente dal fatto
     * che siano o meno pubblicati
     * 
     * @param idcomune
     * @param codiceAlbero
     * @param codiceEndoIntervento
     * @return
     */
    public List<AlberoprocEndoLoc> findInterventiPubblicatiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero, Integer codiceEndoIntervento,
	    String idcomuneEndoIntervento);

    /**
     * Torna i record per i quali flag_intervento=0 e per la voce dell'albero specificata, indipendentemente dal fatto
     * che siano o meno pubblicati
     * 
     * @param idcomune
     * @param codiceAlbero
     * @return
     */
    public List<AlberoprocEndoLoc> findEndoprocedimentiFromAlbero(String idcomuneAlberoproc, Integer codiceAlbero, String codiceComune);

    /**
     * <pre>
     * Inserisce un oggetto AlberoprocEndoLoc, il parametro booleano isEndoIdComuneBase indica:
     * 	a.isEndoIdComuneBase: true, l'inventario procedimento associato è del comune di base (imposterà all'inventario procedimento come idcomune==idcomunebase) 
     * 	b.isEndoIdComuneBase: false/null, l'inventario procedimento associato è del comune locale
     * @param entity
     * @param isEndoIdComuneBase
     * </pre>
     */
    public void insert(AlberoprocEndoLoc entity, Boolean isEndoIdComuneBase);
}
