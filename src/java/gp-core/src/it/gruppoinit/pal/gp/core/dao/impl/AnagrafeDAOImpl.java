package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AnagrafeDAOImpl extends BaseDAOImpl<Anagrafe, PkId> implements AnagrafeDAO {

    @Override
    public Class<Anagrafe> getEntityClass() {

	return Anagrafe.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Anagrafe> findByNominativo(String nominativo, AnagrafeEnum anagrafeEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(nominativo) && !nominativo.equals("%")) {
	    Criterion nominativoCrit = getCriterionForSplittableString(nominativo, "nominativo", "nome");
	    if (nominativoCrit != null) {
		det.add(nominativoCrit);
	    }
	}
	switch (anagrafeEnum) {
	case ACTIVE:
	    det.add(Restrictions.eq("flagDisabilitato", 0));
	    break;
	case DISABLED:
	    det.add(Restrictions.eq("flagDisabilitato", 1));
	    break;
	case SUSPENDED:
	    det.add(Restrictions.eq("flagDisabilitato", 2));
	    break;
	}
	det.addOrder(Order.asc("nominativo"));
	det.addOrder(Order.asc("nome"));
	List<Anagrafe> anagrafeList = getHibernateTemplate().findByCriteria(det);
	return anagrafeList;
    }

    @Override
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "nominativo", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult, boolean soloSeAutorizzazioniPresenti) {

	String hql = "Select a from Anagrafe a where a.id.idcomune=? ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and (upper(concat(concat(a.nominativo,' '),(case when  a.nome is null then '' else a.nome end))) like ? or id.codice like ? or (upper(a.codicefiscale) like ? or upper(a.partitaiva) like ?))";
	    }
	}
	if (statoAnagrafe != null) {
	    switch (statoAnagrafe) {
	    case ACTIVE:
		hql += " and a.flagDisabilitato=?";
		break;
	    case DISABLED:
		hql += " and a.flagDisabilitato=?";
		break;
	    case SUSPENDED:
		hql += " and a.flagDisabilitato=?";
		break;
	    case ALL:
		break;
	    }
	}
	if (StringUtils.isNotBlank(tipoAnagrafe)) {
	    if (tipoAnagrafe.equalsIgnoreCase("T")) {
		hql += " and a.tipologia in (?,?)";
	    } else if (tipoAnagrafe.equalsIgnoreCase("F") || tipoAnagrafe.equalsIgnoreCase("G")) {
		hql += " and tipoanagrafe = ?";
	    }
	}
	if (soloSeAutorizzazioniPresenti) {
	    hql += " and ( a.autorizzazionis is not empty)";
	}
	hql += " order by a.nominativo,a.nome";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		q.setString(paramPos, "%" + descrizione.toUpperCase() + "%");
		paramPos++;
		q.setString(paramPos, descrizione);
		paramPos++;
		q.setString(paramPos, descrizione.toUpperCase() + "%");
		paramPos++;
		q.setString(paramPos, descrizione.toUpperCase() + "%");
		paramPos++;
	    }
	}
	if (statoAnagrafe != null) {
	    switch (statoAnagrafe) {
	    case ACTIVE:
		q.setInteger(paramPos, 0);
		paramPos++;
		break;
	    case DISABLED:
		q.setInteger(paramPos, 1);
		paramPos++;
		break;
	    case SUSPENDED:
		q.setInteger(paramPos, 2);
		paramPos++;
		break;
	    }
	}
	if (StringUtils.isNotBlank(tipoAnagrafe)) {
	    if (tipoAnagrafe.equalsIgnoreCase("T")) {
		q.setInteger(paramPos, -1);
		paramPos++;
		q.setInteger(paramPos, 1);
		paramPos++;
	    } else if (tipoAnagrafe.equalsIgnoreCase("F") || tipoAnagrafe.equalsIgnoreCase("G")) {
		q.setString(paramPos, tipoAnagrafe);
		paramPos++;
	    }
	}
	List<Anagrafe> anagrafeList = q.list();
	return anagrafeList;
    }

    @Override
    public List<AnagrafeRicercaBean> findRichiedentiByIstanza(String descrizione, Integer codiceIstanza, String tipoAnagrafe) {

	StringBuilder sql = new StringBuilder(
		"SELECT t1.codiceanagrafe as codiceanagrafe,t1.tipoanagrafe as tipoanagrafe,t1.tipologia as tipologia,t1.nominativo as nominativo,t1.nome as nome,t1.codicefiscale as codicefiscale,t1.partitaiva as partitaiva ");
	sql.append(" FROM ( ");
	sql.append(" SELECT ");
	sql.append(
		" anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanzerichiedenti ");
	sql.append(" INNER JOIN istanze ON istanzerichiedenti.IDCOMUNE = istanze.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICEISTANZA = istanze.CODICEISTANZA ");
	sql.append(" INNER JOIN anagrafe ON istanzerichiedenti.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICERICHIEDENTE = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" UNION ");
	sql.append(
		" SELECT anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanzerichiedenti ");
	sql.append(" INNER JOIN istanze ON istanzerichiedenti.IDCOMUNE = istanze.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICEISTANZA = istanze.CODICEISTANZA ");
	sql.append(" INNER JOIN anagrafe ON istanzerichiedenti.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICEANAGRAFECOLL = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" UNION ");
	sql.append(
		" SELECT anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanzerichiedenti ");
	sql.append(" INNER JOIN istanze ON istanzerichiedenti.IDCOMUNE = istanze.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICEISTANZA = istanze.CODICEISTANZA ");
	sql.append(" INNER JOIN anagrafe ON istanzerichiedenti.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanzerichiedenti.CODICEPROCURATORE = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" UNION ");
	sql.append(
		" SELECT anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanze ");
	sql.append(" INNER JOIN anagrafe ON istanze.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanze.CODICERICHIEDENTE = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" UNION ");
	sql.append(
		" SELECT anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanze ");
	sql.append(" INNER JOIN anagrafe ON istanze.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanze.codicetitolarelegale = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" UNION ");
	sql.append(
		" SELECT anagrafe.codiceanagrafe,anagrafe.tipoanagrafe,anagrafe.tipologia,anagrafe.nome,anagrafe.nominativo,anagrafe.codicefiscale,anagrafe.partitaiva ");
	sql.append(" FROM istanze ");
	sql.append(" INNER JOIN anagrafe ON istanze.IDCOMUNE = anagrafe.IDCOMUNE ");
	sql.append(" AND istanze.codiceprofessionista = anagrafe.codiceanagrafe ");
	sql.append(" WHERE istanze.idcomune = :idcomune ");
	sql.append(" AND istanze.codiceistanza = :codiceistanza ");
	sql.append(" ) t1 ");
	sql.append(" WHERE 1=1 ");
	sql.append(
		" AND (UPPER(CONCAT(CONCAT(t1.nominativo,' '),(CASE WHEN t1.nome IS NULL THEN '' ELSE t1.nome END))) LIKE :testoricerca OR t1.codiceanagrafe LIKE :testoricerca ");
	sql.append("  OR (UPPER(t1.codicefiscale) LIKE :testoricerca OR UPPER(t1.partitaiva) LIKE :testoricerca)) ");
	if (StringUtils.isNotBlank(tipoAnagrafe)) {
	    if (tipoAnagrafe.equalsIgnoreCase("T")) {
		sql.append(" and NOT coalesce(t1.tipologia, 0) = :tipologia ");
	    } else if (tipoAnagrafe.equalsIgnoreCase("F") || tipoAnagrafe.equalsIgnoreCase("G")) {
		sql.append(" and  t1.tipoanagrafe = :tipoanagrafe ");
	    }
	}
	sql.append(" GROUP BY ");
	sql.append("  t1.codiceanagrafe,t1.tipoanagrafe,t1.tipologia,t1.nominativo,t1.nome,t1.codicefiscale,t1.partitaiva ");
	sql.append(" ORDER BY t1.nominativo,t1.nome");
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("tipoanagrafe", Hibernate.STRING);
	q.addScalar("tipologia", Hibernate.INTEGER);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	// 
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceistanza", codiceIstanza);
	q.setString("testoricerca", "%" + StringUtils.defaultString(descrizione).toUpperCase() + "%");
	if (StringUtils.isNotBlank(tipoAnagrafe)) {
	    if (tipoAnagrafe.equalsIgnoreCase("T")) {
		q.setInteger("tipologia", 0);
	    } else if (tipoAnagrafe.equalsIgnoreCase("F") || tipoAnagrafe.equalsIgnoreCase("G")) {
		sql.append(" and  t1.tipoanagrafe = :tipoanagrafe ");
		q.setString("tipoanagrafe", tipoAnagrafe);
	    }
	}
	q.setResultTransformer(Transformers.aliasToBean(AnagrafeRicercaBean.class));
	return q.list();
    }

    @Override
    public List<Integer> findCodiciAnagrafe() {

	String hql = "Select i.id.codice from Anagrafe i where i.id.idcomune=? ";
	Object[] values = null;
	values = new Object[] { ORMHelper.getIdcomune() };
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public void updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean stato) {

	stato = BooleanUtils.negate(stato);
	if (codiceanagrafe == null) {
	    throw new RuntimeException("updateAbiltaOrDisabilita: il parametro codice anagrafe  passato è nullo");
	}
	Integer _stato = BooleanUtils.toInteger(stato);
	String hql = "update Anagrafe set flagDisabilitato = ? , dataDisabilitato=null where id.idcomune = ? and id.codice=?";
	int i;
	if (_stato.equals(1)) {
	    hql = "update Anagrafe set flagDisabilitato = ?  , dataDisabilitato=? where id.idcomune = ? and id.codice=?";
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { _stato, new Date(), ORMHelper.getIdcomune(), codiceanagrafe });
	} else {
	    i = getHibernateTemplate().bulkUpdate(hql, new Object[] { _stato, ORMHelper.getIdcomune(), codiceanagrafe });
	}
	if (i != 1) {
	    throw new RuntimeException(
		    "La query di aggiornamento del flag disabilitato dell'anagrafica :[" + codiceanagrafe + "] ha modificato " + i + " record");
	}
    }

    @Override
    public Set<String> findCfPivaAnagraficheCollegateDelleIstanze(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum filtroAutorizzazioni) {

	if (filtroAutorizzazioni == null) {
	    filtroAutorizzazioni = RicercaAnagraficeCollegateEnum.TUTTE;
	}
	flush(); // PRIMA DI ESEGUIRE LA QUERY MANDO UN FLUSH PER MANDARE AL DB TUTTE LE MODIFICHE DELLO STACK
	Set<String> ret = new HashSet<String>();
	StringBuilder query = new StringBuilder();
	query.append("select tipo,codicefiscale,partitaiva, data from (select  "); //
	query.append(" 'richiedente' as tipo, "); //
	query.append(" coalesce(richiedente.codicefiscale,'') as codicefiscale, "); //
	query.append(" coalesce(richiedente.partitaiva,'') as partitaiva,istanze.data "); //
	query.append("  from istanze  "); //
	query.append(" inner join anagrafe richiedente on  "); //
	query.append(" richiedente.idcomune=istanze.idcomune and "); //
	query.append(" richiedente.codiceanagrafe=istanze.codicerichiedente "); //
	if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI)) {
	    query.append(" inner join autorizzazioni on  "); //
	    query.append(" autorizzazioni.idcomune=istanze.idcomune and "); //
	    query.append(" autorizzazioni.fkidistanza=istanze.codiceistanza "); //
	} else if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" inner join autorizzazioni on  "); //
	    query.append(" autorizzazioni.idcomune=istanze.idcomune and "); //
	    query.append(" autorizzazioni.fkidistanza=istanze.codiceistanza "); //
	    query.append(" inner join tipologiaregistri on  "); //
	    query.append(" tipologiaregistri.idcomune=autorizzazioni.idcomune and "); //
	    query.append(" tipologiaregistri.tr_id=autorizzazioni.fkidregistro "); //
	}
	query.append(" where  "); //
	query.append(" istanze.idcomune=:idcomune "); //
	if (!filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" and istanze.software=:software "); //
	}
	query.append(" and istanze.codicetitolarelegale=:codiceanagrafe "); //
	if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" and tipologiaregistri.flag_manifestazioni=:flag_manifestazioni "); //
	}
	query.append(" group by  "); //
	query.append(" richiedente.codicefiscale, "); //
	query.append(" richiedente.partitaiva,istanze.data "); //
	query.append(" union "); //
	query.append(" select  "); //
	query.append(" 'azienda' as tipo, "); //
	query.append(" coalesce(azienda.codicefiscale,'') as codicefiscale, "); //
	query.append(" coalesce(azienda.partitaiva,'') as partitaiva,istanze.data "); //
	query.append("  from istanze  "); //
	query.append(" inner join anagrafe azienda on  "); //
	query.append(" azienda.idcomune=istanze.idcomune and "); //
	query.append(" azienda.codiceanagrafe=istanze.codicetitolarelegale "); //
	if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI)) {
	    query.append(" inner join autorizzazioni on  "); //
	    query.append(" autorizzazioni.idcomune=istanze.idcomune and "); //
	    query.append(" autorizzazioni.fkidistanza=istanze.codiceistanza "); //
	} else if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" inner join autorizzazioni on  "); //
	    query.append(" autorizzazioni.idcomune=istanze.idcomune and "); //
	    query.append(" autorizzazioni.fkidistanza=istanze.codiceistanza "); //
	    query.append(" inner join tipologiaregistri on  "); //
	    query.append(" tipologiaregistri.idcomune=autorizzazioni.idcomune and "); //
	    query.append(" tipologiaregistri.tr_id=autorizzazioni.fkidregistro "); //
	}
	query.append(" where istanze.idcomune=:idcomune "); //
	if (!filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" and istanze.software=:software "); // TUTTE LE AUTORIZZAZIONI CON REGISTRO IMPOSTATO SU FLAG_MERCATI
	}
	query.append(" and istanze.codicerichiedente=:codiceanagrafe "); //
	if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    query.append(" and tipologiaregistri.flag_manifestazioni=:flag_manifestazioni "); //
	}
	query.append(" group by  "); //
	query.append(" azienda.codicefiscale, "); //
	query.append(" azienda.partitaiva,istanze.data) t1 order by t1.data desc");
	SQLQuery q = getSession().createSQLQuery(query.toString());
	q.addScalar("tipo", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("data", Hibernate.DATE);
	// 
	q.setString("idcomune", ORMHelper.getIdcomune());
	if (!filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    q.setString("software", ORMHelper.getSoftware());  // TUTTE LE AUTORIZZAZIONI CON REGISTRO IMPOSTATO SU FLAG_MERCATI
	}
	q.setInteger("codiceanagrafe", codiceAnagrafe);
	if (filtroAutorizzazioni.equals(RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI)) {
	    q.setInteger("flag_manifestazioni", 1);
	}
	List<Object[]> list = q.list();
	for (Object[] object : list) {
	    if (StringUtils.isNotBlank((String) object[1])) {
		ret.add((String) object[1]);
	    }
	    if (StringUtils.isNotBlank((String) object[2])) {
		ret.add((String) object[2]);
	    }
	}
	return ret;
    }
}
