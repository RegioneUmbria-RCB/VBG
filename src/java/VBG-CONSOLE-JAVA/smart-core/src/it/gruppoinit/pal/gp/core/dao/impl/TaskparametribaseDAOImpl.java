package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TaskparametribaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TaskparametribaseDAOImpl extends BaseDAOImpl<Taskparametribase, PkId> implements TaskparametribaseDAO {

    @Override
    public Class<Taskparametribase> getEntityClass() {

	return Taskparametribase.class;
    }

    @Override
    public List<Taskparametribase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "ordine", DAOOrderTypeEnum.ASC);
    }
}
