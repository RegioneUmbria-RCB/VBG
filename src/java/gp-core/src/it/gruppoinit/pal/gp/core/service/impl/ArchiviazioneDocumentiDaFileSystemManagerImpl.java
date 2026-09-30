package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ArchiviazioneDocumentiDaFileSystemManager;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.LoggerArchiviazioneDocumentiDaFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArchiviazioneDocumentiDaFileSystemManagerImpl extends BaseEnvironment implements ArchiviazioneDocumentiDaFileSystemManager {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneDocumentiDaFileSystemManagerImpl.class);
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;

    @Override
    synchronized public void eseguiArchiviazione(String idcomunealias, String software, String tipomov, String pathRepository) {

	log.info("ArchiviazioneDocumentiDaFileSystemManagerImpl#eseguiArchiviazione: start");
	String messageError = "";
	String messageLog = "";
	log.debug("eseguiArchiviazione# Controllo se il tipo movimento a cui allegare i file è stato impostato");
	boolean isTipoMovConfigurato = false;
	if (StringUtils.isNotBlank(tipomov)) {
	    isTipoMovConfigurato = true;
	    log.debug("eseguiArchiviazione# Tipo movimento configurato : {}", tipomov);
	}
	log.debug("Setto i parametri idcomune e software nell' ORMHelper");
	if (StringUtils.isNotBlank(idcomunealias)) {
	    if (StringUtils.isNotBlank(software)) {
		setORMHelperSoftware(idcomunealias, software);
	    } else {
		setORMHelper(idcomunealias);
	    }
	}
	List<File> directorys = null;
	try {
	    log.debug("Recupero la directory dove sono salvati i file");
	    directorys = searchDirectory(pathRepository);
	    //"/mnt/nfs2volstor/autovbg"; 
	    log.debug("percorso repository : {}", pathRepository);
	} catch (RuntimeException re) {
	    log.error("{}, non trovata", pathRepository);
	    messageError = pathRepository + ", non trovata";
	}
	// Controllo se si sono già verificati errori, in particolare se non ho trovato il path
	// configurato come repositoty. Non posso fare nessuna operazione, neanche la cancellazione.
	if (StringUtils.isBlank(messageError)) {
	    if (!directorys.isEmpty()) {
		for (File directory : directorys) {
		    log.debug("Dal nome della directory recupero il codice istanza");
		    Integer codiceIstanza = null;
		    try {
			codiceIstanza = Integer.parseInt(directory.getName());
			log.debug("Codice istanza: {}", codiceIstanza);
		    } catch (Exception e) {
			log.error(
				"Attenzione impossibile procedere con l'operazione di salvataggio dei documenti, format codice istanza [{}] non corretto. Errore {}",
				new Object[] { directory.getName(), e.getMessage() });
			messageError = "Format codice istanza non corretto : " + directory.getName() + "\n";
		    }
		    // Se sono riuscito a recuperare un codice istanza valido vado avanti
		    if (codiceIstanza != null) {
			Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(directory.getName())));
			// Se il codice istanza recuperato corrisponde a un'istanza vado avanti
			if (EntityUtils.getNestedProperty(istanza, "id.codice") != null) {
			    log.debug("Trovata l'istanza {} per il codice {}", new Object[] { istanza.getNumeroistanza(), codiceIstanza });
			    log.debug("eseguiArchiviazione# Recupero tutti i file dal path {}", directory);
			    File[] file = getFileFromDirectory(directory);
			    messageLog = "Istanza numero " + istanza.getNumeroistanza() + "[" + istanza.getId().getCodice() + "], numero file : "
				    + file.length + "\n";
			    Movimenti movimento = null;
			    // Se il tipo movimento è presente nella configurazione del job, cerco se esiste nell'istanza per allegare i file 
			    // direttamente al movimento.
			    if (isTipoMovConfigurato) {
				movimento = movimentiService.findMovimentiByTipoMovimento(istanza.getId().getCodice(), tipomov);
			    }
			    log.debug("Ciclo tutti i file recuperati");
			    for (int i = 0; i < file.length; i++) {
				try {
				    insertDocumento(movimento, istanza, file[i]);
				    messageLog += "Inserito il file: " + file[i].getName();
				    File f = new File(file[i].getPath());
				    f.delete();
				    messageLog += ", file cancellato\n";
				} catch (IOException e) {
				    log.error("Non è stato possibilie inserire il documento sull'istaza {}[{}]. Errore: {}",
					    new Object[] { istanza.getNumeroistanza(), codiceIstanza, e.getMessage() });
				    messageError += "Format codice istanza non corretto : " + directory.getName() + "\n";
				}
			    }
			    // Messaggio che verrà riportato come evento dell'istanza, notifica all'operatore l'avvenuta operazione
			    String messaggioNuovoDocumenti = "";
			    if (EntityUtils.getNestedProperty(movimento, "id.codice") != null) {
				messaggioNuovoDocumenti = "Negli allegati del movimento <b>" + movimento.getDescrizioneMovimento()
					+ " </b>sono presenti nuovi documenti provenienti dal sistema oneri";
			    } else {
				messaggioNuovoDocumenti = "Nella sezione 'Documenti' sono presenti nuovi documenti provenienti dal sistema oneri";
			    }
			    istanzeeventiService.insert(messaggioNuovoDocumenti, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
			} else {
			    log.error("Attenzione impossibile recuperare l'istanza per il codice passato: {}", codiceIstanza);
			    messageError += "Impossibile recuperare l'istanza per il codice passato: " + codiceIstanza + "\n";
			}
			// Scrive il messaggio di log su file.
			LoggerArchiviazioneDocumentiDaFileSystem.logArchiviazioneDocumentiDaFileSystem(messageLog);
		    }
		    // Prende la directory corrente e cancella tutto il suo contenuto ricorsivamente 
		    if (directory.isDirectory()) {
			deleteDirectory(directory);
		    }
		}
	    } else {
		LoggerArchiviazioneDocumentiDaFileSystem.logArchiviazioneDocumentiDaFileSystem("Directory vuota, file non presenti");
	    }
	}
	if (StringUtils.isNotBlank(messageError)) {
	    LoggerArchiviazioneDocumentiDaFileSystem.logArchiviazioneDocumentiDaFileSystemErrori(messageError);
	}
	log.info("ArchiviazioneDocumentiDaFileSystemManagerImpl#eseguiArchiviazione: end");
    }

    private List<File> searchDirectory(String path) {

	List<File> directory = new ArrayList<File>();
	File file = new File(path);
	if (file.isDirectory()) {
	    File[] filesInDir = file.listFiles();
	    Arrays.sort(filesInDir);
	    for (File f : filesInDir) {
		String prefix = "";
		if (f.isFile()) {
		    //list.add(f.toString());
		    prefix = "[f] ";
		} else if (f.isDirectory()) {
		    prefix = "[d] ";
		    directory.add(f);
		}
	    }
	}
	return directory;
    }

    /**
     * metodo che restituisce un array di byte a partire da un oggetto File
     * 
     * @param file
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private byte[] getBytesFromFile(File file) throws IOException {

	InputStream is = new FileInputStream(file);
	long length = file.length();
	if (length > Integer.MAX_VALUE) {
	    throw new IOException("File troppo grande!");
	}
	byte[] bytes = new byte[(int) length];
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	if (offset < bytes.length) {
	    throw new IOException("Errore durante la lettura del file: " + file.getName());
	}
	is.close();
	return bytes;
    }

    private File[] getFileFromDirectory(File directory) {

	File[] files = directory.listFiles();
	return files;
    }

    private boolean deleteDirectory(File path) {

	if (path.exists()) {
	    File[] files = path.listFiles();
	    for (int i = 0; i < files.length; i++) {
		if (files[i].isDirectory()) {
		    deleteDirectory(files[i]);
		} else {
		    files[i].delete();
		}
	    }
	}
	return (path.delete());
    }

    private void insertDocumento(Movimenti movimento, Istanze istanza, File file) throws IOException {

	if (EntityUtils.getNestedProperty(movimento, "id.codice") != null) {
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    movimentiallegati.setDataregistrazione(new Date());
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setDescrizione(file.getName());
	    Oggetti oggetto = new Oggetti();
	    oggetto.setNomefile(file.getName());
	    byte[] b = getBytesFromFile(file);
	    oggetto.setDimensioneFile(b.length);
	    oggetto.setOggetto(b);
	    movimentiallegati.setOggetto(oggetto);
	    oggettiService.insert(oggetto);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegatiService.insert(movimentiallegati);
	} else {
	    Documentiistanza documentiistanza = new Documentiistanza();
	    documentiistanza.setData(new Date());
	    documentiistanza.setIstanza(istanza);
	    Oggetti oggetti = new Oggetti();
	    oggetti.setNomefile(file.getName());
	    byte[] b = getBytesFromFile(file);
	    oggetti.setDimensioneFile(b.length);
	    oggetti.setOggetto(b);
	    oggettiService.insert(oggetti);
	    documentiistanza.setOggetto(oggetti);
	    documentiistanzaService.insert(documentiistanza);
	}
    }
}
