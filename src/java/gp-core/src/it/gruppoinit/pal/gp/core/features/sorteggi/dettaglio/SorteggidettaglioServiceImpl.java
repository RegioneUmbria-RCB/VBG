package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.helper.SorteggidettaglioDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class SorteggidettaglioServiceImpl extends BaseServiceImpl<Sorteggidettaglio, PkId> implements SorteggidettaglioService {

    private SorteggidettaglioDAO sorteggidettaglioDAO;

    @Autowired
    public void setSorteggidettaglioDAO(SorteggidettaglioDAO sorteggidettaglioDAO) {

	this.sorteggidettaglioDAO = sorteggidettaglioDAO;
    }

    @Override
    protected Class<Sorteggidettaglio> getEntityClass() {

	return Sorteggidettaglio.class;
    }

    @Override
    public List<Sorteggidettaglio> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return sorteggidettaglioDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Sorteggidettaglio entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggidettaglioDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Sorteggidettaglio findById(PkId id) {

	// §§§BEGIN§§§
	return sorteggidettaglioDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Sorteggidettaglio entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggidettaglioDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Sorteggidettaglio entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    sorteggidettaglioDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public Sorteggidettaglio findByIstanza(Istanze istanza) {

	// §§§BEGIN§§§
	return sorteggidettaglioDAO.findByIstanza(istanza);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@    
    }

    @Override
    public List<Sorteggidettaglio> findBySorteggitestata(Sorteggitestata sorteggitestata) {

	// §§§BEGIN§§§
	return sorteggidettaglioDAO.findBySorteggitestata(sorteggitestata);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Sorteggidettaglio> findAllByIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanza", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("stDatasorteggio", "sorteggitestata"));
	return sorteggidettaglioDAO.findByFilterTable(ft);
    }

    @Override
    public List<Integer> findCodiciIstanzaBySorteggitestata(Integer codiceSorteggitestata) {

	return sorteggidettaglioDAO.findCodiciIstanzaBySorteggitestata(codiceSorteggitestata);
    }

    @Override
    public List<Integer> findCodiciIstanzaBySorteggicategoria(Integer codiceSorteggiCategoria) {

	return sorteggidettaglioDAO.findCodiciIstanzaBySorteggicategoria(codiceSorteggiCategoria);
    }

    @Override
    public List<SorteggidettaglioDTO> findBySorteggitestata(Integer codice) {

	return sorteggidettaglioDAO.findBySorteggitestata(codice);
    }
}
