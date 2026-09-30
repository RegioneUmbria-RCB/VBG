package it.gruppoinit.pal.gp.core.features.oneri;

public enum EnumTipologiaOnereType {

    ENTRATA,
    USCITA,
    INCASSATO,
    RIVERSATO;

    public static EnumTipologiaOnereType fromDettaglio(boolean isEntrata, boolean isPagato) {

	if (isEntrata && !isPagato) {
	    return EnumTipologiaOnereType.ENTRATA;
	}
	if (!isEntrata && !isPagato) {
	    return EnumTipologiaOnereType.USCITA;
	}
	if (isEntrata && isPagato) {
	    return EnumTipologiaOnereType.INCASSATO;
	}
	return EnumTipologiaOnereType.RIVERSATO;
    }
}
