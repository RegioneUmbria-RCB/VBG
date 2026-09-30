package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import java.util.Collection;
import java.util.Map;

import org.springframework.web.context.ContextLoader;

public class BaseAsyncHelper {

    @SuppressWarnings("unchecked")
    public <T> T getBeanOfType(String type) throws ClassNotFoundException {

	Class<?> forName = Class.forName(type);
	Map<String, Collection<T>> ret = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(forName);
	if (ret.isEmpty()) {
	    throw new ClassNotFoundException("Non è stato trovato il bean della classe " + type.getClass());
	}
	return (T) ret.values().iterator().next();
    }
}
