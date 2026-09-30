package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;

import java.util.Date;

public class OperazioniEsterneCommand extends BaseCommand {

    private RiferimentiPraticaSTCRestBean entity;
    private String numero_protocollo;
    private Date data_protocollo;

    public OperazioniEsterneCommand() {

	this.entity = new RiferimentiPraticaSTCRestBean();
    }

    public RiferimentiPraticaSTCRestBean getEntity() {

	return entity;
    }

    public void setEntity(RiferimentiPraticaSTCRestBean entity) {

	this.entity = entity;
    }

    public String getNumero_protocollo() {

	return numero_protocollo;
    }

    public void setNumero_protocollo(String numero_protocollo) {

	this.numero_protocollo = numero_protocollo;
    }

    public Date getData_protocollo() {

	return data_protocollo;
    }

    public void setData_protocollo(Date data_protocollo) {

	this.data_protocollo = data_protocollo;
    }
}
