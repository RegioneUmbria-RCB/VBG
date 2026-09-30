package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.SituazioneAllegato;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Cds;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.TIpoDocumentoDocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.CdsService;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService.SceltaMovimentiEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareByCodiceOggettoReaderService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

@Service
public class DocumentiHelperServiceImpl implements DocumentiHelperService {

    private static final Logger log = LoggerFactory.getLogger(DocumentiHelperServiceImpl.class);
    private TipimovStcMappingService tipimovStcMappingService;
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;
    private AutorizzazioniService autorizzazioniService;
    private CdsattiService cdsattiService;
    private CdsService cdsService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    @Qualifier("compositeSoftwareByCodiceOggettoReader")
    private SoftwareByCodiceOggettoReaderService softwareByCodiceOggettoReaderService;

    private enum TipoFunzionalitaEnum {
	INVIO_EMAIL,
	INVIO_STC,
	INVIO_ZIP_LOGICO,
	DEFAULT
    }

    @Autowired
    public void setCdsService(CdsService cdsService) {

	this.cdsService = cdsService;
    }

    @Autowired
    public void setCdsattiService(CdsattiService cdsattiService) {

	this.cdsattiService = cdsattiService;
    }

    @Autowired
    public void setTipimovStcMappingService(TipimovStcMappingService tipimovStcMappingService) {

	this.tipimovStcMappingService = tipimovStcMappingService;
    }

