/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoDocumentoPosizioneDebitoriaType;

/**
 * @author francol
 *
 */
public interface PayRichiesteService extends BaseService<PayRichieste, PkId> {

    /**
     * Il metodo deve essere invocato in tutti quei servizi che gestiscono chiamate in ingresso per la richiesta di
     * operazioni sulle posizioni che implicano la successiva invocazione di servizi esterni esposti dal PSP. Un recod
     * di PAY_RICHIESTE deve essere registrato sia che la chiamata al servizio esterno sia prevista immediatamente in
     * modalità sincrona sia che venga schedulata successivamente in modalità asincrona. Il metodo inserisce una nuova
     * richiesta in PAY_RICHIESTE solo se non esiste una richiesta non conclusa per la stessa posizione debitoria e per
     * lo stesso tipo di richiesta, se tale richiesta esiste viene restituita senza effettuaremodifiche al DB.
     * 
     * @param payPos
     * @param evt
     * @return
     * @throws PayException
     */
    public PayRichieste registraRichiestaPerPosizioneDebitoria(PayPosizioniDebitorie payPos, TipiEvento tipoRichiesta) throws PayException;

    /**
     * crea la richiesta in PAY_RICHIESTE per la posizione debitoria passata in Input e committa in una transazione
     * separata
     * 
     * @param payPos
     * @param evt
     * @param tipoRichiesta
     * @return
     * @throws PayException
     */
    public PayRichieste registraRichiestaPerPosizioneDebitoriaTrans(PayPosizioniDebitorie payPos, TipiEvento tipoRichiesta) throws PayException;

    /**
     * Il metodo deve essere invocato per aggiornare PAY_RICHIESTE in tutti quei servizi che gestiscono chiamate in
     * ingresso per la richiesta di operazioni sulle posizioni. Deve essere invocato dopo aver invocato i servizi
     * esterni tramite i metodi del connettore. Il metodo incrementa di 1 il N_CHIAMATE e memorizza la data conclusione
     * a SYSDATE se l'argomento boolean vale true.
     * 
     * @param payRich
     *            richiesta da aggiornare
     * @param conclusa
     *            passando true viene impostata la data di completamento della richiesta
     */
    public void aggiornaEsitoRichiesta(PayRichieste payRich, boolean conclusa);

    /**
     * aggiorna l'esito della richiesta dopo la chiamata al connettore in una transazione separata
     * 
     * @param payRich
     * @param conclusa
     * @see #aggiornaEsitoRichiesta(PayRichieste, boolean)
     */
    public void aggiornaEsitoRichiestaTrans(PayRichieste payRich, boolean conclusa);

    /**
     * imposta la richiesta pendente come completata senza incrementare il numero di chiamate.
     * 
     * @param payRich
     * @param conclusa
     * @see #aggiornaEsitoRichiesta(PayRichieste, boolean)
     */
    public void annullaRichiestaTrans(PayRichieste payRich);

    /**
     * restituisce la richiesta del tipo 'tipoRichiesta' collegata alla posizione debitoria 'posDeb'. Se ci sono più
     * richieste che soddisfano il filtro restituisce la più recente. Se non ce ne sono restituisce null. Il parametro
     * Boolean soloApertaChiusa se diverso da null serve per specificare un filtro sullo stato aperta/chiusa della
     * richiesta cercata, se true vengono considerate solo quelle aperte, se false solo quelle chiuse.
     * 
     * @param posDeb
     * @param tipoRichiesta
     * @return
     */
    public PayRichieste getRichiestaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta, Boolean soloApertaChiusa);

    /**
     * restituisce la richiesta chiusa (DATA_COMPLETAMENTO IS NOT NULL) del tipo 'tipoRichiesta' collegata alla
     * posizione debitoria 'posDeb'. Se ci sono più richieste che soddisfano il filtro restituisce la più recente. Se
     * non ce ne sono restituisce null.
     * 
     * @param posDeb
     * @param tipoRichiesta
     * @return
     */
    public PayRichieste getRichiestaChiusaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta);

    /**
     * restituisce la richiesta aperta (DATA_COMPLETAMENTO IS NULL) del tipo 'tipoRichiesta' collegata alla posizione
     * debitoria 'posDeb'. Se ci sono più richieste che soddisfano il filtro restituisce la più recente. Se non ce ne
     * sono restituisce null.
     * 
     * @param posDeb
     * @param tipoRichiesta
     * @return
     */
    public PayRichieste getRichiestaApertaPerTipoEPosizione(PayPosizioniDebitorie posDeb, TipiEvento tipoRichiesta);

    /**
     * restituisce una lista di richieste non chiuse (DATA_COMPLETAMENTO IS NOT NULL) del tipo 'tipoRichiesta' associate
     * al profilo ente passato in input.
     * 
     * @param ente
     * @param tipoRichiesta
     * @return
     */
    public List<PayRichieste> getRichiesteApertePerTipoEProfiloEnte(PayProfiliEntiCreditori ente, TipiEvento tipoRichiesta);

    /**
     * crea la richiesta in PAY_RICHIESTE per la generazione fattura della posizione debitoria passata in Input e
     * registra in FK_OGGETTO_FATTURA la richiesta di generazione serializzata in XML al fine di poter ecuperare i dati
     * della gfattura nel caso in cui la richiesta sia gestita dallo scheduler. tutte le operazioni di scrittura nel DB
     * sono committate in una transazione separata.
     * 
     * @param payPos
     * @param evt
     * @param tipoRichiesta
     * @return
     * @throws PayException
     */
    public PayRichieste registraRichiestaGenerazioneFatturaTrans(PayPosizioniDebitorie payPos, DatiFatturaType datiFattura, TipiEvento tipoRichiesta)
	    throws PayException;

    /**
     * Il metodo gestisce e registra l'esito della richiesta relativa alla generazione di un documento collegato alla
     * posizione debitoria (fattura, avviso o ricevuta). Se l'esito è positivo il metodo scrive anche il documento in
     * PAY_DOCUMENTI e imposta i campi data associati nella posizione debitoria (DATA_REAZIONE_FATTURA,
     * DATA_INVIO_AVVISO)
     */
    public void registraEsitoRichiestaDocumentoTrans(EsitoDocumentoPosizioneDebitoriaType esitoDoc, PayPosizioniDebitorie posDeb) throws PayException;

    /*
     * Il metodo crea la richiesta in PAY_RICHIESTE per la notifica del cambio di stato della posizione debitoria passata in input; inoltre genera anche la riga necessara in PAY_IO_EVENTI
     * @param payPos
     */
    public PayRichieste registraRichiestaNotificaCambioStato(PayPosizioniDebitorie payPos) throws PayException;
}
