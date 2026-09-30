package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class VerticalizzazioneComportamentiMercatiServiceImpl implements IVerticalizzazioneComportamentiMercatiService {

    @Autowired
    private VerticalizzazioniService service;
    @Autowired
    private TipidocumentoService tipidocumentoService;
    @Autowired
    private OggettiService oggettiService;

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(nomeVerticalizzazione);
    }

    @Override
    public boolean autorizUniqueNumeroComune() {

	return this.service.getBoolean(nomeVerticalizzazione, parAutorizUniqueNumeroComune, "S");
    }

    @Override
    public boolean bloccaAccessoMercNelFuturo() {

	return this.service.getBoolean(nomeVerticalizzazione, parBloccaAccessoMercNelFuturo, "S", true);
    }

    @Override
    public String codiceIstatBattitori() {

	return this.service.getString(nomeVerticalizzazione, parCodiceIstatBattitori);
    }

    @Override
    public Date dataPosDebConcessionari() {

	Date defaultValue;
	try {
	    defaultValue = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN).parse("31/12/9999");
	} catch (ParseException e) {
	    throw new RuntimeException(e);
	}
	return this.service.getDate(nomeVerticalizzazione, parDataPosDebConcessionari, defaultValue);
    }

    @Override
    public boolean gestisciProprietario() {

	return this.service.getBoolean(nomeVerticalizzazione, parGestisciProprietario, "S", true);
    }

    @Override
    public boolean insConcessionariPresenti() {

	Verticalizzazioniparametri insConcessionari = this.service.getVerticalizzazioniparametri(nomeVerticalizzazione, parInsConcessionariPresenti);
	return (insConcessionari != null && StringUtils.isNotBlank(insConcessionari.getValore())
		&& (insConcessionari.getValore().equalsIgnoreCase("S") || insConcessionari.getValore().equalsIgnoreCase("1")));
    }

    @Override
    public boolean nascondiBottoneConcPres() {

	return this.service.getBoolean(nomeVerticalizzazione, parNascondiBottoneConcPres, "S");
    }

    @Override
    public String restCercaComuneListEsclusi() {

	return this.service.getString(nomeVerticalizzazione, parRestCercaComuneListEsclusi);
    }

    @Override
    public boolean restVerificaCFPIva() {

	Verticalizzazioniparametri verificaCFPIva = this.service.getVerticalizzazioniparametri(nomeVerticalizzazione, parRestVerificaCFPIva);
	return (verificaCFPIva != null && StringUtils.isNotBlank(verificaCFPIva.getValore())
		&& (verificaCFPIva.getValore().equalsIgnoreCase("S") || verificaCFPIva.getValore().equalsIgnoreCase("1")));
    }

    @Override
    public String scCodiceIstNuovSpuntista() {

	return this.service.getString(nomeVerticalizzazione, parScCodiceIstNuovSpuntista);
    }

    @Override
    public boolean servGradAddNomeGiorno() {

	return this.service.getBoolean(nomeVerticalizzazione, parServGradAddNomeGiorno, "S");
    }

    @Override
    public String urlAppAmbulanteWeb() {

	return this.service.getString(nomeVerticalizzazione, parUrlAppAmbulanteWeb);
    }

    @Override
    public String urlAppSpuntaDigitale() {

	return this.service.getString(nomeVerticalizzazione, parUrlAppSpuntaDigitale);
    }

    @Override
    public String settoriNonSalvaMerceologie() {

	return this.service.getString(nomeVerticalizzazione, parSettoriNonSalvaMerceologie);
    }

    @Override
    public String criteriOrdinamentoGraduatoriePredefinito() {

	return this.service.getString(nomeVerticalizzazione, parQueryOrdinamentoGraduatorieDefault);
    }

    @Override
    public String criteriOrdinamentoGraduatorieSeAttivaGradSpuntisti() {

	return this.service.getString(nomeVerticalizzazione, parQueryOrdinamentoGraduatorieSeCFGSpuntisti);
    }

    @Override
    public boolean verificaChiusuraGiornatePrecedenti() {

	return this.service.getBoolean(nomeVerticalizzazione, parChiusuraGiornateCheckGGPrecedenti, "S", Boolean.TRUE);
    }

    @Override
    public boolean bloccaChiusuraGiornataAlCheckPosteggiNonOccupati() {

	return this.service.getBoolean(nomeVerticalizzazione, parAppVigiliBloccaChiusuraGiornataAlCheckPosteggiNonOccupati, "S", false);
    }

    @Override
    public String messaggioGiornataAlCheckPosteggiNonOccupati() {

	return this.service.getString(nomeVerticalizzazione, parAppVigiliMessaggioChiusuraGiornataAlCheckPosteggiNonOccupati,
		MESSAGGIO_CHIUSURAGIORNATA_AL_CHECK_POSTEGGI_NON_OCCUPATI);
    }

    @Override
    public String messaggioChiusuraGiornataMercato() {

	return this.service.getString(nomeVerticalizzazione, parAppVigiliMessaggioChiusuraGiornataMercato,
		MESSAGGIO_CHIUSURAGIORNATAMERCATO_PREDEFINITA);
    }

    @Override
    public boolean visualizzaTerminaAppello() {

	return this.service.getBoolean(nomeVerticalizzazione, parAppVigiliVisualizzaTerminaAppello, "S", true);
    }

    @Override
    public Integer codTipodocStampa() {

	Integer returnValue = this.service.getInteger(nomeVerticalizzazione, PAR_COD_TIPODOC_STAMPA);
	if (returnValue != null) {
	    Tipidocumento t = tipidocumentoService.findById(new PkId(returnValue));
	    if (t == null) {
		throw new InvalidConfigurationException("Il valore [" + returnValue + "] configurato nelle verticalizzazioni " +
							nomeVerticalizzazione + "." + PAR_COD_TIPODOC_STAMPA +
							" non è collegato a nessun TIPODOCUMENTO. La configurazione non è corretta.");
	    }
	}
	return returnValue;
    }

    @Override
    public Oggetti tipoDocStampaAutXsl() {

	Integer cod = codTipodocStampa();
	if (cod != null) {
	    Tipidocumento t = tipidocumentoService.findById(new PkId(cod));
	    if (t != null && t.getOggettoXsl() != null && t.getOggettoXsl().getId() != null && t.getOggettoXsl().getId().getCodice() != null) {
		return oggettiService.findById(new PkId(t.getOggettoXsl().getId().getCodice()));
	    }
	}
	return null;
    }

    @Override
    public Date dataInizioRicercaPagamentiAppAmbulanti() {

	Integer gg = this.service.getInteger(nomeVerticalizzazione, parAppApmbulantiGiorniFiltroRicercaPagamenti, 73000);
	return Utilities.addAndremoveDays(Calendar.getInstance().getTime(), gg, false);
    }

    public static void main(String[] args) {

	System.out.println(Utilities.formatDate(Calendar.getInstance().getTime(), true));
	System.out.println(Utilities.formatDate(Utilities.addAndremoveDays(Calendar.getInstance().getTime(), 180, false), true));
	System.out.println(Utilities.formatDate(Calendar.getInstance().getTime(), true));
	System.out.println(Utilities.formatDate(Utilities.addAndremoveDays(Calendar.getInstance().getTime(), 73000, false), true));
    }

    @Override
    public boolean isAttivaGiornateNulle() {

	return this.service.getBoolean(nomeVerticalizzazione, PAR_ATTIVA_GIORNATE_NULLE, "S", false);
    }

    @Override
    public String registroAutPonte() {

	return this.service.getString(nomeVerticalizzazione, REGISTRO_AUT_PONTE);
    }

    @Override
    public boolean isNascondiInserimantoSpuntista() {

	return this.service.getBoolean(nomeVerticalizzazione, PAR_APP_VIGILI_NASCONDI_INS_SPUNT, "S", false);
    }
}
