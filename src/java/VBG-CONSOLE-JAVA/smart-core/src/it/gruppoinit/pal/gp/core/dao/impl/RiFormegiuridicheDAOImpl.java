package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RiFormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class RiFormegiuridicheDAOImpl extends BaseDAOImpl<RiFormegiuridiche, String> implements RiFormegiuridicheDAO {

    @Override
    public Class<RiFormegiuridiche> getEntityClass() {

	return RiFormegiuridiche.class;
    }

    @Override
    public List<RiFormegiuridiche> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
