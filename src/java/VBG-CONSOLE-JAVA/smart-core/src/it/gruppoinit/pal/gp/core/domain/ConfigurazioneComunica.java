package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIGURAZIONE_COMUNICA")
public class ConfigurazioneComunica implements Serializable {

    private static final long serialVersionUID = -2690551776876400386L;
    private String id;
    private Oggetti oggettoWorkflowAreaRis;
    private Inventarioprocedimenti inventarioprocedimento;
    private String comunicaUsername;
    private String comunicaPassword;
    private String versioneRfc;

    @Id
    @Column(name = "IDCOMUNE", unique = true, nullable = false, length = 6)
    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEOGGETTO_RICEVUTA", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getOggettoWorkflowAreaRis() {

	return oggettoWorkflowAreaRis;
    }

    public void setOggettoWorkflowAreaRis(Oggetti oggettoWorkflowAreaRis) {

	this.oggettoWorkflowAreaRis = oggettoWorkflowAreaRis;
    }

    private Integer oggettoWorkflowAreaRisId;

    @Column(name = "CODICEOGGETTO_RICEVUTA")
    private Integer getOggettoWorkflowAreaRisId() {

	if (null != this.getOggettoWorkflowAreaRis()) {
	    if (null != this.getOggettoWorkflowAreaRis().getId()) {
		this.oggettoWorkflowAreaRisId = getOggettoWorkflowAreaRis().getId().getCodice();
		return this.oggettoWorkflowAreaRisId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setOggettoWorkflowAreaRisId(Integer oggettoWorkflowAreaRisId) {

	if (null != this.getOggettoWorkflowAreaRis()) {
	    if (null != this.getOggettoWorkflowAreaRis().getId()) {
		this.oggettoWorkflowAreaRisId = getOggettoWorkflowAreaRis().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEINVENTARIO_COMUNICA", referencedColumnName = "CODICEINVENTARIO", nullable = false, insertable = false, updatable = false) })
    public Inventarioprocedimenti getInventarioprocedimento() {

	return this.inventarioprocedimento;
    }

    public void setInventarioprocedimento(Inventarioprocedimenti inventarioprocedimento) {

	this.inventarioprocedimento = inventarioprocedimento;
    }

    private Integer inventarioprocedimentoId;

    @Column(name = "FK_CODICEINVENTARIO_COMUNICA")
    private Integer getInventarioprocedimentoId() {

	if (null != this.getInventarioprocedimento()) {
	    if (null != this.getInventarioprocedimento().getId()) {
		this.inventarioprocedimentoId = getInventarioprocedimento().getId().getCodice();
		return this.inventarioprocedimentoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setInventarioprocedimentoId(Integer inventarioprocedimentoId) {

	if (null != this.getInventarioprocedimento()) {
	    if (null != this.getInventarioprocedimento().getId()) {
		this.inventarioprocedimentoId = getInventarioprocedimento().getId().getCodice();
	    }
	}
    }

    @Column(name = "COMUNICA_USERNAME", length = 25)
    public String getComunicaUsername() {

	return comunicaUsername;
    }

    public void setComunicaUsername(String comunicaUsername) {

	this.comunicaUsername = comunicaUsername;
    }

    @Column(name = "COMUNICA_PASSWORD", length = 25)
    public String getComunicaPassword() {

	return comunicaPassword;
    }

    public void setComunicaPassword(String comunicaPassword) {

	this.comunicaPassword = comunicaPassword;
    }

    @Column(name = "VERSIONE_RFC", length = 10)
    public String getVersioneRfc() {

	return versioneRfc;
    }

    public void setVersioneRfc(String versioneRfc) {

	this.versioneRfc = versioneRfc;
    }
}
