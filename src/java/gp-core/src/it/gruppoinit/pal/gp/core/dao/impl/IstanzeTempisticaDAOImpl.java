package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeTempisticaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzeTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeTempisticaDAOImpl extends BaseDAOImpl<IstanzeTempistica, PkId> implements IstanzeTempisticaDAO {

    @Override
    public Class<IstanzeTempistica> getEntityClass() {

	return IstanzeTempistica.class;
    }

    @Override
    public List<IstanzeTempistica> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
