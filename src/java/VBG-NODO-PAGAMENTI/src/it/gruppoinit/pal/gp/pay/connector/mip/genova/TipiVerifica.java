package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipiVerifica {

    ESITO_CARICAMENTO("Esiti"),
    RENDICONTAZIONE_PAGAMENTI("Pagati");

    private String subFolderName;

    private TipiVerifica(String name) {

        this.subFolderName = name;
    }

    public String subFolderName() {

        return this.subFolderName;
    }
}