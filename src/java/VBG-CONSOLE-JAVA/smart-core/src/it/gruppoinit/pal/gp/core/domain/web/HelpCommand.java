package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.Helpbase;

public class HelpCommand extends BaseCommand {

    private Help help;
    private Helpbase helpbase;

    public HelpCommand() {

	super();
	this.help = new Help();
	this.helpbase = new Helpbase();
    }

    public Help getHelp() {

	return help;
    }

    public void setHelp(Help help) {

	this.help = help;
    }

    public Helpbase getHelpbase() {

	return helpbase;
    }

    public void setHelpbase(Helpbase helpbase) {

	this.helpbase = helpbase;
    }
}
