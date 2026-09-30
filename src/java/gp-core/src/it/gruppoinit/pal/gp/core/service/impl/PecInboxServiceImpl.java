/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.PecInboxAllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.PecInboxDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxAllegati;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.PECAttachmentHelper;
import it.gruppoinit.pal.gp.core.domain.web.PECInboxFilter;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

/**
 * @author francol
 * 
 */
@Service
public class PecInboxServiceImpl extends BaseServiceImpl<PecInbox, PecInboxId> implements PecInboxService {

    private static final Logger log = LoggerFactory.getLogger(PecInboxServiceImpl.class);
    private static final String PECINBOX_TEMP_UNZIP_DIR = "TEMP_PECUNZIP";
    private PecInboxDAO pecInboxDAO;
    private PecInboxAllegatiDAO pecInboxAllegatiDAO;
    private OggettiService oggettiService;
    private UserSecurityService userSecurityService;

    public PecInboxDAO getPecInboxDAO() {

	return pecInboxDAO;
    }

    @Autowired
    public void setPecInboxDAO(PecInboxDAO pecInboxDAO) {

	this.pecInboxDAO = pecInboxDAO;
    }

    public PecInboxAllegatiDAO getPecInboxAllegatiDAO() {

	return pecInboxAllegatiDAO;
    }

    @Autowired
    public void setPecInboxAllegatiDAO(PecInboxAllegatiDAO pecInboxAllegatiDAO) {

	this.pecInboxAllegatiDAO = pecInboxAllegatiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    public void insert(PecInbox entity) {

	if (validateEntity(entity)) {
	    getPecInboxDAO().insert(entity);
	}
    }

    @Override
    public void update(PecInbox entity) {

	if (validateEntity(entity)) {
	    getPecInboxDAO().update(entity);
	}
    }

    @Override
    public void delete(PecInbox entity) {

	getPecInboxDAO().delete(entity);
    }

    @Override
    public List<PecInbox> findAll(Integer firstResult, Integer maxResult) {

	return getPecInboxDAO().findAll(firstResult, maxResult);
    }

    @Override
    public PecInbox findById(PecInboxId id) {

	return getPecInboxDAO().findById(id);
    }

    @Override
    protected Class<PecInbox> getEntityClass() {

	return PecInbox.class;
    }

    @Override
    public String findProtocolloOggetto(PecInbox pec) {

	//TODO implementare logiche specifiche per la composizione dell'oggetto del protocollo a partire dalla PEC
	return pec.getPecSubject();
    }

    @Override
    public void salvaOggettoProtocollo(PecInbox pec, byte[] bytesPec, String nomeFile) {

	if (bytesPec != null) {
	    Oggetti pecObj = new Oggetti();
	    pecObj.setOggetto(bytesPec);
	    pecObj.setNomefile(nomeFile);
	    this.oggettiService.insert(pecObj);
	    pecInboxDAO.flush();
	    pec.setOggettoProtocollo(pecObj);
	    this.pecInboxDAO.update(pec);
	    pecInboxDAO.flush();
	}
    }

    @Override
    public String getNlaGestioneMailWSURL() {

	return WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_NLAPEC);
	//return "http://devel9:8080/nla-pec/services/nlaGestioneMail.wsdl";
    }

