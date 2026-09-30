package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota;

public enum ProviderFirmaEnum {

    ARUBA("Aruba"),
    INFOCERT("Infocert"),
    UANATACA("Uanataca");

    private final String value;

    ProviderFirmaEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }

    public static ProviderFirmaEnum fromValue(String v) {

	for (ProviderFirmaEnum c : ProviderFirmaEnum.values()) {
	    if (c.value.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
