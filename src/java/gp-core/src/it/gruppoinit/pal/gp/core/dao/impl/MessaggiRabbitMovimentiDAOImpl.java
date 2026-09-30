package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MessaggiRabbitMovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitMovimenti;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitMovimentiId;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

@Repository
public class MessaggiRabbitMovimentiDAOImpl extends BaseDAOImpl<MessaggiRabbitMovimenti, MessaggiRabbitMovimentiId>
	implements MessaggiRabbitMovimentiDAO {

    @Override
    public Class<MessaggiRabbitMovimenti> getEntityClass() {

	return MessaggiRabbitMovimenti.class;
    }

    @Override
    public List<MessaggiRabbitMovimenti> findByCodiceMovimento(Integer codiceMovimento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codicemovimento", codiceMovimento));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public void deleteByUuIdMovimento(String uuIdMovimento) {

	SQLQuery q = getSession().createSQLQuery("delete from MESSAGGI_RABBIT_MOVIMENTI where idcomune=:idcomune and UUID_MOVIMENTO=:uuidmovimento");
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("uuidmovimento", uuIdMovimento);
	q.addSynchronizedEntityClass(MessaggiRabbitMovimenti.class).addSynchronizedEntityClass(Movimenti.class).executeUpdate();
	flush();
    }
}
