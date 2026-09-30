/**
 * 
 */
package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciColonnaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciRigaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciTabellaHelper;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 * 
 */
public class Dyn2Utils {

    public static Map<String, String> valoriCampiDinamiciFromRequest(HttpServletRequest request) {

	Map<String, String> valoriCampiDinamici = new HashMap<String, String>();
	Enumeration<String> parameterNames = request.getParameterNames();
	while (parameterNames.hasMoreElements()) {
	    String paramName = (String) parameterNames.nextElement();
	    if (paramName.startsWith("FLD_")) {
		String[] parameterValues = request.getParameterValues(paramName);
		if (parameterValues != null) {
		    String paramValue = "";
		    if (parameterValues.length > 0) {
			if (parameterValues.length > 1) {
			    paramValue = StringUtils.join(parameterValues, ";");
			} else {
			    paramValue = parameterValues[0];
			}
		    }
		    valoriCampiDinamici.put(paramName, paramValue);
		}
	    }
	}
	return valoriCampiDinamici;
    }

    public static void popolaModelloDaMappaDatiRequest(ModellidinamiciHelper modHelper, Map<String, String> mappaDati) {

	ModellidinamiciTabellaHelper tabellaprincipale = modHelper.getTabella();
	popolaTabellaDaMappaDatiRequest(tabellaprincipale, mappaDati);
    }

