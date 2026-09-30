<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Grafico posteggi</title>
</head>
<body>
<script type="text/javascript">
	var zeroX = 0;
	var zeroY = 0;
	function inserisciCoordinate(event){
		evt = event || window.event;
		var _zeroX = <%=(String)session.getAttribute("zeroX")%>;
		var _zeroY = <%=(String)session.getAttribute("zeroY")%>;
		if(_zeroX==null || _zeroY==null)return;
		var s = (evt.clientX + getViewportScrollX() -_zeroX) + ',' + (evt.clientY + getViewportScrollY() -_zeroY) ;
		var cod = prompt('Inserisci codice posteggio ('+s+') ('+_zeroX+','+_zeroY+')','0');
		location.href='inserisciCoordinate.htm?codiceMercato=${mercato.id.codice}&codicePosteggio='+cod+'&coords='+s+'&codiceUso=${uso.id.codice}';
	}
	
	function segnaZero(event){
		
		evt = event || window.event;
		zeroX = evt.clientX + getViewportScrollX();
		zeroY = evt.clientY + getViewportScrollY();
		if(confirm('Vuoi abilitare la configurazione dei posteggi? ('+zeroX+','+zeroY+')')){
			location.href='segnaZero.htm?codiceMercato=${mercato.id.codice}&zeroX='+zeroX+'&zeroY='+zeroY+'&codiceUso=${uso.id.codice}';
		}
	}
	
	function getViewportScrollX() {
		var scrollX = 0;
		if( document.documentElement && document.documentElement.scrollLeft ) { //IE standards compliant and W3C 
		scrollX = document.documentElement.scrollLeft;
		}
		else if( document.body && document.body.scrollLeft ) { // IE 6 not standards compliant
		scrollX = document.body.scrollLeft;
		}
		else if( window.pageXOffset ) { // older browsers
		scrollX = window.pageXOffset;
		}
		else if( window.scrollX ) { // Gecko and KHTML/Webkit browsers I think...
		scrollX = window.scrollX;
		}
		return scrollX;
	}

	function getViewportScrollY() {
		var scrollY = 0;
		if( document.documentElement && document.documentElement.scrollTop ) {
		scrollY = document.documentElement.scrollTop;
		}
		else if( document.body && document.body.scrollTop ) {
		scrollY = document.body.scrollTop;
		}
		else if( window.pageYOffset ) {
		scrollY = window.pageYOffset;
		}
		else if( window.scrollY ) {
		scrollY = window.scrollY;
		}
		return scrollY;
	}
	
	function visGraficoPosteggi(obj){
		location.href='graficoPosteggi.htm?codiceMercato=${mercato.id.codice}&codiceUso='+obj.options[obj.selectedIndex].value;
	}
	
	
	jQuery(function() {
		jQuery('#abilitaConf').click(function(event) {
			 	segnaZero(event);
			});
	});
	
	
	function dettaglioPosteggio(elemento, idPosteggio){
		
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/ajax/dettaglioPosteggio.htm?codicePosteggio='+idPosteggio+"&codiceUso=${uso.id.codice}&showInfo=true&idMercatipresenze=",
			  method: "POST",
			  context: document.body,			  
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  jQuery('#dialogPosteggio').dialog({ autoOpen: false, modal : true, width: 600, height: 400 }).html(data);
				  jQuery('#dialogPosteggio').dialog("open");
			  },
			  error: function(jqXHR, textStatus, errorThrown){
			  }
				  
		});
	 
	 }
	
</script>
<span class="titoloPagina"><fmt:message key="form.gestionegraficaposteggi.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../gestionepresenze/list" />
</jsp:include>
<div id="subcontent">
<span class="parametri"><fmt:message key="form.gestionegraficaposteggi.mercato" />:<label> ${mercato.descrizione}</label></span>
<c:if test="${not empty uso}">
<span class="parametri"><fmt:message key="form.gestionepresenze.mercatiUso" />:<label> ${uso.descrizione}</label></span>
</c:if>
<div id="piantina" style="position: relative;" >
	<c:if test="${not empty listaPosteggiConcessionari}">


	<a href="#" id="abilitaConf" title="Abilita configurazione posteggi" class="vbg-btn btn-aggiungi"
		style="position: absolute; width: 16px; height: 16px;"
		>
	</a>

	
	<img 
		id="mappamercato"
		src="<%=request.getContextPath() %>/file/ajaxDownload.htm?fileId=${mercato.oggetto.id.codice}"
		alt="Piantina ${mercato.descrizione}" 
		onclick="inserisciCoordinate(event); return false;"/>
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
	<div id="dialogPosteggio" style="display:hidden"></div>
</div>


	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>	


</body>
</html>