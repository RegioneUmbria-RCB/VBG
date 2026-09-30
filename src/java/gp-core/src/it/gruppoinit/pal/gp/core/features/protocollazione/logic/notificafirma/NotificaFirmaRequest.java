package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

public class NotificaFirmaRequest {

    private String idDocumento;

    public String getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {

	this.idDocumento = idDocumento;
    }

    public static NotificaFirmaRequest fromIdProtocollo(String idProtocollo) {

	NotificaFirmaRequest request = new NotificaFirmaRequest();
	request.setIdDocumento(idProtocollo);
	return request;
    }
}
