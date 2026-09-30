package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RaggruppamentocausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class RaggruppamentocausalioneriDAOImpl extends BaseDAOImpl<Raggruppamentocausalioneri, PkId> implements RaggruppamentocausalioneriDAO {

    @Override
    public Class<Raggruppamentocausalioneri> getEntityClass() {

	return Raggruppamentocausalioneri.class;
    }

    @Override
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "rcoDescr", DAOOrderTypeEnum.ASC);
    }
}
