package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipidocumentoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class TipidocumentoDAOImpl extends BaseDAOImpl<Tipidocumento, PkId> implements TipidocumentoDAO {

    @Override
    public Class<Tipidocumento> getEntityClass() {

	return Tipidocumento.class;
    }
}
