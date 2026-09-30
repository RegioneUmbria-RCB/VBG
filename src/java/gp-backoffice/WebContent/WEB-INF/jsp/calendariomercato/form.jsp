<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Giorno"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Mercati"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.PeriodicitaHelper"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeT"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeD"%>
<%@ page import="java.util.Date"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatiUso"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page import="org.jmesa.web.HttpServletRequestWebContext"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.Set"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Anagrafe"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it_IT">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
	    <c:if test="${calendariomercatoParametri.step == '0'}">	
			<fmt:message key="form.calendariomercatoParametri.title.create" />
		</c:if>
		<c:if test="${calendariomercatoParametri.step == '1'}">	
			<fmt:message key="form.calendariomercatoParametri.title.calendario" />
		</c:if>
		<c:if test="${calendariomercatoParametri.step == '2'}">	
			<fmt:message key="form.calendariomercatoParametri.title.presenze" />
		</c:if>
	</title>
</head>
<body>
<style>
 .step1{
	cursor: pointer; 
}

</style>
<%
String _confirm="";
boolean giornataEsistente = false; 
String insertConfirm="Stai per aggiungere il giorno @GIORNO@ al calendario. Continuare?";
String deleteConfirm="Attenzione! Stai per eliminare una giornata di calendario e le eventuali presenze registrate. Continuare?";
String[][] mesi = new String[][]{{"Gennaio","Febbraio","Marzo","Aprile"},{"Maggio","Giugno","Luglio","Agosto"},{"Settembre","Ottobre","Novembre","Dicembre"}};
pageContext.setAttribute("mesi", mesi);
CalendariomercatoParametri calendariomercatoParametri=(CalendariomercatoParametri) request.getAttribute("calendariomercatoParametri");
List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
int numerogiornimercato=0;
Integer codiceuso=0;
if(calendariomercatoParametri.getStep()==1 || calendariomercatoParametri.getStep()==2){
	MercatiUso mercatiUso=(MercatiUso) request.getAttribute("mercatiUso");
    numerogiornimercato=list.size();
    codiceuso=mercatiUso.getId().getCodice();
}
int anno=calendariomercatoParametri.getAnno();
int mese=0;
int mese1=0;
int i=0;
int j=0;
int di=0;
int dj=0;
int dk=0;
String storico = "";
String selected = "";
String holiday = "";
Mercati mercati=(Mercati)request.getAttribute("mercati");
Integer codicemercato=mercati.getId().getCodice();
%>

<%!
private boolean isMarketDay(Calendar date,HttpServletRequest request){
	boolean success = false;
	List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
	Calendar dataMercato=null;
    for(MercatipresenzeT  mercatipresenzeT:list){
    	Date temp=mercatipresenzeT.getDataRegistrazione();
    	dataMercato=new GregorianCalendar();
    	dataMercato.setTime(temp);
		if(date.get(Calendar.YEAR) == dataMercato.get(Calendar.YEAR)
				&& date.get(Calendar.MONTH) == dataMercato.get(Calendar.MONTH) 
				&&date.get(Calendar.DAY_OF_MONTH) == dataMercato.get(Calendar.DAY_OF_MONTH)){
			success = true;
		    break;
		}
	}
	return success;
}

private MercatipresenzeT getGiornata(Calendar date,HttpServletRequest request){
	boolean success = false;
	List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
	Calendar dataMercato=null;
	for(MercatipresenzeT  mercatipresenzeT:list){
		Date temp = mercatipresenzeT.getDataRegistrazione();
		dataMercato = new GregorianCalendar();
		dataMercato.setTime(temp);
			if(date.get(Calendar.YEAR) == dataMercato.get(Calendar.YEAR)
					&& date.get(Calendar.MONTH) == dataMercato.get(Calendar.MONTH) 
					&&date.get(Calendar.DAY_OF_MONTH) == dataMercato.get(Calendar.DAY_OF_MONTH)){
				return  mercatipresenzeT;
			    
			}
	}
	return null;
}

private Integer getIdGiornata(Calendar date,HttpServletRequest request){
	boolean success = false;
	List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
	Calendar dataMercato=null;
	for(MercatipresenzeT  mercatipresenzeT:list){
		Date temp = mercatipresenzeT.getDataRegistrazione();
		dataMercato = new GregorianCalendar();
		dataMercato.setTime(temp);
			if(date.get(Calendar.YEAR) == dataMercato.get(Calendar.YEAR)
					&& date.get(Calendar.MONTH) == dataMercato.get(Calendar.MONTH) 
					&&date.get(Calendar.DAY_OF_MONTH) == dataMercato.get(Calendar.DAY_OF_MONTH)){
				return  mercatipresenzeT.getId().getCodice();
			    
			}
	}
	return null;
}


