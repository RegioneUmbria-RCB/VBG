/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ValoreIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciTabellaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreLogicoEnum;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author francol
 * 
 */
public class Dyn2RegoleHelper {

    private static final Logger log = LoggerFactory.getLogger(Dyn2RegoleHelper.class);
    private DettaglioPraticaType stcDomain;
    private DatiDomandaCart cartDomain;
    private Dyn2ModellidService dyn2ModellidService;
    private Dyn2CampiService dyn2CampiService;
    private Map<Integer, FunzioneDinamicaHelper> cacheFunzioni = new HashMap<Integer, FunzioneDinamicaHelper>();

    public Dyn2RegoleHelper(Dyn2ModellidService dyn2ModellidService, Dyn2CampiService dyn2CampiService) {

	this.dyn2ModellidService = dyn2ModellidService;
	this.dyn2CampiService = dyn2CampiService;
    }

    public DettaglioPraticaType getStcDomain() {

	return stcDomain;
    }

    public void setStcDomain(DettaglioPraticaType stcDomain) {

	this.stcDomain = stcDomain;
    }

    public DatiDomandaCart getCartDomain() {

	return cartDomain;
    }

    public void setCartDomain(DatiDomandaCart cartDomain) {

	this.cartDomain = cartDomain;
    }

    /**
     * Restituisce un'istanza di {@link EspressioneBooleana} a partire da una istanza di {@link Dyn2Espressioni}.
     * L'oggetto restituito sarà una istanza di {@link OperazioneConfronto} che rappresenta il confronto specificato
     * nell'argomento, Nel caso di operatori di confronto negativi (NOT_EQ = NOT EQ, GT_EQ = NOT LT, etc...) l'oggetto
     * restituito sarà una istanza di {@link OperazioneLogica} di tipo NOT applicata all'{@link OperazioneConfronto}
     * specificata.
     * 
     * @param expr
     * @return
     * @throws Dyn2RegoleSyntaxError
     */
    @SuppressWarnings("incomplete-switch")
    public EspressioneBooleana buildEspressioneConfronto(Dyn2Espressioni expr, ModellidinamiciTabellaHelper forBlocco, Integer indiceBlocco)
	    throws Dyn2RegoleSyntaxError {

	Object val = getValueForExpression(expr,forBlocco,indiceBlocco);
	OperatoreConfrontoEnum operType = OperatoreConfrontoEnum.valueOf(expr.getOperatoreConfronto());
	EspressioneBooleana boolExpr = new OperazioneConfronto(val, operType, expr.getValoreConfronto());
	switch (operType) {
	case NOT_EQ:
	case GT_EQ:
	case LT_EQ:
	case NOT_IS_NULL:
	case NOT_IN:
	case NOT_MATCHES:
	    boolExpr = new OperazioneLogica(boolExpr, OperatoreLogicoEnum.NOT, null);
	    break;
	}
	return boolExpr;
    }

