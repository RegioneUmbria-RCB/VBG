package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.audti;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackListAuditLogger {

    public static Logger logger = LoggerFactory.getLogger("blacklist");

    public BlackListAuditLogger() {

	super();
    }
    
    
    public void scriviReportErroriChiusurePDeb(List<String> warningsL, String autore, int totalePDeb) {

	if(warningsL == null || warningsL.isEmpty()){
	    return;
	}
	
	StringBuilder sb = new StringBuilder();		
	if(StringUtils.isBlank(autore)){
	    sb.append("Eseguita operazione di chiusura su ").append(totalePDeb).append(" posizioni debitorie");
	}else{
	    sb.append("L'operatore ").append(autore).append(" ha eseguito un'operazione di chiusura su ").append(totalePDeb).append(" posizioni debitorie");
	}
	sb.append("\r\n");
	for(String w : warningsL){
	    sb.append(w).append("\r\n");
	}
	
	logger.error(sb.toString());
    }
    
    public void scriviReportErroriAccertamentiPDeb(List<String> warningsL, String autore, int totalePDeb) {

	if(warningsL == null || warningsL.isEmpty()){
	    return;
	}
	
	StringBuilder sb = new StringBuilder();		
	if(StringUtils.isBlank(autore)){
	    sb.append("Eseguita operazione di avvio accertamento su ").append(totalePDeb).append(" posizioni debitorie");
	}else{
	    sb.append("L'operatore ").append(autore).append(" ha eseguito un'operazione di avvio accertamento su ").append(totalePDeb).append(" posizioni debitorie");
	}
	sb.append("\r\n");
	for(String w : warningsL){
	    sb.append(w).append("\r\n");
	}
	
	logger.error(sb.toString());
    }
    
}

