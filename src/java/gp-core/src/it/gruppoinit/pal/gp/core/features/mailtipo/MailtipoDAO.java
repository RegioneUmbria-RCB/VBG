package it.gruppoinit.pal.gp.core.features.mailtipo;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MailtipoDAO extends BaseDAO<Mailtipo, PkId> {

    /**
     * Restituisce le Mail tipo (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Il metodo deve restituire una lista di mail tipo, il filtro sarà dato un oggetto Mail tipo. Campi utilizzati per
     * il filtro: 1-Descrizione
     * 
     * @param filter
     * @return una lista di mail tipo filtrato per Mail tipo
     */
    public List<Mailtipo> findByFilter(Mailtipo filter);

    /**
     * <pre>
     * Il metodo ritorna una lista di oggetti mail tipo filtrati per:
     * 
     *  1- idcomune 
     *  2- software corrente e TT 
     *  3- ambito mail se contestiMailTipoEnum diverso da null
     * 
     * @param contestiMailTipoEnum
     * @return
     * </pre>
     */
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum);
}
