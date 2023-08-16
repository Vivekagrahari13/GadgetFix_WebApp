package com.fix_gadget.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fix_gadget.models.DAO;

/**
 * Servlet implementation class UserSignIn
 */
@WebServlet("/UserSignIn")
public class UserSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			DAO db = new DAO();
			String name = db.userSignIn(email, password);
			db.closeConnection();
			HttpSession session = request.getSession();
			if (name != null) {
				session.setAttribute("user_name", name);
				session.setAttribute("user_email", email);
				response.sendRedirect("UserHome.jsp");
			} else {
				session.setAttribute("msg", "Invalid Entries!!");
				response.sendRedirect("User.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}

	}

}
