package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.LogSistema;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface LogSistemaService extends BaseService<LogSistema, PkId> {

    public static enum CODICI_EVENTO {
	ACCESSO, ELIMIN_PRATICA_PRE, ELIMIN_PRATICA_TIT, LOGOUT, MOD_INTERM_PRATICA, NON_DEFINITO, MOD_DATI_PRATICA
    };

    public void registraLogSistemaInNewTransaction(LogSistema entity);
}
