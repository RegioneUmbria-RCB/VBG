package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArconfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class FoArconfigurazioneDAOImpl extends BaseDAOImpl<FoArconfigurazione, FoArconfigurazioneId> implements FoArconfigurazioneDAO {

    @Override
    public Class<FoArconfigurazione> getEntityClass() {

	return FoArconfigurazione.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FoArconfigurazione findBySoftware(Software software) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.software", software.getCodice()));
	List<FoArconfigurazione> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<FoArconfigurazione> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<FoArconfigurazione> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FoArconfigurazione> findByOggetto(Integer codiceOggetto) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.or(Restrictions.eq("codiceoggettoFirma.id.codice", codiceOggetto),
		Restrictions.eq("codiceoggettoSottoscriz.id.codice", codiceOggetto)));
	return getHibernateTemplate().findByCriteria(det);
    }
}
