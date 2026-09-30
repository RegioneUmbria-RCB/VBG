package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryParamDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class JobRepositoryParamDAOImpl extends BaseDAOImpl<JobRepositoryParam, Integer> implements JobRepositoryParamDAO {

    @Override
    public Class<JobRepositoryParam> getEntityClass() {

	return JobRepositoryParam.class;
    }

    @Override
    public List<JobRepositoryParam> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "etichetta", DAOOrderTypeEnum.ASC);
    }
}
