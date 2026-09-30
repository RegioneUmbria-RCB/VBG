package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.JobRepositoryDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.JobRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class JobRepositoryDAOImpl extends BaseDAOImpl<JobRepository, Integer> implements JobRepositoryDAO {

    @Override
    public Class<JobRepository> getEntityClass() {

	return JobRepository.class;
    }

    @Override
    public List<JobRepository> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "jobName", DAOOrderTypeEnum.ASC);
    }
}