    public static String[] decodeValoriForCampo(Dyn2Campi d2c, String value) {

	String[] result = new String[2];
	if (d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.Data.name())) {
	    if (StringUtils.isNotBlank(value)) {
		Calendar d = Utilities.getDate(value, WebConstants.DATE_FORMAT_PATTERN);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		result[0] = sdf.format(d.getTime());
		result[1] = value;
	    }
	} else {
	    result[0] = value;
	    result[1] = value;
	}
	return result;
    }

    private static void popolaTabellaDaMappaDatiRequest(ModellidinamiciTabellaHelper tabella, Map<String, String> mappaDati) {

	List<ModellidinamiciRigaHelper> righe = tabella.getRighe();
	if (righe != null) {
	    for (ModellidinamiciRigaHelper riga : righe) {
		List<ModellidinamiciColonnaHelper> colonne = riga.getColonne();
		if (colonne != null) {
		    for (ModellidinamiciColonnaHelper colonna : colonne) {
			if (colonna.getTabelle() != null) {
			    if (colonna.getTabelle().size() > 0) {
				ModellidinamiciTabellaHelper tabellaNested = colonna.getTabelle().get(0);
				int maxRighe = contaRigheBloccoDaDatiRequest(tabellaNested, mappaDati);
				colonna.getTabelle().clear();
				for (int i = 0; i < maxRighe; i++) {
				    boolean incrementIndex = i > 0;
				    ModellidinamiciTabellaHelper tabellaCopy = new ModellidinamiciTabellaHelper();
				    tabellaDTO(tabellaCopy, tabellaNested, incrementIndex);
				    popolaTabellaDaMappaDatiRequest(tabellaCopy, mappaDati);
				    colonna.getTabelle().add(tabellaCopy);
				    tabellaNested = tabellaCopy;
				}
			    }
			}
			if (colonna.getCampo() != null) {
			    //Dyn2Modellid d2md = colonna.getDyn2Modellid();
			    Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			    //d2c = dyn2CampiService.findById(d2c.getId());
			    if (d2c != null) {
				StringBuilder paramPrefix = new StringBuilder("FLD_").append(d2c.getId().getCodice()).append("_");
				paramPrefix.append(colonna.getCampo().getIndice()).append("_");
				paramPrefix.append(colonna.getCampo().getIndicemolteplicita());
				String[] valori = new String[2];
				if (d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.ListaSIGePro.name())
					|| d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.Ricerca.name())) {
				    valori[0] = mappaDati.get(paramPrefix.toString() + "_ID");
				    valori[1] = mappaDati.get(paramPrefix.toString() + "_DESC");
				} else {
				    valori = decodeValoriForCampo(d2c, mappaDati.get(paramPrefix.toString()));
				}
				colonna.getCampo().setValore(valori[0]);
				colonna.getCampo().setValoreDecodificato(valori[1]);
				/*
				List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza,
					d2c.getId().getCodice(), 0);
				if (datis.size() > 0) {
				    Istanzedyn2dati dato = datis.get(0);
				    colonna.getCampo().setValore(dato.getValore());
				    colonna.getCampo().setValoreDecodificato(dato.getValoredecodificato());
				    colonna.getCampo().setIndice(dato.getId().getIndice());
				    colonna.getCampo().setIndicemolteplicita(dato.getId().getIndiceMolteplicita());
				}
				if (datis.size() > 1) {
				    log.error("populateModellodinamicoForIstanza: Trovati più valori per il campo [" + d2c.getId() + "]:"
					    + d2c.getNomecampo());
				}
				*/
			    }
			}
		    }
		}
	    }
	}
    }

    public static ModellidinamiciTabellaHelper tabellaDTO(ModellidinamiciTabellaHelper result, ModellidinamiciTabellaHelper tabellaNested,
	    boolean aumentaIndiceMolteplicita) {

	List<ModellidinamiciRigaHelper> righeResult = new ArrayList<ModellidinamiciRigaHelper>();
	List<ModellidinamiciRigaHelper> righeCopy = tabellaNested.getRighe();
	for (ModellidinamiciRigaHelper rc : righeCopy) {
	    ModellidinamiciRigaHelper rigaRes = new ModellidinamiciRigaHelper(rc.getNumRiga());
	    List<ModellidinamiciColonnaHelper> colResult = new ArrayList<ModellidinamiciColonnaHelper>();
	    List<ModellidinamiciColonnaHelper> colCopy = rc.getColonne();
	    int idxMolteplicitaUltimo = 0;
	    boolean indiceCalcolato = false;
	    for (ModellidinamiciColonnaHelper colC : colCopy) {
		if (colC.getCampo() != null) {
		    if (colC.getCampo().getIndicemolteplicita() != null) {
			int idMoltCampo = colC.getCampo().getIndicemolteplicita().intValue();
			if (idxMolteplicitaUltimo < idMoltCampo) {
			    idxMolteplicitaUltimo = idMoltCampo;
			}
		    }
		}
	    }
	    for (ModellidinamiciColonnaHelper colC : colCopy) {
		ModellidinamiciColonnaHelper colRes = new ModellidinamiciColonnaHelper(colC.getNumColonna());
		Dyn2Modellid d2md = colC.getDyn2Modellid();
		colRes.setDyn2Modellid(d2md);
		if (colC.getCampo() != null) {
		    ModellidinamiciCampoHelper campoRes = new ModellidinamiciCampoHelper();
		    campoRes.setApplicationContext(colC.getCampo().getApplicationContext());
		    campoRes.setDyn2Campi(colC.getCampo().getDyn2Campi());
		    campoRes.setRegoleDipendenti(colC.getCampo().getRegoleDipendenti());
		    campoRes.setIdModello(colC.getCampo().getIdModello());
		    campoRes.setIndice(colC.getCampo().getIndice());
		    campoRes.setObbligatorio(colC.getCampo().isObbligatorio());
		    colRes.setCampo(campoRes);
		    if (aumentaIndiceMolteplicita) {
			if (indiceCalcolato == false) {
			    indiceCalcolato = true;
			    campoRes.setIndicemolteplicita(++idxMolteplicitaUltimo);
			} else {
			    campoRes.setIndicemolteplicita(idxMolteplicitaUltimo);
			}
			//			}
		    }
		}
		if (colC.getTabelle() != null) {
		    if (colC.getTabelle().size() > 0) {
			List<ModellidinamiciTabellaHelper> tabelleRes = new ArrayList<ModellidinamiciTabellaHelper>();
			List<ModellidinamiciTabellaHelper> tabelleCopy = colC.getTabelle();
			for (ModellidinamiciTabellaHelper modellidinamiciTabellaHelper : tabelleCopy) {
			    ModellidinamiciTabellaHelper tabellaDaAggiungere = new ModellidinamiciTabellaHelper();
			    tabellaDTO(tabellaDaAggiungere, modellidinamiciTabellaHelper, aumentaIndiceMolteplicita);
			    tabelleRes.add(tabellaDaAggiungere);
			}
			colC.setTabelle(tabelleRes);
		    }
		}
		colResult.add(colRes);
	    }
	    rigaRes.getColonne().addAll(colResult);
	    righeResult.add(rigaRes);
	}
	result.setRighe(righeResult);
	return result;
    }

    private static int contaRigheBloccoDaDatiRequest(ModellidinamiciTabellaHelper blocco, Map<String, String> datiRequest) {

	int val = 0;
	if (null != blocco) {
	    int maxIndex = 0;
	    for (ModellidinamiciRigaHelper riga : blocco.getRighe()) {
		for (ModellidinamiciColonnaHelper colonna : riga.getColonne()) {
		    if (colonna.getCampo() != null && colonna.getCampo().getDyn2Campi() != null) {
			Dyn2Campi d2c = colonna.getCampo().getDyn2Campi();
			StringBuilder sbFldName = new StringBuilder("FLD_").append(d2c.getId().getCodice()).append("_")
				.append(colonna.getCampo().getIndice()).append("_");
			while (datiRequest.containsKey(sbFldName.toString() + maxIndex)) {
			    maxIndex++;
			}
			if (val < maxIndex) {
			    val = maxIndex;
			}
			maxIndex = 0;
		    }
		}
	    }
	}
	return val;
    }
}