private Integer isMarketDayGestionePresenze(Calendar date,HttpServletRequest request){
    // verifico se attivo o no il controllo sulle giornate future, se non attivo per ogni giornata 
    // del mervato ritorno sempre 1
    String blocco_accesso_futuro="N";
    if(StringUtils.isNotBlank((String)request.getAttribute("blocco_accesso_futuro")))
    {
    	blocco_accesso_futuro=(String)request.getAttribute("blocco_accesso_futuro");
    }
    Integer success = -1;
	List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
	Calendar dataMercato=null;
	for(MercatipresenzeT  mercatipresenzeT:list){
	    GregorianCalendar today=new GregorianCalendar();
		Date temp=mercatipresenzeT.getDataRegistrazione();
		dataMercato=new GregorianCalendar();
		dataMercato.setTime(temp);
			if(date.get(Calendar.YEAR) == dataMercato.get(Calendar.YEAR)
					&& date.get(Calendar.MONTH) == dataMercato.get(Calendar.MONTH) 
					&& date.get(Calendar.DAY_OF_MONTH) == dataMercato.get(Calendar.DAY_OF_MONTH)){
			//System.out.print(Utilities.compareDates(temp, today));
			if("N".equalsIgnoreCase(blocco_accesso_futuro))
			{
			    success = 1;
			    break; 
			}
			else
			{
				if(Utilities.compareDates(temp, today) == 1)
				{
				    // data giornaliera superiore alla data della giornata di mercato, non devo dare la possibilità 
				    // di accedere alla giornata
				    success = 0;
				    break;
				}else{
				    // data giornaliera  inferiore (o uguale) alla data della giornata di mercato, posso 
				    // accedere alla giornata
				    success = 1;
				    break;
				}
			}
		}
	}
	return success;
}

private boolean isHolidayDay(GregorianCalendar date,HttpServletRequest request){
	boolean success = false;
	CalendariomercatoParametri calendariomercatoParametri =(CalendariomercatoParametri) request.getAttribute("calendariomercatoParametri"); 
	List<Giorno> list=calendariomercatoParametri.getGiorniFestivi();
	for(Giorno giorno:list){
		if(date.get(Calendar.YEAR) == giorno.getData().get(Calendar.YEAR)&& date.get(Calendar.MONTH) == giorno.getData().get(Calendar.MONTH) && date.get(Calendar.DAY_OF_MONTH) == giorno.getData().get(Calendar.DAY_OF_MONTH)){
			success = true;
			return success;
		}		 
	}
	success = isSunday(date);
	return success;
} 

private boolean isSunday(Calendar date){
	boolean success = false;
	if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)success=true;
	return success;
}

//questo metodo verifica se una giornata è stata storicizzata
private boolean isStorico(GregorianCalendar date,HttpServletRequest request){
	boolean success = false;
	List<MercatipresenzeT> list=(List<MercatipresenzeT>) request.getAttribute("mercatipresenzeTList"); 
	Calendar dataMercato=null;
    for(MercatipresenzeT  mercatipresenzeT:list){
    	Date temp=mercatipresenzeT.getDataRegistrazione();
    	dataMercato=new GregorianCalendar();
    	dataMercato.setTime(temp);
		if(date.get(Calendar.YEAR) == dataMercato.get(Calendar.YEAR)
				&& date.get(Calendar.MONTH) == dataMercato.get(Calendar.MONTH) 
				&&date.get(Calendar.DAY_OF_MONTH) == dataMercato.get(Calendar.DAY_OF_MONTH)){
			success = mercatipresenzeT.getFlagPresenze();
		    break;
		}
	}
	return success;
}

private boolean isMercatoStoricizzato(HttpServletRequest request){
    boolean success = false;
    success=(Boolean) request.getAttribute("flagMercatoStoricizzato");
    return success;
}
%>
<span class="titoloPagina">
	<c:if test="${calendariomercatoParametri.step == '0'}">	
			<fmt:message key="form.calendariomercatoParametri.title.create" />
		</c:if>
		<c:if test="${calendariomercatoParametri.step == '1'}">	
			<fmt:message key="form.calendariomercatoParametri.title.calendario" />
		</c:if>
		<c:if test="${calendariomercatoParametri.step == '2'}">	
			<fmt:message key="form.calendariomercatoParametri.title.presenze" />
		</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../calendariomercato/view" />
	</jsp:include>
	
