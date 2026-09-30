package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAree;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.StatoElaborazioneEnum;

@Repository
public class RicalcoloAreeDAOImpl extends BaseDAOImpl<RicalcoloAree, RicalcoloAreeId> implements RicalcoloAreeDAO {

    @Override
    public Class<RicalcoloAree> getEntityClass() {

	return RicalcoloAree.class;
    }

    @Override
    public List<RicalcoloAree> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RicalcoloAree> getMonitorRicalcolaAree() {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	det.addOrder(Order.desc("datafine"));
	return (List<RicalcoloAree>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public String creaRicalcoloAreeIstanza() {

	String id = UUID.randomUUID().toString();
	RicalcoloAree ricalcoloAree = new RicalcoloAree();
	ricalcoloAree.setPk(new RicalcoloAreeId(id));
	ricalcoloAree.setStato(StatoElaborazioneEnum.DA_ESEGUIRE.value());
	ricalcoloAree.setDatafine(new Date());
	ricalcoloAree.setDafare(1);
	ricalcoloAree.setFatti(0);
	ricalcoloAree.setTotali(1);
	this.insert(ricalcoloAree);
	return id;
    }
}
