package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiUsoDAO;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.domain.SituazioneContabile;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MercatipresenzeTDAOImpl extends BaseDAOImpl<MercatipresenzeT, PkId> implements MercatipresenzeTDAO {

    @Override
    public Class<MercatipresenzeT> getEntityClass() {

	return MercatipresenzeT.class;
    }

    private MercatiUsoDAO mercatiUsoDAO;
    private SoftwareDAO softwareDAO;
    private MercatiDAO mercatiDAO;

    @Autowired
    public void setMercatiDAO(MercatiDAO mercatiDAO) {

	this.mercatiDAO = mercatiDAO;
    }

    @Autowired
    public void setSoftwareDAO(SoftwareDAO softwareDAO) {

	this.softwareDAO = softwareDAO;
    }

    @Autowired
    public void setMercatiUsoDAO(MercatiUsoDAO mercatiUsoDAO) {

	this.mercatiUsoDAO = mercatiUsoDAO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	criteria.add(Restrictions.eq("mercato", mercati));
	criteria.add(Restrictions.eq("mercatoUso", mercatiUso));
	criteria.add(Restrictions.eq("anno", anno));
	criteria.addOrder(Order.desc("dataRegistrazione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public boolean findDay(List<MercatipresenzeT> list, Calendar date) {

	boolean success = false;
	Calendar giorno = null;
	Date data = null;
	for (MercatipresenzeT mercatipresenzeT : list) {
	    data = mercatipresenzeT.getDataRegistrazione();
	    giorno = Calendar.getInstance();
	    giorno.setTime(data);
	    if (date.get(Calendar.YEAR) == giorno.get(Calendar.YEAR) && date.get(Calendar.MONTH) == giorno.get(Calendar.MONTH)
		    && date.get(Calendar.DAY_OF_MONTH) == giorno.get(Calendar.DAY_OF_MONTH)) {
		success = true;
		break;
	    }
	}
	return success;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatipresenzeT findByDataregistrazioneAndMercatoAndMercatoUso(Calendar date, Mercati mercati, MercatiUso mercatiUso) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	criteria.add(Restrictions.eq("mercato", mercati));
	criteria.add(Restrictions.eq("mercatoUso", mercatiUso));
	criteria.add(Restrictions.eq("dataRegistrazione", date.getTime()));
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findByMercatoAndGroupByAnnoAndMercatoUso(Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	criteria.add(Restrictions.eq("mercato", mercati));
	// Fa una proiezione solo su anno e mercato uso
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.groupProperty("anno"));
	projList.add(Projections.groupProperty("mercatoUso.id.codice"));
	criteria.setProjection(projList);
	criteria.addOrder(Order.asc("anno"));
	MercatipresenzeT temp = null;
	List<MercatipresenzeT> listmercatiregistrati = new ArrayList<MercatipresenzeT>();
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	Iterator iter = list.iterator();
	// Ricostruisco l'ogetto mercatipresenze_t a partire dagli oggetti
	// recuperati con la query
	while (iter.hasNext()) {
	    Object[] obj = (Object[]) iter.next();
	    temp = new MercatipresenzeT();
	    Integer annoShort = (Integer) obj[0];
	    Integer idMercartoUso = (Integer) obj[1];
	    MercatiUso mercatoUso = mercatiUsoDAO.findById(new PkId(idMercartoUso));
	    temp.setAnno(annoShort);
	    temp.setMercatoUso(mercatoUso);
	    temp.setMercato(mercati);
	    temp.setDataRegistrazione(new Date());
	    temp.setSoftware(new Software());
	    temp.setResponsabile(new Responsabili());
	    listmercatiregistrati.add(temp);
	}
	return listmercatiregistrati;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findByMercatoAndAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	List<MercatipresenzeT> mercatiPresenzeTList = new ArrayList<MercatipresenzeT>();
	MercatipresenzeT temp = null;
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	if (EntityUtils.getNestedProperty(mercati, "id.codice") != null) {
	    criteria.add(Restrictions.eq("mercato.id.codice", mercati.getId().getCodice()));
	}
	if (null != anno) {
	    criteria.add(Restrictions.eq("anno", anno));
	}
	criteria.addOrder(Order.desc("anno"));
	criteria.addOrder(Order.asc("mercato.id.codice"));
	criteria.addOrder(Order.asc("mercatoUso.id.codice"));
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.groupProperty("anno"));
	projList.add(Projections.groupProperty("mercatoUso.id.codice"));
	projList.add(Projections.groupProperty("mercato.id.codice"));
	criteria.setProjection(projList);
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	Iterator iter = list.iterator();
	// Ricostruisco l'ogetto mercatipresenze_t a partire dagli oggetti
	// recuperati con la query
	while (iter.hasNext()) {
	    Object[] obj = (Object[]) iter.next();
	    temp = new MercatipresenzeT();
	    Integer annoShort = (Integer) obj[0];
	    Integer idMercartoUso = (Integer) obj[1];
	    Integer idMercato = (Integer) obj[2];
	    MercatiUso mercatoUso = mercatiUsoDAO.findById(new PkId(idMercartoUso));
	    Mercati mercato = mercatiDAO.findById(new PkId(idMercato));
	    temp.setAnno(annoShort);
	    temp.setMercatoUso(mercatoUso);
	    temp.setMercato(mercato);
	    temp.setDataRegistrazione(new Date());
	    temp.setSoftware(new Software());
	    temp.setResponsabile(new Responsabili());
	    mercatiPresenzeTList.add(temp);
	}
	return mercatiPresenzeTList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean verificaGestionePresenze(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	boolean storicizzabile = false;
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("mercato.id.codice", mercati.getId().getCodice()));
	criteria.add(Restrictions.eq("anno", anno.shortValue()));
	criteria.add(Restrictions.eq("mercatoUso.id.codice", mercatiUso.getId().getCodice()));
	criteria.add(Restrictions.eq("flagPresenze", false));
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	// se esiste almeno un record con flagPresenze=false(0) allora non è storicizzabile
	if (list.isEmpty()) {
	    storicizzabile = true;
	}
	return storicizzabile;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean verificaMercatoStoricizzato(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	boolean mercatoStoricizzato = false;
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("mercato.id.codice", mercati.getId().getCodice()));
	criteria.add(Restrictions.eq("anno", anno));
	criteria.add(Restrictions.eq("mercatoUso.id.codice", mercatiUso.getId().getCodice()));
	criteria.add(Restrictions.eq("flagPresenzeArchivio", false));
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	// se esiste almeno un record con flagPresenzeArchivio=false allora il mercato non è stato storicizzato
	if (list.isEmpty()) {
	    mercatoStoricizzato = true;
	}
	return mercatoStoricizzato;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findMercatipresenzetFiereByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	List<MercatipresenzeT> listForFiere = new ArrayList<MercatipresenzeT>();
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	criteria.add(Restrictions.eq("mercato", mercati));
	criteria.add(Restrictions.eq("mercatoUso", mercatiUso));
	criteria.add(Restrictions.eq("anno", anno));
	criteria.addOrder(Order.desc("dataRegistrazione"));
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    listForFiere.add(list.get(0));
	}
	return listForFiere;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Riepilogomercato> findByMercatoOrAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	List<MercatipresenzeT> mercatiPresenzeTList = new ArrayList<MercatipresenzeT>();
	MercatipresenzeT temp = null;
	// creo la query che mi ritorna una tupla formata da mercato anno mercatouso
	// la query viene fatta di mercatipresenzeT perche contiene tutti i tre parametri da recuperare
	// la ricerca viene filtra per anno e/o mercato o non viene filtrata
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software", softwareDAO.findById(ORMHelper.getSoftware())));
	ProjectionList projList = Projections.projectionList();
	if (EntityUtils.getNestedProperty(mercati, "id.codice") != null) {
	    criteria.createAlias("mercato", "_mercato");
	    criteria.add(Restrictions.eq("_mercato.id.codice", mercati.getId().getCodice()));
	    criteria.add(Restrictions.eq("_mercato.flagContabilita", true));
	    criteria.add(Restrictions.eq("_mercato.attivo", true));
	}
	if (anno != null) {
	    criteria.add(Restrictions.eq("anno", anno));
	}
	projList.add(Projections.groupProperty("mercato.id.codice"));
	projList.add(Projections.groupProperty("anno"));
	projList.add(Projections.groupProperty("mercatoUso.id.codice"));
	criteria.addOrder(Order.asc("mercato.id.codice"));
	criteria.addOrder(Order.asc("mercatoUso.id.codice"));
	criteria.addOrder(Order.asc("anno"));
	criteria.setProjection(projList);
	List<MercatipresenzeT> list = getHibernateTemplate().findByCriteria(criteria);
	Iterator iter = list.iterator();
	// Ricostruisco l'ogetto mercatipresenze_t a partire dagli oggetti
	// recuperati con la query
	while (iter.hasNext()) {
	    Object[] obj = (Object[]) iter.next();
	    temp = new MercatipresenzeT();
	    Integer annoShort = (Integer) obj[1];
	    Integer idMercartoUso = (Integer) obj[2];
	    MercatiUso mercatoUso = mercatiUsoDAO.findById(new PkId(idMercartoUso));
	    temp.setAnno(annoShort);
	    temp.setMercatoUso(mercatoUso);
	    temp.setMercato(mercatiDAO.findById(new PkId((Integer) obj[0])));
	    temp.setDataRegistrazione(new Date());
	    temp.setSoftware(new Software());
	    temp.setResponsabile(new Responsabili());
	    mercatiPresenzeTList.add(temp);
	}
	// Crea una lista del bean RiepilogoMercato utilizzato per la visualizzazione
	List<Riepilogomercato> risultato = createRiepilogoMercatoList(mercatiPresenzeTList);
	return risultato;
    }

    private List<Riepilogomercato> createRiepilogoMercatoList(List<MercatipresenzeT> list) {

	List<Riepilogomercato> riepilogomercatolist = new ArrayList<Riepilogomercato>();
	Riepilogomercato riepilogomercato = null;
	MercatipresenzeT mercatipresenzeT = null;
	Iterator iterator = list.iterator();
	Integer codicemercatoprecedente = null;
	Integer codicemercatousoprecedente = null;
	// crea una lista di Riepilogomercato inserendo escludendo i mercati che si ripetono
	while (iterator.hasNext()) {
	    mercatipresenzeT = new MercatipresenzeT();
	    mercatipresenzeT = (MercatipresenzeT) iterator.next();
	    if (!(mercatipresenzeT.getMercato().getId().getCodice().equals(codicemercatoprecedente) && mercatipresenzeT.getMercatoUso().getId()
		    .getCodice().equals(codicemercatousoprecedente))) {
		riepilogomercato = new Riepilogomercato();
		riepilogomercato.setMercati(mercatiDAO.findById(new PkId(mercatipresenzeT.getMercato().getId().getCodice())));
		riepilogomercato.setMercatiUso(mercatiUsoDAO.findById(new PkId(mercatipresenzeT.getMercatoUso().getId().getCodice())));
		riepilogomercatolist.add(riepilogomercato);
	    }
	    codicemercatoprecedente = mercatipresenzeT.getMercato().getId().getCodice();
	    codicemercatousoprecedente = mercatipresenzeT.getMercatoUso().getId().getCodice();
	}
	// per ogni bean Riepilogomercato della lista "riepilogomercatolist" creata al passo precedente
	// setta le varie situazioni contabili
	SituazioneContabile situazionecontabile = null;
	List<SituazioneContabile> situazionecontabilelist = null;
	Iterator iterator1 = list.iterator();
	for (Iterator iterator2 = riepilogomercatolist.iterator(); iterator2.hasNext();) {
	    Riepilogomercato riepilogomercato2 = (Riepilogomercato) iterator2.next();
	    situazionecontabilelist = new ArrayList<SituazioneContabile>();
	    while (iterator1.hasNext()) {
		mercatipresenzeT = new MercatipresenzeT();
		mercatipresenzeT = (MercatipresenzeT) iterator1.next();
		if (riepilogomercato2.getMercati().getId().getCodice().equals(mercatipresenzeT.getMercato().getId().getCodice())
			&& riepilogomercato2.getMercatiUso().getId().getCodice().equals(mercatipresenzeT.getMercatoUso().getId().getCodice())) {
		    situazionecontabile = new SituazioneContabile();
		    situazionecontabile.setAnno(mercatipresenzeT.getAnno().shortValue());
		    situazionecontabilelist.add(situazionecontabile);
		}
		riepilogomercato2.setSituazionecontabileList(situazionecontabilelist);
	    }
	    iterator1 = list.iterator();
	}
	return riepilogomercatolist;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findAnniMercatiPresenti() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.setProjection(Projections.distinct(Projections.property("anno")));
	criteria.addOrder(Order.desc("anno"));
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	List<MercatipresenzeT> result = new ArrayList<MercatipresenzeT>();
	MercatipresenzeT mercatipresenzeT = null;
	Iterator iterator = list.iterator();
	while (iterator.hasNext()) {
	    mercatipresenzeT = new MercatipresenzeT();
	    mercatipresenzeT.setAnno((Integer) iterator.next());
	    result.add(mercatipresenzeT);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUsoFiere(Mercati mercati, MercatiUso mercatiUso) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("mercato.id.codice", mercati.getId().getCodice()));
	criteria.add(Restrictions.eq("mercatoUso.id.codice", mercatiUso.getId().getCodice()));
	criteria.addOrder(Order.desc("dataRegistrazione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createCriteria("mercato", "_mercato", DetachedCriteria.INNER_JOIN);
	if (uso != null) {
	    criteria.createCriteria("mercatoUso", "_uso", DetachedCriteria.INNER_JOIN);
	}
	if (anno != null && anno.shortValue() > 0) {
	    criteria.add(Restrictions.eq("anno", anno));
	}
	criteria.add(Restrictions.eq("mercato.id.codice", mercato.getId().getCodice()));
	if (uso != null) {
	    criteria.add(Restrictions.eq("mercatoUso.id.codice", uso.getId().getCodice()));
	}
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("dataRegistrazione"), "giornoReg");
	projectionList.add(Projections.groupProperty("anno"), "annoReg");
	criteria.setProjection(projectionList);
	criteria.setResultTransformer(Transformers.aliasToBean(MercatiAnnoGiornoDTO.class));
	List<MercatiAnnoGiornoDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<Integer> findAnniDaConsolidare(Integer codiceMercato) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuffer sql = new StringBuffer();
	sql.append("select mpt.anno as anno ");
	sql.append("from " + schema + ".mercatipresenze_t mpt ");
	sql.append("inner join " + schema + ".mercati m ");
	sql.append("on m.idcomune=mpt.idcomune ");
	sql.append("and m.codicemercato=mpt.fkcodicemercato ");
	sql.append("left join " + schema + ".mercati_elabpresenze mel ");
	sql.append("on mel.idcomune = m.idcomune ");
	sql.append("and mel.fk_codicemercato=m.codicemercato and mel.anno=mpt.anno ");
	sql.append("where mpt.idcomune=? ");
	sql.append("and mpt.fkcodicemercato=? ");
	sql.append("and mel.anno is null ");
	sql.append("group by mpt.anno order by mpt.anno");
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceMercato);
	q.addScalar("anno", Hibernate.INTEGER);
	return q.list();
    }
}
