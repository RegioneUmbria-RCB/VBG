package it.sgp.middleware.security.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("comunisecuritysession")
@RequestMapping("/comunisecuritysession")
public class ComunisecuritySessionController extends BaseController<ComunisecuritySession> {

    @Autowired
    private ComunisecuritySessionService comunisecuritysessionService;

    @Override
    public Order getListDefaultOrder() {

	return new Order(Sort.Direction.DESC, "firstrequest");
    }

    /**
     * 
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/list.htm", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
	    HttpServletRequest request) {

	// https://www.bezkoder.com/thymeleaf-pagination-and-sorting-example/
	// https://www.baeldung.com/spring-thymeleaf-pagination
	PageRequest pageable = getPageFromRequest(page, size, request);
	Page<ComunisecuritySession> list = comunisecuritysessionService.findAllByExamplePaginated(pageable,
		getExampleFromRequest(model, request, new ComunisecuritySession()));
	model.addAttribute("sessioni", list);
	int totalPages = list.getTotalPages();
	if (totalPages > 0) {
	    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
	    model.addAttribute("pageNumbers", pageNumbers);
	}
	model.addAttribute("currentPage", page.orElse(1));
	return "comunisecuritysession/list";
    }

    @GetMapping("/delete.htm")
    public String delete(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	ComunisecuritySession objToDelete = comunisecuritysessionService.findById(codice);
	comunisecuritysessionService.delete(objToDelete);
	return "redirect:list.htm";
    }

    @PostMapping("/deleteAll.htm")
    public String deleteAll(Model model, HttpServletRequest request) {

	comunisecuritysessionService.deleteAll();
	return "redirect:list.htm";
    }

    @GetMapping("/deleteByDate.htm")
    public String deleteByDate(@RequestParam("data") String data, @RequestParam("saveTextFile") Boolean saveTextFile, Model model,
	    HttpServletRequest request) throws ParseException {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	comunisecuritysessionService.deleteBeforeDate(sdf.parse(data), saveTextFile);
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(ComunisecuritySession entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunisecuritySession entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
