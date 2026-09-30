package it.gruppoinit.pal.gp.pay.web;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Another viable workaround is to disable binding to particular fields by setting disallowedFieldson WebDataBinder
 * globally:
 * 
 * <pre>

&#64;ControllerAdvice
&#64;Order(Ordered.LOWEST_PRECEDENCE)
public class BinderControllerAdvice {

    &#64;InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
         String[] denylist = new String[]{"class.*", "Class.*", "*.class.*", "*.Class.*"};
         dataBinder.setDisallowedFields(denylist);
    }

}

This works generally, but as a centrally applied workaround fix, may leave some loopholes, in particular if a controller sets disallowedFields locally through its own @InitBinder method, which overrides the global setting.
 * </pre>
 * 
 * @author riccardob
 *
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class BinderControllerAdvice {

    public BinderControllerAdvice() {

    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {

	String[] denylist = new String[] { "class.*", "Class.*", "*.class.*", "*.Class.*" };
	dataBinder.setDisallowedFields(denylist);
    }
}