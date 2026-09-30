package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;
import java.util.Set;

public interface ResponsabiliService extends BaseService<Responsabili, PkId> {

    public List<Responsabili> findByFilter(Responsabili responsabili);

    public void saveRuoli(Responsabili responsabili, Set<Responsabiliruoli> responsabiliruolis);

    public void saveParametriprotocollo(Responsabili entity);

    /**
     * Metodo che aggiorna la lista di permessi di un responsabile con quella fornita in input per un determinato
     * software
     * 
     * @param entity
     * @param nuoviPermessi
     * @param codiceSoftware
     */
    public void savePermessiSW(Responsabili entity, Set<Clpermmenu> nuoviPermessi, String codiceSoftware);

    /**
     * Metodo che aggiorna la lista di permessi di un responsabile con quella fornita in input per una lista di Software
     */
    public void savePermessi(Responsabili entity, Set<Clpermmenu> nuoviPermessi, List<Software> listSoftware);

    public List<Responsabili> findAllByAbilitati();

    /**
     * Metodo che restituisce la lista di responsabili.<br/>
     * Fitri:<br/>
     * 1) SoftwareAbilitati = Software corrente<br/>
     * 2) Tipiresponsabili.trFlagresponsabile=true or amministrazione="1"<br/>
     * 3) disabilitato=false<br/>
     * 
     * Se la lista è vuota restituisce la lista filtrata solo per i punti 1) e 3)
     * 
     * @param responsabili
     * @return
     */
    public List<Responsabili> findResponsabiliProcedimento(Responsabili responsabili);

    /**
     * Metodo che restituisce la lista di responsabili.<br/>
     * Fitri:<br/>
     * 1) SoftwareAbilitati = Software corrente<br/>
     * 2) Tipiresponsabili.trFlagistruttore=true or amministrazione="1"<br/>
     * 3) disabilitato=false<br/>
     * 
     * Se la lista è vuota restituisce la lista filtrata solo per i punti 1) e 3)
     * 
     * @param responsabili
     * @return
     */
    public List<Responsabili> findResponsabiliIstruttoria(Responsabili responsabili);

    /**
     * @see ResponsabiliDAO#findAll(Integer, Integer)
     */
    public List<Responsabili> findAll(Integer firstResult, Integer maxResult);

    public List<Responsabili> findByFilterTable(FilterTable filterTable);

    /**
     * Replica i permessi del responsabile passato in input alla lista di responsabili (responsabiliSet)
     * 
     * @param responsabiliSet
     * @param responsabile
     */
    public void saveReplicapermessi(Set<Responsabili> responsabiliSet, Responsabili responsabile);

    /**
     * Ritorna una lista di responsabile che sono amministratori
     * 
     * @param disablitati
     *            il parametro applica un ulteriore filtro alla ricerca; true : disabilitati false: abilitati null ;
     *            tutti
     * @return
     */
    public List<Responsabili> findAmministratori(Boolean disabilitati);

    /**
     * Ritorna una lista di amministratori software
     * 
     * @param disabilitati
     *            - ulteriore filtro alla ricerca; true : disabilitati false: abilitati null ; tutti
     * @param amministratori
     *            - ulteriore filtro che permette di escludere quelli che sono anche amministratori (0 non
     *            amministratori,1 amministratori,null tutti)
     * @param software
     *            - parametro che ci permette di filtrare gli amministratori software per un particolare software (null
     *            tutti i software)
     * @return
     */
    public List<Responsabili> findAmministratoriSoftware(Boolean disabilitati, String amministratori, String software);

    /**
     * Ritorna una lista di tutti gli operatori configurati (campo amministratore=0 e amministratorisoftware=0)
     * 
     * @param disabilitati
     *            - ulteriore filtro alla ricerca; true : disabilitati false: abilitati null ; tutti
     * @param software
     *            - parametro che ci permette di filtrare gli amministratori software per un particolare software (null
     *            tutti i software)
     * @return
     */
    public List<Responsabili> findOperatori(Boolean disabilitati, String software);

    /**
     * Il metodo trova tutti i responsabili che hanno l'operatore individuato da codoperatore e ORMHelper.getIdComune()
     * legati nel campo SCAD_OPERATORE
     * 
     * @param codoperatore
     * @return
     */
    public List<Responsabili> findOperatoriUsedInScadOperatore(Integer codoperatore);

    /**
     * Permette la modifica della password
     * 
     * @param responsabile
     * @param clearPassword
     * @param clearNewPassword
     * @param clearNewPasswordConfirmation
     */
    public void updatePassword(Responsabili responsabile, String clearPassword, String clearNewPassword, String clearNewPasswordConfirmation);

    /**
     * La funzione controlla se tra gli operatori censiti ce ne sia almeno uno con il flag flagBloccaOneri settato a
     * true
     * 
     * @return
     */
    public boolean isAbilitaBloccaOneri();

    public CodiceDescrizioneBean findDescrizioneById(Integer codiceOperatore);

    public Responsabili findByUserId(String userId);

    /**
     * metodo per l'inserimento di un nuovo permesso (se non è presente) per il menu ed il software specificati.
     * 
     * @param responsabile
     * @param codiceClmenu
     * @param codiceSoftware
     */
    public void insertPermesso(Responsabili responsabile, Integer codiceClmenu, String codiceSoftware);

    /**
     * metodo per la rimozione di un permesso per il menu ed il software specificati.
     * 
     * @param responsabile
     * @param codiceClmenu
     * @param codiceSoftware
     */
    public void deletePermesso(Responsabili responsabile, Integer codiceClmenu, String codiceSoftware);

    public Set<Responsabilisoftware> findListResponsabilisoftware(Responsabili responsabile);

    public Set<Responsabilicomuni> findListResponsabilicomuni(Responsabili responsabile);
}
