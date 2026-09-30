package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CfRichiedentiBean;

/**
 * 
 * @author francescop
 */
public interface IstanzerichiedentiService extends BaseService<Istanzerichiedenti, PkId> {

    public List<Istanzerichiedenti> findByIstanza(Istanze istanza);

    public List<Istanzerichiedenti> findByIstanza(Integer codiceIstanza);

    /**
     * Il metodo copia i soggetti collegati legati all'istanza sorgente all'istanza destinatario. Il metodo prima di
     * replicare i soggetti collegati nell'istanza destinatario effettuerà un controllo sul destinatario in modo da non
     * duplicare i sogetti collegati.
     * 
     * @param istanzaSorgente
     * @param istanzaDestinatario
     */
    public void copiaIstanzeRichiedenti(Istanze istanzaSorgente, Istanze istanzaDestinatario);

    /**
     * Torna la lista delle Istanzerichiedenti di un Richiedente
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzerichiedenti> findByAnagrafeRichiedente(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Istanzerichiedenti di un'AnagrafeCollegata
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzerichiedenti> findByAnagrafeAnagrafeCollegata(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle Istanzerichiedenti di un procuratore
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzerichiedenti> findByAnagrafeProcuratore(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista delle istanze per cui l'anagrafe storico è AnagrafeColl o richiedente o Procuratore
     * 
     * @param anagrafeStorico
     * @return
     */
    public List<Istanzerichiedenti> findAnagrafeCollOrRichiedenteOrProcuratoreStorico(Anagrafestorico anagrafeStorico);

    public List<Istanzerichiedenti> findByTiposoggetto(Integer codice, int firstResult, int maxResult);

    /**
     * Ritorna il numero di soggetti collegati all'istanza passata.
     * 
     * @param codiceIstanza
     * @return
     */
    public int countSoggettiCollegatiByIstanza(Integer codiceIstanza);

    /**
     * Ritorna una lista di istanze richiedenti per l'istanza e tipo soggetti passati
     * 
     * @param codice
     * @param codice2
     * @return
     */
    public List<Istanzerichiedenti> findByIstanzaAndTiposoggetto(Integer codice, Integer codice2);

    /**
     * Il metodo verifica i soggetti collegati presenti nell'istanza e quelli richiesti presenti nella configurazione
     * dell' albero (il figlio eredita anche quelli del pradre)
     * 
     * @param alberoprocHelper
     * @return
     */
    public void checkTipisoggettoRichiesti(AlberoprocHelper alberoprocHelper, Integer codiceIstanza);

    /**
     * Ritorna una lista di istanze richiedenti per l'istanza passata che come tipo oggetto hanno configurato il campo
     * flagMostraDettIstanza==true
     * 
     * @param codice
     * @return
     */
    public List<Istanzerichiedenti> findByIstanzaAndTiposoggeettoMostraInIstanza(Integer codice);

    public List<CfRichiedentiBean> findBeanByCodiceIstanza(Integer codiceIstanza);
}
