package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AssegnazioneGruppiTestataDAOImpl extends BaseDAOImpl<AssegnazioneGruppiTestata, PkId> implements IAssegnazioneGruppiTestataDAO {

    @Override
    public Class<AssegnazioneGruppiTestata> getEntityClass() {

	return AssegnazioneGruppiTestata.class;
    }

    @Override
    public Integer verificaSeAperta(Integer fkGruppoIstrutori, Integer codiceIstanza) {

	String sql = this.query(codiceIstanza);
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setInteger(0, fkGruppoIstrutori);
	query.setInteger(1, codiceIstanza);
	query.setString(2, ORMHelper.getIdcomune());
	query.addScalar("id", Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public Integer trovaTestataAperta(Integer fkGruppoIstrutori) {

	String sql = query(null);
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setInteger(0, fkGruppoIstrutori);
	query.setString(1, ORMHelper.getIdcomune());
	query.addScalar("id", Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    private String query(Integer codiceIstanza) {

	String sql = " select " + // 
		     " ag.ID as id " + // 
		     " from " + // 
		     " assegnazione_gruppi_testata ag " + // 
		     " where " + // 
		     " ag.GRUPPO_ISTRUTTORI = ? ";
	if (codiceIstanza != null) {
	    sql += " and agd.codiceIstanza = ? ";
	}
	sql += " and ag.idcomune = ? " + // 
	       " and ag.DATA_CHIUSURA is null";
	return sql;
    }
}
