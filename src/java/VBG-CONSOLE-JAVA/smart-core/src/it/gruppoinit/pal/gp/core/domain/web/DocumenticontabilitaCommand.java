package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.helper.DocumenticontabilitaFilter;

public class DocumenticontabilitaCommand {

    private DocumentiContabilita entity;
    private DocumenticontabilitaFilter filter;
    private boolean isInserimentoVeloce;

    public DocumenticontabilitaCommand() {

	super();
	this.entity = new DocumentiContabilita();
	this.filter = new DocumenticontabilitaFilter();
    }

    public DocumenticontabilitaCommand(DocumentiContabilita entity, DocumenticontabilitaFilter filter) {

	super();
	this.entity = entity;
	this.filter = filter;
    }

    public DocumentiContabilita getEntity() {

	return entity;
    }

    public void setEntity(DocumentiContabilita entity) {

	this.entity = entity;
    }

    public DocumenticontabilitaFilter getFilter() {

	return filter;
    }

    public void setFilter(DocumenticontabilitaFilter filter) {

	this.filter = filter;
    }

    public boolean getIsInserimentoVeloce() {

	return isInserimentoVeloce;
    }

    public void setIsInserimentoVeloce(boolean isInserimentoVeloce) {

	this.isInserimentoVeloce = isInserimentoVeloce;
    }
}
