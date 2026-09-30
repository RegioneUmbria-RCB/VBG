<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.configurazione_bolkestein" />
	</title>	
	<style>
		.posteggio 
		{
			float:left;
			width: 200px;
			border-width: 1px;border-style: solid;border-color: #888;
 			margin-top: 20px;margin-right: 20px;margin-left: 20px;margin-bottom: 20px;
 			text-align: center; 			
		}			
		.posteggio>a {display: block; height: 100px; line-height: 100px; 
		}		
		a.posteggiodisattivo { background-color: #C0C0C0; box-shadow: 10px 10px 5px #888888; }
		a.posteggiodisattivo:hover, a.posteggiodisattivo:focus, a.posteggiodisattivo:active { background-color:#99cc66;}
		a.posteggioattivo { background-color: #99cc66; }
		a.posteggioattivo:hover, a.posteggioattivo:focus, a.posteggioattivo:active { background-color:#C0C0C0;}
	</style>	
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoprocbolkestein.label.dettaglio_bolkestein.title" />
	</span>
	<div id="subcontent">
		<spring-form:form commandName="alberoprocbolkestein" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
				<jsp:param name="commandName" value="alberoprocbolkestein"/>
			</jsp:include>
			<table>
				<%
			    	String mercatiUso = "";
				%>				
				
				<tr id="mercati">
					<td><fmt:message key="label.manifestazione" /></td>
					<td colspan="3">	
						<script type="text/javascript">
							function clearuso(inputField,listItem) {
								var a = listItem.id;
								document.getElementById('mercati_id').value = inputField.value;
								document.getElementById('mercati_hidden').value = a;
								$('mercati_id_choices').fade();
								clearField('mercatiUso_id', 'mercatiUso_hidden');
								jQuery('#listaposteggiid').hide();
							}
						</script>		
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercati" />		
							<jsp:param name="propertyPath" value="entity" />				
							<jsp:param name="pathPropertyDescription" value="entity.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.id.codice" />
							<jsp:param name="autocompleterAjax" value="findMercati.htm" />	
							<jsp:param name="afterUpdateElement" value="clearuso" />
							<jsp:param name="titleKey" value="label.manifestazione" />
						</jsp:include>								
					</td>
				</tr>
				<tr id="mercatiUso" style="<%=mercatiUso%>">
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<script type="text/javascript">
							function filtermercato(element, entry) { 
								return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;
							}
						</script>
						<script type="text/javascript">
							function setHiddenFieldmercati(inputField,listItem){
								var a = listItem.id;
								document.getElementById('mercatiUso_id').value = inputField.value;
								document.getElementById('mercatiUso_hidden').value = a;
								apriConfigurazione();
							}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiUso" />		
							<jsp:param name="propertyPath" value="mercatiUso" />				
							<jsp:param name="pathPropertyDescription" value="mercatiUso.descrizione" />
							<jsp:param name="pathPropertyCode" value="mercatiUso.id.codice" />
							<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
							<jsp:param name="ajaxCallBack" value="filtermercato"/>
							<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati"/>
							<jsp:param name="titleKey" value="label.giorno" />
						</jsp:include>		
					</td>
				</tr>			
			</table>		
			<div id="functions">			
				<script type="text/javascript">
					function apriConfigurazione(){
						var mercati_id = jQuery('#mercati_hidden').val();
						var mercatiUso_id = jQuery('#mercatiUso_hidden').val();
						historySet('${_urlback}','../alberoprocbolkestein/view.htm?codiceAlberoproc=${param.codiceAlberoproc}&codiceMercato='+mercati_id+'&codiceUso='+mercatiUso_id,'')
					}
				// 		
				</script>
				<ul>
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
				</ul>	
			</div>				
			<div id="listaposteggiid">
				<br /><br />		
				<c:choose>
					<c:when test="${empty alberoprocbolkestein.posteggis }">
					</c:when>
					<c:otherwise>
						<br />	
						<fieldset>				
							<legend><b>${alberoprocbolkestein.entity.descrizione} - ${alberoprocbolkestein.mercatiUso.descrizione}</b></legend>
							
							<div id="functions">
								<ul>													
									<li><a href="javascript:doSubmit('disattivaTutti.htm','',document.inviodati);"><fmt:message key="button.disattiva_tutti" /></a></li>								
									<li><a href="javascript:doSubmit('attivaTutti.htm','',document.inviodati);"><fmt:message key="button.attiva_tutti" /></a></li>
								</ul>
							</div><br />							
							<c:forEach items="${alberoprocbolkestein.posteggis }" var="posteggio">
								<div id="posteggio${posteggio.idposteggio }" class="posteggio">
									<c:if test="${posteggio.selezionato eq true }">
										<a href="javascript:doSubmit('delete.htm?codiceAlberoprocbolkestein=${posteggio.codiceAlberoprocbolkestein}','',document.inviodati);" <fmt:message key="${posteggio.codicePosteggio}" /> class="posteggioattivo" >
											${posteggio.codicePosteggio}
										</a>
									</c:if>
									<c:if test="${posteggio.selezionato eq false }">
										<a href="javascript:doSubmit('insert.htm?idposteggio=${posteggio.idposteggio}','',document.inviodati);" <fmt:message key="${posteggio.codicePosteggio}" /> class="posteggiodisattivo" >
											${posteggio.codicePosteggio}
										</a>
									</c:if>									
								</div>			
							</c:forEach>				
						</fieldset>
					</c:otherwise>
				</c:choose>
			</div>
		</spring-form:form>
	</div>
</body>
</html>