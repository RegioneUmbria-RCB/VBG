package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OValiditacoefficientiDAOImpl extends BaseDAOImpl<OValiditacoefficienti, PkId> implements OValiditacoefficientiDAO {

    @Override
    public Class<OValiditacoefficienti> getEntityClass() {

	return OValiditacoefficienti.class;
    }

    @Override
    public List<OValiditacoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
