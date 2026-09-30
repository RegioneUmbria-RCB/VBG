package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ImpiantiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class ImpiantiDAOImpl extends BaseDAOImpl<Impianti, PkId> implements ImpiantiDAO {

    @Override
    public Class<Impianti> getEntityClass() {

	return Impianti.class;
    }

    @Override
    public List<Impianti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "impianto", DAOOrderTypeEnum.ASC);
    }
}
