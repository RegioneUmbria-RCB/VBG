package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayPosDebMassiveDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.service.helper.CaricamentoMassivoStatiEnum;

@Repository
public class PayPosDebMassiveDAOImpl extends BaseDAOImpl<PayPosDebMassive, PkId> implements PayPosDebMassiveDAO {

    @Override
    public Class<PayPosDebMassive> getEntityClass() {

	return PayPosDebMassive.class;
    }

    @Override
    public Map<String, List<Integer>> findPosizioniDaElaborare() {

	Map<String, List<Integer>> ret = new HashMap<>();
	String hqlQuery = "select i FROM PayPosDebMassive i  WHERE  i.id.idcomune = :idcomune AND  i.flagProcessata = :flagprocessata order by i.idOperazione";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("flagprocessata", CaricamentoMassivoStatiEnum.CARICATO.getValore());
	List<PayPosDebMassive> list = q.list();
	for (PayPosDebMassive payPosDebMassive : list) {
	    List<Integer> posDebs = ret.get(payPosDebMassive.getIdOperazione());
	    if (posDebs == null) {
		posDebs = new ArrayList<>();
	    }
	    posDebs.add(payPosDebMassive.getPosizioneDebitoria().getId().getCodice());
	    ret.put(payPosDebMassive.getIdOperazione(), posDebs);
	}
	return ret;
    }
}
