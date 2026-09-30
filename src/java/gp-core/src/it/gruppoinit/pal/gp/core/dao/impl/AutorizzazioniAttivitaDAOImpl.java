package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniAttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaDTO;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class AutorizzazioniAttivitaDAOImpl extends BaseDAOImpl<AutorizzazioniAttivita, PkId> implements AutorizzazioniAttivitaDAO {

    @Override
    public Class<AutorizzazioniAttivita> getEntityClass() {

	return AutorizzazioniAttivita.class;
    }

    @Override
    public void deleteByAutorizzazione(Integer codiceAutorizzazione) {

	if (codiceAutorizzazione == null) {
	    return;
	}
	String hql = "delete AutorizzazioniAttivita om where om.id.idcomune=? and om.autorizzazioniId=?";
	getHibernateTemplate().bulkUpdate(hql, new Object[] { ORMHelper.getIdcomune(), codiceAutorizzazione });
    }

    @Override
    public void deleteByAttivita(String codiceAttivita) {

	if (codiceAttivita == null) {
	    return;
	}
	String hql = "delete AutorizzazioniAttivita om where om.id.idcomune=? and om.attivitaId=?";
	getHibernateTemplate().bulkUpdate(hql, new Object[] { ORMHelper.getIdcomune(), codiceAttivita });
    }

    @Override
    public List<AutorizzazioniAttivitaDTO> findByAutorizzazioni(Set<Integer> codiciAutorizzazione) {

	String hqlQuery = "SELECT  aa.autorizzazioniId as idautorizzazione, at.id.codiceistat as codiceattivita, at.istat as descrizioneattivita FROM AutorizzazioniAttivita aa inner join aa.attivita at  WHERE "
		+ " aa.id.idcomune=? AUTORIZZAZIONI_ID_IN_CLAUSE";
	//
	String inClause = "";
	int num = codiciAutorizzazione.size();
	Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
	Double cicli = filter_getListaCodiceAttivita_length / 1000;
	int cicliDaMille = cicli.intValue();
	int resto = num - (cicliDaMille * 1000);
	inClause = " and ( 1=2 ";
	for (int i = 0; i < cicliDaMille; i++) {
	    String qm = StringUtils.repeat("?,", 1000);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause += " or aa.autorizzazioniId in (" + qm + ")";
	}
	if (resto > 0) {
	    String qm = StringUtils.repeat("?,", resto);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause += " or aa.autorizzazioniId in (" + qm + ")";
	}
	inClause += ")";
	//
	hqlQuery = hqlQuery.replace("AUTORIZZAZIONI_ID_IN_CLAUSE", inClause);
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setString(0, ORMHelper.getIdcomune());
	//////////////////////////
	int position = 1;
	for (Integer idAutorizzazione : codiciAutorizzazione) {
	    q.setInteger(position++, idAutorizzazione);
	}
	/////////////////////////
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniAttivitaDTO.class));
	return q.list();
    }
}
