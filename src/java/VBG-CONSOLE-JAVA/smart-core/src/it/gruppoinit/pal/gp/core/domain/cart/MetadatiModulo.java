/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe che memorizza metadati relativi al singolo modulo dell'accettatore STAR
 * 
 * @author francol
 *
 */
public class MetadatiModulo {

    private static final Logger log = LoggerFactory.getLogger(MetadatiModulo.class);
    private Map<String, SetUniqueList> metadatiQuadri = new HashMap<String, SetUniqueList>();

    public void registraMetadatiCampo(String quadro, ItemType item, IndiceIdSemantico indiceCampo, Boolean isAttivo) {

	SetUniqueList fields = this.metadatiQuadri.get(quadro);
	if (fields == null) {
	    fields = SetUniqueList.decorate(new ArrayList<MetadatiCampo>());
	    this.metadatiQuadri.put(quadro, fields);
	}
	String idItem = null;
	String idSemantico = null;
	EspressioneType ca = null;
	if (item.getCampo() != null) {
	    idItem = item.getCampo().getId();
	    idSemantico = item.getCampo().getIdSemantico();
	    ca = item.getCampo().getAttivo();
	} else if (item.getFile() != null) {
	    idItem = item.getFile().getId();
	    idSemantico = item.getFile().getIdSemantico();
	    ca = item.getFile().getAttivo();
	} else {
	    return;
	}
	MetadatiCampo mc = new MetadatiCampo();
	mc.setAttivo(isAttivo);
	if (isAttivo == null) {
	    mc.setCondizioneAttivo(ca);
	}
	mc.setIdCampo(idItem);
	mc.setIdSemantico(idSemantico);
	if (indiceCampo == null) {
	    indiceCampo = new IndiceIdSemantico();
	}
	mc.setIndice(indiceCampo);
	int indexOfMeta = fields.indexOf(mc);
	if (indexOfMeta < 0) {
	    fields.add(mc);
	} else {
	    fields.set(indexOfMeta, mc);
	}
    }

    public Boolean verificaStatoCampoAttivo(String quadro, ItemType item, IndiceIdSemantico indiceCampo, DatiDomandaCart datiDomanda) {

	Boolean retVal = null;
	if (item != null) {
	    String idItem = null;
	    String idSemantico = null;
	    EspressioneType condizAtt = null;
	    if (item.getCampo() != null) {
		idItem = item.getCampo().getId();
		idSemantico = item.getCampo().getIdSemantico();
		condizAtt = item.getCampo().getAttivo();
	    } else if (item.getFile() != null) {
		idItem = item.getFile().getId();
		idSemantico = item.getFile().getIdSemantico();
		condizAtt = item.getFile().getAttivo();
	    }
	    if (StringUtils.isNotBlank(idItem)) {
		//retVal = Boolean.TRUE;
		SetUniqueList fieldsMeta = this.metadatiQuadri.get(quadro);
		MetadatiCampo search4Me = new MetadatiCampo();
		search4Me.setIdCampo(idItem);
		search4Me.setIndice(indiceCampo);
		search4Me.setIdSemantico(idSemantico);
		int idx = -1;
		if (fieldsMeta != null) {
		    idx = fieldsMeta.indexOf(search4Me);
		    if (idx > -1) {
			search4Me = (MetadatiCampo) fieldsMeta.get(idx);
			if (search4Me.isAttivo() != null) {
			    retVal = search4Me.isAttivo();
			}
		    }
		}
		if (retVal == null) {
		    search4Me.setCondizioneAttivo(condizAtt);
		    retVal = search4Me.elaboraStatoCampoAttivo(datiDomanda);
		}
		if (idx < 0) {
		    log.info("verificaStatoCampoAttivo - nessun metadato presente per il campo con id: {} e idSemantico: {}.", new Object[] { idItem,
			    idSemantico });
		}
	    } else {
		log.info(
			"verificaStatoCampoAttivo - il campo da verificare associato all'id semantico {} ha id nullo, impossibile recuperare i dati per il campo che sarà comunque considerato attivo.",
			idSemantico);
	    }
	}
	return retVal;
    }

    public List<MetadatiCampo> getStatoCampiPerIdSemanticoEIndice(String idSemantico, IndiceIdSemantico indice, String soloQuadroId) {

	List<MetadatiCampo> retMeta = new ArrayList<MetadatiCampo>();
	Set<String> idQuadri = this.metadatiQuadri.keySet();
	Iterator<String> idQuadriIterator = idQuadri.iterator();
	String idQuadro = null;
	while (idQuadriIterator.hasNext()) {
	    idQuadro = idQuadriIterator.next();
	    if (StringUtils.isNotBlank(soloQuadroId)) {
		if (!soloQuadroId.equals(idQuadro)) {
		    continue;
		}
	    }
	    SetUniqueList metaCampiQuadro = this.metadatiQuadri.get(idQuadro);
	    if (metaCampiQuadro != null) {
		for (Object metadatiCampoObj : metaCampiQuadro) {
		    MetadatiCampo metadatiCampo = (MetadatiCampo) metadatiCampoObj;
		    boolean getIt = idSemantico.equals(metadatiCampo.getIdSemantico());
		    if (indice != null && indice.contaLivelli() > 0) {
			getIt = getIt && indice.equals(metadatiCampo.getIndice());
		    }
		    if (getIt) {
			retMeta.add(metadatiCampo);
		    }
		}
	    }
	}
	return retMeta;
    }
}
