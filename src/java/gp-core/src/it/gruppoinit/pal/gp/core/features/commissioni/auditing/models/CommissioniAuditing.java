package it.gruppoinit.pal.gp.core.features.commissioni.auditing.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommissioniAuditing {

    private Integer id;
    private String categoria;
    private String data;
    private String messaggio;

    public CommissioniAuditing(Integer id, String categoria, Date dataLog, String messaggio) {

	super();
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm.ss");
	this.id = id;
	this.categoria = categoria;
	this.data = dateFormat.format(dataLog);
	this.messaggio = messaggio;
    }

    public Integer getId() {

	return id;
    }

    public String getCategoria() {

	return categoria;
    }

    public String getData() {

	return data;
    }

    public String getMessaggio() {

	return messaggio;
    }
}
