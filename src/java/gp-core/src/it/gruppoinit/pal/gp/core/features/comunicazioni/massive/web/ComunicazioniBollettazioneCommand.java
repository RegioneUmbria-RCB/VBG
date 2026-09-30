package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class ComunicazioniBollettazioneCommand extends ComunicazioniBaseCommand {

    private BollGestTestata gestTestata;
    private boolean allegaAvvisiPagamento;
    private boolean soloPosizioniDebitorieNonPagate;

    public ComunicazioniBollettazioneCommand() {

	super();
	this.gestTestata = new BollGestTestata();
	this.setFirmatario(new Responsabili());
    }

    public BollGestTestata getGestTestata() {

	return gestTestata;
    }

    public void setGestTestata(BollGestTestata gestTestata) {

	this.gestTestata = gestTestata;
    }

    public boolean isAllegaAvvisiPagamento() {

	return allegaAvvisiPagamento;
    }

    public void setAllegaAvvisiPagamento(boolean allegaAvvisiPagamento) {

	this.allegaAvvisiPagamento = allegaAvvisiPagamento;
    }

    public boolean isSoloPosizioniDebitorieNonPagate() {

	return soloPosizioniDebitorieNonPagate;
    }

    public void setSoloPosizioniDebitorieNonPagate(boolean soloPosizioniDebitorieNonPagate) {

	this.soloPosizioniDebitorieNonPagate = soloPosizioniDebitorieNonPagate;
    }
}
