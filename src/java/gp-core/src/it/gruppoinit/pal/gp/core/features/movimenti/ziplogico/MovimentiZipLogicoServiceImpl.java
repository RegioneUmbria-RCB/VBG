package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestataId;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;
import it.gruppoinit.pal.gp.core.service.helper.ZipLogicoLinkHelper;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;

@Service
public class MovimentiZipLogicoServiceImpl extends BaseServiceImpl implements MovimentiZipLogicoService {

    private static final Logger log = LoggerFactory.getLogger(MovimentiZipLogicoServiceImpl.class);
    private static final String MSG_NOT_DOCUMENT_SELECTED = "Selezionare almeno un documento per eseguire l'operazione ";
    private ZipLogicoDAO zipLogicoDAO;
    private MovimentiZipLogicoTestataDAO movimentiZipLogicoTestataDAO;
    private DocumentiHelperService documentiHelperService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private MovimentiallegatiService movimentiAllegatiService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeallegatiService istanzeAllegatiService;
    private AnagrafedocumentiService anagrafeDocumentiService;
    private IstanzeprocureService istanzeProcureService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private CdsattiService cdsattiService;
    private CdsService cdsService;
    private UserSecurityService userSecurityService;
    private OggettiService oggettiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setMovimentiZipLogicoTestataDAO(MovimentiZipLogicoTestataDAO movimentiZipLogicoTestataDAO) {

	this.movimentiZipLogicoTestataDAO = movimentiZipLogicoTestataDAO;
    }

