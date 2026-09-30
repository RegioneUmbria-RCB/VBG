package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;

import java.util.List;

public class MovAllegatiDaMettereAllaFirmaHelepr {

    private Movimentiallegati movimentiallegati;
    private boolean isOggettoPresente;
    private List<DocumentiDaFirmare> documentiDaFirmares;

    public Movimentiallegati getMovimentiallegati() {

	return movimentiallegati;
    }

    public void setMovimentiallegati(Movimentiallegati movimentiallegati) {

	this.movimentiallegati = movimentiallegati;
    }

    public boolean getOggettoPresente() {

	return isOggettoPresente;
    }

    public void setOggettoPresente(boolean isOggettoPresente) {

	this.isOggettoPresente = isOggettoPresente;
    }

    public List<DocumentiDaFirmare> getDocumentiDaFirmares() {

	return documentiDaFirmares;
    }

    public void setDocumentiDaFirmares(List<DocumentiDaFirmare> documentiDaFirmares) {

	this.documentiDaFirmares = documentiDaFirmares;
    }
}
