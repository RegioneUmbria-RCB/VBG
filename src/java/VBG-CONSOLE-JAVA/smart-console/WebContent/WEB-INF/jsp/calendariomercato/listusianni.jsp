<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatipresenzeT.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatipresenzeT.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <div id="subcontent">
        <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../calendariomercato/listusianni" />
	    </jsp:include>
  		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="mercatipresenzeT" />
		</jsp:include>
		<div class="parametriDiv">
		  	<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${mercati.descrizione}" /></div>
			</div>
	    </div>
      
    
		
		 	<form name="mercatipresenzeTForm" action="listusianni.htm">
				<jmesa:springTableFacade
					id="mercatipresenzeT_id" 
					items="${mercatipresenzeTList}" 
					var="mercatipresenzeT_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="anno" titleKey="form.mercatipresenzeT.anno" />
                            <jmesa:htmlColumn property="mercatoUso.descrizione" titleKey="form.mercatipresenzeT.descrizione" />
							<jmesa:htmlColumn property="id.codice" titleKey="label.edit.record" sortable="false" filterable="false">
								<a href="view.htm?codice=${mercatipresenzeT_var.mercato.id.codice}&mercatouso.id.codice=${mercatipresenzeT_var.mercatoUso.id.codice}&anno=${mercatipresenzeT_var.anno}"  title="<fmt:message key="label.edit.record" />">
									<img src="../images/edit.gif" alt="<fmt:message key="label.edit.record" />" />
								</a>
								<a href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fcalendariomercato%2Fview.htm%3Fcodice%3D${mercatipresenzeT_var.mercato.id.codice}%26mercatouso.id.codice%3D${mercatipresenzeT_var.mercatoUso.id.codice}%26anno%3D${mercatipresenzeT_var.anno}%26step%3D2"  title="<fmt:message key="button.presenze.market" />">
									<img src="../images/bob.gif" alt="<fmt:message key="button.presenze.market" />" />
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
                <input type="hidden" value="${mercati.id.codice}" name="mercati.id.codice" />
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='listusianni.htm?mercati.id.codice=${mercati.id.codice}&';
				var _captionTab='<fmt:message key="form.mercatipresenzeT.title.list" />';
			</script> 
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?codice=${codice} ','');"><fmt:message key="button.new" /></a></li>
                <%--
                <c:if test="${mercati.flagContabilita==true}">
                <li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../calendariomercato/situazionecontabile.htm?codice=${codice} ','');"><fmt:message key="button.situazionecontabile" /></a></li>
				</c:if>
                --%>
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>