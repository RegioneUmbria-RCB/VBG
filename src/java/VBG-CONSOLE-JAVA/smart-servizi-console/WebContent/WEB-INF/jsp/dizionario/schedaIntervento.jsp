<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.ArrayList"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.AlberoEndo"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.StpCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/dettagliIntervento.css" rel="stylesheet"></link>
<script type="text/javascript">
$(document).ready(function(){	
		$( "#accordion" ).accordion(
				 {
				      heightStyle: "content"
				    }
		);
	}
);
</script>
	
<div>
		<div id="accordion" class="popupDettagliEndo inputForm dettagliIntervento">
			
				<h3><a href="#">Attività selezionata</a></h3>
				<div>
					<fieldset>
					<ul>
						<c:forEach items="${helper.descrizionis}" var="cdb">							
								<li>${cdb.codice}</li>						
						</c:forEach>
					</ul>
					</fieldset>
				</div>
				<h3><a href="#">Informazioni varie</a></h3>
				<div>
					<c:forEach items="${helper.descrizionis}" var="cdb">
						<c:if test="${not empty cdb.descrizione}">
							<fieldset>
								<legend>${cdb.codice}</legend>
									${cdb.descrizione}
							</fieldset>
						</c:if>							
					</c:forEach>
				</div>
				<h3><a href="#">Normativa</a></h3>
				<div>
					<table>		
					<tr>
						<th>Descrizione</th>
						<th>Tipologia</th>
					</tr>	
					<c:forEach items="${helper.alberoprocLeggis }" var="lex">					
								<tr>
									<td>${lex.legge.leDescrizione }</td>
									<td>${lex.legge.leggitipi.ltDescrizione}</td>
								</tr>										
					</c:forEach>		
					</table>	
				</div>			
				<h3><a href="#">Modulistica</a></h3>
				<div>
					<table>		
					<tr>
						<th>descrizione</th>
					</tr>	
					<c:forEach items="${helper.alberoprocDocumentis }" var="doc">
						<c:if test="${doc.flgDomandafo eq false }">
							<c:if test="${doc.pubblica gt 0 }">
								<tr>
									<td>${doc.descrizione }</td>
								</tr>
							</c:if>
						</c:if>		
					</c:forEach>		
					</table>				
				</div>			
			 
				<h3><a href="#">Procedimenti</a></h3>
				
				<div>
					<div>
				<%
				List<String> endoAttivi = new ArrayList<String>();
				List<String> endoNoCartAttivi = new ArrayList<String>();
				ElenchiEndoFACCT elenchiEndo =(ElenchiEndoFACCT) request.getAttribute("endos");
				AlberoEndo endoCART = elenchiEndo.getEndoCart();
	            AlberoEndo endoNecessari = elenchiEndo.getEndoNecessari();
	            AlberoEndo endoRicorrenti = elenchiEndo.getEndoRicorrenti();
	            AlberoEndo altriEndo = elenchiEndo.getAltriEndo();

	        int count = 0;
		    if(endoCART != null && endoCART.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoId" style="">
			<div class="elenco-endo-title">Endoprocedimenti Attivabili</div>
		<% 
				Iterator<String> famiglieIter = endoCART.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>

	    	<ul>
	    		<li class="famigliaEndo">	    		
	    		<%= famiglia %>	    		
	    		<%
	    			Iterator<String> categorieIter = endoCART.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoCART.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		
	    			<ul>
	    			
	    			<li class="tipoEndo"><%= categoria %>
	    			
			    		<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++){
				            count++;
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoAttivi.contains(endo.getCodiceBDR()) || endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || endo.isObbligatorio() ? "checked=\"checked\"" : "";
				    		String readOnly = endo.isObbligatorio() ? "readonly='readonly'" : "";
			    %>
						<li class="endo">
							<dl>
								<dd>									
									<%=endo.getDescrizione()%>									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
				    	</ul>
				    </li>
			    	
			    	</ul>
			  
			    <%
	    			}
			    %>
			    </li>
			</ul>
		    
	    <%
				}
			%>
			</div> 
			<%
			} 			
		%>
	           
	      <%
		    if(endoNecessari != null && endoNecessari.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoNecessari">
   			<div class="elenco-endo-title">Endoprocedimenti Necessari</div>
		<% 
				Iterator<String> famiglieIter = endoNecessari.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = endoNecessari.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoNecessari.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    			<li class="tipoEndo">
	    			<%= categoria %>
				    	<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = "checked=\"checked\"";
				    		String readOnly = "readonly='readonly'";
			    %>
						<li class="endo">
							<dl>
								<dd>									
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
			    		</ul>
			    	</li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			}		    
		%>
	           
	         	<%
		    if(endoRicorrenti != null && endoRicorrenti.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoRicorrenti">
   			<div class="elenco-endo-title">Endoprocedimenti Ricorrenti</div>
		<% 
				Iterator<String> famiglieIter = endoRicorrenti.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = endoRicorrenti.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoRicorrenti.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    		<li class="tipoEndo">
	    			<%= categoria %>
		    		<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String readOnly = "";
			    %>
						<li class="endo">
							<dl>
								<dd>									
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
		    		</ul>
			    </li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			} 
		   
		%>
	         	
	       <%
		    if(altriEndo != null && altriEndo.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoAltriEndo">
   			<div class="elenco-endo-title">Altri Endoprocedimenti</div>
		<% 
				Iterator<String> famiglieIter = altriEndo.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = altriEndo.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = altriEndo.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    			<li class="tipoEndo">
	    			<%= categoria %>
				    	<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String readOnly = "";
			    %>
						<li class="endo">
							<dl>
								<dd>									
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
			    		</ul>
			    	</li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			}
		%>
	         	
			</div>
		</div>

</div>

</div>	

