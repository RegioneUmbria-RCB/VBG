package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;


public class PkIdComparator implements Comparator<PkId> {

    @Override
    public int compare(PkId pk1, PkId pk2) {

	int cpVal = 0;
	if(pk1 == null){
	    if(pk2 == null){
		return 0;
	    }
	    else{
		return -1;
	    }
	}
	else{
	    if(pk2 == null){
		return 1;
	    }
	    else{
		String idCom1 = StringUtils.defaultString(pk1.getIdcomune());
		String idCom2 = StringUtils.defaultString(pk2.getIdcomune());
		cpVal = idCom1.compareTo(idCom2);
	    }
	}
	if(cpVal == 0){
	    Integer id1 = pk1.getCodice() == null ? 0 : pk1.getCodice();
	    Integer id2 = pk2.getCodice() == null ? 0 : pk2.getCodice();
	    cpVal = id1.compareTo(id2);
	}
	return cpVal;
    }
}

