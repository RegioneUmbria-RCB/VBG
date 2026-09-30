package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiaffissioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaffissioni;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipiaffissioniDAOImpl extends BaseDAOImpl<Tipiaffissioni, PkId> implements TipiaffissioniDAO {

    @Override
    public Class<Tipiaffissioni> getEntityClass() {

	return Tipiaffissioni.class;
    }

    @Override
    public List<Tipiaffissioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tipoaffissione", DAOOrderTypeEnum.ASC);
    }
}
