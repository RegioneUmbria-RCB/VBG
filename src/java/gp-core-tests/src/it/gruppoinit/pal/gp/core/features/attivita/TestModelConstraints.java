package it.gruppoinit.pal.gp.core.features.attivita;

import java.util.Date;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaScollegata;

public class TestModelConstraints {

    @Test(expected = IllegalArgumentException.class)
    public void nuovoEventoIstanzaCollegataRilanciaIllegalArgumentExceptionSeParametriNulli() {

	new EventoIstanzaCollegata(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nuovoEventoIstanzaCollegataRilanciaIllegalArgumentExceptionSeIattivitaNulla() {

	new EventoIstanzaCollegata(null, new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nuovoEventoIstanzaScollegataRilanciaIllegalArgumentExceptionSeParametriNulli() {

	new EventoIstanzaScollegata(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nuovoEventoIstanzaScollegataRilanciaIllegalArgumentExceptionSeIattivitaNulla() {

	new EventoIstanzaScollegata(null, new Date());
    }
}