<div id="subcontent">
	<spring-form:form commandName="calendariomercatoParametri" name="inviodati">	
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="calendariomercatoParametri" />
    </jsp:include> 
    <c:if test="${param.storicizzazione == 'ok'}">
	    <div id="status_msg" class="success_header" >
	          <fmt:message key="label.storicizzazione.eseguita"/>
	    </div><br/>
    </c:if>
    <c:if test="${flagMercatoStoricizzato eq true}">
   		 <span class="parametri"><label><fmt:message key="label.storicizzazionegiorno.mercatochiuso"/></label></span><br/>
    </c:if>
    <span class="parametri">   
	    <fmt:message key="form.calendariomercatoParametri.mercato" />: <label> <c:out value="${mercati.descrizione}"></c:out></label>	
    </span>
    <c:if test="${calendariomercatoParametri.step == '1' or calendariomercatoParametri.step == '2'}">
    <span class="parametri"><fmt:message key="form.calendariomercatoParametri.mercatiUso" />: <label>${mercatiUso.descrizione}</label> </span>
    <spring-form:hidden path="mercatiUso.id.codice"/> 
    <span class="parametri"><fmt:message key="form.calendariomercatoParametri.anno" />: <label>${anno}</label> </span>
    <spring-form:hidden path="anno"/>
    </c:if>
    <br/>
    <table>   
    	<%--STEP-0 INSERIMENTO PARAMETRI PER CREAZIONE CALENDARIO --%>   
      	<c:if test="${calendariomercatoParametri.step == '0'}">
		<tr>
			<td><fmt:message key="form.calendariomercatoParametri.anno" /></td>
			<td class="inline-ui-cell">
				<spring-form:input id="anno_id" path="anno" size="4" maxlength="4"/>
			    <button type="button" class="functionsPlus" onclick="javascript:this.form.anno_id.value++;" title="<fmt:message key="form.calendariomercatoParametri.anno.plus" />">+</button>
                <button type="button" class="functionsMinus" onclick="javascript:this.form.anno_id.value--;" title="<fmt:message key="form.calendariomercatoParametri.anno.minus" />">-</button>
				<spring-form:errors path="anno" cssClass="error"/>
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.calendariomercatoParametri.mercatiUso" /></td>
			<td>
				<spring-form:select path="mercatiUso.id.codice" onchange="setCheckboxValues(this);">
					<spring-form:option value="-1"><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${mercatiusoList}" itemLabel="descrizione" itemValue="id.codice" />	
				</spring-form:select>
				<spring-form:errors path="mercatiUso" cssClass="error"/>
			</td>
		</tr>
		<c:if test="${mercati.manifestazione.codice eq 1}">
		<%--//FIXME i checkbox vanno visualizzati solo se tipoUso di Uso è "NESSUNO" fare con ajax vedi scadenze registrazioni--%>
		<tr>
			<td><fmt:message key="form.calendariomercatoParametri.giorniSettimana" /></td>
			<td>
				<span id="checkboxDiv">
				<c:forEach items="${calendariomercatoParametri.giorniSettimana}" var="giornoSettimana" varStatus="index">
					<c:if test="${not empty giornoSettimana.gsValore}">
					<spring-form:checkbox path="giorniSettimana[${index.index}].transientSelected" label="${giornoSettimana.gsDescrizione}"/>
					</c:if>
				</c:forEach>
				</span>
				<init:help idHelp="help1" textKey="form.calendariomercatoParametri.giornoSettimana.help"/>
			</td>
		</tr>	
		</c:if>
      	</c:if>
     </table>
     <script type='text/javascript'>
     
     	function setCheckboxValues(obj){
			new Ajax.Request('<%=request.getContextPath()%>/calendariomercato/ajaxCheckboxMercatoUso.htm', {
						  method: 'post',
						  parameters: {
				  				codiceMercato: ${mercati.id.codice}, 
				  				codiceUso: obj.options[obj.selectedIndex].value
				  		  },
						  onSuccess: function(transport){
							  var response = transport.responseText;		
							  $("checkboxDiv").innerHTML = response;
							  applyStyle();					  
						  },
						  onFailure: function(transport){ 
							var response = transport.responseText;
						  }						    		 
			});
		}
     
     </script>
	 <%--STEP-1 VISUALIZZO IL CALENDARIO PER LA GESTIONE DEI GIORNI (INSERIMENTO/CANCELLAZIONE) --%>
     <table>
	    <c:if test="${calendariomercatoParametri.step == '1'}">
		<div class="titoloSezione">
	         <fmt:message key="form.calendariomercatoParametri.giorni" />: <%=numerogiornimercato %>
	    </div>
		<c:forEach begin="0" end="2" varStatus="monthrow">
		<tr>
			<c:forEach begin="0" end="3" varStatus="monthcol">		
				<%
				GregorianCalendar cal = new GregorianCalendar();
				cal.set(anno,mese++,1);
				int first_day_of_week = cal.getFirstDayOfWeek();
				int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
				int days_in_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				%>
			<td>
				<table class="calendar_month_cell">
	                <tr class="calendar_month_header_cell"><td colspan="7"><%=mesi[i][j]%> <a name="<%=mesi[i][j]%>"/></td></tr>
					<tr class="calendar_days_cell"><td>Lu</td><td>Ma</td><td>Me</td><td>Gi</td><td>Ve</td><td>Sa</td><td>Do</td></tr>				
					<%
					GregorianCalendar cal1 = new GregorianCalendar();
					%>
					<c:forEach begin="0" end="5" varStatus="dayrow">
					<tr>
						<c:forEach begin="0" end="6" varStatus="daycol">
						<td>						
						<%
							boolean mercatoStoricizzato=isMercatoStoricizzato(request);
						    boolean isStorico=false;
							int c = (day_of_week+5)%7;
							_confirm = insertConfirm;
							
							giornataEsistente = false;
							
							boolean flagPopolaConcessionari = true;
							boolean flagConteggiaPresAss = true;
							Integer concessioniUsoId = null;
							Integer idGiornata = null;
						    if(dj == 0){					
								//PRIMA RIGA DEL MESE
								if(c <= di){
									int today = ++dk;
									cal1.set(anno,mese1,today,0,0,0);
									
									MercatipresenzeT mercatipresenzeT = getGiornata(cal1,request);
									if(mercatipresenzeT != null){
									    idGiornata = mercatipresenzeT.getId().getCodice();
									    flagPopolaConcessionari = mercatipresenzeT.getFlagPopolaConcessionari();
										flagConteggiaPresAss = mercatipresenzeT.getFlagConteggiaPresAss();
										if(mercatipresenzeT.getConcessioniuso()!=null 
												&& mercatipresenzeT.getConcessioniuso().getId()!=null 
												&& mercatipresenzeT.getConcessioniuso().getId().getCodice()!=null){
										    concessioniUsoId = mercatipresenzeT.getConcessioniuso().getId().getCodice();    
										}										
									}
									// verifica se giorno festivo
									if(isHolidayDay(cal1,request)){
										holiday = "_holiday";
										// verifica se è giorno di mercato
										if(isMarketDay(cal1,request)){
											selected = "_selected";
											_confirm = deleteConfirm;
											
											giornataEsistente = true;
											
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
												isStorico=true;
											}else{
												storico = "";
											}
										}else{
										    storico = "";
											selected="";
										}
										// non festivo	
									}else{
										holiday = "";
										// verifica se è giorno di mercato
										if(isMarketDay(cal1,request)){
											selected = "_selected";
											_confirm = deleteConfirm;
											giornataEsistente = true;
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
												isStorico=true;
											}else{
												storico = "";
											}
											
										//no mercato
										}else{
										    selected="";	
										}
									}
									//////////////////////////////////////////////////////
									//  visulizza giorni storicizzati senza link
									if(isStorico){
										out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");
									// visulaizza giorni non storicizzati con link
									}else{
									    if(mercatoStoricizzato){
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");
									    }else{
											_confirm = _confirm.replace("@GIORNO@", Utilities.formatDate(cal1.getTime(), false));
											%>
											<div class="calendar<%= holiday+selected+storico%>_cell funzioni-calendario step1" data-idgiornata="<%= (idGiornata==null)?"": idGiornata %>"
												data-codicemercato="<%= codicemercato%>" data-codiceuso="<%= codiceuso %>"
												data-day="<%= today%>" data-month="<%= mese1%>" data-year="<%= anno%>"
												data-monthname="<%= mesi[i][j]%>" data-confirm="<%= _confirm%>" 
												data-esistente="<%= giornataEsistente %>"
												data-flagpopolaconcessionari="<%= flagPopolaConcessionari%>"
												data-flagconteggiapresass="<%= flagConteggiaPresAss%>" 
												data-concessioniusoid="<%=  (concessioniUsoId==null)?"": concessioniUsoId %>" ><%=today%></div>
											<%
									    }
									 }
								}else{
									out.print("<div class=\"calendar_cell\">&nbsp;</div>");
								}
							    ///////////////////////////////////////////////////////////	
							
							}else{
								//TUTTI GLI ALTRI GIORNI DEL MESE ESCLUSA LA PRIMA RIGA
								if(dk < days_in_month){
									int today = ++dk;
									cal1.set(anno,mese1,today,0,0,0);
									
									
									MercatipresenzeT mercatipresenzeT = getGiornata(cal1,request);
									if(mercatipresenzeT != null){
									    idGiornata = mercatipresenzeT.getId().getCodice();
									    flagPopolaConcessionari = mercatipresenzeT.getFlagPopolaConcessionari();
										flagConteggiaPresAss = mercatipresenzeT.getFlagConteggiaPresAss();
										if(mercatipresenzeT.getConcessioniuso()!=null 
												&& mercatipresenzeT.getConcessioniuso().getId()!=null 
												&& mercatipresenzeT.getConcessioniuso().getId().getCodice()!=null){
										    concessioniUsoId = mercatipresenzeT.getConcessioniuso().getId().getCodice();    
										}										
									}
									
									
									// verifica se giorno festivo
									if(isHolidayDay(cal1,request)){
										holiday = "_holiday";
										storico = "";
										// verifica se è giorno di mercato
										if(isMarketDay(cal1,request)){
											selected = "_selected";
											_confirm = deleteConfirm;
											giornataEsistente = true;
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
												isStorico=true;
											}else{
												storico = "";
											}
											
										}else{
										    selected="";
										}
										
									}else{
										// giorno non festivo
										holiday = "";
										storico = "";
										// verifica se è giorno di mercato
										if(isMarketDay(cal1,request)){
											selected = "_selected";
											_confirm = deleteConfirm;
											giornataEsistente = true;
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
												isStorico=true;
											}else{
												storico = "";
											}
										//no mercato
										}else{
											selected="";	
										}
									}
									///////////////////////////////////////////////////////////////
									//  visualizza giorni storicizzati senza link
									if(isStorico){
									  out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");
									// visualizza giorni non storicizzati con link
									}else{
									   if(mercatoStoricizzato){
									       out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">" +(today)+ "</div>");
									   }else{
									    _confirm = _confirm.replace("@GIORNO@", Utilities.formatDate(cal1.getTime(), false));
									    
									    %>
										<div class="calendar<%= holiday+selected+storico%>_cell funzioni-calendario step1" data-idgiornata="<%= (idGiornata==null)?"": idGiornata %>"
												data-codicemercato="<%= codicemercato%>" data-codiceuso="<%= codiceuso %>"
												data-day="<%= today%>" data-month="<%= mese1%>" data-year="<%= anno%>"
												data-monthname="<%= mesi[i][j]%>" data-confirm="<%= _confirm%>" 
												data-esistente="<%= giornataEsistente %>"
												data-flagpopolaconcessionari="<%= flagPopolaConcessionari%>"
												data-flagconteggiapresass="<%= flagConteggiaPresAss%>" 
												data-concessioniusoid="<%=  (concessioniUsoId==null)?"": concessioniUsoId %>" ><%=today%></div>
										<%									   	
									   }
									}
								}else{
									out.print("<div class=\"calendar_cell\">&nbsp;</div>");
								}
								//////////////////////////////////////////////////////////////////////	
							}
							di++;
						%>					
						</td>
						</c:forEach>
						<%di=0; %>
						<%dj++; %>
					</tr>
					</c:forEach>			
					<%dj=0; %>
					<%dk=0; %>
				</table>
			 </td>
			<%j++; %>
			<%mese1++; %>
			</c:forEach>
			<%j=0; %>
		</tr>
		<%i++; %>
		</c:forEach>
	    <tr class="calendar_month_cell">
	    	<td colspan="4">
	    	<span>Legenda: </span>
	    	<span class="calendar_holiday_selected_cell">14</span>&nbsp;Giorno festivo&nbsp;&nbsp;
	    	<span class="calendar_selected_cell">14</span>&nbsp;Giorno feriale&nbsp;&nbsp;
	    	<span class="calendar_selected_storico_cell">14</span>&nbsp;Giorno storicizzato&nbsp;&nbsp;
	    	</td>
	    </tr>
	    </c:if>
	    
	    <%--STEP-2 VISUALIZZO IL CALENDARIO SOLO PER LA GESTIONE PRESENZE --%>
	    
	    <c:if test="${calendariomercatoParametri.step == '2'}">
		<div class="titoloSezione">
	         <fmt:message key="form.gestionepresenze.title" />
	    </div>
		<c:forEach begin="0" end="2" varStatus="monthrow">
		<tr>
			<c:forEach begin="0" end="3" varStatus="monthcol">		
				<%
				GregorianCalendar cal = new GregorianCalendar();
				cal.set(anno,mese++,1);
				int first_day_of_week = cal.getFirstDayOfWeek();
				int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
				int days_in_month = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				%>
			<td>
				<table class="calendar_month_cell">
	                <tr class="calendar_month_header_cell"><td colspan="7"><%=mesi[i][j]%> <a name="<%=mesi[i][j]%>"/></td></tr>
					<tr class="calendar_days_cell"><td>Lu</td><td>Ma</td><td>Me</td><td>Gi</td><td>Ve</td><td>Sa</td><td>Do</td></tr>				
					<%
					GregorianCalendar cal1 = new GregorianCalendar();
					%>
					<c:forEach begin="0" end="5" varStatus="dayrow">
					<tr>
						<c:forEach begin="0" end="6" varStatus="daycol">
						<td>						
						<%
						    int marketDay = -1;
							int c = (day_of_week+5)%7;
							if(dj == 0){					
								if(c <= di){
									int today = ++dk;
									cal1.set(anno,mese1,today,0,0,0);
									Integer idGiornata = getIdGiornata(cal1,request);
									
									// verifica se giorno festivo
									if(isHolidayDay(cal1,request)){
										holiday = "_holiday";
										// verifica se è giorno di mercato
										marketDay = isMarketDayGestionePresenze(cal1,request);
										if(marketDay != -1){
											selected = "_selected";
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
											}else{
												storico = "";
											}

												out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\"><a href=\"javascript:apriGestionePresenza('"+idGiornata+"')\">"+(today)+"</a></div>");
										}else{
										    storico = "";
											selected="";
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");
										}
										// non festivo	
									}else{
										holiday = "";
										// verifica se è giorno di mercato
										marketDay = isMarketDayGestionePresenze(cal1,request);
										if(marketDay != -1){
											selected = "_selected";
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
											}else{
												storico = "";
											}
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\"><a href=\"javascript:apriGestionePresenza('"+idGiornata+"')\">"+(today)+"</a></div>");						

										}else{
											selected="";
											storico="";
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");	
										}
									}								
								}else{
									out.print("<div class=\"calendar_cell\">&nbsp;</div>");
								}
							}else{
								if(dk < days_in_month){
									int today = ++dk;
									cal1.set(anno,mese1,today,0,0,0);
									// verifica se giorno festivo
									Integer idGiornata = getIdGiornata(cal1,request);
									if(isHolidayDay(cal1,request)){
										holiday = "_holiday";
										storico = "";
										// verifica se è giorno di mercato
										marketDay = isMarketDayGestionePresenze(cal1,request);
										if(marketDay != -1){
											selected = "_selected";
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
											}else{
												storico = "";
											}
												out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\"><a href=\"javascript:apriGestionePresenza('"+idGiornata+"')\">"+(today)+"</a></div>");

										}else{
											selected="";
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");
										}
										// non festivo	
									}else{
										holiday = "";
										storico = "";
										// verifica se è giorno di mercato
										marketDay = isMarketDayGestionePresenze(cal1,request);
										if(marketDay != -1){
											selected = "_selected";
											// verifica se il giorno è storicizzato
											if(isStorico(cal1,request)){
												storico = "_storico";
											}else{
												storico = "";
											}
												out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\"><a href=\"javascript:apriGestionePresenza('"+idGiornata+"')\">"+(today)+"</a></div>");
				
										}else{
											selected="";
											out.print("<div class=\"calendar"+holiday+selected+storico+"_cell\">"+(today)+"</div>");	
										}
									}									
								}else{
									out.print("<div class=\"calendar_cell\">&nbsp;</div>");
								}
							}
							di++;
						%>					
						</td>
						</c:forEach>
						<%di=0; %>
						<%dj++; %>
					</tr>
					</c:forEach>			
					<%dj=0; %>
					<%dk=0; %>
				</table>
			 </td>
			<%j++; %>
			<%mese1++; %>
			</c:forEach>
			<%j=0; %>
		</tr>
		<%i++; %>
		</c:forEach>
	    <tr class="calendar_month_cell">
	    	<td colspan="4">
	    	<span>Legenda: </span>
	    	<span class="calendar_holiday_selected_cell">14</span>&nbsp;Giorno festivo&nbsp;&nbsp;
	    	<span class="calendar_selected_cell">14</span>&nbsp;Giorno feriale&nbsp;&nbsp;
	    	<span class="calendar_selected_storico_cell">14</span>&nbsp;Giorno storicizzato&nbsp;&nbsp;
	    	</td>
	    </tr>
	    </c:if>
	  </table> 
	</spring-form:form>