    @Autowired
    public void setDocumentiAutorizzazioneService(DocumentiAutorizzazioneService documentiAutorizzazioneService) {

	this.documentiAutorizzazioneService = documentiAutorizzazioneService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    public void setSoftwareByCodiceOggettoReaderService(SoftwareByCodiceOggettoReaderService softwareByCodiceOggettoReaderService) {

	this.softwareByCodiceOggettoReaderService = softwareByCodiceOggettoReaderService;
    }

    /**
     * <pre>
     * La lista sarà composta da N elementi (quando sono gli endo associati all'istanza che hanno un documento con allegato )
     * dove:
     *    key 	: descrizione dell'endo
     *    lista	: list di documenti dell'endo dell'istanza
     * &#64;param codiceistanza
     * &#64;param tipoRicercaDocumentoEnum
     * &#64;return
     * </pre>
     */
    private List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> populateDocumentiEndoprocedimentiDTO(Integer codiceistanza,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	Integer codiceIstanza = istanza.getId().getCodice();
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
	// FIXME migliorabile cercando per codice istanza
	List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(istanza);
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    List<IstanzeallegatiDTO> ialls = istanzeallegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(codiceIstanza,
		    istanzeprocedimenti.getId().getCodiceinventario(), TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
	    ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
	    bean.setChiave(istanzeprocedimenti.getDescrizioneAndAmministrazione());
	    bean.setValore(ialls);
	    beans.add(bean);
	}
	return beans;
    }

    /**
     * <pre>
     * La lista sarà composta da N elementi (quando sono gli endo associati all'istanza che hanno un documento con allegato )
     * dove:
     *    key 	: descrizione dell'endo
     *    lista	: list di documenti dell'endo dell'istanza
     * se impostato il codice movimento allora controllerà se esiste una configurazione in TipimovStcMapping per tipomov e amminitrazione stc scelta, nel caso estista
     * se il flag TipimovStcMapping.flagAllegaDocumentiEndo == true i documenti saranno spuntati come da inviare
     *    
     * &#64;param codiceistanza
     * &#64;param codiceMovimento :opzionale, può essere NULL
     * &#64;param tipoRicercaDocumentoEnum
     * &#64;return
     * </pre>
     */
    private List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> populateDocumentiEndoprocedimenti(Integer codiceistanza, Integer codiceMovimento,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, TipoFunzionalitaEnum tipoFunzionalitEnum) {

	TipimovStcMapping stcMapping = null;
	Movimenti movimenti = null;
	Autorizzazioni autorizzazione = null;
	boolean _isFlgRegistroChecked = false;
	boolean _isZipLogicoExist = false;
	switch (tipoFunzionalitEnum) {
	case INVIO_EMAIL:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		if (BooleanUtils.toBoolean(movimenti.getTipomovimento().getFlagRegistro())) {
		    List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimenti.getIstanza(), movimenti);
		    if (!lAut.isEmpty()) {
			_isFlgRegistroChecked = true;
			autorizzazione = lAut.get(0);
		    }
		}
	    }
	case INVIO_STC:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		if (EntityUtils.getNestedProperty(movimenti.getAmministrazioniStc(), "id.codice") != null) {
		    log.debug(
			    "populateDocumentiEndoprocedimenti# E' stato popolato il campo codice movimento, la configurazione TipimovStcMapping per tipoMov {} e amministrazione {}",
			    new Object[] { movimenti.getTipomovimento().getId().getTipomovimento(),
				    movimenti.getAmministrazioniStc().getId().getCodice() });
		    stcMapping = new TipimovStcMapping();
		    stcMapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(
			    movimenti.getTipomovimento().getId().getTipomovimento(), movimenti.getAmministrazioniStc().getId().getCodice());
		}
	    }
	    break;
	case INVIO_ZIP_LOGICO:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		Set<MovimentiZipLogico> zipLogicos = movimentiZipLogicoService.findByMovimento(movimenti.getId().getCodice());
		if (!zipLogicos.isEmpty()) {
		    _isZipLogicoExist = true;
		}
	    }
	default:
	    break;
	}
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
	if (codiceistanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    Integer codiceIstanza = istanza.getId().getCodice();
	    Integer codiceEndo = null;
	    if (movimenti != null) {
		if (movimenti.getEndoprocedimento() != null) {
		    if (movimenti.getEndoprocedimento().getId() != null) {
			codiceEndo = movimenti.getEndoprocedimento().getId().getCodice();
		    }
		}
	    }
	    List<Istanzeprocedimenti> istanzeprocedimentis = istanzeprocedimentiService.findByIstanze(istanza);
	    for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
		List<IstanzeallegatiDTO> ialls = istanzeallegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(codiceIstanza,
			istanzeprocedimenti.getId().getCodiceinventario(), TipoRicercaDocumentoEnum.RICERCA_TUTTI);
		List<IstanzeallegatiDTO> _ialls = new ArrayList<IstanzeallegatiDTO>();
		boolean isAllegatoWithOggetto = false;
		for (IstanzeallegatiDTO istanzeallegati : ialls) {
		    if (istanzeallegati.getCodiceOggetto() != null) {
			isAllegatoWithOggetto = true;
			log.debug("populateDocumentiEndoprocedimenti# movimento genera aut ={}", _isFlgRegistroChecked);
			if (!_isFlgRegistroChecked) {
			    // Controllo se esiste la configurazione in TipimovStcMapping per tipo mov e amminitrazione stc scelta.
			    if (stcMapping != null) {
				log.debug(
					"populateDocumentiEndoprocedimenti# Configurazione TipimovStcMapping per tipoMov {} e amministrazione {} esistenete ",
					new Object[] { movimenti.getTipomovimento().getId().getTipomovimento(),
						movimenti.getAmministrazioniStc().getId().getCodice() });
				// Devo controllare se TipimovStcMapping.flagAllegaDocumentiEndo == true
				if (BooleanUtils.toBoolean(stcMapping.getFlagAllegaDocumentiEndo())
					|| istanzeprocedimenti.getId().getCodiceinventario().equals(codiceEndo)) {
				    if (verificaAllegatoValido(istanzeallegati.getControllook())) {
					istanzeallegati.setTransientSegnaPerInvio(true);
				    }
				}
			    }
			} else {
			    if (verificaAllegatoValido(istanzeallegati.getControllook())) {
				Boolean isExistInAutorizzazioni = documentiAutorizzazioneService.isInAutorizzazione(
					autorizzazione.getId().getCodice(), istanzeallegati.getId().getCodice(), "istanzeallegati");
				if (isExistInAutorizzazioni) {
				    istanzeallegati.setTransientSegnaPerInvio(true);
				}
			    }
			}
			_ialls.add(istanzeallegati);
		    }
		}
		if (isAllegatoWithOggetto) {
		    if (_isZipLogicoExist) {
			if (!_ialls.isEmpty()) {
			    List<IstanzeallegatiDTO> toRemove = listToRemoveFromEndoprocedimenti(codiceMovimento, _ialls);
			    if (!toRemove.isEmpty()) {
				_ialls.removeAll(toRemove);
				if (!_ialls.isEmpty()) {
				    ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = newChiaveValoreBeanEndo(
					    istanzeprocedimenti.getDescrizioneAndAmministrazione(), _ialls);
				    beans.add(bean);
				}
			    } else {
				ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = newChiaveValoreBeanEndo(
					istanzeprocedimenti.getDescrizioneAndAmministrazione(), _ialls);
				beans.add(bean);
			    }
			}
		    } else {
			ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
			bean.setChiave(istanzeprocedimenti.getDescrizioneAndAmministrazione());
			bean.setValore(_ialls);
			beans.add(bean);
		    }
		}
	    }
	}
	return beans;
    }

    private ChiaveValoreBean<String, List<IstanzeallegatiDTO>> newChiaveValoreBeanEndo(String chiave, List<IstanzeallegatiDTO> valore) {

	ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
	bean.setChiave(chiave);
	bean.setValore(valore);
	return bean;
    }

    private List<IstanzeallegatiDTO> listToRemoveFromEndoprocedimenti(Integer codicemovimento, List<IstanzeallegatiDTO> fromList) {

	List<IstanzeallegatiDTO> toRemove = new ArrayList<IstanzeallegatiDTO>();
	for (IstanzeallegatiDTO iaDTO : fromList) {
	    Boolean ifiaDTOExistInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
		    iaDTO.getId().getCodice(), "istanzeallegati");
	    if (ifiaDTOExistInZipLogico) {
		toRemove.add(iaDTO);
	    }
	}
	return toRemove;
    }

    /**
     * <pre>
     * La lista sarà composta da un solo elemento che avra la struttura :
     * 		key 	: numeroistanza
     *          lista	: list di documenti istanza
     * &#64;param codiceistanza
     * &#64;param tipoRicercaDocumentoEnum
     * &#64;return
     * </pre>
     */
    private List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> populateDocumentiIstanza(Integer codiceistanza, Integer codiceMovimento,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, TipoFunzionalitaEnum tipoFunzionalitEnum) {

	TipimovStcMapping stcMapping = null;
	Movimenti movimenti = null;
	Autorizzazioni autorizzazione = null;
	boolean _isFlgRegistroChecked = false;
	boolean _isZipLogicoExist = false;
	switch (tipoFunzionalitEnum) {
	case INVIO_EMAIL:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		if (BooleanUtils.toBoolean(movimenti.getTipomovimento().getFlagRegistro())) {
		    List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimenti.getIstanza(), movimenti);
		    if (!lAut.isEmpty()) {
			_isFlgRegistroChecked = true;
			autorizzazione = lAut.get(0);
		    }
		}
	    }
	case INVIO_STC:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		if (EntityUtils.getNestedProperty(movimenti.getAmministrazioniStc(), "id.codice") != null) {
		    log.debug(
			    "populateDocumentiIstanza# E' stato popolato il campo codice movimento, la configurazione TipimovStcMapping per tipoMov {} e amministrazione {}",
			    new Object[] { movimenti.getTipomovimento().getId().getTipomovimento(),
				    movimenti.getAmministrazioniStc().getId().getCodice() });
		    stcMapping = new TipimovStcMapping();
		    stcMapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(
			    movimenti.getTipomovimento().getId().getTipomovimento(), movimenti.getAmministrazioniStc().getId().getCodice());
		}
	    }
	    break;
	case INVIO_ZIP_LOGICO:
	    if (codiceMovimento != null) {
		movimenti = new Movimenti();
		movimenti = movimentiService.findById(new PkId(codiceMovimento));
		Set<MovimentiZipLogico> zipLogico = movimentiZipLogicoService.findMovimentiZipLogicoByMovimento(movimenti.getId().getCodice());
		if (!zipLogico.isEmpty()) {
		    _isZipLogicoExist = true;
		}
	    }
	    break;
	default:
	    break;
	}
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
	if (codiceistanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    List<DocumentiistanzaDTO> list = new ArrayList<DocumentiistanzaDTO>();
	    list = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceistanza, Boolean.FALSE);
	    List<DocumentiistanzaDTO> list2 = new ArrayList<DocumentiistanzaDTO>();
	    list2 = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceistanza, Boolean.TRUE);
	    list.addAll(list2);
	    List<DocumentiistanzaDTO> result = new ArrayList<DocumentiistanzaDTO>();
	    switch (tipoRicercaDocumentoEnum) {
	    case RICERCA_CON_OGGETTO:
		log.debug("populateDocumentiIstanza# Ricerca documenti dell'istanza filtrando per cod istanza {} e documenti con oggetto,",
			codiceistanza);
		// list = documentiistanzaService.findByIstanzaOggetto(codiceistanza);
		for (DocumentiistanzaDTO dd : list) {
		    if (dd.getCodiceOggetto() != null) {
			log.debug("populateDocumentiIstanza# movimento genera autorizzazione={}", _isFlgRegistroChecked);
			if (_isFlgRegistroChecked) {
			    Boolean isExistInAutorizzazioni = documentiAutorizzazioneService.isInAutorizzazione(autorizzazione.getId().getCodice(),
				    dd.getId().getCodice(), "documentiistanza");
			    if (isExistInAutorizzazioni) {
				if (verificaAllegatoValido(dd.getControllook())) {
				    dd.setTransientSegnaPerInvio(true);
				}
			    }
			} else {
			    if (stcMapping != null) {
				log.debug(
					"populateDocumentiEndoprocedimenti# Configurazione TipimovStcMapping per tipoMov {} e amministrazione {} esistenete ",
					new Object[] { movimenti.getTipomovimento().getId().getTipomovimento(),
						movimenti.getAmministrazioniStc().getId().getCodice() });
				// Devo controllare se TipimovStcMapping.flagAllegaDocumentiIstanza == true
				if (BooleanUtils.toBoolean(stcMapping.getFlagAllegaDocumentiIstanza())) {
				    if (verificaAllegatoValido(dd.getControllook())) {
					dd.setTransientSegnaPerInvio(true);
				    }
				}
			    }
			}
			result.add(dd);
		    }
		}
		break;
	    case RICERCA_SENZA_OGGETTO:
		log.debug("populateDocumentiIstanza# Ricerca documenti dell'istanza filtrando per cod istanza {} e documenti senza oggetto,",
			codiceistanza);
		throw new NotImplementedException();
		// break;
	    case RICERCA_TUTTI:
		log.debug("populateDocumentiIstanza# Ricerca documenti dell'istanza filtrando per cod istanza {}", codiceistanza);
		// list = documentiistanzaService.findByIstanza(codiceistanza);
		result.addAll(list);
		break;
	    default:
		break;
	    }
	    if (!result.isEmpty()) {
		if (_isZipLogicoExist) { //// MODIFICA START
		    List<DocumentiistanzaDTO> toRemove = listToRemoveFromDocumentiistanza(codiceMovimento, result);
		    if (!toRemove.isEmpty()) {
			result.removeAll(toRemove);
			if (!result.isEmpty()) {
			    ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = chiaveValoreBean(istanza.getNumeroistanza(), result);
			    beans.add(bean);
			}
		    } else {
			ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = chiaveValoreBean(istanza.getNumeroistanza(), result);
			beans.add(bean);
		    }
		} else { /// MODIFICA END /// => COMPORTAMENTO DI DEFAULT
		    ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = new ChiaveValoreBean<String, List<DocumentiistanzaDTO>>();
		    bean.setChiave(istanza.getNumeroistanza());
		    bean.setValore(result);
		    beans.add(bean);
		}
	    }
	}
	return beans;
    }

    private ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean(String numeroistanza, List<DocumentiistanzaDTO> list) {

	ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = new ChiaveValoreBean<String, List<DocumentiistanzaDTO>>();
	bean.setChiave(numeroistanza);
	bean.setValore(list);
	return bean;
    }

    private List<DocumentiistanzaDTO> listToRemoveFromDocumentiistanza(Integer codicemovimento, List<DocumentiistanzaDTO> fromList) {

	List<DocumentiistanzaDTO> toRemove = new ArrayList<DocumentiistanzaDTO>();
	for (DocumentiistanzaDTO docIstDTO : fromList) {
	    Boolean isDocumentoIstanzaExistInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
		    docIstDTO.getId().getCodice(), "documentiistanza");
	    if (isDocumentoIstanzaExistInZipLogico) {
		toRemove.add(docIstDTO);
	    }
	}
	return toRemove;
    }

    private boolean verificaAllegatoValido(Integer controlloOk) {

	if (controlloOk == null || controlloOk.equals(1)) {
	    return true;
	}
	return false;
    }

    /**
     * <pre>
     * La lista sarà composta un elmento 
     * dove:
     *    key 	: descrizione del movimento passato
     *    lista	: list di documenti del movimento passato
     * &#64;param codiceistanza
     * &#64;param tipoRicercaDocumentoEnum
     * &#64;return
     * </pre>
     */
    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> populateDocumentiMovimento(Integer codicemovimento,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, TipoFunzionalitaEnum tipoFunzionalitEnum) {

	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
	boolean _isFlgRegistroChecked = false;
	Autorizzazioni autorizzazione = null;
	switch (tipoFunzionalitEnum) {
	case INVIO_EMAIL:
	    if (BooleanUtils.toBoolean(movimento.getTipomovimento().getFlagRegistro())) {
		List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimento.getIstanza(), movimento);
		if (!lAut.isEmpty()) {
		    _isFlgRegistroChecked = true;
		    autorizzazione = lAut.get(0);
		}
	    }
	    break;
	default:
	    break;
	}
	List<MovimentiallegatiDTO> movalls = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codicemovimento);
	ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean = new ChiaveValoreBean<String, List<MovimentiallegatiDTO>>();
	List<MovimentiallegatiDTO> movalls2 = new ArrayList<MovimentiallegatiDTO>();
	if (!movalls.isEmpty()) {
	    boolean isAllegatoWithOggetto = false;
	    boolean isAllegatoEml = false;
	    for (MovimentiallegatiDTO movimentiallegati : movalls) {
		if (movimentiallegati.getCodiceOggetto() != null) {
		    if (movimento.getId().getCodice().equals(movimentiallegati.getCodiceMovimento())) {
			if (verificaAllegatoValido(movimentiallegati.getControllook())) {
			    if (_isFlgRegistroChecked) {
				boolean isExistInAutorizzazione = movimentiallegatiService.isPresenteInDocAut(movimentiallegati.getId().getCodice(),
					autorizzazione.getId().getCodice());
				if (isExistInAutorizzazione) {
				    movimentiallegati.setTransientSegnaPerInvio(true);
				}
			    } else {
				movimentiallegati.setTransientSegnaPerInvio(true);
			    }
			}
		    }
		    isAllegatoWithOggetto = true;
		    movalls2.add(movimentiallegati);
		} else {
		    if (StringUtils.isNotBlank(movimentiallegati.getMessageId())) {
			movimentiallegati.setTransientSegnaPerInvio(false);
			movalls2.add(movimentiallegati);
			isAllegatoEml = true;
		    }
		}
	    }
	    if (isAllegatoWithOggetto || isAllegatoEml) {
		String descrizioneMovimento = movimento.getDescrizioneMovimento();
		bean.setChiave(descrizioneMovimento);
		bean.setValore(movalls2);
		beans.add(bean);
	    }
	}
	return beans;
    }

    /**
     * <pre>
     * La lista sarà composta da N  elmenti (quanti sono i movimenti associati all'istanza, escluso quello passato, che hanno almeno un documento con un allegato) 
     * dove:
     *    key 	: descrizione del movimento passato
     *    lista	: list di documenti del movimento passato
     * &#64;param codiceistanza
     * &#64;param tipoRicercaDocumentoEnum
     * &#64;return
     * </pre>
     */
    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> populateDocumentiAltriMovimenti(Integer codiceistanza, Integer codicemovimento,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum, TipoFunzionalitaEnum tipoFunzionalitEnum) {

	Movimenti movimentoInvioEmail = movimentiService.findById(new PkId(codicemovimento));
	boolean _isFlgRegistroChecked = false;
	boolean _isZipLogicoExist = false;
	Autorizzazioni autorizzazione = null;
	switch (tipoFunzionalitEnum) {
	case INVIO_EMAIL:
	    if (BooleanUtils.toBoolean(movimentoInvioEmail.getTipomovimento().getFlagRegistro())) {
		List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimentoInvioEmail.getIstanza(), movimentoInvioEmail);
		if (!lAut.isEmpty()) {
		    _isFlgRegistroChecked = true;
		    autorizzazione = lAut.get(0);
		}
	    }
	    break;
	case INVIO_ZIP_LOGICO:
	    if (codicemovimento != null) {
		Set<MovimentiZipLogico> zipLogico = movimentiZipLogicoService.findMovimentiZipLogicoByMovimento(codicemovimento);
		if (!zipLogico.isEmpty()) {
		    _isZipLogicoExist = true;
		}
	    }
	    break;
	default:
	    break;
	}
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	List<Movimenti> listMov = movimentiService.findByIstanzaAndExcludeMovimento(codiceistanza, codicemovimento, SceltaMovimentiEnum.ESEGUITI);
	ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean = new ChiaveValoreBean<String, List<MovimentiallegatiDTO>>();
	for (Movimenti movimenti : listMov) {
	    List<MovimentiallegatiDTO> movalls = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(movimenti.getId().getCodice());
	    bean = new ChiaveValoreBean<String, List<MovimentiallegatiDTO>>();
	    if (!movalls.isEmpty()) {
		boolean isAllegatoWithOggetto = false;
		boolean isAllegatoEml = false;
		List<MovimentiallegatiDTO> movalls2 = new ArrayList<MovimentiallegatiDTO>();
		for (MovimentiallegatiDTO movimentiallegati : movalls) {
		    if (movimentiallegati.getCodiceOggetto() != null) {
			if (movimenti.getId().getCodice().equals(movimentiallegati.getCodiceMovimento())) {
			    //movimentiallegati.setTransientSegnaPerInvio(true);
			    if (verificaAllegatoValido(movimentiallegati.getControllook())) {
				if (_isFlgRegistroChecked) {
				    boolean isExistInDocAutorizzazioni = movimentiallegatiService
					    .isPresenteInDocAut(movimentiallegati.getId().getCodice(), autorizzazione.getId().getCodice());
				    if (isExistInDocAutorizzazioni) {
					movimentiallegati.setTransientSegnaPerInvio(true);
				    }
				} else {
				    movimentiallegati.setTransientSegnaPerInvio(false);
				}
			    }
			}
			isAllegatoWithOggetto = true;
			movalls2.add(movimentiallegati);
		    } else {
			if (StringUtils.isNotBlank(movimentiallegati.getMessageId())) {
			    movimentiallegati.setTransientSegnaPerInvio(false);
			    movalls2.add(movimentiallegati);
			    isAllegatoEml = true;
			}
		    }
		    //		    			    }
		    //		    			}
		    //		    		    }
		}
		if (isAllegatoWithOggetto || isAllegatoEml) {
		    if (_isZipLogicoExist) {
			if (!movalls2.isEmpty()) {
			    List<MovimentiallegatiDTO> toRemove = listToRemoveFromMovimentiallegati(codicemovimento, movalls2);
			    if (!toRemove.isEmpty() && toRemove.size() > 0) {
				movalls2.removeAll(toRemove);
				if (!movalls2.isEmpty()) {
				    String descrizioneMovimento = movimenti.getDescrizioneMovimento();
				    bean.setChiave(descrizioneMovimento);
				    bean.setValore(movalls2);
				    beans.add(bean);
				}
			    } else {
				String descrizioneMovimento = movimenti.getDescrizioneMovimento();
				bean.setChiave(descrizioneMovimento);
				bean.setValore(movalls2);
				beans.add(bean);
			    }
			}
		    } else {
			String descrizioneMovimento = movimenti.getDescrizioneMovimento();
			bean.setChiave(descrizioneMovimento);
			bean.setValore(movalls2);
			beans.add(bean);
		    }
		}
	    }
	}
	return beans;
    }

    private List<MovimentiallegatiDTO> listToRemoveFromMovimentiallegati(Integer codicemovimento, List<MovimentiallegatiDTO> fromList) {

	List<MovimentiallegatiDTO> toRemove = new ArrayList<MovimentiallegatiDTO>();
	for (MovimentiallegatiDTO movallDTO : fromList) {
	    Boolean ifallegatoMovExistInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
		    movallDTO.getId().getCodice(), "movimentiallegati");
	    if (ifallegatoMovExistInZipLogico) {
		toRemove.add(movallDTO);
	    }
	}
	return toRemove;
    }

    private List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> populateDocumentiAnagrafe(Integer codiceistanza, Integer codiceMovimento,
	    TipoFunzionalitaEnum tipoFunzionalitaEnum) {

	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	boolean _isFlgRegistroChecked = false;
	boolean _ifZipLogicoExists = false;
	Autorizzazioni autorizzazione = null;
	if (codiceMovimento != null) {
	    if (BooleanUtils.toBoolean(movimento.getTipomovimento().getFlagRegistro())) {
		List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimento.getIstanza(), movimento);
		if (!lAut.isEmpty()) {
		    _isFlgRegistroChecked = true;
		    autorizzazione = lAut.get(0);
		}
	    }
	}
	switch (tipoFunzionalitaEnum) {
	case INVIO_ZIP_LOGICO:
	    if (codiceMovimento != null) {
		Set<MovimentiZipLogico> movimentiZipLogicos = movimentiZipLogicoService.findMovimentiZipLogicoByMovimento(codiceMovimento);
		if (!movimentiZipLogicos.isEmpty()) {
		    _ifZipLogicoExists = true;
		}
	    }
	    break;
	default:
	    break;
	}
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>>();
	if (codiceistanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    // Recupero il richiedente
	    Anagrafe richiedente = istanza.getRichiedente();
	    Integer codiceRichiedente = richiedente.getId().getCodice();
	    List<AnagrafedocumentiDTO> list = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(null, richiedente, true);
	    ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
	    if (!list.isEmpty()) {
		log.debug("populateDocumentiAnagrafe# movimento = {} genera aut = {}", codiceMovimento, _isFlgRegistroChecked);
		if (_isFlgRegistroChecked) {
		    for (AnagrafedocumentiDTO anagrafedocumentiDTO : list) {
			if (anagrafedocumentiDTO.getCodiceOggetto() != null) {
			    Boolean isExistInAutorizzazione = documentiAutorizzazioneService.isInAutorizzazione(autorizzazione.getId().getCodice(),
				    anagrafedocumentiDTO.getId().getCodice(), "anagrafedocumenti");
			    if (isExistInAutorizzazione) {
				anagrafedocumentiDTO.setTransientSegnaPerInvio(true);
			    }
			}
		    }
		    bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		    bean.setChiave("<b>Richiedente: </b>" + list.get(0).getTransientTipoSoggettoAndRichiedente());
		    bean.setValore(list);
		    beans.add(bean);
		} else {
		    if (_ifZipLogicoExists) {
			List<AnagrafedocumentiDTO> toRemove = listToRemoveFromAnagrafedocumentiDTO(codiceMovimento, list);
			if (!toRemove.isEmpty() && toRemove.size() > 0) {
			    list.removeAll(toRemove);
			    if (!list.isEmpty()) {
				bean = newChiaveValoreBeanDocanagrafe("<b>Richiedente: </b>" + list.get(0).getTransientTipoSoggettoAndRichiedente(),
					list);
				beans.add(bean);
			    }
			} else {
			    bean = newChiaveValoreBeanDocanagrafe("<b>Richiedente: </b>" + list.get(0).getTransientTipoSoggettoAndRichiedente(),
				    list);
			    beans.add(bean);
			}
		    } else {
			bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
			bean.setChiave("<b>Richiedente: </b>" + list.get(0).getTransientTipoSoggettoAndRichiedente());
			bean.setValore(list);
			beans.add(bean);
		    }
		}
	    }
	    //	    // Recupero azienda richiedente
	    if (EntityUtils.getNestedProperty(istanza.getTitolarelegale(), "id.codice") != null) {
		Anagrafe aziendarichiedente = istanza.getTitolarelegale();
		Integer codiceAziendaRichiedente = istanza.getTitolarelegale().getId().getCodice();
		List<AnagrafedocumentiDTO> listDocAzienzaRichiedente = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(null, aziendarichiedente,
			true);
		ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanAziendaRichiedente = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		if (!listDocAzienzaRichiedente.isEmpty()) {
		    if (_ifZipLogicoExists) {
			List<AnagrafedocumentiDTO> toRemove = listToRemoveFromAnagrafedocumentiDTO(codiceMovimento, listDocAzienzaRichiedente);
			if (!toRemove.isEmpty()) {
			    listDocAzienzaRichiedente.removeAll(toRemove);
			    if (!listDocAzienzaRichiedente.isEmpty()) {
				beanAziendaRichiedente = newChiaveValoreBeanDocanagrafe(
					"<b>Azienda Richiedente: </b>" + listDocAzienzaRichiedente.get(0).getTransientTipoSoggettoAndRichiedente(),
					listDocAzienzaRichiedente);
				beans.add(beanAziendaRichiedente);
			    }
			} else {
			    beanAziendaRichiedente = newChiaveValoreBeanDocanagrafe(
				    "<b>Azienda Richiedente: </b>" + listDocAzienzaRichiedente.get(0).getTransientTipoSoggettoAndRichiedente(),
				    listDocAzienzaRichiedente);
			    beans.add(beanAziendaRichiedente);
			}
		    } else {
			beanAziendaRichiedente = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
			beanAziendaRichiedente.setChiave(
				"<b>Azienda Richiedente: </b>" + listDocAzienzaRichiedente.get(0).getTransientTipoSoggettoAndRichiedente());
			beanAziendaRichiedente.setValore(listDocAzienzaRichiedente);
			beans.add(beanAziendaRichiedente);
		    }
		}
	    }
	    //	    // Recupero azienda professionista
	    if (EntityUtils.getNestedProperty(istanza.getProfessionista(), "id.codice") != null) {
		Anagrafe professionista = istanza.getProfessionista();
		Integer codiceProfessionista = istanza.getProfessionista().getId().getCodice();
		List<AnagrafedocumentiDTO> listDocProfessionista = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(null, professionista, true);
		ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanProfessionista = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
		if (!listDocProfessionista.isEmpty()) {
		    if (_ifZipLogicoExists) {
			List<AnagrafedocumentiDTO> toRemove = listToRemoveFromAnagrafedocumentiDTO(codiceMovimento, listDocProfessionista);
			if (!toRemove.isEmpty()) {
			    listDocProfessionista.removeAll(toRemove);
			    if (!listDocProfessionista.isEmpty()) {
				beanProfessionista = newChiaveValoreBeanDocanagrafe(
					"<b>Intermediario: </b>" + listDocProfessionista.get(0).getTransientTipoSoggettoAndRichiedente(),
					listDocProfessionista);
				beans.add(beanProfessionista);
			    }
			} else {
			    beanProfessionista = newChiaveValoreBeanDocanagrafe(
				    "<b>Intermediario: </b>" + listDocProfessionista.get(0).getTransientTipoSoggettoAndRichiedente(),
				    listDocProfessionista);
			    beans.add(beanProfessionista);
			}
		    } else {
			beanProfessionista = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
			beanProfessionista
				.setChiave("<b>Intermediario: </b>" + listDocProfessionista.get(0).getTransientTipoSoggettoAndRichiedente());
			beanProfessionista.setValore(listDocProfessionista);
			beans.add(beanProfessionista);
		    }
		}
	    }
	    // Recupero i soggetti collegati dell'istanza
	    List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanza);
	    for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
		Integer codIstanzaRich = istanzerichiedenti.getRichiedente().getId().getCodice();
		if (!codiceRichiedente.equals(codIstanzaRich)) {
		    List<AnagrafedocumentiDTO> listDocumenti = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(null,
			    istanzerichiedenti.getRichiedente(), true);
		    if (!listDocumenti.isEmpty()) {
			if (_ifZipLogicoExists) {
			    List<AnagrafedocumentiDTO> toRemove = listToRemoveFromAnagrafedocumentiDTO(codiceMovimento, listDocumenti);
			    if (!toRemove.isEmpty()) {
				listDocumenti.removeAll(toRemove);
				if (!listDocumenti.isEmpty()) {
				    bean = newChiaveValoreBeanDocanagrafe(istanzerichiedenti.getTransientTipoSoggettoAndRichiedente(), listDocumenti);
				    beans.add(bean);
				}
			    } else {
				bean = newChiaveValoreBeanDocanagrafe(istanzerichiedenti.getTransientTipoSoggettoAndRichiedente(), listDocumenti);
				beans.add(bean);
			    }
			} else {
			    bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
			    bean.setChiave(istanzerichiedenti.getTransientTipoSoggettoAndRichiedente());
			    bean.setValore(listDocumenti);
			    beans.add(bean);
			}
		    }
		}
	    }
	}
	return beans;
    }

    private ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> newChiaveValoreBeanDocanagrafe(String chiave, List<AnagrafedocumentiDTO> valore) {

	ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
	bean.setChiave(chiave);
	bean.setValore(valore);
	return bean;
    }

    private List<AnagrafedocumentiDTO> listToRemoveFromAnagrafedocumentiDTO(Integer codicemovimento, List<AnagrafedocumentiDTO> fromList) {

	List<AnagrafedocumentiDTO> toRemove = new ArrayList<AnagrafedocumentiDTO>();
	for (AnagrafedocumentiDTO anagrafeDocumentiDTO : fromList) {
	    Boolean ifAnagrafedocumentoExsistInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
		    anagrafeDocumentiDTO.getId().getCodice(), "anagrafedocumenti");
	    if (ifAnagrafedocumentoExsistInZipLogico) {
		toRemove.add(anagrafeDocumentiDTO);
	    }
	}
	return toRemove;
    }

    private List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> populateDocumentiAnagrafe(Integer codiceistanza) {

	return this.populateDocumentiAnagrafe(codiceistanza, null, TipoFunzionalitaEnum.DEFAULT);
    }

    private List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> populateProcure(Integer codiceistanza, Integer codiceMovimento,
	    TipoFunzionalitaEnum tipoFunzionalitaEnum) {

	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	boolean _isFlgRegistroChecked = false;
	boolean _ifZipLogicoExist = false;
	Autorizzazioni autorizzazione = null;
	if (codiceMovimento != null) {
	    if (BooleanUtils.toBoolean(movimento.getTipomovimento().getFlagRegistro())) {
		List<Autorizzazioni> lAut = autorizzazioniService.findByIstanzaMovimento(movimento.getIstanza(), movimento);
		if (!lAut.isEmpty()) {
		    _isFlgRegistroChecked = true;
		    autorizzazione = lAut.get(0);
		}
	    }
	}
	switch (tipoFunzionalitaEnum) {
	case INVIO_ZIP_LOGICO:
	    if (codiceMovimento != null) {
		Set<MovimentiZipLogico> zipLogico = movimentiZipLogicoService.findMovimentiZipLogicoByMovimento(codiceMovimento);
		if (!zipLogico.isEmpty()) {
		    _ifZipLogicoExist = true;
		}
	    }
	    break;
	default:
	    break;
	}
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> beans = new ArrayList<ChiaveValoreBean<String, List<IstanzeprocureDTO>>>();
	List<IstanzeprocureDTO> list = istanzeprocureService.findIstanzeprocureDTOByIstanza(codiceistanza,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
	ChiaveValoreBean<String, List<IstanzeprocureDTO>> bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
	if (!list.isEmpty()) {
	    log.debug("populateProcure# Codice mov = {} genera autorizzazione = {}", _isFlgRegistroChecked);
	    if (_isFlgRegistroChecked) {
		for (IstanzeprocureDTO istanzeprocureDTO : list) {
		    if (istanzeprocureDTO.getCodiceOggetto() != null) {
			if (verificaAllegatoValido(istanzeprocureDTO.getControllook())) {
			    Boolean isInAutorizzazioni = documentiAutorizzazioneService.isInAutorizzazione(autorizzazione.getId().getCodice(),
				    istanzeprocureDTO.getId().getCodice(), "istanzeprocure");
			    if (isInAutorizzazioni) {
				istanzeprocureDTO.setTransientSegnaPerInvio(true);
			    }
			}
		    }
		}
		bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
		bean.setChiave("");
		bean.setValore(list);
		beans.add(bean);
	    } else {
		if (_ifZipLogicoExist) {
		    List<IstanzeprocureDTO> toRemove = listToRemovefromIstanzeprocureDTOList(codiceMovimento, list);
		    if (!toRemove.isEmpty()) {
			list.removeAll(toRemove);
			if (!list.isEmpty()) {
			    bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
			    bean.setChiave("");
			    bean.setValore(list);
			    beans.add(bean);
			}
		    } else {
			bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
			bean.setChiave("");
			bean.setValore(list);
			beans.add(bean);
		    }
		} else {
		    bean = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
		    bean.setChiave("");
		    bean.setValore(list);
		    beans.add(bean);
		}
	    }
	}
	return beans;
    }

    private List<IstanzeprocureDTO> listToRemovefromIstanzeprocureDTOList(Integer codicemovimento, List<IstanzeprocureDTO> fromList) {

	List<IstanzeprocureDTO> toRemove = new ArrayList<IstanzeprocureDTO>();
	for (IstanzeprocureDTO istprocDTO : fromList) {
	    if (istprocDTO.getCodiceOggetto() != null) {
		Boolean ifIstprocExistInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
			istprocDTO.getId().getCodice(), "istanzeprocure");
		if (ifIstprocExistInZipLogico) {
		    toRemove.add(istprocDTO);
		}
	    }
	}
	return toRemove;
    }

    /**
     * 
     * @param codiceistanza
     * @return
     */
    private List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> populateProcure(Integer codiceistanza) {

	return populateProcure(codiceistanza, null, TipoFunzionalitaEnum.DEFAULT);
    }

    @Override
    public DocumentiHelper findDocumentiInvioMailDaMovimento(Integer codiceMovimento) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	Integer codiceIstanza = movimenti.getIstanza().getId().getCodice();
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiMovimenti = this.populateDocumentiMovimento(codiceMovimento,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_EMAIL);
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiAltriMovimenti = this.populateDocumentiAltriMovimenti(codiceIstanza,
		movimenti.getId().getCodice(), TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_EMAIL);
	//.
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceIstanza,
		codiceMovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_EMAIL);
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(
		movimenti.getIstanza().getId().getCodice(), codiceMovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO,
		TipoFunzionalitaEnum.INVIO_EMAIL);
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> listDocumentiAnagrafe = this.populateDocumentiAnagrafe(codiceIstanza,
		codiceMovimento, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listIstanzeprocure = this.populateProcure(codiceIstanza, codiceMovimento,
		TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> listAtti = this.populateAttiCDS(codiceIstanza, null, Boolean.TRUE,
		TipoFunzionalitaEnum.DEFAULT);
	documentiHelper.setDocumentiAnagrafeList(listDocumentiAnagrafe);
	documentiHelper.setDocumentiEndoprocedimentiList(listDocumentoEndo);
	documentiHelper.setDocumentiIstanzaList(listDocumentiIstanza);
	documentiHelper.setDocumentiMovimentoList(listDocumentiMovimenti);
	documentiHelper.setDocumentiAltriMovimentiList(listDocumentiAltriMovimenti);
	documentiHelper.setIstanzeprocureList(listIstanzeprocure);
	documentiHelper.setCdsattiList(listAtti);
	return documentiHelper;
    }

    @Override
    public DocumentiHelper findDocumentiInvioDocumentiProtocollo(Integer codiceMovimento, Integer codiceIstanza, boolean isProtocolloDaMovimento) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	if (isProtocolloDaMovimento) {
	    Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiMovimenti = this.populateDocumentiMovimento(codiceMovimento,
		    TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiAltriMovimenti = this.populateDocumentiAltriMovimenti(
		    codiceIstanza, movimenti.getId().getCodice(), TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	    documentiHelper.setDocumentiMovimentoList(listDocumentiMovimenti);
	    documentiHelper.setDocumentiAltriMovimentiList(listDocumentiAltriMovimenti);
	}
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> listDocumentiAnagrafe = this.populateDocumentiAnagrafe(codiceIstanza);
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listDocumentiProcure = this.populateProcure(codiceIstanza);
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> listAtti = this.populateAttiCDS(codiceIstanza, null, Boolean.TRUE,
		TipoFunzionalitaEnum.DEFAULT);
	documentiHelper.setDocumentiEndoprocedimentiList(listDocumentoEndo);
	documentiHelper.setDocumentiIstanzaList(listDocumentiIstanza);
	documentiHelper.setDocumentiAnagrafeList(listDocumentiAnagrafe);
	documentiHelper.setIstanzeprocureList(listDocumentiProcure);
	documentiHelper.setCdsattiList(listAtti);
	return documentiHelper;
    }

    private List<ChiaveValoreBean<String, List<CdsattiDTO>>> populateAttiCDS(Integer codiceIstanza, Integer codicemovimento, Boolean cercaOggetti,
	    TipoFunzionalitaEnum tipoFunzionalitaEnum) {

	boolean _ifZipLogicoExist = false;
	switch (tipoFunzionalitaEnum) {
	case INVIO_ZIP_LOGICO:
	    if (codicemovimento != null) {
		Set<MovimentiZipLogico> zipLogico = movimentiZipLogicoService.findMovimentiZipLogicoByMovimento(codicemovimento);
		if (!zipLogico.isEmpty()) {
		    _ifZipLogicoExist = true;
		}
	    }
	    break;
	default:
	    break;
	}
	List<Cds> cdss = null;
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> result = new ArrayList<ChiaveValoreBean<String, List<CdsattiDTO>>>();
	//	if (codiceMovimento != null) {
	//	    cdss = cdsService.findByMovimento(movimentiService.findById(new PkId(codiceMovimento)));
	//	} else {
	if (codiceIstanza != null) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    cdss = cdsService.findByIstanza(istanza);
	    //	}
	    for (Cds cds : cdss) {
		ChiaveValoreBean<String, List<CdsattiDTO>> cvb = new ChiaveValoreBean<String, List<CdsattiDTO>>();
		cvb.setChiave(String.valueOf(cds.getId().getCodice()));
		List<CdsattiDTO> valore = cdsattiService.findDTOByCds(cds.getId().getCodice(), cercaOggetti);
		if (_ifZipLogicoExist) {
		    if (!valore.isEmpty()) {
			List<CdsattiDTO> toRemove = listToRemoveFromCdsatti(codicemovimento, valore);
			if (!toRemove.isEmpty()) {
			    valore.removeAll(toRemove);
			    if (!valore.isEmpty()) {
				cvb.setValore(valore);
				result.add(cvb);
			    }
			} else {
			    cvb.setValore(valore);
			    result.add(cvb);
			}
		    }
		} else {
		    if (!valore.isEmpty()) {
			cvb.setValore(valore);
			result.add(cvb);
		    }
		}
	    }
	}
	return result;
    }

    private List<CdsattiDTO> listToRemoveFromCdsatti(Integer codicemovimento, List<CdsattiDTO> fromList) {

	List<CdsattiDTO> toRemove = new ArrayList<CdsattiDTO>();
	for (CdsattiDTO cdsattoDTO : fromList) {
	    Boolean ifCdsattiExistsInZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(null, codicemovimento,
		    cdsattoDTO.getId().getCodice(), "cdsatti");
	    if (ifCdsattiExistsInZipLogico) {
		toRemove.add(cdsattoDTO);
	    }
	}
	return toRemove;
    }

    @Override
    public DocumentiHelper findDocumentiDownloadZip(Integer codiceIstanza) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiAltriMovimenti = this.populateDocumentiAltriMovimenti(codiceIstanza,
		null, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> listDocumentiAnagrafe = this.populateDocumentiAnagrafe(codiceIstanza);
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listDocumentiProcure = this.populateProcure(codiceIstanza);
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> listAtti = this.populateAttiCDS(codiceIstanza, null, Boolean.TRUE,
		TipoFunzionalitaEnum.DEFAULT);
	documentiHelper.setDocumentiAltriMovimentiList(listDocumentiAltriMovimenti);
	documentiHelper.setDocumentiEndoprocedimentiList(listDocumentoEndo);
	documentiHelper.setDocumentiIstanzaList(listDocumentiIstanza);
	documentiHelper.setDocumentiAnagrafeList(listDocumentiAnagrafe);
	documentiHelper.setIstanzeprocureList(listDocumentiProcure);
	documentiHelper.setCdsattiList(listAtti);
	return documentiHelper;
    }

    @Override
    public DocumentiHelper findDocumentiDaAggiungereAZipLogico(Integer codiceistanza, Integer codicemovimento) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(codiceistanza, codicemovimento,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceistanza,
		codicemovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listMovimentiallegati = this.populateDocumentiAltriMovimenti(codiceistanza,
		codicemovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> listAnagrafedocumentis = this.populateDocumentiAnagrafe(codiceistanza,
		codicemovimento, TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listIstanzeprocures = this.populateProcure(codiceistanza, codicemovimento,
		TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> listCdsatti = this.populateAttiCDS(codiceistanza, codicemovimento, Boolean.TRUE,
		TipoFunzionalitaEnum.INVIO_ZIP_LOGICO);
	documentiHelper.setDocumentiIstanzaList(listDocumentiIstanza);
	documentiHelper.setDocumentiEndoprocedimentiList(listDocumentoEndo);
	documentiHelper.setDocumentiAltriMovimentiList(listMovimentiallegati);
	documentiHelper.setDocumentiAnagrafeList(listAnagrafedocumentis);
	documentiHelper.setIstanzeprocureList(listIstanzeprocures);
	documentiHelper.setCdsattiList(listCdsatti);
	return documentiHelper;
    }

    @Override
    public DocumentiHelper findDocumentiInvioDocumentiSTC(Integer codiceMovimento) {

	DocumentiHelper documentiHelper = new DocumentiHelper();
	Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	Integer codiceIstanza = movimenti.getIstanza().getId().getCodice();
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiMovimenti = this.populateDocumentiMovimento(codiceMovimento,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_STC);
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiAltriMovimenti = this.populateDocumentiAltriMovimenti(codiceIstanza,
		movimenti.getId().getCodice(), TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_STC);
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceIstanza,
		codiceMovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.INVIO_STC);
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(
		movimenti.getIstanza().getId().getCodice(), codiceMovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO,
		TipoFunzionalitaEnum.INVIO_STC);
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> listDocumentiAnagrafe = this.populateDocumentiAnagrafe(codiceIstanza);
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listIstanzeprocure = this.populateProcure(codiceIstanza);
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> listAtti = this.populateAttiCDS(codiceIstanza, null, Boolean.TRUE,
		TipoFunzionalitaEnum.DEFAULT);
	documentiHelper.setDocumentiAnagrafeList(listDocumentiAnagrafe);
	documentiHelper.setDocumentiEndoprocedimentiList(listDocumentoEndo);
	documentiHelper.setDocumentiIstanzaList(listDocumentiIstanza);
	documentiHelper.setDocumentiMovimentoList(listDocumentiMovimenti);
	documentiHelper.setDocumentiAltriMovimentiList(listDocumentiAltriMovimenti);
	documentiHelper.setIstanzeprocureList(listIstanzeprocure);
	documentiHelper.setCdsattiList(listAtti);
	return documentiHelper;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiIstanza(List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList) {

	List<DocumentiistanzaDTO> risultato = new ArrayList<DocumentiistanzaDTO>();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : documentiIstanzaList) {
	    List<DocumentiistanzaDTO> documentiistanzas = chiaveValoreBean.getValore();
	    risultato.addAll(documentiistanzas);
	}
	return risultato;
    }

    @Override
    public List<IstanzeprocureDTO> findDocumentiProcure(List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> documentiProcureList) {

	List<IstanzeprocureDTO> risultato = new ArrayList<IstanzeprocureDTO>();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : documentiProcureList) {
	    List<IstanzeprocureDTO> documentiprocures = chiaveValoreBean.getValore();
	    risultato.addAll(documentiprocures);
	}
	return risultato;
    }

    @Override
    public List<IstanzeallegatiDTO> findDocumentiEndo(List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoprocedimentiList) {

	List<IstanzeallegatiDTO> risultato = new ArrayList<IstanzeallegatiDTO>();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : documentiEndoprocedimentiList) {
	    List<IstanzeallegatiDTO> istanzeallegatis = chiaveValoreBean.getValore();
	    risultato.addAll(istanzeallegatis);
	}
	return risultato;
    }

    @Override
    public List<MovimentiallegatiDTO> findDocumentiMovimento(List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentoList) {

	List<MovimentiallegatiDTO> risultato = new ArrayList<MovimentiallegatiDTO>();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : documentiMovimentoList) {
	    List<MovimentiallegatiDTO> movimentiallegatis = chiaveValoreBean.getValore();
	    risultato.addAll(movimentiallegatis);
	}
	return risultato;
    }

    @Override
    public List<AnagrafedocumentiDTO> findDocumentiAnagrafe(List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList) {

	List<AnagrafedocumentiDTO> risultato = new ArrayList<AnagrafedocumentiDTO>();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : documentiAnagrafeList) {
	    List<AnagrafedocumentiDTO> anagrafedocumentis = chiaveValoreBean.getValore();
	    risultato.addAll(anagrafedocumentis);
	}
	return risultato;
    }

    @Override
    public DocumentiHelper findDocumentiInvioTrue(DocumentiHelper documentiHelper) {

	// Bonifico la lista dei documenti del movimento
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movDocchiaveValoreBeans = findDocumentiMovimentoInvioTrue(
		documentiHelper.getDocumentiMovimentoList());
	// Bonifico la lista dei documenti di altri mov
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _altriMovDocchiaveValoreBeans = findDocumentiMovimentoInvioTrue(
		documentiHelper.getDocumentiAltriMovimentiList());
	// Bonifico lista documenti istanza
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _istanzaDocchiaveValoreBeans = findDocumentiIstanzaInvioTrue(
		documentiHelper.getDocumentiIstanzaList());
	// Bonifico lista documenti endo
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoDocchiaveValoreBeans = findDocumentiEndoInvioTrue(
		documentiHelper.getDocumentiEndoprocedimentiList());
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _docAagrafeValoreBeans = findDocumentiAnagrafeInvioTrue(
		documentiHelper.getDocumentiAnagrafeList());
	// Bonifico lista documenti procure
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _docProcureValoreBeans = findDocumentiProcureInvioTrue(
		documentiHelper.getIstanzeprocureList());
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> _docCdsAttiValoreBeans = findDocumentiCdsAttiInvioTrue(documentiHelper.getCdsattiList());
	documentiHelper.setDocumentiMovimentoList(_movDocchiaveValoreBeans);
	documentiHelper.setDocumentiAltriMovimentiList(_altriMovDocchiaveValoreBeans);
	documentiHelper.setDocumentiIstanzaList(_istanzaDocchiaveValoreBeans);
	documentiHelper.setDocumentiEndoprocedimentiList(_endoDocchiaveValoreBeans);
	documentiHelper.setDocumentiAnagrafeList(_docAagrafeValoreBeans);
	documentiHelper.setIstanzeprocureList(_docProcureValoreBeans);
	documentiHelper.setCdsattiList(_docCdsAttiValoreBeans);
	return documentiHelper;
    }

    private List<ChiaveValoreBean<String, List<CdsattiDTO>>> findDocumentiCdsAttiInvioTrue(
	    List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattiList) {

	List<ChiaveValoreBean<String, List<CdsattiDTO>>> _docchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<CdsattiDTO>>>();
	ChiaveValoreBean<String, List<CdsattiDTO>> beanDoc = null;
	for (ChiaveValoreBean<String, List<CdsattiDTO>> procureDocchiaveValoreBean : cdsattiList) {
	    beanDoc = new ChiaveValoreBean<String, List<CdsattiDTO>>();
	    beanDoc.setChiave(procureDocchiaveValoreBean.getChiave());
	    List<CdsattiDTO> procureallegatis = procureDocchiaveValoreBean.getValore();
	    List<CdsattiDTO> list = null;
	    list = new ArrayList<CdsattiDTO>();
	    for (CdsattiDTO pdoc : procureallegatis) {
		if (pdoc.getTransientSegnaPerInvio()) {
		    list.add(pdoc);
		}
	    }
	    if (!list.isEmpty()) {
		beanDoc.setValore(list);
		_docchiaveValoreBeans.add(beanDoc);
	    }
	}
	return _docchiaveValoreBeans;
    }

    private List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> findDocumentiProcureInvioTrue(
	    List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> documentiAnagrafeList) {

	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _docchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<IstanzeprocureDTO>>>();
	ChiaveValoreBean<String, List<IstanzeprocureDTO>> beanDoc = null;
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> procureDocchiaveValoreBean : documentiAnagrafeList) {
	    beanDoc = new ChiaveValoreBean<String, List<IstanzeprocureDTO>>();
	    beanDoc.setChiave(procureDocchiaveValoreBean.getChiave());
	    List<IstanzeprocureDTO> procureallegatis = procureDocchiaveValoreBean.getValore();
	    List<IstanzeprocureDTO> list = null;
	    list = new ArrayList<IstanzeprocureDTO>();
	    for (IstanzeprocureDTO pdoc : procureallegatis) {
		if (pdoc.getTransientSegnaPerInvio()) {
		    list.add(pdoc);
		}
	    }
	    if (!list.isEmpty()) {
		beanDoc.setValore(list);
		_docchiaveValoreBeans.add(beanDoc);
	    }
	}
	return _docchiaveValoreBeans;
    }

    private List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> findDocumentiAnagrafeInvioTrue(
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList) {

	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _docchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>>();
	ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> beanDoc = null;
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> endoDocchiaveValoreBean : documentiAnagrafeList) {
	    beanDoc = new ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>();
	    beanDoc.setChiave(endoDocchiaveValoreBean.getChiave());
	    List<AnagrafedocumentiDTO> istanzeallegatis = endoDocchiaveValoreBean.getValore();
	    List<AnagrafedocumentiDTO> list = null;
	    list = new ArrayList<AnagrafedocumentiDTO>();
	    for (AnagrafedocumentiDTO adoc : istanzeallegatis) {
		if (adoc.isTransientSegnaPerInvio()) {
		    list.add(adoc);
		}
	    }
	    if (!list.isEmpty()) {
		beanDoc.setValore(list);
		_docchiaveValoreBeans.add(beanDoc);
	    }
	}
	return _docchiaveValoreBeans;
    }

    private List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> findDocumentiEndoInvioTrue(
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> endoDocChiaveValoreBeans) {

	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoDocchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
	ChiaveValoreBean<String, List<IstanzeallegatiDTO>> beanEndoDoc = null;
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> endoDocchiaveValoreBean : endoDocChiaveValoreBeans) {
	    beanEndoDoc = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
	    beanEndoDoc.setChiave(endoDocchiaveValoreBean.getChiave());
	    List<IstanzeallegatiDTO> istanzeallegatis = endoDocchiaveValoreBean.getValore();
	    List<IstanzeallegatiDTO> _istanzeallegatis = null;
	    _istanzeallegatis = new ArrayList<IstanzeallegatiDTO>();
	    for (IstanzeallegatiDTO istanzeallegati : istanzeallegatis) {
		if (istanzeallegati.isTransientSegnaPerInvio()) {
		    //		    Oggetti oggetto = oggettiService.findById(new PkId(istanzeallegati.getOggetto().getId().getCodice()));
		    //		    istanzeallegati.setOggetto(oggetto);
		    _istanzeallegatis.add(istanzeallegati);
		}
	    }
	    if (!_istanzeallegatis.isEmpty()) {
		beanEndoDoc.setValore(_istanzeallegatis);
		_endoDocchiaveValoreBeans.add(beanEndoDoc);
	    }
	}
	return _endoDocchiaveValoreBeans;
    }

    private List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> findDocumentiIstanzaInvioTrue(
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> istanzaDocChiaveValoreBeans) {

	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _istanzaDocchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
	ChiaveValoreBean<String, List<DocumentiistanzaDTO>> beanIstanzaDoc = null;
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> istanzaDocchiaveValoreBean : istanzaDocChiaveValoreBeans) {
	    beanIstanzaDoc = new ChiaveValoreBean<String, List<DocumentiistanzaDTO>>();
	    beanIstanzaDoc.setChiave(istanzaDocchiaveValoreBean.getChiave());
	    List<DocumentiistanzaDTO> documentiistanzas = istanzaDocchiaveValoreBean.getValore();
	    List<DocumentiistanzaDTO> _documentiistanzas = null;
	    _documentiistanzas = new ArrayList<DocumentiistanzaDTO>();
	    for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
		if (documentiistanza.getTransientSegnaPerInvio()) {
		    //		    Oggetti oggetto = oggettiService.findById(new PkId(documentiistanza.getOggetto().getId().getCodice()));
		    //		    documentiistanza.setOggetto(oggetto);
		    _documentiistanzas.add(documentiistanza);
		}
	    }
	    if (!_documentiistanzas.isEmpty()) {
		beanIstanzaDoc.setValore(_documentiistanzas);
		_istanzaDocchiaveValoreBeans.add(beanIstanzaDoc);
	    }
	}
	return _istanzaDocchiaveValoreBeans;
    }

    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> findDocumentiMovimentoInvioTrue(
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> movDocchiaveValoreBeans) {

	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movDocchiaveValoreBeans = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	ChiaveValoreBean<String, List<MovimentiallegatiDTO>> beanMovDoc = null;
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> movDocchiaveValoreBean : movDocchiaveValoreBeans) {
	    beanMovDoc = new ChiaveValoreBean<String, List<MovimentiallegatiDTO>>();
	    beanMovDoc.setChiave(movDocchiaveValoreBean.getChiave());
	    List<MovimentiallegatiDTO> movimentiallegatis = movDocchiaveValoreBean.getValore();
	    List<MovimentiallegatiDTO> _movimentiallegatis = new ArrayList<MovimentiallegatiDTO>();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		if (movimentiallegati.isTransientSegnaPerInvio()) {
		    //		    Oggetti oggetto = oggettiService.findById(new PkId(movimentiallegati.getOggetto().getId().getCodice()));
		    //		    movimentiallegati.setOggetto(oggetto);
		    _movimentiallegatis.add(movimentiallegati);
		}
	    }
	    if (!_movimentiallegatis.isEmpty()) {
		beanMovDoc.setValore(_movimentiallegatis);
		_movDocchiaveValoreBeans.add(beanMovDoc);
	    }
	}
	return _movDocchiaveValoreBeans;
    }

    @Override
    public Map<SituazioneAllegato, Integer> findSituazioneDocumentiIstanzaEdEndo(Integer codiceIstanza) {

	int documentiRichiesti = 0;
	int documentiPresentati = 0;
	int documentiNonValidi = 0;
	int documentiValidi = 0;
	Map<SituazioneAllegato, Integer> situazioniAllegati = new HashMap<SituazioneAllegato, Integer>();
	List<IstanzeallegatiDTO> istanzeallegatiDTOs = istanzeallegatiService.findIstanzeallegatiDTOByIstanza(codiceIstanza);
	for (IstanzeallegatiDTO istanzeallegatiDTO : istanzeallegatiDTOs) {
	    //Controllo se necessario
	    if (BooleanUtils.isTrue(istanzeallegatiDTO.getNecessario())) {
		documentiRichiesti++;
	    }
	    //Controllo se presente
	    if (BooleanUtils.isTrue(istanzeallegatiDTO.getPresente())) {
		documentiPresentati++;
	    }
	    // Controllo se verificato
	    if (istanzeallegatiDTO.getControllook() != null && istanzeallegatiDTO.getControllook().equals(Integer.valueOf(0))) {
		documentiNonValidi++;
	    }
	    // Controllo se valido
	    if (istanzeallegatiDTO.getControllook() != null && istanzeallegatiDTO.getControllook().equals(Integer.valueOf(1))) {
		documentiValidi++;
	    }
	}
	List<DocumentiistanzaDTO> documentiistanzaDTOs = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, false);
	for (DocumentiistanzaDTO documentiistanzaDTO : documentiistanzaDTOs) {
	    //Controllo se necessario
	    if (BooleanUtils.isTrue(documentiistanzaDTO.getNecessario())) {
		documentiRichiesti++;
	    }
	    //Controllo se presente
	    if (BooleanUtils.isTrue(documentiistanzaDTO.getPresente())) {
		documentiPresentati++;
	    }
	    // Controllo se verificato
	    if (documentiistanzaDTO.getControllook() != null && documentiistanzaDTO.getControllook().equals(Integer.valueOf(0))) {
		documentiNonValidi++;
	    }
	    // Controllo se valido
	    if (documentiistanzaDTO.getControllook() != null && documentiistanzaDTO.getControllook().equals(Integer.valueOf(1))) {
		documentiValidi++;
	    }
	}
	situazioniAllegati.put(SituazioneAllegato.RICHIESTO, documentiRichiesti);
	situazioniAllegati.put(SituazioneAllegato.PRESENTE, documentiPresentati);
	situazioniAllegati.put(SituazioneAllegato.NON_VALIDO, documentiNonValidi);
	situazioniAllegati.put(SituazioneAllegato.VALIDO, documentiValidi);
	return situazioniAllegati;
    }

    @Override
    public List<Integer> findCodiceOggettoAllegatiDaInviare(DocumentiHelper documentiHelper) {

	List<Integer> risultato = new ArrayList<Integer>();
	documentiHelper = this.findDocumentiInvioTrue(documentiHelper);
	// Recupero codici dalla lista dei documenti del movimento
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movDocchiaveValoreBeans = documentiHelper.getDocumentiMovimentoList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> movDocchiaveValoreBeans : _movDocchiaveValoreBeans) {
	    List<MovimentiallegatiDTO> documentimovimentis = movDocchiaveValoreBeans.getValore();
	    for (MovimentiallegatiDTO documentimovimenti : documentimovimentis) {
		risultato.add(documentimovimenti.getCodiceOggetto());
	    }
	}
	// Recupero codici dalla lista dei documenti di altri mov
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _altriMovDocchiaveValoreBeans = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> altriMovDocchiaveValoreBeans : _altriMovDocchiaveValoreBeans) {
	    List<MovimentiallegatiDTO> documentimovimentis = altriMovDocchiaveValoreBeans.getValore();
	    for (MovimentiallegatiDTO documentimovimenti : documentimovimentis) {
		risultato.add(documentimovimenti.getCodiceOggetto());
	    }
	}
	// Recupero codici dalla lista documenti istanza
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _istanzaDocchiaveValoreBeans = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> istanzaDocchiaveValoreBeans : _istanzaDocchiaveValoreBeans) {
	    List<DocumentiistanzaDTO> documentiistanzas = istanzaDocchiaveValoreBeans.getValore();
	    for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
		risultato.add(documentiistanza.getCodiceOggetto());
	    }
	}
	// Recupero codici dalla lista documenti endo
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoDocchiaveValoreBeans = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> endoDocchiaveValoreBeans : _endoDocchiaveValoreBeans) {
	    List<IstanzeallegatiDTO> documentiendos = endoDocchiaveValoreBeans.getValore();
	    for (IstanzeallegatiDTO documentiendo : documentiendos) {
		risultato.add(documentiendo.getCodiceOggetto());
	    }
	}
	//Recupero codici dalla	lista dei documenti dell'anagrafica
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _docAagrafeValoreBeans = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> docAagrafeValoreBeans : _docAagrafeValoreBeans) {
	    List<AnagrafedocumentiDTO> documentiAnagrafes = docAagrafeValoreBeans.getValore();
	    for (AnagrafedocumentiDTO documentiAnagrafe : documentiAnagrafes) {
		risultato.add(documentiAnagrafe.getCodiceOggetto());
	    }
	}
	// Recupero codici dalla lista documenti procure
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _docProcureValoreBeans = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> docProcureValoreBeans : _docProcureValoreBeans) {
	    List<IstanzeprocureDTO> documentiProcures = docProcureValoreBeans.getValore();
	    for (IstanzeprocureDTO documentiProcure : documentiProcures) {
		risultato.add(documentiProcure.getCodiceOggetto());
	    }
	}
	return risultato;
    }

    private AnagrafedocumentiService anagrafedocumentiService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeService istanzeService;
    private IstanzeallegatiService istanzeallegatiService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private IstanzeprocureService istanzeprocureService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setAnagrafedocumentiService(AnagrafedocumentiService anagrafedocumentiService) {

	this.anagrafedocumentiService = anagrafedocumentiService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
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
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Override
    public Oggetti checkDocumentoPerResponsabile(Integer responsabile, String software, Integer codiceoggetto) throws SecurityException {

	log.debug("checkDocumentoPerResponsabile {}-{}-{}", new Object[] { responsabile, software, codiceoggetto });
	boolean b = responsabilisoftwareService.checkByResponsabileAndSoftware(responsabile, software);
	if (!b) {
	    log.error("checkDocumentoPerResponsabile il responsabile {} non ha i permessi sul software {}", responsabile, software);
	    throw new SecurityException("Non si dispone dei permessi per recuperare la risorsa");
	}
	Set<String> softwaresOggetti = softwareByCodiceOggettoReaderService.findSoftwareByCodiceOggetto(codiceoggetto);
	//	softwaresOggetti.addAll(this.getSoftwareDiDocumentiAllegatiDaCodiceOggetto(codiceoggetto));
	//	softwaresOggetti.addAll(this.getSoftwareDiDocumentiIstanzaDaCodiceOggetto(codiceoggetto));
	if (softwaresOggetti.isEmpty()) {
	    // non sono riuscito a determinare il software ..... servo il file
	    log.error("checkDocumentoPerResponsabile {}-{}-{}, NON sono riuscito a recuperare il software dell'oggetto",
		    new Object[] { responsabile, software, codiceoggetto });
	    Oggetti o = oggettiService.findById(new PkId(codiceoggetto));
	    return o;
	}
	if (softwaresOggetti.contains(software)) {
	    Oggetti o = oggettiService.findById(new PkId(codiceoggetto));
	    return o;
	}
	log.error("checkDocumentoPerResponsabile {}-{}-{}, Non si dispone delle autorizzazioni per visualizzare la risorsa {}",
		new Object[] { responsabile, software, codiceoggetto, softwaresOggetti });
	throw new SecurityException("Non si dispone delle autorizzazioni per visualizzare la risorsa");
    }

    @Override
    public TIpoDocumentoDocumentiCondivisiMetadato findMetadatoProvenienzaDocumento(Integer codiceIstanza, Integer codiceMovimento,
	    Integer codiceOggetto) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile invocare il metodo DocumentiHelperServiceImpl.findMetadatoProvenienzaDocumento passando codiceIstanza null");
	}
	if (codiceOggetto == null) {
	    throw new IllegalArgumentException(
		    "Impossibile invocare il metodo DocumentiHelperServiceImpl.findMetadatoProvenienzaDocumento passando codiceOggetto null");
	}
	TIpoDocumentoDocumentiCondivisiMetadato retVal = findDocumentiCondivisiMetadatoDaMovimenti(codiceIstanza, codiceMovimento, codiceOggetto);
	if (retVal != null) {
	    return retVal;
	}
	retVal = findDocumentiCondivisiMetadatoDaProcure(codiceIstanza, codiceOggetto);
	if (retVal != null) {
	    return retVal;
	}
	retVal = findDocumentiCondivisiMetadatoDaEndoprocedimenti(codiceIstanza, codiceOggetto);
	if (retVal != null) {
	    return retVal;
	}
	return findDocumentiCondivisiMetadatoDaDocumentiIstanza(codiceIstanza, codiceOggetto);
    }

    private TIpoDocumentoDocumentiCondivisiMetadato findDocumentiCondivisiMetadatoDaMovimenti(Integer codiceIstanza, Integer codiceMovimento,
	    Integer codiceOggetto) {

	//verifico se l'oggetto è presente nel movimento passato ( opzionale )
	if (codiceMovimento != null) {
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiMovimenti = this.populateDocumentiMovimento(codiceMovimento,
		    TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : listDocumentiMovimenti) {
		List<MovimentiallegatiDTO> allegati = chiaveValoreBean.getValore();
		for (MovimentiallegatiDTO allegato : allegati) {
		    if (codiceOggetto.equals(allegato.getCodiceOggetto())) {
			return new TIpoDocumentoDocumentiCondivisiMetadato(allegato.getDescrizioneMovimento());
		    }
		}
	    }
	}
	//verifico se l'oggetto è in uno dei movimenti dell'istanza
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> listDocumentiAltriMovimenti = this.populateDocumentiAltriMovimenti(codiceIstanza,
		codiceMovimento, TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : listDocumentiAltriMovimenti) {
	    List<MovimentiallegatiDTO> allegati = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO allegato : allegati) {
		if (codiceOggetto.equals(allegato.getCodiceOggetto())) {
		    return new TIpoDocumentoDocumentiCondivisiMetadato(allegato.getDescrizioneMovimento());
		}
	    }
	}
	return null;
    }

    private TIpoDocumentoDocumentiCondivisiMetadato findDocumentiCondivisiMetadatoDaProcure(Integer codiceIstanza, Integer codiceOggetto) {

	//verifico se l'oggetto è presente tra le procure
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> listDocumentiProcure = this.populateProcure(codiceIstanza);
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : listDocumentiProcure) {
	    List<IstanzeprocureDTO> procure = chiaveValoreBean.getValore();
	    for (IstanzeprocureDTO procura : procure) {
		if (codiceOggetto.equals(procura.getCodiceOggetto())) {
		    return new TIpoDocumentoDocumentiCondivisiMetadato("Procura");
		}
	    }
	}
	return null;
    }

    private TIpoDocumentoDocumentiCondivisiMetadato findDocumentiCondivisiMetadatoDaEndoprocedimenti(Integer codiceIstanza, Integer codiceOggetto) {

	//verifico se l'oggetto è presente tra gli allegati degli endo
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = this.populateDocumentiEndoprocedimenti(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : listDocumentoEndo) {
	    List<IstanzeallegatiDTO> allegatiEndo = chiaveValoreBean.getValore();
	    for (IstanzeallegatiDTO allegato : allegatiEndo) {
		if (codiceOggetto.equals(allegato.getCodiceOggetto())) {
		    return new TIpoDocumentoDocumentiCondivisiMetadato(allegato.getProcedimento());
		}
	    }
	}
	return null;
    }

    private TIpoDocumentoDocumentiCondivisiMetadato findDocumentiCondivisiMetadatoDaDocumentiIstanza(Integer codiceIstanza, Integer codiceOggetto) {

	//verifico se l'oggetto è presente tra i documenti dell'istanza
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> listDocumentiIstanza = this.populateDocumentiIstanza(codiceIstanza, null,
		TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO, TipoFunzionalitaEnum.DEFAULT);
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : listDocumentiIstanza) {
	    List<DocumentiistanzaDTO> documentiIstanza = chiaveValoreBean.getValore();
	    for (DocumentiistanzaDTO allegato : documentiIstanza) {
		if (codiceOggetto.equals(allegato.getCodiceOggetto())) {
		    return new TIpoDocumentoDocumentiCondivisiMetadato(allegato.getDocumento());
		}
	    }
	}
	return null;
    }
}
