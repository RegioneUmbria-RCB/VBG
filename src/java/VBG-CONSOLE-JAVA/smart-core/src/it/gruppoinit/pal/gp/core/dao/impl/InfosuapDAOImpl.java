package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InfosuapDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class InfosuapDAOImpl extends BaseDAOImpl<Infosuap, PkId> implements InfosuapDAO {

    @Override
    public Class<Infosuap> getEntityClass() {

	return Infosuap.class;
    }

    @Override
    public List<Infosuap> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
