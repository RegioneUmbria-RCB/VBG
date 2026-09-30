package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TaskschedulerparametriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TaskschedulerparametriDAOImpl extends BaseDAOImpl<Taskschedulerparametri, PkId> implements TaskschedulerparametriDAO {

    @Override
    public Class<Taskschedulerparametri> getEntityClass() {

	return Taskschedulerparametri.class;
    }

    @Override
    public List<Taskschedulerparametri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.parametro", DAOOrderTypeEnum.ASC);
    }
}
