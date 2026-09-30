/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * 
 */
@Repository
public class MercatiDAOImpl extends BaseDAOImpl<Mercati, PkId> implements MercatiDAO {

    @Override
    public Class<Mercati> getEntityClass() {

	return Mercati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByDescrizione(String descrizione, MercatiEnum mercatiEnum) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	switch (mercatiEnum) {
	    case ACTIVE:
		det.add(Restrictions.eq("attivo", true));
		break;
	    case DISABLED:
		det.add(Restrictions.eq("attivo", false));
		break;
	    default:
		break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByFlagContabilita(String descrizione, boolean isFlagContabilita, MercatiEnum mercatiEnum) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	switch (mercatiEnum) {
	    case ACTIVE:
		det.add(Restrictions.eq("attivo", true));
		break;
	    case DISABLED:
		det.add(Restrictions.eq("attivo", false));
		break;
	    default:
		break;
	}
	det.add(Restrictions.eq("flagContabilita", isFlagContabilita));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Mercati> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findAllMercatiAttivi(Integer firstResult, Integer maxResult) {

	DetachedCriteria detachedCriteria;
	detachedCriteria = getIdcomuneAndSoftwareCriteria();
	detachedCriteria.addOrder(Order.asc("descrizione"));
	detachedCriteria.add(Restrictions.eq("attivo", true));
	if (null != firstResult && null != maxResult) {
	    return (List<Mercati>) getHibernateTemplate().findByCriteria(detachedCriteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Mercati>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByDescrizioneAndResponsabile(String descrizione, Integer codiceResponsabile, MercatiEnum mercatiEnum,
	    Integer firstResult, Integer maxResults) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.createAlias("mercatiResponsabilis", "_mercatiResponsabili");
	det.createAlias("mercatiResponsabilis.responsabili", "_responsabile");
	det.add(Restrictions.eq("_responsabile.id.codice", codiceResponsabile));
	switch (mercatiEnum) {
	    case ACTIVE:
		det.add(Restrictions.eq("attivo", true));
		break;
	    case DISABLED:
		det.add(Restrictions.eq("attivo", false));
		break;
	    default:
		break;
	}
	if (null != firstResult && null != maxResults) {
	    return getHibernateTemplate().findByCriteria(det, firstResult, maxResults);
	} else {
	    return getHibernateTemplate().findByCriteria(det);
	}
    }
    //   

    @SuppressWarnings("unchecked")
    @Override
    public List<PosteggiConcessioniHelper> findPosteggiMercatoAllaData(Integer codiceMercato, Integer codiceUso, Date dataGiornataMercato) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String sql = getSqlConcessioniByMercato(DialettoEnum.fromHibernateDialect(sfi.getDialect().toString()));
	//
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idautsub", Hibernate.INTEGER);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codicemercato", Hibernate.INTEGER);
	q.addScalar("iduso", Hibernate.INTEGER);
	q.addScalar("idposteggio", Hibernate.INTEGER);
	q.addScalar("codiceposteggio", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceconcessione", Hibernate.INTEGER);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("codicetitolare", Hibernate.INTEGER);
	q.addScalar("codiceoccupante", Hibernate.INTEGER);
	q.addScalar("datafineaffitto", Hibernate.DATE);
	q.addScalar("datacessazione", Hibernate.DATE);
	q.addScalar("flagcausaliaffitto", Hibernate.BOOLEAN);
	q.addScalar("posteggiodisabilitato", Hibernate.BOOLEAN);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codicemercato", codiceMercato);
	q.setInteger("codiceuso", codiceUso);
	q.setInteger("flagaffitto", 1);
	q.setDate("datariferimento", Utilities.dateWithoutTime(dataGiornataMercato));
	q.setResultTransformer(Transformers.aliasToBean(PosteggiConcessioniHelper.class));
	//
	return q.list();
    }

    private String getSqlConcessioniByMercato(DialettoEnum dialetto) {

	StringBuilder sql = new StringBuilder();
	sql.append("select stato_concessioni.id_autsub as idautsub, "); //
	sql.append("  mercati.idcomune as idcomune,  "); //
	sql.append("  mercati.codicemercato as codicemercato,  "); //
	sql.append("  stato_concessioni.fk_idmercatiuso as iduso,  "); //
	sql.append("  mercati_d.idposteggio as idposteggio,  "); //
	sql.append("  mercati_d.codiceposteggio as codiceposteggio, "); //
	sql.append("  mercati.software as software,  "); //
	sql.append("  stato_concessioni.idriferimento as codiceconcessione, "); //
	sql.append("  stato_concessioni.codiceistanza as codiceistanza,  "); //
	sql.append("  stato_concessioni.codicetitolare as codicetitolare,  "); //
	sql.append("  stato_concessioni.codiceoccupante as codiceoccupante,  "); //
	sql.append("  stato_concessioni.datacessazione as datacessazione, "); //
	sql.append("  stato_concessioni.data_fine_affitto as datafineaffitto,  "); //
	sql.append("  coalesce(stato_concessioni.flag_causali_affitto, 0) as flagcausaliaffitto, "); //
	sql.append("  coalesce(mercati_d.disabilitato, 0) as posteggiodisabilitato  "); //
	sql.append("  from  "); //
	sql.append("  mercati  "); //
	sql.append("  inner join mercati_d on mercati.idcomune = mercati_d.idcomune  "); //
	sql.append("  and mercati.codicemercato = mercati_d.fkcodicemercato  "); //
	sql.append("  left join (  "); //
	sql.append("  select  "); //
	sql.append("  autorizzazioni.id + 99000000 as id_autsub "); //
	sql.append("  ,autorizzazioni.data_cessazione "); //
	sql.append("  ,autorizzazioni.autorizdata as datavalidita "); //
	sql.append("  ,autorizzazioni_concessioni.idcomune "); //
	sql.append("  ,autorizzazioni_concessioni.fk_idmercatiuso "); //
	sql.append("  ,istanze.codiceistanza "); //
	sql.append("  ,0 as subentro "); //
	sql.append("  ,autorizzazioni_concessioni.fk_idposteggio as idposteggio "); //
	sql.append("  ,autorizzazioni.id as idriferimento "); //
	sql.append("  ,autorizzazioni.fk_codiceanagrafe as codicetitolare "); //
	sql.append("  ,autorizzazioni.codiceoccupante as codiceoccupante "); //
	sql.append("  ,autorizzazioni_concessioni.id as idautorizzazioneconcessione "); //
	sql.append("  ,concessionicausali.flag_causali_affitto "); //
	sql.append("  ,autorizzazioni.data_fine_affitto "); //
	switch (dialetto) {
	    case MYSQL:
		sql.append("  ,coalesce(autorizzazioni.data_cessazione, date_add(sysdate(), interval 100 year)) as datacessazione "); //
		break;
	    case ORACLE:
		sql.append("  ,coalesce(autorizzazioni.data_cessazione, ( SYSDATE + 36500)) as datacessazione "); //
		break;
	    default:
		throw new NotImplementedException("Dialetto " + dialetto + " non implementato per la funzione getSqlConcessioniByMercato()");
	}
	sql.append("  ,autorizzazioni.datascadenza as datascadenza "); //
	sql.append("  from  "); //
	sql.append("  autorizzazioni_concessioni  "); //
	sql.append("  inner join autorizzazioni on autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id  "); //
	sql.append("  inner join istanze on istanze.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and istanze.codiceistanza = autorizzazioni.fkidistanza  "); //
	sql.append("  left join concessionicausali on concessionicausali.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and concessionicausali.codicecausale = autorizzazioni.fk_causale_acquisizione  "); //
	sql.append("  and concessionicausali.flag_causali_affitto = :flagaffitto "); //
	sql.append("  where  "); //
	sql.append("  autorizzazioni_concessioni.idcomune = :idcomune "); //
	sql.append("  and autorizzazioni_concessioni.fk_codicemercato = :codicemercato  "); //
	sql.append("  and autorizzazioni_concessioni.fk_idmercatiuso = :codiceuso  "); //
	sql.append(" and (autorizzazioni.data_cessazione is null or autorizzazioni.data_cessazione >  :datariferimento) "); //
	sql.append(" and autorizzazioni.autorizdata<=:datariferimento "); //
	sql.append("  union  "); //
	sql.append("  select  "); //
	sql.append("  autorizzazioni.id as id_autsub "); //
	sql.append("  ,autorizzazioni.data_cessazione "); //
	sql.append("  ,autorizzazioni.autorizdata as datavalidita  "); //
	sql.append("  ,autorizzazioni_concessioni.idcomune "); //
	sql.append("  ,autorizzazioni_concessioni.fk_idmercatiuso "); //
	sql.append("  ,istanze.codiceistanza "); //
	sql.append("  ,1 as subentro "); //
	sql.append("  ,autorizzazioni_concessioni.fk_idposteggio as idposteggio "); //
	sql.append("  ,autorizzazioni.fk_idaut_attuale as idriferimento "); //
	sql.append("  ,autorizzazioni.fk_codiceanagrafe as codicetitolare "); //
	sql.append("  ,autorizzazioni.codiceoccupante as codiceoccupante "); //
	sql.append("  ,autorizzazioni_concessioni.id as idautorizzazioneconcessione "); //
	sql.append("  ,concessionicausali.flag_causali_affitto "); //
	sql.append("  ,autorizzazioni.data_fine_affitto "); //
	sql.append("  ,autorizzazioni.data_cessazione as datacessazione "); //
	sql.append("  ,autorizzazioni.datascadenza as datascadenza "); //
	sql.append("  from  "); //
	sql.append("  autorizzazioni_subentri_conc autorizzazioni_concessioni  "); //
	sql.append("  inner join autorizzazioni_subentri autorizzazioni on autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and autorizzazioni_concessioni.fk_autsub_id = autorizzazioni.id  "); //
	sql.append("  inner join istanze on istanze.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and istanze.codiceistanza = autorizzazioni.fkidistanza  "); //
	sql.append("  left join concessionicausali on concessionicausali.idcomune = autorizzazioni.idcomune  "); //
	sql.append("  and concessionicausali.codicecausale = autorizzazioni.fk_causale_acquisizione  "); //
	sql.append("  and concessionicausali.flag_causali_affitto = :flagaffitto "); //
	sql.append("  where  "); //
	sql.append("  autorizzazioni_concessioni.idcomune = :idcomune "); //
	sql.append("  and autorizzazioni_concessioni.fk_codicemercato = :codicemercato  "); //
	sql.append("  and autorizzazioni_concessioni.fk_idmercatiuso = :codiceuso "); //
	sql.append(" and autorizzazioni.data_cessazione > :datariferimento "); //
	sql.append("  and autorizzazioni.autorizdata <= :datariferimento "); //
	sql.append("  ) stato_concessioni on stato_concessioni.idcomune = mercati_d.idcomune  "); //
	sql.append("  and stato_concessioni.idposteggio = mercati_d.idposteggio  "); //
	sql.append("  left join anagrafe on stato_concessioni.idcomune = anagrafe.idcomune  "); //
	sql.append("  and stato_concessioni.codicetitolare = anagrafe.codiceanagrafe  "); //
	sql.append("  left join anagrafe occupante on stato_concessioni.idcomune = occupante.idcomune  "); //
	sql.append("  and stato_concessioni.codiceoccupante = occupante.codiceanagrafe  "); //
	sql.append("  where  "); //
	sql.append("  mercati.idcomune = :idcomune "); //
	sql.append("  and mercati.codicemercato = :codicemercato "); //
	sql.append("  order by  "); //
	switch (dialetto) {
	    case MYSQL:
		sql.append("  COALESCE(stato_concessioni.data_cessazione, DATE_ADD(SYSDATE(), INTERVAL 100 YEAR)) ");
		break;
	    case ORACLE:
		sql.append("  COALESCE(stato_concessioni.data_cessazione, ( SYSDATE + 36500) ) ");
		break;
	    default:
		throw new NotImplementedException("Dialetto " + dialetto + " non implementato per la funzione getSqlConcessioniByMercato()");
	}
	sql.append(" , id_autsub ");
	return sql.toString();
    }
}
