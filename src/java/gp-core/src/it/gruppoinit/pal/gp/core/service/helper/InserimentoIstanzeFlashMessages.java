package it.gruppoinit.pal.gp.core.service.helper;

import java.util.List;

public class InserimentoIstanzeFlashMessages {

    private static ThreadLocal<List<String>> eventisHelper = new ThreadLocal<List<String>>();

    public static List<String> getEventis() {

	return eventisHelper.get();
    }

    public static synchronized void setEventis(List<String> warnings) {

	eventisHelper.set(warnings);
    }

    public static void removeWarnings() {

	eventisHelper.remove();
    }
}
