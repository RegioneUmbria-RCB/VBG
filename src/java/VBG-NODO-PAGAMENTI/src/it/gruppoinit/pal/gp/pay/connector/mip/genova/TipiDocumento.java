package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipiDocumento {

    LOTTO("Lotto", "txt"), //
    DEBITO("Debito", "txt"), //
    RATA("Rata", "txt"), //
    RIPARTIZIONE("Ripartizione", "txt"), //
    //ALLEGATO("Allegato", "pdf"),
    ESITO_CARICAMENTO("CodiceAvviso", "txt"), //
    NOTIFICA_PAGAMENTO("NODO_Notifica", "txt"), //
    REPORT("Report", "txt"), PDF("PDF", "zip");

    private String name;
    private String ext;

    private TipiDocumento(String name, String ext) {

        this.name = name;
        this.ext = ext;
    }

    public String fileName() {

        return this.name;
    }

    public String extension() {

        return this.ext;
    }
}