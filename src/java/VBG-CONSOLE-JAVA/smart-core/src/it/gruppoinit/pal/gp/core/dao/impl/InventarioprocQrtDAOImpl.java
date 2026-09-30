package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.InventarioprocQrtDAO;
import it.gruppoinit.pal.gp.core.domain.InventarioprocQrt;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class InventarioprocQrtDAOImpl extends BaseDAOImpl<InventarioprocQrt, PkId> implements InventarioprocQrtDAO {

    @Override
    public Class<InventarioprocQrt> getEntityClass() {

	return InventarioprocQrt.class;
    }

    @Override
    public List<InventarioprocQrt> findAll(Integer firstResult, Integer maxResult) {

	throw new RuntimeException("METODO NON IMPLEMENTATO");
    }
}
