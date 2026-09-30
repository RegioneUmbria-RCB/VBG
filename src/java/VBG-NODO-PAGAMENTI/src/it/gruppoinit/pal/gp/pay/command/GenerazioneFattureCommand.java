package it.gruppoinit.pal.gp.pay.command;

import java.util.ArrayList;
import java.util.List;

public class GenerazioneFattureCommand extends PayBaseCommand {

    private static final long serialVersionUID = -4814674478965551602L;
    private List<RichiestaFatturaCommand> richieste = new ArrayList<RichiestaFatturaCommand>();

    public GenerazioneFattureCommand(String idRichiesta) {

	this.idRichiesta = idRichiesta;
    }

    public List<RichiestaFatturaCommand> getRichieste() {

	return richieste;
    }
}
