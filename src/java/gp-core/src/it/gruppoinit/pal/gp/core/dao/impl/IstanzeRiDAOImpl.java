package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeRiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeRi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeRiDAOImpl extends BaseDAOImpl<IstanzeRi, PkId> implements IstanzeRiDAO {

    @Override
    public Class<IstanzeRi> getEntityClass() {

	return IstanzeRi.class;
    }

    @Override
    public List<IstanzeRi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
