package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.dao.MovimentiallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

public interface MovimentiallegatiService extends BaseService<Movimentiallegati, PkId>, SoftwareByCodiceOggettoReaderService {

    /**
     * Trova tutti i documenti dei movimenti di un'istanza ordinati per MOVIMENTIALLEGATI.DESCRIZIONE,
     * MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Movimentiallegati> findByIstanza(int codiceIstanza);

    /**
     * Trova tutti i documenti dei movimenti di un'istanza che hanno un oggetto ordinati per
     * MOVIMENTIALLEGATI.DESCRIZIONE, MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @param codicemovimento
     *            TODO
     * @return
     */
    public List<Movimentiallegati> findByIstanzaOggetto(int codiceIstanza, int codicemovimento);

    /**
     * Trova tutti i documenti di un movimento ordinati per MOVIMENTIALLEGATI.DESCRIZIONE, MOVIMENTIALLEGATI.ID
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Movimentiallegati> findByMovimento(int codiceMovimento);

    /**
     * Trova tutti i documenti di un movimento ordinati per MOVIMENTIALLEGATI.DESCRIZIONE, MOVIMENTIALLEGATI.ID Se
     * escludiAllegatiSenzaOggetto == true non verranno recuperati gli allegati che non hanno un oggetto
     * 
     * @param codiceIstanza
     * @param escludiAllegatiSenzaOggetto
     * @return
     */
    public List<Movimentiallegati> findByMovimento(int codiceMovimento, boolean escludiAllegatiSenzaOggetto);

    public void insert(Movimentiallegati movimentiallegati);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per il movimento (parametro codiceMovimento)
     * 
     * &#64;param codiceMovimento
     * &#64;return
     * </pre>
     */
    public int countByMovimento(Integer codiceMovimento);

