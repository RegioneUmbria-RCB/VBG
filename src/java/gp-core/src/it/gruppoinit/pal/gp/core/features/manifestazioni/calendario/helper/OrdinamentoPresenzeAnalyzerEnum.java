package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.helper;

public enum OrdinamentoPresenzeAnalyzerEnum {

    NUMPRESENZE("NUMPRESENZE"),
    DATAREGDITTE("DATAREGDITTE"),
    DATAANZIANITA("DATAANZIANITA"),
    AUTORIZDATA("AUTORIZDATA");

    private String valore;

    OrdinamentoPresenzeAnalyzerEnum(String valore) {

	this.valore = valore;
    }

    public String getValore() {

	return valore;
    }
}
