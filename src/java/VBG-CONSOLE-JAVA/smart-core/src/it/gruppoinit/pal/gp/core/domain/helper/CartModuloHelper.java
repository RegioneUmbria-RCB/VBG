package it.gruppoinit.pal.gp.core.domain.helper;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.ColonnaType;
import it.eng.suap.xengine.model.modulistica.EnumItemType;
import it.eng.suap.xengine.model.modulistica.EnumerazioneType;
import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.eng.suap.xengine.model.modulistica.ItemType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.modulistica.NumericoType;
import it.eng.suap.xengine.model.modulistica.OperandoType;
import it.eng.suap.xengine.model.modulistica.OperatoreLogicoType;
import it.eng.suap.xengine.model.modulistica.OperatoreType;
import it.eng.suap.xengine.model.modulistica.OperazioneType;
import it.eng.suap.xengine.model.modulistica.QuadroType;
import it.eng.suap.xengine.model.modulistica.RangeType;
import it.eng.suap.xengine.model.modulistica.RiferimentoType;
import it.eng.suap.xengine.model.modulistica.SezioneType;
import it.eng.suap.xengine.model.modulistica.TabellaType;
import it.eng.suap.xengine.model.modulistica.TestoType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerApplication;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerApplicationConfig;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerDefinition;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerResultMapppings;
import it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOption;
import it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions;
import it.gruppoinit.pal.gp.core.domain.autocompiler.SearchResultAttributeMapping;
import it.gruppoinit.pal.gp.core.domain.autocompiler.UsageType;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.DatiModulo;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.cart.QuadroDinamicoType;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import bsh.This;

public class CartModuloHelper {

    private static final Logger log = LoggerFactory.getLogger(CartModuloHelper.class);
    // private static final String INDEXING_SUFFIX = "_row_";
    private static final String REPLACE_WITH_UNDERSCORE_PATTERN = "\\s";
    private static final String REPLACE_WITH_MINUS_PATTERN = "\\p{Punct}";
    private static final String REPLACE_WITH_A_PATTERN = "[à]";// TODO aggiungere altre a accentate
    private static final String REPLACE_WITH_O_PATTERN = "[ò]";// TODO aggiungere altre o accentate
    private static final String REPLACE_WITH_E_PATTERN = "[èé]";// TODO aggiungere altre e accentate
    private static final String REPLACE_WITH_I_PATTERN = "[ì]";// TODO aggiungere altre i accentate
    private static final String REPLACE_WITH_U_PATTERN = "[ù]";// TODO aggiungere altre u accentate
    private static final String CSS_SELECTOR_METACHARACTERS_PATTERN = "[\\./:;<=>\\[\\]]";
    private static final String JS_VARIABLE_NAME_INVALID_START_CHARACTERS_PATTERN = "[\\s\\.\\-\\d]";
    private static final String JS_VARIABLE_NAME_INVALID_CHARACTERS_PATTERN = "[\\s\\.-]";
    private static final String SEGNAPOSTO_PATTERN = "(\\[[^\\[\\]]+\\]){1}?";
    private static final String SEGNAPOSTO_DOMANDA_PREFIX = "FO_DOMANDE";
    // TODO completare l'espressione regolare che sostiutuisce con '_' negli id
    // semantici i caratteri che non sono validi all'interno del nome di una
    // variabile Javascript
    private static final Pattern REPLACE_WITH_UNDERSCORE_REGEX;
    private static final Pattern REPLACE_WITH_MINUS_REGEX;
    private static final Pattern REPLACE_WITH_A_REGEX;
    private static final Pattern REPLACE_WITH_O_REGEX;
    private static final Pattern REPLACE_WITH_E_REGEX;
    private static final Pattern REPLACE_WITH_I_REGEX;
    private static final Pattern REPLACE_WITH_U_REGEX;
    private static final Pattern CSS_SELECTOR_METACHARACTERS_REGEX;
    private static final Pattern JS_VARIABLE_NAME_INVALID_START_CHARACTERS_REGEX;
    private static final Pattern JS_VARIABLE_NAME_INVALID_CHARACTERS_REGEX;
    private static final Pattern SEGNAPOSTO_REGEX;
    static {
	REPLACE_WITH_UNDERSCORE_REGEX = Pattern.compile(REPLACE_WITH_UNDERSCORE_PATTERN);
	REPLACE_WITH_MINUS_REGEX = Pattern.compile(REPLACE_WITH_MINUS_PATTERN);
	REPLACE_WITH_A_REGEX = Pattern.compile(REPLACE_WITH_A_PATTERN);
	REPLACE_WITH_O_REGEX = Pattern.compile(REPLACE_WITH_O_PATTERN);
	REPLACE_WITH_E_REGEX = Pattern.compile(REPLACE_WITH_E_PATTERN);
	REPLACE_WITH_I_REGEX = Pattern.compile(REPLACE_WITH_I_PATTERN);
	REPLACE_WITH_U_REGEX = Pattern.compile(REPLACE_WITH_U_PATTERN);
	CSS_SELECTOR_METACHARACTERS_REGEX = Pattern.compile(CSS_SELECTOR_METACHARACTERS_PATTERN);
	JS_VARIABLE_NAME_INVALID_START_CHARACTERS_REGEX = Pattern.compile(JS_VARIABLE_NAME_INVALID_START_CHARACTERS_PATTERN);
	JS_VARIABLE_NAME_INVALID_CHARACTERS_REGEX = Pattern.compile(JS_VARIABLE_NAME_INVALID_CHARACTERS_PATTERN);
	SEGNAPOSTO_REGEX = Pattern.compile(SEGNAPOSTO_PATTERN);
    }
    private static final int SELECT_OPTION_MAXCHARACTERS = 80;
    private static final int TEXT_INPUT_MAXSIZE = 80;
    private ModuloType modulo;
    private DatiDomandaCart inputData;
    private String idModulo;
    private String idEndo;
    private List<String> jsCache = new ArrayList<String>();
    private Map<String, List<Integer>> idCache = new HashMap<String, List<Integer>>();
    private Deque<ContainerWrapper> containerStack = new ArrayDeque<ContainerWrapper>();
    private AutocompilerConfig autocompilerConfig;
    private Boolean flagBackoffice = null;
    private OggettiService oggettiService;
    private Set<TabellaType> renderedTables = new HashSet<TabellaType>();

    public CartModuloHelper(ModuloType modulo, DatiDomandaCart inputData) {

	this.modulo = modulo;
	this.inputData = inputData;
    }

    /**
     * @return the modulo
     */
    public ModuloType getModulo() {

	return modulo;
    }

    /**
     * @param modulo
     *            the modulo to set
     */
    public void setModulo(ModuloType modulo) {

	this.modulo = modulo;
    }

    /**
     * @return the inputData
     */
    public DatiDomandaCart getInputData() {

	return inputData;
    }

    /**
     * @param inputData
     *            the inputData to set
     */
    public void setInputData(DatiDomandaCart inputData) {

	this.inputData = inputData;
    }

    /**
     * @return the autocompilerConfig
     */
    public AutocompilerConfig getAutocompilerConfig() {

	return autocompilerConfig;
    }

    /**
     * @param autocompilerConfig
     *            the autocompilerConfig to set
     */
    public void setAutocompilerConfig(AutocompilerConfig autocompilerConfig) {

	this.autocompilerConfig = autocompilerConfig;
    }

    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    public List<String> getJsCache() {

	return this.jsCache;
    }

    public void clearJsCache() {

	this.jsCache = new ArrayList<String>();
    }

    public void addScriptToCache(String jsText) {

	this.jsCache.add(jsText);
    }

    public String getIdModulo() {

	// §§§BEGIN§§§
	if (StringUtils.isBlank(this.idModulo)) {
	    String retVal = buildIdModuloFromRefModulo(getRiferimentoModulo(this.modulo));
	    this.idModulo = retVal;
	}
	// §§§END§§§
	return this.idModulo;
    }

    
    public String getIdEndo() {
    
        return idEndo;
    }

    
    public void setIdEndo(String idEndo) {
    
        this.idEndo = idEndo;
    }

    public String getRiferimentoModulo() {

	return CartModuloHelper.getRiferimentoModulo(this.modulo);
    }

    @SuppressWarnings("unused")
    public void debug(Object o) {

	if (o != null) {
	    String y = o.toString();
	}
    }

    @SuppressWarnings("unused")
    public void debug(Object o1, Object o2, Object o3) {

	if (o1 != null) {
	    String y = o1.toString();
	}
    }

    @SuppressWarnings("unused")
    public void debug(Object o, ItemType item) {

	if (o != null) {
	    if (o instanceof Dyn2Regole) {
		Dyn2Regole r = (Dyn2Regole) o;
	    }
	    String y = o.toString();
	}
    }

    public String getUniqueId(String id, Object moduleElement) {

	String retVal = id;
	Integer hash = moduleElement.hashCode();
	List<Integer> ids = null;
	if (this.idCache.containsKey(id)) {
	    ids = this.idCache.get(id);
	    StringBuilder sbid = new StringBuilder(id);
	    int idIndex = ids.indexOf(hash);
	    if (idIndex == -1) {
		ids.add(hash);
		idIndex = ids.size() - 1;
	    }
	    if (idIndex > 0) {
		sbid.append(FACCTConstants.HTML_UNIQUE_ID_SEPARATOR).append(idIndex);
	    }
	    retVal = sbid.toString();
	} else {
	    ids = new ArrayList<Integer>();
	    ids.add(hash);
	    this.idCache.put(id, ids);
	}
	return retVal;
    }

    public String getUniqueIdSeparator() {

	return FACCTConstants.HTML_UNIQUE_ID_SEPARATOR;
    }

    public int countUniqueIds(String id) {

	int retval = 0;
	if (this.idCache.containsKey(id)) {
	    List<Integer> numIds = this.idCache.get(id);
	    retval = numIds.size();
	}
	return retval;
    }

    public String getIdCounterSuffix(String id) {

	StringBuilder sbId = new StringBuilder();
	int numDuplicati = this.countUniqueIds(id);
	if (numDuplicati > 1) {
	    sbId.append(FACCTConstants.HTML_UNIQUE_ID_SEPARATOR).append(numDuplicati - 1);
	}
	return sbId.toString();
    }

    public void useIdCache(Map<String, List<Integer>> idCache) {

	this.idCache = idCache;
    }

    public Map<String, List<Integer>> getIdCache() {

	return this.idCache;
    }

    public boolean isTableRendered(TabellaType tabella) {

	return this.renderedTables.add(tabella);
    }

    /*
     * public String generaIdQuadro(QuadroType quadro){
     * 
     * String retVal = quadro. }
     */
    public String getIdModuloForJsVariableName() {

	return escapeForJsVariableName(getIdModulo());
    }

    /**
     * Restituisce un {@link QuadroType} di cui si specifica l'id ed il modulo di appartenenza
     * 
     * @param modulisticaResponse
     * @param refModulo
     * @param idQuadro
     * @return
     */
    public static QuadroType findQuadro(ModulisticaContentType modulisticaResponse, String refModulo, String idQuadro) {

	// individuo il modulo
	ModuloType modulo = null;
	QuadroType quadro = null;
	// §§§BEGIN§§§
	List<ModuloType> moduli = modulisticaResponse.getModulistica().getModulo();
	for (ModuloType moduloTemp : moduli) {
	    if (getRiferimentoModulo(moduloTemp).equals(refModulo)) {
		modulo = moduloTemp;
		break;
	    }
	}
	if (null != modulo) {
	    // individuo il quadro all'interno del modulo
	    List<QuadroType> quadriModulo = modulo.getQuadro();
	    for (QuadroType quadroTemp : quadriModulo) {
		if (getIdQuadro(quadroTemp).equals(idQuadro)) {
		    quadro = quadroTemp;
		    break;
		}
	    }
	    if (null == quadro) {
		String errMsg = MessageFormat.format("impossibile trovare il quadro con id {0} all''interno del modulo {1}.", idQuadro, refModulo);
		log.warn("findQuadro() - {}", errMsg);
	    }
	} else {
	    String errMsg = MessageFormat.format("impossibile trovare un modulo dal titolo {0}.", refModulo);
	    log.warn("findQuadro() - {}", errMsg);
	}
	// §§§END§§§
	return quadro;
    }

    /**
     * Restituisce un {@link QuadroType} di cui si specifica l'id ed il modulo di appartenenza nel modulo di Tipo STAR
     * 
     * @param modulisticaResponse
     * @param refModulo
     * @param idQuadro
     * @return
     */
    public static QuadroType findQuadroXMLModulisticaSTAR(ModulisticaSTAR modulisticaSTAR, String refModulo, String idQuadro) {

	// individuo il modulo	
	if (modulisticaSTAR != null) {
	    if (modulisticaSTAR.getModuli() != null && modulisticaSTAR.getModuli().size() > 0) {
		ModuloType mod = modulisticaSTAR.getModuli().get(refModulo);
		if (mod != null) {
		    List<QuadroType> qs = mod.getQuadro();
		    for (QuadroType qt : qs) {
			String quadroId = CartModuloHelper.getIdQuadro(qt);
			if (StringUtils.defaultString(quadroId).equals(idQuadro) && !(qt instanceof QuadroDinamicoType)) {
			    return qt;
			}
		    }
		}
		/*
		if (modulisticaSTAR.getModuli().get(refModulo) != null) {
		// verifico se quadro_xml presente
		HashMap<String, Modulo186ListWrapper> m186 = modulisticaSTAR.getModelli186();
		if (m186 != null && m186.size() > 0) {
		    Modulo186ListWrapper mw = m186.get(refModulo);
		    if (mw != null) {
			if (mw.getQuadro() != null && mw.getQuadro().size() > 0) {
			    List<QuadroType> qs = mw.getQuadro();
			    for (QuadroType qt : qs) {
				String quadroId = CartModuloHelper.getIdQuadro(qt);
				if (StringUtils.defaultString(quadroId).equals(idQuadro)) {
				    return qt;
				}
			    }
			}
		    }
		}
		}
		*/
	    }
	}
	return null;
    }

