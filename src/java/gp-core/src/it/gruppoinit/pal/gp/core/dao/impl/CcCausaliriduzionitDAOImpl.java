package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcCausaliriduzionitDAOImpl extends BaseDAOImpl<CcCausaliriduzionit, PkId> implements CcCausaliriduzionitDAO {

    @Override
    public Class<CcCausaliriduzionit> getEntityClass() {

	return CcCausaliriduzionit.class;
    }

    @Override
    public List<CcCausaliriduzionit> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
