package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;

public class DataAlbopubblicazioniFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Date.class, "dataPubblicazione"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	filterMatcherMap.put(new MatcherKey(Date.class, "dataCreazione"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	filterMatcherMap.put(new MatcherKey(Date.class, "validaAl"), new DateFilterMatcher(WebConstants.DATE_FORMAT_PATTERN));
	return filterMatcherMap;
    }
}
