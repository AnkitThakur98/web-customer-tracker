package com.customertracker.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user = "springstudent";
        String pass = "springstudent";

        String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";

        try{
            PrintWriter out = response.getWriter();
            out.println("Connecting to database..." + jdbcUrl);
            Class.forName(driver);
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);

            out.println("Connection successful...");
            myConn.close();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }

    }
}
