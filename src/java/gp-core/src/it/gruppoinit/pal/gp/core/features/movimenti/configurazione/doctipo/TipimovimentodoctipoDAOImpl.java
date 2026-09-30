package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.TipimovimentodoctipoId;

@Repository
public class TipimovimentodoctipoDAOImpl extends BaseDAOImpl<Tipimovimentodoctipo, TipimovimentodoctipoId> implements TipimovimentodoctipoDAO {

    @Override
    public Class<Tipimovimentodoctipo> getEntityClass() {

	return Tipimovimentodoctipo.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tipimovimentodoctipo findByTipoMovimentoAndTipoLettera(String codicemovimento, Integer codicelettera) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add((Restrictions.eq("letteretipo.id.codice", codicelettera)));
	criteria.add((Restrictions.eq("tipomovimento.id.tipomovimento", codicemovimento)));
	List<Tipimovimentodoctipo> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciLettereAutomaticheByTipoMovimentoAndFase(String tipoMovimento, FasiDiEsecuzioneEnum faseEsecuzione) {

	if (StringUtils.isEmpty(tipoMovimento)) {
	    throw new IllegalArgumentException("findCodiciLettereAutomaticheByTipoMovimentoAndFase: il parametro tipoMovimento non è stato indicato");
	}
	if (faseEsecuzione == null) {
	    throw new IllegalArgumentException(
		    "findCodiciLettereAutomaticheByTipoMovimentoAndFase: il parametro faseEsecuzione non è stato indicato");
	}
	String sql = "select codicelettera from tipimovimentodoctipo where idcomune = ? and tipomovimento = ? and flg_generaaut = ? and fase_esecuzione = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Tipimovimentodoctipo.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, tipoMovimento);
	query.setInteger(2, 1);
	query.setString(3, faseEsecuzione.toString());
	query.addScalar("codicelettera", Hibernate.INTEGER);
	return query.list();
    }
}