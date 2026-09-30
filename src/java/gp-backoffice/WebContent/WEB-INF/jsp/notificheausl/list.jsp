<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.notificheausl.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.notificheausl.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="notificheForm" action="list.htm">
				<jmesa:springTableFacade
					id="notiche_id" 
					items="${notificheAuslList}" 
					var="notifiche_var"
					exportTypes="pdfp,excel,csv"
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateNotificheFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="id.codNotifica" titleKey="form.notificheausl.codicenotifica" />
                            <jmesa:htmlColumn property="ragSoc" titleKey="form.notificheausl.ragionesociale" />
                            <jmesa:htmlColumn property="indirizzo" titleKey="form.notificheausl.indirizzo" />
                            <jmesa:htmlColumn property="dataPerv" titleKey="form.notificheausl.dataperv" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" width="10%" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataNotificaCustomFilter"/>
							<jmesa:htmlColumn property="rapLeg" titleKey="form.notificheausl.rappresentantelegale" />
							<jmesa:htmlColumn property="localita" titleKey="form.notificheausl.localita" />
							<jmesa:htmlColumn property="descAtt" titleKey="form.notificheausl.descrizioneattivita" />
							<jmesa:htmlColumn property="tipoNotifica" titleKey="form.notificheausl.tiponotifica" />
							<jmesa:htmlColumn property="protSian" titleKey="form.notificheausl.protocollosian" />
							<jmesa:htmlColumn property="id" titleKey="label.edit.record" sortable="false" filterable="false">
								<a class="vbg-btn btn-dettaglio" href="view.htm?id=${notifiche_var.id.codNotifica}" title="<fmt:message key="label.edit.record" /> ${notifiche_var.id.codNotifica}">
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="" name="filterIndirizzo" />
				<input type="hidden" value="" name="filterRagSoc"/>
			</form>
			<%
			String filterIndirizzo="";
			String filterRagSoc="";
			if(request.getAttribute("filterIndirizzo")!=null){
			    filterIndirizzo=(String)request.getAttribute("filterIndirizzo");
			}
			if(request.getAttribute("filterRagSoc")!=null){
			    filterRagSoc=(String)request.getAttribute("filterRagSoc");
			}
			%>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?filterRagSoc=&filterIndirizzo=';
				var _captionTab='<fmt:message key="form.notificheausl.title.list" />';
			</script>
			<script type="text/javascript">
		   		function getElementByClass(theClass) { 
		   		 //Populate the array with all the page tags 
		   		 var allPageTags=document.getElementsByTagName("tr"); 
		   		 //Cycle through the tags using a for loop 
		   		 for (var i=0; i<allPageTags.length; i++) { 
		   		 //Pick out the tags with our class name 
		   		 if (allPageTags[i].className==theClass) {
			   		return allPageTags[i]; 
		   		 } 
		   		 } 
		   		} 
		   	var valueQueryRagSoc="<%=filterRagSoc%>";
			var valueQueryIndirizzo="<%=filterIndirizzo%>";
			<%if(request.getAttribute("filterRagSoc")!=null && !((String) request.getAttribute("filterRagSoc")).equals("")){%>
				var tr=getElementByClass("filter");
				var div=tr.getElementsByTagName("div");
		   		jQuery.jmesa.createDynFilter(div[1], 'notiche_id','ragSoc');
		   		document.getElementById("dynFilterInput").value=valueQueryRagSoc;
		   		var changedValue = valueQueryRagSoc;
		   		jQuery.jmesa.addFilterToLimit('notiche_id', 'ragSoc', changedValue);
		   		jQuery.jmesa.onInvokeAction('notiche_id', 'filter');
                jQuery.jmesa.createDynFilter(div[1], 'notiche_id','ragSoc');
		   		$('dynFilterInput').focus(); 
		   	<%}else{}%>
		   	<%if(request.getAttribute("filterIndirizzo")!=null && !((String) request.getAttribute("filterIndirizzo")).equals("")){%>
			   	var tr2=getElementByClass("filter");
				var div2=tr2.getElementsByTagName("div");
		   		jQuery.jmesa.createDynFilter(div2[2], 'notiche_id','indirizzo');
		   		document.getElementById("dynFilterInput").value=valueQueryIndirizzo;
		   		var changedValue2 = valueQueryIndirizzo;
		   		jQuery.jmesa.addFilterToLimit('notiche_id', 'indirizzo', changedValue2);
		   		jQuery.jmesa.onInvokeAction('notiche_id', 'filter');
                jQuery.jmesa.createDynFilter(div2[2], 'notiche_id','indirizzo');
		   		$('dynFilterInput').focus(); 
		   	<%}else{}%>
		   	
			</script>
		</div>
		<div id="functions">
			<ul>
 				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li> 
				<li><a href="javascript:doHref('createImport.htm','');"><fmt:message key="button.importexcel" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>