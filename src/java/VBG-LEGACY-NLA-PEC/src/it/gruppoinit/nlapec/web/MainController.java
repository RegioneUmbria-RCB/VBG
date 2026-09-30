package it.gruppoinit.nlapec.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.nlapec.service.PECReader;
import it.gruppoinit.nlapec.util.ReportBean;

@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private PECReader pecReader;

    @RequestMapping
    public void run(Model model) throws Exception {

	log.debug("run...");
	//	List<PECMessage> list = pecReader.run();
	//	model.addAttribute("list", list);
	log.debug("run...done!");
    }

    @RequestMapping
    public void run2(Model model, @RequestParam("alias") String alias, HttpServletRequest request) throws Exception {

	log.debug("run2...");
	List<ReportBean> listaReport = pecReader.mainRun(alias);
	model.addAttribute("list", listaReport);
	log.debug("run2...done!");
    }
}
