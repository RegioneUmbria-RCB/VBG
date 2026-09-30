package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.ArrayList;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;

public class ElencoSoggettiCollegati extends SegnapostoTestualeBaseConValoreMultiplo {

    @Override
    public String getNome() {

	return "ELENCOSOGGETTICOLLEGATI";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

    }

    @Override
    protected String[] onGetValori(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	ArrayList<String> listaSoggColl = new ArrayList<String>();
	for (Istanzerichiedenti istrich : data.getIstanza().getIstanzerichiedentis()) {
	    listaSoggColl.add(this.getTransientRichiedenteQualitaAzienda(istrich));
	}
	return listaSoggColl.toArray(new String[0]);
    }

    private String getTransientRichiedenteQualitaAzienda(Istanzerichiedenti istanzerichiedenti) {

	String descrizioneRichiedente = "";
	if (EntityUtils.getNestedProperty(istanzerichiedenti.getRichiedente(), "id.codice") != null) {
	    descrizioneRichiedente = StringUtils.defaultIfEmpty(istanzerichiedenti.getRichiedente().getNominativo(), "");
	    if (StringUtils.isNotBlank(istanzerichiedenti.getRichiedente().getNome())) {
		descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(istanzerichiedenti.getRichiedente().getNome());
	    }
	}
	if (EntityUtils.getNestedProperty(istanzerichiedenti.getTiposoggetto(), "id.codice") != null) {
	    if (BooleanUtils.isTrue(istanzerichiedenti.getTiposoggetto().getFlgSpecificadescrizione())) {
		if (StringUtils.isNotBlank(istanzerichiedenti.getDescrsoggetto())) {
		    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(istanzerichiedenti.getDescrsoggetto());
		}
	    } else {
		descrizioneRichiedente = descrizioneRichiedente.concat(" ")
			.concat(StringUtils.defaultIfEmpty(istanzerichiedenti.getTiposoggetto().getTiposoggetto(), ""));
	    }
	}
	if (EntityUtils.getNestedProperty(istanzerichiedenti.getAnagrafeCollegata(), "id.codice") != null
		&& StringUtils.isNotBlank(istanzerichiedenti.getAnagrafeCollegata().getNominativo())) {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(istanzerichiedenti.getAnagrafeCollegata().getNominativo());
	}
	return descrizioneRichiedente;
    }
}
