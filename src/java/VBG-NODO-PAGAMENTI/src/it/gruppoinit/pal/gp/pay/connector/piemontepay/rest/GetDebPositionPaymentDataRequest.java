package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetDebPositionPaymentDataRequest extends GetDebtPositionStatusRequest {

    public GetDebPositionPaymentDataRequest(String cfEnte, String codiceVersamento, String iuv) {

	super(cfEnte, codiceVersamento, iuv);
    }
}
