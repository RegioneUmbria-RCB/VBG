package it.gruppoinit.pal.gp.core.features.sistema;


public enum TecnologiaPaginaEnum {
    MICROSOFT("MICROSOFT"),
    JAVA("JAVA");
    
    
    private String value;
    
    TecnologiaPaginaEnum(String value) {

	this.value = value;
    }
    
    @Override
    public String toString() {

	return String.valueOf(this.value);
    }
    
    public static TecnologiaPaginaEnum fromValue(String text) {
	for (TecnologiaPaginaEnum b : TecnologiaPaginaEnum.values()) {
	    if (b.value.equalsIgnoreCase(text)) {
		return b;
	    }
	}
	return null;
    }
}