    /**
     * Restituisce una {@link EspressioneBooleana} a partire da una {@link List} di {@link Dyn2Espressioni} collegate
     * fra loro da operatori logici. Nel caso di parentesi il metodo invoca se stesso ricorsivamente.
     * 
     * @param exprs
     * @return
     */
    private EspressioneBooleana buildEspressioneBooleana(List<Dyn2Espressioni> exprs, MutableInt fromIndex, MutableInt computedOpenBrackets,
	    MutableInt computedClosedBrackets, ModellidinamiciTabellaHelper forBlocco, Integer indiceBlocco) throws Dyn2RegoleSyntaxError {

	//TODO throw Dyn2RegoleSyntaxError on syntax errors
	EspressioneBooleana prevBoolExpr = null;
	Dyn2Espressioni expr = null;
	Dyn2Espressioni expr2 = null;
	for (; fromIndex.intValue() < exprs.size(); fromIndex.add(1)) {
	    expr = exprs.get(fromIndex.intValue());
	    EspressioneBooleana boolExpr = null;
	    //gestione parentesi aperte
	    int numBrackets = contaParentesi(expr.getParentesiAperta());
	    //prima espressione
	    if (prevBoolExpr == null) {
		//Se presenti le parentesi escludo quelle eventualmente già elaborate dal metodo chiamante
		numBrackets = numBrackets - computedOpenBrackets.intValue();
	    }
	    //espressioni successive
	    else {
		computedOpenBrackets = new MutableInt(0);
	    }
	    MutableInt closedBrackets = new MutableInt(0);
	    //se ci sono parentesi aperte ancora da elaborare elaboro la più esterna
	    if (numBrackets > 0) {
		computedOpenBrackets.add(1);
		boolExpr = buildEspressioneBooleana(exprs, fromIndex, computedOpenBrackets, closedBrackets, forBlocco, indiceBlocco);
	    } else {
		boolExpr = buildEspressioneConfronto(expr, forBlocco, indiceBlocco);
	    }
	    //per esppressioni successive alla prima
	    if (prevBoolExpr != null) {
		//elaboro l'operatore logico fra questa espressione e la precedente
		OperatoreLogicoEnum operType = OperatoreLogicoEnum.valueOf(expr.getOperatoreLogico());
		boolExpr = new OperazioneLogica(prevBoolExpr, operType, boolExpr);
	    }
	    prevBoolExpr = boolExpr;
	    //gestione parentesi chiuse
	    /*
	     * se ci sono state chiamate ricorsive per le parentesi, l'espressione corrente non è più quella
	     * recuperata dalla lista all'inizio dell'iterazione del ciclo ma deve essere di nuovo recuperata al 
	     * nuovo valore che nel frattempo ha assunto fromIndex
	     */
	    if (numBrackets > 0) {
		expr = exprs.get(fromIndex.intValue());
	    }
	    numBrackets = contaParentesi(expr.getParentesiChiusa());
	    numBrackets = numBrackets - computedClosedBrackets.intValue();
	    if (numBrackets > 0) {
		break;
	    }
	}
	return prevBoolExpr;
    }

    public Boolean evaluateExpression(Dyn2Espressioni expr) throws Dyn2RegoleSyntaxError {

	return buildEspressioneConfronto(expr,null,null).valutaBoolean();
    }
    
    public Boolean evaluateRule(Dyn2Regole rule) throws Dyn2RegoleSyntaxError {
	
	return evaluateRule(rule,null,null);
    }

    public Boolean evaluateRule(Dyn2Regole rule, ModellidinamiciTabellaHelper blocco, Integer indiceBlocco) throws Dyn2RegoleSyntaxError {

	Boolean retVal = null;
	if (null != rule) {
	    List<Dyn2Espressioni> exprs = new ArrayList<Dyn2Espressioni>(rule.getDyn2Espressionis());
	    EspressioneBooleana resultingExpr = buildEspressioneBooleana(exprs, new MutableInt(0), new MutableInt(0), new MutableInt(0), blocco,
		    indiceBlocco);
	    retVal = resultingExpr.valutaBoolean();
	}
	return retVal;
    }

    /**
     * Metodo che consente di recuperare il valore (o i valori) da confrontare durante la valutazione delle espressioni
     * booleane che formano le regole. I dati vengono recuperati dalle strutture dati del dominio di stc o del cart in
     * base a quanto richiesto dall'espressione passata come argomento.
     * 
     * @param expr
     * @return
     * @throws Dyn2RegoleSyntaxError
     */
    public Object getValueForExpression(Dyn2Espressioni expr, ModellidinamiciTabellaHelper forBlocco, Integer indiceBlocco)
	    throws Dyn2RegoleSyntaxError {

	Object value = null;
	if (expr != null) {
	    if (StringUtils.isNotBlank(expr.getAttributoStc())) {
		value = getStcValue(expr.getAttributoStc());
	    } else if (expr.hasDyn2Campo()) {
		value = getDynValue(expr.getDyn2Campi(), forBlocco, indiceBlocco);
	    } else if (StringUtils.isNotBlank(expr.getIdSemanticoCart())) {
		value = getCartValue(expr.getIdSemanticoCart());
	    }
	}
	return value;
    }

