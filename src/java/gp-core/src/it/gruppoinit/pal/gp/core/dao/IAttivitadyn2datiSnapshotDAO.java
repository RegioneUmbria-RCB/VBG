package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitadyn2datiSnapshotDAO extends BaseDAO<IAttivitadyn2datiSnapshot, IAttivitadyn2datiSnapshotId> {

    public List<IAttivitadyn2datiSnapshot> findAll(Integer firstResult, Integer maxResult);

    public int getMaxIndice(Integer codiceAttivita, Integer idmodello);

    public int getMaxIndiceMolteplicita(Integer codiceAttivita, Integer idmodello);

    public void deleteByAttivitaAndCampo(Integer codiceAttivita, Integer idCampo);

    /**
     * Elimina i dati di uno snaphost
     * 
     * @param idSnapshot
     * @param deleteOnlyAutoIns
     *            se true allora cancella solo i record con autoins=1
     */
    public void deleteBySnapshot(Integer idSnapshot, boolean deleteOnlyAutoIns);
}
