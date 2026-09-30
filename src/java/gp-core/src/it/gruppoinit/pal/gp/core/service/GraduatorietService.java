/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GraduatorietDAO;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface GraduatorietService extends BaseService<Graduatoriet, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see GraduatorietDAO#compilaGraduatoriaSingoloIntervento(Graduatoriet)
     * @param entity
     */
    public void compilaGraduatoria(Graduatoriet entity);

    /**
     * vedi doc del DAO
     * 
     * @see GraduatorietDAO#compilaGraduatoriaMultiIntervento(Graduatoriet entity)
     * @param entity
     */
    public void compilaGraduatoriaMultiIntervento(Graduatoriet entity);

    /**
     * vedi doc del DAO
     * 
     * @see GraduatorietDAO#findByFilter(Graduatoriet)
     * @param entity
     * @return
     */
    public List<Graduatoriet> findByFilter(Graduatoriet entity);

    public List<Tipibandooutput> findTipiBandiOutput(Integer tipiGraduatorieId);

    public List<Bandiinput> findTipiBandiInput(Integer bandiId);

    /**
     * ritorna un alista di graduatorie T filtrate per bando, se passato e per (ilike) descrizione
     * 
     * @param textToSearch
     * @param codicebando
     * @return
     */
    public List<Graduatoriet> findByAndBandoDescrizione(String textToSearch, Integer codicebando);
}
