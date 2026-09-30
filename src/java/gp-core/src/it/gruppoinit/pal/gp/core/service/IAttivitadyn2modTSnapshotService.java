package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modTSnapshotDAO;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitadyn2modTSnapshotService extends BaseService<IAttivitadyn2modTSnapshot, IAttivitadyn2modTSnapshotId> {

    /**
     * @see IAttivitadyn2modTSnapshotDAO#findAll(Integer, Integer)
     */
    public List<IAttivitadyn2modTSnapshot> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di IAttivitadyn2modTSnapshot filtrata per IAttivitaSnapshot
     * 
     * @param codice
     * @return
     */
    public List<IAttivitadyn2modTSnapshot> findByfindByIAttivitaSnapshot(Integer codice);

    public void deleteBySnapshot(Integer idSnapshot);
}