</div>
<script type="text/javascript">
		var goToUrl = "../registrazionimercato/registrazioniByMercato.htm?mercati.id.codice=${mercati.id.codice}&mercatoUso=${mercatiUso.id.codice}&anno=${anno}";
		goToUrl = escape(goToUrl);
		
		function alertGiornoFuturo()
		{
			alert('La configurazione non permettone di accedere alle giornate successive alla data odierna');
		}
		
		
		function vaiAGiornata(codicemercato,codiceuso,giornomercato){
				historySet('${_urlback}','../gestionepresenze/list.htm?codiceMercato='+codicemercato +'&usoMercato='+codiceuso+'&giornoMercato='+giornomercato,'');		
		}
		function apriGestionePresenza(idGiornata){

			disableFunctions();
			
			var jhqrPr = jQuery.ajax({
				  url: '../calendariomercato/ajaxViewConfGestionePresenza.htm',
				  context: document.body,
				  type: "POST",
				  cache: false,
				  data: "idGiornata=" + idGiornata+"&url_back=${_urlback}",
				  dataType: "html",
				  success: function(data) {				
					 
					  jQuery('#giornoMercatoPanelContent').dialog({ 
						 	autoOpen: false, 
						 	modal : true, 
						 	width: 400,
							height: 400,
							resizable: false
					 	});
					enableFunctions();
					jQuery('#giornoMercatoPanelContent').html(data);
				 	jQuery('#giornoMercatoPanelContent').dialog("open");  
					  
					  
				
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata:" + jqXHR.responseText);
				}
			});		
		}
		
		


		function modificaUsoGiornata(obj, idGiornata){
			if(confirm('Attenzione! Si intende procedere alla modifica della configurazione?')){
					if(jQuery(obj).val()==''){
						alert("E' necessario selezionare un valore");
						return;
					}
					disableFunctions();
					var jhqrPr = jQuery.ajax({
						url: '${pageContext.request.contextPath}/gestionepresenze/ajaxModificaUsoGiornata.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'giornoMercatoIdCodice='+idGiornata+'&concessioneusoid='+ jQuery(obj).val(),
			     		cache: false,	
						
						  success: function(data) {				
							  
						  jQuery('#esitoOperazioni').dialog({ 
							 	autoOpen: false, 
							 	modal : true, 
							 	width: 400,
								height: 400,
								resizable: false
						 	});
							jQuery('#esitoOperazioni').html(data);
				     		enableFunctions();
				     		jQuery('#esitoOperazioni').dialog("open"); 
						  },
						  error: function(jqXHR, textStatus, errorThrown){
								console.error("Errore nella chiamata:" + jqXHR.responseText);
						}
					});		
			}
		}
		
		function modificaFlagPopolaConcessionari(obj, idGiornata){
			if(confirm('Attenzione! Si intende procedere alla modifica della configurazione?')){
				disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: '../gestionepresenze/ajaxModificaFlagPopolaConcessionari.htm',
					  context: document.body,
					  type: "POST",
					  cache: false,
					  data: "idGiornata=" + idGiornata+"&valore="+jQuery(obj).val(),
					  dataType: "html",
					  success: function(data) {				
						  jQuery('#esitoOperazioni').dialog({ 
							 	autoOpen: false, 
							 	modal : true, 
							 	width: 400,
								height: 400,
								resizable: false
						 	});
							jQuery('#esitoOperazioni').html(data);
				     		enableFunctions();
				     		jQuery('#esitoOperazioni').dialog("open"); 
					  },
					  error: function(jqXHR, textStatus, errorThrown){
							console.error("Errore nella chiamata:" + jqXHR.responseText);
					}
				});		
			}
		}
		
		function modificaFlagConteggiaPresenzeAssenze(obj, idGiornata){
			if(confirm('Attenzione! Si intende procedere alla modifica della configurazione?')){
				disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: '../gestionepresenze/ajaxModificaFlagConteggiaPresAss.htm',
					  context: document.body,
					  type: "POST",
					  cache: false,
					  data: "idGiornata=" + idGiornata+"&valore="+jQuery(obj).val(),
					  dataType: "html",
					  success: function(data) {				
						jQuery('#esitoOperazioni').html(data);
			     		enableFunctions();
			     		jQuery('#esitoOperazioni').show();
					  },
					  error: function(jqXHR, textStatus, errorThrown){
							console.error("Errore nella chiamata:" + jqXHR.responseText);
					}
				});		
			}
		}
		
		
