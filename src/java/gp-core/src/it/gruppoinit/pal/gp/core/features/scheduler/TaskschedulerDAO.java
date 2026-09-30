package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;

/**
 * 
 * @author Luca Proietti
 */
public interface TaskschedulerDAO extends BaseDAO<Taskscheduler, PkId> {

    public List<Taskscheduler> findDaEseguire();

    public List<Taskscheduler> findAttivi();

    public List<TaskBean> findAll();
}
