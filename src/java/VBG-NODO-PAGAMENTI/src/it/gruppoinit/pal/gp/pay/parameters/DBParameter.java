package it.gruppoinit.pal.gp.pay.parameters;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;

public class DBParameter extends ParameterBase {

    public DBParameter(String descrizione, String help) {

	super(descrizione, help);
    }

    private String nomeParametro;

    @Override
    public String getNomeParametro() {

	return nomeParametro;
    }

    public static DBParameter fromPayRegcausaliParametri(PayRegcausaliParametri payReg) {

	// riprende dai vari connettori le configurazioni dei parametri
	String nomeParametro = payReg.getChiave();
	DBParameter db = new DBParameter(nomeParametro, "");
	db.nomeParametro = payReg.getChiave();
	db.setValore(payReg.getValore());
	return db;
    }
}
