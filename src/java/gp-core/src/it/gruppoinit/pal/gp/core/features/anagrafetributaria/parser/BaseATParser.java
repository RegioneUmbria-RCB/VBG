package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipologiaTracciatoEnum;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErrori;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserErroriTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnTribParserGruppi;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnagrafeTribParserEsito;

public abstract class BaseATParser implements ITracciatoATParser {

    private final String RIEPILOGO = "-- riepilogo --";
    private final String regexrRichiestaNonIdentificata = "Dal Record n. [0-9]* al [0-9]* - RICHIESTA NON IDENTIFICATA";
    private final String REGEX_POSIZIONE_TRACCIATO = "Record n. ([0-9]*)";
    private final Pattern pattern = Pattern.compile(REGEX_POSIZIONE_TRACCIATO);
    private final String REGEX_TRACCIATO_COPIATO_1 = "([A-Z]+)(================================================================================)";
    private final String REGEX_TRACCIATO_COPIATO_2 = "(================================================================================)([A-Z]+)";
    private final String replacement = "$1\n$2";
    private final String LINE_FEED = "\n";
    public String INIZIO_GRUPPO = "================================================================================";

    private List<String> splitGruppi(String esitoContent) throws IOException {

	List<String> ret = new ArrayList<String>();
	StringReader sr = new StringReader(esitoContent);
	BufferedReader r = new BufferedReader(sr);
	boolean gruppiIniziati = false;
	StringBuffer gruppo = null;
	String riga;
	while ((riga = r.readLine()) != null) {
	    riga = StringUtils.defaultString(riga);
	    if (riga.indexOf(INIZIO_GRUPPO) >= 0) {
		gruppiIniziati = true;
	    }
	    if (gruppiIniziati) {
		if (riga.indexOf(INIZIO_GRUPPO) >= 0 || riga.toLowerCase().indexOf(RIEPILOGO) > 0) {
		    // il gruppo inizia con INIZIO_GRUPPO
		    if (gruppo != null) {
			ret.add(gruppo.toString());
		    }
		    if (riga.toLowerCase().indexOf(RIEPILOGO) > 0) {
			break;
		    }
		    gruppo = new StringBuffer();
		    continue;
		}
		if (StringUtils.isBlank(riga.trim())) {
		    // fine del gruppo linea vuota
		    continue;
		}
		gruppo.append(riga) //
			.append(LINE_FEED);
	    }
	}
	return ret;
    }

    public List<AnTribParserGruppi> getErrori(List<String> splitGruppi, String tracciatoContent) throws IOException {

	List<AnTribParserGruppi> gruppi = new ArrayList<AnTribParserGruppi>();
	List<AnTribParserErrori> ret = null;
	//        Record n. 000248 - Codice Fiscale Indicato 01702180330
	//(**) 
	//RECORD TIPO: 1 
	//NUMERO DEL PROVVEDIMENTO - VALORE ASSENTE
	//(**) 
	//RECORD TIPO: 1 
	//DATA INIZIO DEL PROVVEDIMENTO - VALORE ASSENTE
	//(*) 
	//RECORD TIPO: 1 
	//DATA FINE DEL PROVVEDIMENTO - VALORE ASSENTE
	AnTribParserErrori errore = null;
	int ordine = 0;
	for (String g : splitGruppi) {
	    AnTribParserGruppi gruppo = new AnTribParserGruppi();
	    ret = new ArrayList<AnTribParserErrori>();
	    gruppo.setOrdine(ordine++);
	    String[] righe = g.split(LINE_FEED);
	    String intestazione = "";
	    boolean richiestaValida = true;
	    for (int i = 0; i < righe.length; i++) {
		String s = StringUtils.defaultString(righe[i]).trim();
		if (s.matches(regexrRichiestaNonIdentificata)) {
		    richiestaValida = false;
		    break;
		}
		if (i == 0) {
		    intestazione = s;
		    continue;
		}
		if (s.matches("\\([*]{1,3}\\)")) {
		    // il gruppo inizia con (*)(**)(***)
		    errore = new AnTribParserErrori();
		    errore.setIntestazione(intestazione);
		    errore.setTipologiaErrore(s);
		    ret.add(errore);
		    continue;
		}
		String e = errore.getErrore();
		if (StringUtils.isNotBlank(e)) {
		    e += ", " + s;
		} else {
		    e = s;
		}
		errore.setErrore(e);
	    }
	    if (richiestaValida && !ret.isEmpty()) {
		gruppo.getErrori().addAll(ret);
		agganciaLineeTraccatiAErrori(ret, tracciatoContent);
		gruppi.add(gruppo);
	    }
	}
	return gruppi;
    }

