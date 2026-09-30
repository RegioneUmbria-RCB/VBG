package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzeStradarioRestBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author francescop
 */
public interface IstanzestradarioService extends BaseService<Istanzestradario, PkId> {

    /**
     * Metodo per ricercare l'istanzestradario per istanza e che sia primaria
     * 
     * @param codiceIstanza
     * @return
     */
    public Istanzestradario findPrimarioByCodiceIstanza(Integer codiceIstanza);

    /**
     * {@link IstanzestradarioDAO#findByIstanza(Istanze)}
     * 
     * @param istanza
     * @return
     */
    public List<Istanzestradario> findByIstanza(Integer codiceIstanza);

    public List<Istanzestradario> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * Il metodo copia le localizzazioni legati all'istanza sorgente all'istanza destinatario. Il metodo prima di
     * replicare le localizzazioni nell'istanza destinatario effettuerà un controllo sul destinatario in modo da non
     * duplicare le localizzazioni.
     * 
     * Il metodo ha un doppio funzionamneto:
     * 
     *  A- 	Se isCopiaMappali = true: copia le localizzazione solo se hanno dei mappali collegati e 
     *  	copia i mappali stessi se già non esiste uno uguale nell'istanza stradario destinatario.
     *  B-	Se isCopiaMappalo = false: copia le localizzazione controllando solo che non sia già presnete 
     *  	una uguale nell'istanza stradario destinatario.
     * 
     * &#64;param istanzaSorgente
     * &#64;param istanzaDestinatario
     * &#64;param isCopiaMappali
     * </pre>
     */
    public void copiaLocalizzazioniWithMappali(Istanze istanzaSorgente, Istanze istanzaDestinatario, boolean isCopiaMappali);

    /**
     * Ritorna il numero dei record presenti nella tabella filtrati per stradario
     * 
     * @param filterTable
     * @return
     */
    public int countRecordByStradario(Stradario stradario);

    /**
     * <pre>
     * Il metodo a partire da un mappale mappale scelto crea una nuova riga di istanze stradario con le seguenti
     * caratteristiche: 
     * 		Istanza 		: quella a cui era collegata il mappale scelto (istanzestradario.istanza)
     * 		Stradario 		: quello collegato al mappale scelto (stradario); 	
     * 		List<Istanzemappali> 	: un solo record che è l' Istanzemappale scelto (istanzemappaleSorgente)
     * 
     * &#64;param istanzemappaleSorgente
     * </pre>
     */
    public void insertReplicaStradario(Istanzemappali istanzemappaleSorgente);

    /**
     * 
     * <pre>
     * Il metodo a partire da un Istanze Stradario (Sorgente) scelto crea una nuovo Istanze Stradario con le seguenti
     * caratteristiche: Istanza : quella a cui era collegata il mappale scelto (istanzestradario.istanza) Stradario :
     * quello collegato al mappale scelto (stradario); List<Istanzemappali> :tutti i mappali associati a
     * l'istanzastradario sorgente (a cui verrà associato il nuovo istanzestradario)
     * 
     * @param istanzeStradarioSorgente
     * @return Istanzestradario
     * 
     *         <pre>
     */
    public Istanzestradario insertDuplicaStradario(Istanzestradario istanzeStradarioSorgente);

    /**
     * Fa una copia esatta dell'oggetto istanze stradario passato senza la lista di IstanzelavoriT
     * 
     * @param istanzeStradarioSorgente
     * @return
     */
    public Istanzestradario copyObjecIstanzeStradarioWithoutIstanzeLavotiT(Istanzestradario istanzeStradarioSorgente);

    /**
     * @see IstanzestradarioDAO#updateFildValido(Integer codiceIstanzaStradario, Boolean value)
     */
    public void updateFildValido(Integer codiceIstanzaStradario, Boolean value);

    /**
     * @see IstanzestradarioDAO#updateFieldCodicecivico(Integer codiceIstanzaStradario, String codiceCivico)
     */
    public void updateFieldCodicecivico(Integer codiceIstanzaStradario, String codiceCivico);

    public List<Istanzestradario> findByTipiLocalizzazioni(Integer idLocalizzazioneTipo, Integer firstResult, Integer maxResult);

    /**
     * Trasforma in pdf e inserisce in documenti istanza il codice html che viene prodotto quando si fa la visura di un
     * campo di istanzestradario
     * 
     * @param codice
     */
    public void trasformAndInsertVisuraInPdf(Integer codice, String html);

    public void updateSettaANullNonValidi();

    public List<IstanzeStradarioRestBean> listaIstanzeStradarioRest(Integer codiceIstanza);

    public IstanzeStradarioRestBean dettaglioIstanzastradarioRest(Integer codiceIstanzastradario, Integer codiceIstanza);

    public IstanzeStradarioRestBean updateIstanzastradarioRest(IstanzeStradarioRestBean bean, Integer codiceIstanza, Integer id);

    public void deleteIstanzastradarioRest(Integer codiceIstanza, Integer id);

    public IstanzeStradarioRestBean insertIstanzastradarioRest(IstanzeStradarioRestBean bean);

    public Istanzestradario findByUuid(Integer codiceIstanza, String uuid);

    public List<IstanzeStradarioExtendedDTO> findByIstanzeFilter(IstanzeFilter filter, Integer firstResult, Integer maxResult);

    public List<IstanzeStradarioExtendedDTO> findByTmp(String token);

    public IstanzeStradarioExtendedDTO findById(Integer idIstanzeStradario);

    public List<IstanzeStradarioExtendedDTO> findByAutorizzazioniFilter(DetachedCriteria autorizzazioniCriteria, Integer firstResult,
	    Integer maxResult);

    void deleteByCodiceIstanza(Integer codiceIstanza);

    public void updateCoordinateByUuId(Integer codiceIstanza, String uuIdLocalizzazione, BigDecimal latitudine, BigDecimal longitudine);
}
