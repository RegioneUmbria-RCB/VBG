package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoCart;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.DatiModulo;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.MappingIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.CartControlloAllegatiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartFileCopyInfo;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModulisticaSTAR;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DomandeFrontOfficeService;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomandeFrontOfficeServiceImpl implements DomandeFrontOfficeService {

    private static final Logger log = LoggerFactory.getLogger(DomandeFrontOfficeServiceImpl.class);
    private static final String ERROR_MESSAGE_ARJ_NOT_SUPPORTED = "La gestione delle domande provenienti dall'area riservata java non è ancora supportata.";
    private static final int DIMENSIONE_MASSIMA_ALLEGATI = 41943040;
    private static final int DIMENSIONE_MASSIMA_ALLEGATI_BASE64 = 31457280;
    // private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private FoDomandeService foDomandeService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private FoDomandeOggettiService foDomandeOggettiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private SdeproxyService sdeproxyService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;

    @Override
    public DatiDomandaCart getDatiDomandaCart(Integer idDomandaFo, boolean isARJ) {

	DatiDomandaCart retVal = null;
	// gestione domande FO.NET
	if (!isARJ) {
	    PkId pk = new PkId(idDomandaFo);
	    FoDomande domanda = this.foDomandeService.findById(pk);
	    if (null == domanda) {
		log.warn(
			"getDatiDomandaCart() - nessun record trovato nella tabella FO_DOMANDE per l'id: {}, sarà impossibile salvare lo stato della domanda.",
			pk);
	    } else {
		// domanda.get
		Integer codiceOggettoCart = null;
		if (domanda.getOggettoCart() != null) {
		    if (domanda.getOggettoCart().getId() != null) {
			if (domanda.getOggettoCart().getId().getCodice() != null) {
			    codiceOggettoCart = domanda.getOggettoCart().getId().getCodice();
			}
		    }
		}
		Oggetti oggettoCart = null;
		if (codiceOggettoCart != null) {
		    oggettoCart = oggettiService.findById(new PkId(codiceOggettoCart));
		}
		if (oggettoCart != null && oggettoCart.getOggetto() != null) {
		    try {
			String domandaXml = new String(oggettoCart.getOggetto(), FACCTConstants.DEFAULT_CHARSET);
			// String domandaXml = new
			// String(oggettoCart.getOggetto());
			// if(log.isDebugEnabled())log.debug("getDatiDomandaCart() - unmarshall dei dati della domanda {} dall'oggetto avente codice {}. XML : \r\n",
			// new Object[]{idDomandaFo, oggettoCart.getId()});
			retVal = (DatiDomandaCart) XmlUtils.unMarshallString(domandaXml, DatiDomandaCart.class);
			if (retVal != null) {
			    String dataVersion = retVal.getVersione();
			    if (StringUtils.defaultString(dataVersion, DatiDomandaCart.OLD_VERSION).equals(DatiDomandaCart.OLD_VERSION)) {
				this.convertiFormatoDatiDomanda(retVal);
			    }
			    retVal.setDomanda(domanda);
			}
		    } catch (UnsupportedEncodingException e) {
			log.error("getDatiDomandaCart() - errore durante la lettura della domanda dal DB: codifica UTF-8 non supportata.", e);
		    } catch (JAXBException e) {
			log.error("getDatiDomandaCart() - errore durante l'unmarshall della domanda dal DB: ", e);
		    }
		} else {
		    log.error("getDatiDomandaCart() - identificato il record di FO_DOMANDE per l'id {}. Nessun oggetto CART associato.", pk);
		}
	    }
	}
	// gestione domande ARJ
	else {
	    log.error("getDatiDomandaCart() - {}", ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	    throw new RuntimeException(ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	}
	return retVal;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void aggiornaDatiDomandaCart(DatiDomandaCart datiDomanda, boolean isARJ) {

	if (!isARJ) {
	    datiDomanda.setVersione(DatiDomandaCart.NEW_VERSION);
	    PkId pk = new PkId(datiDomanda.getDatiContestoDomanda().getIdDomandaFo());
	    FoDomande domanda = this.foDomandeService.findById(pk);
	    if (null == domanda) {
		log.warn(
			"aggiornaDatiDomandaCart() - nessun record trovato nella tabella FO_DOMANDE per l'id: {}, sarà impossibile salvare lo stato della domanda.",
			pk);
	    } else {
		// domanda.get
		Oggetti oggettoCart = domanda.getOggettoCart();
		/*
		 * se l'oggetto domanda cart non esiste ancora ne inserisco il
		 * record in OGGETTI ma il suo contenuto viene impostato solo
		 * alla fine perchè la struttura dati da serializzare subisce le
		 * ultime modifiche durante l'elaborazione degli allegati della
		 * domanda
		 */
		//
		boolean insertOggetto = false;
		if (oggettoCart == null) {
		    insertOggetto = true;
		} else {
		    if (oggettoCart.getId() != null) {
			if (oggettoCart.getId().getCodice() == null) {
			    insertOggetto = true;
			}
		    }
		}
		if (insertOggetto) {
		    if (log.isDebugEnabled())
			log.debug(
				"aggiornaDatiDomandaCart() - identificato il record di FO_DOMANDE per l'id {}. L'oggetto CART associato non esisteva e sarà inserito per la prima volta.",
				pk);
		    // inseriso il nuovo record in OGGETTI
		    oggettoCart = new Oggetti();
		    oggettoCart.setNomefile(getNomeFileOggettoCart(datiDomanda));
		    oggettoCart.setOggetto("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes());
		    // oggettoCart.setDimensioneFile(bytesDomanda.length);
		    oggettiService.insert(oggettoCart);
		    domanda.setOggettoCart(oggettoCart);
		    foDomandeService.update(domanda);
		    oggettiService.flush();
		}
		// elaborazione allegati da cancellare per via della
		// cancellazione delle righe dalle tabelle o l'eliminazione di
		// interi moduli o parti di moduli
		List<AllegatoCart> deletedFiles = datiDomanda.getFileDeletions();
		for (AllegatoCart fileInfo : deletedFiles) {
		    deleteAllegatoDomanda(datiDomanda.getDatiContestoDomanda().getIdDomandaFo(), fileInfo.getRiferimento());
		    if (log.isDebugEnabled())
			log.debug("aggiornaDatiDomandaCart() - cancellato dal DB l'alleagto avente nome: {} e id: {}",
				new Object[] { fileInfo.getFileName(), fileInfo.getRiferimento() });
		    oggettiService.flush();
		}
		/*
		 * ripulisco la lista degli allegati da cancellare per
		 * predispormi a gestire le cancellazioni delle righe dalle
		 * tabelle del quadro successivo.
		 */
		deletedFiles.clear();
		// memorizzo l'oggetto cart nel campo BLOB solo dopo aver
		// completato l'elaborazione degli allegati
		String domandaXml = null;
		byte[] bytesDomanda = null;
		try {
		    Class[] bindingClasses = new Class[] { DatiModulo.class, MappingIdSemantico.class, ValoreIdSemantico.class, FileInfo.class,
			    AllegatoDaFirmare.class, ModulisticaSTAR.class };
		    domandaXml = XmlUtils.marshallObject(datiDomanda, true, bindingClasses);
		    bytesDomanda = domandaXml.getBytes(FACCTConstants.DEFAULT_CHARSET);
		    //bytesDomanda = domandaXml.getBytes();
		} catch (JAXBException e) {
		    String errMsg = "errore durante il salvataggio della domanda nel DB: ";
		    log.error("aggiornaDatiDomandaCart() - " + errMsg, e);
		    throw new RuntimeException(errMsg, e);
		} catch (UnsupportedEncodingException uee) {
		    String errMsg = "errore durante il salvataggio della domanda nel DB: codifica UTF-8 non supportata.";
		    log.error("aggiornaDatiDomandaCart() - " + errMsg, uee);
		    throw new RuntimeException(errMsg, uee);
		}
		oggettoCart.setOggetto(bytesDomanda);
		oggettiService.update(oggettoCart);
		if (log.isDebugEnabled()) {
		    log.debug("aggiornaDatiDomandaCart() - l'oggetto CART associato alla domanda con id {} è stato salvato con successo.", pk);
		}
		domanda.setOggettoCart(oggettoCart);
		domanda.setDataUltimaModifica(new Date());
		foDomandeService.update(domanda);
	    }
	} else {
	    log.error("aggiornaDatiDomandaCart() - {}", ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	    throw new RuntimeException(ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	}
    }

    @Override
    public FoDomande impostaDomandaPresentata(Integer idDomandaFo, String codiceDomanda, Date dataPresentazione, String idEgov, File zipFile)
	    throws IOException {

	PkId pk = new PkId(idDomandaFo);
	FoDomande domanda = this.foDomandeService.findById(pk);
	if (null == domanda) {
	    log.warn(
		    "impostaDomandaPresentata() - nessun record trovato nella tabella FO_DOMANDE per l'id: {}, sarà impossibile aggiornare lo stato della domanda.",
		    pk);
	} else {
	    domanda.setFlgPresentata(dataPresentazione != null);
	    Date now = new Date();
	    domanda.setDatainvio(dataPresentazione);
	    domanda.setDataUltimaModifica(now);
	    domanda.setIdentificativodomanda(codiceDomanda);
	    // domanda.setIdentificativoCart(idEgov);
	    foDomandeService.update(domanda);
	    if (log.isDebugEnabled()) {
		log.debug("impostaDomandaPresentata() - la domanda associata all'id {} è stata impostata come presentata in data {}.", new Object[] {
			pk, now });
	    }
	    if (zipFile != null && zipFile.canRead()) {
		foDomandeOggettiService.insertOrUpdateZipfile(domanda.getId().getIdcomune(), idDomandaFo, zipFile);
	    }
	}
	return domanda;
    }

    @Override
    public List<CartFileCopyInfo> downloadAllegatiDomanda(DatiDomandaCart datiDomanda, File downloadDir) {

	List<CartFileCopyInfo> retAllegati = new ArrayList<CartFileCopyInfo>();
	PresentazioneDomandaCartCommand cmd = datiDomanda.getDatiContestoDomanda();
	boolean isARJ = cmd.isARJ();
	Integer idDomandaFo = cmd.getIdDomandaFo();
	if (!isARJ) {
	    PkId pk = new PkId(idDomandaFo);
	    FoDomande domanda = this.foDomandeService.findById(pk);
	    if (null == domanda) {
		log.warn("downloadAllegatiDomanda() - nessun record trovato nella tabella FO_DOMANDE per l'id: {}, nessun allegato sarà scaricato.",
			pk);
	    } else {
		// TODO nuovi campi file l'elenco dei file da scaricare nella
		// cartella temporanea deve essere preso dalla list di FileInfo
		// restituita da domanda.getAllegati()
		Set<FoDomandeOggetti> allegatiRef = domanda.getFoDomandeOggettis();
		for (FoDomandeOggetti allegatoRef : allegatiRef) {
		    Oggetti allegato = allegatoRef.getOggetti();
		    allegato = oggettiService.findById(allegato.getId());
		    FileInfo infoAllegato = datiDomanda.getAllegatoByCodiceOggetto(allegato.getId().getCodice());
		    if (infoAllegato != null && StringUtils.isNotEmpty(infoAllegato.getIdSemantico())) {
			CartFileCopyInfo fci = new CartFileCopyInfo();
			fci.setIdComune(allegato.getId().getIdcomune());
			fci.setIdOggetto(allegato.getId().getCodice());
			String dbFileName = allegato.getNomefile();
			String prefix = allegato.getId().getCodice().toString() + "-";
			if (dbFileName.startsWith(prefix)) {
			    dbFileName = dbFileName.substring(prefix.length());
			}
			fci.setNomeFileTemporaneo(dbFileName);
			if (StringUtils.isEmpty(fci.getIdSemantico())) {
			    fci.setIdSemantico(infoAllegato.getIdSemantico());
			}
			File outFile = new File(downloadDir, fci.buildNomeFilePresentazioneDomanda());
			if (!outFile.isFile()) {
			    try {
				outFile.createNewFile();
			    } catch (IOException e) {
				String errMsg = MessageFormat
					.format("Si è verificato un errore durante la creazione del file {0} per la copia dell'''allegato per la domanda front office avente id {1}: {2}",
						new Object[] { outFile.getAbsolutePath(), idDomandaFo, e.getMessage() });
				throw new RuntimeException(errMsg, e);
			    }
			} else {
			    log.warn(
				    "downloadAllegatiDomanda() - Il file {} in cui copiare l'allegato esiste già e il suo contenuto sarà sovrascritto.",
				    new Object[] { outFile.getAbsolutePath() });
			}
			FileOutputStream fos = null;
			try {
			    fos = new FileOutputStream(outFile);
			    byte[] binaryData = allegato.getOggetto();
			    AttachmentsUtils.writeBytesToStream(binaryData, fos);
			    retAllegati.add(fci);
			    log.debug("downloadAllegatiDomanda() - l'allegato {} della domanda con id front office {} è stato copiato nel percorso",
				    new Object[] { dbFileName, idDomandaFo, outFile.getAbsolutePath() });
			} catch (IOException e) {
			    String errMsg = MessageFormat
				    .format("Si è verificato un errore durante la scrittura del file {0} per la copia dell'''allegato della domanda front office avente id {1}: {2}",
					    new Object[] { outFile.getAbsolutePath(), idDomandaFo, e.getMessage() });
			    throw new RuntimeException(errMsg, e);
			} finally {
			    if (null != fos) {
				try {
				    fos.close();
				} catch (IOException e) {
				    log.error("downloadAllegatiDomanda() - errore nella chiusura del flusso durante la copia locale del file {}",
					    new Object[] { dbFileName });
				}
			    }
			}
		    }
		}
	    }
	} else {
	    log.error("downloadAllegatiDomanda() - {}", ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	    throw new RuntimeException(ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	}
	return retAllegati;
    }

    @Override
    public void cancellaAllegatoUtente(DatiDomandaCart datiDomanda, Integer idDomandaFo, Integer idOggetto, boolean isArj) {

	if (!isArj) {
	    // aggiornamento dell'oggetto CART di FO_DOMANDE iin cui è
	    // serializzato lo stato della domanda
	    PkId pk = new PkId(datiDomanda.getDatiContestoDomanda().getIdDomandaFo());
	    FoDomande domanda = this.foDomandeService.findById(pk);
	    if (null == domanda) {
		String errMsg = MessageFormat.format(
			"Impossibile cancellare l'''allegato {0} della domanda {1} perchè non esiste nessuna doomanda associata a quell'''id.",
			new Object[] { idOggetto, idDomandaFo });
		log.error(errMsg);
		throw new RuntimeException(errMsg);
	    } else {
		// domanda.get
		Oggetti oggettoCart = domanda.getOggettoCart();
		String domandaXml = null;
		byte[] bytesDomanda = null;
		try {
		    Class[] bindingClasses = new Class[] { DatiModulo.class, MappingIdSemantico.class, ValoreIdSemantico.class, FileInfo.class };
		    domandaXml = XmlUtils.marshallObject(datiDomanda, true, bindingClasses);
		    // bytesDomanda =
		    // domandaXml.getBytes(FACCTConstants.DEFAULT_CHARSET);
		    bytesDomanda = domandaXml.getBytes();
		    /*
		     * } catch (UnsupportedEncodingException e) { String errMsg
		     * =
		     * "errore durante il salvataggio della domanda nel DB: codifica UTF-8 non supportata."
		     * ; log.error("aggiornaDatiDomandaCart() - " + errMsg, e);
		     * throw new RuntimeException(errMsg, e);
		     */
		} catch (JAXBException e) {
		    String errMsg = "errore durante il salvataggio della domanda nel DB: ";
		    log.error("aggiornaDatiDomandaCart() - " + errMsg, e);
		    throw new RuntimeException(errMsg, e);
		}
		oggettoCart.setDimensioneFile(bytesDomanda.length);
		oggettoCart.setOggetto(bytesDomanda);
		oggettiService.update(oggettoCart);
	    }
	    if (idOggetto != null && idOggetto > 0) {
		// cancellazione record da FO_DOMANDE_OGGETTI
		deleteAllegatoDomanda(idDomandaFo, idOggetto);
	    }
	} else {
	    log.error("cancellaAllegatoUtente() - {}", ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	    throw new RuntimeException(ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.cartfacct.service.DomandeFrontOfficeService#
     * salvaAllegatiFirmati
     * (it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart, java.util.List)
     */
    @Override
    public void salvaAllegatiFirmati(DatiDomandaCart datiDomanda, List<AllegatoDaFirmare> allegati, boolean isArj) {

	if (!isArj) {
	    // aggiornamento dell'oggetto CART di FO_DOMANDE iin cui è
	    // serializzato lo stato della domanda
	    PkId pk = new PkId(datiDomanda.getDatiContestoDomanda().getIdDomandaFo());
	    FoDomande domanda = this.foDomandeService.findById(pk);
	    if (null == domanda) {
		String errMsg = MessageFormat.format(
			"Impossibile salvare gli allegati della domanda {0} perchè non esiste nessuna doomanda associata a quell'''id.",
			new Object[] { datiDomanda.getDatiContestoDomanda().getIdDomandaFo() });
		log.error(errMsg);
		throw new RuntimeException(errMsg);
	    } else {
		// domanda.get
		Oggetti oggettoCart = domanda.getOggettoCart();
		String domandaXml = null;
		byte[] bytesDomanda = null;
		try {
		    Class[] bindingClasses = new Class[] { DatiModulo.class, MappingIdSemantico.class, ValoreIdSemantico.class, FileInfo.class };
		    domandaXml = XmlUtils.marshallObject(datiDomanda, true, bindingClasses);
		    bytesDomanda = domandaXml.getBytes(FACCTConstants.DEFAULT_CHARSET);
		    // bytesDomanda = domandaXml.getBytes();
		} catch (UnsupportedEncodingException e) {
		    String errMsg = "errore durante il salvataggio della domanda nel DB: codifica UTF-8 non supportata.";
		    log.error("aggiornaDatiDomandaCart() - " + errMsg, e);
		    throw new RuntimeException(errMsg, e);
		} catch (JAXBException e) {
		    String errMsg = "errore durante il salvataggio della domanda nel DB: ";
		    log.error("salvaAllegatiFirmati() - " + errMsg, e);
		    throw new RuntimeException(errMsg, e);
		}
		oggettoCart.setDimensioneFile(bytesDomanda.length);
		oggettoCart.setOggetto(bytesDomanda);
		oggettiService.update(oggettoCart);
	    }
	    // aggiornamento record in OGGETTI
	    for (AllegatoDaFirmare allegato : allegati) {
		updateAllegatoFirmato(allegato);
	    }
	} else {
	    log.error("salvaAllegatiFirmati() - {}", ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	    throw new RuntimeException(ERROR_MESSAGE_ARJ_NOT_SUPPORTED);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.cartfacct.service.DomandeFrontOfficeService#
     * salvaAllegatoDaFirmare(it.gruppoinit.pal.gp.cartfacct.domain.DatiDomandaCart)
     */
    @Override
    public void salvaAllegatoDaFirmare(AllegatoDaFirmare all) {

	if (checkCanSaveAttachment(all)) {
	    // se esiste già un record in oggetti per questo documento lo
	    // recupero dal DB per aggiornarlo
	    Oggetti oggettoAll = null;
	    byte[] data = null;
	    try {
		data = AttachmentsUtils.readBytesFromFile(all.getAttachment());
	    } catch (IOException e) {
		String errMsg = MessageFormat.format("impossibile salvare il file {0} nella tabella OGGETTI. Errore: {1}",
			new Object[] { all.getFileName(), e.getMessage() });
		log.error("salvaAllegatoDaFirmare() - " + errMsg, e);
		throw new RuntimeException(errMsg, e);
	    }
	    if (all.getRiferimento() != null) {
		oggettoAll = this.oggettiService.findById(new PkId(all.getRiferimento()));
	    }
	    if (oggettoAll != null) {
		oggettoAll.setDimensioneFile(data.length);
		oggettoAll.setNomefile(all.getFileName());
		oggettoAll.setOggetto(data);
		this.oggettiService.update(oggettoAll);
	    } else {
		oggettoAll = new Oggetti();
		oggettoAll.setNomefile(all.getFileName());
		oggettoAll.setDimensioneFile(data.length);
		oggettoAll.setOggetto(data);
		this.oggettiService.insert(oggettoAll);
		all.setRiferimento(oggettoAll.getId().getCodice());
	    }
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.DomandeFrontOfficeService#salvaAllegatoCart(it.gruppoinit.pal.gp.core.domain.cart.AllegatoCart, java.lang.Integer)
     */
    @Override
    public void salvaAllegatoCart(AllegatoCart all, Integer idDomanda, boolean updateOggetti) {

	if (checkCanSaveAttachment(all)) {
	    Oggetti oggettoAll = null;
	    byte[] data = null;
	    try {
		data = AttachmentsUtils.readBytesFromFile(all.getAttachment());
	    } catch (IOException e) {
		String errMsg = MessageFormat.format("impossibile salvare il file {0} nella tabella OGGETTI. Errore: {1}",
			new Object[] { all.getFileName(), e.getMessage() });
		log.error("salvaMDADaFirmare() - " + errMsg, e);
		throw new RuntimeException(errMsg, e);
	    }
	    if (all.getRiferimento() != null) {
		oggettoAll = this.oggettiService.findById(new PkId(all.getRiferimento()));
	    }
	    if (oggettoAll != null) {
		if (updateOggetti) {
		    oggettoAll.setDimensioneFile(data.length);
		    oggettoAll.setNomefile(all.getFileName());
		    oggettoAll.setOggetto(data);
		    this.oggettiService.update(oggettoAll);
		}
	    } else {
		oggettoAll = new Oggetti();
		oggettoAll.setNomefile(all.getFileName());
		oggettoAll.setDimensioneFile(data.length);
		oggettoAll.setOggetto(data);
		this.oggettiService.insert(oggettoAll);
		all.setRiferimento(oggettoAll.getId().getCodice());
	    }
	    FoDomandeOggetti allDom = null;
	    if (idDomanda != null) {
		FoDomandeOggettiId id = new FoDomandeOggettiId();
		id.setCodiceoggetto(all.getRiferimento());
		id.setIddomanda(idDomanda);
		allDom = this.foDomandeOggettiService.findById(id);
		if (allDom == null) {
		    allDom = new FoDomandeOggetti();
		    allDom.setId(id);
		    allDom.setOggetti(oggettoAll);
		    allDom.setTipoFile(all.getTipoFile());
		    this.foDomandeOggettiService.insert(allDom);
		}
	    }
	}
    }

    private boolean checkCanSaveAttachment(AllegatoCart allegato) {

	boolean retVal = false;
	if (allegato != null && allegato.getAttachment() != null) {
	    retVal = allegato.getAttachment().isFile() && allegato.getAttachment().canRead();
	}
	return retVal;
    }

    @Override
    public void deleteAllegatoDomanda(Integer idDomandaFo, Integer idOggetto) {

	// FoDomandeOggetti allegatoDomanda = new FoDomandeOggetti();
	FoDomandeOggettiId idAllegatoDomanda = new FoDomandeOggettiId(idDomandaFo, idOggetto);
	FoDomandeOggetti allegatoDomanda = foDomandeOggettiService.findById(idAllegatoDomanda);
	if (allegatoDomanda != null) {
	    // allegatoDomanda.setId(idAllegatoDomanda);
	    log.error("###CANCELLAZIONE OGGETTO deleteAllegatoDomanda prima di cancellare [{},{}]", idOggetto, ORMHelper.getIdcomune());
	    foDomandeOggettiService.delete(allegatoDomanda);
	    log.error("###CANCELLAZIONE OGGETTO deleteAllegatoDomanda ESEGUITA [{},{}]", idOggetto, ORMHelper.getIdcomune());
	    /*
	     * cancellazione da OGGETTI: tanto sicuramente, se la domoanda non è
	     * ancora stata presentata, l'oggetto non è referenziato in altre
	     * tabelle e la cancellazione da Oggetti è possibile.
	     */
	    /*
	     * Oggetti oggetto = this.oggettiService.findById(new
	     * PkId(idOggetto));// new // Oggetti();
	     * this.oggettiService.delete(oggetto);
	     */
	}
    }

    @Override
    public void insertAllegatoDomanda(Integer idDomandaFo, Oggetti newObj) {

	FoDomande dom = null;
	boolean isRfc239 = false;
	if (null != idDomandaFo) {
	    dom = this.foDomandeService.findById(new PkId(idDomandaFo));
	    Sdeproxy proxy = this.sdeproxyService.findById(ORMHelper.getIdente());
	    if (proxy != null) {
		// isRfc239 = BooleanUtils.isTrue(proxy.getFlgRfc239()) || BooleanUtils.isTrue(dom.getFlagComunica());
	    }
	}
	if (null != newObj && dom != null) {
	    if (isRfc239) {
		newObj.setNomefile(getNomeFileRfc239(newObj.getNomefile()));
	    }
	    oggettiService.insert(newObj);
	    FoDomandeOggetti oggettoDomanda = new FoDomandeOggetti();
	    oggettoDomanda.setOggetti(newObj);
	    oggettoDomanda.setFoDomande(dom);
	    oggettoDomanda.getId().setCodiceoggetto(newObj.getId().getCodice());
	    oggettoDomanda.getId().setIddomanda(dom.getId().getCodice());
	    foDomandeOggettiService.insert(oggettoDomanda);
	}
    }

    private String getNomeFileRfc239(String originalFileName) {

	String retName = null;
	if (StringUtils.isNotBlank(originalFileName)) {
	    retName = originalFileName.replaceAll("[^a-zA-Z0-9\\-_.]", "_");
	}
	return retName;
    }

    private void updateAllegatoFirmato(AllegatoDaFirmare fileUpload) {

	/*
	 * aggiornamento dei dati binari del file, il nome e la dimensione
	 */
	FileItem fi = fileUpload.getUploadedFile();
	if (fi != null && fi.getSize() > 0) {
	    Oggetti oggetto = this.oggettiService.findById(new PkId(new Integer(fileUpload.getRiferimento())));
	    //StringBuilder sbFileName = new StringBuilder();
	    /*
	     * if (!StringUtils.isEmpty(fileUpload.getIdSemantico())) {
	     * sbFileName.append(fileUpload.getIdSemantico());
	     * sbFileName.append(
	     * FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR); }
	     */
	    //sbFileName.append(fileUpload.getFileName());
	    oggetto.setNomefile(fi.getName());
	    oggetto.setDimensioneFile((int) fi.getSize());
	    oggetto.setOggetto(fi.get());
	    this.oggettiService.update(oggetto);
	}
    }

    private String getNomeFileOggettoCart(DatiDomandaCart datiDomanda) {

	StringBuilder sb = new StringBuilder();
	sb.append("DOMANDA_CART_").append(datiDomanda.getDatiContestoDomanda().getIdDomandaFo()).append(".xml");
	return sb.toString();
    }

    private void convertiFormatoDatiDomanda(DatiDomandaCart dati) {

	Set<DatiModulo> moduli = dati.getDatiModuli();
	for (Iterator<DatiModulo> iterator = moduli.iterator(); iterator.hasNext();) {
	    DatiModulo modulo = (DatiModulo) iterator.next();
	    List<MappingIdSemantico> valoriModulo = modulo.getValoreModulo();
	    for (MappingIdSemantico mis : valoriModulo) {
		ValoreIdSemantico vis = mis.getValore();
		if (vis != null && vis.isFile() && !vis.hasNoValues()) {
		    List<ValoreIdSemantico> innerValues = vis.listaValoriNormalizzata();
		    List<FileInfo> visFiles = null;
		    for (ValoreIdSemantico innerVis : innerValues) {
			// i campi di tipo file sono sempre di tipo vettoriale
			String[] oldValues = innerVis.getValoreVettoriale();
			if (oldValues == null && innerVis.getValoreScalare() != null) {
			    oldValues = new String[] { innerVis.getValoreScalare() };
			}
			String[] newValues = new String[oldValues.length];
			for (int i = 0; i < oldValues.length; i++) {
			    // se il valore non è numerico è un valore vecchio
			    // che contiene il nome del file e deve essere
			    // sostituito
			    if (StringUtils.isNotEmpty(oldValues[i]) && NumberUtils.toInt(oldValues[i], 0) == 0) {
				StringBuilder searchFileName = new StringBuilder(mis.getIdSemantico()).append(
					FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR).append(oldValues[i]);
				FileInfo fileInfo = innerVis.getFileInfoForFileName(searchFileName.toString());
				if (fileInfo == null) {
				    innerVis.getFileInfoForFileName(oldValues[i]);
				}
				String codiceOggetto = "";
				if (fileInfo != null) {
				    Oggetti fileObj = oggettiService.findById(new PkId(fileInfo.getIdOggetto()));
				    if (fileObj != null) {
					codiceOggetto = fileInfo.getIdOggetto() != null ? fileInfo.getIdOggetto().toString() : "";
					CartFileCopyInfo cfci = new CartFileCopyInfo();
					cfci.setIdComune(ORMHelper.getIdcomune());
					cfci.setIdOggetto(fileInfo.getIdOggetto());
					String dbFileName = fileObj.getNomefile();
					String prefix = fileInfo.getIdOggetto().toString() + "-";
					if (dbFileName.startsWith(prefix)) {
					    dbFileName = dbFileName.substring(prefix.length());
					}
					cfci.setNomeFileTemporaneo(dbFileName);
					if (StringUtils.isEmpty(cfci.getIdSemantico())) {
					    cfci.setIdSemantico(fileInfo.getIdSemantico());
					}
					String origFileName = new StringBuilder(cfci.getNomeFileOriginale()).append(".").append(cfci.getEstensione())
						.toString();
					if (!origFileName.equals(dbFileName)) {
					    fileObj.setNomefile(origFileName);
					    oggettiService.update(fileObj);
					    if (log.isDebugEnabled()) {
						log.debug(
							"convertiFormatoDatiDomanda - eseguito aggiornamento nome file sul DB per alleagto: id={}, vecchio nome={}, nuovo nome={}",
							new Object[] { codiceOggetto, dbFileName, origFileName });
					    }
					}
					fileInfo.setIdSemantico(mis.getIdSemantico());
					fileInfo.setNomeFile(origFileName);
					fileInfo.setDimensione(fileObj.getDimensioneFile());
					dati.addAllegato(fileInfo);
					if (log.isDebugEnabled()) {
					    log.debug(
						    "convertiFormatoDatiDomanda - eseguito aggiornamento formato dati XML per alleagto: id={}, vecchio nome={}, nuovo nome={}",
						    new Object[] { codiceOggetto, dbFileName, origFileName });
					}
				    }
				}
				newValues[i] = codiceOggetto;
			    } else {
				newValues[i] = oldValues[i];
			    }
			}
			if (innerVis.isScalare()) {
			    String newVal = newValues.length > 0 ? newValues[0] : "";
			    innerVis.setValoreScalare(StringUtils.defaultString(newVal));
			} else if (innerVis.isVettoriale()) {
			    innerVis.setValoreVettoriale(newValues);
			}
			innerVis.getFileInfo().clear();
		    }
		}
	    }
	}
    }

    @Override
    public CartControlloAllegatiHelper checkDimensioniAllegati(Integer idDomandaFo) {

	CartControlloAllegatiHelper result = new CartControlloAllegatiHelper();
	List<FoDomandeOggetti> oggettiDomanda = foDomandeOggettiService.findByIdDomandaFo(ORMHelper.getIdcomune(), idDomandaFo);
	int somma = 0;
	List<String> listaAllegati = new ArrayList<String>();
	for (FoDomandeOggetti fdo : oggettiDomanda) {
	    if (StringUtils.defaultString(fdo.getTipoFile()).equalsIgnoreCase(FoDomandeOggettiService.TIPO_FILE.ALLEGATO.toString())) {
		Oggetti o = oggettiService.findByIdLazy(new PkId(fdo.getId().getCodiceoggetto()));
		if (o.getDimensioneFile() != null) {
		    listaAllegati.add(o.getNomefile() + ": (" + o.getDimensioneFileLeggibile() + ")");
		    somma += o.getDimensioneFile();
		}
	    }
	}
	boolean isAttachmentBase64 = false;
	FoDomande dom = foDomandeService.findById(new PkId(idDomandaFo));
	if (dom != null) {
	    Sdeproxy proxy = dom.getSdeproxy();
	    //	    if (proxy != null) {
	    //		isAttachmentBase64 = proxy.getEncodeAttAsBase64() == null ? false : proxy.getEncodeAttAsBase64().booleanValue();
	    //	    }
	}
	int dimensioneMassima = getDimensioneMassimaAllegati(isAttachmentBase64);
	if (somma > dimensioneMassima) {
	    result.setLimiteSuperato(true);
	    result.setLimite(getDimensioneFileLeggibile(dimensioneMassima));
	    if (result.getListaAllegati() == null) {
		result.setListaAllegati(new ArrayList<String>());
	    }
	    result.getListaAllegati().addAll(listaAllegati);
	}
	return result;
    }

    private int getDimensioneMassimaAllegati(boolean isAttachmentBase64) {

	Verticalizzazioniparametri dimensione = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		"DIMENSIONE_MASSIMA_ALLEGATI");
	if (dimensione != null) {
	    if (StringUtils.isNotBlank(dimensione.getValore())) {
		try {
		    Integer d = Integer.parseInt(dimensione.getValore().trim());
		    if (d != null) {
			return d.intValue();
		    }
		} catch (Exception e) {
		    log.error("Il parametro di verticalizzazione DIMENSIONE_MASSIMA_ALLEGATI non è stato configurato correttamente [{}]",
			    dimensione.getValore());
		}
	    }
	}
	if (isAttachmentBase64) {
	    return DIMENSIONE_MASSIMA_ALLEGATI_BASE64;
	}
	return DIMENSIONE_MASSIMA_ALLEGATI;
    }

    private String getDimensioneFileInBytes(Integer dimensioneFile) {

	if (null == dimensioneFile) {
	    return "";
	}
	return String.valueOf(dimensioneFile) + " Bytes ";
    }

    private String getDimensioneFileInKb(Integer dimensioneFile) {

	if (null == dimensioneFile) {
	    return "";
	}
	return String.valueOf(dimensioneFile / 1000) + " Kb ";
    }

    private String getDimensioneFileInMb(Integer dimensioneFile) {

	if (null == dimensioneFile) {
	    return "";
	}
	return String.valueOf(dimensioneFile / 1000000) + " Mb ";
    }

    public String getDimensioneFileLeggibile(Integer dimensioneFile) {

	if (null == dimensioneFile) {
	    return "";
	}
	if (dimensioneFile < 1000) {
	    return getDimensioneFileInBytes(dimensioneFile);
	} else if (dimensioneFile < 1000000) {
	    return getDimensioneFileInKb(dimensioneFile);
	} else {
	    return getDimensioneFileInMb(dimensioneFile);
	}
    }

    @Override
    public int contaAllegatiDomanda(Integer idDomandaFo) {

	int numall = 0;
	List<FoDomandeOggetti> oggettiDomanda = foDomandeOggettiService.findByIdDomandaFo(ORMHelper.getIdcomune(), idDomandaFo);
	if (oggettiDomanda != null) {
	    for (FoDomandeOggetti fod : oggettiDomanda) {
		if (StringUtils.defaultString(fod.getTipoFile()).equalsIgnoreCase(FoDomandeOggettiService.TIPO_FILE.ALLEGATO.toString())) {
		    numall++;
		}
	    }
	}
	return numall;
    }

    @Override
    public List<Inventarioprocedimenti> getEndoDomanda(DatiDomandaCart d) {

	List<Inventarioprocedimenti> endos = new ArrayList<Inventarioprocedimenti>();
	Set<String> codiciEndo = d.getDatiContestoDomanda().getEndoAttivi();
	Set<String> codEndoNoCart = d.getDatiContestoDomanda().getEndoNoCartAttivi();
	Set<PkId> inseriti = new HashSet<PkId>();
	Integer scId = d.getDatiContestoDomanda().getIdAlberoProc();
	PkId key = null;
	Inventarioprocedimenti endo = null;
	if (scId != null) {
	    log.debug("cerco la natura dall'avvio dell'attività {} in quanto non possibile risalire ad una specifica", d.getDatiContestoDomanda()
		    .getCodiceAttivitaBdr());
	    // Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), scId));
	    StpEndoTipo2 endoPrincipale = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), scId);
	    if (endoPrincipale != null) {
		key = endoPrincipale.getInventarioprocedimenti().getId();
		if (!inseriti.contains(key)) {
		    inseriti.add(key);
		    endo = this.inventarioprocedimentiService.findById(key);
		    endo.setCodiceEndoRegionale(endoPrincipale.getCodiceEndoRegionale());
		    endos.add(endo);
		}
	    }
	}
	if (codiciEndo != null) {
	    for (String c : codiciEndo) {
		StpEndoTipo1 st1 = stpEndoTipo1Service.findByCodiceEndoRegionale(ORMHelper.getIdcomunebase(), c);
		if (st1 != null) {
		    if (st1.getInventarioprocedimenti() != null) {
			if (st1.getInventarioprocedimenti().getNaturaendo() != null) {
			    key = st1.getInventarioprocedimenti().getId();
			    if (!inseriti.contains(key)) {
				inseriti.add(key);
				endo = this.inventarioprocedimentiService.findById(key);
				endo.setCodiceEndoRegionale(st1.getCodiceEndoRegionale());
				endos.add(endo);
				log.debug("Trovata naturaendo base {}", new Object[] { st1.getInventarioprocedimenti().getNaturaendo() });
			    }
			}
		    }
		}
	    }
	}
	if (codEndoNoCart != null) {
	    for (String c : codEndoNoCart) {
		PkId id = inventarioprocedimentiService.getIdFromEndoprocedimentoKey(c);
		if (id != null) {
		    StpEndoTipo1 st1 = stpEndoTipo1Service.findByInventarioProcedimenti(id.getIdcomune(), id.getCodice());
		    if (st1 != null) {
			if (st1.getInventarioprocedimenti() != null) {
			    if (st1.getInventarioprocedimenti().getNaturaendo() != null) {
				key = st1.getInventarioprocedimenti().getId();
				if (!inseriti.contains(key)) {
				    inseriti.add(key);
				    endo = this.inventarioprocedimentiService.findById(key);
				    endo.setCodiceEndoRegionale(st1.getCodiceEndoRegionale());
				    endos.add(endo);
				    log.debug("Trovata naturaendo base {}", new Object[] { st1.getInventarioprocedimenti().getNaturaendo() });
				}
			    }
			}
		    } else {
			StpEndoTipo2 st2 = stpEndoTipo2Service.findByAlberoprocInventarioproc(id.getIdcomune(), scId, id.getCodice());
			if (st2 != null) {
			    if (st2.getInventarioprocedimenti() != null) {
				if (st2.getInventarioprocedimenti().getNaturaendo() != null) {
				    key = st2.getInventarioprocedimenti().getId();
				    if (!inseriti.contains(key)) {
					inseriti.add(key);
					endo = this.inventarioprocedimentiService.findById(key);
					endo.setCodiceEndoRegionale(st2.getCodiceEndoRegionale());
					endos.add(endo);
				    }
				}
			    }
			} else {
			    endo = inventarioprocedimentiService.findById(id);
			    endo.setCodiceEndoRegionale(FACCTConstants.PRESENTAZIONE_DOMANDA_CODICE_ENDOLCALE_PREFIX
				    + CartModuloHelper.cleanCodiceEndoLoc(c));
			    endos.add(endo);
			}
		    }
		}
	    }
	}
	return endos;
    }
}
