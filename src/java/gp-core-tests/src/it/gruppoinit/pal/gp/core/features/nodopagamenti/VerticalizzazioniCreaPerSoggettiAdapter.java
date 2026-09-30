package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioniCreaPerSoggettiAdapter extends VerticalizzazioniAdapter implements VerticalizzazioniService {

    protected enum TIPORESTITUITO {
	NULL,
	VUOTO,
	S,
	N
    };

    private TIPORESTITUITO tipo = TIPORESTITUITO.VUOTO;

    public VerticalizzazioniCreaPerSoggettiAdapter(TIPORESTITUITO t) {

	this.tipo = t;
    }

    @Override
    public boolean isAttivaPerComune(String modulo, String codiceComune) {

	return true;
    }

    @Override
    public Verticalizzazioniparametri getVerticalizzazioniparametriPerComune(String modulo, String parametro, String codiceComune) {

	Verticalizzazioniparametri ret = new Verticalizzazioniparametri();
	if (parametro.equalsIgnoreCase(VerticalizzazioneNodoPagamentiServiceImpl.CREA_PER_SOGGETTI_COLLEGATI)) {
	    switch (tipo) {
	    case NULL:
		return null;
	    case VUOTO:
		return ret;
	    case S:
	    case N:
		ret.setValore(tipo.name());
	    default:
		break;
	    }
	    return ret;
	}
	ret.setValore("1"); // devo tornare intero altrimenti IDMODALITAPAGAMENTO SALTA
	return ret;
    }

    @Override
    public List<String> findValoreByModuloEParametro(String modulo, String parametro) {

	return null;
    }

    @Override
    public List<Verticalizzazioni> findAttivazioni(String modulo) {

	// TODO Auto-generated method stub
	return null;
    }
}
