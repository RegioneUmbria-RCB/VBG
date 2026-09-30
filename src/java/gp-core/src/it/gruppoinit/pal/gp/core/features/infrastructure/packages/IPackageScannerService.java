package it.gruppoinit.pal.gp.core.features.infrastructure.packages;

import java.util.List;

public interface IPackageScannerService {

    public <T> List<Class<? extends T>> scan(Class<T> type);
}