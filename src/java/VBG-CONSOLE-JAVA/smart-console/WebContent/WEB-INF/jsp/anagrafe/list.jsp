<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="anagrafe.label.lista_anagrafe.title"/></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="anagrafe.label.lista_anagrafe.title"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../anagrafe/list" />
		</jsp:include>
		<div id="subcontent">
			<form name="inviodati" action="list.htm">
				${htmltable}
			</form>
			<%-- 
			<form name="anagrafeForm" action="list.htm">			
				<jmesa:springTableFacade
					id="anagrafe_id" 
					items="${anagrafeList}" 
					var="anagrafe_var" 
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.AnagrafeFilterMatcherMap" >
						<jmesa:htmlTable>
							<jmesa:htmlRow>
								<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  	<a href="javascript:historySet('${_urlback }','../anagrafe/view.htm?codice=${anagrafe_var.id.codice}','') ">${anagrafe_var.id.codice}</a>
                            	</jmesa:htmlColumn>
								<jmesa:htmlColumn property="richiedente" titleKey="anagrafe.label.nominativo"  width="30%"/>
								<jmesa:htmlColumn property="descrizioneResidenza" titleKey="label.residenza"  width="15%"/>
								<jmesa:htmlColumn property="corrispondenza" titleKey="anagrafe.label.indirizzo_corrispondenza" width="15%"/>
								<jmesa:htmlColumn property="tipoanagrafe" titleKey="anagrafe.label.tipo_anagrafe" filterEditor="org.jmesa.custom.TipoAnagrafeDroplist" cellEditor="org.jmesa.custom.FisicaGiuridicaCellEditor"  width="8%"/>
								
								<jmesa:htmlColumn property="tipologia" titleKey="anagrafe.label.tipologia_anagrafe" filterEditor="org.jmesa.custom.SiNoDroplist" cellEditor="org.jmesa.custom.SiNoTecnicoCellEditor" width="4%"/>
								
								<jmesa:htmlColumn property="flagDisabilitato" titleKey="anagrafe.label.stato" filterEditor="org.jmesa.custom.AttivoDisattSospesoDroplist" cellEditor="org.jmesa.custom.StatoanagrafeCellEditor" width="5%"/>
								
								<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../anagrafe/view.htm?codice=${anagrafe_var.id.codice}','')"  title="<fmt:message key="label.edit.record" />&nbsp;${anagrafe_var.richiedente}">
									<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			--%>
			<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="anagrafe.label.lista_anagrafe.title" />';
			</script>
			
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>