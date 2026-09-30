/**
 * 
 */
package org.jmesa.custom;

import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;

/**
 * Specifica la mappa dei filtri da utilizzare in una specifica table facade. Viene specificato il filter matcher
 * custom, mentre gli altri Filter Matcher sono quelli di default di JMesa
 * 
 * @author Francesco Palenga
 */
public class AreedettagliFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Boolean.class, "paridispari"), new AreedettagliFilterMatcher());
	return filterMatcherMap;
    }
}