    @Override
    public void elaboraAllegatiPec(PecInbox pec, PECAttachmentHelper corpoPec, List<PECAttachmentHelper> allegatiWS) {

	// §§§BEGIN§§§
	if (null != pec && null != allegatiWS) {
	    if (null != corpoPec) {
		allegatiWS = new ArrayList<PECAttachmentHelper>(allegatiWS);
		allegatiWS.add(0, corpoPec);
	    }
	    for (int i = 0; i < allegatiWS.size(); i++) {
		PECAttachmentHelper allH = allegatiWS.get(i);
		Boolean isCorpo = null != corpoPec && i == 0;
		Oggetti oggettoAllegato = new Oggetti();
		oggettoAllegato.setNomefile(allH.getNomeFile());
		oggettoAllegato.setOggetto(allH.getBinaryContent());
		oggettoAllegato.setDimensioneFile(allH.getBinaryContent().length);
		this.oggettiService.insert(oggettoAllegato);
		PecInboxAllegati allegato = new PecInboxAllegati();
		allegato.setPec(pec);
		allegato.setOggetto(oggettoAllegato);
		allegato.setFlagCorpo(isCorpo);
		this.pecInboxAllegatiDAO.insert(allegato);
		if (log.isDebugEnabled()) {
		    log.debug("elaboraAllegatiPec() - l'allegato {} della PEC {} è stato salvato nella tabella OGGETTI con codice {}", new Object[] {
			    allH.getNomeFile(), pec.getId().getId(), oggettoAllegato.getId().getCodice() });
		}
		//imposto nell'helper il riferimento al codice oggetto appena salvato nel DB.
		allH.setCodiceOggetto(oggettoAllegato.getId().getCodice());
	    }
	}
	// §§§END§§§
    }

    @Override
    public List<PecInboxAllegati> findAllegatiPec(PecInbox pec) {

	List<PecInboxAllegati> allegati = new ArrayList<PecInboxAllegati>();
	// §§§BEGIN§§§
	if (null != allegati && null != pec.getId()) {
	    allegati = pecInboxAllegatiDAO.findAllegatiPec(pec.getId().getId());
	}
	// §§§END§§§
	return allegati;
    }

    @Override
    public void associaPecAIstanza(PecInbox pec, Istanze istanza) {

	// §§§BEGIN§§§
	pec.setIstanze(istanza);
	pec.setResponsabili(null);
	if (StringUtils.isEmpty(pec.getIdprotocollo()) && StringUtils.isNotEmpty(istanza.getFkidprotocollo())) {
	    //se l'istanza è stata protocollata in automatico riporto i riferimenti del protocollo nella PEC
	    pec.setIdprotocollo(istanza.getFkidprotocollo());
	    pec.setNumeroprotocollo(istanza.getNumeroprotocollo());
	    pec.setDataprotocollo(istanza.getDataprotocollo());
	}
	this.pecInboxDAO.update(pec);
	// §§§END§§§
    }

    @Override
    public void associaPecAMovimento(PecInbox pec, Movimenti movimento) {

	// §§§BEGIN§§§
	pec.setMovimenti(movimento);
	pec.setResponsabili(null);
	this.pecInboxDAO.update(pec);
	if (log.isDebugEnabled()) {
	    log.debug("associaPecAMovimento - la pec {} è stata associata al movimento n°{}, id={}.",
		    new Object[] { pec.getId().getId(), movimento.getMovimento(), movimento.getId().getCodice() });
	}
	// §§§END§§§
    }

