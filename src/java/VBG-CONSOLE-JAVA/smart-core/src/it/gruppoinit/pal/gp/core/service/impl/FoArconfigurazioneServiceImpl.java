package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArconfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoArconfigurazioneServiceImpl extends BaseServiceImpl<FoArconfigurazione, FoArconfigurazioneId> implements FoArconfigurazioneService {

    private Dyn2ModellitService dyn2ModellitService;
    private FoArconfigurazioneDAO foArconfigurazioneDAO;
    private OggettiService oggettiService;
    private FoArjStepsTestataService foArjStepsTestataService;

    @Autowired
    public void setFoArjStepsTestataService(FoArjStepsTestataService foArjStepsTestataService) {

	this.foArjStepsTestataService = foArjStepsTestataService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setFoArconfigurazioneDAO(FoArconfigurazioneDAO foArconfigurazioneDAO) {

	this.foArconfigurazioneDAO = foArconfigurazioneDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<FoArconfigurazione> getEntityClass() {

	return FoArconfigurazione.class;
    }

    @Override
    public void delete(FoArconfigurazione entity) {

	Integer codiceOggettoFirmaDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoFirma", true, entity.getId());
	Integer codiceOggettoSottoscrizDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoSottoscriz", true, entity.getId());
	Integer codiceOggettoWorkflowDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflow", true, entity.getId());
	Integer codiceOggettoMenuxmlDaCancellare = controllaCancellaOggetti(entity, "oggettoMenuxml", true, entity.getId());
	foArconfigurazioneDAO.delete(entity);
	entity.setCodiceoggettoSottoscriz(null);
	entity.setCodiceoggettoFirma(null);
	if (codiceOggettoFirmaDaCancellare != null) {
	    Oggetti oggettoDaCancellarefirma = oggettiService.findById(new PkId(codiceOggettoFirmaDaCancellare));
	    oggettiService.delete(oggettoDaCancellarefirma);
	}
	if (codiceOggettoSottoscrizDaCancellare != null) {
	    Oggetti oggettoDaCancellaresottoscriz = oggettiService.findById(new PkId(codiceOggettoSottoscrizDaCancellare));
	    oggettiService.delete(oggettoDaCancellaresottoscriz);
	}
	if (codiceOggettoWorkflowDaCancellare != null) {
	    Oggetti oggettoWorkflowDaCancellare = oggettiService.findById(new PkId(codiceOggettoWorkflowDaCancellare));
	    oggettiService.delete(oggettoWorkflowDaCancellare);
	}
	if (codiceOggettoMenuxmlDaCancellare != null) {
	    Oggetti oggettoMenuxmlDaCancellare = oggettiService.findById(new PkId(codiceOggettoMenuxmlDaCancellare));
	    oggettiService.delete(oggettoMenuxmlDaCancellare);
	}
    }

    @Override
    public List<FoArconfigurazione> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public FoArconfigurazione findById(FoArconfigurazioneId id) {

	return foArconfigurazioneDAO.findById(id);
    }

    @Override
    public void insert(FoArconfigurazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    Integer codiceOggettoFirmaDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoFirma", false, entity.getId());
	    Integer codiceOggettoSottoscrizDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoSottoscriz", false, entity.getId());
	    Integer codiceOggettoWorkflowDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflow", false, entity.getId());
	    Integer codiceOggettoMenuxmlDaCancellare = controllaCancellaOggetti(entity, "oggettoMenuxml", false, entity.getId());
	    foArconfigurazioneDAO.insert(entity);
	    if (codiceOggettoFirmaDaCancellare != null) {
		Oggetti oggettoDaCancellarefirma = oggettiService.findById(new PkId(codiceOggettoFirmaDaCancellare));
		oggettiService.delete(oggettoDaCancellarefirma);
	    }
	    if (codiceOggettoSottoscrizDaCancellare != null) {
		Oggetti oggettoDaCancellaresottoscriz = oggettiService.findById(new PkId(codiceOggettoSottoscrizDaCancellare));
		oggettiService.delete(oggettoDaCancellaresottoscriz);
	    }
	    if (codiceOggettoWorkflowDaCancellare != null) {
		Oggetti oggettoWorkflowDaCancellare = oggettiService.findById(new PkId(codiceOggettoWorkflowDaCancellare));
		oggettiService.delete(oggettoWorkflowDaCancellare);
	    }
	    if (codiceOggettoMenuxmlDaCancellare != null) {
		Oggetti oggettoMenuxmlDaCancellare = oggettiService.findById(new PkId(codiceOggettoMenuxmlDaCancellare));
		oggettiService.delete(oggettoMenuxmlDaCancellare);
	    }
	}
    }

    @Override
    public void update(FoArconfigurazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    Integer codiceOggettoFirmaDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoFirma", false, entity.getId());
	    Integer codiceOggettoSottoscrizDaCancellare = controllaCancellaOggetti(entity, "codiceoggettoSottoscriz", false, entity.getId());
	    Integer codiceOggettoWorkflowDaCancellare = controllaCancellaOggetti(entity, "oggettoWorkflow", false, entity.getId());
	    Integer codiceOggettoMenuxmlDaCancellare = controllaCancellaOggetti(entity, "oggettoMenuxml", false, entity.getId());
	    foArconfigurazioneDAO.update(entity);
	    if (codiceOggettoFirmaDaCancellare != null) {
		Oggetti oggettoDaCancellarefirma = oggettiService.findById(new PkId(codiceOggettoFirmaDaCancellare));
		if (oggettoDaCancellarefirma != null) {
		    oggettiService.delete(oggettoDaCancellarefirma);
		}
	    }
	    if (codiceOggettoSottoscrizDaCancellare != null) {
		Oggetti oggettoDaCancellaresottoscriz = oggettiService.findById(new PkId(codiceOggettoSottoscrizDaCancellare));
		if (oggettoDaCancellaresottoscriz != null) {
		    oggettiService.delete(oggettoDaCancellaresottoscriz);
		}
	    }
	    if (codiceOggettoWorkflowDaCancellare != null) {
		Oggetti oggettoWorkflowDaCancellare = oggettiService.findById(new PkId(codiceOggettoWorkflowDaCancellare));
		if (oggettoWorkflowDaCancellare != null) {
		    oggettiService.delete(oggettoWorkflowDaCancellare);
		}
	    }
	    if (codiceOggettoMenuxmlDaCancellare != null) {
		Oggetti oggettoMenuxmlDaCancellare = oggettiService.findById(new PkId(codiceOggettoMenuxmlDaCancellare));
		if (oggettoMenuxmlDaCancellare != null) {
		    oggettiService.delete(oggettoMenuxmlDaCancellare);
		}
	    }
	}
    }

    private void dataIntegration(FoArconfigurazione entity) {

	if (entity.getFlgSchedaEcRichiedefirma() == null) {
	    entity.setFlgSchedaEcRichiedefirma(false);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(FoArconfigurazione entity) {

	//	Statiistanza statiistanza = statiistanzaService.bindDomainObject(entity.getStatoInizialeIstanza(), String.class, "id.codicestato");
	//	entity.setStatoInizialeIstanza(statiistanza);
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(entity.getDyn2Modellit(), PkId.class, "id.codice");
	entity.setDyn2Modellit(dyn2Modellit);
	Oggetti oggettiFirma = oggettiService.bindDomainObject(entity.getCodiceoggettoFirma(), PkId.class, "id.codice");
	entity.setCodiceoggettoFirma(oggettiFirma);
	Oggetti oggettiSottoscriz = oggettiService.bindDomainObject(entity.getCodiceoggettoSottoscriz(), PkId.class, "id.codice");
	entity.setCodiceoggettoSottoscriz(oggettiSottoscriz);
	Oggetti oggettoWorkflow = oggettiService.bindDomainObject(entity.getOggettoWorkflow(), PkId.class, "id.codice");
	entity.setOggettoWorkflow(oggettoWorkflow);
	Oggetti oggettoMenuxml = oggettiService.bindDomainObject(entity.getOggettoMenuxml(), PkId.class, "id.codice");
	entity.setOggettoMenuxml(oggettoMenuxml);
	FoArjStepsTestata testata = foArjStepsTestataService.bindDomainObject(entity.getFoArjStepsTestata(), PkId.class, "id.codice");
	entity.setFoArjStepsTestata(testata);
    }

    @Override
    public FoArconfigurazione findBySoftware(Software software) {

	return foArconfigurazioneDAO.findBySoftware(software);
    }

    //    private Integer controllaCancellaOggettiFirma(FoArconfigurazione entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getCodiceoggettoFirma())) {
    //	    if (!(null == entity.getCodiceoggettoFirma().getId())) {
    //		if (!(null == entity.getCodiceoggettoFirma().getId().getCodice())) {
    //		    codiceOggetto = entity.getCodiceoggettoFirma().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("FO_ARCONFIGURAZIONE", "CODICEOGGETTO_FIRMA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    FoArconfigurazione entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getCodiceoggettoFirma())) {
    //		    if (!(null == entityCopy.getCodiceoggettoFirma().getId())) {
    //			if (!(null == entityCopy.getCodiceoggettoFirma().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getCodiceoggettoFirma().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("FO_ARCONFIGURAZIONE", "CODICEOGGETTO_FIRMA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    @Override
    public List<FoArconfigurazione> findByOggetto(Integer codiceOggetto) {

	return foArconfigurazioneDAO.findByOggetto(codiceOggetto);
    }

    //    private Integer controllaCancellaOggettiSottoscriz(FoArconfigurazione entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getCodiceoggettoSottoscriz())) {
    //	    if (!(null == entity.getCodiceoggettoSottoscriz().getId())) {
    //		if (!(null == entity.getCodiceoggettoSottoscriz().getId().getCodice())) {
    //		    codiceOggetto = entity.getCodiceoggettoSottoscriz().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("FO_ARCONFIGURAZIONE", "CODICEOGGETTO_SOTTOSCRIZ", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    FoArconfigurazione entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getCodiceoggettoSottoscriz())) {
    //		    if (!(null == entityCopy.getCodiceoggettoSottoscriz().getId())) {
    //			if (!(null == entityCopy.getCodiceoggettoSottoscriz().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getCodiceoggettoSottoscriz().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("FO_ARCONFIGURAZIONE", "CODICEOGGETTO_SOTTOSCRIZ", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    //    @Override
    //    public Statiistanza findStatoInizialeIstanzaOnline() {
    //
    //	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
    //	FilterRestriction software = new FilterRestriction();
    //	software.addFilterField(FilterUtils.in("id.software", new String[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
    //	ft.addRestriction(software);
    //	FilterRestriction fr = new FilterRestriction();
    //	fr.addFilterField(FilterUtils.isNotNull("id.codicestato", "statoInizialeIstanza"));
    //	ft.addRestriction(fr);
    //	ft.addOrder(FilterUtils.orderDesc("moduloopzionale", "statoInizialeIstanza.software", FunctionsEnum.NVL_FUNCTION, "0"));
    //	List<FoArconfigurazione> configurazione = foArconfigurazioneDAO.findByFilterTable(ft);
    //	for (FoArconfigurazione foArconfigurazione : configurazione) {
    //	    Statiistanza statoInizialeIstanza = foArconfigurazione.getStatoInizialeIstanza();
    //	    return statoInizialeIstanza;
    //	}
    //	return null;
    //    }
    private boolean isInsertAllowed(FoArconfigurazione entity) {

	boolean isInsert = true;
	if (EntityUtils.isNestedPropertyBlank(entity.getDyn2Modellit(), "id.codice") && entity.getFlgSchedaEcRichiedefirma() != null
		&& entity.getFlgSchedaEcRichiedefirma().equals(true)) {
	    isInsert = false;
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    _ivs.add(new InvalidValue("errors.foarconfigurazione.scheda_da_firmare_non_selezionata", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	return isInsert;
    }

    @Override
    public List<FoArconfigurazione> findByFoArjStepsTestata(Integer codiceTestata) {

	if (codiceTestata == null) {
	    throw new RuntimeException("il parametro codice testata è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("foArjStepsTestataId", codiceTestata, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.software"));
	return foArconfigurazioneDAO.findByFilterTable(ft);
    }
}
