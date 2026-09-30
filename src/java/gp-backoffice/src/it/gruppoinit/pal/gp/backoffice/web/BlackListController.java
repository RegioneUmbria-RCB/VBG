package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListResultBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.audti.BlackListAuditLogger;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Controller
public class BlackListController extends BaseController<BlacklistMotivi>{
    
    private static final Logger log = LoggerFactory.getLogger(BlackListController.class);
    private static final String SENZA_ACCERTAMENTO = "Senza accertamento";
    private static final String DASH = "-";
    private static final String[] EXPORT_BL_CHIUSE_PRESENZE_HEADER = {"IUV", "DESCRIZIONE", "MERCATO", "DATA", "TITOLARE", "IMPORTO", "DATA INIZIO BLACKLIST", "DATA ACCERTAMENTO", "DATA FINE BLACKLIST"};
    private static final String[] EXPORT_BL_CHIUSE_BOLLETTAZIONE_HEADER = {"IUV", "DATA", "CAUSALE", "TITOLARE", "IMPORTO", "DATA INIZIO BLACKLIST", "DATA ACCERTAMENTO", "DATA FINE BLACKLIST"};
    
    @Autowired
    private BlacklistMotiviService blacklistMotiviService;
    
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    
    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping
    public String view (Model model, HttpServletRequest request, @RequestParam("contesto") String contesto){
	userHasRole(true, RuoliUtentiEnum.GESTIONE_ACCERTAMENTI_ESECUTIVI.name());
	genericViewModel(model,contesto);
	return "blacklist/form";
    }
    
    @RequestMapping
    public String cerca(Model model, HttpServletRequest request){

	try{
	    
	    List<BlackListResultBean> blacklistResultList = blacklistMotiviService.getBlackListResultFe(BlackListContestoEnum.getByContesto(request.getParameter("contestohidden")),
		    request.getParameter("dataaccertamentoselect"), getDateByString(request.getParameter("dallaData")), 
		    getDateByString(request.getParameter("allaData")), getDateByString(request.getParameter("dallaDataIuv")), 
		    getDateByString(request.getParameter("allaDataIuv")), request.getParameter("iuvnumeroname"), request.getParameter("titolarename"),
		    request.getParameter("codicefiscalename"), 
		    Integer.parseInt(request.getParameter("firstresult_name")), 
		    Integer.parseInt(request.getParameter("maxresult_name")));	    	    	    	    
	    
	    model.addAttribute("blacklistResultList", blacklistResultList);
	    model.addAttribute("isRicerca", true);	    	    
	    model.addAttribute("dataaccertamentoselect", request.getParameter("dataaccertamentoselect"));
	    model.addAttribute("dallaData", request.getParameter("dallaData"));
	    model.addAttribute("allaData", request.getParameter("allaData"));
	    model.addAttribute("dallaDataIuv", request.getParameter("dallaDataIuv"));
	    model.addAttribute("allaDataIuv", request.getParameter("allaDataIuv"));
	    model.addAttribute("iuvnumeroname", request.getParameter("iuvnumeroname"));
	    model.addAttribute("titolarename", request.getParameter("titolarename"));
	    model.addAttribute("codicefiscalename", request.getParameter("codicefiscalename"));
	    model.addAttribute("nascondiImportiName",request.getParameter("nascondi_importi_name") != null && request.getParameter("nascondi_importi_name").equals("true"));
	    model.addAttribute("totrecordestratti", blacklistResultList != null ? blacklistResultList.size() : 0);
	    
			
	    genericViewModel(model,request.getParameter("contestohidden"));	
		
	}catch(Exception e){
	    log.error("",e);
	    throw new RuntimeException(e);
	}
	
	
	return "blacklist/form";
    }
    
    private void genericViewModel(Model model, String contesto){
	model.addAttribute("contesto", contesto);
	
	//Tendina
	List<String> dropdownDateList = new ArrayList<String>();
	dropdownDateList.add(DASH);
	dropdownDateList.add(SENZA_ACCERTAMENTO);
	List<Date> returnLDB = blacklistMotiviService.findAllDataAccertamentoByContesto(BlackListContestoEnum.getByContesto(contesto));
	for(Date d : returnLDB){
	    dropdownDateList.add(getStringByDate(d));
	}
	
	model.addAttribute("dropdownDateList", dropdownDateList);
    }
    
