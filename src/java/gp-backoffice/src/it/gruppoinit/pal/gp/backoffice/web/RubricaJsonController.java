package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.Rubrica;
import it.gruppoinit.pal.gp.core.features.rubrica.IRubricaService;
import it.gruppoinit.pal.gp.core.features.rubrica.RisultatoRicercaRubrica;

@Controller
@RequestMapping("/rubricajson")
public class RubricaJsonController extends BaseJsonController<Rubrica> {

    @XmlRootElement(name = "ricercaRubricaResponseItem")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RicercaRubricaResponseItem {

	@XmlElement(name = "titolo")
	public String titolo;
	@XmlElement(name = "email")
	public String email;

	public RicercaRubricaResponseItem() {

	}

	public RicercaRubricaResponseItem(String titolo, String email) {

	    this.titolo = titolo;
	    this.email = email;
	}
    }

    @XmlRootElement(name = "ricercaRubricaResponse")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RicercaRubricaResponse {

	@XmlElement(name = "items")
	public List<RicercaRubricaResponseItem> items;

	public RicercaRubricaResponse() {

	    this.items = new ArrayList<RicercaRubricaResponseItem>();
	}
    }

    @Autowired
    private IRubricaService rubricaService;

    @RequestMapping(method = RequestMethod.GET)
    public void ricerca(@RequestParam("partial") String partial, HttpServletRequest request, HttpServletResponse response) {

	try {
	    int resultCount = 10; // La ricerca restituisce al massimo 10 risultati
	    RicercaRubricaResponse result = new RicercaRubricaResponse();
	    List<RisultatoRicercaRubrica> indirizzi = this.rubricaService.ricercaIndirizzo(partial, resultCount);
	    for (RisultatoRicercaRubrica i : indirizzi) {
		result.items.add(new RicercaRubricaResponseItem(i.getDescrizione(), i.getEmail()));
	    }
	    response.setContentType("application/json");
	    response.getOutputStream().write(toJsonBytes(result, false));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }
}
