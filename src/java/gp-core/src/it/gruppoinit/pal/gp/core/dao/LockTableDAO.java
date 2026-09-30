package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.LockTable;

public interface LockTableDAO extends BaseDAO<LockTable, String> {

    public LockTable getLock(String lockName);
}
