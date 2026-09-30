package it.gruppoinit.pal.gp.core.features.oggetti;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.rest.client.DSSClientException;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface OggettiService extends BaseService<Oggetti, PkId> {

    /**
     * @see OggettiDAO#controllaCancellaOggetto(String, String, Integer)
     */
    // boolean controllaCancellaOggetto(String nomeTabella, String nomeColonna, Integer codiceOggetto);
    /**
     * Recupera da database la lista degli oggetti. <br />
     * <b>ATTENZIONE!!!</b> La lista tornata non contiene nel campo oggetto il contenuto del file. Questo va recuperato
     * solamente dopo avere eseguito una findById sull'oggetto della lista.
     */
    @Override
    public List<Oggetti> findAll(Integer firstResult, Integer maxResult);

    /**
     * Si occupa di recuperare le informazioni dell'oggetto dalla base dati e di recuperare il contenuto del file dal
     * sistema di persistenza (Database, FileSystem, ecc..) adottato.
     */
    @Override
    public Oggetti findById(PkId id);

    /**
     * Si occupa di recuperare le informazioni principali ( escluso il contenuto Binario ) dell'oggetto dalla base dati.
     * Serve per quelle funzionalità che non utilizzano il contenuto binario (che non viene recuperato) ad esempio
     * durante la cancellazione di un oggetto. Può tornare un oggetto nullo. Se l'oggetto viene recuperato allora è
     * svincolato dalla Sessione di Hibernate e in questo caso non deve essere riassociato come oggetto ad una entity in
     * quanto può verificarsi una perdita di dati. Deve in pratica essere usato solamente come oggetto per dei
     * controlli. Per tutte le altre operazioni va usato il metodo findById().
     */
    public Oggetti findByIdLazy(PkId id);

    /**
     * @see OggettiDAO#findNomeById(PkId)
     * 
     * @param id
     * @return
     */
    public String findNomeById(PkId id);

    /**
     * @see OggettiDAO#findNomeById(PkId)
     * 
     * @param id
     * @return
     */
    public String findNomeByCodice(Integer intId);

    /**
     * @see OggettiDAO#findNomeById(PkId)
     * 
     * @param id
     * @return
     */
    public String findNomeByCodice(String strId);

    /**
     * Si occupa di salvare le informazioni dell'oggetto sulla base dati e di salvare il contenuto del file a seconda
     * del sistema di persistenza (Database, FileSystem, ecc..) adottato. Si occupa anche di popolare il campo
     * dimensioneFile della entity
     */
    @Override
    public void insert(Oggetti entity);

    /**
     * Si occupa di salvare le informazioni dell'oggetto sulla base dati e di salvare il contenuto del file a seconda
     * del sistema di persistenza (Database, FileSystem, ecc..) adottato. Si occupa anche di popolare il campo
     * dimensioneFile della entity. Si occupa di storicizzare l'oggetto
     */
    @Override
    public void update(Oggetti entity);

    /**
     * Il metodo richiama l'update del service, senza però storicizzare l'oggetto. Viene utilizzato ad esempio in caso
     * di ripristino di un oggetto precedentemente storicizzato
     * 
     * @param entity
     */
    public void aggiornaSenzaStoricizzare(Oggetti entity);

    @Override
    public void delete(Oggetti entity);

    public void deleteAll(List<Integer> objectsIdsToDelete);

    @Override
    public Oggetti bindDomainObject(Oggetti entity, Class<?> idClass, String idPath);

    /**
     * Metodo che restituisce il path al percorso condiviso dei file nel caso in cui sia attiva la verticalizzazione
     * <b>FILESYSTEM</b> ed il parametro SHAREDPATH sia compilato. Il percorso resituito rappresenta una condivisione
     * pubblica ad es: <b>\\fileserver2\files\</b> dalla quale i file possano essere letti/scritti
     * 
     * @return
     */
    // public String getSharedPath();
    /**
     * La funzione torna il corretto puntamento al file nel caso sia attiva la verticalizzazione FileSystem ed il
     * parametro SharedPath sia compilato. A questa stringa viene aggiunto il percorso messo sulla tabella oggetti ed il
     * nomefile. Se la verticalizzazzione non è attiva o il parametro sharedpath è vuoto torna null che indica che il
     * file non deve essere scaricato tramite condivisione
     * 
     * @param codiceOggetto
     * @return
     */
    public String getSharedFileLink(Integer codiceOggetto);

    @DeletableCacheElements
    public void resetObjectCached();

    /**
     * Segna il file come BLOCCATO in scrittura dall'utente
     * 
     * @param codiceoggetto
     * @param codiceResponsabile
     * @throws SecurityException
     *             in caso che il file sia bloccato da un'altro utente
     */
    public void updateFilesBloccaModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException;

    /**
     * rimuove il BLOCCO in scrittura sui files passati
     * 
     * @param codiceoggetto
     * @param codiceResponsabile
     */
    public void updateFilesRimuoviBloccoModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException;

    /**
     * rimuove il BLOCCO in scrittura sul file
     * 
     * @param codiceoggetto
     * @param codiceResponsabile
     */
    public void updateFileRimuoviBloccoModifica(Integer codiceoggetto, Integer codiceResponsabile) throws SecurityException;

    public InputStream getOggettoAsInputStream(Integer codiceOggetto);

    public String insertOrGetUID(Integer codiceOggetto);

    public String insertOrGetSHA256(Integer codiceOggetto);

    public void evict(Oggetti entity);

    /**
     * Elimina l'oggetto medato che blocca il file.
     * 
     * @param oggetto
     */
    public void updateSbloccaOggetto(OggettiMetadati oggettiMetadati);

    /**
     * A partire dal codice oggetto crea un link del tipo : // file: [link_allegato] , PIN: [codiceoggetto] <br />
     * [link_allegato] : percorso che permette di fare il download del file fisico tramite un applicazione esterna
     */
    public String creaSingoloLinkAllegati(Integer codiceOggetto);

    /**
     * Il metodo crea un documento contenete i link salvati sulla tabella TEMP_LINKALLEGATI con uuid passato a partire
     * da un template.
     * 
     * @param lettereTipo
     * @param entity
     * @param codiceLetteraTipoAllegati
     * @param uuid
     * @return
     */
    public Integer creaDocumentoConLinkOggetti(Letteretipo lettereTipo, Integer codiceIst, Integer codiceMov, String codiceTipoMov, String uuid);

    /**
     * Trasforma il file rtf in un file pdf tramite il servizio di conversione in pdf
     * 
     * @param oggettoRtf
     * @return
     */
    public byte[] trasformRtfInPdf(Oggetti oggettoRtf);

    /**
     * Trasforma il file con estensione passata in un file pdf tramite il servizio di conversione in pdf Se l'estensione
     * è null viene calcolata in automatico dal nome file
     * 
     * @param oggettoRtf
     * @return
     */
    public byte[] trasformInPdf(Oggetti oggetto, String estensione);

    public void aggiornaMetadatiCMIS(Integer codiceOggetto);

    public Oggetti insert(MultipartFile multipartFile);

    /**
     * Trasforma l'oggetto passato un file pdf. Non ritorna l'oggetto ma un nuovo oggetto con il nome deil file e il
     * BLOB aggiornato
     * 
     * @param codiceOgegtto
     * @return
     */
    public Oggetti convertFileInPdf(Integer codiceOggetto);

    /**
     * Trasforma l'oggetto passato un file pdf. Non ritorna l'oggetto ma un nuovo oggetto con il nome deil file e il
     * BLOB aggiornato
     * 
     * @param obj
     * @return
     */
    public Oggetti convertFileInPdf(Oggetti obj) throws FunzioneBusinessRemotaException;

    /**
     * 
     * Trasforma l'oggetto passato in un file pdf e aggiorna l'oggetto con il nuovo file.
     * 
     * @param codiceOggetto
     * @return
     */
    public Oggetti convertFileInPdfAndSostituisci(Integer codiceOggetto);

    /**
     * 
     * Trasforma l'oggetto passato in un file pdf e aggiorna l'oggetto con il nuovo file.
     * 
     * @param codiceOggetto
     * @return
     */
    public Oggetti convertFileInPdfAndSostituisci(Oggetti oggetti);

    public void updateOggettoFirmatoCAdES(Oggetti oggetti);

    public void updateOggettoFirmatoPAdES(Oggetti oggetti);

    /**
     * Trasforma l'oggetto passato in un file pdf. Non ritorna l'oggetto ma un nuovo oggetto con il nome deil file e il
     * BLOB aggiornato
     * 
     * @param oggetti
     * @return
     */
    public Oggetti verificaConvertiPdf(Oggetti oggetti);

    /**
     * se eCMIS=true torna True<br/>
     * se eAPIAlfresco=true e solaLettura=false torna true<br/>
     * se eAPIAlfresco=true e solaLettura=true torna false<br/>
     * 
     * @param eCMIS
     * @param eApiAlfresco
     * @param eApiAlfrescoSolaLettura
     * @return
     */
    boolean scriviSuAlfrescoOCMIS(boolean eCMIS, boolean eApiAlfresco, boolean eApiAlfrescoSolaLettura);

    /**
     * ATTENZIONE SEGNATURA DICHIARATA ANCHE IN APPLICATIONCONTEXT.XML per rollback
     * @param oggetto
     * @throws DSSClientException
     */
    void processaMetadatiFirmaOggetto(Oggetti oggetto) throws DSSClientException;
}
