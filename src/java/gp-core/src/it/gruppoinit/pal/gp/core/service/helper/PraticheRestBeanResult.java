package it.gruppoinit.pal.gp.core.service.helper;

import java.util.List;

public class PraticheRestBeanResult {

    private List<PraticaRestBean> results;
    private Integer total; // totale degli elementi restituiti dal WS
    private Integer offset;
    private Integer limit;

    public List<PraticaRestBean> getResults() {

	return results;
    }

    public void setResults(List<PraticaRestBean> results) {

	this.results = results;
    }

    public Integer getTotal() {

	return total;
    }

    public void setTotal(Integer total) {

	this.total = total;
    }

    public Integer getOffset() {

	return offset;
    }

    public void setOffset(Integer offset) {

	this.offset = offset;
    }

    public Integer getLimit() {

	return limit;
    }

    public void setLimit(Integer limit) {

	this.limit = limit;
    }
}
