package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagAccorpamentoPerDestinatario {

    NO("0"),
    SI("1");

    private String name;

    private FlagAccorpamentoPerDestinatario(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}