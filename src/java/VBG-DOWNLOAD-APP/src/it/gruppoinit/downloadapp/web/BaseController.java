package it.gruppoinit.downloadapp.web;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

	String[] denylist = new String[] { "class.*", "Class.*", "*.class.*", "*.Class.*" };
	binder.setDisallowedFields(denylist);
    }
}
