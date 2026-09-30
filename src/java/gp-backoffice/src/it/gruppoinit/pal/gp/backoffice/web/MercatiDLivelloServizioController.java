package it.gruppoinit.pal.gp.backoffice.web;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioHelper;
import it.gruppoinit.pal.gp.core.domain.web.LivelloServizioWizard;
import it.gruppoinit.pal.gp.core.domain.web.LivelloServizioWizard.GiornateDaConfigurare;
import it.gruppoinit.pal.gp.core.domain.web.LivelloServizioWizard.MercatiLivelloServizioParziale;
import it.gruppoinit.pal.gp.core.domain.web.MercatiDLivelloServizioCommand;
import it.gruppoinit.pal.gp.core.domain.web.MercatiLivelloServizioCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiDLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("mercatidlivelloservizio")
public class MercatiDLivelloServizioController extends BaseController<MercatiDLivelloServizio> {

    @Autowired
    private MercatiDLivelloServizioService mercatidlivelloservizioService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiLivelloServizioService mercatiLivelloServizioService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private LivelloServizioService livelloServizioService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<MercatiDLivelloServizio> mercatidlivelloservizioList = mercatidlivelloservizioService.findAll(null, null);
	ModelMap model = new ModelMap(mercatidlivelloservizioList);
	boolean export = createJMesaExport(request, response, mercatidlivelloservizioList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatidlivelloservizioList", mercatidlivelloservizioList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam(required = false, value = "codiceposteggio") Integer codiceposteggio, Model model, HttpServletRequest request) {

	MercatiDLivelloServizioCommand mercatidlivelloservizio = new MercatiDLivelloServizioCommand();
	String[] listacodiciposteggio = request.getParameterValues("codiceposteggi");
	if (listacodiciposteggio != null && listacodiciposteggio.length > 0) {
	    codiceposteggio = Integer.parseInt(listacodiciposteggio[1]);
	    mercatidlivelloservizio.setListacodici(listacodiciposteggio);
	}
	MercatiDLivelloServizio entity = new MercatiDLivelloServizio();
	MercatiD p = mercatiDService.findById(new PkId(codiceposteggio));
	entity.setMercatiD(p);
	mercatidlivelloservizio.setEntity(entity);
	mercatidlivelloservizio.setDisplayMode(MercatiDLivelloServizioCommand.NEW);
	fixRenderCommandProperty(mercatidlivelloservizio);
	
	
	model.addAttribute("mercatidlivelloservizio", mercatidlivelloservizio);
	model.addAttribute("codicemercato", p.getMercati().getId().getCodice());
	setPageAttributes(model);
	setPageAttributes(model, codiceposteggio);
	return "mercatidlivelloservizio/form";
    }
    
    @RequestMapping
    public String createM(Model model, HttpServletRequest request) {

	MercatiDLivelloServizioCommand mercatidlivelloservizio = new MercatiDLivelloServizioCommand();
	String[] listacodiciposteggio = request.getParameterValues("codiceposteggi");
	if (listacodiciposteggio != null && listacodiciposteggio.length > 0) {
	    mercatidlivelloservizio.setListacodici(listacodiciposteggio);
	}
	
	
	MercatiD p = mercatiDService.findById(new PkId(Integer.parseInt( mercatidlivelloservizio.getListacodici()[0])));
	
	mercatidlivelloservizio.setEntity(new MercatiDLivelloServizio());
	mercatidlivelloservizio.getEntity().setMercatiD(p);
	mercatidlivelloservizio.setDisplayMode(MercatiDLivelloServizioCommand.NEW);
	
	LivelloServizioWizard livelloServizioWizard = new LivelloServizioWizard();
	livelloServizioWizard.setStep(1);
	livelloServizioWizard.getScegliLivelloDiServizio().setTipoLivelloDiServizio("associatiAlMercato");
	
	List<MercatiUso> usos = mercatiUsoService.findByMercato(p.getMercati().getId().getCodice());
	
	if(usos != null){
	    for(MercatiUso uso : usos){
		GiornateDaConfigurare giornataDaConfigurare = new GiornateDaConfigurare();
		giornataDaConfigurare.setId(uso.getId().getCodice());
		giornataDaConfigurare.setDescrizione(uso.getDescrizione());
		giornataDaConfigurare.setChecked(false);
		livelloServizioWizard.getGiornateDaConfigurare().add(giornataDaConfigurare);
	    }
	}
	
	mercatidlivelloservizio.setLivelloServizioWizard(livelloServizioWizard);
	
	model.addAttribute("mercatidlivelloservizio", mercatidlivelloservizio);
	model.addAttribute("codicemercato", p.getMercati().getId().getCodice());
	
	return "mercatidlivelloservizio/formM";
    }
    
