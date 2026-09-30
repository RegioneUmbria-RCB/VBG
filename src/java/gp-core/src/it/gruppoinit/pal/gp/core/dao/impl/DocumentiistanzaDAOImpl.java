package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DocumentiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class DocumentiistanzaDAOImpl extends BaseDAOImpl<Documentiistanza, PkId> implements DocumentiistanzaDAO {

    private static final Logger log = LoggerFactory.getLogger(DocumentiistanzaDAOImpl.class);

    @Override
    public Class<Documentiistanza> getEntityClass() {

	return Documentiistanza.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza) {

	Assert.notNull(codiceIstanza);
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", codiceIstanza));
	det.addOrder(Order.asc("documento"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documentiistanza> findByIstanzaOggetto(Integer codiceistanza) {

	Assert.notNull(codiceistanza);
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", codiceistanza));
	det.add(Restrictions.isNotNull("oggetto.id.codice"));
	det.addOrder(Order.asc("data"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico, Boolean isCodiceOggetto) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("alberoprocDocumenticat", "_alberoprocDocumenticat", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.comune", "_comune");
	criteria.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	if (flgDaModelloDinamico != null) {
	    if (flgDaModelloDinamico.equals(Boolean.TRUE)) {
		// se flgDaModelloDinamico = true cerco quelli inseriti dalle schede dinamiche
		criteria.add(Restrictions.eq("flgDaModelloDinamico", flgDaModelloDinamico));
	    } else {
		criteria.add(Restrictions.or(Restrictions.eq("flgDaModelloDinamico", flgDaModelloDinamico),
			Restrictions.isNull("flgDaModelloDinamico")));
	    }
	}
	if (isCodiceOggetto != null) {
	    log.debug("findDocumentiistanzaDTOByIstanza# isCodiceOggetto={}", isCodiceOggetto);
	    if (isCodiceOggetto) {
		criteria.add(Restrictions.isNotNull("_oggetto.id.codice"));
	    } else {
		criteria.add(Restrictions.isNull("_oggetto.id.codice"));
	    }
	}
	criteria.addOrder(Order.asc("documento"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("data"), "DATA");
	plist.add(Projections.property("documento"), "DOCUMENTO");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("necessario"), "NECESSARIO");
	plist.add(Projections.property("presente"), "PRESENTE");
	plist.add(Projections.property("stcIddocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdallegato"), "STCIDALLEGATO");
	plist.add(Projections.property("idBase"), "IDBASE");
	plist.add(Projections.property("idDocer"), "IDDOCER");
	plist.add(Projections.property("flgDaModelloDinamico"), "FLGDAMODELLODINAMICO");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_oggetto.dimensioneFile"), "DIMENSIONEFILE");
	plist.add(Projections.property("_alberoprocDocumenticat.descrizione"), "ALBEROPROCDOCUMENTICAT");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(DocumentiistanzaDTO.class));
	List<DocumentiistanzaDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico) {

	return findDocumentiistanzaDTOByIstanza(codiceIstanza, flgDaModelloDinamico, null);
    }

    @Override
    public void updatePresente(Integer codiceDocIstanza, Boolean isCheked) {

	if (codiceDocIstanza == null) {
	    throw new RuntimeException("updatePresente: il parametro documento istanza  passato è nullo");
	}
	String hql = "update Documentiistanza set presente = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { isCheked, ORMHelper.getIdcomune(), codiceDocIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento del flag presente del documento istanza :[" + codiceDocIstanza + "] ha modificato "
		    + i + " record");
	}
    }

    @Override
    public void updateNecessario(Integer codiceDocIstanza, boolean necessario) {

	if (codiceDocIstanza == null) {
	    throw new RuntimeException("updatePresente: il parametro documento istanza  passato è nullo");
	}
	String hql = "update Documentiistanza set necessario = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { necessario, ORMHelper.getIdcomune(), codiceDocIstanza });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento del flag necessario del documento istanza :[" + codiceDocIstanza
		    + "] ha modificato " + i + " record");
	}
    }
    
    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza) {

	return findDocumentiistanzaDTOByIstanza(codiceIstanza, null, null);
    }
}
