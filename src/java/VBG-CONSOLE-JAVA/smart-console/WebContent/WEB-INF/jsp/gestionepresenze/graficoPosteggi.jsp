<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>grafico posteggi</title>
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
	<img 
		src="<%=request.getContextPath() %>/images/add.gif" 
		alt="Abilita configurazione posteggi" 
		style="position: absolute; width: 16px; height: 16px;"
		onclick="segnaZero(event); return false;"/>
	<img 
		src="<%=request.getContextPath() %>/file/ajaxDownload.htm?fileId=${mercato.oggetto.id.codice}"
		alt="Piantina ${mercato.descrizione}" 
		onclick="inserisciCoordinate(event); return false;"/>
	<ul style="margin: 0; padding: 0; list-style: none;">		
		<c:forEach items="${listaPosteggiConcessionari}" var="posteggioConcessionario" varStatus="varIndex">
		<c:if test="${not empty posteggioConcessionario.posteggio.coordinate}">
		<li>
			<img 
				<c:if test="${posteggioConcessionario.occupante ne null}">
				src="<%=request.getContextPath() %>/images/error.gif"
				</c:if>
				<c:if test="${posteggioConcessionario.occupante eq null}">
				src="<%=request.getContextPath() %>/images/success.gif"
				</c:if> 
				alt="<fmt:message key="form.gestionepresenze.posteggio" /> ${posteggioConcessionario.posteggio.codiceposteggio}" 
				style="position: absolute; width: 16px; height: 16px; text-indent: -1000em; top: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[1]}px; left: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[0]}px;"
				onmouseover="$('posteggio_dettaglio${varIndex.index}').appear()"
			/>
			<span id="posteggio_dettaglio${varIndex.index}" class="posteggi_dettaglio" style="display: none; text-align: left; position: absolute; top: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[1]}px; left: ${fn:split(posteggioConcessionario.posteggio.coordinate,',')[0]+16}px;">
			    <div class="posteggi_dettaglio_header">
			    	<img 
						src="<%=request.getContextPath() %>/images/cross.gif" 
						alt="Chiudi" 
						onclick="$('posteggio_dettaglio${varIndex.index}').style.display='none'"
					/>
			    	<label>
			    	<fmt:message key="form.gestionepresenze.posteggio" /> ${posteggioConcessionario.posteggio.codiceposteggio}
					</label>
			    </div>
			    <label><fmt:message key="form.gestionepresenze.concessionario" />: ${posteggioConcessionario.occupante.descrizioneRichiedente}</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.tipo" />: ${posteggioConcessionario.posteggio.tipoSpazio.tipospazio}</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.larghezza" />: ${posteggioConcessionario.posteggio.larghezza} m</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.lunghezza" />: ${posteggioConcessionario.posteggio.lunghezza} m</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.superficie" />: ${posteggioConcessionario.posteggio.superficie} m<small><sup>2</sup></small></label><br />
  				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.note" />: ${posteggioConcessionario.posteggio.note}</label>		
			</span>
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
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>

</div>	
</body>
</html>