package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FormegiuridicheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 * 
 */
@Repository
public class FormegiuridicheDAOImpl extends BaseDAOImpl<Formegiuridiche, PkId> implements FormegiuridicheDAO {

    @Override
    public Class<Formegiuridiche> getEntityClass() {

	return Formegiuridiche.class;
    }

    @Override
    public List<Formegiuridiche> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "formagiuridica", DAOOrderTypeEnum.ASC);
    }
}
