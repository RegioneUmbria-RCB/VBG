package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagPagamento {

    PAGABILE("1"),
    NON_PAGABILE("2"),
    PAGATO("3");

    private String name;

    private FlagPagamento(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}