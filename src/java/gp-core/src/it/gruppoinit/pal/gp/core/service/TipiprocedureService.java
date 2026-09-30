package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;

import java.util.List;

public interface TipiprocedureService extends BaseService<Tipiprocedure, PkId> {

    /**
     * @see TipiprocedureDAO#findAll(Integer, Integer)
     */
    public List<Tipiprocedure> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo che restituisce i tipi procedure filtrate per Idcomune, per il software corrente e per il softrware TT
     * 
     * @return
     */
    public List<Tipiprocedure> findAllBySoftwareAndTT();

    /**
     * @see TipiprocedureDAO#findByDescrizione(String)
     */
    public List<Tipiprocedure> findByDescrizione(String textToSearch);

    /**
     * Restituisce tutti i tipi procedura per il software in esame e TT, che hanno almeno un movimento di avvio
     * configurato
     * 
     * @param textToSearch
     * @return
     */
    public List<Tipiprocedure> findTipiprocedureWithMovimentoavvio();

    /**
     * @see TipiprocedureDAO#findByCodiceODescrizione(String)
     */
    public List<Tipiprocedure> findByCodiceODescrizione(String text, boolean includiDisabilitate, boolean soloConMovimentoAvvio);

    /**
     * Ritorna una lista di tipi procedure per il quale movimento passato è configurato come movimento che determina la
     * data di validità dell'istanza (DETERMINAZIONEIDMOVIMENTO) legata alla procedura
     * 
     * @param tipomovimento
     * @return Lista di oggetti Tipiprocedure, se presenti, altrimenti lista vuota.
     */
    public List<Tipiprocedure> findByMovimetoDeterminazioneDataValiditaIstanza(String tipomovimento);

    /**
     * Trova tutti i record legati alla lettera tipo passata come argomento
     * 
     * @param letteretipo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipiprocedure> findByLetteretipo(Letteretipo letteretipo, int firstResult, int maxResult);

    /**
     * Il metodo controlla se è possibile disabilitare il record corrente. E' possibile disabilitare un record di
     * Tipiprocedure se non è configurata nessuna dipendenza nelle tabelle di configurazione. Ad oggi
     * <fieldset><legend>Tabelle per le quali viene effettuato il controllo</legend>
     * <ul>
     * <li>ALBEROPROC.FKIDPROCEDURA</li>
     * <li>TIPICONTROMOVIMENTO.CODICEPROCEDURA</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo</legend>
     * <ul>
     * <li>TIPIPROCEDUREAVVIO.CODICEPROCEDURA</li>
     * <li>TIPIPROCEDURE_DOCUMENTI.TP_FKPROCEDURA</li>
     * <li>TIPIPROCEDURE_DYN2MODELLIT.FK_CODICEPROCEDURA</li>
     * <li>IMPIANTIPROCEDURE.CODICEPROCEDURA</li>
     * <li>SUBPROCEDURE.CODICEPROCEDURA</li>
     * <li>TEMPIRISPOSTA.CODICEPROCEDURA (ci pensa l'elaborazione)</li>
     * </ul>
     * </fieldset><br />
     * <fieldset><legend>Tabelle per le quali non viene effettuato il controllo in quanto non sono di
     * configurazione</legend>
     * <ul>
     * <li>ISTANZE.CODICEPROCEDURA</li>
     * </ul>
     * 
     * @param tipiprocedure
     * @return
     */
    public boolean checkSeDisabilitare(Tipiprocedure tipiprocedure);

    /**
     * Il metodo cerca nella tabella procedure filtrando per tipomovimento nei seguenti campi:
     * <ul>
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
     * </ul>
     * 
     * L'ordinamento è TIPIPROCEDURE.SOFTWARE.ORDINE,TIPIPROCEDURE.SOFTWARE.DESCRIZIONE, TIPIPROCEDURE.PROCEDURA
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipiprocedure> findByTuttiCampiTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);
}
