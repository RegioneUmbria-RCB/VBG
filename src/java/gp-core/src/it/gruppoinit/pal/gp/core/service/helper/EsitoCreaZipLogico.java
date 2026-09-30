package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;

public class EsitoCreaZipLogico {

    private boolean prosegui;
    private boolean isZipLogico;
    private DocumentiHelper documentHelper;

    public boolean isProsegui() {

	return prosegui;
    }

    public void setProsegui(boolean prosegui) {

	this.prosegui = prosegui;
    }

    public boolean isZipLogico() {

	return isZipLogico;
    }

    public void setZipLogico(boolean isZipLogico) {

	this.isZipLogico = isZipLogico;
    }

    public DocumentiHelper getDocumentHelper() {

	return documentHelper;
    }

    public void setDocumentHelper(DocumentiHelper documentHelper) {

	this.documentHelper = documentHelper;
    }
}
