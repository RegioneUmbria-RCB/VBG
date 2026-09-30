package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegate;

@XmlRootElement()
public class AmministrazioneCollegataBean implements Comparable<AmministrazioneCollegataBean> {

    @XmlElement(name = "idAmministrazione")
    private Integer idAmministrazione;
    @XmlElement(name = "amministrazione")
    private String amministrazione;
    @XmlElement(name = "comuni", nillable = true)
    private List<AmmCollComuneBean> comuni;

    public AmministrazioneCollegataBean() {

    }

    public AmministrazioneCollegataBean(AmministrazioniCollegate ammCollegata) {

	this();
	this.idAmministrazione = ammCollegata.getAmmCollegata().getId().getCodice();
	this.amministrazione = ammCollegata.getAmmCollegata().getAmministrazione();
	this.getComuni().add(new AmmCollComuneBean(ammCollegata.getComune()));
    }

    public Integer getIdAmministrazione() {

	return idAmministrazione;
    }

    public void setIdAmministrazione(Integer idAmministrazione) {

	this.idAmministrazione = idAmministrazione;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public List<AmmCollComuneBean> getComuni() {

	if (this.comuni == null) {
	    this.comuni = new ArrayList<AmmCollComuneBean>();
	}
	Collections.sort(this.comuni);
	return comuni;
    }

    public void setComuni(List<AmmCollComuneBean> comuni) {

	this.comuni = comuni;
    }

    @Override
    public int compareTo(AmministrazioneCollegataBean o) {

	String questaAmministrazione = StringUtils.defaultString(this.amministrazione, "00000000000000000000");
	String altraAmministrazione = "00000000000000000000";
	if (o != null && o.getAmministrazione() != null) {
	    altraAmministrazione = o.getAmministrazione();
	}
	return questaAmministrazione.compareTo(altraAmministrazione);
    }
}
