package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.helper.SituazioneAllegato;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.TIpoDocumentoDocumentiCondivisiMetadato;

public interface DocumentiHelperService {

    /**
     * <pre>
     * Il metodo popola le liste dei documenti:
     *  1. Documenti dell'istanza
     *  2. Documenti dei procedimenti dell'istanza
     *  3. Documenti del movimento da cui si sta generando la mail
     *  4. Documenti delle CDS
     *      
     * &#64;param codiceMovimento
     * &#64;return
     * </pre>
     */
    public DocumentiHelper findDocumentiInvioMailDaMovimento(Integer codiceMovimento);

    /**
     * <pre>
     * Il metodo popola le liste dei documenti:
     *  1. Documenti dell'istanza
     *  2. Documenti dei procedimenti dell'istanza
     *  3. Documenti del movimento da cui si sta protocollando
     *  4. Documenti dei movimenti che appartengono alla stessa istanza
     *  5. Documenti delle CDS
     *  Le liste 3 e 4 verranno popolato solo se il parametro  "isProtocolloDaMovimento" è ugualea true
     * &#64;param codiceIstanza
     * &#64;param codiceMovimento
     * &#64;param isProtocolloDaMovimento
     * &#64;return
     * </pre>
     */
    public DocumentiHelper findDocumentiInvioDocumentiProtocollo(Integer codiceMovimento, Integer codiceIstanza, boolean isProtocolloDaMovimento);

    /**
     * <pre>
     * Il metodo popola le liste dei documenti:
     *  1. Documenti dell'istanza
     *  2. Documenti dei procedimenti dell'istanza
     *  3. Documenti del movimento da cui si sta facendo la notifica
     *  4. Documenti delle CDS
     *  
     * &#64;param codiceIstanza
     * &#64;param codiceMovimento
     * &#64;param isProtocolloDaMovimento
     * &#64;return
     * </pre>
     */
    public DocumentiHelper findDocumentiInvioDocumentiSTC(Integer codiceMovimento);

    /**
     * <pre>
     * 
     * Ritorna le liste popolate di documentiHelper bonificate degli elementi che hanno il flag transientSegnaPerInvio posto a false
     * &#64;param documentiHelper
     * &#64;return
     * 
     * </pre>
     */
    public DocumentiHelper findDocumentiInvioTrue(DocumentiHelper documentiHelper);

    /**
     * Ritorna una lista di documenti istanza
     * 
     * @param documentiIstanzaList
     * @return
     */
    public List<DocumentiistanzaDTO> findDocumentiIstanza(List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList);

    /**
     * Ritorna una lista di documenti delle procure
     * 
     * @param documentiProcureList
     * @return
     */
    public List<IstanzeprocureDTO> findDocumentiProcure(List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> documentiProcureList);

    /**
     * Ritorna una lista di documenti deglie endo
     * 
     * @param documentiEndoprocedimentiList
     * @return
     */
    public List<IstanzeallegatiDTO> findDocumentiEndo(List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoprocedimentiList);

    /**
     * Ritorna una lista di documento dei movimenti
     * 
     * @param documentiMovimentoList
     * @return
     */
    public List<MovimentiallegatiDTO> findDocumentiMovimento(List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentoList);

    /**
     * Ritorna una lista di documento dell'anagrafe
     * 
     * @param documentiAnagrafeList
     * @return
     */
    public List<AnagrafedocumentiDTO> findDocumentiAnagrafe(List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList);

    /**
     * <pre>
     * 
     * Il metodo ritorna una mappa <SituazioneAllegato,Integer> che contiene il numero di documenti:
     * 		
     * 		1.Richiesti
     * 		2.Presenti
     * 		3.Verificati
     * 		4.Validi
     * 
     * Nota un documento valido è anche verificato.
     * 
     * &#64;return
     * </pre>
     */
    public Map<SituazioneAllegato, Integer> findSituazioneDocumentiIstanzaEdEndo(Integer codiceIstanza);

    /**
     * Recupera tutta la lista dei codice oggetto dei documenti settati come da inviare
     * 
     * @param documentiHelper
     * @return
     */
    public List<Integer> findCodiceOggettoAllegatiDaInviare(DocumentiHelper documentiHelper);

    /**
     * Ritorna tutti i documenti dell'istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public DocumentiHelper findDocumentiDownloadZip(Integer codiceIstanza);

    /**
     * Ritorna tutti i documenti dell'istanza, esclusi quelli presenti nello zip logico del movimento.
     * 
     * @param codiceistanza
     * @param codicemovimento
     * @return
     */
    public DocumentiHelper findDocumentiDaAggiungereAZipLogico(Integer codiceistanza, Integer codicemovimento);

    /**
     * La funzione verifica se il documento appartiene ad una istanze del software dichiarato come parametro, se il
     * responsabile ha i permessi su quel software. In caso positivo ritorna l'oggetto altrimenti una securityException
     * 
     * @param responsabile
     * @param software
     * @param codiceoggetto
     * @return L'oggetto richiesto
     */
    public Oggetti checkDocumentoPerResponsabile(Integer responsabile, String software, Integer codiceoggetto) throws SecurityException;

    /**
     * La funzione cerca di capire dove il codiceOggetto passato è referenziato nell'istanza di cui viene passato il
     * codice
     * 
     * @param codiceIstanza
     * @param codiceMovimento
     * @param codiceOggetto
     * @return
     */
    public TIpoDocumentoDocumentiCondivisiMetadato findMetadatoProvenienzaDocumento(Integer codiceIstanza, Integer codiceMovimento,
	    Integer codiceOggetto);
}
