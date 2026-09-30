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
public class SiNoFilterMatcherMap implements FilterMatcherMap {

    @Override
    public Map<MatcherKey, FilterMatcher> getFilterMatchers() {

	Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>();
	filterMatcherMap.put(new MatcherKey(Boolean.class, "richiedePosteggio"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "richiedeEndo"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "abilitato"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagDisabilitato"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagInterruzione"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagRichiestaintegrazione"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagStc"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "nonPrevedeIncassi"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "soloImportiNegativi"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "flagPagato"), new SiNoFilterMatcher());
	filterMatcherMap.put(new MatcherKey(Boolean.class, "attiva"), new SiNoFilterMatcher());
	return filterMatcherMap;
    }
}
