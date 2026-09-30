/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.dataimport.web.helper.ImportRunContext;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MercatiRestConIdGiornataBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;

/**
 * @author francescop
 * 
 */
public interface MercatiService extends BaseService<Mercati, PkId> {

    public List<Mercati> findByDescrizione(String descrizione);

    /**
     * Metodo che serve per verificare se un mercato è configurato correttamente dal punto di vista dei conti per l'anno
     * selezionato.
     * 
     * @param mercato
     *            il mercato per il quale verificare le configurazioni
     * @param anno
     *            l'anno da controllare
     * @return true o false a seconda che la configurazione sia valida o meno
     */
    public boolean verificaConfigurazioneContiMercato(Mercati mercato, int anno);

    /**
     * Metodo per filtrare i mercati anche tra quelli attivi e disattivi
     * 
     * @param descrizione
     * @param mercatiEnum
     * @return
     */
    public List<Mercati> findByDescrizione(String descrizione, MercatiEnum mercatiEnum);

    /**
     * Cerca una lista di mercati che abbiano la contabilità abilitata o meno
     * 
     * @param descrizione
     *            la stringa per filtrare i mercati in base a descrizione
     * @param isFlagContabilita
     *            se la gestione della contabilità è attivata o meno
     * @param mercatiEnum
     *            tutti i mercati, solo quelli attivi, solo quelli disabilitati
     * @see it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum
     * @return una lista di mercati che corrisponde ai criteri di ricerca
     */
    public List<Mercati> findByFlagContabilita(String descrizione, boolean isFlagContabilita, MercatiEnum mercatiEnum);

    /**
     * @see MercatiDAO#findAll(Integer, Integer)
     */
    public List<Mercati> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce i mercati ATTIVI (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Mercati> findAllMercatiAttivi(Integer firstResult, Integer maxResult);

    /**
     * @see MercatiDAO#existsRecords(FilterTable);
     */
    public boolean existsRecords(FilterTable filterTable);

    /**
     * Il metodo verifica se per il mercato in esame è configurato una preferenza sull' uso (Configurato il campo
     * dinamico che gestisce le preferenze sull'uso)
     */
    public ConfigurazionePreferenzeUsoPerMercatoEnum isPreferenzaUsoConfigurata(Integer codiceMercato);

    public List<MercatiRestHelper> findAttiviByDescrizione(String filtroDescrizione, Integer firstResult, Integer maxResults);

    public List<Mercati> findByDescrizioneAndResponsabile(String descrizione, Integer codiceResponsabile, MercatiEnum active, Integer firstResult,
	    Integer maxResults);

    public Mercati findByPosteggio(Integer idPosteggio);

    public List<MercatiRestConIdGiornataBean> findAttiviOggi(String filtroDescrizione);

    public void updateCopiaInfoMercato(Integer codiceMercato, boolean spostaConcessioni, Integer idCausaleCessazione, Integer idCausaleAcquisizione);

    public int countByCategorieMercato(Integer idCategoriaMercato);

    public void insertDaImport(Mercati entity, ImportRunContext context);

    /**
     * Torna una mappa con chiave l'idposteggio e valore le caratteristiche del posteggio occupato o no
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param dataGiornataMercato
     * @return
     */
    public Map<Integer, PosteggiConcessioniHelper> findPosteggiMercatoAllaData(Integer codiceMercato, Integer codiceUso, Date dataGiornataMercato);
}
