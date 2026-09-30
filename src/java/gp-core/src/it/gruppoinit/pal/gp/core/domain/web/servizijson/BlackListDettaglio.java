package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class BlackListDettaglio {

    private List<BlackListOggettoDettaglio> dettaglioBlackList;

    public List<BlackListOggettoDettaglio> getDettaglioBlackList() {

	return dettaglioBlackList;
    }

    public void setDettaglioBlackList(List<BlackListOggettoDettaglio> dettaglioBlackList) {

	this.dettaglioBlackList = dettaglioBlackList;
    }
}
