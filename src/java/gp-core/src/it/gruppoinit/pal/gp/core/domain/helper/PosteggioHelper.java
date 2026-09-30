package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

public class PosteggioHelper {

    private String codiceposteggio;
    private List<GiorniHelper> giornosHelper = new ArrayList<GiorniHelper>();
    private boolean isOccupantiPresneti;

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public List<GiorniHelper> getGiornosHelper() {

	return giornosHelper;
    }

    public void setGiornosHelper(List<GiorniHelper> giornosHelper) {

	this.giornosHelper = giornosHelper;
    }

    public boolean getIsOccupantiPresneti() {

	if (this.getGiornosHelper().isEmpty()) {
	    isOccupantiPresneti = false;
	} else {
	    isOccupantiPresneti = true;
	}
	return isOccupantiPresneti;
    }

    public void setIsOccupantiPresneti(boolean isOccupantiPresneti) {

	this.isOccupantiPresneti = isOccupantiPresneti;
    }
}
