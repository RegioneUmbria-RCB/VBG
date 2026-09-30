package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.List;

public interface Istanzedyn2datiService extends BaseService<Istanzedyn2dati, Istanzedyn2datiId> {

    /**
     * Trova tutti i dati dinamici legati ad un'istanza
     * 
     * @param idIstanza
     *            il PkId della pratica per la quale si intende trovare il set di dati dinamici
     * @throws BusinessValidationException
     *             nel caso di parametro idIStanza nullo o non valido idIstanza.codice = null
     * @return
     */
    public List<Istanzedyn2dati> findByIstanza(PkId idIstanza);

    /**
     * Trova tutti i dati dinamici legati ad un'istanza ed appartenenti ad un modello di scheda dinamica
     * 
     * @param idIstanza
     *            il PkId della pratica per la quale si intende trovare il set di dati dinamici
     * @param idModello
     *            il PkId del modello per il quale si ricercano i dati dinamici.<br />
     *            Dal modello si devono andare a ricercare quali campi dinamici sono stati inseriti
     * @throws BusinessValidationException
     *             nel caso di parametro idIStanza o IdModello nulli o non validi idIstanza.codice = null
     *             idModello.codice = null
     * @return
     */
    public List<Istanzedyn2dati> findByIstanzaAndModello(PkId idIstanza, PkId idModello);

    /**
     * 
     * @param istanza
     * @param nomeCampo
     * @param softwareDyn2Campi
     * @return
     */
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Istanze istanza, String nomeCampo, String softwareDyn2Campi);

    /**
     * CERCA I VALORI PER IL CAMPO CON QUEL NOME. IL CAMPO LO CERCA PER IL SOFTWARE CORRENTE E SE NON ESISTE PER IL
     * SOFTWARE TT
     */
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Istanze istanza, String nomeCampo);

    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Integer codIst, String nomeCampo);

    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Integer codiceistanza, String nomeCampo, String softwareDyn2Campi);

    /**
     * Torna la lista dei dati dinamici individuati per una istanza individuati dal codiceCampo con indice = 0 e
     * ordinati per indicempolteplicita desc
     * 
     * @param codiceIstanza
     * @param codiceCampo
     * @return
     */
    public List<Istanzedyn2dati> findByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo);

    /**
     * Torna la lista dei dati dinamici individuati per una istanza individuati dal codiceCampo con indice = 0 e
     * ordinati per indicempolteplicita desc
     * 
     * @param codiceIstanza
     * @param codiceCampo
     * @return
     */
    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice,
	    Integer indiceMolteplicita);

    /**
     * Torna la lista dei dati dinamici individuati per una istanza individuati dal codiceCampo ordinati per
     * indicempolteplicita desc. Indice può essere null
     * 
     * @param codiceIstanza
     * @param codiceCampo
     * @param indice
     * @return
     */
    public List<Istanzedyn2dati> findByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice);

    /**
     * <pre>
     * Ritorna il numero di record presenti nella tabella filtrata per l'istanza (parametro codiceIstanza)
     * 
     * @param codiceIstanza
     * @return
     * </pre>
     */
    public int countByIstanza(Integer codiceIstanza);

    /**
     * Il metodo fa una copia e inserisce dei dati dinamici dell'istanza sorgente (Istanzedyn2dati) su un istanza
     * destinataria passata
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void updateCopiaDyn2DatiIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario);

    /**
     * @see Istanzedyn2datiDAO#findByIstanzasAndDyn2Campi(Integer idCampo, Integer codiceAttivita, List<Istanze>
     *      listaIstanze, Integer firstResult, Integer maxResult)
     */
    public List<Istanzedyn2dati> findByIstanzasAndDyn2Campi(Integer idCampo, Integer codiceAttivita, List<Integer> listaIstanze, Integer firstResult,
	    Integer maxResult);

    public String findValoreById(Istanzedyn2datiId id);

    public List<Istanzedyn2datiDTO> findBandoOutput(Integer graduatoriedId);

    //    /**
    //     * <pre>
    //     * Il metodo cancella tutti i valori presenti in IstanzeDyn2Dati filtrando per codice istanza e codice campo(CampiDyn2Dati)
    //     * e inserisce i nuovi campi per l'istanza passati tramite la lista List<Istanzedyn2dati> liIstanzedyn2datis.
    //     * @param codiceIstanze
    //     * @param codiceDyn2dati
    //     * @param liIstanzedyn2datis
    //     * </pre>
    //     */
    //    public void deleteAndInsertIstanzeDyn2Dati(Integer codiceIstanza, Integer codiceDyn2dato, List<Istanzedyn2dati> liIstanzedyn2datis);
    /**
     * Torna la lista delle schede che usano il campo dinamico di tipo localizzazione con valore=uuid
     * 
     * @param codiceistanza
     * @param uuid
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<CodiceDescrizioneBean> findModelliCheUsanoLocalizzazioneByUUID(Integer codiceistanza, String uuid, Integer firstResult,
	    Integer maxResults);

    /**
     * Torna la lista dei campi e dei valori indipendentemente dall'indice, indicemolteplicita. La lista è ordinata per
     * i campi indice, indicemolteplicita
     * 
     * @param codiceIstanza
     * @param codiceCampo
     * @return
     */
    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo);

    @Override
    public void insert(Istanzedyn2dati entity);

    public int countByIstanzaAndNomecampo(Integer codiceIstanza, String nomeCampo);

    public Istanzedyn2dati findByIstanzaAndNomeCampoAndMolteplicita(Integer codiceIstanza, String nomeCampo, Integer molteplicita);
}
