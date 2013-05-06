<%@ page import="javax.naming.Context" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.naming.InitialContext" %>

<%

    Context ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bees-shop-db");
    Connection conn = ds.getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rst = stmt.executeQuery("select 1");
    while (rst.next()) {
        out.print(rst.getString(1));
    }
    rst.close();
    stmt.close();
    conn.close();
%>