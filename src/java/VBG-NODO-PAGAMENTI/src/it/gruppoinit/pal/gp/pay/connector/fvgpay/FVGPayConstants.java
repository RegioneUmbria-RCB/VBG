package it.gruppoinit.pal.gp.pay.connector.fvgpay;

public class FVGPayConstants {

    public enum ElencoModelliPagamentoEnum {

	UNO("1"),
	TRE("3");

	private String valore;

	private ElencoModelliPagamentoEnum(String valore) {

	    this.valore = valore;
	}

	public String valore() {

	    return this.valore;
	}
    }
}
