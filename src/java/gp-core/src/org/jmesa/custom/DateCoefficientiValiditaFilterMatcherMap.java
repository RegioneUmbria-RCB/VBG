package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;

public class DateCoefficientiValiditaFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Date.class, "datainiziovalidita"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	return filterMatcherMap;
    }
}
