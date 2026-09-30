package it.gruppoinit.pal.gp.core.features.istanze.temp;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.TmpIstanze;
import it.gruppoinit.pal.gp.core.domain.TmpIstanzeId;

@SuppressWarnings("rawtypes")
@Repository
public class TmpIstanzeDAOImpl extends BaseDAOImpl implements TmpIstanzeDAO {

    @Override
    public Class getEntityClass() {

	return TmpIstanze.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void insert(String sessionId, List<String> uuidIstanze) {

	//1. Cancello eventuali record presenti
	this.delete(sessionId);
	//2. Inserisco i nuovi record
	for (String uuid : uuidIstanze) {
	    TmpIstanzeId tmpId = new TmpIstanzeId();
	    tmpId.setIdcomune(ORMHelper.getIdcomune());
	    tmpId.setSessionid(sessionId);
	    tmpId.setUuid(uuid);
	    this.insert(new TmpIstanze(tmpId));
	}
	//3. Flush commit
	this.commitFlush();
    }

    private void delete(String sessionId) {

	String sql = "delete from tmp_istanze where idcomune = ? and sessionid = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(TmpIstanze.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, sessionId, new StringType());
	q.executeUpdate();
    }
}
