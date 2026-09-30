package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;

public interface AlberoprocDAO extends BaseDAO<Alberoproc, PkId> {

    public List<Alberoproc> findByCriteria(DetachedCriteria criteria);

    public Alberoproc findByScCodice(String sccodice);

    public Alberoproc findBySoftwareAndScCodice(String software, String sccodice);

    /**
     * torna la lista di record di alberoproc di un determinato software ordinati per la proprietà sc_Codice ASC
     */
    public List<Alberoproc> findAll(Integer firstResult, Integer maxResult);

    public List<Alberoproc> findAlberoprocFigli(String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    boolean isPerCalcoloprogressivo);

    public int countAlberoprocFigli(String scCodicePadre, boolean soloPrimoLivello);

    /**
     * <pre>
     * Rappresneta il primo livello gerarchico della voce dell'albero scelta. Logica: dal codice scCodice della voce dell'albero
     * estraggo solo i primi due caratteri (rappresentano il codice del primo livello) e esegue una query per idcomune,software e
     * scCodice(ricavato).
     * Es. 
     * 	Voce albero scelta 			: Somministrazione bevande alcoliche
     *  Gerarchia albero per la voce scelta 	: Commercio - Bar all'aperto - Somministrazione bevande alcoliche
     *  
     *  Il metodo deve ritornare :  Commercio
     * 
     * &#64;param scCodice
     * &#64;return
     * </pre>
     */
    public String findDescrizionePrimaVoceAlberoproc(String scCodice);

    /**
     * <pre>
     * Ritorna una lista di AlberoprocCommand filtrata per idcomune e software, ogni oggetto  
     *  AlberoprocCommand viene popolato con le informazioni dell'albero: 
     * <ol>
     * 	<li>alberoproc.id.codice</li>
     * 	<li>alberoproc.scCodice</li>
     * 	<li>alberoproc.ScPadre</li>
     * 	<li>alberoproc.scDescrizione</li>
     * 	<li>VwAlberoproc.scDescrizioneEstesa</li>
     * 	<li>alberoproc.scAttivo</li>
     * </ol>
     * &#64;param rootCodiceAlbero 
     * 
     * &#64;return
     * </pre>
     */
    public List<AlberoprocCommand> findAlberoprocCommand(Integer rootCodiceAlbero);

    public void updateScCodice(Integer codiceAlberoproc, String scCodice);
}
