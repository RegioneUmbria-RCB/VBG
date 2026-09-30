package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiUsoDAO;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniInOutDAO;
import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoRiepiloghiIncassi;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrazioniInOutDAOImpl extends BaseDAOImpl<RegistrazioniInOut, PkId> implements RegistrazioniInOutDAO {

    private AnagrafeDAO anagrafeDAO;
    private MercatiDAO mercatiDAO;
    private MercatiDDAO mercatiDDAO;
    private ContiDAO contiDAO;
    private MercatiUsoDAO mercatiUsoDAO;

    @Autowired
    public void setMercatiUsoDAO(MercatiUsoDAO mercatiUsoDAO) {

	this.mercatiUsoDAO = mercatiUsoDAO;
    }

    @Autowired
    public void setContiDAO(ContiDAO contiDAO) {

	this.contiDAO = contiDAO;
    }

    @Autowired
    public void setMercatiDDAO(MercatiDDAO mercatiDDAO) {

	this.mercatiDDAO = mercatiDDAO;
    }

    @Autowired
    public void setMercatiDAO(MercatiDAO mercatiDAO) {

	this.mercatiDAO = mercatiDAO;
    }

    @Autowired
    public void setAnagrafeDAO(AnagrafeDAO anagrafeDAO) {

	this.anagrafeDAO = anagrafeDAO;
    }

    @Override
    public Class<RegistrazioniInOut> getEntityClass() {

	return RegistrazioniInOut.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniInOut> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.addOrder(Order.desc("dataIncasso"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistrazioniFilter> findByDataAndAnagrafeAndMercato(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("regIoAssegnazionis", "_regIoAssegnazioni");
	criteria.createAlias("_regIoAssegnazioni.registrazioniImporti", "_registrazioniImporti");
	criteria.createAlias("_registrazioniImporti.registrazioni", "_registrazioni");
	criteria.createAlias("_registrazioni.mercatiD", "_mercatiD", Criteria.LEFT_JOIN);
	if (registrazioniFilter.getAnagrafe() != null && registrazioniFilter.getAnagrafe().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("anagrafe", registrazioniFilter.getAnagrafe()));
	if (registrazioniFilter.getConti() != null && registrazioniFilter.getConti().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioniImporti.conti", registrazioniFilter.getConti()));
	if (registrazioniFilter.getMercatiUso() != null && registrazioniFilter.getMercatiUso().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioni.mercatiUso", registrazioniFilter.getMercatiUso()));
	if (registrazioniFilter.getPosteggio() != null && registrazioniFilter.getPosteggio().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioni.mercatiD", registrazioniFilter.getPosteggio()));
	if (registrazioniFilter.getMercati() != null && registrazioniFilter.getMercati().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_mercatiD.mercati", registrazioniFilter.getMercati()));
	if (registrazioniFilter.getDataInizio() != null && registrazioniFilter.getDataFine() == null) {
	    criteria.add(Restrictions.gt("dataDistinta", registrazioniFilter.getDataInizio()));
	}
	if (registrazioniFilter.getDataInizio() == null && registrazioniFilter.getDataFine() != null) {
	    criteria.add(Restrictions.lt("dataDistinta", registrazioniFilter.getDataFine()));
	}
	if (registrazioniFilter.getDataInizio() != null && registrazioniFilter.getDataFine() != null)
	    criteria.add(Restrictions.between("dataDistinta", registrazioniFilter.getDataInizio(), registrazioniFilter.getDataFine()));
	if (registrazioniFilter.getRegistrazioniCausali() != null && registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazioni.registrazioniCausaliId", registrazioniFilter.getRegistrazioniCausali().getId().getCodice()));
	}
	List<RegistrazioniFilter> result = new ArrayList<RegistrazioniFilter>();
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.NESSUN_RAGGRUPPAMENTO) {
	    criteria.addOrder(Order.asc("dataDistinta"));
	    ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.groupProperty("dataDistinta"));
	    projList.add(Projections.groupProperty("anagrafe.id.codice"));
	    projList.add(Projections.groupProperty("_mercatiD.mercati.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioni.mercatiD.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioniImporti.conti.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioni.mercatiUso.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioniImporti.iva"));
	    projList.add(Projections.sum("_regIoAssegnazioni.importo"));
	    criteria.setProjection(projList);
	    List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	    for (Object object : list) {
		RegistrazioniFilter registrazioniFilterTemp = new RegistrazioniFilter();
		Object[] resultObj = (Object[]) object;
		registrazioniFilterTemp.setDataDistinta((Date) resultObj[0]);
		Integer codiceAnagrafe = (Integer) resultObj[1];
		Anagrafe anagrafe = anagrafeDAO.findById(new PkId(codiceAnagrafe));
		registrazioniFilterTemp.setAnagrafe(anagrafe);
		Mercati mercati = mercatiDAO.findById(new PkId((Integer) resultObj[2]));
		registrazioniFilterTemp.setMercati(mercati);
		MercatiD mercatiD = mercatiDDAO.findById(new PkId((Integer) resultObj[3]));
		registrazioniFilterTemp.setPosteggio(mercatiD);
		Conti conti = contiDAO.findById(new PkId((Integer) resultObj[4]));
		registrazioniFilterTemp.setConti(conti);
		MercatiUso uso = mercatiUsoDAO.findById(new PkId((Integer) resultObj[5]));
		registrazioniFilterTemp.setMercatiUso(uso);
		Integer iva = (Integer) resultObj[6];
		registrazioniFilterTemp.setIva(iva);
		BigDecimal importo = (BigDecimal) resultObj[7];
		registrazioniFilterTemp.setImporto(importo);
		BigDecimal imponibile = importo.subtract(importo.multiply(new BigDecimal(iva)).divide(new BigDecimal(100),
			WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP));
		registrazioniFilterTemp.setImponibile(imponibile);
		result.add(registrazioniFilterTemp);
	    }
	}
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.ANAGRAFE_MERCATO_POSTEGGIO) {
	    ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.groupProperty("anagrafe.id.codice"));
	    projList.add(Projections.groupProperty("_mercatiD.mercati.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioni.mercatiD.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioniImporti.conti.id.codice"));
	    projList.add(Projections.groupProperty("_registrazioni.mercatiUso.id.codice"));
	    projList.add(Projections.sum("_regIoAssegnazioni.importo"));
	    criteria.setProjection(projList);
	    List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	    for (Object object : list) {
		RegistrazioniFilter registrazioniFilterTemp = new RegistrazioniFilter();
		Object[] resultObj = (Object[]) object;
		Integer codiceAnagrafe = (Integer) resultObj[0];
		Anagrafe anagrafe = anagrafeDAO.findById(new PkId(codiceAnagrafe));
		registrazioniFilterTemp.setAnagrafe(anagrafe);
		Mercati mercati = mercatiDAO.findById(new PkId((Integer) resultObj[1]));
		registrazioniFilterTemp.setMercati(mercati);
		MercatiD mercatiD = mercatiDDAO.findById(new PkId((Integer) resultObj[2]));
		registrazioniFilterTemp.setPosteggio(mercatiD);
		Conti conti = contiDAO.findById(new PkId((Integer) resultObj[3]));
		registrazioniFilterTemp.setConti(conti);
		MercatiUso uso = mercatiUsoDAO.findById(new PkId((Integer) resultObj[4]));
		registrazioniFilterTemp.setMercatiUso(uso);
		BigDecimal importo = (BigDecimal) resultObj[5];
		registrazioniFilterTemp.setImporto(importo);
		result.add(registrazioniFilterTemp);
	    }
	}
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.CONTI) {
	    ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.groupProperty("_registrazioniImporti.conti.id.codice"));
	    projList.add(Projections.sum("_regIoAssegnazioni.importo"));
	    criteria.setProjection(projList);
	    List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	    for (Object object : list) {
		RegistrazioniFilter registrazioniFilterTemp = new RegistrazioniFilter();
		Object[] resultObj = (Object[]) object;
		Conti conti = contiDAO.findById(new PkId((Integer) resultObj[0]));
		registrazioniFilterTemp.setConti(conti);
		BigDecimal importo = (BigDecimal) resultObj[1];
		registrazioniFilterTemp.setImporto(importo);
		result.add(registrazioniFilterTemp);
	    }
	}
	if (registrazioniFilter.getRaggruppamentoRiepiloghiIncassi() == RaggruppamentoRiepiloghiIncassi.DATADISTINTA_CONTO) {
	    criteria.addOrder(Order.asc("dataDistinta"));
	    ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.groupProperty("_registrazioniImporti.conti.id.codice"));
	    projList.add(Projections.groupProperty("dataDistinta"));
	    projList.add(Projections.sum("_regIoAssegnazioni.importo"));
	    criteria.setProjection(projList);
	    List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	    for (Object object : list) {
		RegistrazioniFilter registrazioniFilterTemp = new RegistrazioniFilter();
		Object[] resultObj = (Object[]) object;
		Conti conti = contiDAO.findById(new PkId((Integer) resultObj[0]));
		registrazioniFilterTemp.setConti(conti);
		registrazioniFilterTemp.setDataDistinta((Date) resultObj[1]);
		BigDecimal importo = (BigDecimal) resultObj[2];
		registrazioniFilterTemp.setImporto(importo);
		result.add(registrazioniFilterTemp);
	    }
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistrazioniInOut> findByFilter(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("regIoAssegnazionis", "_regIoAssegnazioni");
	criteria.createAlias("_regIoAssegnazioni.registrazioniImporti", "_registrazioniImporti");
	criteria.createAlias("_registrazioniImporti.registrazioni", "_registrazioni");
	criteria.createAlias("_registrazioni.mercatiD", "_mercatiD", Criteria.LEFT_JOIN);
	if (registrazioniFilter.getAnagrafe() != null && registrazioniFilter.getAnagrafe().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("anagrafe", registrazioniFilter.getAnagrafe()));
	if (registrazioniFilter.getConti() != null && registrazioniFilter.getConti().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioniImporti.conti", registrazioniFilter.getConti()));
	if (registrazioniFilter.getMercatiUso() != null && registrazioniFilter.getMercatiUso().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioni.mercatiUso", registrazioniFilter.getMercatiUso()));
	if (registrazioniFilter.getPosteggio() != null && registrazioniFilter.getPosteggio().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_registrazioni.mercatiD", registrazioniFilter.getPosteggio()));
	if (registrazioniFilter.getMercati() != null && registrazioniFilter.getMercati().getId().getCodice() != null)
	    criteria.add(Restrictions.eq("_mercatiD.mercati", registrazioniFilter.getMercati()));
	if (registrazioniFilter.getDataInizio() != null && registrazioniFilter.getDataFine() == null) {
	    criteria.add(Restrictions.gt("dataDistinta", registrazioniFilter.getDataInizio()));
	}
	if (registrazioniFilter.getDataInizio() == null && registrazioniFilter.getDataFine() != null) {
	    criteria.add(Restrictions.lt("dataDistinta", registrazioniFilter.getDataFine()));
	}
	if (registrazioniFilter.getRegistrazioniCausali() != null && registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazioni.registrazioniCausaliId", registrazioniFilter.getRegistrazioniCausali().getId().getCodice()));
	}
	if (registrazioniFilter.getDataInizio() != null && registrazioniFilter.getDataFine() != null)
	    criteria.add(Restrictions.between("dataDistinta", registrazioniFilter.getDataInizio(), registrazioniFilter.getDataFine()));
	List<RegistrazioniInOut> result = getHibernateTemplate().findByCriteria(criteria);
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
