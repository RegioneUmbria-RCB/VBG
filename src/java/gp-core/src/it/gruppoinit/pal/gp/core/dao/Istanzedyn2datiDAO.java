package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;

import java.util.List;

public interface Istanzedyn2datiDAO extends BaseDAO<Istanzedyn2dati, Istanzedyn2datiId> {

    /**
     * Trova tutti i dati dinamici legati ad un'istanza
     * 
     * @param idIstanza
     *            il PkId della pratica per la quale si intende trovare il set di dati dinamici
     * @return
     */
    public List<Istanzedyn2dati> findByIstanza(PkId idIstanza);

    /**
     * 
     * <pre>
     * Ritorna una lista di istanzedyn2dati filtrati per attivita, campo (dyn2dati) e per le istanze che compongono
     * l'attivita e sono ordinati per :
     *  	1.  datavalidita dell'istanza  (desc), 
     *  	2   attivitaordine (asc)
     * 
     * @param idCampo
     * @param codiceAttivita
     * @param listaIstanze
     * @return
     * 
     * </pre>
     */
    public List<Istanzedyn2dati> findByIstanzasAndDyn2Campi(Integer idCampo, Integer codiceAttivita, List<Integer> listaIstanze, Integer firstResult,
	    Integer maxResult);

    public String findValoreById(Istanzedyn2datiId id);

    public List<Istanzedyn2datiDTO> findBandoOutput(Integer graduatoriedId);

    public List<CodiceDescrizioneBean> findModelliCheUsanoLocalizzazioneByUUID(Integer codiceistanza, String uuid, Integer firstResult,
	    Integer maxResults);

    public List<Istanzedyn2datiDTO> findValoreDecodificatoByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice,
	    Integer indiceMolteplicita);

    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo);
}
