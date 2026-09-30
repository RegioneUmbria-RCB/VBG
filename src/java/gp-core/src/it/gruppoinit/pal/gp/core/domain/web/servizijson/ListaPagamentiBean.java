package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaPagamentiBean {

    private List<Long> pagamenti = null;

    public List<Long> getPagamenti() {

	if (this.pagamenti == null) {
	    this.pagamenti = new ArrayList<Long>();
	}
	return pagamenti;
    }

    public void setPagamenti(List<Long> pagamenti) {

	this.pagamenti = pagamenti;
    }

    public List<Integer> asIntegerArray() {

	List<Integer> ret = new ArrayList<Integer>();
	Set<Long> s = new HashSet<Long>(this.getPagamenti());
	for (Long l : s) {
	    ret.add(l.intValue());
	}
	return ret;
    }
}
