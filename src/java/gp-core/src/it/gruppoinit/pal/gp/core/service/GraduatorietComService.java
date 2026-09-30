package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.GraduatorietComDAO;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietComHelper;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface GraduatorietComService extends BaseService<GraduatorietCom, PkId> {

    /**
     * @see GraduatorietComDAO#findAll(Integer, Integer)
     */
    public List<GraduatorietCom> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista paginata di GraduatorietCom filtrati per idcomune e GraduatoriaT
     * 
     * @param codiceGraduatoriaT
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<GraduatorietCom> findByGraduatoriT(Integer codiceGraduatoriaT, Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista paginata di GraduatorietComHelper filtrati per idcomune e GraduatoriaT
     * 
     * @param codiceGraduatoriaT
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<GraduatorietComHelper> findGraduatorietComHelperByGraduatoriT(Integer codiceGraduatoriaT, Integer firstResult, Integer maxResult);

    /**
     * <pre>
     * Inserisce la testata GraduatorietCom e associate ad essa N record del dettaglio GraduatoriedCom secondo la logica:
     * 1. Recupera la lista graduatoried che hanno il referimento a GraduatorietCom.graduatorit
     * 2. Verranno selezionate solo quelle che rispettano i filtri impostati sulla testata GraduatorietCom:
     *    a. Riservata a: Tutti i soggetti delle graduatoria (non considera se hanno o no una concessione),Solamente i titolari di concessione,
     *       solamente ai non titolari di concessione
     *    b. dalla posizione alla posizione (valore compreso graduatoried.posizione)
     *    c. le schede dell'istanza devono rispettare le condizioni del campo  GraduatorietCom.dyn2filtri
     * 3. Per ogni graduatoried recuperta andrò ad inserire 
     *    a.Viene fatto il movimento “Movimento da generare” e si salva il codice in GRADUATORIED_COM.CODICEMOVIMENTO, in caso di eccezioni deve essere generato un evento nell’istanza legato ad una categoria che identifica le GRADUATORIE
     *    b.Se valorizzato “Allegato da generare”, si genera  la lettera tipo e si allega al movimento andando a scrivere in GRADUATORIED_COM.IDALLEGATO l’id del documento generato, in caso di eccezioni deve essere generato un evento nel movimento legato ad una categoria che identifica le GRADUATORIE
     *    c.Se valorizzato “Mail da inviare”, si genera una mail legata al movimento generato e si salva l’id della mail in GRADUATORIED_COM.IDMAIL, in caso di eccezioni deve essere generato un evento nel movimento legato ad una categoria che identifica le GRADUATORIE
     * 
     * @param entity
     * @param dinamicaFilter
     * </pre>
     */
    public void insertComunicazioni(GraduatorietCom entity, SchedaDinamicaFilter dinamicaFilter);

    public String findListaFirmatariToHtml(Integer codiceGraduatoriatcom);

    public List<Responsabili> findResponsabiliFirmatari(String listaFirmatari);

    public int countByGraduatoriet(Integer graduatoriaid);
}
