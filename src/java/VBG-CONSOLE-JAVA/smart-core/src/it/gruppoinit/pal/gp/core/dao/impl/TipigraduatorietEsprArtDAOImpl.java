package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatorietEsprArtDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipigraduatorietEsprArtDAOImpl extends BaseDAOImpl<TipigraduatorietEsprArt, PkId> implements TipigraduatorietEsprArtDAO {

    @Override
    public Class<TipigraduatorietEsprArt> getEntityClass() {

	return TipigraduatorietEsprArt.class;
    }

    @Override
    public List<TipigraduatorietEsprArt> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }
}
