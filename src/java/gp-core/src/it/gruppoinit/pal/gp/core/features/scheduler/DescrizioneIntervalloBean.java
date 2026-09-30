package it.gruppoinit.pal.gp.core.features.scheduler;

public class DescrizioneIntervalloBean {

    private Integer intervallo;

    public static DescrizioneIntervalloBean fromIntervallo(Integer intervallo) {

	DescrizioneIntervalloBean bean = new DescrizioneIntervalloBean();
	bean.intervallo = intervallo;
	return bean;
    }

    @Override
    public String toString() {

	String descrizioneIntervallo = "";
	if (this.intervallo != null && this.intervallo != 0) {
	    Integer giorni = 0;
	    Integer ore = 0;
	    Integer minuti = 0;
	    Integer resto = this.intervallo;
	    giorni = resto / 1440;
	    resto = resto - (giorni * 1440);
	    ore = resto / 60;
	    resto = resto - (ore * 60);
	    minuti = resto;
	    descrizioneIntervallo = giorni + " GG " + ore + " HH " + minuti + " MI";
	}
	return descrizioneIntervallo;
    }
}
