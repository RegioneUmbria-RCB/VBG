package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AlberoprocMovimentiDAOImpl extends BaseDAOImpl<AlberoprocMovimenti, PkId> implements AlberoprocMovimentiDAO {

    @Override
    public List<AlberoprocMovimenti> findByAlberoproc(Integer codiceAlberoproc) {

	if (codiceAlberoproc == null) {
	    throw new IllegalArgumentException("Il parametro codicealberoproc non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceAlberoproc, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("tipimovimentoId"));
	return this.findByFilterTable(ft);
    }

    @Override
    public List<AlberoprocMovimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Class<AlberoprocMovimenti> getEntityClass() {

	return AlberoprocMovimenti.class;
    }
}
