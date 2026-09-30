<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%


Context initCtx = new InitialContext();
Context envCtx = (Context) initCtx.lookup("java:comp/env");
DataSource ds = (DataSource)
envCtx.lookup("jdbc/cart-im-adapter");
try{
	Connection conn = ds.getConnection();
	out.print("CARICAMENTO CONNESSIONE DB COMUNICA: OK");
	conn.close();
}catch(Exception e){
    out.print("CARICAMENTO CONNESSIONE DB COMUNICA: KO");
}
out.print("<br />");

	String csv_sportelli_suap = (String)envCtx.lookup("csv_sportelli_suap");
	
out.print("COMUNICA: sportelli suap disponibile in "+csv_sportelli_suap);
out.print("<br />");

%>
</body>
</html>