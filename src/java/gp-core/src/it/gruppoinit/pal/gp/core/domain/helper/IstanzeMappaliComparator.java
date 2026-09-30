package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class IstanzeMappaliComparator implements Comparator<Istanzemappali>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2962061655670872138L;

    @Override
    public int compare(Istanzemappali o1, Istanzemappali o2) {

	// Controlla che gli oggetti non siano vuoti
	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	// lista dei campi per cui si vuole ordinare 
	String[] arrayField = { "catasto.descrizione", "sezione", "foglio", "particella", "sub", "unitaimmob" };
	// Confronto gli oggetti campo a campo 
	for (int i = 0; i < arrayField.length; i++) {
	    String object1 = null;
	    String object2 = null;
	    String[] field = arrayField[i].split("\\.");
	    try {
		// Recupero le stringhe da confrontare
		//1- Caso in cui la profondità di ricerca della stringa è maggiore di uno (la stringa da confrontare 
		//si trova all'interno di un altro oggetto) 
		if (field.length > 1) {
		    Object objTemp1 = null;
		    Object objTemp2 = null;
		    for (int j = 0; j < field.length - 1; j++) {
			Method get1 = o1.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
			Method get2 = o2.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
			objTemp1 = get1.invoke(o1, new Object[0]);
			objTemp2 = get2.invoke(o2, new Object[0]);
			// Se uno dei due è null fermo il ciclo
			if (objTemp1 == null || objTemp2 == null) {
			    break;
			}
		    }
		    // Se uno dei due è null faccio il confronto senza andare avanti nella ricerca del campo da confrontare
		    if (objTemp1 == null || objTemp2 == null) {
			if (objTemp1 != null && objTemp2 == null) {
			    return -1;
			}
			if (objTemp1 == null && objTemp2 != null) {
			    return 1;
			}
		    } else {
			Method get1 = objTemp1.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
			Method get2 = objTemp2.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
			object1 = (String) get1.invoke(objTemp1, new Object[0]);
			object2 = (String) get2.invoke(objTemp2, new Object[0]);
		    }
		    //2- Caso in cui la profondità di ricerca della stringa è uno (la stringa da confrontare 
		    //si trova all'interno dell' oggetto stesso) 
		} else {
		    Method get1 = o1.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
		    Method get2 = o2.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
		    object1 = (String) get1.invoke(o1, new Object[0]);
		    object2 = (String) get2.invoke(o2, new Object[0]);
		}
		if (object1 == null && object2 == null) {
		    // return 0;
		    object1 = "";
		    object2 = "";
		}
		if (object1 != null && object2 == null) {
		    return -1;
		}
		if (object1 == null && object2 != null) {
		    return 1;
		}
	    } catch (SecurityException e) {
		// log.debug("SecurityException: " + e);
		e.printStackTrace();
	    } catch (NoSuchMethodException e) {
		// log.debug("NoSuchMethodException " + e);
	    } catch (IllegalArgumentException e) {
		// log.debug("IllegalArgumentException " + e);
	    } catch (IllegalAccessException e) {
		// log.debug("IllegalAccessException " + e);
	    } catch (InvocationTargetException e) {
		//  log.debug("InvocationTargetException " + e);
	    }
	    // finche il confornto tra due campi non è deiverso da zero vado a confrontare il campo successivo.
	    //ES. Al primo controllo vedo se i due oggetti che confronto hanno "catasto.descrizione" uguale:
	    //    1- Si: allora passo al campo successivo "sezione" e rifaccio in controllo.Vado avanti fino a quando non trovo due 
	    //           campi con stringhe differenti o sono finit i campi da comparare fino a che non sono diversi
	    //	  2- No: ritorno l'intero che restituisce il metodo e passo all'oggetto successivo.	
	    if (comparatorStringhe(object1, object2) != 0) {
		return comparatorStringhe(object1, object2);
	    }
	}
	return 0;
    }

    // Metodo che compara due stringhe, viene aggiunto un left padding di Z per avere stringhe di lunghezza uguali
    private Integer comparatorStringhe(String a, String b) {

	a = StringUtils.leftPad(a, 15, 'Z');
	b = StringUtils.leftPad(b, 15, 'Z');
	return a.compareTo(b);
    }
}
