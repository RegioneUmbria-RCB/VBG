package it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MercatipresenzeDMassiveDAOImpl extends BaseDAOImpl<MercatipresenzeDMassive, PkId> implements IMercatipresenzeDMassiveDAO {

    @Override
    public Class<MercatipresenzeDMassive> getEntityClass() {

	return MercatipresenzeDMassive.class;
    }

    @Override
    public Integer findIdPresenzaByDettaglio(Integer idMercatipresenzeDMassive) {

	if (idMercatipresenzeDMassive == null) {
	    throw new IllegalArgumentException("idMercatipresenzeDMassive non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idMercatipresenzeDMassive, Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeDMassive> result = this.findByFilterTable(ft);
	if (result.isEmpty()) {
	    return null;
	}
	if (result.get(0) != null && result.get(0).getMercatipresenzeD() != null && result.get(0).getMercatipresenzeD().getId() != null) {
	    return result.get(0).getMercatipresenzeD().getId().getCodice();
	}
	return null;
    }

    @Override
    public MercatipresenzeDMassive findByDettaglio(Integer idMassivaDettaglio) {

	if (idMassivaDettaglio == null) {
	    throw new IllegalArgumentException("idMassivaDettaglio non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("massiveDettaglioId", idMassivaDettaglio, Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeDMassive> result = this.findByFilterTable(ft);
	if (result.isEmpty()) {
	    return null;
	}
	if (result.get(0) != null && result.get(0).getMercatipresenzeD() != null && result.get(0).getMercatipresenzeD().getId() != null) {
	    return result.get(0);
	}
	return null;
    }
}
