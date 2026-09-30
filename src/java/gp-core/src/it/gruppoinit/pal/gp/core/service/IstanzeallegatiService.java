package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAllegatiControlloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IstanzeallegatiService extends BaseService<Istanzeallegati, PkId> {

    /**
     * Torna la lista degli allegati degli endprocedimenti di un'istanza. I record sono ordinati per
     * ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanza(int codiceIstanza);

    /**
     * Torna la lista degli allegati di un endprocedimento attivato di un'istanza. I record sono ordinati per
     * ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @param codiceInventario
     *            il codice dell'endoprocedimento per il quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanzaAndEndo(int codiceIstanza, int codiceInventario);

    /**
     * Torna la lista degli allegati degli endprocedimenti di un'istanza che hanno un oggetto. I record sono ordinati
     * per ISTANZEALLEGATI.FK_IDALLEGATO.ORDINE (SE PRESENTE),ISTANZEALLEGATI.ALLEGATOEXTRA
     * 
     * @param codiceIstanza
     *            il codice dell'istanza per la quale si cercano gli allegati
     * @return
     */
    public List<Istanzeallegati> findByIstanzaOggetto(int intValue);

    /**
     * Ritorna una lista di IstanzeallegatiHelper (istanze allegati helper è una struttura dati che ci permette di
     * visualizzare su una tabella la lista di tutti gli allegati filtrati per istanza e raggruppati per ogni invetario
     * procedimento attivato)
     * 
     * @param istanza
     *            : filtra i dati per l'istanza passata
     * @return ritorna una lista di IstanzeallegatiHelper
     */
    public List<IstanzeallegatiHelper> findIstanzeallegatiHelper(Istanze istanza);

    /**
     * Replica l'inserimento standard di istanze allegati con aggiunta: 1- inserita un ulteriore validazione su il campo
     * allegato, in quando se inserisco da endo è obbligatorio mentre non lo è per un inserimento generico 2- Se setta
     * il flag se da endo uguale true
     * 
     * @param entity
     */
    public void insertDaEndo(Istanzeallegati entity);

    /**
     * Copia l'oggetto collegato all stc sulla tabella oggetti e poi lo collega all'istanzaallegato
     * 
     * @param codiceistanzaAllegato
     */
    public void insertcopiaAllegatoStc(Istanzeallegati istanzeallegati);

    public IstanzeAllegatiControlloHelper controlloDocumentazione(Istanze istanza);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per inventarioprocedimento  (parametro codiceProcedimento)
     * 
     * @param codiceIstanza
     * @return
     * </pre>
     */
    public int countByInventarioprocedimento(Integer codiceProcedimento);

    /**
     * Setta a null il campo ISTANZEALLEGATI.FK_IDALLEGATO
     * 
     * @param allegato
     * @return Il numero di record aggiornati
     */
    public int updateResettaRiferimentoAllegatoEndo(Allegati allegato);

    /**
     * Torna la lista degli allegati di un Inventarioprocedimenti
     * 
     * @param codiceProcedimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzeallegati> findByInventarioprocedimenti(Integer codiceProcedimento, Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista di tutti gli allegati degli endo procedimenti provenieti da STC (record tabella ISTANZEALLEGATI
     * con il campo stcIdallegato notBlank) filtrati per istanza
     * 
     * @param codiceIstanza
     * @return
     */
    public List<Istanzeallegati> findProvenientiDaSTC(Integer codiceIstanza);

    /**
     * <pre>
     * Ritorna:
     * True	: se esiste almeno un allegato proveniete da STC in istanza allegati per l'istanza passata
     * False 	: se non esiste almeno un allegato proveniete da STC in istanza allegati per l'istanza passata
     * 
     * @param codiceIstanza : filtro 
     * @return
     * </pre>
     */
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza);

    /**
     * @see IstanzeallegatiDAO#findIstanzeallegatiDTOByIstanza(Integer codiceIstanza)
     * 
     */
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza);

    /**
     * @see IstanzeallegatiDAO#findIstanzeallegatiDTOByIstanzaAndEndo(Integer codiceIstanza, Integer codicendo,
     *      TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum)
     * 
     */
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanzaAndEndo(Integer codiceIstanza, Integer codicendo,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum);

    /**
     * Ritorna la lista dei documenti filtrando per istanza e codice oggetto
     * 
     * @param codiceIstanza
     * @param codiceOggetto
     * @return
     */
    public List<Istanzeallegati> findByIstanzaAndOggetto(Integer codiceIstanza, Integer codiceOggetto);

    public Integer insertMultiFile(Istanzeallegati entity, List<MultipartFile> multipartFiles);

    public Integer insertSingoloOrMultiFile(Istanzeallegati entity, List<MultipartFile> lMultipartFiles);

    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto);

    /**
     * Restituisce la lista degli endoprocedimenti che non sono presenti in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceistanza
     * @param codiceautorizzazione
     * @return
     */
    public List<IstanzeallegatiDTO> findIstanzeAllegatiDTOByIstanzaNonInDocAutorizzazione(Integer codiceistanza, Integer codiceautorizzazione);

    public int countByIstanza(Integer codiceIstanza);
}
