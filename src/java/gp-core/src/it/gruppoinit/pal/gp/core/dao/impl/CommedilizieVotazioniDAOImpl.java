package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
@Repository
public class CommedilizieVotazioniDAOImpl extends BaseDAOImpl<CommedilizieVotazioni, PkId> implements CommedilizieVotazioniDAO {

    @Override
    public Class<CommedilizieVotazioni> getEntityClass() {

	return CommedilizieVotazioni.class;
    }

    @Override
    public List<CommedilizieVotazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @Override
    public void delete(CommedilizieVotazioni entity) {

	Session session = getSession();
	String sql = "delete from commedilizie_votazioni where idcomune=? and id=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieVotazioni.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, entity.getId().getCodice(), new IntegerType());
	q.executeUpdate();
    }
}
