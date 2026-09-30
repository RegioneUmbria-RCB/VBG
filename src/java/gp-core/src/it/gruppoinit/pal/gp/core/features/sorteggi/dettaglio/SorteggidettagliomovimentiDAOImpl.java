package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;
import it.gruppoinit.pal.gp.core.domain.SorteggidettagliomovimentiId;

/**
 * 
 * @author
 */
@Repository
public class SorteggidettagliomovimentiDAOImpl extends BaseDAOImpl<Sorteggidettagliomovimenti, SorteggidettagliomovimentiId>
	implements SorteggidettagliomovimentiDAO {

    @Override
    public Class<Sorteggidettagliomovimenti> getEntityClass() {

	return Sorteggidettagliomovimenti.class;
    }

    @Override
    public List<Sorteggidettagliomovimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
