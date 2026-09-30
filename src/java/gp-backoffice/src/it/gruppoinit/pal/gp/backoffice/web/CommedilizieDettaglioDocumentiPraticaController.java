package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.RiferimentiDocumentiSelezionati;

@Controller
// @SessionAttributes("commissionidettagliodocumentipratica")
public class CommedilizieDettaglioDocumentiPraticaController extends BaseController<CommissioniDettaglioDocumentiPratica> {

    private static final String FORM = "commediliziedettagliodocumentipratica/form";
    @Autowired
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;

    @RequestMapping
    public String list(@RequestParam("idCommissioneR") Integer idCommissioneR, @RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    HttpServletRequest request) {

	CommissioniDettaglioDocumentiPratica commissione = commissioniDocumentiPraticheService.getByIdCommissioneRIdPratica(idCommissioneR,
		codiceIstanza);
	fixRenderEntityProperty(commissione);
	model.addAttribute("dati", commissione);
	setPageAttributes(model);
	return FORM;
    }

    public static class UpdateRequestModel {

	private Integer idCommissioneR;
	private Integer codiceIstanza;
	private List<Integer> documentiGenerali = new ArrayList<Integer>();
	private List<Integer> documentiEndo = new ArrayList<Integer>();
	private List<Integer> documentiMovimenti = new ArrayList<Integer>();

	public Integer getIdCommissioneR() {

	    return idCommissioneR;
	}

	public void setIdCommissioneR(Integer idCommissioneR) {

	    this.idCommissioneR = idCommissioneR;
	}

	public Integer getCodiceIstanza() {

	    return codiceIstanza;
	}

	public void setCodiceIstanza(Integer codiceIstanza) {

	    this.codiceIstanza = codiceIstanza;
	}

	public List<Integer> getDocumentiGenerali() {

	    return documentiGenerali;
	}

	public void setDocumentiGenerali(List<Integer> documentiGenerali) {

	    this.documentiGenerali = documentiGenerali;
	}

	public List<Integer> getDocumentiEndo() {

	    return documentiEndo;
	}

	public void setDocumentiEndo(List<Integer> documentiEndo) {

	    this.documentiEndo = documentiEndo;
	}

	public List<Integer> getDocumentiMovimenti() {

	    return documentiMovimenti;
	}

	public void setDocumentiMovimenti(List<Integer> documentiMovimenti) {

	    this.documentiMovimenti = documentiMovimenti;
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@ModelAttribute("updateRequest") UpdateRequestModel updateRequest, BindingResult result, HttpServletRequest request,
	    Model model, SessionStatus status) {

	int idCommissioneR = updateRequest.getIdCommissioneR();
	int codiceIstanza = updateRequest.getCodiceIstanza();
	String statusMsg = "";
	// try {
	RiferimentiDocumentiSelezionati documentiSelezionati = new RiferimentiDocumentiSelezionati();
	documentiSelezionati.setDocumentiIstanza(updateRequest.getDocumentiGenerali());
	documentiSelezionati.setDocumentiEndo(updateRequest.getDocumentiEndo());
	documentiSelezionati.setDocumentiMovimenti(updateRequest.getDocumentiMovimenti());
	commissioniDocumentiPraticheService.impostaDocumentiSelezionati(idCommissioneR, codiceIstanza, documentiSelezionati);
	statusMsg = "&status_msg=02";
	/*} catch (Exception e) {
	copyErrorsToBindingResult(result, updateRequest, e);
	CommissioniDettaglioDocumentiPratica commissione = commissioniDocumentiPraticheService
	    .getByIdCommissioneRIdPratica(updateRequest.getIdCommissioneR(), updateRequest.getCodiceIstanza());
	fixRenderEntityProperty(commissione);
	model.addAttribute("dati", commissione);
	setPageAttributes(model);
	}*/
	return "redirect:list.htm?idCommissioneR=" + //
		idCommissioneR + //
		"&codiceIstanza=" + //
		codiceIstanza + //
		statusMsg;
	/*
	// §§§BEGIN§§§
	CommissioniedilizieT entity = commissioniediliziet.getEntity();
	if (EntityUtils.getNestedProperty(entity, "commedilizieTipologie") != null) {
	    CommedilizieTipologie commedilizieTipologie = commedilizieTipologieService.findById(entity.getCommedilizieTipologie().getId());
	    entity.setCommedilizieTipologie(commedilizieTipologie);
	}
	try {
	    commissioniedilizietService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commissioniediliziet.getEntity(), true, e);
	    fixRenderEntityProperty(entity);
	    model.addAttribute("commissioniediliziet", commissioniediliziet);
	    if (commissioniediliziet.getEntity() != null) {
		List<CommedilizieConvocazioni> commedilizieConvocazionis = commedilizieConvocazioniService
			.findByCommissioneEdiliziaT(commissioniediliziet.getEntity());
		if (!commedilizieConvocazionis.isEmpty()) {
		    model.addAttribute("commedilizieConvocazionis", commedilizieConvocazionis);
		    model.addAttribute("sizeConvocazionis", commedilizieConvocazionis.size());
		}
	    }
	    return FORM;
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	 * */
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(CommissioniDettaglioDocumentiPratica entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(CommissioniDettaglioDocumentiPratica entity) {

	// TODO Auto-generated method stub
    }
}
