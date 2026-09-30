package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class FlashMessages {

    private static ThreadLocal<List<String>> infosHelper = new ThreadLocal<List<String>>();
    private static ThreadLocal<List<String>> warningsHelper = new ThreadLocal<List<String>>();

    public static List<String> getInfos() {

	List<String> infos = infosHelper.get();
	if (infos == null) {
	    infos = new ArrayList<String>();
	    setInfos(infos);
	}
	return infos;
    }

    public static void setInfos(List<String> infos) {

	infosHelper.set(infos);
    }

    public static void removeInfos() {

	infosHelper.remove();
    }

    public static List<String> getWarnings() {

	List<String> warns = warningsHelper.get();
	if (warns == null) {
	    warns = new ArrayList<String>();
	    setWarnings(warns);
	}
	return warns;
    }

    public static synchronized void setWarnings(List<String> warnings) {

	warningsHelper.set(warnings);
    }

    public static void removeWarnings() {

	warningsHelper.remove();
    }
}
