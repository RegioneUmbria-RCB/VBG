package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SanAmbitiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.SanAmbiti;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class SanAmbitiDAOImpl extends BaseDAOImpl<SanAmbiti, Integer> implements SanAmbitiDAO {

    @Override
    public Class<SanAmbiti> getEntityClass() {

	return SanAmbiti.class;
    }

    @Override
    public List<SanAmbiti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "ambito", DAOOrderTypeEnum.ASC);
    }
}
