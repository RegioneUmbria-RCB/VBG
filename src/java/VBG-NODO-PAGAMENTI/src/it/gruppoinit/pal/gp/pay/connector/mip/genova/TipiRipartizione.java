package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipiRipartizione {

    SOTTOSERVIZIO("S"),
    ACCERTAMENTO("A"),
    CODICI_TRIBUTO("T");

    private String name;

    private TipiRipartizione(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}