package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagRateizzazione {

    NON_RATEIZZATO("1"),
    RATEIZZATO_IMPORTO_COMPLESSIVO_PAGABILE("2"),
    RATEIZZATO_IMPORTO_COMPLESSIVO_NON_PAGABILE("3");

    private String name;

    private FlagRateizzazione(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}