package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PagamentoModel;

@XmlRootElement
public class StatiPosizioniDebitorieGiornataMercatoBean {

    @XmlElement
    private List<PagamentoModel> spuntisti = new ArrayList<PagamentoModel>(0);
    @XmlElement
    private List<PagamentoModel> concessionari = new ArrayList<PagamentoModel>(0);

    public StatiPosizioniDebitorieGiornataMercatoBean() {

	super();
    }

    public List<PagamentoModel> getSpuntisti() {

	if (this.spuntisti == null) {
	    this.spuntisti = new ArrayList<PagamentoModel>();
	}
	return this.spuntisti;
    }

    public List<PagamentoModel> getConcessionari() {

	if (this.concessionari == null) {
	    this.concessionari = new ArrayList<PagamentoModel>();
	}
	return this.concessionari;
    }

    public void setSpuntisti(List<PagamentoModel> spuntisti) {

	if (spuntisti == null) {
	    return;
	}
	this.spuntisti = spuntisti;
    }

    public void setConcessionari(List<PagamentoModel> concessionari) {

	if (concessionari == null) {
	    return;
	}
	this.concessionari = concessionari;
    }
}
