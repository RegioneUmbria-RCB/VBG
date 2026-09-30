package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OCausaliriduzionitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OCausaliriduzionit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OCausaliriduzionitDAOImpl extends BaseDAOImpl<OCausaliriduzionit, PkId> implements OCausaliriduzionitDAO {

    @Override
    public Class<OCausaliriduzionit> getEntityClass() {

	return OCausaliriduzionit.class;
    }

    @Override
    public List<OCausaliriduzionit> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
