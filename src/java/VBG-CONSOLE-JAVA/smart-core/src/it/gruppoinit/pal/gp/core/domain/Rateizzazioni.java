package it.gruppoinit.pal.gp.core.domain;

import java.util.ArrayList;
import java.util.List;

public class Rateizzazioni {

    private List<RangeRateizzazioni> rangerateizzazioniList = new ArrayList<RangeRateizzazioni>();

    public List<RangeRateizzazioni> getRangerateizzazioniList() {

	return rangerateizzazioniList;
    }

    public void setRangerateizzazioniList(List<RangeRateizzazioni> rangerateizzazioniList) {

	this.rangerateizzazioniList = rangerateizzazioniList;
    }
}
