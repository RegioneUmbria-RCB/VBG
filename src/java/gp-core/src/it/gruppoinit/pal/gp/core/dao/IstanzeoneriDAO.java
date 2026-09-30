/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.features.oneri.CalcolaInteressiDiMoraBean;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeOneriNodoPagamentiHelper;
import it.gruppoinit.pal.gp.core.features.rateizzazioni.IstanzeoneriDerateizzatoBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneri;
import it.gruppoinit.pal.gp.core.filters.TipologiaOnere;

/**
 * @author francescop
 * 
 */
public interface IstanzeoneriDAO extends BaseDAO<Istanzeoneri, PkId> {

    public List<Istanzeoneri> getDebtSituationIstanzeOneri(String codiceFiscale, Date DATAINIZIO, Date DATAFINE, String annoDocumento,
	    String codiceTributo);

    public Integer getNumeroRateInScadenza(Tipicausalioneri tipicausalioneri);

    public List<Istanzeoneri> getBillDetailsIstanzeOneri(String nrDocumento, String codicefiscale, String annoDocumento, String codiceTributo);

    public Istanze getIstanzeByNrDocumento(String nrDocumento);

    public Istanzeoneri getOneriByNrDocRata(String nrDocumento, Short nrRata);

    /**
     * Metodo per determinare l'istanza onere identificata come BOLLO
     * 
     * @param codiceCausaleBollo
     * @param istanza
     * @return
     */
    public List<Istanzeoneri> getOnereBollo(Integer codiceCausaleBollo, Istanze istanza);

    /**
     * Metodo per determinare l'istanza onere che ha un bollo non pagato.
     * 
     * @param codiceCausale
     * @param istanza
     * @return
     */
    public Istanzeoneri getOnereBolloByOnere(Integer codiceCausale, Istanze istanza);

