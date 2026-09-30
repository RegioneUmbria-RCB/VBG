package it.gruppoinit.pal.gp.core.service.helper;

import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;

import java.io.File;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileExcelParser {

    public static final Logger log = LoggerFactory.getLogger("FileExcelParser");
    private Workbook wb;

    public FileExcelParser(DataHandler dh) throws Exception {

	wb = WorkbookFactory.create(dh.getInputStream());
    }

    public void populateSchede(List<SchedaType> schede, List<String> mappature) {

	for (String mappatura : mappature) {
	    String[] mappaturaParts = splitMappatura(mappatura);
	    String valoreMappatura = getCellValue(mappaturaParts[0], mappaturaParts[1]);
	    if (StringUtils.isNotBlank(valoreMappatura)) {
		boolean isSchedaTrovata = false;
		for (SchedaType scheda : schede) {
		    if (scheda.getNome().equals(mappaturaParts[0])) {
			scheda.getCampi().add(getCampo(mappatura, valoreMappatura));
			isSchedaTrovata = true;
			break;
		    }
		}
		if (!isSchedaTrovata) {
		    SchedaType scheda = new SchedaType();
		    scheda.setNome(mappaturaParts[0]);
		    scheda.setCodice(mappaturaParts[0]);
		    scheda.setDescrizione(mappaturaParts[0]);
		    scheda.getCampi().add(getCampo(mappatura, valoreMappatura));
		    schede.add(scheda);
		}
	    }
	}
    }

    private CampoSchedaType getCampo(String nome, String valore) {

	CampoSchedaType campo = new CampoSchedaType();
	campo.setCodice(nome);
	campo.setDescrizione(nome);
	CampoDinamicoType campoDinamico = new CampoDinamicoType();
	ValoreCampoDinamicoType campoVal = new ValoreCampoDinamicoType();
	campoVal.setNome(nome);
	ElementoValoreCampoDinamicoType elVal = new ElementoValoreCampoDinamicoType();
	elVal.setCodice(valore);
	elVal.setDescrizione(valore);
	campoVal.getValore().add(elVal);
	campoDinamico.setValoreUtente(campoVal);
	campo.setCampoDinamico(campoDinamico);
	return campo;
    }

    private String[] splitMappatura(String mappatura) {

	mappatura = mappatura.substring(5);
	String[] parts = mappatura.split("!");
	return parts;
    }

    private String getCellValue(String sheetName, String cellRef) {

	int sheetIndex = -1;
	for (int i = 0; i < wb.getNumberOfSheets(); i++) {
	    String currentSheetName = wb.getSheetName(i);
	    if (sheetName.equalsIgnoreCase(currentSheetName.trim())) {
		sheetIndex = i;
		break;
	    }
	}
	CellReference cellReference;
	Name name = wb.getName(cellRef);
	if (name != null) {
	    String refersToFormula = name.getRefersToFormula();
	    cellReference = new CellReference(refersToFormula);
	} else {
	    cellReference = new CellReference(cellRef);
	}
	int rowIndex = cellReference.getRow();
	int columnIndex = cellReference.getCol();
	return getCellValue(sheetIndex, rowIndex, columnIndex);
    }

    private String getCellValue(int sheetIndex, int rowIndex, int columnIndex) {

	String cellValue = "";
	Sheet sheet = wb.getSheetAt(sheetIndex);
	Row row = sheet.getRow(rowIndex);
	Cell cell = row.getCell(columnIndex);
	int cellType = cell.getCellType();
	if (cellType == Cell.CELL_TYPE_FORMULA) {
	    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
	    CellValue _cellValue = evaluator.evaluate(cell);
	    cellType = _cellValue.getCellType();
	}
	switch (cellType) {
	case Cell.CELL_TYPE_BOOLEAN:
	    cellValue = String.valueOf(cell.getBooleanCellValue());
	    break;
	case Cell.CELL_TYPE_NUMERIC:
	    cellValue = String.valueOf(cell.getNumericCellValue());
	    break;
	case Cell.CELL_TYPE_STRING:
	    cellValue = cell.getStringCellValue();
	    break;
	case Cell.CELL_TYPE_BLANK:
	    break;
	case Cell.CELL_TYPE_ERROR:
	    cellValue = String.valueOf(cell.getErrorCellValue());
	    break;
	case Cell.CELL_TYPE_FORMULA:
	    // non si verifica
	    break;
	default:
	    log.error("getCellValue: tipo cella non previsto: {}", cellType);
	}
	return cellValue;
    }

    public static void main(String[] args) throws Exception {

	String fileName = "prova.xlsx";
	DataHandler dh = new DataHandler(new FileDataSource(new File("C:\\Temp\\" + fileName)));
	FileExcelParser fep = new FileExcelParser(dh);
	System.out.println("dirigenti: " + fep.getCellValue("Tabelle personale", "B11"));
	System.out.println("quadri: " + fep.getCellValue("Tabelle personale", "B12"));
	System.out.println("impiegati: " + fep.getCellValue("Tabelle personale", "B13"));
	System.out.println("operai: " + fep.getCellValue("Tabelle personale", "B14"));
	System.out.println("totale: " + fep.getCellValue("Tabelle personale", "B15"));
    }
}
