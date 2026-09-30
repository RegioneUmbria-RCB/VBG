package it.gruppoinit.pal.gp.core.dao.helper;

public enum OperazioniPentaho {
    RUN_JOB("runJob/?job="), STATUS_JOB("jobStatus/?name=${name_trasformazione}&id=${id_trasformazione}&xml=y"), STOP_JOB(
	    "stopJob/?name={name_trasformazione}&id={id_trasformazione}&xml=y"), REMOVE_JOB(
	    "removeJob?name={name_trasformazione}&id={id_trasformazione}");

    private String value;

    public String getValue() {

	return this.value;
    }

    private OperazioniPentaho(String value) {

	this.value = value;
    }
}
