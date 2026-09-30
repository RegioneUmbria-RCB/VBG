package it.gruppoinit.stc.web.helper;

import it.gruppoinit.stc.domain.VwMessaggipratiche;
import it.gruppoinit.stc.service.VwMessaggipraticheService;

import java.util.Collection;

import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;

public class VwMessaggipratichePageItems extends BasePageItem<VwMessaggipratiche> implements PageItems {

    private VwMessaggipraticheService vwMessaggipraticheService;
    private VwMessaggipratiche vwMessaggipratiche;

    public VwMessaggipratichePageItems(VwMessaggipraticheService vwMessaggipraticheService) {

	super();
	this.vwMessaggipraticheService = vwMessaggipraticheService;
	this.vwMessaggipratiche = new VwMessaggipratiche();
    }

    @Override
    public Collection<VwMessaggipratiche> getItems(Limit limit) {

	this.vwMessaggipratiche = getFilterQuery(vwMessaggipratiche, limit);
	return vwMessaggipraticheService.findByFilter(vwMessaggipratiche, limit.getRowSelect().getRowStart(), limit.getRowSelect().getMaxRows());
    }

    @Override
    public int getTotalRows(Limit limit) {

	this.vwMessaggipratiche = getFilterQuery(vwMessaggipratiche, limit);
	return vwMessaggipraticheService.countByFilter(vwMessaggipratiche);
    }
}
