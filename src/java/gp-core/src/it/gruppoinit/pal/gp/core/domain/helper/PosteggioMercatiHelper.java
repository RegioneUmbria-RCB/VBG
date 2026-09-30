package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

public class PosteggioMercatiHelper {

    private PosteggioInfoHelper posteggioInfoHelper;
    private List<IstanzeConcessioniMercatoHelper> istanzeConcessioniMercatoHelpers = new ArrayList<IstanzeConcessioniMercatoHelper>();

    public PosteggioInfoHelper getPosteggioInfoHelper() {

	return posteggioInfoHelper;
    }

    public void setPosteggioInfoHelper(PosteggioInfoHelper posteggioInfoHelper) {

	this.posteggioInfoHelper = posteggioInfoHelper;
    }

    public List<IstanzeConcessioniMercatoHelper> getIstanzeConcessioniMercatoHelpers() {

	return istanzeConcessioniMercatoHelpers;
    }

    public void setIstanzeConcessioniMercatoHelpers(List<IstanzeConcessioniMercatoHelper> istanzeConcessioniMercatoHelpers) {

	this.istanzeConcessioniMercatoHelpers = istanzeConcessioniMercatoHelpers;
    }
}
