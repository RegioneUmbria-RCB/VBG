package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiSnapshotDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitadyn2datiSnapshotService extends BaseService<IAttivitadyn2datiSnapshot, IAttivitadyn2datiSnapshotId> {

    /**
     * @see IAttivitadyn2datiSnapshotDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2datiSnapshot> findAll(Integer firstResult, Integer maxResult);

    /**
     * Il metodo deve inserire/aggiornare i campi di IAttivitadyn2datiSnapshot collegati a IAttivitaSnapShot con i
     * valori presenti in I_ATTIVITADYN2MODELLIT
     * 
     * @param codiceIAttivita
     */
    public void updateCampiSchedeDinamicheSnapShot(Integer codiceIAttivita, Integer codiceIattivitaSnapShot, List<Integer> istanzes);

    /**
     * Ritorna un alista di IAttivitadyn2datiSnapshot filtrate per IAttivitaSnapshot
     * 
     * @param codice
     * @return
     */
    public List<IAttivitadyn2datiSnapshot> findByIAttivitaSnapshot(Integer codice);

    /**
     * Ritornauna lista di campi (Dyn2Campi) filtrati per codiceAttivita,codiceAttivitaSnapshot,codice campo e ordinati
     * per il campo molteplicità
     * 
     * @param codiceAttivita
     * @param codiceAttivitaSnapshot
     * @param codice
     * @return
     */
    public List<IAttivitadyn2datiSnapshot> findByAttivitaAndAttivitaSnapshotAndCampo(Integer codiceAttivita, Integer codiceAttivitaSnapshot,
	    Integer codice);

    public int getMaxIndice(Integer codiceAttivita, Integer idmodello);

    public int getMaxIndiceMolteplicita(Integer codiceAttivita, Integer idmodello);

    public void deleteByAttivitaAndCampo(Integer codiceAttivita, Integer idCampo);

    /**
     * @see IAttivitadyn2datiSnapshotDAO#deleteBySnapshot(Integer, boolean)
     * @param idSnapshot
     * @param deleteOnlyAutoIns
     */
    public void deleteBySnapshot(Integer idSnapshot, boolean deleteOnlyAutoIns);
}
