package it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

@Service
public class SoggettoPendenzaServiceImpl implements SoggettoPendenzaService {

    @Override
    public Anagrafe getSoggettoPendenza(SoggettiPendenzaEnum soggettoPendenza, Istanzeoneri istoneri) {

	Anagrafe richiedente = new Anagrafe();
	switch (soggettoPendenza) {
	case AZIENDA:
	    if (istoneri.getIstanza().getTitolarelegale() != null) {
		richiedente = istoneri.getIstanza().getTitolarelegale();
	    } else {
		richiedente = istoneri.getIstanza().getRichiedente();
	    }
	    break;
	case RICHIEDENTE:
	    richiedente = istoneri.getIstanza().getRichiedente();
	    break;
	default:
	    richiedente = istoneri.getIstanza().getRichiedente();
	    break;
	}
	return richiedente;
    }

    @Override
    public boolean isAzienda(SoggettiPendenzaEnum isAzienda) {

	switch (isAzienda) {
	case AZIENDA:
	    return true;
	case RICHIEDENTE:
	    return false;
	default:
	    return false;
	}
    }
}
