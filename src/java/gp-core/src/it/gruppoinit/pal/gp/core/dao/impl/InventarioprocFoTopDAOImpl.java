package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocFoTopDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class InventarioprocFoTopDAOImpl extends BaseDAOImpl<InventarioprocFoTop, PkId> implements InventarioprocFoTopDAO {

    @Override
    public Class<InventarioprocFoTop> getEntityClass() {

	return InventarioprocFoTop.class;
    }

    @Override
    public List<InventarioprocFoTop> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
