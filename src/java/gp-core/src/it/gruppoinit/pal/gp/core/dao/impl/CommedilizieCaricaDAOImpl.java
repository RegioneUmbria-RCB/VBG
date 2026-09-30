package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieCaricaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class CommedilizieCaricaDAOImpl extends BaseDAOImpl<CommedilizieCarica, PkId> implements CommedilizieCaricaDAO {

    @Override
    public Class<CommedilizieCarica> getEntityClass() {

	return CommedilizieCarica.class;
    }

    @Override
    public List<CommedilizieCarica> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordinamento", DAOOrderTypeEnum.ASC);
    }
}
