package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcTipointerventoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcTipointervento;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcTipointerventoDAOImpl extends BaseDAOImpl<CcTipointervento, PkId> implements CcTipointerventoDAO {

    @Override
    public Class<CcTipointervento> getEntityClass() {

	return CcTipointervento.class;
    }

    @Override
    public List<CcTipointervento> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "intervento", DAOOrderTypeEnum.ASC);
    }
}
