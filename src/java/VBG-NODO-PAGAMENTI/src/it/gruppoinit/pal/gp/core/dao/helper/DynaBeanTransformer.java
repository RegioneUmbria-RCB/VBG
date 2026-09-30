package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.hibernate.transform.ResultTransformer;

public class DynaBeanTransformer implements ResultTransformer {

    private static final long serialVersionUID = 1276759395290811577L;
    private DynaClass userDynaClass;

    public DynaBeanTransformer(DynaClass userDynaClass) {

	this.userDynaClass = userDynaClass;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {

	DynaBean dynaBean;
	try {
	    dynaBean = userDynaClass.newInstance();
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("DynaBeanTrasnformer#transformTuple: " + e.getMessage(), e);
	} catch (InstantiationException e) {
	    throw new RuntimeException("DynaBeanTrasnformer#transformTuple: " + e.getMessage(), e);
	}
	for (int i = 0; i < aliases.length; i++) {
	    String alias = aliases[i];
	    if (alias != null) {
		dynaBean.set(alias, tuple[i]);
	    }
	}
	return dynaBean;
    }

    @Override
    public List transformList(List collection) {

	return collection;
    }
}
