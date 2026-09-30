package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class PagamentiMercatiHelper {

    private List<PagamentiMercatiDaEffettuareHelper> pagamentiEffettuati = new ArrayList<PagamentiMercatiDaEffettuareHelper>();
    private List<PagamentiMercatiDaEffettuareHelper> pagamentiDaEffettuare = new ArrayList<PagamentiMercatiDaEffettuareHelper>();

    public List<PagamentiMercatiDaEffettuareHelper> getPagamentiEffettuati() {

	return pagamentiEffettuati;
    }

    public void setPagamentiEffettuati(List<PagamentiMercatiDaEffettuareHelper> pagamentiEffettuati) {

	this.pagamentiEffettuati = pagamentiEffettuati;
    }

    public List<PagamentiMercatiDaEffettuareHelper> getPagamentiDaEffettuare() {

	return pagamentiDaEffettuare;
    }

    public void setPagamentiDaEffettuare(List<PagamentiMercatiDaEffettuareHelper> pagamentiDaEffettuare) {

	this.pagamentiDaEffettuare = pagamentiDaEffettuare;
    }
}
