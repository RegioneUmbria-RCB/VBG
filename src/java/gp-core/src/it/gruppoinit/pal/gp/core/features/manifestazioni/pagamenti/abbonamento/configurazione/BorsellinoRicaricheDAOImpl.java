package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoRicaricheDAOImpl extends BaseDAOImpl<BorsellinoRicariche, PkId> implements IBorsellinoRicaricheDAO {

    @Override
    public Class<BorsellinoRicariche> getEntityClass() {

	return BorsellinoRicariche.class;
    }

    @Override
    public List<BorsellinoRicariche> findByCodiceComune(String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("importo"));
	return findByFilterTable(ft);
    }

    @Override
    public void deleteById(Integer id) {

	if (id == null) {
	    throw new IllegalArgumentException("Impossibile cancellare una metodologia di ricarica senza passare l'id di riferimento");
	}
	String sql = "delete from borsellino_ricariche where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoRicariche.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, id);
	query.executeUpdate();
    }

    @Override
    public boolean importoLiberoConfiguratoPerComune(String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("comune.codicecomune", codiceComune, String.class));
	fr.addFilterField(FilterUtils.equals("tipo", TipoRicaricaEnum.LIBERO.name(), String.class));
	ft.addRestriction(fr);
	return existsRecords(ft);
    }

    @Override
    public void deleteImportiLiberoPerComune(String codicecomune) {

	String sql = "delete from borsellino_ricariche where idcomune = ? and codicecomune = ? and tipo = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoRicariche.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, codicecomune);
	query.setString(2, TipoRicaricaEnum.LIBERO.name());
	query.executeUpdate();
    }
}
