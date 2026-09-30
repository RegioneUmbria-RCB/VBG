package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface IAttivitadyn2modTSnapshotDAO extends BaseDAO<IAttivitadyn2modTSnapshot, IAttivitadyn2modTSnapshotId> {

    public List<IAttivitadyn2modTSnapshot> findAll(Integer firstResult, Integer maxResult);

    public void deleteBySnapshot(Integer idSnapshot);
}