    public FunzioneDinamicaHelper generaFunzioneElaborazioneRegola(Dyn2Modellid d2md) {

	StringBuilder js = new StringBuilder();
	Dyn2Regole regola = d2md.getDyn2RegoleAttivo();
	FunzioneDinamicaHelper dynFuncH = null;
	if (null != regola && regola.getId().getCodice() != null) {
	    dynFuncH = new FunzioneDinamicaHelper();
	    dynFuncH.setNomeFunzione("elaboraRegola_" + regola.getId().getCodice());
	    List<String> funcArgs = Arrays.asList(new String[] { "idx", "idxMulti", "idxBlocco", "defaultValue", "stessoBlocco" });
	    dynFuncH.setArgomentiFunzione(funcArgs);
	    indent(js, 3).append("var result = defaultValue;\r\n");
	    Set<Dyn2Espressioni> espressioni = d2md.getDyn2RegoleAttivo().getDyn2Espressionis();
	    //devo distinguere le espressioni relative a attributi statici della pratica o relative a campi dinamici appartenenti ad altre schede
	    if (espressioni != null && !espressioni.isEmpty()) {
		indent(js, 3).append("result = ");
		Boolean evaluatedExpr = null;
		for (Dyn2Espressioni espressione : espressioni) {
		    try {
			if (StringUtils.isNotEmpty(espressione.getOperatoreLogico())) {
			    OperatoreLogicoEnum opLogico = OperatoreLogicoEnum.valueOf(espressione.getOperatoreLogico());
			    switch (opLogico) {
			    case AND:
				js.append(" && ");
				break;
			    case OR:
				js.append(" || ");
				break;
			    default:
				break;
			    }
			}
			js.append(StringUtils.defaultString(espressione.getParentesiAperta()));
			if (StringUtils.isNotBlank(espressione.getAttributoStc()) || StringUtils.isNotBlank(espressione.getIdSemanticoCart())) {
			    evaluatedExpr = evaluateExpression(espressione);
			    js.append(evaluatedExpr.booleanValue());
			} else if (espressione.getDyn2Campi() != null) {
			    //verificare se il campo dinamico appartiene allo stesso modello
			    List<Dyn2Campi> campiModello = dyn2CampiService.findByIdModello(d2md.getDyn2Modellit().getId().getCodice());
			    boolean stessoModello = false;
			    PkId searchForId = espressione.getDyn2Campi().getId();
			    for (Dyn2Campi campo : campiModello) {
				PkId compareId = campo.getId();
				if (compareId != null) {
				    if (searchForId.getCodice().equals(compareId.getCodice())) {
					stessoModello = true;
					dynFuncH.getCampiOnChange().add(espressione.getDyn2Campi());
					break;
				    }
				}
			    }
			    if (stessoModello) {
				//lettura valore runtime di campo presente nella scheda
				js.append(getJsCompareExpression(espressione));
			    } else {
				evaluatedExpr = evaluateExpression(espressione);
				js.append(evaluatedExpr.booleanValue());
			    }
			}
			js.append(StringUtils.defaultString(espressione.getParentesiChiusa()));
		    } catch (Dyn2RegoleSyntaxError e) {
			log.error(
				"generaScriptElaborazioneRegola - si è verificato un errore durante l'eleborazione della regola '{}': {}. La regola non sarà applicata.",
				new Object[] { regola.getDescrizione(), e });
			return null;
		    }
		}
		js.append(";\r\n");
	    }
	    indent(js, 3).append("return result;");
	    dynFuncH.setCorpoFunzione(js.toString());
	    //indent(js, 2).append("};");
	    this.cacheFunzioni.put(regola.getId().getCodice(), dynFuncH);
	}
	return dynFuncH;
    }