    @Autowired
    public void setZipLogicoDAO(ZipLogicoDAO zipLogicoDAO) {

	this.zipLogicoDAO = zipLogicoDAO;
    }

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setMovimentiAllegatiService(MovimentiallegatiService movimentiAllegatiService) {

	this.movimentiAllegatiService = movimentiAllegatiService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setIstanzeAllegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeAllegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setAnagrafeDocumentiService(AnagrafedocumentiService anagrafeDocumentiService) {

	this.anagrafeDocumentiService = anagrafeDocumentiService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeProcureService) {

	this.istanzeProcureService = istanzeProcureService;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }

    @Autowired
    public void setCdsattiService(CdsattiService cdsattiService) {

	this.cdsattiService = cdsattiService;
    }

    @Autowired
    public void setCdsService(CdsService cdsService) {

	this.cdsService = cdsService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getEntityClass() {

	return null;
    }

    @Override
    public List<MovimentiZipLogico> findAll(Integer firstResult, Integer maxResult) {

	return this.zipLogicoDAO.findAllDettagli(firstResult, maxResult);
    }

    @Override
    public void insertDettaglio(MovimentiZipLogico entity) {

	this.zipLogicoDAO.insertDettaglio(entity);
	this.loggaZipLogicoActions(entity, false);
    }

    public void insertTestata(MovimentiZipLogicoTestataHelper testata) {

	this.zipLogicoDAO.insertTestata(testata);
    }

    @Override
    public MovimentiZipLogico findById(PkId id) {

	return this.zipLogicoDAO.getMovimentiZipLogicoById(id);
    }

    @Override
    public void updateDettaglio(MovimentiZipLogico entity) {

	this.zipLogicoDAO.updateDettaglio(entity);
    }

    @Override
    public void insertZipLogico(Integer codiceMovimento, DocumentiHelper documentiHelper) throws Exception {

	DocumentiHelper documentiSelezionati = documentiHelperService.findDocumentiInvioTrue(documentiHelper);
	if (!checkIfDocumentsAreSelected(documentiSelezionati)) {
	    throw new Exception(MSG_NOT_DOCUMENT_SELECTED + "di inserimento.");
	} else {
	    this.insertTestata(new MovimentiZipLogicoTestataHelper(codiceMovimento));
	    this.populateZipLogicoWithAllDocuments(codiceMovimento, documentiSelezionati);
	}
    }

    private void populateZipLogicoWithAllDocuments(Integer codiceMovimento, DocumentiHelper documentiHelper) {

	this.populateZipLogicoWithMovimentiAllegatiMovimentoCorrente(codiceMovimento, documentiHelper.getDocumentiMovimentoList());
	this.populateZipLogicoWithMovimentiAllegatiAltriMovimenti(codiceMovimento, documentiHelper.getDocumentiAltriMovimentiList());
	this.populateZipLogicoWithDocumentiistanza(codiceMovimento, documentiHelper.getDocumentiIstanzaList());
	this.populateZipLogicoWithIstanzeAllegati(codiceMovimento, documentiHelper.getDocumentiEndoprocedimentiList());
	this.populateZipLogicoWithAnagrafeDocumenti(codiceMovimento, documentiHelper.getDocumentiAnagrafeList());
	this.populateZipLogicoWithIstanzeprocure(codiceMovimento, documentiHelper.getIstanzeprocureList());
	this.populateZipLogicoWithCdsatti(codiceMovimento, documentiHelper.getCdsattiList());
    }

    private void populateZipLogicoWithMovimentiAllegatiMovimentoCorrente(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listMovimentiDTO) {

	if (!listMovimentiDTO.isEmpty()) {
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : listMovimentiDTO) {
		List<MovimentiallegatiDTO> movimentiAllegatiDTO = chiaveValoreBean.getValore();
		for (MovimentiallegatiDTO movDTO : movimentiAllegatiDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Movimentiallegati movAllegati = movimentiAllegatiService.findById(new PkId(movDTO.getId().getCodice()));
		    entity.setOggetti(movAllegati.getOggetto());
		    entity.setMovimentiallegati(movAllegati);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug(
			    "populateZipLogicoWithMovimentiAllegatiMovimentoCorrente# è stato inserito, in data = {}, l'allegato del movimento = {} con id = {} in MovimentiZipLogico",
			    new Object[] { new Date(), movAllegati.getDescrizione(), movAllegati.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithMovimentiAllegatiAltriMovimenti(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listMovimentiDTO) {

	if (!listMovimentiDTO.isEmpty()) {
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : listMovimentiDTO) {
		List<MovimentiallegatiDTO> movimentiAllegatiDTO = chiaveValoreBean.getValore();
		for (MovimentiallegatiDTO movDTO : movimentiAllegatiDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Movimentiallegati movAllegati = movimentiAllegatiService.findById(new PkId(movDTO.getId().getCodice()));
		    entity.setOggetti(movAllegati.getOggetto());
		    entity.setMovimentiallegati(movAllegati);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug(
			    "populateZipLogicoWithMovimentiAllegatiAltriMovimenti# è stato inserito, in data = {}, l'allegato del movimento = {} con id = {} in MovimentiZipLogico",
			    new Object[] { new Date(), movAllegati.getDescrizione(), movAllegati.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithDocumentiistanza(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList) {

	if (!documentiIstanzaList.isEmpty()) {
	    for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : documentiIstanzaList) {
		List<DocumentiistanzaDTO> documentiIstDTOList = chiaveValoreBean.getValore();
		for (DocumentiistanzaDTO docIstDTO : documentiIstDTOList) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Documentiistanza documentoIstanza = documentiistanzaService.findById(new PkId(docIstDTO.getId().getCodice()));
		    entity.setOggetti(documentoIstanza.getOggetto());
		    entity.setDocumentiistanza(documentoIstanza);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug("populateZipLogicoWithDocumentiistanza# è stato inserito in data = {} l'allegato dell'istanza = {} con id = {} ",
			    new Object[] { new Date(), docIstDTO.getDocumento(), docIstDTO.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithIstanzeAllegati(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoList) {

	if (!documentiEndoList.isEmpty()) {
	    for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : documentiEndoList) {
		List<IstanzeallegatiDTO> istAllegatiDTO = chiaveValoreBean.getValore();
		for (IstanzeallegatiDTO documentoEndoDTO : istAllegatiDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Istanzeallegati istanzaAllegato = istanzeAllegatiService.findById(new PkId(documentoEndoDTO.getId().getCodice()));
		    entity.setOggetti(istanzaAllegato.getOggetto());
		    entity.setIstanzeallegati(istanzaAllegato);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug(
			    "populateZipLogicoWithIstanzeAllegati# è stato inserito in data = {} l'allegato dell'endoprocedimento = {} con id = {} ",
			    new Object[] { new Date(), documentoEndoDTO.getAllegatoextra(), documentoEndoDTO.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithAnagrafeDocumenti(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList) {

	if (!documentiAnagrafeList.isEmpty()) {
	    for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : documentiAnagrafeList) {
		List<AnagrafedocumentiDTO> anagrafeDocumentiDTO = chiaveValoreBean.getValore();
		for (AnagrafedocumentiDTO anagrafeDocumentoDTO : anagrafeDocumentiDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Anagrafedocumenti anagrafedocumenti = anagrafeDocumentiService.findById(new PkId(anagrafeDocumentoDTO.getId().getCodice()));
		    entity.setOggetti(anagrafedocumenti.getOggetto());
		    entity.setAnagrafedocumenti(anagrafedocumenti);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug("populateZipLogicoWithAnagrafeDocumenti# è stato inserito in data = {} l'allegato dell'angrafica = {} con id = {} ",
			    new Object[] { new Date(), anagrafeDocumentoDTO.getDocumento(), anagrafeDocumentoDTO.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithIstanzeprocure(Integer codiceMovimento,
	    List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzeprocureList) {

	if (!istanzeprocureList.isEmpty()) {
	    for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : istanzeprocureList) {
		List<IstanzeprocureDTO> istanzeProcureDTO = chiaveValoreBean.getValore();
		for (IstanzeprocureDTO istProcDTO : istanzeProcureDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codiceMovimento);
		    Istanzeprocure istanzeprocure = istanzeProcureService.findById(new PkId(istProcDTO.getId().getCodice()));
		    entity.setOggetti(istanzeprocure.getOggetti());
		    entity.setIstanzeprocure(istanzeprocure);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug("populateZipLogicoWithIstanzeprocure# è stato inserito in data = {} l'allegato della procura = {} con id = {} ",
			    new Object[] { new Date(), istProcDTO.getNomeFile(), istProcDTO.getId().getCodice() });
		}
	    }
	}
    }

    private void populateZipLogicoWithCdsatti(Integer codicemovimento, List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattiList) {

	if (!cdsattiList.isEmpty()) {
	    for (ChiaveValoreBean<String, List<CdsattiDTO>> chiaveValoreBean : cdsattiList) {
		List<CdsattiDTO> cdsattiDTO = chiaveValoreBean.getValore();
		for (CdsattiDTO cdaDTO : cdsattiDTO) {
		    MovimentiZipLogico entity = newMovimentiZipLogico(codicemovimento);
		    Cdsatti cdsatti = cdsattiService.findById(new PkId(cdaDTO.getId().getCodice()));
		    entity.setOggetti(cdsatti.getOggetti());
		    entity.setCdsatti(cdsatti);
		    this.zipLogicoDAO.insertDettaglio(entity);
		    log.debug("populateZipLogicoWithCdsatti# è stato inserito in data = {} l'allegato del cds = {} con id = {} ",
			    new Object[] { new Date(), cdaDTO.getFileverbale(), cdaDTO.getId().getCodice() });
		}
	    }
	}
    }

    private MovimentiZipLogico newMovimentiZipLogico(Integer codiceMovimento) {

	MovimentiZipLogico returnValue = new MovimentiZipLogico();
	returnValue.setMovimenti(getMovimento(codiceMovimento));
	return returnValue;
    }

    private Movimenti getMovimento(Integer codiceMovimento) {

	return movimentiNoSecurityService.findById(new PkId(codiceMovimento));
    }

    @Override
    public Set<MovimentiZipLogico> findByMovimento(Integer codicemovimento) {

	return this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
    }

    @Override
    public Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath) {

	return this.zipLogicoDAO.isDocumentoPresenteInZipLogico(codiceZipLogico, codiceMovimento, codiceDocumento, associationPath);
    }

    @Override
    public Boolean isDocumentoPresenteInZipLogico(Integer codiceDocumento, String associationPath) {

	return this.isDocumentoPresenteInZipLogico(null, null, codiceDocumento, associationPath);
    }

    @Override
    public Boolean isZipLogicoExistInMovimento(Integer codicemovimento) {

	return this.zipLogicoDAO.isZipLogicoExistInMovimento(codicemovimento);
    }

    @Override
    public DocumentiHelper findDocumentiZipLogicoToDisplay(Integer codicemovimento) {

	return newDocumentiHelper(codicemovimento);
    }

    private DocumentiHelper newDocumentiHelper(Integer codicemovimento) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	MovimentiZipLogicoTestataHelper testata = this.findByCodiceMovimento(codicemovimento);
	if (testata != null) {
	    documentiHelper.setGuidZipLogico(testata.getGuid());
	    ///
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiistanzas = this.populateDocumentiIstanza(codicemovimento);
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> movimentiallegatis = this.populateMovimentiallegati(codicemovimento);
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> istanzeallegatis = this.populateDocumentiEndoprocedimenti(codicemovimento);
	    List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzeprocures = this.populateDocumentiProcure(codicemovimento);
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> anagrafedocumentis = this.populateDocumentiAnagrafe(codicemovimento);
	    List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattis = this.populateCdsatti(codicemovimento);
	    ///
	    documentiHelper.setDocumentiIstanzaList(documentiistanzas);
	    documentiHelper.setDocumentiAltriMovimentiList(movimentiallegatis);
	    documentiHelper.setDocumentiEndoprocedimentiList(istanzeallegatis);
	    documentiHelper.setIstanzeprocureList(istanzeprocures);
	    documentiHelper.setDocumentiAnagrafeList(anagrafedocumentis);
	    documentiHelper.setCdsattiList(cdsattis);
	    ///
	}
	return documentiHelper;
    }

    private List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> populateDocumentiIstanza(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
	List<DocumentiistanzaDTO> documentiistanzas = new ArrayList<DocumentiistanzaDTO>();
	Istanze istanza = getMovimento(codicemovimento).getIstanza();
	Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	List<DocumentiistanzaDTO> documentiistanzaDTO = documentiistanzaService.findDocumentiistanzaDTOByIstanza(istanza.getId().getCodice(), null,
		true);
	if (!movimentiZipLogico.isEmpty()) {
	    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
		if (!documentiistanzaDTO.isEmpty()) {
		    for (DocumentiistanzaDTO docistDTO : documentiistanzaDTO) {
			if (EntityUtils.getNestedProperty(movZipLogico.getDocumentiistanza(), "id.codice") != null
				&& movZipLogico.getDocumentiistanza().getId().getCodice().equals(docistDTO.getId().getCodice())
				&& movZipLogico.getDocumentiistanza().getOggetto().getId().getCodice().equals(docistDTO.getCodiceOggetto())) {
			    documentiistanzas.add(docistDTO);
			}
		    }
		}
	    }
	}
	if (!documentiistanzas.isEmpty()) {
	    ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = new ChiaveValoreBean<String, List<DocumentiistanzaDTO>>();
	    bean.setChiave(istanza.getNumeroistanza());
	    bean.setValore(documentiistanzas);
	    beans.add(bean);
	}
	return beans;
    }

    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> populateMovimentiallegati(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	Movimenti movimento = getMovimento(codicemovimento);
	Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	Istanze istanza = movimento.getIstanza();
	List<Movimenti> movimentiistanzas = movimentiNoSecurityService.findEseguitiByIstanza(istanza);
	if (!movimentiistanzas.isEmpty() && !movimentiZipLogico.isEmpty()) {
	    for (Movimenti movimentoistanza : movimentiistanzas) {
		List<MovimentiallegatiDTO> allegatimovimentoDTO = movimentiAllegatiService
			.findMovimentiallegatiDTOByMovimenti(movimentoistanza.getId().getCodice());
		List<MovimentiallegatiDTO> movimentiallegatis = new ArrayList<MovimentiallegatiDTO>();
		if (!allegatimovimentoDTO.isEmpty()) {
		    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			for (MovimentiallegatiDTO allegatomovimentoDTO : allegatimovimentoDTO) {
			    if (movimentoistanza.getId().getCodice().equals(allegatomovimentoDTO.getCodiceMovimento())) {
				if (EntityUtils.getNestedProperty(movZipLogico.getMovimentiallegati(), "id.codice") != null) {
				    if (movZipLogico.getMovimentiallegati().getMovimento().getId().getCodice()
					    .equals(movimentoistanza.getId().getCodice())
					    && (movZipLogico.getMovimentiallegati().getId().getCodice().equals(
						    allegatomovimentoDTO.getId().getCodice()) && allegatomovimentoDTO.getCodiceOggetto() != null)) {
					movimentiallegatis.add(allegatomovimentoDTO);
				    }
				}
			    }
			}
		    }
		}
		if (!movimentiallegatis.isEmpty()) {
		    ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean = new ChiaveValoreBean<String, List<MovimentiallegatiDTO>>();
		    bean.setChiave(movimentoistanza.getDescrizioneMovimento());
		    bean.setValore(movimentiallegatis);
		    beans.add(bean);
		}
	    }
	}
	return beans;
    }

    private List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> populateDocumentiEndoprocedimenti(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
	Istanze istanza = getMovimento(codicemovimento).getIstanza();
	List<Istanzeprocedimenti> istanzeprocedimenti = istanzeprocedimentiService.findByIstanze(istanza.getId().getCodice());
	if (!istanzeprocedimenti.isEmpty()) {
	    for (Istanzeprocedimenti procedimento : istanzeprocedimenti) {
		Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
		List<IstanzeallegatiDTO> istanzeallegatisDTO = istanzeAllegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(
			istanza.getId().getCodice(), procedimento.getId().getCodiceinventario(), TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
		List<IstanzeallegatiDTO> istanzeallegatis = new ArrayList<IstanzeallegatiDTO>();
		if (!movimentiZipLogico.isEmpty()) {
		    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			if (!istanzeallegatisDTO.isEmpty()) {
			    for (IstanzeallegatiDTO istanzaallegatoDTO : istanzeallegatisDTO) {
				if (EntityUtils.getNestedProperty(movZipLogico.getIstanzeallegati(), "id.codice") != null
					&& movZipLogico.getIstanzeallegati().getId().getCodice().equals(istanzaallegatoDTO.getId().getCodice())) {
				    istanzeallegatis.add(istanzaallegatoDTO);
				}
			    }
			}
		    }
		}
		if (!istanzeallegatis.isEmpty()) {
		    ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
		    bean.setChiave(procedimento.getDescrizioneAndAmministrazione());
		    bean.setValore(istanzeallegatis);
		    beans.add(bean);
		}
	    }
	}
	return beans;
    }

    private List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> populateDocumentiProcure(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<IstanzeprocureDTO>>>();
	Integer codiceistanza = getMovimento(codicemovimento).getIstanza().getId().getCodice();
	List<IstanzeprocureDTO> istanzeprocures = new ArrayList<IstanzeprocureDTO>();
	Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	List<IstanzeprocureDTO> istanzeprocureDTO = istanzeProcureService.findIstanzeprocureDTOByIstanza(codiceistanza,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
	if (!movimentiZipLogico.isEmpty()) {
	    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
		if (!istanzeprocureDTO.isEmpty()) {
		    for (IstanzeprocureDTO istanzaprocure : istanzeprocureDTO) {
			if (EntityUtils.getNestedProperty(movZipLogico.getIstanzeprocure(), "id.codice") != null
				&& movZipLogico.getIstanzeprocure().getId().getCodice().equals(istanzaprocure.getId().getCodice())) {
			    istanzeprocures.add(istanzaprocure);
			}
		    }
		}
	    }
	    if (!istanzeprocures.isEmpty()) {
		ChiaveValoreBean<String, List<IstanzeprocureDTO>> bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
		bean.setChiave("");
		bean.setValore(istanzeprocures);
		beans.add(bean);
	    }
	}
	return beans;
    }

    private List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> populateDocumentiAnagrafe(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>>();
	List<AnagrafedocumentiDTO> listDocRichiedente = new ArrayList<AnagrafedocumentiDTO>();
	List<AnagrafedocumentiDTO> listDocAziendaRichiedente = new ArrayList<AnagrafedocumentiDTO>();
	List<AnagrafedocumentiDTO> listDocProfessionista = new ArrayList<AnagrafedocumentiDTO>();
	Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	///
	if (!movimentiZipLogico.isEmpty()) {
	    Istanze istanza = getMovimento(codicemovimento).getIstanza();
	    Anagrafe richiedente = istanza.getRichiedente();
	    Integer codiceRichiedente = richiedente.getId().getCodice();
	    ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
	    List<AnagrafedocumentiDTO> listDocRichiedenteDTO = anagrafeDocumentiService.findByIstanzaAndAnagrafeDTO(null, richiedente, true);
	    if (!listDocRichiedenteDTO.isEmpty()) {
		for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
		    for (AnagrafedocumentiDTO documentoanagrafe : listDocRichiedenteDTO) {
			if (EntityUtils.getNestedProperty(movZipLogico.getAnagrafedocumenti(), "id.codice") != null
				&& movZipLogico.getAnagrafedocumenti().getId().getCodice().equals(documentoanagrafe.getId().getCodice())
				&& documentoanagrafe.getCodiceOggetto() != null) {
			    listDocRichiedente.add(documentoanagrafe);
			}
		    }
		}
	    }
	    if (!listDocRichiedente.isEmpty()) {
		bean.setChiave("<b>Richiedente: </b>" + listDocRichiedenteDTO.get(0).getTransientTipoSoggettoAndRichiedente());
		bean.setValore(listDocRichiedente);
		beans.add(bean);
	    }
	    ///
	    // Azienda Richiedente
	    ///
	    if (EntityUtils.getNestedProperty(istanza.getTitolarelegale(), "id.codice") != null) {
		Anagrafe aziendaRichiedente = istanza.getTitolarelegale();
		List<AnagrafedocumentiDTO> listDocAziendaRichiedenteDTO = anagrafeDocumentiService.findByIstanzaAndAnagrafeDTO(null,
			aziendaRichiedente, true);
		if (!listDocAziendaRichiedenteDTO.isEmpty()) {
		    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			for (AnagrafedocumentiDTO anagrafeDocRichiedente : listDocAziendaRichiedenteDTO) {
			    if (EntityUtils.getNestedProperty(movZipLogico.getAnagrafedocumenti(), "id.codice") != null
				    && movZipLogico.getAnagrafedocumenti().getId().getCodice().equals(anagrafeDocRichiedente.getId().getCodice())
				    && anagrafeDocRichiedente.getCodiceOggetto() != null) {
				listDocAziendaRichiedente.add(anagrafeDocRichiedente);
			    }
			}
		    }
		}
		if (!listDocAziendaRichiedente.isEmpty()) {
		    ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanAziendaRichiedente = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		    beanAziendaRichiedente
			    .setChiave("<b>Azienda Richiedente: </b>" + listDocAziendaRichiedenteDTO.get(0).getTransientTipoSoggettoAndRichiedente());
		    beanAziendaRichiedente.setValore(listDocAziendaRichiedente);
		    beans.add(beanAziendaRichiedente);
		}
	    }
	    ///
	    // Azienda Professionista
	    ///
	    if (EntityUtils.getNestedProperty(istanza.getProfessionista(), "id.codice") != null) {
		Anagrafe professionista = istanza.getProfessionista();
		List<AnagrafedocumentiDTO> listDocProfessionistaDTO = anagrafeDocumentiService.findByIstanzaAndAnagrafeDTO(null, professionista,
			true);
		if (!listDocProfessionistaDTO.isEmpty()) {
		    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			for (AnagrafedocumentiDTO anagrafeDocProfessionista : listDocProfessionistaDTO) {
			    if (EntityUtils.getNestedProperty(movZipLogico.getAnagrafedocumenti(), "id.codice") != null) {
				if (movZipLogico.getAnagrafedocumenti().getId().getCodice().equals(anagrafeDocProfessionista.getId().getCodice())
					&& anagrafeDocProfessionista.getCodiceOggetto() != null) {
				    listDocProfessionista.add(anagrafeDocProfessionista);
				}
			    }
			}
		    }
		}
		if (!listDocProfessionista.isEmpty()) {
		    ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanProfessionista = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		    beanProfessionista = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		    beanProfessionista.setChiave("<b>Intermediario: </b>" + listDocProfessionistaDTO.get(0).getTransientTipoSoggettoAndRichiedente());
		    beanProfessionista.setValore(listDocProfessionista);
		    beans.add(beanProfessionista);
		}
	    }
	    ///
	    // Soggetti collegati all'istanza
	    ///
	    List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanza);
	    for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
		List<AnagrafedocumentiDTO> listDocumenti = new ArrayList<AnagrafedocumentiDTO>();
		Integer codIstanzaRich = istanzerichiedenti.getRichiedente().getId().getCodice();
		if (!codiceRichiedente.equals(codIstanzaRich)) {
		    List<AnagrafedocumentiDTO> listDocumentiDTO = anagrafeDocumentiService.findByIstanzaAndAnagrafeDTO(null,
			    istanzerichiedenti.getRichiedente(), true);
		    if (!listDocumentiDTO.isEmpty()) {
			for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			    for (AnagrafedocumentiDTO document : listDocumentiDTO) {
				if (EntityUtils.getNestedProperty(movZipLogico.getAnagrafedocumenti(), "id.codice") != null
					&& movZipLogico.getAnagrafedocumenti().getId().getCodice().equals(document.getId().getCodice())
					&& document.getCodiceOggetto() != null) {
				    listDocumenti.add(document);
				}
			    }
			}
		    }
		    if (!listDocumenti.isEmpty()) {
			ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanDocumenti = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
			beanDocumenti.setChiave(istanzerichiedenti.getTransientTipoSoggettoAndRichiedente());
			beanDocumenti.setValore(listDocumenti);
			beans.add(beanDocumenti);
		    }
		}
	    }
	}
	return beans;
    }

    private List<ChiaveValoreBean<String, List<CdsattiDTO>>> populateCdsatti(Integer codicemovimento) {

	List<ChiaveValoreBean<String, List<CdsattiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<CdsattiDTO>>>();
	Set<MovimentiZipLogico> movimentiZipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	Istanze istanza = getMovimento(codicemovimento).getIstanza();
	List<Cds> cdss = cdsService.findByIstanza(istanza);
	if (!cdss.isEmpty()) {
	    for (Cds cds : cdss) {
		List<CdsattiDTO> cdsattiDTO = cdsattiService.findDTOByCds(cds.getId().getCodice(), true);
		List<CdsattiDTO> cdsatti = new ArrayList<CdsattiDTO>();
		///
		if (!cdsattiDTO.isEmpty() && !movimentiZipLogico.isEmpty()) {
		    for (MovimentiZipLogico movZipLogico : movimentiZipLogico) {
			for (CdsattiDTO cdaDTO : cdsattiDTO) {
			    if (EntityUtils.getNestedProperty(movZipLogico.getCdsatti(), "id.codice") != null
				    && movZipLogico.getCdsatti().getId().getCodice().equals(cdaDTO.getId().getCodice())) {
				cdsatti.add(cdaDTO);
			    }
			}
		    }
		}
		if (!cdsatti.isEmpty()) {
		    ChiaveValoreBean<String, List<CdsattiDTO>> bean = new ChiaveValoreBean<String, List<CdsattiDTO>>();
		    bean.setChiave(String.valueOf(cds.getId().getCodice()));
		    bean.setValore(cdsatti);
		    beans.add(bean);
		}
	    }
	}
	return beans;
    }

    @Override
    public List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento) {

	return zipLogicoDAO.findMovimentiZipLogicoDTOByMovimento(codicemovimento);
    }

    @Override
    public Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento) {

	return this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
    }

    @Override
    public void deleteDettagli(Integer codicemovimento, DocumentiHelper documentiHelper) throws Exception {

	DocumentiHelper documentiSelezionati = documentiHelperService.findDocumentiInvioTrue(documentiHelper);
	Set<MovimentiZipLogico> zipLogico = this.zipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
	if (!checkIfDocumentsAreSelected(documentiSelezionati)) {
	    throw new Exception(MSG_NOT_DOCUMENT_SELECTED + "di cancellazione.");
	} else {
	    deleteDocumentiistanzaDaZipLogico(zipLogico, documentiSelezionati.getDocumentiIstanzaList());
	    deleteMovimentiallegatiDaZipLogico(zipLogico, documentiSelezionati.getDocumentiAltriMovimentiList());
	    deleteAnagrafedocumentiDaZipLogico(zipLogico, documentiSelezionati.getDocumentiAnagrafeList());
	    deleteIstanzeprocureDaZipLogico(zipLogico, documentiSelezionati.getIstanzeprocureList());
	    deleteEndoprocedimentiDaZipLogico(zipLogico, documentiSelezionati.getDocumentiEndoprocedimentiList());
	    deleteCdsattiDaZipLogico(zipLogico, documentiSelezionati.getCdsattiList());
	}
	if (!this.zipLogicoDAO.existDettagliByCodiceMovimento(codicemovimento)) {
	    this.zipLogicoDAO.eliminaZipLogicoByCodiceMovimento(codicemovimento);
	}
    }

    private void deleteDocumentiistanzaDaZipLogico(Set<MovimentiZipLogico> zipLogico,
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean : valorebeanList) {
		List<DocumentiistanzaDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (DocumentiistanzaDTO docIstanza : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getDocumentiistanza(), "id.codice") != null) {
				Integer codiceDocIstanzaNelloZip = documentoZip.getDocumentiistanza().getId().getCodice();
				if (codiceDocIstanzaNelloZip.equals(docIstanza.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void deleteMovimentiallegatiDaZipLogico(Set<MovimentiZipLogico> zipLogico,
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean : valorebeanList) {
		List<MovimentiallegatiDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (MovimentiallegatiDTO movall : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getMovimentiallegati(), "id.codice") != null) {
				Integer codiceMovallegatoNelloZip = documentoZip.getMovimentiallegati().getId().getCodice();
				if (codiceMovallegatoNelloZip.equals(movall.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void deleteAnagrafedocumentiDaZipLogico(Set<MovimentiZipLogico> zipLogico,
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean : valorebeanList) {
		List<AnagrafedocumentiDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (AnagrafedocumentiDTO docanag : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getAnagrafedocumenti(), "id.codice") != null) {
				Integer codiceAnagrafedocNelloZip = documentoZip.getAnagrafedocumenti().getId().getCodice();
				if (codiceAnagrafedocNelloZip.equals(docanag.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void deleteIstanzeprocureDaZipLogico(Set<MovimentiZipLogico> zipLogico,
	    List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> bean : valorebeanList) {
		List<IstanzeprocureDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (IstanzeprocureDTO istproc : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getIstanzeprocure(), "id.codice") != null) {
				Integer codiceIstanzaprocuraNelloZip = documentoZip.getIstanzeprocure().getId().getCodice();
				if (codiceIstanzaprocuraNelloZip.equals(istproc.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void deleteEndoprocedimentiDaZipLogico(Set<MovimentiZipLogico> zipLogico,
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean : valorebeanList) {
		List<IstanzeallegatiDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (IstanzeallegatiDTO istall : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getIstanzeallegati(), "id.codice") != null) {
				Integer codiceIstallegatiNelloZip = documentoZip.getIstanzeallegati().getId().getCodice();
				if (codiceIstallegatiNelloZip.equals(istall.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void deleteCdsattiDaZipLogico(Set<MovimentiZipLogico> zipLogico, List<ChiaveValoreBean<String, List<CdsattiDTO>>> valorebeanList) {

	if (!zipLogico.isEmpty()) {
	    for (ChiaveValoreBean<String, List<CdsattiDTO>> bean : valorebeanList) {
		List<CdsattiDTO> list = bean.getValore();
		if (!list.isEmpty()) {
		    for (MovimentiZipLogico documentoZip : zipLogico) {
			for (CdsattiDTO cdsatti : list) {
			    if (EntityUtils.getNestedProperty(documentoZip.getCdsatti(), "id.codice") != null) {
				Integer codicecdsattiNelloZip = documentoZip.getCdsatti().getId().getCodice();
				if (codicecdsattiNelloZip.equals(cdsatti.getId().getCodice())) {
				    this.deleteDettaglio(documentoZip);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private void loggaZipLogicoActions(MovimentiZipLogico zipLogico, boolean isDelete) {

	Responsabili currentAuthenticatedUser = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String descrizioneRichiedente = "";
	if (currentAuthenticatedUser != null) {
	    descrizioneRichiedente = currentAuthenticatedUser.getResponsabile() + "[" + zipLogico.getMovimenti().getIstanza().getId().getCodice() +
				     "]";
	}
	String messaggio = messageForDocumentInZipLogico(zipLogico, descrizioneRichiedente, isDelete);
	if (isDelete) {
	    LoggerModificheIstanze.log("#DELETEZIPLOGICO#{}", new Object[] { messaggio });
	} else {
	    LoggerModificheIstanze.log("#INSERTZIPLOGICO#{}", new Object[] { messaggio });
	}
    }

    private String messageForDocumentInZipLogico(MovimentiZipLogico zipLogico, String descrizioneRichiedente, boolean isDelete) {

	String returnMessage = "";
	String startMessage = "";
	String eliminatoOInserito = (isDelete) ? "eliminato" : "inserito";
	if (StringUtils.isNotBlank(descrizioneRichiedente)) {
	    startMessage = "L'operatore " + descrizioneRichiedente + " ha " + eliminatoOInserito + " il documento ";
	} else {
	    startMessage = "E'stato " + eliminatoOInserito + " il documento ";
	}
	String preposizione = (isDelete) ? "dello" : "allo";
	String endMessage = " " + preposizione + " zip logico del movimento: " + zipLogico.getMovimenti().getMovimento() + " con codice: " +
			    zipLogico.getMovimenti().getId().getCodice();
	if (EntityUtils.getNestedProperty(zipLogico.getMovimentiallegati(), "id.codice") != null) {
	    Movimentiallegati movall = zipLogico.getMovimentiallegati();
	    if (movall.getOggetto() != null) {
		returnMessage += "con nome: [" + movall.getOggetto().getNomefile() + "] e ";
	    }
	    returnMessage = startMessage + "con descrizione: [" + movall.getDescrizione() + "]" + endMessage;
	}
	if (EntityUtils.getNestedProperty(zipLogico.getDocumentiistanza(), "id.codice") != null) {
	    Documentiistanza docIst = zipLogico.getDocumentiistanza();
	    returnMessage = startMessage + "con nome: [" + docIst.getOggetto().getNomefile() + "] e descrizione: [" + docIst.getDocumento() + "]" +
			    endMessage;
	}
	if (EntityUtils.getNestedProperty(zipLogico.getIstanzeallegati(), "id.codice") != null) {
	    Istanzeallegati istall = zipLogico.getIstanzeallegati();
	    returnMessage = startMessage + "con nome: [" + istall.getOggetto().getNomefile() + "] e descrizione: [" + istall.getAllegatoextra() +
			    "]" + endMessage;
	}
	if (EntityUtils.getNestedProperty(zipLogico.getIstanzeprocure(), "id.codice") != null) {
	    Istanzeprocure istProc = zipLogico.getIstanzeprocure();
	    String anagrafeProcuratore = (EntityUtils.getNestedProperty(istProc.getAnagrafeProcuratore(), "id.codice") != null)
		    ? istProc.getAnagrafeProcuratore().getDescrizioneRichiedente()
		    : "descrizione assente";
	    returnMessage = startMessage + "con nome: [" + istProc.getOggetti().getNomefile() + "] e descrizione: [" + anagrafeProcuratore + "]" +
			    endMessage;
	}
	if (EntityUtils.getNestedProperty(zipLogico.getAnagrafedocumenti(), "id.codice") != null) {
	    Anagrafedocumenti anagrafeDoc = zipLogico.getAnagrafedocumenti();
	    String anagrafica = (EntityUtils.getNestedProperty(anagrafeDoc.getAnagrafe(), "id.codice") != null)
		    ? anagrafeDoc.getAnagrafe().getDescrizioneRichiedente()
		    : "descrizione assente";
	    returnMessage = startMessage + "con nome: [" + anagrafeDoc.getOggetto().getNomefile() + "] e descrizione: [" + anagrafica + "]" +
			    endMessage;
	}
	if (EntityUtils.getNestedProperty(zipLogico.getCdsatti(), "id.codice") != null) {
	    Cdsatti cdsatti = zipLogico.getCdsatti();
	    returnMessage = startMessage + "con nome: [" + cdsatti.getOggetti().getNomefile() + "] e descrizione: [" + cdsatti.getFileverbale() +
			    "]" + endMessage;
	}
	return returnMessage;
    }

    private boolean checkIfDocumentsAreSelected(DocumentiHelper documentiHelper) {

	boolean result = false;
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> chiaveValoreBeanDocist = documentiHelper.getDocumentiIstanzaList();
	if (!chiaveValoreBeanDocist.isEmpty()) {
	    result = true;
	}
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> chiaveValoreBeanEndo = documentiHelper.getDocumentiEndoprocedimentiList();
	if (!chiaveValoreBeanEndo.isEmpty()) {
	    result = true;
	}
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> chiaveValoreBeanAltrimov = documentiHelper.getDocumentiAltriMovimentiList();
	if (!chiaveValoreBeanAltrimov.isEmpty()) {
	    result = true;
	}
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> chiaveValoreBeanIstproc = documentiHelper.getIstanzeprocureList();
	if (!chiaveValoreBeanIstproc.isEmpty()) {
	    result = true;
	}
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> chiaveValoreBeanAnagrafedoc = documentiHelper.getDocumentiAnagrafeList();
	if (!chiaveValoreBeanAnagrafedoc.isEmpty()) {
	    result = true;
	}
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> chiaveValoreBeanCdsatti = documentiHelper.getCdsattiList();
	if (!chiaveValoreBeanCdsatti.isEmpty()) {
	    result = true;
	}
	return result;
    }

    protected boolean isDeleteAllowed(MovimentiZipLogico entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void update(Object entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Object entity) {

	throw new NotImplementedException();
    }

    @Override
    public Object findById(Serializable id) {

	throw new NotImplementedException();
    }

    @Override
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento) {

	this.zipLogicoDAO.eliminaZipLogicoByCodiceMovimento(codiceMovimento);
    }

    @Override
    public void upgrCreaTestate() {

	String idComuneInSession = ORMHelper.getIdcomune();
	Set<MovimentiZipLogicoTestataHelper> elenco = this.zipLogicoDAO.recuperaTestateMancanti();
	for (MovimentiZipLogicoTestataHelper testataMancanteItem : elenco) {
	    ORMHelper.setIdcomune(testataMancanteItem.getIdComune());
	    this.insertTestata(testataMancanteItem);
	}
	ORMHelper.setIdcomune(idComuneInSession);
    }

    @Override
    public void insert(Object entity) {

	throw new NotImplementedException();
    }

    @Override
    public void deleteDettaglio(MovimentiZipLogico entity) {

	this.zipLogicoDAO.delete(entity);
    }

    @Override
    public MovimentiZipLogicoTestataHelper findByCodiceMovimento(Integer codiceMovimento) {

	return this.zipLogicoDAO.findByCodiceMovimento(codiceMovimento);
    }

    private MovimentiZipLogicoTestataHelper findByGuid(String guid) {

	MovimentiZipLogicoTestata testata = this.zipLogicoDAO.findByGuid(guid);
	return this.findByCodiceMovimento(testata.getId().getCodicemovimento());
    }

    @Override
    public void generaZipLogicoDaZipLogicoCollegato(Integer codiceMovimento, Set<Movimentiallegati> movimentiallegatis, String guidOrigine) {

	if (movimentiallegatis != null && !movimentiallegatis.isEmpty()) {
	    log.debug("inserisco la testata con guid di origine {}", guidOrigine);
	    this.insertTestata(new MovimentiZipLogicoTestataHelper(codiceMovimento, guidOrigine));
	    log.debug("Recupero la composizione dello zip logico padre con guid {}", guidOrigine);
	    MovimentiZipLogicoTestataHelper testataDiOrigine = this.findByGuid(guidOrigine);
	    log.debug("Composizione dello zip logico padre con guid {} recuperata. Popolo gli oggetti che appartengono al guid di origine",
		    guidOrigine);
	    Set<Integer> codiciOggetto = new HashSet<Integer>();
	    Set<MovimentiZipLogicoHelper> dettagliZipLogicoPadre = testataDiOrigine.getDettaglii();
	    for (MovimentiZipLogicoHelper mzh : dettagliZipLogicoPadre) {
		if (mzh.getCodiceOggetto() != null) {
		    codiciOggetto.add(mzh.getCodiceOggetto());
		}
	    }
	    log.debug("Composizione dello zip logico padre con guid {} recuperata. Gli oggetti che appartengono al guidi di origine sono {}",
		    guidOrigine, codiciOggetto);
	    for (Movimentiallegati allegato : movimentiallegatis) {
		log.debug("Verifico se l'allegato {} appartiene allo zip logico. Dovrebbero avere lo stesso codice oggetto ",
			allegato.getId().getCodice());
		if (allegato.getOggetto() != null && allegato.getOggetto().getId() != null
			&& codiciOggetto.contains(allegato.getOggetto().getId().getCodice())) {
		    log.debug("L'allegato {} appartiene allo zip logico. il codice oggetto è {} ", allegato.getId().getCodice(),
			    allegato.getOggetto().getId().getCodice());
		    this.insertDettaglio(new MovimentiZipLogico(allegato));
		}
	    }
	}
    }

    @Override
    public boolean checkIsModificabile(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Parametro codiceMovimento nullo");
	}
	Movimenti movimenti = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	if (movimenti == null) {
	    throw new IllegalArgumentException("Movimento con codice " + codiceMovimento + " non trovato");
	}
	if ((StringUtils.isNotBlank(movimenti.getNumeroprotocollo()) && movimenti.getDataprotocollo() != null)
		|| !(movimenti.getInviatoConStc() == null || movimenti.getInviatoConStc().equals(MovimentiBaseService.STC_NON_INVIATO))) {
	    return false;
	}
	return true;
    }

    @Override
    public Integer contaDocumenti(Integer codiceMovimento) {

	return this.zipLogicoDAO.contaDocumenti(codiceMovimento);
    }

    @Override
    public MovimentiZipLogicoTestata findTestataByCodiceMovimento(Integer codiceMovimento) {

	MovimentiZipLogicoTestataId id = new MovimentiZipLogicoTestataId(codiceMovimento);
	return this.movimentiZipLogicoTestataDAO.findById(id);
    }

    @Override
    public void updateDocAllegatoTestata(Integer codiceMovimento, Integer codiceOggettoDocAllegato) {

	MovimentiZipLogicoTestata entity = this.findTestataByCodiceMovimento(codiceMovimento);
	if (codiceOggettoDocAllegato != null && entity.getCodiceoggettoDocAll() != null) {
	    throw new BusinessValidationException("Lo zip logico ha già associato un documento con il link degli allegati");
	}
	entity.setCodiceoggettoDocAll(codiceOggettoDocAllegato);
	zipLogicoDAO.saveEntity(entity);
    }

    @Override
    public DocumentiHelper manageDocumentiZipLogico(Integer codiceMovimento, DocumentiHelper documentiHelper) {

	// TODO VERIFICARE CHE GIà NON ESISTANO
	DocumentiHelper dh2 = newDocumentiHelper(codiceMovimento);
	if (!dh2.getCdsattiList().isEmpty()) {
	    documentiHelper.getCdsattiList().addAll(dh2.getCdsattiList());
	}
	if (!dh2.getDocumentiAltriMovimentiList().isEmpty()) {
	    documentiHelper.getDocumentiAltriMovimentiList().addAll(dh2.getDocumentiAltriMovimentiList());
	}
	if (!dh2.getDocumentiAnagrafeList().isEmpty()) {
	    documentiHelper.getDocumentiAltriMovimentiList().addAll(dh2.getDocumentiAltriMovimentiList());
	}
	if (!dh2.getDocumentiEndoprocedimentiList().isEmpty()) {
	    documentiHelper.getDocumentiEndoprocedimentiList().addAll(dh2.getDocumentiEndoprocedimentiList());
	}
	if (!dh2.getDocumentiIstanzaList().isEmpty()) {
	    documentiHelper.getDocumentiIstanzaList().addAll(dh2.getDocumentiIstanzaList());
	}
	if (!dh2.getDocumentiMovimentoList().isEmpty()) {
	    documentiHelper.getDocumentiMovimentoList().addAll(dh2.getDocumentiMovimentoList());
	}
	if (!dh2.getIstanzeprocureList().isEmpty()) {
	    documentiHelper.getIstanzeprocureList().addAll(dh2.getIstanzeprocureList());
	}
	documentiHelper.setGuidZipLogico(dh2.getGuidZipLogico());
	return documentiHelper;
    }

    @Override
    public String insertOrGetSHA256(Integer codiceoggetto) {

	return this.oggettiService.insertOrGetSHA256(codiceoggetto);
    }

    @Override
    public Boolean isZipLogicoDocumentoAllegato(Integer codicemovimento) {

	return movimentiZipLogicoTestataDAO.isZipLogicoDocumentoAllegato(codicemovimento);
    }

    @Override
    public ZipLogicoLinkHelper creaLinkZipLogico(Integer codiceMovimento) {

	ZipLogicoLinkHelper ret = new ZipLogicoLinkHelper();
	Movimenti mov = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	String uuidIstanza = mov.getIstanza().getUuid();
	// Metodo esposto per recuperare il link
	// Verifico che sia attiva la verticalizzazione
	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	    log.error("creaLinkZipLogico# La verticalizzazione : {}. Non è attiva ", WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC);
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    ivs.add(new InvalidValue("service_error.verticalizzazione_allegati_pec_non_attiva", null, null, null, null));
	    return ret;
	}
	//Verifico la verticalizzazione contenete l'url del servizio per il recupero dei file fisici
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, WebConstants.VERTICALIZZAZIONE_PARAMETRI_URL_SERVIZIO_RECUPERO_DOC);
	Verticalizzazioniparametri senzapin = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC,
		WebConstants.VERTICALIZZAZIONE_PARAMETRI_ALLEGATI_PEC_DOWNLOAD_SENZA_PIN);
	String urlPath = "";
	boolean senzaPinBool = false;
	String parametriUrl = "getZipLogico.htm?a=" + ORMHelper.getIdcomuneAlias() + "&u=" + uuidIstanza + "&m={md5_richiesta}";
	if (senzapin != null) {
	    senzaPinBool = StringUtils.defaultString(senzapin.getValore(), "N").equalsIgnoreCase("S");
	    if (senzaPinBool) {
		parametriUrl = "viewZipLogico.htm?a=" + ORMHelper.getIdcomuneAlias() + "&u=" + uuidIstanza + "&m={md5_richiesta}&pin=" +
			       codiceMovimento;
	    }
	}
	if (verticalizzazioniparametri != null && StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
	    urlPath = verticalizzazioniparametri.getValore();
	    log.debug("creaLinkZipLogico# Url trovato : {}", urlPath);
	} else {
	    log.error(
		    "creaLinkZipLogico# Controllare la configurazione della verticalizzazione : {}. Non è stato impostato il path del servizio di recupero file ",
		    WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC);
	    List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	    ivs.add(new InvalidValue("service_error.configurazione_verticalizzazione_allegati_pec", null, null, null, null));
	    return ret;
	}
	String mac = macPerDownloadZipLogico(ORMHelper.getIdcomuneAlias(), uuidIstanza, String.valueOf(codiceMovimento));
	// Creao il path : urlServizio+urlMethodEsposto
	urlPath += parametriUrl.replace("{md5_richiesta}", mac);
	log.debug("creaLinkZipLogico# {}", urlPath);
	ret.setUrl(urlPath);
	ret.setPin(codiceMovimento);
	ret.setUsaPin(!senzaPinBool);
	return ret;
    }

    /**
     * md5 deve essere verificato con md5 di DigestUtils.md5Hex(alias + "-" + uuidistanza + "-" + codice_movimento_pin);
     * 
     * @param alias
     * @param uuidistanza
     * @param codiceMovimentoPin
     * @return
     * @throws UnsupportedEncodingException
     */
    private String macPerDownloadZipLogico(String alias, String uuidistanza, String codiceMovimentoPin) {

	String calcolato = new String((alias + "-" + uuidistanza + "-" + codiceMovimentoPin).getBytes());
	try {
	    calcolato = new String((alias + "-" + uuidistanza + "-" + codiceMovimentoPin).getBytes(), "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error("macPerDownloadZip {}", e);
	}
	return DigestUtils.md5Hex(calcolato);
    }

    @Override
    public NumeroDataProtocolloZipLogico getNumeroDataProtocolloZipLogico(MovimentiZipLogicoDTO movimentiZipLogicoDTO) {

	if (movimentiZipLogicoDTO == null) {
	    return new NumeroDataProtocolloZipLogico();
	}
	if (movimentiZipLogicoDTO.getCodiceMovimentiallegati() != null) {
	    // torno protocollo movimento
	    Movimentiallegati m = (Movimentiallegati) zipLogicoDAO.getById(Movimentiallegati.class,
		    new PkId(movimentiZipLogicoDTO.getCodiceMovimentiallegati()));
	    if (m != null) {
		return new NumeroDataProtocolloZipLogico(m.getMovimento().getNumeroprotocollo(), m.getMovimento().getDataprotocollo());
	    }
	} else if (movimentiZipLogicoDTO.getCodiceDocumentiistanza() != null || movimentiZipLogicoDTO.getCodiceIstanzeallegati() != null
		|| movimentiZipLogicoDTO.getCodiceIstanzeprocure() != null) {
	    // torno protocollo istanza
	    Movimenti m = (Movimenti) zipLogicoDAO.getById(Movimenti.class, new PkId(movimentiZipLogicoDTO.getCodiceMovimento()));
	    if (m != null) {
		return new NumeroDataProtocolloZipLogico(m.getIstanza().getNumeroprotocollo(), m.getIstanza().getDataprotocollo());
	    }
	}
	// per cdsatti  e istanzeprocure non torno riferimenti di protocollazione
	return new NumeroDataProtocolloZipLogico();
    }
}
