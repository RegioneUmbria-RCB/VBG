package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface VerticalizzazioniparametriDAO extends BaseDAO<Verticalizzazioniparametri, PkId> {

    List<Verticalizzazioniparametri> findByCriteria(DetachedCriteria criteria);

    /**
     * Recupera la lista delle verticalizzazioni paramatri filtrate per modulo,idcomune e software, la lista sarà
     * ordinata per il campo "ordine" di software e per il campo parametro
     * 
     * @param modulo
     * @param idcomune
     * @return
     */
    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftware(String modulo, String idcomune, String software);

    public List<Verticalizzazioniparametri> findByModuloAndIdcomuneAndSoftwareAndComune(String modulo, String idcomune, String software,
	    String codiceComune);

    public List<ChiaveValoreBean<Comuni, Software>> findComuniESoftware(String modulo, String... parametro);

    /**
     * Il metodo NON tiene conto di IdComune volutamente, serve principalmente per funzionalità di UPGR in cui bisogna
     * leggere/spostare parametri in modo trasversale al comune corrente
     * 
     * @param modulo
     * @param parametri
     * @return
     */
    public List<Verticalizzazioniparametri> findParametriByModulo(String modulo, String... parametro);
}
