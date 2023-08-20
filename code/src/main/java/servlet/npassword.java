package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/npassword")
public class npassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String newpassword=request.getParameter("newpassword");
		String confirmpassword=request.getParameter("confirmpassword");
		RequestDispatcher dispatcher = null;
		if(newpassword!=null && confirmpassword!=null && newpassword.equals(confirmpassword))
		{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/kerala_tour?useSSL=false","root","Parthiban@12345");
				PreparedStatement pst=con.prepareStatement("update register_table set password= ? where email=?");
				pst.setString(1, newpassword);
				pst.setString(2, (String) session.getAttribute("email"));
				int rowcount=pst.executeUpdate();
				if(rowcount>0)
				{
					request.setAttribute("status", "resetSuccess");
					dispatcher = request.getRequestDispatcher("login.html");
				}
				else
				{
					request.setAttribute("status", "resetFailed");
					dispatcher = request.getRequestDispatcher("notsuccess.html");
				}
				dispatcher.forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
