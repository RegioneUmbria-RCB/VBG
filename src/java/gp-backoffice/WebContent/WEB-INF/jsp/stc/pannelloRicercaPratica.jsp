<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.ricerca_istanza" />
	</title>
</head>
<body>
	<div style="padding: 20px;">
	<span class="titoloPagina">
		<fmt:message key="label.ricerca_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanze" />
		    </jsp:include>
		    <jsp:include page="../includes/history.jsp">
		    	<jsp:param name="path" value="../stc/popupPannelloRicerca" />
			</jsp:include>
		    <form name="inviodati" id="inviodati" action="">
			<table>
				<tr>
					<td>
						<fmt:message key="label.modulo" />
					</td>
					<td>
						<select id="software_var_hidden" name="modulo">
							<option value=""><fmt:message key="label.select.default" /></option>
							<c:if test="${not empty softwareList}">
								<c:forEach items="${ softwareList }" var="soft">
									<option value="${soft.codice }">${soft.descrizione}</option>
								</c:forEach>
							</c:if>
						</select>
					
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.numeroistanza" />
					</td>
					<td>
						<input type="text" id="numeroistanza_id" name="numeroistanza" size="20" />
						
					</td>
				</tr>
			</table>
			</form>
			<script type='text/javascript'>
				function ricercaPratica(){
					ajaxHistorySet(URLDecode('${_urlback}'));
				}
				function ajaxHistorySet(url){
					
					var jhqr = jQuery.ajax({
						  url: '../history/ajaxSet.htm?ReturnTo='+url,
						  context: document.body,
						  cache: false,				
						  dataType: "html",
						  success: function(data) { 	
							  doSubmit('popupcercapratica.htm','',document.inviodati);	
							} 
						});
					
				}
				
				var executed = false;
				jQuery("*").keypress(function(e) {
			  	  	var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() == 13) {
					if(!executed){
							executed=true;
							ricercaPratica();
						}
					}
			});
			</script>	
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:ricercaPratica()"><fmt:message key="button.ok" /></a></li>
			<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	</div>
</body>
</html>