/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingException;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.File;
import java.util.List;

/**
 * @author francol
 * 
 */
public interface CartMappingService {

    /**
     * Restituisce la configurazione di base delle mappature VBG - CART valida per tutti i comuni. Tale configurazione
     * si trova definita nel file cart-mappings-standard0-bo.xml che si deve trovare nel classpath.
     * 
     * @return l'oggetto che contiene tutte le configurazioni delle mappature CART.
     */
    public CartMappingsConfig loadDefaultCartMappings() throws Exception;

    /**
     * Passando in input il path che punta al file di configurazione restituisce l'oggetto {@link CartMappingsConfig}
     * che contiene le definizioni delle mappature fra gli id semantici del CART e i dati dell'istanza nella base dati
     * di VBG.
     * 
     * @param cfgResPath
     *            path alla risorsa del classpayh che contiene il file di configurazione delle mappature
     * @return l'oggetto che contiene tutte le configurazioni delle mappature CART.
     */
    public CartMappingsConfig loadCartMappingsConfiguration(String cfgResPath) throws Exception;

    /**
     * Restituisce la configurazione delle mappature VBG - CART valida per l'id comune alias passato come argomento.
     * Tale configurazione viene creata dal sistema con la seguente procedura:
     * <ol>
     * <li>viene caricata la configurazione di default valida per tutti i comuni</li>
     * <li>se nel classpath è presente il file [idComuneAlias]-cart-mappings-standard0-bo.xml allora vengono caricate
     * anche le mappature definite in questo file che vanno ad aggiungersi a quelle di default oppure le ridefiniscono
     * nei casi in cui facciano riferimento allo stesso id semantico.</li>
     * </ol>
     * 
     * @param idComuneAlias
     *            alias del comune per cui si vuole caricare la configurazione delle mappature
     * @return l'oggetto che contiene tutte le configurazioni delle mappature CART per l'id comune alias specificato.
     */
    public CartMappingsConfig loadCartMappingsForComune(String idComuneAlias) throws Exception;

    /**
     * Passando in input un riferimento ad un istanza di VBG il metodo restutisce un'oggetto {@link DatiDomandaCart} che
     * è popolato recuperando i dati dall'istanza passata come primo argomento utilizzando le mappature specifiche per
     * il comune dell'istanza.
     * 
     * @param codiceIstanza
     * @param cartMappings
     * @param modulistica
     * @param mappingErrors
     *            passare una lista vuota, all'uscita dal metodo sarà riempita con l'elenco degli errori riscontrati
     * @param listaDocumenti
     * @return
     */
    public DatiDomandaCart popolaDomandaCart(Integer codiceIstanza, ModulisticaContentType modulistica, List<CartMappingException> mappingErrors,
	    List<DocumentiType> listaDocumenti) throws Exception;

    /**
     * Il metodo popola gli id semantici relativi al quadro ALLEGATI STD_0 all'interno dell'oggetto
     * {@link DatiDomandaCart} passato come secondo argomento. I dati sugli allegati sono recuperati interrogando i
     * {@link Documentiistanza} e le {@link Istanzeallegati} dell'istanza passata come primo argomento
     * 
     * @param istanze
     * @param datiDomanda
     * @param listaDocumenti
     * @throws Exception
     */
    public void popolaAllegatiDomandaCart(Istanze istanze, DatiDomandaCart datiDomanda, List<DocumentiType> listaDocumenti) throws Exception;

    /**
     * In questo metodo: viene popolata la sezione IndiceZip del messaggio di presentazione domanda passato come
     * argomento con i riferimenti ai files presenti nei dati della domanda passata come primo argomento.
     * 
     * @param datiDomanda
     * @param messaggioPresentazione
     * @throws Exception
     */
    public File[] completaMessaggioPresentazioneDomanda(DatiDomandaCart datiDomanda, PresentazioneDomanda messaggioPresentazione) throws Exception;

    /**
     * Salva gli allegati CART passati come secondo argomento come allegati dell'istanza il cui codice è specificato nel
     * primo argomento. se esistono già allegati dell'istanza con lo stesso nome file allora viene solo aggiornato il
     * contenuto binario degli allegati già esistenti. Restituisce una lista di {@link DocumentiistanzaDTO} che contiene
     * tutti i documenti inseriti o aggiornati nel DB.
     * 
     * @param codiceIstanza
     * @param files
     * @throws Exception
     */
    public List<DocumentiistanzaDTO> salvaAllegatiCart(Integer codiceIstanza, File[] files) throws Exception;
}
