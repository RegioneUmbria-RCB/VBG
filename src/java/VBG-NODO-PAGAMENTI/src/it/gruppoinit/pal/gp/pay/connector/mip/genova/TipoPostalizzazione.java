package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum TipoPostalizzazione {

    POSTA_MASSIVA("1"),
    TNT("2"),
    RACCOMANDATA("3"),
    MESSI("4");

    String name;

    private TipoPostalizzazione(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}