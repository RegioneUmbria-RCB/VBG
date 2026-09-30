<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="jmesa" uri="http://code.google.com/p/jmesa" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="init" uri="http://www.gruppoinit.it/tag" %>
<%@ taglib prefix="inite" uri="/WEB-INF/init_tags/custom.tld" %>
<%String  vJS = ((String)request.getSession().getServletContext().getServletContextName()).replace(" ", "_").trim().toLowerCase();%>