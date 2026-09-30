package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.DocumentoHelper;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.StcDomainHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.EstremiAttoType;
import it.init.sigepro.rte.types.ProcedimentoType;

import java.util.ArrayList;
import java.util.List;

public class ProcedimentoHelper {

    private boolean selezionato;
    private ProcedimentoType procedimento;
    private List<DocumentoHelper> documenti = new ArrayList<DocumentoHelper>();
    private ProcedimentoKey famigliaKey;
    private ProcedimentoKey categoriaKey;
    private ProcedimentoKey key;
    private boolean tipiTitoloPresent;
    private boolean tipiTitoloSonoInPossesso;
    private AllegatiType allegatoTipoTitolo;
    private Integer idx;

    public ProcedimentoHelper(Inventarioprocedimenti procedimento) {

	this.procedimento = StcDomainHelper.populateProcedimentoType(procedimento);
	this.key = new ProcedimentoKey(procedimento.getOrdine(), procedimento.getProcedimento());
	if (EntityUtils.isNestedPropertyBlank(procedimento, "tipoendo")) {
	    this.categoriaKey = new ProcedimentoKey(null, null);
	    this.famigliaKey = new ProcedimentoKey(null, null);
	} else {
	    this.categoriaKey = new ProcedimentoKey(procedimento.getTipoendo().getOrdine(), procedimento.getTipoendo().getTipo());
	    if (EntityUtils.isNestedPropertyBlank(procedimento, "tipoendo.tipifamiglieendo")) {
		this.famigliaKey = new ProcedimentoKey(null, null);
	    } else {
		this.famigliaKey = new ProcedimentoKey(procedimento.getTipoendo().getTipifamiglieendo().getOrdine(), procedimento.getTipoendo()
			.getTipifamiglieendo().getTipo());
	    }
	}
	if (!procedimento.getInventarioprocTipititolos().isEmpty()) {
	    tipiTitoloPresent = true;
	    this.procedimento.setEstremiAtto(new EstremiAttoType());
	    this.allegatoTipoTitolo = new AllegatiType();
	}
    }

    public boolean isSelezionato() {

	return selezionato;
    }

    public void setSelezionato(boolean selezionato) {

	this.selezionato = selezionato;
    }

    public ProcedimentoKey getFamigliaKey() {

	return famigliaKey;
    }

    public void setFamigliaKey(ProcedimentoKey famigliaKey) {

	this.famigliaKey = famigliaKey;
    }

    public ProcedimentoKey getCategoriaKey() {

	return categoriaKey;
    }

    public void setCategoriaKey(ProcedimentoKey categoriaKey) {

	this.categoriaKey = categoriaKey;
    }

    public ProcedimentoType getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(ProcedimentoType procedimento) {

	this.procedimento = procedimento;
    }

    public List<DocumentoHelper> getDocumenti() {

	return documenti;
    }

    public void setDocumenti(List<DocumentoHelper> documenti) {

	this.documenti = documenti;
    }

    public ProcedimentoKey getKey() {

	return key;
    }

    public void setKey(ProcedimentoKey key) {

	this.key = key;
    }

    public boolean isTipiTitoloPresent() {

	return tipiTitoloPresent;
    }

    public void setTipiTitoloPresent(boolean tipiTitoloPresent) {

	this.tipiTitoloPresent = tipiTitoloPresent;
    }

    public boolean isTipiTitoloSonoInPossesso() {

	return tipiTitoloSonoInPossesso;
    }

    public void setTipiTitoloSonoInPossesso(boolean tipiTitoloSonoInPossesso) {

	this.tipiTitoloSonoInPossesso = tipiTitoloSonoInPossesso;
    }

    public AllegatiType getAllegatoTipoTitolo() {

	return allegatoTipoTitolo;
    }

    public void setAllegatoTipoTitolo(AllegatiType allegatoTipoTitolo) {

	this.allegatoTipoTitolo = allegatoTipoTitolo;
    }

    public Integer getIdx() {

	return idx;
    }

    public void setIdx(Integer idx) {

	this.idx = idx;
    }
}
