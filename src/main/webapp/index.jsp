<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%><%
    String serverName = request.getServerName();
    if( serverName.startsWith("platform")){
        response.sendRedirect("platform/login.jsp");
    }else{
        response.sendRedirect("platform/login.jsp");
    }
    return;
%>