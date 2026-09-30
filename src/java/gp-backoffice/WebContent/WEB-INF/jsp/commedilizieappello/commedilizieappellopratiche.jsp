<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<table class="vbg-table">
<thead>
	<tr>
		<th><fmt:message key="label.ordine"/></th>
		<th><fmt:message key="label.istanza"/></th>
		<th>
		<label for="appello-pratiche-chk-all-id"><fmt:message key="label.checkbox.selDeselAll"/></label>
			<input type="checkbox" id="appello-pratiche-chk-all-id" />
		</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${lista_pratiche}" var="commediliziar_var" varStatus="idx">
		<tr>
			<td>
				${commediliziar_var.commissioniedilizieR.ordine}	
			</td>
			<td>
				${commediliziar_var.commissioniedilizieR.movimento.istanza.descrizioneIstanza}			
			</td>
			<td>
			<c:set var="checked"></c:set>
				<c:choose>
					<c:when test="${commediliziar_var.checked}">
						<c:set var="checked">checked="checked"</c:set>
					</c:when>
				</c:choose>
				<input class="appello-pratiche-chk" type="checkbox" name="commissioneEdiliziaR"  value="${commediliziar_var.commissioniedilizieR.id.codice}" ${ checked }/>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<script type="text/javascript">


	
document.querySelector('#appello-pratiche-chk-all-id').addEventListener('click', selezionaTuttePratiche);
	

function selezionaTuttePratiche(){
	let checkIt = document.querySelector('#appello-pratiche-chk-all-id').checked;
	let det = document.querySelectorAll('.appello-pratiche-chk');
	det.forEach((chk)=>{
		chk.checked = checkIt;		
	});	
}
function verificaCheckBoxSelectAll(){
	
	let det = document.querySelectorAll('.appello-pratiche-chk');
	let allSelected = true;
	det.forEach((chk)=>{
		allSelected = allSelected && chk.checked;		
	});	
	document.querySelector('#appello-pratiche-chk-all-id').checked = allSelected;
	
}


vbg.ready(() => {	
	verificaCheckBoxSelectAll();

	
}); 



</script>