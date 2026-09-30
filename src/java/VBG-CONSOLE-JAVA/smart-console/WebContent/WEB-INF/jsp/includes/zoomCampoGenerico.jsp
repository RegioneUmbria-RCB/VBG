<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<!-- 
La pagina di inclusione crea una finestra di dialog che permette di
zoommare il campo di testo passato.
Il campo di testo è obbligatorio altrimenti nella finestra non compare nulla

Campi obligatori: 

1- ${param.label_dialog}
2- ${param.id_testo} indica l'id del campo di testo da cui andiamo a recuperare il valore che vogliamo inserire
   nella finestra di dialog


Campi opzionali

1-${param.indice} (se sono presenti più inclusioni sulla stessa jsp)
2-${param.sizeDialog}
-->

<c:set var="size" value="700" />
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="size" value="700" />
<c:if test="${not empty param.sizeDialog}">
	<c:set var="size" value="${param.sizeDialog}" />
</c:if>
<c:set var="indice" value="" />
<c:if test="${not empty param.indice}">
	<c:set var="indice" value="${param.indice}" />
</c:if>
<c:set var="id_campo_zoom" value="${param.id_testo}" />
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<a class="infoColumn" href="javascript:showDialogTwo${indice}();"  
	title="<fmt:message key="label.zoom"></fmt:message>">
   	<label><fmt:message key="label.zoom" /></label>
</a>

<script type="text/javascript">
		var secondDlg${indice};
	    dojo.addOnLoad(function() {
	        // create the dialog:
	        secondDlg${indice} = new dijit.Dialog({
	            title: "<fmt:message key="${param.label_dialog}" />",
	            style: "width:${size}px;"
	          
	           
	        });
	    });
	    function showDialogTwo${indice}(${id_campo_zoom}) {
	        // set the content of the dialog:
	        secondDlg${indice}.attr("content",document.getElementById('${id_campo_zoom}').value);
	        secondDlg${indice}.show();
	    }
</script>	