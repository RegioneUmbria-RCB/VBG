package it.gruppoinit.pal.gp.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.gruppoinit.pal.gp.core.dao.LockTableDAO;
import it.gruppoinit.pal.gp.core.domain.LockTable;
import it.gruppoinit.pal.gp.core.service.LockTableService;

@Service
public class LockTableServiceImpl implements LockTableService {

    private LockTableDAO lockTableDAO;

    @Autowired
    public LockTableServiceImpl(LockTableDAO lockTableDAO) {

	this.lockTableDAO = lockTableDAO;
    }

    /**
     * Questo metodo esegue il lock del record corrispondente ad un valore passato come argomento. inserendo nella
     * tabella LOCK_TABLE <br>
     * Il lock funziona bloccando il record su db fino alla conclusione del thread. Per funzionare, il record deve
     * essere presente su db altrimenti il lock non viene acquisito. Il metodo al termine della transazione non
     * inserisce su db perchè i metodi che iniziano con get sono registrati come readonly a livello di transazione
     */
    @Override
    @Transactional(readOnly = true)
    public LockTable getLock(String lockName) {

	return lockTableDAO.getLock(lockName);
    }
}
