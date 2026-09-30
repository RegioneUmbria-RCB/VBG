package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.helper.TempirispostaHelper;

import java.util.ArrayList;
import java.util.List;

public class TempirispostaCommand extends BaseCommand {

    private Tempirisposta entity;
    private List<TempirispostaHelper> tempirispostaHelpers = new ArrayList<TempirispostaHelper>();
    private Tipicontromovimento tipicontromovimento;

    public TempirispostaCommand() {

	super();
	this.entity = new Tempirisposta();
    }

    public Tempirisposta getEntity() {

	return entity;
    }

    public void setEntity(Tempirisposta entity) {

	this.entity = entity;
    }

    public List<TempirispostaHelper> getTempirispostaHelpers() {

	return tempirispostaHelpers;
    }

    public void setTempirispostaHelpers(List<TempirispostaHelper> tempirispostaHelpers) {

	this.tempirispostaHelpers = tempirispostaHelpers;
    }

    public Tipicontromovimento getTipicontromovimento() {

	return tipicontromovimento;
    }

    public void setTipicontromovimento(Tipicontromovimento tipicontromovimento) {

	this.tipicontromovimento = tipicontromovimento;
    }
}
