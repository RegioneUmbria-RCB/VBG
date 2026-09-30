package it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces;

public interface IOCKernel {

    public <T> T getBeanOfType(String type) throws ClassNotFoundException;

    public <T> T getBeanOfType(Class<T> classType) throws ClassNotFoundException;
}
