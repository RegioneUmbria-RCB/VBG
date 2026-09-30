package it.gruppoinit.nlapec.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Validator {

    public boolean validateMailSubjectLombardia(String mailSujects) {

	try {
	    String regexSeparator = "[\\s]+-[\\s]+";
	    String regexComunicazione = "^POSTA CERTIFICATA:[\\s]*Comunicazione[\\s]*:[\\s]*+Pratica[\\s]+[A-Z0-9]+";
	    String regexProtocollo = "Protocollo\\s[A-Z0-9/]*";
	    String regexCF = "C\\.F\\.\\s[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	    String regexPIVA = "C\\.F\\.\\s[0-9]{11}";
	    //String regexNominativo = "[0-9A-Za-zèìùàòé][0-9a-zA-Z'èìùàòé\\.& ]*$";
	    String regexNominativo = "[\\d\\D\\s\\S\\w\\W]*$";
	    String regex1 = regexComunicazione + regexSeparator + regexProtocollo + regexSeparator + regexCF + regexSeparator + regexNominativo;
	    Pattern myPattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
	    String regex2 = regexComunicazione + regexSeparator + regexProtocollo + regexSeparator + regexPIVA + regexSeparator + regexNominativo;
	    Pattern myPattern2 = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
	    if (myPattern1.matcher(mailSujects).matches() || myPattern2.matcher(mailSujects).matches()) {
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }

    public boolean validazionePresenzaAllegatiLombardia(ArrayList<String> listaFileAttachment) {

	boolean trovatoModelloRiepilogo = false;
	try {
	    String regexCF = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	    String regexPIVA = "^[0-9]{11}";
	    String regexData = "(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])02((19|[2-9]\\d)\\d{2}))|(2902((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";
	    String regexOra = "(([0]?[0-9]|[1]?[0-9]|[2]?[0-3])([0-5][0-9]))";
	    String regexSeparator = "-";
	    String regexExtensionFileModelloRiepilogo = ".SUAP.xml$";
	    String regexModelloRiepilogo = regexCF + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloRiepilogo;
	    String regexModelloRiepilogo2 = regexPIVA + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloRiepilogo;
	    Pattern myPatternModelloRiepilogo = Pattern.compile(regexModelloRiepilogo, Pattern.CASE_INSENSITIVE);
	    Pattern myPatternModelloRiepilogo2 = Pattern.compile(regexModelloRiepilogo2, Pattern.CASE_INSENSITIVE);
	    for (Iterator iterator = listaFileAttachment.iterator(); iterator.hasNext();) {
		String nomeFile = (String) iterator.next();
		if (myPatternModelloRiepilogo.matcher(nomeFile).matches() || myPatternModelloRiepilogo2.matcher(nomeFile).matches()) {
		    trovatoModelloRiepilogo = true;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return trovatoModelloRiepilogo;
    }

    public boolean validazionePresenzaAllegati(String[] listaNomiFile) {

	boolean trovatoModelloRiepilogo = false;
	boolean trovatoDistintaModelloRiepilogo = false;
	boolean trovatoModelloAttivita = false;
	boolean trovatoModelloDistintaAttivita = false;
	String regexCF = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	String regexData = "(((0[1-9]|[12]\\d|3[01])(0[13578]|1[02])((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)(0[13456789]|1[012])((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])02((19|[2-9]\\d)\\d{2}))|(2902((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";
	String regexOra = "(([0]?[1-9]|1[0-2])([0-5][0-9]))";
	String regexSeparator = "-";
	String regexExtensionFileModelloRiepilogo = ".SUAP.xml$";
	String regexExtensionFileDistintaModelloRiepilogo = ".SUAP.pdf.p7m$";
	String regexExtensionFileModelloAttivita = ".[0-9]{3}.MDA.xml";
	String regexExtensionFileDistintaModelloAttivita = ".[0-9]{3}.MDA.PDF.P7M";
	String regexModelloRiepilogo = regexCF + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloRiepilogo;
	Pattern myPatternModelloRiepilogo = Pattern.compile(regexModelloRiepilogo, Pattern.CASE_INSENSITIVE);
	String regexDistintaModelloRiepilogo = regexCF + regexSeparator + regexData + regexSeparator + regexOra
		+ regexExtensionFileDistintaModelloRiepilogo;
	Pattern myPatternDistintaModelloRiepilogo = Pattern.compile(regexDistintaModelloRiepilogo, Pattern.CASE_INSENSITIVE);
	String regexModelloAttivita = regexCF + regexSeparator + regexData + regexSeparator + regexOra + regexExtensionFileModelloAttivita;
	Pattern myPatternModelloAttivita = Pattern.compile(regexModelloAttivita, Pattern.CASE_INSENSITIVE);
	String regexDistintaModelloAttivita = regexCF + regexSeparator + regexData + regexSeparator + regexOra
		+ regexExtensionFileDistintaModelloAttivita;
	Pattern myPatternDistintaModelloAttivita = Pattern.compile(regexDistintaModelloAttivita, Pattern.CASE_INSENSITIVE);
	for (int i = 0; i < listaNomiFile.length; i++) {
	    // System.out.println(listaNomiFile[i]);
	    if (myPatternModelloRiepilogo.matcher(listaNomiFile[i]).matches()) {
		trovatoModelloRiepilogo = true;
	    } else if (myPatternDistintaModelloRiepilogo.matcher(listaNomiFile[i]).matches()) {
		trovatoDistintaModelloRiepilogo = true;
	    } else if (myPatternModelloAttivita.matcher(listaNomiFile[i]).matches()) {
		trovatoModelloAttivita = true;
	    } else if (myPatternDistintaModelloAttivita.matcher(listaNomiFile[i]).matches()) {
		trovatoModelloDistintaAttivita = true;
	    }
	}
	return trovatoModelloRiepilogo && trovatoDistintaModelloRiepilogo && trovatoModelloAttivita && trovatoModelloDistintaAttivita;
    }

    public boolean validateMailSubjectPecCittadini(String mailSubjects) {

	String regexComunicazione = "^POSTA CERTIFICATA:[\\s]*SUAP[\\s]*:[\\s]*+[0-9]+[\\s]*-[\\s]*";
	String regexCF = "[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]";
	String regexPIVA = "[0-9]{11}";
	String regexNominativo = "[\\d\\D\\s\\S\\w\\W]*$";
	String regex1 = regexComunicazione + regexPIVA + "[\\s]*-[\\s]*" + regexNominativo;
	Pattern myPattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
	String regex2 = regexComunicazione + regexCF + "[\\s]*-[\\s]*" + regexNominativo;
	Pattern myPattern2 = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
	if (myPattern1.matcher(mailSubjects.toUpperCase()).matches() || myPattern2.matcher(mailSubjects.toUpperCase()).matches()) {
	    return true;
	}
	return false;
    }

    public boolean validateMailSubjectPecNonFormatta(String subject, String regularExpression) {

	Pattern myPattern1 = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE);
	if (myPattern1.matcher(subject).matches()) {
	    return true;
	}
	return false;
    }
}
