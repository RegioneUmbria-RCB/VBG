package it.gruppoinit.pal.gp.core.features.segnaposto;

public class SegnapostoTemplateWrapperFactory {

    private SegnapostoTemplateWrapperFactory() {

    }

    public static ISegnapostoTemplateWrapper getWrapperDaTipoFile(ISegnapostoService segnapostoService, TipoFileEnum tipoFile) {

	if (tipoFile == null) {
	    throw new IllegalArgumentException("Impossibile risalire al tipo di wrapper da utilizzare senza passare la tipologia di file da gestire");
	}
	switch (tipoFile) {
	case RTF:
	    return new SegnapostoTemplateRTFWrapper(segnapostoService);
	case ODT:
	default:
	    return null;
	}
    }
}
