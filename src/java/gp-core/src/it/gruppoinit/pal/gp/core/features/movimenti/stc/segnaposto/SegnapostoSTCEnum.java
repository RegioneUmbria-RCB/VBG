package it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto;

public enum SegnapostoSTCEnum {

    MAIL_AMMINISTRAZIONE("$MAIL_AMMINISTRAZIONE$"),
    PEC_AMMINISTRAZIONE("$PEC_AMMINISTRAZIONE$");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private SegnapostoSTCEnum(String valore) {

	this.valore = valore;
    }

    public static SegnapostoSTCEnum fromValue(String v) {

	for (SegnapostoSTCEnum c : SegnapostoSTCEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
