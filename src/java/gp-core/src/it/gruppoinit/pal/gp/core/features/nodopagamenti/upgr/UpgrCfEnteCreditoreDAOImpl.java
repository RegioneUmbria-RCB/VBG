package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrCfEnteCreditoreDAOImpl extends BaseDAOImpl implements UpgrCfEnteCreditoreDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<PosizioneConCFNullBean> getElencoPosizioniDaSanare() {

	QueryElencoPosizioniDaSanare queryHelper = new QueryElencoPosizioniDaSanare();
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneConCFNullBean.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<CFDaVerticalizzazioneChiave, String> leggiConfigurazione() {

	QueryConfigurazione queryHelper = new QueryConfigurazione();
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(CFDaVerticalizzazione.class));
	List<CFDaVerticalizzazione> parametri = q.list();
	Map<CFDaVerticalizzazioneChiave, String> mappa = new HashMap<CFDaVerticalizzazioneChiave, String>();
	for (CFDaVerticalizzazione parametro : parametri) {
	    mappa.put(new CFDaVerticalizzazioneChiave(parametro.getIdcomune(), parametro.getSoftware(), parametro.getCodiceComune()),
		    parametro.getValore());
	}
	return mappa;
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public void aggiornaPosizioneDebitoria(String idComune, int id, String cfEnteCreditore) {

	String sql = "update dett_posizione_debitoria set cf_ente_creditore = ? where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DettPosizioneDebitoria.class);
	query.setString(0, cfEnteCreditore);
	query.setString(1, idComune);
	query.setInteger(2, id);
	query.executeUpdate();
    }
}
