<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.backoffice.web.util.Easter"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Calendario</title>
</head>
<body>
<%
String[][] mesi = new String[][]{{"Gennaio","Febbraio","Marzo","Aprile"},{"Maggio","Giugno","Luglio","Agosto"},{"Settembre","Ottobre","Novembre","Dicembre"}};
pageContext.setAttribute("mesi", mesi);

int anno = (Integer)request.getAttribute("anno");


int mese = 0;
int mese1 = 0;
int i = 0;
int j = 0;
int di = 0;
int dj = 0;
int dk = 0;
%>
<%!
private boolean isToday(Calendar date){
	boolean success = false;
	GregorianCalendar today = new GregorianCalendar();
	if(date.get(Calendar.YEAR) == today.get(Calendar.YEAR)&& date.get(Calendar.MONTH) == today.get(Calendar.MONTH) && date.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))success = true;
	return success;
}
private boolean isHoliday(Calendar date){
	boolean success = false;
	List<Calendar> feste = new ArrayList<Calendar>();
	int anno = 2009;//va bene un anno fisso, serve solo per creare gli oggetti calendar.
	
	try{
		Calendar pasqua = new GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH)-1);
		if(Easter.isEaster(pasqua.getTime())){
			feste.add(date);
		}
	}catch(Easter.YearOutOfRangeException e){
	}
	
	
	Calendar primoGennaio = new GregorianCalendar(anno,Calendar.JANUARY,1);
	feste.add(primoGennaio);
	Calendar seiGennaio = new GregorianCalendar(anno,Calendar.JANUARY,6);
	feste.add(seiGennaio);
	Calendar venticinqueAprile = new GregorianCalendar(anno,Calendar.APRIL,25);
	feste.add(venticinqueAprile);
	Calendar primoMaggio = new GregorianCalendar(anno,Calendar.MAY,1);
	feste.add(primoMaggio);
	Calendar dueGiugno = new GregorianCalendar(anno,Calendar.JUNE,2);
	feste.add(dueGiugno);
	Calendar quindiciAgosto = new GregorianCalendar(anno,Calendar.AUGUST,15);
	feste.add(quindiciAgosto);
	Calendar primoNovembre = new GregorianCalendar(anno,Calendar.NOVEMBER,1);
	feste.add(primoNovembre);
	Calendar ottoDicembre = new GregorianCalendar(anno,Calendar.DECEMBER,8);
	feste.add(ottoDicembre);
	Calendar venticinqueDicembre = new GregorianCalendar(anno,Calendar.DECEMBER,25);
	feste.add(venticinqueDicembre);
	Calendar santoStefano = new GregorianCalendar(anno,Calendar.DECEMBER,26);
	feste.add(santoStefano);
	for(int i = 0; i < feste.size();i++){
		Calendar festa = feste.get(i);
		if(date.get(Calendar.MONTH)==festa.get(Calendar.MONTH)&&date.get(Calendar.DAY_OF_MONTH)==festa.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
	}
	if(isSunday(date))return true;
	return success;
} 
private boolean isSunday(Calendar date){
	boolean success = false;
	if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)success=true;
	return success;
}
%>
<table cellspacing="5">
	<tr><td colspan="4" class="calendar_header"><a href="calendario.htm?anno=${anno-1 }">&lt;</a><%=anno %><a href="calendario.htm?anno=${anno+1 }">&gt;</a></td></tr>
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
				<tr class="calendar_month_header_cell"><td colspan="7"><%=mesi[i][j] %></td></tr>
				<tr class="calendar_days_cell"><td>Lu</td><td>Ma</td><td>Me</td><td>Gi</td><td>Ve</td><td>Sa</td><td>Do</td></tr>				
				<%
					GregorianCalendar cal1 = new GregorianCalendar();
				%>
				<c:forEach begin="0" end="5" varStatus="dayrow">
				<tr>
					<c:forEach begin="0" end="6" varStatus="daycol">
					<td>
					<%
						int c = (day_of_week+5)%7;
						if(dj == 0){					
							if(c <= di){
								int today = ++dk;
								cal1.set(anno,mese1,today,0,0,0);
								if(isHoliday(cal1)){
									if(isToday(cal1)){
										out.print("<div class=\"calendar_holiday_selected_cell\"><a href=\"#\">"+today+"</a></div>");
									}else{
										out.print("<div class=\"calendar_holiday_cell\"><a href=\"#\">"+today+"</a></div>");
									}
								}else{
									if(isToday(cal1)){
										out.print("<div class=\"calendar_selected_cell\"><a href=\"#\">"+today+"</a></div>");
									}else{
										out.print("<div class=\"calendar_day_cell\"><a href=\"#\">"+today+"</a></div>");
									}
								}
							}else{
								out.print("<div class=\"calendar_day_cell\">&nbsp;</div>");
							}
						}else{
							if(dk < days_in_month){
								int today = ++dk;
								cal1.set(anno,mese1,today,0,0,0);
								if(isHoliday(cal1)){
									if(isToday(cal1)){
										out.print("<div class=\"calendar_holiday_selected_cell\"><a href=\"#\">"+today+"</a></div>");
									}else{
										out.print("<div class=\"calendar_holiday_cell\"><a href=\"#\">"+today+"</a></div>");
									}
								}else{
									if(isToday(cal1)){
										out.print("<div class=\"calendar_selected_cell\"><a href=\"#\">"+today+"</a></div>");
									}else{
										out.print("<div class=\"calendar_day_cell\"><a href=\"#\">"+today+"</a></div>");
									}
								}
							}else{
								out.print("<div class=\"calendar_day_cell\">&nbsp;</div>");
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
</table>
</body>
</html>