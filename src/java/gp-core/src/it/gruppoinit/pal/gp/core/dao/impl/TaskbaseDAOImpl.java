package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TaskbaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Taskbase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TaskbaseDAOImpl extends BaseDAOImpl<Taskbase, String> implements TaskbaseDAO {

    @Override
    public Class<Taskbase> getEntityClass() {

	return Taskbase.class;
    }

    @Override
    public List<Taskbase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "task", DAOOrderTypeEnum.ASC);
    }
}
