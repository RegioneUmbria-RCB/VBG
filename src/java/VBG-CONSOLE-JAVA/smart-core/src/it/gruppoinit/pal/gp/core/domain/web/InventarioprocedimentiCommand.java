package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;

public class InventarioprocedimentiCommand extends BaseCommand {

    private Inventarioprocedimenti entity;
    private Allegati allegati;
    private Testiestesi testiestesi;
    private Documenti documenti;
    private Integer codiceAlberoproc;
    private Inventarioprocedimentioneri inventarioprocedimentioneri;
    private Inventarioprocedimentisoftware inventarioprocedimentisoftware;
    private Inventarioprocdyn2modellit inventarioprocdyn2modellit;
    private Inventarioprocedimentiincomp inventarioprocedimentiincomp;
    private InventarioprocLeggi inventarioprocLeggi;
    private InventarioprocTipititolo inventarioprocTipititolo;
    private Inventarioprocedimentipeople inventarioprocedimentipeople;
    private StpEndoTipo1 stpEndoTipo1;
    private boolean modificaPermessa = false;

    public InventarioprocedimentiCommand() {

	super();
	this.entity = new Inventarioprocedimenti();
	this.allegati = new Allegati();
	this.documenti = new Documenti();
	this.testiestesi = new Testiestesi();
	this.inventarioprocedimentioneri = new Inventarioprocedimentioneri();
	this.inventarioprocedimentisoftware = new Inventarioprocedimentisoftware();
	this.inventarioprocdyn2modellit = new Inventarioprocdyn2modellit();
	this.inventarioprocedimentiincomp = new Inventarioprocedimentiincomp();
	this.inventarioprocLeggi = new InventarioprocLeggi();
	this.setInventarioprocedimentipeople(new Inventarioprocedimentipeople());
    }

    public Inventarioprocedimenti getEntity() {

	return entity;
    }

    public void setEntity(Inventarioprocedimenti entity) {

	this.entity = entity;
    }

    public Allegati getAllegati() {

	return allegati;
    }

    public void setAllegati(Allegati allegati) {

	this.allegati = allegati;
    }

    public Testiestesi getTestiestesi() {

	return testiestesi;
    }

    public void setTestiestesi(Testiestesi testiestesi) {

	this.testiestesi = testiestesi;
    }

    public Documenti getDocumenti() {

	return documenti;
    }

    public void setDocumenti(Documenti documenti) {

	this.documenti = documenti;
    }

    public Inventarioprocedimentioneri getInventarioprocedimentioneri() {

	return inventarioprocedimentioneri;
    }

    public void setInventarioprocedimentioneri(Inventarioprocedimentioneri inventarioprocedimentioneri) {

	this.inventarioprocedimentioneri = inventarioprocedimentioneri;
    }

    public Inventarioprocedimentisoftware getInventarioprocedimentisoftware() {

	return inventarioprocedimentisoftware;
    }

    public void setInventarioprocedimentisoftware(Inventarioprocedimentisoftware inventarioprocedimentisoftware) {

	this.inventarioprocedimentisoftware = inventarioprocedimentisoftware;
    }

    public Inventarioprocdyn2modellit getInventarioprocdyn2modellit() {

	return inventarioprocdyn2modellit;
    }

    public void setInventarioprocdyn2modellit(Inventarioprocdyn2modellit inventarioprocdyn2modellit) {

	this.inventarioprocdyn2modellit = inventarioprocdyn2modellit;
    }

    public Inventarioprocedimentiincomp getInventarioprocedimentiincomp() {

	return inventarioprocedimentiincomp;
    }

    public void setInventarioprocedimentiincomp(Inventarioprocedimentiincomp inventarioprocedimentiincomp) {

	this.inventarioprocedimentiincomp = inventarioprocedimentiincomp;
    }

    public InventarioprocLeggi getInventarioprocLeggi() {

	return inventarioprocLeggi;
    }

    public void setInventarioprocLeggi(InventarioprocLeggi inventarioprocLeggi) {

	this.inventarioprocLeggi = inventarioprocLeggi;
    }

    public InventarioprocTipititolo getInventarioprocTipititolo() {

	return inventarioprocTipititolo;
    }

    public void setInventarioprocTipititolo(InventarioprocTipititolo inventarioprocTipititolo) {

	this.inventarioprocTipititolo = inventarioprocTipititolo;
    }

    public void setInventarioprocedimentipeople(Inventarioprocedimentipeople inventarioprocedimentipeople) {

	this.inventarioprocedimentipeople = inventarioprocedimentipeople;
    }

    public Inventarioprocedimentipeople getInventarioprocedimentipeople() {

	return inventarioprocedimentipeople;
    }

    public Integer getCodiceAlberoproc() {

	return codiceAlberoproc;
    }

    public void setCodiceAlberoproc(Integer codiceAlberoproc) {

	this.codiceAlberoproc = codiceAlberoproc;
    }

    public StpEndoTipo1 getStpEndoTipo1() {

	return stpEndoTipo1;
    }

    public void setStpEndoTipo1(StpEndoTipo1 stpEndoTipo1) {

	this.stpEndoTipo1 = stpEndoTipo1;
    }

    public boolean isModificaPermessa() {

	String idcomune = ORMHelper.getIdcomune();
	String idcomuneEntity = "";
	if (getEntity() != null) {
	    if (getEntity().getId() != null) {
		idcomuneEntity = getEntity().getId().getIdcomune();
	    }
	}
	return idcomune.equalsIgnoreCase(idcomuneEntity);
    }

    public void setModificaPermessa(boolean modificaPermessa) {

	this.modificaPermessa = modificaPermessa;
    }
}
