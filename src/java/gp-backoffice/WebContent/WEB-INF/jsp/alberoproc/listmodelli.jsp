<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_modelli" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_modelli" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.intervento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${alberoproc.scDescrizione}" /></div>
			</div>
	    </div>   
	    <div class="clear"></div>
	    <br/>
			<c:if test="${not empty alberoprocHelpers}">
			<fieldset><legend>
				<a
				class="sezioneDatiMeno"
				id="id_link_modelli_ereditati"
				href="javascript:showHidePanelBase('id_table_modelli_ereditati', 'id_link_modelli_ereditati', '', '${pageContext.request.contextPath}/images/','div',false);"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <b><fmt:message key="label.lista_modelli_ereditati" /></b>">
				<label for="id_link_modelli_ereditati"><b><fmt:message key="label.lista_modelli_ereditati" /></b></label> 
				</a>
			</legend>
			<div class="jmesa" id="id_table_modelli_ereditati">
			<table border="0"  cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td width="30%" valign="top"><fmt:message key="label.intervento" /> </td>
						<td width="70%" ><fmt:message key="label.lista_modelli" /></td>
		            </tr>
				</thead>
				<tbody class="tbody">
				<%int l=1;%>
				<c:forEach items="${alberoprocHelpers}" var="alberoprocHelper_var">
				<tr class="<%=(l%2)==0?"odd":"even"%>">
				      <td >	
				      	<b>					
							${alberoprocHelper_var.currentAlberoproc.vwAlberoproc.scDescrizionebreve} 
							[${alberoprocHelper_var.currentAlberoproc.vwAlberoproc.id.codice} - 
							${alberoprocHelper_var.currentAlberoproc.vwAlberoproc.scCodice}]
						</b>
					  </td>
					  <!-- TABELLA LISTA MODELLI ASSOCIATA AD INTERVENTO -->
					  <td>
					  		<div class="jmesa">
								<table border="0"  cellpadding="2" cellspacing="0" class="table">
									<thead>
										<tr class="header">
											<td width="40%"><fmt:message key="label.modello" /> </td>
											<td width="20%" ><fmt:message key="label.tipo_firma" /></td>
											<td width="10%" ><fmt:message key="label.pubblica" /></td>
											<td width="10%" ><fmt:message key="label.modello_facoltativo" /></td>
							            </tr>
									</thead>
									<tbody class="tbody">
									<%int j=1;%>
										<c:forEach items="${alberoprocHelper_var.alberoprocDyn2modellits}" var="dyn2modellit_var">
										<tr class="<%=(j%2)==0?"odd":"even"%>">
											<td>${dyn2modellit_var.dyn2Modellit.descrizione}</td>
										    <td>
										    	<c:if test="${dyn2modellit_var.flagTipofirma==0}">
													<fmt:message key='alberoprocDyn2modellit.label.no_firma' />
												</c:if>
												<c:if test="${dyn2modellit_var.flagTipofirma==1}">
													<fmt:message key='alberoprocDyn2modellit.label.firma_modello' />
												</c:if>
												<c:if test="${dyn2modellit_var.flagTipofirma==2}">
													<fmt:message key='alberoprocDyn2modellit.label.firma_ogni_blocco' />
												</c:if>
										    </td>
										    <td>
										    	<c:if test="${dyn2modellit_var.flagPubblica==false}">
													<fmt:message key='label.no' />
												</c:if>
												<c:if test="${dyn2modellit_var.flagPubblica==true}">
													<fmt:message key='label.si' />
												</c:if>
										    </td>
										    <td>
										    	<c:if test="${dyn2modellit_var.flagFacoltativa==false}">
													<fmt:message key='label.no' />
												</c:if>
												<c:if test="${dyn2modellit_var.flagFacoltativa==true}">
													<fmt:message key='label.si' />
												</c:if>
										    </td>
										</tr>
										<%j++; %>
										</c:forEach>
									</tbody>
									
								</table>
							</div>	
					  </td>
	             </tr>
				<%l++; %>
				</c:forEach>
				</tbody>
			</table>
		</div>
	    </fieldset>
	    </c:if>
	    <div class="clear"></div> 
	    <br/>
	    <div class="titoloTabella">
	       <fmt:message key="label.lista_modelli" />
	    </div>	
		<form name="alberoprocModelliForm" action="listmodelli.htm">
			<jmesa:springTableFacade
				id="modelliAlberoproc_id" 
				items="${alberoprocDyn2modellits}" 
				var="modelli_var"
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>		
						<jmesa:htmlColumn property="id.fkD2mtId" titleKey="label.codice" width="2%">
                           	<a href="viewModelli.htm?codiceprocedimento=${alberoproc.id.codice}&codicemodello=${modelli_var.id.fkD2mtId}">${modelli_var.id.fkD2mtId}</a>
                        </jmesa:htmlColumn>			
						<jmesa:htmlColumn property="dyn2Modellit.descrizione" titleKey="label.modello"/>
						<jmesa:htmlColumn property="flagTipofirma" titleKey="label.tipo_firma" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagTipofirma==0}">
								<fmt:message key='alberoprocDyn2modellit.label.no_firma' />
							</c:if>
							<c:if test="${modelli_var.flagTipofirma==1}">
								<fmt:message key='alberoprocDyn2modellit.label.firma_modello' />
							</c:if>
							<c:if test="${modelli_var.flagTipofirma==2}">
								<fmt:message key='alberoprocDyn2modellit.label.firma_ogni_blocco' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagPubblica==false}">
								<fmt:message key='label.no' />
							</c:if>
							<c:if test="${modelli_var.flagPubblica==true}">
								<fmt:message key='label.si' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="flagFacoltativa" titleKey="label.modello_facoltativo" filterable="false" sortable="false">
							<c:if test="${modelli_var.flagFacoltativa==false}">
								<fmt:message key='label.no' />
							</c:if>
							<c:if test="${modelli_var.flagFacoltativa==true}">
								<fmt:message key='label.si' />
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="ordine" titleKey="label.ordine" width="5%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewModelli.htm?codiceprocedimento=${alberoproc.id.codice}&codicemodello=${modelli_var.id.fkD2mtId}" title="<fmt:message key="label.edit.record" />${modelli_var.id.fkD2mtId}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${alberoproc.id.codice}" name="codiceprocedimento"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listmodelli.htm?codiceprocedimento=${alberoproc.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_modelli" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createmodelli.htm?codiceprocedimento=${alberoproc.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>