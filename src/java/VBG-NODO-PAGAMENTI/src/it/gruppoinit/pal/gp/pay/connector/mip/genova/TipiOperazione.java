package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipiOperazione {

    INSERT("I"),
    DELETE("D"),
    UPDATE("U");

    private String name;

    private TipiOperazione(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}