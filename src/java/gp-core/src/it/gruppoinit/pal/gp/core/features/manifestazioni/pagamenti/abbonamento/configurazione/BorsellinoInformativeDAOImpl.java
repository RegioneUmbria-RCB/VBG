package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoInformativeDAOImpl extends BaseDAOImpl<BorsellinoInformative, PkId> implements IBorsellinoInformativeDAO {

    @Override
    public Class<BorsellinoInformative> getEntityClass() {

	return BorsellinoInformative.class;
    }

    @Override
    public List<BorsellinoInformative> findByCodiceComune(String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public List<BorsellinoInformative> findByCodiceComuneAttive(String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	fr.addFilterField(FilterUtils.greaterEqual("dataFineValidita", Calendar.getInstance().getTime(), Date.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public void deleteById(Integer id) {

	if (id == null) {
	    throw new IllegalArgumentException("Impossibile cancellare un'informativa senza passare l'id di riferimento");
	}
	String sql = "delete from borsellino_informative where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoInformative.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, id);
	query.executeUpdate();
    }
}