    public void agganciaLineeTraccatiAErrori(List<AnTribParserErrori> errori, String contenutoTracciato) throws IOException {

	for (AnTribParserErrori ate : errori) {
	    Matcher matcher = pattern.matcher(ate.getIntestazione());
	    String rigaTracciato = null;
	    while (matcher.find()) {
		rigaTracciato = matcher.group(1);
		break;
	    }
	    int posizione = Integer.parseInt(rigaTracciato);
	    AnTribParserErroriTracciato aprt = new AnTribParserErroriTracciato();
	    aprt.setPosizioneNelTracciato(posizione);
	    aprt.setRigaTracciato(recuperaRigaTracciato(posizione, contenutoTracciato));
	    ate.getRigheTracciato().add(aprt);
	}
    }

    private String recuperaRigaTracciato(int posizione, String contenutoTracciato) throws IOException {

	StringReader sr = new StringReader(contenutoTracciato);
	BufferedReader r = new BufferedReader(sr);
	String riga = null;
	int i = 1;
	while ((riga = r.readLine()) != null) {
	    if (i == posizione) {
		return riga;
	    }
	    i++;
	}
	return "";
    }

    public static void main(String[] args) throws IOException {

	BaseATParser p = new BaseATParser() {

	    @Override
	    public AnagrafeTribParserEsito parseInternal(AnagrafeTribParserEsito esito) {

		return esito;
	    }

	    @Override
	    public ATTipologiaTracciatoEnum getTipologia() {

		return ATTipologiaTracciatoEnum.EDILIZIA;
	    }
	};
	String esitoContent = p.sistemaTracciatoCopiato(FileUtils.readFileToString(new File("c:/temp/esportazioni/esito_Edilizia.txt")));
	// FileUtils.writeStringToFile(new File("c:/temp/esportazioni/esito_Edilizia_" + System.currentTimeMillis() + ".txt"), esitoContent);
	List<String> splitGruppi = p.splitGruppi(esitoContent);
	for (String string : splitGruppi) {
	    System.out.println(string);
	    System.out.println("-----");
	}
	System.out.println("===========================================");
	System.out.println("-----");
	System.out.println("===========================================");
	List<AnTribParserGruppi> errori = p.getErrori(splitGruppi,
		FileUtils.readFileToString(new File("c:/temp/esportazioni/ExpAnagrafeEdilizia.txt")));
	for (AnTribParserGruppi g : errori) {
	    List<AnTribParserErrori> errori2 = g.getErrori();
	    System.out.println("\t==>" + g.getOrdine());
	    for (AnTribParserErrori err : errori2) {
		System.out.println(err.getIntestazione());
		System.out.println(err.getTipologiaErrore());
		System.out.println(err.getErrore());
		System.out.println("-----");
	    }
	}
	//	p.agganciaLineeTraccatiAErrori(errori, FileUtils.readFileToString(new File("c:/temp/esportazioni/ExpAnagrafeCommercio.txt")));
    }

    public abstract AnagrafeTribParserEsito parseInternal(AnagrafeTribParserEsito esito);

    public abstract ATTipologiaTracciatoEnum getTipologia();

    @Override
    public AnagrafeTribParserEsito parse(String esitoContent, String tracciatoContent) throws AtParserException {

	try {
	    AnagrafeTribParserEsito esito = new AnagrafeTribParserEsito();
	    esito.setTipologia(getTipologia());
	    esitoContent = sistemaTracciatoCopiato(esitoContent);
	    List<String> splitGruppi = this.splitGruppi(esitoContent);
	    List<AnTribParserGruppi> gruppi = this.getErrori(splitGruppi, tracciatoContent);
	    esito.getGruppi().addAll(gruppi);
	    return parseInternal(esito);
	} catch (Exception e) {
	    throw new AtParserException(e);
	}
    }

    public String sistemaTracciatoCopiato(String esitoContent) {

	// quando copio incollo da PDF alcune righe vengono riportate così
	//================================================================================Dal Record n. 000366 al 000369 - RICHIESTA NON IDENTIFICATA
	// altre così
	// SEGNALAZIONE ERRATA: RECORD TIPO 1 ASSENTE O ERRATO================================================================================
	// per cui devo sistemare mettenro i ritorni a capo
	return esitoContent.replaceAll(REGEX_TRACCIATO_COPIATO_1, replacement).replaceAll(REGEX_TRACCIATO_COPIATO_2, replacement);
    }
}
