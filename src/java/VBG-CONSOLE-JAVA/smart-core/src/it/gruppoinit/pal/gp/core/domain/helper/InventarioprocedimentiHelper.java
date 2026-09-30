package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;

import java.util.HashSet;
import java.util.Set;

public class InventarioprocedimentiHelper {

    private Inventarioprocedimenti inventarioprocedimenti;
    private Set<Allegati> allegatis = new HashSet<Allegati>();
    private Set<Testiestesi> testiestesis = new HashSet<Testiestesi>();
    private Set<Documenti> documentis = new HashSet<Documenti>();
    private Set<Inventarioprocedimentioneri> inventarioprocedimentioneris = new HashSet<Inventarioprocedimentioneri>();
    private Set<EndoCausali> endoCausalis = new HashSet<EndoCausali>();
    private Set<Inventarioprocedimentiincomp> inventarioprocedimentiincomps = new HashSet<Inventarioprocedimentiincomp>();
    private Set<Inventarioprocdyn2modellit> inventarioprocdyn2modellits = new HashSet<Inventarioprocdyn2modellit>();
    private Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = new HashSet<Inventarioprocedimentisoftware>();
    private Set<Inventarioprocedimentipeople> inventarioprocedimentipeoples = new HashSet<Inventarioprocedimentipeople>();
    private Set<InventarioprocLeggi> inventarioprocLeggis = new HashSet<InventarioprocLeggi>();

    public InventarioprocedimentiHelper() {

	this.inventarioprocedimenti = new Inventarioprocedimenti();
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public Set<Allegati> getAllegatis() {

	return allegatis;
    }

    public void setAllegatis(Set<Allegati> allegatis) {

	this.allegatis = allegatis;
    }

    public Set<Testiestesi> getTestiestesis() {

	return testiestesis;
    }

    public void setTestiestesis(Set<Testiestesi> testiestesis) {

	this.testiestesis = testiestesis;
    }

    public Set<Documenti> getDocumentis() {

	return documentis;
    }

    public void setDocumentis(Set<Documenti> documentis) {

	this.documentis = documentis;
    }

    public Set<Inventarioprocedimentioneri> getInventarioprocedimentioneris() {

	return inventarioprocedimentioneris;
    }

    public void setInventarioprocedimentioneris(Set<Inventarioprocedimentioneri> inventarioprocedimentioneris) {

	this.inventarioprocedimentioneris = inventarioprocedimentioneris;
    }

    public Set<EndoCausali> getEndoCausalis() {

	return endoCausalis;
    }

    public void setEndoCausalis(Set<EndoCausali> endoCausalis) {

	this.endoCausalis = endoCausalis;
    }

    public Set<Inventarioprocedimentiincomp> getInventarioprocedimentiincomps() {

	return inventarioprocedimentiincomps;
    }

    public void setInventarioprocedimentiincomps(Set<Inventarioprocedimentiincomp> inventarioprocedimentiincomps) {

	this.inventarioprocedimentiincomps = inventarioprocedimentiincomps;
    }

    public Set<Inventarioprocdyn2modellit> getInventarioprocdyn2modellits() {

	return inventarioprocdyn2modellits;
    }

    public void setInventarioprocdyn2modellits(Set<Inventarioprocdyn2modellit> inventarioprocdyn2modellits) {

	this.inventarioprocdyn2modellits = inventarioprocdyn2modellits;
    }

    public Set<Inventarioprocedimentisoftware> getInventarioprocedimentisoftwares() {

	return inventarioprocedimentisoftwares;
    }

    public void setInventarioprocedimentisoftwares(Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares) {

	this.inventarioprocedimentisoftwares = inventarioprocedimentisoftwares;
    }

    public Set<Inventarioprocedimentipeople> getInventarioprocedimentipeoples() {

	return inventarioprocedimentipeoples;
    }

    public void setInventarioprocedimentipeoples(Set<Inventarioprocedimentipeople> inventarioprocedimentipeoples) {

	this.inventarioprocedimentipeoples = inventarioprocedimentipeoples;
    }

    public Set<InventarioprocLeggi> getInventarioprocLeggis() {

	return inventarioprocLeggis;
    }

    public void setInventarioprocLeggis(Set<InventarioprocLeggi> inventarioprocLeggis) {

	this.inventarioprocLeggis = inventarioprocLeggis;
    }
}
