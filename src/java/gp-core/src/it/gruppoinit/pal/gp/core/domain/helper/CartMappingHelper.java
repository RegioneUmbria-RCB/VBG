package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.OperatorType;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueMapping;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueMappings;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueProcessing;
import it.gruppoinit.pal.gp.core.domain.cart.mapping.ValueReplace;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreLogicoEnum;
import it.gruppoinit.pal.gp.core.service.regole.EspressioneBooleana;
import it.gruppoinit.pal.gp.core.service.regole.OperazioneConfronto;
import it.gruppoinit.pal.gp.core.service.regole.OperazioneLogica;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartMappingHelper {

    private static final Logger log = LoggerFactory.getLogger(CartMappingHelper.class);

    /*
    public ValoreIdSemantico getValoreIdSemantico(CartMapping mapping) {

    ValoreIdSemantico vis = null;
    if (mapping != null && StringUtils.isNotBlank(mapping.getIdSemantico())) {
        ValueBuilder vb = mapping.getValue();
        List<VbgValue> vbgValues = vb.getVbgValues();
        if (valueSrc.getValoreFisso() != null) {
    	vis = new ValoreIdSemantico(valueSrc.getValoreFisso());
        } else if (valueSrc.getCampoDinamico() != null) {
    	CampoDinamico dynCampo = valueSrc.getCampoDinamico();
    	if (StringUtils.isNotEmpty(dynCampo.getCodiceCampo()) && StringUtils.isNotEmpty(dynCampo.getSoftware())) {
    	}
        } else if (true) {
        } else {
        }
    }
    return vis;
    }
    */
    public static String getValoreCartMappato(String vbgValue, ValueMappings valueMappings) {

	String mappedValue = vbgValue;
	if (null != valueMappings) {
	    List<ValueMapping> mappings = valueMappings.getValueMappings();
	    for (ValueMapping mapping : mappings) {
		if (StringUtils.isBlank(mapping.getVbgValueMatch())) {
		    mapping.setVbgValueMatch(FACCTConstants.REGEX_ALL_WORDS_MATCH);
		}
		Pattern matchPattern = Pattern.compile(mapping.getVbgValueMatch(), Pattern.CASE_INSENSITIVE);
		Matcher matcher = matchPattern.matcher(vbgValue);
		if (matcher.matches()) {
		    mappedValue = mapping.getCartValue();
		    break;
		}
	    }
	}
	return mappedValue;
    }

    public static CartMappingIndexedProperty getProprietaIndicizzataIstanza(Istanze istanza, String propertyPath) throws Exception {

	CartMappingIndexedProperty retProp = null;
	//String[] splittedPath = propertyPath.split("\\.");
	try {
	    Object propValues = EntityUtils.getPropertyValues(istanza, propertyPath);
	    if(propValues instanceof Collection){
		retProp = new CartMappingIndexedProperty(propertyPath, (Collection<Object>)propValues);
	    }
	    //retProp = getIndexedProperyRecursive(istanza, splittedPath, 0);
	} catch (Exception e) {
	    String msg = MessageFormat.format("Errore durante la lettura del valore per la proprietà {0}: {1}", new Object[] { propertyPath, e });
	    log.error("getProprietaIndicizzataIstanza - {}", msg);
	    throw new Exception(msg, e);
	}
	return retProp;
    }

    private static CartMappingIndexedProperty getIndexedProperyRecursive(Object readFrom, String[] propertyPath, int pathIndex) throws Exception {

	CartMappingIndexedProperty retProp = null;
	/*
	PropertyUtils.getPropertyType(bean, name)
	if(readFrom instanceof Collection){
	    String[] subPath = Arrays.copyOf(propertyPath, pathIndex +1);
	    retProp = new CartMappingIndexedProperty(StringUtils.join(subPath, "."), (Collection<Object>)readFrom);
	}
	*/
	Class propertyType = null;
	for (; pathIndex < propertyPath.length; pathIndex++) {
	    String[] subPath = Arrays.copyOf(propertyPath, pathIndex + 1);
	    String subPathStr = StringUtils.join(subPath, '.');
	    propertyType = PropertyUtils.getPropertyType(readFrom, subPathStr);
	    Object value = PropertyUtils.getNestedProperty(readFrom, subPathStr);
	    if (Collection.class.isAssignableFrom(propertyType)) {
		if (value != null) {
		    Collection<Object> collectionOfValues = (Collection<Object>) value;
		    CartMappingIndexedProperty tempProp = null;
		    if (!collectionOfValues.isEmpty()) {
			Object innerVal = collectionOfValues.iterator().next();
			String[] nextPropertyPath = Arrays.copyOfRange(propertyPath, pathIndex + 1, propertyPath.length);
			tempProp = getIndexedProperyRecursive(innerVal, nextPropertyPath, 0);
		    }
		    if (tempProp == null) {
			retProp = new CartMappingIndexedProperty(subPathStr, collectionOfValues);
		    } else {
			//TODO 
			retProp = tempProp;
		    }
		} else {
		    log.warn("getIndexedProperyRecursive - proprietà {} di tipo Collection nulla in un oggetto di dominio.", propertyPath);
		    retProp = new CartMappingIndexedProperty(subPathStr, null);
		}
		break;
	    } else {
		if (value == null) {
		    break;
		}
	    }
	}
	return retProp;
    }

    public static String valueAsString(Object value) {

	String strVal = "";
	if (value != null) {
	    if (value instanceof Date) {
		strVal = Utilities.formatDate((Date) value, false);
	    } else {
		//TODO verificare se sono necessarie altre formattazioni specifiche per tipi di dato 
		strVal = value.toString();
	    }
	}
	return strVal;
    }

    public static boolean evaluateCondition(Object entity, String propertyPath, OperatorType operatore, String compareValue) throws Exception {

	boolean retVal = true;
	if (StringUtils.isNotBlank(propertyPath)) {
	    Object val = EntityUtils.getPropertyValues(entity, propertyPath);
	    switch (operatore) {
	    case EXISTS:
		retVal = exists(val, compareValue);
		break;
	    case NOT_EXISTS:
		retVal = !exists(val, compareValue);
		break;
	    case EQUALS:
		retVal = isEqual(val, compareValue);
		break;
	    case NOT_EQUALS:
		retVal = !isEqual(val, compareValue);
		break;
	    case MATCHES:
		retVal = matches(val, compareValue);
		break;
	    case NOT_MATCHES:
		retVal = !matches(val, compareValue);
		break;
	    default:
		throw new Exception("Operatore di confronto " + operatore.name() + " non supportato.");
	    }
	}
	//se non è specificato il propertyPath la condizione viene sempre considerata true qualunque valore abbiano operatore e compareValue
	return retVal;
    }

    public static boolean exists(Object value, String existsValue) throws Dyn2RegoleSyntaxError {

	boolean retVal = value != null;
	if (retVal) {
	    EspressioneBooleana boolexpr = new OperazioneConfronto(value, OperatoreConfrontoEnum.IS_NULL, existsValue);
	    boolexpr = new OperazioneLogica(boolexpr, OperatoreLogicoEnum.NOT, null);
	    if (value instanceof Collection && existsValue != null) {
		//se specificato un valore di confronto si verifica che nella collezione esista tale valore
		boolexpr = new OperazioneConfronto(value, OperatoreConfrontoEnum.EQ, existsValue);
	    }
	    retVal = BooleanUtils.isTrue(boolexpr.valutaBoolean());
	}
	return retVal;
    }

    public static boolean isEqual(Object value, String equalTo) throws Dyn2RegoleSyntaxError {

	EspressioneBooleana boolExpr = new OperazioneConfronto(value, OperatoreConfrontoEnum.EQ, equalTo);
	return BooleanUtils.isTrue(boolExpr.valutaBoolean());
    }

    public static boolean matches(Object value, String matchesTo) throws Dyn2RegoleSyntaxError {

	EspressioneBooleana boolExpr = new OperazioneConfronto(value, OperatoreConfrontoEnum.MATCHES, matchesTo);
	return BooleanUtils.isTrue(boolExpr.valutaBoolean());
    }

    public static String processaValoreVBG(String vbgValue, ValueProcessing processing) {

	String processedString = vbgValue;
	if (processing != null) {
	    List<ValueReplace> processingSteps = processing.getProcessingSteps();
	    for (ValueReplace step : processingSteps) {
		processedString = processedString.replaceAll(step.getReplaceMatch(), step.getReplaceWith());
	    }
	}
	return processedString;
    }
}
