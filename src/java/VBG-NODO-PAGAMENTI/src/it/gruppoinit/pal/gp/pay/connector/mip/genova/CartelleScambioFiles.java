package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum CartelleScambioFiles {

    ELABORATI("Elaborati"), //
    SCARTATI("Scartati"), //
    REPORT("Report");

    private String name;

    private CartelleScambioFiles(String name) {

	this.name = name;
    }

    public String fileName() {

	return this.name;
    }
}