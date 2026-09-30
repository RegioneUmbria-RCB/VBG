package it.gruppoinit.pal.gp.core.service;

import java.util.Date;
import java.util.List;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InfoAutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MappaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PostRestNuovoSpuntistaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ResponsabileRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatoPagamentoSpuntistaRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiAutorizzazioniResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiStampaPDFResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.PresenzaDaRegistrareBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListAttivaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;

public interface MercatiAppService extends BaseService<Mercati, PkId> {

    public List<IdentificativoDescrizioneBean> findGiornateByResponsabileDallaDataAllaData(Integer codiceResponsabile, Date dallaData, Date allaData);

    public MercatipresenzeD spuntistaPresente(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException;

    public MercatipresenzeD spuntistaPresenteAndAggiungiCatMerceologica(Integer idGiornata, Integer idAutorizzazione, String idCategoria)
	    throws MercatiAppException;

    public List<String> spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage(Integer idGiornata, Integer idAutorizzazione, String idCategoria)
	    throws MercatiAppException;

    public void spuntistaAssente(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException;

    public void collegaPosteggioASpuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione, Attivita categoriaMerceologica)
	    throws MercatiAppException;

    public void scollegaPosteggioDaSPuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException;

    public void concessionarioPresente(Integer idGiornata, Integer idPosteggio, PosteggiConcessioniHelper pch) throws MercatiAppException;

    public void concessionarioAssente(Integer idGiornata, Integer idPosteggio) throws MercatiAppException;

    public void concessionariPresenti(Integer idGiornata, List<Long> idPosteggi) throws MercatiAppException;

    public void concessionariAssenti(Integer idGiornata, List<Long> idPosteggi) throws MercatiAppException;

    public void terminaAppello(Integer idGiornata) throws MercatiAppException;

    public void riapriAppello(Integer idGiornata) throws MercatiAppException;

    public void impostaCategoriaMerceologica(Integer idGiornata, Integer idAutorizzazione, String idCategoria) throws MercatiAppException;

    public MercatipresenzeD impostaCategoriaMerceologica(MercatipresenzeD mpd, String idCategoria) throws MercatiAppException;

    public void rifiutaPosteggio(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio) throws MercatiAppException;

    public void annullaRifiutaPosteggio(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException;

    public void rollbackPosteggioASPuntista(Integer idGiornata, Integer idPosteggio) throws MercatiAppException;

    public void rollbackPosteggioASPuntistaPrecedente(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException;

    public List<GiornataMercatoSpuntistaRestBean> ricercaAnagrafe(Integer idGiornataMercato, String testo, int numMaxRecords)
	    throws MercatiAppException;

    public void updatePagato(Integer idGiornata, Integer idAutorizzazione, boolean isPagato) throws MercatiAppException;

    /**
     * <pre>
     *     Ricerca in base al codice fiscale passato: 
     *     	1. se l'anagrafica è presnete sul db 
     *     	2. se non trovata ricerca sul ws anagrafe (tramite i servizi configurati)
     * </pre>
     */
    public AnagraferestBean ricercaAnagrafeCFI(String testo) throws MercatiAppException;

    /**
     * <pre>
     * Inserisci un nuovo spuntista:
     * 1. Controlla se anagrafe già presnete : 
     * 		a. true	: usa anagrafe trovata
     *          b. false: inserisce nuova anagrafe 
     * 2. Insert istanza
     * 3. Insert autorizzazione legata ad istanza e anagrafe
     * 4. insert presenza su mercato
     * </pre>
     */
    public Integer inserisceNuovoSpuntista(PostRestNuovoSpuntistaBean nuovoSpuntista) throws MercatiAppException;

    public List<CodiceDescrizioneBean> ricercaComuni(String testo) throws MercatiAppException;

    public List<CodiceDescrizioneBean> ricercaTuttiComuni() throws MercatiAppException;

    public void updateNoteAutorizzazione(Integer id, String note) throws MercatiAppException;

    public boolean verificaMercatoPerOperatore(Integer idGiornata, Integer codice);

    public void updatePassaAFase(Integer codice, Integer idGiornata, Integer idFase) throws MercatiAppException;

    public InfoAutorizzazioneRestBean infoAutorizzazione(Integer idAutorizzazione, Integer idGiornata) throws MercatiAppException;

    public void impostaIncaricatoVendita(Integer idGiornata, Integer idAutorizzazione, Integer idAnagrafe) throws MercatiAppException;

    /**
     * Ritorna l'oggetto autorizzazione_soggetti inserito
     * 
     * @param codiceFiscale
     * @param nome
     * @param cognome
     * @param idAutorizzazione
     * @return
     * @throws MercatiAppException
     */
    public AutorizzazioniSoggetti insertAnagrafeInAutorizzazioniSoggetti(String codiceFiscale, String nome, String cognome, Integer idAutorizzazione)
	    throws MercatiAppException;

    public AutorizzazioniSoggetti insertAnagrafeInAutorizzazioniSoggettiAndUpdateSuPosteggio(String codiceFiscale, String nome, String cognome,
	    Integer idAutorizzazione, Integer idGiornata) throws MercatiAppException;

    public ResponsabileRestBean infoUtente() throws MercatiAppException;

    public List<IdentificativoDescrizioneBean> uploadFile(List<Attachment> allAttachments) throws MercatiAppException;

    public boolean isGiornataNelFuturo(Integer idGiornata);

    /**
     * Calcola il costo del posteggio nella giornata di mercato per l'autorizzazione impostata
     * 
     * @param idGiornata
     * @param idAutorizzazione
     * @param idPosteggio
     * @see #calcolaCostoPosteggio(Integer)
     * @return
     */
    public PosteggioInfoRestBean calcolaCostoPosteggio(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio) throws MercatiAppException;

    /**
     * Calcola il costo del posteggio nella giornata di mercato per l'autorizzazione impostata
     * 
     * @param idMercatipresenzeD
     * @return
     */
    public PosteggioInfoRestBean calcolaCostoPosteggio(Integer idMercatipresenzeD) throws MercatiAppException;

    /**
     * Non ritorna le autorizzazioni collegate "DOPPIO ATTO"
     * 
     * @param r
     * @return
     */
    public List<AutorizzazioniFrontRestBean> findAutorizzazioniHelperByUtente(Anagrafe r, boolean consideraAncheIlProprietarioTraLeAnagrafiche,
	    RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive);

    public AutorizzazioniFrontRestBean findAutorizzazioneHelperById(Integer idAutorizzazione);

    public String exportModalitaPentaho(Integer giornoMercato, Esportazioni esportazioni, String defaultIfEmpty, boolean boolean1);

    /**
     * Aggiorna le informazioni mail / telefono dell'utente collegato e delle anagrafiche collegaet all'utente che sono
     * associate ad autorizzazioni
     * 
     * @param bean
     * @return
     */
    public CodiceDescrizioneBean updateInfoUtente(DettaglioAnagrafeRestBean bean);

    public StatoPagamentoSpuntistaRestHelper verificaStatoPagamentoSpuntista(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio)
	    throws MercatiAppException;

    /*
    public CodiceDescrizioneBean annullaPagamentoASpuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione)
        throws MercatiAppException;
    */
    /*
    public CodiceDescrizioneBean annullaPosizioneDebitoriaASpuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione)
    	    throws MercatiAppException;
    */
    public MercatipresenzeD getPresenza(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException;

    public void updateCfAnagrafeSpuntista(Integer idAutSpuntista, String codiceFiscale) throws MercatiAppException;

    /**
     * 
     * Al momento pagano tutti e non ci sono particolari controlli da fare
     * 
     * @param pres
     * @param idPosteggio
     * @param idAutorizzazione
     * @param throwException
     *            se rilanciare o meno l'eccezione
     * @return
     */
    public boolean verificaInserimentoPagamento(MercatipresenzeD pres, Integer idPosteggio, Integer idAutorizzazione, boolean throwException);

    /**
     * Torna la lista degli identificativi delle autorizzazioni in BlackList attive (data_fine_bl non nulla) e la lista
     * dei mercatiuso a cui sono eventualmente riferiti
     * 
     * @return
     */
    public List<BlackListAttivaBean> findAutorizzazioniInBlackList();

    /**
     * torna il dettaglio di una blacklist per autorizzazione. Può utilizzare, se passato, l'id della giornata per
     * risalire a MERCATI_USO e capire se filtrare solo le autorizzazioni in blacklist legate a quell'uso ( per i
     * concessionari ).
     * 
     * @param idAutorizzazione
     * @param idGiornata
     * @return
     */
    public BlackListDettaglio findDettaglioBlackListAutorizzazioneEGiornata(Integer idAutorizzazione, Integer idGiornata, BlackListContestoEnum[] contesti);

    /**
     * Imposta per la giornata di mercato la fascia (concessioniuso) definita nei parametri.
     * 
     * @param idGiornata
     *            (l'identificativo della giornata di mercato). Non può essere nulla
     * @param idFasciaMercato
     *            (l'identificativo della fascia - concessione uso - da impostare. Non può essere nulla
     */
    public void modificaFasciaGiornata(Integer idGiornata, Integer idFasciaMercato) throws MercatiAppException;

    GiornataMercatoRestBean getGiornataMercatoRestBean(Integer idGiornata) throws MercatiAppException;

    public MappaMercatoBean getMappaMercato(Integer idGiornata) throws MercatiAppException;

    MercatipresenzeD updatePresenzaSpuntistaNoPosteggio(Integer presenzaDId, Integer codiceMercato, Integer idPosteggio, Integer usoMercato,
	    Date giornoMercato) throws MercatiAppException;

    MercatipresenzeD segnaPresenzaSpuntistaDaGraduatoria(Integer idPresenza, Integer idAutorizzazione) throws MercatiAppException;

    MercatipresenzeD segnaPresenzaSpuntistaDaLista(Integer idPresenzaSrc, Integer idPresenzaDst) throws MercatiAppException;

    MercatipresenzeD segnaPresenzaSpuntista(PresenzaDaRegistrareBean dati) throws MercatiAppException;

    public List<AutorizzazioniMercatoSrv> findAutorizzazioniMercatoSrvByRequest(MercatoSrvRequest req, boolean escludiAutorizzazioniDateInAffitto);

    public AppAmbulantiAutorizzazioniResponse findAutorizzazioniAppAmbulantiByUtente(Anagrafe r);

    public AppAmbulantiStampaPDFResponse updateStampaAutorizzazioniAppAmbulantiByUtente(String cf, Integer codiceAnagrafe);
}
