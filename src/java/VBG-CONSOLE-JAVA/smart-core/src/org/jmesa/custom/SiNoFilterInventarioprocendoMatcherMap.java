package org.jmesa.custom;

import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;


public class SiNoFilterInventarioprocendoMatcherMap implements FilterMatcherMap {
    

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagPubblica"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagNecessario"), new SiNoFilterMatcher());
	return filterMatcherMap;
    }
}
