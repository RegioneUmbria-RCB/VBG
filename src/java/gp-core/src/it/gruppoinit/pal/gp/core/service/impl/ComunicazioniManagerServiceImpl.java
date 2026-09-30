package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipoComunicazioniTService;
import it.gruppoinit.pal.gp.core.service.TmpStatiComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

@Service
public class ComunicazioniManagerServiceImpl implements ComunicazioniManagerService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniDServiceImpl.class);
    private ComunicazioniDDAO comunicazioniDDAO;
    private ComunicazioniDService comunicazioniDService;
    private MovimentiService movimentiService;
    private TmpStatiComunicazioniDService tmpStatiComunicazioniDService;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;
    private AmministrazioniService amministrazioniService;
    private ComunicazioniTService comunicazioniTService;
    private ProtocollazioneService protocollazioneService;
    private MovimentiallegatiService movimentiallegatiService;
    private OggettiService oggettiService;
    private MovimentimailService movimentimailService;
    private DocumentMergeService documentMergeService;
    private MailConfigService mailConfigService;
    private MailtipoService mailtipoService;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private ResponsabiliService responsabiliService;
    private IstanzeService istanzeService;
    private TipoComunicazioniTService tipoComunicazioniTService;

    @Autowired
    public void setTipoComunicazioniTService(TipoComunicazioniTService tipoComunicazioniTService) {

	this.tipoComunicazioniTService = tipoComunicazioniTService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    @Autowired
    public void setDocumentMergeService(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setComunicazioniTService(ComunicazioniTService comunicazioniTService) {

	this.comunicazioniTService = comunicazioniTService;
    }

    @Autowired
    public void setComunicazioniDDAO(ComunicazioniDDAO comunicazioniDDAO) {

	this.comunicazioniDDAO = comunicazioniDDAO;
    }

    @Autowired
    public void setComunicazioniDService(ComunicazioniDService comunicazioniDService) {

	this.comunicazioniDService = comunicazioniDService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setTmpStatiComunicazioniDService(TmpStatiComunicazioniDService tmpStatiComunicazioniDService) {

	this.tmpStatiComunicazioniDService = tmpStatiComunicazioniDService;
    }

    @Override
    public void flush() {

	comunicazioniDDAO.flush();
    }

    @Override
    public void commit() {

	comunicazioniDDAO.commit();
    }

    @Override
    public void clear() {

	comunicazioniDDAO.clear();
    }

    @Override
    public void insertInizializzazioniStep(Integer codiceComT) {

	List<ComunicazioniD> liComunicazioniDs = comunicazioniDService.findByComunicazioniT(codiceComT);
	ComunicazioniT comunicazioniT = comunicazioniTService.findById(new PkId(codiceComT));
	List<TmpStatiComunicazioniD> listStepAttivi = createStepComunicazione(comunicazioniT);
	for (ComunicazioniD comunicazioniD : liComunicazioniDs) {
	    if (ComunicazioniDStatoEnum.NON_INIZIALIZZATA.value().equals(comunicazioniD.getStatoElaborazione())) {
		for (TmpStatiComunicazioniD tmpStatiComunicazioniD : listStepAttivi) {
		    tmpStatiComunicazioniD.setFkComunicazioniD(comunicazioniD.getId().getCodice());
		    tmpStatiComunicazioniDService.insert(tmpStatiComunicazioniD.getPosizione(), tmpStatiComunicazioniD.getFkComunicazioniD(),
			    tmpStatiComunicazioniD.getStato());
		}
		comunicazioniDService.upadateStato(comunicazioniD.getId().getCodice(), ComunicazioniDStatoEnum.INIZIALIZZATA);
	    }
	}
	comunicazioniDDAO.commit();
	comunicazioniDDAO.flush();
	comunicazioniDDAO.clear();
    }

    @Override
    public List<TmpStatiComunicazioniD> findStepNonEseguiti(Integer codiceComunicazioneD) {

	return tmpStatiComunicazioniDService.findByIdComunicazioned(codiceComunicazioneD);
    }

    @Override
    public void insertComunicazioniT(ComunicazioniT comunicazioniT) {

	comunicazioniTService.insert(comunicazioniT);
    }

    @Override
    public void updateComunicazioniT(ComunicazioniT comunicazioniT) {

	comunicazioniTService.update(comunicazioniT);
    }

    @Override
    public TipoComunicazioniT findTipoComunicazioniById(String codiceTipoComunicazione) {

	return tipoComunicazioniTService.findById(codiceTipoComunicazione);
    }

    @Override
    public void insertComunicazioneD(ComunicazioniD comunicazionid) {

	comunicazioniDDAO.update(comunicazionid);
    }

    @Override
    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum) {

	comunicazioniTService.updateStatoComunicazioneT(codiceComunicazioneT, comunicazioniTStatoEnum);
    }

    @Override
    public void updateStatoComunicazioneD(Integer codiceComunicazioneD, ComunicazioniDStatoEnum comunicazioniDStatoEnum) {

	comunicazioniDService.upadateStato(codiceComunicazioneD, comunicazioniDStatoEnum);
    }

    @Override
    public boolean exsistComunicazioniDNonTerminate(Integer codiceComunicazioneT) {

	List<ComunicazioniD> comunicazioniDs = comunicazioniDService.findByComunicazioniT(codiceComunicazioneT);
	for (ComunicazioniD comunicazioniD : comunicazioniDs) {
	    List<TmpStatiComunicazioniD> tmp = tmpStatiComunicazioniDService.findByIdComunicazioned(comunicazioniD.getId().getCodice());
	    for (TmpStatiComunicazioniD tmpStatiComunicazioniD : tmp) {
		if (!tmpStatiComunicazioniD.getStato().equals(ComunicazioniDStatoEnum.ELABORATA_TERMINATA)) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public void checkComunicazioniBloccateSuInvioEmail(Integer codiceCominicazioneT) {

	List<TmpStatiComunicazioniD> listComunicazionibloccateSuInvioEmail = tmpStatiComunicazioniDService
		.findComunicazioniBloccateInvioEmail(codiceCominicazioneT);
	for (TmpStatiComunicazioniD tmpStatiComunicazioniD : listComunicazionibloccateSuInvioEmail) {
	    ComunicazioniD comunicazionid = comunicazioniDService.findById(new PkId(tmpStatiComunicazioniD.getFkComunicazioniD()));
	    List<Movimentimail> movimentimails = movimentimailService.findByMovimento(comunicazionid.getMovimenti());
	    Movimentimail movimentiailDB = null;
	    if (!movimentimails.isEmpty()) {
		movimentiailDB = movimentimails.get(0);
		comunicazionid.setMovimentimail(movimentiailDB);
		// inserisco l'oggetto graduatoriedCom modificato
		comunicazioniDService.update(comunicazionid);
		tmpStatiComunicazioniDService.delete(tmpStatiComunicazioniD.getFkComunicazioniD(), PassoCreazioneComunicazioneEnum.INVIO_MAIL);
		log.debug("checkComunicazioniBloccateSuInvioEmail# Eliminato dalla coda degli step il passo = {}",
			PassoCreazioneComunicazioneEnum.INVIO_MAIL.toString());
		this.updateStatoComunicazioneD(tmpStatiComunicazioniD.getFkComunicazioniD(), ComunicazioniDStatoEnum.ELABORATA_TERMINATA);
		log.debug("checkComunicazioniBloccateSuInvioEmail# Inserito riferimento  movimenti mail  = {} in comunicazione d = {}",
			movimentiailDB.getId().getCodice(), comunicazionid.getId().getCodice());
	    }
	}
    }

    @Override
    public Istanze findIstanzaById(Integer codiceistanza) {

	return istanzeService.findById(new PkId(codiceistanza));
    }

    @Override
    public ComunicazioniD findComunicazioniDById(Integer codicecomunicazioned) {

	return comunicazioniDService.findById(new PkId(codicecomunicazioned));
    }

    @Override
    public ComunicazioniT findComunicazioniTById(Integer codicecomunicazionet) {

	return comunicazioniTService.findById(new PkId(codicecomunicazionet));
    }

    @Override
    public int eseguiStep(String stato, ComunicazioniD comunicazioniD, Istanze istanza, ComunicazioniT comunicazioniT,
	    ComunicazioniDHelper comunicazioniDHelper) {

	long t0 = System.currentTimeMillis();
	//	Integer codiceIstanza = comunicazioniDHelper.getCodiceIstanza();
	//	Integer codicecomunicazionid = comunicazioniDHelper.getCodiceComunicazioneD();
	String a = comunicazioniDHelper.getDestinatarioA();
	String cc = comunicazioniDHelper.getDescatanatioCc();
	PassoCreazioneComunicazioneEnum passo = PassoCreazioneComunicazioneEnum.valueOf(stato);
	//	ComunicazioniD comunicazioniD = comunicazioniDService.findById(new PkId(comunicazioniDHelper.getCodiceComunicazioneD()));
	//	Istanze istanza = istanzeService.findById(new PkId(comunicazioniDHelper.getCodiceIstanza()));
	//	ComunicazioniT comunicazioniT = comunicazioniD.getComunicazioniT();
	int i = 0;
	switch (passo) {
	case VERIFICA_PRESENZA_ISTANZA:
	    long t100 = System.currentTimeMillis();
	    i = this.insertStepVerificaPresenzaIstanza(istanza, comunicazioniD);
	    long t101 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t101 - t100));
	    break;
	case INSERT_MOVIMENTO:
	    long t1 = System.currentTimeMillis();
	    i = this.insertStepMovimento(comunicazioniD, istanza, comunicazioniT);
	    long t2 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t2 - t1));
	    break;
	case PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO:
	    long t3 = System.currentTimeMillis();
	    i = this.insertStepProtocolloMovimento(comunicazioniD,
		    PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO);
	    long t4 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t4 - t3));
	    break;
	case PROTOCOLLAZIONE_MOVIMENTO:
	    long t5 = System.currentTimeMillis();
	    i = this.insertStepProtocolloMovimento(comunicazioniD, PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO);
	    long t6 = System.currentTimeMillis();
	    log.debug("eseguiStep# {}, Time = {}", stato, (t6 - t5));
	    break;
	case CREAZIONE_ALLEGATO:
	    long t7 = System.currentTimeMillis();
	    i = this.insertStepMovimentoAllegato(comunicazioniD, comunicazioniT);
	    long t8 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t8 - t7));
	    break;
	case CONVERSIONE_PDF_ALLEGATO:
	    long t9 = System.currentTimeMillis();
	    i = this.insertStepconversioneInPDFAllegatoMovimento(comunicazioniD, comunicazioniT);
	    long t10 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t10 - t9));
	    break;
	case FIRMA_DOCUMENTI:
	    long t11 = System.currentTimeMillis();
	    i = this.insertStepVerificaFirma(comunicazioniD, istanza, comunicazioniT);
	    long t12 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t12 - t11));
	    break;
	case INVIO_MAIL:
	    long t13 = System.currentTimeMillis();
	    i = this.insertStepMailInviataDalMovimento(comunicazioniD, comunicazioniT, istanza, a, cc);
	    long t14 = System.currentTimeMillis();
	    log.debug("eseguiStep# Comunicazione = {}, Passo =  {}, Time = {}", stato, (t14 - t13));
	    break;
	case ELABORAZIONE_COMPLETA:
	    break;
	default:
	    break;
	}
	long t1000 = System.currentTimeMillis();
	log.debug("eseguiStep# END Comunicazione = {}, Time = {}", stato, (t1000 - t0));
	return i;
    }

    private int insertStepVerificaPresenzaIstanza(Istanze istanza, ComunicazioniD comunicazionid) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    log.error("insertStepMovimento# Errore  su step = {} comunicazioned = {} . Errore = {}",
		    new Object[] { PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA, comunicazionid.getId().getCodice(),
			    "Istanza non trovata. La tipologia di comunicazione richiede che vi sia un istanza collegata" });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(),
		    PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA,
		    "Istanza non trovata. La tipologia di comunicazione richiede che vi sia un istanza collegata");
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	    return 0;
	} else {
	    tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA);
	    log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
		    PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA.toString());
	}
	return 1;
    }

    //@Override
    private int insertStepMovimento(ComunicazioniD comunicazionid, Istanze istanza, ComunicazioniT comunicazioniT) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	Movimenti movimento = null;
	//ComunicazioniT comunicazioniT = comunicazionid.getComunicazioniT();
	try {
	    // Popolo il movimento che andrò ad inserire
	    movimento = popolateMovimento(comunicazioniT, istanza);
	    movimentiService.insert(movimento);
	    log.debug("insertStepMovimento# Movimento inserito = {}, su istanza = {}[{}]",
		    new Object[] { movimento.getId().getCodice(), movimento.getIstanza().getNumeroistanza(), movimento.getId().getCodice() });
	    // associo movimento craeto al dettaglio della comunicazione (comunicazionid)
	    comunicazionid.setMovimenti(movimento);
	    comunicazioniDService.update(comunicazionid);
	    tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO);
	    log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
		    PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO.toString());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	    return 1;
	} catch (Exception e) {
	    log.error("insertStepMovimento# Errore inatteso su step = {} comunicazioned = {}, tipo movimento = {} . Errore = {}[{}]",
		    new Object[] { PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO, comunicazionid.getId().getCodice(),
			    comunicazioniT.getTipimovimento().getId().getTipomovimento(), e.getMessage(), e });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO,
		    e.getMessage());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	return risultato;
    }

    //@Override
    private int insertStepProtocolloMovimento(ComunicazioniD comunicazionid, PassoCreazioneComunicazioneEnum passoCreazioneComunicazioneEnum) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	//ComunicazioniD comunicazionid = comunicazioniDService.findById(new PkId(codicecomunicazionid));
	Movimenti movimento = movimentiService.findById(new PkId(comunicazionid.getMovimenti().getId().getCodice()));
	log.debug("insertStepProtocolloMovimento# Protocollo movimento = {}", movimento.getId().getCodice());
	if (StringUtils.isNotBlank(movimento.getNumeroprotocollo())) {
	    comunicazionid.setMovimenti(movimento);
	    comunicazionid.setFlgProtocollato(Boolean.TRUE);
	    log.debug("insertStepProtocolloMovimento# aggiornata comunicazione = {}, protocollata = {}", comunicazionid.getId().getCodice(),
		    Boolean.TRUE);
	    comunicazioniDService.update(comunicazionid);
	    tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), passoCreazioneComunicazioneEnum);
	    log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}", passoCreazioneComunicazioneEnum.toString());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	    return 1;
	}
	try {
	    DatiProtocolloResponseType dpr = protocollazioneService.protocollaComunicazione(movimento, ORMHelper.getToken());
	    if (dpr != null) {
		if (dpr.getErrore() != null) {
		    log.error(
			    "insertStepProtocolloMovimento# Errore (prot) su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
			    new Object[] { passoCreazioneComunicazioneEnum.toString(), comunicazionid.getId().getCodice(),
				    comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(),
				    dpr.getErrore().getDescrizione(), dpr.getErrore().getStackTrace() });
		    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(), passoCreazioneComunicazioneEnum,
			    StringUtils.defaultIfEmpty(dpr.getErrore().getDescrizione(), "Errore non classificato"));
		    comunicazioniDDAO.commit();
		    comunicazioniDDAO.flush();
		    comunicazioniDDAO.clear();
		    return 0;
		} else {
		    log.debug(
			    "insertStepProtocolloMovimento# update dati protocollo su movimento = {}. Numero protocollo = {}, data protocollo = {}, Fkidprotocollo = {} ",
			    new Object[] { dpr.getNumeroProtocollo(),
				    Utilities.getDate(dpr.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN).getTime(), dpr.getIdProtocollo() });
		    Movimenti movTemp = movimentiService.findById(new PkId(movimento.getId().getCodice()));
		    movTemp.setNumeroprotocollo(dpr.getNumeroProtocollo());
		    movTemp.setDataprotocollo(Utilities.getDate(dpr.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN).getTime());
		    movTemp.setFkidprotocollo(dpr.getIdProtocollo());
		    // Viene utilizzato il metodo deprecato perchè evitiamo cosi di fare operazioni unitile in quetso caso 
		    // come chekprotocollo
		    movimentiService.update(movTemp);
		    comunicazionid.setMovimenti(movTemp);
		    comunicazionid.setFlgProtocollato(Boolean.TRUE);
		    log.debug("insertStepProtocolloMovimento# aggiornata comunicazione = {}, protocollata = {}", comunicazionid.getId().getCodice(),
			    Boolean.TRUE);
		    comunicazioniDService.update(comunicazionid);
		    tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), passoCreazioneComunicazioneEnum);
		    log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}", passoCreazioneComunicazioneEnum.toString());
		    comunicazioniDDAO.commit();
		    comunicazioniDDAO.flush();
		    comunicazioniDDAO.clear();
		    return 1;
		}
	    }
	} catch (Exception e) {
	    log.error(
		    "insertStepProtocolloMovimento# Errore inatteso su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
		    new Object[] { passoCreazioneComunicazioneEnum.toString(), comunicazionid.getId().getCodice(), comunicazionid.getId().getCodice(),
			    comunicazionid.getMovimenti().getIstanza().getId().getCodice(), e.getMessage(), e });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(), passoCreazioneComunicazioneEnum, e.getMessage());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	return risultato;
    }

    //@Override
    private int insertStepMovimentoAllegato(ComunicazioniD comunicazionid, ComunicazioniT comunicazioniT) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	//ComunicazioniD comunicazionid = comunicazioniDService.findById(new PkId(codicecomunicazionid));
	Movimenti movimento = movimentiService.findById(new PkId(comunicazionid.getMovimenti().getId().getCodice()));
	//	ComunicazioniT comunicazioniT = comunicazionid.getComunicazioniT();
	//	if (EntityUtils.getNestedProperty(comunicazioniT.getLetteretipo(), "id.codice") != null) {
	log.debug("insertStepMovimentoAllegato# Modello per allegato = {} ({}), Movimento = {}",
		new Object[] { comunicazioniT.getLetteretipo().getDescrizione(), comunicazioniT.getLetteretipo().getId().getCodice() });
	try {
	    Oggetti oggetto = documentMergeService.insertAllegatoDaDocumentoTipo(comunicazioniT.getLetteretipo().getId().getCodice(),
		    movimento.getIstanza().getId().getCodice(), movimento.getId().getCodice(), new DocumentMergeHelper());
	    log.debug("insertStepMovimentoAllegato# Oggetto allegato creato. Codice = {}, Nome file = {}", oggetto.getId().getCodice(),
		    oggetto.getNomefile());
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegati.setNote("Allegato generato automaticamente dalla procedura di comunicazione");
	    movimentiallegati.setDescrizione(oggetto.getNomefile());
	    movimentiallegatiService.insert(movimentiallegati);
	    log.debug("insertStepMovimentoAllegato# Movimento allegato creato. Codice = {}", movimentiallegati.getId().getCodice());
	    comunicazionid.setOggetti(oggetto);
	    comunicazioniDService.update(comunicazionid);
	    tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO);
	    log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
		    PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO.toString());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	    return 1;
	} catch (Exception e) {
	    log.error("insertStepMovimentoAllegato# Errore inatteso su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
		    new Object[] { PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO, comunicazionid.getId().getCodice(),
			    comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(), e.getMessage(), e });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(),
		    PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO, e.getMessage());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	//	} else {
	//	    log.error("insertStepMovimentoAllegato# Lettera tipo non presente per comunicazioniT : \"{}\"({})",
	//		    new Object[] { comunicazioniT.getDescrizione(), comunicazioniT.getId().getCodice() });
	//	    comunicazioniDService.insert(comunicazionid);
	//	    comunicazioniDDAO.flush();
	//	    comunicazioniDDAO.commit();
	//	    StringBuffer message = new StringBuffer("Creazione allegato  del movimento: '").append(movimento.getTipomovimento().getMovimento())
	//		    .append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]' non avvenuta");
	//	    log.error("insertStepMovimentoAllegato# Errore su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}",
	//		    new Object[] { PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO, comunicazionid.getId().getCodice(),
	//			    comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(), message });
	//	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(),
	//		    PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO, message.toString());
	//	    comunicazioniDDAO.flush();
	//	    comunicazioniDDAO.commit();
	//	}
	return risultato;
    }

    //@Override
    private int insertStepconversioneInPDFAllegatoMovimento(ComunicazioniD comunicazionid, ComunicazioniT comunicazioniT) {

	int risultato = 0;
	//ComunicazioniD comunicazionid = comunicazioniDService.findById(new PkId(codicecomunicazionid));
	Movimenti movimento = movimentiService.findById(new PkId(comunicazionid.getMovimenti().getId().getCodice()));
	//ComunicazioniT comunicazioniT = comunicazionid.getComunicazioniT();
	//if (BooleanUtils.isTrue(comunicazioniT.getFlgTrasformaPdf())) {
	//    if (EntityUtils.getNestedProperty(comunicazioniT.getLetteretipo(), "id.codice") != null) {
	log.debug("insertStepconversioneInPDFAllegatoMovimento# lettera tipo = {}", comunicazioniT.getLetteretipo().getId().getCodice());
	if (EntityUtils.getNestedProperty(comunicazionid.getOggetti(), "id.codice") != null) {
	    log.debug("insertStepconversioneInPDFAllegatoMovimento# oggetto = {}", comunicazionid.getOggetti().getId().getCodice());
	    try {
		Oggetti o = oggettiService.findByIdLazy(new PkId(comunicazionid.getOggetti().getId().getCodice()));
		if (o != null) {
		    if (o.getNomefile().toLowerCase().endsWith(".rtf") || o.getNomefile().toLowerCase().endsWith(".odt")) {
			FileConverterWsClient fileConverterWService = new FileConverterWsClient();
			o = oggettiService.findById(new PkId(o.getId().getCodice()));
			String estensione = "RTF";
			if (o.getNomefile().toLowerCase().endsWith(".odt")) {
			    estensione = "ODT";
			}
			ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), o.getOggetto(), estensione, "PDF");
			ConvertBinaryResponse s = fileConverterWService.convertBinary(cbr);
			byte[] res = s.getBinaryData();
			o.setOggetto(res);
			o.setNomefile(o.getNomefile() + ".pdf");
			oggettiService.update(o);
			tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(),
				PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO);
			log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
				PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO.toString());
			comunicazioniDDAO.commit();
			comunicazioniDDAO.flush();
			comunicazioniDDAO.clear();
			risultato = 1;
		    }
		}
		return risultato;
	    } catch (Exception e) {
		log.error(
			"insertStepconversioneInPDFAllegatoMovimento# Errore inatteso su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
			new Object[] { PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO, comunicazionid.getId().getCodice(),
				comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(), e.getMessage(),
				e });
		comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(),
			PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO, e.getMessage());
		comunicazioniDDAO.commit();
		comunicazioniDDAO.flush();
		comunicazioniDDAO.clear();
	    }
	} else {
	    StringBuffer message = new StringBuffer("Conversione dell' allegato  del movimento: '")
		    .append(movimento.getTipomovimento().getMovimento()).append("[").append(movimento.getTipomovimento().getId().getTipomovimento())
		    .append("]' non avvenuta");
	    log.error(
		    "insertStepconversioneInPDFAllegatoMovimento# Errore su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}",
		    new Object[] { PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO, comunicazionid.getId().getCodice(),
			    comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(), message.toString() });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(),
		    PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO, message.toString());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	//    }
	//}
	return risultato;
    }

    //@Override
    private int insertStepVerificaFirma(ComunicazioniD comunicazioniD, Istanze istanza, ComunicazioniT comunicazioniT) {

	//ComunicazioniD comunicazioniD = comunicazioniDService.findById(new PkId(codicecomunicazioniD));
	//ComunicazioniT comunicazioniT = comunicazioniD.getComunicazioniT();
	//Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	//	if (BooleanUtils.isTrue(comunicazioniT.getFlagMettiallafirma())) {
	log.debug("inserimentoMettiAllaFirmaPerLaComunicazione# Metti alla firma = {}", true);
	// allegato è stato generato
	// inserire record su metti alla firma con operatore correntemente loggato e lista dei responsabili presi da lista firmatari
	try {
	    Integer codiceOggetto = null;
	    if (EntityUtils.getNestedProperty(comunicazioniD.getOggetti(), "id.codice") != null) {
		codiceOggetto = comunicazioniD.getOggetti().getId().getCodice();
		log.debug("inserimentoMettiAllaFirmaPerLaComunicazione# Codice oggetto da firmare = {}", codiceOggetto);
	    } else {
		throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non è stato trovato il codiceoggetto");
	    }
	    if (StringUtils.isBlank(comunicazioniT.getListaFirmatari())) {
		throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non sono stati definiti i firmatari");
	    }
	    Movimentiallegati movallegato = movimentiallegatiService
		    .findMovimentiallegatiConOggettoByMovimenti(comunicazioniD.getMovimenti().getId().getCodice(), codiceOggetto);
	    if (movallegato == null) {
		throw new RuntimeException(
			"La comunicazione prevede la firma degli allegati generati ma non è stato trovato l'allegato del movimento");
	    }
	    List<Responsabili> resp = responsabiliService.findResponsabili(comunicazioniT.getListaFirmatari());
	    if (resp.isEmpty()) {
		throw new RuntimeException("La comunicazione prevede la firma degli allegati generati ma non sono stati trovati i firmatari");
	    }
	    boolean success = true;
	    StringBuffer messFirmatariMancanti = new StringBuffer("FIRME MANCANTI");
	    for (Responsabili responsabili : resp) {
		List<DocumentiDaFirmare> docs = documentiDaFirmareService.findByIdOggettoAndFirmatarioAndIstanza(codiceOggetto,
			responsabili.getId().getCodice(), istanza.getId().getCodice());
		if (!docs.isEmpty()) {
		    for (DocumentiDaFirmare doc : docs) {
			if (!documentiDaFirmareService.isFirmaCompleta(doc)) {
			    // messFirmatariMancanti = messFirmatariMancanti.append(doc.getFirmatario().getResponsabile()).append(",");
			    success = false;
			    break;
			}
		    }
		} else {
		    success = false;
		    DocumentiDaFirmare doc = new DocumentiDaFirmare();
		    doc.setDataRichiesta(Calendar.getInstance().getTime());
		    doc.setFirmatario(responsabili);
		    doc.setRichiedente((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
		    doc.setOggetti(comunicazioniD.getOggetti());
		    doc.setIstanze(istanza);
		    doc.setMovimentiallegati(movallegato);
		    doc.setFlagLetto(Boolean.FALSE);
		    documentiDaFirmareService.insert(doc, Boolean.FALSE);
		}
	    }
	    if (success) {
		tmpStatiComunicazioniDService.delete(comunicazioniT.getId().getCodice(), PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI);
		log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
			PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI.toString());
		return 1; //TUTTO OK
	    } else {
		log.error(
			"insertStepconversioneInPDFAllegatoMovimento# Errore su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}",
			new Object[] { PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI, comunicazioniD.getId().getCodice(),
				comunicazioniD.getId().getCodice(), comunicazioniD.getMovimenti().getIstanza().getId().getCodice(),
				"Firme mancanti" });
		comunicazioniDService.updateComunicazioniDConErrore(comunicazioniD.getId().getCodice(),
			PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI, messFirmatariMancanti.toString());
		comunicazioniDDAO.commit();
		comunicazioniDDAO.flush();
		comunicazioniDDAO.clear();
	    }
	} catch (Exception e) {
	    log.error(
		    "insertStepconversioneInPDFAllegatoMovimento# Errore inatteso su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
		    new Object[] { PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI, comunicazioniD.getId().getCodice(),
			    comunicazioniD.getId().getCodice(), comunicazioniD.getMovimenti().getIstanza().getId().getCodice(), e.getMessage(), e });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazioniD.getId().getCodice(), PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI,
		    e.getMessage());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	//	}
	return 0;
    }

    //@Override
    private int insertStepMailInviataDalMovimento(ComunicazioniD comunicazionid, ComunicazioniT comunicazioniT, Istanze istanza, String destinatarioA,
	    String destinatarioCc) {

	// valore 0, non si è verificato alcun errore
	int risultato = 0;
	//ComunicazioniD comunicazionid = comunicazioniDService.findById(new PkId(codicecomunicazionid));
	//ComunicazioniT comunicazioniT = comunicazionid.getComunicazioniT();
	Movimenti movimento = movimentiService.findById(new PkId(comunicazionid.getMovimenti().getId().getCodice()));
	//Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	try {
	    Movimentimail movimentimail = createMovimentiMail(comunicazioniT, istanza, movimento, comunicazionid, destinatarioA, destinatarioCc);
	    log.debug("inserimentoMailInviataDalMovimentoPerLaComunicazione# Movimento email creata ....");
	    movimentimailService.sendMail2(movimentimail, null, null);
	    log.debug("inserimentoMailInviataDalMovimentoPerLaComunicazione# Recupero le mail del movimento......");
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		log.debug("eseguiStep# errore durante Thread.sleep(1000");
	    }
	    List<Movimentimail> movimentimails = movimentimailService.findByMovimento(movimento);
	    Movimentimail movimentiailDB = null;
	    if (!movimentimails.isEmpty()) {
		movimentiailDB = movimentimails.get(0);
		comunicazionid.setMovimentimail(movimentiailDB);
		// inserisco l'oggetto graduatoriedCom modificato
		comunicazioniDService.update(comunicazionid);
		tmpStatiComunicazioniDService.delete(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.INVIO_MAIL);
		log.debug("insertStepMovimento# Eliminato dalla coda degli step il passo = {}",
			PassoCreazioneComunicazioneEnum.INVIO_MAIL.toString());
		comunicazioniDDAO.commit();
		comunicazioniDDAO.flush();
		comunicazioniDDAO.clear();
		log.debug("inserimentoMailInviataDalMovimentoPerLaComunicazione# Inserito riferimento  movimenti mail  = {} in comunicazione d = {}",
			movimentiailDB.getId().getCodice(), comunicazionid.getId().getCodice());
	    } else {
		StringBuffer message = new StringBuffer("Email non trovata per il movimento '").append(movimento.getTipomovimento().getMovimento())
			.append("[").append(movimento.getTipomovimento().getId().getTipomovimento()).append("]'");
		log.error(
			"inserimentoMailInviataDalMovimentoPerLaComunicazione# Errore  su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}",
			new Object[] { PassoCreazioneComunicazioneEnum.INVIO_MAIL, comunicazionid.getId().getCodice(),
				comunicazionid.getId().getCodice(), comunicazionid.getMovimenti().getIstanza().getId().getCodice(),
				message.toString() });
		comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.INVIO_MAIL,
			message.toString());
		comunicazioniDDAO.commit();
		comunicazioniDDAO.flush();
		comunicazioniDDAO.clear();
	    }
	} catch (Exception e) {
	    log.error(
		    "inserimentoMailInviataDalMovimentoPerLaComunicazione# Errore inatteso su step = {} comunicazioned = {}, movimento = {}, Istanza = {} . Errore = {}[{}]",
		    new Object[] { PassoCreazioneComunicazioneEnum.INVIO_MAIL, comunicazionid.getId().getCodice(), comunicazionid.getId().getCodice(),
			    comunicazionid.getMovimenti().getIstanza().getId().getCodice(), e.getMessage(), e });
	    comunicazioniDService.updateComunicazioniDConErrore(comunicazionid.getId().getCodice(), PassoCreazioneComunicazioneEnum.INVIO_MAIL,
		    e.getMessage());
	    comunicazioniDDAO.commit();
	    comunicazioniDDAO.flush();
	    comunicazioniDDAO.clear();
	}
	return risultato;
    }

    private Movimentimail createMovimentiMail(ComunicazioniT comunicazioniT, Istanze istanza, Movimenti movimento, ComunicazioniD comunicazioniD,
	    String destinatarioA, String destinatarioCc) {

	log.debug("createMovimentiMail# start .....");
	Movimentimail movimentimail = null;
	if (EntityUtils.getNestedProperty(comunicazioniT.getMailtipo(), "id.codice") != null) {
	    log.debug("createMovimentiMail# codice mail tipo = {}", comunicazioniT.getMailtipo().getId().getCodice());
	    movimentimail = new Movimentimail();
	    if (StringUtils.isNotBlank(destinatarioA)) {
		movimentimail.setDestinatario(destinatarioA);
		if (StringUtils.isNotBlank(destinatarioCc)) {
		    movimentimail.setDestinatariocc(destinatarioCc);
		}
		log.debug("createMovimentiMail# destinatario A= {}, destinatario Cc = {}", destinatarioA,
			StringUtils.defaultIfEmpty(destinatarioCc, "Non presente"));
		String emailMittente = "";
		MailConfig mailConfig = mailConfigService.findMailConfig();
		if (mailConfig != null) {
		    emailMittente = mailConfig.getSenderaddress();
		    movimentimail.setMittente(emailMittente);
		    log.debug("createMovimentiMail# Mittente = {}", emailMittente);
		} else {
		    log.error(
			    "createMovimentiMail# Non è stato possibile recuperare il mittente per l'invio mail. Controllare configurazione email per il modulo");
		    throw new RuntimeException(
			    "Non è stato possibile recuperare il mittente dell'eamil. Controllare le configurazioni email per il modulo");
		}
		movimentimail.setDatainvio(comunicazioniT.getData());
		log.debug("createMovimentiMail# data innvio = {}", Utilities.formatDate(comunicazioniT.getData(), false));
		movimentimail.setMovimento(movimento);
		Mailtipo mailtipoReplace = mailtipoService.replaceOggettoCorpo(comunicazioniT.getMailtipo(), istanza, movimento);
		if (StringUtils.isBlank(mailtipoReplace.getOggetto()) || StringUtils.isBlank(mailtipoReplace.getCorpo())) {
		    log.error(
			    "createMovimentiMail# Non è stato possibile generare l'oggetto o il corpo della mail, i campi sono obbligatori per l'invio mail. " +
			      "Controllare la mailtipo utilizzata. Codice mail tipo = {} ",
			    mailtipoReplace.getId().getCodice());
		    throw new RuntimeException("Non è stato possibile costruire l'oggetto e/o il corpo dell'email");
		} else {
		    movimentimail.setOggetto(mailtipoReplace.getOggetto());
		    movimentimail.setCorpo(mailtipoReplace.getCorpo());
		    log.debug("createMovimentiMail# popolati oggetto e corpo email...");
		}
		if (EntityUtils.getNestedProperty(comunicazioniD.getOggetti(), "id.codice") != null) {
		    Oggetti o = comunicazioniD.getOggetti();
		    // Devo recuperare l'allegato creato per inserire l'oggetto dell'allegato in graduatoriedCom
		    Movimentiallegati movimentomail = movimentiallegatiService
			    .findMovimentiallegatiConOggettoByMovimenti(movimento.getId().getCodice(), o.getId().getCodice());
		    if (movimentomail != null) {
			Set<Movimentimailallegati> movimentimailallegatis = new HashSet<Movimentimailallegati>();
			Movimentimailallegati movimentimailallegati = new Movimentimailallegati();
			movimentimailallegati.setMovimentimail(movimentimail);
			Oggetti oggetto = oggettiService.findById(new PkId(movimentomail.getOggetto().getId().getCodice()));
			movimentimailallegati.setOggetto(oggetto);
			movimentimailallegati.setDocumento(movimentomail.getDescrizione());
			movimentimailallegatis.add(movimentimailallegati);
			movimentimail.setMovimentimailallegatis(movimentimailallegatis);
		    }
		}
	    } else {
		log.error("Mail non creata per il movimento codice = {}, impossibile inviarla, Destinatario dell' email non presente",
			movimento.getId().getCodice());
		throw new RuntimeException("Impossibile ricavare il destinatario dell'email");
	    }
	} else {
	    log.error("Mail non creata per il movimento codice = {}, template mail non configurato in comunicazioni t = {}",
		    movimento.getId().getCodice(), comunicazioniT.getId().getCodice());
	    throw new RuntimeException("Template non configurato in comunicazioni t");
	}
	return movimentimail;
    }

    private List<TmpStatiComunicazioniD> createStepComunicazione(ComunicazioniT comunicazioniT) {

	log.debug("createStepComunicazione# Creo gli step della comunicazione....");
	List<TmpStatiComunicazioniD> list = new ArrayList<TmpStatiComunicazioniD>();
	Integer posizione = 1;
	TmpStatiComunicazioniD stepPresenzaIstanza = new TmpStatiComunicazioniD();
	if (BooleanUtils.toBoolean(comunicazioniT.getFlagPrevedePresenzaIstanza()) == true) {
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA.toString());
	    stepPresenzaIstanza.setPosizione(posizione);
	    posizione++;
	    stepPresenzaIstanza.setStato(PassoCreazioneComunicazioneEnum.VERIFICA_PRESENZA_ISTANZA.toString());
	    list.add(stepPresenzaIstanza);
	}
	TmpStatiComunicazioniD stepInsertMovimento = new TmpStatiComunicazioniD();
	if (EntityUtils.getNestedProperty(comunicazioniT.getTipimovimento(), "id.tipomovimento") != null) {
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO.toString());
	    stepInsertMovimento.setPosizione(posizione);
	    posizione++;
	    stepInsertMovimento.setStato(PassoCreazioneComunicazioneEnum.INSERT_MOVIMENTO.toString());
	    list.add(stepInsertMovimento);
	}
	TmpStatiComunicazioniD stepProtPrimareazioneAllegato = new TmpStatiComunicazioniD();
	if (BooleanUtils.toBoolean(comunicazioniT.getFlgProtocolla()) == true && comunicazioniT.getProtDopoCreazioneAllegato() == 0) {
	    log.debug("createStepComunicazione# Passo {}: {}", posizione,
		    PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO.toString());
	    stepProtPrimareazioneAllegato.setPosizione(posizione);
	    posizione++;
	    stepProtPrimareazioneAllegato.setStato(PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO.toString());
	    list.add(stepProtPrimareazioneAllegato);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione,
		    PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO.toString());
	}
	TmpStatiComunicazioniD stepCreazioneAllegato = new TmpStatiComunicazioniD();
	if (EntityUtils.getNestedProperty(comunicazioniT.getLetteretipo(), "id.codice") != null) {
	    stepCreazioneAllegato.setPosizione(posizione);
	    posizione++;
	    stepCreazioneAllegato.setStato(PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO.toString());
	    list.add(stepCreazioneAllegato);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.CREAZIONE_ALLEGATO.toString());
	}
	TmpStatiComunicazioniD stepTrasformaInPdf = new TmpStatiComunicazioniD();
	if (BooleanUtils.toBoolean(comunicazioniT.getFlgTrasformaPdf()) == true) {
	    stepTrasformaInPdf.setPosizione(posizione);
	    posizione++;
	    stepTrasformaInPdf.setStato(PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO.toString());
	    list.add(stepTrasformaInPdf);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.CONVERSIONE_PDF_ALLEGATO.toString());
	}
	TmpStatiComunicazioniD stepMettiAllaFirma = new TmpStatiComunicazioniD();
	if (BooleanUtils.isTrue(comunicazioniT.getFlagMettiallafirma())) {
	    stepMettiAllaFirma.setPosizione(posizione);
	    posizione++;
	    stepMettiAllaFirma.setStato(PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI.toString());
	    list.add(stepMettiAllaFirma);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.FIRMA_DOCUMENTI.toString());
	}
	TmpStatiComunicazioniD stepProtocollaMovimento = new TmpStatiComunicazioniD();
	if (BooleanUtils.toBoolean(comunicazioniT.getFlgProtocolla()) == true && comunicazioniT.getProtDopoCreazioneAllegato() == 1) {
	    stepProtocollaMovimento.setPosizione(posizione);
	    posizione++;
	    stepProtocollaMovimento.setStato(PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO.toString());
	    list.add(stepProtocollaMovimento);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.PROTOCOLLAZIONE_MOVIMENTO.toString());
	}
	TmpStatiComunicazioniD stepInvioEmail = new TmpStatiComunicazioniD();
	if (EntityUtils.getNestedProperty(comunicazioniT.getMailtipo(), "id.codice") != null) {
	    stepInvioEmail.setPosizione(posizione);
	    posizione++;
	    stepInvioEmail.setStato(PassoCreazioneComunicazioneEnum.INVIO_MAIL.toString());
	    list.add(stepInvioEmail);
	    log.debug("createStepComunicazione# Passo {}: {}", posizione, PassoCreazioneComunicazioneEnum.INVIO_MAIL.toString());
	}
	return list;
    }

    private Movimenti popolateMovimento(ComunicazioniT comunicazioniT, Istanze istanza) {

	log.debug("popolateMovimento# start ....");
	Movimenti movimento = new Movimenti();
	log.debug("popolateMovimento# Tipo movimento = {}", new Object[] { comunicazioniT.getTipimovimento().getId().getTipomovimento() });
	movimento.setTipomovimento(comunicazioniT.getTipimovimento());
	log.debug("popolateMovimento# Istanza = {} ({})", new Object[] { istanza.getNumeroistanza(), istanza.getId().getCodice() });
	movimento.setIstanza(istanza);
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	log.debug("popolateMovimento# Responsabile (Utente loggato) = {} ({})....",
		new Object[] { responsabile.getResponsabile(), responsabile.getId().getCodice() });
	movimento.setResponsabile(responsabile);
	log.debug("popolateMovimento# Data = {}", new Object[] { Utilities.formatDate(comunicazioniT.getData(), false) });
	movimento.setData(comunicazioniT.getData());
	if (EntityUtils.getNestedProperty(comunicazioniT.getAmministrazioni(), "id.codice") != null) {
	    log.debug("popolateMovimento# Amministrazione (da configurazione web) = {} ",
		    new Object[] { comunicazioniT.getAmministrazioni().getAmministrazione() });
	    movimento.setAmministrazioni(comunicazioniT.getAmministrazioni());
	} else {
	    Verticalizzazioniparametri amministrazioneprotocollo = verticalizzazioniService.getVerticalizzazioniparametri(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT);
	    if (amministrazioneprotocollo != null && amministrazioneprotocollo.getValore() != null) {
		log.debug("popolateMovimento# Amministrazione (da {}.{}) = {} ",
			new Object[] { VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
				VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT,
				amministrazioneprotocollo.getValore() });
		Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(amministrazioneprotocollo.getValore())));
		movimento.setAmministrazioni(amministrazioni);
	    } else {
		log.debug("popolateMovimento# Nessuna amministrazione trovata");
	    }
	}
	Date toDate = new Date();
	log.debug("popolateMovimento# Data inserimento = {}", new Object[] { Utilities.formatDate(toDate, false) });
	movimento.setDatainserimento(toDate);
	log.debug("insertComunicazioneDettaglio# end....");
	return movimento;
    }
}
