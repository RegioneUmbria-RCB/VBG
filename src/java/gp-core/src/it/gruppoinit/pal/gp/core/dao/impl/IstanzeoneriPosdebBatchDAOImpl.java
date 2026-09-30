package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriPosdebBatchDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class IstanzeoneriPosdebBatchDAOImpl extends BaseDAOImpl<IstanzeoneriPosdebBatch, IstanzeoneriPosdebBatchId>
	implements IstanzeoneriPosdebBatchDAO {

    @Override
    public Class<IstanzeoneriPosdebBatch> getEntityClass() {

	return IstanzeoneriPosdebBatch.class;
    }

    @Override
    public List<IstanzeoneriPosdebBatch> findAll(Integer firstResult, Integer maxResult) {

	//	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	throw new NotImplementedException();
    }

    @Override
    public int contaByOnereWherePosizioneDebitoriaDiversaDa(Integer onereId, Integer idDettPosizioneDebitoria) {

	String hql = "Select count(*) from IstanzeoneriPosdebBatch a where a.id.idcomune=? and a.id.idistanzeoneri=? and a.id.idDettPosizioneDebitoria<>?";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), onereId, idDettPosizioneDebitoria };
	return ((Long) getHibernateTemplate().find(hql, values).get(0)).intValue();
    }

    @Override
    public void deleteByIdOnere(Integer onereId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("id.idistanzeoneri", onereId, onereId.getClass()));
	ft.addRestriction(r);
	List<IstanzeoneriPosdebBatch> righe = super.findByFilterTable(ft);
	for (IstanzeoneriPosdebBatch riga : righe) {
	    super.delete(riga);
	}
    }

    @Override
    public int contaByOnereWherePosizioneDebitoriaUgualeA(Integer onereId, Integer idDettPosizioneDebitoria) {

	String hql = "Select count(*) from IstanzeoneriPosdebBatch a where a.id.idcomune=? and a.id.idistanzeoneri=? and a.id.idDettPosizioneDebitoria=?";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), onereId, idDettPosizioneDebitoria };
	return ((Long) getHibernateTemplate().find(hql, values).get(0)).intValue();
    }

    @Override
    public List<Integer> findCodiciIstanzaPosizioniDaPreparare(int limit) {

	String hql = "Select io.istanzaId from IstanzeoneriPosdebBatch a inner join a.istanzeoneri io where a.id.idcomune=? and a.flagCompletata=? group by io.istanzaId";
	Query q = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
	q.setFirstResult(0);
	q.setMaxResults(limit);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, IstanzeoneriPosdebBatch.DA_ELABORARE);
	return q.list();
    }

    @Override
    public List<IstanzeoneriPosdebBatch> trovaIdOnerePerIstanzaECausaleDaElaborare(Integer codiceIstanza, Integer codiceCausaleOnere,
	    int daElaborare) {

	String hql = "Select ipb " //
		+ "from IstanzeoneriPosdebBatch ipb " //
		+ "inner join ipb.istanzeoneri io " //
		+ "inner join io.tipicausalioneri tco " //
		+ "where ipb.id.idcomune=? " //
		+ "and io.istanzaId=? " //
		+ "and io.tipicausalioneriId=? " //
		+ "and ipb.flagCompletata=? "; //
	Object[] values = new Object[] { ORMHelper.getIdcomune(), codiceIstanza, codiceCausaleOnere, daElaborare };
	HibernateTemplate hibernateTemplate = getHibernateTemplate();
	return hibernateTemplate.find(hql, values);
    }
}
