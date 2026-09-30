/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface AlberoprocpeopleoperService extends BaseService<Alberoprocpeopleoper, PkId> {

    public List<Alberoprocpeopleoper> findByFilterTable(FilterTable filterTable);

    /**
     * Metodo che restituisce la lista di Alberoprocpeopleoper filtrati per Alberoproc
     * 
     * @param alberoproc
     * @return
     */
    public List<Alberoprocpeopleoper> findByAlberoProc(Alberoproc alberoproc);

    /**
     * <pre>
     * 
     *  <b>Metodo utilizzato per l'integrazione con un nodo NLA PEOPLE</b>
     * 
     * Logica:
     * 
     *   1- Viene ricercata una lista di  ALBEROPROCPEOPLEOPER  (order by sc_codice desc):
     *    	
     *    	- settore individuato  dalla pratica
     *    	- operazioni individuate dalle pratiche 
     *    	- software (tramite ALBEROPROC)
     * 	 
     * 	 2- Se non viene trovato Viene ricercata una lista di  ALBEROPROCPEOPLEOPER(order by sc_codice desc): 
     * 
     * 		- con codice settore nullo 
     * 		- operazioni individuate dalle pratiche 
     * 		- software (tramite ALBEROPROC) 
     * 
     *  
     *  @param settore 		: filtro che indica il settore della pratica
     *  @param operazioni	: filtro che indica la lista delle operazioni per cui filtrare la ricerca
     *  @param software		: filtro software 
     *  @param searchForSettore	: Parametro booleano per decidere se filtare anche per Settore
     *  				
     *  				1- true : Cerca per settore
     *  				2- false: Esclude la settore dalla ricerca.
     *  				3- null : Exception
     * 
     * @return  List<Alberoprocpeopleoper>
     * 
     * <pre>
     */
    public List<Alberoprocpeopleoper> findBySettoreAndOperazioniNlaPeople(String settore, List<String> operazioni, String codiceSoftware,
	    boolean searchForSettore);
}
