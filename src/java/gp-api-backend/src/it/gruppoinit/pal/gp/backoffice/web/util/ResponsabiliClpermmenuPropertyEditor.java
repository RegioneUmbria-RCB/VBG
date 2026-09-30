package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class ResponsabiliClpermmenuPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

	Set<Clpermmenu> set = new HashSet<Clpermmenu>();
	StringTokenizer st = new StringTokenizer(text, ",");
	while (st.hasMoreTokens()) {
	    String token = st.nextToken();
	    Clpermmenu clpermmenu = new Clpermmenu();
	    Clmenu clmenu = new Clmenu();
	    Software software = new Software();
	    if (token.indexOf("#") >= 1) {
		String[] subProperties = token.split("#");
		clmenu.setId(Integer.parseInt(subProperties[0]));
		clpermmenu.setMenu(clmenu);
		software.setCodice(subProperties[1]);
		clpermmenu.setSoftware(software);
	    }
	    set.add(clpermmenu);
	}
	if (set.size() > 0) {
	    setValue(set);
	} else {
	    setValue(null);
	}
    }
}
