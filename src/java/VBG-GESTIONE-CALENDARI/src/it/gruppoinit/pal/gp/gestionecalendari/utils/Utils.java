package it.gruppoinit.pal.gp.gestionecalendari.utils;

import org.apache.commons.lang.StringUtils;

public class Utils {

    public static String formatOrganizzatore(String nominativo, String nome, String cf, String piva) {

	StringBuffer desc = new StringBuffer();
	desc.append(nominativo);
	if (StringUtils.isNotBlank(nome)) {
	    desc.append(" ");
	    desc.append(nome);
	}
	if (StringUtils.isNotBlank(cf)) {
	    desc.append(" cf.");
	    desc.append(cf);
	}
	if (StringUtils.isNotBlank(piva)) {
	    desc.append(" pIva.");
	    desc.append(piva);
	}
	return desc.toString().trim();
    }
}
