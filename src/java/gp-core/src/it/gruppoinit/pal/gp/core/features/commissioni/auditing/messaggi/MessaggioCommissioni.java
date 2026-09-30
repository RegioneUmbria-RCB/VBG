package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public abstract class MessaggioCommissioni extends MessaggioDiSistema {

    private CommissioniCategorieEnum categoria;

    protected MessaggioCommissioni(CommissioniCategorieEnum categoria) {

	super();
	this.categoria = categoria;
    }

    public CommissioniCategorieEnum getCategoria() {

	return this.categoria;
    }

    public Date getDataSistema() {

	return new Date();
    }
}
