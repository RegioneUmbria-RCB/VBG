package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestMercatiDett;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.RiepilogoGiornateNonInizializzateBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class BollGestMercatiDettDAOImpl extends BaseDAOImpl<BollGestMercatiDett, PkId> implements BollGestMercatiDettDAO {

    @Override
    public Class<BollGestMercatiDett> getEntityClass() {

	return BollGestMercatiDett.class;
    }

    @Override
    public void deleteBollGestMercatiDettByIdGestAutorizzazioni(Integer idGestAutorizzazioni) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestDettAutorizzId", idGestAutorizzazioni, Integer.class));
	ft.addRestriction(fr);
	List<BollGestMercatiDett> dettagli = findByFilterTable(ft);
	if (dettagli != null) {
	    for (BollGestMercatiDett dettaglio : dettagli) {
		delete(dettaglio);
	    }
	}
    }

    @Override
    public void deleteByIdBollettazione(Integer idBollettazione) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteByIdBollettazione senza passare il riferimento della testata della bollettazione");
	}
	String sql = "delete from boll_gest_mercati_dett where idcomune = ? and fk_bollgest_id = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestMercatiDett.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
    }

    @Override
    public List<RiepilogoGiornateNonInizializzateBean> trovaGiornateNonInizializzateNelPeriodo(IntervalloDate intervalloDate,
	    List<Integer> filtriMercati) {

	StringBuilder sql = new StringBuilder();
	sql.append(" select mercatipresenze_t.dataregistrazione as dataregistrazione "); //
	sql.append(" ,mercatipresenze_t.descrizione as mercato "); //
	sql.append(" from mercatipresenze_t "); //
	sql.append(" left join mercatipresenze_d on mercatipresenze_d.idcomune = mercatipresenze_t.idcomune "); //
	sql.append(" and mercatipresenze_d.fkidtestata = mercatipresenze_t.id "); //
	sql.append(" where mercatipresenze_t.idcomune = ? "); //
	sql.append(" and ( "); //
	sql.append(" mercatipresenze_t.dataregistrazione between ? "); //
	sql.append(" and ? "); //
	sql.append(" ) "); //
	if (filtriMercati != null && !filtriMercati.isEmpty()) {
	    sql.append(" and mercatipresenze_t.fkcodicemercato in (?");
	    for (Integer codiceMercato : filtriMercati) {
		sql.append(",?");
	    }
	    sql.append(") ");
	}
	sql.append(" group by mercatipresenze_t.dataregistrazione "); //
	sql.append(" ,mercatipresenze_t.descrizione "); //
	sql.append(" having count(*) < ? "); //
	sql.append(" order by descrizione "); //
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.addScalar("mercato", Hibernate.STRING);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setDate(pos++, Utilities.impostaOrarioAData(intervalloDate.getDataInizio(), 0, 0, 0, Calendar.AM));
	q.setDate(pos++, Utilities.impostaOrarioAData(intervalloDate.getDataFine(), 23, 59, 59, Calendar.AM));
	if (filtriMercati != null && !filtriMercati.isEmpty()) {
	    q.setInteger(pos++, -99999); // record fasullo
	    for (Integer codiceMercato : filtriMercati) {
		q.setInteger(pos++, codiceMercato);
	    }
	}
	q.setInteger(pos++, 2);
	q.setResultTransformer(Transformers.aliasToBean(RiepilogoGiornateNonInizializzateBean.class));
	return q.list();
    }
}