    public FunzioneDinamicaHelper recuperaFunzioneElaborazioneRegola(Dyn2Modellid d2md) {

	FunzioneDinamicaHelper retFunc = null;
	Dyn2Regole regola = d2md.getDyn2RegoleAttivo();
	if (null != regola && regola.getId().getCodice() != null) {
	    retFunc = this.cacheFunzioni.get(regola.getId().getCodice());
	}
	return retFunc;
    }

    public static StringBuilder indent(StringBuilder sb, int numTabs) {

	if (sb != null) {
	    for (int i = 0; i < numTabs; i++) {
		sb.append("\t");
	    }
	}
	return sb;
    }

    /*
     *   INIZIO METODI PRIVATI
     */
    /*
     * 
     */
    private String getJsCompareExpression(Dyn2Espressioni expr) {

	String js = "";
	if (expr.hasDyn2Campo()) {
	    String funcName = "";
	    OperatoreConfrontoEnum operType = OperatoreConfrontoEnum.valueOf(expr.getOperatoreConfronto());
	    switch (operType) {
	    case EQ:
		funcName = "valueEquals({0},{1})";
		break;
	    case NOT_EQ:
		funcName = "!valueEquals({0},{1})";
		break;
	    case LT:
		funcName = "valueCompare(false,false,{0},{1})";
		break;
	    case GT_EQ:
		funcName = "valueCompare(true,true,{0},{1})";
		break;
	    case GT:
		funcName = "valueCompare(true,false,{0},{1})";
		break;
	    case LT_EQ:
		funcName = "valueCompare(false,true,{0},{1})";
		break;
	    case IS_NULL:
		funcName = "valueIsNull({0})";
		break;
	    case NOT_IS_NULL:
		funcName = "!valueIsNull({0})";
		break;
	    case IN:
		funcName = "valueIn({0},{1})";
		break;
	    case NOT_IN:
		funcName = "!valueIn({0},{1})";
		break;
	    case MATCHES:
		funcName = "valueMatches({0},{1})";
		break;
	    case NOT_MATCHES:
		funcName = "!valueMatches({0},{1})";
		break;
	    default:
		break;
	    }
	    StringBuilder jquery = new StringBuilder("readValuesFromElements(jQuery(\"[name^='FLD_");
	    jquery.append(expr.getDyn2Campi().getId().getCodice()).append("_");
	    jquery.append("']\"), idx, idxMulti, idxBlocco, stessoBlocco)");
	    String compareValueLiteral = getJsCompareValueLiteral(expr);
	    js = MessageFormat.format(funcName, new Object[] { jquery, compareValueLiteral });
	}
	return js;
    }

    @SuppressWarnings("unchecked")
    private String getJsCompareValueLiteral(Dyn2Espressioni expr) {

	StringBuilder sb = new StringBuilder();
	OperatoreConfrontoEnum operType = OperatoreConfrontoEnum.valueOf(expr.getOperatoreConfronto());
	//String[] splittedCompValue = null;
	if (!operType.equals(OperatoreConfrontoEnum.IS_NULL) && !operType.equals(OperatoreConfrontoEnum.NOT_IS_NULL)
		&& StringUtils.isNotEmpty(expr.getValoreConfronto())) {
	    if (operType.equals(OperatoreConfrontoEnum.MATCHES) || operType.equals(OperatoreConfrontoEnum.NOT_MATCHES)) {
		//il valore di confronto viene passato come regex js (case insensitive e multiline)
		//TODO verificare se ci sono caratteri di cui fare escape
		sb.append("/").append(expr.getValoreConfronto()).append("/im");
	    } else {
		try {
		    Object compareObject = getValoreConfrontoTipizzato(expr);
		    List<Object> compValues = new ArrayList<Object>();
		    if (compareObject instanceof Collection) {
			sb.append("[");
			compValues.addAll((Collection<Object>) compareObject);
		    } else {
			compValues.add(compareObject);
		    }
		    for (int i = 0; i < compValues.size(); i++) {
			if (i > 0) {
			    sb.append(", ");
			}
			sb.append(getJsLiteral(compValues.get(i)));
		    }
		    if (compareObject instanceof Collection) {
			sb.append("]");
		    }
		} catch (Dyn2RegoleSyntaxError e) {
		    log.error(
			    "getJsCompareValueLiteral - impossibile convertire il valore di confronto {} nel tipo di dato previsto dal campo. Sarà considerato come stringa.",
			    new Object[] { expr.getValoreConfronto() });
		    //TODO escape apici
		    sb.append("\"").append(expr.getValoreConfronto()).append("\"");
		}
	    }
	}
	return sb.toString();
    }