(
	function($){
			$(function (){
				
				$('.funzioni-calendario').on('click', function(){
					var el = $(this);
					var codicemercato = el.data('codicemercato');					
					var codiceuso = el.data('codiceuso');
					var today = el.data('day');
					var month = el.data('month');
					var year = el.data('year');
					var monthname = el.data('monthname');
					var nomeGiorno = today +' '+monthname+ ' ' +year;
					
					var confirm = el.data('confirm');
					var giornataEsistente = el.data('esistente').toString()==='true';
					
					var idGiornata = el.data('idgiornata');
					var flagPopolaConcessionari = el.data('flagpopolaconcessionari').toString() === 'true'; 
					var flagConteggiaPresAss	= el.data('flagconteggiapresass').toString() === 'true'; 
					var	concessioniUsoId 		= el.data('concessioniusoid'); 
					
					$('#dlg_codicemercato').val(codicemercato);
					$('#dlg_codiceuso').val(codiceuso);
					$('#dlg_anno').val(year);
					$('#dlg_mese').val(month);
					$('#dlg_giorno').val(today);
					
					
					$('#dlg_elimina_id').toggle(idGiornata!=='');
					
					$('#dlg_id_giornata').val(idGiornata);
				

					$('#dlg_concessioni_uso_id').val(concessioniUsoId);
					
					$('#dlg_flagPopolaConcessionari_id').val(flagPopolaConcessionari.toString()); 	
				
					$('#dlg_flagConteggiaPresAss_id').val(flagConteggiaPresAss.toString());
					
					$('#dlg_configura_giornata_id').dialog({
						title: 'Configura giornata '+nomeGiorno,
					 	autoOpen: false, 
					 	modal : true, 
					 	width: 600,
						height: 400,
						resizable: false
				 	});
					
		     		$('#dlg_configura_giornata_id').dialog("open");					


				});
			}
		);
			
			
	 }
	
)(jQuery);
		
