<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%-- <spring-form:form commandName="movimentiCommand" name="inner_inviodati">	 --%>
	<table width="100%"> 
	   <tr>
	   		<td>
	   			
	   			<div class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
						<tbody class="tbody">
							<tr>
							<td>
								${mTipo.corpo}
							</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
								<tr align="center"><td><input id="radiobox_accetto" type="radio" onclick="accetto('radiobox_non_accetto');" />&nbsp;<b>Accetto</b></td></tr>
								<tr align="center"><td><input id="radiobox_non_accetto" type="radio" onclick="rigetto('radiobox_accetto');"/><b>&nbsp;Non accetto</b></td></tr>
						</tbody>
					</table>
				</div>
				
	   		</td>
	   </tr>
	 
					
	    <tr>
		  	 <td>
		  		<div id="functions">
					<ul>
					    <li><a href="javascript:accettaOrRigetta(${codiceIstanza},'radiobox_accetto','radiobox_non_accetto')"><fmt:message key="button.ok" /></a></li>
						<li><a href="javascript:void(0)" onClick="dijit.byId('accettazioneRuoloIstruttoriDiv').hide()"><fmt:message key="button.annulla" /></a></li>
			        </ul>
			  </div>			
			</td>
	   </tr>
	</table>
	

<%--  </spring-form:form>	--%>

