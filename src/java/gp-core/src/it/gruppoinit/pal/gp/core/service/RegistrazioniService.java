package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.ContoInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniDaMercato;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniStatisticheMercati;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public interface RegistrazioniService extends BaseService<Registrazioni, PkId> {

    public static final int CODICE_CONTO_INTERESSI_TEMPORANEO = -999;
    public static final String DESCRIZIONE_CONTO_INTERESSI_TEMPORANEO = "$CONTO_INTERESSI$";

    public List<Registrazioni> findByAnagrafe(Anagrafe anagrafe);

    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter);

    public List<RegistrazioniStatisticheMercati> findRegByMercato(Integer idMercato);

    public List<RegistrazioniDaMercato> findRegByMercatoForCausale(short anno, Integer idMercato, MercatiUso uso);

    /**
     * data una giornata di mercato sistema le registrazioni contabili
     * 
     * @param mercatipresenzeT
     *            la giornata di mercato
     * @param spuntistiConPosteggio
     *            una lista di mercatipresenze_D con l'importo assegnato agli spuntisti durante il mercato
     * @return
     */
    public List<Registrazioni> sistemaContabilitaGiornoMercato(MercatipresenzeT mercatipresenzeT, List<MercatipresenzeDDTO> spuntistiConPosteggio,
	    Responsabili operatore);

    public List<Registrazioni> findRegistrazioniSpuntistiByMercatoUsoDataregistrazione(Mercati mercato, MercatiUso mercatoUso, Date dataRegistrazione);

    public List<Registrazioni> findRegistrazioniConcessionariByMercatoUsoDataregistrazione(Mercati mercato, MercatiUso mercatoUso,
	    Date dataRegistrazione);

    /**
     * metodo per la cancellazione di una registrazione durante la cancellazione del calendario di un mercato
     * 
     * @param entity
     */
    public void deleteFromDeleteCalendario(Registrazioni entity);

    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity);

    /**
     * @gianpaolot
     * @param registrazioniFilter
     * @return ritorna una lista di Registrazioni filtrata per i parametri inseriti nella maschera di ricerca
     *         (registrazioni/registrazioniSearch.jsp)
     */
    public List<Registrazioni> findByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter, Integer firstResult, Integer maxResult);

    /**
     * @gianpaolot
     * @param registrazioniFilter
     * @return ritorna quanti record di Registrazioni filtrata per i parametri inseriti nella maschera di ricerca
     *         (registrazioni/registrazioniSearch.jsp)
     */
    public int countByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter);

    /**
     * @gianpaolot
     * @param mercati
     * @param mercatiUso
     * @return ritorna una lista di posteggio filtrata per mercati e mercati uso Posteggio è un bean che contiene la
     *         situazione contabile di un posteggio raggruppata per : anno conto
     * 
     */
    public List<Posteggio> findSituazioneContabileByMercatoAndPosteggio(Mercati mercati, MercatiUso mercatiUso);

    /**
     * @gianpaolot
     * @param mercati
     * @param mercatiUso
     *            se mercatiUso è nullo allora torna la situazione contabile per tutti gli Usi del mercato
     * @return Un vettore di list<Posteggio> il vettore avra dimensione pari al numero di mercati uso del mercato
     *         passato
     * 
     */
    public Vector<List<Posteggio>> findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(Mercati mercati, MercatiUso mercatiUso);

    public Integer insertRiduzioniAccertamento(Registrazioni entity, Responsabili respSistema, String regImpSel);

    /**
     * Metodo che inserisce tutte le registrazioni importi configurati per tutti i posteggi del mercato scelti
     * considerando anche se devono essere rateizzati o no
     * 
     * @param listPosteggio
     * @param dataRegistrazione
     * @param mercatiuso
     * @param rate
     * @param registrazioniCausali
     * 
     */
    public void insertPagamentiUtente(List<Posteggio> listPosteggio, Date dataRegistrazione, MercatiUso mercatiuso, Boolean rate,
	    RegistrazioniCausali registrazioniCausali);

    /**
     * Il metodo riesegue il conteggio delle righe di importo per calcolare l'importo della registrazione
     * 
     * @param registrazioneId
     */
    public void updateImportoRegistrazione(Registrazioni entity);

    /**
     * metodo per l'ajax request per recuperare tutti i mercati per cui una anagrafica ha una registrazione.
     * 
     * @param anagrafe
     * @return
     */
    public List<Registrazioni> findMercatiByRegistrazioniAndAnagrafe(Anagrafe anagrafe);

    /**
     * metodo per l'ajax request per recuperare tutte le anagrafiche per una manifestazioni.
     * 
     * @param anagrafe
     * @return
     */
    public List<Registrazioni> findAnagrafeByRegistrazioniAndMercati(Mercati mercati);

    /**
     * Metodo per adeguare gli importi delle registrazioni individuate tramite RegistrazioniFilter di una percentuale
     * indicata nel parametro percentualeAdeguamento
     * 
     * @param registrazioniFilter
     * @param percentualeAdeguamento
     */
    public void adeguaImportiRegistrazioni(RegistrazioniFilter registrazioniFilter, BigDecimal percentualeAdeguamento);

    /**
     * Inserisce una transazione contabile
     * 
     * @param entity
     *            i dati della registrazione che rappresenta la transazione, comprese le righe di importo
     * @param registrazioniDaRidurre
     *            la lista delle registrazioni da ridurre
     * @param importidaRidurre
     *            la lista degli importi da ridurre
     * @return il codice della registrazione creata
     */
    public Integer insertTransazione(Registrazioni entity, List<Registrazioni> registrazioniDaRidurre, List<RegistrazioniImporti> importidaRidurre);

    /**
     * Metodo per rateizzare l'importi, per effetuare una simulazione o per rateizzare le registrazioni importi di una
     * registrazione
     * 
     * @param dataregistrazione
     *            In caso di interessi legali la data di ragistrazione rappresenta la data finale
     * @param regList
     * @param oneritipirateizzazione
     * @param contoInteressiLegaliList
     *            TODO
     * @return
     */
    public Set<RegistrazioniImporti> rateizzaRegistrazioni(Date dataregistrazione, List<RegistrazioniImporti> regList,
	    Oneritipirateizzazione oneritipirateizzazione, List<ContoInteressiLegali> contoInteressiLegaliList, Integer tipologiaRateizzazioneRate,
	    Conti contoInteressirate, List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat);

    /**
     * validazione per la simulazione e per la rateizzazzione
     * 
     * @param registrazioniFilter
     */
    public void validateRateizzazione(RegistrazioniFilter registrazioniFilter);

    /**
     * metodo per eliminare tutte le registrazioni importi di una registrazione
     * 
     * @param registrazioni
     */
    public void deleteAllRegistrazioniImporti(Registrazioni registrazioni);

    /**
     * Metodo per aggiornare le rate di una registrazione. Elimino tutte le registrazioni e inserisco le nuove
     * registrazioni importi con la nuova rateizzazione.
     * 
     * @param oneritipirateizzazione
     * @param dataregistrazione
     * @param registrazioni
     * @param contoInteressiLegaliList
     */
    public void updateRegistrazioniImportiRateizzati(Oneritipirateizzazione oneritipirateizzazione, Date dataregistrazione,
	    Registrazioni registrazioni, List<ContoInteressiLegali> contoInteressiLegaliList, Integer tipologiaRateizzazioneRate,
	    Conti contoInteressirate, List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat);

    /**
     * Metodo che rateizza le registrazioni importi. Questo metodo incapsula il metodo generale che crea le
     * rateizzazioni a partire da un importo. Se il tipo di rateizzazione viene selezionato esternamente e non scelto a
     * partire dall'importo si può utilizzare il metodo public getImportiRateizzati.
     * 
     * @param registrazioni
     * @param importoTotale
     * @param rigaImportoList
     * @return
     */
    public List<RegistrazioniImporti> getRegistrazioniImportiRateizzati(Registrazioni registrazioni, BigDecimal importoTotale,
	    List<RigaImporto> rigaImportoList);

    /**
     * Metodo che inserisce le rate mancati di tutte le registrazioni per un determinato anno. Metodo utilizzato per il
     * Comune di Bari. 12 rate che partono da Gennaio.
     * 
     * @author francescop
     * @param anno
     *            delle registrazioni
     * @param numerorate
     *            totali
     */
    public void insertRateMancanti(short anno, int numerorate);

    /**
     * Metodo che restituisce tutte le registrazioni per un determinato anno.
     * 
     * @param anno
     * @return
     */
    public List<Registrazioni> findRegistrazioniByAnno(short anno);

    /**
     * Metodo privato utilizzato per il calcolo delle registrazioni importi.Questo è un metodo accessorio del generico
     * metodo di rateizzazioni.Questo metodo viene utilizzato dal metodo che simula le rateizzazioni, poichè viene
     * scelto in view quale tipo di reteizzazione utilizzare.
     * 
     * @param registrazioni
     * @param dataregistrazione
     *            In caso di interessi legali la data di ragistrazione rappresenta la data finale
     * @param rigaImportoList
     * @param oneritipirateizzazione
     * @return
     */
    public List<RegistrazioniImporti> getImportiRateizzati(Registrazioni registrazioni, Date dataregistrazione, List<RigaImporto> rigaImportoList,
	    Oneritipirateizzazione oneritipirateizzazione, Integer tipologiaRateizzazioneRate, Conti contoInteressirate,
	    List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat);

    /**
     * metodo per inserire le registrazioni e le registrazioni importi per le letture dei contatori
     * 
     * @param registrazioni
     * @param set
     */
    public void insertRegistrazioniLettureContatori(Registrazioni registrazioni, Set<RegistrazioniImporti> set);

    /**
     * Torna la lista delle Registrazioni di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Registrazioni> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    public void clear();

    public List<RegistrazioniImporti> getRegistrazioniAnnuali(Registrazioni registrazione, List<RigaImporto> righeCostoPosteggio,
	    Integer tipoScadenzaRata);

    public List<Integer> findCodiciByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter);

    public void updateAggiornaIVA(BigDecimal valoreIva, Date data, Software software);

    public byte[] createTracciatoByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter);
}
