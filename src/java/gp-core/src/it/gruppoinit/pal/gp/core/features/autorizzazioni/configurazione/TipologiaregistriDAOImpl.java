package it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService.TIPO_RICERCA;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipologiaregistriDAOImpl extends BaseDAOImpl<Tipologiaregistri, PkId> implements TipologiaregistriDAO {

    @Override
    public Class<Tipologiaregistri> getEntityClass() {

	return Tipologiaregistri.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity, String codicecomune, TIPO_RICERCA tipoRicerca) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.ilike("trDescrizione", entity.getTrDescrizione(), MatchMode.ANYWHERE));
	det.createAlias("comune", "_comune", DetachedCriteria.LEFT_JOIN);
	if (StringUtils.isNotBlank(codicecomune)) {
	    Criterion isNull = Restrictions.isNull("_comune.codicecomune");
	    Criterion codComuneCrit = Restrictions.eq("_comune.codicecomune", codicecomune);
	    LogicalExpression orComuni = Restrictions.or(codComuneCrit, isNull);
	    det.add(orComuni);
	}
	if (tipoRicerca != null && tipoRicerca.equals(TIPO_RICERCA.SOLO_MANIFESTAZIONI)) {
	    det.add(Restrictions.eq("flagManifestazioni", Boolean.TRUE));
	} else if (tipoRicerca != null && tipoRicerca.equals(TIPO_RICERCA.ESCLUDI_MANIFESTAZIONI)) {
	    det.add(Restrictions.eq("flagManifestazioni", Boolean.FALSE));
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "trDescrizione", DAOOrderTypeEnum.ASC);
    }
}
