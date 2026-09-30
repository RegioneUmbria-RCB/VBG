package org.jmesa.custom;

import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;


public class DocumentiistanzaFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Boolean.class, "necessario"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "presente"), new SiNoFilterMatcher());
	return filterMatcherMap;
    }
}
