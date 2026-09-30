package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.NaturaendoDAO;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

import java.util.List;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
public interface NaturaendoService extends BaseService<Naturaendo, NaturaendoId> {

    /**
     * 
     * @see NaturaendoDAO#findBydescrizione(String descrizione)
     */
    public List<Naturaendo> findBydescrizione(String descrizione);

    /**
     * Il metodo ritorna una lista di nature endo compatibili contenute in una lista di nature endo e una natura endo
     * passata (regola dipendenze).<br />
     * <b>Regola dipendenze</b><br />
     * <b>Natura endo 1</b>: codicenatura: 1 binariodipendenze:7 natura: Ordinario <br/>
     * <b>Natura endo 2</b>: codicenatura: 2 binariodipendenze:6 natura: Autocertificazione <br />
     * <b>Natura endo 3</b>: codicenatura: 4 binariodipendenze:4 natura:DIA <br/>
     * 
     * <b>ES.</b><br />
     * La natura endo <b>Ordinario</b> ha il campo <b>binariodipendenze</b> uguale a 7 quindi sarà compatibile con:<br>
     * 
     * <ul>
     * <li>Se stessa</li>
     * <li>Autocertificazione</li>
     * <li>DIA</li>
     * </ul>
     * In quanto la somma dei loro codicinatura fa 7<br />
     * 
     * La natura endo <b>DIA</b> ha il campo <b>binariodipendenze</b> uguale a 4 quindi sarà compatibile con:<br />
     * <ul>
     * <li>Se stessa</li>
     * </ul>
     * <br />
     * 
     * In quanto il suo codicenatura è già pari al valore <b>binariodipendenze</b>
     * 
     * Se non viene passata nessuna lista, la compatibilità sarà verificata su tutta la lista delle nature configurate
     * per il comune
     * 
     * @param list
     *            :Lista di nature endo di cui si vuol vedere la compatibiltà con la natura passata
     * @param naturaendo
     *            : natura per cui si vogliono trovare le altre nature endo compatibili
     * @param isEscludiNaturaPassata
     *            : dal risultato esclude,se presente la natura endo passata.
     * @return : Lista di nature endo compatibili
     */
    public List<Naturaendo> getNatureendoByDipendenze(List<Naturaendo> list, Naturaendo naturaendo, boolean isEscludiNaturaPassata);

    /**
     * Ritorna la lista delle nature endo esclusa quella passata
     * 
     * @param naturaendo
     *            opzionale, se passato null non escluse nessuna natura endo dal risultato
     * @return
     */
    public List<Naturaendo> findAllExcludeNatura(Naturaendo naturaendo, OrderTypeEnum orderTypeEnum);

    /**
     * 
     * @see NaturaendoDAO#findMaxCodicenatura()
     */
    public Integer findMaxCodicenatura();

    /**
     * Ritorna la natura endo associata alla modalita di apertura configurata in STP_MODALITA_APERTURA per il tipo
     * scehda passata(se ci sono più record prende il primo ), se non la trova ritorna null
     * 
     * @param nameModalitaAperturaEndo1
     * @param tipoScheda
     *            : SCHEDA_TIPO_ENDO1 e SCHEDA_TIPO_ENDO2
     * @return
     */
    public Naturaendo findByStpModalitaAperturaAndTipoScheda(String nameModalitaAperturaEndo1, String tipoScheda);
}
