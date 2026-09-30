package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroChiaveApplicationCodeIUV extends ParameterBase {

    public ParametroChiaveApplicationCodeIUV(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroChiaveApplicationCodeIUV() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "CHIAVE_APPLICATION_CODE_IUV";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
