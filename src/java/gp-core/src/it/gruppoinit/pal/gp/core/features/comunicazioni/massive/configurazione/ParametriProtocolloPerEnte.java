package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.MassiveTProtMetadati;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtocollo;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

public class ParametriProtocolloPerEnte {

    private Integer codiceAmministrazione;
    private String classifica;
    private String tipodocumento;
    private String codiceComune;
    private List<MetadatiBean> metadati;

    public ParametriProtocolloPerEnte(Integer codiceAmministrazione, String classifica, String tipodocumento, String codiceComune,
	    List<MetadatiBean> metadati) {

	this.codiceAmministrazione = codiceAmministrazione;
	this.classifica = classifica;
	this.tipodocumento = tipodocumento;
	this.codiceComune = codiceComune;
	if (metadati != null) {
	    this.getMetadati().addAll(metadati);
	}
    }

    public static ParametriProtocolloPerEnte fromIParametriProtocolloPerEnteHelper(IParametriProtocolloPerEnteHelper parametri) {

	if (parametri == null) {
	    return null;
	}
	String codiceComune = null;
	if (parametri.getComune() != null && StringUtils.isNotBlank(parametri.getComune().getCodice())) {
	    codiceComune = parametri.getComune().getCodice();
	}
	return new ParametriProtocolloPerEnte(parametri.getAmmMittente().getId(), parametri.getClassifica(), parametri.getTipodocumento(),
		codiceComune, parametri.getMetadati());
    }

    public static ParametriProtocolloPerEnte fromMassiveTProtocollo(MassiveTProtocollo m) {

	if (m == null) {
	    return null;
	}
	List<MetadatiBean> metadati = new ArrayList<MetadatiBean>(0);
	if (m.getMetadati() != null) {
	    for (MassiveTProtMetadati metadato : m.getMetadati()) {
		metadati.add(new MetadatiBean(metadato.getChiave(), metadato.getValore()));
	    }
	}
	return new ParametriProtocolloPerEnte(m.getAmministrazioni().getId().getCodice(), m.getClassifica(), m.getTipodocumento(),
		m.getComuni() == null ? null : m.getComuni().getCodicecomune(), metadati);
    }

    public Integer getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public String getClassifica() {

	return classifica;
    }

    public String getTipodocumento() {

	return tipodocumento;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public List<MetadatiBean> getMetadati() {

	if (metadati == null) {
	    metadati = new ArrayList<MetadatiBean>(0);
	}
	return metadati;
    }
}
