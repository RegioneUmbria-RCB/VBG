package it.gruppoinit.pal.gp.core.features.alfresco.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.eclipse.persistence.oxm.annotations.XmlVariableNode;

public class AlfrescoApiPropertiesAdapter extends XmlAdapter<AlfrescoApiPropertiesAdapter.AdaptedMap, Map<String, String>> {

    @Override
    public AdaptedMap marshal(Map<String, String> map) throws Exception {

	AdaptedMap adaptedMap = new AdaptedMap();
	for (Entry<String, String> entry : map.entrySet()) {
	    AdaptedEntry adaptedEntry = new AdaptedEntry();
	    adaptedEntry.key = entry.getKey();
	    adaptedEntry.value = entry.getValue();
	    adaptedMap.entries.add(adaptedEntry);
	}
	return adaptedMap;
    }

    @Override
    public Map<String, String> unmarshal(AdaptedMap adaptedMap) throws Exception {

	List<AdaptedEntry> adaptedEntries = adaptedMap.entries;
	Map<String, String> map = new HashMap<String, String>(adaptedEntries.size());
	for (AdaptedEntry adaptedEntry : adaptedEntries) {
	    map.put(adaptedEntry.key, adaptedEntry.value);
	}
	return map;
    }

    public static class AdaptedMap {

	@XmlVariableNode("key")
	List<AdaptedEntry> entries = new ArrayList<AdaptedEntry>();
    }

    public static class AdaptedEntry {

	@XmlTransient
	public String key;
	@XmlValue
	public String value;
    }
}