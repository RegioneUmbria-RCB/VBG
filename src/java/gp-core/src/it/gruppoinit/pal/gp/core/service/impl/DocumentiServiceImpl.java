package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocSoggFirmatariService;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class DocumentiServiceImpl extends BaseServiceImpl<Documenti, PkId> implements DocumentiService {

    private DocumentiDAO documentiDAO;
    private OggettiService oggettiService;
    private InventarioprocSoggFirmatariService inventarioprocSoggFirmatariService;

    @Autowired
    public void setDocumentiDAO(DocumentiDAO documentiDAO) {

	this.documentiDAO = documentiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<Documenti> getEntityClass() {

	return Documenti.class;
    }

    @Autowired
    public void setInventarioprocSoggFirmatariService(InventarioprocSoggFirmatariService inventarioprocSoggFirmatariService) {

	this.inventarioprocSoggFirmatariService = inventarioprocSoggFirmatariService;
    }

    @Override
    public List<Documenti> findAll(Integer firstResult, Integer maxResult) {

	return documentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Documenti entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    documentiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public Documenti findById(PkId id) {

	return documentiDAO.findById(id);
    }

    @Override
    public void update(Documenti entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    documentiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Documenti entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	childDelete(entity);
	documentiDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public List<Documenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return documentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    protected void childDelete(Documenti entity) {

	Set<InventarioprocSoggFirmatari> inventarioprocSoggFirmataris = entity.getInventarioprocSoggFirmataris();
	for (InventarioprocSoggFirmatari inventarioprocSoggFirmatari : inventarioprocSoggFirmataris) {
	    inventarioprocSoggFirmatariService.delete(inventarioprocSoggFirmatari);
	}
    }
    //    private Integer controllaCancellaOggetti(Documenti entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggetti())) {
    //	    if (!(null == entity.getOggetti().getId())) {
    //		if (!(null == entity.getOggetti().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggetti().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("DOCUMENTI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Documenti entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggetti())) {
    //		    if (!(null == entityCopy.getOggetti().getId())) {
    //			if (!(null == entityCopy.getOggetti().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggetti().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("DOCUMENTI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    // protected boolean isDeleteAllowed(Documenti entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
