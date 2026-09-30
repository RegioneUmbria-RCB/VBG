package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum IdentificativoVettore {

    POSTE("1"),
    MESSI("2");

    String name;

    private IdentificativoVettore(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}