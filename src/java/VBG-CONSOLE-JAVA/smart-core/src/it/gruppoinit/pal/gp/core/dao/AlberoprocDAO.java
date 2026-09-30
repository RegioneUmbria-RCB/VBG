package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

public interface AlberoprocDAO extends BaseDAO<Alberoproc, PkId> {

    public List<Alberoproc> findByCriteria(DetachedCriteria criteria);

    public Alberoproc findByScCodice(String idcomune, String sccodice);

    /**
     * torna la lista di record di alberoproc di un determinato software ordinati per la proprietà sc_Codice ASC
     */
    public List<Alberoproc> findAll(Integer firstResult, Integer maxResult);

    public List<Alberoproc> findAlberoprocFigli(String idcomune, String scCodicePadre, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    Boolean isPerCalcoloprogressivo);

    public int countAlberoprocFigli(String idcomune, String scCodicePadre, boolean soloPrimoLivello);

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
     * @param scCodice
     * @return
     * </pre>
     */
    public String findDescrizionePrimaVoceAlberoproc(String idcomune, String scCodice);

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
     * @param rootCodiceAlbero 
     * 
     * @return
     * </pre>
     */
    public List<AlberoprocCommand> findAlberoprocCommand(String idcomune, Integer rootCodiceAlbero);

    public void updateScCodice(String idcomune, Integer codiceAlberoproc, String scCodice);

    public String findDescrizioneAlberoproc(String idcomune, Integer codiceAlberoproc);

    public boolean checkComunica(Alberoproc ap);

    public List<InterventoSimpleBean> findInterventiByDescrizione(String idcomune, String testoDaCercare, String tipoRicerca, String campiRicerca,
	    boolean filtraSoloComunica, Integer firstResult, Integer maxResults, boolean soloModulisticaNazionale);

    public boolean checkModulisticaNazionale(Alberoproc ap);

    /**
     * <pre>
     * 
     * 
     * SELECT COUNT(*)  FROM ALBEROPROC_ENDO WHERE ALBEROPROC_ENDO.IDCOMUNE='MODUMB' AND (FKSCID IN 
     * (SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE='MODUMB' AND SOFTWARE='SS' AND SC_CODICE IN ('01','0101') GROUP BY SC_ID)
     * OR FKSCID IN 
     * (
     * SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE='MODUMB' AND SOFTWARE='SS' AND SC_CODICE LIKE ('0101%') GROUP BY SC_ID)
     * )
     * AND FLAG_PRINCIPALE=1 AND FLAG_PUBBLICA=1
     * ;
     * 
     * SELECT COUNT(*)  FROM ALBEROPROC_ENDO_LOC WHERE IDCOMUNE='LOCAL' AND FK_AP_IDCOMUNE='MODUMB' AND (ALBEROPROC_ENDO_LOC.FK_AP_SCID IN 
     * (SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE='MODUMB' AND SOFTWARE='SS' AND SC_CODICE IN ('01','0101') GROUP BY SC_ID)
     * OR FK_AP_SCID IN 
     * (
     * SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE='MODUMB' AND SOFTWARE='SS' AND SC_CODICE LIKE ('0101%') GROUP BY SC_ID)
     * )
     * AND ALBEROPROC_ENDO_LOC.FLAG_NECESSARIO=1 AND FLAG_PUBBLICA=1 and (ALBEROPROC_ENDO_LOC.codicecomune is null or ALBEROPROC_ENDO_LOC.codicecomune='E256');
     * 
     * </pre>
     * 
     * @param codiciIntervento
     * @param codiceComune
     * @return
     */
    public Set<Integer> verificaInterventiConEndoPrincipale(Map<Integer, String> codiciIntervento, String codiceComune);
}
