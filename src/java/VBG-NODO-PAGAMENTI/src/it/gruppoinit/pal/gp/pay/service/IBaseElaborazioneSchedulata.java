package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public interface IBaseElaborazioneSchedulata {

    public String getServiceName();

    public boolean isAttivo();

    public EsitoElaborazione elabora();

    public IPayConnector getIPayConnector();

    public String getQuartzScheduleExpression();
}
