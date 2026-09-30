package it.gruppoinit.pal.gp.pay.service;

import javax.activation.DataHandler;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;

public interface PayDocumentiService extends BaseService<PayDocumenti, PkId> {

    /**
     * metodo che inserisce un nuovo record in payDocumenti inizializzandone il nome in base a logiche condivise nel
     * nodo pagamenti (se non specificato) e imposta la FK nella posizione debitoria in base al tipo di documento
     * specificato se tipoDoc != null. Si tratta solo di un metodo di utilità e i dati del documento devono essere
     * persistiti a carico del metodo chiamante. Il metodo condivide la transazione in cui viene invocato
     * 
     * @param posDeb
     * @param tipoDoc
     * @param nomeDoc
     * @return
     */
    public PayDocumenti creaDocumentoPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, String docName);

    /**
     * metodo che inserisce un nuovo record in payDocumenti inizializzandone il nome in base a logiche condivise nel
     * nodo pagamenti (se non specificato) e imposta la FK nella posizione debitoria in base al tipo di documento
     * specificato se tipoDoc != null. Se viene specificato il data handler anche i bytes del documento saranno
     * persistiti (su FS o BLOB a seconda della configurazione del connettore) Il metodo condivide la transazione in cui
     * viene invocato
     * 
     * @param posDeb
     * @param tipoDoc
     * @param nomeDoc
     * @return
     */
    public PayDocumenti salvaDocumentoPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, DataHandler docData,
	    String docName);

    /**
     * metodo che inserisce un nuovo record in payDocumenti inizializzandone il nome in base a logiche condivise nel
     * nodo pagamenti (se non specificato) e imposta la FK nella posizione debitoria in base al tipo di documento
     * specificato se tipoDoc != null. Se viene specificato il data handler anche i bytes del documento saranno
     * persistiti (su FS o BLOB a seconda della configurazione del connettore) Il metodo lavora in una transazione
     * separata (le modifiche sono committate all'uscita del metodo)
     * 
     * @param posDeb
     * @param tipoDoc
     * @param nomeDoc
     * @return
     */
    public PayDocumenti salvaDocumentoPerPosizioneDebitoriaTrans(PayPosizioniDebitorie posDeb, TipoDocumentoType tipoDoc, DataHandler docData,
	    String docName);

    /**
     * il metodo popola gli attributi dell'oggetto datiFattura che viene passato per riferimento. Se nell'oggetto
     * passato in input mancano le righe di dettaglio della fattura, le recupera dal DB leggendo i PAY_DETTAGLIO_IMPORTI
     * associati alla posizione debitoria. Se non è impostata la data di scadenza della fattura la imposta con la data
     * di scadenza della posizione debitoria. I dati del soggetto debitore non verngono recuperati in questa sede ma
     * saranno recuperati dirattamente dal record di PAY_SOGGETTI_DEBITORI associato alla posizione nel connettore che
     * si occupa di generare la fattura
     * 
     * @param datiFattura
     * @throws PayException
     */
    /**
     * Associa un PayDocumenti già esistente ad una posizione debitoria.
     * 
     * @param pos
     * @param doc
     */
    public void associaDocumentoAPosizioneDebitoria(PayPosizioniDebitorie pos, PayDocumenti doc, TipoDocumentoType tipoDoc);

    /**
     * Associa un PayDocumenti già esistente ad una posizione debitoria in una transazione separata.
     * 
     * @param pos
     * @param doc
     */
    public void associaDocumentoAPosizioneDebitoriaTrans(PayPosizioniDebitorie pos, PayDocumenti doc, TipoDocumentoType tipoDoc);

    public void populateDatiFattura(DatiFatturaType datiFattura) throws PayException;

    /**
     * Il metodo crea un nuovo oggetto che verrà salvato nel riferimento della ricevutaXML della posizione debitoria.
     * 
     * @param posDeb:
     *            Posizione debitoria
     * @param docData:
     *            DataHandler che contiene l'array di byte della ricevuta XML
     * @return
     */
    public PayDocumenti salvaRicevutaXMLPerPosizioneDebitoria(PayPosizioniDebitorie posDeb, DataHandler docData);
}
