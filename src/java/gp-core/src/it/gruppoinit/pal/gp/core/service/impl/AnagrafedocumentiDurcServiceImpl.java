package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafedocumentiDurcDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiDurcService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AnagrafedocumentiDurcServiceImpl extends BaseServiceImpl<AnagrafedocumentiDurc, PkId> implements AnagrafedocumentiDurcService {

    private AnagrafedocumentiDurcDAO anagrafedocumentidurcDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAnagrafedocumentiDurcDAO(AnagrafedocumentiDurcDAO anagrafedocumentidurcDAO) {

	this.anagrafedocumentidurcDAO = anagrafedocumentidurcDAO;
    }

    @Override
    protected Class<AnagrafedocumentiDurc> getEntityClass() {

	return AnagrafedocumentiDurc.class;
    }

    @Override
    public List<AnagrafedocumentiDurc> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedocumentidurcDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AnagrafedocumentiDurc entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    anagrafedocumentidurcDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public AnagrafedocumentiDurc findById(PkId id) {

	return anagrafedocumentidurcDAO.findById(id);
    }

    @Override
    public void update(AnagrafedocumentiDurc entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    anagrafedocumentidurcDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(AnagrafedocumentiDurc entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    anagrafedocumentidurcDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(AnagrafedocumentiDurc entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<AnagrafedocumentiDurc> findByAnagrafedocumenti(Integer codiceAnagrafeDocumenti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafedocumentiId", codiceAnagrafeDocumenti, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("issuedate"));
	return anagrafedocumentidurcDAO.findByFilterTable(ft);
    }
}
