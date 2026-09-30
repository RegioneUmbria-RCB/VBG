package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.annotations.validate.Validateable;

import java.lang.annotation.Annotation;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;

@Aspect
public class Validation implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(Validation.class);
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	if (this.applicationContext == null)
	    this.applicationContext = applicationContext;
    }

    private Validator getValidator(String validator) {

	return (Validator) applicationContext.getBean(validator);
    }

    @Before("execution(* (@org.springframework.stereotype.Controller *..*Controller).*(..)) && " + "@annotation(validateable)")
    public void validate(JoinPoint joinPoint, Validateable validateable) throws Throwable {

	Object target = null;
	BindingResult result = null;
	// Get validators' id/names
	String[] validators = validateable.validators();
	// Get the arguments
	Object[] args = joinPoint.getArgs();
	// Get annotations of the arguments
	Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
	// Check arguments array and retrieve the target bean and the BindingResult object
	for (int i = 0; i < args.length; i++) {
	    if (target != null && result != null)
		break;
	    else if (args[i] instanceof BindingResult)
		result = (BindingResult) args[i];
	    else if (annotations[i].length > 0) {
		for (Annotation annotation : annotations[i]) {
		    if (annotation.annotationType().isAssignableFrom(ModelAttribute.class)) {
			target = args[i];
			break;
		    }
		}
	    }
	}
	if (validators.length == 0)
	    validators = new String[] { StringUtils.uncapitalize(target.getClass().getSimpleName()).concat("Validator") };
	for (String validator : validators) {
	    // Get the validator bean from application context and validate the target bean
	    getValidator(validator).validate(target, result);
	    if (result.hasErrors())
		break;
	}
    }
}
