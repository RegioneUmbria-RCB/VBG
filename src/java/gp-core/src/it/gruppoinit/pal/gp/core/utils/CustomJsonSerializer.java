/**
 * 
 */
package it.gruppoinit.pal.gp.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.sojo.interchange.SerializerException;
import net.sf.sojo.interchange.json.JsonParser;

/**
 * @author Franco.Leone
 *
 */
public class CustomJsonSerializer {

    private static final Logger log = LoggerFactory.getLogger(CustomJsonSerializer.class);

    public static enum JsonFormat {
	CAMEL_CASE, SNAKE_CASE, CAPITAL_CAMEL_CASE, AS_IT_IS
    }

    private JsonFormat jsonFormat = JsonFormat.CAMEL_CASE;
    private boolean dropEmptyValues = true;
    private boolean useSuperclassFields = false;
    private List<String> doNotRenameTheseProperties = new ArrayList<String>();
    private Map<String, Class<?>> javaCollectionTypes = new HashMap<String, Class<?>>();

    public JsonFormat getJsonFormat() {

	return jsonFormat;
    }

    public void setJsonFormat(JsonFormat jsonFormat) {

	this.jsonFormat = jsonFormat;
    }

    public boolean isDropEmptyValues() {

	return dropEmptyValues;
    }

    public void setDropEmptyValues(boolean dropEmptyValues) {

	this.dropEmptyValues = dropEmptyValues;
    }

    public boolean isUseSuperclassFields() {

	return useSuperclassFields;
    }

    public void setUseSuperclassFields(boolean useSuperclassFields) {

	this.useSuperclassFields = useSuperclassFields;
    }

    public List<String> getDoNotRenameTheseProperties() {

	return doNotRenameTheseProperties == null ? new ArrayList<String>() : doNotRenameTheseProperties;
    }

    public void setDoNotRenameTheseProperties(List<String> doNotRenameTheseProperties) {

	this.doNotRenameTheseProperties = doNotRenameTheseProperties;
    }

    public void registerCollectionJavaType(String propName, Class javaType) {

	this.javaCollectionTypes.put(propName, javaType);
    }

    public Class getRegisteredJavaTypeForCollectionProperty(String propertyPath) {

	return this.javaCollectionTypes.get(propertyPath);
    }

    public void clearRegisteredCollectionJavaTypes() {

	this.javaCollectionTypes.clear();
    }

    public Object deserialize(String json, Class<?> javaType) {

	Object retObj = null;
	if (StringUtils.isNotBlank(json)) {
	    JsonParser parser = new JsonParser();
	    //Map<String, Object> dataMap = (Map<String, Object>) parser.parse(json);
	    Object parsedJson = parser.parse(json);
	    String path = "";
	    if (Collection.class.isInstance(parsedJson)) {
		//faccio in modo che gli elementi della lista che si trova nel root path dell'albero dei dati json siano trasformati in oggetti di classe javaType
		path = "/";
		this.javaCollectionTypes.put(path, javaType);
		//la lista nella root deve essere considerata come java.util.List
		javaType = List.class;
	    }
	    retObj = this.convertJsonDataToJavaType(parsedJson, javaType, path);
	}
	return retObj;
    }

