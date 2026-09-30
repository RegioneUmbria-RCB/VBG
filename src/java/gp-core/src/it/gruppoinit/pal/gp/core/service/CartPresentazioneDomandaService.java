package it.gruppoinit.pal.gp.core.service;

import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.PropertyUtilsBean;

public interface CartPresentazioneDomandaService {

    /**
     * Riceve come argomento la struttura dati {@link DatiDomandaCart} che contiene i dati trasmessi dalla modulistica
     * online e la trasforma in un file MDA che corrisponde al nome e al percorso passati nel secondo argomento. Nel
     * file MDA vengono scritti i valori di tutti gli id semantici contenuti nell'oggetto {@link DatiDomandaCart}.
     * 
     * @param datiDomanda
     * @return
     * @throws JAXBException
     */
    public void scriviFileMDADaDatiDomandaPresentata(DatiDomandaCart datiDomanda, File outputFile) throws Exception;

    /**
     * Riceve come argomento la struttura dati {@link DatiDomandaCart} che contiene i dati trasmessi dalla modulistica
     * online e la trasforma in un file MDA che corrisponde al nome e al percorso passati nel secondo argomento. Nel
     * file MDA vengono scritti solo i valori degli id semantici che appartengono al modulo specificato dal terzo
     * argomento. Se riferimentoModulo vale null allora viene prodotto il file MDA dell'intera domanda con tutti i
     * moduli presenti.
     * 
     * @param datiDomanda
     * @return
     * @throws JAXBException
     */
    public void scriviFileMDADaDatiDomandaPresentata(DatiDomandaCart datiDomanda, File outputFile, String riferimentoModulo) throws Exception;

    /*
     * Il metodo restituisce una istanza di classe {@link DatiDomandaCart} che contiene gli id semantici e i relativi
     * valori letti dal file MDA passato come argomento.
     * 
     * @param inputFile
     * @return
     * 
     */
    //public DatiDomandaCart leggiDatiDomandaPresentataDaFileMDA(File inputFile);
    /**
     * Il metodo crea e restituisce una nuova istanza di {@link PresentazioneDomanda} popolata con i dati presenti
     * nell'oggetto {@link DatiDomandaCart} passato come primo argomento. Il secondo argomento rappresenta la mappatura
     * degli id semantici sui nomi delle proprietà dell'oggetto {@link PresentazioneDomanda} che si va a popolare:
     * ciascun id semantico deve essere associato al nome della proprietà espresso con la notazione prevista dallAPI di
     * apache-commons.beanutils. Vedi javadoc della classe {@link PropertyUtilsBean}
     * 
     * @param dati
     * @param mappaturaIdSemantici
     * @return
     */
    public PresentazioneDomanda creaOggettoPresentazioneDomanda(DatiDomandaCart dati, Properties mappaturaIdSemantici, String tipoAttivita,
	    String std2_std0, Date dataDomanda) throws Exception;

    /**
     * Invia il messaggio di presentazione domanda che viene passato come primo argomento. Il secondo argomento deve
     * essere un riferimento al pacchetto zip che contiene gli allegati della domanda (precedentemente creato). Nel
     * terzo argomento boolean passare true se si vuole che l'allegato zip sia incluso nel corpo del messaggio nel campo
     * Base64Biniary oppure passare false se si vuole che il pacchetto zip sia trasmesso come attachment del messaggio
     * SOAP.
     * 
     * @param datiDomanda
     * @param zipAllegati
     * @param encodeAttachmentAs64Binary
     */
    public void inviaMessaggioPresentazioneDomanda(PresentazioneDomanda datiDomanda, File zipAllegati, boolean encodeAttachmentAs64Binary)
	    throws Exception;

