package it.gruppoinit.pal.gp.core.features.rabbitmq;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;

@SuppressWarnings("rawtypes")
@Repository
public class RabbitMQDAOImpl extends BaseDAOImpl implements RabbitMQDAO {

    private static final String IDCOMUNE = "idcomune";

    @SuppressWarnings("unchecked")
    @Override
    public Integer recuperaTestoTipodaMovimentoETopic(String tipoMovimento, String topic) {

	String sql = "Select fk_mailtipo as mailtipo from tipimovimento_rabbit where idcomune=:idcomune and fk_tipimovimento=:tipomovimento and topic=:topic";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(IDCOMUNE, ORMHelper.getIdcomune());
	q.setString("tipomovimento", tipoMovimento);
	q.setString("topic", topic);
	q.addScalar("mailtipo", Hibernate.INTEGER);
	List<Integer> result = (List<Integer>) q.list();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return null;
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
