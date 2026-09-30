package it.gruppoinit.pal.gp.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.helper.TipoAuthQRcodeEnum;

public class DocumentMergeUtils {

    private static final Pattern NON_ASCII_REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]");
    private static final String RTF_CRLF = "\\\\par ";
    private static final String RTF_TAB = "\\\\tab ";

    public static String getIndirizzoResidenza(Anagrafe anagrafe) {

	String val = "";
	if (anagrafe != null) {
	    val = StringUtils.defaultIfEmpty(anagrafe.getIndirizzo(), "");
	}
	return val;
    }

    public static String getCittaResidenza(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getCitta(), "");
	}
	return val;
    }

    //istanze.richiedente.cap
    public static String getCapResidenza(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getCap(), "");
	}
	return val;
    }

    public static String getProvinciaResidenza(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getProvincia(), "");
	}
	return val;
    }

    public static String getComuneResidenza(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && EntityUtils.getNestedProperty(richiedente.getComuneResidenza(), "codicecomune") != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getComuneResidenza().getComune(), "");
	}
	return val;
    }

    public static String getComuneNascita(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && EntityUtils.getNestedProperty(richiedente.getComuneNascita(), "codicecomune") != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getComuneNascita().getComune(), "");
	}
	return val;
    }

    public static String getCittadinanza(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && EntityUtils.getNestedProperty(richiedente.getCittadinanza(), "codice") != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getCittadinanza().getCittadinanza(), "");
	}
	return val;
    }

    public static String getSesso(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getSesso(), "");
	}
	return val;
    }

    public static String getEmail(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getEmail(), "");
	}
	return val;
    }

    public static String getPec(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getPec(), "");
	}
	return val;
    }

    public static String getPassword(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getPassword(), "");
	}
	return val;
    }

    public static String getCodiceAnagrafe(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = richiedente.getId().getCodice().toString();
	}
	return val;
    }

    public static String getFormaGiuridica(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && richiedente.getFormagiuridica() != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getFormagiuridica().getFormagiuridica(), "");
	}
	return val;
    }

    public static String getDataNascita(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && richiedente.getDatanascita() != null) {
	    val = dateFormat(richiedente.getDatanascita());
	}
	return val;
    }

    public static String getElencoprofessionale(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null && richiedente.getElenchiprofessionalibase() != null && richiedente.getElenchiprofessionalibase().getId() != null) {
	    val = richiedente.getElenchiprofessionalibase().getEpDescrizione();
	}
	return val;
    }

    public static String getPrElencoprofessionale(Anagrafe richiedente) {

	String val = "";
	if (richiedente != null) {
	    val = StringUtils.defaultIfEmpty(richiedente.getProvinciaelencopro(), "");
	}
	return val;
    }

    public static String getTipoCatasto(Istanzemappali im) {

	String val = "";
	if (im != null) {
	    if (im.getCatasto() != null && StringUtils.isNotBlank(im.getCatasto().getDescrizione())) {
		val = im.getCatasto().getDescrizione();
	    }
	}
	return val;
    }

    public static String getSezione(Istanzemappali im) {

	String val = "";
	if (im != null) {
	    if (im.getCatasto() != null && StringUtils.isNotBlank(im.getSezione())) {
		val = im.getSezione();
	    }
	}
	return val;
    }

    public static String getUnitaImmob(Istanzemappali im) {

	String val = "";
	if (im != null) {
	    if (im.getCatasto() != null && StringUtils.isNotBlank(im.getUnitaimmob())) {
		val = im.getUnitaimmob();
	    }
	}
	return val;
    }

    public static String getScala(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getScala(), "");
	}
	return val;
    }

    public static String getPiano(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getPiano(), "");
	}
	return val;
    }

    public static String getEsponeneteInterno(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getEsponenteinterno(), "");
	}
	return val;
    }

    public static String getNote(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getNote(), "");
	}
	return val;
    }

    public static String getinterno(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getInterno(), "");
	}
	return val;
    }

    public static String getFabbricato(Istanzestradario is) {

	String val = "";
	if (is != null) {
	    val = StringUtils.defaultIfEmpty(is.getFabbricato(), "");
	}
	return val;
    }

    public static String getAree2(Istanze ist) {

	String val = "";
	if (ist != null && EntityUtils.getNestedProperty(ist.getAree2(), "id.codice") != null) {
	    val = ist.getAree2().getDenominazione();
	}
	return val;
    }

    public static String getCodiceIstanzaPeople(Istanze ist) {

	String val = "";
	if (ist != null) {
	    val = StringUtils.defaultIfEmpty(ist.getCodicepraticatel(), "");
	}
	return val;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// MERCATI ///////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getNumeroConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    buffer.append(StringUtils.defaultIfEmpty(autorizzazioni.getAutoriznumero(), ""));
		    buffer.append(RTF_CRLF);
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getDataConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni) && autorizzazioni.getAutorizdata() != null) {
		    buffer.append(dateFormat(autorizzazioni.getAutorizdata()));
		    buffer.append(RTF_CRLF);
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getTitolare(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    buffer.append(autorizzazioni.getAnagrafe().getNominativo());
		    if (StringUtils.isNotBlank(autorizzazioni.getAnagrafe().getNome())) {
			buffer.append(" ").append(autorizzazioni.getAnagrafe().getNome());
		    }
		    buffer.append(RTF_CRLF);
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getTipoConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (autorizzazioniConcessioni.getConcessionitipi() != null
				&& StringUtils.isNotBlank(autorizzazioniConcessioni.getConcessionitipi().getTipoconcessione())) {
			    String tipoConcessione = autorizzazioniConcessioni.getConcessionitipi().getDescrizione();
			    buffer.append(tipoConcessione);
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getDataScadenzaConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni) && autorizzazioni.getDatascadenza() != null) {
		    buffer.append(dateFormat(autorizzazioni.getDatascadenza()));
		    buffer.append(RTF_CRLF);
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getStagionaleDaConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (StringUtils.isNotBlank(autorizzazioniConcessioni.getStagionaleda())) {
			    String stgda = autorizzazioniConcessioni.getStagionaleda();
			    String da = stgda.substring(0, 2) + "/" + stgda.substring(2, 4);
			    buffer.append(da);
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getStagionaleAConcessione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (StringUtils.isNotBlank(autorizzazioniConcessioni.getStagionalea())) {
			    String stga = autorizzazioniConcessioni.getStagionalea();
			    String a = stga.substring(0, 2) + "/" + stga.substring(2, 4);
			    buffer.append(a);
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getCausaleConcessioneAcqu(Set<Autorizzazioni> auts) {

	//
	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    if (EntityUtils.getNestedProperty(autorizzazioni.getConcessionicausaliByFkAutConccausAcq(), "id.codice") != null) {
			Concessionicausali concCausaliAcq = autorizzazioni.getConcessionicausaliByFkAutConccausAcq();
			buffer.append(concCausaliAcq.getDescrizione());
			buffer.append(RTF_CRLF);
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getMercato(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercati(), "id.codice") != null) {
			    buffer.append(autorizzazioniConcessioni.getMercati().getDescrizione());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getGiornoMercato(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiUso(), "id.codice") != null) {
			    buffer.append(autorizzazioniConcessioni.getMercatiUso().getDescrizione());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getNumPosteggio(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD(), "id.codice") != null) {
			    buffer.append(autorizzazioniConcessioni.getMercatiD().getCodiceposteggio());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getMqPosteggio(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD(), "id.codice") != null
				&& autorizzazioniConcessioni.getMercatiD().getSuperficie() != null) {
			    buffer.append(autorizzazioniConcessioni.getMercatiD().getSuperficie());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getPosteggioVia(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD(), "id.codice") != null
				&& EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD().getStradario(), "id.codice") != null) {
			    buffer.append(autorizzazioniConcessioni.getMercatiD().getStradario().getPrefisso() +
				    " " +
				    autorizzazioniConcessioni.getMercatiD().getStradario().getDescrizione());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getDataCessazione(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni) && autorizzazioni.getDataCessazione() != null) {
		    buffer.append(dateFormat(autorizzazioni.getDataCessazione()));
		    buffer.append(RTF_CRLF);
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getPosteggioNote(Set<Autorizzazioni> auts) {

	String val = "";
	if (auts != null) {
	    StringBuffer buffer = new StringBuffer();
	    for (Autorizzazioni autorizzazioni : auts) {
		if (isConcessione(autorizzazioni)) {
		    Set<AutorizzazioniConcessioni> autconcs = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autconcs) {
			if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD(), "id.codice") != null
				&& EntityUtils.getNestedProperty(autorizzazioniConcessioni.getMercatiD().getStradario(), "id.codice") != null
				&& StringUtils.isNotBlank(autorizzazioniConcessioni.getMercatiD().getNote())) {
			    buffer.append(autorizzazioniConcessioni.getMercatiD().getNote());
			    buffer.append(RTF_CRLF);
			}
		    }
		}
	    }
	    val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	}
	return val;
    }

    public static String getListaDocumentiIstanzaNonRichiestiConData(Set<Documentiistanza> docIstanza) {

	String val = "";
	StringBuffer buffer = new StringBuffer();
	for (Documentiistanza documentiistanza : docIstanza) {
	    if (!documentiistanza.getNecessario() && EntityUtils.getNestedProperty(documentiistanza.getOggetto(), "id.codice") != null
		    && documentiistanza.getData() != null) {
		buffer.append(documentiistanza.getDocumento()).append(RTF_TAB).append(Utilities.formatDate(documentiistanza.getData(), false));
		buffer.append(RTF_CRLF);
	    }
	}
	val = StringUtils.removeEnd(buffer.toString(), RTF_CRLF);
	return val;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// AMMINISTRAZIONI /////// ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getAmministrazione(Amministrazioni amministrazioni) {

	StringBuffer val = new StringBuffer();
	if (amministrazioni != null) {
	    val.append(StringUtils.defaultIfEmpty(stringFormat(amministrazioni.getAmministrazione()), ""));
	}
	return val.toString();
    }

    public static String getAmministrazionePec(Amministrazioni amministrazioni) {

	StringBuffer val = new StringBuffer();
	if (amministrazioni != null) {
	    val.append(StringUtils.defaultIfEmpty(stringFormat(amministrazioni.getPec()), ""));
	}
	return val.toString();
    }

    public static String getAmministrazioneMail(Amministrazioni amministrazioni) {

	StringBuffer val = new StringBuffer();
	if (amministrazioni != null) {
	    val.append(StringUtils.defaultIfEmpty(stringFormat(amministrazioni.getEmail()), ""));
	}
	return val.toString();
    }

    public static String getAmministrazionePI(Amministrazioni amministrazioni) {

	StringBuffer val = new StringBuffer();
	if (amministrazioni != null) {
	    val.append(StringUtils.defaultIfEmpty(stringFormat(amministrazioni.getPartitaiva()), ""));
	}
	return val.toString();
    }

    public static String getAmministrazioneReferente(Amministrazioni amministrazioni) {

	StringBuffer val = new StringBuffer();
	if (amministrazioni != null) {
	    val.append(StringUtils.defaultIfEmpty(stringFormat(amministrazioni.getReferente()), ""));
	}
	return val.toString();
    }

    public static String stringFormat(String value, boolean capitalize) {

	String retVal = StringUtils.trimToEmpty(value);
	if (capitalize) {
	    retVal = WordUtils.capitalizeFully(retVal);
	}
	//escape dei caratteri non ASCII
	Matcher matcher = NON_ASCII_REGEX_PATTERN.matcher(retVal);
	StringBuffer sbRetVal = new StringBuffer();
	String nonAsciiChar = null;
	while (matcher.find()) {
	    nonAsciiChar = matcher.group();
	    matcher.appendReplacement(sbRetVal, escapeUnicodeCharacter(nonAsciiChar));
	}
	matcher.appendTail(sbRetVal);
	return sbRetVal.toString();
    }

    public static String stringFormat(String value) {

	return stringFormat(value, false);
    }

    public static String escapeUnicodeCharacter(String toEscape) {

	StringBuilder sbEscaped = new StringBuilder();
	if (toEscape != null && toEscape.length() > 0) {
	    sbEscaped.append("\\\\\\\\u");
	    char[] chars = toEscape.toCharArray();
	    //in teoria il metodo dovrebbe ricevere stringhe di un solo carattere ma può funzionare anche con sequenze di caratteri.
	    for (int i = 0; i < chars.length;) {
		Character c = new Character(chars[i]);
		int unicodeCodePoint = Character.codePointAt(chars, i);
		if (Character.isHighSurrogate(chars[i])) {
		    i++;
		}
		i++;
		//sbEscaped.append(Integer.toHexString(unicodeCodePoint));
		sbEscaped.append(unicodeCodePoint);
	    }
	    sbEscaped.append("?");
	}
	return sbEscaped.toString();
    }

    public static String dateFormat(Date dateValue) {

	return dateFormat(dateValue, null);
    }

    private static final DateFormat defaultDateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);

    public static String dateFormat(Date dateValue, DateFormat df) {

	String retVal = "";
	if (dateValue != null) {
	    if (df == null) {
		df = defaultDateFormat;
	    }
	    retVal = df.format(dateValue);
	}
	return retVal;
    }

    public static boolean isConcessione(Autorizzazioni aut) {

	if (aut != null && !aut.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
	    return true;
	}
	return false;
    }

    public static TipoAuthQRcodeEnum getAuthQRcode(String tipoQrcode) {

	if (tipoQrcode.contains("PIN")) {
	    return TipoAuthQRcodeEnum.PIN;
	} else if (tipoQrcode.contains("GUEST")) {
	    return TipoAuthQRcodeEnum.GUEST;
	} else if (tipoQrcode.contains("AUTH")) {
	    return TipoAuthQRcodeEnum.AUTH;
	}
	return null;
    }
}
