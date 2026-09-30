package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class SorteggitestatainfoDAOImpl extends BaseDAOImpl<Sorteggitestatainfo, PkId> implements SorteggitestatainfoDAO {

    @Override
    public Class<Sorteggitestatainfo> getEntityClass() {

	return Sorteggitestatainfo.class;
    }

    @Override
    public List<Sorteggitestatainfo> findAll(Integer firstResult, Integer maxResult) {

	return findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
