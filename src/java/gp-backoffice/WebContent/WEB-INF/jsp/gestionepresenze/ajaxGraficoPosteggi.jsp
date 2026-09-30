<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<div id="piantina" style="position: relative;" >
	<c:if test="${not empty listaPosteggiConcessionari}">
	<img 
		id="mappamercato"
		src="<%=request.getContextPath() %>/file/ajaxDownload.htm?fileId=${mercato.oggetto.id.codice}"
		alt="Piantina ${mercato.descrizione}" 
		/>
	<ul style="margin: 0; padding: 0; list-style: none;">		
		<c:forEach items="${listaPosteggiConcessionari}" var="posteggioConcessionario" varStatus="varIndex">
		<c:if test="${not empty posteggioConcessionario.posteggio.coordinate}">
		<li>
			<img 
				<c:if test="${posteggioConcessionario.occupante ne null}">
				src="<%=request.getContextPath() %>/images/bob.png"
				</c:if>
				<c:if test="${posteggioConcessionario.occupante eq null}">
				src="<%=request.getContextPath() %>/images/success.png"
				</c:if> 
				alt="<fmt:message key="form.gestionepresenze.posteggio" /> ${posteggioConcessionario.posteggio.codiceposteggio}" 
				style="position: absolute; width: 16px; height: 16px; text-indent: -1000em; top: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[1]}px; left: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[0]}px;"
				onclick="dettaglioPosteggio(this,${posteggioConcessionario.posteggio.id.codice} )"
			/>			
		</li>
		</c:if>
		</c:forEach>		
	</ul>
	</c:if>
	<c:if test="${empty listaPosteggiConcessionari}">
		<span class="parametri">
		<fmt:message key="form.gestionepresenze.mercatiUso" />: 
			<select name="temp" onchange="visGraficoPosteggi(this);">
			<option value=" "><fmt:message key="label.select.default" /></option>
			<c:forEach items="${mercato.mercatiUsos}" var="usoCurrent">
			<option value="${usoCurrent.id.codice }">${usoCurrent.descrizione}</option>
			</c:forEach>
			</select>
		</span>
	</c:if>
</div>