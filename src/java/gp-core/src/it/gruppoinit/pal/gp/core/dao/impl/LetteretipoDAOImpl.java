package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LetteretipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class LetteretipoDAOImpl extends BaseDAOImpl<Letteretipo, PkId> implements LetteretipoDAO {

    @Override
    public Class<Letteretipo> getEntityClass() {

	return Letteretipo.class;
    }

    @Override
    public List<Letteretipo> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Letteretipo> findByDescrizione(String descrizione, boolean includiDisabilitate) {

	String hql = "Select a from Letteretipo a where a.id.idcomune=? and fileId is not null ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and (upper(a.descrizione) like ? or id.codice like ? )";
	    }
	}
	if (includiDisabilitate == false) {
	    hql += " and (a.flagDisabilitato=? or a.flagDisabilitato is null) ";
	}
	hql += " and a.software.codice=? ";
	hql += " order by a.descrizione";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	// q.setFirstResult(firstResult);
	// q.setMaxResults(maxResult);
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		q.setString(paramPos, "%" + descrizione.toUpperCase() + "%");
		paramPos++;
		q.setString(paramPos, descrizione);
		paramPos++;
	    }
	}
	if (includiDisabilitate == false) {
	    q.setBoolean(paramPos, Boolean.FALSE);
	    paramPos++;
	}
	q.setString(paramPos, ORMHelper.getSoftware());
	paramPos++;
	List<Letteretipo> letteretipos = q.list();
	return letteretipos;
    }

    @Override
    public List<Letteretipo> findByDescrizioneAndSoftware(String descrizione, String software, boolean includiDisabilitate) {

	String hql = "Select a from Letteretipo a where a.id.idcomune=? and fileId is not null ";
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		hql += " and (upper(a.descrizione) like ? or id.codice like ? )";
	    }
	}
	if (StringUtils.isNotBlank(software)) {
	    hql += " and a.software.codice=? ";
	} else {
	    hql += " and a.software.codice=? ";
	}
	if (includiDisabilitate == false) {
	    hql += " and (a.flagDisabilitato=? or a.flagDisabilitato is null) ";
	}
	hql += " order by a.descrizione";
	int paramPos = 0;
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	// q.setFirstResult(firstResult);
	// q.setMaxResults(maxResult);
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	if (StringUtils.isNotBlank(descrizione)) {
	    if (StringUtils.isNotBlank(descrizione.replaceAll("%", ""))) {
		q.setString(paramPos, "%" + descrizione.toUpperCase() + "%");
		paramPos++;
		q.setString(paramPos, descrizione);
		paramPos++;
	    }
	}
	if (StringUtils.isNotBlank(software)) {
	    q.setString(paramPos, software);
	    paramPos++;
	} else {
	    q.setString(paramPos, ORMHelper.getSoftware());
	    paramPos++;
	}
	if (includiDisabilitate == false) {
	    q.setBoolean(paramPos, Boolean.FALSE);
	    paramPos++;
	}
	List<Letteretipo> letteretipos = q.list();
	return letteretipos;
    }
}
