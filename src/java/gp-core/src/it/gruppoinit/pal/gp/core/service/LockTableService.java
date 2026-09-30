package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.LockTable;

public interface LockTableService {

    public LockTable getLock(String lockName);
}
