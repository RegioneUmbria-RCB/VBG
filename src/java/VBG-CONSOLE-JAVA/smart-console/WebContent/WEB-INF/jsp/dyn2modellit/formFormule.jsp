<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService.EventoModelli"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.dettaglio_formula" />
	</title>
</head>
<body>

	<script src="${pageContext.request.contextPath}/scripts/codemirror/lib/codemirror.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/codemirror/lib/codemirror.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/codemirror/mode/clike/clike.js"></script>	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/codemirror/theme/neat.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/codemirror/formule.css" />
	<script type="text/javascript">
	
	var g_codeMIrror = null;

	jQuery(document).ready(function () {
        g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('scriptString_id'), {
            mode: "text/x-csharp",
            lineNumbers: true,
            theme: 'neat',
            tabMode: 'default',
            tabSize: 4,
            smartIndent: false,
            indentUnit: 4,
            indentWithTabs: true,
            enterMode: 'keep',
            onCursorActivity: function () {
                g_codeMIrror.setLineClass(hlLine, null);
                hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
            },
            extraKeys: {
                "F11": function () {
                    var scroller = g_codeMIrror.getScrollerElement();
                    if (scroller.className.search(/\bCodeMirror-fullscreen\b/) === -1) {
                        scroller.className += " CodeMirror-fullscreen";
                        scroller.style.height = "100%";
                        scroller.style.width = "100%";
                        g_codeMIrror.refresh();
                    } else {
                        scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                        scroller.style.height = '';
                        scroller.style.width = '';
                        g_codeMIrror.refresh();
                    }
                },
                "Esc": function () {
                    var scroller = g_codeMIrror.getScrollerElement();
                    if (scroller.className.search(/\bCodeMirror-fullscreen\b/) !== -1) {
                        scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                        scroller.style.height = '';
                        scroller.style.width = '';
                        g_codeMIrror.refresh();
                    }
                }
            }
        });

        var hlLine = g_codeMIrror.setLineClass(0, "activeline");
    });

    
    function resizeHeight() {
    	
	    var winHeight = jQuery(window).height(),
            element = jQuery('.CodeMirror'),
            elementScroll = jQuery('.CodeMirror-scroll'),
            elTop = element.offset().top,
            elHeight = element.height(),
            newHeight = winHeight - elTop - 20;

	    element.height(newHeight);
	    elementScroll.height(newHeight);
	    
	}

	function resizeWidth() {
	    var winWidth = jQuery(window).width();
	  
             functionsWidth = jQuery('.d2mf>.lista-funzionalita').width(),
            newWidth = winWidth - functionsWidth;

	    jQuery('.editor-codice').width(newWidth);
	}

	function resizeEditor() {
	    resizeHeight();
	    resizeWidth();
	}
	

	function hideSuccess() {
		/*
	    var el = jQuery('.formula-salvata');
	    if (!el.length) {
	        return;
	    }
	    el.hide('slow', resizeEditor);
	    */
	    
	}

	
	jQuery(document).ready(function () {
	    resizeEditor();

	    jQuery(window).on('resize', resizeEditor);

	    setTimeout(hideSuccess, 2000);
	});

    
	/*
	var g_codeMIrror = null;
	jQuery(document).ready(
	function () {
		g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('scriptString_id'),{
								mode: "javascript",
								lineNumbers: true,
								theme: 'eclipse',
								tabMode: 'shift',
								indentUnit: 4,
								enterMode: 'keep',
								onCursorActivity: function() {
									g_codeMIrror.setLineClass(hlLine, null);
									hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
								}
						});
		var hlLine = g_codeMIrror.setLineClass(0, "activeline");
	}
	);
*/
	function saveForm(){
		// g_codeMIrror.toTextArea();
		doSubmit('saveFormula.htm','',document.inviodati);
	}
	</script>

	<span class="titoloPagina">
		<fmt:message key="label.dettaglio_formula" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellit/view" />
	</jsp:include>	
		<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.dettaglio_dyn2modellit.title" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${dyn2modellit.descrizione}							
				</div>
			</div>
		</div>
		<br class="clear" />
		<spring-form:form commandName="dyn2modellit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="dyn2modellit" />
		    </jsp:include>
		    <input type="hidden" name="codice" value="${codice}"/>
	   
		    <c:set var="eventoCaricamento" value="<%= EventoModelli.Caricamento.name() %>"></c:set>
		    <c:set var="eventoSalvataggio" value="<%= EventoModelli.Salvataggio.name() %>"></c:set>
			<c:set var="eventoModifica" value="<%= EventoModelli.Modifica.name() %>"></c:set>
			<c:set var="eventoFunzioni" value="<%= EventoModelli.Funzioni.name() %>"></c:set>		
<div id="CorpoPagina">
				
			<div class="d2mf">
				<div class="lista-funzionalita">
						<select id="eventoId" name="evento" onchange="formuleView();">
							<option <c:if test="${evento eq eventoCaricamento}"> selected </c:if> value="<%=EventoModelli.Caricamento.name() %>"><%=EventoModelli.Caricamento.name() %></option>
							<option <c:if test="${evento eq eventoSalvataggio}"> selected </c:if> value="<%=EventoModelli.Salvataggio.name() %>"><%=EventoModelli.Salvataggio %></option>
							<option <c:if test="${evento eq eventoModifica}"> selected </c:if> value="<%=EventoModelli.Modifica.name() %>">Aggiornamento/Modifica</option>
							<option <c:if test="${evento eq eventoFunzioni}"> selected </c:if> value="<%=EventoModelli.Funzioni.name() %>">Funzioni Condivise tra tutti gli eventi</option>
						</select>						
			</div>
				<div class="editor-codice">
					<div>					
						<textarea id="scriptString_id" name="scriptString" cols="100" rows="25" class="CampoFormula">${scriptString}</textarea>
					</div>
				</div>
			</div>
</div>			
			<script type="text/javascript">
				function formuleView(){
					doSubmit('viewFormule.htm','');					
				}		
			</script>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:saveForm();"><fmt:message key="button.insert" /></a></li>	
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>