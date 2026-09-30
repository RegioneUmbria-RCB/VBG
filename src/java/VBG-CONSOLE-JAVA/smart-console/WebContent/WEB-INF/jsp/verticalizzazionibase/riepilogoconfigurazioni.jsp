<%@ include file="../includes/taglibs.jsp"%>

<ul class="listaSchede">
	<c:forEach items="${listaComunis}" var="comune">
		<c:set var="classCSS" value="Scheda"></c:set>
		<c:if test="${codiceComune eq  comune.comune.codicecomune}">
			<c:set var="classCSS" value="SchedaAttiva"></c:set>
		</c:if>
		<li><a id="dettaglioScheda" class="${classCSS }"
			 href="javascript:loadConfigurazioni('${comune.comune.codicecomune}');">${comune.comune.comune}</a></li>
	</c:forEach>
</ul>	
<div class="jmesa" >

<table class="table">

	<c:forEach items="${datis}" var="d" varStatus="idx">
		<c:if test="${idx.index == 0 }">
		<thead>
		<tr class="header">
			<td width="30%"><fmt:message key="label.parametro" /></td>
			<c:forEach items="${d.valori}" var="s">
					<td width="5%" class="${s.codiceSoftware} softwareCss">
						${s.software}
					</td>
			</c:forEach>
		</tr>
		</thead>
		</c:if>			
	</c:forEach>
	<tbody class="tbody" >
	<%int i=0; %>
	<c:forEach items="${datis}" var="d" varStatus="vstat">
		<tr class="<%=(i%2)==0?"odd":"even"%>" nome="${d.parametro}" id="riga">
			<td title="${d.descrizioneParametro }"><b>${d.parametro}</b></td>
			<c:forEach items="${d.valori}" var="s">
				<td class="${s.codiceSoftware} softwareCss">
				<c:choose>
					<c:when test="${fn:contains(d.parametro,'PASSWORD')}">
						************
					</c:when>
					<c:otherwise>${s.valore}</c:otherwise>
				</c:choose>	
				</td>
			</c:forEach>
		</tr>
		<%i++; %>			
	</c:forEach>
	</tbody>
</table>
</div>

