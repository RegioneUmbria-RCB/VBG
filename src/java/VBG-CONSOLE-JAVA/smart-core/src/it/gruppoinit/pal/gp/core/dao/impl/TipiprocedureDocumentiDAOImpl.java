package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipiprocedureDocumentiDAOImpl extends BaseDAOImpl<TipiprocedureDocumenti, PkId> implements TipiprocedureDocumentiDAO {

    @Override
    public Class<TipiprocedureDocumenti> getEntityClass() {

	return TipiprocedureDocumenti.class;
    }

    @Override
    public List<TipiprocedureDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
