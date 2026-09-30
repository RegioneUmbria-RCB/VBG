package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniImportiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrazioniImportiDAOImpl extends BaseDAOImpl<RegistrazioniImporti, PkId> implements RegistrazioniImportiDAO {

    private ContiDAO contiDAO;

    @Autowired
    public void setContiDAO(ContiDAO contiDAO) {

	this.contiDAO = contiDAO;
    }

    @Override
    public Class<RegistrazioniImporti> getEntityClass() {

	return RegistrazioniImporti.class;
    }

    @SuppressWarnings("unchecked")
    public List<RegistrazioniImporti> findByRegistrazione(Registrazioni registrazioni) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("registrazioni", registrazioni));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniImporti> findByRegistrazioniFilter(RegistrazioniFilter filter) {

	DetachedCriteria det = getRegistrazioniImportiCriteria(filter);
	return getHibernateTemplate().findByCriteria(det);
    }

    private DetachedCriteria getRegistrazioniImportiCriteria(RegistrazioniFilter filter) {

	// FIXME questa query produce un WARNING SQL
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("registrazioni", "_registrazioni");
	criteria.createAlias("conti", "_conti");
	criteria.createAlias("_registrazioni.anagrafe", "_anagrafe");
	criteria.createAlias("_registrazioni.registrazioniCausali", "_registrazioniCausali");
	criteria.add(Restrictions.eq("_registrazioni.software.codice", ORMHelper.getSoftware()));
	criteria.addOrder(Order.asc("scadenza"));
	criteria.addOrder(Order.asc("nrRata"));
	if (filter.getRegistrazioniCausali() != null && filter.getRegistrazioniCausali().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazioni.registrazioniCausali", filter.getRegistrazioniCausali()));
	}
	if (filter.getConti() != null && filter.getConti().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("conti", filter.getConti()));
	}
	if (filter.getDataInizio() != null) {
	    criteria.add(Restrictions.ge("scadenza", filter.getDataInizio()));
	}
	if (filter.getDataFine() != null) {
	    criteria.add(Restrictions.le("scadenza", filter.getDataFine()));
	}
	if (!(filter.getProgressivo() == null || filter.getProgressivo().equals(""))) {
	    criteria.add(Restrictions.eq("_registrazioni.progressivo", filter.getProgressivo()));
	}
	if (filter.getAnagrafe() != null && filter.getAnagrafe().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazioni.anagrafe", filter.getAnagrafe()));
	}
	if (filter.getMercati() != null && filter.getMercati().getId().getCodice() != null) {
	    criteria.createAlias("_registrazioni.mercatiD", "_mercatiD").add(Restrictions.eq("_mercatiD.mercati", filter.getMercati()));
	}
	if (filter.getPosteggio().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_mercatiD.id.codice", filter.getPosteggio().getId().getCodice()));
	}
	if (filter.getMercatiUso().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazioni.mercatiUso", filter.getMercatiUso()));
	}
	if (filter.getAlberoproc() != null && filter.getAlberoproc().getId().getCodice() != null) {
	    criteria.createAlias("_registrazioni.istanze", "_istanze").add(Restrictions.eq("_istanze.alberoproc", filter.getAlberoproc()));
	}
	if (filter.getAmministrazioni() != null && filter.getAmministrazioni().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_conti.amministrazioni", filter.getAmministrazioni()));
	}
	if (filter.getImporto() != null) {
	    criteria.add(Restrictions.ge("importo", filter.getImporto()));
	}
	if (filter.getSaldo() != null) {
	    criteria.createAlias("_registrazioni.vwRegistrazionisaldo", "_vwRegistrazionisaldo");
	    criteria.add(Restrictions.ge("_vwRegistrazionisaldo.saldo", filter.getSaldo()));
	}
	criteria.add(Restrictions.eq("nonPrevedeIncassi", false));
	return criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistrazioniImporti> findByRegistrazioneGroupByConto(Registrazioni registrazioni) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("registrazioni", registrazioni));
	det.createAlias("conti", "_conti");
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("_conti.id.codice"));
	projectionList.add(Projections.sum("importo"));
	projectionList.add(Projections.max("iva")); // FIXME per quello che riguarda l'iva ci metto il max del valore
	// trovato
	det.setProjection(projectionList);
	List<Object> list = getHibernateTemplate().findByCriteria(det);
	List<RegistrazioniImporti> regList = new ArrayList<RegistrazioniImporti>();
	for (Object object : list) {
	    Object[] obj = (Object[]) object;
	    RegistrazioniImporti regImporti = new RegistrazioniImporti();
	    Conti conti = contiDAO.findById(new PkId((Integer) obj[0]));
	    regImporti.setConti(conti);
	    regImporti.setImporto((BigDecimal) obj[1]);
	    regImporti.setIva((Integer) obj[2]);
	    regList.add(regImporti);
	}
	return regList;
    }

    @Override
    public int countPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software) {

	DetachedCriteria det = getCriteriaForAggiornamentoIva(valoreIva, data, software, true);
	return ((Integer) getHibernateTemplate().findByCriteria(det).get(0)).intValue();
    }

    @Override
    public List<Integer> findPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software) {

	DetachedCriteria det = getCriteriaForAggiornamentoIva(valoreIva, data, software, false);
	return getHibernateTemplate().findByCriteria(det);
    }

    private DetachedCriteria getCriteriaForAggiornamentoIva(BigDecimal valoreIva, Date data, Software software, boolean isCount) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (software != null && StringUtils.isNotBlank(software.getCodice())) {
	    DetachedCriteria soft = det.createAlias("registrazioni", "_registrazioni");
	    soft.add(Restrictions.eq("_registrazioni.software.codice", software.getCodice()));
	}
	det.add(Restrictions.eq("nonPrevedeIncassi", false));
	if (data != null) {
	    det.add(Restrictions.ge("scadenza", data));
	}
	det.add(Restrictions.and(Restrictions.gt("iva", 0), Restrictions.ne("iva", valoreIva.intValue())));
	if (isCount) {
	    det.setProjection(Projections.rowCount());
	} else {
	    det.setProjection(Projections.groupProperty("id.codice"));
	}
	return det;
    }
}
