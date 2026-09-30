package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
	    Integer maxResult) {

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
    public List<Anagrafe> findRichiedentiByIstanza(String descrizione, Integer codiceIstanza, String tipoAnagrafe) {

	String hql = "Select a from Anagrafe a left join a.istanze ir left join a.tecniciIstanzes ti left join a.aziendaIstanzes ai "
		+ "left join a.istanzerichiedentisForRichiedente irs left join a.istanzerichiedentisForAnagrafeCollegata irac "
		+ "left join a.istanzerichiedentisForProcuratore irp where a.id.idcomune=? ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and (upper(concat(concat(a.nominativo,' '),(case when  a.nome is null then '' else a.nome end))) like ? or a.id.codice like ? or (upper(a.codicefiscale) like ? or upper(a.partitaiva) like ?))";
	    }
	}
	if (StringUtils.isNotBlank(tipoAnagrafe)) {
	    if (tipoAnagrafe.equalsIgnoreCase("T")) {
		hql += " and a.tipologia in (?,?)";
	    } else if (tipoAnagrafe.equalsIgnoreCase("F") || tipoAnagrafe.equalsIgnoreCase("G")) {
		hql += " and tipoanagrafe = ?";
	    }
	}
	hql += "and (ir.id.codice=? or ti.id.codice=? or ai.id.codice=? or (irs.istanza.id.codice=? or irac.istanza.id.codice=? or irp.istanza.id.codice=?))";
	hql += " order by a.nominativo,a.nome";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
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
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setInteger(paramPos, codiceIstanza);
	paramPos++;
	q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Anagrafe> anagrafeList = q.list();
	return anagrafeList;
    }

    @Override
    public List<Integer> findCodiciAnagrafe() {

	String hql = "Select i.id.codice from Anagrafe i where i.id.idcomune=? ";
	Object[] values = null;
	values = new Object[] { ORMHelper.getIdcomune() };
	return getHibernateTemplate().find(hql, values);
    }
}
