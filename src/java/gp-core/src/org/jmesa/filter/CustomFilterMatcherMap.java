package org.jmesa.filter;

import java.util.List;
import java.util.Map;

import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;

public class CustomFilterMatcherMap implements FilterMatcherMap {

    private List<Map<MatcherKey, FilterMatcher>> listDiMapFilterMatcher;

    public CustomFilterMatcherMap(List<Map<MatcherKey, FilterMatcher>> listDiMapFilterMatcher) {

	super();
	this.listDiMapFilterMatcher = listDiMapFilterMatcher;
    }

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	return null;
    }
}