    /**
     * <pre>
     * Somma di tutti gli oneri associati all'istanza e filtrati per raggruppamenti. 
     * 1- Il parametro tipologiaOnere : permette di decidere se fare la somma causali oneri o istruttoria oneri
     * 
     * &#64;param istanza
     * &#64;param raggruppamentocausalioneri
     * &#64;param tipologiaOnere
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    TipologiaOnere tipologiaOnere, Boolean isEntrata);

    /**
     * <pre>
     * Somma di tutti i ribassi applicati alle uscite associate all'istanza e filtrati per raggruppamenti. 
     * 1- Il parametro tipologiaOnere : permette di decidere se fare la somma causali oneri o istruttoria oneri
     * 
     * &#64;param istanza
     * &#64;param raggruppamentocausalioneri
     * &#64;param tipologiaOnere
     * &#64;return
     * </pre>
     */
    public BigDecimal sumRibassiOneriByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri);

    /**
     * <pre>
     * Somma di tutti gli oneri associati all'istanza
     * 
     * 
     * &#64;param istanza
     * 
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriCausaliByIstanza(Istanze istanza, TipologiaOnere tipologiaOnere, Boolean isEntrata);

    /**
     * <pre>
     * Somma di tutti i ribassi applicati alle uscite associate all'istanza  
     * 
     * 
     * &#64;param istanza
     * &#64;param raggruppamentocausalioneri
     * &#64;param tipologiaOnere
     * &#64;return
     * </pre>
     */
    public BigDecimal sumRibassiOneriByIstanza(Istanze istanza);

    /**
     * <pre>
     * Somma di tutti gli oneri associati all'istanza e filtrati per raggruppamenti e datapagamento. 
     * 1- Il parametro tipologiaOnere : permette di decidere se fare la somma causali oneri o istruttoria oneri
     * 
     * &#64;param istanza
     * &#64;param raggruppamentocausalioneri
     * &#64;param datapagamento
     * &#64;param tipologiaOnere
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriCausaliByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Date datapagamento, TipologiaOnere tipologiaOnere);

    /**
     * Ritorna tutte le amministrazioni che sono coinvolte negli oneri dell'istanza passata
     * 
     * @param istanzeoneris
     * @return
     */
    public List<Amministrazioni> findAmministrazioniInIstanzeOneri(Istanze istanza);

    /**
     * <pre>
     * Somma di tutti gli oneri associati all'istanza e filtrati per amministrazione. 
     * 
     * 		1- Il parametro tipologiaOnere : permette di decidere se fare la somma causali oneri o istruttoria oneri
     *          2- I parametro isEntrata indica se devo ricercare l'onere in entrata o uscita
     * 
     * &#64;param istanza
     * &#64;param amministrazioni
     * &#64;param tipologiaOnere
     * &#64;isEntrata
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriCausaliByIstanzaAndAmministrazione(Istanze istanza, Amministrazioni amministrazioni, TipologiaOnere tipologiaOnere,
	    Boolean isEntrata);

    /**
     * Ritorna la somma dei ribassi delle uscite verso l' amministrazione specificata
     * 
     * @param istanza
     * @param amministrazioni
     * @return
     */
    public BigDecimal sumRibassiOneriByIstanzaAndAmministazioni(Istanze istanza, Amministrazioni amministrazioni);

    /**
     * <pre>
     * Ritorna la somma degli oneri versati per raggruppamento.
     * Se isEntrata:
     * 	1. true : allora sono gli oneri versati al comune relativi alle entrate
     * 	2. false: allora sono gli oneri versati dal comune ad enti terzi relativi alle uscite
     * 
     * 
     * &#64;param istanza
     * &#64;param raggruppamentocausalioneri
     * &#64;param isEntrata
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriImportoVersatoByIstanzaAndRaggruppamento(Istanze istanza, Raggruppamentocausalioneri raggruppamentocausalioneri,
	    Boolean isEntrata);

    /**
     * <pre>
     * Ritorna la somma degli oneri versati.
     * Se isEntrata:
     * 	1. true : allora sono gli oneri versati al comune relativi alle entrate
     * 	2. false: allora sono gli oneri versati dal comune ad enti terzi relativi alle uscite
     * 
     *      
     * &#64;param istanza
     * &#64;param isEntrata
     * &#64;return
     * </pre>
     */
    public BigDecimal sumOneriImportoVersatoByIstanza(Istanze istanza, Boolean isEntrata);

    /**
     * La funzione torna una lista di @IstanzeOneriNodoPagamentiHelper in base all'id ( quello assegnato dal nodo dei
     * pagamenti ) della posizione debitoria
     * 
     * @param idPosizioneDebitoria
     * @return
     */
    List<IstanzeOneriNodoPagamentiHelper> findByIdPosizioneDebitoria(Integer idPosizioneDebitoria, String cfEnteCreditore);

    /**
     * La funzionalità imposta i dati del pagamento di un record di istanzeoneri
     * 
     * @param idIstanzeOneri
     * @param dataPagamento
     * @param importoPagato
     * @param modalitaPagamento
     * @param riferimentoPagamento
     */
    void impostaOnerePagatoConImportoByIdPosizioneDebitoria(Integer idIstanzeOneri, Date dataPagamento, BigDecimal importoPagato,
	    Tipimodalitapagamento modalitaPagamento, String riferimentoPagamento);

    /**
     * La funzionalità imposta i dati del pagamento di un record di istanzeoneri
     * 
     * @param idIstanzeOneri
     * @param dataPagamento
     * @param modalitaPagamento
     * @param riferimentoPagamento
     */
    void impostaOnerePagatoByIdPosizioneDebitoria(Integer idIstanzeOneri, Date dataPagamento, Tipimodalitapagamento modalitaPagamento,
	    String riferimentoPagamento);

    public List<IstanzeoneriDerateizzatoBean> findOneriDaDerateizzare(Integer codiceIstanza, Integer codiceCausaleOneri);

    public List<Integer> findOneriDerateizzatiDaEliminare(Integer codiceIstanza, Integer codiceCausaleOneri, Integer fkcanonetestata);

    public List<Integer> findOneriConMappaturaNPById(Set<Integer> idIstanzeOneri);

    public Istanzeoneri findOnereByIdDettaglioPosizioneDebitoria(Integer idDettaglioPosizioneDebitoria);

    public List<ScadenzarioOneri> findTabellaScadenzario(Date dataOdierna, String[] codiciComune);

    public List<Integer> findCausaliPerMappatureConti(Integer codiceIstanza, Set<String> listaMappaturePerVersamento);

    public Integer findCodiceIstanzaByDettPosDebitoria(Integer codice);

    public List<CalcolaInteressiDiMoraBean> calcolaInteressiDiMora(Integer idIstanzeOneri);

    public void deleteByIdPadre(Integer idPadre);
}
