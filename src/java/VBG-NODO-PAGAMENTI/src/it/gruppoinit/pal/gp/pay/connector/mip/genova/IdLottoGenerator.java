package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import org.apache.commons.lang.StringUtils;

public class IdLottoGenerator {

    private String idNexi;
    private String centroDiCosto;

    public IdLottoGenerator(String idNexi, String centroDiCosto) {

	super();
	this.idNexi = idNexi;
	this.centroDiCosto = centroDiCosto;
    }

    @Override
    public String toString() {

	int len = centroDiCosto.length();
	int lenNexi = idNexi.length();
	int diff = 20 - (len + lenNexi);
	if (diff > 0) {
	    idNexi = StringUtils.leftPad(idNexi, diff + lenNexi, '0');
	} else {
	    idNexi = idNexi.substring(-1 * diff);
	}
	return centroDiCosto + idNexi;
    }
}
