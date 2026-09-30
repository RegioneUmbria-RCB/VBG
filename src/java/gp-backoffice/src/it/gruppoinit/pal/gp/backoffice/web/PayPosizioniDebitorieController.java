package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.helper.PayPosizioniDebitorieFilter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;

@Controller
@SessionAttributes(value = { "payPosizioniDebitorieFilter" })
public class PayPosizioniDebitorieController extends BaseController<DettPosizioneDebitoria> {

    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private MercatiAppService mercatiAppService;

    @RequestMapping
    public String createlist(Model model, HttpServletRequest request, HttpServletResponse response) {

	PayPosizioniDebitorieFilter payPosizioniDebitorieFilter = new PayPosizioniDebitorieFilter();
	model.addAttribute("payPosizioniDebitorieFilter", payPosizioniDebitorieFilter);
	return "redirect:list.htm";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("payPosizioniDebitorieFilter") PayPosizioniDebitorieFilter payPosizioniDebitorieFilter,
	    HttpServletRequest request, HttpServletResponse response) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	populateFilter(request, payPosizioniDebitorieFilter);
	//	GenerateTable<PayPosizioniDebitorie> inventarioprocedimentiTable = new PayPosizioniDebitorieTable(payPosizioniDebitorieFilter,
	//		payPosizioniDebitorieService);
	//	String htmlTable = inventarioprocedimentiTable.createJMesaList(request, response, "label.posizioni_debitorie.title", "posizionidebitorie_id",
	//		true);
	//	if (htmlTable == null) {
	//	    return null;
	//	}
	//	model.addAttribute("htmltable", htmlTable);
	return "payposizionidebitorie/list";
    }

    private void populateFilter(HttpServletRequest request, PayPosizioniDebitorieFilter filter) {

	String presenze = StringUtils.defaultString(request.getParameter("presenze"));
	String pagato = StringUtils.defaultString(request.getParameter("pagato"));
	filter.setDescrizione(StringUtils.defaultString(request.getParameter("descrizione")));
	filter.setRichiedente(StringUtils.defaultString(request.getParameter("nominativo")));
	request.setAttribute("presenze", presenze);
	request.setAttribute("pagato", pagato);
	request.setAttribute("descrizione", StringUtils.defaultString(request.getParameter("descrizione")));
	request.setAttribute("nominativo", StringUtils.defaultString(request.getParameter("nominativo")));
	request.setAttribute("id", StringUtils.defaultString(request.getParameter("id")));
	if (StringUtils.isBlank(presenze)) {
	    filter.setPresenza(null);
	} else {
	    filter.setPresenza(presenze.equalsIgnoreCase("1"));
	}
	if (StringUtils.isBlank(pagato)) {
	    filter.setPagato(null);
	} else {
	    filter.setPagato(pagato.equalsIgnoreCase("1"));
	}
    }

    @RequestMapping
    public String ajaxDettaglioPosizione(@RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	// StatoPagamentoPosDebHelper ppds = nodoPagamentiService.getStatoPagamentoPosDebHelper(idPosizioneDebitoria);
	//	PayPosizioniDebitorie ppd = payPosizioniDebitorieService.findById(new PkId(idPosizioneDebitoria));
	//	model.addAttribute("pd", ppd);
	// model.addAttribute("stato", ppds);
	return "payposizionidebitorie/ajaxDettaglioPosizione";
    }

    @RequestMapping
    public void ajaxSegnaPosizioneDebitoriaPagatoOffline(@RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria,
	    @RequestParam("riferimentiPagamento") String riferimentiPagamento, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	//	try {
	//	    if (StringUtils.isBlank(riferimentiPagamento)) {
	//		riferimentiPagamento = "Pagato offline con altre forme di pagamento";
	//	    }
	//	    nodoPagamentiService.updatePosizioneDebitoriaSegnaPagata(idPosizioneDebitoria, riferimentiPagamento);
	//	    response.getOutputStream().write("OK".getBytes());
	//	} catch (FunzioneBusinessRemotaException e) {
	//	    response.getOutputStream().write(e.getMessage().getBytes());
	//	}
    }

    @RequestMapping
    public void ajaxAnnullaPosizioneDebitoria(@RequestParam("idPosizioneDebitoria") Integer idPosizioneDebitoria, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	//	try {
	//	    nodoPagamentiService.annullaPosizioneDebitoria(idPosizioneDebitoria);
	//	    response.getOutputStream().write("OK".getBytes());
	//	} catch (FunzioneBusinessRemotaException e) {
	//	    response.getOutputStream().write(e.getMessage().getBytes());
	//	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(DettPosizioneDebitoria entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(DettPosizioneDebitoria entity) {

	// TODO Auto-generated method stub
    }
}
