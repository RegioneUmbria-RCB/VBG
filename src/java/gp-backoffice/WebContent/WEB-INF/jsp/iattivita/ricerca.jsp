<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_attivita" />
	</title>
</head>
<body>

	<div id="subcontent">	
		<form id="iattivitaCommand" name="inviodati" action="/backend/iattivita/list.htm?software=CO" method="post">
		<%
		
		String[] vals = request.getParameterValues("id_attivita[]");
		if (vals!=null){
		for(String val: vals){
		    if(Utilities.isInteger(val)){
			Integer v = Integer.parseInt(val);
		    
		%>		    
				<input type="hidden"  id="codice_id" name="attivitaFilter.listaCodiceAttivita" value="<%= v %>"	/>	    
	<%
		    }
		}
	}
	%>	    
		    
	    </form>
	</div>
	
	<script type="text/javascript">
	
	jQuery(function(){
		  doSubmit('list.htm?isFunzioneDiUtility=${isFunzioneDiUtility}&codiceIstanza=${codiceIstanza}','',document.inviodati);
	});
	
	
	</script>
</body>
</html>