    private Object getStcValue(String stcXPath) {

	Object value = null;
	if (stcDomain != null) {
	    try {
		value = EntityUtils.getPropertyValues(stcDomain, stcXPath);
	    } catch (Exception e) {
		log.error("getStcValue - errore nella lettura della proprietà STC {}: {}", new Object[] { stcXPath, e.getMessage() });
	    }
	}
	return value;
    }

    private Object getCartValue(String idSemantico) {

	Object value = null;
	if (cartDomain != null) {
	    ValoreIdSemantico vis = cartDomain.searchValoreIdSemantico(idSemantico);
	    if (vis != null) {
		if (vis.isScalare()) {
		    value = vis.getValoreScalare();
		} else if (vis.isVettoriale()) {
		    value = Arrays.asList(vis.getValoreVettoriale());
		} else if (vis.isIndicizzato()) {
		    List<ValoreIdSemantico> vals = vis.getValoreIndicizzato();
		    if (vals != null) {
			List<Object> listVal = new ArrayList<Object>();
			for (Iterator<ValoreIdSemantico> valIter = vals.iterator(); valIter.hasNext();) {
			    ValoreIdSemantico val = valIter.next();
			    if (val.isScalare()) {
				listVal.add(val.getValoreScalare());
			    } else if (val.isVettoriale()) {
				listVal.add(Arrays.asList(vis.getValoreVettoriale()));
			    }
			}
			value = listVal;
		    }
		}
	    }
	}
	return value;
    }

    private Object getDynValue(Dyn2Campi dynCampo, ModellidinamiciTabellaHelper forBlocco, Integer indiceBlocco) throws Dyn2RegoleSyntaxError {

	Object value = null;
	List<SchedaType> schede = stcDomain.getSchede();
	CampoSchedaType stcCampo = null;
	for (int i = 0; i < schede.size() && stcCampo == null; i++) {
	    SchedaType scheda = schede.get(i);
	    List<CampoSchedaType> campiScheda = scheda.getCampi();
	    for (CampoSchedaType campoScheda : campiScheda) {
		if (campoScheda.getCodice().equals(dynCampo.getId().getCodice().toString())) {
		    stcCampo = campoScheda;
		    break;
		}
	    }
	}
	if (stcCampo != null) {
	    /*
	     * TODO verifico se il campo dinamico di cui verifico il valore si trova nello stesso blocco 
	     * a cui appartiene il campo per cui sto elaborando la regola
	     */
	    boolean stessoBlocco = false;
	    //nel caso di campi non appartenenti a blocchi l'argomento indiceBlocco viene passato con valore null
	    if (forBlocco != null && indiceBlocco != null) {
		stessoBlocco = forBlocco.containsCampo(dynCampo);
	    }
	    ValoreCampoDinamicoType dynData = stcCampo.getCampoDinamico().getValoreUtente();
	    if (null != dynData) {
		List<ElementoValoreCampoDinamicoType> dynValues = dynData.getValore();
		//individuo la molteplicità prevista per il campo dinamico
		boolean indicizzato = false;
		List<Dyn2Modellid> d2mds = this.dyn2ModellidService.findByCampo(dynCampo.getId().getCodice());
		for (Dyn2Modellid d2md : d2mds) {
		    if (BooleanUtils.isTrue(d2md.getFlgMultiplo())) {
			indicizzato = true;
			break;
		    }
		}
		int maxLoop = 1;
		List<Object> listValue = null;
		if (dynValues.size() > 1 || indicizzato) {
		    listValue = new ArrayList<Object>(dynValues.size());
		    value = listValue;
		    maxLoop = dynValues.size();
		} else {
		    maxLoop = Math.min(maxLoop, dynValues.size());
		}
		for (int i = 0; i < maxLoop; i++) {
		    if (!stessoBlocco || (stessoBlocco && indiceBlocco.intValue() == i)) {
			ElementoValoreCampoDinamicoType dynValue = dynValues.get(i);
			Object innerVal = getValoreDinamicoTipizzato(dynValue.getDescrizione(), dynCampo);
			if (listValue != null) {
			    listValue.add(innerVal);
			} else {
			    value = innerVal;
			}
		    }
		}
	    }
	}
	return value;
    }

