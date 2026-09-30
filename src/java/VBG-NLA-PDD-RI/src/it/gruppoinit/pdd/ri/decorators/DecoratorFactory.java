package it.gruppoinit.pdd.ri.decorators;

import org.apache.commons.lang.StringUtils;

public class DecoratorFactory {

    public static RIDecorator getDecorator(String msgDecorator, String idcomunealias) {

	if (StringUtils.isBlank(msgDecorator)) {
	    return null;
	}
	if (msgDecorator.equalsIgnoreCase("INSIEL_DECORATOR")) {
	    return new InsielDecorator(idcomunealias);
	}
	return null;
    }
}
