package it.gruppoinit.pal.gp.core.domain.web;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoTestataHelper;

public class MovimentiZipLogicoCommand extends BaseCommand {

    private MovimentiZipLogicoTestataHelper testata;
    private MovimentiZipLogico entity;
    private List<MovimentiZipLogico> movimentiZipLogicoList;
    private DocumentiHelper documentiHelper;
    private List<ChiaveValoreBean<String, MovimentiZipLogicoDTO>> movimentiZipLogicoDTOList;

    public MovimentiZipLogicoCommand() {

	super();
	this.entity = new MovimentiZipLogico();
	this.testata = new MovimentiZipLogicoTestataHelper();
    }

    public MovimentiZipLogico getEntity() {

	return entity;
    }

    public void setEntity(MovimentiZipLogico entity) {

	this.entity = entity;
    }

    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public List<MovimentiZipLogico> getMovimentiZipLogicoList() {

	return movimentiZipLogicoList;
    }

    public void setMovimentiZipLogicoList(List<MovimentiZipLogico> movimentiZipLogicoList) {

	this.movimentiZipLogicoList = movimentiZipLogicoList;
    }

    public List<ChiaveValoreBean<String, MovimentiZipLogicoDTO>> getMovimentiZipLogicoDTOList() {

	return movimentiZipLogicoDTOList;
    }

    public void setMovimentiZipLogicoDTOList(List<ChiaveValoreBean<String, MovimentiZipLogicoDTO>> movimentiZipLogicoDTOList) {

	this.movimentiZipLogicoDTOList = movimentiZipLogicoDTOList;
    }

    public MovimentiZipLogicoTestataHelper getTestata() {

	return testata;
    }

    public void setTestata(MovimentiZipLogicoTestataHelper testata) {

	this.testata = testata;
    }
}
