package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

public enum SegnapostoMassiveComunicazioniManifestazione {

    POSTEGGIO("[POSTEGGIO]"),
    MERCATO("[MERCATO]"),
    DATA_GIORNATA("[DATA_GIORNATA]"),
    IMPORTO_SCALATO_ABBONAMENTO("[IMPORTO_SCALATO_ABBONAMENTO]"),
    IMPORTO_POSIZIONE_DEBITORIA("[IMPORTO_POSIZIONE_DEBITORIA]"),
    IUV_POSIZIONE_DEBITORIA("[IUV_POSIZIONE_DEBITORIA]"),
    AUTORIZZAZIONE_NUMERO("[AUTORIZZAZIONE_NUMERO]"),
    AUTORIZZAZIONE_DATA("[AUTORIZZAZIONE_DATA]"),
    NOMINATIVO_ANAGRAFE_MASSIVA("[1]"),
    CF_ANAGRAFE_MASSIVA("[RIC_CF]");

    private final String value;

    SegnapostoMassiveComunicazioniManifestazione(String v) {

	value = v;
    }

    public String getValue() {

	return value;
    }
}
