<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${bachecalavoroconfigurazione.idcomune==null}">
			<fmt:message key="bachecalavoroconfigurazione.label.nuovo_bachecalavoroconfigurazione.title" />
		</c:if> 
		<c:if test="${bachecalavoroconfigurazione.idcomune!=null}">
			<fmt:message key="bachecalavoroconfigurazione.label.dettaglio_bachecalavoroconfigurazione.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${bachecalavoroconfigurazione.idcomune==null}">
			<fmt:message key="bachecalavoroconfigurazione.label.nuovo_bachecalavoroconfigurazione.title" />
		</c:if> 
		<c:if test="${bachecalavoroconfigurazione.idcomune!=null}">
			<fmt:message key="bachecalavoroconfigurazione.label.dettaglio_bachecalavoroconfigurazione.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="bachecalavoroconfigurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bachecalavoroconfigurazione" />
		    </jsp:include>		    
			<table>		
				<tr>
					<td class="titoloSezione" colspan="2">
						<fmt:message key="bachecalavoroconfigurazione.label.sezioneoffrolavoro.table" />
					</td>
				</tr>	
				<tr>
					<td width="20%">
						<fmt:message key="bachecalavoroconfigurazione.label.offrolavoroindicazioni" />
					</td>
					<td width="80%">
						<spring-form:textarea id="offrolavoroindicazioni_id" path="offrolavoroindicazioni"/>
						<spring-form:errors path="offrolavoroindicazioni" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="bachecalavoroconfigurazione.label.offrolavoroemail" />
					</td>
					<td>
						<spring-form:input id="offrolavoroemail_id" path="offrolavoroemail" size="50" />
						<spring-form:errors path="offrolavoroemail" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="bachecalavoroconfigurazione.label.oggettiByFkBachecalavconf1Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice1" />
	   					<jsp:param name="codiceOggetto" value="${bachecalavoroconfigurazione.oggettiByFkBachecalavconf1Oggetti.id.codice}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto1_id_codice" />	
	   					<jsp:param name="nomefileId" value="oggetto1_nomefile" />
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkBachecalavconf1Oggetti.id.codice" id="oggetto1_id_codice"/>
    				<spring-form:hidden path="oggettiByFkBachecalavconf1Oggetti.nomefile" id="oggetto1_nomefile"/>
    				<spring-form:errors path="oggettiByFkBachecalavconf1Oggetti" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td class="titoloSezione" colspan="2">
						<fmt:message key="bachecalavoroconfigurazione.label.sezionecercolavoro.table" />
					</td>
				</tr>
				<tr>
					<td width="20%">
						<fmt:message key="bachecalavoroconfigurazione.label.cercolavoroindicazioni" />
					</td>
					<td width="80%">
						<spring-form:textarea id="cercolavoroindicazioni_id" path="cercolavoroindicazioni" />
						<spring-form:errors path="cercolavoroindicazioni" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="bachecalavoroconfigurazione.label.cercolavoroemail" />
					</td>
					<td>
						<spring-form:input id="cercolavoroemail_id" path="cercolavoroemail" size="50" />
						<spring-form:errors path="cercolavoroemail" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavoroconfigurazione.label.oggettiByFkBachecalavconf2Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice2" />
	   					<jsp:param name="codiceOggetto" value="${bachecalavoroconfigurazione.oggettiByFkBachecalavconf2Oggetti.id.codice}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto2_id_codice" />	
	   					<jsp:param name="nomefileId" value="oggetto2_nomefile" />
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkBachecalavconf2Oggetti.id.codice" id="oggetto2_id_codice"/>
    				<spring-form:hidden path="oggettiByFkBachecalavconf2Oggetti.nomefile" id="oggetto2_nomefile"/>
    				<spring-form:errors path="oggettiByFkBachecalavconf2Oggetti" cssClass="error"/>
					</td>
				</tr>	
				</table>		
			
			<script type='text/javascript'>
				$('offrolavoroindicazioni_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${bachecalavoroconfigurazione.idcomune==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${bachecalavoroconfigurazione.idcomune!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>				
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">

		/*
		tinyMCE.init({
  			mode: "exact",   			
			elements: "offrolavoroindicazioni_id, cercolavoroindicazioni_id", 
  			theme: "advanced",
  			theme_advanced_toolbar_location: "top",
  			theme_advanced_toolbar_align: "left",  			
  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
  			forced_root_block : false,
  	        force_br_newlines : true,
  	        force_p_newlines : false
		});
		*/
		initTextEditors();
	</script>	
</body>
</html>