/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface AlberoprocpeoplehrefService extends BaseService<Alberoprocpeoplehref, PkId> {

    /**
     * Metodo che restituisce la lista di Alberoprocpeoplehref filtrati per Alberoproc
     * 
     * @param alberoproc
     * @return
     */
    public List<Alberoprocpeoplehref> findByAlberoProc(Alberoproc alberoproc);

    public List<Alberoprocpeoplehref> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * 
     *  <b>Metodo utilizzato per l'integrazione con un nodo NLA PEOPLE</b>
     *  
     * 	Logica:
     * 
     * ALBEROPROCPEOPLEHREF in join con ALBEROPROC
     *  
     *  	1- Il metodo filtra i record della tabella ALBEROPROCPEOPLEHREF per nomeTag e la lista valoriTag e software.
     *  	2- Ordina (desc) il risultato per sc_codice della tabella ALBEROPROC
     *  	3- Ritorna il primo valore della lista (quello che ha sc_codice maggiore)
     *  
     *   
     *  
     *  
     *  
     *  @param 	nomeTag
     *            	 filtro da applicare
     *  @param 	valoriTag
     *            	 filtro da applicare
     *  @param 	codiceSoftware
     *            	 filtro da applicare tramire la tabella ALBEROPROC
     *  @return	Alberoprocpeoplehref con valore sc_codice maggiore se esiste, null se non è presente un record con le caratteristiche passate
     * 
     * </pre>
     */
    public Alberoprocpeoplehref findByTagNlaPeople(String nomeTag, List<String> valoriTag, String codiceSoftware);
}
