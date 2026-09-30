package it.gruppoinit.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class MappaturaMovimenti {

    public static enum DEFINIZIONE {
	MOV_AGIB, MOV_FINLAV, MOV_COMGEN, MOV_ILPDC, MOV_VARSOGCOL, MOV_LOTT, MOV_NOTPRE, MOV_RICHCCEA, MOV_RICGEN, MOV_TITEDI, MOV_DEFAULT
    }

    private static HashMap<String, Properties> pComune = new HashMap<String, Properties>();

    private static Properties getPropertyPerEnte(String aliasEnte) {

	if (pComune.get(aliasEnte) != null) {
	    return pComune.get(aliasEnte);
	}
	InputStream isProps = MappaturaMovimenti.class.getClassLoader().getResourceAsStream(aliasEnte + ".sieder.movimenti.properties");
	Properties p = new Properties();
	try {
	    p.load(isProps);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	pComune.put(aliasEnte, p);
	return p;
    }

    // MOV_AGIB=CESIEDAG,MOV_FINLAV=CESIEDFL,MOV_COMGEN=CESIEDCG,MOV_ILPDC=CESIEDIL,MOV_VARSOGCOL=CESIEDVS,MOV_LOTT=CESIEDLT,MOV_NOTPRE=CESIEDNP,MOV_RICHCCEA=CESIEDCE,MOV_RICGEN=CESIEDRG,MOV_TITEDI=CESIEDTE,MOV_DEFAULT=CESIEDCG
    public static String getTipomovimento(DEFINIZIONE def, String idcomune) {

	Properties p = getPropertyPerEnte(idcomune);
	String r = p.getProperty(def.name());
	if (StringUtils.isBlank(r)) {
	    r = defaultVal(def);
	}
	return r;
    }

    public static void reloadProperties() {

	pComune = new HashMap<String, Properties>();
    }

    private static String defaultVal(DEFINIZIONE def) {

	switch (def) {
	case MOV_AGIB:
	    return "CESIEDAG";
	case MOV_FINLAV:
	    return "CESIEDFL";
	case MOV_COMGEN:
	    return "CESIEDCG";
	case MOV_ILPDC:
	    return "CESIEDIL";
	case MOV_VARSOGCOL:
	    return "CESIEDVS";
	case MOV_LOTT:
	    return "CESIEDLT";
	case MOV_NOTPRE:
	    return "CESIEDNP";
	case MOV_RICHCCEA:
	    return "CESIEDCE";
	case MOV_RICGEN:
	    return "CESIEDRG";
	case MOV_TITEDI:
	    return "CESIEDTE";
	case MOV_DEFAULT:
	    return "CESIEDCG";
	default:
	    return "CESIEDCG";
	}
    }
}
