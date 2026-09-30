package it.gruppoinit.service.impl;

import it.gruppoinit.service.FilePropertiesService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FilePropertiesServiceImpl implements FilePropertiesService {

    private static final Logger log = LoggerFactory.getLogger(FilePropertiesServiceImpl.class);
    private static final String mappingEnti = "mapping_enti.properties";
    private static final String mappingSportelli = "mapping_sportello.properties";
    private static final String zone = "zone.properties";

    @Override
    public Properties loadMappingEnti() {

	Properties properties = new Properties();
	ClassLoader loader = FilePropertiesServiceImpl.class.getClassLoader();
	InputStream is = loader.getResourceAsStream(mappingEnti);
	try {
	    properties.load(is);
	} catch (IOException e) {
	    log.error("loadMappingEnti# Errore nel caricamento del file = {}, e={}", mappingEnti, e);
	    throw new RuntimeException(e);
	}
	return properties;
    }

    @Override
    public Properties loadMappingSportello() {

	Properties properties = new Properties();
	ClassLoader loader = FilePropertiesServiceImpl.class.getClassLoader();
	InputStream is = loader.getResourceAsStream(mappingSportelli);
	try {
	    properties.load(is);
	} catch (IOException e) {
	    log.error("loadMappingEnti# Errore nel caricamento del file = {}, e={}", mappingSportelli, e);
	    throw new RuntimeException(e);
	}
	return properties;
    }

    @Override
    public String findSportello(String key) {

	Properties properties = loadMappingSportello();
	String r = properties.getProperty(key);
	log.debug("findSportello# key = {}, value = {}", key, r);
	return r;
    }

    @Override
    public String findEnte(String key) {

	Properties properties = loadMappingEnti();
	String r = properties.getProperty(key.toUpperCase());
	log.debug("findEnte# key = {}, value = {}", key.toUpperCase(), r);
	return r;
    }

    @Override
    public Map<String, List<String>> loadZoneProcedimenti() {

	Map<String, List<String>> ris = new HashMap<String, List<String>>();
	Properties properties = new Properties();
	ClassLoader loader = FilePropertiesServiceImpl.class.getClassLoader();
	InputStream is = loader.getResourceAsStream(zone);
	try {
	    properties.load(is);
	    Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();
	    while (enums.hasMoreElements()) {
		String key = enums.nextElement();
		String value = properties.getProperty(key);
		String str[] = value.split(",");
		List<String> al = new ArrayList<String>();
		al = Arrays.asList(str);
		ris.put(key, al);
	    }
	} catch (IOException e) {
	    log.error("loadZoneProcedimenti# Errore nel caricamento del file = {}, e={}", zone, e);
	    throw new RuntimeException(e);
	}
	return ris;
    }
}
