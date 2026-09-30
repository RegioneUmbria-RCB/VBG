<%@ include file="../includes/taglibs.jsp"%>

<c:set var="_intestazione" value="1" />
<c:if test="${not empty param.intestazione}">
	<c:set var="_intestazione"
		value="${param.intestazione}" />
</c:if>
<c:set var="_calcolarimanenaza" value="1" />
<c:if test="${not empty param.calcolarimanenaza}">
	<c:set var="_calcolarimanenza"
		value="${param.calcolarimanenaza}" />
</c:if>

<c:set var="_areareadonly" value="0" />
<c:if test="${not empty param.areareadonly}">
	<c:set var="_areareadonly"
		value="${param.areareadonly}" />
</c:if>

<c:set value="false" var="_readonly" scope="page"></c:set>
<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
	<c:set value="true" var="_readonly" scope="page"></c:set>
</c:if>

    <c:if test="${_intestazione eq '1'}"> 
	<tr class="titoloSezione">
	    <td width="100%" colspan="2"><fmt:message key="label.situazione_dehors" />
	    <c:if test="${autorizzazioniCommand.dehorsMqIstanze.id.codice !=null}">
			(<a style="cursor: pointer; color: black;"  href="javascript:historySet('${_urlback}','../dehorsmqistanze/view.htm?codice=${autorizzazioniCommand.dehorsMqIstanze.id.codice}','')">
					<fmt:message key="label.modifica" />
			</a>)
	    </c:if>
	     <c:if test="${autorizzazioniCommand.dehorsMqIstanze.id.codice == null && autorizzazioniCommand.entity.id.codice!=null}">
			
			(<a style="cursor: pointer; color: black;"  onclick="javascript:historySet('${_urlback}','../dehorsmqistanze/create.htm?codiceAut=${autorizzazioniCommand.entity.id.codice}','')"/>
							<label><fmt:message key="label.modifica" /></label>
			</a>)  
	     </c:if>
	     </td>
	</tr>
	</c:if>
	
<tr>
	<td><fmt:message key="label.mq_richiesti_dehors"/></td>
	<td >
		<spring-form:input id="mq_richiesti_id" path="dehorsMqIstanze.mqrichiesti" size="28" onblur="checkNumberValue(this);" onchange="calcolaSeDisponibile(0)" readonly="${_readonly}" />
		<spring-form:errors path="dehorsMqIstanze.mqrichiesti" cssClass="error" />
	</td>
</tr>
<tr>
	<td><fmt:message key="label.area" /></td>
	<c:if test="${!_readonly && _areareadonly eq '0'}">
		<td>
			<spring-form:input size="25" id="aree_id" path="dehorsMqIstanze.aree.denominazione" cssClass="searchbox" onchange="checkValue(this,'tipiaree_hidden')" onkeydown="javascript:return searchAll(this,event)" /> <init:autocompleter
			methodAjax="findAreeDehors.htm" afterUpdateElement="calcolaMqDisponibiliCallBack" idHidden="aree_hidden" idInput="aree_id" inputTitleKey="label.ricerca_area"/> 
			<spring-form:errors path="dehorsMqIstanze.aree" cssClass="error" /> 
			<spring-form:hidden id="aree_hidden" path="dehorsMqIstanze.aree.id.codice" />
		</td>		
	</c:if>	
	
	<c:if test="${_readonly || _areareadonly eq '1'}">
		<td>
			<spring-form:input size="28" id="aree_id" path="dehorsMqIstanze.aree.denominazione" readonly="${_readonly}" /> 
		</td>	
	</c:if>
	<c:if test="${disponibiliDaAssegnare != null}">
		<tr>
			<td colspan="2">E' possibile assegnare un massimo di  ${disponibiliDaAssegnare} Mq.</td>
		</tr>

	</c:if>
</tr>
<tr>
	<td colspan="2">
  		<div id="pannelloMqDisponibili">
  		<!-- TABELLA  PER MOSTRARE E GESTIRE LA POSSIBILITà DI INSERIRE I MQ RICHIESTI
  		     CREATA TRAMITE UNA CHIAMATA AJAX-->
		</div>
		<!-- FINE -->
	</td>
</tr>


<script type="text/javascript">
		
		
		if(${_calcolarimanenaza eq '1'}){
        	calcolaSeDisponibile(0);
		}
        
		function calcolaMqDisponibiliCallBack(inputField,listItem){
			 var a = listItem.id;
			 document.getElementById('aree_id').value = inputField.value;
			 document.getElementById('aree_hidden').value = a;
			 calcolaSeDisponibile(document.getElementById('aree_hidden').value);
		}
		
		function calcolaSeDisponibile(codiceArea){
			if(codiceArea==0)
			{
			 codiceArea=document.getElementById('aree_hidden').value
			}
			var mqrichiesti=document.getElementById('mq_richiesti_id').value
			if(codiceArea!='' && mqrichiesti!='')
			{
			new Ajax.Request(
			'${pageContext.request.contextPath}/autorizzazioni/ajaxCalcolaAreaDehorsDisonibile.htm?codicearea='+ codiceArea+'&mqRichiesti='+mqrichiesti,
			{
				method : 'post',
				onSuccess : function(transport) {
					var response = transport.responseText;
					$("pannelloMqDisponibili").innerHTML = parseAjaxResponse(response, true, false);
					$("pannelloMqDisponibili").style.color = "red";
					$("pannelloMqDisponibili").style.font = "italic bold 15px arial,serif";
					
					
					var diffDisponibiliRichiesti=document.getElementById('disponibili_id').value
					if(diffDisponibiliRichiesti>0)
					{
						$("pannelloMqDisponibili").style.color = "green";
						var mqRichiesti=document.getElementById('mqRichiesti_id').value	;
						if(mqRichiesti.indexOf(".",0)>0){
							mqRichiesti=mqRichiesti.replace(".",",");
						}
						jQuery("#mq_assegnati_id").val(mqRichiesti);
					}
					
					$("pannelloMqDisponibili").appear();
					applyStyle();
				},
				onFailure : function(transport) {
					var response = transport.responseText;
					alert(response);
				}
			});
			}
		}
		
	</script>			

<tr>
	<td><fmt:message key="label.mq_assegnati_dehors"/></td>
	<td>
		<spring-form:input id="mq_assegnati_id" path="dehorsMqIstanze.mqassegnati" size="28" onblur="checkNumberValue(this);" readonly="${_readonly}" />
		<spring-form:errors path="dehorsMqIstanze.mqassegnati" cssClass="error" />
	</td>
</tr>

