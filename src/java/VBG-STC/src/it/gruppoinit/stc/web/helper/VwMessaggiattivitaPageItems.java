package it.gruppoinit.stc.web.helper;

import it.gruppoinit.stc.domain.VwMessaggiattivita;
import it.gruppoinit.stc.service.VwMessaggiattivitaService;

import java.util.Collection;

import org.jmesa.limit.Limit;
import org.jmesa.model.PageItems;

public class VwMessaggiattivitaPageItems extends BasePageItem<VwMessaggiattivita> implements PageItems {

    private VwMessaggiattivitaService vwMessaggiattivitaService;
    private VwMessaggiattivita vwMessaggiattivita;

    public VwMessaggiattivitaPageItems(VwMessaggiattivitaService vwMessaggiattivitaService) {

	this.vwMessaggiattivitaService = vwMessaggiattivitaService;
	this.vwMessaggiattivita = new VwMessaggiattivita();
    }

    @Override
    public Collection<?> getItems(Limit limit) {

	this.vwMessaggiattivita = getFilterQuery(vwMessaggiattivita, limit);
	return vwMessaggiattivitaService.findByFilter(vwMessaggiattivita, limit.getRowSelect().getRowStart(), limit.getRowSelect().getMaxRows());
    }

    @Override
    public int getTotalRows(Limit limit) {

	this.vwMessaggiattivita = getFilterQuery(vwMessaggiattivita, limit);
	return vwMessaggiattivitaService.countByFilter(vwMessaggiattivita);
    }
}
