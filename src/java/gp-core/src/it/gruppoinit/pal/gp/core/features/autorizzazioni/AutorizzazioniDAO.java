package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;
import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniExportHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniDAO extends BaseDAO<Autorizzazioni, PkId> {

    public void insert(Autorizzazioni autorizzazioni);

    /**
     * recupera la lista delle sole autorizzazioni ricercando solo nella tabella AUTORIZZAZIONI con
     * FKIDISTANZA=istanze.id.codice e FKIDREGISTRO=codiceregistro. I risultati sono ordinati per autorizdata
     * decrescente.
     * 
     * @param istanze
     * @param codiceregistro
     * @return List<Autorizzazioni> ordinata per autorizdata decrescente
     */
    public List<Autorizzazioni> findByIstanzaRegistro(Istanze istanze, Integer codiceregistro);

    public List<Autorizzazioni> findByIstanzaRegistro(Integer codIstanze, Integer codiceregistro);

    /**
     * recupera la lista delle sole autorizzazioni ricercando solo nella tabella AUTORIZZAZIONI con
     * FKIDISTANZA=istanze.id.codice e CODICEMOVIMENTO=movimento.id.codice. I risultati sono ordinati per autorizdata.
     * 
     * @param istanze
     * @param movimento
     * @return List<Autorizzazioni> ordinata per autorizdata decrescente
     */
    public List<Autorizzazioni> findByIstanzaMovimento(Istanze istanze, Movimenti movimento);

    /**
     * recupera l'autorizzazione/concessione per chiave univoca (numero,data,comune,registro).<br />
     * cerca solo tra quelle attive.<br />
     * (LEFT JOIN TRA AUTORIZZAZIONI E AUTORIZZAZIONI_CONCESSIONI DOVE AUT_CONC.ID IS NULL)
     * 
     * @param autoriznumero
     * @param autorizdata
     * @param codicecomune
     * @param codiceregistro
     * @return l'autorizzazione o null
     */
    public Autorizzazioni findAutOConcAttivaByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * recupera l'autorizzazione/concessione (attiva o cessata) cercando nella sola tabella AUTORIZZAZIONI per chiave
     * univoca.
     * 
     * @param autoriznumero
     * @param autorizdata
     * @param codicecomune
     * @param codiceregistro
     * @return
     */
    public Autorizzazioni findAutOConcByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * recupera la lista delle sole autorizzazioni cercando nella tabella AUTORIZZAZIONI con
     * FK_CODICEANAGRAFE=codiceAnagrafe.<br />
     * (LEFT JOIN TRA AUTORIZZAZIONI E AUTORIZZAZIONI_CONCESSIONI DOVE AUT_CONC.ID IS NULL)
     * 
     * @param codiceAnagrafe
     * @return
     */
    public List<Autorizzazioni> findByAnagrafe(Integer codiceAnagrafe);

    /**
     * * recupera la lista delle sole concessioni cercando nella tabella AUTORIZZAZIONI con
     * FK_CODICEANAGRAFE=codiceAnagrafe.<br />
     * (INNER JOIN TRA AUTORIZZAZIONI E AUTORIZZAZIONI_CONCESSIONI)
     * 
     * @param codiceAnagrafe
     * @return
     */
    public List<Autorizzazioni> findConcessioniByAnagrafe(Integer codiceAnagrafe);

    /**
     * <p>
     * recupera la lista di autorizzazioni/concessioni utilizzando come filtro l'oggetto
     * {@link AutorizzazioniFilter}.<br />
     * ricerca nella sola tabella AUTORIZZAZIONI. La ricerca può essere limitata specificando:
     * 
     * 1- Il primo risultatto da cui partire 2- Il numero di record massimo da mostrare.
     * 
     * @param filter
     * @return
     * 
     *         </p>
     */
    public List<Autorizzazioni> findByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);

    /**
     * recupera le sole autorizzazioni per l'istanza specificata.<br />
     * (LEFT JOIN TRA AUTORIZZAZIONI E AUTORIZZAZIONI_CONCESSIONI DOVE AUT_CONC.ID IS NULL)
     * 
     * @param istanza
     * @return
     */
    public List<Autorizzazioni> findByIstanza(Istanze istanza);

    /**
     * metodo per la ricerca delle autorizzazioni passando gli estremi come stringa i cui valori sono separati da
     * virgola. (numero,data,comune,registro)
     * 
     * @param estremi
     * @param maxResult
     * @return
     */
    public List<Autorizzazioni> findByEstremi(String estremi, Integer maxResult);

    /**
     * Ritorna il numero di autorizzazioni in base al filtro passato
     * 
     * @param filter
     * @return
     */
    public int countByFilter(AutorizzazioniFilter filter);

    public List<Autorizzazioni> findByAutorizzazioniFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);

    /**
     * Recupere le concessioni e i subentri dell'istanza passata
     * 
     * @param codiceIstanza
     * @return
     */
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza);

    /**
     * Recupere le concessioni e i subentri dell'istanza passata, e da la possibilità di escludere quelle cessate
     * 
     * @param codiceIstanza
     * @return
     */
    public List<AutorizzazioniDTO> findConcESub(Integer codiceIstanza, Boolean escludiCessate);

    //    /**
    //     * Ritorna i codici delle autorizzazioni/concessioni degli spuntisti per le quali fare la lista delle presenze
    //     * 
    //     * @param codiceMercato
    //     * @param codiceUso
    //     * @return
    //     */
    //    public Set<Integer> findAutorizzazioniSpuntisti(Integer codiceMercato, Integer codiceUso);
    /**
     * Ritorna i codici delle autorizzazioni/concessioni degli spuntisti per le quali fare la lista delle presenze
     * 
     * @param codiceMercato
     * @param codiceUso
     * @return
     */
    public List<AutorizzazioneSpuntistaHelper> findAutorizzazioniObjSpuntisti(Integer codiceMercato, Integer codiceUso,
	    Integer idGiornataRiferimento);

    public List<AutorizzazioniRestHelper> findRestHelper(Set<Integer> auts, Integer idGiornata, Integer codiceMercato, Integer codiceUso);

    /**
     * ritorna un oggetto [codice,descrizione] contenente il codice dell'attivita (codiceistat) e la descrizione
     * (istat). La ricerca viene fatta tra tutti quelli associati alle autorizzazioni censite per idcomune e software in
     * esame
     * 
     * @return
     */
    public List<CodiceDescrizioneBean> findAttivitaInAutorizzazioni();

    public List<AutorizzazioniRestHelper> findAnagraficheConAutorizzazione(String testo, Integer firstResult, Integer maxResults);

    /**
     * Recupera le autorizzazioni dove i codici anagrafe sono o Gerenti o occupanti / non titolari
     * 
     * @param codiciAnagrafe
     * @return
     */
    public List<AutorizzazioniRestHelper> findAutorizzazioniAnagrafiche(Set<Integer> codiciAnagrafe,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive);

    public AutorizzazioniRestHelper findAutorizzazioneRestHelper(Integer idAutorizzazione);

    public void exportModalitaPentaho(AutorizzazioniExportHelper autorizzazioniExportHelper, Esportazioni esportazioni, Date _data, String email,
	    String contestoExport, boolean isInvioMail);

    public void cessaAutorizzazione(int idAutorizzazione, Date dataCessazione, int idCausaleCessazione);

    public void cambiaBloccoAutorizzazione(Integer codiceAutorizzazione, boolean bloccata);

    public void aggiornaEstremiAutorizzazioni(Integer codiceAutorizzazione, String numero, Date data);

    public List<Integer> findAutorizzazioniScaduteAllaDataENonCessate(Date oggi);

    public List<Integer> findAutorizzazioniCessateAllaDataEAncoraAttive(Date oggi);

    public List<Integer> findAutorizzazioniConAffittoScadutoENonRientrateInPossesso(Date oggi);

    public List<AutorizzazioniMercatoSrvBean> findAutorizzazioniMercatoSrvBean(MercatoSrvRequest req);

    public DetachedCriteria createCriteriaFilter(AutorizzazioniFilter filter);

    /**
     * Recupera l'elenco di autorizzazioni attive relative a una specifica istanza e comune,
     * escludendo quelle "collegate" (ovvero con {@code FK_IDAUT_COLLEGATA} in {@code autorizzazioni_concessioni}).
     * <p>
     * Per le autorizzazioni aventi un'autorizzazione collegata, la colonna {@code autorizNumeroFull} includerà il
     * numero dell'autorizzazione collegata ({@code AUTORIZNUMERO}) nel formato:
     * "AutorizNumeroAttuale (Aut. Coll. AutorizNumeroCollegata)".
     * Per le altre autorizzazioni, i dati non verranno alterati.
     *
     * @param codice Il codice identificativo dell'istanza ({@code FKIDISTANZA}).
     * @param idComune L'ID del comune associato all'autorizzazione ({@code IDCOMUNE}).
     * @return Una lista di record di autorizzazioni filtrate
     */
    public List<AutorizzazioniComposteSpostaPresenzeDTO> findAutorizzazioniComposteSpostaPresenze(Integer codice, String idComune);
}
