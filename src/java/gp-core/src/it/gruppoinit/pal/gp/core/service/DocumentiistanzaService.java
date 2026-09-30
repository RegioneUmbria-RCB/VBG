package it.gruppoinit.pal.gp.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.dao.DocumentiistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCompareHelper;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.service.helper.DocumentiIstanzaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.RiepilogoHelper;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

public interface DocumentiistanzaService extends BaseService<Documentiistanza, PkId>, SoftwareByCodiceOggettoReaderService {

    /**
     * Trova tutti i documenti di un'istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza);

    /**
     * Trova tutti i documenti di un'istanza filtrando per flgDaModelloDinamico e ordinando per documento
     * <ol>
     * <li>Se [flgDaModelloDinamico = true] ritorna la lista dei documentiistanza provenienti da modelli dinamici</li>
     * <li>Se [flgDaModelloDinamico = false] ritorna la lista dei documentiistanza che non provengono da modelli
     * dinamici</li>
     * </ol>
     * 
     * @param codiceIstanza
     * @param flgDaModelloDinamico
     * @return
     */
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico);

    /**
     * @see DocumentiistanzaDAO#findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico)
     * 
     */
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico);

    /**
     * @see DocumentiistanzaDAO#findDocumentiistanzaDTOByIstanza(Integer codiceIstanza)
     * 
     */
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza);

    /**
     * Trova tutti i documenti di un'istanza che hanno oggetto
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Documentiistanza> findByIstanzaOggetto(Integer codiceistanza);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'istanza (parametro codiceIstanza)
     * 
     * &#64;param codiceIstanza
     * &#64;return
     * </pre>
     */
    public int countByIstanza(Integer codiceIstanza);

    /**
     * Elimina la lista di documentiistanza fornita in input
     * 
     * @param documentiistanzas
     */
    public void deleteDocumentiistanzas(List<Documentiistanza> documentiistanzas);

    /**
     * Ritorna la lista di tutti i documenti dell'istanaza provenieti da STC (record tabella DOCUMENTIISTANZA con il
     * campo stcIdallegato notBlank) filtrati per istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Documentiistanza> findProvenientiDaSTC(Integer codiceIstanza);

    /**
     * <pre>
     * Ritorna:
     * True	: se esiste almeno un allegato proveniete da STC in documenti istanza per l'istanza passata
     * False 	: se non esiste almeno un allegato proveniete da STC in documenti istanza per l'istanza passata
     * 
     * &#64;param codiceIstanza : filtro 
     * &#64;return
     * </pre>
     */
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza);

    /**
     * 
     * <pre>
     * Salva sulla tabella documenti istanza il file recuperato dal protocollo: 	
     * 		1- Recupera il documento tramite il WS esposto dal servizio di protocollazione (leggiAllegato) 
     * 		2- Crea l'oggetto documenti istanza 
     * 		3- Salva l'oggetto creato sul DB
     * 
     * Prima di creare l'oggetto e inserirelo,se il parametro chekFileIsEsistente è uguale a true,  il sistema controlla che un oggetto con la stessa descrizione non sia presente 
     * su documenti istanza. Nel caso esista, non permette
     * l'inserimento del documento specificando che non è possibile inserire in quanto già esistente.
     * 
     * &#64;param idBase
     *            : codice identificativo dell'allegato proveniente dal servizio di protocollo
     * &#64;param descrizioneFile  
     * 	          : nome del file salvato sul protocollo
     * &#64;param codiceIstanza : codice dell'istanza in cui andremo a salvare il documento
     * &#64;param chekFileIsEsistente : specifica se controllare o no se il file è già stato inserito
     * 
     * </pre>
     */
    public void salvaDocumentoProtocollo(String idBase, Integer codiceIstanza, String descrizioneFile, boolean chekFileIsEsistente);

    /**
     * 
     * <pre>
     * 
     * Per ognuno dei documenti presenti sul protocollo controlla se non è stato già salvato. Se non è già presente lo salva
     * sulla tabella documenti istanza il file recuperato dal protocollo	
     * 		1- Recupera il documento tramite il WS esposto dal servizio di protocollazione (leggiAllegato) 
     * 		2- Crea l'oggetto documenti istanza 
     * 		3- Salva l'oggetto creato sul DB
     * 
     * 
     * &#64;param mappaGiaSalvati
     *            : codice identificativo dell'allegato proveniente dal servizio di protocollo
     * &#64;param allegato  
     * 	          : nome del file salvato sul protocollo
     * &#64;param codiceIstanza : codice dell'istanza in cui andremo a salvare il documento
     * 
     * </pre>
     */
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceIstanza);

    /**
     * @see DocumentiistanzaDAO#updatePresente(Integer codiceDocIstanza, Boolean isCheked)
     * 
     */
    public void updatePresente(Integer codiceDocIstanza, Boolean isCheked);

    /**
     * @see DocumentiistanzaDAO#updateNecessario(Integer codiceDocIstanza, boolean necessario)
     * 
     */
    public void updateNecessario(Integer codiceDocIstanza, boolean necessario);

    /**
     * <pre>
     * Funzione specifica che allinea i documenti dell'istanza: 
     * 1. Copia i documenti presenti nell'intevento sui documenti dell'istanza, se sono selezionati come aggiungi 
     * 2. Elimina i documenti dell'istanza che non hanno un oggetto collegato e non selezionati
     * </pre>
     * 
     * @param istanza
     * @param cambioInterventoCommand
     */
    public void updateAllineaDocumenti(Istanze istanza, List<CambioInterventoCompareHelper> docs);

    /**
     * Crea un nuovo documento istanza per il codiceistanza e codice oggetto passati
     * 
     * @param codiceIst
     * @param codOggetto
     */
    public void insertAllegatoInIstanza(Integer codiceIst, Integer codOggetto);

    /**
     * Restituisce la lista di tutti i documenti dell'istanza per cui esiste l'oggetto associato e il cui nome
     * corrisponde ai criteri di ricerca specificati
     * 
     * @param codiceIstanza
     *            codice dell'istanza in cui cercare il documento
     * @param nameSearch
     *            stringa con cui confrontare il nome del file
     * @param searchMode
     *            specifica il tipo di riceca che si intende effettuare. sono accettati solo i tipi di ricerca
     *            compatibili con il tipo stringa.
     * @return
     */
    public List<Documentiistanza> findByNome(Integer codiceIstanza, String nameSearch, FieldOperationsEnum searchMode);

    /**
     * Recupera tutti i documenti all'interno dell'oggetto che sono settati come il flag "transientSegnaPerInvio" e le
     * raggruppa in un archivio zip
     */
    public ByteArrayOutputStream downloadDocumentiZip(DocumentiHelper documentiHelper);

    /**
     * Ritorna la lista dei documenti filtrando per istanza e codice oggetto
     * 
     * @param codiceIstanza
     * @param codiceOggetto
     * @return
     */
    public List<Documentiistanza> findByIstanzaAndOggetto(Integer codiceIstanza, Integer codiceOggetto);

    /**
     * <pre>
     * Inserisce N documennti dell'istanza in base agli N Oggetti passati. I documenti dell'istanza avranno tutte le stesse caratteristiche,
     * le differenze saranno su:
     * 	1. Nome : sara dato dal suffisso [NM]  Documento (Es. [01] documento di test)
     * 	2. L'oggetto : sarà quello passato
     * &#64;param entity
     * &#64;param Multi
     * </pre>
     */
    public Integer insertMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles);

    public Integer insertSingoloOrMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles);

    public Documentiistanza updateAggiornaRiepilogo(Istanze istanza, byte[] content, String nomeFile);
    
    public RiepilogoHelper rigeneraRiepilogo(Integer codiceistanza) throws Exception;

    public Integer findOggettoArConsole(String valoreDaRicercare, Integer codice);

    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico, Boolean isCodiceOggetto);

    /**
     * Restituisce la lista dei documenti dell'istanza che non sono presenti in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceistanza
     * @param codiceautorizzazione
     * @return
     */
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanzaNonInDocAutorizzazione(Integer codiceistanza, Integer codiceautorizzazione);

    public List<Documentiistanza> findByIstanzaNomeFile(String nomefile, Integer codiceIstanza);

    /**
     * Trova tutti i documenti delle istanze che hanno oggetto
     * 
     * @param codiceOggetto
     * @return
     */
    public List<Documentiistanza> findByCodiceOggetto(Integer codiceOggetto);

    public Integer insertDocumentoDaHelper(Integer codiceIstanza, DocumentiIstanzaRestHelper helper, InputStream documento) throws IOException;

    public BaseEsitoOperazione updateSpostaCopiaDocumenti(Integer codiceIstanza, Integer codiceIstanzaDest, boolean isCopia,
	    Set<Integer> docIstanzaId);
}
