package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive.IMercatipresenzeDMassiveDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SostituzioneSegnapostoManifestazioniServiceImpl implements ISostituzioneSegnapostoManifestazioniService {

    private static final Logger log = LoggerFactory.getLogger(SostituzioneSegnapostoManifestazioniServiceImpl.class);
    @Autowired
    private IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;

    @Override
    public OggettoComunicazioneManifestazioni effettuaSostituzioniByMassiveDettaglio(Integer idMailTipo, MassiveDettaglio massiveDettaglio) {

	if (idMailTipo == null || massiveDettaglio == null || massiveDettaglio.getId() == null || massiveDettaglio.getId().getCodice() == null) {
	    log.error("Integer idMailTipo {}, MassiveDettaglio massiveDettaglio {}", idMailTipo, massiveDettaglio);
	    throw new IllegalArgumentException("i parametri Integer idMailTipo, MassiveDettaglio massiveDettaglio non possono essere nulli");
	}
	Mailtipo mailtipo = this.mailtipoService.findById(new PkId(idMailTipo));
	if (mailtipo == null) {
	    log.error("Mail tipo con codice {} è nulla", idMailTipo);
	    throw new IllegalArgumentException("Mail tipo con codice " + idMailTipo + " è nulla");
	}
	OggettoComunicazioneManifestazioni o = OggettoComunicazioneManifestazioni.fromMailTipo(mailtipo);
	sostituisciAnagrafe(massiveDettaglio, o);
	sostituisciSegnapostoDettaglio(massiveDettaglio.getId().getCodice(), o);
	return sostituisciValoriNonSostituitiConStringaVuota(o);
    }

    private OggettoComunicazioneManifestazioni sostituisciValoriNonSostituitiConStringaVuota(OggettoComunicazioneManifestazioni o) {

	SegnapostoMassiveComunicazioniManifestazione[] values = SegnapostoMassiveComunicazioniManifestazione.values();
	for (SegnapostoMassiveComunicazioniManifestazione s : values) {
	    sostituisciStringa("", o, s);
	}
	return o;
    }

    private void sostituisciSegnapostoDettaglio(Integer idMassivaDettaglio, OggettoComunicazioneManifestazioni o) {

	MercatipresenzeDMassive m = mercatipresenzeDMassiveDAO.findByDettaglio(idMassivaDettaglio);
	MercatipresenzeD presenza = m.getMercatipresenzeD();
	if (presenza != null) {
	    Integer idPosteggio = null;
	    Integer idGiornata = presenza.getMercatiPresenzeT().getId().getCodice();
	    Integer idAutorizzazione = null;
	    if (presenza.getPosteggio() != null) {
		idPosteggio = presenza.getPosteggio().getId().getCodice();
		sostituisciStringa(presenza.getPosteggio().getCodiceposteggio(), o, SegnapostoMassiveComunicazioniManifestazione.POSTEGGIO);
	    }
	    if (presenza.getMercatiPresenzeT().getMercato() != null) {
		sostituisciStringa(presenza.getMercatiPresenzeT().getMercato().getDescrizione(), o,
			SegnapostoMassiveComunicazioniManifestazione.MERCATO);
	    }
	    if (presenza.getMercatiPresenzeT().getDataRegistrazione() != null) {
		String dataRegistrazione = Utilities.formatDate(presenza.getMercatiPresenzeT().getDataRegistrazione(), false);
		sostituisciStringa(dataRegistrazione, o, SegnapostoMassiveComunicazioniManifestazione.DATA_GIORNATA);
	    }
	    if (presenza.getAutorizzazioni() != null) {
		sostituisciStringa(presenza.getAutorizzazioni().getAutoriznumero(), o,
			SegnapostoMassiveComunicazioniManifestazione.AUTORIZZAZIONE_NUMERO);
		sostituisciStringa(Utilities.formatDate(presenza.getAutorizzazioni().getAutorizdata(), false), o,
			SegnapostoMassiveComunicazioniManifestazione.AUTORIZZAZIONE_DATA);
		idAutorizzazione = presenza.getAutorizzazioni().getId().getCodice();
	    }
	    if (presenza.getDettPosizioneDebitoria() != null) {
		sostituisciStringa(presenza.getDettPosizioneDebitoria().getIuv(), o,
			SegnapostoMassiveComunicazioniManifestazione.IUV_POSIZIONE_DEBITORIA);
		BigDecimal importoIvato = presenza.getDettPosizioneDebitoria().getImportoIvato();
		if (importoIvato != null) {
		    sostituisciStringa(NumberFormat.getInstance().format(importoIvato), o,
			    SegnapostoMassiveComunicazioniManifestazione.IMPORTO_POSIZIONE_DEBITORIA);
		}
	    }
	    if (idPosteggio != null && idGiornata != null && idAutorizzazione != null) {
		Integer id = borsellinoMovimentiDAO.findIdNonStornatoByRiferimenti(idPosteggio, idGiornata, idAutorizzazione);
		if (id != null) {
		    BorsellinoMovimenti bm = borsellinoMovimentiDAO.getById(BorsellinoMovimenti.class, id);
		    if (bm != null && bm.getImporto() != null) {
			sostituisciStringa(NumberFormat.getInstance().format(bm.getImporto()), o,
				SegnapostoMassiveComunicazioniManifestazione.IMPORTO_SCALATO_ABBONAMENTO);
		    }
		}
	    }
	}
    }

    private void sostituisciStringa(String stringaDaSostituire, OggettoComunicazioneManifestazioni o,
	    SegnapostoMassiveComunicazioniManifestazione segnaposto) {

	o.setOggetto(StringUtils.defaultString(o.getOggetto()) //
		.replace(segnaposto.getValue(), StringUtils.defaultIfEmpty(stringaDaSostituire, "") //
			.trim()));
	o.setCorpo(StringUtils.defaultString(o.getCorpo()) //
		.replace(segnaposto.getValue(), StringUtils.defaultIfEmpty(stringaDaSostituire, "") //
			.trim()));
    }

    private void sostituisciAnagrafe(MassiveDettaglio massiveDettaglio, OggettoComunicazioneManifestazioni o) {

	String nominativo = "";
	String cf = "";
	if (massiveDettaglio.getDestinatari().getAnagrafe() != null) {
	    Anagrafe a = massiveDettaglio.getDestinatari().getAnagrafe();
	    if (a.getNominativo() != null) {
		nominativo = a.getNominativo() + " ";
	    }
	    if (a.getNome() != null) {
		nominativo = nominativo.concat(a.getNome());
	    }
	} else if (massiveDettaglio.getDestinatari().getAmministrazioni() != null) {
	    nominativo = massiveDettaglio.getDestinatari().getAmministrazioni().getAmministrazione();
	    cf = massiveDettaglio.getDestinatari().getAmministrazioni().getPartitaiva();
	} else if (massiveDettaglio.getDestinatari().getResponsabili() != null) {
	    nominativo = massiveDettaglio.getDestinatari().getResponsabili().getResponsabile();
	    cf = massiveDettaglio.getDestinatari().getResponsabili().getCodicefiscale();
	}
	sostituisciStringa(nominativo, o, SegnapostoMassiveComunicazioniManifestazione.NOMINATIVO_ANAGRAFE_MASSIVA);
	sostituisciStringa(cf, o, SegnapostoMassiveComunicazioniManifestazione.CF_ANAGRAFE_MASSIVA);
    }
}
