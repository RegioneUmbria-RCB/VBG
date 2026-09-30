package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.Date;

public class QueriesUtil {
    
    
    public static String buildQueryMercatiForAutorizzazioni(boolean isconcessionari, boolean isspuntisti, Date dalladata, Date alladata, int[] idsmercati){
	return buildQueryMercati(isconcessionari, isspuntisti, dalladata, alladata, idsmercati).replace("(:campi)","A.ID as codiceAutorizzazione, A.FK_CODICEANAGRAFE AS titolare, COALESCE(A.CODICEOCCUPANTE,A.FK_CODICEANAGRAFE) AS occupante, C.CODICEMERCATO as codicemercato");
    }
    public static String buildQueryMercatiForSoftwareAndComune(boolean isconcessionari, boolean isspuntisti, Date dalladata, Date alladata, int[] idsmercati){
	return buildQueryMercati(isconcessionari, isspuntisti, dalladata, alladata, idsmercati).replace("(:campi)","C.SOFTWARE, C.CODICECOMUNE");
    }
    
    public static String buildQueryMercati(boolean isconcessionari, boolean isspuntisti, Date dalladata, Date alladata, int[] idsmercati){
	
	if(!isconcessionari && !isspuntisti){
	    throw new RuntimeException("almeno uno tra concessionari e spuntisti deve essere true");
	}
	
	StringBuilder sb = new StringBuilder();
	
	//CASO DATE NON VALORIZZATE
	if(dalladata == null && alladata == null){
	    
	    String mercatiWhereCondition = "";
	    if(idsmercati != null && idsmercati.length > 0){
		mercatiWhereCondition = " AND C.CODICEMERCATO IN (:idsmercati) ";
	    }
	    
	    
	    if(isconcessionari){
		sb.append(QueriesConstants.COMUNICAZIONIMERCATICONCSTISELECT).append(mercatiWhereCondition);
		if(isspuntisti){
		    sb.append(" UNION ");
		}
	    }
	    if(isspuntisti){
		sb.append(QueriesConstants.COMUNICAZIONIMERCATISPUNTISTISELECT).append(mercatiWhereCondition);
	    }
	    
	    return sb.toString();
	}
	
	StringBuilder andcondition = new StringBuilder();
	//CASO DATE VALORIZZATE
	if(dalladata != null){
	    andcondition.append(" AND T.DATAREGISTRAZIONE >= :dalladata ");
	}
	if(alladata != null){
	    andcondition.append(" AND T.DATAREGISTRAZIONE <= :alladata ");
	}
	
	if(idsmercati != null && idsmercati.length > 0){
	    andcondition.append(" AND C.CODICEMERCATO IN (:idsmercati) ");	    
	}
	
	sb.append(QueriesConstants.COMUNICAZIONIMERCATICONCSPUNTSELECT).append(andcondition);
	if (!isconcessionari) {
	    sb.append(" AND B.SPUNTISTA = 1 ");
	}
	if (!isspuntisti) {
	    sb.append(" AND B.SPUNTISTA = 0 ");
	}
	return sb.toString();

    }
    
}
