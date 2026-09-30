package it.gruppoinit.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface FilePropertiesService {

    public Properties loadMappingEnti();

    public Properties loadMappingSportello();

    public String findSportello(String key);

    public String findEnte(String key);

    public Map<String, List<String>> loadZoneProcedimenti();
}
