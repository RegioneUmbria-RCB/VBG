package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Subprocedure;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocGruppiSmistService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ImpiantiprocedureService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.SubprocedureService;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDocumentiService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureavvioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiprocedureServiceImpl extends BaseServiceImpl<Tipiprocedure, PkId> implements TipiprocedureService {

    private TipiprocedureDAO tipiprocedureDAO;
    private AlberoprocService alberoprocService;
    private ImpiantiprocedureService impiantiprocedureService;
    private OggettiService oggettiService;
    private SubprocedureService subprocedureService;
    private TempirispostaService tempirispostaService;
    private TipicontromovimentoService tipicontromovimentoService;
    private TipiprocedureavvioService tipiprocedureavvioService;
    private TipiprocedureDocumentiService tipiprocedureDocumentiService;
    private TipiprocedureDyn2modellitService tipiprocedureDyn2modellitService;
    private IstanzeService istanzeService;
    private StatiistanzaService statiistanzaService;
    private NatureProcedureService natureProcedureService;
    private AlberoprocGruppiSmistService alberoprocGruppiSmistService;

    @Autowired
    public void setAlberoprocGruppiSmistService(AlberoprocGruppiSmistService alberoprocGruppiSmistService) {

	this.alberoprocGruppiSmistService = alberoprocGruppiSmistService;
    }

    @Autowired
    public void setNatureProcedureService(NatureProcedureService natureProcedureService) {

	this.natureProcedureService = natureProcedureService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setImpiantiprocedureService(ImpiantiprocedureService impiantiprocedureService) {

	this.impiantiprocedureService = impiantiprocedureService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSubprocedureService(SubprocedureService subprocedureService) {

	this.subprocedureService = subprocedureService;
    }

    @Autowired
    public void setTipicontromovimentoService(TipicontromovimentoService tipicontromovimentoService) {

	this.tipicontromovimentoService = tipicontromovimentoService;
    }

    @Autowired
    public void setTipiprocedureDAO(TipiprocedureDAO tipiprocedureDAO) {

	this.tipiprocedureDAO = tipiprocedureDAO;
    }

    @Autowired
    public void setTempirispostaService(TempirispostaService tempirispostaService) {

	this.tempirispostaService = tempirispostaService;
    }

    @Autowired
    public void setTipiprocedureavvioService(TipiprocedureavvioService tipiprocedureavvioService) {

	this.tipiprocedureavvioService = tipiprocedureavvioService;
    }

    @Autowired
    public void setTipiprocedureDocumentiService(TipiprocedureDocumentiService tipiprocedureDocumentiService) {

	this.tipiprocedureDocumentiService = tipiprocedureDocumentiService;
    }

    @Autowired
    public void setTipiprocedureDyn2modellitService(TipiprocedureDyn2modellitService tipiprocedureDyn2modellitService) {

	this.tipiprocedureDyn2modellitService = tipiprocedureDyn2modellitService;
    }

    @Override
    protected Class<Tipiprocedure> getEntityClass() {

	return Tipiprocedure.class;
    }

    @Override
    public void delete(Tipiprocedure entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoModello = controllaCancellaOggetti(entity, "oggettoModello", true, entity.getId());
	    Integer codiceOggettoModelloPrecompilato = controllaCancellaOggetti(entity, "oggettoModelloPrecompilato", true, entity.getId());
	    Integer codiceOggettoModelloDiagramma = controllaCancellaOggetti(entity, "oggettoModelloDiagramma", true, entity.getId());
	    Integer codiceOggettoModelloDomandaOnline = controllaCancellaOggetti(entity, "oggettoModelloDomandaOnline", true, entity.getId());
	    Integer codiceOggettoCertificatoInvio = controllaCancellaOggetti(entity, "oggettoCertificatoInvio", true, entity.getId());
	    Integer codiceOggettoRicevutaCart = controllaCancellaOggetti(entity, "oggettoRicevutaCart", false, entity.getId());
	    tipiprocedureDAO.delete(entity);
	    entity.setOggettoModello(null);
	    entity.setOggettoModelloDiagramma(null);
	    entity.setOggettoModelloDomandaOnline(null);
	    entity.setOggettoModelloPrecompilato(null);
	    entity.setOggettoCertificatoInvio(null);
	    entity.setOggettoRicevutaCart(null);
	    if (codiceOggettoModello != null) {
		Oggetti oggettoModello = oggettiService.findById(new PkId(codiceOggettoModello));
		oggettiService.delete(oggettoModello);
	    }
	    if (codiceOggettoModelloPrecompilato != null) {
		Oggetti oggettoModelloPrecompilato = oggettiService.findById(new PkId(codiceOggettoModelloPrecompilato));
		oggettiService.delete(oggettoModelloPrecompilato);
	    }
	    if (codiceOggettoModelloDiagramma != null) {
		Oggetti oggettoModelloDiagramma = oggettiService.findById(new PkId(codiceOggettoModelloDiagramma));
		oggettiService.delete(oggettoModelloDiagramma);
	    }
	    if (codiceOggettoModelloDomandaOnline != null) {
		Oggetti oggettoModelloDomandaOnline = oggettiService.findById(new PkId(codiceOggettoModelloDomandaOnline));
		oggettiService.delete(oggettoModelloDomandaOnline);
	    }
	    if (codiceOggettoCertificatoInvio != null) {
		Oggetti oggettoCertificatoInvio = oggettiService.findById(new PkId(codiceOggettoCertificatoInvio));
		oggettiService.delete(oggettoCertificatoInvio);
	    }
	    if (codiceOggettoRicevutaCart != null) {
		Oggetti oggettoRicevutaCart = oggettiService.findById(new PkId(codiceOggettoRicevutaCart));
		oggettiService.delete(oggettoRicevutaCart);
	    }
	}
    }

    @Override
    public List<Tipiprocedure> findAll(Integer firstResult, Integer maxResult) {

	return tipiprocedureDAO.findAll(null, null);
    }

    @Override
    public Tipiprocedure findById(PkId id) {

	return tipiprocedureDAO.findById(id);
    }

    @Override
    public void insert(Tipiprocedure entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoModello = controllaCancellaOggetti(entity, "oggettoModello", false, entity.getId());
	    Integer codiceOggettoModelloPrecompilato = controllaCancellaOggetti(entity, "oggettoModelloPrecompilato", false, entity.getId());
	    Integer codiceOggettoModelloDiagramma = controllaCancellaOggetti(entity, "oggettoModelloDiagramma", false, entity.getId());
	    Integer codiceOggettoModelloDomandaOnline = controllaCancellaOggetti(entity, "oggettoModelloDomandaOnline", false, entity.getId());
	    Integer codiceOggettoCertificatoInvio = controllaCancellaOggetti(entity, "oggettoCertificatoInvio", false, entity.getId());
	    Integer codiceOggettoRicevutaCart = controllaCancellaOggetti(entity, "oggettoRicevutaCart", false, entity.getId());
	    tipiprocedureDAO.insert(entity);
	    if (codiceOggettoModello != null) {
		Oggetti oggettoModello = oggettiService.findById(new PkId(codiceOggettoModello));
		oggettiService.delete(oggettoModello);
	    }
	    if (codiceOggettoModelloPrecompilato != null) {
		Oggetti oggettoModelloPrecompilato = oggettiService.findById(new PkId(codiceOggettoModelloPrecompilato));
		oggettiService.delete(oggettoModelloPrecompilato);
	    }
	    if (codiceOggettoModelloDiagramma != null) {
		Oggetti oggettoModelloDiagramma = oggettiService.findById(new PkId(codiceOggettoModelloDiagramma));
		oggettiService.delete(oggettoModelloDiagramma);
	    }
	    if (codiceOggettoModelloDomandaOnline != null) {
		Oggetti oggettoModelloDomandaOnline = oggettiService.findById(new PkId(codiceOggettoModelloDomandaOnline));
		oggettiService.delete(oggettoModelloDomandaOnline);
	    }
	    if (codiceOggettoCertificatoInvio != null) {
		Oggetti oggettoCertificatoInvio = oggettiService.findById(new PkId(codiceOggettoCertificatoInvio));
		oggettiService.delete(oggettoCertificatoInvio);
	    }
	    if (codiceOggettoRicevutaCart != null) {
		Oggetti oggettoRicevutaCart = oggettiService.findById(new PkId(codiceOggettoRicevutaCart));
		oggettiService.delete(oggettoRicevutaCart);
	    }
	}
    }

    @Override
    protected boolean validateEntity(Tipiprocedure entity) {

	boolean valid = super.validateEntity(entity);
	if (BooleanUtils.isTrue(entity.getFlagChiusuraAut())) {
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    if (entity.getStatochiusuraistanza() == null) {
		InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "statochiusuraistanza", null, entity);
		ivs.add(iv);
	    }
	    if (entity.getTipimovimentoChiusura() == null) {
		InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "tipimovimentoChiusura", null, entity);
		ivs.add(iv);
	    }
	    if (ivs.size() > 0) {
		throwValidationMessages(ivs);
	    }
	}
	return valid;
    }

    private void dataIntegration(Tipiprocedure entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("La procedura passata è nullo");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	if (entity.getFlagattochiusura() == null) {
	    entity.setFlagattochiusura(Boolean.FALSE);
	}
	if (entity.getFlagautocertificabile() == null) {
	    entity.setFlagautocertificabile(Boolean.FALSE);
	}
	if (entity.getFlagElabchEndo() == null) {
	    entity.setFlagElabchEndo(Boolean.FALSE);
	}
	if (entity.getFlagElabchTermini() == null) {
	    entity.setFlagElabchTermini(Boolean.FALSE);
	}
	if (entity.getFlagprevedecds() == null) {
	    entity.setFlagprevedecds(Boolean.FALSE);
	}
	if (entity.getFlagChiusuraAut() == null) {
	    entity.setFlagChiusuraAut(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Tipiprocedure entity) {

	Statiistanza statiistanza = statiistanzaService.bindDomainObject(entity.getStatochiusuraistanza(), StatiistanzaId.class, "id.codicestato");
	entity.setStatochiusuraistanza(statiistanza);
    }

    @Override
    public void update(Tipiprocedure entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoModello = controllaCancellaOggetti(entity, "oggettoModello", false, entity.getId());
	    Integer codiceOggettoModelloPrecompilato = controllaCancellaOggetti(entity, "oggettoModelloPrecompilato", false, entity.getId());
	    Integer codiceOggettoModelloDiagramma = controllaCancellaOggetti(entity, "oggettoModelloDiagramma", false, entity.getId());
	    Integer codiceOggettoModelloDomandaOnline = controllaCancellaOggetti(entity, "oggettoModelloDomandaOnline", false, entity.getId());
	    Integer codiceOggettoCertificatoInvio = controllaCancellaOggetti(entity, "oggettoCertificatoInvio", false, entity.getId());
	    Integer codiceOggettoRicevutaCart = controllaCancellaOggetti(entity, "oggettoRicevutaCart", false, entity.getId());
	    tipiprocedureDAO.update(entity);
	    if (codiceOggettoModello != null) {
		Oggetti oggettoModello = oggettiService.findById(new PkId(codiceOggettoModello));
		oggettiService.delete(oggettoModello);
	    }
	    if (codiceOggettoModelloPrecompilato != null) {
		Oggetti oggettoModelloPrecompilato = oggettiService.findById(new PkId(codiceOggettoModelloPrecompilato));
		oggettiService.delete(oggettoModelloPrecompilato);
	    }
	    if (codiceOggettoModelloDiagramma != null) {
		Oggetti oggettoModelloDiagramma = oggettiService.findById(new PkId(codiceOggettoModelloDiagramma));
		oggettiService.delete(oggettoModelloDiagramma);
	    }
	    if (codiceOggettoModelloDomandaOnline != null) {
		Oggetti oggettoModelloDomandaOnline = oggettiService.findById(new PkId(codiceOggettoModelloDomandaOnline));
		oggettiService.delete(oggettoModelloDomandaOnline);
	    }
	    if (codiceOggettoCertificatoInvio != null) {
		Oggetti oggettoCertificatoInvio = oggettiService.findById(new PkId(codiceOggettoCertificatoInvio));
		oggettiService.delete(oggettoCertificatoInvio);
	    }
	    if (codiceOggettoRicevutaCart != null) {
		Oggetti oggettoRicevutaCart = oggettiService.findById(new PkId(codiceOggettoRicevutaCart));
		oggettiService.delete(oggettoRicevutaCart);
	    }
	}
    }

    @Override
    public List<Tipiprocedure> findAllBySoftwareAndTT() {

	return tipiprocedureDAO.findAllBySoftwareAndTT();
    }

    @Override
    public List<Tipiprocedure> findByDescrizione(String descrizione) {

	return tipiprocedureDAO.findByDescrizione(descrizione);
    }

    @Override
    public List<Tipiprocedure> findByCodiceODescrizione(String text, boolean includiDisabilitate, boolean soloConMovimentoAvvio) {

	return tipiprocedureDAO.findByCodiceODescrizione(text, includiDisabilitate, soloConMovimentoAvvio);
    }

    @Override
    public List<Tipiprocedure> findTipiprocedureWithMovimentoavvio() {

	List<Tipiprocedure> risultato = new ArrayList<Tipiprocedure>();
	List<Tipiprocedure> listaProcedure = tipiprocedureDAO.findAllBySoftwareAndTT();
	for (Tipiprocedure tipiprocedure : listaProcedure) {
	    if (!tipiprocedure.getTipiProcedureavvios().isEmpty()) {
		risultato.add(tipiprocedure);
	    }
	}
	return risultato;
    }

    //    private Integer controllaCancellaOggettiModelliPrecompilati(Tipiprocedure entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggettoModelloPrecompilato())) {
    //	    if (!(null == entity.getOggettoModelloPrecompilato().getId())) {
    //		if (!(null == entity.getOggettoModelloPrecompilato().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggettoModelloPrecompilato().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Tipiprocedure entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggettoModelloPrecompilato())) {
    //		    if (!(null == entityCopy.getOggettoModelloPrecompilato().getId())) {
    //			if (!(null == entityCopy.getOggettoModelloPrecompilato().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggettoModelloPrecompilato().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    //    private Integer controllaCancellaOggettiModelli(Tipiprocedure entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggettoModello())) {
    //	    if (!(null == entity.getOggettoModello().getId())) {
    //		if (!(null == entity.getOggettoModello().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggettoModello().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Tipiprocedure entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggettoModello())) {
    //		    if (!(null == entityCopy.getOggettoModello().getId())) {
    //			if (!(null == entityCopy.getOggettoModello().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggettoModello().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    //    private Integer controllaCancellaOggettoModelloDomandaOnline(Tipiprocedure entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggettoModelloDomandaOnline())) {
    //	    if (!(null == entity.getOggettoModelloDomandaOnline().getId())) {
    //		if (!(null == entity.getOggettoModelloDomandaOnline().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggettoModelloDomandaOnline().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Tipiprocedure entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggettoModelloDomandaOnline())) {
    //		    if (!(null == entityCopy.getOggettoModelloDomandaOnline().getId())) {
    //			if (!(null == entityCopy.getOggettoModelloDomandaOnline().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggettoModelloDomandaOnline().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    //    private Integer controllaCancellaOggettoModelloDiagramma(Tipiprocedure entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggettoModelloDiagramma())) {
    //	    if (!(null == entity.getOggettoModelloDiagramma().getId())) {
    //		if (!(null == entity.getOggettoModelloDiagramma().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggettoModelloDiagramma().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Tipiprocedure entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggettoModelloDiagramma())) {
    //		    if (!(null == entityCopy.getOggettoModelloDiagramma().getId())) {
    //			if (!(null == entityCopy.getOggettoModelloDiagramma().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggettoModelloDiagramma().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE", "CODICEPROCEDURA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    protected boolean isDeleteAllowed(Tipiprocedure entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!tipicontromovimentoService.findByTipiprocedure(entity.getId().getCodice(), 0, 2).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "TIPICONTROMOVIMENTO", null));
	}
	if (!alberoprocService.findByTipiprocedure(entity.getId().getCodice(), 0, 2).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
	}
	List<Istanze> istanzes = istanzeService.findByProcedure(entity, 0, 2);
	if (!istanzes.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZE", null));
	}
	if (!natureProcedureService.findByTipiprocedure(entity.getId().getCodice(), 0, 2).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NATURE_PROCEDURE", null));
	}
	if (!alberoprocGruppiSmistService.findByprocedura(entity.getId().getCodice(), 0, 1).isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC_GRUPPI_SMIST", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /**
     * Ricerca per descrizione {@link TipiprocedureService#findByDescrizione(String)}
     * 
     * 
     */
    @Override
    protected Tipiprocedure customBindDomainObject(Tipiprocedure entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isNotBlank(entity.getProcedura())) {
	    List<Tipiprocedure> procedures = findByDescrizione(entity.getProcedura());
	    if (procedures.size() == 1) {
		return procedures.get(0);
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }

    protected void childDelete(Tipiprocedure entity) {

	// Tempirisposta
	Set<Tempirisposta> tempirispostas = entity.getTempirispostas();
	for (Tempirisposta tempirisposta : tempirispostas) {
	    tempirispostaService.delete(tempirisposta);
	}
	// TipiprocedureDyn2modellit
	Set<TipiprocedureDyn2modellit> tipiprocedureDyn2modellits = entity.getTipiprocedureDyn2modellits();
	for (TipiprocedureDyn2modellit tipiprocedureDyn2modellit : tipiprocedureDyn2modellits) {
	    tipiprocedureDyn2modellitService.delete(tipiprocedureDyn2modellit);
	}
	// Tipiprocedureavvio
	Set<Tipiprocedureavvio> tipiprocedureavvios = entity.getTipiProcedureavvios();
	for (Tipiprocedureavvio tipiprocedureavvio : tipiprocedureavvios) {
	    tipiprocedureavvioService.delete(tipiprocedureavvio);
	}
	// Impiantiprocedure
	Set<Impiantiprocedure> impiantiprocedures = entity.getImpiantiprocedures();
	for (Impiantiprocedure impiantiprocedure : impiantiprocedures) {
	    impiantiprocedureService.delete(impiantiprocedure);
	}
	// Subprocedure
	Set<Subprocedure> subprocedures = entity.getSubprocedures();
	for (Subprocedure subprocedure : subprocedures) {
	    subprocedureService.delete(subprocedure);
	}
	// TipiprocedureDocumenti
	Set<TipiprocedureDocumenti> tipiprocedureDocumentis = entity.getTipiprocedureDocumentis();
	for (TipiprocedureDocumenti tipiprocedureDocumenti : tipiprocedureDocumentis) {
	    tipiprocedureDocumentiService.delete(tipiprocedureDocumenti);
	}
    }

    @Override
    public List<Tipiprocedure> findByMovimetoDeterminazioneDataValiditaIstanza(String tipomovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, "tipimovimentoDeterminazione", String.class));
	ft.addRestriction(fr);
	return tipiprocedureDAO.findByFilterTable(ft);
    }

    @Override
    public List<Tipiprocedure> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("letteretipoId", letteretipo.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("procedura"));
	return tipiprocedureDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public boolean checkSeDisabilitare(Tipiprocedure tipiprocedure) {

	List<Alberoproc> procs = alberoprocService.findByTipiprocedure(tipiprocedure.getId().getCodice(), 0, 2);
	if (procs.size() > 0) {
	    return false;
	}
	List<Tipicontromovimento> tcs = tipicontromovimentoService.findByTipiprocedure(tipiprocedure.getId().getCodice(), 0, 2);
	if (tcs.size() > 0) {
	    return false;
	}
	return true;
    }

    @Override
    public List<Tipiprocedure> findByTuttiCampiTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	//	    private Tipimovimento tipimovimentoEsitoAut;
	fr.addFilterField(FilterUtils.equals("tipimovimentoEsitoAutId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoChiusura;
	fr.addFilterField(FilterUtils.equals("tipimovimentoChiusuraId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoChiusuraContraddittorio;
	fr.addFilterField(FilterUtils.equals("tipimovimentoChiusuraContraddittorioId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoCds;
	fr.addFilterField(FilterUtils.equals("tipimovimentoCdsId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoIntegrazioneDocumentale;
	fr.addFilterField(FilterUtils.equals("tipimovimentoIntegrazioneDocumentaleId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoAudizioneContraddittorio;
	fr.addFilterField(FilterUtils.equals("tipimovimentoAudizioneContraddittorioId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoChiusuraCds;
	fr.addFilterField(FilterUtils.equals("tipimovimentoChiusuraCdsId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoSospensione;
	fr.addFilterField(FilterUtils.equals("tipimovimentoSospensioneId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoPubblicita;
	fr.addFilterField(FilterUtils.equals("tipimovimentoPubblicitaId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoDeterminazione;
	fr.addFilterField(FilterUtils.equals("tipimovimentoDeterminazioneId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoConsiglioDeiMinistri;
	fr.addFilterField(FilterUtils.equals("tipimovimentoConsiglioDeiMinistriId", tipomovimento, String.class));
	//	    private Tipimovimento tipimovimentoTrasmissioneNegativa;
	fr.addFilterField(FilterUtils.equals("tipimovimentoTrasmissioneNegativaId", tipomovimento, String.class));
	ft.addOrder(FilterUtils.orderAsc("ordine", "software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "software"));
	ft.addOrder(FilterUtils.orderAsc("procedura"));
	ft.addRestriction(fr);
	return tipiprocedureDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
