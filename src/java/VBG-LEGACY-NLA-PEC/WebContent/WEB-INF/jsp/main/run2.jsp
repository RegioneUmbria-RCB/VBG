<%@ page import="it.gruppoinit.nlapec.util.PECMessage" %>
<%@ page import="it.gruppoinit.nlapec.util.ReportBean" %>
<%@ page import="it.gruppoinit.nlapec.util.ReportDettaglioBean" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>NLA PEC</title>
	</head>
	<body>
		<h1>NLA PEC</h1>
		<h3>Esito processamento:</h3>
		<%
		
			List<ReportBean> list = (List<ReportBean>)request.getAttribute("list"); 
			if(list!=null){
			    for(int i = 0;i < list.size();i++){
			    	ReportBean report = list.get(i);
					out.print("<b>Ente : " + report.getDescrizioneEnte()+"</b> (IdComune:"+report.getIdComune()+" - IdComuneAlias:"+report.getIdComuneAlias()+")");
					out.print("<br />");
					if (report.getError()!=null && !"".equalsIgnoreCase(report.getError())){
						out.print("<b>Errore : </b>" + report.getError());
						out.print("<br />");
					}
					if (report.getWarn()!=null && !"".equalsIgnoreCase(report.getWarn())){
						out.print("<b>Warning : </b>" + report.getWarn());
						out.print("<br />");
					}
					if (report.getDettaglio()==null || report.getDettaglio().size()==0){
						out.print("<b>Nessuna PEC processata per questo Comune </b>");
						out.print("<br />");
					} else {
						for(int j = 0;j < report.getDettaglio().size();j++){
							ReportDettaglioBean dettaglioReport = report.getDettaglio().get(j);
							out.print("Software : "+dettaglioReport.getSoftware());
							out.print("           Tipologie PEC elaborate : "+dettaglioReport.getListaTipologiePECProcessate());
							out.print("<br />");
							if (dettaglioReport.getError()!=null && !"".equalsIgnoreCase(dettaglioReport.getError())){
								out.print("Errore : "+dettaglioReport.getError());
								out.print("<br />");
							}
							out.print("Numero mail totali : "+dettaglioReport.getNumeroMessaggiTotali());
							out.print("<br />");
							out.print("Numero MIME Message : "+dettaglioReport.getNumeroMimeMessage());
							out.print("<br />");
							out.print("Numero MIME Message gia' elaborati: "+dettaglioReport.getNumeroMessageConErrori());
							out.print("<br />");
							if (dettaglioReport.getDettaglio()!=null){
								out.print("Dettaglio Mail lette : ");
								out.print("<br />");
								for(int k=0;k<dettaglioReport.getDettaglio().size();k++){
									out.print((String) dettaglioReport.getDettaglio().get(k));
									out.print("<br />");
								}
							}
							out.print("<br />");
						}
					}
					out.print("<b>------------------------------</b>");
					out.print("<br />");
			    }
			}
			
		%>	
	</body>
</html>