    /**
     * Restituisce il riferimento al modello dinamico nella forma idcomune-codicemodello nel modulo di Tipo STAR
     * 
     * @param modulisticaResponse
     * @param refModulo
     * @param idQuadro
     * @return
     */
    public static String findQuadroDinamicoSTAR(ModulisticaSTAR modulisticaSTAR, String refModulo, String idQuadro) {

	String retVal = null;
	if (modulisticaSTAR != null) {
	    if (modulisticaSTAR.getModuli() != null) {
		if (modulisticaSTAR.getModuli().size() > 0) {
		    if (modulisticaSTAR.getModuli().get(refModulo) != null) {
			HashMap<String, ModuloType> mnm = modulisticaSTAR.getModuli();
			if (mnm != null) {
			    ModuloType mt = mnm.get(refModulo);
			    if (mt != null) {
				List<QuadroType> quadri = mt.getQuadro();
				for (QuadroType quadro : quadri) {
				    if (quadro instanceof QuadroDinamicoType) {
					QuadroDinamicoType qdt = (QuadroDinamicoType) quadro;
					String idQuadroCheck = qdt.getIdComune() + "-" + qdt.getIdModello();
					if (idQuadroCheck.equalsIgnoreCase(idQuadro)) {
					    retVal = idQuadro;
					    break;
					}
				    }
				}
			    }
			}
			if (StringUtils.isEmpty(retVal)) {
			    HashMap<String, ModellidinamiciHelperListWrapper> mm = modulisticaSTAR.getModuliModelli();
			    if (mm != null) {
				ModellidinamiciHelperListWrapper mdw = mm.get(refModulo);
				if (mdw != null && mdw.getModuliModelli() != null && mdw.getModuliModelli().size() > 0) {
				    List<String> mmids = mdw.getModuliModelli();
				    for (String mmid : mmids) {
					mmid = mmid.replaceAll(" ", "_");
					if (mmid.equalsIgnoreCase(idQuadro)) {
					    retVal = mmid;
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return retVal;
    }

    /**
     * Restituisce un {@link ModuloType} in base al nome identificativo
     * 
     * @param modulistica
     * @param nomeRiferimento
     * @return
     */
    public static ModuloType findModuloByRiferimento(ModulisticaContentType modulistica, String nomeRiferimento) {

	ModuloType retModulo = null;
	// §§§BEGIN§§§
	List<ModuloType> moduli = modulistica.getModulistica().getModulo();
	for (ModuloType modulo : moduli) {
	    RiferimentoType ref = modulo.getRiferimento();
	    String codiceModello = ref.getCodiceModello();
	    String codiceEndo = ref.getCodiceEndoProcedimento();
	    if (StringUtils.isNotEmpty(codiceModello)) {
		if (nomeRiferimento.equalsIgnoreCase(codiceModello)) {
		    retModulo = modulo;
		    break;
		}
	    } else if (StringUtils.isNotEmpty(codiceEndo)) {
		if (nomeRiferimento.equalsIgnoreCase(codiceEndo)) {
		    retModulo = modulo;
		    break;
		}
	    }
	}
	// §§§END§§§
	return retModulo;
    }

    /**
     * Restituisce un'istanza di {@link ItemType} associata all'id semantico specificato. Il campo viene cercato solo
     * all'interno del modulo e del quadro specificati in refModulo e idQuadro.
     * 
     * @param modulisticaResponse
     * @param refModulo
     * @param idQuadro
     * @param idSemantico
     * @param tablesStack
     * @return
     */
    public static ItemType findCampoByIdSemantico(ModulisticaContentType modulisticaResponse, String refModulo, String idQuadro, String idSemantico,
	    Deque<TabellaType> tablesStack) {

	ItemType retCampo = null;
	QuadroType quadro = findQuadro(modulisticaResponse, refModulo, idQuadro);
	if (null != quadro) {
	    List<ItemType> items = quadro.getItem();
	    for (ItemType item : items) {
		retCampo = findItemByIdSemanticoRecursive(item, idSemantico, tablesStack);
		if (retCampo != null) {
		    break;
		}
	    }
	}
	return retCampo;
    }

    /**
     * Restituisce un'istanza di {@link ItemType} associata all'id semantico specificato. Il campo viene cercato
     * all'interno di tutti i quadri di tutti i moduli della modulistica. L'argomento tablesStack deve essere passato
     * come riferimento <code>new ArrayDeque&lt;TabellaType&gt;()</code> , se il campo restituito è previsto che sia
     * indicizzato allora all'uscita del metodo si avrà tablesStack.isEmpty() == false.
     * 
     * @param modulisticaResponse
     * @param idSemantico
     * @return
     */
    public static ItemType searchCampoByIdSemantico(ModulisticaContentType modulisticaResponse, String idSemantico, Deque<TabellaType> tablesStack) {

	ItemType retCampo = null;
	List<ModuloType> moduli = modulisticaResponse.getModulistica().getModulo();
	for (ModuloType modulo : moduli) {
	    List<QuadroType> quadri = modulo.getQuadro();
	    for (QuadroType quadro : quadri) {
		List<ItemType> items = quadro.getItem();
		for (ItemType item : items) {
		    retCampo = findItemByIdSemanticoRecursive(item, idSemantico, tablesStack);
		    if (retCampo != null) {
			return retCampo;
		    }
		}
	    }
	}
	return retCampo;
    }

    private static ItemType findItemByIdSemanticoRecursive(ItemType item, String idsemantico, Deque<TabellaType> tables) {

	ItemType retItem = null;
	if (item.getSezione() != null) {
	    SezioneType section = item.getSezione();
	    List<ItemType> sectionItems = section.getItem();
	    for (ItemType sectionItem : sectionItems) {
		retItem = findItemByIdSemanticoRecursive(sectionItem, idsemantico, tables);
		if (retItem != null) {
		    break;
		}
	    }
	} else if (item.getTabella() != null) {
	    TabellaType table = item.getTabella();
	    List<ColonnaType> columns = table.getColonna();
	    tables.push(table);
	    for (ColonnaType column : columns) {
		ItemType cell = column.getCella();
		retItem = findItemByIdSemanticoRecursive(cell, idsemantico, tables);
		if (retItem != null) {
		    return retItem;
		}
	    }
	    tables.pop();
	} else if (item.getCampo() != null) {
	    CampoType textField = item.getCampo();
	    if (idsemantico.equals(textField.getIdSemantico())) {
		retItem = item;
	    }
	} else if (item.getFile() != null) {
	    FileType fileField = item.getFile();
	    if (idsemantico.equals(fileField.getIdSemantico())) {
		retItem = item;
	    }
	}
	return retItem;
    }

    /**
     * Il metodo è utilizzato per recuperare la descrizione del campo file passato come primo argomento. nel caso in cui
     * il campo non abbia etichetta e si trovi in una tabella (secondo argomento != null) viene recuperata l'etichetta
     * della colonna in cui si trova il campo a meno che nella colonna non ci siano anche altri campi.
     */
    public static String getEtichettaPerCampoFile(FileType campoFile, TabellaType tabella) {

	String label = null;
	label = campoFile.getTitolo();
	if (StringUtils.isEmpty(label) && tabella != null) {
	    List<ColonnaType> cols = tabella.getColonna();
	    List<ItemType> fields = null;
	    for (ColonnaType col : cols) {
		if (StringUtils.isNotBlank(col.getTitolo())) {
		    fields = new ArrayList<ItemType>();
		    collectFields(col.getCella(), fields);
		    // se la colonna contiene anche altri campi non può essere
		    // utilizzata come etichetta per il campo singolo
		    if (fields.size() == 1) {
			ItemType field = fields.get(0);
			if (field.getFile() != null && campoFile.equals(field.getFile())) {
			    label = col.getTitolo();
			    break;
			}
		    }
		}
	    }
	    if (StringUtils.isEmpty(label) && tabella != null && tabella.getColonna().size() == 1) {
		label = tabella.getTitolo();
	    }
	}
	return label;
    }

    private static void collectFields(ItemType item, List<ItemType> collectedFields) {

	if (item.getCampo() != null || item.getFile() != null) {
	    collectedFields.add(item);
	} else if (item.getSezione() != null) {
	    SezioneType sezione = item.getSezione();
	    List<ItemType> items = sezione.getItem();
	    for (ItemType itm : items) {
		collectFields(itm, collectedFields);
	    }
	} else if (item.getTabella() != null) {
	    TabellaType tabella = item.getTabella();
	    List<ColonnaType> colonne = tabella.getColonna();
	    for (ColonnaType colonna : colonne) {
		collectFields(colonna.getCella(), collectedFields);
	    }
	}
    }

    public static String getIdQuadro(QuadroType quadro) {

	StringBuilder sb = new StringBuilder();
	if (null != quadro) {
	    if (quadro instanceof QuadroDinamicoType) {
		return getIdQuadroDinamico(((QuadroDinamicoType) quadro).getQuadroHelper());
	    } else {
		sb.append(quadro.getId() != null ? quadro.getId() : "X");
		sb.append("-");
		sb.append(quadro.getCodice() != null ? quadro.getCodice() : "Y");
	    }
	}
	return sb.toString();
    }

    public static String getIdTabella(TabellaType tabella, int numIdDuplicati, IndiceIdSemantico indice) {

	StringBuilder sb = new StringBuilder();
	if (null != tabella) {
	    sb.append(tabella.getId());
	    if (numIdDuplicati > 1) {
		sb.append(".").append(numIdDuplicati - 1);
	    }
	    if (null != indice && indice.contaLivelli() > 1) {
		sb.append("_i");
		for (int i = 0; i < indice.contaLivelli() - 1; i++) {
		    sb.append("_").append(indice.getIndicePerLivello(i));
		}
	    }
	}
	return sb.toString();
    }

    public static String getIdTabella(TabellaType tabella, int numIdDuplicati, Integer[] prevLevelsIndexes) {

	StringBuilder sb = new StringBuilder();
	if (null != tabella) {
	    sb.append(tabella.getId());
	    if (numIdDuplicati > 0) {
		sb.append(".").append(numIdDuplicati - 1);
	    }
	    if (null != prevLevelsIndexes && prevLevelsIndexes.length > 0) {
		sb.append("_i");
		for (int i = 0; i < prevLevelsIndexes.length; i++) {
		    sb.append("_").append(prevLevelsIndexes[i]);
		}
	    }
	}
	return sb.toString();
    }

    public static String getIdTabellaWhithPlaceholder(TabellaType tabella, int numIdDuplicati, IndiceIdSemantico indice, String placeholder) {

	StringBuilder sb = new StringBuilder();
	if (null != tabella) {
	    sb.append(tabella.getId());
	    if (numIdDuplicati > 1) {
		sb.append(".").append(numIdDuplicati - 1);
	    }
	    if (null != indice && indice.contaLivelli() > 1) {
		sb.append("_i");
		for (int i = 0; i < indice.contaLivelli() - 2; i++) {
		    sb.append("_").append(indice.getIndicePerLivello(i));
		}
		sb.append("_").append(placeholder);
	    }
	}
	return sb.toString();
    }

    public static String buildIndexingSuffix(IndiceIdSemantico indice) {

	return buildIndexingSuffix(indice, "_row_");
    }

    public static String buildIndexingSuffix(IndiceIdSemantico indice, String prefix) {

	StringBuilder sb = new StringBuilder();
	if (indice != null) {
	    Integer[] indexes = indice.vettoreIndici();
	    if (indexes.length > 0) {
		sb.append(prefix);
	    }
	    for (int i = 0; i < indexes.length; i++) {
		if (i > 0) {
		    sb.append("_");
		}
		sb.append(indexes[i]);
	    }
	}
	return sb.toString();
    }

    public static String buildIndexingSuffixWhithPlaceholder(IndiceIdSemantico indice, String placeholder, Integer templateNestingLevel) {

	return buildIndexingSuffixWhithPlaceholder(indice, placeholder, templateNestingLevel, "_row_");
    }

    public static String buildIndexingSuffixWhithPlaceholder(IndiceIdSemantico indice, String placeholder, Integer templateNestingLevel,
	    String suffixSeparator) {

	StringBuilder sb = new StringBuilder();
	if (indice != null) {
	    Integer[] indexes = indice.vettoreIndici();
	    if (indexes.length > 0 && StringUtils.isNotEmpty(suffixSeparator)) {
		sb.append(suffixSeparator);
	    }
	    for (int i = 0; i < indexes.length; i++) {
		if (i > 0) {
		    sb.append("_");
		}
		if (i <= templateNestingLevel) {
		    try {
			String replaced = MessageFormat.format(placeholder, "[" + i + "]");
			sb.append(replaced);
		    } catch (Exception e) {
			log.error("buildIndexingSuffixWhithPlaceholder({},{},{}) - ERRORE durante la creazione del placeholder per il template: {}",
				new Object[] { indice, placeholder, templateNestingLevel, e });
		    }
		} else {
		    sb.append(indexes[i]);
		}
	    }
	}
	return sb.toString();
    }

    public static String getIndexAsStringWithPlaceholder(IndiceIdSemantico indice, String placeholder, Integer templateNestingLevel) {

	StringBuilder sb = new StringBuilder();
	if (indice != null) {
	    Integer[] indexes = indice.vettoreIndici();
	    if (indexes.length > 0) {
		sb.append("[\" + ");
		for (int i = 0; i < indexes.length; i++) {
		    if (i <= templateNestingLevel) {
			if (i > 0) {
			    sb.append(" + \",\" + ");
			}
			try {
			    String replaced = MessageFormat.format(placeholder, "[" + i + "]");
			    sb.append(replaced);
			} catch (Exception e) {
			    log.error("getIndexAsStringWithPlaceholder({},{},{}) - ERRORE durante la creazione del placeholder per il template: {}",
				    new Object[] { indice, placeholder, templateNestingLevel, e });
			}
		    } else {
			sb.append(indexes[i]);
		    }
		}
		sb.append(" + \"]");
	    }
	}
	return sb.toString();
    }

    public static String escapeForJsVariableName(String toEscape) {

	Matcher match = JS_VARIABLE_NAME_INVALID_CHARACTERS_REGEX.matcher(toEscape);
	String retVal = match.replaceAll("_");
	while (retVal.length() > 0 && !Character.isJavaIdentifierStart(retVal.charAt(0))) {
	    retVal = retVal.substring(1);
	}
	return retVal;
    }

    public static String escapeForCssSelector(String selectorName) {

	Matcher match = CSS_SELECTOR_METACHARACTERS_REGEX.matcher(selectorName);
	String retVal = match.replaceAll("\\\\\\\\$0");
	return retVal;
    }

    public static IndiceIdSemantico nuovoIndiceIdSemantico() {

	return new IndiceIdSemantico();
    }

    public Boolean evaluateExpression(EspressioneType expression, IndiceIdSemantico rowIndex) {

	return CartModuloHelper.evaluateExpression(expression, getInputData(), rowIndex);
    }

    public Boolean evaluateOperand(OperandoType operand, IndiceIdSemantico rowIndex) {

	return CartModuloHelper.evaluateOperand(operand, getInputData(), rowIndex);
    }

    public Boolean evaluateOperation(OperazioneType operation, IndiceIdSemantico rowIndex) {

	return CartModuloHelper.evaluateOperation(operation, getInputData(), rowIndex);
    }

    public String sostituisciSegnaposto(String pattern, IndiceIdSemantico iis) {
	return sostituisciSegnaposto(pattern, iis, false);
    }
    
    public String sostituisciSegnaposto(String pattern, IndiceIdSemantico iis, boolean soloDatiDomanda) {

	String replaced = pattern;
	if (StringUtils.isNotBlank(replaced)) {
	    Matcher mtch = SEGNAPOSTO_REGEX.matcher(replaced);
	    StringBuilder sb = new StringBuilder();
	    int matchPos = -1;
	    while (mtch.find()) {
		String ph = mtch.group();
		sb.append(replaced.substring(matchPos == -1 ? 0 : matchPos, mtch.start()));
		matchPos = mtch.end();
		ph = ph.substring(1,ph.length()-1);
		int firtDotPos = ph.indexOf(".");
		if (firtDotPos > -1 && ph.substring(0, firtDotPos).toUpperCase().equals(SEGNAPOSTO_DOMANDA_PREFIX)) {
		    //prendo i valori da foDomande
		    String propName = toCamelCase(ph.substring(firtDotPos +1));
		    String propVal = "";
		    //cerco di recuperare il valore della proprietà specificata da FoDomande
		    FoDomande dom = this.inputData != null ? this.inputData.getDomanda() : null;
		    if (dom != null) {
			Object rawVal = EntityUtils.getNestedProperty(dom, propName);
			propVal = Utilities.valueAsString(rawVal);
		    }
		    sb.append(propVal);
		} else {
		    
		    String propVal = "";
		    if(this.inputData != null && !soloDatiDomanda){
			ValoreIdSemantico vis = this.inputData.searchValoreIdSemanticoPerIndice(ph, iis);
			if(vis == null){
			    vis = this.inputData.searchValoreIdSemantico(ph);
			}
			if (vis != null) {
			    if (vis.isScalare()) {
				propVal = vis.getValoreScalare();
			    } else if (vis.isVettoriale()) {
				String[] values = vis.getValoreVettoriale();
				if (values.length > 0) {
				    propVal = values[0];
				}
			    }
			}
		    }
		    else{
			propVal = mtch.group();
		    }
		    sb.append(propVal);
		}
	    }
	    if (matchPos > -1) {
		replaced = sb.toString();
	    }
	}
	return replaced;
    }
    
    public static List<String> getSegnapostoIdSemantici(String patternString){
	List<String> phs = new ArrayList<String>();
	if(StringUtils.isNotEmpty(patternString)){
	    Matcher m = SEGNAPOSTO_REGEX.matcher(patternString);
	    while (m.find()) {
		phs.add(m.group());
	    }
	}
	return phs;
    }
    
    public static String getIdSemanticoForTemplate(String refModulo, String idSemantico) {

	StringBuilder sb = new StringBuilder();
	if (StringUtils.isNotBlank(refModulo)) {
	    sb.append(refModulo.replaceAll(" ", "_"));
	}
	if (StringUtils.isNotBlank(idSemantico)) {
	    if (sb.length() > 0) {
		sb.append(".");
	    }
	    sb.append(idSemantico);
	}
	return sb.toString();
    }


    //tranfornma LA_NOTAZIONE_COSI inQuellaCosì TODO metter in classe Utils se può servire
    private static String toCamelCase(String input) {

	if (input != null) {
	    input = input.toLowerCase();
	    StringBuilder sb = new StringBuilder();
	    String[] props = input.split("\\.");
	    for (int i = 0; i < props.length; i++) {
		String[] parts = props[i].split("_");
		if (parts.length > 0) {
		    if(sb.length() > 0){
			sb.append('.');
		    }
		    sb.append(parts[0]);
		    for (int ii = 1; ii < parts.length; ii++) {
			sb.append(StringUtils.capitalize(parts[ii]));
		    }
		}
	    }
	    input = sb.toString();
	}
	return input;
    }

    /**
     * Il metodo viene invocato per ciascun campo durante il rendering dei moduli in velocity, elabora la gerarchia di
     * contenimento del quadro che si sta renderizzando per determinare se il layout dei campi deve essere di tipo form
     * (etichetta a sinistra e campo a destra) o di tipo griglia (solo campo a sinistra). L'unico caso in cui sia
     * previsto un layout di tipo griglia si ha per quei campi che si trovano all'interno di una tabella e solo nel caso
     * in cui tutti i campi della stessa colonna siano privi di etichetta.
     * 
     * @param nowRendering
     *            riferimento all'istanza di CampoType o FileType che si sta renderizzando nel template al momento della
     *            chiamata
     * @return Boolean true se rendering a form, false se rendering a griglia
     */
    public Boolean renderFormLayout(ItemType nowRendering, QuadroType quadro) {

	Boolean retVal = this.containerStack.isEmpty();
	if (!retVal) {
	    TabellaType table = null;
	    Iterator<ContainerWrapper> containersIterator = this.containerStack.iterator();
	    while (containersIterator.hasNext()) {
		ContainerWrapper container = (ContainerWrapper) containersIterator.next();
		if (container.getContainer().getTabella() != null) {
		    table = container.getContainer().getTabella();
		    break;
		}
	    }
	    // mi trovo all'interno di una tabella
	    if (null != table) {
		// determino la colonna in cui mi trovo
		ColonnaType column = getContainingColumnOfItem(nowRendering, table);
		if (column != null) {
		    ItemType cell = column.getCella();
		    retVal = containsLabelRecursive(cell);
		} else {
		    log.error("renderFormLayout() - ERRORE la tabella che sto' elaborando non contiene il campo che sto' elaborando. E' evidente che c'é qualche errore!!!!!");
		}
	    } else {
		retVal = isLabelInQuadro(quadro);
	    }
	} else {
	    retVal = isLabelInQuadro(quadro);
	}
	return retVal;
    }

    private ColonnaType getContainingColumnOfItem(ItemType item, TabellaType inTable) {

	ColonnaType inColumn = null;
	List<ColonnaType> columns = inTable.getColonna();
	for (Iterator colsIterator = columns.iterator(); colsIterator.hasNext();) {
	    ColonnaType col = (ColonnaType) colsIterator.next();
	    ItemType cell = col.getCella();
	    if (itemContainsItemRecursive(cell, item)) {
		inColumn = col;
		break;
	    }
	}
	return inColumn;
    }

    private boolean itemContainsItemRecursive(ItemType searchIn, ItemType searchFor) {

	boolean retVal = false;
	if (searchIn.equals(searchFor)) {
	    retVal = true;
	} else {
	    if (searchIn.getSezione() != null) {
		List<ItemType> children = searchIn.getSezione().getItem();
		for (Iterator iterator = children.iterator(); iterator.hasNext() && !retVal;) {
		    ItemType child = (ItemType) iterator.next();
		    retVal = itemContainsItemRecursive(child, searchFor);
		}
	    } else if (searchIn.getTabella() != null) {
		List<ColonnaType> columns = searchIn.getTabella().getColonna();
		for (Iterator iterator = columns.iterator(); iterator.hasNext() && !retVal;) {
		    ColonnaType childColumn = (ColonnaType) iterator.next();
		    ItemType child = childColumn.getCella();
		    retVal = itemContainsItemRecursive(child, searchFor);
		}
	    }
	}
	return retVal;
    }

    /*
     * restituisce true se la colonna contiene almeno un CampoType o un FileType
     * con etichetta da visualizzare.
     */
    private boolean containsLabelRecursive(ItemType item) {

	boolean hasLabel = false;
	if (item.getCampo() != null) {
	    hasLabel = StringUtils.isNotEmpty(item.getCampo().getTitolo());
	} else if (item.getFile() != null) {
	    hasLabel = StringUtils.isNotEmpty(item.getFile().getTitolo());
	} else if (item.getSezione() != null) {
	    List<ItemType> children = item.getSezione().getItem();
	    for (Iterator<ItemType> it = children.iterator(); it.hasNext() && !hasLabel;) {
		ItemType child = (ItemType) it.next();
		hasLabel = containsLabelRecursive(child);
	    }
	}
	return hasLabel;
    }

    public void registraStatoCampoAttivo(ItemType item, IndiceIdSemantico index, String idQuadro, Boolean isAttivo) {

	String idModulo = getIdModulo();
	if (item != null && StringUtils.isNotBlank(idQuadro)) {
	    DatiModulo moduleData = this.inputData.getDatiModulo(idModulo);
	    //TODO se i dati del modulo non ci fosero ancora?
	    if (moduleData == null) {
		return;
	    } else {
		moduleData.registraMetadatiCampo(idQuadro, item, index, isAttivo);
	    }
	}
    }

    public boolean isRenderingSezione() {

	boolean isSez = false;
	if (!this.containerStack.isEmpty()) {
	    ContainerWrapper wrap = this.containerStack.peek();
	    if (wrap != null) {
		isSez = wrap.getContainer().getSezione() != null;
	    }
	}
	return isSez;
    }

    public boolean isLabelInQuadro(QuadroType quadro) {

	boolean hasLabel = false;
	List<ItemType> campiQuadro = quadro.getItem();
	for (Iterator<ItemType> quadroIter = campiQuadro.iterator(); quadroIter.hasNext() && !hasLabel;) {
	    ItemType item = (ItemType) quadroIter.next();
	    hasLabel = containsLabelRecursive(item);
	}
	return hasLabel;
    }

    public static Boolean rendersAsCheckbox(CampoType campo) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	if (null != campo && null != campo.getSetValori() && null != campo.getSetValori().getEnumerazione()) {
	    EnumerazioneType enumerazione = campo.getSetValori().getEnumerazione();
	    retVal = campo.isMultiValore() || enumerazione.getEnumItem().size() == 1;
	    if (!retVal) {
		for (EnumItemType enumItem : enumerazione.getEnumItem()) {
		    if (StringUtils.isNotEmpty(enumItem.getValore()) && enumItem.getValore().length() > SELECT_OPTION_MAXCHARACTERS) {
			retVal = Boolean.TRUE;
			break;
		    }
		}
	    }
	}
	// §§§END§§§
	return retVal;
    }

    public static Boolean rendersAsRadioButton(CampoType campo) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	if (null != campo && null != campo.getSetValori() && null != campo.getSetValori().getEnumerazione()) {
	    EnumerazioneType enumerazione = campo.getSetValori().getEnumerazione();
	    Boolean isSelect = !campo.isMultiValore() && enumerazione.getEnumItem().size() > 1;
	    if (isSelect) {
		for (EnumItemType enumItem : enumerazione.getEnumItem()) {
		    if (StringUtils.isNotEmpty(enumItem.getValore()) && enumItem.getValore().length() > SELECT_OPTION_MAXCHARACTERS) {
			retVal = Boolean.TRUE;
			break;
		    }
		}
	    }
	}
	// §§§END§§§
	return retVal;
    }

    public static Boolean rendersAsTextarea(CampoType campo) {

	Boolean retVal = Boolean.FALSE;
	if (null != campo && null != campo.getSize()) {
	    Integer size = NumberUtils.toInt(campo.getSize(), -1);
	    if (size == -1) {
		log.warn("rendersAsTextarea() - il campo con id semantico {} ha la proprietà size con valore non numerico: {}",
			new Object[] { campo.getIdSemantico(), campo.getSize() });
	    }
	    if (size > TEXT_INPUT_MAXSIZE) {
		retVal = Boolean.TRUE;
	    }
	}
	return retVal;
    }

    public static String escapeValueForHtmlAttribute(String rawValue) {

	String retVal = "";
	if (StringUtils.isNotBlank(rawValue)) {
	    retVal = HtmlUtils.htmlEscape(rawValue);
	}
	return retVal;
    }

    public static StringBuilder initErrorMessageFor(CampoType campo, Integer rowIndex) {

	StringBuilder sbErr = new StringBuilder("Il campo ");
	if (StringUtils.isNotEmpty(campo.getTitolo())) {
	    sbErr.append("'").append(campo.getTitolo()).append("' ");
	}
	/*
	sbErr.append("associato all'id semantico ").append(campo.getIdSemantico());
	if (null != rowIndex && rowIndex > -1) {
	    sbErr.append(" alla riga ").append(rowIndex + 1);
	}
	*/
	return sbErr;
    }

    public static StringBuilder initErrorMessageFor(FileType file, Integer rowIndex) {

	StringBuilder sbErr = new StringBuilder("Il campo file ");
	if (StringUtils.isNotEmpty(file.getTitolo())) {
	    sbErr.append("'").append(file.getTitolo()).append("' ");
	}
	/*
	sbErr.append("associato all'id semantico ").append(file.getIdSemantico());
	if (null != rowIndex && rowIndex > -1) {
	    sbErr.append(" alla riga ").append(rowIndex + 1);
	}
	*/
	return sbErr;
    }

    public String buildJavascriptToDynamicallyEnableElement(String elementId, EspressioneType evaluateAtRuntime) {

	return buildJavascriptToDynamicallyEnableElement(elementId, evaluateAtRuntime, 2);
    }

    public String buildJavascriptToDynamicallyEnableElement(String elementId, EspressioneType evaluateAtRuntime, Integer indentation) {

	// §§§BEGIN§§§
	String indexingSuffix = "";
	int suffixStartIndex = elementId.lastIndexOf(FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR);
	if (suffixStartIndex < 0) {
	    suffixStartIndex = elementId.lastIndexOf(FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_TABLE_NAME_SEPARATOR);
	}
	if (suffixStartIndex > -1) {
	    indexingSuffix = elementId.substring(suffixStartIndex);
	    elementId = elementId.substring(0, suffixStartIndex);
	}
	return buildJavascriptToDynamicallyEnableElement(elementId, evaluateAtRuntime, indexingSuffix, indentation);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return "";@@@ENDALTERNATIVEEXIT@@@
    }

    public String buildJavascriptToDynamicallyEnableElementStar(String elementId) {

	// TODO
	return "";
    }

    public String buildJavascriptToDynamicallyEnableElement(String elementId, EspressioneType evaluateAtRuntime, String indexingSuffix,
	    Integer indentation) {

	// §§§BEGIN§§§
	StringBuilder js = new StringBuilder();
	js.append("// ### processo ").append(elementId);
	appendCRLFAndIndent(js, indentation);
	indexingSuffix = StringUtils.defaultString(indexingSuffix);
	//String fieldIndexingSuffix = indexingSuffix.replaceAll(FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_TABLE_NAME_SEPARATOR, FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR);
	if (null != evaluateAtRuntime) {
	    // js.append("\r\n\t\t<script>\r\n");
	    /*
	     * se il campo è attivato dinamicamente in base ai valori assunti da
	     * altri campi allora associo una funzione come handler dell'evento
	     * change del/i campo/i da cui dipende
	     */
	    Set<String> bindEventsToSet = extractDependenciesFromExpression(evaluateAtRuntime);
	    Iterator<String> bindEventsTo = bindEventsToSet.iterator();
	    boolean isDynamic = isEspressioneDinamica(evaluateAtRuntime);
	    if (bindEventsTo.hasNext()) {
		isDynamic = true;
		appendCRLFAndIndent(js, indentation);
		js.append("var dependsFrom = findAnyInputByIdSemanticoForActiveIndex('").append(escapeForCssSelector(bindEventsTo.next()));
		js.append("', '").append(indexingSuffix).append("');");
		appendCRLFAndIndent(js, indentation);
		while (bindEventsTo.hasNext()) {
		    js.append("dependsFrom = dependsFrom.add(findAnyInputByIdSemanticoForActiveIndex('").append(
			    escapeForCssSelector(bindEventsTo.next()));
		    js.append("', '").append(indexingSuffix).append("'));");
		    appendCRLFAndIndent(js, indentation);
		}
		js.append("dependsFrom.change(function(){");
		appendCRLFAndIndent(js, ++indentation);
	    }
	    js.append("var evaluatedExpr = ");
	    appendJavascriptToEvaluateExpression(js, evaluateAtRuntime, indexingSuffix, --indentation);
	    js.append(".call(this);");
	    appendCRLFAndIndent(js, indentation);
	    js.append("var dependent = $('#").append(escapeForCssSelector(elementId)).append(indexingSuffix).append("');");
	    appendCRLFAndIndent(js, indentation);
	    js.append("displayElements(dependent,evaluatedExpr,renameInputNameOnDisable);");
	    appendCRLFAndIndent(js, indentation);
	    js.append("adjustGridLayout(dependent);");
	    // se sto attivando/disattivando un tab di un quadro devo anche
	    // impostare il flag 'attivo'
	    // a true o false nell'array di oggetti JSON che memorizza lo stato
	    // dei quadri
	    if (elementId.startsWith(FACCTConstants.HTML_ID_TAB_QUADRO_PEREFIX)) {
		String idQuadro = elementId.substring(FACCTConstants.HTML_ID_TAB_QUADRO_PEREFIX.length());
		appendCRLFAndIndent(js, indentation);
		js.append("quadri = ").append(FACCTConstants.JS_VAR_INFO_QUADRI_MODULO_PREFIX).append(getIdModuloForJsVariableName()).append(";");
		appendCRLFAndIndent(js, indentation);
		js.append("for(var j = 0; j < quadri.length; j++){");
		appendCRLFAndIndent(js, ++indentation);
		js.append("if(quadri[j].idQuadro == \"").append(idQuadro).append("\"){");
		appendCRLFAndIndent(js, ++indentation);
		js.append("quadri[j].attivo = evaluatedExpr;");
		appendCRLFAndIndent(js, indentation);
		js.append("break;");
		appendCRLFAndIndent(js, --indentation);
		js.append("}");
		appendCRLFAndIndent(js, --indentation);
		js.append("}");
	    }
	    if (isDynamic) {
		appendCRLFAndIndent(js, --indentation);
		js.append("});");
	    }
	    // scateno l'evento change su tutti i campi da cui dipende
	    // l'elemento corrente in modo da impostare il corretto stato
	    // iniziale del campo
	    bindEventsTo = bindEventsToSet.iterator();
	    if (bindEventsTo.hasNext()) {
		appendCRLFAndIndent(js, indentation);
		//js.append("dependsFrom.change();");
		js.append("changeAfterLoad = changeAfterLoad.add(dependsFrom);");
	    }
	}
	return js.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public String buildJavascriptToDynamicallySetMandatoryElement(String elementId, EspressioneType evaluateAtRuntime) {

	return buildJavascriptToDynamicallySetMandatoryElement(elementId, evaluateAtRuntime, 2);
    }

    public String buildJavascriptToDynamicallySetMandatoryElement(String elementId, EspressioneType evaluateAtRuntime, Integer indentation) {

	// §§§BEGIN§§§
	String indexingSuffix = "";
	int suffixStartIndex = elementId.lastIndexOf(FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR);
	if (suffixStartIndex > -1) {
	    indexingSuffix = elementId.substring(suffixStartIndex);
	    elementId = elementId.substring(0, suffixStartIndex);
	}
	return buildJavascriptToDynamicallySetMandatoryElement(elementId, evaluateAtRuntime, indexingSuffix, indentation);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return "";@@@ENDALTERNATIVEEXIT@@@
    }

    public String buildJavascriptToDynamicallySetMandatoryElement(String elementId, EspressioneType evaluateAtRuntime, String indexingSuffix,
	    Integer indentation) {

	// §§§BEGIN§§§
	StringBuilder js = new StringBuilder();
	if (null != evaluateAtRuntime) {
	    // js.append("\r\n\t\t<script>\r\n");
	    /*
	     * se il campo è attivato dinamicamente in base ai valori assunti da
	     * altri campi allora associo una funzione come handler dell'evento
	     * change del/i campo/i da cui dipende
	     */
	    Set<String> bindEventsToSet = extractDependenciesFromExpression(evaluateAtRuntime);
	    Iterator<String> bindEventsTo = bindEventsToSet.iterator();
	    boolean isDynamic = isEspressioneDinamica(evaluateAtRuntime);
	    if (bindEventsTo.hasNext()) {
		appendCRLFAndIndent(js, indentation);
		js.append("var dependsFrom = findAnyInputByIdSemanticoForActiveIndex('").append(escapeForCssSelector(bindEventsTo.next()));
		js.append("', '").append(indexingSuffix).append("');");
		appendCRLFAndIndent(js, indentation);
		while (bindEventsTo.hasNext()) {
		    js.append("dependsFrom = dependsFrom.add(findAnyInputByIdSemanticoForActiveIndex('").append(
			    escapeForCssSelector(bindEventsTo.next()));
		    js.append("', '").append(indexingSuffix).append("'));");
		    appendCRLFAndIndent(js, indentation);
		}
		js.append("dependsFrom.change(function(){");
		appendCRLFAndIndent(js, ++indentation);
	    }
	    js.append("var evaluatedExpr = ");
	    appendJavascriptToEvaluateExpression(js, evaluateAtRuntime, indexingSuffix, indentation);
	    js.append(".call(this);");
	    appendCRLFAndIndent(js, indentation);
	    js.append("setMandatoryField(evaluatedExpr,'").append(elementId).append(indexingSuffix).append("');");
	    /*
	     * js.append("var dependent = $('[name = \"").append(
	     * escapeForCssSelector
	     * (elementId)).append(indexingSuffix).append("\"]');");
	     * appendCRLFAndIndent(js, indentation);
	     * js.append("if(evaluatedValue){"); appendCRLFAndIndent(js,
	     * ++indentation);
	     * js.append("dependent.addClass('campo-cart-obbligatorio');");
	     * appendCRLFAndIndent(js, --indentation); js.append("}else{");
	     * appendCRLFAndIndent(js, ++indentation);
	     * js.append("dependent.removeClass('campo-cart-obbligatorio');");
	     * appendCRLFAndIndent(js, --indentation); js.append("}");
	     */
	    if (isDynamic) {
		appendCRLFAndIndent(js, --indentation);
		js.append("});");
	    }
	    // scateno l'evento change su tutti i campi da cui dipende
	    // l'elemento corrente in modo da impostare il corretto stato
	    // iniziale del campo
	    bindEventsTo = bindEventsToSet.iterator();
	    if (bindEventsTo.hasNext()) {
		appendCRLFAndIndent(js, indentation);
		js.append("dependsFrom.change();");
	    }
	}
	return js.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public String buildJavascriptForAutocompiler(AutocompilerApplication autocomp, CampoType campo, String indexingSuffix, String appContext, String idEndo) {

	String idSemantico = campo.getIdSemantico();
	String idCampo = campo.getId();
	String idPrefix = "";
	return buildJavascriptForAutocompiler(autocomp, idSemantico, idCampo, idPrefix, indexingSuffix, "#{0}", appContext, idEndo);
    }

    public String buildJavascriptForAutocompiler(AutocompilerApplication autocomp, FileType campo, String indexingSuffix, String appContext, String idEndo) {

	String idSemantico = campo.getIdSemantico();
	String idCampo = campo.getId();
	String idPrefix = "fileupload-";
	return buildJavascriptForAutocompiler(autocomp, idSemantico, idCampo, idPrefix, indexingSuffix, "#{0}", appContext, idEndo);
    }

    public String buildJavascriptForAutocompiler(AutocompilerApplication autocomp, Dyn2Modellid d2md, String indexingSuffix, String appContext, String idEndo) {

	String idSemantico = d2md.getDyn2Campi().getNomecampo();
	String idCampo = d2md.getId().getCodice().toString();
	String idPrefix = "";
	return buildJavascriptForAutocompiler(autocomp, idSemantico, idCampo, idPrefix, indexingSuffix, "[starid={0}]", appContext, idEndo);
    }

    public String buildJavascriptForAutocompiler(AutocompilerApplication autocomp, String idSemantico, String idCampo, String idPrefix,
	    String indexingSuffix, String cssSelectorPattern, String appContext, String idEndo) {

	StringBuilder js = new StringBuilder();
	String idCountKey = "campo_" + idCampo;
	int numIdDuplicati = countUniqueIds(idCountKey);
	if (numIdDuplicati > 1) {
	    idCampo = idCampo + FACCTConstants.HTML_UNIQUE_ID_SEPARATOR + (numIdDuplicati - 1);
	    idCampo = escapeForCssSelector(idCampo);
	}
	int indent = 0;
	if (null != autocomp) {
	    AutocompilerDefinition autocompDef = (AutocompilerDefinition) autocomp.getAutocompilerRef();
	    if (autocompDef != null && StringUtils.isNotBlank(autocompDef.getJsControlObject())) {
		js.append("var attrMappings = [];");
		appendCRLFAndIndent(js, indent);
		AutocompilerResultMapppings autocompMappings = autocomp.getResultMappings();
		List<SearchResultAttributeMapping> mappings = autocompMappings.getResultMappings();
		for (SearchResultAttributeMapping mapping : mappings) {
		    js.append("attrMappings[\"").append(mapping.getResultAttributeName()).append("\"] = '").append(mapping.getDestinationFieldId())
			    .append("';");
		    appendCRLFAndIndent(js, indent);
		}
		js.append("var hiddenComune = $('input[name=\"codicecomune\"]')");
		appendCRLFAndIndent(js, indent);
		js.append("var codicecomune = hiddenComune.length == 1 ? hiddenComune.val() : '';");
		appendCRLFAndIndent(js, indent);
		StringBuilder sbId = new StringBuilder(idPrefix).append("\\\\[").append(escapeForCssSelector(idSemantico)).append(indexingSuffix)
			.append("\\\\]").append(idCampo);
		js.append("$('").append(MessageFormat.format(cssSelectorPattern, sbId.toString())).append("').")
			.append(autocompDef.getJsControlObject().trim()).append("({");
		appendCRLFAndIndent(js, ++indent);
		js.append("mappings: attrMappings,");
		appendCRLFAndIndent(js, indent);
		js.append("idComune: codicecomune,");
		appendCRLFAndIndent(js, indent);
		js.append("minLength: 0,");
		appendCRLFAndIndent(js, indent);
		js.append("idComuneAlias: \"").append(ORMHelper.getIdcomuneAlias()).append("\",");
		appendCRLFAndIndent(js, indent);
		js.append("idModulo: \"").append(this.getRiferimentoModulo()).append("\",");
		appendCRLFAndIndent(js, indent);
		if(StringUtils.isNotBlank(idEndo)){
			js.append("idEndo: \"").append(idEndo).append("\",");
			appendCRLFAndIndent(js, indent);
		}
		js.append("appContext: \"").append(appContext).append("\"");
		appendCRLFAndIndent(js, indent);
		Integer idAproc = this.getInputData() != null && this.getInputData().getDatiContestoDomanda() != null ? this.getInputData()
			.getDatiContestoDomanda().getIdAlberoProc() : null;
		if (idAproc != null) {
		    js.append(",");
		    appendCRLFAndIndent(js, indent);
		    js.append("idAlberoproc: \"").append(idAproc).append("\"");
		}
		if (StringUtils.isNotBlank(autocompDef.getControllerUrl())) {
		    js.append(",");
		    appendCRLFAndIndent(js, indent);
		    js.append("url: \"");
		    if (StringUtils.isNotBlank(appContext)) {
			js.append(appContext);
		    }
		    js.append(autocompDef.getControllerUrl()).append("\"");
		}
		if (StringUtils.isNotBlank(autocomp.getLabel())) {
		    js.append(",");
		    appendCRLFAndIndent(js, indent);
		    js.append("label: \"").append(autocomp.getLabel()).append("\"");
		}
		js.append(",");
		appendCRLFAndIndent(js, indent);//'[name ^= "\\[{field}\\]"]'
		js.append("fieldSelectorPattern: '[starid ^= \"\\\\[{field}").append(indexingSuffix).append("\\\\]\"]'");
		ConfigOptions cfgOpts = autocomp.getConfigOptions();
		if (cfgOpts != null) {
		    List<ConfigOption> opts = cfgOpts.getConfigOptions();
		    if (opts != null) {
			for (ConfigOption opt : opts) {
			    js.append(",");
			    appendCRLFAndIndent(js, indent);
			    js.append(opt.getOptionName().replaceAll("[\\s\\p{Punct}]", "_")).append(": '").append(opt.getOptionValue()).append("'");
			}
		    }
		}
		appendCRLFAndIndent(js, --indent);
		js.append("});");
	    }
	}
	return js.toString();
    }

    public Boolean isEspressioneDinamica(EspressioneType espressione) {

	Boolean retVal = espressione != null;
	if (retVal) {
	    retVal = espressione.isValore() == null;
	}
	return retVal;
    }

    public void handleNestedtable(String tableId) {

	log.error(
		"handleNestedtable() - la tabella avente id: {} è annidata all'interno di un'altra tabella enon può essere rappresentata a schermo",
		tableId);
    }

    /**
     * Poichè in Velocity è possibile fare cicli solo su liste o array questo metodo restituisce una lista di oggetti
     * fittizi sulla quale viene fatto il ciclo all'interno del template di Velocity. Se ci sono dati già trasmessi
     * dall'utente la lista restituita conterrà tanti oggetti quante sono le righe effettivamente trasmesse dall'utente.
     * Se invece non ci sono dati viene restituita comunque una lista di lunghezza 1 affinchè venga renderizzata
     * comunque la prima riga vuota.
     * 
     * @param tableItem
     * @return
     */
    public List<Object> getListOfRows(ModellidinamiciTabellaHelper blocco, IndiceIdSemantico indice, Boolean forTemplate) {

	List<Object> retRows = new ArrayList<Object>();
	// §§§BEGIN§§§
	List<ModellidinamiciRigaHelper> righe = blocco.getRighe();
	Iterator<ModellidinamiciRigaHelper> righeIter = righe.iterator();
	ModellidinamiciCampoHelper campo = null;
	int numRows = 0;
	if (!BooleanUtils.toBoolean(forTemplate)) {
	    while (righeIter.hasNext() && campo == null) {
		ModellidinamiciRigaHelper riga = righeIter.next();
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		Iterator<ModellidinamiciColonnaHelper> colonneIter = colonne.iterator();
		while (colonneIter.hasNext()) {
		    ModellidinamiciColonnaHelper colonna = colonneIter.next();
		    if (colonna.getCampo() != null) {
			campo = colonna.getCampo();
			if (campo != null) {
			    List<ValoreIdSemantico> valoriTest = this.getValoreIndicizzato(campo.getNomeCampo(), indice);
			    if (valoriTest.size() > numRows) {
				numRows = valoriTest.size();
			    }
			}
		    }
		}
	    }
	}
	if (numRows == 0) {
	    numRows = 1;
	}
	for (int i = 0; i < numRows; i++) {
	    retRows.add(new Object());
	}
	// §§§END§§§
	return retRows;
    }

    /**
     * Poichè in Velocity è possibile fare cicli solo su liste o array questo metodo restituisce una lista di oggetti
     * fittizi sulla quale viene fatto il ciclo all'interno del template di Velocity. Se ci sono dati già trasmessi
     * dall'utente la lista restituita conterrà tanti oggetti quante sono le righe effettivamente trasmesse dall'utente.
     * Se invece non ci sono dati viene restituita comunque una lista di lunghezza 1 affinchè venga renderizzata
     * comunque la prima riga vuota.
     * 
     * @param tableItem
     * @return
     */
    public List<Object> getListOfRows(ItemType tableItem, IndiceIdSemantico indice, Boolean forTemplate) {

	List<Object> retRows = new ArrayList<Object>();
	// §§§BEGIN§§§
	if (tableItem.getTabella() != null) {
	    int numRows = 0;
	    if (!BooleanUtils.toBoolean(forTemplate)) {
		numRows = CartModuloHelper.contaRigheTabella(tableItem, getInputData(), indice);
	    }
	    if (numRows == 0) {
		numRows = 1;
	    }
	    for (int i = 0; i < numRows; i++) {
		retRows.add(new Object());
	    }
	}
	// §§§END§§§
	return retRows;
    }

    /**
     * Restituisce il valore di un campo a valore singolo. Se il campo è indicizzato all'interno di una tabella occorre
     * passare come secondo argomento l'indice della riga da cui si vuole recuperare il valore. Se il campo non è
     * indicizzato occorre passare un oggetto indice vuoto (new IndiceIdSemantico()). Se non ci sono dati associati
     * all'id semantico specificato viene restituita la stringa vuota.
     * 
     * @param idSemantico
     * @param rowIndex
     * @return
     */
    public String getValoreSingolo(String idSemantico, IndiceIdSemantico rowIndex) {

	String retVal = null;
	// §§§BEGIN§§§
	String refModulo = getRiferimentoModulo(this.modulo);
	ValoreIdSemantico valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, idSemantico, rowIndex);
	if (valore != null && valore.isScalare()) {
	    retVal = valore.getValoreScalare();
	}
	if (null == retVal) {
	    retVal = "";
	}
	// §§§END§§§
	return retVal;
    }

    public String getValoreCampoDinamico(String nomecampo, Integer indice, IndiceIdSemantico rowIndex) {

	String retVal = null;
	// §§§BEGIN§§§
	String refModulo = getRiferimentoModulo(this.modulo);
	ValoreIdSemantico valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, nomecampo, rowIndex);
	if (valore != null) {
	    if (valore.isScalare()) {
		retVal = valore.getValoreScalare();
	    } else if (valore.isVettoriale()) {
		String[] vs = valore.getValoreVettoriale();
		if (vs != null) {
		    retVal = vs[0];
		}
	    } else {
		log.error("getValoreCampoDinamico - idsemantico: {}, rowIndex: {}. errore trovato id semantico indicizzato.", new Object[] {
			nomecampo, rowIndex });
	    }
	}
	if (null == retVal) {
	    retVal = "";
	}
	// §§§END§§§
	return retVal;
    }

    public String[] getValoreVettorialeCampoDinamico(String nomecampo, Integer indice, IndiceIdSemantico rowIndex) {

	String[] retVal = null;
	// §§§BEGIN§§§
	String refModulo = getRiferimentoModulo(this.modulo);
	ValoreIdSemantico valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, nomecampo, rowIndex);
	if (valore != null) {
	    if (valore.isScalare()) {
		retVal = new String[] { valore.getValoreScalare() };
	    } else if (valore.isVettoriale()) {
		retVal = valore.getValoreVettoriale();
	    } else if (valore.isIndicizzato()) {
		log.error("getValoreVettorialeCampoDinamico - idsemantico: {}, rowIndex: {}. errore trovato id semantico indicizzato.", new Object[] {
			nomecampo, rowIndex });
	    }
	}
	if (null == retVal) {
	    retVal = new String[] { "" };
	}
	// §§§END§§§
	return retVal;
    }

    public String getValoreDecodificatoCampoDinamico(String nomecampo, Integer indice, IndiceIdSemantico rowIndex) {

	String retVal = null;
	// §§§BEGIN§§§
	String refModulo = getRiferimentoModulo(this.modulo);
	ValoreIdSemantico valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, nomecampo, rowIndex);
	if (valore != null) {
	    if (valore.isScalare()) {
		retVal = valore.getValoreScalare();
	    } else if (valore.isVettoriale()) {
		String[] vs = valore.getValoreVettoriale();
		if (vs != null) {
		    retVal = vs[0];
		}
	    } else if (valore.isIndicizzato()) {
		log.error("getValoreDecodificatoCampoDinamico - idsemantico: {}, rowIndex: {}. errore trovato id semantico indicizzato.",
			new Object[] { nomecampo, rowIndex });
	    }
	}
	if (null == retVal) {
	    retVal = "";
	}
	// §§§END§§§
	return retVal;
    }

    /**
     * Restituisce un array di stringhe che rappresentano i valori associati all'id semantico passato come primo
     * argomento. Se il campo è indicizzato occorre passare come secondo argomento l'indice della riga da cui si vuole
     * recuperare il valore. Se il campo non è indicizzato occorre passare un oggetto indice vuoto (new
     * IndiceIdSemantico()). Se il campo contiene un valore scalare viene restituito un array di lunghezza uno. Se non
     * ci sono valori associati all'id semantico viene restituito un array vuoto.
     * 
     * @param idSemantico
     * @param rowIndex
     * @return
     */
    public String[] getValoreMultiplo(String idSemantico, IndiceIdSemantico rowIndex) {

	String[] retVals = new String[0];
	// §§§BEGIN§§§
	ValoreIdSemantico valore = null;
	String refModulo = getRiferimentoModulo(this.modulo);
	valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, idSemantico, rowIndex);
	if (null != valore) {
	    if (valore.isScalare()) {
		retVals = new String[] { valore.getValoreScalare() };
	    } else if (valore.isVettoriale()) {
		retVals = valore.getValoreVettoriale();
	    } else {
		// questo metodo non è pensato per i valori indicizzati
		log.error("getValoreMultiplo - idsemantico: {}, rowIndex: {}. errore trovato id semantico indicizzato.", new Object[] { idSemantico,
			rowIndex });
	    }
	}
	// §§§END§§§
	return retVals;
    }

    /**
     * Restituisce una java.util.List di ValoreIdSemantico che rappresentano i valori associati all'id semantico passato
     * come primo argomento. Se si vuole accedere alla lista di valori presente ad un certo indice (per tabelle
     * annidate) occorre passare come secondo argomento l'indice della riga da cui si vuole recuperare il valore,
     * altrimenti occorre passare un oggetto indice vuoto (new IndiceIdSemantico()). Se il campo contiene un valore
     * indicizzato viene restituita una lista vuota.
     * 
     * @param idSemantico
     * @param rowIndex
     * @return
     */
    public List<ValoreIdSemantico> getValoreIndicizzato(String idSemantico, IndiceIdSemantico rowIndex) {

	List<ValoreIdSemantico> retVals = new ArrayList<ValoreIdSemantico>();
	// §§§BEGIN§§§
	ValoreIdSemantico valore = null;
	String refModulo = getRiferimentoModulo(this.modulo);
	valore = this.inputData.getValoreIdSemanticoPerIndice(refModulo, idSemantico, rowIndex);
	if (null != valore) {
	    if (valore.isIndicizzato()) {
		retVals = valore.getValoreIndicizzato();
	    } else {
		// questo metodo non è pensato per i valori non indicizzati
		log.error("getValoreIndicizzato - idsemantico: {}, rowIndex: {}. errore trovato id semantico indicizzato.", new Object[] {
			idSemantico, rowIndex });
	    }
	}
	// §§§END§§§
	return retVals;
    }

    public Integer contaValoriMultipli(String idSemantico, IndiceIdSemantico rowIndex) {

	Integer retVal = new Integer(0);
	String[] values = getValoreMultiplo(idSemantico, rowIndex);
	if (null != values) {
	    retVal = values.length;
	}
	return retVal;
    }

    /**
     * Calcola il numero di colonne richieste per il rendering del campo. Utilizzato dai templates di velocity per il
     * rendering dei modulil PDF.
     * 
     * @param item
     * @param rowIndex
     * @return
     */
    public Integer countColumnsForItem(ItemType item, IndiceIdSemantico rowIndex) {

	Integer numCols = 0;
	EspressioneType expr = null;
	if (null != item) {
	    if (item.getTesto() != null) {
		expr = item.getTesto().getAttivo();
		if (evaluateExpression(expr, rowIndex)) {
		    numCols = 1;
		}
	    }
	    if (item.getCampo() != null) {
		expr = item.getCampo().getAttivo();
		if (evaluateExpression(expr, rowIndex)) {
		    if (StringUtils.isBlank(item.getCampo().getTitolo())) {
			numCols = 1;
		    } else {
			numCols = 2;
		    }
		}
	    }
	    if (item.getFile() != null) {
		expr = item.getFile().getAttivo();
		if (evaluateExpression(expr, rowIndex)) {
		    if (StringUtils.isBlank(item.getFile().getTitolo())) {
			numCols = 1;
		    } else {
			numCols = 2;
		    }
		}
	    }
	    if (item.getSezione() != null) {
		expr = item.getSezione().getAttivo();
		if (evaluateExpression(expr, rowIndex)) {
		    numCols = 1;
		}
	    }
	    if (item.getTabella() != null) {
		expr = item.getTabella().getAttivo();
		if (evaluateExpression(expr, rowIndex)) {
		    numCols = 1;
		}
	    }
	}
	return numCols;
    }

    public String getQuadroHelp(QuadroType quadro) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(quadro.getNota())) {
	    sbHelp.append(quadro.getNota());
	}
	if (StringUtils.isNotBlank(quadro.getHelp()) && !quadro.getHelp().equalsIgnoreCase(quadro.getNota())) {
	    if (sbHelp.length() > 0) {
		sbHelp.append("\r\n");
	    }
	    sbHelp.append(quadro.getHelp());
	}
	return sbHelp.toString();
    }

    public String getSezioneHelp(SezioneType sezione) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(sezione.getNota())) {
	    sbHelp.append(sezione.getNota());
	}
	if (StringUtils.isNotBlank(sezione.getHelp()) && !sezione.getHelp().equalsIgnoreCase(sezione.getNota())) {
	    if (sbHelp.length() > 0) {
		sbHelp.append("\r\n");
	    }
	    sbHelp.append(sezione.getHelp());
	}
	return sbHelp.toString();
    }

