package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocFoTopDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocFoTopDAOImpl extends BaseDAOImpl<AlberoprocFoTop, PkId> implements AlberoprocFoTopDAO {

    @Override
    public Class<AlberoprocFoTop> getEntityClass() {

	return AlberoprocFoTop.class;
    }

    @Override
    public List<AlberoprocFoTop> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
