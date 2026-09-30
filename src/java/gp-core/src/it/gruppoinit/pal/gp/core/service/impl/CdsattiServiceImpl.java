package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CdsattiDAO;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CdsattiServiceImpl extends BaseServiceImpl<Cdsatti, PkId> implements CdsattiService {

    private CdsattiDAO cdsattiDAO;
    private CdsService cdsService;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private MovimentiZipLogicoService movimentiZipLogicoService;

    @Autowired
    public void setCdsattiDAO(CdsattiDAO cdsattiDAO) {

	this.cdsattiDAO = cdsattiDAO;
    }

    @Autowired
    public void setCdsService(CdsService cdsService) {

	this.cdsService = cdsService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    protected Class<Cdsatti> getEntityClass() {

	return Cdsatti.class;
    }

    @Override
    public List<Cdsatti> findAll(Integer firstResult, Integer maxResult) {

	return cdsattiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cdsatti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    cdsattiDAO.insert(entity);
	}
    }

    @Override
    public Cdsatti findById(PkId id) {

	return cdsattiDAO.findById(id);
    }

    @Override
    public void update(Cdsatti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    cdsattiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Cdsatti entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    childDelete(entity);
	    cdsattiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected void childDelete(Cdsatti entity) {

    }

    private void dataIntegration(Cdsatti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro CDSATTI è nullo.");
	}
	fixMergeEntityProperties(entity);
	//TODO da decidere se gestire il campo ed eventualmente come
	//	if (entity.getPositivia() == null) {
	//	    entity.setPositivia(Boolean.FALSE);
	//	}
	if (entity.getChiusa() == null) {
	    entity.setChiusa(WebConstants.N);
	}
	if (entity.getData() == null) {
	    entity.setData(new Date());
	}
    }

    @Override
    protected void fixMergeEntityProperties(Cdsatti entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	// TODO aspettando modifica di Corsetti
	//	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	//	entity.setOggetti(oggetto);
	Cds cds = cdsService.bindDomainObject(entity.getCds(), PkId.class, "id.codice");
	entity.setCds(cds);
    }

    protected boolean isDeleteAllowed(Cdsatti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(), "cdsatti");
	if (isExistEntityInMovimentiZipLogico) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<CdsattiDTO> findDTOByCds(Integer idCds, Boolean cercaOggetti) {

	return cdsattiDAO.findDTOByCds(idCds, cercaOggetti);
    }

    @Override
    public List<CdsattiDTO> findDTOByIstanza(Integer codiceIstanza, Boolean cercaOggetti) {

	return cdsattiDAO.findDTOByIstanza(codiceIstanza, cercaOggetti);
    }
}
