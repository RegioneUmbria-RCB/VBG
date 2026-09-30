 <?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_dyn2modellid.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_dyn2modellid.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellid/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="dyn2modellidForm" action="list.htm">
			<jmesa:springTableFacade
				id="dyn2modellid_id" 
				items="${dyn2modellidList}" 
				var="dyn2modellid_var"
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${dyn2modellid_var.id.codice}">${dyn2modellid_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="posverticale" titleKey="label.riga" width="8%"/>
						<jmesa:htmlColumn property="posorizzontale" titleKey="label.colonna" width="8%" />
						<jmesa:htmlColumn property="dyn2Campi.nomecampo" titleKey="label.campo_dinamico" />
						<jmesa:htmlColumn property="transientModelloDtipoTesto" titleKey="label.testo" />
						<jmesa:htmlColumn property="dyn2RegoleAttivo.descrizione" titleKey="label.regole_attivazione" />
						<c:if test="${dyn2modellid_var.dyn2Modellit.modellomultiplo eq false }">
							<jmesa:htmlColumn width="5%"  property="flagMultipla" titleKey="label.riga_multipla" sortable="false" filterable="false">
									<input id="flagMultiploId${dyn2modellid_var.id.codice }" type="checkbox" onclick="aggiornaFlag(${dyn2modellid_var.id.codice })" ${dyn2modellid_var.flgMultiplo?'checked':''} />
							</jmesa:htmlColumn>
						</c:if>
						
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
						
							<a class="dettaglioColumn" href="view.htm?codice=${dyn2modellid_var.id.codice}" title="<fmt:message key="label.edit.record" />${dyn2modellid_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${dyn2Modellit.id.codice}" name="codiceModelloT"></input>
		</form>
		
		<script type="text/javascript">
				function aggiornaFlag(id)
				{
					if(checkConfirmMessage('<fmt:message key="alert.verrano_aggiornate_modelli_D_riga_uguale" />'))
					{
						javascript:doHref('changeFlagMultiplo.htm?codice='+id,'');
					}
				}
		</script>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceModelloT=${dyn2Modellit.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_dyn2modellid.title" />';
		</script>
		
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceModelloT=${dyn2Modellit.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:historySet('${_urlback }','../istanzedyn2dati/viewAnteprimaModello.htm?codiceModello=${dyn2modellit.id.codice}','')"><fmt:message key="button.anteprima_modello" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>