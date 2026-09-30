package org.jmesa.custom;

import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
/**
 * E' utilizzato sia per la lista dei menufo che per la lista dei menuInfo
 * @author gianpaolot
 *
 */
public class MenufoFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagAttivo"), new SiNoFilterMatcher());
	return filterMatcherMap;
    }
}
