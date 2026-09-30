package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoCart;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.helper.CartControlloAllegatiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartFileCopyInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DomandeFrontOfficeService {

    /**
     * Restituisce un'istanza di {@link DatiDomandaCart} che rappresenta lo stato della domanda in corso
     * deserializzandola dal campo OggettoCart di {@link FoDomande} (o di {@link FoArjDomande} de si tratta dell'area
     * riservata Java)
     * 
     * @param idDomandaFo
     * @param isARJ
     * @return DatiDomandaCart
     */
    public DatiDomandaCart getDatiDomandaCart(Integer idDomandaFo, boolean isARJ);

    /**
     * Salva l'oggetto {@link DatiDomandaCart} passato come argomento nelle tabelle del FO ({@link FoDomande} se AR.NET
     * o {@link FoArjDomande} se ARJ)
     * 
     * @param datiDomanda
     * @param isARJ
     * @return void
     */
    public void aggiornaDatiDomandaCart(DatiDomandaCart datiDomanda, boolean isARJ);

    /**
     * Aggiorna lo stato della domanda impostatndola come presentata alla data di ora, imposta anche il codice domanda
     * passato come argomento.
     * 
     * @param idDomandaFo
     * @param codiceDomanda
     * @param isARJ
     * @param zipFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    public FoDomande impostaDomandaPresentata(Integer idDomandaFo, String codiceDomanda, Date dataPresentazione, String idegov, File zipFile)
	    throws FileNotFoundException, IOException;

    /**
     * Recupera il contenuto BLOB degli allegati della domada front office avente come id il valore passato come primo
     * argomento. Gli allegati vengono copiati nella directory passata coome secondo argomento.
     * 
     * @param idDomandaFo
     * @param downloadDir
     */
    public List<CartFileCopyInfo> downloadAllegatiDomanda(DatiDomandaCart datiDomanda, File downloadDir);

    /**
     * Effettua la cancellazione dal database dell'allegato utente memorizzato nella tabella oggetti con l'id idOggetto
     * ed associato alla domanda front-office avente id idDomandaFo. Aggiorna anche l'xml della domanda datiDomanda da
     * cui devono essere già stati eliminati i dati dell'allegato da cancellare.
     * 
     * @param idDomandaFo
     * @param idOggetto
     * @param isArj
     */
    public void cancellaAllegatoUtente(DatiDomandaCart datiDomanda, Integer idDomandaFo, Integer idOggetto, boolean isArj);

    /**
     * Effettua la cancellazione dell'allegato associato all'idOggetto passato come argomento. Viene effettuata la
     * cancellazione si da FO_DOMANDE_OGGETTI sia da OGGETTI.
     * 
     * @param idDomandaFo
     * @param idOggetto
     */
    public void deleteAllegatoDomanda(Integer idDomandaFo, Integer idOggetto);

    /**
     * Inserisce l'oggetto passato come secondo argomento nella tabella OGGETTI ed associa il nuovo oggetto inserito
     * alla domanda 'idDomandaFo' inserendo un nuovo record iin FO_DOMANDE_OGGETTI
     * 
     * @param idDomandaFo
     * @param idOggetto
     */
    public void insertAllegatoDomanda(Integer idDomandaFo, Oggetti newObj);

    /**
     * Aggiorna i campi BLOB e i nomi dei file nei record della tabella OGGETTI che corrispondono agli allegati utente
     * che sono stati uploadati. Al termine aggiorna anche l'oggetto CART in cui è memorizzato lo stato della domanda
     * 
     * @param datiDomanda
     * @param allegati
     */
    public void salvaAllegatiFirmati(DatiDomandaCart datiDomanda, List<AllegatoDaFirmare> allegati, boolean isArj);

    /**
     * Salva nella tabella oggetti il file rappresentato dall'oggetto passato come argomento. Se il file é già presente
     * nel DB viene aggiornato il campo BLOB con i dati binari, il nome del file e la sua dimensione senza creare nuovi
     * record nel DB.
     * 
     * @param datiDomanda
     * @param allegati
     * @param isArj
     * @return
     */
    public void salvaAllegatoDaFirmare(AllegatoDaFirmare all);

    /**
     * Salva nella tabella oggetti il file rappresentato dall'oggetto passato come argomento e lo associa come allegato
     * della domanda inserendo un record anche in FoDomandeOggetti. Se il file é già presente nel DB viene aggiornato il
     * campo BLOB con i dati binari, il nome del file e la sua dimensione solo se il terzo argomento vale true senza
     * creare nuovi record in Oggetti ne in FoDomandeOggetti.
     * 
     * @param all
     * @param idDomanda
     * @return
     */
    public void salvaAllegatoCart(AllegatoCart all, Integer idDomanda, boolean upadteOggetti);

    public CartControlloAllegatiHelper checkDimensioniAllegati(Integer idDomandaFo);

    public int contaAllegatiDomanda(Integer idDomandaFo);
    
    /**
     * Restituisce una List<Inventarioprocedimenti> che contiene tutti gli endo attivi per la domanda passata 
     * @param d
     * @return
     */
    public List<Inventarioprocedimenti> getEndoDomanda(DatiDomandaCart d);
}
