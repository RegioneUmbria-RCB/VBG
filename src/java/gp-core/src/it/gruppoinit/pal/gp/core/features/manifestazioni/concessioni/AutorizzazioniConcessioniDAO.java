package it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniConcessioniDAO extends BaseDAO<AutorizzazioniConcessioni, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see AutorizzazioniConcessioniDAO#findAll(Integer, Integer)
     * 
     */
    public List<AutorizzazioniConcessioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * recupera tutte le concessioni (INNER JOIN TRA AUTORIZZAZIONI_CONCESSIONI E AUTORIZZAZIONI) con codiceistanza
     * specificato
     * 
     */
    public List<AutorizzazioniConcessioni> findConcessioniByIstanza(Integer codiceIstanza);

    /**
     * recupera tutte le concessioni attive per quel mercato e uso
     * 
     * @param mercato
     * @param uso
     * @return
     */
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercatoEUso(Mercati mercato, MercatiUso uso);

    /**
     * recupera tutte le concessioni attive per quel mercato e uso
     * 
     * @param mercato
     * 
     * @return
     */
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercato(Mercati mercato);

    /**
     * recupera tutte le concessioni attive e non per quel mercato e uso
     * 
     * @param mercato
     * 
     * @return
     */
    public List<AutorizzazioniConcessioni> findConcessioniByMercato(Mercati mercato);
    
    public List<Integer> findIdAutorizzazioniAttiveByIdPosteggio(int idPosteggio);

    public Integer getFkIdautAttualePerAutCollegata(Integer fkIdautCollegata);

    public Map<Integer, IdentificativoDescrizioneBean> findAutorizzazioniCollegate(Set<Integer> auts);

}
