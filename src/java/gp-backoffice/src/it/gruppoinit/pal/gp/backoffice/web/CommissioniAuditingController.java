package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.models.CommissioniAuditing;

@Controller
public class CommissioniAuditingController extends BaseController<CommissioniAuditing> {

    @Autowired
    private ICommissioniAuditingService commissioniAuditingService;

    @RequestMapping
    public String list(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<CommissioniAuditing> listAuditing = commissioniAuditingService.findByCodiceCommissione(codice);
	model.addAttribute("listAuditing", listAuditing);
	model.addAttribute("codiceCommissione", codice);
	return "commissioniauditing/list";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(CommissioniAuditing entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(CommissioniAuditing entity) {

	// TODO Auto-generated method stub
    }
}
