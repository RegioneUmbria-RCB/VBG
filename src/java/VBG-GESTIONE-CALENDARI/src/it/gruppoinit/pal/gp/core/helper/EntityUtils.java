package it.gruppoinit.pal.gp.core.helper;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * classe con metodi di utilità per gli oggetti di dominio
 * 
 * @author fabrizioc
 * 
 */
public class EntityUtils {

    private static final Logger log = LoggerFactory.getLogger(EntityUtils.class);

    /**
     * Metodo per il recupero di un oggetto interno all'oggetto passato come argomento. <br />
     * La profondità dell'oggetto interno è specificata nel parametro name concatenando con il punto '.' i vari path da
     * percorrere. Se un path intermedio è nullo il metodo ritorna null.<br />
     * Es.:
     * 
     * <pre>
     * 	if(EntityUtils.getNestedProperty(istanza, "id.codice")!=null){
     * 	  <...AZIONI DA ESEGUIRE..>	
     * 	}
     * </pre>
     * 
     * @param bean
     *            L'oggetto per il quale ricercare le proprietà nested. Poterbbe essere nullo.
     * @param name
     *            La stringa che rappresenta la proprietà interna da controllare. Non può essere nulla o vuota.
     * @throws RuntimeException
     *             Nel caso che il parametro name sia nullo o stringa vuota oppure non sia possibile accedere alla
     *             proprietà del bean.
     * @return La proprietà dell'oggetto (bean) che si intende controllare se impostata, altrimenti null
     */
    public static Object getNestedProperty(Object bean, String name) {

	Object obj = null;
	if (StringUtils.isBlank(name)) {
	    log.error("getNestedProperty: null parameter name is not allowed");
	    throw new InvalidParameterException("getNestedProperty: null parameter name is not allowed");
	}
	if (bean != null) {
	    try {
		obj = PropertyUtils.getNestedProperty(bean, name);
		if (obj != null && obj instanceof String) {
		    if (StringUtils.isBlank((String) obj)) {
			obj = null;
		    }
		}
	    } catch (NestedNullException e) {
		log.debug("getNestedProperty of bean:{} and name:{} has a null nested path.", bean.toString(), name);
	    } catch (IllegalAccessException e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    } catch (InvocationTargetException e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    } catch (NoSuchMethodException e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    }
	}
	return obj;
    }

    /**
     * metodo per verificare se nel path specificato relativo all'oggetto radice c'è un null.
     * 
     * @see EntityUtils#getNestedProperty(Object, String)
     * 
     * @param bean
     *            oggetto radice
     * @param name
     *            path della property interna all'oggetto radice
     * @return
     */
    public static boolean isNestedPropertyBlank(Object bean, String name) {

	boolean isBlank = false;
	if (getNestedProperty(bean, name) == null) {
	    isBlank = true;
	}
	return isBlank;
    }

    /**
     * Metodo per la verifica dell' uguaglianza di due entity di hibernate.<br />
     * Due entity sono uguali se rappresentano la stessa riga della tabella che mappano.<br />
     * Il metodo ritorna true solo se le due chiavi primarie delle entity non sono nulle ed il metodo id1.equals(id2)
     * restituisce true.
     * 
     * @param id1
     *            oggetto che rappresenta la chiave primaria della entity di hibernate
     * @param id2
     *            oggetto che rappresenta la chiave primaria della entity di hibernate
     * @return
     */
    public static boolean equals(Object id1, Object id2) {

	boolean success = false;
	if (id1 != null && id2 != null) {
	    success = id1.equals(id2);
	}
	return success;
    }

    /**
     * Restituisce il valore della proprietà mappata in propertyPath recuperandolo dall'oggetto entity. Se la proprietà
     * che si vuole ottenere appartiene ad elenchi di altre entità che sono in relazione i-n con l'entità passata allora
     * vengono restituiti tutti gli N valori recuperati da ciascun'entità in relazione con quella principale. Per
     * esempio se entity è un istanza di classe Istanze e propertyPath vale "istanzemappalis.foglio" verrà restituita
     * una {@link List} che contiene i valori della proprietà "foglio" per ciascun oggetto {@link Istanzemappali}
     * collegato all'istanza passata come primo argomento
     * 
     * @param entity
     * @param propertyPath
     * @return
     * @throws Exception
     */
    public static Object getPropertyValues(Object entity, String propertyPath) throws Exception {

	Object retVal = null;
	String[] splittedPath = propertyPath.split("\\.");
	try {
	    retVal = getProperyRecursive(entity, splittedPath, 0, null);
	} catch (Exception e) {
	    String msg = MessageFormat.format("Errore durante la lettura del valore per la proprietà {0}: {1}", new Object[] { propertyPath, e });
	    log.error("getPropertyValues - {}", msg);
	    throw new Exception(msg, e);
	}
	return retVal;
    }

    @SuppressWarnings("unchecked")
    private static Object getProperyRecursive(Object readFrom, String[] propertyPath, int pathIndex, List<Object> valuesFound) throws Exception {

	Object retVal = null;
	if (!(readFrom instanceof Collection)) {
	    retVal = PropertyUtils.getSimpleProperty(readFrom, propertyPath[pathIndex]);
	    if (pathIndex == propertyPath.length - 1) {
		if (valuesFound != null) {
		    valuesFound.add(retVal);
		}
	    } else if (retVal != null) {
		pathIndex++;
		retVal = getProperyRecursive(retVal, propertyPath, pathIndex, valuesFound);
	    }
	} else {
	    Collection<Object> readFromCollection = (Collection<Object>) readFrom;
	    if (valuesFound == null) {
		valuesFound = new ArrayList<Object>();
	    }
	    for (Object object : readFromCollection) {
		getProperyRecursive(object, propertyPath, pathIndex, valuesFound);
	    }
	    retVal = valuesFound;
	}
	return retVal;
    }
}
