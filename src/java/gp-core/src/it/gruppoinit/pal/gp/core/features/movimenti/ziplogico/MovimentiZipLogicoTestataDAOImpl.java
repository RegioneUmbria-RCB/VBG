package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestataId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@SuppressWarnings("rawtypes")
@Repository
public class MovimentiZipLogicoTestataDAOImpl extends BaseDAOImpl<MovimentiZipLogicoTestata, MovimentiZipLogicoTestataId>
	implements MovimentiZipLogicoTestataDAO {

    @Override
    public Class<MovimentiZipLogicoTestata> getEntityClass() {

	return MovimentiZipLogicoTestata.class;
    }

    @Override
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento) {

	MovimentiZipLogicoTestata testata = this.findById(new MovimentiZipLogicoTestataId(codiceMovimento));
	this._delete(testata);
    }

    @Override
    public Boolean isZipLogicoExistInMovimento(Integer codicemovimento) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicemovimento", codicemovimento, Integer.class));
	filterTable.addRestriction(fr);
	return this.existsRecords(filterTable);
    }

    @Override
    public Boolean isZipLogicoDocumentoAllegato(Integer codicemovimento) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codicemovimento", codicemovimento, Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("codiceoggettoDocAll"));
	filterTable.addRestriction(fr);
	return this.existsRecords(filterTable);
    }
}