    @Override
    public List<PecInbox> findByDataRicezione(Date da, Date a) {

	// §§§BEGIN§§§
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (null != da) {
	    FilterRestriction daRest = new FilterRestriction();
	    daRest.addFilterField(FilterUtils.greaterEqual("pecDate", da, Date.class));
	    ft.addRestriction(daRest);
	}
	if (null != a) {
	    FilterRestriction aRest = new FilterRestriction();
	    aRest.addFilterField(FilterUtils.smallerEqual("pecDate", a, Date.class));
	    ft.addRestriction(aRest);
	}
	ft.addOrder(FilterUtils.orderDesc("pecDate"));
	List<PecInbox> pecs = this.pecInboxDAO.findByFilterTable(ft);
	return pecs;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<PecInbox> findByDataRicezioneFlagLettaMittenteOggettoProtocolloAndSort(PECInboxFilter pecFilter, List<FilterOrder> sortBy,
	    Integer firstRow, Integer maxRows) {

	// §§§BEGIN§§§
	FilterTable ft = buildFilterTableForPecInbox(pecFilter);
	if (sortBy != null && sortBy.size() > 0) {
	    for (FilterOrder orderBy : sortBy) {
		ft.addOrder(orderBy);
	    }
	} else {
	    ft.addOrder(FilterUtils.orderDesc("pecDate"));
	}
	List<PecInbox> pecs = this.pecInboxDAO.findByFilterTable(ft, firstRow, maxRows);
	return pecs;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countByDataRicezioneFlagLettaMittenteOggettoProtocollo(PECInboxFilter pecFilter) {

	FilterTable ft = buildFilterTableForPecInbox(pecFilter);
	return this.pecInboxDAO.countRecord(ft);
    }

    private FilterTable buildFilterTableForPecInbox(PECInboxFilter pecFilter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (null != pecFilter.getDataRicezioneDa()) {
	    //già arrotondato hh:mm:ss a 00:00:00
	    FilterRestriction daRest = new FilterRestriction();
	    daRest.addFilterField(FilterUtils.greaterEqual("pecDate", pecFilter.getDataRicezioneDa(), Date.class));
	    ft.addRestriction(daRest);
	}
	// FIXME è giusto
	//	if (pecFilter.getIdMailConfig() != null) {
	//	    log.debug("buildFilterTableForPecInbox# Imposto la find per idAccount = {}", pecFilter.getIdMailConfig());
	//	    FilterRestriction frAccount = new FilterRestriction();
	//	    frAccount.addFilterField(FilterUtils.equals("id.codice", pecFilter.getIdMailConfig(), "mailConfig", Integer.class));
	//	    ft.addRestriction(frAccount);
	//	} else {
	//	    if (StringUtils.isNotEmpty(pecFilter.getMailAccount())) {
	//		FilterRestriction protocolloRest = new FilterRestriction();
	//		protocolloRest.addFilterField(FilterUtils.equals("inLoginname", pecFilter.getMailAccount(), String.class));
	//		ft.addRestriction(protocolloRest);
	//	    }
	//	}
	if (StringUtils.isNotEmpty(pecFilter.getMailAccount())) {
	    FilterRestriction protocolloRest = new FilterRestriction();
	    protocolloRest.addFilterField(FilterUtils.equals("inLoginname", pecFilter.getMailAccount(), String.class));
	    ft.addRestriction(protocolloRest);
	}
	if (null != pecFilter.getDataRicezioneA()) {
	    //arrotondamento hh:mm:ss a 23:59:59
	    Date dataA = pecFilter.getDataRicezioneA();
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(dataA);
	    gc.set(Calendar.HOUR, 23);
	    gc.set(Calendar.MINUTE, 59);
	    gc.set(Calendar.SECOND, 59);
	    gc.set(Calendar.MILLISECOND, 999);
	    dataA = gc.getTime();
	    FilterRestriction aRest = new FilterRestriction();
	    aRest.addFilterField(FilterUtils.smallerEqual("pecDate", dataA, Date.class));
	    ft.addRestriction(aRest);
	}
	if (!(pecFilter.getFlagLetti() && pecFilter.getFlagNonLetti())) {
	    if (pecFilter.getFlagLetti()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.equals("flagLetta", Boolean.TRUE, Boolean.class));
		ft.addRestriction(rest);
	    } else if (pecFilter.getFlagNonLetti()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.equals("flagLetta", Boolean.FALSE, Boolean.class));
		rest.setAndOrRestriction(AndOrRestriction.OR);
		rest.addFilterField(FilterUtils.isNull("flagLetta"));
		ft.addRestriction(rest);
	    }
	}
	if (!(pecFilter.getFlagElaborati() && pecFilter.getFlagNonElaborati())) {
	    if (pecFilter.getFlagElaborati()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.equals("flagProcessata", Boolean.TRUE, Boolean.class));
		ft.addRestriction(rest);
	    } else if (pecFilter.getFlagNonElaborati()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.equals("flagProcessata", Boolean.FALSE, Boolean.class));
		rest.setAndOrRestriction(AndOrRestriction.OR);
		rest.addFilterField(FilterUtils.isNull("flagProcessata"));
		ft.addRestriction(rest);
	    }
	}
	if (!(pecFilter.getFlagLavorati() && pecFilter.getFlagNonLavorati())) {
	    if (pecFilter.getFlagLavorati()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.isNotNull("id.codice", "istanze"));
		rest.setAndOrRestriction(AndOrRestriction.OR);
		rest.addFilterField(FilterUtils.isNotNull("id.codice", "movimenti"));
		rest.addFilterField(FilterUtils.isNotNull("numeroprotocollo"));
		ft.addRestriction(rest);
	    } else if (pecFilter.getFlagNonLavorati()) {
		FilterRestriction rest = new FilterRestriction();
		rest.addFilterField(FilterUtils.isNull("id.codice", "istanze"));
		rest.setAndOrRestriction(AndOrRestriction.AND);
		rest.addFilterField(FilterUtils.isNull("id.codice", "movimenti"));
		rest.addFilterField(FilterUtils.isNull("numeroprotocollo"));
		ft.addRestriction(rest);
	    }
	}
	if (!pecFilter.getFlagRicevute()) {
	    //TODO aggiungere una nuova colonna in PEC_INBOX.FLAG_RICEVUTA e fare filtro su quella
	    FilterRestriction noRicevuteRest = new FilterRestriction();
	    noRicevuteRest.addFilterField(FilterUtils.notStartsWith("pecSubject", "CONSEGNA:"));
	    noRicevuteRest.setAndOrRestriction(AndOrRestriction.AND);
	    noRicevuteRest.addFilterField(FilterUtils.notStartsWith("pecSubject", "ACCETTAZIONE:"));
	    ft.addRestriction(noRicevuteRest);
	}
	if (StringUtils.isNotEmpty(pecFilter.getMittente())) {
	    FilterRestriction mittenteRest = new FilterRestriction();
	    mittenteRest.addFilterField(FilterUtils.like("pecFrom", pecFilter.getMittente()));
	    ft.addRestriction(mittenteRest);
	}
	if (StringUtils.isNotEmpty(pecFilter.getOggetto())) {
	    FilterRestriction oggettoRest = new FilterRestriction();
	    oggettoRest.addFilterField(FilterUtils.like("pecSubject", pecFilter.getOggetto()));
	    ft.addRestriction(oggettoRest);
	}
	if (StringUtils.isNotEmpty(pecFilter.getNumeroProtocollo())) {
	    FilterRestriction protocolloRest = new FilterRestriction();
	    protocolloRest.addFilterField(FilterUtils.startsWith("numeroprotocollo", pecFilter.getNumeroProtocollo()));
	    ft.addRestriction(protocolloRest);
	}
	if (StringUtils.isNotEmpty(pecFilter.getDestinatarioCC())) {
	    FilterRestriction destinatariRest = new FilterRestriction();
	    destinatariRest.addFilterField(FilterUtils.like("pecToCC", pecFilter.getDestinatarioCC()));
	    ft.addRestriction(destinatariRest);
	}
	return ft;
    }

    @Override
    public void contrassegnaPecCancellata(PecInbox pec, boolean annullaResponsabile) {

	// §§§BEGIN§§§
	pec.setFlagCancellata(true);
	if (annullaResponsabile) {
	    pec.setResponsabili(null);
	}
	this.pecInboxDAO.update(pec);
	if (log.isDebugEnabled()) {
	    log.debug("contrassegnaPecCancellata - la pec avente codice {} è stata contrassegnata come cancellata dal server di posta.",
		    new Object[] { pec.getId().getId() });
	    if (annullaResponsabile) {
		log.debug("contrassegnaPecCancellata - la pec avente codice {} non è più assegnata a nessun responsabile.", new Object[] { pec
			.getId().getId() });
	    }
	}
	// §§§END§§§
    }

    @Override
    public List<PecInbox> findByMovimento(Integer codiceMovimento, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceMovimento == null) {
	    throw new BusinessValidationException("Il parametro codice movimento non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction aRest = new FilterRestriction();
	aRest.addFilterField(FilterUtils.equals("movimentiId", codiceMovimento, Integer.class));
	ft.addRestriction(aRest);
	ft.addOrder(FilterUtils.orderDesc("pecDate"));
	List<PecInbox> pecs = this.pecInboxDAO.findByFilterTable(ft, firstResult, maxResult);
	return pecs;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<PecInbox> findByIstanza(Integer codiceIstanza, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceIstanza == null) {
	    throw new BusinessValidationException("Il parametro codice istanza non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction aRest = new FilterRestriction();
	aRest.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	ft.addRestriction(aRest);
	ft.addOrder(FilterUtils.orderDesc("pecDate"));
	List<PecInbox> pecs = this.pecInboxDAO.findByFilterTable(ft, firstResult, maxResult);
	return pecs;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Date getLastSynchronizationDate(String mailAccount) {

	Date lastSyncDAte = null;
	if (StringUtils.isNotBlank(mailAccount)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction aRest = new FilterRestriction();
	    aRest.addFilterField(FilterUtils.equals("inLoginname", mailAccount, Integer.class));
	    ft.addRestriction(aRest);
	    FilterRestriction soloPostaCertificata = new FilterRestriction();
	    soloPostaCertificata.addFilterField(FilterUtils.startsWith("pecSubject", "POSTA CERTIFICATA:"));
	    ft.addRestriction(soloPostaCertificata);
	    lastSyncDAte = (Date) this.pecInboxDAO.max(ft, "pecDate");
	    if (lastSyncDAte == null) {
		ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		aRest = new FilterRestriction();
		aRest.addFilterField(FilterUtils.equals("inLoginname", mailAccount, Integer.class));
		ft.addRestriction(aRest);
		lastSyncDAte = (Date) this.pecInboxDAO.max(ft, "pecDate");
	    }
	}
	return lastSyncDAte;
    }

    @Override
    public List<Oggetti> getAllegatiPECPerIstanzaoMovimento(Collection<PecInboxAllegati> allegatiPec, boolean scompatta) {

	List<Oggetti> retDocs = new ArrayList<Oggetti>();
	if (allegatiPec != null && !allegatiPec.isEmpty()) {
	    File tempDir = Utilities.getSystemTempDir();
	    tempDir = new File(tempDir, PECINBOX_TEMP_UNZIP_DIR);
	    //se è richiesto di scopattare degli archivi compressi allora creo una directory temporanea specifica per questa PEC 
	    // che alla fiine della chiamata sarà cancellata
	    if (scompatta) {
		if (!tempDir.isDirectory()) {
		    tempDir.mkdir();
		}
	    }
	    File unpackDir = null;
	    List<File> uncompressed = null;
	    for (PecInboxAllegati pecAttachment : allegatiPec) {
		Oggetti doc = pecAttachment.getOggetto();
		if (null != doc && doc.getId() != null && doc.getId().getCodice() != null) {
		    if (scompatta) {
			uncompressed = null;
			doc = this.oggettiService.findById(new PkId(doc.getId().getCodice()));
			if (StringUtils.defaultIfEmpty(doc.getNomefile(), "").toLowerCase().endsWith(".odt") == false) {
			    ByteArrayInputStream bais = new ByteArrayInputStream(doc.getOggetto());
			    // gli ODT sono a tutti gli effetti degli ZIP e li escludo dalla ricerca
			    try {
				if (Utilities.isZipFile(bais)) {
				    try {
					unpackDir = new File(tempDir, Utilities.cleanFilename(doc.getId().getCodice().toString()));
					if (!unpackDir.isDirectory()) {
					    unpackDir.mkdir();
					    if (log.isDebugEnabled()) {
						log.debug(
							"getAllegatiPECPerIstanzaoMovimento - creata directory temporanea {} per la decompressione dell' archivio ZIP: {}",
							new Object[] { unpackDir.getAbsolutePath(), doc.getNomefile() });
					    }
					}
					uncompressed = Utilities.unzipToDirAndReturnEntries(new ByteArrayInputStream(doc.getOggetto()), unpackDir);
				    } catch (IOException e) {
					log.error(
						"getAllegatiPECPerIstanzaoMovimento - si è verificato un errore durante lo scompattamento dell'archivio ZIP {}, il file sarà trattato come se non fosse un file zip. Errore: {}",
						new Object[] { doc.getNomefile(), e.getMessage() });
				    }
				} else if (Utilities.isRarFile(new ByteArrayInputStream(doc.getOggetto()))) {
				    OutputStream fos = null;
				    try {
					unpackDir = new File(tempDir, Utilities.cleanFilename(doc.getId().getCodice().toString()));
					if (!unpackDir.isDirectory()) {
					    unpackDir.mkdir();
					    if (log.isDebugEnabled()) {
						log.debug(
							"getAllegatiPECPerIstanzaoMovimento - creata directory temporanea {} per la decompressione dell' archivio RAR: {}",
							new Object[] { unpackDir.getAbsolutePath(), doc.getNomefile() });
					    }
					}
					//copia locale del file RAR: per leggere direttamente il bytes array occorre realizzare implementazioni di Volume e VolumeMAnager basate du byte array
					File rarFile = new File(unpackDir, Utilities.cleanFilename(doc.getNomefile()));
					if (!rarFile.isFile()) {
					    rarFile.createNewFile();
					}
					fos = new FileOutputStream(rarFile);
					IOUtils.copy(new ByteArrayInputStream(doc.getOggetto()), fos);
					fos.close();
					if (log.isDebugEnabled()) {
					    log.debug("getAllegatiPECPerIstanzaoMovimento - file RAR copiato nel file temporaneo {}",
						    new Object[] { rarFile.getAbsolutePath() });
					}
					uncompressed = Utilities.unrarToDirAndReturnEntries(rarFile, unpackDir);
					boolean deleted = rarFile.delete();
				    } catch (Exception e) {
					log.error(
						"getAllegatiPECPerIstanzaoMovimento - si è verificato un errore durante lo scompattamento dell'archivio RAR {}, il file sarà trattato come se non fosse un file rar. Errore: {}",
						new Object[] { doc.getNomefile(), e.getMessage() });
					if (fos != null) {
					    fos.close();
					}
				    }
				}
			    } catch (IOException e) {
				log.error(
					"getAllegatiPECPerIstanzaoMovimento - Impossibile verificare se il file {} è un archivio ZIP o RAR. Il file non sarà considerato come archivio compresso. Errore: {}",
					new Object[] { doc.getNomefile(), e.getMessage() });
			    }
			}
			if (uncompressed == null || uncompressed.isEmpty()) {
			    //se l'allegato non è un archivio compresso restituisco il riferimento al record di oggetti che lo contiene
			    retDocs.add(doc);
			} else {
			    /*
			     * se invece si tratta di un archivio che è stato scompattato allora inserisco in Oggetti 
			     * tutti i files estratti e restituisco i riferimenti a questi
			     */
			    for (File file : uncompressed) {
				try {
				    Oggetti o = new Oggetti();
				    o.setNomefile(file.getName());
				    o.setOggetto(FileUtils.readFileToByteArray(file));
				    this.oggettiService.insert(o);
				    this.oggettiService.resetObjectCached();
				    retDocs.add(o);
				} catch (IOException e) {
				    String error = MessageFormat.format(
					    "errore nel salvataggio nel DB del file {0} estratto dall'''archivio compresso {1}",
					    new Object[] { file.getAbsolutePath(), doc.getNomefile() });
				    log.error("getAllegatiPECPerIstanzaoMovimento - " + error, e);
				    throw new RuntimeException(error, e);
				}
			    }
			    if (unpackDir != null && unpackDir.exists()) {
				Utilities.deleteFolder(unpackDir);
			    }
			}
		    } else {
			retDocs.add(pecAttachment.getOggetto());
		    }
		}
	    }
	}
	return retDocs;
    }

    @Override
    public PecInbox lockPecInboxAssegnaResponsabileProtocollo(String idPec) {

	PecInbox retVal = null;
	HibernateDaoSupport hibernateDaoSupport = (HibernateDaoSupport) getPecInboxDAO();
	if (StringUtils.isNotBlank(idPec)) {
	    try {
		retVal = (PecInbox) hibernateDaoSupport.getHibernateTemplate().get(PecInbox.class, new PecInboxId(idPec), LockMode.UPGRADE_NOWAIT);
	    } catch (DataAccessException e) {
		throw new RuntimeException("La PEC selezionata é bloccata perchè in lavorazione da parte di un altro operatore.", e);
	    }
	    if (retVal != null) {
		Responsabili resp = retVal.getResponsabili();
		Responsabili me = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		if (resp != null) {
		    Integer codiceResponsabile = null;
		    Integer codiceUtenteLoggato = null;
		    if (resp.getId() != null && resp.getId().getCodice() != null) {
			codiceResponsabile = resp.getId().getCodice();
		    }
		    if (me != null && me.getId() != null && me.getId().getCodice() != null) {
			codiceUtenteLoggato = me.getId().getCodice();
		    }
		    if (codiceUtenteLoggato == null) {
			codiceUtenteLoggato = -10000;
		    }
		    if (codiceResponsabile == null) {
			codiceResponsabile = -10000;
		    }
		    log.debug("codiceUtenteLoggato:{}, codiceResponsabile: {}", codiceUtenteLoggato, codiceResponsabile);
		    log.debug("me:{}, repPec: {}", me, resp);
		    if (!codiceUtenteLoggato.equals(codiceResponsabile)) {
			//La pec è già assegnata d un altro responsabile -> throw exception
			String errMsg = MessageFormat.format("La pec selezionata è già assegnata all''operatore {0}", resp.getResponsabile());
			throw new RuntimeException(errMsg);
		    }
		} else {
		    //la PEC non è assegnata a nessun responsabile
		    //verifico che la PEC non sia già associata ad un protocollo
		    String prot = retVal.getNumeroprotocollo();
		    if (!StringUtils.isEmpty(prot)) {
			if (log.isDebugEnabled()) {
			    log.debug("lockPecInboxAssegnaResponsabileProtocollo - la pec ha già il protocollo assegnato {}", prot);
			}
		    } else {
			/*
			 * se la pec è associata ad un'istanza o un movimento verifico se esiste il protocollo 
			 * dell'istanza o del movimento.
			 */
			Istanze istanzaPec = retVal.getIstanze();
			if (null != istanzaPec && null != istanzaPec.getId() && null != istanzaPec.getId().getCodice()) {
			    if (log.isDebugEnabled()) {
				log.debug("protocolloDaPEC# assegnata ad istanza {}", istanzaPec.toString());
			    }
			    prot = istanzaPec.getNumeroprotocollo();
			    if (!StringUtils.isEmpty(prot)) {
				if (log.isDebugEnabled()) {
				    log.debug(
					    "lockPecInboxAssegnaResponsabileProtocollo - la pec è associata all'istanza n.{} che ha già il protocollo assegnato {}",
					    istanzaPec.getNumeroistanza(), prot);
				}
			    }
			} else {
			    Movimenti movimentoPec = retVal.getMovimenti();
			    if (null != movimentoPec && null != movimentoPec.getId() && null != movimentoPec.getId().getCodice()) {
				prot = movimentoPec.getNumeroprotocollo();
				if (!StringUtils.isEmpty(prot)) {
				    if (log.isDebugEnabled()) {
					log.debug(
						"lockPecInboxAssegnaResponsabileProtocollo - la pec è associata al movimento {} che ha già il protocollo assegnato {}",
						movimentoPec.getId().getCodice(), prot);
				    }
				}
			    }
			}
		    }
		    if (StringUtils.isEmpty(prot)) {
			//assegno la PEC all'operatore corrente che sta creando l'istanza
			retVal.setResponsabili(me);
			getPecInboxDAO().update(retVal);
			if (log.isDebugEnabled()) {
			    log.debug("lockPecInboxAssegnaResponsabileProtocollo - la pec con codice: {} è stata assegnata al responsabile: {}.",
				    new Object[] { retVal.getId().getId(), me.getResponsabile() });
			}
		    }
		}
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("lockPecInboxAssegnaResponsabileProtocollo - impossibile trovare la PEC avente id={} per l'idcomune {}", new Object[] {
			    idPec, ORMHelper.getIdcomune() });
		}
	    }
	}
	return retVal;
    }

    @Override
    public PecInbox lockPecInboxAssegnaResponsabileIstanzaOMovimento(String idPec) {

	PecInbox retVal = null;
	HibernateDaoSupport hibernateDaoSupport = (HibernateDaoSupport) getPecInboxDAO();
	if (StringUtils.isNotBlank(idPec)) {
	    try {
		retVal = (PecInbox) hibernateDaoSupport.getHibernateTemplate().get(PecInbox.class, new PecInboxId(idPec), LockMode.UPGRADE_NOWAIT);
	    } catch (DataAccessException e) {
		throw new RuntimeException("La PEC selezionata é bloccata perchè in lavorazione da parte di un altro operatore.", e);
	    }
	    if (retVal != null) {
		Responsabili resp = retVal.getResponsabili();
		Responsabili me = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		if (resp != null) {
		    if (!resp.equals(me)) {
			//La pec è già assegnata d un altro responsabile -> throw exception
			String errMsg = MessageFormat.format("La pec selezionata è già assegnata all''operatore {0}", resp.getResponsabile());
			throw new RuntimeException(errMsg);
		    }
		} else {
		    //la PEC non è assegnata a nessun responsabile
		    //verifico che la PEC non sia già associata ad un'istanza
		    Istanze istanza = retVal.getIstanze();
		    if (istanza != null && istanza.getId() != null && istanza.getId().getCodice() != null) {
			if (log.isDebugEnabled()) {
			    log.debug("lockPecInboxAssegnaResponsabileIstanzaOMovimento - la pec é già associata all'istanza n.{}",
				    istanza.getNumeroistanza());
			}
		    } else {
			//verifico che la PEC non sia già associata ad un movimento
			Movimenti movimentoPec = retVal.getMovimenti();
			if (null != movimentoPec && null != movimentoPec.getId() && null != movimentoPec.getId().getCodice()) {
			    if (log.isDebugEnabled()) {
				log.debug("lockPecInboxAssegnaResponsabileIstanzaOMovimento - la pec é già associata al movimento {}", movimentoPec
					.getId().getCodice());
			    }
			} else {
			    //assegno la PEC all'operatore corrente 
			    retVal.setResponsabili(me);
			    getPecInboxDAO().update(retVal);
			    if (log.isDebugEnabled()) {
				log.debug(
					"lockPecInboxAssegnaResponsabileIstanzaOMovimento - la pec con codice: {} è stata assegnata al responsabile: {}.",
					new Object[] { retVal.getId().getId(), me.getResponsabile() });
			    }
			}
		    }
		}
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("lockPecInboxAssegnaResponsabileIstanzaOMovimento - impossibile trovare la PEC avente id={} per l'idcomune {}",
			    new Object[] { idPec, ORMHelper.getIdcomune() });
		}
	    }
	}
	return retVal;
    }

    @Override
    public List<PecInbox> findByAccountId(Integer idAccount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAccount, "mailConfig", Integer.class));
	ft.addRestriction(fr);
	return pecInboxDAO.findByFilterTable(ft);
    }

    @Override
    public int countByAccountId(Integer idAccount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAccount, "mailConfig", Integer.class));
	ft.addRestriction(fr);
	return pecInboxDAO.countRecord(ft);
    }
}