    /**
     * Genera l'id domanda secondo DPR 160: <cf_richiedente>-<DDMMYYYY>-<HHmm>. L'oggetto datiPresentazione passato come
     * argomento deve essere già popolato con i dati del richiedente
     * 
     * @param datiPresentazione
     * @return
     */
    //public String generaIdDomanda(PresentazioneDomanda datiDomanda);
    /**
     * Scarica tutti gli allegati utente associati alla domanda in una directory temporanea in cui si trovano già glia
     * allegati del messaggio di presentazione già generati dalla procedura. Il metodo si occupa anche di valorizzare la
     * sezione <indiceZip> del messaggio di presentazione recuparando anche i codici associati ai files se presenti
     * negli specifici id semantici. Il metodo inoltre genera anche il file che contiene il messaggio stesso di
     * presentazione domanda sia in XML che in PDF, questi files vengono anch'essi inseriti nella sezione
     * &lt;indiceZip&gt; del messaggio di presentazione stesso. Infine il metodo analizza anche tutti gli allegati
     * utente e ne valida la firma digitale per quelli per cui era prevista; il metodo restituuisce una {@link List} di
     * {@link AllegatoDaFirmare} in cui sono presenti tutti glil allegati utente per cui era prevista la firma digitale
     * ma che non ce l'hanno oppure non ce l'hanno valida. I files restituiti in questa lista saranno ripresentati
     * all'utente nell'ultima pagina in cui sarà richiesto di apporre la firma digitale su tutti quiei files per cui é
     * richiesta.
     * 
     * @param dati
     * @param domanda
     */
    public void gestisciAllegatiDomanda(DatiDomandaCart dati, PresentazioneDomanda domanda, String stdRef);

    /**
     * Carica le mappature fra gli attributi della copertina del messaggio di presentazione domanda e gli id semantici
     * presenti nei dati della domanda stessa. Le mappature sono caricate dal file mapping_id_semantici.properties
     * 
     * @return
     * @throws IOException
     */
    public Properties getMappaturaIdSemantici() throws IOException;

    /**
     * Cerca fra i documenti dell'istanza per individuare il documento che contiene il messaggio XML di presentazione
     * della domanda CART. Il file viene identificato esclusivamente dal fatto che il suo nome termina per .SUAP.XML
     * 
     * @param codiceIstanza
     *            id dell'istanza in cui cercare l'allegato
     * @return
     */
    public Documentiistanza findDocumentoIstanzaMessaggioPresentazioneDomanda(Istanze istanza);

    /**
     * Restituice un'istanza di {@link PresentazioneDomanda} creata facendo l'unmarshall del contenuto XML dell'oggetto
     * passato come argomento
     * 
     * @param codiceOggetto
     * @return
     */
    public PresentazioneDomanda getMessaggioPresentazioneDomanda(Integer codiceOggetto);

    /**
     * Restituisce una lista di stringhe che contiene i nomi degli allegati mancanti per la notifica CART ad un ente
     * terzo relativamente all'istanza avente codice 'codiceIstanza'. Il metodo recupera la lista degli allegati
     * previsti leggendo i dati contenuti nella proprietà indicezip dell'oggetto {@link PresentazioneDomanda} passato
     * come primo argomento e li confronta con i documenti dell'istanza.
     * 
     * @param pd
     * @param codiceIstanza
     * @param std_2_0
     * @param codiceEndoRegionale
     * @return
     */
    public List<String> getAllegatiMancantiPerNotificaEnteTerzo(PresentazioneDomanda pd, Integer codiceIstanza, String std_2_0,
	    String codiceEndoRegionale);

    /**
     * Il metodo popola i dati di una domanda CART fittizia (di tipo STANDARD_00_BO) recuperando i dati dall'istanza
     * passata come primo argomento secondo i criteri specificati nelle mappature passate come secondo argomento. Se la
     * domanda fittizia supera la validazione il metodo genera i file &lt;codice_pratica%gt;.MDA.XML,
     * &lt;codice_pratica%gt;.MDA.STANDARD_0.XML e &lt;codice_pratica%gt;.SUAP.XML che sono previsti per la notifica
     * CART ad un'ente terzo ma che non esistono per istanze non provenienti da domande CART. I files generati vengono
     * memorizzati nel DB come {@link Documentiistanza} associati all'istanza passata come argomento; I nuovi
     * {@link Documentiistanza} che sono stati creati vengono alla fine restituiti dal metodo.
     * 
     * @param codiceIstanza
     * @param listaDocumenti
     * @param codiceEndoRegionale
     * @return
     */
    public List<DocumentiistanzaDTO> generaDocumentiIstanzaperNotificaEnteTerzo(Integer codiceIstanza, CartMappingsConfig cartMappings,
	    ModulisticaContentType modulistica, List<DocumentiType> listaDocumenti);

    /**
     * Genera l'identificativo di una domanda CART a partire dai dati dell'istanza secondo lo schema:
     * &ltistanza.;richiedente.cf&gt;-&lt;istanza.data(ddMMyyyy-HHmm)&gt;
     * 
     * @param istanza
     * @return
     */
    public String generaIdentificativoPraticaCART(Istanze istanza);
}
