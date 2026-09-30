package it.gruppoinit.pal.gp.pay.parameters;

public abstract class ParameterBase implements IParameter {

    private String valore;
    private String descrizione;
    private String help;

    public ParameterBase() {

	super();
    }

    public ParameterBase(String descrizione, String help) {

	this();
	this.descrizione = descrizione;
	this.help = help;
    }

    @Override
    public void setValore(String valore) {

	this.valore = valore;
    }

    @Override
    public String getValore() {

	return this.valore;
    }

    @Override
    public String getDescrizione() {

	return descrizione;
    }

    @Override
    public String getHelp() {

	return help;
    }
}
