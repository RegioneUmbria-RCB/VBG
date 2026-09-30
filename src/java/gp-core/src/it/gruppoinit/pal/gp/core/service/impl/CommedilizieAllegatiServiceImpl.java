package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.commissioni.allegati.ICommissioniAllegatiService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioDocumentiCommissioniAggiunti;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioDocumentiCommissioniEliminati;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioDocumentiCommissioniModificati;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.CommedilizieAllegatiService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommedilizieAllegatiServiceImpl extends BaseServiceImpl<CommedilizieAllegati, PkId> implements CommedilizieAllegatiService {

    private CommedilizieAllegatiDAO commedilizieallegatiDAO;
    private ICommissioniAllegatiService commissioniAllegatiService;
    private CommissioniedilizieTService commissioniedilizieTService;
    private OggettiService oggettiService;
    private ICommissioniAuditingService auditingService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setAuditingService(ICommissioniAuditingService auditingService) {

	this.auditingService = auditingService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setCommedilizieAllegatiDAO(CommedilizieAllegatiDAO commedilizieallegatiDAO) {

	this.commedilizieallegatiDAO = commedilizieallegatiDAO;
    }

    @Autowired
    public void setCommissioniedilizieTService(CommissioniedilizieTService commissioniedilizieTService) {

	this.commissioniedilizieTService = commissioniedilizieTService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setCommissioniAllegatiService(ICommissioniAllegatiService commissioniAllegatiService) {

	this.commissioniAllegatiService = commissioniAllegatiService;
    }

    @Override
    protected Class<CommedilizieAllegati> getEntityClass() {

	return CommedilizieAllegati.class;
    }

    @Override
    public List<CommedilizieAllegati> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return commedilizieallegatiDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(CommedilizieAllegati entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	if (validateEntity(entity)) {
	    commedilizieallegatiDAO.insert(entity);
	    childDataInsert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioDocumentiCommissioniAggiunti(getResponsabile(), entity.getOggetti().getNomefile()));
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieAllegati findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizieallegatiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieAllegati entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	if (validateEntity(entity)) {
	    commedilizieallegatiDAO.update(entity);
	    childDataUpdate(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
		this.commissioniAllegatiService.eliminaFirmePerAllegato(entity.getId().getCodice());
	    }
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(), new MessaggioDocumentiCommissioniModificati(
		    getResponsabile(), entity.getOggetti() == null ? "" : entity.getOggetti().getNomefile()));
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieAllegati entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    childDelete(entity);
	    commedilizieallegatiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(),
		    new MessaggioDocumentiCommissioniEliminati(getResponsabile(), entity.getOggetti().getNomefile()));
	}
	// §§§END§§§
    }

    @Override
    public List<CommedilizieAllegati> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commedilizieallegatiDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    //    protected boolean isDeleteAllowed(CommedilizieAllegati entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    private void childDataInsert(CommedilizieAllegati entity) {

    }

    private void childDataUpdate(CommedilizieAllegati entity) {

    }

    @Override
    protected void childDelete(CommedilizieAllegati entity) {

	super.childDelete(entity);
    }

    private void dataIntegration(CommedilizieAllegati entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("L'allegato della commissione passato è nullo");
	}
	entity.setDataregistrazione(Calendar.getInstance().getTime());
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(CommedilizieAllegati entity) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.bindDomainObject(entity.getCommissioniedilizieT(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieT(commissioniedilizieT);
	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetti);
	// §§§END§§§
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }
}
