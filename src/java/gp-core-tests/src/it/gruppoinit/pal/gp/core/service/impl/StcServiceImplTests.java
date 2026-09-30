package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.web.TipimovStcAltridatiValoreBean;

public class StcServiceImplTests {

    protected List<TipimovStcAltridatiValoreBean> altriDatiDiversiStcDefault() {

	List<TipimovStcAltridatiValoreBean> retVal = new ArrayList<TipimovStcAltridatiValoreBean>(0);
	TipimovStcAltridatiValoreBean parametro = new TipimovStcAltridatiValoreBean();
	TipimovStcAltridati chiave = new TipimovStcAltridati();
	String nomeCampo = "pippo";
	chiave.setNomeCampo(nomeCampo);
	chiave.setValoreDefaultCampo(nomeCampo);
	parametro.setChiave(chiave);
	parametro.setValore(nomeCampo);
	retVal.add(parametro);
	return retVal;
    }

    protected List<TipimovStcAltridatiValoreBean> altriDatiStcDefault() {

	List<TipimovStcAltridatiValoreBean> retVal = new ArrayList<TipimovStcAltridatiValoreBean>(0);
	TipimovStcAltridatiValoreBean parametro = new TipimovStcAltridatiValoreBean();
	TipimovStcAltridati chiave = new TipimovStcAltridati();
	String nomeCampo = "tipo_operazione";
	chiave.setNomeCampo(nomeCampo);
	chiave.setValoreDefaultCampo("notifica");
	parametro.setChiave(chiave);
	parametro.setValore("notifica");
	retVal.add(parametro);
	return retVal;
    }

    @Test()
    public void isNotificaCART_passando_una_lista_valorizzata_ritorna_true() {

	StcServiceImpl service = new StcServiceImpl();
	Boolean retval = service.isNotificaCART(this.altriDatiStcDefault());
	Assert.assertTrue(retval);
    }

    @Test()
    public void isNotificaCART_passando_una_lista_null_ritorna_false() {

	StcServiceImpl service = new StcServiceImpl();
	Boolean retval = service.isNotificaCART(null);
	Assert.assertFalse(retval);
    }

    @Test()
    public void isNotificaCART_passando_una_lista_conv_altri_valori_ritorna_false() {

	StcServiceImpl service = new StcServiceImpl();
	Boolean retval = service.isNotificaCART(this.altriDatiDiversiStcDefault());
	Assert.assertFalse(retval);
    }
}
