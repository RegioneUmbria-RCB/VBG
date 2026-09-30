package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagPresenzaIndirizzo {

    SI("1"),
    NO("0");

    private String name;

    private FlagPresenzaIndirizzo(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}