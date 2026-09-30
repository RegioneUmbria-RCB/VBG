package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DomandestcHelper;
import it.gruppoinit.pal.gp.core.domain.web.DomandeStcFilter;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface DomandestcDAO extends BaseDAO<Domandestc, PkId> {

    /**
     * 
     * Lista di tutte le domande stc filtrate per idcomune
     */
    public List<Domandestc> findAll(Integer firstResult, Integer maxResult);

    public List<DomandestcHelper> countDomandestc(String codicesoftware, String stato);

    /**
     * <pre>
     * recupera la lista di tutte le domande provenienti da stc. Le instanze verranno filtrate per 
     * 	<ol> 
     * 		<li>Software corrente, se la ricerca viene fatta su TT allora si filtrerà per i software attivi sull'operatore</li>
     * 		<li>Idcomune</li>
     * 		<li>Nel caso di installazione multicomune sarà applicato un filtro per tutti i comuni attivi per l'operatore loggato </li>
     * 		<li>per il campo flag_import: true=importare, false = non importate</li> 
     *          <li>Nel caso String codiceSoftwareSca:String sia diverso da vuoto si filtrerà solo per il codice software passato</li>
     * 	</ol>
     * @param firstResult
     * @param maxResult
     * @return
     * </pre>
     */
    public List<DomandeSTCScadenzarioDTO> findDomandePervenuteSTC(DomandeStcFilter domandeStcFilter, boolean isImportate, String codiceSoftwareScad,
	    Integer firstResult, Integer maxResult);

    public int countScadenzarioDomandePervenuteSTC(boolean isImportate, String codiceSoftwareScad, DomandeStcFilter domandeStcFilter);
}
