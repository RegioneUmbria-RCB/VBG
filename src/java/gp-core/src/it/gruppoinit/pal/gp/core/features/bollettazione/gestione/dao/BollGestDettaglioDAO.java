package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.VerificaCausaliDettaglioBollettazioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;

/**
 * 
 * @author
 */
public interface BollGestDettaglioDAO extends BaseDAO<BollGestDettaglio, PkId> {

    /**
     * 
     * 
     */
    public List<BollGestDettaglio> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @return
     */
    public List<BollGestDettaglioDTO> findByIdBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    /**
     * Ritorna la lista di tutte le righe non cancellate logicamente, di dettaglio della bollettazione di cui è stato
     * passato l'id
     * 
     * @param idBollettazione
     * @return
     */
    public List<BollGestDettaglio> findByIdBollettazione(Integer idBollettazione);

    /**
     * Effettua la cancellazione fisiche di tutte le righe legate all'id della bollettazione passato
     * 
     * @param idBollettazione
     */
    void deleteByIdBollettazione(Integer idBollettazione);

    /**
     * Verifica l'esistenza di righe inviate al sistema dei pagamenti per l'ìd della bollettazione passato come filtro
     * 
     * @param idBollettazione
     * @return
     */
    Boolean existsRigheInviateASistemaPagamenti(Integer idBollettazione);

    /**
     * Il metodo provvede a salvare il riferimento della tabella DETT_POSIZIONE_DEBITORIA su tutte le righe interessate
     * 
     * @param idRigheBollettazione
     * @param idDettPosizioneDebitoria
     */
    void updateRiferimentoPosizioneDebitoria(List<Integer> idRigheBollettazione, Integer idDettPosizioneDebitoria);

    /**
     * LA funzionalità imposta a null il campo FK_RETTIFICA_ID per tutte le righe dell'idbollettazione passato
     * 
     * @param idBollettazione
     */
    void updateAnnullaRettifiche(Integer idBollettazione);

    /**
     * La funzionalità ritorna la lista dei riferimenti alle posizioni debitoria per l'anagrafica specificata e la
     * bollettazione specificata
     * 
     * @param idBollettazione
     * @param idAnagrafica
     * @return
     */
    Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione, Integer idAnagrafica);

    /**
     * La funzionalità ritorna la lista dei riferimenti alle posizioni debitoria per la bollettazione specificata
     * 
     * @param idBollettazione
     * @return
     */
    Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione);

    /**
     * La funzionalità ritorna la riga VALIDA secondo i filtri passati; se non ci sono righe VALIDE ritorna null, se ci
     * sono più righe valide genera una RuntimeException
     * 
     * 
     * @param idBollettazione
     * @param idAnagrafe
     * @param idConto
     * @param idAutorizzazione
     * @return
     */
    BollGestDettaglio getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(Integer idBollettazione, Integer idAnagrafe, Integer idConto,
	    Integer idAutorizzazione);

    /**
     * LA funzionalità torna la lista delle righe validabili, cioè allo stato attuale tutte quelle non rettificate e non
     * eliminate
     * 
     * @param idBollettazione
     * @return
     */
    public List<BollGestDettaglio> findRigheValidabili(Integer idBollettazione);

    /**
     * @see #getSoftwareAndComune(Integer)
     * @param idRigaDettaglio
     * @return
     */
    public String getComune(Integer idRigaDettaglio);

    /**
     * Risale dalla riga di dettaglio al comune e al software dell'istanza o dei mercati a seconda del collegamento
     * della riga di dettaglio della bollettazione
     * 
     * @param idRigaDettaglio
     * @return
     */
    public List<ISoftwareComuneData> getSoftwareAndComunePerRiga(Integer idRigaDettaglio);

    /**
     * Trova tutte le righe di dettaglio dove il riferimento di id_dettaglio_posizione debitoria è quello passato in
     * argomento.
     * 
     * @param idDettPosizioneDebitoria
     * @return
     */
    public List<BollGestDettaglio> findByIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria);

    /**
     * Risale dalla riga di dettaglio al comune e al software dell'istanza o dei mercati a seconda del collegamento di
     * tutte le righe di dettaglio di una bollettazione
     * 
     * @param idRigaTestataBollettazione
     * @return
     */
    public List<ISoftwareComuneData> getSoftwareAndComuneForBollettazione(Integer idRigaTestataBollettazione);

    public List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(int idDettaglioComunicazione);

    public List<String> recuperaMappaturaNodoPagDaIdDettaglio(Integer idRigaDettaglio);

    /**
     * Torna le righe NON eliminate
     * 
     * @param idBollettazione
     * @return
     */
    public List<BollGestDettaglioDTO> findBollGestDettaglioDTOByTestata(Integer idBollettazione);

    /**
     * Il metodo torna una mappa di interi (id dettaglio bollettazione e la lista dei comuni trovati per il dettaglio
     * 
     * @param idTestataBollettazione
     * @return
     */
    public Map<Integer, Set<String>> getComuniPerDettagliBollettazione(int idTestataBollettazione);

    public List<VerificaCausaliDettaglioBollettazioneBean> verificaConfigurazioniCausali(Set<Integer> idRigheDettaglioBollettazione);

    public String findArrotondamentoByBollettazione(Integer idBollettazione);
}
