package it.gruppoinit.pal.gp.pay.connector.nexi.genova;

public enum ModalitaEmissioneTipoDocumentoPagamento {

    //NESSUNO("0"),
    //BANCA("1"),
    //POSTALIZZATORE("2"),
    //EMAIL("3"),
    //SITO("4")
    TEST("5");

    private String name;

    private ModalitaEmissioneTipoDocumentoPagamento(String name) {

	this.name = name;
    }

    public String value() {

	return this.name;
    }
}