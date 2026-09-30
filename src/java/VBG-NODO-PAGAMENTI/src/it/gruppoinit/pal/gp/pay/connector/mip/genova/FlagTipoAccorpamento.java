package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagTipoAccorpamento {

    NON_ACCORPABILE("N"),
    ACCORPABILE_STESSA_TIPOLGIA("S"),
    ACCORPABILE_ALTRE_TIPOLGIE("T");

    private String name;

    private FlagTipoAccorpamento(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}