function aggiornaInserisciGiorno(){
	
	var idGiornata = jQuery('#dlg_id_giornata').val();
	
	
	if(!(idGiornata==='')){
		doSubmit('../calendariomercato/aggiornaGiornataMercato.htm', 'Volete modificare le impostazioni della giornata?', document.formdlgconfig);
	}else{
		doSubmit('../calendariomercato/inserisciGiornataMercato.htm', 'Volete inserire una nuova giornata?',document.formdlgconfig);
	}
}

function eliminaGiorno(){
	doSubmit('../calendariomercato/eliminaGiornataMercato.htm', '<%=deleteConfirm%>', document.formdlgconfig);
}			
		
</script>

<div id="dlg_configura_giornata_id" style="display: none">
	<form name="formdlgconfig" method="post">
	<input type="hidden" id="dlg_id_giornata" name="id_giornata" />	
	<input type="hidden" id="dlg_codicemercato" name="codicemercato" />
	<input type="hidden" id="dlg_codiceuso" name="codiceuso" />
	<input type="hidden" id="dlg_anno" name="anno" />	
	<input type="hidden" id="dlg_mese" name="mese" />
	<input type="hidden" id="dlg_giorno" name="giorno" />
	
	<div class="row">	
		<div class="col form-group">
			<label for="dlg_concessioni_uso_id"><fmt:message key="label.concessione_uso"/>			
			</label>
			
			<select name="conc_uso" id="dlg_concessioni_uso_id" class="form-control">
				<option value=""></option>
				<c:forEach items="${ concessioniUsos }" var="concessioniUso">			
					<option value="${concessioniUso.id.codice}">${concessioniUso.descrizione}</option>
				</c:forEach>
			</select>
			<init:help idHelp="helpConcessione" textKey="help.mercati.presenze.concessione_uso" />
		</div>            
	</div>
	<div class="row">	
		<div class="col form-group">
			<label for="dlg_flagPopolaConcessionari_id"><fmt:message key="label.flag_popola_concessionari" /></label>
			
			<select name="flagPopolaConcessionari" id="dlg_flagPopolaConcessionari_id" class="form-control">				
				<option value="true"><fmt:message key="label.si" /></option>
				<option value="false"><fmt:message key="label.no" /></option>					
			</select>            
			<init:help idHelp="helppopolaConcessionari" textKey="help.mercati.presenze.flag_popola_concessionari" />
		</div>            
	</div>   	     	
	<div class="row">	
		<div class="col form-group">
			<label for="dlg_flagConteggiaPresAss_id"><fmt:message key="label.flag_conteggia_presenze_assenze" /></label>
			
			<select name="flagConteggiaPresAss" id="dlg_flagConteggiaPresAss_id" class="form-control">
				
				<option value="true"><fmt:message key="label.si" /></option>
				<option value="false"><fmt:message key="label.no" /></option>					
			</select>            
			<init:help idHelp="helpflag_conteggia_presenze_assenze" textKey="help.mercati.presenze.flag_conteggia_presenze_assenze" />
		</div>            
	</div>  
	

	
	</form>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:aggiornaInserisciGiorno();"><fmt:message key="button.update" /></a></li>
			<li id="dlg_elimina_id"><a href="javascript:eliminaGiorno();"><fmt:message key="button.delete" /></a></li>
		</ul>
	</div>	
	
