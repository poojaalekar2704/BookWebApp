package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class registerServlet extends HttpServlet {
	
	private static final String query="insert into bookdata(bookname,bookedition,bookprice)values(?,?,?)";
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
// get printwriter
	PrintWriter pw=res.getWriter();	
	//set containt
	res.setContentType("text/html");
	//get book info
	String bookname=req.getParameter("bookname");
	String bookedition=req.getParameter("bookedition");
	float bookprice=Float.parseFloat(req.getParameter("bookprice"));
	// load driver
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	//genertae 
	try(Connection con=DriverManager.getConnection("jdbc:mysql:///book", "root", "root");
			PreparedStatement ps=con.prepareStatement(query);){
		ps.setString(1,bookname);
		ps.setString(2, bookedition);
		ps.setFloat(3, bookprice);
		int count=ps.executeUpdate();
		if(count==1) {
			pw.println("record inserted  successfully");
		}else {
			pw.println("not registered");
		}
		
}catch(SQLException se) {
	se.printStackTrace();
	pw.println("<h1>"+se.getMessage()+"</h2>");
}catch(Exception e) {
	e.printStackTrace();
	pw.println("<h1>"+e.getMessage()+"</h2>");
}
	pw.println("<a href='home.html'>Home</a>");
	pw.println("<br>");
	pw.println("<a href='bookList'>Book List</a>");
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
doGet(req,res);
	
	}
	
}
