package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerArchiviazioneDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzePerArchiviazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiIstanza;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggetto;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggettoList;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneArchiviazioneDocumentale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IstanzePerArchiviazioneServiceImpl extends BaseServiceImpl<Istanze, PkId> implements IstanzePerArchiviazioneService {

    public static final Logger log = LoggerFactory.getLogger(IstanzePerArchiviazioneServiceImpl.class);
    @Autowired
    private IstanzeDAO istanzeDAO;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private IstanzeprocureService istanzeprocureService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private IstanzestradarioService istanzestradarioservice;

    @Override
    public List<IstanzePerArchiviazioneDTO> findIstanzePerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter) {

	return istanzeDAO.findIstanzePerArchiviazioneDocumentale(filter);
    }

    @Override
    public Set<IstanzePerArchiviazioneDTO> findIstanzeConOggettiPerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter) {

	return istanzeDAO.findIstanzeConOggettiPerArchiviazioneDocumentale(filter);
    }

    @Override
    public ArchiviazioneMetadatiIstanza findMetadatiIstanza(Integer codiceIstanza) {

	ArchiviazioneMetadatiIstanza ami = istanzeDAO.findMetadatiIstanzaPerArchiviazioneDocumentale(codiceIstanza);
	//TODO mancano i metadati titolario e collegamento (al momento il titolario non è utilizzato, dice la Mazzoni di Livorno, 
	//il collegamento è il numero di protocollo della pratica collegata
	List<Istanzestradario> istanzestradarioList = istanzestradarioservice.findByIstanza(codiceIstanza);
	if (!istanzestradarioList.isEmpty()) {
	    ami.setIndirizzo(istanzestradarioList.get(0).getDescrizioneEstesaTransient());
	}
	return ami;
    }

    @Override
    public ArchiviazioneMetadatiOggettoList findOggettiArchiviabili(ArchiviazioneMetadatiIstanza metadatiIstanza,
	    VerticalizzazioneArchiviazioneDocumentale vad) {

	return this.findOggettiArchiviabili(metadatiIstanza, null, vad);
    }

    @Override
    public ArchiviazioneMetadatiOggettoList findOggettiArchiviabili(ArchiviazioneMetadatiIstanza metadatiIstanza,
	    IstanzePerArchiviazioneFilter filter, VerticalizzazioneArchiviazioneDocumentale vad) {

	ArchiviazioneMetadatiOggettoList archiviazioneMetadatiOggettoList = new ArchiviazioneMetadatiOggettoList();
	Set<ArchiviazioneMetadatiOggetto> oggettiArchiviabili = new HashSet<ArchiviazioneMetadatiOggetto>();
	boolean isDocPrincipaleTrovato = false;
	String[] docPSearch = vad.getDocumentoPrincipaleSearchString();
	ArchiviazioneMetadatiOggetto metaOggTemp = null;
	List<ArchiviazioneMetadatiOggetto> metadatiOggettiIstanza = new ArrayList<ArchiviazioneMetadatiOggetto>();
	metadatiOggettiIstanza = istanzeDAO.findMetadatiOggettiPerArchiviazioneDocumentale(metadatiIstanza.getCodiceIstanza(), filter,
		ArchiviazioniManagerImpl.ARCHIVIAZIONE_PER_OGGETTO );
	//metadatiOggettiIstanza = istanzeDAO.findMetadatiOggettiPerArchiviazioneDocumentale(metadatiIstanza.getCodiceIstanza());
	StringBuffer errori = new StringBuffer();
	StringBuffer erroriBloccanti = new StringBuffer();
	for (ArchiviazioneMetadatiOggetto metaOgg : metadatiOggettiIstanza) {
	   // String[] erroreOggArchiviabile = isOggettoArchiviabile(metadatiIstanza, metaOgg, vad);
	    // NON PIù BLOCCANTE
	    //	    if (StringUtils.isBlank(erroreOggArchiviabile[0]) && StringUtils.isBlank(erroreOggArchiviabile[1])) {
	    //		oggettiArchiviabili.add(metaOgg);
	    //		//cerco tra i documenti istanza il doc principale perchè questo deve avere metadati differenti.
	    //		//il doc principale sarà o quello restituito dal metodo isDocumentoPrincipale o in alternativa quello con codiceoggetto minore (il primo inserito)
	    //		if ("DOCUMENTIISTANZA".equals(metaOgg.getOrigine())) {
	    //		    if (!isDocPrincipaleTrovato && isDocumentoPrincipale(metaOgg, docPSearch)) {
	    //			metaOgg.setDocPrincipale(true);
	    //			isDocPrincipaleTrovato = true;
	    //		    }
	    //		    if (metaOggTemp == null) {
	    //			metaOggTemp = metaOgg;
	    //		    } else {
	    //			if (metaOgg.getCodiceOggetto() < metaOggTemp.getCodiceOggetto()) {
	    //			    metaOggTemp = metaOgg;
	    //			}
	    //		    }
	    //		}
	    //	    } else {
	    //		errori.append(erroreOggArchiviabile[0]);
	    //		erroriBloccanti.append(erroreOggArchiviabile[1]);
	    //	    }
	    //	    errori.append(erroreOggArchiviabile[0]);
	    //	    erroriBloccanti.append(erroreOggArchiviabile[1]);
	    oggettiArchiviabili.add(metaOgg);
	}
	//	if (!isDocPrincipaleTrovato && metaOggTemp != null) {
	//	    metaOggTemp.setDocPrincipale(true);
	//	}
	archiviazioneMetadatiOggettoList.setArchiviazioneMetadatiOggettoList(oggettiArchiviabili);
	archiviazioneMetadatiOggettoList.setErrore(errori.toString());
	archiviazioneMetadatiOggettoList.setErroreBloccante(erroriBloccanti.toString());
	return archiviazioneMetadatiOggettoList;
    }

    private boolean isDocumentoPrincipale(ArchiviazioneMetadatiOggetto archMetaOgg, String[] docPSearchString) {

	//il documento principale deve essere ricavato dal nome del file cercando dei pattern configurati in verticalizzazione
	if (docPSearchString != null) {
	    for (String ss : docPSearchString) {
		if (archMetaOgg.getNomeFile().toLowerCase().indexOf(ss.toLowerCase()) != -1) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public String[] isOggettoArchiviabile(ArchiviazioneMetadatiIstanza metaIstanza, ArchiviazioneMetadatiOggetto metaOgg,
	    VerticalizzazioneArchiviazioneDocumentale vad) {

	String errore = "";
	String erroreBloccante = ""; // non più utilizzato
	String[] errori = new String[2];
	boolean isFileExtOk = true;
	boolean isDimFileOk = false;
	Integer maxFileSize = vad.getMaxFileSize();
	String[] fileExtsAmmesse = vad.getFileExtensions();
	List<ChiaveValoreBean<String, String>> fileRepExts = vad.getFileExtensionsReplacement();
	String fileName = metaOgg.getNomeFile();
	//String fileExt = getFileExtension(fileName);
	Integer dimFile = metaOgg.getDimensioneFile();
	// PREFILTRAGGIO SULLE ESTENZIONI POSSIBILI
	//	if (fileExtsAmmesse != null && fileExtsAmmesse.length > 0) {
	//	    //ci sono delle limitazioni sull'estensione, allora metto a false e controllo se è ammessa
	//	    isFileExtOk = false;
	//	    for (String fileExtAmmessa : fileExtsAmmesse) {
	//		if (fileName.toLowerCase().endsWith(fileExtAmmessa)) {
	//		    isFileExtOk = true;
	//		    break;
	//		}
	//	    }
	//	    //metto come ammesse anche le estensioni che dovrò poi modificare
	//	    if (!isFileExtOk && fileRepExts != null) {
	//		for (ChiaveValoreBean<String, String> chiaveValoreBean : fileRepExts) {
	//		    if (fileName.toLowerCase().endsWith(chiaveValoreBean.getChiave())) {
	//			isFileExtOk = true;
	//			break;
	//		    }
	//		}
	//	    }
	//	}
	if (!isFileExtOk) {
	    log.warn("isOggettoArchiviabile: l'oggetto {} ha estensione non ammessa. Le estensioni ammesse sono {}",
		    new Object[] { metaOgg, vad.getFileExtensions() });
	    errore = "Il documento " + metaOgg.getNomeFile() + " dell'istanza " + metaIstanza.getCollegamentoPratiche() + " del modulo "
		    + metaIstanza.getTipologiaPratica() + " non è stato archiviato perchè ha estensione non ammessa. Le estensioni ammesse sono "
		    + Arrays.toString(vad.getFileExtensions()) + "<br />";
	} else {
	    //se l'oggetto supera la dimensione max allora l'istanza va messa tra quelle escluse (erroreBloccante)
	    //se l'estensione è ammessa controllo la dimensione
	    if (dimFile == null) {
		log.info("isOggettoArchiviabile: l'oggetto codice {} non ha una dimensione calcolata, la calcolo.", metaOgg.getCodiceOggetto());
		Oggetti ogg = oggettiService.findById(new PkId(metaOgg.getCodiceOggetto()));
		dimFile = ogg.getOggetto() != null ? ogg.getOggetto().length : 0;
	    }
	    if (dimFile <= maxFileSize || maxFileSize == null) {
		isDimFileOk = true;
	    }
	    if (!isDimFileOk) {
		log.warn("isOggettoArchiviabile: l'oggetto {} supera la dimensione massima ammessa di {} bytes.", metaOgg, vad.getMaxFileSize());
		errore += "Il documento " + metaOgg.getNomeFile() + " dell'istanza " + metaIstanza.getCollegamentoPratiche() + " del modulo "
			+ metaIstanza.getTipologiaPratica() + " supera la dimensione massima ammessa di " + vad.getMaxFileSize() + " bytes.<br />";
	    }
	}
	errori[0] = errore;
	errori[1] = erroreBloccante; // non più utilizzato
	return errori;
    }

    @Override
    public void insert(Istanze entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Istanze entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Istanze entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Istanze> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Istanze findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    public Istanze bindDomainObject(Istanze entity, Class<?> idClass, String idPath) {

	throw new NotImplementedException();
    }

    @Override
    public PkId newIdFromSequencetable(Istanze entity) {

	throw new NotImplementedException();
    }

    @Override
    protected Class getEntityClass() {

	return Istanze.class;
    }
}
