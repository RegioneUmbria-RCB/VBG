package it.gruppoinit.pal.gp.core.features.infrastructure.ioc;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;

@Service
public class IOCKernelServiceImpl implements IOCKernel {

    @Override
    public <T> T getBeanOfType(String type) throws ClassNotFoundException {

	Class<?> forName = Class.forName(type);
	Map<String, Collection<T>> ret = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(forName);
	if (ret.isEmpty()) {
	    throw new RuntimeException("Non è stato trovato il bean della classe " + type.getClass());
	}
	return (T) ret.values().iterator().next();
    }

    @Override
    public <T> T getBeanOfType(Class<T> classType) throws ClassNotFoundException {

	Map<String, Collection<T>> ret = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(classType);
	if (ret.isEmpty()) {
	    throw new RuntimeException("Non è stato trovato il bean della classe " + classType);
	}
	return (T) ret.values().iterator().next();
    }
}
