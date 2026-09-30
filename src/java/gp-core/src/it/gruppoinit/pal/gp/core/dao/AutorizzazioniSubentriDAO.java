package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface AutorizzazioniSubentriDAO extends BaseDAO<AutorizzazioniSubentri, PkId> {

    /**
     * Non implementato
     * 
     */
    public List<AutorizzazioniSubentri> findAll(Integer firstResult, Integer maxResult);

    /**
     * recupera l'autorizzazione/concessione tra i subentri per chiave univoca (numero,data,comune,registro).
     * 
     * @param autoriznumero
     * @param autorizdata
     * @param codicecomune
     * @param codiceregistro
     * @return l'autorizzazione o null
     */
    public AutorizzazioniSubentri findByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro);

    /**
     * recupera le autorizzazioni presenti tra i subentri con codice istanza specificato.<br />
     * (INNER JOIN TRA AUTORIZZAZIONI_SUBENTRI ED AUTORIZZAZIONI E LEFT JOIN CON AUTORIZZAZIONI_CONCESSIONI CON
     * AUT_CONC.ID NULL)
     * 
     * @param istanza
     * @return
     */
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByIstanza(Istanze istanza);

    /**
     * recupera le concessioni presenti tra i subentri con codice istanza specificato.<br />
     * (INNER JOIN TRA AUTORIZZAZIONI_SUBENTRI ED AUTORIZZAZIONI ED INNER JOIN CON AUTORIZZAZIONI_CONCESSIONI)
     * 
     * @param istanza
     * @return
     */
    public List<AutorizzazioniSubentri> findConcessioniSubentriByIstanza(Integer codiceIstanza);

    public int countByFilter(AutorizzazioniFilter filter);

    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult);
}
