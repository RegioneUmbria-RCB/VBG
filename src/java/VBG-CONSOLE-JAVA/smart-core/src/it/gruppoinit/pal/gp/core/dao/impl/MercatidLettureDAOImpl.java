package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatidLettureDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatidLetture;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MercatidLettureDAOImpl extends BaseDAOImpl<MercatidLetture, PkId> implements MercatidLettureDAO {

    @Override
    public Class<MercatidLetture> getEntityClass() {

	return MercatidLetture.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatidLetture> findByFilter(MercatidLetture entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("posteggio", "_posteggio");
	det.addOrder(Order.asc("_posteggio.codiceposteggio"));
	if (entity.getAnno() != null) {
	    det.add(Restrictions.eq("anno", entity.getAnno()));
	}
	if (entity.getTipiContatore() != null && entity.getTipiContatore().getId() != null) {
	    det.add(Restrictions.eq("tipiContatore.id", entity.getTipiContatore().getId()));
	}
	if (entity.getMercatiUso() != null && entity.getMercatiUso().getId().getCodice() != null) {
	    det.add(Restrictions.eq("mercatiUso.id.codice", entity.getMercatiUso().getId().getCodice()));
	}
	if (entity.getMercatiUso().getMercati() != null && entity.getMercatiUso().getMercati().getId().getCodice() != null) {
	    det.createAlias("mercatiUso", "_uso", DetachedCriteria.INNER_JOIN);
	    det.createAlias("_uso.mercati", "_mercati");
	    det.add(Restrictions.eq("_mercati.id.codice", entity.getMercatiUso().getMercati().getId().getCodice()));
	}
	if (entity.getDataLettura() != null) {
	    det.add(Restrictions.eq("dataLettura", entity.getDataLettura()));
	}
	// Filtra per data
	// Filtra per da data di inizio in poi
	if ((entity.getDataInizio() != null)) {
	    det.add(Restrictions.ge("dataInizio", entity.getDataInizio()));
	}
	// Filtra data minore uguale di quella inserita
	if ((entity.getDataFine() != null)) {
	    det.add(Restrictions.le("dataFine", entity.getDataFine()));
	}
	return (List<MercatidLetture>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatidLetture> findByLettureConImporto(MercatidLetture entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("posteggio", "_posteggio");
	det.addOrder(Order.asc("_posteggio.codiceposteggio"));
	if (entity.getAnno() != null) {
	    det.add(Restrictions.eq("anno", entity.getAnno()));
	}
	if (entity.getTipiContatore() != null && entity.getTipiContatore().getId() != null) {
	    det.add(Restrictions.eq("tipiContatore.id", entity.getTipiContatore().getId()));
	}
	if (entity.getMercatiUso() != null && entity.getMercatiUso().getId().getCodice() != null) {
	    det.add(Restrictions.eq("mercatiUso.id.codice", entity.getMercatiUso().getId().getCodice()));
	}
	if (entity.getMercatiUso().getMercati() != null && entity.getMercatiUso().getMercati().getId().getCodice() != null) {
	    det.createAlias("mercatiUso", "_uso", DetachedCriteria.INNER_JOIN);
	    det.createAlias("_uso.mercati", "_mercati");
	    det.add(Restrictions.eq("_mercati.id.codice", entity.getMercatiUso().getMercati().getId().getCodice()));
	}
	if (entity.getDataLettura() != null) {
	    det.add(Restrictions.eq("dataLettura", entity.getDataLettura()));
	}
	// Filtra per data
	// Filtra per da data di inizio in poi
	if ((entity.getDataInizio() != null)) {
	    det.add(Restrictions.ge("dataInizio", entity.getDataInizio()));
	}
	// Filtra data minore uguale di quella inserita
	if ((entity.getDataFine() != null)) {
	    det.add(Restrictions.le("dataFine", entity.getDataFine()));
	}
	det.add(Restrictions.isNotNull("importo"));
	det.add(Restrictions.gt("importo", new BigDecimal(0)));
	return (List<MercatidLetture>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Date> findDataLettura(MercatiUso mercatiUso) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("mercatiUso", "_uso", DetachedCriteria.INNER_JOIN);
	det.createAlias("_uso.mercati", "_mercati");
	det.add(Restrictions.eq("_mercati.id.codice", mercatiUso.getMercati().getId().getCodice()));
	det.add(Restrictions.eq("_uso.id.codice", mercatiUso.getId().getCodice()));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("dataLettura"));
	det.setProjection(projectionList);
	det.addOrder(Order.asc("dataLettura"));
	List<Date> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatidLetture findUltimaLetturaFinaleByPosteggio(MercatiD mercatiD) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("posteggio.id.codice", mercatiD.getId().getCodice()));
	det.addOrder(Order.desc("dataLettura"));
	List<MercatidLetture> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return new MercatidLetture();
    }
}
