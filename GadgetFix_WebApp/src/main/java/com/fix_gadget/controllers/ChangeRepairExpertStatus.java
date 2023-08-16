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
@WebServlet("/ChangeRepairExpertStatus")
public class ChangeRepairExpertStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String status = request.getParameter("status");
			DAO db = new DAO();
			db.changeRepairExpertStatus(email, status);
			db.closeConnection();
			HttpSession session = request.getSession();
			session.setAttribute("msg", "Status Updation Success !");
			response.sendRedirect("RepairExpertDetails.jsp?email=" + email);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
