package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

@SuppressWarnings("rawtypes")
@Repository
public class NotificaFirmaDAOImpl extends BaseDAOImpl implements NotificaFirmaDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findFirmeNonNotificate() {

	//non serve verificare anche le istanze perchè il protocollo dell'istanza è anche sul movimento di avvio
	String sql = "select movimenti.fkidprotocollo from movimenti inner join istanze on movimenti.idcomune = istanze.idcomune and movimenti.codiceistanza = istanze.codiceistanza where istanze.idcomune = ? and istanze.software = ? and movimenti.fkidprotocollo is not null and movimenti.numeroprotocollo is null and movimenti.dataprotocollo is null";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Movimenti.class);
	Integer idx = 0;
	q.setParameter(idx, ORMHelper.getIdcomune(), new StringType());
	idx++;
	q.setParameter(idx, ORMHelper.getSoftware(), new StringType());
	q.addScalar("fkidprotocollo", Hibernate.STRING);
	return q.list();
    }
}
