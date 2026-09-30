package it.gruppoinit.pdd.ri.service;

import java.util.List;

import it.gruppoinit.domain.helper.PraticaXmlHelper;
import it.gruppoinit.pdd.utils.AllegatoHelper;
import it.gruppoinit.pdd.utils.ConfigurazioneHelper;
import it.gruppoinit.pdd.utils.IstanzaHelper;
import it.gruppoinit.pdd.utils.TIPO_PRATICA;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;

public interface PraticaXmlHelperService<T> {

    PraticaXmlHelper<T> getPraticaXmlHelper(List<AllegatoHelper> alls, IstanzaHelper ihelper, ConfigurazioneHelper confHelper,
	    boolean effettuaValidazione, TIPO_PRATICA tipoPRATICA, GetDbConnectionInfoResponse dbconninfo);

    public Class<T> getEntityClass();
}