    @RequestMapping
    public String nextStep(Model model, @ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio,
	    BindingResult result, SessionStatus status, @RequestParam(required = true, value = "currstep") Integer currstep, HttpServletRequest request) {
	
	if(currstep < 1 || currstep > 4){
	    throw new RuntimeException("Step non valido");
	}
	
        if( mercatidlivelloservizio.getLivelloServizioWizard().getStep() == currstep ||  
        	mercatidlivelloservizio.getLivelloServizioWizard().getStep() - 1 == currstep || 
        	mercatidlivelloservizio.getLivelloServizioWizard().getStep() + 1 ==  currstep  ){
	    //DO NOTHING
	}else{
	    throw new RuntimeException("Il numero di step non è coerente, current step " + mercatidlivelloservizio.getLivelloServizioWizard().getStep() + " new step " + currstep);
	}
       
	try{

	    boolean isGiornataChecked = false;
	    List<Integer> fkIdUsos = new ArrayList<Integer>();
	    
	    for (GiornateDaConfigurare giornata : mercatidlivelloservizio.getLivelloServizioWizard().getGiornateDaConfigurare()) {
		if (giornata.isChecked()) {
		    isGiornataChecked = true;
		    fkIdUsos.add(giornata.getId());
		}
	    }
	    
	    if(!isGiornataChecked){
		throw new Exception("Selezionare almeno una giornata");
	    }
	    
	    if("associatiAlMercato".equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio()) || 
		    "nuovoDaAssociare".equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio())){
		//DO NOTHING
	    }else{
		throw new Exception("Tipo livello di servizio non valorizzato correttamente");
	    }
	    
	    
	    class Step4DataHelper {
		    
		    private boolean isBothDataEmpty;
		    private String da;
		    private String a;
		    
		    Step4DataHelper(String da, String a){
			this.da = da;
			this.a = a;
			this.isBothDataEmpty = StringUtils.isBlank(da) && StringUtils.isBlank(a);
		    }
		    
		    String getStringByData(){
			if(isBothDataEmpty){
			    return "";
			}
			if(StringUtils.isBlank(da)){
			    return "fino al " + a;
			}else{
			    StringBuilder sb = new StringBuilder();
			    sb.append("dal ").append(da);
			    if(!StringUtils.isBlank(a)){
				sb.append(" al ").append(a);
			    }
			    return sb.toString();
			}
		    }
		    
		    String getLivelloServizioDescription(String livelloServizioDescription){
			if(isBothDataEmpty){
			    return livelloServizioDescription;
			}
			return livelloServizioDescription + " ( validità massima " + getStringByData() + " )";
		    }
	    }
	    