    private Object getValoreDinamicoTipizzato(String decodedVal, Dyn2Campi dynCampo) throws Dyn2RegoleSyntaxError {

	Object retVal = decodedVal;
	String tipoDatoString = dynCampo.getTipodato();
	TipoControlloEnum tipoDato = TipoControlloEnum.valueOf(tipoDatoString);
	OperatoreConfrontoEquals op = new OperatoreConfrontoEquals();
	switch (tipoDato) {
	case Data:
	    retVal = op.getValoreData(decodedVal);
	    break;
	case NumericoDouble:
	case NumericoIntero:
	    retVal = op.getValoreNumero(decodedVal);
	    break;
	default:
	    break;
	}
	return retVal;
    }

    /*
     * Viene invocato solo per espressioni con valore di confronto valorizzato e operatore di confronto diverso da IS_NULL e NOT_IS_NULL
     */
    private Object getValoreConfrontoTipizzato(Dyn2Espressioni expr) throws Dyn2RegoleSyntaxError {

	Object retVal = null;
	OperatoreConfrontoEnum operType = OperatoreConfrontoEnum.valueOf(expr.getOperatoreConfronto());
	if (operType.equals(OperatoreConfrontoEnum.IN) || operType.equals(OperatoreConfrontoEnum.NOT_IN)) {
	    List<Object> splittedRetVal = new ArrayList<Object>();
	    String[] splittedCompVal = expr.getValoreConfronto().split("[|]");
	    for (int i = 0; i < splittedCompVal.length; i++) {
		if (StringUtils.isNotEmpty(splittedCompVal[i])) {
		    splittedRetVal.add(getValoreDinamicoTipizzato(splittedCompVal[i], expr.getDyn2Campi()));
		}
	    }
	    retVal = splittedRetVal;
	} else {
	    retVal = getValoreDinamicoTipizzato(expr.getValoreConfronto(), expr.getDyn2Campi());
	}
	return retVal;
    }

    private String getJsLiteral(Object val) {

	StringBuilder literal = new StringBuilder();
	if (val instanceof BigDecimal) {
	    literal.append(((BigDecimal) val).toPlainString());
	} else if (val instanceof Date) {
	    Date data = (Date) val;
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(data);
	    literal.append("new JsDate(");
	    literal.append(calendar.get(Calendar.YEAR)).append(",");
	    literal.append(calendar.get(Calendar.MONTH) + 1).append(",");
	    literal.append(calendar.get(Calendar.DATE)).append(")");
	} else {
	    //in tutti gli altri casi il valore viene trattato come una stringa
	    literal.append("\"");
	    //TODO escape apici dal valore
	    literal.append(val);
	    literal.append("\"");
	}
	return literal.toString();
    }

    public static int contaParentesi(String parentesiEspressione) {

	int count = 0;
	Character searchForChar = null;
	parentesiEspressione = StringUtils.defaultString(parentesiEspressione);
	String validBrackets = new String(new char[] { Dyn2RegoleService.PARENTESI_APERTA, Dyn2RegoleService.PARENTESI_CHIUSA });
	for (char c : parentesiEspressione.toCharArray()) {
	    if (searchForChar != null) {
		if (c == searchForChar.charValue()) {
		    count++;
		}
	    } else if (validBrackets.indexOf(c) > -1) {
		searchForChar = c;
		count++;
	    }
	}
	return count;
    }
}
