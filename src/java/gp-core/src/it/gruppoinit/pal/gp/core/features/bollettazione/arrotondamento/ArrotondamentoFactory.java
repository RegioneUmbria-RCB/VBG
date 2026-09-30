package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

public class ArrotondamentoFactory {

    public ArrotondamentoService get(ArrotondamentoEnum arrotondamentoEnum) {

	if (arrotondamentoEnum == null) {
	    return new NoArrotondamentoServiceImpl();
	}
	switch (arrotondamentoEnum) {
	case STANDARD:
	    return new ArrotondamentoDefaultServiceImpl();
	default:
	    return new NoArrotondamentoServiceImpl();
	}
    }
}
