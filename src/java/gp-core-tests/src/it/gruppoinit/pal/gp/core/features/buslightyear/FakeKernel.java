package it.gruppoinit.pal.gp.core.features.buslightyear;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;

public class FakeKernel implements IOCKernel {

    Map<String, Object> beansregistrati = new HashMap<String, Object>();

    public FakeKernel() {

    }

    @Override
    public <T> T getBeanOfType(String type) throws ClassNotFoundException {

	T result = (T) this.beansregistrati.get(type);
	if (result == null) {
	    throw new ClassNotFoundException("Classe " + type + " non trovata");
	}
	return result;
    }

    @Override
    public <T> T getBeanOfType(Class<T> type) throws ClassNotFoundException {

	return this.getBeanOfType(type.getName());
    }
}
