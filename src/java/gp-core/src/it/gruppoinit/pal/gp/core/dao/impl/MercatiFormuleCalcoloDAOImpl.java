package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiFormuleCalcoloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiFormuleCalcoloDAOImpl extends BaseDAOImpl<MercatiFormuleCalcolo, PkId> implements MercatiFormuleCalcoloDAO {

    @Override
    public Class<MercatiFormuleCalcolo> getEntityClass() {

	return MercatiFormuleCalcolo.class;
    }

    @Override
    public List<MercatiFormuleCalcolo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
