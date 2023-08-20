package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/guideservlet")
public class guideservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String place=request.getParameter("place");
		String member=request.getParameter("member");
		String day=request.getParameter("day");
		String budget=request.getParameter("budget");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/kerala_tour?useSSL=false","root","Parthiban@12345");
			PreparedStatement pst=con.prepareStatement("insert into forms(name,phone,place,member,day,budget) values(?,?,?,?,?,?)");
			pst.setString(1, name);
			pst.setString(2, phone);
			pst.setString(3, place);
			pst.setString(4, member);
			pst.setString(5, day);
			pst.setString(6, budget);
			int rowcount=pst.executeUpdate();
			dispatcher=request.getRequestDispatcher("successfull.html");
			if(rowcount>0)
			{
				request.setAttribute("status","success");	
			}
			else
			{
				request.setAttribute("status","failed");	
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