    public String getTabellaHelp(TabellaType tabella) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(tabella.getNota())) {
	    sbHelp.append(tabella.getNota());
	}
	if (StringUtils.isNotBlank(tabella.getHelp()) && !tabella.getHelp().equalsIgnoreCase(tabella.getNota())) {
	    if (sbHelp.length() > 0) {
		sbHelp.append("\r\n");
	    }
	    sbHelp.append(tabella.getHelp());
	}
	return sbHelp.toString();
    }

    public String getTestoHelp(TestoType testo) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(testo.getNota())) {
	    sbHelp.append(testo.getNota());
	}
	return sbHelp.toString();
    }

    public String getCampoHelp(CampoType campo) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(campo.getNota())) {
	    sbHelp.append(campo.getNota());
	}
	if (StringUtils.isNotBlank(campo.getHelp()) && !campo.getHelp().equalsIgnoreCase(campo.getNota())) {
	    if (sbHelp.length() > 0) {
		sbHelp.append("\r\n");
	    }
	    sbHelp.append(campo.getHelp());
	}
	return sbHelp.toString();
    }

    public String getFileHelp(FileType file) {

	StringBuilder sbHelp = new StringBuilder();
	if (StringUtils.isNotBlank(file.getNota())) {
	    sbHelp.append(file.getNota());
	}
	if (StringUtils.isNotBlank(file.getHelp()) && !file.getHelp().equalsIgnoreCase(file.getNota())) {
	    if (sbHelp.length() > 0) {
		sbHelp.append("\r\n");
	    }
	    sbHelp.append(file.getHelp());
	}
	return sbHelp.toString();
    }

    public Boolean contieneValore(String idSemantico, IndiceIdSemantico rowIndex, String valoreConfronto) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	String[] values = getValoreMultiplo(idSemantico, rowIndex);
	retVal = new Boolean(ArrayUtils.contains(values, valoreConfronto));
	// §§§END§§§
	return retVal;
    }

    public String buildFileTypeRestrictionsString(FileType campoFile) {

	return this.buildFileTypeRestrictionsString(campoFile.getTipo());
    }

    public static String buildFileTypeRestrictionsFromDynUpload(String acceptsFilesDynAttribute) {

	if (StringUtils.isBlank(acceptsFilesDynAttribute)) {
	    acceptsFilesDynAttribute = FACCTConstants.RFC239_ALLOWED_EXTENSIONS;
	}
	List<String> exts = ModellidinamiciCampoHelper.splitEstensioniConsentite(acceptsFilesDynAttribute);
	StringBuilder sb = new StringBuilder("/(\\.|\\/)");
	if (exts.size() > 0) {
	    sb.append("(");
	    for (int i = 0; i < exts.size(); i++) {
		if (i > 0) {
		    sb.append("|");
		}
		sb.append("\\\\").append(exts.get(i));
	    }
	    sb.append(")");
	}
	sb.append("$/i");
	return sb.toString();
    }

    public String buildFileTypeRestrictionsString(String acceptsFilesXmlAttribute) {

	String retVal = "/(\\.|\\/)(\\\\.pdf|\\\\.pdf.p7m|\\\\.xml|\\\\.dwf|\\\\.dwf.p7m|\\\\.svg|\\\\.svg.p7m|\\\\.jpg|\\\\.jpg.p7m)$/i";
	// §§§BEGIN§§§
	if (StringUtils.isNotEmpty(acceptsFilesXmlAttribute)) {
	    retVal = acceptsFilesXmlAttribute.replace(';', '|');
	}
	// §§§END§§§
	return retVal;
    }

    public String buildUploadedFilesAsJsonObjectArray(String idSemantico, IndiceIdSemantico rowIndex, String contextPath, String idModulo) {

	JSONObject jo = getUploadedFilesAsJsonObject(idSemantico, rowIndex, contextPath, idModulo);
	if (jo != null && jo.get("files") != null) {
	    JSONArray filesArrayObj = jo.getJSONArray("files");
	    Object[] filesArray = filesArrayObj.toArray();
	    if (filesArray.length > 0) {
		return jo.toString();
	    } else {
		return "undefined";
	    }
	} else {
	    return "undefined";
	}
    }

    public JSONObject getUploadedFilesAsJsonObject(String idSemantico, IndiceIdSemantico rowIndex, String contextPath, String idModulo) {

	List<FileInfo> uploadedFiles = getUploadedFiles(idSemantico, rowIndex);
	JSONObject jo = new JSONObject();
	Object[] files = new Object[uploadedFiles.size()];
	if (null != uploadedFiles && uploadedFiles.size() > 0) {
	    for (int i = 0; i < uploadedFiles.size(); i++) {
		FileInfo fileInfo = uploadedFiles.get(i);
		JSONObject jsonFile = new JSONObject();
		jsonFile.element("name", fileInfo.getNomeFile());
		jsonFile.element("size", fileInfo.getDimensione());
		jsonFile.element("deleteType", "POST");
		jsonFile.element("url",
			contextPath + "/ajax/downloadOggetto.htm?idOggetto=" + fileInfo.getIdOggetto() + "&fileRename=" + fileInfo.getNomeFile());
		jsonFile.element("deleteUrl", contextPath + "/cart/ajaxDeleteAllegatoDomanda.htm?codiceOggetto=" + fileInfo.getIdOggetto()
			+ "&idSemantico=" + idSemantico + "&indice=" + rowIndex + "&idModulo=" + idModulo);
		jsonFile.element("codiceOggetto", fileInfo.getIdOggetto());
		// verifico se ci sono erori di validazione della firma
		AllegatoDaFirmare adf = this.inputData.getAllegatoDaFirmare(fileInfo.getIdOggetto());
		if (adf != null) {
		    jsonFile.element("dserror", adf.buildErrorMessage());
		}
		files[i] = jsonFile;
	    }
	}
	jo.element("files", files);
	return jo;
    }

    public String buildFileUploadAdditionalParamsJson(FileType campoFile, Object indice) {

	return buildFileUploadAdditionalParamsJson(campoFile.getIdSemantico(), campoFile.isFirmato(), indice);
    }

    public String buildFileUploadAdditionalParamsJson(String idSemantico, Boolean dsValidation, Object indice) {

	/*
	JSONObject json = new JSONObject();
	if (null != campoFile) {
	    json.element("idSemantico", campoFile.getIdSemantico());
	    json.element("dsValidation", BooleanUtils.toBoolean(campoFile.isFirmato()));
	}
	if (null != indice) {
	    //JsonConfig jsc = new JsonConfig();
	    json.element("indice", "'" + indice.toString() + "'");
	}
	json.element("idModulo", CartModuloHelper.getRiferimentoModulo(getModulo()));
	return json.toString();
	*/
	StringBuilder json = new StringBuilder("{idSemantico: \"");
	json.append(idSemantico).append("\", dsValidation: ");
	json.append(BooleanUtils.toBoolean(dsValidation)).append(", idModulo: \"");
	json.append(CartModuloHelper.getRiferimentoModulo(getModulo())).append("\"");
	if (null != indice) {
	    json.append(", indice: \"").append(indice.toString()).append("\"");
	}
	json.append("}");
	return json.toString();
    }

    public String buildNumericMaxValueExpression(NumericoType numerico) {

	String maxValue = null;
	if (null != numerico) {
	    RangeType range = numerico.getRangeValidita();
	    maxValue = range.getMassimo();
	    if (FACCTConstants.RFC186_UNBOUNDED_RANGE.equalsIgnoreCase(maxValue)) {
		maxValue = "Number.MAX_VALUE";
	    } else {
		// se il valore espresso dalla modulistica è decimale occorre
		// assicurarsi che sia utilizzato il punto come separatore
		// decimale
		maxValue.replaceAll(",", ".");
		BigDecimal decVal = new BigDecimal(maxValue);
		if (!range.isMassimoIncluso()) {
		    BigDecimal unit = new BigDecimal(BigInteger.ONE, numerico.getMaxNumeroCifreDecimali().intValue());
		    decVal.subtract(unit);
		}
		maxValue = decVal.toPlainString();
	    }
	}
	return maxValue;
    }

    public String buildNumericMinValueExpression(NumericoType numerico) {

	String minValue = null;
	if (null != numerico) {
	    RangeType range = numerico.getRangeValidita();
	    minValue = range.getMinimo();
	    if (FACCTConstants.RFC186_UNBOUNDED_RANGE.equalsIgnoreCase(minValue)) {
		minValue = "Number.MIN_VALUE";
	    } else {
		// se il valore espresso dalla modulistica è decimale occorre
		// assicurarsi che sia utilizzato il punto come separatore
		// decimale
		minValue.replaceAll(",", ".");
		BigDecimal decVal = new BigDecimal(minValue);
		if (!range.isMinimoIncluso()) {
		    BigDecimal unit = new BigDecimal(BigInteger.ONE, numerico.getMaxNumeroCifreDecimali().intValue());
		    decVal.add(unit);
		}
		minValue = decVal.toPlainString();
	    }
	}
	return minValue;
    }

    public List<FileInfo> getUploadedFiles(String idSemantico, IndiceIdSemantico rowIndex) {

	List<FileInfo> uploadedFiles = new ArrayList<FileInfo>();
	// String refModulo = getRiferimentoModulo(this.getModulo());
	String[] valori = this.getValoreMultiplo(idSemantico, rowIndex);// this.inputData.getValoreIdSemanticoPerIndice(refModulo,
									// idSemantico,
									// rowIndex);
	for (int i = 0; i < valori.length; i++) {
	    if (NumberUtils.isNumber(valori[i])) {
		Integer codiceOggetto = NumberUtils.toInt(valori[i]);
		/*
		 * verifico l'effettiva esistenza del file nella tabella oggetti
		 * perché se si elimina il file dal campo upload ma non si
		 * conferma il quadro nei dati della domanda rimane un
		 * riferimento non valido all'oggetto che è stato effettivamente
		 * eliminato.
		 */
		boolean checkFileExists = this.oggettiService != null;
		Oggetti file = null;
		if (checkFileExists) {
		    file = this.oggettiService.findByIdLazy(new PkId(codiceOggetto));
		}
		if (file != null || !checkFileExists) {
		    FileInfo fi = this.inputData.getAllegatoByCodiceOggetto(codiceOggetto);
		    if (fi != null) {
			uploadedFiles.add(fi);
		    }
		}
	    }
	}
	return uploadedFiles;
    }

    public void pushContainerInStack(ItemType container, String uniqueId) {

	ContainerWrapper itemWrap = new ContainerWrapper(container, uniqueId);
	this.containerStack.push(itemWrap);
    }

    public ContainerWrapper popContainerFromStack() {

	ContainerWrapper retItem = this.containerStack.pop();
	return retItem;
    }

    public ContainerWrapper peekContainerFromStack() {

	ContainerWrapper retItem = this.containerStack.peek();
	return retItem;
    }

    public List<AutocompilerApplication> getAutocompilersForIdSemantico(String idSemantico) {

	return CartModuloHelper.getAutocompilersForIdSemantico(getAutocompilerConfig(), idSemantico, getRiferimentoModulo());
    }

    public static List<AutocompilerApplication> getAutocompilersForIdSemantico(AutocompilerConfig cfg, String idSemantico, String module) {

	List<AutocompilerApplication> autocomps = new ArrayList<AutocompilerApplication>();
	if (null != cfg) {
	    AutocompilerApplicationConfig appCfg = cfg.getAutocompilerApplications();
	    List<AutocompilerApplication> autocompApps = appCfg.getAutocompilerApplications();
	    boolean isBackoffice = Utilities.isBackOffice();
	    for (AutocompilerApplication app : autocompApps) {
		String fieldId = app.getTriggerFieldId();
		if (StringUtils.isNotBlank(fieldId)) {
		    fieldId = fieldId.replace(".", "\\.");
		    fieldId = fieldId.replace("*", ".*");
		    if (Pattern.matches(fieldId, idSemantico)) {
			List<String> exclMods = app.getExcludeModules() != null ? app.getExcludeModules().getModuleNames() : null;
			if (exclMods != null && exclMods.contains(module)) {
			    continue;
			}
			if (isBackoffice) {
			    if (app.getUsage().equals(UsageType.BO) || app.getUsage().equals(UsageType.FO_BO)) {
				autocomps.add(app);
			    }
			} else {
			    if (app.getUsage().equals(UsageType.FO) || app.getUsage().equals(UsageType.FO_BO)) {
				autocomps.add(app);
			    }
			}
		    }
		}
	    }
	}
	return autocomps;
    }

    private StringBuilder appendCRLFAndIndent(StringBuilder appendTo, int indentTimes) {

	appendTo.append("\r\n");
	for (int i = 0; i < indentTimes; i++) {
	    appendTo.append("\t");
	}
	return appendTo;
    }

    private String buildJQueryObjectForIdSemantico(String idSemantico, String jsVariableName, String indexingSuffix, int indentation) {

	// §§§BEGIN§§§
	if (null == indexingSuffix)
	    indexingSuffix = "";
	StringBuilder js = new StringBuilder();
	appendCRLFAndIndent(js, indentation);
	// cerco campi con nome='idSemantico' + eventuale suffisso per campi
	// indicizzati
	js.append("var ").append(jsVariableName).append(" = findAnyInputByIdSemanticoForActiveIndex('");
	js.append(escapeForCssSelector(idSemantico)).append("','").append(indexingSuffix).append("');");
	appendCRLFAndIndent(js, indentation);
	return js.toString();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private StringBuilder appendJavascriptToEvaluateOperando(StringBuilder appendTo, OperandoType operando, String indexingSuffix, int indentation) {

	// §§§BEGIN§§§
	/*
	 * appendCRLFAndIndent(appendTo, indentation);
	 * appendTo.append("var evaluatedValue = false;");
	 * appendCRLFAndIndent(appendTo, indentation);
	 */
	// appendTo.append("var elementValue = null;");
	// appendCRLFAndIndent(appendTo, indentation);
	// appendTo.append("var checkElement = $(\"#").append(escapeForCssSelector(elementId)).append("\");");
	appendTo.append("function(){ ");
	appendCRLFAndIndent(appendTo, ++indentation);
	appendTo.append("var operandoVal;");
	appendCRLFAndIndent(appendTo, indentation);
	String variableName = escapeForJsVariableName(operando.getIdSemantico());
	appendTo.append("var value_").append(variableName).append(" = [];");
	appendCRLFAndIndent(appendTo, indentation);
	// recupero il campo da cui recuperare il valore
	appendTo.append(buildJQueryObjectForIdSemantico(operando.getIdSemantico(), variableName, indexingSuffix, indentation));
	// se il campo c'è recupero il valore
	appendCRLFAndIndent(appendTo, indentation);
	// appendTo.append("if(").append(variableName).append(".length > 0 && checkElement.length > 0 ){");
	//appendTo.append("if(").append(variableName).append(".length > 0){");
	//appendCRLFAndIndent(appendTo, ++indentation);
	appendTo.append("value_").append(variableName).append(" = readValuesFromElements(").append(variableName).append(");");
	appendCRLFAndIndent(appendTo, indentation);
	// appendTo.append("elementValue = checkElement.val();");
	// appendCRLFAndIndent(appendTo, indentation);
	switch (operando.getOperatore()) {
	/*
	 * case EQ:
	 * appendTo.append("if(value_").append(variableName).append(".length == 0){"
	 * ); appendCRLFAndIndent(appendTo, ++indentation);
	 * appendTo.append("operandoVal = false;");
	 * appendCRLFAndIndent(appendTo, --indentation);
	 * appendTo.append("}else if(value_"
	 * ).append(variableName).append(".length == 1){");
	 * appendCRLFAndIndent(appendTo, ++indentation);
	 * appendTo.append("value_"
	 * ).append(variableName).append(" = value_").append
	 * (variableName).append("[0]"); appendCRLFAndIndent(appendTo,
	 * indentation);
	 * appendTo.append("operandoVal = value_").append(variableName
	 * ).append(".toUpperCase() == '")
	 * .append(StringEscapeUtils.escapeJavaScript
	 * (operando.getValoreConfronto())).append("'.toUpperCase();");
	 * appendCRLFAndIndent(appendTo, --indentation);
	 * appendTo.append("}else{"); appendCRLFAndIndent(appendTo,
	 * ++indentation); appendTo.append("operandoVal = false;");
	 * appendCRLFAndIndent(appendTo, indentation); appendTo.append(
	 * "showDialog(\"Impossibile attivare/disattivare un campo in base al valore assunto dall'id semantico "
	 * ) .append(operando.getIdSemantico()) .append(
	 * " perchè il campo associato a quell'id semantico ha valori vettoriali non compatibili con l'operatore di eguaglianza (EQ).\", \"Errore\");"
	 * ); appendCRLFAndIndent(appendTo, --indentation);
	 * appendTo.append("}"); break;
	 */
	case EX:
	    appendTo.append("operandoVal = value_").append(variableName).append(".length > 0;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("var index = 0;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("while(operandoVal && index < value_").append(variableName).append(".length){");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("operandoVal = isValue(value_").append(variableName).append("[index]);");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("index++;");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}");
	    break;
	case GT:
	    appendTo.append("if(value_").append(variableName).append(".length == 1){");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("value_").append(variableName).append(" = value_").append(variableName).append("[0]");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("operandoVal = Number(value_").append(variableName).append(") > Number('").append(operando.getValoreConfronto())
		    .append("');");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}else{");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("operandoVal = false;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("showDialog(\"Impossibile attivare/disattivare un campo in base al valore assunto dall'id semantico ")
		    .append(operando.getIdSemantico())
		    .append(" perchè il campo associato a quell'id semantico ha valori vettoriali non compatibili con l'operatore 'maggiore di (GT)'.\", \"Errore\");");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}");
	    break;
	case LT:
	    appendTo.append("if(value_").append(variableName).append(".length == 1){");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("value_").append(variableName).append(" = value_").append(variableName).append("[0]");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("operandoVal = Number(value_").append(variableName).append(") < Number('").append(operando.getValoreConfronto())
		    .append("');");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}else{");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("operandoVal = false;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("showDialog(\"Impossibile attivare/disattivare un campo in base al valore assunto dall'id semantico ")
		    .append(operando.getIdSemantico())
		    .append(" perchè il campo associato a quell'id semantico ha valori vettoriali non compatibili con l'operatore 'minore di (LT)'.\", \"Errore\");");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}");
	    break;
	case EQ:
	case CO:
	    appendTo.append("operandoVal = 'false';");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("for(var idx = 0; idx < value_").append(variableName).append(".length; idx++){");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("value_").append(variableName).append("[idx] = value_").append(variableName).append("[idx].toUpperCase();");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}");
	    appendCRLFAndIndent(appendTo, indentation);
	    /*
	     * appendTo.append("if($.isArray(value_").append(variableName).append
	     * (")){"); appendCRLFAndIndent(appendTo, ++indentation);
	     */
	    appendTo.append("operandoVal = $.inArray('").append(StringEscapeUtils.escapeJavaScript(operando.getValoreConfronto()))
		    .append("'.toUpperCase(),value_").append(variableName).append(") > -1;");
	    /*
	     * appendCRLFAndIndent(appendTo, --indentation);
	     * appendTo.append("}");
	     */
	    break;
	case IN:
	    appendTo.append("var compareValues = \"").append(StringEscapeUtils.escapeJavaScript(operando.getValoreConfronto())).append("\";");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("var compareValuesArray = compareValues.split('|');");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("operandoVal = value_").append(variableName).append(".length > 0;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("var index = 0;");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("while(operandoVal && index < value_").append(variableName).append(".length){");
	    appendCRLFAndIndent(appendTo, ++indentation);
	    appendTo.append("operandoVal = $.inArray(value_").append(variableName).append("[index],compareValuesArray);");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("if(operandoVal != -1){\r\n operandoVal = 1; \r\n break;\r\n}");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("index++;");
	    appendCRLFAndIndent(appendTo, --indentation);
	    appendTo.append("}");
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("if(operandoVal === -1){\r\n operandoVal = 0; \r\n}");
	    break;
	default:
	    log.error("appendJavascriptToEvaluateOperando() - operatore di tipo non supportato: " + operando.getOperatore().name());
	    break;
	}
	/*
	appendCRLFAndIndent(appendTo, --indentation);
	appendTo.append("}");
	appendTo.append("else{");
	appendCRLFAndIndent(appendTo, ++indentation);
	appendTo.append("showDialog(\"Impossibile attivare/disattivare un campo in base al valore assunto dall'id semantico ")
		.append(operando.getIdSemantico()).append(" perchè non esiste nessun campo associato a quell'id semantico\", \"Errore\");");
	appendCRLFAndIndent(appendTo, --indentation);
	appendTo.append("}");
	*/
	appendCRLFAndIndent(appendTo, indentation);
	appendTo.append("return operandoVal;");
	appendCRLFAndIndent(appendTo, --indentation);
	appendTo.append("}");
	return appendTo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private StringBuilder appendJavascriptToEvaluateOperazione(StringBuilder appendTo, OperazioneType operazione, String indexingSuffix,
	    int indentation) {

	// §§§BEGIN§§§
	appendTo.append("function(){ ");
	appendCRLFAndIndent(appendTo, ++indentation);
	appendTo.append("var operazVal;");
	appendCRLFAndIndent(appendTo, indentation);
	List<EspressioneType> exprsns = operazione.getEspressione();
	EspressioneType expr = exprsns.get(0);
	appendTo.append("var evaluatedValueA;");
	appendCRLFAndIndent(appendTo, indentation);
	appendTo.append("evaluatedValueA = ");
	appendJavascriptToEvaluateExpression(appendTo, expr, indexingSuffix, indentation);
	appendTo.append(".call(this);");
	appendCRLFAndIndent(appendTo, indentation);
	if (operazione.getOperatoreLogico() == OperatoreLogicoType.NOT) {
	    // se l'operatore è NOT considero solo la prima espressione (do per
	    // scontato che ce ne sia una sola)
	    appendTo.append("operazVal = !evaluatedValueA;");
	    appendCRLFAndIndent(appendTo, indentation);
	} else {
	    appendCRLFAndIndent(appendTo, indentation);
	    appendTo.append("var evaluatedValueB;");
	    if (exprsns.size() > 1) {
		expr = exprsns.get(1);
		appendCRLFAndIndent(appendTo, indentation);
		appendTo.append("evaluatedValueB = ");
		appendJavascriptToEvaluateExpression(appendTo, expr, indexingSuffix, indentation);
		appendTo.append(".call(this);");
		appendCRLFAndIndent(appendTo, indentation);
		String booleanOperator = "";
		switch (operazione.getOperatoreLogico()) {
		case AND:
		    booleanOperator = "&&";
		    break;
		case OR:
		    booleanOperator = "||";
		    break;
		default:
		    log.error("appendJavascriptToEvaluateOperazione() - operatore logico non supportato {}", operazione.getOperatoreLogico().name());
		    break;
		}
		appendCRLFAndIndent(appendTo, indentation);
		appendTo.append("operazVal = evaluatedValueA ");
		if (StringUtils.isNotEmpty(booleanOperator)) {
		    appendTo.append(booleanOperator).append(" evaluatedValueB;");
		} else {
		    appendTo.append(";");
		}
	    }
	}
	appendCRLFAndIndent(appendTo, indentation);
	appendTo.append("return operazVal;");
	appendCRLFAndIndent(appendTo, --indentation);
	appendTo.append("}");
	return appendTo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private StringBuilder appendJavascriptToEvaluateExpression(StringBuilder appendTo, EspressioneType espressione, String indexingSuffix,
	    int indentation) {

	appendTo.append("function(){");
	appendCRLFAndIndent(appendTo, ++indentation);
	appendTo.append("var exprVal;");
	appendCRLFAndIndent(appendTo, indentation);
	if (espressione.isValore() != null) {
	    appendTo.append("exprVal = ").append(espressione.isValore().booleanValue()).append(";");
	    appendCRLFAndIndent(appendTo, indentation);
	} else if (null != espressione.getOperando()) {
	    appendTo.append("exprVal = ");
	    appendJavascriptToEvaluateOperando(appendTo, espressione.getOperando(), indexingSuffix, indentation);
	    appendTo.append(".call(this);");
	} else if (null != espressione.getOperazione()) {
	    appendTo.append("exprVal = ");
	    appendJavascriptToEvaluateOperazione(appendTo, espressione.getOperazione(), indexingSuffix, indentation);
	    appendTo.append(".call(this);");
	}
	appendCRLFAndIndent(appendTo, indentation);
	appendTo.append("return exprVal;");
	appendCRLFAndIndent(appendTo, --indentation);
	appendTo.append("}");
	return appendTo;
    }

    private StringBuilder operandoToString(StringBuilder sb, OperandoType operando) {

	sb.append(operando.getIdSemantico()).append(" ").append(operando.getOperatore().value()).append(" ");
	if (StringUtils.isNotEmpty(operando.getValoreConfronto())) {
	    sb.append("'").append(operando.getValoreConfronto()).append("'");
	}
	return sb;
    }

    private StringBuilder operazioneToString(StringBuilder sb, OperazioneType operazione) {

	List<EspressioneType> exprs = operazione.getEspressione();
	EspressioneType expr = null;
	for (int i = 0; i < exprs.size(); i++) {
	    expr = exprs.get(i);
	    sb.append(espressioneToString(sb, expr));
	    if (i < exprs.size() - 1) {
		sb.append(" ").append(operazione.getOperatoreLogico().value()).append(" ");
	    }
	}
	return sb;
    }

    private StringBuilder espressioneToString(StringBuilder sb, EspressioneType espressione) {

	if (espressione.isValore() != null) {
	    sb.append(espressione.isValore());
	} else if (espressione.getOperando() != null) {
	    sb.append(operandoToString(sb, espressione.getOperando()));
	} else if (espressione.getOperazione() != null) {
	    sb.append(operazioneToString(sb, espressione.getOperazione()));
	}
	return sb.append(" ");
    }

    private Set<String> extractDependenciesFromExpression(EspressioneType expression) {

	Set<String> idSemantici = new HashSet<String>();
	collectDependenciesFromExpressionRecursive(expression, idSemantici);
	return idSemantici;
    }

    private void collectDependenciesFromExpressionRecursive(EspressioneType expression, Set<String> dependencies) {

	// §§§BEGIN§§§
	if (null != expression.getOperando()) {
	    dependencies.add(expression.getOperando().getIdSemantico());
	} else if (null != expression.getOperazione()) {
	    List<EspressioneType> innerExpressions = expression.getOperazione().getEspressione();
	    for (EspressioneType expr : innerExpressions) {
		collectDependenciesFromExpressionRecursive(expr, dependencies);
	    }
	}
	// §§§END§§§
    }

    private String cleanNomeFile(String dirtyFileName) {

	String retVal = dirtyFileName;
	if (StringUtils.isNotBlank(retVal)) {
	    int separatorIndex = retVal.indexOf(FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR);
	    if (separatorIndex > -1) {
		retVal = retVal.substring(separatorIndex + FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR.length());
	    }
	}
	return retVal;
    }

    public static String getRiferimentoModulo(ModuloType modulo) {

	// §§§BEGIN§§§
	RiferimentoType refModulo = modulo.getRiferimento();
	String retVal = StringUtils.isNotEmpty(refModulo.getCodiceModello()) ? refModulo.getCodiceModello() : refModulo.getCodiceEndoProcedimento();
	if (StringUtils.isEmpty(retVal)) {
	    log.warn(
		    "getRiferimentoModulo() - il modulo {} non contiene nè il riferimento al modello nè il riferimento all'endoprocedimento, come ID del modulo sarà utilizzato il titolo.",
		    modulo.getTitolo());
	    retVal = modulo.getTitolo();
	}
	return retVal;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public static String buildIdModuloFromRefModulo(String refModulo) {

	String retVal = refModulo;
	if (retVal != null) {
	    Matcher m = REPLACE_WITH_UNDERSCORE_REGEX.matcher(retVal);
	    retVal = m.replaceAll("_");
	    m = REPLACE_WITH_MINUS_REGEX.matcher(retVal);
	    retVal = m.replaceAll("-");
	    m = REPLACE_WITH_A_REGEX.matcher(retVal);
	    retVal = m.replaceAll("A");
	    m = REPLACE_WITH_E_REGEX.matcher(retVal);
	    retVal = m.replaceAll("E");
	    m = REPLACE_WITH_I_REGEX.matcher(retVal);
	    retVal = m.replaceAll("I");
	    m = REPLACE_WITH_O_REGEX.matcher(retVal);
	    retVal = m.replaceAll("O");
	    m = REPLACE_WITH_U_REGEX.matcher(retVal);
	    retVal = m.replaceAll("U").toLowerCase();
	    if (Character.isDigit(retVal.charAt(0))) {
		retVal = "var_" + retVal;
	    }
	} else {
	    retVal = "null";
	}
	return retVal;
    }

    public static Boolean evaluateExpression(EspressioneType expression, DatiDomandaCart inputData, IndiceIdSemantico rowIndex) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	if (null != expression) {
	    if (null != expression.isValore()) {
		retVal = expression.isValore();
	    } else if (null != expression.getOperando()) {
		OperandoType operand = expression.getOperando();
		retVal = evaluateOperand(operand, inputData, rowIndex);
	    } else if (null != expression.getOperazione()) {
		OperazioneType operation = expression.getOperazione();
		retVal = evaluateOperation(operation, inputData, rowIndex);
	    }
	}
	// §§§END§§§
	return retVal;
    }

    public static Boolean evaluateOperand(OperandoType operand, DatiDomandaCart inputData, IndiceIdSemantico rowIndex) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	if (null != operand) {
	    String actualValue = null;
	    // se l'espressione che si valuta appartiene ad un campo indicizzato
	    // limito il confronto al valore che si trova all'indice specificato
	    ValoreIdSemantico valoreIdSemantico = inputData.searchValoreIdSemanticoPerIndice(operand.getIdSemantico(), rowIndex);
	    // String actualValue =
	    // inputData.getValoreScalare(operand.getIdSemantico());
	    String compareWithValue = operand.getValoreConfronto();
	    String[] compareValues = null;
	    String[] actualValues = new String[0];
	    switch (operand.getOperatore()) {
	    case IN:
	    case EQ:
		if (null != valoreIdSemantico && !valoreIdSemantico.isEmpty()) {
		    if (operand.getOperatore().equals(OperatoreType.IN)) {
			compareValues = compareWithValue.split("\\|");
		    } else {
			compareValues = new String[] { compareWithValue };
		    }
		    if (valoreIdSemantico.isScalare()) {
			actualValues = new String[] { valoreIdSemantico.getValoreScalare() };
			// retVal = ArrayUtils.contains(compareValues,
			// actualValue);
		    } else if (valoreIdSemantico.isVettoriale()) {
			/*
			 * StringBuilder errMsg = new
			 * StringBuilder("Errore nella valutazione dell'operando ["
			 * ).append(operand.getIdSemantico());
			 * errMsg.append(" ")
			 * .append(operand.getOperatore().value
			 * ()).append(" ").append(operand.getValoreConfronto());
			 * errMsg
			 * .append("]: il campo associato all'id semantico "
			 * ).append(operand.getIdSemantico()); if (rowIndex >
			 * -1) { errMsg.append(" all'indice ").append(rowIndex);
			 * } errMsg.append(
			 * " contiene valori multipli che non possono essere confrontati con l'operatore IN, utilizzare l'operatore CO"
			 * ); //throw new RuntimeException(errMsg.toString());
			 * log.error("evaluateOperand() - {}", new
			 * Object[]{errMsg.toString()});
			 */
			actualValues = valoreIdSemantico.getValoreVettoriale();
		    }
		}
		for (int i = 0; i < actualValues.length; i++) {
		    actualValue = actualValues[i];
		    retVal = ArrayUtils.contains(compareValues, actualValue);
		    if (retVal) {
			break;
		    }
		}
		break;
	    case EX:
		retVal = inputData.hasValore(operand.getIdSemantico());
		break;
	    case CO:
		actualValues = new String[0];
		if (null != valoreIdSemantico) {
		    if (valoreIdSemantico.isScalare()) {
			actualValues = new String[] { valoreIdSemantico.getValoreScalare() };
		    } else if (valoreIdSemantico.isVettoriale()) {
			actualValues = valoreIdSemantico.getValoreVettoriale();
		    }
		}
		retVal = ArrayUtils.contains(actualValues, compareWithValue);
		break;
	    case LT:
	    case GT:
		StringBuilder errMsg;
		if (null != valoreIdSemantico && !valoreIdSemantico.isEmpty()) {
		    errMsg = null;
		    if (valoreIdSemantico.isScalare()) {
			actualValue = valoreIdSemantico.getValoreScalare();
		    } else {
			errMsg = new StringBuilder("Errore nella valutazione dell'operando [").append(operand.getIdSemantico());
			errMsg.append(" ").append(operand.getOperatore().value()).append(" ").append(operand.getValoreConfronto());
			errMsg.append("]: il campo associato all'id semantico ").append(operand.getIdSemantico());
			if (rowIndex.contaLivelli() > 0) {
			    errMsg.append(" all'indice ").append(rowIndex.getIndice());
			}
			errMsg.append(" contiene valori multipli che non possono essere confrontati con l'operatore GT");
			log.error("evaluateOperand() - {}", new Object[] { errMsg.toString() });
		    }
		    if (NumberUtils.isNumber(actualValue) && NumberUtils.isNumber(compareWithValue)) {
			double dbl1 = NumberUtils.createDouble(actualValue);
			double dbl2 = NumberUtils.createDouble(compareWithValue);
			retVal = operand.getOperatore() == OperatoreType.GT ? dbl1 > dbl2 : dbl1 < dbl2;
		    } else {
			errMsg = new StringBuilder("Errore nella valutazione dell'operando [").append(operand.getIdSemantico());
			errMsg.append(" ").append(operand.getOperatore().value()).append(" ").append(operand.getValoreConfronto());
			if (!NumberUtils.isNumber(actualValue)) {
			    errMsg.append("]: il campo associato all'id semantico ").append(operand.getIdSemantico());
			    errMsg.append(" non ha un valore numerico che può essere confrontato con l'operatore ").append(
				    operand.getOperatore().value());
			} else if (!NumberUtils.isNumber(compareWithValue)) {
			    errMsg.append("]: il valore di confronto ").append(operand.getValoreConfronto());
			    errMsg.append(" non ha un valore numerico che può essere confrontato con l'operatore ").append(
				    operand.getOperatore().value());
			}
			log.error("evaluateOperand() - {}", new Object[] { errMsg.toString() });
		    }
		}
		break;
	    default:
		errMsg = new StringBuilder("Errore nella valutazione dell'operando [").append(operand.getIdSemantico());
		errMsg.append(" ").append(operand.getOperatore().value()).append(" ").append(operand.getValoreConfronto());
		errMsg.append("]: operatore ").append(operand.getOperatore().value()).append(" non supportato.");
		// log.error("evaluateOperand() - operatore non supportato {}",
		// operand.getOperatore().name());
		log.error("evaluateOperand() - {}", new Object[] { errMsg.toString() });
	    }
	}
	// §§§END§§§
	return retVal;
    }

    public static Boolean evaluateOperation(OperazioneType operation, DatiDomandaCart inputData, IndiceIdSemantico rowIndex) {

	Boolean retVal = Boolean.FALSE;
	// §§§BEGIN§§§
	if (null != operation) {
	    List<EspressioneType> espressioni = operation.getEspressione();
	    if (null != espressioni && espressioni.size() > 0) {
		retVal = evaluateExpression(espressioni.get(0), inputData, rowIndex);
		if (operation.getOperatoreLogico() == OperatoreLogicoType.NOT) {
		    retVal = !retVal;
		} else if (espressioni.size() > 1) {
		    for (int i = 1; i < espressioni.size(); i++) {
			Boolean otherExpression = evaluateExpression(espressioni.get(i), inputData, rowIndex);
			switch (operation.getOperatoreLogico()) {
			case AND:
			    retVal = retVal && otherExpression;
			    break;
			case OR:
			    retVal = retVal || otherExpression;
			    break;
			default:
			    log.error("evaluateOperation() - operatore logico non supportato {}", operation.getOperatoreLogico().name());
			    break;
			}
		    }
		}
	    }
	}
	// §§§END§§§
	return retVal;
    }

    /**
     * restituisce il numero di valori memorizzati nell'oggetto DatiDomandaCart in corrispondenza del primo id semantico
     * trovato nei campi (di tipo CampoType oppure FileType) all'interno della tabella, dando per scontato che tutti i
     * campi della tabella sono ripetuti tante volte quante sono le righe della tabella. Se l'argomento {@link ItemType}
     * non contiene direttamente una tabella viene restituito il valore 0.
     */
    public static int contaRigheTabella(ItemType tabella, DatiDomandaCart dati, IndiceIdSemantico indice) {

	int numRighe = 0;
	// §§§BEGIN§§§
	ItemType campoIndicizzato = estraiCampoRecursive(tabella);
	String idSemantico = null;
	if (campoIndicizzato.getCampo() != null) {
	    idSemantico = campoIndicizzato.getCampo().getIdSemantico();
	} else if (campoIndicizzato.getFile() != null) {
	    idSemantico = campoIndicizzato.getFile().getIdSemantico();
	}
	if (StringUtils.isNotEmpty(idSemantico)) {
	    ValoreIdSemantico valore = dati.searchValoreIdSemantico(idSemantico);
	    Integer[] indexes = indice.vettoreIndici();
	    for (int i = 0; i < indexes.length - 1; i++) {
		if (valore != null && valore.isIndicizzato() && valore.getValoreIndicizzato().size() > indexes[i]) {
		    valore = valore.getValoreIndicizzato().get(indexes[i]);
		} else {
		    valore = null;
		    break;
		}
	    }
	    numRighe = valore != null && valore.getValoreIndicizzato() != null ? valore.getValoreIndicizzato().size() : 0;
	} else {
	    log.warn("contaRigheTabella() - impossibile trovare campi all'interno dell'item passato come argomento");
	}
	// §§§END§§§
	return numRighe;
    }

    public Boolean isUltimoQuadro(QuadroType quadro) {

	Boolean retVal = Boolean.FALSE;
	if (quadro != null) {
	    List<QuadroType> quadriModulo = getModulo().getQuadro();
	    int quadroPos = quadriModulo.indexOf(quadro);
	    if (quadroPos == quadriModulo.size() - 1) {
		retVal = Boolean.TRUE;
	    }
	}
	return retVal;
    }

    public static String removeIndexingSuffix(String indexedId) {

	String retVal = indexedId;
	if (StringUtils.isNotBlank(indexedId)) {
	    String[] parts = indexedId.split(FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR);
	    if (parts != null && parts.length > 0) {
		retVal = parts[0];
	    }
	}
	return retVal;
    }

    /**
     * Scorre ricorsivamente la struttura ad albero della modulistica e restituisce il primo {@link ItemType} il cui
     * contenuto sia di tipo campo {@link CampoType} o di tipo {@link FileType}.
     * 
     * @param item
     * @return
     */
    private static ItemType estraiCampoRecursive(ItemType item) {

	ItemType retItem = null;
	// §§§BEGIN§§§
	if (item.getCampo() != null || item.getFile() != null) {
	    retItem = item;
	} else if (item.getSezione() != null) {
	    SezioneType sezione = item.getSezione();
	    for (ItemType itemSezione : sezione.getItem()) {
		retItem = estraiCampoRecursive(itemSezione);
		if (retItem != null) {
		    break;
		}
	    }
	} else if (item.getTabella() != null) {
	    TabellaType tabella = item.getTabella();
	    List<ColonnaType> colonne = tabella.getColonna();
	    for (ColonnaType colonna : colonne) {
		retItem = estraiCampoRecursive(colonna.getCella());
		if (retItem != null) {
		    break;
		}
	    }
	}
	// §§§END§§§
	return retItem;
    }

    /*
     * public List<ChiaveValoreBean<Integer, String>> getAllegatiIstanza(Integer
     * codiceIstanza){
     * 
     * }
     */
    private List<ModellidinamiciHelper> quadriAggiuntivi = new ArrayList<ModellidinamiciHelper>();

    public List<ModellidinamiciHelper> getQuadriAggiuntivi() {

	return quadriAggiuntivi;
    }

    public void setQuadriAggiuntivi(List<ModellidinamiciHelper> quadriAggiuntivi) {

	this.quadriAggiuntivi = quadriAggiuntivi;
    }

    public static String getIdQuadroDinamico(ModellidinamiciHelper quadro) {

	StringBuilder sb = new StringBuilder();
	if (null != quadro) {
	    sb.append(String.valueOf(quadro.getIdcomune()));
	    sb.append("-");
	    sb.append(String.valueOf(quadro.getIdModello()));
	    if(StringUtils.isNotEmpty(quadro.getTemplateFor())){
		sb.append("-").append(quadro.getTemplateFor().replaceAll(" ", "_"));
	    }
	}
	return sb.toString();
    }

    public static String cleanCodiceEndoLoc(String codEndo) {

	String retCod = codEndo;
	if (retCod != null) {
	    retCod = retCod.replace('|', '-');
	}
	return retCod;
    }

    public boolean isQuadroDinamico(QuadroType q) {

	return q != null && q instanceof QuadroDinamicoType;
    }
    
}
