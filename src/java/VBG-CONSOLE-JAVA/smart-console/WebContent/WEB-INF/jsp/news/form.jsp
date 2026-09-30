<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${news.id.codice==null}">
			<fmt:message key="news.label.nuovo_news.title" />
		</c:if> 
		<c:if test="${news.id.codice!=null}">
			<fmt:message key="news.label.dettaglio_news.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${news.id.codice==null}">
			<fmt:message key="news.label.nuovo_news.title" />
		</c:if> 
		<c:if test="${news.id.codice!=null}">
			<fmt:message key="news.label.dettaglio_news.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="news" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="news" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td>
						<spring-form:input  tabindex="2" id="data_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<spring-form:errors	path="data" cssClass="error" />
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="news.label.titolo" />
					</td>
					<td>
						<spring-form:textarea id="titolo_id" path="titolo" cols="70" />
						<spring-form:errors path="titolo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.sommario" />
					</td>
					<td>
						<spring-form:textarea id="sommario_id" path="sommario" cols="100" rows="4" />
						<spring-form:errors path="sommario" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="news.label.news" />
					</td>
					<td>
						<spring-form:textarea id="news_id" path="news" cols="70"/>
						<spring-form:errors path="news" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="news.label.software" />
					</td>
					<td>
						<spring-form:select path="software.codice">
						<spring-form:options items="${listSoftware}" itemLabel="descrizione" itemValue="codice"/>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="news.label.oggettiByFkNews1Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice1" />
	   					<jsp:param name="codiceOggetto" value="${news.oggettiByFkNews1Oggetti.id.codice}" />
	   					<jsp:param name="idComuneOggetto" value="${news.oggettiByFkNews1Oggetti.id.idcomune}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto1_id_codice" />
	   					<jsp:param name="nomefileId" value="oggetto1_nomefile" />
	   					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg"/>
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkNews1Oggetti.id.codice" id="oggetto1_id_codice"/>
    				<spring-form:hidden path="oggettiByFkNews1Oggetti.nomefile" id="oggetto1_nomefile"/>
    				<spring-form:errors path="oggettiByFkNews1Oggetti" cssClass="error"/>
					</td>
				</tr>					
				<tr>
					<td>
						<fmt:message key="news.label.oggettiByFkNews2Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice2" />
	   					<jsp:param name="codiceOggetto" value="${news.oggettiByFkNews2Oggetti.id.codice}" />
	   					<jsp:param name="idComuneOggetto" value="${news.oggettiByFkNews2Oggetti.id.idcomune}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto2_id_codice" />
	   					<jsp:param name="nomefileId" value="oggetto2_nomefile" />
	   					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg"/>
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkNews2Oggetti.id.codice" id="oggetto2_id_codice"/>
    				<spring-form:hidden path="oggettiByFkNews2Oggetti.nomefile" id="oggetto2_nomefile"/>
    				<spring-form:errors path="oggettiByFkNews2Oggetti" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="news.label.oggettiByFkNews3Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice3" />
	   					<jsp:param name="codiceOggetto" value="${news.oggettiByFkNews3Oggetti.id.codice}" />
	   					<jsp:param name="idComuneOggetto" value="${news.oggettiByFkNews3Oggetti.id.idcomune}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto3_id_codice" />
	   					<jsp:param name="nomefileId" value="oggetto3_nomefile" />
	   					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg"/>
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkNews3Oggetti.id.codice" id="oggetto3_id_codice"/>
    				<spring-form:hidden path="oggettiByFkNews3Oggetti.nomefile" id="oggetto3_nomefile"/>
    				<spring-form:errors path="oggettiByFkNews3Oggetti" cssClass="error"/>
					</td>
				</tr>					
				<tr>
					<td>
						<fmt:message key="news.label.oggettiByFkNews4Oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice4" />
	   					<jsp:param name="codiceOggetto" value="${news.oggettiByFkNews4Oggetti.id.codice}" />
	   					<jsp:param name="idComuneOggetto" value="${news.oggettiByFkNews4Oggetti.id.idcomune}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto4_id_codice" />
	   					<jsp:param name="nomefileId" value="oggetto4_nomefile" />
	   					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg"/>
    				</jsp:include>
    				<spring-form:hidden path="oggettiByFkNews4Oggetti.id.codice" id="oggetto4_id_codice"/>
    				<spring-form:hidden path="oggettiByFkNews4Oggetti.nomefile" id="oggetto4_nomefile"/>
    				<spring-form:errors path="oggettiByFkNews4Oggetti" cssClass="error"/>
					</td>
				</tr>													
			</table>
			<script type='text/javascript'>
				$('titolo_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${news.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${news.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		jQuery(document).ready(function(){
			initTextEditors();
		});
		/*
		tinyMCE.init({
  			mode: "exact",   			
			elements: "titolo_id, news_id", 
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
	</script>	
</body>
</html>