</div>


<div id="functions">
<ul>
	<c:if test="${calendariomercatoParametri.step==0}">
		<li><a href="javascript:doSubmit('insert.htm?mercati.id.codice=${mercati.id.codice}','',document.inviodati)"><fmt:message key="button.forward"/></a></li>
		<li><a href="javascript:doHref('listusianni.htm?mercati.id.codice=${mercati.id.codice}','')"><fmt:message key="button.close" /></a></li>
	</c:if>
	<c:if test="${calendariomercatoParametri.step==1}">
		
    	<c:if test="${flagMercatoStoricizzato eq false}">
    		<li><a href="javascript:doHref('delete.htm?mercati.id.codice=${mercati.id.codice}&mercatiuso.id.codice=${mercatiUso.id.codice}&anno=${anno}','<fmt:message key="javascript.confirm.delete" />')"><fmt:message key="button.delete" /></a></li>
    	</c:if>
    	<%if(numerogiornimercato == 0){ %>
    	<li><a href="javascript:doHref('listusianni.htm?mercati.id.codice=${mercati.id.codice}','<fmt:message key="form.calendariomercatoParametri.zerogiorni.alert" />')"><fmt:message key="button.close" /></a></li>
    	<%}else{ %>
    	<li><a href="javascript:doHref('listusianni.htm?mercati.id.codice=${mercati.id.codice}','')"><fmt:message key="button.close" /></a></li>
    	<%} %>
    </c:if>
    <c:if test="${calendariomercatoParametri.step==2}"> 
    	<c:if test="${flagMercatoStoricizzato eq false}">
    		<li><a href="javascript:doHref('../gestionepresenze/chiudiAnnoMercato.htm?mercati.id.codice=${mercati.id.codice}&mercatouso.id.codice=${mercatiUso.id.codice}&anno=${anno}','<fmt:message key="button.storicizzazione.alert" />')"><fmt:message key="button.storicizzazione" /></a></li>
  		 </c:if>
  		 <%if(numerogiornimercato == 0){ %>		 
  		 <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','<fmt:message key="form.calendariomercatoParametri.zerogiorni.alert" />')"><fmt:message key="button.back" /></a></li>
  		 <%}else{ %>		 
  		 <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
  		 <%} %> 
    </c:if>
  
</ul>
</div>

	<div style="display: none" url-back="${_urlback}" id="url_back_el" />
	
	
		
		<div id="giornoMercatoPanelContent" style="padding: 10px;">
		</div>
		
			<div id="esitoOperazioni" style="padding: 10px;">
		</div>



</body>
</html>
