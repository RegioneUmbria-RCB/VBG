package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;

public class CommissioniediliziaRFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Date.class, "movimento.istanza.data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	filterMatcherMap.put(new MatcherKey(Date.class, "movimento.istanza.dataprotocollo"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	filterMatcherMap.put(new MatcherKey(Date.class, "movimento.data"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	return filterMatcherMap;
    }
}
