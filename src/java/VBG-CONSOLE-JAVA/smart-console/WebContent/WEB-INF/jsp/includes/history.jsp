<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 
	pagina da includere dove è necessario utilizzare il meccanismo di history back. 
	in questo modo nella request è settato come attributo (_urlback) il valore dell'uri con
	il quale si è raggiunta la funzionalità corrente.
--%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<%
	String urlBack = "";
	try{
	    String qs = request.getQueryString();
	    String _qs = request.getParameter("qs");
	    if(_qs != null){
			qs = URLDecoder.decode(_qs,"UTF-8");
	    }else{
	    	qs = qs == null ? "" : qs;
	    }
	    urlBack = URLEncoder.encode(request.getParameter("path") + ".htm?" + qs, "UTF-8");
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	}catch(java.io.UnsupportedEncodingException e){};
	request.setAttribute("_urlback", urlBack);
%>
