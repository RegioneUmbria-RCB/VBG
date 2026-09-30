package it.gruppoinit.pal.gp.core.service.helper;

public enum PeriodicitaEnum {
    MENSILE(12), BIMESTRALE(6), TRIMESTRALE(4), QUADRIMESTRALE(3), SEMESTRALE(2), ANNUALE(1);

    PeriodicitaEnum(int n) {

	this.n = n;
    }

    private int n;

    public int getN() {

	return n;
    }

    public void setN(int n) {

	this.n = n;
    }
}
