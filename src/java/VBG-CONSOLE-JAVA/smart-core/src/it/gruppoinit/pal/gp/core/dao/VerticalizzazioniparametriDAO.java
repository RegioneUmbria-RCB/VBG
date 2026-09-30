package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;

import java.util.List;

public interface VerticalizzazioniparametriDAO extends BaseDAO<Verticalizzazioniparametri, PkId> {

    /**
     * Recupera il parametro del modulo(verticalizzazione) specificati. la ricerca è effettuata sia per il software
     * corrente che per TT. Se trova il parametro per il software corrente ritorna quello altrimenti quello per il
     * software TT altrimenti <b>null</b>. (non verifica se la verticalizzazione è attiva)
     * 
     * @param modulo
     *            verticalizzazione scelta
     * @param parametro
     *            parametro della verticalizzazione
     * @return il record della tabella verticalizzazioniparametri
     */
    public Verticalizzazioniparametri findByModuloAndParametro(String modulo, String parametro);

    public Verticalizzazioniparametri findByModuloAndParametroAndComune(String modulo, String parametro, String codiceComune);

    public Verticalizzazioniparametri findByModuloAndParametroAndSoftware(String modulo, String parametro, String software);

    public Verticalizzazioniparametri findByModuloAndParametroAndSoftwareAndComune(String modulo, String parametro, String software,
	    String codiceComune);

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
}
