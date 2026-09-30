package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OggettiStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OggettiStoricoDAOImpl extends BaseDAOImpl<OggettiStorico, PkId> implements OggettiStoricoDAO {

    @Override
    public Class<OggettiStorico> getEntityClass() {

	return OggettiStorico.class;
    }

    @Override
    public List<OggettiStorico> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "dataSostituzione", DAOOrderTypeEnum.ASC);
    }
}
