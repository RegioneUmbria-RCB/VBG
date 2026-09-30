package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniCsiRestBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ResponsabileRestBean;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RestBeanHelper {

    public static void populateAnagraferestBean(AnagraferestBean anagraferestBean, Integer codiceAnagrafe, String nominativo, String nome,
	    String codicefiscale, String partitaiva, String indirizzo, String cap, String citta, String provincia, String numeroRea, Date datRea,
	    String telefono, Date dataInizioAttivita, String email, Date dataRegDitte) {

	if (anagraferestBean == null) {
	    anagraferestBean = new AnagraferestBean();
	}
	if (codiceAnagrafe != null) {
	    anagraferestBean.setId(codiceAnagrafe);
	}
	String ragSoc = StringUtils.defaultIfEmpty(nome, "") + " " + StringUtils.defaultIfEmpty(nominativo, "");
	if (StringUtils.isNotBlank(ragSoc)) {
	    ragSoc = ragSoc.trim();
	    anagraferestBean.setRagionesociale(ragSoc);
	}
	if (StringUtils.isNotBlank(codicefiscale)) {
	    anagraferestBean.setCodiceFiscale(codicefiscale);
	}
	List<String> indirizzi = new ArrayList<String>();
	if (StringUtils.isNotBlank(indirizzo)) {
	    indirizzi.add(indirizzo);
	}
	if (StringUtils.isNotBlank(cap)) {
	    indirizzi.add(cap);
	}
	if (StringUtils.isNotBlank(citta)) {
	    indirizzi.add(citta);
	}
	if (StringUtils.isNotBlank(provincia)) {
	    indirizzi.add("(" + provincia + ")");
	}
	if (!indirizzi.isEmpty()) {
	    anagraferestBean.setIndirizzo(StringUtils.join(indirizzi.toArray(), ", "));
	}
	if (StringUtils.isNotBlank(partitaiva)) {
	    anagraferestBean.setPartitaIva(partitaiva);
	}
	if (StringUtils.isNotBlank(numeroRea)) {
	    anagraferestBean.setRea(numeroRea);
	}
	if (datRea != null) {
	    anagraferestBean.setDataiscrrea(Utilities.formatDate(datRea, false));
	}
	if (StringUtils.isNotBlank(telefono)) {
	    anagraferestBean.setTelefono(telefono);
	}
	if (dataInizioAttivita != null) {
	    anagraferestBean.setDataInizioAttivita(Utilities.formatDate(dataInizioAttivita, false));
	}
	if (dataRegDitte != null) {
	    anagraferestBean.setDataRegDitte(Utilities.formatDate(dataRegDitte, false));
	}
	if (StringUtils.isNotBlank(email)) {
	    anagraferestBean.setEmail(email);
	}
    }

    public static void populateAnagrafeRestBeanDaDTO(AnagraferestBean anagraferestBean, AnagrafeDTO anagrafeDTO) {

	if (anagrafeDTO != null) {
	    populateAnagraferestBean(anagraferestBean, anagrafeDTO.getId().getCodice(), anagrafeDTO.getNominativo(), anagrafeDTO.getNome(),
		    anagrafeDTO.getCodicefiscale(), anagrafeDTO.getPartitaiva(), anagrafeDTO.getIndirizzo(), anagrafeDTO.getCap(),
		    anagrafeDTO.getCitta(), anagrafeDTO.getProvincia(), anagrafeDTO.getNumiscrrea(), anagrafeDTO.getDataiscrrea(),
		    anagrafeDTO.getTelefono(), anagrafeDTO.getDataInizioAttivita(), anagrafeDTO.getEmail(), null);
	}
    }

    public static void populateAnagrafeRestBeanDaAnagrafe(AnagraferestBean anagraferestBean, Anagrafe anagrafe) {

	if (EntityUtils.getNestedProperty(anagrafe, "id.codice") != null) {
	    populateAnagraferestBean(anagraferestBean, anagrafe.getId().getCodice(), anagrafe.getNominativo(), anagrafe.getNome(),
		    anagrafe.getCodicefiscale(), anagrafe.getPartitaiva(), anagrafe.getIndirizzo(), anagrafe.getCap(), anagrafe.getCitta(),
		    anagrafe.getProvincia(), anagrafe.getNumiscrrea(), anagrafe.getDataiscrrea(), anagrafe.getTelefono(),
		    anagrafe.getDataInizioAttivita(), anagrafe.getEmail(), anagrafe.getDataregditte());
	}
    }

    public static void populateAutorizzazioneRestBean(AutorizzazioneRestBean autorizzazioneRestBean, Autorizzazioni autorizzazioni) {

	if (autorizzazioneRestBean == null) {
	    autorizzazioneRestBean = new AutorizzazioneRestBean();
	}
	if (StringUtils.isNotBlank(autorizzazioni.getNote())) {
	    autorizzazioneRestBean.setAnnotazioniOperatore(autorizzazioni.getNote());
	}
	if (StringUtils.isNotBlank(autorizzazioni.getNoteSistema())) {
	    autorizzazioneRestBean.setAnnotazioniSistema(autorizzazioni.getNoteSistema());
	}
	if (autorizzazioni.getAutorizdata() != null) {
	    autorizzazioneRestBean.setData(Utilities.formatDate(autorizzazioni.getAutorizdata(), false));
	}
	if (autorizzazioni.getDataAnzianita() != null) {
	    autorizzazioneRestBean.setDataAnzianita((Utilities.formatDate(autorizzazioni.getDataAnzianita(), false)));
	}
	autorizzazioneRestBean.setId(autorizzazioni.getId().getCodice());
	autorizzazioneRestBean.setNumero(autorizzazioni.getAutoriznumero());
	if (autorizzazioni.getAutorigComune() != null && StringUtils.isNotBlank(autorizzazioni.getAutorigComune().getCodicecomune())) {
	    autorizzazioneRestBean.setRilasciataDa(autorizzazioni.getAutorigComune().getComune());
	} else if (autorizzazioni.getAutorizcomune() != null && StringUtils.isNotBlank(autorizzazioni.getAutorizcomune().getCodicecomune())) {
	    autorizzazioneRestBean.setRilasciataDa(autorizzazioni.getAutorizcomune().getComune());
	}
    }

    public static void populateAutorizzazioneCSIRestBean(AutorizzazioniCsiRestBean autCsi, AutorizzazioniRestHelper autHelper) {

	autCsi.setCausaleSospensione(autHelper.getCausaleSospensione());
	autCsi.setDataFineGerenza(autHelper.getDataFineGerenza());
	autCsi.setDataSospA(autHelper.getDataFineGerenza());
	autCsi.setDataSospDa(autHelper.getDataFineGerenza());
	String nomeGerente = StringUtils.isNotBlank(autHelper.getGerenteNome()) ? autHelper.getGerenteNominativo() + " " + autHelper.getGerenteNome()
		: autHelper.getGerenteNominativo();
	if (StringUtils.isNotBlank(autHelper.getGerenteCodicefiscale())) {
	    nomeGerente = nomeGerente + " CF: " + autHelper.getGerenteCodicefiscale();
	}
	autCsi.setEmailGerente(autHelper.getGerenteEmail());
	autCsi.setNomeGerente(nomeGerente);
	autCsi.setCodiceGerente(autHelper.getGerentecodice());
	autCsi.setStatoWarning(autHelper.getStatowarning());
	autCsi.setValidaSpunta(autHelper.getValidaSpunta() == null ? Boolean.TRUE : autHelper.getValidaSpunta().booleanValue());
	if (StringUtils.isNotBlank(autHelper.getStatoAutorizzazione())) {
	    autCsi.setStatoAutorizzazione(autHelper.getStatoAutorizzazione());
	}
    }

    public static void populateResponsabileRestBean(ResponsabileRestBean responsabileRestBean, Responsabili responsabile) {

	if (responsabileRestBean == null) {
	    responsabileRestBean = new ResponsabileRestBean();
	}
	responsabileRestBean.setResponsabile(responsabile.getResponsabile());
	responsabileRestBean.setUserid(responsabile.getUserid());
    }
}
