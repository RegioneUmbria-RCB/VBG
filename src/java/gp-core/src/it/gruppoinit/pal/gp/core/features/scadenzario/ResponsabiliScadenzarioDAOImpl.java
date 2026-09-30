package it.gruppoinit.pal.gp.core.features.scadenzario;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliScadenzario;
import it.gruppoinit.pal.gp.core.features.scadenzario.dao.IResponsabiliScadenzarioDAO;

@Repository
public class ResponsabiliScadenzarioDAOImpl extends BaseDAOImpl<ResponsabiliScadenzario, PkId> implements IResponsabiliScadenzarioDAO {

    @Override
    public Class<ResponsabiliScadenzario> getEntityClass() {

	return ResponsabiliScadenzario.class;
    }
}
