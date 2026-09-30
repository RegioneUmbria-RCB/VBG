package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum FlagRipartizione {

    SENZA_RIPARTIZIONE("S"),
    RIPARTIZIONE_SU_FLUSSO("C"),
    RIPARTIZIONE_PARAMETRIZATA("P");

    private String name;

    private FlagRipartizione(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}