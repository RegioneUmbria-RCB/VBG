package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitaTipologieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IAttivitaTipologieDAOImpl extends BaseDAOImpl<IAttivitaTipologie, PkId> implements IAttivitaTipologieDAO {

    @Override
    public Class<IAttivitaTipologie> getEntityClass() {

	return IAttivitaTipologie.class;
    }

    @Override
    public List<IAttivitaTipologie> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
