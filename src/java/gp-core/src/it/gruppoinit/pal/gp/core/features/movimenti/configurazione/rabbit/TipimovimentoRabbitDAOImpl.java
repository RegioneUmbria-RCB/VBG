package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit;

import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class TipimovimentoRabbitDAOImpl extends BaseDAOImpl<TipimovimentoRabbit, TipimovimentoRabbitId> implements ITipimovimentoRabbitDAO {

    @Override
    public Class<TipimovimentoRabbit> getEntityClass() {

	return TipimovimentoRabbit.class;
    }

    @Override
    public List<TipimovimentoRabbit> findTipimovRabbitByTipomov(String tipomovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkTipimovimento", tipomovimento, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	ft.addRestriction(fr);
	List<TipimovimentoRabbit> result = findByFilterTable(ft);
	if (!result.isEmpty()) {
	    return result;
	}
	return Collections.emptyList();
    }

    @Override
    public boolean checkTipimovRabbitByTipomovAndTopic(String tipomovimento, RabbitTopicEnum topic) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkTipimovimento", tipomovimento, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.topic", topic.getValue(), String.class));
	ft.addRestriction(fr);
	return existsRecords(ft);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer recuperaTestoTipodaMovimentoETopic(String tipoMovimento, String topic) {

	String sql = "Select fk_mailtipo as mailtipo from tipimovimento_rabbit where idcomune=:idcomune and fk_tipimovimento=:tipomovimento and topic=:topic";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("tipomovimento", tipoMovimento);
	q.setString("topic", topic);
	q.addScalar("mailtipo", Hibernate.INTEGER);
	List<Integer> result = (List<Integer>) q.list();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return null;
    }
}
