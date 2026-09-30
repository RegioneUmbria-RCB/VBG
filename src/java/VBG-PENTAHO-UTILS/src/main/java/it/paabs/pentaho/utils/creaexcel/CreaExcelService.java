package it.paabs.pentaho.utils.creaexcel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.paabs.pentaho.utils.TipoCellaEnum;
import it.paabs.pentaho.utils.api.CreaexcelApiDelegate;
import it.paabs.pentaho.utils.model.RequestExportExcel;
import it.paabs.pentaho.utils.model.ResponseExcel;

@Service
public class CreaExcelService implements CreaexcelApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(CreaExcelService.class);

    @Override
    public ResponseEntity<ResponseExcel> creaexcelPost(RequestExportExcel requestExportExcel) {

	try {
	    var mapper = new ObjectMapper();
	    var json = mapper.writeValueAsString(requestExportExcel);
	    log.debug(json);
	    try (Workbook workbook = new XSSFWorkbook()) {
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("export");
		// Row for Header
		Row headerRow = sheet.createRow(0);
		List<SimpleDateFormat> pattern = new ArrayList<SimpleDateFormat>();
		pattern.add(new SimpleDateFormat("dd/MM/yyyy"));
		pattern.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
		// Header
		int col = 0;
		for (col = 0; col < requestExportExcel.getDictionary().size(); col++) {
		    Cell cell = headerRow.createCell(col);
		    cell.setCellValue(requestExportExcel.getDictionary().get(col).getNome());
		}
		sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, col));
		CellStyle cellStyle = workbook.createCellStyle();
		for (int i = 0; i < requestExportExcel.getData().size(); i++) {
		    Row row = sheet.createRow(i + 1);
		    for (int j = 0; j < requestExportExcel.getDictionary().size(); j++) {
			Cell cell = row.createCell(j);
			if (StringUtils.isBlank(requestExportExcel.getData().get(i).getRiga().get(j))) {
			    continue;
			}
			TipoCellaEnum tipo = TipoCellaEnum.fromValore(requestExportExcel.getDictionary().get(j).getTipo());
			log.debug("Tipo={}, i={}, j={}, data={}", tipo, i, j, requestExportExcel.getData().get(i).getRiga().get(j));
			switch (tipo) {
			case DATA:
			    Date d = null;
			    for (SimpleDateFormat sdf : pattern) {
				try {
				    if (!StringUtils.contains(requestExportExcel.getData().get(i).getRiga().get(j), " - ")) {
					d = sdf.parse(requestExportExcel.getData().get(i).getRiga().get(j));
				    }
				} catch (Exception e) {
				    //loop
				}
			    }
			    if (d != null) {
				cell.setCellValue(d);
			    } else {
				cell.setCellValue(requestExportExcel.getData().get(i).getRiga().get(j));
			    }
			    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			    cell.setCellStyle(cellStyle);
			    break;
			case NUMERICOINTERO:
			    String s[] = requestExportExcel.getData().get(i).getRiga().get(j).split("\\s+-\\s+");
			    if (s.length > 1) {
				cell.setCellValue(requestExportExcel.getData().get(i).getRiga().get(j));
			    } else {
				cell.setCellValue(Integer.parseInt(requestExportExcel.getData().get(i).getRiga().get(j)));
			    }
			    break;
			case NUMERICODOUBLE:
			    String s1[] = requestExportExcel.getData().get(i).getRiga().get(j).split("\\s+-\\s+");
			    if (s1.length > 1) {
				cell.setCellValue(requestExportExcel.getData().get(i).getRiga().get(j));
			    } else {
				String m = requestExportExcel.getData().get(i).getRiga().get(j).replace(",", ".");
				cell.setCellValue(Double.parseDouble(m));
			    }
			    break;
			default:
			    cell.setCellValue(requestExportExcel.getData().get(i).getRiga().get(j));
			    break;
			}
		    }
		}
		for (int c = 0; c < requestExportExcel.getDictionary().size(); c++) {
		    sheet.autoSizeColumn(c);
		}
		log.debug("Prima di convertire l'excel in bytes");
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		workbook.write(b);
		byte[] bytes = b.toByteArray();
		String encodeBase64String = Base64.encodeBase64String(bytes);
		log.debug("l'excel convertito in bytes");
		ResponseExcel res = new ResponseExcel();
		res.setContenuto(encodeBase64String);
		res.setNomefile("export.xlsx");
		return new ResponseEntity<>(res, HttpStatus.OK);
	    }
	} catch (Exception e) {
	    log.error("Errore nella creazione dell'excel", e);
	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }
}
