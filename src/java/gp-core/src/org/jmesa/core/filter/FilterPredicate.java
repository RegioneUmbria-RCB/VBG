/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.jmesa.core.filter;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the Jakarta Collections predicate pattern to filter out the table.
 * 
 * @since 2.0
 * @author Jeff Johnston
 * @author Francesco Palenga
 */
public final class FilterPredicate implements Predicate {

    private Logger logger = LoggerFactory.getLogger(FilterPredicate.class);
    private Map<Filter, FilterMatcher> filterMatchers;
    private FilterSet filterSet;

    public FilterPredicate(Map<Filter, FilterMatcher> filterMatchers, FilterSet filterSet) {

	this.filterMatchers = filterMatchers;
	this.filterSet = filterSet;
    }

    /**
     * Use the filter parameters to filter out the table.
     * 
     * Eliminato il controllo se il valore è null. é possibile filtrare i valori null sulle colonne
     */
    public boolean evaluate(Object item) {

	boolean result = false;
	boolean nestedPropertyIsNull = false;
	try {
	    for (Filter filter : filterSet.getFilters()) {
		nestedPropertyIsNull = false;
		String property = filter.getProperty();
		// se viene passata una proprietà nested esempio: "comune.comune" vedi stradario/list.jsp
		// e la proprietà comune è nulla dava un eccezione NestedNullExecption
		// queste linee di codice evitano il verificarsi di questa situazione
		// queste righe funzionano se la proprietà è che si controlla è di primo livello non per proprietà di
		// proprietà es: istanze.stradario.comune.comune
		// potevamo anche eseguire un catch sull'eventuale errore tornato ma abbiamo preferito questo per non
		// appesantire con i catch l'esecuzione se le righe fossero troppe (es. stradario)
		if (property.indexOf(".") > 0) {
		    String nestedProperty = property.substring(0, property.indexOf("."));
		    Object nestedValue = PropertyUtils.getProperty(item, nestedProperty);
		    nestedPropertyIsNull = (nestedValue == null) ? true : false;
		}
		Object value = null;
		if (!nestedPropertyIsNull) {
		    value = PropertyUtils.getProperty(item, property);
		}
		// eliminato il controllo se il value è null
		// é possibile filtrare anche quando il valore è null
		FilterMatcher match = filterMatchers.get(filter);
		// // Viene effettuato il decoder poichè arriva la stringa non codificata.
		// String encode = filter.getValue();
		// String decode = URLDecoder.decode(encode, "UTF-8");
		result = match.evaluate(value, filter.getValue());
		// short circuit if does not match
		if (result == false) {
		    return false;
		}
	    }
	} catch (Exception e) {
	    logger.error("Had problems evaluating the items.", e);
	}
	return result;
    }
}
