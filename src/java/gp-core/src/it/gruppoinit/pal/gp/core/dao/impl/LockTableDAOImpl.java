package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LockTableDAO;
import it.gruppoinit.pal.gp.core.domain.LockTable;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LockTableDAOImpl extends BaseDAOImpl<LockTable, String> implements LockTableDAO {

    private static final Logger log = LoggerFactory.getLogger(LockTableDAOImpl.class);

    @Override
    public LockTable getLock(String lockName) {

	log.info("getLock: provo ad acquisire il lock {}.", lockName);
	Session session = getSessionFactory().getCurrentSession();
	LockTable lock = new LockTable();
	lock.setLockName(lockName);
	session.merge(lock);
	session.flush();
	LockTable lockUpgrade = (LockTable) session.get(LockTable.class, lockName, LockMode.UPGRADE);
	log.info("getLock: lock {} acquisito.", lockUpgrade.getLockName());
	return lockUpgrade;
    }

    @Override
    public Class<LockTable> getEntityClass() {

	return LockTable.class;
    }
}
