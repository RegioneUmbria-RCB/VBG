package it.gruppoinit.pal.gp.core.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.validator.NotNull;

/*
 * BEAN che non appartiene al dominio è usato solo per passare i parametri anno mercatiUso Per calcolare i giorni di un
 * mercato dati questi due parametri
 */
public class CalendariomercatoParametri {

    private Integer anno;
    private MercatiUso mercatiUso;
    private List<Giorno> giorniMercato;
    private List<Giorno> giorniFestivi;
    private List<Giornisettimana> giorniSettimana;
    private Integer step;

    public CalendariomercatoParametri() {

	this.anno = (new GregorianCalendar()).get(Calendar.YEAR);
	this.mercatiUso = new MercatiUso();
    }

    public Integer getAnno() {

	return anno;
    }

    public List<Giorno> getGiorniMercato() {

	return giorniMercato;
    }

    public void setGiorniMercato(List<Giorno> giorniMercato) {

	this.giorniMercato = giorniMercato;
    }

    public List<Giorno> getGiorniFestivi() {

	return giorniFestivi;
    }

    public void setGiorniFestivi(List<Giorno> giorniFestivi) {

	this.giorniFestivi = giorniFestivi;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    @NotNull
    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public Integer getStep() {

	return step;
    }

    public void setStep(Integer step) {

	this.step = step;
    }

    public void setGiorniSettimana(List<Giornisettimana> giorniSettimana) {

	this.giorniSettimana = giorniSettimana;
    }

    public List<Giornisettimana> getGiorniSettimana() {

	return giorniSettimana;
    }
}
