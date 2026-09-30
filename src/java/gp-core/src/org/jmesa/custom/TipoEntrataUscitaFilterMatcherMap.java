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
 * @author francescop
 * 
 */
public class TipoEntrataUscitaFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(String.class, "tipo"), new TipoEntrataUscitaFilterMatcher());
	return filterMatcherMap;
    }
}