	    if(currstep == 2){
		
		mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().setLivelloDiServizioM("");
		
		List<MercatiLivelloServizio> livelliServizios = mercatiLivelloServizioService.findByMultiUso(fkIdUsos.toArray(new Integer[]{}));
		if(livelliServizios != null && !livelliServizios.isEmpty()){
		    
		    Map<MercatiLivelloServizioParziale, Map<Integer,Integer>> groupMap = new HashMap<MercatiLivelloServizioParziale, Map<Integer,Integer>>();
		    for(MercatiLivelloServizio livelloServizio : livelliServizios){
			MercatiLivelloServizioParziale parziale = new MercatiLivelloServizioParziale(livelloServizio.getDescrizione(), livelloServizio.getAttivo(), livelloServizio.getLivelloServizio().getId().getCodice(), 
				livelloServizio.getTariffa(), livelloServizio.getDataInizioValidita() , livelloServizio.getDataFineValidita(), livelloServizio.getNote());
			
			if(!groupMap.containsKey(parziale)){
			    groupMap.put(parziale, new HashMap<Integer,Integer>());
			}
			groupMap.get(parziale).put(livelloServizio.getMercatiUso().getId().getCodice(), livelloServizio.getId().getCodice());
			
		    }
		   
		    Map<String,String> optionsFinali = new HashMap<String,String>();
		    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		    for(Map.Entry<MercatiLivelloServizioParziale, Map<Integer,Integer>> entry : groupMap.entrySet()){
			if(entry.getValue().keySet().size() != fkIdUsos.size()){
			    continue;
			}
			StringBuilder sb = new StringBuilder();
			for(Integer key : entry.getValue().values()){
			    sb.append(key).append("-");
			}
			optionsFinali.put(sb.toString(),  new Step4DataHelper(entry.getKey().getIniziovalidita() != null ? sdf2.format(entry.getKey().getIniziovalidita()) : null, entry.getKey().getFinevalidita() != null ? sdf2.format(entry.getKey().getFinevalidita()) : null).getLivelloServizioDescription(entry.getKey().getDescrizione()));
		    }

		    mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().setLivelloDiServizioMDD(optionsFinali);
		}
		
		
	    }

	    
	    if(currstep > 2){
		
		if("associatiAlMercato".equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio())){
		    
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioM())){
			throw new Exception("Non è stato scelto un livello di servizio valido");
		    }
		    
		    
		    
		}else{
		    
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioNid())){
			throw new Exception("Valorizzare il nuovo livello di servizio cliccando sull'apposita tendina");
		    }
		    

		    LivelloServizio livelloServizio = livelloServizioService.findById(new PkId(Integer.parseInt(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioNid())));
		    if(livelloServizio != null){
			mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().setLivelloDiServizioN(livelloServizio.getDescrizione());
		    }else{
			throw new Exception("Livello servizio non valido, id livello servizio : " + mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioNid());
		    }
		    
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTariffa())){
			throw new Exception("Tariffa obbligatoria");
		    }
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getDescrizione())){
			throw new Exception("Descrizione obbligatoria");
		    }
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getInizioValidita())){
			throw new Exception("Inizio validità obbligatorio");
		    }
		    if(StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getFineValidita())){
			throw new Exception("Fine validità obbligatorio");
		    }
		    
		}
		
	    }
	    
	    if(currstep > 3){
		
		if (StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getInizioValidita())) {
		    throw new Exception("Impostazioni: Inizio validità obbligatorio");
		}
		if (StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFineValidita())) {
		    throw new Exception("Impostazioni: Fine validità obbligatorio");
		}
		
		//mercato
		mercatidlivelloservizio.getLivelloServizioWizard().setStep4Mercato(mercatidlivelloservizio.getEntity().getMercatiD().getMercati().getDescrizione());
		
		//giornate
		StringBuilder sb = new StringBuilder();
		for(GiornateDaConfigurare giornata : mercatidlivelloservizio.getLivelloServizioWizard().getGiornateDaConfigurare()){
		    if(giornata.isChecked()){
		     sb.append(giornata.getDescrizione()).append("; ");
		    }
		}
		sb.delete(sb.length() - 2, sb.length());
		mercatidlivelloservizio.getLivelloServizioWizard().setStep4Giorni(sb.toString());
		
		
		//livello servizio e validità

		String step4LivelloServizio;
		if (mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio()
			.equals("associatiAlMercato")) {

		    String[] idsLivelloServizio = mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio()
			    .getLivelloDiServizioM().split("-");
		    MercatiLivelloServizio livServizio = mercatiLivelloServizioService.findById(new PkId(Integer.parseInt(idsLivelloServizio[0])));
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    
		    step4LivelloServizio = new Step4DataHelper(livServizio.getDataInizioValidita() != null ? sdf.format(livServizio.getDataInizioValidita()) : null, livServizio.getDataFineValidita() != null ? sdf.format(livServizio.getDataFineValidita()) : null).getLivelloServizioDescription(livServizio.getLivelloServizio().getDescrizione());
		    
		    mercatidlivelloservizio.getLivelloServizioWizard().setStep4LivelloServizio(step4LivelloServizio);
		    
		} else {
		    step4LivelloServizio = new Step4DataHelper(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getInizioValidita(),
				mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getFineValidita()).getLivelloServizioDescription(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioN());
		    
		    mercatidlivelloservizio.getLivelloServizioWizard().setStep4LivelloServizio(step4LivelloServizio);
		    
		}
				
		mercatidlivelloservizio.getLivelloServizioWizard().setStep4Validita(new Step4DataHelper(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getInizioValidita(),
				mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFineValidita()).getStringByData());
		
		//posteggi
		List<String> posteggi = new ArrayList<String>();
		for(String idposteggio : mercatidlivelloservizio.getListacodici()){
		    MercatiD p = mercatiDService.findById(new PkId(Integer.parseInt(idposteggio)));
		    posteggi.add(p.getCodiceposteggio());
		}

		StringBuilder sb2 = new StringBuilder();
		for(String p : posteggi){
		    sb2.append(p).append(" - ");
		}
		sb2.delete(sb2.length() - 2, sb2.length());
		mercatidlivelloservizio.getLivelloServizioWizard().setStep4Posteggi(sb2.toString());
		mercatidlivelloservizio.getLivelloServizioWizard().setStep4UsaMqPosteggio(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().isMqposteggio() ? "Usa i mq del posteggio" : "Non usare i mq del posteggio");
	    }
	    
	    mercatidlivelloservizio.getLivelloServizioWizard().setStep(currstep);
	    model.addAttribute("lvsCodiceUso", 0);
	    return "mercatidlivelloservizio/formM";
	}catch(Exception e){
	    model.addAttribute("scrollIntoStep",Boolean.FALSE);
	    copyErrorsToBindingResult(result, mercatidlivelloservizio.getEntity(), true, e);
	    return "mercatidlivelloservizio/formM";
	}
	
	
	
	
    }	

    @RequestMapping
    public String insertLivelloserviziPosteggi(Model model,
	    @ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio, BindingResult result,
	    SessionStatus status) {

	fixRenderCommandProperty(mercatidlivelloservizio);
	try {
	    mercatidlivelloservizioService.insertMultiplo(mercatidlivelloservizio.getEntity(), mercatidlivelloservizio.getListacodici());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidlivelloservizio.getEntity(), true, e);
	    fixRenderCommandProperty(mercatidlivelloservizio);
	    setPageAttributes(model, mercatidlivelloservizio.getEntity().getMercatiD().getId().getCodice());
	    return "mercatidlivelloservizio/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F" + "&status_msg=02";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio,
	    BindingResult result, SessionStatus status) {

	fixRenderCommandProperty(mercatidlivelloservizio);
	try {
	    mercatidlivelloservizioService.insert(mercatidlivelloservizio.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidlivelloservizio.getEntity(), true, e);
	    fixRenderCommandProperty(mercatidlivelloservizio);
	    setPageAttributes(model, mercatidlivelloservizio.getEntity().getMercatiD().getId().getCodice());
	    return "mercatidlivelloservizio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidlivelloservizio.getEntity().getId().getCodice() + "&status_msg=01";
    }
    
    @RequestMapping
    public String insertM(Model model, @ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio,
	    BindingResult result, SessionStatus status) {

	MercatiD p = mercatiDService.findById(new PkId(Integer.parseInt( mercatidlivelloservizio.getListacodici()[0])));
	
	try {
	    
	    
	    
	    boolean isGiornataChecked = false;
	    List<Integer> fkIdUsos = new ArrayList<Integer>();
	    for(GiornateDaConfigurare giornata : mercatidlivelloservizio.getLivelloServizioWizard().getGiornateDaConfigurare()){
		if(giornata.isChecked()){
		    isGiornataChecked = true;
		    fkIdUsos.add(giornata.getId());
		}
	    }
	    if(!isGiornataChecked){
		throw new Exception("Selezionare almeno una giornata");
	    }
	    
	    if("associatiAlMercato".equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio()) || 
		    "nuovoDaAssociare".equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio())){
		//DO NOTHING
	    }else{
		throw new Exception("Tipo livello di servizio non valorizzato correttamente");
	    }
	    
	    LivelloServizio nuovoLivelloServizio = null; //Ce lo teniamo in memoria solo in caso di nuovo livello di servizio
	    if ("associatiAlMercato"
		    .equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio())) {
		if (StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioM())) {
		    throw new Exception("Non è stato scelto un livello di servizio valido");
		}
		
		if(!mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio()
			.getLivelloDiServizioMDD().containsKey(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioM())){
		    throw new Exception("L'id fornito per il livello di servizio non corrisponde agli id in lista");
		}
		
		
	    } else {
		if (StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioN())) {
		    throw new Exception("Valorizzare il nuovo livello di servizio");
		}
		LivelloServizio livelloServizio = livelloServizioService.findById(new PkId(
			Integer.parseInt(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioNid())));
		if (livelloServizio != null) {
		    nuovoLivelloServizio = livelloServizio;
		} else {
		    throw new Exception("Livello servizio non valido, id livello servizio : " +
					mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioNid());
		}
	    }
	    
	    List<Integer> idposteggi = new ArrayList<Integer>();
	    for(String idposteggio : mercatidlivelloservizio.getListacodici()){
		idposteggi.add(Integer.parseInt(idposteggio));
	    }
	    BigDecimal fattoreMoltiplicativo = null;
	    if(!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFattoremoltiplicativo())){
		NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
	        Number number = nf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFattoremoltiplicativo());
		fattoreMoltiplicativo = new BigDecimal(number.toString());
	    }
	    Date dataInizio = null;
	    if(!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getInizioValidita())){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataInizio = sdf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getInizioValidita());
	    }
	    Date dataFine = null;
	    if(!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFineValidita())){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFine = sdf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().getFineValidita());
	    }
	    boolean usaMqPosteggio = mercatidlivelloservizio.getLivelloServizioWizard().getImpostazioni().isMqposteggio();
	    
	    if ("associatiAlMercato"
		    .equals(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTipoLivelloDiServizio())) {
		
		
		String[] idsLivelloServizio = mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getLivelloDiServizioM().split("-");
		List<Integer> idsLivelloServizioI = new ArrayList<Integer>();
		for(String idLivelloServizio : idsLivelloServizio){
		    Integer idLivelloServizioI = Integer.parseInt(idLivelloServizio);
		    idsLivelloServizioI.add(idLivelloServizioI);
		}
		
		mercatidlivelloservizioService.insertMultiPosteggioMultiUso(idposteggi, idsLivelloServizioI, 
			fattoreMoltiplicativo, dataInizio, dataFine, usaMqPosteggio);
	    
	    }else{
		
		//NUOVA ENTITY MercatiLivelloServizio
		MercatiLivelloServizio entity = new MercatiLivelloServizio();
		entity.setDescrizione(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getDescrizione());
		entity.setLivelloServizio(nuovoLivelloServizio);
		entity.setAttivo(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().isAttivo());
		
		BigDecimal tariffa = null;
		if (!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTariffa())) {
		    NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
		    Number number = nf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getTariffa());
		    tariffa = new BigDecimal(number.toString());
		}
		Date dataInizioLVS = null;
		if (!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getInizioValidita())) {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    dataInizioLVS = sdf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getInizioValidita());
		}
		Date dataFineLVS = null;
		if (!StringUtils.isBlank(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getFineValidita())) {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    dataFineLVS = sdf.parse(mercatidlivelloservizio.getLivelloServizioWizard().getScegliLivelloDiServizio().getFineValidita());
		}
		
		entity.setTariffa(tariffa);
		entity.setDataInizioValidita(dataInizioLVS);
		entity.setDataFineValidita(dataFineLVS);
		
		//NUOVA ENTITY MercatiDLivelloServizio
		MercatiDLivelloServizio dentity = new MercatiDLivelloServizio();
		dentity.setFattoreMoltiplicativo(fattoreMoltiplicativo);
		dentity.setUsaMqPosteggio(usaMqPosteggio);
		dentity.setDataInizio(dataInizio);
		dentity.setDataFine(dataFine);
		
		List<String> stringList = new ArrayList<String>(fkIdUsos.size());
	        for (Integer i : fkIdUsos) {
	            stringList.add(String.valueOf(i));
	        } 
		mercatiLivelloServizioService.insert(entity, dentity, stringList, idposteggi);
		
	    }
	    	    
	} catch (Exception e) {
	    model.addAttribute("scrollIntoStep",Boolean.FALSE);
	    copyErrorsToBindingResult(result, mercatidlivelloservizio.getEntity(), true, e);
	    return "mercatidlivelloservizio/formM";
	}
	status.setComplete();
	return "redirect:../mercatid/list.htm?codicemercato="+p.getMercati().getId().getCodice()+"&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiDLivelloServizioCommand mercatidlivelloservizio = new MercatiDLivelloServizioCommand();
	mercatidlivelloservizio.setDisplayMode(MercatiDLivelloServizioCommand.VIEW);
	MercatiDLivelloServizio entity = mercatidlivelloservizioService.findById(id);
	MercatiLivelloServizio servizio = mercatiLivelloServizioService.findById(new PkId(entity.getMercatiLivelloServizio().getId().getCodice()));
	entity.setMercatiLivelloServizio(servizio);
	mercatidlivelloservizio.setEntity(entity);
	mercatidlivelloservizio.setMercatiUso(entity.getMercatiLivelloServizio().getMercatiUso());
	fixRenderEntityProperty(mercatidlivelloservizio.getEntity());
	model.addAttribute("mercatidlivelloservizio", mercatidlivelloservizio);
	model.addAttribute("servizioattivo", servizio.getAttivo());
	setPageAttributes(model);
	setPageAttributes(model, mercatidlivelloservizio.getEntity().getMercatiD().getId().getCodice());
	return "mercatidlivelloservizio/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixRenderCommandProperty(mercatidlivelloservizio);
	try {
	    mercatidlivelloservizioService.update(mercatidlivelloservizio.getEntity());
	} catch (Exception e) {
	    mercatidlivelloservizio.setDisplayMode(MercatiDLivelloServizioCommand.VIEW);
	    copyErrorsToBindingResult(result, mercatidlivelloservizio.getEntity(), true, e);
	    fixRenderCommandProperty(mercatidlivelloservizio);
	    setPageAttributes(model, mercatidlivelloservizio.getEntity().getMercatiD().getId().getCodice());
	    return "mercatidlivelloservizio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidlivelloservizio.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatidlivelloservizio") MercatiDLivelloServizioCommand mercatidlivelloservizio, BindingResult result,
	    SessionStatus status) {

	MercatiDLivelloServizio objToDelete = mercatidlivelloservizioService.findById(mercatidlivelloservizio.getEntity().getId());
	try {
	    mercatidlivelloservizioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderCommandProperty(mercatidlivelloservizio);
	    return "mercatidlivelloservizio/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public ModelAndView ajaxRangeDate(@RequestParam("codiceservizio") Integer codiceservizio, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Map map = new HashMap();
	MercatiLivelloServizio mercatiLivelloServizio = mercatiLivelloServizioService.findById(new PkId(codiceservizio));
	map.put("da", Utilities.formatDate(mercatiLivelloServizio.getDataInizioValidita(), false));
	if (mercatiLivelloServizio.getDataFineValidita() != null) {
	    map.put("a", Utilities.formatDate(mercatiLivelloServizio.getDataFineValidita(), false));
	} else {
	    map.put("a", "");
	}
	return new ModelAndView("jsonView", map);
    }

    @Override
    protected void fixMergeEntityProperty(MercatiDLivelloServizio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiDLivelloServizio entity) {

	if (entity.getMercatiLivelloServizio() == null) {
	    entity.setMercatiLivelloServizio(new MercatiLivelloServizio());
	}
	if (entity.getMercatiD() == null) {
	    entity.setMercatiD(new MercatiD());
	}
    }

    private void fixRenderCommandProperty(MercatiDLivelloServizioCommand entity) {

	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
	fixRenderEntityProperty(entity.getEntity());
    }

    protected void setPageAttributes(Model model, Integer codiceposteggio) {

	List<MercatiDLivelloServizioHelper> lMercatiDLivelloServizioHelpers = mercatidlivelloservizioService
		.findMercatiDLivelloServizioHelperByPosteggio(codiceposteggio, false, true);
	model.addAttribute("mercatiDLivelloServizioHelpers", lMercatiDLivelloServizioHelpers);
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
