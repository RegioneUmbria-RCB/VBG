package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

@Controller
public class FoArjDomandeController extends BaseController<FoArjDomande> {

    @Autowired
    private FoArjDomandeService foArjDomandeService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	// Controllo se la verticalizzazione AREARISERVATA è attiva e il parametro AREA_RISERVATA_JAVA_ATTIVA
	// è attivo e diverso da vuoto
	boolean isAttiva = verticalizzazioneAreaRiservataService.isAttiva() && verticalizzazioneAreaRiservataService.isAreaRiservataJavaAttiva();
	if (isAttiva) {
	    List<FoArjDomande> domandes = foArjDomandeService.findDomandePerSoftware(null, null, null);
	    boolean export = createJMesaExport(request, response, domandes);
	    if (export) {
		return null;
	    }
	    model.addAttribute("domandes", domandes);
	} else {
	    return "redirect:../fodomande/list.htm";
	}
	return "foarjdomande/list";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String invalidaDomanda(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	FoArjDomande domanda = foArjDomandeService.findById(id);
	domanda.setDataInvalidata(Calendar.getInstance().getTime());
	domanda.setFlagInvalidata(Boolean.TRUE);
	foArjDomandeService.update(domanda);
	FlashMessages.getInfos().add("Aggiornamento eseguito");
	// §§§END§§§
	return "redirect:list.htm";
    }

    @RequestMapping
    public String invalidaTutte(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	setPageAttributes(model);
	List<FoArjDomande> domandes = foArjDomandeService.findDomandePerSoftware(ORMHelper.getSoftware(), null, null);
	for (FoArjDomande domanda : domandes) {
	    domanda.setDataInvalidata(Calendar.getInstance().getTime());
	    domanda.setFlagInvalidata(Boolean.TRUE);
	    foArjDomandeService.update(domanda);
	}
	FlashMessages.getInfos().add("Aggiornamento eseguito");
	// §§§END§§§
	return "redirect:list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(FoArjDomande entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoArjDomande entity) {

    }
}
