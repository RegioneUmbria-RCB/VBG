package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniDaMercato;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniStatisticheMercati;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @author francescop
 * 
 */
public interface RegistrazioniDAO extends BaseDAO<Registrazioni, PkId> {

    public Integer findMaxProgressivo(String year);

    public List<Registrazioni> findByAnagrafe(Anagrafe anagrafe);

    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter);

    public List<RegistrazioniStatisticheMercati> findRegByMercato(Integer idMercato);

    public List<RegistrazioniDaMercato> findRegByMercatoForCausale(short anno, Integer idMercato, MercatiUso uso);

    public List<Registrazioni> findByMercatoUsoData(Mercati mercato, MercatiUso mercatoUso, Date dataRegistrazione,
	    RegistrazioniMercatoEnum registrazioniMercatoEnum);

    /**
     * @gianpaolot
     * @param registrazioniFilter
     * @return ritorna una lista di Registrazioni filtrata per i parametri inseriti nella maschera di ricerca
     *         (registrazioni/registrazioniSearch.jsp)
     */
    public List<Registrazioni> findByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter);

    public enum RegistrazioniMercatoEnum {
	ALL, SPUNTISTI, CONCESSIONARI
    }

    /**
     * @gianpaolot
     * @param mercati
     * @param mercatiUso
     * @return ritorna una lista di posteggio filtrata per mercati e mercati uso Posteggio è un bean che contiene la
     *         situazione contabile di un posteggio raggruppata per : anno conto
     * 
     */
    public List<Posteggio> findSituazioneContabileByMercatoAndPosteggio(Mercati mercati, MercatiUso mercatiUso);

    /**
     * @gianpaolot
     * @param mercati
     * @param mercatiUso
     *            se mercatiUso è nullo allora torna la situazione contabile per tutti gli Usi del mercato
     * @return Un vettore di list<Posteggio> il vettore avrà dimensione pari al numero di mercati uso del mercato
     *         passato
     * 
     */
    public Vector<List<Posteggio>> findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(Mercati mercati, MercatiUso mercatiUso);

    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity);

    /**
     * metodo per l'ajax request per recuperare tutti i mercati per cui una anagrafica ha una registrazione.
     * 
     * @param anagrafe
     * @return
     */
    public List<Registrazioni> findMercatiByRegistrazioniAndAnagrafe(Anagrafe anagrafe);

    /**
     * metodo per l'ajax request per recuperare tutte le anagrafiche per una manifestazioni.
     * 
     * @param anagrafe
     * @return
     */
    public List<Registrazioni> findAnagrafeByRegistrazioniAndMercati(Mercati mercati);

    public List<Registrazioni> findRegistrazioniByAnno(short anno);

    public List<Integer> findCodiciByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter);
}
