/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BandiDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeFiere;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorieHelper;

import java.util.Date;
import java.util.List;

/**
 * @author fabrizioc
 * 
 */
public interface BandiService extends BaseService<Bandi, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see BandiDAO#findByAlberoproc(Alberoproc)
     * @param alberoproc
     * @return
     */
    public List<Bandi> findByAlberoproc(Alberoproc alberoproc);

    /**
     * vedi doc del DAO
     * 
     * @see BandiDAO#findIstanzeAnagrafeGraduatoria(Alberoproc, Anagrafe)
     * @param alberoproc
     * @param anagrafe
     * @return
     */
    public List<AnagrafeFiere> findIstanzeAnagrafeGraduatoria(Alberoproc alberoproc, Anagrafe anagrafe);

    /**
     * Torna la lista dei Bandi di una voce dell'albero
     * 
     * @param codiceAlberoproc
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Bandi> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult);

    /**
     * Il metodo assegna automaticamente la concessione alle istanze presenti nelle graduatorie seguendo la logica:
     * 
     * TODO
     * 
     * @param graduatoriet
     */
    public void insertConcessioniAlleIstanzeInGratuatoria(Graduatoriet graduatoriet);

    public GraduatorieHelper findHelperGraduatoria(Integer graduatoriaid);

    /**
     * <pre>
     * Il metodo rilascia le concessioni per il piano di rotazione creato secondo la logica:
     * 1- Recupera le istanza della graduatorie:
     * 2- Per ognuna rilascia un'autorizzazione 
     * 3- Associa l'autorizzaiozne creata a N concessioni che verranno create per ogni posteggio occupato
     *    nel piano di rotazione
     * @param graduatoriet
     * @param dateRilascio
     * @param tipologiaregistri
     * </pre>
     */
    public void insertConcessioniPianoRotazione(Graduatoriet graduatoriet, Date dateRilascio, Tipologiaregistri tipologiaregistri);

    /**
     * Ritorna una lista di bandi filtrati (ilike) per descrizione
     * 
     * @param textToSearch
     * @param object
     * @param object2
     * @return
     */
    public List<Bandi> findByDescrizione(String textToSearch);
}