    /**
     * Ritorna la lista di tutti gli allegati di movmenti provenieti da STC (record tabella MOVIMENTIALLEGATI con il
     * campo stcIdallegato notBlank) filtrati per istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Movimentiallegati> findProvenientiDaSTC(Integer codiceIstanza);

    /**
     * <pre>
     * Ritorna:
     * True	: se esiste almeno un allegato proveniete da STC in movimenti allegati per l'istanza passata
     * False 	: se non esiste almeno un allegato proveniete da STC in movimenti allegati per l'istanza passata
     * 
     * &#64;param codiceIstanza : filtro 
     * &#64;return
     * </pre>
     */
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza);

    /**
     * 
     * @param objToDelete
     * @return
     */
    public boolean existsProvenientiDaSTCPerMovimenti(Integer codiceMovimento);

    /**
     * A partire da una riga di movimentiallegati crea una nuova riga con l'oggetto trasformato in PDF. Se il documento
     * di origine non è adatto alla trasformazione o è nullo allora rilancia una eccezione
     * 
     * @param codice
     */
    public void insertTrasformaInPdf(Integer codicemovimentiallegati);

    /**
     * A partire da una riga di movimentiallegati e un codice oggetto esistente, convere il documento in pdf e lo
     * sostituisce al alla riga di movimenti allegati passati
     * 
     * @param codice
     */
    public void insertTrasformaInPdf(Integer codiceMovimentoAllegato, Integer codiceOggetto);

    /**
     * 
     * <pre>
     * Salva sulla tabella movimenti allegati  il file recuperato dal protocollo: 	
     * 		1- Recupera il documento tramite il WS esposto dal servizio di protocollazione (leggiAllegato) 
     * 		2- Crea l'oggetto movimenti allegati
     * 		3- Salva l'oggetto creato sul DB
     * 
     * Prima di creare l'oggetto e inserirelo,se il valore di chekFileIsEsistente=true, il sistema controlla che un oggetto con la stessa descrizione non sia presente 
     * su movimenti allegati. Nel caso esista, non permette
     * l'inserimento del documento specificando che non è possibile inserire in quanto già esistente.
     * 
     * &#64;param idBase
     *            : codice identificativo dell'allegato proveniente dal servizio di protocollo
     * &#64;param descrizioneFile  
     * 	          : nome del file salvato sul protocollo
     * &#64;param codiceMovimento: codice del movimento in cui andremo a salvare i file
     * &#64;param chekFileIsEsistente : specifica se controllare o no se il file è già stato inserito
     * 
     * 
     * </pre>
     */
    public void salvaDocumentoProtocollo(String idBase, Integer codiceMovimento, String descrizioneFile, boolean chekFileIsEsistente);

    /**
     * 
     * <pre>
     * 
     * Per ognuno dei documenti presenti sul protocollo controlla se non è stato già salvato. Se non è già presente lo salva
     * sulla tabella movimenti allegati il file recuperato dal protocollo	
     * 		1- Recupera il documento tramite il WS esposto dal servizio di protocollazione (leggiAllegato) 
     * 		2- Crea l'oggetto documenti istanza 
     * 		3- Salva l'oggetto creato sul DB
     * 
     * 
     * &#64;param mappaGiaSalvati
     *            : codice identificativo dell'allegato proveniente dal servizio di protocollo
     * &#64;param allegato  
     * 	          : nome del file salvato sul protocollo
     * &#64;param codiceMovimento : codice del movimento in cui andremo a salvare il documento
     * 
     * </pre>
     */
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceMovimento);

    /**
     * 
     * Recupera la lista degli allegati associati ai moviemementi dell'istanza esclusi quelli del movimento passato
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @return
     */
    public List<Movimentiallegati> findByIstanzaAndExcludeMoviemento(Integer codiceIstanza, Integer codiceMovimento);

    /**
     * @see MovimentiallegatiDAO#findMovimentiallegatiDTOByIstanza(Integer codiceIstanza)
     * 
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza);

    /**
     * @see MovimentiallegatiDAO#findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento)
     * 
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento);

    /**
     * Recupero il record Movimentiallegati dal codiceoggetto
     * 
     * @param codice
     * @return
     */
    public Movimentiallegati findByOggetto(Integer codice);

    /***
     * torna il record ( se presente ) con codiceoggetto non nullo ordinato per data desc, id desc
     * 
     * @param codiceMovimento
     * @return
     */
    public Movimentiallegati findUltimoMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento);

    /**
     * torna il record ( se presente ) con codiceoggetto indicato nei parametri
     * 
     * @param codiceMovimento
     * @return
     */
    public Movimentiallegati findMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento, Integer codiceoggetto);

    /**
     * Crea un oggetto contenete i link di documenti passati in DocumentiHelper, verrano selezionati per creare il
     * documento solo quelli con il campo transientSegnaPerInvio==true
     * 
     * @param movimento
     * @param codiceLetteraTipo
     * @param documentiHelper
     * @param isZipLogico
     * @return
     */
    public Integer createDocumentoConLink(Movimenti movimento, Integer codiceLetteraTipo, DocumentiHelper documentiHelper, Boolean isZipLogico);

    /**
     * <pre>
     * Recupera gli allegati del tipo movimento selezionato per l'istanza passata. 
     * 
     * 1. codiceMovFiglio,==null && codiceTipoMovAllegati!=null Recupera tutti gli allegati presenti nell'istanza per il movimeto
     *    codiceTipoMovAllegati.Nel caso ce ne fossere più di uno verranno recuperati dquelli del più recente (Orderby data desc,ordineInserimento desc). Nel caso il
     * 2. codiceTipoMovFiglio!=null && codiceTipoMov=MOVPADRE allora gli allegati saranno recuperati dal movimento padre che ha generato il mov
     *    figlio [ codiceMovFiglio,]
     * 
     * &#64;param codiceIstanza
     * &#64;param codiceMovFiglio
     * &#64;param codiceTipoMovAllegati
     * &#64;return
     * </pre>
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceIstanza, Integer codiceMovFiglio,
	    String codiceTipoMovAllegati);

    public Integer createAndInsertMovimentoAllegato(Letteretipo lettera, Integer codiceIstanza, Integer codiceMovimento);

    public void flush();

    public Integer insertMultiFile(Movimentiallegati movimentiallegati, List<MultipartFile> lMultipartFiles);

    public Integer insertSingoloOrMultiFile(Movimentiallegati movimentiallegati, List<MultipartFile> lMultipartFiles);

    public Movimentiallegati findbyMessageId(String idmessage);

    /**
     * Applica sul file pdf passato un layer che contiene numero e data protocollo.
     * 
     * @param mov
     * @param o
     */
    public Oggetti applicaLayerProtocolloPdf(Movimenti mov, Oggetti o) throws FunzioneBusinessRemotaException, Exception;

    /**
     * Genera la domanda in formato xml compatibile con il formato impresa in un giorno a partire dai dati dell'istanza
     * e associa l'xml creato al movimento
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     */
    public void insertCreaAllegatoXmlDomandaSuapRegistroImprese(Integer codiceMovimento);

    public Oggetti applicaAnnotazioneProtocolloPdf(Istanze istanza, Movimenti mov, Oggetti o);

    public void updateApplicaQRCode(Integer codiceMovimentiAllegati);

    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto);

    /**
     * Restituisce la lista degli allegati dei movimenti che non sono presenti in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceIstanza
     * @param codiceAut
     * @return
     */
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione(Integer codiceIstanza, Integer codiceAut);

    public boolean isPresenteInDocAut(Integer codiceMovAllegato, Integer codiceAutorizzazione);

    /**
     * Recupero i record di Movimentiallegati dal codiceoggetto
     * 
     * @param codice
     * @return
     */
    public List<Movimentiallegati> findListByOggetto(Integer codice);

    public TempLinkallegati populateTempLinkallegati(Integer codiceOggetto, String nomeFile, String UUID, String descrizioneDocumento);
}
