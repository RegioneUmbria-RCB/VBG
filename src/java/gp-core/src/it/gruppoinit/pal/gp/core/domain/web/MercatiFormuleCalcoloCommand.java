package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;

public class MercatiFormuleCalcoloCommand extends BaseCommand {

    private MercatiFormuleCalcolo entity;
    private MercatiContabilitaTributi mercatiContabilitaTributi;

    public MercatiFormuleCalcoloCommand() {

	this.entity = new MercatiFormuleCalcolo();
	this.mercatiContabilitaTributi = new MercatiContabilitaTributi();
    }

    public MercatiFormuleCalcolo getEntity() {

	return entity;
    }

    public void setEntity(MercatiFormuleCalcolo entity) {

	this.entity = entity;
    }

    public MercatiContabilitaTributi getMercatiContabilitaTributi() {

	return mercatiContabilitaTributi;
    }

    public void setMercatiContabilitaTributi(MercatiContabilitaTributi mercatiContabilitaTributi) {

	this.mercatiContabilitaTributi = mercatiContabilitaTributi;
    }
}
