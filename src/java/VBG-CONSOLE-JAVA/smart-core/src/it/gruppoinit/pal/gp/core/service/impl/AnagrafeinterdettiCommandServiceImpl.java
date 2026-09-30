package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ScadenzecategoriebaseEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeInterdettiHelper;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeInterdettiCommand;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafeinterdettiCommandService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ScadenzeService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagrafeinterdettiCommandServiceImpl implements AnagrafeinterdettiCommandService {

    private AnagrafeService anagrafeService;
    private ComuniService comuniService;
    private ScadenzeService scadenzeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setScadenzeService(ScadenzeService scadenzeService) {

	this.scadenzeService = scadenzeService;
    }

    @Override
    public AnagrafeInterdettiHelper insertAnagrafeInterdetti(FileUpload fileUpload) {

	// §§§BEGIN§§§
	//Il metodo recupera da file excel i campi da inserire sul BD
	AnagrafeInterdettiHelper anagrafeInterdettiHelper = importRecordDaFileExcell(fileUpload);
	List<AnagrafeInterdettiCommand> listInterdetti = anagrafeInterdettiHelper.getListaAnagrafeInterdettiDaImportare();
	int recordNonApportanoCambiamneti = 0;
	for (AnagrafeInterdettiCommand anagrafeInterdettiCommand : listInterdetti) {
	    // creo gli oggetti anagrafe e scadenze dalla informazioni presenti su anagrafeInterdettiCommnad
	    Anagrafe anagrafeDaInserire = AnagrafeInterdetiToAnagrafeDTO(anagrafeInterdettiCommand);
	    Scadenze scadenzadaInserire = AnagrafeInterdetiToScadenzeDTO(anagrafeInterdettiCommand);
	    //ritona l'anagrafica se esiste o un eccezione se c'è ominimina
	    try {
		// Il metodo ritorna un anagrafica se quella passata è già presente nel DB.
		Anagrafe anagrafe = anagrafeService.bindDomainObject(anagrafeDaInserire, PkId.class, "id.codice");
		if (anagrafe == null)// anagrafica non esistente (inserisco anagrafica e scadenza)
		{
		    scadenzadaInserire.setAnagrafe(anagrafeDaInserire);
		    // inserisco anagrafe e inserisco scadenza 
		    anagrafeService.insert(anagrafeDaInserire);
		    scadenzeService.insert(scadenzadaInserire);
		} else // anagrafica gia presente (inserisco solo la scadenza, se già non esiste)
		{
		    // cerca se ci sono scadenze con categoria I per l'anagrafe per l'anagrafe passata
		    List<Scadenze> listScadenze = scadenzeService.findAvvisiForAnagrafe(anagrafe.getId().getCodice(),
			    ScadenzecategoriebaseEnum.INTERDIZIONE);
		    // Controlla se ci sono :
		    // 1- Lista vuota : allora inserisco scadenza
		    // 2- Se la lista non è vuota, ma non esiste una scadenza uguale a quella passata inserisco
		    // Per Scadenza uguale si intende:
		    // 	a.Stessa anagrafica
		    //	b.Stessa data inizio
		    //	c.Stessa data fine
		    if (listScadenze.isEmpty() || (!listScadenze.isEmpty() && !exsistScadenza(listScadenze, scadenzadaInserire))) // non esistono
		    {
			scadenzadaInserire.setAnagrafe(anagrafe);
			scadenzeService.insert(scadenzadaInserire);
		    } else {
			recordNonApportanoCambiamneti++;
			anagrafeInterdettiHelper.setRecordImportati(anagrafeInterdettiHelper.getRecordImportati() - 1);
		    }
		}
		// Metto il record tra quelli scartati, segnalando come errore l'eccezione di possibile omomimia
	    } catch (BusinessValidationException e) {
		String errore = anagrafeInterdettiCommand.getErroriIncongruenze();
		errore = errore.replace("<ol>", "");
		errore = errore.replace("</ol>", "");
		errore = "<li>Possibile omonimia</li>";
		anagrafeInterdettiCommand.setErroriIncongruenze("<ol>" + errore + "</ol>");
		anagrafeInterdettiHelper.getListaAnagrafeInterdettiIncongruenti().add(anagrafeInterdettiCommand);
		anagrafeInterdettiHelper.getListaAnagrafeInterdettiDaImportare().remove(anagrafeInterdettiCommand);
		anagrafeInterdettiHelper.setRecordImportati(anagrafeInterdettiHelper.getRecordImportati() - 1);
		anagrafeInterdettiHelper.setRecordScartati(anagrafeInterdettiHelper.getRecordScartati() + 1);
	    }
	}
	anagrafeInterdettiHelper.setRecordNonAggiornati(recordNonApportanoCambiamneti);
	return anagrafeInterdettiHelper;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Controlla se due scadenza sono uguali , controllando i campi anagrafe,dataRegistrazione,dataScadenza
     * 
     * @param listScadenze
     * @param scadenzadaInserire
     * @return
     */
    private boolean exsistScadenza(List<Scadenze> listScadenze, Scadenze scadenzadaInserire) {

	boolean isEquals = false;
	for (Scadenze scadenze : listScadenze) {
	    if (Utilities.isEqualsDates(scadenze.getDataregistrazione(), scadenzadaInserire.getDataregistrazione())
		    && Utilities.isEqualsDates(scadenze.getDatascadenza(), scadenzadaInserire.getDatascadenza())) {
		return true;
	    }
	}
	return isEquals;
    }

    // Crea un oggetto Scadenza dai campi presenti nel command AnagrafeInterdetti
    private Scadenze AnagrafeInterdetiToScadenzeDTO(AnagrafeInterdettiCommand anagrafeInterdettiCommand) {

	Scadenze scadenze = new Scadenze();
	scadenze.setCategoria(WebConstants.INTERDETTI);
	if (anagrafeInterdettiCommand.getDataInizioInterdizione() != null) {
	    scadenze.setDataregistrazione(anagrafeInterdettiCommand.getDataInizioInterdizione());
	}
	if (anagrafeInterdettiCommand.getDataFineInterdizione() != null) {
	    scadenze.setDatascadenza(anagrafeInterdettiCommand.getDataFineInterdizione());
	}
	scadenze.setNote("");
	scadenze.setFlagNascondi(Boolean.valueOf(false));
	scadenze.setScadenza(WebConstants.DESCRIZIONE_MOTIVO_INTERDIZIONE);
	return scadenze;
    }

    // Crea un oggetto Anagrafe dai campi presenti nel command AnagrafeInterdetti
    private Anagrafe AnagrafeInterdetiToAnagrafeDTO(AnagrafeInterdettiCommand anagrafeInterdettiCommand) {

	Anagrafe anagrafe = new Anagrafe();
	anagrafe.setNome(anagrafeInterdettiCommand.getNome());
	anagrafe.setNominativo(anagrafeInterdettiCommand.getCognome());
	if (StringUtils.isNotBlank(anagrafeInterdettiCommand.getIdComune())) {
	    Comuni comune = comuniService.findById(anagrafeInterdettiCommand.getIdComune());
	    anagrafe.setComuneNascita(comune);
	}
	if (anagrafeInterdettiCommand.getDataNascita() != null) {
	    anagrafe.setDatanascita(anagrafeInterdettiCommand.getDataNascita());
	}
	if (StringUtils.isNotBlank(anagrafeInterdettiCommand.getSesso())) {
	    anagrafe.setSesso(anagrafeInterdettiCommand.getSesso().toUpperCase());
	}
	if (StringUtils.isNotBlank(anagrafeInterdettiCommand.getCodiceFiscale()))
	    anagrafe.setCodicefiscale(anagrafeInterdettiCommand.getCodiceFiscale());
	anagrafe.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	return anagrafe;
    }

    // Importa dal foglio Excel i dati per creare le anagrafe interdette 
    private AnagrafeInterdettiHelper importRecordDaFileExcell(FileUpload fileUpload) {

	// §§§BEGIN§§§
	AnagrafeInterdettiHelper anagrafeInterdettiHelper = new AnagrafeInterdettiHelper();
	// Liste che conterranno le anagrafiche da importare e quelle che verranno scartate per possibili anamalie dei dati sul foglio Excel
	List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiDaImportare = new ArrayList<AnagrafeInterdettiCommand>();
	List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiIncongruenti = new ArrayList<AnagrafeInterdettiCommand>();
	try {
	    // Recupera il file passato 
	    InputStream inp = fileUpload.getFile().getInputStream();
	    HSSFWorkbook wb = new HSSFWorkbook(inp);
	    HSSFSheet sheet = wb.getSheetAt(0); // first sheet
	    int numeroRighe = sheet.getLastRowNum();
	    numeroRighe++;
	    // Il for inizia da uno per escludere dall'inserimeto la prima riga che contiene solo i titoli delle colonne
	    for (int i = 1; i < numeroRighe; i++) {
		AnagrafeInterdettiCommand anagrafeInterdetta = new AnagrafeInterdettiCommand();
		HSSFRow row = sheet.getRow(i); // first row
		HSSFCell cellCognome = row.getCell(0); // COGNOME
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		StringBuffer errore = new StringBuffer();
		try {
		    String cognome = cellCognome.getStringCellValue();
		    anagrafeInterdetta.setCongnome(cognome);
		    // Se il cognome è vuoto,record escluso
		    if (StringUtils.isBlank(cognome)) {
			errore.append("<li>Cognome non presente</li>");
		    }
		} catch (Exception e) {
		    anagrafeInterdetta.setCongnome(null);
		    errore.append("<li>Cognome non presente</li>");
		}
		HSSFCell cellNome = row.getCell(1); // NOME
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		try {
		    String nome = cellNome.getStringCellValue();
		    anagrafeInterdetta.setNome(nome);
		    // Se il nome è vuoto,record escluso
		    if (StringUtils.isBlank(nome)) {
			errore.append("<li>Nome non presente</li>");
		    }
		} catch (Exception e) {
		    anagrafeInterdetta.setNome(null);
		    errore.append("<li>Nome non presente</li>");
		}
		//try-catch per controllare che i valori inseriti siano DATA
		HSSFCell cellDataNascita = row.getCell(2); // DATA NASCITA
		try {
		    Date dataNascita = cellDataNascita.getDateCellValue();
		    anagrafeInterdetta.setDataNascita(dataNascita);
		} catch (Exception e) {
		    anagrafeInterdetta.setDataNascita(null);
		}
		HSSFCell cellComune = row.getCell(3); // COMUNE NASCITA
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		try {
		    String comuneNascita = cellComune.getStringCellValue();
		    anagrafeInterdetta.setComuneNascita(comuneNascita);
		} catch (Exception e) {
		    anagrafeInterdetta.setComuneNascita(null);
		}
		HSSFCell cellIdComune = row.getCell(4); // IDCOMUNE
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		try {
		    String idComune = cellIdComune.getStringCellValue();
		    anagrafeInterdetta.setIdComune(idComune);
		} catch (Exception e) {
		    anagrafeInterdetta.setIdComune(null);
		}
		HSSFCell cellSesso = row.getCell(5); // SESSO
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		try {
		    String sesso = cellSesso.getStringCellValue();
		    // I campi possono essere o M/m (Maschio) o F/f (Femmina)
		    if (sesso.toUpperCase().equals(WebConstants.MASCHIO) || sesso.toUpperCase().equals(WebConstants.FEMMINA)) {
			anagrafeInterdetta.setSesso(sesso);
		    } else {
			anagrafeInterdetta.setSesso(null);
		    }
		} catch (Exception e) {
		    anagrafeInterdetta.setSesso(null);
		}
		//try-catch per controllare che i valori inseriti siano DATA
		HSSFCell cellInizioInterdizione = row.getCell(6); // DATA INIZIO INTERDIZIONE
		try {
		    Date dataInizioInterdizione = cellInizioInterdizione.getDateCellValue();
		    anagrafeInterdetta.setDataInizioInterdizione(dataInizioInterdizione);
		} catch (Exception e) {
		    anagrafeInterdetta.setDataInizioInterdizione(null);
		    errore.append("<li>Data inizio interdizione non è nel formato dd/MM/yyyy o non presente</li>");
		}
		//try-catch per controllare che i valori inseriti siano DATA
		HSSFCell cellFineInterdizione = row.getCell(7); // DATA FINE INTERDIZIONE
		String tipoDato = isDateOrString(cellFineInterdizione);
		// Questo controllo è utilizzato per vedere se c'è un iterdizione perpetua.
		// Se l'interdizione sarà perpetua sul foglio Excel sarà presente la dicitura "PERPETUA"
		// Nel nostro caso metteremo la data 01/01/2999
		if (tipoDato.equals("stringa")) {
		    try {
			String dataFineInterdizione = cellFineInterdizione.getStringCellValue();
			if (dataFineInterdizione.toUpperCase().equals("PERPETUA") || StringUtils.isBlank(dataFineInterdizione)) {
			    anagrafeInterdetta.setDataFineInterdizione(new GregorianCalendar(2999, 0, 1).getTime());
			} else {
			    errore.append("<li>Data fine interdizione non è nel formato dd/MM/yyyy o non presente</li>");
			}
		    } catch (Exception e1) {
			anagrafeInterdetta.setDataFineInterdizione(null);
			errore.append("<li>Data fine interdizione non è nel formato dd/MM/yyyy o non presente</li>");
		    }
		}
		if (tipoDato.equals("data"))
		    try {
			Date dataFineInterdizione = cellFineInterdizione.getDateCellValue();
			anagrafeInterdetta.setDataFineInterdizione(dataFineInterdizione);
		    } catch (Exception e) {
			anagrafeInterdetta.setDataFineInterdizione(null);
			errore.append("<li>Data fine interdizione non è nel formato dd/MM/yyyy o non presente</li>");
		    }
		HSSFCell cellCodiceFiscale = row.getCell(8); // CF
		// try-catch per controllare che i valori inseriti siano corretti e presenti
		try {
		    String cf = cellCodiceFiscale.getStringCellValue();
		    anagrafeInterdetta.setCodiceFiscale(cf);
		} catch (Exception e) {
		    anagrafeInterdetta.setCodiceFiscale(null);
		}
		// Se il campo errore non è vuoto significa che il record non deve essere importato
		if (StringUtils.isNotBlank(errore.toString())) {
		    StringBuffer messaggioDiErrore = new StringBuffer();
		    messaggioDiErrore.append("<ol>");
		    messaggioDiErrore.append(errore);
		    messaggioDiErrore.append("</ol>");
		    anagrafeInterdetta.setErroriIncongruenze(messaggioDiErrore.toString());
		    listaAnagrafeInterdettiIncongruenti.add(anagrafeInterdetta);
		} else {
		    listaAnagrafeInterdettiDaImportare.add(anagrafeInterdetta);
		}
		anagrafeInterdettiHelper.setListaAnagrafeInterdettiDaImportare(listaAnagrafeInterdettiDaImportare);
		anagrafeInterdettiHelper.setListaAnagrafeInterdettiIncongruenti(listaAnagrafeInterdettiIncongruenti);
		anagrafeInterdettiHelper.setTotaleRecord(numeroRighe - 1);
		anagrafeInterdettiHelper.setRecordImportati(listaAnagrafeInterdettiDaImportare.size());
		anagrafeInterdettiHelper.setRecordScartati(listaAnagrafeInterdettiIncongruenti.size());
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	return anagrafeInterdettiHelper;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * <pre>
     * Ritorna una stringa:
     * 
     * 	1- stringa: se la cella è una stringa;
     * 	2- data   : se è una data;
     *  3 -""     : altro
     * @param cell
     * @return
     * 
     * </pre>
     */
    private String isDateOrString(HSSFCell cell) {

	String risultato = "";
	try {
	    cell.getStringCellValue();
	    return "stringa";
	} catch (Exception e) {
	}
	risultato = "data";
	try {
	    cell.getDateCellValue();
	    return "data";
	} catch (Exception e) {
	}
	return risultato;
    }
}
