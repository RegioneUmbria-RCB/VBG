package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

import java.io.Serializable;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class IstanzeMappaliRestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4962500310850744903L;
	private Integer id;
	private String foglio;
	private String particella;
	private String sub;
	private Istanze istanza;
	private Boolean primario;
	private CodiceDescrizioneBean catasto;
	private String sezione;
	private String unitaimmob;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Istanze getIstanza() {
		return istanza;
	}

	public void setIstanza(Istanze istanza) {
		this.istanza = istanza;
	}

	public Boolean getPrimario() {
		return primario;
	}

	public void setPrimario(Boolean primario) {
		this.primario = primario;
	}

	public CodiceDescrizioneBean getCatasto() {
		return catasto;
	}

	public void setCatasto(CodiceDescrizioneBean catasto) {
		this.catasto = catasto;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getUnitaimmob() {
		return unitaimmob;
	}

	public void setUnitaimmob(String unitaimmob) {
		this.unitaimmob = unitaimmob;
	}

}
