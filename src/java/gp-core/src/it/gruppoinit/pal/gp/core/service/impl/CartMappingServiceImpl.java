/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.eng.suap.xengine.model.modulistica.ItemType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.modulistica.TabellaType;
import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.DatiModulo;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.MappingIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CampoDinamico;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMapping;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingException;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ElaborationType;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueBuilder;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueMappings;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueProcessing;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.VbgValue;
import it.gruppoinit.pal.gp.core.domain.helper.CartMappingHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartMappingIndexedProperty;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.CartMappingService;
import it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Allegato;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Allegato.Destinatari;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.IndiceZip;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ZipFileType;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;
import it.init.sigepro.rte.types.DocumentiType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.xml.validation.Schema;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author francol
 * 
 */
@Service
public class CartMappingServiceImpl implements CartMappingService {

    private static final Logger log = LoggerFactory.getLogger(CartMappingServiceImpl.class);
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private EndoRegioneToscanaService endoRegioneToscanaService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private CartPresentazioneDomandaService cartPresentazioneDomandaService;
    @Autowired
    private ApplicationContext applicationContext;
    private HashMap<String, CartMappingsConfig> cartMappingsCache = new HashMap<String, CartMappingsConfig>();

    /*
     * (non-Javadoc)
     * 
     * @see it.gruppoinit.pal.gp.core.service.CartMappingService#
     * loadCartMappingsConfiguration(java.lang.String)
     */
    @Override
    public CartMappingsConfig loadCartMappingsConfiguration(String cfgPath) throws Exception {

	CartMappingsConfig retCfg = this.cartMappingsCache.get(cfgPath);
	if (retCfg == null) {
	    Resource mappingRes = this.applicationContext.getResource("classpath:" + cfgPath);
	    URL url = mappingRes.getURL();
	    InputStream is = url.openStream();
	    if (null != is) {
		byte[] cfgData = AttachmentsUtils.readBytesFromStream(is);
		String cfgString = new String(cfgData, FACCTConstants.DEFAULT_CHARSET);
		retCfg = (CartMappingsConfig) XmlUtils.unMarshallString(cfgString, CartMappingsConfig.class);
		this.cartMappingsCache.put(cfgPath, retCfg);
	    }
	}
	return retCfg;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartMappingService#loadDefaultCartMappings()
     */
    @Override
    public CartMappingsConfig loadDefaultCartMappings() throws Exception {

	return this.loadCartMappingsConfiguration(FACCTConstants.CFG_FILE_MAPPING_CART_VBG_STD0_BO);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartMappingService#loadCartMappingsForComune(java.lang.String)
     */
    @Override
    public CartMappingsConfig loadCartMappingsForComune(String idComuneAlias) throws Exception {

	CartMappingsConfig retCfg = loadDefaultCartMappings();
	if (StringUtils.isNotBlank(idComuneAlias)) {
	    StringBuilder sbPath = new StringBuilder(idComuneAlias).append("-").append(FACCTConstants.CFG_FILE_MAPPING_CART_VBG_STD0_BO);
	    Resource mappingRes = this.applicationContext.getResource("classpath:" + sbPath.toString());
	    if (mappingRes.exists()) {
		if (mappingRes.isReadable()) {
		    CartMappingsConfig comuneCfg = loadCartMappingsConfiguration(sbPath.toString());
		    if (comuneCfg != null) {
			CartMappingsConfig mergedCfg = new CartMappingsConfig();
			HashMap<String, CartMapping> mappingsMap = new HashMap<String, CartMapping>();
			for (CartMapping mapping : retCfg.getCartMappings()) {
			    mappingsMap.put(mapping.getIdSemantico(), mapping);
			}
			for (CartMapping mapping : comuneCfg.getCartMappings()) {
			    mappingsMap.put(mapping.getIdSemantico(), mapping);
			}
			mergedCfg.getCartMappings().addAll(mappingsMap.values());
			retCfg = mergedCfg;
		    }
		} else {
		    log.warn(
			    "loadCartMappingsForComune() - impossibile accedere in lettura al file {} per la configurazione delle mappature CART per l'id comune alias {}",
			    new Object[] { sbPath.toString(), idComuneAlias });
		}
	    }
	}
	return retCfg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.gruppoinit.pal.gp.core.service.CartMappingService#popolaDomandaCart
     * (it.gruppoinit.pal.gp.core.domain.Istanze,
     * it.gruppoinit.pal.gp.core.domain.cart.mapping.CartMappingsConfig)
     */
    @Override
    public DatiDomandaCart popolaDomandaCart(Integer codiceIstanza, ModulisticaContentType modulistica, List<CartMappingException> mappingErrors,
	    List<DocumentiType> listaDocumenti) throws Exception {

	DatiDomandaCart retData = null;
	// logica per il recupero dei dati dall'istanza o dai campi dinamici
	// secondo le mappature configurate.
	if (codiceIstanza != null) {
	    //String idcomune = ORMHelper.getIdcomune();
	    Istanze istanza = this.istanzeService.findById(new PkId(ORMHelper.getIdcomune(), codiceIstanza));
	    if (null != istanza) {
		CartMappingsConfig comuneMappings = this.loadCartMappingsForComune(ORMHelper.getIdcomuneAlias());
		if (comuneMappings != null) {
		    retData = new DatiDomandaCart();
		    PresentazioneDomandaCartCommand cmd = new PresentazioneDomandaCartCommand();
		    if (istanza.getComune() != null) {
			cmd.setCodicecomune(istanza.getComune().getCodicecomune());
		    }
		    // popolamento tipo azione.
		    // i tipi azione presenti nei nodi dell'albero devono essere
		    // gestiti in stp_endo_tipo2 anche per i nodi non CART.
		    AzioneType tipoAz = this.endoRegioneToscanaService.checkAzione(istanza.getAlberoproc().getId().getCodice());
		    cmd.setTipoAzione(tipoAz.name());
		    StpEndoTipo2 stp2 = this.endoRegioneToscanaService.getDatiAttivitaBdr(istanza.getAlberoproc().getId().getCodice());
		    if (stp2 != null) {
			cmd.setCodiceAttivitaBdr(stp2.getCodiceEndoRegionale());
			VwAlberoproc vAlberoProc = null;
			Alberoproc alberoProc = stp2.getAlberoproc();
			if (null != alberoProc) {
			    vAlberoProc = alberoProc.getVwAlberoproc();
			}
			if (null != vAlberoProc) {
			    cmd.setNomeAttivitaBdr(vAlberoProc.getScDescrizionepadre());
			}
		    }
		    retData.setDatiContestoDomanda(cmd);
		    // predispongo il modulo STANDARD_0 nei dati della domanda
		    // dando per scontato che sia il primo e unico modulo della
		    // modulistica passata
		    List<ModuloType> moduliCart = modulistica.getModulistica().getModulo();
		    DatiModulo moduloStd0 = new DatiModulo(moduliCart.get(0).getRiferimento().getCodiceModello());
		    TreeSet<DatiModulo> moduli = new TreeSet<DatiModulo>();
		    moduli.add(moduloStd0);
		    retData.setModuli(moduli);
		    List<CartMapping> mappings = comuneMappings.getCartMappings();
		    for (CartMapping mapping : mappings) {
			try {
			    ValoreIdSemantico vis = getValoreIdSemantico(mapping, istanza, modulistica);
			    if (null != vis) {
				moduloStd0.setValoreIdSemantico(mapping.getIdSemantico(), vis);
			    }
			} catch (CartMappingException e) {
			    mappingErrors.add(e);
			}
		    }
		    if (istanza.getTitolarelegale() == null) {
			// modifiche per standard 0 back quando azienda è nulla
			// ValoreIdSemantico vis = getValoreIdSemantico(mapping, istanza, modulistica);
			moduloStd0.setValoreIdSemantico("IMPRESA.FORMA_GIURIDICA", new ValoreIdSemantico("Persona fisica"));
			Anagrafe richiedente = istanza.getRichiedente();
			String denominazione = richiedente.getNominativo() + " " + richiedente.getNome();
			moduloStd0.setValoreIdSemantico("IMPRESA.DENOMINAZIONE", new ValoreIdSemantico(denominazione));
			String comune = "";
			String provincia = "";
			String cap = "";
			String indirizzo = "";
			if (richiedente.getComuneResidenza() != null) {
			    comune = richiedente.getComuneResidenza().getComune();
			    provincia = richiedente.getComuneResidenza().getSiglaprovincia();
			    cap = richiedente.getComuneResidenza().getCap();
			    indirizzo = richiedente.getIndirizzo();
			}
			if (StringUtils.isBlank(comune)) {
			    if (richiedente.getComunecorrispondenza() != null) {
				comune = richiedente.getComunecorrispondenza().getComune();
				provincia = richiedente.getComunecorrispondenza().getSiglaprovincia();
				cap = richiedente.getComunecorrispondenza().getCap();
				indirizzo = richiedente.getIndirizzocorrispondenza();
			    }
			}
			moduloStd0.setValoreIdSemantico("IMPRESA.SEDE_LEGALE.COMUNE", new ValoreIdSemantico(StringUtils.defaultString(comune, ".")));
			moduloStd0.setValoreIdSemantico("IMPRESA.SEDE_LEGALE.PROVINCIA",
				new ValoreIdSemantico(StringUtils.defaultString(provincia, ".")));
			moduloStd0.setValoreIdSemantico("IMPRESA.SEDE_LEGALE.CAP", new ValoreIdSemantico(StringUtils.defaultString(cap, ".")));
			moduloStd0.setValoreIdSemantico("IMPRESA.SEDE_LEGALE.VIA", new ValoreIdSemantico(StringUtils.defaultString(indirizzo, ".")));
			moduloStd0.setValoreIdSemantico("IMPRESA.SEDE_LEGALE.PRESENZA_CIVICO", new ValoreIdSemantico("Senza numero civico"));
			moduloStd0.setValoreIdSemantico("IMPRESA.TIPO_ISCRIZIONE", new ValoreIdSemantico(
				"Non tenuto all'iscrizione al Registro Imprese e/o al REA"));
			moduloStd0.setValoreIdSemantico("IMPRESA.COD_FISCALE", new ValoreIdSemantico(richiedente.getCodicefiscale()));
		    }
		    if (istanza.getTipisoggetto() == null) {
			moduloStd0.setValoreIdSemantico("RICHIEDENTE.IN_QUALITA_DI", new ValoreIdSemantico("Altro (erede, persona fisica, ecc)"));
			moduloStd0.setValoreIdSemantico("RICHIEDENTE.IN_QUALITA_DI.ALTRO.SPEC", new ValoreIdSemantico("Persona fisica"));
		    }
		} else {
		    String msg = MessageFormat.format("Nessuna mappatura CART configurata per il comune {0}.",
			    new Object[] { ORMHelper.getIdcomuneAlias() });
		    CartMappingException cme = new CartMappingException(msg, null);
		    mappingErrors.add(cme);
		    log.warn("popolaDomandaCart - {}", new Object[] { msg });
		    throw cme;
		}
	    } else {
		String msg = MessageFormat.format("impossibile individuare l''istanza avente codice {0} per il comune {1}.", new Object[] {
			codiceIstanza, ORMHelper.getIdcomune() });
		CartMappingException cme = new CartMappingException(msg, null);
		mappingErrors.add(cme);
		log.warn("popolaDomandaCart - {}", new Object[] { msg });
		throw cme;
	    }
	}
	return retData;
    }

    private ValoreIdSemantico getValoreIdSemantico(CartMapping mapping, Istanze istanza, ModulisticaContentType modulistica)
	    throws CartMappingException {

	ValoreIdSemantico vis = null;
	if (mapping != null && StringUtils.isNotBlank(mapping.getIdSemantico()) && mapping.getValue() != null) {
	    // recupero la definizione del campo nella modulistica
	    Deque<TabellaType> tables = new ArrayDeque<TabellaType>();
	    ItemType itemCart = CartModuloHelper.searchCampoByIdSemantico(modulistica, mapping.getIdSemantico(), tables);
	    if (itemCart != null) {
		ValueBuilder vb = mapping.getValue();
		if (vb.getValueElaboration().equals(ElaborationType.APPEND)) {
		    return buildValoreIdsemanticoConcatenato(mapping, istanza, itemCart);
		}
		CartMappingIndexedProperty indexedProperty = null;
		if (StringUtils.isNotBlank(vb.getIndexedProperty())) {
		    try {
			indexedProperty = CartMappingHelper.getProprietaIndicizzataIstanza(istanza, vb.getIndexedProperty());
		    } catch (Exception e) {
			throw new CartMappingException(e, mapping.getIdSemantico());
		    }
		}
		if (indexedProperty != null) {
		    vis = buildValoreIdsemanticoIndicizzato(mapping, istanza, indexedProperty, itemCart);
		    //se il campo di destinazione nella modulistica non è indicizzato (ossia non si trova in una tabella)
		    //allora i valori multipli recuperati vengono gestiti come valori vettoriali in un campo singolo.
		    if (tables.isEmpty()) {
			List<ValoreIdSemantico> innerVis = vis.getValoreIndicizzato();
			String[] valueVector = new String[innerVis.size()];
			for (int i = 0; i < innerVis.size(); i++) {
			    valueVector[i] = innerVis.get(i).getValoreScalare();
			}
			vis.setValoreIndicizzato(null);
			vis.setValoreVettoriale(valueVector);
		    }
		    return vis;
		}
		List<VbgValue> vbgValues = vb.getVbgValues();
		for (VbgValue valueSrc : vbgValues) {
		    boolean getValue = false;
		    try {
			getValue = CartMappingHelper.evaluateCondition(istanza, valueSrc.getIfProperty(), valueSrc.getIfOperator(),
				valueSrc.getIfValue());
		    } catch (Exception e1) {
			throw new CartMappingException(e1, mapping.getIdSemantico());
		    }
		    if (getValue) {
			// ValoreIdSemantico tmpVis = null;
			if (valueSrc.getValoreFisso() != null) {
			    // valori fissi per campi a valore scalare o vettoriale
			    vis = buildValoreIdSemanticoNonIndicizzato(valueSrc.getValoreFisso(), itemCart, null, mapping.getValueProcessing(),
				    mapping.getValueMappings());
			    if (!tables.isEmpty()) {
				//se il campo CART è indicizzato restuisco comunque il valore fisso di default come valore indicizzato
				ValoreIdSemantico indexedVis = new ValoreIdSemantico(new ArrayList<ValoreIdSemantico>(1));
				indexedVis.getValoreIndicizzato().add(vis);
				vis = indexedVis;
			    }
			} else if (valueSrc.getCampoDinamico() != null) {
			    // TODO gestione campi file
			    CampoDinamico dynCampo = valueSrc.getCampoDinamico();
			    // recupero i dati dinamici del campo dinamico mappato
			    if (StringUtils.isNotEmpty(dynCampo.getCodiceCampo())) {
				// recupero la definizione del campo dinamico mappato
				// e recupero i valori del campo dinamico mappato
				Dyn2Campi dyn2Campo = null;
				List<Istanzedyn2dati> dynData = null;
				if (NumberUtils.isNumber(dynCampo.getCodiceCampo())) {
				    dyn2Campo = this.dyn2CampiService.findById(new PkId(new Integer(dynCampo.getCodiceCampo())));
				    dynData = this.istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(),
					    new Integer(dynCampo.getCodiceCampo()), null);
				} else {
				    dyn2Campo = this.dyn2CampiService.findByNomeCampo(dynCampo.getCodiceCampo());
				    dynData = this.istanzedyn2datiService.findByIstanzaAndNomeCampo(istanza, dynCampo.getCodiceCampo(),
					    dynCampo.getSoftware());
				}
				// se il campo deve essere indicizzato (in base
				// alla modulistica)
				if (!tables.isEmpty()) {
				    ValoreIdSemantico visLevel1 = new ValoreIdSemantico(new ArrayList<ValoreIdSemantico>());
				    // values4Indice.add(visLevel1);
				    /*
				     * Se indice ha più di un valore diverso
				     * nelle righe restituite significa che la
				     * scheda dinamica è multipla ==> 1° livello
				     * di indicizzazione. Per ogni valore
				     * diverso di indicemolteplicità si ha un
				     * diverso valore ad un secondo livello di
				     * indicizzazione. La modulistica del CART
				     * di fatto però non prevede mai strutture
				     * dati a doppio lilvello di indicizzazione
				     * quindi il ValoreIdSemantico restituito
				     * avrà sempre un solo livello di
				     * indicizzazione.
				     */
				    for (Istanzedyn2dati dynDato : dynData) {
					String vbgValue = dynDato.getValoredecodificato();
					ValoreIdSemantico value = buildValoreIdSemanticoNonIndicizzato(vbgValue, itemCart, dyn2Campo,
						mapping.getValueProcessing(), mapping.getValueMappings());
					visLevel1.getValoreIndicizzato().add(value);
				    }
				    vis = visLevel1;
				}
				// se il campo non deve essere indicizzato
				else {
				    // se ci sono valori indicizzati nei campi
				    // dinamici recupero solo il primo e genero
				    // un warning da visualizzare all'utente
				    if (!dynData.isEmpty()) {
					String vbgValue = dynData.get(0).getValoredecodificato();
					vis = buildValoreIdSemanticoNonIndicizzato(vbgValue, itemCart, dyn2Campo, mapping.getValueProcessing(),
						mapping.getValueMappings());
					if (dynData.size() > 1) {
					    /*
					     * TODO generare warning "per l'id
					     * semantico XYZ è previsto un solo
					     * valore ma nell'istanza ne sono
					     * presenti N. Sarà recuperato solo
					     * il primo." Il warning deve essere
					     * visualizzato al termine della
					     * procedura insieme ad altri
					     * eventuali messaggi
					     */
					}
				    } else {
					vis = new ValoreIdSemantico("");
				    }
				}
			    }
			} else if (valueSrc.getCampoStatico() != null) {
			    // recupero valori da dati di dominio
			    String propertyPath = valueSrc.getCampoStatico();
			    if (StringUtils.isNotBlank(propertyPath)) {
				Object dato = null;
				try {
				    dato = EntityUtils.getPropertyValues(istanza, propertyPath);
				} catch (Exception e) {
				    throw new CartMappingException(e, mapping.getIdSemantico());
				}
				String strValue = null;
				/*
				 * se il campo CART non è indicizzato ma il
				 * valore recuperato è una lista di valori
				 * allora recupero solo il primo dei valori
				 * restituiti e genero un warning all'utente
				 */
				if (tables.isEmpty()) {
				    if (dato != null) {
					if (dato instanceof Collection) {
					    Collection<Object> dataVector = (Collection<Object>) dato;
					    if (!dataVector.isEmpty()) {
						dato = dataVector.iterator().next();
						// TODO generare warning campo
						// CART di tipo scalare mappato
						// su proprietà VBG di tipo
						// Collection
					    }
					}
				    } else {
					dato = "";
				    }
				    strValue = CartMappingHelper.valueAsString(dato);
				    vis = buildValoreIdSemanticoNonIndicizzato(strValue, itemCart, null, mapping.getValueProcessing(),
					    mapping.getValueMappings());
				    if (dato instanceof Oggetti) {
				    }
				}
				// campo CART indicizzato
				else {
				    List<ValoreIdSemantico> indexedValues = new ArrayList<ValoreIdSemantico>();
				    if (dato != null) {
					if (!(dato instanceof Collection)) {
					    strValue = CartMappingHelper.valueAsString(dato);
					    vis = buildValoreIdSemanticoNonIndicizzato(strValue, itemCart, null, mapping.getValueProcessing(),
						    mapping.getValueMappings());
					    indexedValues.add(vis);
					} else {
					    Collection<Object> indexedData = (Collection<Object>) dato;
					    for (Object datum : indexedData) {
						strValue = CartMappingHelper.valueAsString(datum);
						vis = buildValoreIdSemanticoNonIndicizzato(strValue, itemCart, null, mapping.getValueProcessing(),
							mapping.getValueMappings());
						indexedValues.add(vis);
					    }
					}
					vis = new ValoreIdSemantico(indexedValues);
				    }
				}
			    } else {
				String msg = MessageFormat.format("Path della proprietà dell''istanza non specificato per l''id semantico {0}",
					new Object[] { mapping.getIdSemantico() });
				log.warn("getValoreIdSemantico - {}", msg);
				throw new CartMappingException(msg, mapping.getIdSemantico());
			    }
			} else {
			    String msg = MessageFormat.format("Nessuna mappatura specificata per l''id semantico {0}",
				    new Object[] { mapping.getIdSemantico() });
			    log.warn("getValoreIdSemantico - {}", msg);
			    throw new CartMappingException(msg, mapping.getIdSemantico());
			}
			if (vis != null) {
			    break;
			}
		    }
		}
		if (vis != null) {
		    vis.setFile(itemCart.getFile() != null);
		}
	    } else {
		String msg = MessageFormat.format("impossibile trovare il campo associato all''id semantico {0} nella modulistica STANDARD_0.",
			new Object[] { mapping.getIdSemantico() });
		log.warn("getValoreIdSemantico - {}", msg);
		throw new CartMappingException(msg, mapping.getIdSemantico());
	    }
	}
	return vis;
    }

    /*
     * Nel caso in cui si voglia concatenare i valori recuperati valgono le
     * seguenti regole e limitazioni: 1) i dati non possono essere recuperati da
     * campi dinamici (per ora non serve quindi non è implementato) 2) deve
     * esserci almeno un valore recuperato da un campo statico 3) se esiste
     * anche un solo valore che deve essere recuperato da una proprietà di tipo
     * lista (1-n) allora i dati concatenati restituiti avranno una molteplicità
     * pari alla lunghezza della lista e anche tutti gli altri valori da campi
     * statici devono restituire o un singolo valore scalare (1-1) che sarà
     * ripetuto uguale per ogni indice della lista oppure una lista di valori
     * presi dalla stessa lista (1-n) 4) nel caso descritto al punto 3 le
     * condizioni booleane per il recupero dei dati che fanno riferimento alla
     * proprietà di tipo lista, se presenti, saranno valutate indice per indice
     * e non sull'intera lista
     */
    private ValoreIdSemantico buildValoreIdsemanticoConcatenato(CartMapping mapping, Istanze istanza, ItemType campoCart) throws CartMappingException {

	// la concatenazione di valori non è utilizzabile per popolare id
	// semantici di tipo file
	if (campoCart.getFile() != null) {
	    String msg = MessageFormat
		    .format("Errore di configurazione delle mappature CART per l''id semantico {0}: la concatenazione di valori mappati non può essere utilizzata per popolare campi di tipo file.",
			    campoCart.getFile().getIdSemantico());
	    throw new CartMappingException(msg, campoCart.getFile().getIdSemantico());
	}
	ValoreIdSemantico vis = null;
	ValueBuilder valueBuilder = mapping.getValue();
	List<VbgValue> vbgValues = valueBuilder.getVbgValues();
	// Object[] dataMatrix = new Object[vbgValues.size()];
	CartMappingIndexedProperty indexedProperty = null;
	/*
	 * se specificato l'attributo indexed-property nel tag <value> allora
	 * dalla proprietà specificata viene recuperata la Collection di oggetti
	 * di dominio da cui leggere i valori da concatenare e su cui verificare
	 * le condizioni booleane quando presenti.
	 */
	if (StringUtils.isNotBlank(valueBuilder.getIndexedProperty())) {
	    try {
		indexedProperty = CartMappingHelper.getProprietaIndicizzataIstanza(istanza, valueBuilder.getIndexedProperty());
	    } catch (Exception e) {
		throw new CartMappingException(e, mapping.getIdSemantico());
	    }
	}
	/*
	 * primo ciclo sulle configurazioni vbg-value per verificare i
	 * prerequisiti e per determinare se avremo un risultato scalare o
	 * indicizzato, nel caso di valori indicizzati viene anche restituita la
	 * Collection di oggetti di dominio da cui recuperare i valori e su cui
	 * valutare le condizioni booleane nel caso di valori condizionali.
	 */
	boolean hasCampoStatico = false;
	for (VbgValue vbgValue : vbgValues) {
	    if (vbgValue.getCampoDinamico() != null) {
		// la concatenazione di valori recuperati dai campi dinamici non
		// è per ora supportata
		String msg = MessageFormat
			.format("Errore di configurazione delle mappature CART per l''id semantico {0}: nel caso un cui sia prevista la concatenazione dei valori recuperati le mappature su campi dinamici non sono supportate.",
				mapping.getIdSemantico());
		throw new CartMappingException(msg, mapping.getIdSemantico());
	    }
	    String campoStatico = vbgValue.getCampoStatico();
	    if (campoStatico != null) {
		hasCampoStatico = true;
		CartMappingIndexedProperty tmpIdxProp = null;
		try {
		    tmpIdxProp = CartMappingHelper.getProprietaIndicizzataIstanza(istanza, campoStatico);
		} catch (Exception e) {
		    throw new CartMappingException(e, mapping.getIdSemantico());
		}
		if (tmpIdxProp != null) {
		    // se non è ancora stata individuata la proprietà
		    // indicizzata da cui prendere i valori
		    if (indexedProperty == null) {
			indexedProperty = tmpIdxProp;
		    } else {
			/*
			 * se è già stata individuata una proprietà indicizzata
			 * fra i valori da concatenare allora verifico che le
			 * altre proprietà indicizzate si riferiscano alla
			 * stessa Collection di entità collegate all'istanza
			 */
			if (!indexedProperty.getPropertyPath().equals(tmpIdxProp.getPropertyPath())) {
			    String msg = "Errore di configurazione delle mappature CART: nel caso un cui sia prevista la concatenazione dei valori recuperati, le mappature sulle proprietà 1-n devono tutte puntare alla stessa collezione di valori.";
			    throw new CartMappingException(msg, mapping.getIdSemantico());
			}
		    }
		}
	    }
	}
	if (!hasCampoStatico) {
	    String msg = "Errore di configurazione delle mappature CART: nel caso in cui sia prevista la concatenazione dei valori, almeno uno dei valori deve essere recuperato da una proprietà dell'istanza (o di un'altra entità ad essa collegata).";
	    throw new CartMappingException(msg, mapping.getIdSemantico());
	}
	StringBuilder[] dataMatrix = null;
	if (indexedProperty == null) {
	    // dataMatrix = new StringBuilder[] { new StringBuilder() };
	    StringBuilder sbValue = new StringBuilder();
	    for (VbgValue vbgValue : vbgValues) {
		try {
		    if (CartMappingHelper.evaluateCondition(istanza, vbgValue.getIfProperty(), vbgValue.getIfOperator(), vbgValue.getIfValue())) {
			if (StringUtils.isNotBlank(vbgValue.getValoreFisso())) {
			    sbValue.append(vbgValue.getValoreFisso());
			} else if (StringUtils.isNotBlank(vbgValue.getCampoStatico())) {
			    // se si tratta di un campo statico do per scontato
			    // che ha valore scalare perchè ho già verificato
			    // che indexedProperty == null
			    Object val = EntityUtils.getPropertyValues(istanza, vbgValue.getCampoStatico());
			    if (val != null) {
				sbValue.append(CartMappingHelper.valueAsString(val));
			    }
			}
		    }
		} catch (Exception e) {
		    throw new CartMappingException(e, mapping.getIdSemantico());
		}
	    }
	    vis = buildValoreIdSemanticoNonIndicizzato(sbValue.toString(), campoCart, null, mapping.getValueProcessing(), mapping.getValueMappings());
	} else {
	    Collection<Object> entities = indexedProperty.getPropertyData();
	    if (entities != null) {
		// predispongo un'array di stringbuffer per concatenare le
		// proprietà lette da ciascun elemento della collection
		dataMatrix = new StringBuilder[entities.size()];
		for (int i = 0; i < dataMatrix.length; i++) {
		    dataMatrix[i] = new StringBuilder();
		}
		Iterator<Object> entityIterator = entities.iterator();
		for (int i = 0; i < entities.size(); i++) {
		    Object entity = entityIterator.next();
		    StringBuilder writeOnMe = dataMatrix[i];
		    for (VbgValue vbgValue : vbgValues) {
			// verifica della condizione booleana in base alla quale
			// appendere o meno il valore
			boolean doAppend = true;
			String ifProp = vbgValue.getIfProperty();
			try {
			    if (StringUtils.isNotBlank(ifProp)) {
				Object evaluateOnMe = istanza;
				/*
				 * se la proprietà su cui valutare la condizione
				 * appartiene alla collezione di valori
				 * recuperati allora la valuto per ciascun
				 * elemento della collection
				 */
				if (ifProp.startsWith(indexedProperty.getPropertyPath() + ".")) {
				    ifProp = ifProp.substring(indexedProperty.getPropertyPath().length() + 1);
				    evaluateOnMe = entity;
				}
				doAppend = CartMappingHelper.evaluateCondition(evaluateOnMe, ifProp, vbgValue.getIfOperator(), vbgValue.getIfValue());
			    }
			    if (doAppend) {
				if (StringUtils.isNotBlank(vbgValue.getValoreFisso())) {
				    writeOnMe.append(vbgValue.getValoreFisso());
				} else if (StringUtils.isNotBlank(vbgValue.getCampoStatico())) {
				    Object readMyProp = istanza;
				    /*
				     * se la proprietà mappata appartiene alla
				     * collezione di valori recuperati allora ne
				     * recupero i valori da ciascun elemento
				     * della collection
				     */
				    String propToRead = vbgValue.getCampoStatico();
				    if (propToRead.startsWith(indexedProperty.getPropertyPath() + ".")) {
					propToRead = propToRead.substring(indexedProperty.getPropertyPath().length() + 1);
					readMyProp = entity;
				    }
				    Object val = EntityUtils.getPropertyValues(readMyProp, propToRead);
				    writeOnMe.append(CartMappingHelper.valueAsString(val));
				} else if (vbgValue.getCampoDinamico() != null) {
				    //non supportato
				}
			    }
			} catch (Exception e) {
			    throw new CartMappingException(e, mapping.getIdSemantico());
			}
		    }
		}
	    } else {
		dataMatrix = new StringBuilder[0];
	    }
	    List<ValoreIdSemantico> indexedValues = new ArrayList<ValoreIdSemantico>();
	    for (int i = 0; i < dataMatrix.length; i++) {
		StringBuilder sbVal = dataMatrix[i];
		ValoreIdSemantico tmpVis = buildValoreIdSemanticoNonIndicizzato(sbVal.toString(), campoCart, null, mapping.getValueProcessing(),
			mapping.getValueMappings());
		indexedValues.add(tmpVis);
	    }
	    vis = new ValoreIdSemantico(indexedValues);
	}
	return vis;
    }

    private ValoreIdSemantico buildValoreIdsemanticoIndicizzato(CartMapping mapping, Istanze istanza, CartMappingIndexedProperty indexedProperty,
	    ItemType campoCart) throws CartMappingException {

	ValoreIdSemantico retVal = null;
	Collection<Object> indexedData = indexedProperty.getPropertyData();
	List<ValoreIdSemantico> indexedVis = new ArrayList<ValoreIdSemantico>();
	int index = 0;
	Iterator<Object> dataIterator = indexedData.iterator();
	Object ithElement = null;
	for (int i = 0; i <= indexedData.size(); i++) {
	    //si fa una iterazione in più con ithElement = null per poter impostare i valori di default nel caso di liste vuote
	    ithElement = dataIterator.hasNext() ? dataIterator.next() : null;
	    //se ho elaborato almeno un elemento della lista salto l'elaborazione dell'elemento null perchè non sevono valori di default
	    if (ithElement == null && indexedData.size() > 0) {
		break;
	    }
	    List<VbgValue> vbgValues = mapping.getValue().getVbgValues();
	    ValoreIdSemantico vis = null;
	    for (VbgValue vbgValue : vbgValues) {
		boolean readValue = true;
		if (StringUtils.isNotBlank(vbgValue.getIfProperty())) {
		    /*
		     * se la proprietà su cui valutare la condizione booleana è
		     * la stessa specificata in indexed-property nel tag <Value>
		     * allora la condizione viene valutata per ciscun elemento
		     * della collection indicizzata
		     */
		    try {
			if (vbgValue.getIfProperty().startsWith(indexedProperty.getPropertyPath() + '.')) {
			    String subPath = vbgValue.getIfProperty().substring(indexedProperty.getPropertyPath().length() + 1);
			    readValue = CartMappingHelper.evaluateCondition(ithElement, subPath, vbgValue.getIfOperator(), vbgValue.getIfValue());
			} else {
			    readValue = CartMappingHelper.evaluateCondition(istanza, vbgValue.getIfProperty(), vbgValue.getIfOperator(),
				    vbgValue.getIfValue());
			}
		    } catch (Exception e) {
			throw new CartMappingException(e, mapping.getIdSemantico());
		    }
		}
		// viene recuperato solo il primo valore per cui la condizione
		// specificata è vera
		if (readValue) {
		    if (vbgValue.getValoreFisso() != null) {
			vis = buildValoreIdSemanticoNonIndicizzato(vbgValue.getValoreFisso(), campoCart, null, mapping.getValueProcessing(),
				mapping.getValueMappings());
		    } else if (ithElement != null) {
			//per campi statici e dinamici non si elabora l'ithElement == null perchè serve solo per impostare i valori di default che si impostano con valore-fisso
			if (vbgValue.getCampoStatico() != null) {
			    if (StringUtils.isNotBlank(vbgValue.getCampoStatico())) {
				Object value = null;
				try {
				    if (vbgValue.getCampoStatico().startsWith(indexedProperty.getPropertyPath() + ".")) {
					String subPath = vbgValue.getCampoStatico().substring(indexedProperty.getPropertyPath().length() + 1);
					value = EntityUtils.getPropertyValues(ithElement, subPath);
				    } else {
					value = EntityUtils.getPropertyValues(istanza, vbgValue.getCampoStatico());
				    }
				} catch (Exception e) {
				    throw new CartMappingException(e, mapping.getIdSemantico());
				}
				if (value == null) {
				    value = "";
				}
				String valueAsString = CartMappingHelper.valueAsString(value);
				vis = buildValoreIdSemanticoNonIndicizzato(valueAsString, campoCart, null, mapping.getValueProcessing(),
					mapping.getValueMappings());
			    } else {
				String msg = MessageFormat.format("Path della proprietà dell''istanza non specificato per l''id semantico {0}",
					new Object[] { mapping.getIdSemantico() });
				log.warn("getValoreIdSemantico - {}", msg);
				throw new CartMappingException(msg, mapping.getIdSemantico());
			    }
			} else if (vbgValue.getCampoDinamico() != null) {
			    CampoDinamico dynCampo = vbgValue.getCampoDinamico();
			    // recupero la definizione del campo dinamico mappato
			    // e recupero i valori del campo dinamico mappato
			    Dyn2Campi dyn2Campo = null;
			    List<Istanzedyn2dati> dynData = null;
			    if (NumberUtils.isNumber(dynCampo.getCodiceCampo())) {
				dyn2Campo = this.dyn2CampiService.findById(new PkId(new Integer(dynCampo.getCodiceCampo())));
				dynData = this.istanzedyn2datiService.findByIstanzaAndDyn2Campi(istanza.getId().getCodice(),
					new Integer(dynCampo.getCodiceCampo()), null);
			    } else {
				dyn2Campo = this.dyn2CampiService.findByNomeCampo(dynCampo.getCodiceCampo());
				dynData = this.istanzedyn2datiService.findByIstanzaAndNomeCampo(istanza, dynCampo.getCodiceCampo(),
					dynCampo.getSoftware());
			    }
			    /*
			     * siccome la molteplicità del campo è già specificata
			     * nell'attributo indexed-property il campo dinamico
			     * deve avere un valore singolo che sarà ripetuto per
			     * tutti gli n elementi presenti nella proprietà
			     * indicizzata
			     */
			    if (dynData != null && dynData.size() > 0) {
				String strVal = "";
				if (dynData.size() == 1) {
				    strVal = dynData.get(0).getValoredecodificato();
				} else {
				    // oppure un valore multiplo che sarà recuperato
				    // tenendo conto degli indici della proprietà
				    // indicizzata
				    if (dynData.size() > index) {
					strVal = dynData.get(index).getValoredecodificato();
				    }
				}
				vis = buildValoreIdSemanticoNonIndicizzato(strVal, campoCart, dyn2Campo, mapping.getValueProcessing(),
					mapping.getValueMappings());
			    }
			} else {
			    String msg = MessageFormat.format("Nessuna mappatura specificata per l''id semantico {0}",
				    new Object[] { mapping.getIdSemantico() });
			    log.warn("getValoreIdSemantico - {}", msg);
			    throw new CartMappingException(msg, mapping.getIdSemantico());
			}
		    }
		    if (vis != null) {
			indexedVis.add(vis);
			break;
		    }
		}
	    }
	    if (vis == null) {
		//se nessun vbgValue ha recuperato un valore (nessuna if-property specifica una condizione vera) imposto all'i-esimo indice un valore vuoto
		vis = new ValoreIdSemantico("");
		indexedVis.add(vis);
	    }
	    index++;
	}
	retVal = new ValoreIdSemantico(indexedVis);
	if (campoCart.getFile() != null) {
	    retVal.setFile(true);
	    //nel caso di campi file gli oggetti FileInfo dei singoli valori vengono messi tutti insieme
	    //nell'oggetto ValoreIdSemantico più esterno che contiene la lista dei valori
	    for (ValoreIdSemantico ivis : indexedVis) {
		retVal.getFileInfo().addAll(ivis.getFileInfo());
		ivis.getFileInfo().clear();
	    }
	}
	return retVal;
    }

    private ValoreIdSemantico buildValoreIdSemanticoNonIndicizzato(String vbgValue, ItemType itemCart, Dyn2Campi campoVbg,
	    ValueProcessing valueProcessing, ValueMappings valueMappings) throws CartMappingException {

	ValoreIdSemantico vis = null;
	// verifico se la modulistica prevede un campo a valori multipli
	if (itemCart.getCampo() != null) {
	    CampoType campoCart = itemCart.getCampo();
	    if (campoCart.isMultiValore()) {
		// restituisco un valore vettoriale
		String[] splittedValue = null;
		// se campoVbg != null verifico se anch'esso prevede valori
		// multipli
		if (campoVbg != null) {
		    if (campoVbg.getTipodato().equals(TipoControlloEnum.MultiLista.name())) {
			// se è select multiple il valore decodificato può
			// contenere valori multipli separati da ';'
			splittedValue = vbgValue.split(WebConstants.ISTANZEDYN2DATI_VALUE_SEPARATOR);
		    } else {
			splittedValue = new String[] { vbgValue };
		    }
		} else {
		    splittedValue = new String[] { vbgValue };
		}
		for (int i = 0; i < splittedValue.length; i++) {
		    //applico eventuali trasformazioni al valore VBG
		    splittedValue[i] = CartMappingHelper.processaValoreVBG(splittedValue[i], valueProcessing);
		    //applico eventuali mappature ai valori
		    splittedValue[i] = CartMappingHelper.getValoreCartMappato(splittedValue[i], valueMappings);
		}
		vis = new ValoreIdSemantico(splittedValue);
	    } else {
		// restituisco un valore scalare
		vbgValue = CartMappingHelper.processaValoreVBG(vbgValue, valueProcessing);
		vbgValue = CartMappingHelper.getValoreCartMappato(vbgValue, valueMappings);
		vis = new ValoreIdSemantico(vbgValue);
	    }
	} else if (itemCart.getFile() != null) {
	    FileType fileCart = itemCart.getFile();
	    // oggetto da campo dinamico
	    if (campoVbg != null) {
		if (campoVbg.getTipodato().equals(TipoControlloEnum.Upload)) {
		    vis = getValoreIdSemanticoFile(vbgValue, fileCart);
		} else {
		    String errMsg = MessageFormat
			    .format("Errore di configurazione delle mappature CART: l'id semantico {0} è associato ad un campo file nella modulistica CART ma è mappato su un campo dinamico che non è di tipo Upload.",
				    fileCart.getIdSemantico());
		    throw new CartMappingException(errMsg, fileCart.getIdSemantico());
		}
	    }
	    // oggetto da proprietà istanza
	    else {
		//nel caso di campi di tipo file vbgValue contiene il codice dell'oggetto con cui interrogo la tabella oggetti
		vis = getValoreIdSemanticoFile(vbgValue, fileCart);
	    }
	}
	return vis;
    }

    private ValoreIdSemantico getValoreIdSemanticoFile(String codiceOggetto, FileType campoFile) throws CartMappingException {

	ValoreIdSemantico vis = null;
	if (NumberUtils.isNumber(codiceOggetto)) {
	    Integer codiceOgg = new Integer(codiceOggetto);
	    PkId pkOgg = new PkId(codiceOgg);
	    Oggetti file = oggettiService.findById(pkOgg);
	    if (file != null) {
		FileInfo fi = new FileInfo();
		fi.setIdOggetto(file.getId().getCodice());
		fi.setNomeFile(file.getNomefile());
		fi.setIdSemantico(campoFile.getIdSemantico());
		fi.setFirmaValidata(true);
		fi.setDimensione(file.getDimensioneFile());
		vis = new ValoreIdSemantico();
		vis.setFile(true);
		vis.setValoreScalare(fi.getIdOggetto().toString());
		vis.getFileInfo().add(fi);
	    } else {
		// nessun record di oggetti trovato
		String msg = MessageFormat.format("Impossibile trovare il file associato al valore {0} per l'id semantico {1}", pkOgg,
			campoFile.getIdSemantico());
		throw new CartMappingException(msg, campoFile.getIdSemantico());
	    }
	} else {
	    //codice oggetto non numerico
	    String msg = MessageFormat.format(
		    "Codice oggetto non numerico: impossibile trovare il file associato al valore {0} per l'id semantico {1}", codiceOggetto,
		    campoFile.getIdSemantico());
	    throw new CartMappingException(msg, campoFile.getIdSemantico());
	}
	return vis;
    }

    @Override
    public File[] completaMessaggioPresentazioneDomanda(DatiDomandaCart datiDomanda, PresentazioneDomanda messaggioPresentazione) throws Exception {

	List<File> retFiles = new ArrayList<File>();
	String idDomanda = AttachmentsUtils.escapeForDirectoryName(messaggioPresentazione.getIdDomanda());
	DatiModulo modStd0 = datiDomanda.getDatiModulo(FACCTConstants.STANDARD_0);
	List<MappingIdSemantico> datiModulo = modStd0.getValoreModulo();
	IndiceZip indiceZip = messaggioPresentazione.getIndiceZip();
	if (indiceZip == null) {
	    indiceZip = new IndiceZip();
	    messaggioPresentazione.setIndiceZip(indiceZip);
	}
	List<Allegato> allegatiPresentazione = indiceZip.getAllegato();
	// aggiungo i files generati (MDA.XML, MDA.STANDARD_0.PDF) e da generare
	// (SUAP.PDF e SUAP.XML)
	Destinatari emptyDest = new Destinatari();
	emptyDest.getDestinatario().add("");
	// MDA.XML
	Allegato allegato = new Allegato();
	allegato.setCodice("");
	allegato.setNomeFile(idDomanda + ".MDA.XML");
	allegato.setDescrizione("");
	allegato.setDestinatari(emptyDest);
	allegatiPresentazione.add(allegato);
	// MDA.STANDARD_0.XML
	allegato = new Allegato();
	allegato.setCodice("");
	allegato.setNomeFile(idDomanda + ".MDA." + FACCTConstants.STANDARD_0 + ".XML");
	allegato.setDescrizione(FACCTConstants.STANDARD_0);
	allegato.setDestinatari(emptyDest);
	allegatiPresentazione.add(allegato);
	// MDA.STANDARD_0.PDF
	allegato = new Allegato();
	allegato.setCodice("");
	allegato.setNomeFile(idDomanda + ".MDA." + FACCTConstants.STANDARD_0 + ".PDF");
	allegato.setDescrizione(FACCTConstants.STANDARD_0);
	allegato.setDestinatari(emptyDest);
	allegatiPresentazione.add(allegato);
	// SUAP.PDF
	allegato = new Allegato();
	allegato.setCodice("");
	allegato.setNomeFile(idDomanda + ".SUAP.PDF");
	allegato.setDescrizione("Distinta firmata della domanda");
	allegato.setDestinatari(emptyDest);
	allegatiPresentazione.add(allegato);
	// SUAP.XML non previsto da RFC-183 previsto da DPR-160
	allegato = new Allegato();
	allegato.setCodice("");
	allegato.setNomeFile(idDomanda + ".SUAP.XML");
	allegato.setDescrizione("");
	allegato.setDestinatari(emptyDest);
	allegatiPresentazione.add(allegato);
	// aggiungo gli allegati presenti nei valori degli id semantici
	List<FileInfo> allegatiIstanza = datiDomanda.getAllegati();
	for (FileInfo fileInfo : allegatiIstanza) {
	    String idSemantico = fileInfo.getIdSemantico();
	    //String codiceAllegato = "";
	    if (StringUtils.isNotEmpty(idSemantico)) {
		// String idSemanticoCodice = null;
		// int lastMinusIndex = idSemantico.lastIndexOf('-');
		//		if (lastMinusIndex > -1) {
		//		    idSemanticoCodice = idSemantico.substring(0, lastMinusIndex).concat("-CODICE");
		//		    ValoreIdSemantico tempVis = datiDomanda.getValoreIdSemantico(FACCTConstants.STANDARD_0, idSemantico);
		//		    ValoreIdSemantico tempVisCodice = datiDomanda.getValoreIdSemantico(FACCTConstants.STANDARD_0, idSemanticoCodice);
		//		    if (tempVisCodice != null) {
		//			List<ValoreIdSemantico> codici = null;
		//			List<ValoreIdSemantico> valAllegati = null;
		//			boolean indexed = tempVis.isIndicizzato();
		//			if (indexed) {
		//			    if (!tempVisCodice.isIndicizzato()) {
		//				log.error(
		//					"completaMessaggioPresentazioneDomanda - impossibile individuare il codice associato agli allegati perché il campo file {} è indicizzato mentre il campo codice {} non lo è",
		//					new Object[] { idSemantico, idSemanticoCodice });
		//			    } else {
		//				codici = tempVisCodice.listaValoriScalariNormalizzata();
		//				valAllegati = tempVis.listaValoriScalariNormalizzata();
		//			    }
		//			} else {
		//			    if (tempVisCodice.isIndicizzato()) {
		//				log.error(
		//					"completaMessaggioPresentazioneDomanda - impossibile individuare il codice associato agli allegati perché il campo file {} non è indicizzato mentre il campo codice {} sì",
		//					new Object[] { idSemantico, idSemanticoCodice });
		//			    } else {
		//				codici = new ArrayList<ValoreIdSemantico>();
		//				codici.add(tempVisCodice);
		//				valAllegati = new ArrayList<ValoreIdSemantico>();
		//				valAllegati.add(tempVis);
		//			    }
		//			}
		//			for (int i = 0; i < valAllegati.size(); i++) {
		//			    ValoreIdSemantico valAllegato = valAllegati.get(i);
		//			    if (valAllegato != null && valAllegato.hasValue()) {
		//				if (valAllegato.getValoreScalare().equalsIgnoreCase(fileInfo.getIdOggetto().toString())) {
		//				    ValoreIdSemantico valCodice = codici.get(i);
		//				    codiceAllegato = StringUtils.defaultString(valCodice.getValoreScalare());
		//				    break;
		//				}
		//			    }
		//			}
		//		    }
		//		}
		allegato = new Allegato();
		allegato.setNomeFile(fileInfo.getNomeFile());
		allegato.setDescrizione(FACCTConstants.STANDARD_0);
		Destinatari dest = new Destinatari();
		dest.getDestinatario().add("");
		allegato.setDestinatari(dest);
		allegato.setCodice(""/*codiceAllegato*/);
		allegatiPresentazione.add(allegato);
	    }
	}
	/*
	for (MappingIdSemantico mappingIdSemantico : datiModulo) {
	    ValoreIdSemantico tempVis = mappingIdSemantico.getValore();
	    if (tempVis.isFile()) {
		boolean indexed = tempVis.isIndicizzato();
		List<ValoreIdSemantico> valori = null;
		List<ValoreIdSemantico> codici = null;
		if (indexed) {
		    valori = tempVis.getValoreIndicizzato();
		} else {
		    valori = new ArrayList<ValoreIdSemantico>();
		    valori.add(tempVis);
		}
		// verifico se esiste un id semantico -CODICE associato al campo
		// file
		String idSemAllegato = mappingIdSemantico.getIdSemantico();
		String idSemCodice = null;
		int lastMinusIndex = idSemAllegato.lastIndexOf('-');
		if (lastMinusIndex > -1) {
		    idSemCodice = idSemAllegato.substring(0, lastMinusIndex).concat("-CODICE");
		    ValoreIdSemantico tempVisCodice = datiDomanda.getValoreIdSemantico(FACCTConstants.STANDARD_0, idSemCodice);
		    if (tempVisCodice != null) {
			if (indexed) {
			    if (!tempVisCodice.isIndicizzato()) {
				log.error(
					"completaMessaggioPresentazioneDomanda - impossibile individuare il codice associato agli allegati perché il campo file {} è indicizzato mentre il campo codice {} non lo è",
					new Object[] { idSemAllegato, idSemCodice });
			    } else {
				codici = tempVisCodice.getValoreIndicizzato();
			    }
			} else {
			    if (tempVisCodice.isIndicizzato()) {
				log.error(
					"completaMessaggioPresentazioneDomanda - impossibile individuare il codice associato agli allegati perché il campo file {} non è indicizzato mentre il campo codice {} sì",
					new Object[] { idSemAllegato, idSemCodice });
			    } else {
				codici = new ArrayList<ValoreIdSemantico>();
				codici.add(tempVisCodice);
			    }
			}
		    }
		}
		ValoreIdSemantico tempVisCodice = null;
		for (int i = 0; i < valori.size(); i++) {
		    tempVis = valori.get(i);
		    List<FileInfo> files = tempVis.getFileInfo();
		    for (FileInfo finfo : files) {
			allegato = new Allegato();
			allegato.setNomeFile(finfo.getNomeFile());
			allegato.setDescrizione(FACCTConstants.STANDARD_0);
			Destinatari dest = new Destinatari();
			dest.getDestinatario().add("");
			allegato.setDestinatari(dest);
			if (codici != null) {
			    if (i < codici.size()) {
				tempVisCodice = codici.get(i);
				allegato.setCodice(tempVisCodice.getValoreScalare());
			    } else {
				log.error(
					"completaMessaggioPresentazioneDomanda - impossibile individuare il codice per l'allegato {} perché non esiste nessun valore per l'id semantico {} all'indice {}",
					new Object[] { finfo.getNomeFile(), idSemCodice, i });
				allegato.setCodice("");
			    }
			} else {
			    allegato.setCodice("");
			}
			allegatiPresentazione.add(allegato);
		    }
		}
	    }
	}
	*/
	/*
	 * aggiungo la sezione zipFile ma non valorizzo l'attributo datiFile che
	 * viene valorizzato in inviaMessaggioPresentazioneDomanda solo se è
	 * richiesto l'invio dell'allegato inline nel messaggio come campo base
	 * 64 binary
	 */
	ZipFileType zipFile = new ZipFileType();
	zipFile.setNomeFile(idDomanda + FACCTConstants.ZIP_FILE_SUFFIX);
	zipFile.setContentType(FACCTConstants.ZIP_CONTENT_TYPE);
	messaggioPresentazione.setZipFile(zipFile);
	// creo l'allegato <codice pratica>.SUAP.XML serializzando in XML
	// l'oggetto PresentazioneDomanda che sto creando
	// creazione del file XML che contiene il messaggio di
	// PresentazioneDomanda per allegarlo al messaggio stesso
	File tempDir = new File(AttachmentsUtils.getSystemTempDir(), FACCTConstants.GENERA_ALLEGATI_NOTIFICA_TEMP_SUBDIR_NAME);
	tempDir = new File(tempDir, idDomanda);
	File distintaModelloFile = new File(tempDir, idDomanda + ".SUAP.XML");
	try {
	    if (!distintaModelloFile.isFile()) {
		distintaModelloFile.createNewFile();
	    }
	    String xml = XmlUtils.marshallObject(messaggioPresentazione);
	    Schema schema = XmlUtils.getSchemaForMessage("cart/schema/RFC183_ComunicazioniInterneSUAP_presentazioneDomanda_FRU_e22.xsd");
	    // XmlUtils.validaXml(xml, schema);
	    AttachmentsUtils.writeBytesToStream(xml.getBytes("UTF-8"), new FileOutputStream(distintaModelloFile));
	    retFiles.add(distintaModelloFile);
	} catch (Exception e) {
	    String errMsg = "errore nella scrittura del file " + distintaModelloFile.getAbsolutePath();
	    log.error("completaMessaggioPresentazioneDomanda - {}", errMsg);
	    throw new Exception(errMsg, e);
	}
	// ne creo anche una versione in pdf
	try {
	    byte[] xmlBytes = AttachmentsUtils.readBytesFromFile(distintaModelloFile);
	    String xmlString = new String(xmlBytes);
	    FileConverterWsClient fileConverterWService = new FileConverterWsClient();
	    ConvertRequest cReq = new ConvertRequest(ORMHelper.getToken(), xmlString, FileConverterWsClient.ContentType.TXT.name(),
		    FileConverterWsClient.ConversionType.PDF.name());
	    ConvertResponse cResp = fileConverterWService.convert(cReq);
	    distintaModelloFile = new File(tempDir, idDomanda + ".SUAP.PDF");
	    AttachmentsUtils.writeBytesToStream(cResp.getBinaryData(), new FileOutputStream(distintaModelloFile));
	    retFiles.add(distintaModelloFile);
	} catch (Exception e) {
	    String errMsg = "errore nella scrittura del file " + distintaModelloFile.getAbsolutePath();
	    log.error("completaMessaggioPresentazioneDomanda - {}", errMsg);
	    throw new Exception(errMsg, e);
	}
	return retFiles.toArray(new File[retFiles.size()]);
    }

    @Override
    public List<DocumentiistanzaDTO> salvaAllegatiCart(Integer codiceIstanza, File[] files) throws Exception {

	Istanze istanza = this.istanzeService.findById(new PkId(codiceIstanza));
	List<Documentiistanza> allegati = this.documentiistanzaService.findByIstanza(codiceIstanza);
	List<DocumentiistanzaDTO> retDocs = new ArrayList<DocumentiistanzaDTO>();
	Documentiistanza dbDoc = null;
	for (int i = 0; i < files.length; i++) {
	    File file = files[i];
	    byte[] fileData = AttachmentsUtils.readBytesFromFile(file);
	    boolean fileFound = false;
	    dbDoc = null;
	    // per ciascun file verifico se esiste già un'allegato istanza con
	    // quel nome.
	    for (Documentiistanza allegato : allegati) {
		// se esiste ne aggiorno solo il contenuto Binario in OGGETTI
		if (allegato.getOggetto() != null && allegato.getOggetto().getNomefile().equalsIgnoreCase(file.getName())) {
		    fileFound = true;
		    Oggetti allegatoBin = allegato.getOggetto();
		    allegatoBin.setOggetto(fileData);
		    this.oggettiService.update(allegatoBin);
		    dbDoc = allegato;
		    if (log.isDebugEnabled()) {
			log.debug("salvaAllegatiCart - aggiornati i dati binari dell'allegato {} con il contenuto del file {} per l'istanza {}",
				new Object[] { allegato.getDocumento(), file.getAbsolutePath(), codiceIstanza });
		    }
		    break;
		}
	    }
	    if (!fileFound) {
		// l'allegato non esiste:
		// 1: inserisco in OGGETTI
		Oggetti allegatoBin = new Oggetti();
		allegatoBin.setNomefile(file.getName());
		allegatoBin.setOggetto(fileData);
		this.oggettiService.insert(allegatoBin);
		if (log.isDebugEnabled()) {
		    log.debug("salvaAllegatiCart - inserito in OGGETTI il contenuto del file {} con id {}", new Object[] { file.getAbsolutePath(),
			    allegatoBin.getId().getCodice() });
		}
		// 2: inserisco in DOCUMENTIISTANZ
		Documentiistanza newAllegato = new Documentiistanza();
		newAllegato.setIstanza(istanza);
		newAllegato.setOggetto(allegatoBin);
		newAllegato.setPresente(true);
		newAllegato.setData(new Date());
		newAllegato.setDocumento(file.getName());
		//newAllegato.setAlberoprocDocumenticat(alberoprocDocumenticat);
		this.documentiistanzaService.insert(newAllegato);
		dbDoc = newAllegato;
		if (log.isDebugEnabled()) {
		    log.debug("salvaAllegatiCart - inserito l'allegato {} collegato all'oggetto {} per l'istanza {}",
			    new Object[] { newAllegato.getDocumento(), allegatoBin.getId().getCodice(), codiceIstanza });
		}
	    }
	    if (dbDoc != null) {
		retDocs.add(getDTOFromDocumentiistanza(dbDoc));
	    }
	}
	return retDocs;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.CartMappingService#popolaAllegatiDomandaCart(it.gruppoinit.pal.gp.core.domain.Istanze, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart)
     */
    @Override
    public void popolaAllegatiDomandaCart(Istanze istanze, DatiDomandaCart datiDomanda, List<DocumentiType> listaDocumenti) throws Exception {

	int docCount = 0;
	if (istanze != null && datiDomanda != null) {
	    Oggetti doc = null;
	    boolean added = false;
	    for (DocumentiType dt : listaDocumenti) {
		if (dt != null) {
		    if (dt.getAllegati() != null) {
			if (StringUtils.isNotBlank(dt.getAllegati().getId())) {
			    String codiceoggettostr = dt.getAllegati().getId();
			    if (Utilities.isInteger(codiceoggettostr.trim())) {
				Integer codiceOggetto = Integer.parseInt(codiceoggettostr.trim());
				doc = oggettiService.findByIdLazy(new PkId(codiceOggetto));
				if (doc != null && !isAllegatoCart(doc.getNomefile())) {
				    added = popolaAllegatoDomandaCart(doc, dt.getDocumento(), datiDomanda);
				    if (added) {
					docCount++;
				    }
				}
			    }
			}
		    }
		}
	    }
	    //recupero i dati da Documentiistanza 
	    //imposto l'id semantico NUMERO_ALLEGATI
	    datiDomanda.setValoreScalare(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_NUMERO_ALLEGATI, "" + docCount);
	    //imposto l'id semantico DATA_NOTIFICA
	    datiDomanda.setValoreScalare(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_DATA_NOTIFICA,
		    Utilities.formatDate(new Date(), false));
	    //imposto l'id semantico IDENTIFICATIVO_PRATICA
	    datiDomanda.setValoreScalare(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_IDENTIFICATIVO_PRATICA,
		    this.cartPresentazioneDomandaService.generaIdentificativoPraticaCART(istanze));
	}
    }

    @DeletableCacheElements
    public void clearMappingsCache() {

	this.cartMappingsCache.clear();
    }

    private DocumentiistanzaDTO getDTOFromDocumentiistanza(Documentiistanza doc) {

	DocumentiistanzaDTO dto = null;
	if (doc != null) {
	    dto = new DocumentiistanzaDTO();
	    dto.setId(new PkId(doc.getId().getCodice()));
	    Integer codIstanza = doc.getIstanza() != null ? doc.getIstanza().getId().getCodice() : null;
	    if (codIstanza != null) {
		dto.setCodiceIstanza(codIstanza);
		dto.setCodicecomune(doc.getIstanza().getComune().getCodicecomune());
	    }
	    if (doc.getOggetto() != null) {
		dto.setCodiceOggetto(doc.getOggetto().getId().getCodice());
		dto.setDimensioneFile(doc.getOggetto().getDimensioneFile());
		dto.setNomeFile(doc.getOggetto().getNomefile());
	    }
	    java.sql.Date dtoDate = doc.getData() != null ? new java.sql.Date(doc.getData().getTime()) : null;
	    dto.setData(dtoDate);
	    dto.setNecessario(doc.getNecessario());
	    dto.setDocumento(doc.getDocumento());
	    dto.setFlgDaModelloDinamico(doc.getFlgDaModelloDinamico());
	    dto.setIdBase(doc.getIdBase());
	    dto.setIdDocer(doc.getIdDocer());
	    dto.setNecessario(doc.getNecessario());
	    dto.setNote(doc.getNote());
	    dto.setPresente(doc.getPresente());
	    dto.setStcIdallegato(doc.getStcIdallegato());
	    dto.setStcIddocumento(doc.getStcIddocumento());
	    dto.setControllook(doc.getControllook());
	}
	return dto;
    }

    /*
     * Distingue gli allegati previsti dallo standard CART in base al nome del file
     */
    private boolean isAllegatoCart(String nomeFile) {

	boolean isCartFile = false;
	if (StringUtils.isNotBlank(nomeFile)) {
	    nomeFile = nomeFile.toUpperCase();
	    if (nomeFile.endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_RIEPILOGO_SUFFIX)
		    || nomeFile.endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_DISTINTA_MODELLO_RIEPILOGO_SUFFIX)
		    || nomeFile.endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_ATTIVITA_SUFFIX)
		    || nomeFile.endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_STD0_SUFFIX)
		    || nomeFile.endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_STD2_SUFFIX)) {
		isCartFile = true;
	    }
	}
	return isCartFile;
    }

    private boolean popolaAllegatoDomandaCart(Oggetti doclazy, String descr, DatiDomandaCart datiDomanda) {

	boolean added = false;
	if (datiDomanda != null && doclazy != null) {
	    //verifico che nei dati della domanda non ci sia già un'allegato con lo stesso codiceoggetto
	    Integer codiceOggetto = doclazy.getId().getCodice();
	    FileInfo fInfo = datiDomanda.getAllegatoByCodiceOggetto(codiceOggetto);
	    if (fInfo == null) {
		//imposto la descrizione dell'allegato nell'id semantico ALLEGATI.MODULO-CODICE 
		List<ValoreIdSemantico> values = datiDomanda.getValoriIndicizzati(FACCTConstants.STANDARD_0,
			FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_CODICE);
		if (values == null) {
		    values = new ArrayList<ValoreIdSemantico>();
		    datiDomanda.setValoriIndicizzati(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_CODICE, values);
		}
		ValoreIdSemantico value = new ValoreIdSemantico(descr);
		values.add(value);
		//e in ALLEGATI.MODULO-DESCRIZIONE 
		values = datiDomanda.getValoriIndicizzati(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_DESCRIZIONE);
		if (values == null) {
		    values = new ArrayList<ValoreIdSemantico>();
		    datiDomanda.setValoriIndicizzati(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_DESCRIZIONE, values);
		}
		value = new ValoreIdSemantico(descr);
		values.add(value);
		//imposto il codice oggetto dell'allegato nell'id semantico ALLEGATI.MODULO-ALLEGATO
		value = datiDomanda.getValoreIdSemantico(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO);
		if (value == null) {
		    value = new ValoreIdSemantico(new ArrayList<ValoreIdSemantico>());
		    value.setFile(true);
		    datiDomanda.setValoreIdSemantico(FACCTConstants.STANDARD_0, FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO, value);
		}
		values = value.getValoreIndicizzato();
		String[] valueVector = new String[] { codiceOggetto.toString() };
		value = new ValoreIdSemantico(valueVector);
		value.setFile(true);
		values.add(value);
		//aggiungo un fileInfo ai dati della domanda
		fInfo = new FileInfo();
		fInfo.setIdOggetto(codiceOggetto);
		fInfo.setIdSemantico(FACCTConstants.ID_SEMANTICO_ALLEGATI_MODULO_ALLEGATO);
		fInfo.setDimensione(doclazy.getDimensioneFile());
		fInfo.setNomeFile(doclazy.getNomefile());
		fInfo.setFirmaValidata(true);
		datiDomanda.addAllegato(fInfo);
		added = true;
	    }
	}
	return added;
    }
}
