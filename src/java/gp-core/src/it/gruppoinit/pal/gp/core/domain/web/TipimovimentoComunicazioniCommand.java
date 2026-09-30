package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;
import it.gruppoinit.pal.gp.core.domain.helper.DestinatariHelper;

import java.util.ArrayList;
import java.util.List;

public class TipimovimentoComunicazioniCommand extends BaseCommand {

    private TipimovimentoComunicazioni entity;
    private List<DestinatariHelper> destinatariHelpers = new ArrayList<DestinatariHelper>();

    public TipimovimentoComunicazioni getEntity() {

	return entity;
    }

    public void setEntity(TipimovimentoComunicazioni entity) {

	this.entity = entity;
    }

    public List<DestinatariHelper> getDestinatariHelpers() {

	return destinatariHelpers;
    }

    public void setDestinatariHelpers(List<DestinatariHelper> destinatariHelpers) {

	this.destinatariHelpers = destinatariHelpers;
    }
}
