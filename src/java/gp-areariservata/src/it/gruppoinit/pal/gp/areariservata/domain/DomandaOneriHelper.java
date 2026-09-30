package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

import java.util.ArrayList;
import java.util.List;

public class DomandaOneriHelper {

    private String emailPagamentiOnline;
    private List<FoArjDomandeOneri> foArjDomandeOneris;
    private List<FoArjDomandeOneri> oneriIntervento = new ArrayList<FoArjDomandeOneri>();
    private List<FoArjDomandeOneri> oneriProcedimenti = new ArrayList<FoArjDomandeOneri>();

    private DomandaOneriHelper() {

	super();
    }

    public DomandaOneriHelper(List<FoArjDomandeOneri> foArjDomandeOneris) {

	this();
	this.foArjDomandeOneris = foArjDomandeOneris;
	initialize();
    }

    private void initialize() {

	for (FoArjDomandeOneri don : foArjDomandeOneris) {
	    if (don.getInventarioprocedimenti() == null) {
		oneriIntervento.add(foArjDomandeOnerisDTO(don));
	    } else {
		oneriProcedimenti.add(foArjDomandeOnerisDTO(don));
	    }
	}
    }

    public List<FoArjDomandeOneri> getOneriIntervento() {

	return oneriIntervento;
    }

    public void setOneriIntervento(List<FoArjDomandeOneri> oneriIntervento) {

	this.oneriIntervento = oneriIntervento;
    }

    public List<FoArjDomandeOneri> getOneriProcedimenti() {

	return oneriProcedimenti;
    }

    public void setOneriProcedimenti(List<FoArjDomandeOneri> oneriProcedimenti) {

	this.oneriProcedimenti = oneriProcedimenti;
    }

    private FoArjDomandeOneri foArjDomandeOnerisDTO(FoArjDomandeOneri source) {

	FoArjDomandeOneri dest = new FoArjDomandeOneri();
	if (dest.getId() == null) {
	    dest.setId(new PkId());
	}
	dest.getId().setCodice(source.getId().getCodice());
	dest.getId().setIdcomune(source.getId().getIdcomune());
	dest.setImporto(source.getImporto());
	dest.setFlagOnline(source.getFlagOnline());
	dest.setFlagStato(source.getFlagStato());
	FoArjDomande dom = new FoArjDomande();
	if (dom.getId() == null) {
	    dom.setId(new PkId());
	}
	dom.getId().setIdcomune(source.getFoArjDomande().getId().getIdcomune());
	dom.getId().setCodice(source.getFoArjDomande().getId().getCodice());
	dest.setFoArjDomande(dom);
	if (source.getInventarioprocedimenti() != null) {
	    Inventarioprocedimenti endo = new Inventarioprocedimenti();
	    if (endo.getId() == null) {
		endo.setId(new PkId());
	    }
	    endo.setProcedimento(source.getInventarioprocedimenti().getProcedimento());
	    endo.getId().setIdcomune(source.getInventarioprocedimenti().getId().getIdcomune());
	    endo.getId().setCodice(source.getInventarioprocedimenti().getId().getCodice());
	    dest.setInventarioprocedimenti(endo);
	}
	Tipicausalioneri tco = new Tipicausalioneri();
	if (tco.getId() == null) {
	    tco.setId(new PkId());
	}
	tco.getId().setIdcomune(source.getTipicausalioneri().getId().getIdcomune());
	tco.getId().setCodice(source.getTipicausalioneri().getId().getCodice());
	tco.setCoDescrizione(source.getTipicausalioneri().getCoDescrizione());
	dest.setTipicausalioneri(tco);
	dest.setIdNumOperazOnline(source.getIdNumOperazOnline());
	dest.setIdordineSistemaPagamenti(source.getIdordineSistemaPagamenti());
	if (source.getOggettoPdf() != null) {
	    Oggetti oggettoPdf = new Oggetti();
	    if (oggettoPdf.getId() == null) {
		oggettoPdf.setId(new PkId());
	    }
	    oggettoPdf.getId().setIdcomune(source.getOggettoPdf().getId().getIdcomune());
	    oggettoPdf.getId().setCodice(source.getOggettoPdf().getId().getCodice());
	    oggettoPdf.setNomefile(source.getOggettoPdf().getNomefile());
	    oggettoPdf.setDimensioneFile(source.getOggettoPdf().getDimensioneFile());
	    dest.setOggettoPdf(oggettoPdf);
	}
	if (source.getOggettoXml() != null) {
	    Oggetti oggettoXml = new Oggetti();
	    if (oggettoXml.getId() == null) {
		oggettoXml.setId(new PkId());
	    }
	    oggettoXml.getId().setIdcomune(source.getOggettoXml().getId().getIdcomune());
	    oggettoXml.getId().setCodice(source.getOggettoXml().getId().getCodice());
	    oggettoXml.setNomefile(source.getOggettoXml().getNomefile());
	    oggettoXml.setDimensioneFile(source.getOggettoXml().getDimensioneFile());
	    dest.setOggettoXml(oggettoXml);
	}
	dest.setNote(source.getNote());
	return dest;
    }

    public String getEmailPagamentiOnline() {

	return emailPagamentiOnline;
    }

    public void setEmailPagamentiOnline(String emailPagamentiOnline) {

	this.emailPagamentiOnline = emailPagamentiOnline;
    }
}
