package it.gruppoinit.pal.gp.pay.features.interfaccia;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.parameters.ParameterBase;

public class PCVParameter extends ParameterBase {

    public PCVParameter(String descrizione, String help) {

	super(descrizione, help);
    }

    private String chiave;

    @Override
    public String getNomeParametro() {

	return chiave;
    }

    public static PCVParameter fromPayRegcausaliParametri(PayRegcausaliParametri payReg) {

	// riprende dai vari connettori le configurazioni dei parametri
	String nomeParametro = payReg.getChiave();
	PCVParameter db = new PCVParameter(nomeParametro, "");
	db.chiave = payReg.getChiave();
	db.setValore(payReg.getValore());
	return db;
    }
}
