package it.gruppoinit.pal.gp.core.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public interface MercatipresenzeTDAO extends BaseDAO<MercatipresenzeT, PkId> {

    /**
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return una lista di mercatipresenze_t filtrate per mercati,mercatiuso,anno
     */
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Metodo che verifica se una data corrisponde ad un giorno del calendario mercato.
     * 
     * @param list
     * @param date
     * @return ritorna un boolean: false: giorno non presente true: giorno presente
     * 
     */
    public boolean findDay(List<MercatipresenzeT> list, Calendar date);

    /**
     * 
     * @param date
     * @param mercati
     * @param mercatiUso
     * @return una lista di mercatipresenze_t filtrato per mercato,mercatiuso,dataregistrazione
     */
    public MercatipresenzeT findByDataregistrazioneAndMercatoAndMercatoUso(Calendar date, Mercati mercati, MercatiUso mercatiUso);

    /**
     * 
     * @param mercati
     * @return ritorna una lista di mercatipresenze_t (una tupla per ogni mercato,mercatouso,anno)
     */
    public List<MercatipresenzeT> findByMercatoAndGroupByAnnoAndMercatoUso(Mercati mercati);

    /**
     * @param mercati
     *            può essere nullo
     * @param anno
     *            può essere nullo
     * @return Lista di mercatiPresenzeT ricercate per anno e mercato. Raggruppate per mercato uso.
     */
    public List<MercatipresenzeT> findByMercatoAndAnnoGroupByMercatoUso(Mercati mercati, Integer anno);

    /**
     * Metodo per verificare se un mercato può essere storicizzabile.
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return boolean = true se tutti i giorni per un mercato, mercato uso e anno sono stati gestiti(FLAG_PRESENZE=1)
     *         boolean = false se almeno un giorno del mercato non è stato gestito(FLAG_PRESENZE=0)
     */
    public boolean verificaGestionePresenze(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Metodo per verificare se un mercato è già stato storicizzato
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return boolean = true se il mercato è già storicizzato(FLAG_PRESENZE_ARCHIVIO=1) boolean = false se il mercato
     *         non è stato storicizzato(FLAG_PRESENZE_ARCHIVIO=0) per tutti i giorni del mercato
     */
    public boolean verificaMercatoStoricizzato(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Metodo per determinare la lista delle presenze per una fiera.
     * 
     * @param mercati
     * @param mercatiUso
     * @param anno
     * @return la lista conterrà un solo elemento che rappresenta l'ultimo giorno di presenza
     */
    public List<MercatipresenzeT> findMercatipresenzetFiereByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno);

    /**
     * Metodo per la ricerca dei mercati filtrati per anno e/o per mercati
     * 
     * @param mercati
     * @param anno
     * @return
     */
    public List<Riepilogomercato> findByMercatoOrAnnoGroupByMercatoUso(Mercati mercati, Integer anno);

    /**
     * Metodo che trova tutti gli anni per cui ci sono state presenze almeno in un mercato configurato
     * 
     * @return una lista di MercatipresenzeT con il campo anno popolato
     */
    public List<MercatipresenzeT> findAnniMercatiPresenti();

    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUsoFiere(Mercati mercati, MercatiUso mercatiUso);

    /**
     * ricerca l'ultimo giorno della fiera per ogni anno o per l'anno specificato
     * 
     * @param mercato
     * @param uso
     * @param anno
     * @return
     */
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno, boolean groupByDataRegistrazione);

    public List<Integer> findAnniDaConsolidare(Integer codiceMercato);

    public List<MercatipresenzeT> findByMercatoAndAnnoAndResponsabileGroupByMercatoUso(Mercati mercati, Integer anno, Integer codiceResponsabile);

    public Date findDataGiornataPrecedenteMercato(Integer idGiornata);

    public List<IdentificativoDescrizioneBean> checkPosizioniDebitorieCreatePerConcessionari(Date giornataDaControllare);

    /**
     * Il metodo segna la giornata identificata con idGiornata come nulla cioè la giornata non verrà considerata nel
     * calcolo della bollettazione
     * 
     * @param idGiornata
     * @param note
     * @return
     */
    boolean segnaGiornataNulla(MercatipresenzeT mercatipresenzeT);

    /**
     * Il metodo segna la giornata identificata con idGiornata come nulla cioè la giornata verrà considerata nel calcolo
     * della bollettazione
     * 
     * @param idGiornata
     * @param note
     * @return
     */
    boolean segnaGiornataNonNulla(MercatipresenzeT mercatipresenzeT);
}
