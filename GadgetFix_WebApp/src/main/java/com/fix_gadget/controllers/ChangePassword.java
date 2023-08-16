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
 * Servlet implementation class AdminLogin
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String old_password = request.getParameter("old_password");
			String new_password = request.getParameter("new_password");
			String confirm_password = request.getParameter("confirm_password");
			String email = request.getParameter("email");
			String type = request.getParameter("type");
			HttpSession session = request.getSession();
			if (new_password.equals(confirm_password)) {
				DAO db = new DAO();
				boolean result = db.changePassword(old_password, new_password, email, type);
				db.closeConnection();
				if (result) {
					session.setAttribute("msg", "Password Updation Success !");
				} else {
					session.setAttribute("msg", "Password Updation Failed !");
				}
			} else {
				session.setAttribute("msg", "Password Mismatched!");
			}
			response.sendRedirect("RepairExpertChangePassword.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
