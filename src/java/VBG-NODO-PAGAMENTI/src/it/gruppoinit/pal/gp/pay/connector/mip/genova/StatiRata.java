package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum StatiRata {

    PAGATO("P"),
    INSOLUTO("I"),
    QUADRATO("Q"),
    RIPARTITO("R"),
    PAGATO_IN_ECCESSO("E"),
    PAGATO_IN_DIFETTO("D"),
    INFO_PAGAMENTO("Z");

    private String name;

    private StatiRata(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }

    public static StatiRata fromValue(String val) {

        for (StatiRata enumVal : StatiRata.values()) {
    	if (enumVal.value().equals(val)) {
    	    return enumVal;
    	}
        }
        throw new RuntimeException("Il valore non è stato trovato nella enumeration StatiRata");
    }
}