    private static Date getDateByString(String dateStr) throws ParseException{
	if(StringUtils.isBlank(dateStr)){
	    return null;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return sdf.parse(dateStr);
    }
    private static String getStringByDate(Date date) {
	if(date == null){
	    return null;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return sdf.format(date);
    }        

    @RequestMapping
    public void ajaxAvviaAccertamentoSingolo(@RequestParam("iddettpd") Integer iddettpd, @RequestParam("idbl") Integer idbl,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    
	    BlacklistMotivi bl = blacklistMotiviService.findById(new PkId(idbl));
	    if(bl == null){
		log.error("null value found for idbl {}", idbl);
		return;
	    }
	    if(bl.getDataAccertamento() != null){
		log.debug("blacklist con idbl {} già accertata, quindi passiamo allo step successivo", idbl);
		return;
	    }
	    
	    DettPosizioneDebitoria dettPos = dettPosizioneDebitoriaService.findById(new PkId(iddettpd));
	    if (dettPos == null) {
		log.error("null value found for iddettpd {}", iddettpd);
		return;
	    }
	    nodoPagamentiService.modificaDataFineValiditaPosizioneDebitoria(iddettpd, idbl, new java.util.Date());
	    log.info("La posizione debitoria iddettpd {} è stata accertata correttamente", iddettpd);
	} catch (Exception e) {
	    log.error("Si è verificato un errore su dett posizione debitoria " + iddettpd, e);
	    addWarning(request, "Errore su posizione debitoria con id " + iddettpd + ":" + e.getMessage());
	    throw new RuntimeException(e);
	}
    }

    @RequestMapping
    public void ajaxConcludiAccertamentoSingolo(@RequestParam("iddettpd") String iddettpd, @RequestParam("idbl") Integer idbl,  HttpServletRequest request,
	    HttpServletResponse response){	
	try{
	    
	    BlacklistMotivi bl = blacklistMotiviService.findById(new PkId(idbl));
	    if(bl == null){
		log.error("null value found for idbl {}", idbl);
		return;
	    }
	    if(bl.getDataAccertamento() == null){
		log.debug("blacklist con idbl {} non ancora accertata, non si può annullare, quindi passiamo allo step successivo", idbl);
		return;
	    }
	    
	    DettPosizioneDebitoria dettPos = dettPosizioneDebitoriaService.findById(new PkId(Integer.parseInt(iddettpd)));
	    if(dettPos == null){
		log.error("null value found for iddettpd" + iddettpd);
		return;
	    }	    	    
	    nodoPagamentiService.annullaPosizioneDebitoria(Integer.parseInt(iddettpd));	    	    
	    log.info("La posizione debitoria iddettpd " + iddettpd + " è stata annullata correttamente");
	}catch(Exception e){
	    log.error("Si è verificato un errore su dett posizione debitoria " + iddettpd);	    
	    addWarning(request,"Errore su posizione debitoria con id " + iddettpd + ":" + e.getMessage());	    
	    throw new RuntimeException(e);
	}
	
    }
    
    @RequestMapping
    public void ajaxLogAuditPosizioniDebitorieChiusure(HttpServletRequest request, HttpServletResponse response, @RequestParam("totpdeb") String totpdeb){
	if(!"POST".equals(request.getMethod())){
	    throw new RuntimeException("Method not allowed");
	}
	
	Integer numpdeb = null;
	try{
	    numpdeb = Integer.parseInt(totpdeb); 
	}catch(Exception e){
	    log.error("",e);
	}	
	
	Object warnings = request.getSession().getAttribute("___warnings");
	if(warnings != null){
	    new BlackListAuditLogger().scriviReportErroriChiusurePDeb((List<String>)warnings, userSecurityService.getCurrentlyAuthenticatedUserDetails().toString(), numpdeb);
	}
    }
    
    @RequestMapping
    public void ajaxLogAuditPosizioniDebitorieAccertamenti(HttpServletRequest request, HttpServletResponse response, @RequestParam("totpdeb") String totpdeb){
	if(!"POST".equals(request.getMethod())){
	    throw new RuntimeException("Method not allowed");
	}
	
	Integer numpdeb = null;
	try{
	    numpdeb = Integer.parseInt(totpdeb); 
	}catch(Exception e){
	    log.error("",e);
	}	
	
	Object warnings = request.getSession().getAttribute("___warnings");
	if(warnings != null){
	    new BlackListAuditLogger().scriviReportErroriChiusurePDeb((List<String>)warnings, userSecurityService.getCurrentlyAuthenticatedUserDetails().toString(), numpdeb);
	}
    }
    
    @RequestMapping
    public void generateReportBlackListChiuse(HttpServletRequest request, HttpServletResponse response, @RequestParam("contesto") String contesto){
	
	try {
	
	  response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	  byte[] content = generateExcelContent(BlackListContestoEnum.getByContesto(contesto));
	  response.setHeader("Pragma", "public");
	  response.setHeader("Cache-Control", "max-age=0");
	  response.setHeader("Content-Disposition", "attachment; filename=ExportBlackList" + System.currentTimeMillis() + ".xlsx");
	  response.setHeader("Content-transfer-encoding", "binary");
	  response.getOutputStream().write(content);
	  
	} catch (Exception e) {
	    
	    response.reset();
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.setContentType("text/plain");
	    response.setHeader("Content-Disposition",
	            "attachment; filename=ErroreDownload.txt");
	 
	    PrintWriter writer = null;
	    try {
		writer = response.getWriter();
	        writer.println("Si è verificato un errore durante la generazione del file:");
	        e.printStackTrace(writer);
	    } catch (IOException e1) {
	        log.error("Errore scrivendo lo stacktrace nel file di errore", e1);
	    }finally{
		if(writer != null){
		    writer.close();
		}
	    }	    	    
	    
	}
    }
    
    private byte[] generateExcelContent( BlackListContestoEnum contesto  ) {

	List<BlackListResultBean> righe = blacklistMotiviService.getBlackListChiuseExport(contesto);
	
	XSSFWorkbook wb = new XSSFWorkbook();
	Sheet s = wb.createSheet();	
	String[] colonne = contesto == BlackListContestoEnum.PRESENZE ? EXPORT_BL_CHIUSE_PRESENZE_HEADER : EXPORT_BL_CHIUSE_BOLLETTAZIONE_HEADER;
	org.apache.poi.ss.usermodel.Row row = s.createRow(0);
	int columncount = 0;
	for (String colonna : colonne) {
		Cell cell = row.createCell(columncount++);
		cell.setCellValue(colonna);
	}
	
	int rowcount = 1;
	
	
	for(BlackListResultBean bean : righe){
	    org.apache.poi.ss.usermodel.Row r = s.createRow(rowcount++);
	    
	    if(contesto == BlackListContestoEnum.PRESENZE){
		r.createCell(0).setCellValue(bean.getIuv());
		r.createCell(1).setCellValue(bean.getDescrizione());
		r.createCell(2).setCellValue(bean.getMercato());
		r.createCell(3).setCellValue(bean.getDataStr());
		r.createCell(4).setCellValue(bean.getTitolare());
		r.createCell(5).setCellValue(bean.getImportoStr());
		r.createCell(6).setCellValue(bean.getDataInizioBlacklistStr());
		r.createCell(7).setCellValue(bean.getDataAccertamentoStr());
		r.createCell(8).setCellValue(bean.getDataFineBlStr());
	    }else{
		r.createCell(0).setCellValue(bean.getIuv());
		r.createCell(1).setCellValue(bean.getDataStr());
		r.createCell(2).setCellValue(bean.getDescrizione());		
		r.createCell(3).setCellValue(bean.getTitolare());
		r.createCell(4).setCellValue(bean.getImportoStr());
		r.createCell(5).setCellValue(bean.getDataInizioBlacklistStr());
		r.createCell(6).setCellValue(bean.getDataAccertamentoStr());
		r.createCell(7).setCellValue(bean.getDataFineBlStr());
	    }
	    
	    
	}
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try {
	    wb.write(baos);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	return baos.toByteArray();
    }
    
    @SuppressWarnings("unchecked")
    private static void addWarning (HttpServletRequest request, String warning){
	Object warnings = request.getSession().getAttribute("___warnings");
	if(warnings == null){
	    List<String> newWarnings = new ArrayList<String>();
	    newWarnings.add("Elaborazione eseguita con errori:");
	    newWarnings.add(warning);
	    request.getSession().setAttribute("___warnings", newWarnings);
	    return;
	}
	
	try{
	    List<String> warningsL = ((List<String>)warnings);
	    if(warningsL.isEmpty()){
		warningsL.add("Elaborazione eseguita con errori:");
	    }
	    warningsL.add(warning);
	}catch(Exception e){
	    log.error("Errore durante l'aggiunta del warning", e);
	    List<String> newWarnings = new ArrayList<String>();
	    newWarnings.add("Elaborazione eseguita con errori: non è stato possibile definire gli errori");
	    request.getSession().setAttribute("___warnings", newWarnings);
	}
    }
    
    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
	
    }

    @Override
    protected void fixMergeEntityProperty(BlacklistMotivi entity) {

	// TODO Auto-generated method stub
	
    }

    @Override
    protected void fixRenderEntityProperty(BlacklistMotivi entity) {

	// TODO Auto-generated method stub
	
    }
}
