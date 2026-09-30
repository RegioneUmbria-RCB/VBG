package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipiCodiceEnte {

    CODICE_INTERNO("1"),
    CODICE_ISTAT("2"),
    CODICE_BELFIORE("3");

    private String name;

    private TipiCodiceEnte(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}