package it.gruppoinit.pal.gp.core.features.infrastructure.packages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

// @Service
// La classe va istanziata direttamente passando il percorso da scansionare e
// non va iniettata tramite il container
public class PackageScannerService implements IPackageScannerService {

    private String pathName;

    public PackageScannerService(String pathName) {

	this.pathName = pathName;
    }

    @Override
    public <T> List<Class<? extends T>> scan(Class<T> type) {

	List<Class<? extends T>> retVal = new ArrayList<Class<? extends T>>();
	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
	scanner.addIncludeFilter(new AssignableTypeFilter(type));
	for (BeanDefinition bd : scanner.findCandidateComponents(this.pathName)) {
	    try {
		@SuppressWarnings("unchecked")
		Class<? extends T> c = (Class<? extends T>) Class.forName(bd.getBeanClassName());
		retVal.add(c);
	    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return retVal;
    }
}
