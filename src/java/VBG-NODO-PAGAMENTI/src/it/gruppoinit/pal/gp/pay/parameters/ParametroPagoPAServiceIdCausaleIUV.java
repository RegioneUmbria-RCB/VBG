package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroPagoPAServiceIdCausaleIUV extends ParameterBase {

    public ParametroPagoPAServiceIdCausaleIUV(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroPagoPAServiceIdCausaleIUV() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "PAGO_PA_SERVICE_ID_CAUSALE_IUV";

    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
