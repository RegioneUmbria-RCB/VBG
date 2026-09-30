package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OTipioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OTipioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OTipioneriDAOImpl extends BaseDAOImpl<OTipioneri, PkId> implements OTipioneriDAO {

    @Override
    public Class<OTipioneri> getEntityClass() {

	return OTipioneri.class;
    }

    @Override
    public List<OTipioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
