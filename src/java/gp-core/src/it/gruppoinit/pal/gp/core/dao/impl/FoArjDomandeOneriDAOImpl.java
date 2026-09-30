package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeOneriDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoArjDomandeOneriDAOImpl extends BaseDAOImpl<FoArjDomandeOneri, PkId> implements FoArjDomandeOneriDAO {

    @Override
    public Class<FoArjDomandeOneri> getEntityClass() {

	return FoArjDomandeOneri.class;
    }

    @Override
    public List<FoArjDomandeOneri> findAll(Integer firstResult, Integer maxResult) {

	throw new RuntimeException("Metodo non implementato");
    }
}
