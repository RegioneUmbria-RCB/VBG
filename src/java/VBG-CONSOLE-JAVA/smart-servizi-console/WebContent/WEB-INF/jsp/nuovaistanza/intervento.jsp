<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<script type="text/javascript">
		<%-- 
		/*
		$(document).ready( function() {     
			$('#interventi_tree').fileTree({ 
				root: '/',
				script: '${pageContext.request.contextPath}/ajax/getAlberoProc.htm' }, 
			function(id,desc) {
				$("#intervento_id").val(id);
				$("#intervento_desc").val(desc);
			}); 
		});
		*/
		--%>
		$(function () {
			$("#interventi_tree").jstree({
				"plugins" : [ "themes", "json_data", "ui" ],
				"json_data" : {
					"ajax" : {
						"url" : "${pageContext.request.contextPath}/ajax/getAlberoProc2.htm",
						"data" : function (n) {
							return { "id" : n.attr ? n.attr("id") : "" }; 
						}
					}
				},
				"themes" : {
					"theme" : "classic",
					"dots" : true,
					"icons" : true
				},
				"ui" : {
					"select_limit" : 1
				}
			}).bind("select_node.jstree", function (event, data) { 
				if(data.rslt.obj.attr("rel") == 'FOLDER'){
					$("#intervento_id").val('');
					$("#intervento_desc").val('');
				}else{
					$("#intervento_id").val(data.rslt.obj.attr("id"));
					$("#intervento_desc").val(data.rslt.obj.attr("rel"));
				}
			});
		});
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand">
	
	<%@ include file="../includes/alertOneri.jsp" %>
	
	<div class="titolo_sezione"></div>
		<div id="sez_intervento_scelto" class="sezione">
			<table class="sezione_table">
				<tr>
					<td class="sezione_table_label"><label><fmt:message key='label.intervento' /></label></td>
					<td>
						<spring-form:textarea path="intervento.descrizione" rows="3" cols="80" readonly="true" id="intervento_desc" />
						<spring-form:hidden path="intervento.codice" id="intervento_id" />
					</td>
				</tr>
				<tr>
					<td class="sezione_table_label"><label><fmt:message key='label.oggetto' /></label></td>
					<td>
						<spring-form:textarea path="oggetto" rows="3" cols="80" />
					</td>
				</tr>
			</table>
		</div>
		<div class="titolo_sezione"><fmt:message key='label.lista-interventi' /></div>
		<div id="sez_interventi_tree" class="sezione">
			<div id="interventi_tree"></div>
		</div>
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>