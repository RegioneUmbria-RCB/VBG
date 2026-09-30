package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Software;
/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

public interface TipiMovimentoDAO extends BaseDAO<Tipimovimento, TipimovimentoId> {

    /**
     * 
     * @param entity
     * @param includiDisabilitati
     * @param softwareDaCercare
     *            se nullo o vuoto cerca per tutti i software
     * @return
     */
    public List<Tipimovimento> findByDescrizione(Tipimovimento entity, boolean includiDisabilitati, List<Software> softwareDaCercare);

    public Tipimovimento getTipiMovimentoFlagCamcom();

    /**
     * Il metodo restituisce una lista di tipi movimento filtrata per descrizione e software ordinata per
     * TIPIMOVIMENTO.MOVIMENTO ASC, se viene passato null o stringa vuota come parametro sofware il filtro verrà
     * applicato sul software corrente
     * 
     * @param entity
     * @param software
     * @param includiDisabilitati:
     *            se true vengono restituiti anche i tipimovimento per cui FLAG_DISABILITATO = 1
     * @return Lista di tipi movimento
     */
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati);

    /**
     * Il metodo restituisce una lista di tipi movimento filtrata per descrizione e software ordinata per
     * TIPIMOVIMENTO.MOVIMENTO ASC, se viene passato null o stringa vuota come parametro sofware il filtro verrà
     * applicato sul software corrente. I due argomenti boolean consentono di includere nei risultati anche i tipi
     * movimento disabilitati o di escludere quelli non utilizzati dal protocollo
     * 
     * @param entity
     * @param software
     * @param includiDisabilitati:
     *            se true vengono restituiti anche i tipimovimento per cui FLAG_DISABILITATO = 1
     * @param escludiNonUsatiInProtocollo:
     *            se true vengono restituiti solo i tipimovimento per cui FLAG_USADALPROTOCOLLO = 1
     * @return Lista di tipi movimento
     */
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati,
	    boolean escludiNonUsatiInProtocollo);

    /**
     * Restituisce la lista tipi movimento (filtrati per idcomune e software) e ordinati per la descrizione del
     * movimento (movimento)
     * 
     */
    public List<Tipimovimento> findAll(Integer firstResult, Integer maxResult);

    public boolean getFlagNoamminterna(String tipomovimento);

    /**
     * @param codiceIstanza
     * @param idcomune
     * @return tipi movimento
     * 
     *         Restituisce tipi movimento (filtrati per idcomune e codiceIstanza,richiesta di integrazioni e soggetti
     *         esterni)
     */
    public Tipimovimento findTipimovimentoBySoggettiEsterniAndRichiestaIntegrazioniAndCodiceIstanza(Integer codiceIstanza, String idcomune);
}
