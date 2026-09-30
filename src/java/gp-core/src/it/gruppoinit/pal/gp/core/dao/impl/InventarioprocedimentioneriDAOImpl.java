package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InventarioprocedimentioneriDAOImpl extends BaseDAOImpl<Inventarioprocedimentioneri, PkId> implements InventarioprocedimentioneriDAO {

    @Override
    public Class<Inventarioprocedimentioneri> getEntityClass() {

	return Inventarioprocedimentioneri.class;
    }

    @Override
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }
}