    public String serialize(Object o) {

	//valori null
	if (o == null) {
	    return "null";
	}
	//liste e array
	if (Collection.class.isInstance(o)) {
	    return serializeCollection((Iterable<Object>) o);
	}
	//valori scalari secchi
	if (this.isScalarValue(o.getClass())) {
	    StringBuilder json = new StringBuilder();
	    boolean asString = isSerializableAsString(o.getClass());
	    if (asString) {
		json.append("\"");
		json.append(escapeJsonString(o.toString()));
	    } else {
		json.append(o);
	    }
	    if (asString) {
		json.append("\"");
	    }
	    return json.toString();
	}
	//oggetti complessi
	else {
	    StringBuilder json = new StringBuilder();
	    json.append("{");
	    Set<Field> fields = new HashSet<Field>();
	    Class<?> javaType = o.getClass();
	    Field[] fieldsArr = javaType.getDeclaredFields();
	    Collections.addAll(fields, fieldsArr);
	    if (this.useSuperclassFields) {
		javaType = javaType.getSuperclass();
		while (javaType != null && !javaType.equals(Object.class)) {
		    Collections.addAll(fields, javaType.getDeclaredFields());
		    javaType = javaType.getSuperclass();
		}
	    }
	    boolean removeColon = false;
	    Iterator<Field> iterFields = fields.iterator();
	    while (iterFields.hasNext()) {
		Field field = iterFields.next();
		String fieldName = field.getName();
		Object val;
		try {
		    if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
			PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(o, fieldName);
			if (pd != null && pd.getReadMethod() != null && Modifier.isPublic(pd.getReadMethod().getModifiers())) {
			    val = pd.getReadMethod().invoke(o);
			    if (dropEmptyValues) {
				if (val == null || (val + "").isEmpty()) {
				    continue;
				}
			    }
			    if (!getDoNotRenameTheseProperties().contains(fieldName)) {
				fieldName = this.javaToJsonFieldName(fieldName);
			    }
			    json.append("\"").append(fieldName).append("\":");
			    json.append(serialize(val));
			    json.append(",");
			    removeColon = true;
			}
		    }
		} catch (Exception e) {
		    String msg = "errore nella lettura del campo " + field.getName() + " dell'oggetto java di classe " + o.getClass().getSimpleName()
			    + ". Impossibile completare la serializzazione json";
		    log.error("serialize - " + msg);
		    throw new SerializerException(msg, e);
		}
	    }
	    if (removeColon) {
		json.deleteCharAt(json.length() - 1);
	    }
	    json.append("}");
	    return json.toString();
	}
    }

    public String serializeCollection(Iterable<?> list) {

	StringBuilder json = new StringBuilder("[");
	Iterator<?> it = list.iterator();
	while (it.hasNext()) {
	    Object value = it.next();
	    json.append(serialize(value));
	    if (it.hasNext()) {
		json.append(",");
	    }
	}
	json.append("]");
	return json.toString();
    }

    private Object populateJavaObject(Class<?> javaClass, Map<String, Object> jsonData, String parentPropertyPath) {

	Object retObj = null;
	if (jsonData != null) {
	    String nowAccessing = null;
	    try {
		nowAccessing = "costruttore vuoto";
		retObj = javaClass.newInstance();
	    } catch (Exception e) {
		String msg = "errore nella creazione di una istanza di " + javaClass.getName()
			+ ". Probabilmente manca il costruttore vuoto o non é dichiarato public";
		log.error("populateJavaObject - " + msg);
		throw new SerializerException(msg, e);
	    }
	    // Map<String, Object> beanProps = pub.describe(retObj);
	    Iterator<String> keys = jsonData.keySet().iterator();
	    PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(javaClass);
	    while (keys.hasNext()) {
		String key = keys.next();
		Object jsonVal = jsonData.get(key);
		nowAccessing = this.jsonToJavaFieldName(key);
		for (PropertyDescriptor pd : pds) {
		    if (pd.getName().equalsIgnoreCase(nowAccessing)) {
			setJsonValueInJavaBeanPropery(jsonVal, retObj, pd, parentPropertyPath);
			break;
		    }
		}
		if (log.isWarnEnabled()) {
		    log.warn("populateJavaObject - nessuna proprietà di nome " + nowAccessing + " è presente nel bean di classe "
			    + javaClass.getSimpleName());
		}
	    }
	}
	return retObj;
    }

    private void setJsonValueInJavaBeanPropery(Object jsonValue, Object javaBean, PropertyDescriptor property, String parentPropertyPath) {

	if (jsonValue != null) {
	    //se il path passaato è nullo vuoto o == "/" rappresenta la root property e viene inizializzata a stringa vuota
	    if (StringUtils.isBlank(parentPropertyPath) || parentPropertyPath.equals("/")) {
		parentPropertyPath = "";
	    } else {
		parentPropertyPath += ".";
	    }
	    parentPropertyPath += property.getName();
	    // se si tratta di impostare un campo di tipo oggetto complesso viene parsato dal sojo parser come una HashMap<String,Object>
	    //in tal caso viene invocato ricorsivamente populateJavaObject per costruire il valore da impostare nel campo
	    if (Map.class.isAssignableFrom(jsonValue.getClass())) {
		Object complexVal = populateJavaObject(property.getClass(), (Map<String, Object>) jsonValue, parentPropertyPath);
	    }
	    // verifica della incompatibilità dei tipi di dato in ingresso rispetto ai campi di destinazione per possibili conversioni
	    else if (!this.checkPropertyIsAssignable(property.getPropertyType(), jsonValue.getClass())) {
		jsonValue = this.convertJsonDataToJavaType(jsonValue, property.getPropertyType(), parentPropertyPath);
	    }
	    // se è possibile invoco il metodo setter altrimenti genero eccezione
	    if (this.checkPropertyIsAssignable(property.getPropertyType(), jsonValue.getClass())) {
		/*
		 * if (!property.getWriteMethod().isAccessible()) { if (log.isWarnEnabled()) {
		 * log.warn("setJsonValueInJavaBeanPropery - impossibile impostare il valore " + jsonValue +
		 * " nel campo " + property.getName() + " perché il metodo " + property.getWriteMethod().getName() +
		 * " non è accessibile"); } } else {
		 */
		try {
		    property.getWriteMethod().invoke(javaBean, jsonValue);
		} catch (Exception e) {
		    String msg = "impossibile impostare il valore " + jsonValue + " nel campo " + property.getName()
			    + " eccezione nell'invocazione del metodo " + property.getWriteMethod().getName() + " del bean di classe "
			    + javaBean.getClass().getSimpleName();
		    log.error("setJsonValueInJavaBeanPropery - " + msg);
		    throw new SerializerException(msg, e);
		}
		//}
	    } else {
		String msg = "impossibile impostare il valore " + jsonValue + " di tipo " + jsonValue.getClass().getSimpleName() + " nel campo "
			+ property.getName() + " di tipo " + property.getPropertyType().getSimpleName() + " del bean di classe "
			+ javaBean.getClass().getSimpleName();
		log.error("setJsonValueInJavaBeanPropery - " + msg);
		throw new SerializerException(msg);
	    }
	}
    }

    private boolean checkPropertyIsAssignable(Class<?> propertyType, Class<?> valueType) {

	if (valueType.equals(Boolean.class) && propertyType.equals(boolean.class)) {
	    return true;
	} else if (valueType.equals(Long.class) && propertyType.equals(long.class)) {
	    return true;
	} else if (valueType.equals(Double.class) && propertyType.equals(double.class)) {
	    return true;
	}
	return propertyType.isAssignableFrom(valueType);
    }

    private Object convertJsonDataToJavaType(Object jsonValue, Class<?> javaType, String propertyPath) {

	Class dataType = jsonValue.getClass();
	// se è un dato numerico tento la conversione fra tipi numerici teoricamente compatibili
	if (Number.class.isAssignableFrom(dataType)) {
	    // try {
	    // il sojo parser mi restituisce i decimali come Double
	    if (dataType.equals(Double.class)) {
		// cerco di valorizzare campi Float
		if (javaType.equals(Float.class)) {
		    jsonValue = NumberUtils.createFloat(jsonValue.toString());
		}
		// o BigDecimal
		else if (javaType.equals(BigDecimal.class)) {
		    jsonValue = NumberUtils.createBigDecimal(jsonValue.toString());
		}
		// o float
		else if (javaType.equals(float.class)) {
		    jsonValue = ((Double) jsonValue).floatValue();
		}
	    }
	    // il sojo parser mi restituisce gli interi come Long
	    else if (dataType.equals(Long.class)) {
		// cerco di valorizzare i campi Integer
		if (javaType.equals(Integer.class)) {
		    jsonValue = NumberUtils.createInteger(jsonValue.toString());
		}
		// Short
		else if (javaType.equals(Short.class)) {
		    jsonValue = new Short(jsonValue.toString());
		}
		// BigInteger
		else if (javaType.equals(BigInteger.class)) {
		    jsonValue = NumberUtils.createBigInteger(jsonValue.toString());
		}
		// o int
		else if (javaType.equals(int.class)) {
		    jsonValue = ((Long) jsonValue).intValue();
		}
		// o short
		else if (javaType.equals(short.class)) {
		    jsonValue = ((Long) jsonValue).shortValue();
		}
	    }
	    /*
	     * } catch (NumberFormatException nfe) {
	     * log.error("setJsonValueInJavaBeanPropery - impossibile impostare il valore " + jsonValue + " nel campo "
	     * + property.getName() + " perché il valore è troppo grosso per il tipo " + javaType.getSimpleName()); }
	     */
	}
	//verifico se è un array di valori che viene parsato come arraylist
	else if (Collection.class.isAssignableFrom(dataType)) {
	    if (!Iterable.class.isAssignableFrom(javaType)) {
		javaType = ArrayList.class;
	    }
	    //se si tratta di impostare un campo di tipo collection 
	    if (Collection.class.isAssignableFrom(javaType)) {
		Collection<Object> coll = null;
		//property.
		if (List.class.isAssignableFrom(javaType)) {
		    coll = new ArrayList<Object>();
		} else if (Set.class.isAssignableFrom(javaType)) {
		    coll = new HashSet<Object>();
		}
		if (coll != null) {
		    //converto i singoli valori al tipo java di destinazione
		    Collection<Object> arrValues = (Collection) jsonValue;
		    Class registeredCollectionType = this.getRegisteredJavaTypeForCollectionProperty(propertyPath);
		    for (Object arrVal : arrValues) {
			if (registeredCollectionType != null) {
			    arrVal = this.convertJsonDataToJavaType(arrVal, registeredCollectionType, propertyPath);
			}
			coll.add(arrVal);
		    }
		    jsonValue = coll;
		} else {
		    throw new UnsupportedOperationException(
			    "convertJsonDataToJavaType - impossibile impostare dati json di tipo array nel campo di tipo " + javaType.getSimpleName()
				    + ". Tipo di collection java non supportata dal serializer.");
		}
	    }
	    //se si tratta di impostare un array di oggetti
	    else if (javaType.isArray()) {
		//TODO conversione di dati fra array di tipo diverso ma compatibile tenuto conto che dai dati json possono arrivare String[] o HashMap<String,Object>
		throw new UnsupportedOperationException("convertJsonDataToJavaType - impossibile impostare dati json di tipo array nel campo di tipo "
			+ javaType.getSimpleName() + ". Tipo di classe java non supportata dal serializer.");
	    }
	}
	//verifico se è un oggetto complesso che viene parsato come HashMap e deve essere trasformato in un istanza di javaType
	else if (Map.class.isAssignableFrom(dataType)) {
	    jsonValue = this.populateJavaObject(javaType, (Map<String, Object>) jsonValue, propertyPath);
	}
	return jsonValue;
    }

    private boolean isScalarValue(Class<?> valueType) {

	boolean isScalar = valueType.isPrimitive();
	if (Number.class.isAssignableFrom(valueType)) {
	    isScalar = true;
	} else if (Boolean.class.isAssignableFrom(valueType)) {
	    isScalar = true;
	} else if (this.isSerializableAsString(valueType)) {
	    isScalar = true;
	}
	//TODO campi data
	return isScalar;
    }

    private boolean isSerializableAsString(Class<?> valueType) {

	if (String.class.isAssignableFrom(valueType)) {
	    return true;
	}
	return false;
    }

    private String javaToJsonFieldName(String javaName) {

	switch (this.getJsonFormat()) {
	case CAMEL_CASE:
	    return CustomJsonSerializer.toCamelCase(javaName);
	case SNAKE_CASE:
	    return CustomJsonSerializer.toSnakeCase(javaName);
	case CAPITAL_CAMEL_CASE:
	    return CustomJsonSerializer.toCapitalCamelCase(javaName);
	default:
	    return javaName;
	}
    }

    private String jsonToJavaFieldName(String jsonName) {

	switch (this.getJsonFormat()) {
	case AS_IT_IS:
	    return jsonName;
	default:
	    return CustomJsonSerializer.toCamelCase(jsonName);
	}
    }

    public static String toSnakeCase(String input) {

	// TODO non supporta variabili composte_da_piu_di_due_parole
	String regex = "(\\p{Lower})(\\p{Upper})";
	String replacement = "$1_$2";
	return input.replaceAll(regex, replacement).toLowerCase();
    }

    public static String toCamelCase(String input) {

	String[] words = StringUtils.split(input, '_');
	StringBuilder sbJson = new StringBuilder();
	for (int i = 0; i < words.length; i++) {
	    String word = i == 0 ? StringUtils.uncapitalize(words[i]) : StringUtils.capitalize(words[i]);
	    sbJson.append(word);
	}
	return sbJson.toString();
    }

    public static String toCapitalCamelCase(String input) {

	return StringUtils.capitalize(toCamelCase(input));
    }

    public static String escapeJsonString(String input) {

	String output = input.replace("\\", "\\\\");
	output = output.replace("\"", "\\\"");
	return output;
    }
}
