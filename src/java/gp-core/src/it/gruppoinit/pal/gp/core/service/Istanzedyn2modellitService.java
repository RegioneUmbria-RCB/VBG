package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public interface Istanzedyn2modellitService extends BaseService<Istanzedyn2modellit, Istanzedyn2modellitId> {

    /**
     * Torna la lista di record legati ad un istanza, ordinati per dyn2_modellit.descrizione
     * 
     * @param idIstanza
     *            il pkid che rappresenta l'istanza
     * 
     * @return
     * 
     * @throws BusinessValidationException
     *             se i parametri non sono corretti o validi o null
     */
    public List<Istanzedyn2modellit> findByIstanza(PkId idIstanza);
    /**
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Integer> findIdModelloByIstanzaAndIdCampo(Integer codiceIstanza, Integer idDyn2Campi);

    public List<Integer> findIdSchedeByIstanza(Integer idIstanza);

    /**
     * Rirtona true se viene trovato un record nella tabella per i filtri passati, altrimenti false
     * 
     * @param filterTable
     * @return
     */
    public boolean existsRecords(FilterTable filterTable);

    /**
     * @see Istanzedyn2modellitDAO#findByFilterTable(FilterTable)
     */
    public List<Istanzedyn2modellit> findByFilterTable(FilterTable filterTable);

    public void salvaSchedaDaModel(Integer codiceIstanza, Integer codiceModello, ModellidinamiciHelper helperScheda,
	    Map<String, String> valoriCampiDinamici, List<Integer> oggettiDaEliminare);

    /**
     * Il metodo fa una copia delle schede dell'istanza sorgente (Istanzedyn2modellit) su un istanza destinataria
     * passata
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void updateCopiaDyn2ModelliIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario);

    /**
     * Ritorna il numero di modelli collegati all'istanza
     */
    public int countByIstanza(Integer codiceIstanza);
}
