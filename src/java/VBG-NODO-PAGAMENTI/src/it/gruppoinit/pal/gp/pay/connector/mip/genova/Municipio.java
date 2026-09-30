package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum Municipio {

    CENTRO_EST("M1"),
    CENTRO_OVEST("M2"),
    BASSA_VAL_BISAGNO("M3"),
    MEDIA_VAL_BISAGNO("M4"),
    VALPOLCEVERA("M5"),
    MEDIO_PONENTE("M6"),
    PONENTE("M7"),
    MEDIO_LEVANTE("M8"),
    LEVANTE("M9"),
    UFFICIO_CENTRALE("M10");

    private String name;

    private Municipio(String name) {

        this.name = name;
    }

    public String value() {

        return this.name;
    }
}