package it.gruppoinit.pal.gp.core.service;

import java.util.List;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.TipiMovimentoDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

public interface TipiMovimentoService extends BaseService<Tipimovimento, TipimovimentoId> {

    /**
     * metodo per la ricerca tramite like dei tipiMovimento. restituisce sia quelli del software corrente sia quelli del
     * software TT
     * 
     * @param entity
     * @param includiDisabilitati
     *            se true allora visualizza anche quelli con FLAG_DISABILITATO=1
     * @return
     */
    public List<Tipimovimento> findByDescrizione(Tipimovimento entity, boolean includiDisabilitati);

    /**
     * Metodo ricorsivo che estare i contro movimenti di un movimento
     */
    public List<Tipimovimento> visitaCMov(Tipimovimento mov, Integer codiceprocedimento);

    /**
     * Lista di movimenti associati a un procedimento
     */
    public List<Tipimovimento> listMovimentiAssociatiAunProcedimento(Integer cod);

    /**
     * svuota la lista dei movimenti visitati
     */
    public void clearListaMovimentivisitati();

    public Tipimovimento getTipiMovimentoFlagCamcom();

    /**
     * @see TipiMovimentoDAO#findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity,String software)
     * @param includiDisabilitati
     *            se true allora visualizza anche quelli con FLAG_DISABILITATO=1
     */
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati);

    /**
     * @see TipiMovimentoDAO#findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity,String software)
     * @param includiDisabilitati
     *            se true allora visualizza anche quelli con FLAG_DISABILITATO=1
     * @param escludiNonUsatiInProtocollo
     *            se true allora vengono restituiti solo quelli con FLAG_USADALPROTOCOLLO=1
     */
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati,
	    boolean escludiNonUsatiInProtocollo);

    /**
     * @see ipiMovimentoDAO#findAll(Integer, Integer)
     */
    public List<Tipimovimento> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista di tipimovimento ordinati per movimento per i softwre definiti da quelli abilitati per il
     * responsabile
     * 
     * @param textToSearch
     * @param codiceResponsabile
     * @param includiDisabilitati
     *            se true allora visualizza anche quelli con FLAG_DISABILITATO=1
     * @return
     */
    public List<Tipimovimento> findByTipimovAndResponsabile(String textToSearch, Integer codiceResponsabile, boolean includiDisabilitati);

    /**
     * Trova tutti i record legati alla lettera tipo passata come argomento
     * 
     * @param letteretipo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipimovimento> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult);

    /**
     * Ritorna il valore del campo FLAG_NOAMMINTERNA per il tipomovimento specificato
     * 
     * @param tipomovimento
     * @return
     */
    public boolean getFlagNoamminterna(String tipomovimento);

    /**
     * Il metodo controlla se è possibile disabilitare il record corrente. E' possibile disabilitare un record di
     * TIPIMOVIMENTO se non è configurata nessuna dipendenza nelle tabelle di configurazione. Ad oggi
     * <fieldset><legend>Tabelle per le quali viene effettuato il controllo</legend> <br />
     * <ul>
     * <li>COMMEDILIZIE_TIPOLOGIEDETT.TIPOMOVIMENTO</li>
     * <li>COMMEDILIZIE_TIPOPARERI.TIPOMOVIMENTO</li>
     * <li>INVENTARIOPROCEDIMENTI.TIPOMOVIMENTO</li>
     * <li>INVENTARIOPROCEDIMENTISOFTWARE.TIPOMOVIMENTO</li>
     * <li>ONERITIPIRATEIZZAZIONE.FK_TIPOMOV_DETERMDATAIN</li>
     * <li>PROTOCOLLO_REGISTRI.IDTIPOMOVIMENTO</li>
     * <li>TIPICONTROMOVIMENTO.TIPOCONTROMOVIMENTO</li>
     * <li>TIPICONTROMOVIMENTO.TIPOMOVIMENTO</li>
     * <li>TIPIPROCEDURE.IDCONSMINISTRI</li>
     * <li>TIPIPROCEDURE.DETERMINAZIONEIDMOVIMENTO</li>
     * <li>TIPIPROCEDURE.IDAUTORICHIESTADOC</li>
     * <li>TIPIPROCEDURE.IDAUTOAUDIZTERZI</li>
     * <li>TIPIPROCEDURE.IDAUTOPUBBLICITA</li>
     * <li>TIPIPROCEDURE.IDAUTOSOSPENSIONE</li>
     * <li>TIPIPROCEDURE.IDAUTOCHIUSURACONTR</li>
     * <li>TIPIPROCEDURE.IDCHIUSURACDS</li>
     * <li>TIPIPROCEDURE.IDCOMUNCDS</li>
     * <li>TIPIPROCEDURE.IDESITOPROVVAUTORIZZATIVO</li>
     * <li>TIPIPROCEDURE.IDCHIUSURAISTANZA</li>
     * <li>TIPIPROCEDURE.IDTRASMNEGATIVA</li>
     * <li>TIPIPROCEDUREAVVIO.TIPOMOVIMENTO</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo</legend> <br />
     * <ul>
     * <li>RESPONSABILI_TM_AVV.TIPOMOVIMENTO</li>
     * <li>RESPONSABILI_TM_SCA.TIPOMOVIMENTO</li>
     * <li>TEMPIRISPOSTA.TIPOCONTROMOVIMENTO</li>
     * <li>TEMPIRISPOSTA.TIPOMOVIMENTO</li>
     * <li>TIPIMOVIMENTIDYN2MODELLIT.TIPOMOVIMENTO</li>
     * <li>TIPIMOVIMENTODOCTIPO.TIPOMOVIMENTO</li>
     * <li>TIPIMOVIMENTOONERI.TIPOMOVIMENTO</li>
     * <li>TIPIMOV_STC_ALBEROPROC.TIPOMOVIMENTO</li>
     * <li>TIPIMOV_STC_ALTRIDATI.TIPOMOVIMENTO</li>
     * <li>TIPIMOV_STC_MAPPING.TIPOMOVIMENTO</li>
     * <li>TIPIMOV_STC_MODELLI.TIPOMOVIMENTO</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo in quanto non sono di
     * configurazione</legend> <br />
     * <ul>
     * <li>ISTANZE.TIPOMOVAVVIO</li>
     * <li>ISTANZEONERI.TIPOMOVIMENTO</li>
     * <li>MOVIMENTI.TIPOMOVIMENTO</li>
     * </ul>
     * 
     * @param tm
     * @return
     */
    public boolean checkSeDisabilitare(Tipimovimento tm);

    /**
     * Ricerca indipendentemente dal software
     * 
     * @param tipoMovimento
     * @param includiDisabilitate
     * @return
     */
    public List<Tipimovimento> findByDescrizionePerTuttiISoftware(Tipimovimento tipoMovimento, Boolean includiDisabilitate);

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
