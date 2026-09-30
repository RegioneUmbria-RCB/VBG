package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiContabilitaTributiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiContabilitaTributiDAOImpl extends BaseDAOImpl<MercatiContabilitaTributi, PkId> implements MercatiContabilitaTributiDAO {

    @Override
    public Class<MercatiContabilitaTributi> getEntityClass() {

	return MercatiContabilitaTributi.class;
    }

    @Override
    public List<MercatiContabilitaTributi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
